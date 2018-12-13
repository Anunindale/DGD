/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.debtors.invoiceregister;

import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.sop.SOPCustomers;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.reporttools.EMCReportConfig;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class DebtorsInvoiceRegisterReportBean extends EMCReportBean implements DebtorsInvoiceRegisterReportLocal {

    @EJB
    private DebtorsParametersLocal debtorsParamBean;

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        boolean breakPerRep = (Boolean) reportConfig.getParameters().get("breakPerRep");
        String invoiceCreditNote = (String) reportConfig.getParameters().get("invoiceCreditNote");
        String salesManual = (String) reportConfig.getParameters().get("salesManual");

        DebtorsParameters debtorsParam = debtorsParamBean.getDebtorsParameters(userData);

        EMCQuery query = (EMCQuery) queryList.get(1);
        query.addField("invoiceDate", DebtorsCustomerInvoiceMaster.class.getName());//0
        query.addField("customerId", SOPCustomers.class.getName());//1
        query.addField("customerName", SOPCustomers.class.getName());//2
        query.addField("invCNNumber", DebtorsCustomerInvoiceMaster.class.getName());//3
        query.addField("salesOrderNo", DebtorsCustomerInvoiceMaster.class.getName());//4
        query.addField("salesRep", DebtorsCustomerInvoiceMaster.class.getName());//5
        query.addField("forenames", BaseEmployeeTable.class.getName());//6
        query.addField("surname", BaseEmployeeTable.class.getName());//7
        query.addFieldAggregateFunction("totalCost", DebtorsCustomerInvoiceLines.class.getName(), "SUM");//8
        query.addFieldAggregateFunction("vatAmount", DebtorsCustomerInvoiceLines.class.getName(), "SUM");//9

        String linesAlias = query.getTableAlias(DebtorsCustomerInvoiceLines.class);
        //Get gross total
        StringBuilder customField = new StringBuilder("SUM(ROUND(");
        customField.append(linesAlias);
        customField.append(".quantity * ");
        customField.append(linesAlias);
        customField.append(".unitPrice, 2))");

        query.addCustomField(customField.toString(), false);//10

        //Get total discount
        customField = new StringBuilder("SUM(ROUND(");
        customField.append(linesAlias);
        customField.append(".discountAmount, 2))");

        query.addCustomField(customField.toString(), false);//11

        query.addGroupBy(DebtorsCustomerInvoiceMaster.class.getName(), "invCNNumber");

        if (breakPerRep) {
            query.addOrderBy("salesRep", DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryOrderByDirections.ASC, 0);
        }

        if ("Sales".equals(salesManual)) {
            query.addAnd("invCNType", "Sales", DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.STARTS_WITH);
        } else if ("Manual".equals(salesManual)) {
            query.addAnd("invCNType", "Manual", DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.STARTS_WITH);
        }

        super.checkCompanyId(query, userData);
        String queryString = query.getNativeQuery();
        queryString = queryString.replaceAll(BaseEmployeeTable.class.getSimpleName(), "BaseEmployee");

        List<Object[]> dataList = new ArrayList<Object[]>();

        if ("Invoices".equals(invoiceCreditNote)) {
            dataList.addAll(util.executeNativeQuery(queryString, userData));
        } else if ("Credit Notes".equals(invoiceCreditNote)) {
            queryString = queryString.replaceAll(DebtorsCustomerInvoiceMaster.class.getSimpleName(), DebtorsCreditNoteMaster.class.getSimpleName());
            queryString = queryString.replaceAll(DebtorsCustomerInvoiceLines.class.getSimpleName(), DebtorsCreditNoteLines.class.getSimpleName());

            dataList.addAll(util.executeNativeQuery(queryString, userData));
        } else if ("Both".equals(invoiceCreditNote)) {
            dataList.addAll(util.executeNativeQuery(queryString, userData));

            queryString = queryString.replaceAll(DebtorsCustomerInvoiceMaster.class.getSimpleName(), DebtorsCreditNoteMaster.class.getSimpleName());
            queryString = queryString.replaceAll(DebtorsCustomerInvoiceLines.class.getSimpleName(), DebtorsCreditNoteLines.class.getSimpleName());

            dataList.addAll(util.executeNativeQuery(queryString, userData));
        }

        DebtorsInvoiceRegisterReportDS ds;
        List reportData = new ArrayList();

        BigDecimal sundri = null;

        for (Object[] data : dataList) {
            ds = new DebtorsInvoiceRegisterReportDS();
            ds.setInvoiceDate(Functions.date2String((Date) data[0]));
            ds.setCustomrtId((String) data[1]);
            ds.setCustomerName((String) data[2]);
            ds.setInvoiceNo((String) data[3]);
            ds.setSalesOrderNo((String) data[4]);
            ds.setSalesRep((String) data[5]);
            ds.setSalesRepName((data[6] == null ? "" : (String) data[6]) + " " + (data[7] == null ? " " : (String) data[7]));

            ds.setSalesValue(util.roundBigDecimal(((BigDecimal) data[8])));
            ds.setVatAmount(util.roundBigDecimal(((BigDecimal) data[9])));
            ds.setGrossTotal(util.roundBigDecimal(((BigDecimal) data[10])));
            ds.setDiscountTotal(data[11] == null ? BigDecimal.ZERO : util.roundBigDecimal(((BigDecimal) data[11])));
            ds.setInvoiceTotal(ds.getSalesValue().add(ds.getVatAmount()));
            ds.setSundri(new BigDecimal(0, new MathContext(2)));

            if (!isBlank(debtorsParam.getDeliveryChargeItem())) {


                query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
                query.addTableAnd(DebtorsCustomerInvoiceMaster.class.getName(), "invCNNumber", DebtorsCustomerInvoiceLines.class.getName(), "invCNNumber");
                query.addAnd("invCNNumber", data[3]);
                query.addAnd("itemId", debtorsParam.getDeliveryChargeItem());
                query.addFieldAggregateFunction("totalCost", DebtorsCustomerInvoiceLines.class.getName(), "SUM");
                query.addGroupBy("itemId");

                if ("Sales".equals(salesManual)) {
                    query.addAnd("invCNType", "Sales", DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.STARTS_WITH);
                } else if ("Manual".equals(salesManual)) {
                    query.addAnd("invCNType", "Manual", DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.STARTS_WITH);
                }

                super.checkCompanyId(query, userData);
                queryString = query.toString();
                queryString = queryString.replaceAll(DebtorsCustomerInvoiceMaster.class.getName(), DebtorsCustomerInvoiceMaster.class.getSimpleName());
                queryString = queryString.replaceAll(DebtorsCustomerInvoiceLines.class.getName(), DebtorsCustomerInvoiceLines.class.getSimpleName());

                if ("Invoices".equals(invoiceCreditNote)) {
                    sundri = (BigDecimal) util.executeSingleResultQuery(queryString, userData);
                    if (sundri == null) {
                        sundri = new BigDecimal(0, new MathContext(2));
                    }
                } else if ("Credit Notes".equals(invoiceCreditNote)) {
                    queryString = queryString.replaceAll(DebtorsCustomerInvoiceMaster.class.getSimpleName(), DebtorsCreditNoteMaster.class.getName());
                    queryString = queryString.replaceAll(DebtorsCustomerInvoiceLines.class.getSimpleName(), DebtorsCreditNoteLines.class.getName());

                    sundri = (BigDecimal) util.executeSingleResultQuery(queryString, userData);
                    if (sundri == null) {
                        sundri = new BigDecimal(0, new MathContext(2));
                    }
                } else if ("Both".equals(invoiceCreditNote)) {
                    sundri = (BigDecimal) util.executeSingleResultQuery(queryString, userData);
                    if (sundri == null) {
                        sundri = new BigDecimal(0, new MathContext(2));
                    }

                    queryString = queryString.replaceAll(DebtorsCustomerInvoiceMaster.class.getSimpleName(), DebtorsCreditNoteMaster.class.getSimpleName());
                    queryString = queryString.replaceAll(DebtorsCustomerInvoiceLines.class.getSimpleName(), DebtorsCreditNoteLines.class.getSimpleName());

                    BigDecimal tempSundri = (BigDecimal) util.executeSingleResultQuery(queryString, userData);
                    if (tempSundri == null) {
                        tempSundri = new BigDecimal(0, new MathContext(2));
                    }
                    sundri = sundri.add(tempSundri);
                }

                BigDecimal sundry = util.roundBigDecimal(new BigDecimal(sundri.doubleValue()));

                ds.setSalesValue(ds.getSalesValue().subtract(sundry));
                ds.setSundri(sundry);
            }

            if (breakPerRep) {
                ds.setRepBreak((String) data[5]);
            }
            reportData.add(ds);
        }
        return reportData;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.debtors.invoiceregisterdetail;

import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
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
public class DebtorsInvoiceRegisterReportDetailBean extends EMCReportBean implements DebtorsInvoiceRegisterReportDetailLocal {

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

        query.addField("itemReference", InventoryItemMaster.class.getName());//10
        query.addField("description", InventoryItemMaster.class.getName());//11
        query.addField("dimension1", DebtorsCustomerInvoiceLines.class.getName());//12
        query.addField("description", InventoryDimension1.class.getName());//13
        query.addField("dimension2", DebtorsCustomerInvoiceLines.class.getName());//14
        query.addField("dimension3", DebtorsCustomerInvoiceLines.class.getName());//15
        query.addField("description", InventoryDimension3.class.getName());//16
        query.addField("quantity", DebtorsCustomerInvoiceLines.class.getName());//17
        query.addField("uom", DebtorsCustomerInvoiceLines.class.getName());//18
        query.addField("totalCost", DebtorsCustomerInvoiceLines.class.getName());//19
        query.addField("vatAmount", DebtorsCustomerInvoiceLines.class.getName());//20
        query.addField("itemId", InventoryItemMaster.class.getName());//21

        query.addField("reference", DebtorsCustomerInvoiceMaster.class.getName());//22
        query.addField("customerOrderNumber", DebtorsCustomerInvoiceMaster.class.getName());//23
        query.addFieldAggregateFunction("quantity", DebtorsCustomerInvoiceLines.class.getName(), "SUM");//24

        //If 'Both' is used, select from Invoices first.
        String linesAlias = query.getTableAlias(DebtorsCustomerInvoiceLines.class);

        //Get gross total
        StringBuilder customField = new StringBuilder("SUM(ROUND(");
        customField.append(linesAlias);
        customField.append(".quantity * ");
        customField.append(linesAlias);
        customField.append(".unitPrice, 2))");

        query.addCustomField(customField.toString(), false);//25

        //Get total discount
        customField = new StringBuilder("SUM(ROUND(");
        customField.append(linesAlias);
        customField.append(".discountAmount, 2))");

        query.addCustomField(customField.toString(), false);//26

        //Get gross total for each line
        customField = new StringBuilder("ROUND(");
        customField.append(linesAlias);
        customField.append(".quantity * ");
        customField.append(linesAlias);
        customField.append(".unitPrice, 2)");

        query.addCustomField(customField.toString(), false);//27

        //Get total discount for each line
        customField = new StringBuilder("ROUND(");
        customField.append(linesAlias);
        customField.append(".discountAmount, 2)");

        query.addCustomField(customField.toString(), false);//28

        query.addGroupBy(DebtorsCustomerInvoiceMaster.class.getName(), "invCNNumber");
        query.addGroupBy(DebtorsCustomerInvoiceLines.class.getName(), "lineNo");

        query.addOrderBy("itemReference", InventoryItemMaster.class.getName());
        query.addOrderBy("sortCode", InventoryDimension1.class.getName());
        query.addOrderBy("sortCode", InventoryDimension3.class.getName());
        query.addOrderBy("sortCode", InventoryDimension2.class.getName());

        if (breakPerRep) {
            query.addOrderBy("salesRep", DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryOrderByDirections.ASC, 0);
        }

        if ("Sales".equals(salesManual)) {
            query.addAnd("invCNType", "Sales", DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.STARTS_WITH);
        } else {
            if ("Manual".equals(salesManual)) {
                query.addAnd("invCNType", "Manual", DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.STARTS_WITH);
            }
        }
        super.checkCompanyId(query, userData);
        String queryString = query.getNativeQuery();
        queryString = queryString.replaceAll(BaseEmployeeTable.class.getSimpleName(), "BaseEmployee");
        //FIX EMCQuery: Comma on outer join with normal joins
        queryString = queryString.replaceAll(DebtorsCustomerInvoiceMaster.class.getSimpleName(), ", " + DebtorsCustomerInvoiceMaster.class.getSimpleName());

        List<Object[]> dataList = new ArrayList<Object[]>();
        if ("Invoices".equals(invoiceCreditNote)) {
            dataList.addAll(util.executeNativeQuery(queryString, userData));
        } else {
            if ("Credit Notes".equals(invoiceCreditNote)) {
                queryString = queryString.replaceAll(DebtorsCustomerInvoiceMaster.class.getSimpleName(), DebtorsCreditNoteMaster.class.getSimpleName());
                queryString = queryString.replaceAll(DebtorsCustomerInvoiceLines.class.getSimpleName(), DebtorsCreditNoteLines.class.getSimpleName());

                dataList.addAll(util.executeNativeQuery(queryString, userData));
            } else {
                if ("Both".equals(invoiceCreditNote)) {
                    dataList.addAll(util.executeNativeQuery(queryString, userData));

                    queryString = queryString.replaceAll(DebtorsCustomerInvoiceMaster.class.getSimpleName(), DebtorsCreditNoteMaster.class.getSimpleName());
                    queryString = queryString.replaceAll(DebtorsCustomerInvoiceLines.class.getSimpleName(), DebtorsCreditNoteLines.class.getSimpleName());

                    dataList.addAll(util.executeNativeQuery(queryString, userData));
                }
            }
        }

        DebtorsInvoiceRegisterReportDetailDS ds;
        List reportData = new ArrayList();

        List<Object[]> invoiceData;
        BigDecimal totalSundri = new BigDecimal(0, new MathContext(2));
        BigDecimal totalSales = new BigDecimal(0, new MathContext(2));
        BigDecimal totalVat = new BigDecimal(0, new MathContext(2));
        BigDecimal totalQuantity = new BigDecimal(0, new MathContext(2));
        BigDecimal totalDiscount = new BigDecimal(0, new MathContext(2));
        BigDecimal totalGross = new BigDecimal(0, new MathContext(2));
        String previousInvoice = "";

        for (int i = 0; i < dataList.size(); i++) {
            Object[] data = dataList.get(i);

            ds = new DebtorsInvoiceRegisterReportDetailDS();
            ds.setInvoiceDate(Functions.date2String((Date) data[0]));
            ds.setCustomrtId((String) data[1]);
            ds.setCustomerName((String) data[2]);
            ds.setInvoiceNo((String) data[3]);
            ds.setSalesOrderNo((String) data[4]);
            ds.setSalesRep((String) data[5]);
            ds.setSalesRepName((data[6] == null ? " " : (String) data[6]) + " " + (data[7] == null ? " " : (String) data[7]));
            ds.setReference((String) data[22]);
            ds.setCustOrder((String) data[23]);
            ds.setSalesValue(util.roundBigDecimal(((BigDecimal) data[8])));
            ds.setVatAmount(util.roundBigDecimal(((BigDecimal) data[9])));
            ds.setInvoiceQuantity(util.roundBigDecimal(((BigDecimal) data[24])));
            ds.setInvoiceTotal(ds.getSalesValue().add(ds.getVatAmount()));

            if (!previousInvoice.equals((String) data[3])) {
                previousInvoice = (String) data[3];

                totalSundri = new BigDecimal(0, new MathContext(2));
                totalSales = new BigDecimal(0, new MathContext(2));
                totalVat = new BigDecimal(0, new MathContext(2));
                totalQuantity = new BigDecimal(0, new MathContext(2));
                totalSales = new BigDecimal(0, new MathContext(2));
                totalDiscount = new BigDecimal(0, new MathContext(2));
                totalGross = new BigDecimal(0, new MathContext(2));
                
                previousInvoice = (String) data[3];
                //Add all records for current invoice
                for (int j = i; j < dataList.size(); j++) {
                    if (!util.checkObjectsEqual(dataList.get(j)[3], previousInvoice)) {
                        break;
                    }
                    Object[] tempData = dataList.get(j);
                    totalQuantity = totalQuantity.add(util.roundBigDecimal(((BigDecimal) tempData[17])));
                    totalDiscount = totalDiscount.add(tempData[28] == null ? BigDecimal.ZERO : util.roundBigDecimal(((BigDecimal) tempData[28])));
                    totalGross = totalGross.add(util.roundBigDecimal(((BigDecimal) tempData[27])));
                    totalVat = totalVat.add(util.roundBigDecimal(((BigDecimal) tempData[20])));
                    if (!isBlank(debtorsParam.getDeliveryChargeItem()) && debtorsParam.getDeliveryChargeItem().equals(tempData[21].toString())) {
                        totalSundri = totalSundri.add(util.roundBigDecimal(((BigDecimal) tempData[19])));
                    } else {
                        totalSales = totalSales.add(util.roundBigDecimal(((BigDecimal) tempData[19])));
                    }
                }
            }

            ds.setInvoiceQuantity(totalQuantity);
            ds.setSundri(totalSundri);
            ds.setSalesValue(totalSales);
            ds.setVatAmount(totalVat);
            ds.setInvoiceTotal(ds.getSalesValue().add(ds.getVatAmount()).add(ds.getSundri()));
            ds.setGrossTotal(totalGross);
            ds.setDiscountTotal(totalDiscount);

            ds.setItemReference((String) data[10]);
            ds.setItemDescription((String) data[11]);
            ds.setDimension1((String) data[12]);
            if (!isBlank(ds.getDimension1())) {
                ds.setDimensionDescription((String) data[13]);
            }
            ds.setDimension2((String) data[14]);
            ds.setDimension3((String) data[15]);
            if (!isBlank(ds.getDimension3())) {
                ds.setDimensionDescription((String) data[16]);
            }
            ds.setQuantity(util.roundBigDecimal(((BigDecimal) data[17])));
            ds.setUom((String) data[18]);
            ds.setDetailSalesValue(util.roundBigDecimal(((BigDecimal) data[19])));
            ds.setDetailVatAmount(util.roundBigDecimal(((BigDecimal) data[20])));
            ds.setDetailInvoiceTotal(ds.getDetailSalesValue().add(ds.getDetailVatAmount()));
            ds.setDetailGrossTotal(util.roundBigDecimal(((BigDecimal) data[27])));
            ds.setDetailDiscountTotal(data[28] == null ? BigDecimal.ZERO : util.roundBigDecimal(((BigDecimal) data[28])));

            if (((String) data[21]).equals(debtorsParam.getDeliveryChargeItem())) {
                ds.setDetailedSundri(ds.getDetailSalesValue());
                ds.setDetailSalesValue(new BigDecimal(0, new MathContext(2)));
            } else {
                ds.setDetailedSundri(new BigDecimal(0, new MathContext(2)));
            }

            if (breakPerRep) {
                ds.setRepBreak((String) data[5]);
            }

            reportData.add(ds);
        }
        return reportData;
    }
}

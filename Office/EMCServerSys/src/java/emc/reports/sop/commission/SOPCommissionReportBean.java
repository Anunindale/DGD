
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.sop.commission;

import emc.bus.base.BaseEmployeeLocal;
import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.bus.sop.parameters.SOPParametersLocal;
import emc.bus.sop.salesrepcommission.SOPSalesRepCommissionLocal;
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.inventory.InventoryItemGroup;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPParameters;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.debtors.DebtorsInvoiceCreditNoteType;
import emc.enums.debtors.transactions.DebtorsTransactionRefTypes;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryFieldTypes;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.reporttools.EMCReportConfig;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPCommissionReportBean extends EMCReportBean implements SOPCommissionreportLocal {

    @EJB
    private SOPSalesRepCommissionLocal commissionBean;
    @EJB
    private SOPParametersLocal paramBean;
    @EJB
    private DebtorsParametersLocal debtorsParamBean;
    @EJB
    private BaseEmployeeLocal employeeBean;

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        SOPParameters param = paramBean.getParameterRecord(userData);
        DebtorsParameters debtorsParam = debtorsParamBean.getDebtorsParameters(userData);

        EMCQuery query = (EMCQuery) queryList.get(1);

        query.addTable(InventoryItemMaster.class.getName());

        query.openAndConditionBracket();
        query.addOr("itemId", DebtorsCustomerInvoiceLines.class.getName(), EMCQueryConditions.EQUALS, "itemId", InventoryItemMaster.class.getName());
        query.addOr("itemId", DebtorsCreditNoteLines.class.getName(), EMCQueryConditions.EQUALS, "itemId", InventoryItemMaster.class.getName());
        query.closeConditionBracket();

        query.addTable(SOPCustomers.class.getName());

        query.openAndConditionBracket();
        query.addOr("customerNo", DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.EQUALS, "customerId", SOPCustomers.class.getName());
        query.addOr("customerNo", DebtorsCreditNoteMaster.class.getName(), EMCQueryConditions.EQUALS, "customerId", SOPCustomers.class.getName());
        query.closeConditionBracket();

        query.openAndConditionBracket();
        query.addOr("referenceType", DebtorsTransactionRefTypes.INVOICE.toString(), DebtorsTransactions.class.getName());
        query.addOr("referenceType", DebtorsTransactionRefTypes.CREDIT_NOTE.toString(), DebtorsTransactions.class.getName());
        query.closeConditionBracket();

        //Check Commission Flag
        //query.openAndConditionBracket();
        //query.addOr("commissionApplicable", true, DebtorsCustomerInvoiceMaster.class.getName());
        //query.addOr("commissionApplicable", true, DebtorsCreditNoteMaster.class.getName());
        //query.closeConditionBracket();

        query.addTableAnd(InventoryItemGroup.class.getName(), "itemGroup", InventoryItemMaster.class.getName(), "itemGroup");
        //query.addAnd("commissionApplicable", true, InventoryItemGroup.class.getName());
        if (!isBlank(debtorsParam.getDeliveryChargeItem())) {
            query.addAnd("itemId", debtorsParam.getDeliveryChargeItem(), InventoryItemMaster.class.getName(), EMCQueryConditions.NOT);
        }

        //Exclude Distribution
        query.addStringIsBlank("distributionNumber", DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryBracketConditions.AND);

        query.addField("salesRep", DebtorsTransactions.class.getName());//0
        if (!isBlank(param.getCustomerItemField1())) {
            query.addField(param.getCustomerItemField1(), param.getCustomerItemTable1());//1
            query.addGroupBy(param.getCustomerItemTable1(), param.getCustomerItemField1());
        } else {
            query.addCustomField("", true, "Custom_1");//1
        }
        if (!isBlank(param.getCustomerItemField2())) {
            query.addField(param.getCustomerItemField2(), param.getCustomerItemTable2());//2
            query.addGroupBy(param.getCustomerItemTable2(), param.getCustomerItemField2());
        } else {
            query.addCustomField("", true, "Custom_2");//2
        }
        if (!isBlank(param.getCustomerItemField3())) {
            query.addField(param.getCustomerItemField3(), param.getCustomerItemTable3());//3
            query.addGroupBy(param.getCustomerItemTable3(), param.getCustomerItemField3());
        } else {
            query.addCustomField("", true, "Custom_3");//3
        }
        if (!isBlank(param.getCustomerItemField4())) {
            query.addField(param.getCustomerItemField4(), param.getCustomerItemTable4());//4
            query.addGroupBy(param.getCustomerItemTable4(), param.getCustomerItemField4());
        } else {
            query.addCustomField("", true, "Custom_4");//4
        }
        if (!isBlank(param.getCustomerItemField5())) {
            query.addField(param.getCustomerItemField5(), param.getCustomerItemTable5());//5
            query.addGroupBy(param.getCustomerItemTable5(), param.getCustomerItemField5());
        } else {
            query.addCustomField("", true, "Custom_5");//5
        }
        if (!isBlank(param.getCustomerItemField6())) {
            query.addField(param.getCustomerItemField6(), param.getCustomerItemTable6());//6
            query.addGroupBy(param.getCustomerItemTable6(), param.getCustomerItemField6());
        } else {
            query.addCustomField("", true, "Custom_6");//6
        }
        query.addField("customerId", SOPCustomers.class.getName());//7
        query.addField("customerName", SOPCustomers.class.getName());//8
        query.addField("referenceNumber", DebtorsTransactions.class.getName());//9
        query.addField("salesOrderNo", DebtorsTransactions.class.getName());//10
        query.addField("customerOrderNo", SOPSalesOrderMaster.class.getName());//11
        query.addField("referenceType", DebtorsTransactions.class.getName());//12

        String invoiceLinesAlias = query.getTableAlias(DebtorsCustomerInvoiceLines.class);
        String creditLinesAlias = query.getTableAlias(DebtorsCreditNoteLines.class);

        query.addCustomField("SUM(" + invoiceLinesAlias + ".totalCost)", false);//13
        query.addCustomField("SUM(" + creditLinesAlias + ".totalCost)", false);//14

        //Check whether commission is applicable.  All of these fields should be true for commission to apply
        query.addField("commissionApplicable", DebtorsCustomerInvoiceMaster.class.getName());   //15
        query.addField("commissionApplicable", DebtorsCreditNoteMaster.class.getName());   //16
        query.addField("commissionApplicable", InventoryItemGroup.class.getName());   //17

        query.addField("itemGroup", InventoryItemGroup.class.getName());   //18

        query.addGroupBy(SOPCustomers.class.getName(), "customerId");
        query.addGroupBy(DebtorsTransactions.class.getName(), "referenceNumber");
        query.addGroupBy(DebtorsTransactions.class.getName(), "salesOrderNo");
        query.addGroupBy(DebtorsCreditNoteMaster.class.getName(), "commissionApplicable");
        query.addGroupBy(DebtorsCustomerInvoiceMaster.class.getName(), "commissionApplicable");
        query.addGroupBy(InventoryItemMaster.class.getName(), "itemGroup");

        query.addOrderBy("brandGroup", InventoryItemMaster.class.getName());
        query.addOrderBy("classificationClassGroup6", InventoryItemMaster.class.getName());
        query.addOrderBy("referenceNumber", DebtorsTransactions.class.getName());

        List<Object[]> dataList = util.executeNativeQuery(query.getNativeQuery(), userData);

        //Get user entered query conditions
        List<String> userDateSelection = query.getFieldValue(DebtorsTransactions.class.getName(), "transactionDate", EMCQueryFieldTypes.DATE, true);
        List<String> userSalesRepSelection = query.getFieldValue(DebtorsTransactions.class.getName(), "salesRep", EMCQueryFieldTypes.STRING, false);
        List<String> userCustomerSelection = query.getFieldValue(DebtorsTransactions.class.getName(), "customerId", EMCQueryFieldTypes.STRING, false);
        List<String> userReferenceSelection = query.getFieldValue(DebtorsTransactions.class.getName(), "referenceNumber", EMCQueryFieldTypes.STRING, false);

        //Fetch Distribution Invoices
        query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceMaster.class);
        query.addTableAnd(DebtorsCustomerInvoiceLines.class.getName(), "invCNNumber", DebtorsCustomerInvoiceMaster.class.getName(), "invCNNumber");
        query.addTableAnd(SOPSalesOrderMaster.class.getName(), "salesOrderNo", DebtorsCustomerInvoiceMaster.class.getName(), "salesOrderNo");
        query.addTableAnd(SOPCustomers.class.getName(), "customerNo", DebtorsCustomerInvoiceMaster.class.getName(), "customerId");
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", DebtorsCustomerInvoiceLines.class.getName(), "itemId");
        query.addTableAnd(InventoryItemGroup.class.getName(), "itemGroup", InventoryItemMaster.class.getName(), "itemGroup");
        query.addAnd("invCNType", DebtorsInvoiceCreditNoteType.DISTRIBUTION_INVOICE.toString(), DebtorsCustomerInvoiceMaster.class.getName());

        if (!userDateSelection.isEmpty()) {
            if (userDateSelection.size() == 1) {
                query.addAnd("invoiceDate", userDateSelection.get(0), DebtorsCustomerInvoiceMaster.class.getName());
            } else {
                query.addAndCommaSeperated("invoiceDate", userDateSelection.get(0) + "~" + userDateSelection.get(1), DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.EQUALS);
            }
        }

        if (!userSalesRepSelection.isEmpty()) {
            if (userSalesRepSelection.size() == 1) {
                query.addAnd("salesRep", userSalesRepSelection.get(0), DebtorsCustomerInvoiceMaster.class.getName());
            } else {
                StringBuilder repSelection = new StringBuilder(userSalesRepSelection.remove(0));
                for (String rep : userSalesRepSelection) {
                    repSelection.append(", ");
                    repSelection.append(rep);
                }
                query.addAndCommaSeperated("salesRep", repSelection.toString(), DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.EQUALS);
            }
        }

        if (!userCustomerSelection.isEmpty()) {
            if (userCustomerSelection.size() == 1) {
                query.addAnd("customerNo", userCustomerSelection.get(0), DebtorsCustomerInvoiceMaster.class.getName());
            } else {
                StringBuilder custSelection = new StringBuilder(userCustomerSelection.remove(0));
                for (String cust : userCustomerSelection) {
                    custSelection.append(", ");
                    custSelection.append(cust);
                }
                query.addAndCommaSeperated("customerNo", custSelection.toString(), DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.EQUALS);
            }
        }

        if (!userReferenceSelection.isEmpty()) {
            if (userReferenceSelection.size() == 1) {
                query.addAnd("customerNo", userReferenceSelection.get(0), DebtorsCustomerInvoiceMaster.class.getName());
            } else {
                StringBuilder refSelection = new StringBuilder(userReferenceSelection.remove(0));
                for (String ref : userReferenceSelection) {
                    refSelection.append(", ");
                    refSelection.append(ref);
                }
                query.addAndCommaSeperated("invCNNumber", refSelection.toString(), DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.EQUALS);
            }
        }

        query.addField("salesRep", DebtorsCustomerInvoiceMaster.class.getName());//0
        if (!isBlank(param.getCustomerItemField1())) {
            query.addField(param.getCustomerItemField1(), param.getCustomerItemTable1());//1
            query.addGroupBy(param.getCustomerItemTable1(), param.getCustomerItemField1());
        } else {
            query.addCustomField("", true, "Custom_1");//1
        }
        if (!isBlank(param.getCustomerItemField2())) {
            query.addField(param.getCustomerItemField2(), param.getCustomerItemTable2());//2
            query.addGroupBy(param.getCustomerItemTable2(), param.getCustomerItemField2());
        } else {
            query.addCustomField("", true, "Custom_2");//2
        }
        if (!isBlank(param.getCustomerItemField3())) {
            query.addField(param.getCustomerItemField3(), param.getCustomerItemTable3());//3
            query.addGroupBy(param.getCustomerItemTable3(), param.getCustomerItemField3());
        } else {
            query.addCustomField("", true, "Custom_3");//3
        }
        if (!isBlank(param.getCustomerItemField4())) {
            query.addField(param.getCustomerItemField4(), param.getCustomerItemTable4());//4
            query.addGroupBy(param.getCustomerItemTable4(), param.getCustomerItemField4());
        } else {
            query.addCustomField("", true, "Custom_4");//4
        }
        if (!isBlank(param.getCustomerItemField5())) {
            query.addField(param.getCustomerItemField5(), param.getCustomerItemTable5());//5
            query.addGroupBy(param.getCustomerItemTable5(), param.getCustomerItemField5());
        } else {
            query.addCustomField("", true, "Custom_5");//5
        }
        if (!isBlank(param.getCustomerItemField6())) {
            query.addField(param.getCustomerItemField6(), param.getCustomerItemTable6());//6
            query.addGroupBy(param.getCustomerItemTable6(), param.getCustomerItemField6());
        } else {
            query.addCustomField("", true, "Custom_6");//6
        }
        query.addField("customerId", SOPCustomers.class.getName());//7
        query.addField("customerName", SOPCustomers.class.getName());//8
        query.addField("invCNNumber", DebtorsCustomerInvoiceMaster.class.getName());//9
        query.addField("salesOrderNo", SOPSalesOrderMaster.class.getName());//10
        query.addField("customerOrderNo", SOPSalesOrderMaster.class.getName());//11
        query.addCustomField(DebtorsTransactionRefTypes.INVOICE.toString(), true);//12

        invoiceLinesAlias = query.getTableAlias(DebtorsCustomerInvoiceLines.class);

        query.addCustomField("SUM(" + invoiceLinesAlias + ".totalCost)", false);//13
        query.addCustomField("0", false);//14

        query.addField("commissionApplicable", DebtorsCustomerInvoiceMaster.class.getName());   //15
        query.addCustomField("false", false);   //16
        query.addField("commissionApplicable", InventoryItemGroup.class.getName());   //17
        query.addField("itemGroup", InventoryItemGroup.class.getName());   //18

        query.addGroupBy(SOPCustomers.class.getName(), "customerId");
        query.addGroupBy(DebtorsCustomerInvoiceMaster.class.getName(), "invCNNumber");
        query.addGroupBy(SOPSalesOrderMaster.class.getName(), "salesOrderNo");
        query.addGroupBy(DebtorsCustomerInvoiceMaster.class.getName(), "commissionApplicable");
        query.addGroupBy(InventoryItemMaster.class.getName(), "itemGroup");

        query.addOrderBy("salesRep", DebtorsCustomerInvoiceMaster.class.getName());
        query.addOrderBy("customerId", SOPCustomers.class.getName());
        query.addOrderBy("brandGroup", InventoryItemMaster.class.getName());
        query.addOrderBy("classificationClassGroup6", InventoryItemMaster.class.getName());
        query.addOrderBy("invCNNumber", DebtorsCustomerInvoiceMaster.class.getName());

        List<Object[]> distributionList = util.executeNativeQuery(query.getNativeQuery(), userData);

        if (!distributionList.isEmpty()) {
            int comparisonFieldCount = 6;
            int dataListIndex = 0;
            Object[] dataListData;
            String dataComparison;
            Object[] distributionListData;
            String distributionComparison;

DistLoop:   while (!distributionList.isEmpty()) {
                distributionListData = distributionList.remove(0);

DataLoop:       while (dataListIndex < dataList.size()) {
                    dataListData = dataList.get(dataListIndex);

                    for (int i = 0; i < comparisonFieldCount + 1; i++) {
                        dataComparison = (String) dataListData[i];
                        if (dataComparison == null) {
                            dataComparison = "";
                        }
                        distributionComparison = (String) distributionListData[i];
                        if (distributionComparison == null) {
                            distributionComparison = "";
                        }

                        if (dataComparison.compareToIgnoreCase(distributionComparison) > 0) {
                            //Data Greater - Add Before
                            dataList.add(dataListIndex, distributionListData);
                            distributionListData = null;
                            continue DistLoop;
                        } else if (dataComparison.compareToIgnoreCase(distributionComparison) < 0) {
                            dataListIndex++;
                            continue DataLoop;
                        }
                    }
                    //All Compared fields are equal - Keep Looking
                    dataListIndex++;
                }

                if (distributionListData != null) {
                    dataList.add(distributionListData);
                }
            }

            if (!distributionList.isEmpty()) {
                dataList.addAll(distributionList);
                distributionList.clear();
            }
        }

        //UD Pos 0 indicates report type
        switch ((Integer) userData.getUserData(0)) {
            case 0:
                return populateDetailedReport(dataList, userData);
            case 1:
                return populateSummaryReport(dataList, userData);
            case 2:
                return populateReport(dataList, userData);
            default:
                return null;
        }
    }

    private List populateDetailedReport(List<Object[]> dataList, EMCUserData userData) {
        Map<String, BigDecimal> commissionMap = null;
        BigDecimal commission;
        String key;
        String previousRep = null;
        String repName = null;

        SOPCommissionReportDS ds;
        List reportData = new ArrayList();

        for (Object[] data : dataList) {
            if (previousRep == null || !previousRep.equals((String) data[0])) {
                commissionMap = new HashMap<String, BigDecimal>();
                repName = employeeBean.findEmployeeNameAndSurname((String) data[0], userData);
            }
            previousRep = isBlank(data[0]) ? "" : (String) data[0];
            key = (String) data[1] + (String) data[2] + (String) data[3] + (String) data[4] + (String) data[5] + (String) data[6];
            commission = commissionMap.get(key);
            if (commission == null) {
                commission = commissionBean.findsalesRepCommission((String) data[0], (String) data[1], (String) data[2], (String) data[3], (String) data[4], (String) data[5], (String) data[6], userData);
                commissionMap.put(key, commission);
            }

            //SQL query returns incorrect results at times - not sure why.  Hack to fix report (urgent).
            ensureCommissionFlagSet(data, userData);
            //Allow for nulls in boolean fields.  Either credit note or invoice should have commission set.  This check
            //mirrors the conditions previously included in the report's SQL query.
            boolean commissionApplicable = (data[15] == Boolean.TRUE || data[16] == Boolean.TRUE) && data[17] == Boolean.TRUE;

            ds = new SOPCommissionReportDS();
            ds.setSalesRep((String) data[0]);
            ds.setSalesRepName(repName);
            ds.setCustomerItemHeader1("B");
            ds.setCustomerItemHeader2("G");
            ds.setCustomerId((String) data[7]);
            ds.setCustomerName((String) data[8]);
            ds.setCustomerItemField1((String) data[2]);
            ds.setCustomerItemField2((String) data[3]);
            ds.setInvoiceNo((String) data[9]);
            ds.setSalesOrderNo((String) data[10]);
            ds.setCustomerOrderNo((String) data[11]);
            if (commissionApplicable) {
                ds.setCommissionPerc(commission);
            }


            switch (DebtorsTransactionRefTypes.fromString((String) data[12])) {
                case CREDIT_NOTE:
                    if (data[14] != null) {
                        ds.setValue(util.getBigDecimal(ds.getValue().add(util.getBigDecimal((((BigDecimal) data[14])).doubleValue())).doubleValue()));
                        if (commissionApplicable) {
                            ds.setCommissionValue(util.getBigDecimal(ds.getCommissionValue().add(util.getBigDecimal((commission.compareTo(BigDecimal.ZERO) > 0 ? util.getBigDecimal(((BigDecimal) data[14]).doubleValue()).multiply(commission.divide(new BigDecimal(100))) : BigDecimal.ZERO).doubleValue())).doubleValue()));
                        }
                    }
                    break;
                case INVOICE:
                    ds.setValue(util.getBigDecimal(ds.getValue().add(util.getBigDecimal((((BigDecimal) data[13])).doubleValue())).doubleValue()));
                    if (commissionApplicable) {
                        ds.setCommissionValue(util.getBigDecimal(ds.getCommissionValue().add(util.getBigDecimal((commission.compareTo(BigDecimal.ZERO) > 0 ? util.getBigDecimal(((BigDecimal) data[13]).doubleValue()).multiply(commission.divide(new BigDecimal(100))) : BigDecimal.ZERO).doubleValue())).doubleValue()));
                    }
                    break;
            }
            reportData.add(ds);
        }
        return reportData;
    }

    private List populateSummaryReport(List<Object[]> dataList, EMCUserData userData) {
        Map<String, BigDecimal> commissionMap = null;
        BigDecimal commission;
        String key;
        String previousRep = null;
        String repName = null;

        List reportData = new ArrayList();
        SOPCommissionReportDS ds = null;

        for (Object[] data : dataList) {
            if (previousRep == null || !previousRep.equals((String) data[0])) {
                if (ds != null) {
                    reportData.add(ds);
                }
                repName = employeeBean.findEmployeeNameAndSurname((String) data[0], userData);


                ds = new SOPCommissionReportDS();
                ds.setSalesRep((String) data[0]);
                ds.setSalesRepName(repName);

                commissionMap = new HashMap<String, BigDecimal>();
            }

            previousRep = isBlank(data[0]) ? "" : (String) data[0];

            //SQL query returns incorrect results at times - not sure why.  Hack to fix report (urgent).
            ensureCommissionFlagSet(data, userData);
            //Allow for nulls in boolean fields.  Either credit note or invoice should have commission set.  This check
            //mirrors the conditions previously included in the report's SQL query.
            boolean commissionApplicable = (data[15] == Boolean.TRUE || data[16] == Boolean.TRUE) && data[17] == Boolean.TRUE;

            key = (String) data[1] + (String) data[2] + (String) data[3] + (String) data[4] + (String) data[5] + (String) data[6];
            commission = commissionMap.get(key);

            if (commission == null) {
                commission = commissionBean.findsalesRepCommission((String) data[0], (String) data[1], (String) data[2], (String) data[3], (String) data[4], (String) data[5], (String) data[6], userData);
                commissionMap.put(key, commission);
            }

            switch (DebtorsTransactionRefTypes.fromString((String) data[12])) {
                case CREDIT_NOTE:
                    if (data[14] != null) {
                        ds.setValue(util.getBigDecimal(ds.getValue().add(util.getBigDecimal((((BigDecimal) data[14])).doubleValue())).doubleValue()));
                        if (commissionApplicable) {
                            ds.setCommissionValue(util.getBigDecimal(ds.getCommissionValue().add(util.getBigDecimal((commission.compareTo(BigDecimal.ZERO) > 0 ? util.getBigDecimal(((BigDecimal) data[14]).doubleValue()).multiply(commission.divide(new BigDecimal(100))) : BigDecimal.ZERO).doubleValue())).doubleValue()));
                        }
                    }
                    break;
                case INVOICE:
                    ds.setValue(util.getBigDecimal(ds.getValue().add(util.getBigDecimal((((BigDecimal) data[13])).doubleValue())).doubleValue()));
                    if (commissionApplicable) {
                        ds.setCommissionValue(util.getBigDecimal(ds.getCommissionValue().add(util.getBigDecimal((commission.compareTo(BigDecimal.ZERO) > 0 ? util.getBigDecimal(((BigDecimal) data[13]).doubleValue()).multiply(commission.divide(new BigDecimal(100))) : BigDecimal.ZERO).doubleValue())).doubleValue()));
                    }
                    break;
            }
        }
        if (ds != null) {
            reportData.add(ds);
        }
        return reportData;
    }

    /**
     * Ensures that the commission applicable flags in the specified data have
     * valid values.
     *
     * @param rowData Row to check.
     * @param userData User data.
     */
    private void ensureCommissionFlagSet(Object[] data, EMCUserData userData) {
        //Hack - for some reason the query sometimes selects null values for
        //the commission applicable flags.  The results are correct when the
        //query is executed in SQL directly, but incorrect when the query is
        //executed via Hibernate.
        if (data[15] == null && data[16] == null) {
            DebtorsTransactionRefTypes refType = DebtorsTransactionRefTypes.fromString((String) data[12]);
            if (refType == DebtorsTransactionRefTypes.INVOICE) {
                //Check actual invoice record.
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceMaster.class);
                query.addAnd("invCNNumber", data[9]);
                query.addField("commissionApplicable");

                data[15] = util.executeSingleResultQuery(query, userData);
            } else if (refType == DebtorsTransactionRefTypes.CREDIT_NOTE) {
                //Check actual credit note record.
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteMaster.class);
                query.addAnd("invCNNumber", data[9]);
                query.addField("commissionApplicable");

                data[16] = util.executeSingleResultQuery(query, userData);
            }
        }

        if (data[17] == null) {
            //Fetch commission flag from item group as well.
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemGroup.class);
            query.addAnd("itemGroup", data[18]);
            query.addField("commissionApplicable");

            data[17] = util.executeSingleResultQuery(query, userData);
        }
    }

    private List populateReport(List<Object[]> dataList, EMCUserData userData) {
        Map<String, BigDecimal> commissionMap = null;
        BigDecimal commission;
        String key;
        String previousRep = null;

        SOPCommissionReportDS ds;
        Map<String, SOPCommissionReportDS> reportData = new TreeMap<String, SOPCommissionReportDS>();

        for (Object[] data : dataList) {
            if (previousRep == null || !previousRep.equals((String) data[0])) {
                commissionMap = new HashMap<String, BigDecimal>();
            }
            previousRep = isBlank(data[0]) ? "" : (String) data[0];
            key = (String) data[1] + (String) data[2] + (String) data[3] + (String) data[4] + (String) data[5] + (String) data[6];
            commission = commissionMap.get(key);
            if (commission == null) {
                commission = commissionBean.findsalesRepCommission((String) data[0], (String) data[1], (String) data[2], (String) data[3], (String) data[4], (String) data[5], (String) data[6], userData);
                commissionMap.put(key, commission);
            }
            ds = reportData.get((String) data[2] + (String) data[3]);
            if (ds == null) {
                ds = new SOPCommissionReportDS();
            }
            ds.setCustomerItemHeader1("B");
            ds.setCustomerItemHeader2("G");
            ds.setCustomerItemField1((String) data[2]);
            ds.setCustomerItemField2((String) data[3]);

            //SQL query returns incorrect results at times - not sure why.  Hack to fix report (urgent).
            ensureCommissionFlagSet(data, userData);
            //Allow for nulls in boolean fields.  Either credit note or invoice should have commission set.  This check
            //mirrors the conditions previously included in the report's SQL query.
            boolean commissionApplicable = (data[15] == Boolean.TRUE || data[16] == Boolean.TRUE) && data[17] == Boolean.TRUE;

            switch (DebtorsTransactionRefTypes.fromString((String) data[12])) {
                case CREDIT_NOTE:
                    if (data[14] != null) {
                        ds.setValue(util.getBigDecimal(ds.getValue().add(util.getBigDecimal((((BigDecimal) data[14])).doubleValue())).doubleValue()));
                        if (commissionApplicable) {
                            ds.setCommissionValue(util.getBigDecimal(ds.getCommissionValue().add(util.getBigDecimal((commission.compareTo(BigDecimal.ZERO) > 0 ? util.getBigDecimal(((BigDecimal) data[14]).doubleValue()).multiply(commission.divide(new BigDecimal(100))) : BigDecimal.ZERO).doubleValue())).doubleValue()));
                        }
                    }
                    break;
                case INVOICE:
                    ds.setValue(util.getBigDecimal(ds.getValue().add(util.getBigDecimal((((BigDecimal) data[13])).doubleValue())).doubleValue()));
                    if (commissionApplicable) {
                        ds.setCommissionValue(util.getBigDecimal(ds.getCommissionValue().add(util.getBigDecimal((commission.compareTo(BigDecimal.ZERO) > 0 ? util.getBigDecimal(((BigDecimal) data[13]).doubleValue()).multiply(commission.divide(new BigDecimal(100))) : BigDecimal.ZERO).doubleValue())).doubleValue()));
                    }
                    break;
            }
            reportData.put((String) data[2] + (String) data[3], ds);
        }
        return new ArrayList(reportData.values());
    }
}

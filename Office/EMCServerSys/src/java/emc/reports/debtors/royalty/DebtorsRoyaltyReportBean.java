/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.debtors.royalty;

import emc.bus.debtors.royaltysetup.DebtorsRoyaltySetupLocal;
import emc.datatypes.EMCDataType;
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsRoyaltyGroups;
import emc.entity.debtors.DebtorsRoyaltySetup;
import emc.entity.debtors.royalty.DebtorsRoyaltyReportTempDS;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.sop.SOPCustomers;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.helpers.debtors.royalty.RoyaltyInterface;
import emc.reporttools.EMCReportConfig;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class DebtorsRoyaltyReportBean extends EMCReportBean implements DebtorsRoyaltyReportLocal {

    @EJB
    private DebtorsRoyaltySetupLocal royaltySetupBean;

    /** Creates a new instance of DebtorsRoyaltyReportBean. */
    public DebtorsRoyaltyReportBean() {
    }

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        EMCQuery query = (EMCQuery) queryList.get(1);

        Object[] royaltyFields = royaltySetupBean.getRoyaltyFields(userData);

        query.addAnd("status", DebtorsInvoiceStatus.POSTED.toString(), DebtorsCustomerInvoiceMaster.class.getName());
        query.addTable(DebtorsRoyaltySetup.class.getName());
        query.addTableAnd(DebtorsRoyaltyGroups.class.getName(), "royaltyGroup", DebtorsRoyaltySetup.class.getName(), "royaltyGroupId");

        //Group by royalty fields and select sum of units and sum of gross amount.
        if (royaltyFields[0] != null) {
            String field = String.valueOf(royaltyFields[0]);
            query.addField(field, InventoryItemMaster.class.getName());
            query.addGroupBy(InventoryItemMaster.class.getName(), field);
            //Select only where royalties have been set up.
            query.addAnd("field1Value", DebtorsRoyaltySetup.class.getName(), EMCQueryConditions.EQUALS, field, InventoryItemMaster.class.getName());
        } else {
            //If no royalty fields have been set up, return nothing.
            return new ArrayList();
        }

        query.addAnd("classificationClassGroup6", "[0-9]+", InventoryItemMaster.class.getName(), EMCQueryConditions.REGEX);

        if (royaltyFields[1] != null) {
            String field = String.valueOf(royaltyFields[1]);
            query.addField(field, InventoryItemMaster.class.getName());
            query.addGroupBy(InventoryItemMaster.class.getName(), field);
            //Select only where royalties have been set up.
            query.addAnd("field2Value", DebtorsRoyaltySetup.class.getName(), EMCQueryConditions.EQUALS, field, InventoryItemMaster.class.getName());
        }

        if (royaltyFields[2] != null) {
            String field = String.valueOf(royaltyFields[2]);
            query.addField(field, InventoryItemMaster.class.getName());
            //Select only where royalties have been set up.
            query.addAnd("field3Value", DebtorsRoyaltySetup.class.getName(), EMCQueryConditions.EQUALS, field, InventoryItemMaster.class.getName());

            //Store query in userData.  Used to fetch totals for Jockey Shops.  Do not group by royalty field 3 - for N & L only fields 1 & 2 (brand & gender) are relevant.
            userData.setUserData(6, query.copyQuery());

            query.addGroupBy(InventoryItemMaster.class.getName(), field);
        }

        StringBuilder grossValueSB = new StringBuilder(query.getTableAlias(DebtorsCustomerInvoiceLines.class.getName()));
        grossValueSB.append(".quantity * ");
        grossValueSB.append(query.getTableAlias(DebtorsCustomerInvoiceLines.class));
        grossValueSB.append(".unitPrice");

        query.addCustomField("SUM(" + grossValueSB + ")", false);
        //MySQL specific.  N & L use classification 6 for pack size.
        query.addCustomField("SUM(" + query.getTableAlias(DebtorsCustomerInvoiceLines.class) + ".quantity * CAST(" + query.getTableAlias(InventoryItemMaster.class) + ".classificationClassGroup6 AS SIGNED))", false);

        //Royalty percentage
        query.addField("royaltyPercentage", DebtorsRoyaltyGroups.class.getName());
        //Royalty group
        query.addField("royaltyGroup", DebtorsRoyaltySetup.class.getName());

        //Calculate royalty in query
        StringBuilder royaltySB = new StringBuilder("SUM(");
        royaltySB.append(grossValueSB);
        royaltySB.append(" * ");
        royaltySB.append(query.getTableAlias(DebtorsRoyaltyGroups.class));
        royaltySB.append(".royaltyPercentage/100)");

        query.addCustomField(royaltySB.toString(), false);

        //Get number of Packs from Invoice lines
        query.addFieldAggregateFunction("quantity", DebtorsCustomerInvoiceLines.class.getName(), "SUM");

        userData.setUserData(5, reportConfig.getParameters().get("exclude_marketing_group"));

        //Store full query in userData - used to select Credit Notes.
        userData.setUserData(7, query);

        return super.getReportResult(queryList, reportConfig, userData);
    }

    @Override
    public List<Object> executeQuery(EMCQuery query, EMCUserData userData) {
        //Need to execute native query.  REGEXP falls over in HQL.
        return util.executeNativeQuery(query, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        long sessionId = System.currentTimeMillis();

        Object[] royaltyFields = royaltySetupBean.getRoyaltyFields(userData);

        List<DebtorsRoyaltyReportTempDS> tempDSList = new ArrayList<DebtorsRoyaltyReportTempDS>();

        //This is used to check for foreign keys and fetch descriptions (where applicable)
        InventoryItemMaster itemMasterInstance = new InventoryItemMaster();

        //N & L specific.  Used to calculate totals for Jockey retail shops.
        StringBuilder lastJockeyTotalString = null;
        BigDecimal jockeyShopGrossValue = BigDecimal.ZERO;
        BigDecimal jockeyShopUnits = BigDecimal.ZERO;
        BigDecimal jockeyShopRoyaltyValue = BigDecimal.ZERO;
        BigDecimal jockeyShopPacks = BigDecimal.ZERO;

        String royaltyField1Value = null;
        String royaltyField2Value = null;

        String jockeyShopMarketingGroup = (String) userData.getUserData(5);
        boolean printLessJockeyShops = !isBlank(jockeyShopMarketingGroup);

        for (Object result : queryResult) {
            Object[] resultArr = (Object[]) result;
            //Because of royalty setup, the indexes at which values are stored are not constant.
            int currentIndex = 0;

            DebtorsRoyaltyReportTempDS ds = new DebtorsRoyaltyReportTempDS();
            ds.setSessionId(sessionId);

            if (royaltyFields[0] != null) {
                String fieldValue = String.valueOf(resultArr[currentIndex++]);
                royaltyField1Value = fieldValue;
                EMCDataType dt = itemMasterInstance.getDataType(String.valueOf(royaltyFields[0]), userData);
                if (dt != null && dt.getRelatedTable() != null) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, dt.getRelatedTable());
                    query.addAnd(dt.getRelatedField(), fieldValue);

                    EMCEntityClass relatedEntity = (EMCEntityClass) util.executeSingleResultQuery(query, userData);

                    if (relatedEntity != null && relatedEntity instanceof RoyaltyInterface) {
                        ds.setRoyaltyField1(((RoyaltyInterface) relatedEntity).getFieldToDisplay());
                    } else {
                        ds.setRoyaltyField1(fieldValue);
                    }
                } else {
                    ds.setRoyaltyField1(fieldValue);
                }
            }

            if (royaltyFields[1] != null) {
                String fieldValue = String.valueOf(resultArr[currentIndex++]);
                royaltyField2Value = fieldValue;
                EMCDataType dt = itemMasterInstance.getDataType(String.valueOf(royaltyFields[1]), userData);
                if (dt != null && dt.getRelatedTable() != null) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, dt.getRelatedTable());
                    query.addAnd(dt.getRelatedField(), fieldValue);

                    EMCEntityClass relatedEntity = (EMCEntityClass) util.executeSingleResultQuery(query, userData);

                    if (relatedEntity != null && relatedEntity instanceof RoyaltyInterface) {
                        ds.setRoyaltyField2(((RoyaltyInterface) relatedEntity).getFieldToDisplay());
                    } else {
                        ds.setRoyaltyField2(fieldValue);
                    }
                } else {
                    ds.setRoyaltyField2(fieldValue);
                }
            }

            StringBuilder currentJockeyTotalString = new StringBuilder();
            currentJockeyTotalString.append(ds.getRoyaltyField1());
            currentJockeyTotalString.append(ds.getRoyaltyField2());

            //If group changes, fetch new totals for Jockey shop.
            if (printLessJockeyShops && (lastJockeyTotalString == null || !lastJockeyTotalString.toString().equals(currentJockeyTotalString.toString()))) {
                lastJockeyTotalString = currentJockeyTotalString;
                //For calculations on Jasper Report, totals should only be set on last line in list for previous group.
                if (!tempDSList.isEmpty()) {
                    DebtorsRoyaltyReportTempDS tempDS = (DebtorsRoyaltyReportTempDS) tempDSList.get(tempDSList.size() - 1);

                    tempDS.setJockeyShopGrossValue(jockeyShopGrossValue);
                    tempDS.setJockeyShopUnits(jockeyShopUnits);
                    tempDS.setJockeyShopRoyaltyValue(jockeyShopRoyaltyValue);
                    tempDS.setJockeyShopPacks(jockeyShopPacks);
                }

                EMCQuery jockeyShopQuery = ((EMCQuery) userData.getUserData(6)).copyQuery();
                jockeyShopQuery.addTableAnd(SOPCustomers.class.getName(), "customerNo", DebtorsCustomerInvoiceMaster.class.getName(), "customerId");
                jockeyShopQuery.addAnd("field1Value", royaltyField1Value, DebtorsRoyaltySetup.class.getName());
                jockeyShopQuery.addAnd("field2Value", royaltyField2Value, DebtorsRoyaltySetup.class.getName());
                jockeyShopQuery.addAnd("marketingGroup", jockeyShopMarketingGroup, SOPCustomers.class.getName());

                StringBuilder grossValueSB = new StringBuilder(jockeyShopQuery.getTableAlias(DebtorsCustomerInvoiceLines.class.getName()));
                grossValueSB.append(".quantity * ");
                grossValueSB.append(jockeyShopQuery.getTableAlias(DebtorsCustomerInvoiceLines.class));
                grossValueSB.append(".unitPrice");

                jockeyShopQuery.addCustomField("SUM(" + grossValueSB + ")", false);

                //MySQL specific.  N & L use classification 6 for pack size.
                jockeyShopQuery.addCustomField("SUM(" + jockeyShopQuery.getTableAlias(DebtorsCustomerInvoiceLines.class) + ".quantity * CAST(" + jockeyShopQuery.getTableAlias(InventoryItemMaster.class) + ".classificationClassGroup6 AS SIGNED))", false);

                //Calculate royalty in query
                StringBuilder royaltySB = new StringBuilder("SUM(");
                royaltySB.append(grossValueSB);
                royaltySB.append(" * ");
                royaltySB.append(jockeyShopQuery.getTableAlias(DebtorsRoyaltyGroups.class));
                royaltySB.append(".royaltyPercentage/100)");

                jockeyShopQuery.addCustomField(royaltySB.toString(), false);
                jockeyShopQuery.addFieldAggregateFunction("quantity", DebtorsCustomerInvoiceLines.class.getName(), "SUM");

                List<Object[]> jockeyShopTotalsList = (List<Object[]>) util.executeNativeQuery(jockeyShopQuery, userData);

                //There should only be one element in the list.
                if (jockeyShopTotalsList != null && jockeyShopTotalsList.size() == 1) {
                    Object[] jockeyShopTotals = jockeyShopTotalsList.get(0);
                    int jockeyShopCurIndex = 0;

                    if (!isBlank(royaltyFields[0])) {
                        jockeyShopCurIndex++;
                    }

                    if (!isBlank(royaltyFields[1])) {
                        jockeyShopCurIndex++;
                    }

                    if (!isBlank(royaltyFields[2])) {
                        jockeyShopCurIndex++;
                    }

                    jockeyShopGrossValue = util.roundBigDecimal((BigDecimal) jockeyShopTotals[jockeyShopCurIndex++]);
                    jockeyShopUnits = util.roundBigDecimal((BigDecimal) jockeyShopTotals[jockeyShopCurIndex++]);
                    jockeyShopRoyaltyValue = util.roundBigDecimal((BigDecimal) jockeyShopTotals[jockeyShopCurIndex++]);
                    jockeyShopPacks = util.roundBigDecimal((BigDecimal) jockeyShopTotals[jockeyShopCurIndex++]);
                } else {
                    //No values for Jockey Shops
                    jockeyShopGrossValue = BigDecimal.ZERO;
                    jockeyShopUnits = BigDecimal.ZERO;
                    jockeyShopRoyaltyValue = BigDecimal.ZERO;
                    jockeyShopPacks = BigDecimal.ZERO;
                }
            }

            if (royaltyFields[2] != null) {
                String fieldValue = String.valueOf(resultArr[currentIndex++]);
                EMCDataType dt = itemMasterInstance.getDataType(String.valueOf(royaltyFields[2]), userData);
                if (dt != null && dt.getRelatedTable() != null) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, dt.getRelatedTable());
                    query.addAnd(dt.getRelatedField(), fieldValue);

                    EMCEntityClass relatedEntity = (EMCEntityClass) util.executeSingleResultQuery(query, userData);

                    if (relatedEntity != null && relatedEntity instanceof RoyaltyInterface) {
                        ds.setRoyaltyField3(((RoyaltyInterface) relatedEntity).getFieldToDisplay());
                    } else {
                        ds.setRoyaltyField3(fieldValue);
                    }
                } else {
                    ds.setRoyaltyField3(fieldValue);
                }
            }

            ds.setGrossValue(util.roundBigDecimal((BigDecimal) resultArr[currentIndex++]));
            ds.setUnits(util.roundBigDecimal((BigDecimal) resultArr[currentIndex++]));

            ds.setRoyaltyPercentage(util.roundBigDecimal((BigDecimal) resultArr[currentIndex++]));

            ds.setRoyaltyGroup((String) resultArr[currentIndex++]);

            ds.setRoyaltyValue(util.roundBigDecimal((BigDecimal) resultArr[currentIndex++]));
            ds.setPacks(util.roundBigDecimal((BigDecimal) resultArr[currentIndex++]));

            //Used for grouping - should always be set.
            ds.setPrintLessJockeyShops(printLessJockeyShops);

            tempDSList.add(ds);
        }

        //Also set totals for last line.
        if (!tempDSList.isEmpty()) {
            DebtorsRoyaltyReportTempDS tempDS = (DebtorsRoyaltyReportTempDS) tempDSList.get(tempDSList.size() - 1);

            tempDS.setJockeyShopGrossValue(jockeyShopGrossValue);
            tempDS.setJockeyShopUnits(jockeyShopUnits);
            tempDS.setJockeyShopRoyaltyValue(jockeyShopRoyaltyValue);
            tempDS.setJockeyShopPacks(jockeyShopPacks);
        }

        //Also get Credit Notes
        tempDSList.addAll(getCreditNotes((EMCQuery) userData.getUserData(7), royaltyFields, sessionId, userData));

        util.insertDirect(DebtorsRoyaltyReportTempDS.class, tempDSList, userData);

        List<Object> reportData = getReportData(sessionId, userData);

        EMCQuery query = new EMCQuery(enumQueryTypes.DELETE, DebtorsRoyaltyReportTempDS.class);
        query.addAnd("sessionId", sessionId);

        util.executeUpdate(query, userData);

        return reportData;
    }

    /**
     * Returns Credit Notes using the same selection criteria as for Invoices in the specified query.
     * @param invoiceQuery Invoice query.
     * @param royaltyFields Royalty fields.
     * @param sessionId Session ID for report data.
     * @param userData User data.
     * @return A list of DebtorsRoyaltyReportTempDS representing Credit Notes.
     */
    private List<DebtorsRoyaltyReportTempDS> getCreditNotes(EMCQuery invoiceQuery, Object[] royaltyFields, long sessionId, EMCUserData userData) {
        List<DebtorsRoyaltyReportTempDS> tempDSList = new ArrayList<DebtorsRoyaltyReportTempDS>();

        //This is used to check for foreign keys and fetch descriptions (where applicable)
        InventoryItemMaster itemMasterInstance = new InventoryItemMaster();

        //N & L specific.  Used to calculate totals for Jockey retail shops.
        StringBuilder lastJockeyTotalString = null;
        BigDecimal jockeyShopGrossValue = BigDecimal.ZERO;
        BigDecimal jockeyShopUnits = BigDecimal.ZERO;
        BigDecimal jockeyShopRoyaltyValue = BigDecimal.ZERO;
        BigDecimal jockeyShopPacks = BigDecimal.ZERO;

        String royaltyField1Value = null;
        String royaltyField2Value = null;

        String jockeyShopMarketingGroup = (String) userData.getUserData(5);
        boolean printLessJockeyShops = !isBlank(jockeyShopMarketingGroup);

        //Replace invoice tables with Credit Note tables in query
        String queryString = invoiceQuery.getNativeQuery().replaceAll(DebtorsCustomerInvoiceMaster.class.getSimpleName(), DebtorsCreditNoteMaster.class.getSimpleName());
        queryString = queryString.replaceAll(DebtorsCustomerInvoiceLines.class.getSimpleName(), DebtorsCreditNoteLines.class.getSimpleName());
        List<Object[]> queryResult = util.executeNativeQuery(queryString, userData);

        for (Object result : queryResult) {
            Object[] resultArr = (Object[]) result;
            //Because of royalty setup, the indexes at which values are stored are not constant.
            int currentIndex = 0;

            DebtorsRoyaltyReportTempDS ds = new DebtorsRoyaltyReportTempDS();
            ds.setSessionId(sessionId);

            if (royaltyFields[0] != null) {
                String fieldValue = String.valueOf(resultArr[currentIndex++]);
                royaltyField1Value = fieldValue;
                EMCDataType dt = itemMasterInstance.getDataType(String.valueOf(royaltyFields[0]), userData);
                if (dt != null && dt.getRelatedTable() != null) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, dt.getRelatedTable());
                    query.addAnd(dt.getRelatedField(), fieldValue);

                    EMCEntityClass relatedEntity = (EMCEntityClass) util.executeSingleResultQuery(query, userData);

                    if (relatedEntity != null && relatedEntity instanceof RoyaltyInterface) {
                        ds.setRoyaltyField1(((RoyaltyInterface) relatedEntity).getFieldToDisplay());
                    } else {
                        ds.setRoyaltyField1(fieldValue);
                    }
                } else {
                    ds.setRoyaltyField1(fieldValue);
                }
            }

            if (royaltyFields[1] != null) {
                String fieldValue = String.valueOf(resultArr[currentIndex++]);
                royaltyField2Value = fieldValue;
                EMCDataType dt = itemMasterInstance.getDataType(String.valueOf(royaltyFields[1]), userData);
                if (dt != null && dt.getRelatedTable() != null) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, dt.getRelatedTable());
                    query.addAnd(dt.getRelatedField(), fieldValue);

                    EMCEntityClass relatedEntity = (EMCEntityClass) util.executeSingleResultQuery(query, userData);

                    if (relatedEntity != null && relatedEntity instanceof RoyaltyInterface) {
                        ds.setRoyaltyField2(((RoyaltyInterface) relatedEntity).getFieldToDisplay());
                    } else {
                        ds.setRoyaltyField2(fieldValue);
                    }
                } else {
                    ds.setRoyaltyField2(fieldValue);
                }
            }

            StringBuilder currentJockeyTotalString = new StringBuilder();
            currentJockeyTotalString.append(ds.getRoyaltyField1());
            currentJockeyTotalString.append(ds.getRoyaltyField2());

            //If group changes, fetch new totals for Jockey shop.
            if (printLessJockeyShops && (lastJockeyTotalString == null || !lastJockeyTotalString.toString().equals(currentJockeyTotalString.toString()))) {
                lastJockeyTotalString = currentJockeyTotalString;
                //For calculations on Jasper Report, totals should only be set on last line in list for previous group.
                if (!tempDSList.isEmpty()) {
                    DebtorsRoyaltyReportTempDS tempDS = (DebtorsRoyaltyReportTempDS) tempDSList.get(tempDSList.size() - 1);

                    tempDS.setJockeyShopGrossValue(jockeyShopGrossValue);
                    tempDS.setJockeyShopUnits(jockeyShopUnits);
                    tempDS.setJockeyShopRoyaltyValue(jockeyShopRoyaltyValue);
                    tempDS.setJockeyShopPacks(jockeyShopPacks);
                }

                EMCQuery jockeyShopQuery = ((EMCQuery) userData.getUserData(6)).copyQuery();
                jockeyShopQuery.addTableAnd(SOPCustomers.class.getName(), "customerNo", DebtorsCustomerInvoiceMaster.class.getName(), "customerId");
                jockeyShopQuery.addAnd("field1Value", royaltyField1Value, DebtorsRoyaltySetup.class.getName());
                jockeyShopQuery.addAnd("field2Value", royaltyField2Value, DebtorsRoyaltySetup.class.getName());
                jockeyShopQuery.addAnd("marketingGroup", jockeyShopMarketingGroup, SOPCustomers.class.getName());

                StringBuilder grossValueSB = new StringBuilder(jockeyShopQuery.getTableAlias(DebtorsCustomerInvoiceLines.class.getName()));
                grossValueSB.append(".quantity * ");
                grossValueSB.append(jockeyShopQuery.getTableAlias(DebtorsCustomerInvoiceLines.class));
                grossValueSB.append(".unitPrice");

                jockeyShopQuery.addCustomField("SUM(" + grossValueSB + ")", false);

                //MySQL specific.  N & L use classification 6 for pack size.
                jockeyShopQuery.addCustomField("SUM(" + jockeyShopQuery.getTableAlias(DebtorsCustomerInvoiceLines.class) + ".quantity * CAST(" + jockeyShopQuery.getTableAlias(InventoryItemMaster.class) + ".classificationClassGroup6 AS SIGNED))", false);

                //Calculate royalty in query
                StringBuilder royaltySB = new StringBuilder("SUM(");
                royaltySB.append(grossValueSB);
                royaltySB.append(" * ");
                royaltySB.append(jockeyShopQuery.getTableAlias(DebtorsRoyaltyGroups.class));
                royaltySB.append(".royaltyPercentage/100)");

                jockeyShopQuery.addCustomField(royaltySB.toString(), false);
                jockeyShopQuery.addFieldAggregateFunction("quantity", DebtorsCustomerInvoiceLines.class.getName(), "SUM");

                //Select from Credit Notes
                String jockeyShopQueryString = jockeyShopQuery.getNativeQuery().replaceAll(DebtorsCustomerInvoiceMaster.class.getSimpleName(), DebtorsCreditNoteMaster.class.getSimpleName());
                jockeyShopQueryString = jockeyShopQueryString.replaceAll(DebtorsCustomerInvoiceLines.class.getSimpleName(), DebtorsCreditNoteLines.class.getSimpleName());

                List<Object[]> jockeyShopTotalsList = (List<Object[]>) util.executeNativeQuery(jockeyShopQueryString, userData);

                //There should only be one element in the list.
                if (jockeyShopTotalsList != null && jockeyShopTotalsList.size() == 1) {
                    Object[] jockeyShopTotals = jockeyShopTotalsList.get(0);
                    int jockeyShopCurIndex = 0;

                    if (!isBlank(royaltyFields[0])) {
                        jockeyShopCurIndex++;
                    }

                    if (!isBlank(royaltyFields[1])) {
                        jockeyShopCurIndex++;
                    }

                    if (!isBlank(royaltyFields[2])) {
                        jockeyShopCurIndex++;
                    }

                    jockeyShopGrossValue = util.roundBigDecimal((BigDecimal) jockeyShopTotals[jockeyShopCurIndex++]);
                    jockeyShopUnits = util.roundBigDecimal((BigDecimal) jockeyShopTotals[jockeyShopCurIndex++]);
                    jockeyShopRoyaltyValue = util.roundBigDecimal((BigDecimal) jockeyShopTotals[jockeyShopCurIndex++]);
                    jockeyShopPacks = util.roundBigDecimal((BigDecimal) jockeyShopTotals[jockeyShopCurIndex++]);
                } else {
                    //No values for Jockey Shops
                    jockeyShopGrossValue = BigDecimal.ZERO;
                    jockeyShopUnits = BigDecimal.ZERO;
                    jockeyShopRoyaltyValue = BigDecimal.ZERO;
                    jockeyShopPacks = BigDecimal.ZERO;
                }
            }

            if (royaltyFields[2] != null) {
                String fieldValue = String.valueOf(resultArr[currentIndex++]);
                EMCDataType dt = itemMasterInstance.getDataType(String.valueOf(royaltyFields[2]), userData);
                if (dt != null && dt.getRelatedTable() != null) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, dt.getRelatedTable());
                    query.addAnd(dt.getRelatedField(), fieldValue);

                    EMCEntityClass relatedEntity = (EMCEntityClass) util.executeSingleResultQuery(query, userData);

                    if (relatedEntity != null && relatedEntity instanceof RoyaltyInterface) {
                        ds.setRoyaltyField3(((RoyaltyInterface) relatedEntity).getFieldToDisplay());
                    } else {
                        ds.setRoyaltyField3(fieldValue);
                    }
                } else {
                    ds.setRoyaltyField3(fieldValue);
                }
            }

            ds.setGrossValue(util.roundBigDecimal((BigDecimal) resultArr[currentIndex++]));
            ds.setUnits(util.roundBigDecimal((BigDecimal) resultArr[currentIndex++]));

            ds.setRoyaltyPercentage(util.roundBigDecimal((BigDecimal) resultArr[currentIndex++]));

            ds.setRoyaltyGroup((String) resultArr[currentIndex++]);

            ds.setRoyaltyValue(util.roundBigDecimal((BigDecimal) resultArr[currentIndex++]));
            ds.setPacks(util.roundBigDecimal((BigDecimal) resultArr[currentIndex++]));

            //Used for grouping - should always be set.
            ds.setPrintLessJockeyShops(printLessJockeyShops);

            tempDSList.add(ds);
        }

        //Also set totals for last line.
        if (!tempDSList.isEmpty()) {
            DebtorsRoyaltyReportTempDS tempCNDS = (DebtorsRoyaltyReportTempDS) tempDSList.get(tempDSList.size() - 1);

            tempCNDS.setJockeyShopGrossValue(jockeyShopGrossValue);
            tempCNDS.setJockeyShopUnits(jockeyShopUnits);
            tempCNDS.setJockeyShopRoyaltyValue(jockeyShopRoyaltyValue);
            tempCNDS.setJockeyShopPacks(jockeyShopPacks);
        }

        return tempDSList;
    }

    /**
     * Gets report data for the specified session id.
     * @param sessionId Session id.
     * @param userData User data.
     * @return Report data for the specified session.
     */
    public List<Object> getReportData(long sessionId, EMCUserData userData) {
        List<Object> reportDSList = new ArrayList<Object>();

        Map<String, BigDecimal[]> jockeyShopTotalMap = new HashMap<String, BigDecimal[]>();
        //Get Jockey Shop totals first, and store in Map
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsRoyaltyReportTempDS.class);
        query.addField("royaltyGroup");                                     //0
        query.addField("royaltyField1");                                    //1
        query.addField("royaltyField2");                                    //2
        query.addFieldAggregateFunction("jockeyShopGrossValue", "SUM");     //3
        query.addFieldAggregateFunction("jockeyShopUnits", "SUM");          //4
        query.addFieldAggregateFunction("jockeyShopRoyaltyValue", "SUM");   //5
        query.addFieldAggregateFunction("jockeyShopPacks", "SUM");          //6
        query.addAnd("sessionId", sessionId);
        query.addGroupBy("royaltyField1");
        query.addGroupBy("royaltyField2");
        query.addGroupBy("royaltyGroup");
        query.addGroupBy("printLessJockeyShops");

        List<Object[]> jockeyShopTotals = (List<Object[]>) util.executeGeneralSelectQuery(query, userData);

        for (Object[] row : jockeyShopTotals) {
            BigDecimal[] totals = new BigDecimal[4];

            totals[0] = (BigDecimal) row[3];
            totals[1] = (BigDecimal) row[4];
            totals[2] = (BigDecimal) row[5];
            totals[3] = (BigDecimal) row[6];

            StringBuilder jockeyShopTotalsKey = new StringBuilder();
            jockeyShopTotalsKey.append(row[1]);
            jockeyShopTotalsKey.append(row[2]);

            jockeyShopTotalMap.put(jockeyShopTotalsKey.toString(), totals);
        }

        query = new EMCQuery(enumQueryTypes.SELECT, DebtorsRoyaltyReportTempDS.class);
        query.addField("royaltyGroup");                                     //0
        query.addField("royaltyField1");                                    //1
        query.addField("royaltyField2");                                    //2
        query.addField("royaltyField3");                                    //3
        query.addFieldAggregateFunction("units", "SUM");                    //4
        query.addFieldAggregateFunction("grossValue", "SUM");               //5
        query.addFieldAggregateFunction("royaltyValue", "SUM");             //6
        query.addField("royaltyPercentage");                                //7
        query.addField("printLessJockeyShops");                             //8
        query.addFieldAggregateFunction("packs", "SUM");                    //9
        query.addAnd("sessionId", sessionId);
        query.addGroupBy("royaltyField1");
        query.addGroupBy("royaltyField2");
        query.addGroupBy("royaltyField3");
        query.addGroupBy("royaltyGroup");
        query.addGroupBy("printLessJockeyShops");
        query.addOrderBy("royaltyField1");
        query.addOrderBy("royaltyField2");
        query.addOrderBy("royaltyField3");
        query.addOrderBy("royaltyGroup");

        List<Object[]> reportData = (List<Object[]>) util.executeGeneralSelectQuery(query, userData);

        String lastJockeyShopTotalsKey = null;

        for (int i = 0; i < reportData.size(); i++) {
            Object[] row = reportData.get(i);

            DebtorsRoyaltyReportDS ds = new DebtorsRoyaltyReportDS();

            ds.setRoyaltyGroup((String) row[0]);
            ds.setRoyaltyField1((String) row[1]);
            ds.setRoyaltyField2((String) row[2]);
            ds.setRoyaltyField3((String) row[3]);
            ds.setUnits((BigDecimal) row[4]);
            ds.setGrossValue((BigDecimal) row[5]);
            ds.setRoyaltyValue((BigDecimal) row[6]);
            ds.setRoyaltyPercentage((BigDecimal) row[7]);
            ds.setPrintLessJockeyShops((Boolean) row[8]);
            ds.setPacks((BigDecimal) row[9]);

            StringBuilder jockeyShopTotalsKey = new StringBuilder();
            jockeyShopTotalsKey.append(ds.getRoyaltyField1());
            jockeyShopTotalsKey.append(ds.getRoyaltyField2());

            //This should never be false.  Only set Jockey shop totals on the last record in the data set for each brand.
            if ((lastJockeyShopTotalsKey != null && !lastJockeyShopTotalsKey.equals(jockeyShopTotalsKey.toString())) && jockeyShopTotalMap.containsKey(lastJockeyShopTotalsKey.toString())) {
                //Get the last DS in the previous set and set totals on it.
                DebtorsRoyaltyReportDS lastDS = (DebtorsRoyaltyReportDS) reportDSList.get(i - 1);

                BigDecimal[] jockeyShopTotalsArr = jockeyShopTotalMap.get(lastJockeyShopTotalsKey.toString());

                lastDS.setJockeyShopGrossValue(jockeyShopTotalsArr[0]);
                lastDS.setJockeyShopUnits(jockeyShopTotalsArr[1]);
                lastDS.setJockeyShopRoyaltyValue(jockeyShopTotalsArr[2]);
                lastDS.setJockeyShopPacks(jockeyShopTotalsArr[3]);
            }

            lastJockeyShopTotalsKey = jockeyShopTotalsKey.toString();

            reportDSList.add(ds);
        }

        //This should never be false.  Set Jockey shop totals on the last record in the data set.
        if (jockeyShopTotalMap.containsKey(lastJockeyShopTotalsKey.toString())) {
            //Get the last DS in the previous set and set totals on it.
            DebtorsRoyaltyReportDS lastDS = (DebtorsRoyaltyReportDS) reportDSList.get(reportDSList.size() - 1);

            BigDecimal[] jockeyShopTotalsArr = jockeyShopTotalMap.get(lastJockeyShopTotalsKey.toString());

            lastDS.setJockeyShopGrossValue(jockeyShopTotalsArr[0]);
            lastDS.setJockeyShopUnits(jockeyShopTotalsArr[1]);
            lastDS.setJockeyShopRoyaltyValue(jockeyShopTotalsArr[2]);
            lastDS.setJockeyShopPacks(jockeyShopTotalsArr[3]);
        }
        
        return reportDSList;
    }
}

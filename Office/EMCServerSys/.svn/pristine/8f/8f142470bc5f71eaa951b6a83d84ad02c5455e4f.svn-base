/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.ageing;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryParametersLocal;
import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.bus.inventory.transactions.InventorySummaryLocal;
import emc.bus.inventory.transactions.InventoryTransactionsLocal;
import emc.bus.workflow.WFActivityLocal;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.agebins.InventoryAgeBins;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.entity.inventory.stocksettlement.InventoryStockSettlement;
import emc.entity.inventory.stocksettlement.InventoryStockSettlementHistory;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.workflow.WorkFlowActivity;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryFieldTypes;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.ageing.AgeingReportTypes;
import emc.enums.inventory.journals.InventoryJournalTypes;
import emc.enums.inventory.settlement.SettlementStatus;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.enums.inventory.transactions.InventoryTransactionsRefType;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.xml.EMCXMLHandler;
import emc.reporttools.EMCReportConfig;
import emc.server.datehandler.EMCDateHandlerLocal;
import emc.server.filehandeler.EMCFileHandlerLocal;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author rico
 */
@Stateless
public class InventoryAgeingReportBean extends EMCReportBean implements InventoryAgeingReportBeanLocal {

    @EJB
    private InventoryItemMasterLocal itemBean;
    @EJB
    private InventoryTransactionsLocal inventoryTransactionsBean;
    @EJB
    private InventoryDimensionTableLocal dimensionBean;
    @EJB
    private InventoryParametersLocal inventParamBean;
    @EJB
    private EMCFileHandlerLocal fileBean;
    @EJB
    private WFActivityLocal activityBean;
    @EJB
    private EMCDateHandlerLocal dateBean;
    @EJB
    private InventorySummaryLocal summaryBean;

    @Override
    public List<Object> getReportResult(List<Object> parameters, AgeingReportTypes type, EMCReportConfig reportConfig, EMCUserData userData) {
        Date atDate = null;
        boolean useCurrentCost = false;
        Date mustBeOlderThan = null;
        boolean subtractReserved = false;

        Connection conn = null;

        List<Object> retList = new ArrayList<Object>();

        try {
            conn = util.connectToDatabase(userData);


            if (reportConfig != null) {
                if (reportConfig.getParameters().get("useCurrentCost") != null) {
                    useCurrentCost = (Boolean) reportConfig.getParameters().get("useCurrentCost");
                }

                if (reportConfig.getParameters().get("atDate") != null) {
                    atDate = (Date) reportConfig.getParameters().get("atDate");
                } else {
                    atDate = Functions.nowDate();
                }

                if (reportConfig.getParameters().get("mustBeOlderThan") != null) {
                    mustBeOlderThan = (Date) reportConfig.getParameters().get("mustBeOlderThan");
                }

                if (reportConfig.getParameters().get("subtractReserved") != null) {
                    subtractReserved = (Boolean) reportConfig.getParameters().get("subtractReserved");
                }

            } else {
                String physicalDateStr = extractDate((EMCQuery) parameters.get(1), userData);
                atDate = Functions.string2Date(physicalDateStr, "yyyy-MM-dd");
            }

            //Get last stock close date before at date.
            EMCQuery settlementQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryStockSettlement.class);
            settlementQuery.addFieldAggregateFunction("endDate", "MAX");
            settlementQuery.addAnd("runStatus", SettlementStatus.COMPLETED.toString());
            settlementQuery.addAnd("endDate", atDate, EMCQueryConditions.LESS_THAN);

            List<Object[]> selectedRec = (List<Object[]>) util.exJDBCFieldReadQuery(conn, settlementQuery, userData);

            Date closedDate = null;
            if (selectedRec != null && !selectedRec.isEmpty() && !isBlank(selectedRec.get(0)[0])) {
                closedDate = (Date) selectedRec.get(0)[0];
            }

            List<Object[]> selectedData;

            switch (type) {
                case DETAIL:
                    selectedData = getDetailGroupsAndItems(parameters, closedDate, conn, userData);
                    break;
                case SUMMARY:
                    selectedData = getGroupsAndItems(parameters, closedDate, conn, userData);
                    break;
                case FULL_DETAIL:
                    selectedData = getSuperDetailGroupsAndItems(parameters, closedDate, conn, userData);
                    break;
                default:
                    selectedData = new ArrayList<Object[]>();
                    break;
            }

            retList = buildDataList(parameters, selectedData, atDate, closedDate, useCurrentCost, type, mustBeOlderThan, subtractReserved, conn, userData);
        } catch (EMCEntityBeanException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Could not print the ageing report: " + ex.getMessage());
        } finally {
            if (conn != null) {
                util.closeConnectionToDB(conn, userData);
            }
        }
        return retList;
    }

    private String extractDate(EMCQuery qu, EMCUserData userData) {
        String physicalDateStr;

        List<String> physicalDateList = qu.getFieldValue(InventoryTransactions.class.getName(), "physicalDate", EMCQueryFieldTypes.DATE, true);
        if (physicalDateList.size() > 0) {
            if (physicalDateList.size() > 1) {
                physicalDateStr = physicalDateList.get(1);
            } else {
                physicalDateStr = physicalDateList.get(0);
            }
        } else {
            Logger.getLogger("emc").log(Level.WARNING, "No date found for selection. Using TODAY", userData);
            physicalDateStr = Functions.nowDateString("yyyy-MM-dd");
            qu.addAnd("physicalDate", physicalDateStr, InventoryTransactions.class.getName(), EMCQueryConditions.LESS_THAN_EQ);
        }
        return physicalDateStr;
    }

    private List<Object[]> getSuperDetailGroupsAndItems(List<Object> parameters, Date closedDate, Connection conn, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery original = (EMCQuery) parameters.get(1);
        EMCQuery qu = original.copyQuery();
        //qu.addTableAsField(InventoryTransactions.class.getName());
        qu.addField("itemGroup", InventoryItemMaster.class.getName());          //0
        qu.addField("itemReference", InventoryItemMaster.class.getName());      //1
        qu.addField("description", InventoryItemMaster.class.getName());        //2
        qu.addField("baseUOM", InventoryItemMaster.class.getName());            //3
        qu.addField("itemId", InventoryItemMaster.class.getName());             //4
        qu.addField("dimension1Id", InventoryDimensionTable.class.getName());   //5
        qu.addField("dimension2Id", InventoryDimensionTable.class.getName());   //6
        qu.addField("dimension3Id", InventoryDimensionTable.class.getName());   //7
        qu.openAndConditionBracket();
        //Select open transactions
        qu.addAnd("closedFlag", false, InventoryTransactions.class.getName());
        if (closedDate != null) {
            //Or select transactions closed after stock close closest to atDate.
            qu.addOr("closedDate", closedDate, InventoryTransactions.class.getName(), EMCQueryConditions.GREATER_THAN);
        }
        qu.closeConditionBracket();
        qu.addGroupBy(InventoryItemMaster.class.getName(), "itemId");
        qu.addGroupBy(InventoryDimensionTable.class.getName(), "dimension1Id");
        qu.addGroupBy(InventoryDimensionTable.class.getName(), "dimension2Id");
        qu.addGroupBy(InventoryDimensionTable.class.getName(), "dimension3Id");

        qu.addOrderBy("itemReference", InventoryItemMaster.class.getName());
        qu.addOrderBy("dimension1Id", InventoryDimensionTable.class.getName());
        qu.addOrderBy("dimension3Id", InventoryDimensionTable.class.getName());
        qu.addOrderBy("dimension2Id", InventoryDimensionTable.class.getName());

        return util.exJDBCFieldReadQuery(conn, qu, userData);
    }

    private List<Object[]> getDetailGroupsAndItems(List<Object> parameters, Date closedDate, Connection conn, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery original = (EMCQuery) parameters.get(1);
        EMCQuery qu = original.copyQuery();
        //qu.addTableAsField(InventoryTransactions.class.getName());
        qu.addField("itemGroup", InventoryItemMaster.class.getName());
        qu.addField("itemReference", InventoryItemMaster.class.getName());
        qu.addField("description", InventoryItemMaster.class.getName());
        qu.addField("baseUOM", InventoryItemMaster.class.getName());
        qu.addField("itemId", InventoryItemMaster.class.getName());
        qu.addField("dimension1Id", InventoryDimensionTable.class.getName());
        qu.addField("dimension2Id", InventoryDimensionTable.class.getName());
        qu.addField("dimension3Id", InventoryDimensionTable.class.getName());
        qu.openAndConditionBracket();
        //Select open transactions
        qu.addAnd("closedFlag", false, InventoryTransactions.class.getName());
        if (closedDate != null) {
            //Or select transactions closed after stock close closest to atDate.
            qu.addOr("closedDate", closedDate, InventoryTransactions.class.getName(), EMCQueryConditions.GREATER_THAN);
        }
        qu.closeConditionBracket();
        qu.addGroupBy(InventoryItemMaster.class.getName(), "itemId");
        qu.addGroupBy(InventoryDimensionTable.class.getName(), "dimension1Id");
        qu.addGroupBy(InventoryDimensionTable.class.getName(), "dimension2Id");
        qu.addGroupBy(InventoryDimensionTable.class.getName(), "dimension3Id");
        return util.exJDBCFieldReadQuery(conn, qu, userData);
    }

    private List<Object[]> getGroupsAndItems(List<Object> parameters, Date closedDate, Connection conn, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery original = (EMCQuery) parameters.get(1);
        EMCQuery qu = original.copyQuery();
        //qu.addTableAsField(InventoryTransactions.class.getName());
        qu.addField("itemGroup", InventoryItemMaster.class.getName());
        qu.addField("itemReference", InventoryItemMaster.class.getName());
        qu.addField("description", InventoryItemMaster.class.getName());
        qu.addField("baseUOM", InventoryItemMaster.class.getName());
        qu.addField("itemId", InventoryItemMaster.class.getName());
        qu.openAndConditionBracket();
        //Select open transactions
        qu.addAnd("closedFlag", false, InventoryTransactions.class.getName());
        if (closedDate != null) {
            //Or select transactions closed after stock close closest to atDate.
            qu.addOr("closedDate", closedDate, InventoryTransactions.class.getName(), EMCQueryConditions.GREATER_THAN);
        }
        qu.closeConditionBracket();

        qu.addGroupBy(InventoryItemMaster.class.getName(), "itemId");
        return util.exJDBCFieldReadQuery(conn, qu, userData);
    }

    private List<Object> buildDataList(List<Object> parameters, List<Object[]> queryResult, Date physicalDate, Date closedDate, boolean useCurrentCost, AgeingReportTypes type, Date mustBeOlderThan, boolean subtractReserved, Connection conn, EMCUserData userData) throws EMCEntityBeanException {
        List messages = new ArrayList();
        List<Object> ret = new ArrayList<Object>();

        //get bins
        EMCQuery binQ = new EMCQuery(enumQueryTypes.SELECT, InventoryAgeBins.class);
        binQ.addOrderBy("binOrder");
        binQ.addField("numberOfDaysInBin");
        List<Object[]> bins = util.exJDBCFieldReadQuery(conn, binQ, userData);
        List<Integer> maxBinSize = new ArrayList();
        //build the binsize list
        if (bins.size() > 6) {
            Logger.getLogger("emc").log(Level.SEVERE, "Only six age bins allowed", userData);
        }
        if (bins.size() > 4) {
            Logger.getLogger("emc").log(Level.WARNING, "Default Report Template only supports 4 bins. " + bins.size() + " found.", userData);
        }
        for (int j = 0; j < bins.size(); j++) {
            Integer value = (Integer) bins.get(j)[0];
            Integer maxSize = (Integer) value;
            for (int k = 0; k < j; k++) {
                Integer curValue = (Integer) bins.get(k)[0];
                maxSize += curValue;
            }
            maxBinSize.add(maxSize);
        }

        Map<String, BigDecimal> reservedStock = null;
        String previousItem = "";

        Date lastStockCloseDate = inventoryTransactionsBean.getLastStockCloseDate(userData);
        if (lastStockCloseDate == null || lastStockCloseDate.compareTo(physicalDate) <= 0) {
            //build data set
            EMCQuery qu = (EMCQuery) parameters.get(1);
            qu.addTableAsField(InventoryTransactions.class.getName());
            if (isBlank(mustBeOlderThan)) {
                qu.addAnd("physicalDate", physicalDate, InventoryTransactions.class.getName(), EMCQueryConditions.LESS_THAN_EQ);
                qu.addAnd("physicalDate", null, InventoryTransactions.class.getName(), EMCQueryConditions.IS_NOT_NULL);
            }
            qu.addAnd("closedFlag", false, InventoryTransactions.class.getName());
            qu.addOrderBy("physicalDate", InventoryTransactions.class.getName());

            //loop for each item
            for (Object[] values : queryResult) {
                if (subtractReserved && !previousItem.equals(values[4].toString())) {
                    reservedStock = fetchOnHandReserved(values[4].toString(), conn, userData);
                    previousItem = values[4].toString();
                }
                InventoryAgeingDS curAge = new InventoryAgeingDS();
                curAge.setItemGroup(values[0].toString());
                curAge.setItemRef(values[1].toString());
                curAge.setUom(values[3].toString().toUpperCase());
                curAge.setItemDesc(values[2].toString());
                curAge.setItemId(values[4].toString());
                if (type.getId() >= AgeingReportTypes.DETAIL.getId()) {
                    curAge.setDimension1((String) values[5]);
                    curAge.setDimension2((String) values[6]);
                    curAge.setDimension3((String) values[7]);
                }
//                if (type.getId() >= AgeingReportTypes.FULL_DETAIL.getId()) {
//                    curAge.setWarehouse((String) values[8]);
//                    curAge.setLocation((String) values[9]);
//                    curAge.setBatch((String) values[10]);
//                    curAge.setSerial((String) values[11]);
//                }
                List<InventoryAgeingDS> curAgeProcessList = getBinData(qu, curAge, maxBinSize, physicalDate, useCurrentCost, type, messages, mustBeOlderThan, conn, userData);
                for (InventoryAgeingDS curAgeProcess : curAgeProcessList) {
                    if (util.compareDouble(curAgeProcess.getBin1Qty(), 0) != 0 ||
                            util.compareDouble(curAgeProcess.getBin2Qty(), 0) != 0 ||
                            util.compareDouble(curAgeProcess.getBin3Qty(), 0) != 0 ||
                            util.compareDouble(curAgeProcess.getBin4Qty(), 0) != 0 ||
                            util.compareDouble(curAgeProcess.getBin5Qty(), 0) != 0 ||
                            util.compareDouble(curAgeProcess.getBin6Qty(), 0) != 0) {
                        if (subtractReserved) {
                            if (!subtractReservedStock(curAgeProcess, reservedStock, conn, userData)) {
                                continue;
                            }
                        }
                        //Only add ageing record if it has values.
                        ret.add(curAgeProcess);
                    }
                }
            }
        } else {
            EMCQuery query = ((EMCQuery) parameters.get(1)).copyQuery();
            query.addTableAsField(InventoryTransactions.class.getName());
            query.addAnd("physicalDate", physicalDate, InventoryTransactions.class.getName(), EMCQueryConditions.LESS_THAN_EQ);
            query.openAndConditionBracket();
            //Select open transactions
            query.addAnd("closedFlag", false, InventoryTransactions.class.getName());
            //Or select transactions closed after stock close closest to atDate.
            query.addOr("closedDate", closedDate, InventoryTransactions.class.getName(), EMCQueryConditions.GREATER_THAN);
            query.closeConditionBracket();
            query.addOrderBy("physicalDate", InventoryTransactions.class.getName());
            query.setEntityClassName(InventoryTransactions.class);
            query.clearFieldList();
            List<InventoryTransactions> transactions = (List<InventoryTransactions>) util.exJDBCReadQuery(conn, query, userData);

            //Seperate in and out transaction immediately after selection.
            List<InventoryTransactions> inTransactions = new ArrayList<InventoryTransactions>();
            List<InventoryTransactions> outTransactions = new ArrayList<InventoryTransactions>();
            for (InventoryTransactions transaction : transactions) {
                if (InventoryTransactionDirection.IN.toString().equals(transaction.getDirection())) {
                    inTransactions.add(transaction);
                } else {
                    outTransactions.add(transaction);
                }
            }

            EMCXMLHandler xmlHandler = new EMCXMLHandler();
            //Roll back stock close to get data as at atDate.
            query = new EMCQuery("SELECT hist.* " +
                    "FROM InventoryTransactions inTx, InventoryStockSettlementHistory hist, InventoryTransactions outTx, InventoryStockSettlement sett " +
                    "WHERE inTx.recordID = hist.inTxRecordId " +
                    "AND outTx.recordID = hist.outTxUsedInSettlement " +
                    "AND hist.settlementId = sett.settlementId " +
                    "AND sett.endDate > '" + Functions.date2SQLString(closedDate) + "' " +
                    "AND ((inTx.physicalDate <= '" + Functions.date2SQLString(physicalDate) + "' AND outTx.physicalDate > '" + Functions.date2SQLString(physicalDate) + "')" +
                    "OR (outTx.physicalDate <= '" + Functions.date2SQLString(physicalDate) + "' AND inTx.physicalDate > '" + Functions.date2SQLString(physicalDate) + "'))");
            query.setEntityClassName(InventoryStockSettlementHistory.class);
            List<InventoryStockSettlementHistory> settlementHistory = (List<InventoryStockSettlementHistory>) util.exJDBCReadQuery(conn, query, userData);


            //For each settlement history record, add totals back to in and out transactions.
            for (InventoryStockSettlementHistory settlement : settlementHistory) {
                for (InventoryTransactions transaction : inTransactions) {
                    if (transaction.getRecordID() == settlement.getInTxRecordId()) {
                        if (transaction.getClosedQty() > 0) {
                            transaction.setClosedFlag(false);

                            if (settlement.isWasInClosed() && settlement.isWasOutClosed()) {
                                //Use smallest quantity
                                InventorySummary inSummary = (InventorySummary) xmlHandler.XMLToObject(settlement.getInXmlSummary(), userData);
                                InventorySummary outSummary = (InventorySummary) xmlHandler.XMLToObject(settlement.getOutXmlSummary(), userData);
                                if (Math.abs(outSummary.getPhysicalTotal()) > inSummary.getPhysicalTotal()) {
                                    //Physical total will be negative for out summaries.
                                    transaction.setClosedQty(transaction.getClosedQty() + outSummary.getPhysicalTotal());
                                } else {
                                    transaction.setClosedQty(transaction.getClosedQty() - inSummary.getPhysicalTotal());
                                }
                            } else if (settlement.isWasOutClosed()) {
                                InventorySummary summary = (InventorySummary) xmlHandler.XMLToObject(settlement.getOutXmlSummary(), userData);
                                //Physical total will be negative for out summaries.
                                transaction.setClosedQty(transaction.getClosedQty() + summary.getPhysicalTotal());
                            } else if (settlement.isWasInClosed()) {
                                InventorySummary summary = (InventorySummary) xmlHandler.XMLToObject(settlement.getInXmlSummary(), userData);
                                //Physical total will be positive for in summaries.
                                transaction.setClosedQty(transaction.getClosedQty() - summary.getPhysicalTotal());
                            }
                        }
                        break;
                    }
                }

                for (InventoryTransactions transaction : outTransactions) {
                    if (transaction.getRecordID() == settlement.getOutTxUsedInSettlement()) {
                        if (transaction.getClosedQty() > 0) {
                            transaction.setClosedFlag(false);

                            if (settlement.isWasInClosed() && settlement.isWasOutClosed()) {
                                //Use smallest quantity
                                InventorySummary inSummary = (InventorySummary) xmlHandler.XMLToObject(settlement.getInXmlSummary(), userData);
                                InventorySummary outSummary = (InventorySummary) xmlHandler.XMLToObject(settlement.getOutXmlSummary(), userData);
                                if (Math.abs(outSummary.getPhysicalTotal()) > inSummary.getPhysicalTotal()) {
                                    //Physical total will be negative for out summaries.
                                    transaction.setClosedQty(transaction.getClosedQty() + outSummary.getPhysicalTotal());
                                } else {
                                    transaction.setClosedQty(transaction.getClosedQty() - inSummary.getPhysicalTotal());
                                }
                            } else if (settlement.isWasOutClosed()) {
                                InventorySummary summary = (InventorySummary) xmlHandler.XMLToObject(settlement.getOutXmlSummary(), userData);
                                //Physical total will be negative for out summaries.
                                transaction.setClosedQty(transaction.getClosedQty() + summary.getPhysicalTotal());
                            } else if (settlement.isWasInClosed()) {
                                InventorySummary summary = (InventorySummary) xmlHandler.XMLToObject(settlement.getInXmlSummary(), userData);
                                //Physical total will be positive for in summaries.
                                transaction.setClosedQty(transaction.getClosedQty() - summary.getPhysicalTotal());
                            }
                        }
                        break;
                    }
                }
            }

            //loop for each item
            for (int j = 0; j < queryResult.size(); j++) {
                Object[] values = (Object[]) queryResult.get(j);
                InventoryAgeingDS curAge = new InventoryAgeingDS();
                curAge.setItemGroup(values[0].toString());
                curAge.setItemRef(values[1].toString());
                curAge.setUom(values[3].toString().toUpperCase());
                curAge.setItemDesc(values[2].toString());
                curAge.setItemId(values[4].toString());
                if (type.getId() >= AgeingReportTypes.DETAIL.getId()) {
                    curAge.setDimension1((String) values[5]);
                    curAge.setDimension2((String) values[6]);
                    curAge.setDimension3((String) values[7]);
                }
//                if (type.getId() >= AgeingReportTypes.FULL_DETAIL.getId()) {
//                    curAge.setWarehouse((String) values[8]);
//                    curAge.setLocation((String) values[9]);
//                    curAge.setBatch((String) values[10]);
//                    curAge.setSerial((String) values[11]);
//                }

                //Get all in and out transaction matching specified dims.
                List<InventoryTransactions> curIn = new ArrayList<InventoryTransactions>();
                List<InventoryTransactions> curOut = new ArrayList<InventoryTransactions>();
                if (!isBlank(curAge.getDimension1()) || !isBlank(curAge.getDimension2()) || !isBlank(curAge.getDimension3())) {
                    //Get all dimension ids for combination
                    EMCQuery dimQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryDimensionTable.class);
                    dimQuery.addField("recordID");
                    dimQuery.addAnd("dimension1Id", isBlank(curAge.getDimension1()) ? null : curAge.getDimension1());
                    dimQuery.addAnd("dimension2Id", isBlank(curAge.getDimension2()) ? null : curAge.getDimension2());
                    dimQuery.addAnd("dimension3Id", isBlank(curAge.getDimension3()) ? null : curAge.getDimension3());

                    List<Object[]> dimIDs = (List<Object[]>) util.exJDBCFieldReadQuery(conn, dimQuery, userData);

                    boolean containsDimId;
                    for (int i = 0; i < inTransactions.size(); i++) {
                        InventoryTransactions inTransaction = (InventoryTransactions) inTransactions.get(i);
                        containsDimId = false;
                        for (Object[] o : dimIDs) {
                            if ((Long) o[0] == inTransaction.getItemDimId()) {
                                containsDimId = true;
                                break;
                            }
                        }
                        if (inTransaction.getItemId().equals(curAge.getItemId()) && containsDimId) {
                            curIn.add(inTransaction);

                            //Remove transaction.
                            inTransactions.remove(i--);
                        }
                    }
                    for (int i = 0; i < outTransactions.size(); i++) {
                        InventoryTransactions outTransaction = (InventoryTransactions) outTransactions.get(i);
                        containsDimId = false;
                        for (Object[] o : dimIDs) {
                            if ((Long) o[0] == outTransaction.getItemDimId()) {
                                containsDimId = true;
                                break;
                            }
                        }
                        if (outTransaction.getItemId().equals(curAge.getItemId()) && containsDimId) {
                            curOut.add(outTransaction);

                            //Remove transaction.
                            outTransactions.remove(i--);
                        }
                    }
                } else {
                    for (int i = 0; i < inTransactions.size(); i++) {
                        InventoryTransactions inTransaction = (InventoryTransactions) inTransactions.get(i);
                        if (inTransaction.getItemId().equals(curAge.getItemId())) {
                            curIn.add(inTransaction);

                            //Remove transaction.
                            inTransactions.remove(i--);
                        }
                    }
                    for (int i = 0; i < outTransactions.size(); i++) {
                        InventoryTransactions outTransaction = (InventoryTransactions) outTransactions.get(i);
                        if (outTransaction.getItemId().equals(curAge.getItemId())) {
                            curOut.add(outTransaction);

                            //Remove transaction.
                            outTransactions.remove(i--);
                        }
                    }
                }

                List<InventoryAgeingDS> curAgeProcessList = populateBinData(curOut, curIn, curAge, maxBinSize, physicalDate, useCurrentCost, true, type, messages, mustBeOlderThan, conn, userData);
                for (InventoryAgeingDS curAgeProcess : curAgeProcessList) {
                    if (util.compareDouble(curAgeProcess.getBin1Qty(), 0) != 0 ||
                            util.compareDouble(curAgeProcess.getBin2Qty(), 0) != 0 ||
                            util.compareDouble(curAgeProcess.getBin3Qty(), 0) != 0 ||
                            util.compareDouble(curAgeProcess.getBin4Qty(), 0) != 0 ||
                            util.compareDouble(curAgeProcess.getBin5Qty(), 0) != 0 ||
                            util.compareDouble(curAgeProcess.getBin6Qty(), 0) != 0) {
                        if (subtractReserved) {
                            if (!subtractReservedStock(curAgeProcess, reservedStock, conn, userData)) {
                                continue;
                            }
                        }
                        //Only add ageing record if it has values.
                        ret.add(curAgeProcess);
                    }
                }
            }
        }
        if (!messages.isEmpty()) {
            String emp = null;
            try {
                emp = inventParamBean.getInventoryParameters(userData).getAgeingMessageLogUser();
            } catch (Exception ex) {
                this.logMessage(Level.WARNING, "Failed to find inventory parameters.", userData);
            }
            if (emp != null) {
                WorkFlowActivity act = new WorkFlowActivity();
                act.setDescription("Stock Ageing Feedback.");
                act.setRequiredCompletionDate(dateBean.nowDate());
                act.setEmployeeNumber(emp);
                //act.setSourceTable(InventoryStockSettlementHistory.class.getName());
                act.setActivityId("Ageing_msg_" + Long.toString(dateBean.nowDate().getTime()));
                act.setStartDate(dateBean.nowDate());
                //act.setReferenceSource(Long.toString(sm.getRecordID()));
                try {
                    act = (WorkFlowActivity) activityBean.insert(act, userData);
                    fileBean.attachFileToRecord(act, "Stock Settlement Feedback", messages, "AgeingMessages.txt", userData);
                    this.logMessage(Level.WARNING, "Some warnings were logged. An activity has been logged for " + emp, userData);
                } catch (Exception ex) {
                    this.logMessage(Level.WARNING, "Failed to log report warnings for " + emp, userData);
                }

            } else {
                this.logMessage(Level.WARNING, "No user set up for ageing messages on inventory parameters.", userData);
            }
            //done
        }
        return ret;
    }

    /**
     * Method used to map the close process
     * @param closeData
     * @param inTx
     * @param outTx
     * @param qtyClosed
     */
    private void mapCloseData(HashMap<Long, List<CloseDataHolder>> closeData, InventoryTransactions inTx, InventoryTransactions outTx, double qtyClosed) {
        List<CloseDataHolder> cur = closeData.get(outTx.getRecordID());
        if (cur == null) {
            cur = new ArrayList<CloseDataHolder>();
        }
        CloseDataHolder curH = new CloseDataHolder(inTx.getRecordID(), qtyClosed,inTx);
        cur.add(curH);
        closeData.put(outTx.getRecordID(), cur);
    }

    /**
     * Resolves the matching out
     * @param result
     * @param outTx
     * @param qtyToResolve
     * @param left
     * @param closeData
     * @param fifoIn
     * @param userData
     * @return
     */
    private ResolverResult resolveMatchingOut(ResolverResult result, InventoryTransactions outTx, double qtyToResolve, double left, HashMap<Long, List<CloseDataHolder>> closeData, HashMap<Long, Double> fifoIn, Connection conn, EMCUserData userData) throws EMCEntityBeanException {
        //check if this out was closed in stored map
        EMCXMLHandler xmlHandler = new EMCXMLHandler();
        List<CloseDataHolder> inList = closeData.get(outTx.getRecordID());
        if (inList != null) {
            //read the data from last to first
            for (int i = inList.size() - 1; i >= 0; i--) {
                //check FIFO map to see if qty is still available
                CloseDataHolder curHolder = inList.get(i);
                Double used = fifoIn.get(curHolder.getRecordId());
                double available = curHolder.getQtyClosed();
                //- (used == null ? 0 : used.doubleValue());
                //enough to satisfy
                if (util.compareDouble(available, left) >= 0) {
                    //use what we nee log it aginst the Fifo map
                    used = (used == null ? 0 : used) + left;
                    fifoIn.put(curHolder.getRecordId(), used);
                    InventoryTransactions t = (InventoryTransactions) util.findPersisted(InventoryTransactions.class, curHolder.getRecordId(), userData);
                    if(t ==null)t=curHolder.getTrans();
                    result = resolveOriginalDate(result,t , left, closeData, fifoIn, conn, userData);
                    left = 0;
                } else if (util.compareDouble(available, 0) > 0) { //some left use it
                    left -= available;
                    used = (used == null ? 0 : used) + available;
                    fifoIn.put(curHolder.getRecordId(), used);
                    InventoryTransactions t = (InventoryTransactions) util.findPersisted(InventoryTransactions.class, curHolder.getRecordId(), userData);
                    if(t==null) t= curHolder.getTrans();
                    result = resolveOriginalDate(result,t , available, closeData, fifoIn, conn, userData);
                }
                if (util.compareDouble(left, 0) <= 0) {
                    i = -1;//break
                }
            }
        }
        //if more left check on disk
        if (util.compareDouble(left, 0) > 0) {
            EMCQuery qHist = new EMCQuery(enumQueryTypes.SELECT, InventoryStockSettlementHistory.class);
            qHist.addAnd("outTxUsedInSettlement", outTx.getRecordID());
            //invert the selection
            qHist.addOrderBy("recordID", InventoryStockSettlementHistory.class.getName(), EMCQueryOrderByDirections.DESC);
            List<InventoryStockSettlementHistory> cList = util.exJDBCReadQuery(conn, qHist, userData);
            for (InventoryStockSettlementHistory curH : cList) {
                Double used = fifoIn.get(curH.getInTxRecordId());
                double onTx = curH.getQtyClosed();//could be zero
                if (util.compareDouble(curH.getQtyClosed(), 0) == 0) {
                    if (curH.isWasInClosed()) {
                        InventorySummary inSummary = (InventorySummary) xmlHandler.XMLToObject(curH.getInXmlSummary(), userData);
                        onTx = inSummary.getPhysicalTotal();
                    } else {
                        InventorySummary outSummary = (InventorySummary) xmlHandler.XMLToObject(curH.getOutXmlSummary(), userData);
                        onTx = -1 * outSummary.getPhysicalTotal();
                    }
                }
                double available = onTx;
                //- (used == null ? 0 : used.doubleValue()); check if using this as well will exceed the qty on the tx?
                //enough to satisfy
                if (util.compareDouble(available, left) >= 0) {
                    //use what we nee log it aginst the Fifo map
                    used = (used == null ? 0 : used) + qtyToResolve;
                    fifoIn.put(curH.getInTxRecordId(), used);
                    result = resolveOriginalDate(result, (InventoryTransactions) util.findPersisted(InventoryTransactions.class, curH.getInTxRecordId(), userData), left, closeData, fifoIn, conn, userData);
                    left = 0;
                } else if (util.compareDouble(available, 0) > 0) { //some left use it
                    left -= available;
                    used = (used == null ? 0 : used) + available;
                    fifoIn.put(curH.getInTxRecordId(), used);
                    result = resolveOriginalDate(result, (InventoryTransactions) util.findPersisted(InventoryTransactions.class, curH.getInTxRecordId(), userData), available, closeData, fifoIn, conn, userData);
                }
                if (util.compareDouble(left, 0) <= 0) {
                    break;//from for all is resolved
                }
            }
        }
        result.setLeft(left);
        return result;
    }

    /**
     * Resolves the original receive date of the stock
     * @param inTx
     * @param closeData
     * @param fifoIn
     * @return List - 0 resolved Date, 1 resolved Order, 2 warning message
     */
    private ResolverResult resolveOriginalDate(ResolverResult result, InventoryTransactions inTx, double qtyToResolve, HashMap<Long, List<CloseDataHolder>> closeData, HashMap<Long, Double> fifoIn, Connection conn, EMCUserData userData) throws EMCEntityBeanException {
        double left = qtyToResolve;
        int stackCnt = 0;
        //overflow check
        if (result.getStack().isEmpty()) {
            result.getStack().add(inTx.getRecordID());
        } else {
            for (long recID : result.getStack()) {
                if (recID == inTx.getRecordID()) {
                    stackCnt++;

                }
                if (stackCnt == 10) {
                    result.addWarning("Cannot resolve transaction " + inTx.getRefNumber() + " overflow will occur.");
                    result.setSucess(false);
                    return result;
                }
            }
            result.getStack().add(inTx.getRecordID());
        }
        switch (InventoryTransactionsRefType.fromString(inTx.getRefType())) {
            case Purchase_Order: //This should only be open transactions - ordered in against PO
                result.addWarning("Found RefType POL - not expected");//thus warn if found
            //fall through
            case Receipt:
                result.setOriginalTransaction(inTx, qtyToResolve, inTx.getPhysicalDate());
                break;
            case Journal_Line:   //determine type to take action
                EMCQuery qJDef = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class);
                qJDef.addAnd("journalNumber", inTx.getRefNumber());
                qJDef.addTableAnd(BaseJournalDefinitionTable.class.getName(), "journalDefinitionId", InventoryJournalMaster.class.getName(), "journalDefinitionId");
                qJDef.addField("journalType", BaseJournalDefinitionTable.class.getName());
                List<Object[]> dList = util.exJDBCFieldReadQuery(conn, qJDef, userData);
                if (dList == null || dList.isEmpty() || dList.get(0).length == 0) {
                    result.addWarning("Failed to obtain journal Definition for " + inTx.getRefNumber());
                    result.setSucess(false);
                    break;
                }
                if (InventoryJournalTypes.fromString(dList.get(0)[0].toString()).equals(InventoryJournalTypes.TRANSFER)) {
                    //go through the transfer as with the move
                    EMCQuery qTrans = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
                    qTrans.addAnd("direction", InventoryTransactionDirection.OUT.toString());
                    qTrans.addAnd("transId", inTx.getTransId());
                    InventoryTransactions outTx = (InventoryTransactions) util.exJDBCSingleReadQuery(conn, qTrans, userData);
                    if (outTx == null) {
                        result.addWarning("Failed to find matching out transaction for in (move) " + inTx.getRefNumber());//thus warn if found
                        result.setSucess(false);
                        break;
                    }
                    result.getOutTransactions().add(outTx);
                    result = resolveMatchingOut(result, outTx, qtyToResolve, left, closeData, fifoIn, conn, userData);
                } else {
                    result.setOriginalTransaction(inTx, qtyToResolve, inTx.getPhysicalDate());
                }
                break;
            case Simple_Movement://get out matching and then find in/s
                EMCQuery qMove = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
                qMove.addAnd("direction", InventoryTransactionDirection.OUT.toString());
                qMove.addAnd("transId", inTx.getTransId());
                InventoryTransactions outTx = (InventoryTransactions) util.exJDBCSingleReadQuery(conn, qMove, userData);
                if (outTx == null) {
                    result.addWarning("Failed to find matching out transaction for in (move) " + inTx.getRefNumber());//thus warn if found
                    result.setSucess(false);
                    break;
                }
                result.getOutTransactions().add(outTx);
                result = resolveMatchingOut(result, outTx, qtyToResolve, left, closeData, fifoIn, conn, userData);
                break;
            case Credit_Note:    //returned goods
                //what about manual credit note? Manual will not have a sales order number
                EMCQuery qINM = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteMaster.class);
                qINM.addAnd("invCNNumber", inTx.getRefNumber());
                qINM.addField("salesOrderNo");//need the SO number
                qINM.addField("refInvoiceNo");//or invoice number, if it was a manual invoice
                DebtorsCreditNoteMaster refArr = (DebtorsCreditNoteMaster) util.exJDBCSingleReadQuery(conn, qINM, userData);
                if (refArr == null || (isBlank(refArr.getInvCNNumber()) && isBlank(refArr.getSalesOrderNo()))) {
                    result.addWarning("Credit note does not reference an invoice. " + inTx.getRefNumber());
                    result.setSucess(false);
                    break;
                }

                //Check for invoice ref or sales order ref.  Sales order takes preference.
                String ref = isBlank(refArr.getInvCNNumber()) ? (String) refArr.getSalesOrderNo() : (String) refArr.getInvCNNumber();

                //get dim
                EMCQuery qDim = new EMCQuery(enumQueryTypes.SELECT, InventoryDimensionTable.class);
                qDim.addAnd("recordID", inTx.getItemDimId());
                InventoryDimensionTable inDim = (InventoryDimensionTable) util.exJDBCSingleReadQuery(conn, qDim, userData);

                EMCQuery qCN = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
                qCN.addAnd("itemId", inTx.getItemId());
                qCN.addAnd("refNumber", ref);
                //dim set
                qCN.addTableAnd(InventoryDimensionTable.class.getName(), "itemDimId", InventoryTransactions.class.getName(), "recordID");
                qCN.addAnd("dimension1Id", inDim.getDimension1Id(), InventoryDimensionTable.class.getName());
                qCN.addAnd("dimension2Id", inDim.getDimension2Id(), InventoryDimensionTable.class.getName());
                qCN.addAnd("dimension3Id", inDim.getDimension3Id(), InventoryDimensionTable.class.getName());
                try {
                    if (inventParamBean.getInventoryParameters(userData).isIncludeBatchOnAgeing()) {
                        qCN.addAnd("batchId", inDim.getBatchId(), InventoryDimensionTable.class.getName());
                    }
                } catch (Exception ex) {
                    result.addWarning("Failed to get inventory Parameters. Affect resolving of credit note: " + inTx.getRefNumber());
                    result.setSucess(false);
                    break;
                }
                qCN.addAnd("itemSerialId", inDim.getItemSerialId(), InventoryDimensionTable.class.getName());
                //end dim set
                List<InventoryTransactions> outList = util.exJDBCReadQuery(conn, qCN, userData);
                if (outList == null || outList.isEmpty()) {
                    result.addWarning("No matching out transactions found for credit note  " + inTx.getRefNumber());
                    result.setSucess(false);
                    break;
                }
                for (InventoryTransactions outCTx : outList) {
                    result.getOutTransactions().add(outCTx);
                    result = resolveMatchingOut(result, outCTx, qtyToResolve, left, closeData, fifoIn, conn, userData);
                    left = result.getLeft();
                    if (util.compareDouble(left, 0) <= 0) {
                        break;//done with this for
                    }
                }
                break;
            case Works_Order:    //This should be incoming goods only
                result.setOriginalTransaction(inTx, qtyToResolve, inTx.getPhysicalDate());
                break;
            case Works_Order_Return:
                //get dim
                EMCQuery qWDim = new EMCQuery(enumQueryTypes.SELECT, InventoryDimensionTable.class);
                qWDim.addAnd("recordID", inTx.getItemDimId());
                InventoryDimensionTable inWDim = (InventoryDimensionTable) util.exJDBCSingleReadQuery(conn, qWDim, userData);
                //find the out Transaction/s
                EMCQuery qTrans = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
                qTrans.addAnd("direction", InventoryTransactionDirection.OUT.toString());
                qTrans.addAnd("transId", inTx.getTransId());
                //dim set
                qTrans.addTableAnd(InventoryDimensionTable.class.getName(), "itemDimId", InventoryTransactions.class.getName(), "recordID");
                qTrans.addAnd("dimension1Id", inWDim.getDimension1Id(), InventoryDimensionTable.class.getName());
                qTrans.addAnd("dimension2Id", inWDim.getDimension2Id(), InventoryDimensionTable.class.getName());
                qTrans.addAnd("dimension3Id", inWDim.getDimension3Id(), InventoryDimensionTable.class.getName());
                qTrans.addAnd("batchId", inWDim.getBatchId(), InventoryDimensionTable.class.getName());
                qTrans.addAnd("itemSerialId", inWDim.getItemSerialId(), InventoryDimensionTable.class.getName());
                List<InventoryTransactions> outPList = util.exJDBCReadQuery(conn, qTrans, userData);
                if (outPList == null || outPList.isEmpty()) {
                    result.addWarning("Failed to find matching out transaction for in (production return) " + inTx.getRefNumber());//thus warn if found
                    result.setSucess(false);
                    break;
                }
                for (InventoryTransactions pOutTx : outPList) {
                    result.getOutTransactions().add(pOutTx);
                    result = resolveMatchingOut(result, pOutTx, qtyToResolve, left, closeData, fifoIn, conn, userData);
                    left = result.getLeft();
                    if (util.compareDouble(left, 0) <= 0) {
                        break;//done with this for
                    }
                } //for transactions
                break;
            //fall through
            case Invoice: //Out only not expected
            case Sales_Order: //not expected ordered out open transactions only
            case Picking_List_Lines: //only out not expected
            case Return: //out only not expected
            case PlannedWO: //unused at this point
            case Journal:   //unused at this point
            default:
                result.addWarning("Reference Type not supported:" + inTx.getRefType());
                result.setSucess(false);
                break;
        }
        return result;
    }
    private void doAllocation(HashMap<Long, List<CloseDataHolder>> closeData,boolean history,List<InventoryTransactions> out,List<InventoryTransactions> in){
        //now allocate using original transaction dates
        for (InventoryTransactions outTx : out) {
            for (InventoryTransactions inTx : in) {
                if (inTx.getItemDimId() == outTx.getItemDimId() && !inTx.getClosedFlag()) {
                    //Added this check to ensure that normal ageing (without undoing allocations) still works.
                    if (history) {
                        //if (!outTx.getClosedFlag()) {
                        //inavailable >= out
                        if (util.compareDouble(inTx.getQuantity() - inTx.getClosedQty(), outTx.getQuantity() - outTx.getClosedQty()) >= 0) {
                            mapCloseData(closeData, inTx, outTx, outTx.getQuantity() - outTx.getClosedQty());
                            inTx.setClosedQty(inTx.getClosedQty() + (outTx.getQuantity() - outTx.getClosedQty()));
                            break; //consumed the out Tx

                        } else {
                            outTx.setQuantity((outTx.getQuantity() - outTx.getClosedQty()) - (inTx.getQuantity() - inTx.getClosedQty()));
                            mapCloseData(closeData, inTx, outTx, inTx.getQuantity() - inTx.getClosedQty());
                            inTx.setClosedQty(inTx.getQuantity()); //close this transaction
                            inTx.setClosedFlag(true);
                            //close this transaction
                            inTx.setClosedFlag(true);
                            //continue still need to consume the rest of this out tx
                        }
                        //}
                    } else {
                        //inavailable >= out
                        if (util.compareDouble(inTx.getQuantity() - inTx.getClosedQty(), outTx.getQuantity() - outTx.getClosedQty()) >= 0) {
                            mapCloseData(closeData, inTx, outTx, outTx.getQuantity() - outTx.getClosedQty());
                            inTx.setClosedQty(inTx.getClosedQty() + (outTx.getQuantity() - outTx.getClosedQty()));
                            outTx.setClosedFlag(true);
                            outTx.setClosedQty(outTx.getQuantity());
                            break; //consumed the out Tx

                        } else {
                            outTx.setClosedQty(outTx.getClosedQty() + (inTx.getQuantity() - inTx.getClosedQty()));
                            mapCloseData(closeData, inTx, outTx, inTx.getQuantity() - inTx.getClosedQty());
                            inTx.setClosedQty(inTx.getQuantity()); //close this transaction
                            inTx.setClosedFlag(true);
                            //continue still need to consume the rest of this out tx
                        }
                    }

                }
            }
        }
    }
    private List<InventoryTransactions> resolveInList(List<InventoryTransactions> in,HashMap<Long, List<CloseDataHolder>> closeData,HashMap<Long, Double> fifoIn,Connection conn, List messages,boolean history,List<InventoryTransactions> outList, EMCUserData userData) throws EMCEntityBeanException{
        List<InventoryTransactions> newIn = new ArrayList();
        long tempRecId = -6000000000L;
        TreeMap<Date,TreeMap<Long,List<InventoryTransactions>>> inMap = new TreeMap<Date,TreeMap<Long,List<InventoryTransactions>>>();
        for(InventoryTransactions inTx : in){
            ResolverResult resolver = resolveOriginalDate(new ResolverResult(), inTx, inTx.getQuantity() - inTx.getClosedQty(), closeData, fifoIn, conn, userData);
            messages.addAll(resolver.getWarningMessages());
            if (resolver.isSucess() && resolver.getOriginalTransactions().size() > 0){
                for(ResolverData rData : resolver.getOriginalTransactions()){
                    InventoryTransactions curResolved = rData.getOriginalTransaction();
                    TreeMap<Long,List<InventoryTransactions>> transMap = inMap.get(curResolved.getPhysicalDate());
                    if(transMap == null){
                        transMap = new TreeMap();
                        inMap.put(curResolved.getPhysicalDate(), transMap);
                    }
                    //transaction may have been used to close many thus could show up more than once
                    List<InventoryTransactions> loaded = transMap.get(curResolved.getRecordID());
                    if(loaded==null){
                        loaded = new ArrayList();
                        InventoryTransactions temp = new InventoryTransactions();
                        tempRecId--;
                        temp.setRecordID(tempRecId);
                        temp.setQuantity(rData.getQtyResolved());
                        temp.setCost(curResolved.getCost()/curResolved.getQuantity()*rData.getQtyResolved());
                        temp.setItemDimId(inTx.getItemDimId());
                        temp.setItemId(inTx.getItemId());
                        temp.setPhysicalDate(curResolved.getPhysicalDate());
                        temp.setRefNumber(curResolved.getRefNumber());
                        temp.setRefType(curResolved.getRefType());
                        loaded.add(temp);
                        transMap.put(curResolved.getRecordID(),loaded);
                    }else{
                        boolean found = false;
                        for(InventoryTransactions aLoaded:loaded){
                            if(aLoaded.getItemDimId()==inTx.getItemDimId()){
                                aLoaded.setCost(aLoaded.getCost() + curResolved.getCost()/curResolved.getQuantity()*rData.getQtyResolved());
                                aLoaded.setQuantity(aLoaded.getQuantity()+rData.getQtyResolved());
                                found = true;
                                break; //done here
                            }
                        }
                        if(!found){
                            InventoryTransactions temp = new InventoryTransactions();
                            tempRecId--;
                            temp.setRecordID(tempRecId);
                            temp.setQuantity(rData.getQtyResolved());
                            temp.setCost(curResolved.getCost()/curResolved.getQuantity()*rData.getQtyResolved());
                            temp.setItemDimId(inTx.getItemDimId());
                            temp.setItemId(inTx.getItemId());
                            temp.setPhysicalDate(curResolved.getPhysicalDate());
                            temp.setRefNumber(curResolved.getRefNumber());
                            temp.setRefType(curResolved.getRefType());
                            loaded.add(temp);
                        }
                    }
                }
            }else{
                //added to resolve buddy ins and outs first
                List tempIn = new ArrayList();
                List tempOut = new ArrayList();
                tempIn.add(inTx);
                for(InventoryTransactions curResOut:resolver.getOutTransactions()){
                    for(InventoryTransactions curOut:outList){
                        if(curResOut.getRecordID()==curOut.getRecordID()){
                            tempOut.add(curOut);
                            break;//got it
                        }
                    }
                }
                doAllocation(closeData, history,tempIn , tempOut);
                //added to resolve buddy ins and outs first
                TreeMap<Long,List<InventoryTransactions>> transMap = inMap.get(inTx.getPhysicalDate());
                if(transMap == null){
                    transMap = new TreeMap();
                    inMap.put(inTx.getPhysicalDate(), transMap);
                }
                List<InventoryTransactions> loaded = transMap.get(inTx.getRecordID());
                if(loaded==null){
                    loaded = new ArrayList();
                    loaded.add(inTx);
                    transMap.put(inTx.getRecordID(),loaded);
                }else{
                    loaded.add(inTx);
                    messages.add("In Transaction added to List not expected");
                }
            }

        }

        util.detachEntities(userData);//just to be safe we may have loaded some during resolving
        //load new In this will contain all resolved transactions
        for(Date curDate:inMap.keySet()){
            for(Long recId:inMap.get(curDate).keySet()){
                for(InventoryTransactions T:inMap.get(curDate).get(recId)){
                    newIn.add(T);
                }
            }
        }
        return newIn;
    }
    private List<InventoryAgeingDS> populateBinData(List<InventoryTransactions> out, List<InventoryTransactions> in, InventoryAgeingDS curDataTemplate, List<Integer> maxBinSize, Date physicalDate, boolean useCurrentCost, boolean history, AgeingReportTypes type, List messages, Date mustBeOlderThan, Connection conn, EMCUserData userData) throws EMCEntityBeanException {
        //Do not update transactions.
        //keep track of updates to allow resolving of original receive date
        //the map is outTx.RecordId - List of Ins used to close this out + qty closed.
        HashMap<Long, List<CloseDataHolder>> closeData = new HashMap<Long, List<CloseDataHolder>>();
        HashMap<Long, Double> fifoIn = new HashMap<Long, Double>();//keep track of in available from a Fifo viewpoint in the case of multiple in
        //Resolve before we allocate
        List<InventoryTransactions> newIn = resolveInList(in, closeData, fifoIn, conn, messages,history,out, userData);
        //allocate
        doAllocation(closeData, history, out, newIn);
        //resolve again for ageing purpose
        fifoIn = new HashMap<Long, Double>();
        newIn = resolveInList(newIn, closeData, fifoIn, conn, messages,history,new ArrayList(), userData);//no point in trying to allocate again thus out new arrylist
        List<InventoryAgeingDS> retList = new ArrayList<InventoryAgeingDS>();

        InventoryAgeingDS curData = null;
        if (!type.equals(AgeingReportTypes.FULL_DETAIL)) {
            curData = curDataTemplate.copy();
        }
        //now place in bins
        for (InventoryTransactions inTx : newIn) {
            Double currentCost = null;
            if (!inTx.getClosedFlag() && util.compareDouble(inTx.getQuantity() - inTx.getClosedQty(), 0) > 0) {
                if (type.equals(AgeingReportTypes.FULL_DETAIL)) {
                    if (mustBeOlderThan != null && inTx.getPhysicalDate() != null) {
                        if (mustBeOlderThan.before(inTx.getPhysicalDate())) {
                            continue;
                        }
                    }
                    InventoryDimensionTable dims = dimensionBean.getDimensionRecord(inTx.getItemDimId(), userData);
                    curData = curDataTemplate.copy();
                    curData.setWarehouse(dims.getWarehouseId());
                    curData.setLocation(dims.getLocationId());
                    curData.setBatch(dims.getBatchId());
                    curData.setSerial(dims.getItemSerialId());
                    curData.setTransDate(isBlank(inTx.getPhysicalDate()) ? null : Functions.date2String(inTx.getPhysicalDate()));
                    curData.setTransRef(inTx.getRefNumber());
                }
                
                long days = dateHandler.getDateDiffInDays(inTx.getPhysicalDate(), physicalDate, userData);
                int binNumber = 0;
                for (int j = 0; j <
                        maxBinSize.size(); j++) {
                    binNumber = j;
                    if (maxBinSize.get(j) >= days) {
                        break;
                    }

                }
                switch (binNumber) {
                    case 0:
                        curData.setBin1Qty(curData.getBin1Qty() + inTx.getQuantity() - inTx.getClosedQty());

                        if (useCurrentCost) {
                            if (currentCost == null) {
                                currentCost = getCurrentCost(inTx, userData);
                            }

                            curData.setBin1Value(curData.getBin1Value() + ((inTx.getQuantity() - inTx.getClosedQty()) * currentCost));
                        } else {
                            curData.setBin1Value(curData.getBin1Value() + (inTx.getQuantity() - inTx.getClosedQty()) * (inTx.getCost() / inTx.getQuantity()));
                        }

                        break;
                    case 1:
                        curData.setBin2Qty(curData.getBin2Qty() + inTx.getQuantity() - inTx.getClosedQty());

                        if (useCurrentCost) {
                            if (currentCost == null) {
                                currentCost = getCurrentCost(inTx, userData);
                            }

                            curData.setBin2Value(curData.getBin2Value() + ((inTx.getQuantity() - inTx.getClosedQty()) * currentCost));
                        } else {
                            curData.setBin2Value(curData.getBin2Value() + (inTx.getQuantity() - inTx.getClosedQty()) * (inTx.getCost() / inTx.getQuantity()));
                        }

                        break;
                    case 2:
                        curData.setBin3Qty(curData.getBin3Qty() + inTx.getQuantity() - inTx.getClosedQty());

                        if (useCurrentCost) {
                            if (currentCost == null) {
                                currentCost = getCurrentCost(inTx, userData);
                            }

                            curData.setBin3Value(curData.getBin3Value() + ((inTx.getQuantity() - inTx.getClosedQty()) * currentCost));
                        } else {
                            curData.setBin3Value(curData.getBin3Value() + (inTx.getQuantity() - inTx.getClosedQty()) * (inTx.getCost() / inTx.getQuantity()));
                        }

                        break;
                    case 3:
                        curData.setBin4Qty(curData.getBin4Qty() + inTx.getQuantity() - inTx.getClosedQty());

                        if (useCurrentCost) {
                            if (currentCost == null) {
                                currentCost = getCurrentCost(inTx, userData);
                            }

                            curData.setBin4Value(curData.getBin4Value() + ((inTx.getQuantity() - inTx.getClosedQty()) * currentCost));
                        } else {
                            curData.setBin4Value(curData.getBin4Value() + (inTx.getQuantity() - inTx.getClosedQty()) * (inTx.getCost() / inTx.getQuantity()));
                        }

                        break;
                    case 4:
                        curData.setBin5Qty(curData.getBin5Qty() + inTx.getQuantity() - inTx.getClosedQty());

                        if (useCurrentCost) {
                            if (currentCost == null) {
                                currentCost = getCurrentCost(inTx, userData);
                            }

                            curData.setBin5Value(curData.getBin5Value() + ((inTx.getQuantity() - inTx.getClosedQty()) * currentCost));
                        } else {
                            curData.setBin5Value(curData.getBin5Value() + (inTx.getQuantity() - inTx.getClosedQty()) * (inTx.getCost() / inTx.getQuantity()));
                        }

                        break;
                    case 5:
                        curData.setBin6Qty(curData.getBin6Qty() + inTx.getQuantity() - inTx.getClosedQty());

                        if (useCurrentCost) {
                            if (currentCost == null) {
                                currentCost = getCurrentCost(inTx, userData);
                            }

                            curData.setBin6Value(curData.getBin6Value() + ((inTx.getQuantity() - inTx.getClosedQty()) * currentCost));
                        } else {
                            curData.setBin6Value(curData.getBin6Value() + (inTx.getQuantity() - inTx.getClosedQty()) * (inTx.getCost() / inTx.getQuantity()));
                        }

                        break;
                    default:
                        break;
                }

                if (type.equals(AgeingReportTypes.FULL_DETAIL)) {
                    retList.add(curData);
                }
              
            }
        }

        if (!type.equals(AgeingReportTypes.FULL_DETAIL)) {
            retList.add(curData);
        }

        return retList;
    }

    private List<InventoryAgeingDS> getBinData(EMCQuery quIn, InventoryAgeingDS curData, List<Integer> maxBinSize, Date physicalDate, boolean useCurrentCost, AgeingReportTypes type, List messages, Date mustBeOlderThan, Connection conn, EMCUserData userData) throws EMCEntityBeanException {
        //clear previous data
        EMCQuery qu = quIn.copyQuery();
        qu.setEntityClassName(InventoryTransactions.class);
        qu.clearFieldList();
        qu.removeAnd("itemId", InventoryItemMaster.class.getName());
        qu.removeAnd("direction", InventoryTransactions.class.getName());
        //getInTx
        qu.addAnd("direction", InventoryTransactionDirection.IN.toString(), InventoryTransactions.class.getName());
        qu.addAnd("itemId", curData.getItemId(), InventoryItemMaster.class.getName());
        if (!isBlank(mustBeOlderThan)) {
            qu.addAnd("physicalDate", mustBeOlderThan, InventoryTransactions.class.getName(), EMCQueryConditions.LESS_THAN_EQ);
        }
        qu.addTableAnd(InventoryTransactions.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");

        if (type.getId() >= AgeingReportTypes.DETAIL.getId()) {
            //clear previous data
            qu.removeAnd("dimension1Id", InventoryDimensionTable.class.getName());
            qu.removeAnd("dimension2Id", InventoryDimensionTable.class.getName());
            qu.removeAnd("dimension3Id", InventoryDimensionTable.class.getName());
            //getInTx
            qu.addAnd("dimension1Id", curData.getDimension1(), InventoryDimensionTable.class.getName());
            qu.addAnd("dimension2Id", curData.getDimension2(), InventoryDimensionTable.class.getName());
            qu.addAnd("dimension3Id", curData.getDimension3(), InventoryDimensionTable.class.getName());
        }
        if (type.getId() >= AgeingReportTypes.FULL_DETAIL.getId()) {
//            //clear previous data
//            qu.removeAnd("warehouseId", InventoryDimensionTable.class.getName());
//            qu.removeAnd("locationId", InventoryDimensionTable.class.getName());
//            qu.removeAnd("batchId", InventoryDimensionTable.class.getName());
//            qu.removeAnd("itemSerialId", InventoryDimensionTable.class.getName());
//            //getInTx
//            qu.addAnd("warehouseId", curData.getWarehouse(), InventoryDimensionTable.class.getName());
//            qu.addAnd("locationId", curData.getLocation(), InventoryDimensionTable.class.getName());
//            qu.addAnd("batchId", curData.getBatch(), InventoryDimensionTable.class.getName());
//            qu.addAnd("itemSerialId", curData.getSerial(), InventoryDimensionTable.class.getName());
        }
        List<InventoryTransactions> in = util.exJDBCReadQuery(conn, qu, userData);
        //getOutTx

        if (!isBlank(mustBeOlderThan)) {
            qu.removeAnd("physicalDate", InventoryTransactions.class.getName());
            qu.addAnd("physicalDate", null, InventoryTransactions.class.getName(), EMCQueryConditions.IS_NOT_NULL);
        }
        qu.removeAnd("direction", InventoryTransactions.class.getName());
        qu.addAnd("direction", InventoryTransactionDirection.OUT.toString(), InventoryTransactions.class.getName());
        List<InventoryTransactions> out = util.exJDBCReadQuery(conn, qu, userData);

        return populateBinData(out, in, curData, maxBinSize, physicalDate, useCurrentCost, false, type, messages, mustBeOlderThan, conn, userData);
    }

    /** Returns the current cost for the item on the specified transaction. */
    private double getCurrentCost(InventoryTransactions trans, EMCUserData userData) {
        return itemBean.getCostPrice(trans.getItemId(), trans.getItemDimId(), userData);
    }

    private Map<String, BigDecimal> fetchOnHandReserved(String itemId, Connection conn, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        query.addAnd("itemId", itemId);

        query.addField("dimension1");//0
        query.addField("dimension2");//1
        query.addField("dimension3");//2
        query.addField("warehouse");//3
        query.addField("location");//4
        query.addField("batch");//5
        query.addField("serialNo");//6
        query.addField("pallet");//7
        query.addFieldAggregateFunction("physicalReserved", "SUM");//8

        query.addGroupBy("dimension1");
        query.addGroupBy("dimension2");
        query.addGroupBy("dimension3");
        query.addGroupBy("warehouse");
        query.addGroupBy("location");
        query.addGroupBy("batch");
        query.addGroupBy("serialNo");
        query.addGroupBy("pallet");

        List<Object[]> reservedList = util.exJDBCFieldReadQuery(conn, query, userData);

        Map<String, BigDecimal> reservedMap = new HashMap<String, BigDecimal>();
        StringBuilder reservedKey;

        for (Object[] reserved : reservedList) {
            if (util.compareDouble((Double) reserved[8], 0) > 0) {
                reservedKey = new StringBuilder();
                reservedKey.append((String) (isBlank(reserved[0]) ? null : reserved[0]));
                reservedKey.append((String) (isBlank(reserved[1]) ? null : reserved[1]));
                reservedKey.append((String) (isBlank(reserved[2]) ? null : reserved[2]));
                reservedKey.append((String) (isBlank(reserved[3]) ? null : reserved[3]));
                reservedKey.append((String) (isBlank(reserved[4]) ? null : reserved[4]));
                reservedKey.append((String) (isBlank(reserved[5]) ? null : reserved[5]));
                reservedKey.append((String) (isBlank(reserved[6]) ? null : reserved[6]));
                reservedKey.append((String) (isBlank(reserved[7]) ? null : reserved[7]));

                reservedMap.put(reservedKey.toString(), util.getBigDecimal((Double) reserved[8]));
            }
        }

        return reservedMap;
    }

    private boolean subtractReservedStock(InventoryAgeingDS curAgeProcess, Map<String, BigDecimal> reservedMap, Connection conn, EMCUserData userData) {
        if (reservedMap != null) {
            StringBuilder reservedKey = new StringBuilder();
            reservedKey.append((String) (isBlank(curAgeProcess.getDimension1()) ? null : curAgeProcess.getDimension1()));
            reservedKey.append((String) (isBlank(curAgeProcess.getDimension2()) ? null : curAgeProcess.getDimension2()));
            reservedKey.append((String) (isBlank(curAgeProcess.getDimension3()) ? null : curAgeProcess.getDimension3()));
            reservedKey.append((String) (isBlank(curAgeProcess.getWarehouse()) ? null : curAgeProcess.getWarehouse()));
            reservedKey.append((String) (isBlank(curAgeProcess.getLocation()) ? null : curAgeProcess.getLocation()));
            reservedKey.append((String) (isBlank(curAgeProcess.getBatch()) ? null : curAgeProcess.getBatch()));
            reservedKey.append((String) (isBlank(curAgeProcess.getSerial()) ? null : curAgeProcess.getSerial()));
            reservedKey.append((String) null);

            BigDecimal reserved = reservedMap.get(reservedKey.toString());
            if (reserved != null) {
                if (reserved.compareTo(BigDecimal.ZERO) > 0 && util.compareDouble(curAgeProcess.getBin6Qty(), 0) != 0) {
                    if (reserved.compareTo(util.getBigDecimal(curAgeProcess.getBin6Qty())) > 0) {
                        reserved = reserved.subtract(util.getBigDecimal(curAgeProcess.getBin6Qty()));
                        curAgeProcess.setBin6Qty(0d);
                    } else {
                        curAgeProcess.setBin6Qty(util.getBigDecimal(curAgeProcess.getBin6Qty()).subtract(reserved).doubleValue());
                        reserved = BigDecimal.ZERO;
                    }
                }
                if (reserved.compareTo(BigDecimal.ZERO) > 0 && util.compareDouble(curAgeProcess.getBin5Qty(), 0) != 0) {
                    if (reserved.compareTo(util.getBigDecimal(curAgeProcess.getBin5Qty())) > 0) {
                        reserved = reserved.subtract(util.getBigDecimal(curAgeProcess.getBin5Qty()));
                        curAgeProcess.setBin5Qty(0d);
                    } else {
                        curAgeProcess.setBin5Qty(util.getBigDecimal(curAgeProcess.getBin5Qty()).subtract(reserved).doubleValue());
                        reserved = BigDecimal.ZERO;
                    }
                }
                if (reserved.compareTo(BigDecimal.ZERO) > 0 && util.compareDouble(curAgeProcess.getBin4Qty(), 0) != 0) {
                    if (reserved.compareTo(util.getBigDecimal(curAgeProcess.getBin4Qty())) > 0) {
                        reserved = reserved.subtract(util.getBigDecimal(curAgeProcess.getBin4Qty()));
                        curAgeProcess.setBin4Qty(0d);
                    } else {
                        curAgeProcess.setBin4Qty(util.getBigDecimal(curAgeProcess.getBin4Qty()).subtract(reserved).doubleValue());
                        reserved = BigDecimal.ZERO;
                    }
                }
                if (reserved.compareTo(BigDecimal.ZERO) > 0 && util.compareDouble(curAgeProcess.getBin3Qty(), 0) != 0) {
                    if (reserved.compareTo(util.getBigDecimal(curAgeProcess.getBin3Qty())) > 0) {
                        reserved = reserved.subtract(util.getBigDecimal(curAgeProcess.getBin3Qty()));
                        curAgeProcess.setBin3Qty(0d);
                    } else {
                        curAgeProcess.setBin3Qty(util.getBigDecimal(curAgeProcess.getBin3Qty()).subtract(reserved).doubleValue());
                        reserved = BigDecimal.ZERO;
                    }
                }
                if (reserved.compareTo(BigDecimal.ZERO) > 0 && util.compareDouble(curAgeProcess.getBin2Qty(), 0) != 0) {
                    if (reserved.compareTo(util.getBigDecimal(curAgeProcess.getBin2Qty())) > 0) {
                        reserved = reserved.subtract(util.getBigDecimal(curAgeProcess.getBin2Qty()));
                        curAgeProcess.setBin2Qty(0d);
                    } else {
                        curAgeProcess.setBin2Qty(util.getBigDecimal(curAgeProcess.getBin2Qty()).subtract(reserved).doubleValue());
                        reserved = BigDecimal.ZERO;
                    }
                }
                if (reserved.compareTo(BigDecimal.ZERO) > 0 && util.compareDouble(curAgeProcess.getBin1Qty(), 0) != 0) {
                    if (reserved.compareTo(util.getBigDecimal(curAgeProcess.getBin1Qty())) > 0) {
                        reserved = reserved.subtract(util.getBigDecimal(curAgeProcess.getBin1Qty()));
                        curAgeProcess.setBin1Qty(0d);
                    } else {
                        curAgeProcess.setBin1Qty(util.getBigDecimal(curAgeProcess.getBin1Qty()).subtract(reserved).doubleValue());
                        reserved = BigDecimal.ZERO;
                    }
                }

                if (util.compareDouble(curAgeProcess.getBin1Qty(), 0) != 0 ||
                        util.compareDouble(curAgeProcess.getBin2Qty(), 0) != 0 ||
                        util.compareDouble(curAgeProcess.getBin3Qty(), 0) != 0 ||
                        util.compareDouble(curAgeProcess.getBin4Qty(), 0) != 0 ||
                        util.compareDouble(curAgeProcess.getBin5Qty(), 0) != 0 ||
                        util.compareDouble(curAgeProcess.getBin6Qty(), 0) != 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}



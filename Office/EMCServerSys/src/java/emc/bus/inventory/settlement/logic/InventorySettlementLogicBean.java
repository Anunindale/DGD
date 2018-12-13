/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.settlement.logic;

import emc.bus.base.BaseEmployeeLocal;
import emc.bus.inventory.settlement.InventorySettlementLocal;
import emc.bus.inventory.transactions.InventorySummaryLocal;
import emc.bus.inventory.transactions.InventoryTransactionsLocal;
import emc.bus.workflow.WFActivityLocal;
import emc.entity.inventory.stocksettlement.InventoryStockSettlement;
import emc.entity.inventory.stocksettlement.InventoryStockSettlementHistory;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.workflow.WorkFlowActivity;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.settlement.SettlementStatus;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.enums.inventory.transactions.InventoryTransactionStatus;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.xml.EMCXMLHandler;
import emc.functions.xml.external.EMCExternalWSXMLException;
import emc.functions.xml.external.EMCExternalWSXMLHandler;
import emc.server.datehandler.EMCDateHandlerLocal;
import emc.server.filehandeler.EMCFileHandlerLocal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author rico
 */
@Stateless
public class InventorySettlementLogicBean extends EMCBusinessBean implements InventorySettlementLogicLocal {
    
    @EJB
    private InventoryTransactionsLocal transBean;
    @EJB
    private InventorySummaryLocal sumBean;
    @EJB
    private InventorySettlementLocal settlementBean;
    @EJB
    private EMCFileHandlerLocal fileBean;
    @EJB
    private WFActivityLocal activityBean;
    @EJB
    private EMCDateHandlerLocal dateBean;
    @EJB
    private BaseEmployeeLocal employeeBean;

    /**
     * Does stock close routine
     *
     * @param sm
     * @param userData
     * @throws EMCEntityBeanException
     */
    public void doStockClose(InventoryStockSettlement sm, EMCUserData userData) throws EMCEntityBeanException {
        switch (SettlementStatus.fromString(sm.getRunStatus())) {
            case COMPLETED:
                logMessage(Level.SEVERE, "This closure has been run.", userData);
                break;
            case CAPTURED:
            case ROLLBACK:
            case PARTIAL:
                transBean.setStockCloseDate(sm.getEndDate(), userData); //stop other users from posting in this period
                userData.setUserData(7, "STOCKCLOSE_IN_PROGRESS");//this is to allow the stock close to post in this period
                detailStockClose(sm, userData);
                break;
        }
    }
    
    private void detailStockClose(InventoryStockSettlement sm, EMCUserData userData) throws EMCEntityBeanException {
        //storage space for data
        HashMap<Long, InventoryTransactions> outToUpdate = new HashMap();
        HashMap<Long, InventoryTransactions> inToUpdate = new HashMap();
        List<InventoryStockSettlementHistory> histToInsert = new ArrayList();
        HashMap<Long, InventorySummary> inSumToUpdate = new HashMap();
        HashMap<Long, InventorySummary> outSumToUpdate = new HashMap();
        HashMap<Long, InventorySummary> sumToDelete = new HashMap();
        HashMap<Long, String> errorList = new HashMap();

        //used to store XML
        EMCExternalWSXMLHandler xmlHandler = new EMCExternalWSXMLHandler();
        EMCQuery outItems = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
        //getItems.addField("itemId");
        //getItems.addAnd("physicalDate", Functions.date2String(sm.getStartDate(), "yyyy-MM-dd"),EMCQueryConditions.GREATER_THAN_EQ);
        outItems.addAnd("physicalDate", Functions.date2String(sm.getEndDate(), "yyyy-MM-dd"), EMCQueryConditions.LESS_THAN_EQ);
        outItems.addAnd("closedFlag", false);
        outItems.addAnd("direction", InventoryTransactionDirection.OUT.toString());
        
        outItems.addTableAnd(InventorySummary.class.getName(), "recordID", InventoryTransactions.class.getName(), "inventoryTransRef");
        outItems.addTableAsField(InventoryTransactions.class.getName());
        outItems.addTableAsField(InventorySummary.class.getName());
        outItems.addOrderBy("itemId");
        outItems.addOrderBy("itemDimId");
        outItems.addOrderBy("physicalDate");
        
        List<Object[]> outAll = util.executeGeneralSelectQuery(outItems, userData);
        util.detachEntities(userData);
        System.out.println("Number of trans:" + outAll.size());
        EMCQuery inSelect = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
        //inSelect.addAnd("physicalDate", Functions.date2String(sm.getStartDate(), "yyyy-MM-dd"),EMCQueryConditions.GREATER_THAN_EQ);
        inSelect.addAnd("physicalDate", Functions.date2String(sm.getEndDate(), "yyyy-MM-dd"), EMCQueryConditions.LESS_THAN_EQ);
        inSelect.addAnd("closedFlag", false);
        inSelect.addOrderBy("itemDimId");
        inSelect.addOrderBy("physicalDate");
        inSelect.addAnd("direction", InventoryTransactionDirection.IN.toString());
        inSelect.addTableAnd(InventorySummary.class.getName(), "recordID", InventoryTransactions.class.getName(), "inventoryTransRef");
        inSelect.addTableAsField(InventoryTransactions.class.getName());
        inSelect.addTableAsField(InventorySummary.class.getName());
        //sum check
        EMCQuery sumGrpQ = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        sumGrpQ.addFieldAggregateFunction("physicalTotal", "SUM");
        sumGrpQ.addField("itemId");
        sumGrpQ.addField("itemDimId");
        sumGrpQ.addGroupBy("itemId");
        sumGrpQ.addGroupBy("itemDimId");
        List sumBefore = util.executeGeneralSelectQuery(sumGrpQ, userData);
        util.detachEntities(userData);
        int lineCount = 1;
        int totalCount = outAll.size();
        String currentItemId = "";
        List<Object[]> in = new ArrayList();
        if (outAll.size() == 0) {
            this.logMessage(Level.INFO, "No records found to close for the date range.", userData);
        }
        for (int j = 0; j < outAll.size(); j++) {
            InventoryTransactions outTx = (InventoryTransactions) outAll.get(j)[0];
            InventorySummary outSum = (InventorySummary) outAll.get(j)[1];
            //sanity check on out
            if (util.compareDouble(outTx.getQuantity() - outTx.getClosedQty(), -1 * outSum.getPhysicalTotal()) != 0
                    || outTx.getStatus().equals(InventoryTransactionStatus.ORDERED.toString())
                    || outTx.getStatus().equals(InventoryTransactionStatus.RESERVED.toString())
                    || util.compareDouble(outTx.getClosedQty(), outTx.getQuantity()) > 0) {
                //this transaction and summary is not in sync
                //or its status is ordered or reserved and should not be in this list
                //or it is over closed
                //cannot use it - flag it as error
                double avail = outTx.getQuantity() - outTx.getClosedQty();
                errorList.put(outTx.getRecordID(), "OUT Transaction: RecordId = " + outTx.getRecordID() + ", status " + outTx.getStatus() + ", close available " + avail + ", sum qty " + outSum.getPhysicalTotal());
                continue;
            }
            if (!outTx.getItemId().equals(currentItemId)) {
                currentItemId = outTx.getItemId();
                inSelect.removeAnd("itemId");
                inSelect.addAnd("itemId", outTx.getItemId());
                in = util.executeGeneralSelectQuery(inSelect, userData);
                util.detachEntities(userData);
                
            }
            //loop through out
            for (int k = 0; k < in.size(); k++) {
                InventoryTransactions inTx = (InventoryTransactions) in.get(k)[0];
                InventorySummary inSum = (InventorySummary) in.get(k)[1];
                if (!inTx.getClosedFlag() && inTx.getItemDimId() == outTx.getItemDimId() && inTx.getItemId().equals(outTx.getItemId())) {
                    //sanity check on in
                    if (util.compareDouble(inTx.getQuantity() - inTx.getClosedQty(), inSum.getPhysicalTotal()) != 0
                            || inTx.getStatus().equals(InventoryTransactionStatus.ORDERED.toString())
                            || inTx.getStatus().equals(InventoryTransactionStatus.RESERVED.toString())
                            || util.compareDouble(inTx.getClosedQty(), inTx.getQuantity()) > 0) {
                        //this transaction and summary is not in sync
                        //or its status is ordered or reserved and should not be in this list
                        //or it is over closed
                        //cannot use it - flag it as error
                        double avail = inTx.getQuantity() - inTx.getClosedQty();
                        errorList.put(inTx.getRecordID(), "IN Transaction: RecordId = " + inTx.getRecordID() + ", status " + inTx.getStatus() + ", close available " + avail + ", sum qty " + inSum.getPhysicalTotal());
                        continue;
                    }
                    //in available >= out e.g. out is settled in only if =
                    if (util.compareDouble(inTx.getQuantity() - inTx.getClosedQty(), outTx.getQuantity() - outTx.getClosedQty()) >= 0) {
                        double closeAvailable = outTx.getQuantity() - outTx.getClosedQty();
                        InventoryStockSettlementHistory hist = new InventoryStockSettlementHistory();
                        hist.setQtyClosed(closeAvailable);
                        //out
                        outTx.setClosedFlag(true);
                        outTx.setClosedQty(outTx.getQuantity());
                        outTx.setClosedDate(sm.getEndDate());
                        outTx = updateCost(inTx, outTx, closeAvailable, userData);
                        try {
                            //delete Summary for this out
                            hist.setOutXmlSummary(xmlHandler.parseObject(outSum));
                        } catch (EMCExternalWSXMLException ex) {
                            throw new EMCEntityBeanException(ex);
                        }
                        sumToDelete.put(outSum.getRecordID(), outSum);
                        outSumToUpdate.remove(outSum.getRecordID());// no need to update it will be deleted
                        //in qty is also closed
                        inTx.setClosedQty(inTx.getClosedQty() + closeAvailable);
                        if (util.compareDouble(inTx.getQuantity() - inTx.getClosedQty(), 0d) == 0) {
                            inTx.setClosedFlag(true);
                            inTx.setClosedDate(sm.getEndDate());
                            inTx.setFifoCost(inTx.getCost());
                            hist.setWasInClosed(true);
                            //delete this in summary
                            try {
                                hist.setInXmlSummary(xmlHandler.parseObject(inSum));
                                sumToDelete.put(inSum.getRecordID(), inSum); //delete in summary
                            } catch (EMCExternalWSXMLException ex) {
                                throw new EMCEntityBeanException(ex);
                            }
                        } else {
                            //need to update the summary
                            inSum.setPhysicalAvailable(inSum.getPhysicalAvailable() + outSum.getPhysicalAvailable());
                            inSum.setPhysicalTotal(inSum.getPhysicalTotal() + outSum.getPhysicalTotal());
                            inSum.setQcAvailable(inSum.getQcAvailable() + outSum.getQcAvailable());
                            inSum.setRecAvailable(inSum.getRecAvailable() + outSum.getRecAvailable());
                            inSumToUpdate.put(inSum.getRecordID(), inSum); //update in summary with reduced values
                        }
                        //set history
                        hist.setInTxRecordId(inTx.getRecordID());
                        hist.setLineNumber(lineCount);
                        lineCount++;
                        hist.setOutTxUsedInSettlement(outTx.getRecordID());
                        hist.setSettlementId(sm.getSettlementId());
                        hist.setWasOutClosed(true);
                        //update tx
                        inToUpdate.put(inTx.getRecordID(), inTx);
                        outToUpdate.put(outTx.getRecordID(), outTx);
                        histToInsert.add(hist);
                        k = in.size(); //consumed the out Tx break
                    } else { //in is settled
                        double closeAvailable = inTx.getQuantity() - inTx.getClosedQty();
                        outTx.setClosedQty(outTx.getClosedQty() + closeAvailable);
                        //update history
                        InventoryStockSettlementHistory hist = new InventoryStockSettlementHistory();
                        hist.setInTxRecordId(inTx.getRecordID());
                        hist.setQtyClosed(closeAvailable);
                        hist.setLineNumber(lineCount);
                        lineCount++;
                        hist.setOutTxUsedInSettlement(outTx.getRecordID());
                        hist.setSettlementId(sm.getSettlementId());
                        hist.setWasOutClosed(false);
                        hist.setWasInClosed(true);
                        //update cost
                        outTx = updateCost(inTx, outTx, inTx.getQuantity() - inTx.getClosedQty(), userData);
                        inTx.setClosedQty(inTx.getQuantity()); //close this transaction
                        inTx.setClosedFlag(true);
                        inTx.setClosedDate(sm.getEndDate());

                        //update out tx summary
                        outSum.setPhysicalAvailable(outSum.getPhysicalAvailable() + inSum.getPhysicalAvailable());
                        outSum.setPhysicalTotal(outSum.getPhysicalTotal() + inSum.getPhysicalTotal());
                        outSum.setRecAvailable(outSum.getRecAvailable() + inSum.getRecAvailable());
                        outSum.setQcAvailable(outSum.getQcAvailable() + inSum.getQcAvailable());
                        outSumToUpdate.put(outSum.getRecordID(), outSum);
                        try {
                            hist.setInXmlSummary(xmlHandler.parseObject(inSum));
                        } catch (EMCExternalWSXMLException ex) {
                            throw new EMCEntityBeanException(ex);
                        }
                        //delete Summary for this in
                        sumToDelete.put(inSum.getRecordID(), inSum);
                        inSumToUpdate.remove(inSum.getRecordID()); //remove from update list, this will be deleted
                        outToUpdate.put(outTx.getRecordID(), outTx);
                        inToUpdate.put(inTx.getRecordID(), inTx);
                        histToInsert.add(hist);
                        //continue still need to consume the rest of this out tx
                    }
                    
                }
                
            }
            
            if ((j + 1) % 1000 == 0) {
                System.out.println("Percentage Done:" + Double.parseDouble(Integer.toString(j)) / Double.parseDouble(Integer.toString(totalCount)) * 100d);
            }
            
        }
        //update in
        int count = 0;
        totalCount = inToUpdate.keySet().size();
        for (Long transRef : inToUpdate.keySet()) {
            InventoryTransactions cur = inToUpdate.get(transRef);
            //more sanity checks
            transactionCloseCheck(cur, userData);
            transBean.update(cur, userData);
            if (count % 20 == 0) {
                util.flushEntity(userData);
                util.detachEntities(userData);
            }
            count++;
            if ((count + 1) % 1000 == 0) {
                System.out.println("Percentage Done Update In:" + Double.parseDouble(Integer.toString(count)) / Double.parseDouble(Integer.toString(totalCount)) * 100d);
            }
        }
        //update out
        count = 0;
        totalCount = outToUpdate.keySet().size();
        for (Long transRef : outToUpdate.keySet()) {
            InventoryTransactions cur = outToUpdate.get(transRef);
            transactionCloseCheck(cur, userData);
            transBean.update(cur, userData);
            if (count % 20 == 0) {
                util.flushEntity(userData);
                util.detachEntities(userData);
            }
            count++;
            if ((count + 1) % 1000 == 0) {
                System.out.println("Percentage Done Update Out:" + Double.parseDouble(Integer.toString(count)) / Double.parseDouble(Integer.toString(totalCount)) * 100d);
            }
        }
        //update sum In
        count = 0;
        totalCount = inSumToUpdate.keySet().size();
        for (Long transRef : inSumToUpdate.keySet()) {
            InventorySummary cur = inSumToUpdate.get(transRef);
            sumBean.update(cur, userData);
            if (count % 20 == 0) {
                util.flushEntity(userData);
                util.detachEntities(userData);
            }
            count++;
            if ((count + 1) % 1000 == 0) {
                System.out.println("Percentage Done Update Sum In:" + Double.parseDouble(Integer.toString(count)) / Double.parseDouble(Integer.toString(totalCount)) * 100d);
            }
        }
        //update sum In
        count = 0;
        totalCount = outSumToUpdate.keySet().size();
        for (Long transRef : outSumToUpdate.keySet()) {
            InventorySummary cur = outSumToUpdate.get(transRef);
            sumBean.update(cur, userData);
            if (count % 20 == 0) {
                util.flushEntity(userData);
                util.detachEntities(userData);
            }
            count++;
            if ((count + 1) % 1000 == 0) {
                System.out.println("Percentage Done Update Sum Out:" + Double.parseDouble(Integer.toString(count)) / Double.parseDouble(Integer.toString(totalCount)) * 100d);
            }
        }
        //insert history
        count = 0;
        totalCount = histToInsert.size();
        List directList = new ArrayList();
        for (int j = 0; j < histToInsert.size(); j++) {
            
            directList.add(histToInsert.get(j));
            if (directList.size() == 1000) {
                util.insertDirect(InventoryStockSettlementHistory.class, directList, userData);
                directList.clear();
            }
            count++;
            if ((count + 1) % 1000 == 0) {
                System.out.println("Percentage Done History insert:" + Double.parseDouble(Integer.toString(count)) / Double.parseDouble(Integer.toString(totalCount)) * 100d);
            }
        }
        if (!directList.isEmpty()) {
            util.insertDirect(InventoryStockSettlementHistory.class, directList, userData);
            directList.clear();
        }
        //finally delete summaries
        count = 0;
        totalCount = sumToDelete.keySet().size();
        for (Long transRef : sumToDelete.keySet()) {
            InventorySummary cur = sumToDelete.get(transRef);
            sumBean.delete(cur, userData);
            if (count % 20 == 0) {
                util.flushEntity(userData);
                util.detachEntities(userData);
            }
            count++;
            if ((count + 1) % 1000 == 0) {
                System.out.println("Percentage Done Delete Sum:" + Double.parseDouble(Integer.toString(count)) / Double.parseDouble(Integer.toString(totalCount)) * 100d);
            }
        }
        sumToDelete.clear();
        inToUpdate.clear();
        outToUpdate.clear();
        List sumAfter = util.executeGeneralSelectQuery(sumGrpQ, userData);
        util.detachEntities(userData);
        HashMap<String, Double> mapAfter = new HashMap<String, Double>();
        for (int t = 0; t < sumAfter.size(); t++) {
            Object[] x = (Object[]) sumAfter.get(t);
            mapAfter.put(x[1].toString() + x[2].toString(), (Double) x[0]);
        }
        for (int t = 0; t < sumBefore.size(); t++) {
            Object[] x = (Object[]) sumBefore.get(t);
            Double comp = mapAfter.get(x[1].toString() + x[2].toString());
            if (util.compareDouble((Double) x[0], comp == null ? 0.0d : comp) != 0) {
                throw new EMCEntityBeanException("Sum for item " + x[1] + " Dimension Id " + x[2] + " does not match settlement before: " + comp + " after " + x[0]);
            }
        }
        //error log
        if (errorList.keySet().size() > 0) {
            WorkFlowActivity act = new WorkFlowActivity();
            act.setDescription("Stock Settlement Feedback.");
            act.setRequiredCompletionDate(dateBean.nowDate());
            act.setEmployeeNumber(employeeBean.findEmployee("emc", userData));
            act.setSourceTable(InventoryStockSettlementHistory.class.getName());
            act.setActivityId(Long.toString(sm.getRecordID()) + "_" + Long.toString(dateBean.nowDate().getTime()));
            act.setReferenceSource(Long.toString(sm.getRecordID()));
            act = (WorkFlowActivity) activityBean.insert(act, userData);
            fileBean.attachFileToRecord(act, "Stock Settlement Feedback", new ArrayList(errorList.values()), "Settlement.txt", userData);
            this.logMessage(Level.WARNING, "Some transactions could not be closed. An activity has been logged for the emc user.", userData);
            //done
        }
        sm.setRunStatus(SettlementStatus.COMPLETED.toString());
        settlementBean.update(sm, userData);
        this.logMessage(Level.INFO, "Inventory Close complete.", userData);
    }
    
    private boolean transactionCloseCheck(InventoryTransactions cur, EMCUserData userData) throws EMCEntityBeanException {
        //more sanity checks
        if (util.compareDouble(cur.getClosedQty(), cur.getQuantity()) == 0 && (isBlank(cur.getClosedDate()) || !cur.getClosedFlag())) {
            throw new EMCEntityBeanException("Incorrect Close Date or closed Flag on transaction:" + cur.getRecordID());
            //error closed Date not set or closed flag not set
        } else if (util.compareDouble(cur.getClosedQty(), cur.getQuantity()) == -1 && (!isBlank(cur.getClosedDate()) || cur.getClosedFlag())) {
            //error closedQty less than qty but closed flag is set or closed date is set
            throw new EMCEntityBeanException("Incorrect Close Date or closed Flag on transaction:" + cur.getRecordID());
        } else if (util.compareDouble(cur.getClosedQty(), cur.getQuantity()) == 1) {
            //serious over closed
            throw new EMCEntityBeanException("Incorrect Close Quantity on transaction:" + cur.getRecordID());
        }
        return true;
    }
    
    private InventoryTransactions updateCost(InventoryTransactions inTx, InventoryTransactions outTx, double closeQty, EMCUserData userData) throws EMCEntityBeanException {
        if (util.compareDouble(inTx.getQuantity(), 0) == 0) {
            outTx.setFifoCost(outTx.getFifoCost());
        } else {
            outTx.setFifoCost(outTx.getFifoCost() + closeQty * (inTx.getCost() / inTx.getQuantity()));
        }
        return outTx;
    }
    
    public void doRollBack(InventoryStockSettlement sm, EMCUserData userData) throws EMCEntityBeanException {
        
        EMCXMLHandler xmlHandler = new EMCXMLHandler();
        EMCQuery summarySelect = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
        HashMap<Long, InventorySummary> inSumMap = new HashMap();
        HashMap<Long, InventorySummary> outSumMap = new HashMap();
        HashMap<Long, Boolean> updateOnceInMap = new HashMap();
        HashMap<Long, Boolean> updateOnceOutMap = new HashMap();
        userData.setUserData(7, "STOCKCLOSE_IN_PROGRESS");//this is to allow the stock close to post in this period
        if (SettlementStatus.fromString(sm.getRunStatus()).equals(SettlementStatus.COMPLETED)) {
            //get Duplicates
            EMCQuery dupQ = new EMCQuery(enumQueryTypes.SELECT, InventoryStockSettlementHistory.class);
            dupQ.addAnd("settlementId", sm.getSettlementId());
            dupQ.addField("inTxRecordId");
            dupQ.addGroupBy("inTxRecordId");
            dupQ.addAndCustomHavingValue(" COUNT(" + dupQ.getTableAlias(InventoryStockSettlementHistory.class.getName()) + ".inTxRecordId) > 1");
            List inDup = util.executeGeneralSelectQuery(dupQ, userData);
            for (Object curRec : inDup) {
                updateOnceInMap.put((Long) curRec, new Boolean(true));
            }
            dupQ = new EMCQuery(enumQueryTypes.SELECT, InventoryStockSettlementHistory.class);
            dupQ.addAnd("settlementId", sm.getSettlementId());
            dupQ.addField("outTxUsedInSettlement");
            dupQ.addGroupBy("outTxUsedInSettlement");
            dupQ.addAndCustomHavingValue(" COUNT(" + dupQ.getTableAlias(InventoryStockSettlementHistory.class.getName()) + ".outTxUsedInSettlement) > 1");
            inDup = util.executeGeneralSelectQuery(dupQ, userData);
            for (Object curRec : inDup) {
                updateOnceOutMap.put((Long) curRec, new Boolean(true));
            }
            //done with duplicates
            System.out.println("Started");
            EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, InventoryStockSettlementHistory.class);
            qu.addAnd("settlementId", sm.getSettlementId());
            qu.addOrderBy("lineNumber", InventoryStockSettlementHistory.class.getName(), EMCQueryOrderByDirections.DESC);
            
            List<InventoryStockSettlementHistory> histList = util.executeGeneralSelectQuery(qu, userData);
            util.detachEntities(userData);
            System.out.println("Done History List");
            //in
            EMCQuery inQu = qu.copyQuery();
            inQu.addTableAnd(InventoryTransactions.class.getName(), "inTxRecordId", InventoryStockSettlementHistory.class.getName(), "recordID");
            inQu.addTableAsField(InventoryTransactions.class.getName());
            List<InventoryTransactions> inList = util.executeGeneralSelectQuery(inQu, userData);
            util.detachEntities(userData);
            System.out.println("Done In List");
            //out
            EMCQuery outQu = qu.copyQuery();
            outQu.addTableAnd(InventoryTransactions.class.getName(), "outTxUsedInSettlement", InventoryStockSettlementHistory.class.getName(), "recordID");
            outQu.addTableAsField(InventoryTransactions.class.getName());
            List<InventoryTransactions> outList = util.executeGeneralSelectQuery(outQu, userData);
            util.detachEntities(userData);
            System.out.println("Done Out List");
            //Open in summaries
            EMCQuery inSumQ = new EMCQuery(enumQueryTypes.SELECT, InventoryStockSettlementHistory.class.getName());
            inSumQ.addAnd("wasInClosed", false);
            qu.addAnd("settlementId", sm.getSettlementId());
            inSumQ.addTableAnd(InventorySummary.class.getName(), "inTxRecordId", InventoryStockSettlementHistory.class.getName(), "inventoryTransRef");
            inSumQ.addTableAsField(InventorySummary.class.getName());
            List<InventorySummary> sumInList = util.executeGeneralSelectQuery(inSumQ, userData);
            util.detachEntities(userData);
            System.out.println("Done Existing Summary");
            for (InventorySummary curSum : sumInList) {
                inSumMap.put(curSum.getInventoryTransRef(), curSum);
            }
            int listCount = 0;
            int totalCount = histList.size();
            for (InventoryStockSettlementHistory curHist : histList) {
                InventoryTransactions txIn = inList.get(listCount);
                InventoryTransactions txOut = outList.get(listCount);
                InventorySummary inSumRestore = null;
                InventorySummary outSumRestore = null;
                //revert in and nothing to out
                if (curHist.isWasInClosed() && !curHist.isWasOutClosed()) {
                    txIn.setClosedDate(null);
                    txIn.setClosedFlag(false);
                    inSumRestore = (InventorySummary) xmlHandler.XMLToObject(curHist.getInXmlSummary(), userData); //inTx
                    txIn.setClosedQty(txIn.getQuantity() - inSumRestore.getPhysicalTotal());//this is what was in the in last
                    txIn.setFifoCost(0d);
                    txOut.setClosedQty(util.compareDouble(txOut.getClosedQty(), 0) == 0 ? 0 : txOut.getClosedQty() - inSumRestore.getPhysicalTotal()); //reduce the out close qty
                    //check if out is still open and has summary
                    InventorySummary outSum = outSumMap.get(txOut.getRecordID());
                    if (outSum == null) {
                        summarySelect.removeAnd("inventoryTransRef");
                        summarySelect.addAnd("inventoryTransRef", txOut.getRecordID());
                        outSum = (InventorySummary) util.executeSingleResultQuery(summarySelect, userData);
                        util.detachEntities(userData);
                        outSumMap.put(outSum.getInventoryTransRef(), outSum);
                    }
                    //update out tx summary
                    outSum.setPhysicalAvailable(outSum.getPhysicalAvailable() - inSumRestore.getPhysicalAvailable());
                    outSum.setPhysicalTotal(outSum.getPhysicalTotal() - inSumRestore.getPhysicalTotal());
                    outSum.setRecAvailable(outSum.getRecAvailable() - inSumRestore.getRecAvailable());
                    outSum.setQcAvailable(outSum.getQcAvailable() - inSumRestore.getQcAvailable());

                    //revert out and adjust in
                } else if (curHist.isWasOutClosed() && !curHist.isWasInClosed()) {
                    txOut.setClosedDate(null);
                    txOut.setClosedFlag(false);
                    outSumRestore = (InventorySummary) xmlHandler.XMLToObject(curHist.getOutXmlSummary(), userData); //outTx
                    txOut.setClosedQty(txOut.getQuantity() + outSumRestore.getPhysicalTotal());//this is what was available in closed
                    txOut.setFifoCost(0d);
                    //set the in transaction closed qty
                    txIn.setClosedQty(util.compareDouble(txIn.getClosedQty(), 0) == 0 ? 0 : txIn.getClosedQty() + outSumRestore.getPhysicalTotal());
                    InventorySummary sum = inSumMap.get(txIn.getRecordID());
                    if (sum == null) {
                        summarySelect.removeAnd("inventoryTransRef");
                        summarySelect.addAnd("inventoryTransRef", txIn.getRecordID());
                        sum = (InventorySummary) util.executeSingleResultQuery(summarySelect, userData);
                        util.detachEntities(userData);
                        inSumMap.put(sum.getInventoryTransRef(), sum);
                    }
                    
                    sum.setPhysicalAvailable(sum.getPhysicalAvailable() - outSumRestore.getPhysicalAvailable());
                    sum.setPhysicalTotal(sum.getPhysicalTotal() - outSumRestore.getPhysicalTotal());
                    sum.setQcAvailable(sum.getQcAvailable() - outSumRestore.getQcAvailable());
                    sum.setRecAvailable(sum.getRecAvailable() - outSumRestore.getRecAvailable());

                    //revert both
                } else if (curHist.isWasInClosed() && curHist.isWasOutClosed()) {
                    inSumRestore = (InventorySummary) xmlHandler.XMLToObject(curHist.getInXmlSummary(), userData);
                    outSumRestore = (InventorySummary) xmlHandler.XMLToObject(curHist.getOutXmlSummary(), userData); //outTx
                    txIn.setClosedDate(null);
                    txIn.setClosedFlag(false);
                    txIn.setClosedQty(txIn.getQuantity() - inSumRestore.getPhysicalTotal()); //set the closed qty to what it was before close
                    txIn.setFifoCost(0d);
                    txOut.setClosedDate(null);
                    txOut.setClosedFlag(false);
                    txOut.setClosedQty(txOut.getQuantity() + outSumRestore.getPhysicalTotal());// set close qty
                    txOut.setFifoCost(0d);
                } else {
                    this.logMessage(Level.WARNING, "History found without indication of what was closed.", userData);
                }
                if (curHist.getInXmlSummary() != null) {
                    InventorySummary sum = inSumRestore == null ? (InventorySummary) xmlHandler.XMLToObject(curHist.getInXmlSummary(), userData) : inSumRestore;
                    sum.setRecordID(0);
                    sum.setVersion(0);
                    inSumMap.put(sum.getInventoryTransRef(), sum);
                }
                if (curHist.getOutXmlSummary() != null) {
                    InventorySummary sum = outSumRestore == null ? (InventorySummary) xmlHandler.XMLToObject(curHist.getOutXmlSummary(), userData) : outSumRestore;
                    sum.setRecordID(0);
                    sum.setVersion(0);
                    outSumMap.put(sum.getInventoryTransRef(), sum);
                }
                listCount++;
                if ((listCount + 1) % 1000 == 0) {
                    System.out.println("Percentage Done:" + Double.parseDouble(Integer.toString(listCount)) / Double.parseDouble(Integer.toString(totalCount)) * 100d);
                }
            }
            //update Transactions:
            int count = 0;
            totalCount = inList.size();
            for (InventoryTransactions tIn : inList) {
                
                Boolean mustUpdate = updateOnceInMap.get(tIn.getRecordID());
                if (mustUpdate == null || mustUpdate == true) {
                    transBean.update(tIn, userData);
                    if (mustUpdate != null) {
                        updateOnceInMap.put(tIn.getRecordID(), new Boolean(false));
                    }
                }
                if (count % 20 == 0) {
                    util.flushEntity(userData);
                    util.detachEntities(userData);
                }
                count++;
                if ((count + 1) % 1000 == 0) {
                    System.out.println("Percentage Done In:" + Double.parseDouble(Integer.toString(count)) / Double.parseDouble(Integer.toString(totalCount)) * 100d);
                }
            }
            count = 0;
            totalCount = outList.size();
            for (InventoryTransactions tOut : outList) {
                Boolean mustUpdate = updateOnceOutMap.get(tOut.getRecordID());
                if (mustUpdate == null || mustUpdate == true) {
                    transBean.update(tOut, userData);
                    if (mustUpdate != null) {
                        updateOnceOutMap.put(tOut.getRecordID(), new Boolean(false));
                    }
                }
                if (count % 20 == 0) {
                    util.flushEntity(userData);
                    util.detachEntities(userData);
                    
                }
                count++;
                if ((count + 1) % 1000 == 0) {
                    System.out.println("Percentage Done Out:" + Double.parseDouble(Integer.toString(count)) / Double.parseDouble(Integer.toString(totalCount)) * 100d);
                }
            }
            inList = null;
            //update insert summary records
            count = 0;
            totalCount = outSumMap.keySet().size();
            List<InventorySummary> sumInsertList = new ArrayList();
            for (Long transRef : outSumMap.keySet()) {
                InventorySummary cur = outSumMap.get(transRef);
                if (cur.getRecordID() == 0) {
                    sumInsertList.add(cur);
                } else {
                    sumBean.update(cur, userData);
                }
                if (count % 20 == 0) {
                    util.flushEntity(userData);
                    util.detachEntities(userData);
                }
                count++;
                if ((count + 1) % 1000 == 0) {
                    System.out.println("Percentage Done Sum Out:" + Double.parseDouble(Integer.toString(count)) / Double.parseDouble(Integer.toString(totalCount)) * 100d);
                }
            }
            //update insert summary records
            /*count = 0;
             totalCount = sumInsertList.size();
            
             for(InventorySummary cur:sumInsertList){
             sumBean.insert(cur, userData);
             if(count%20==0){
             util.flushEntity(userData);
             util.detachEntities(userData);
             }
             count++;
             if((count+1)%1000 == 0){
             System.out.println("Percentage Done Sum Out insert:"+Double.parseDouble(Integer.toString(count))/Double.parseDouble(Integer.toString(totalCount))*100d);
             }
             }
             sumInsertList = new ArrayList();*/
            count = 0;
            totalCount = inSumMap.keySet().size();
            for (Long transRef : inSumMap.keySet()) {
                InventorySummary cur = inSumMap.get(transRef);
                if (cur.getRecordID() == 0) {
                    sumInsertList.add(cur);
                } else {
                    sumBean.update(cur, userData);
                }
                if (count % 20 == 0) {
                    util.flushEntity(userData);
                    util.detachEntities(userData);
                }
                count++;
                if ((count + 1) % 1000 == 0) {
                    System.out.println("Percentage Done Sum In:" + Double.parseDouble(Integer.toString(count)) / Double.parseDouble(Integer.toString(totalCount)) * 100d);
                }
            }
            //update insert summary records
            count = 0;
            totalCount = sumInsertList.size();
            List directList = new ArrayList();
            for (int j = 0; j < sumInsertList.size(); j++) {
                /*sumBean.insert(cur, userData);
                 if(count%20==0){
                 util.flushEntity(userData);
                 util.detachEntities(userData);
                 }*/
                directList.add(sumInsertList.get(j));
                if (directList.size() == 1000) {
                    util.insertDirect(InventorySummary.class, directList, userData);
                    directList.clear();
                }
                count++;
                if ((count + 1) % 1000 == 0) {
                    System.out.println("Percentage Done Sum In insert:" + Double.parseDouble(Integer.toString(count)) / Double.parseDouble(Integer.toString(totalCount)) * 100d);
                }
            }
            if (!directList.isEmpty()) {
                util.insertDirect(InventorySummary.class, directList, userData);
                directList.clear();
            }
            System.out.println("Deleting Settlement History");
            //delete history
            EMCQuery remHistQ = new EMCQuery(enumQueryTypes.DELETE, InventoryStockSettlementHistory.class);
            remHistQ.addAnd("settlementId", sm.getSettlementId());
            util.executeUpdate(remHistQ, userData);
            //done
            logMessage(Level.INFO, "Roll Back Completed.", userData);
            sm.setRunStatus(SettlementStatus.ROLLBACK.toString());
            settlementBean.update(sm, userData);
        } else {
            logMessage(Level.INFO, "This settlement has not been completed. It cannot be rolled back.", userData);
        }
        
    }
}

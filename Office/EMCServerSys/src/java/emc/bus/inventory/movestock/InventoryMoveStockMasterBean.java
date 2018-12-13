/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor. 
 */
package emc.bus.inventory.movestock;

import emc.bus.inventory.transactions.EMCStockException;
import emc.bus.inventory.transactions.ProcessStockTransactionLocal;
import emc.bus.inventory.transactions.TransactionHelper;
import emc.bus.inventory.transactions.TransactionType;
import emc.bus.inventory.transactions.datasource.InventorySummaryDSLocal;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.movestock.InventoryMoveStockMaster;
import emc.entity.inventory.picking.InventoryPickingListLines;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryLocationsEnum;
import emc.enums.inventory.transactions.InventoryTransactionStatus;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.StopWatchFactory;
import emc.tables.EMCTable;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryMoveStockMasterBean extends EMCEntityBean implements InventoryMoveStockMasterLocal {

    @EJB
    private ProcessStockTransactionLocal stockBean;
    @EJB
    private InventoryMoveStockSummaryLocal movementSummaryBean;
    @EJB
    private InventorySummaryDSLocal summDSBean;

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);
        if (ret) {
            InventoryMoveStockMaster master = (InventoryMoveStockMaster) vobject;
            if ((master.getQuantity() != 0 || !isBlank(master.getToLocation())) && (master.getQuantity() == 0 || isBlank(master.getToLocation())) && !master.isSplit()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Both Quantity and Location needs to be specified.", userData);
                return false;
            }
        }
        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);
        if (ret) {
            InventoryMoveStockMaster master = (InventoryMoveStockMaster) vobject;
            if ((master.getQuantity() != 0 || !isBlank(master.getToLocation())) && (master.getQuantity() == 0 || isBlank(master.getToLocation())) && !master.isSplit()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Both Quantity and Location needs to be specified.", userData);
                return false;
            }
        }
        return ret;
    }

    @Override
    public String populateTable(String warehouse, String location, EMCUserData userData) throws EMCEntityBeanException {
        userData = userData.copyUserData();
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        query.addAnd("warehouse", warehouse);
        query.addAnd("location", location);

        boolean useQC = InventoryLocationsEnum.QUALITY_CHECK.toString().equals(location);
        boolean useRec = InventoryLocationsEnum.RECEIVING_AREA.toString().equals(location);

      

        //Group by all dimension
        userData.setUserData(11, true);
        userData.setUserData(12, true);
        userData.setUserData(13, true);
        userData.setUserData(14, true);
        userData.setUserData(15, true);
        userData.setUserData(16, true);
        userData.setUserData(17, true);
        userData.setUserData(18, true);

        userData.setUserData(0, query);

        StopWatchFactory fact = new StopWatchFactory();
        fact.start("sel");
        List<InventorySummary> summary = (List<InventorySummary>) summDSBean.getDataInRange(InventorySummary.class, userData, 0, Integer.MAX_VALUE);
        System.out.println("Selected data in: " + fact.stop("sel"));

        return buildMaster(null, useQC, useRec, useQC, summary, userData);
    }

    private String buildMaster(String sessionId, boolean useQC, boolean useRec, boolean setQCStatus,
            List<InventorySummary> summaryList, EMCUserData userData) throws EMCEntityBeanException {
        StopWatchFactory fact = new StopWatchFactory();
        fact.start("pop");
        System.out.println("Records found: " + summaryList.size());
        for (InventorySummary curSum : summaryList) {
            double quantityAvailable = curSum.getPhysicalAvailable();
            if (useQC) {
                quantityAvailable += curSum.getQcAvailable();
            }
            if (useRec) {
                quantityAvailable += curSum.getRecAvailable();
            }
            if (util.compareDouble(quantityAvailable, 0) > 0) {
                InventoryMoveStockMaster moveRecord = new InventoryMoveStockMaster();
                moveRecord.setItemId(curSum.getItemId());
                moveRecord.setDimensionId(curSum.getItemDimId());
                moveRecord.setAvailableQty(quantityAvailable);
                //Dummy trans id
                moveRecord.setTransId("temp");
                //Generated session id.  If we use a number sequence, two users cannot initiate a move simultaneously.

                moveRecord.setMasterSessionId(userData.getUserName() + System.currentTimeMillis());

                if (setQCStatus) {
                    moveRecord.setQCStatus(curSum.getQCStatus());
                }
                if (sessionId != null) {
                    moveRecord.setMasterSessionId(sessionId);
                }
                super.insert(moveRecord, userData);
                if (sessionId == null) {
                    sessionId = moveRecord.getMasterSessionId();
                }
            }
        }

        System.out.println("Populated records in: " + fact.stop("pop"));
        return sessionId;
    }

    @Override
    public boolean moveStock(String warehouseId, String locationId, String sessionId, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryMoveStockMaster.class);
        query.addTableAnd(InventoryDimensionTable.class.getName(), "dimensionId", InventoryMoveStockMaster.class.getName(), "recordID");
        query.addAnd("warehouseId", warehouseId, InventoryDimensionTable.class.getName());
        query.addAnd("locationId", locationId, InventoryDimensionTable.class.getName());
        query.addAnd("posted", false, InventoryMoveStockMaster.class.getName());
        query.addAnd("masterSessionId", sessionId);
        List recordList = util.executeGeneralSelectQuery(query, userData);
        for (Object o : recordList) {
            InventoryMoveStockMaster record = (InventoryMoveStockMaster) o;
            if (record.getQuantity() != 0) {
                try {
                    //Only assign "real" transaction id when posting.
                    record.setTransId(null);
                    super.doHonourDataTypesOnInsert(record, userData);

                    stockBean.post(record, new TransactionHelper(TransactionType.IVENT_MOVEMENT_LOCATION), userData);
                    movementSummaryBean.insertRecord(record, userData);
                    record.setPosted(true);
                    super.update(record, userData);
                } catch (EMCStockException ex) {
                    throw new EMCEntityBeanException("Failed to post:" + ex.getMessage());
                }
            }
        }
        return true;
    }

    @Override
    public String populateReserveTable(String warehouse, String location, String so, String awo, EMCUserData userData) throws EMCEntityBeanException {
        userData = userData.copyUserData();
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        if (!isBlank(so) || !isBlank(awo)) {
            query.addTableAnd(InventoryTransactions.class.getName(), "inventoryTransRef", InventorySummary.class.getName(), "recordID");
            if (!isBlank(so)) {
                query.addAnd("refNumber", so, InventoryTransactions.class.getName());
            }
            if (!isBlank(awo)) {
                query.addTableAnd(" " + InventoryTransactions.class.getName(), "refTransId", InventoryTransactions.class.getName(), "recordID");
                query.addAnd("refNumber", awo, " " + InventoryTransactions.class.getName());
//                query.addTableAnd(ProductionWorksOrder.class.getName(), "refNumber", " " + InventoryTransactions.class.getName(), "worksOrderId");
//                query.addAnd("awoId", awo, ProductionWorksOrder.class.getName());
            }
        }
        query.addAnd("warehouse", warehouse);
        query.addAnd("location", location);
        query.setSelectDistinctAll(true);

        boolean useQC = InventoryLocationsEnum.QUALITY_CHECK.toString().equals(location);
        boolean useRec = InventoryLocationsEnum.RECEIVING_AREA.toString().equals(location);

      

        //Group by all dimension
        userData.setUserData(11, true);
        userData.setUserData(12, true);
        userData.setUserData(13, true);
        userData.setUserData(14, true);
        userData.setUserData(15, true);
        userData.setUserData(16, true);
        userData.setUserData(17, true);
        userData.setUserData(18, true);

        userData.setUserData(0, query.copyQuery());

        List<InventorySummary> summary = (List<InventorySummary>) summDSBean.getDataInRange(InventorySummary.class, userData, 0, Integer.MAX_VALUE);

        //Reserved
        query.addTableAnd(InventoryTransactions.class.getName(), "inventoryTransRef", InventorySummary.class.getName(), "recordID");
        query.addAnd("status", InventoryTransactionStatus.RESERVED.toString(), InventoryTransactions.class.getName());

        //Ignote Picking list reservations
        EMCQuery nested = new EMCQuery(enumQueryTypes.SELECT, InventoryPickingListLines.class);
        nested.addAnd("issued", false);
        nested.addAnd("cancelled", false);
        nested.addAnd("warehouse", warehouse);
        nested.addAnd("location", location);
        nested.addField("transId");
        nested.setSelectDistinctAll(true);
        List<String> notTransId = util.executeGeneralSelectQuery(nested, userData);

        if (notTransId != null && !notTransId.isEmpty()) {
            query.addAndInList("transId", InventoryTransactions.class.getName(), notTransId, true, true);
        }

        userData.setUserData(0, query.copyQuery());

        List<InventorySummary> reservedSummary = (List<InventorySummary>) summDSBean.getDataInRange(InventorySummary.class, userData, 0, Integer.MAX_VALUE);

        return buildReservedMaster(null, summary, reservedSummary, useQC, useRec, useQC, userData);
    }

    private String buildReservedMaster(String sessionId, List<InventorySummary> summaryList, List<InventorySummary> reservedSummaryList, boolean useQC, boolean useRec, boolean setQCStatus, EMCUserData userData) throws EMCEntityBeanException {
        for (InventorySummary curSum : summaryList) {
            double quantityAvailable = curSum.getPhysicalAvailable();
            if (useQC) {
                quantityAvailable += curSum.getQcAvailable();
            }
            if (useRec) {
                quantityAvailable += curSum.getRecAvailable();
            }
            if (util.compareDouble(quantityAvailable, 0) > 0) {
                InventoryMoveStockMaster moveRecord = new InventoryMoveStockMaster();
                moveRecord.setItemId(curSum.getItemId());
                moveRecord.setDimensionId(curSum.getItemDimId());
                moveRecord.setAvailableQty(quantityAvailable);
                moveRecord.setReserved(false);
                //Dummy trans id
                moveRecord.setTransId("temp");
                //Generated session id.  If we use a number sequence, two users cannot initiate a move simultaneously.
                moveRecord.setMasterSessionId(userData.getUserName() + System.currentTimeMillis());

                if (setQCStatus) {
                    moveRecord.setQCStatus(curSum.getQCStatus());
                }

                if (sessionId != null) {
                    moveRecord.setMasterSessionId(sessionId);
                }

                super.insert(moveRecord, userData);

                if (sessionId == null) {
                    sessionId = moveRecord.getMasterSessionId();
                }
            }
        }
        for (InventorySummary curSum : reservedSummaryList) {
            double quantityAvailable = curSum.getPhysicalReserved();
            if (util.compareDouble(quantityAvailable, 0) > 0) {
                InventoryMoveStockMaster moveRecord = new InventoryMoveStockMaster();
                moveRecord.setItemId(curSum.getItemId());
                moveRecord.setDimensionId(curSum.getItemDimId());
                moveRecord.setAvailableQty(quantityAvailable);
                moveRecord.setReserved(true);
                //Dummy trans id
                moveRecord.setTransId("temp");
                //Generated session id.  If we use a number sequence, two users cannot initiate a move simultaneously.
                moveRecord.setMasterSessionId(userData.getUserName() + System.currentTimeMillis());

                if (setQCStatus) {
                    moveRecord.setQCStatus(curSum.getQCStatus());
                }

                if (sessionId != null) {
                    moveRecord.setMasterSessionId(sessionId);
                }

                super.insert(moveRecord, userData);

                if (sessionId == null) {
                    sessionId = moveRecord.getMasterSessionId();
                }
            }
        }

        return sessionId;
    }

    @Override
    public boolean moveReservedStock(String warehouseId, String locationId, String sessionId, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryMoveStockMaster.class);
        query.addTableAnd(InventoryDimensionTable.class.getName(), "dimensionId", InventoryMoveStockMaster.class.getName(), "recordID");
        query.addAnd("warehouseId", warehouseId, InventoryDimensionTable.class.getName());
        query.addAnd("locationId", locationId, InventoryDimensionTable.class.getName());
        query.addAnd("posted", false, InventoryMoveStockMaster.class.getName());
        query.addAnd("masterSessionId", sessionId);
        List recordList = util.executeGeneralSelectQuery(query, userData);
        for (Object o : recordList) {
            InventoryMoveStockMaster record = (InventoryMoveStockMaster) o;
            if (record.getQuantity() != 0) {
                try {
                    record.setTransId(null);
                    super.doHonourDataTypesOnInsert(record, userData);

                    if (record.isReserved()) {
                        stockBean.post(record, new TransactionHelper(TransactionType.IVENT_RESERVED_MOVEMENT_LOCATION), userData);
                    } else {
                        stockBean.post(record, new TransactionHelper(TransactionType.IVENT_MOVEMENT_LOCATION), userData);
                    }
                    movementSummaryBean.insertRecord(record, userData);
                    record.setPosted(true);
                    super.update(record, userData);
                } catch (EMCStockException ex) {
                    throw new EMCEntityBeanException("Failed to post:" + ex.getMessage());
                }
            }
        }
        return true;
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);
        super.checkCompanyId(query, userData);
        return util.executeLimitedNativeQuery(query, InventoryMoveStockMaster.class, start, end, userData);
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);
        return util.executeNativeQuery(query, InventoryMoveStockMaster.class, userData).size() + ", 0";

    }
}

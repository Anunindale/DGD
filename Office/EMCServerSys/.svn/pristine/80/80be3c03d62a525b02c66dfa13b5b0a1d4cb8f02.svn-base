/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.transactions;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.bus.inventory.stocktakelogger.InventoryStockTakeLoggerLocal;
import emc.bus.inventory.transactions.stockreservationrules.DefaultReservationRule;
import emc.bus.inventory.transactions.stockreservationrules.StockReservationRule;
import emc.entity.inventory.InventoryItemGroup;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.inventory.transactions.unreserve.InventoryUnreservedSummary;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryLocationsEnum;
import emc.enums.inventory.requirementsplanning.RequirementsPlanningReferenceTypes;
import emc.enums.inventory.transactions.InventorySummaryUpdateOptions;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.enums.inventory.transactions.InventoryTransactionStatus;
import emc.enums.inventory.transactions.InventoryTransactionTypes;
import emc.enums.inventory.transactions.InventoryTransactionsRefType;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.server.commandmanager.EMCCommandManagerLocal;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.TransactionSynchronizationRegistry;

/**
 *
 * @author rico
 */
public class TransactionsSuperClass extends EMCBusinessBean {

    @EJB
    protected InventoryDimensionTableLocal dimIdLocal;
    @EJB
    protected InventorySummaryLocal itemSumLocal;
    @EJB
    protected emc.bus.inventory.transactions.InventoryTransactionsLocal trans;
    @EJB
    protected emc.bus.base.logic.BaseUOMLogicLocal uomConv;
    @EJB
    protected InventoryReferenceLocal referenceBean;
    @EJB
    protected EMCCommandManagerLocal commandManager;
    @EJB
    private InventoryStockTakeLoggerLocal stockTakeLoggerBean;
    @Resource
    protected TransactionSynchronizationRegistry registry;

    public TransactionsSuperClass() {
    }

    /**
     * Location available in specific warehouse also checks recAvailable and QC
     * available
     *
     * @param warehouse
     * @param location
     * @param userData
     * @return
     */
    protected boolean isAvailableInWarehouse(String warehouse, String location, EMCUserData userData) {
        if (location == null) {
            return true;
        }
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouse.class.getName());
        query.addAnd("warehouseId", warehouse);
        if (location.equals(InventoryLocationsEnum.RECEIVING_AREA.toString())) {
            query.addField("RECAvailable");
        } else if (location.equals(InventoryLocationsEnum.QUALITY_CHECK.toString())) {
            query.addField("QCAvailable");
        } else {
            return true;
        }
        Object o = util.executeSingleResultQuery(query, userData);
        return o == null ? false : (Boolean) o;
    }

    /**
     * Checks the quarantine flag on the Item Group
     *
     * @param itemId
     * @param userData
     * @return
     */
    protected boolean isQuarantineActive(String itemId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemGroup.class.getName());
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemGroup", InventoryItemGroup.class.getName(), "itemGroup");
        query.addAnd("itemId", itemId, InventoryItemMaster.class.getName());
        InventoryItemGroup group = (InventoryItemGroup) util.executeSingleResultQuery(query, userData);
        return group.getQuarantineReq();
    }

    /**
     * Checks the quarantine flag status on the Item Group.
     *
     * @param summary
     * @param userData
     * @return
     */
    protected boolean isQuarantineActive(InventorySummary summary, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemGroup.class.getName());
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemGroup", InventoryItemGroup.class.getName(), "itemGroup");
        query.addAnd("itemId", summary.getItemId(), InventoryItemMaster.class.getName());
        InventoryItemGroup group = (InventoryItemGroup) util.executeSingleResultQuery(query, userData);
        return group.getQuarantineReq();
    }

    /**
     * Checks if location is set on the item
     *
     * @param summary
     * @param userData
     * @return
     */
    protected boolean isLocationActive(String itemId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemDimensionGroupId", InventoryItemDimensionGroup.class.getName(), "dimensionGroup");
        query.addAnd("itemId", itemId, InventoryItemMaster.class.getName());
        query.addField("locationActive", InventoryItemDimensionGroup.class.getName());
        Boolean x = (Boolean) util.executeSingleResultQuery(query, userData);
        return x == null ? false : x;
    }

    /**
     * Checks if location is set on the item
     *
     * @param summary
     * @param userData
     * @return
     */
    protected boolean isLocationActive(InventorySummary summary, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemDimensionGroupId", InventoryItemDimensionGroup.class.getName(), "dimensionGroup");
        query.addAnd("itemId", summary.getItemId(), InventoryItemMaster.class.getName());
        query.addField("locationActive", InventoryItemDimensionGroup.class.getName());
        Boolean x = (Boolean) util.executeSingleResultQuery(query, userData);
        return x == null ? false : x;
    }

    /**
     * Checks if the stock will be available as soon as it is received
     *
     * @param summary
     * @param userData
     * @return
     */
    protected boolean isAvailableInWarehouse(InventorySummary summary, EMCUserData userData) {
        if (!isBlank(summary.getWarehouse())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouse.class.getName());
            query.addAnd("warehouseId", summary.getWarehouse());
            query.addAnd("companyId", userData.getCompanyId());
            InventoryWarehouse warehouse = (InventoryWarehouse) util.executeSingleResultQuery(query, userData);
            if (isQuarantineActive(summary, userData)) {
                return warehouse.getQCAvailable();
            } else {
                return warehouse.getRECAvailable();
            }
        }
        return true;
    }

    /**
     * Deletes the given transaction and its relevant on hand record.
     *
     * @param transaction
     * @param userData
     * @throws emc.framework.EMCEntityBeanException
     */
    protected void deleteTransactionAndOnHand(InventoryTransactions transaction, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
        query.addAnd("inventoryTransRef", transaction.getRecordID());
        InventorySummary summary = (InventorySummary) util.executeSingleResultQuery(query, userData);
        itemSumLocal.delete(summary, userData);
        trans.delete(transaction, userData);
    }

    /**
     *
     * @param theOption
     * @param itemDimId
     * @param itemId
     * @param userData
     * @return
     */
    protected EMCTable updateOnHand(InventorySummaryUpdateOptions theOption, InventoryTransactions trans, EMCUserData userData) throws EMCStockException {
        boolean quarantine;
        boolean available;
        boolean location;
        boolean createQCRec = false;
        String sumLocation;
        InventorySummary inventSum = new InventorySummary();
        //see if summary tx already exists
        EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
        qu.addAnd("inventoryTransRef", trans.getRecordID());
        Object res = util.executeSingleResultQuery(qu, userData);
        if (res != null) {
            inventSum = (InventorySummary) res;
        }
        inventSum.setInventoryTransRef(trans.getRecordID());
        inventSum.setItemDimId(trans.getItemDimId());
        //set the detail dimensions
        InventoryDimensionTable dimRes = dimIdLocal.getDimensionRecord(trans.getItemDimId(), userData);
        inventSum.setDimension1(dimRes.getDimension1Id());
        inventSum.setDimension2(dimRes.getDimension2Id());
        inventSum.setDimension3(dimRes.getDimension3Id());
        inventSum.setWarehouse(dimRes.getWarehouseId());
        inventSum.setCompanyId(util.getCompanyId(emc.entity.inventory.transactions.InventorySummary.class.getName(), userData));
        //need to set these
        inventSum.setBatch(dimRes.getBatchId());
        inventSum.setSerialNo(dimRes.getItemSerialId());
        //pallet
        inventSum.setPallet(dimRes.getPalletId());
        //item
        inventSum.setItemId(trans.getItemId());
        //Location
        inventSum.setLocation(dimRes.getLocationId());

        double qty = trans.getQuantity() - trans.getClosedQty();
        EMCQuery checkQty = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
        checkQty.addAnd("itemId", inventSum.getItemId());
        checkQty.addAnd("itemDimId", inventSum.getItemDimId());

        double availQty = 0.0;
        Object qRes;
        switch (theOption) {
            case ORDERED_IN:
                //check if there is enough stock
                checkQty.addFieldAggregateFunction("orderedTotal", InventorySummary.class.getName(), "SUM");
                qRes = util.executeSingleResultQuery(checkQty, userData);
                if (qRes != null) {
                    availQty = Double.parseDouble(qRes.toString());
                }
                if (util.compareDouble(availQty + qty, 0) >= 0) {
                    inventSum.setOrderedTotal(qty);
                    inventSum.setOrderedAvailable(qty);
                    //clear qtys
                    inventSum.setPhysicalAvailable(0.0);
                    inventSum.setPhysicalReserved(0.0);
                    inventSum.setPhysicalTotal(0.0);
                    inventSum.setOrderedReserved(0.0);
                    inventSum.setPicked(0);
                    inventSum.setPosted(0.0);
                    inventSum.setReceived(0.0);
                    inventSum.setRegistered(0.0);
                    inventSum.setOrderedOut(0.0);
                } else {
                    throw new EMCStockException("Ordered quantity may not be negative:" + referenceBean.checkItemReference(inventSum.getItemId(), userData)[1]);
                }
                break;
            case ORDERED_OUT:
                //check if there is enough stock
                checkQty.addFieldAggregateFunction("orderedOut", InventorySummary.class.getName(), "SUM");
                qRes = util.executeSingleResultQuery(checkQty, userData);
                if (qRes != null) {
                    availQty = Double.parseDouble(qRes.toString());
                }
                if (util.compareDouble(availQty + qty, 0) >= 0) {
                    inventSum.setOrderedOut(qty);
                    //clear qtys
                    inventSum.setPhysicalAvailable(0.0);
                    inventSum.setPhysicalReserved(0.0);
                    inventSum.setPhysicalTotal(0.0);
                    inventSum.setOrderedAvailable(0.0);
                    inventSum.setOrderedReserved(0.0);
                    inventSum.setOrderedTotal(0.0);
                    inventSum.setPicked(0);
                    inventSum.setPosted(0.0);
                    inventSum.setReceived(0.0);
                    inventSum.setRegistered(0.0);
                    inventSum.setRecAvailable(0.0);
                    inventSum.setQcAvailable(0.0);
                } else {
                    throw new EMCStockException("Not enough stock available:" + referenceBean.checkItemReference(inventSum.getItemId(), userData)[1]);
                }
                break;
            case ORDERED_OUT_RESERVED:
                //check if there is enough stock String sumLocation = isBlank(inventSum.getLocation()) ? "" : inventSum.getLocation();
                sumLocation = isBlank(inventSum.getLocation()) ? "" : inventSum.getLocation();
                if (sumLocation.equals(InventoryLocationsEnum.QUALITY_CHECK.toString())) {
                    if (isAvailableInWarehouse(inventSum, userData)) {
                        checkQty.addFieldAggregateFunction("physicalAvailable", InventorySummary.class.getName(), "SUM");
                    } else {
                        checkQty.addFieldAggregateFunction("qcAvailable", InventorySummary.class.getName(), "SUM");
                    }
                } else if (sumLocation.equals(InventoryLocationsEnum.RECEIVING_AREA.toString())) {
                    if (isAvailableInWarehouse(inventSum, userData)) {
                        checkQty.addFieldAggregateFunction("physicalAvailable", InventorySummary.class.getName(), "SUM");
                    } else {
                        checkQty.addFieldAggregateFunction("recAvailable", InventorySummary.class.getName(), "SUM");
                    }
                } else {
                    checkQty.addFieldAggregateFunction("physicalAvailable", InventorySummary.class.getName(), "SUM");
                }
                //sets as if there is enough stock - do this so code is not duplicated on suspence transaction
                location = isLocationActive(inventSum, userData);

                if (location) {
                    if (sumLocation.equals(InventoryLocationsEnum.QUALITY_CHECK.toString())) {
                        if (isAvailableInWarehouse(inventSum.getWarehouse(), inventSum.getLocation(), userData)) {
                            inventSum.setPhysicalAvailable(-1.0 * qty);
                        } else {
                            inventSum.setQcAvailable(-1.0 * qty);
                        }
                    } else if (sumLocation.equals(InventoryLocationsEnum.RECEIVING_AREA.toString())) {
                        if (isAvailableInWarehouse(inventSum.getWarehouse(), inventSum.getLocation(), userData)) {
                            inventSum.setPhysicalAvailable(-1.0 * qty);
                        } else {
                            inventSum.setRecAvailable(-1.0 * qty);
                        }
                    } else {
                        inventSum.setPhysicalAvailable(-1.0 * qty);
                    }
                }

                inventSum.setPhysicalReserved(qty);
                //clear qtys
                inventSum.setPhysicalTotal(0.0);
                inventSum.setOrderedAvailable(0.0);
                inventSum.setOrderedReserved(0.0);
                inventSum.setOrderedTotal(0.0);
                inventSum.setPicked(0);
                inventSum.setPosted(0.0);
                inventSum.setReceived(0.0);
                inventSum.setRegistered(0.0);
                inventSum.setOrderedOut(0.0);
                try {
                    List result = itemSumLocal.getQuantityAvailableLoadSuspenceTrans(checkQty, inventSum, theOption, registry.getTransactionKey().toString(), userData);
                    availQty = (Double) result.get(0);
                    //load suspence transaction cleanup
                    EMCQuery sfQuery = new EMCQuery(enumQueryTypes.DELETE, InventorySummary.class);
                    sfQuery.addAnd("recordID", result.get(1));
                    commandManager.setTransactionFailQuery(registry.getTransactionKey(), sfQuery.toString());
                    commandManager.setTransactionSucceedQuery(registry.getTransactionKey(), sfQuery.toString());
                    if (!(util.compareDouble(availQty - qty, 0) >= 0)) {
                        throw new EMCStockException("Not enough stock available:" + referenceBean.checkItemReference(inventSum.getItemId(), userData)[1]);
                    }
                    //Update transaction status
                    trans.setStatus(InventoryTransactionStatus.RESERVED.toString());
                    try {
                        this.trans.update(trans, userData);
                    } catch (EMCEntityBeanException ex) {
                        throw new EMCStockException("Failed to set reserved status on transaction.");
                    }
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException("Failed to insert Suspence Transaction during Reservation of stock.");
                }
                break;
            case ORDERED_OUT_UNRESERVED:
                //check if there is enough stock String sumLocation = isBlank(inventSum.getLocation()) ? "" : inventSum.getLocation();
                sumLocation = isBlank(inventSum.getLocation()) ? "" : inventSum.getLocation();
                if (sumLocation.equals(InventoryLocationsEnum.QUALITY_CHECK.toString())) {
                    if (isAvailableInWarehouse(inventSum, userData)) {
                        checkQty.addFieldAggregateFunction("physicalAvailable", InventorySummary.class.getName(), "SUM");
                    } else {
                        checkQty.addFieldAggregateFunction("qcAvailable", InventorySummary.class.getName(), "SUM");
                    }
                } else if (sumLocation.equals(InventoryLocationsEnum.RECEIVING_AREA.toString())) {
                    if (isAvailableInWarehouse(inventSum, userData)) {
                        checkQty.addFieldAggregateFunction("physicalAvailable", InventorySummary.class.getName(), "SUM");
                    } else {
                        checkQty.addFieldAggregateFunction("recAvailable", InventorySummary.class.getName(), "SUM");
                    }
                } else {
                    checkQty.addFieldAggregateFunction("physicalAvailable", InventorySummary.class.getName(), "SUM");
                }
                //sets as if there is enough stock - do this so code is not duplicated on suspence transaction
                location = isLocationActive(inventSum, userData);
               
                if (location) {
                    if (sumLocation.equals(InventoryLocationsEnum.QUALITY_CHECK.toString())) {
                        if (isAvailableInWarehouse(inventSum.getWarehouse(), inventSum.getLocation(), userData)) {
                            inventSum.setPhysicalAvailable(qty);
                        } else {
                            inventSum.setQcAvailable(qty);
                        }
                    } else if (sumLocation.equals(InventoryLocationsEnum.RECEIVING_AREA.toString())) {
                        if (isAvailableInWarehouse(inventSum.getWarehouse(), inventSum.getLocation(), userData)) {
                            inventSum.setPhysicalAvailable(qty);
                        } else {
                            inventSum.setRecAvailable(qty);
                        }
                    } else {
                        inventSum.setPhysicalAvailable(qty);
                    }
                }
                
                inventSum.setPhysicalReserved(-1 * qty);
                //clear qtys
                inventSum.setPhysicalTotal(0.0);
                inventSum.setOrderedAvailable(0.0);
                inventSum.setOrderedReserved(0.0);
                inventSum.setOrderedTotal(0.0);
                inventSum.setPicked(0);
                inventSum.setPosted(0.0);
                inventSum.setReceived(0.0);
                inventSum.setRegistered(0.0);
                inventSum.setOrderedOut(0.0);
                try {
                    List result = itemSumLocal.getQuantityAvailableLoadSuspenceTrans(checkQty, inventSum, theOption, registry.getTransactionKey().toString(), userData);
                    //availQty = (Double)result.get(0);
                    //load suspence transaction cleanup
                    EMCQuery sfQuery = new EMCQuery(enumQueryTypes.DELETE, InventoryUnreservedSummary.class);
                    sfQuery.addAnd("recordID", result.get(1));
                    commandManager.setTransactionFailQuery(registry.getTransactionKey(), sfQuery.toString());
                    commandManager.setTransactionSucceedQuery(registry.getTransactionKey(), sfQuery.toString());
                    //if (!(util.compareDouble(availQty - qty, 0) >= 0)) {
                    //    throw new EMCStockException("Not enough stock available:" + referenceBean.checkItemReference(inventSum.getItemId(), userData)[1]);
                    //}
                    //Update transaction status
                    trans.setStatus(InventoryTransactionStatus.ORDERED.toString());
                    try {
                        this.trans.update(trans, userData);
                    } catch (EMCEntityBeanException ex) {
                        throw new EMCStockException("Failed to set reserved status on transaction.");
                    }
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException("Failed to insert Suspence Transaction during Reservation of stock.");
                }
                break;
            case RECEIVED:
                //Check Stock Taking
                checkStockTaking(trans.getItemId(), trans.getItemDimId(), userData);

                location = isLocationActive(inventSum, userData);
                if (location && isBlank(inventSum.getLocation())) {
                    available = isAvailableInWarehouse(inventSum, userData);
                    if (available) {
                        checkQty.addFieldAggregateFunction("physicalAvailable", InventorySummary.class.getName(), "SUM");
                    } else {
                        quarantine = isQuarantineActive(inventSum, userData);
                        if (quarantine) {
                            checkQty.addFieldAggregateFunction("qcAvailable", InventorySummary.class.getName(), "SUM");
                        } else {
                            checkQty.addFieldAggregateFunction("recAvailable", InventorySummary.class.getName(), "SUM");
                        }
                    }
                } else {
                    checkQty.addFieldAggregateFunction("physicalAvailable", InventorySummary.class.getName(), "SUM");
                }
                qRes = util.executeSingleResultQuery(checkQty, userData);
                if (qRes != null) {
                    availQty = Double.parseDouble(qRes.toString());
                }
                if (util.compareDouble(availQty + qty, 0) >= 0) {
                    available = isAvailableInWarehouse(inventSum, userData);
                    quarantine = isQuarantineActive(inventSum, userData);
                    if (location && isBlank(inventSum.getLocation())) {
                        if (available) {
                            inventSum.setPhysicalAvailable(qty);
                            inventSum.setPhysicalTotal(qty);
                            if (quarantine) {
                                inventSum.setLocation(InventoryLocationsEnum.QUALITY_CHECK.toString());
                                inventSum.setQcAvailable(0);
                                createQCRec = true;
                            } else {
                                inventSum.setLocation(InventoryLocationsEnum.RECEIVING_AREA.toString());
                                inventSum.setRecAvailable(0);
                            }
                        } else {
                            if (quarantine) {
                                inventSum.setQcAvailable(qty);
                                inventSum.setPhysicalTotal(qty);
                                inventSum.setLocation(InventoryLocationsEnum.QUALITY_CHECK.toString());
                                createQCRec = true;
                            } else {
                                inventSum.setRecAvailable(qty);
                                inventSum.setPhysicalTotal(qty);
                                inventSum.setLocation(InventoryLocationsEnum.RECEIVING_AREA.toString());
                            }
                            inventSum.setPhysicalAvailable(0);
                        }
                        inventSum.setItemDimId(dimIdLocal.getDimRecordId(inventSum.getBatch(), inventSum.getDimension1(), inventSum.getDimension2(),
                                                                         inventSum.getDimension3(), inventSum.getWarehouse(), inventSum.getLocation(), inventSum.getPallet(), inventSum.getSerialNo(), userData));
                        trans.setItemDimId(inventSum.getItemDimId());

                        dimRes = dimIdLocal.getDimensionRecord(trans.getItemDimId(), userData);
                        try {
                            this.trans.update(trans, userData);
                        } catch (EMCEntityBeanException ex) {
                            throw new EMCStockException("Failed to update transaction:" + ex.getMessage());
                        }
                    } else {
                        if (InventoryLocationsEnum.QUALITY_CHECK.toString().equals(inventSum.getLocation())) {
                            createQCRec = true;
                            if (available) {
                                inventSum.setQcAvailable(0);
                                inventSum.setRecAvailable(0);
                                inventSum.setPhysicalAvailable(qty);
                                inventSum.setPhysicalTotal(qty);
                            } else {
                                inventSum.setQcAvailable(qty);
                                inventSum.setRecAvailable(0);
                                inventSum.setPhysicalAvailable(0);
                                inventSum.setPhysicalTotal(qty);
                            }
                        } else if ((InventoryLocationsEnum.RECEIVING_AREA.toString().equals(inventSum.getLocation()))) {
                            if (available) {
                                inventSum.setRecAvailable(0);
                                inventSum.setQcAvailable(0);
                                inventSum.setPhysicalAvailable(qty);
                                inventSum.setPhysicalTotal(qty);
                            } else {
                                inventSum.setRecAvailable(qty);
                                inventSum.setQcAvailable(0);
                                inventSum.setPhysicalAvailable(0);
                                inventSum.setPhysicalTotal(qty);
                            }
                        } else {
                            inventSum.setPhysicalAvailable(qty);
                            inventSum.setPhysicalTotal(qty);
                            inventSum.setQcAvailable(0);
                            inventSum.setRecAvailable(0);
                        }
                    }
                    //clear qtys
                    inventSum.setPhysicalReserved(0.0);
                    inventSum.setOrderedAvailable(0.0);
                    inventSum.setOrderedReserved(0.0);
                    inventSum.setOrderedTotal(0.0);
                    inventSum.setPicked(0);
                    inventSum.setPosted(0.0);
                    inventSum.setReceived(0.0);
                    inventSum.setRegistered(0.0);
                    inventSum.setOrderedOut(0.0);
                } else {
                    throw new EMCStockException("Not enough stock available:" + inventSum.getItemId());
                }
                break;
            case DELIVERED:
                //Check Stock Taking
                checkStockTaking(trans.getItemId(), trans.getItemDimId(), userData);

                checkQty.addFieldAggregateFunction("physicalReserved", InventorySummary.class.getName(), "SUM");
                qRes = util.executeSingleResultQuery(checkQty, userData);
                if (qRes != null) {
                    availQty = Double.parseDouble(qRes.toString());
                }
                if (util.compareDouble(availQty - qty, 0) >= 0) {
                 if (InventoryLocationsEnum.RECEIVING_AREA.equals(InventoryLocationsEnum.fromString(inventSum.getLocation()))) {
                        inventSum.setRecAvailable(-1 * qty);
                        inventSum.setPhysicalTotal(-1 * qty);
                    } else {
                        //inventSum.setPhysicalReserved(inventSum.getPhysicalReserved()+qty);
                        inventSum.setPhysicalAvailable(-1 * qty);
                        inventSum.setPhysicalTotal(-1 * qty);
                    }

                    //clear qtys
                    inventSum.setPhysicalReserved(0.0);
                    inventSum.setOrderedAvailable(0.0);
                    inventSum.setOrderedReserved(0.0);
                    inventSum.setOrderedTotal(0.0);
                    inventSum.setPicked(0);
                    inventSum.setPosted(0.0);
                    inventSum.setReceived(0.0);
                    inventSum.setRegistered(0.0);
                    inventSum.setOrderedOut(0.0);
                } else {
                    throw new EMCStockException("Not enough stock available:" + referenceBean.checkItemReference(inventSum.getItemId(), userData)[1]);
                }
                break;
            case RETURN:
                //Check Stock Taking
                checkStockTaking(trans.getItemId(), trans.getItemDimId(), userData);

                qty = qty * -1.0; //TX Qty is possitve direction determines sign
                boolean rec = isAvailableInWarehouse(inventSum.getWarehouse(), inventSum.getLocation(), userData);
                if (rec) {
                    checkQty.addFieldAggregateFunction("physicalAvailable", InventorySummary.class.getName(), "SUM");
                } else {
                    if ("QC".equals(inventSum.getLocation())) {
                        checkQty.addFieldAggregateFunction("qcAvailable", InventorySummary.class.getName(), "SUM");
                    } else {
                        checkQty.addFieldAggregateFunction("recAvailable", InventorySummary.class.getName(), "SUM");
                    }

                }
                qRes = util.executeSingleResultQuery(checkQty, userData);
                if (qRes != null) {
                    availQty = Double.parseDouble(qRes.toString());
                }
                if (util.compareDouble(availQty + qty, 0) >= 0) {
                    if (rec) {
                        inventSum.setPhysicalAvailable(qty);
                        inventSum.setPhysicalTotal(qty);
                    } else {
                        if ("QC".equals(inventSum.getLocation())) {
                            inventSum.setQcAvailable(qty);
                            inventSum.setPhysicalTotal(qty);
                        } else {
                            inventSum.setRecAvailable(qty);
                            inventSum.setPhysicalTotal(qty);
                        }
                    }
                    //clear qtys
                    inventSum.setOrderedTotal(0);
                    inventSum.setOrderedAvailable(0);
                    inventSum.setPhysicalReserved(0.0);
                    inventSum.setOrderedReserved(0.0);
                    inventSum.setPicked(0);
                    inventSum.setPosted(0.0);
                    inventSum.setReceived(0.0);
                    inventSum.setRegistered(0.0);
                    inventSum.setOrderedOut(0.0);
                } else {
                    throw new EMCStockException("Not enough stock available:" + referenceBean.checkItemReference(inventSum.getItemId(), userData)[1]);
                }

                break;
            default:
                Logger.getLogger("emc").log(Level.SEVERE, "No logic implementation found for " + theOption.toString(), userData);
                break;
        }
        if (inventSum.getRecordID() == 0) {
            try {
                itemSumLocal.insert(inventSum, userData);
            } catch (EMCEntityBeanException entEx) {
                throw new EMCStockException("Could not insert on hand: " + entEx.getMessage());
            }
        } else {
            try {
                itemSumLocal.update(inventSum, userData);
            } catch (EMCEntityBeanException entEx) {
                throw new EMCStockException("Could not update on hand: " + entEx.getMessage());
            }

        }

        return inventSum;
    }

    /**
     * Reserves stock.
     *
     * @param refNumber Transaction reference number.
     * @param refType Type of the transaction reference.
     * @param transType Type of order.
     * @param itemId Id of item to be reserved.
     * @param dimensionId Dimension id
     * @param transId Transaction id of line being unreserved.
     * @param requiredQuantity Quantity required by the order/line for which
     * stock is being reserved.
     * @param reservationRule Reservation rule to use when reserving stock.
     * @param parameters Parameters to use when reserving stock. This will be
     * passed to the reservation rule.
     * @param orderedTx Ordered transaction. This is used when both an ordered
     * transaction and a reserved transaction are created within a single
     * container transaction. If this parameter is null, the system will attempt
     * to fetch an ordered transaction from the database.
     * @param unreserveOld A boolean used to specify whether stock which was
     * previously reserved against the given reference should be unreserved.
     * @param userData Userdata.
     * @return A boolean indicating whether stock was reserved successfully.
     * @throws EMCStockExceptionException
     */
    public boolean reserveStock(String refNumber, InventoryTransactionsRefType refType, InventoryTransactionTypes transType, String itemId, long dimensionId, String transId, double requiredQuantity, StockReservationRule reservationRule, Map<String, Object> parameters, InventoryTransactions orderTx, boolean unreserveOld, EMCUserData userData) throws EMCStockException {
        if (orderTx == null) {
            EMCQuery transQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
            transQuery.addAnd("itemDimId", dimensionId);
            transQuery.addAnd("itemId", itemId);
            transQuery.addAnd("status", InventoryTransactionStatus.ORDERED.toString());
            transQuery.addAnd("direction", InventoryTransactionDirection.OUT.toString());
            transQuery.addAnd("refNumber", refNumber);
            transQuery.addAnd("refType", refType);
            transQuery.addAnd("type", transType);

            if (transId == null) {
                if (EMCDebug.getDebug()) {
                    logMessage(Level.WARNING, "No trans id passed to unreserve method.", userData);
                }
            } else {
                transQuery.addAnd("transId", transId);
            }

            orderTx = (InventoryTransactions) util.executeSingleResultQuery(transQuery, userData);
        }

        if (orderTx == null) {
            throw new EMCStockException("Ordered transaction not found.");
        }

        if (unreserveOld) {
            //An exception will be thrown if this fails.
            unreserveStock(refNumber, refType, transType, itemId, dimensionId, transId, orderTx, userData);
        }

        //check to see if this line is really available
        EMCQuery verifySummary = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
        verifySummary.addFieldAggregateFunction("physicalAvailable", InventorySummary.class.getName(), "SUM");
        verifySummary.addAnd("itemId", itemId);
        verifySummary.addAnd("itemDimId", dimensionId);

        Double availableQuantity = (Double) util.executeSingleResultQuery(verifySummary, userData);

        if (util.compareDouble(availableQuantity, requiredQuantity) < 0) {
            throw new EMCStockException("Not enough stock available to reserve for item:" + referenceBean.checkItemReference(orderTx.getItemId(), userData)[1]);
        }

        //Subtract reserved from required to reserve only against outstanding quantity
        double quantityToReserve = reservationRule.calcRuleQuantityToReserve(availableQuantity, requiredQuantity, parameters, userData);

        InventoryTransactions reservedTx;

        //If ordered transaction is not yet saved, make it reserved tx.
        if (orderTx.getRecordID() == 0) {
            reservedTx = orderTx;
        } else {
            //Attempt to find reserved transaction.
            EMCQuery transQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
            transQuery.addAnd("itemDimId", dimensionId);
            transQuery.addAnd("itemId", itemId);

            //TODO Remove this code.  Backwards compatibility.
            transQuery.openConditionBracket(EMCQueryBracketConditions.AND);
            transQuery.addAnd("status", InventoryTransactionStatus.ORDERED.toString());
            transQuery.addOr("status", InventoryTransactionStatus.RESERVED.toString());
            transQuery.closeConditionBracket();

            transQuery.addAnd("direction", InventoryTransactionDirection.OUT.toString());
            transQuery.addAnd("refNumber", refNumber);
            transQuery.addAnd("refType", refType);
            transQuery.addAnd("type", transType);
            transQuery.addAnd("transId", transId);

            reservedTx = (InventoryTransactions) util.executeSingleResultQuery(transQuery, userData);

            if (reservedTx == null) {
                //Set values from ordered transaction.
                reservedTx = new InventoryTransactions();
            }
        }

        double orderedTxUnitCost = orderTx.getQuantity() == 0 ? 0 : orderTx.getCost() / orderTx.getQuantity();

        //If an existing reserve transaction was found, simply update quantity
        reservedTx.setQuantity(reservedTx.getQuantity() + quantityToReserve);
        reservedTx.setCost(reservedTx.getQuantity() * orderedTxUnitCost);

        try {
            if (reservedTx.getRecordID() == 0) {
                //These fields should be set on new reserved transactions
                reservedTx.setRefNumber(refNumber);
                reservedTx.setRefType(refType.toString());
                reservedTx.setDirection(InventoryTransactionDirection.OUT.toString());
                reservedTx.setType(transType.toString());
                reservedTx.setItemId(itemId);
                reservedTx.setItemDimId(dimensionId);
                reservedTx.setCustomerId(orderTx.getCustomerId());
                reservedTx.setDocumentDate(orderTx.getDocumentDate());
                reservedTx.setDocumentNo(orderTx.getDocumentNo());
                reservedTx.setTransId(orderTx.getTransId());
                reservedTx.setStatus(InventoryTransactionStatus.RESERVED.toString());
                reservedTx.setDirection(InventoryTransactionDirection.OUT.toString());
                reservedTx.setExpectedDate(orderTx.getExpectedDate());

                trans.insert(reservedTx, userData);
            } else {
                double qty = reservedTx.getQuantity();

                reservedTx.setQuantity(0);
                trans.update(reservedTx, userData);
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, reservedTx, userData);

                reservedTx.setQuantity(qty);
                trans.update(reservedTx, userData);
            }
        } catch (EMCEntityBeanException ex) {
            throw new EMCStockException("Failed to create Reserved Transaction for item: " + referenceBean.checkItemReference(orderTx.getItemId(), userData)[1]);
        }


        updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_RESERVED, reservedTx, userData);

        //Update ordered transaction
        orderTx.setQuantity(orderTx.getQuantity() - quantityToReserve);
        orderTx.setCost(orderTx.getQuantity() * orderedTxUnitCost);

        try {
            if (util.compareDouble(orderTx.getQuantity(), 0) == 0) {
                deleteTransactionAndOnHand(orderTx, userData);
            } else {
                trans.update(orderTx, userData);
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT, orderTx, userData);
            }
        } catch (EMCEntityBeanException ex) {
            throw new EMCStockException("Failed to update Ordered Transaction");
        }

        return true;
    }

    /**
     * Reserves stock against the default reservation rule.
     *
     * @param refNumber Transaction reference number.
     * @param refType Type of the transaction reference.
     * @param transType Type of order.
     * @param itemId Id of item to be reserved.
     * @param dimensionId Dimension id
     * @param transId Transaction id of line being unreserved.
     * @param requiredQuantity Quantity required by the order/line for which
     * stock is being reserved.
     * @param orderedTx Ordered transaction. This is used when both an ordered
     * transaction and a reserved transaction are created within a single
     * container transaction. If this parameter is null, the system will attempt
     * to fetch an ordered transaction from the database.
     * @param unreserveOld A boolean used to specify whether stock which was
     * previously reserved against the given reference should be unreserved.
     * @param userData Userdata.
     * @return A boolean indicating whether stock was reserved successfully.
     * @throws EMCStockExceptionException
     */
    public boolean reserveStock(String refNumber, InventoryTransactionsRefType refType, InventoryTransactionTypes transType, String itemId, long dimensionId, String transId, double requiredQuantity, InventoryTransactions orderTx, boolean unreserveOld, EMCUserData userData) throws EMCStockException {
        return reserveStock(refNumber, refType, transType, itemId, dimensionId, transId, requiredQuantity, new DefaultReservationRule(), null, orderTx, unreserveOld, userData);
    }

    /**
     * Unreserves stock against the specified reference.
     *
     * @param refNumber Transaction reference number.
     * @param refType Type of the transaction reference.
     * @param transType Type of order.
     * @param itemId Id of item to be reserved.
     * @param dimensionId Dimension id
     * @param transId Transaction id of line being unreserved.
     * @param orderedTx The original ordered transaction for the stock being
     * unreserved. This transaction be null; if it is null, stock will not be
     * reallocated to an ordered transaction. If the original ordered
     * transaction no longer exists, a new transaction should be created,
     * inserted and passed to this method. This method will update and save the
     * ordered transaction.
     * @param userData Userdata.
     * @return A boolean indicating whether stock was unreserved successfully.
     * @throws EMCStockExceptionException
     */
    public boolean unreserveStock(String refNumber, InventoryTransactionsRefType refType, InventoryTransactionTypes transType, String itemId, long dimensionId, String transId, InventoryTransactions orderedTx, EMCUserData userData) throws EMCStockException {
        double qtyUnreserved = 0d;

        EMCQuery transQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
        transQuery.addAnd("itemDimId", dimensionId);
        transQuery.addAnd("itemId", itemId);
        transQuery.addAnd("status", InventoryTransactionStatus.RESERVED.toString());
        transQuery.addAnd("direction", InventoryTransactionDirection.OUT.toString());
        transQuery.addAnd("refNumber", refNumber);
        transQuery.addAnd("refType", refType);
        transQuery.addAnd("type", transType);

        if (transId == null) {
            if (EMCDebug.getDebug()) {
                logMessage(Level.WARNING, "No trans id passed to unreserve method.", userData);
            }
        } else {
            transQuery.addAnd("transId", transId);
        }

        List<InventoryTransactions> reservedTransactions = (List<InventoryTransactions>) util.executeGeneralSelectQuery(transQuery, userData);

        for (InventoryTransactions reservedTx : reservedTransactions) {
            //Add quantity back to ordered transaction.
            qtyUnreserved += reservedTx.getQuantity();

            reservedTx.setQuantity(0);

            try {
                trans.update(reservedTx, userData);
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT, reservedTx, userData);
                deleteTransactionAndOnHand(reservedTx, userData);
            } catch (EMCEntityBeanException ex) {
                throw new EMCStockException("Failed to unreserve stock for item: " + referenceBean.checkItemReference(itemId, userData)[1]);
            }
        }

        if (orderedTx != null && util.compareDouble(qtyUnreserved, 0) > 0) {
            orderedTx.setQuantity(orderedTx.getQuantity() + qtyUnreserved);
            try {
                trans.update(orderedTx, userData);
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT, orderedTx, userData);
            } catch (EMCEntityBeanException ex) {
                throw new EMCStockException("Failed to unreserve stock for item: " + referenceBean.checkItemReference(itemId, userData)[1]);
            }
        }

        return true;
    }

    /**
     * Unreserves stock. Unreserves specified reserved transactions and adds
     * quantities back to ordered transaction.
     *
     * @param orderedTransaction Ordered transaction. Dimensions and item should
     * be set up correctly on transaction.
     * @param reservedTransactions Reserved transactions. These will be deleted.
     * Dimensions and item should be set up correctly on transactions.
     * @param userData User data
     * @return A boolean indicating success.
     * @throws EMCStockException
     */
    public boolean unreserveStock(InventoryTransactions orderedTransaction, List<InventoryTransactions> reservedTransactions, EMCUserData userData) throws EMCStockException {
        double qtyUnreserved = 0;
        for (InventoryTransactions reservedTx : reservedTransactions) {
            //Add quantity back to ordered transaction.
            qtyUnreserved += reservedTx.getQuantity();

            //reservedTx.setQuantity(0);

            try {
                trans.update(reservedTx, userData);
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT_UNRESERVED, reservedTx, userData);
                deleteTransactionAndOnHand(reservedTx, userData);
            } catch (EMCEntityBeanException ex) {
                throw new EMCStockException("Failed to unreserve stock for item: " + referenceBean.checkItemReference(orderedTransaction.getItemId(), userData)[1]);
            }
        }

        if (orderedTransaction != null && util.compareDouble(qtyUnreserved, 0) > 0) {
            orderedTransaction.setQuantity(orderedTransaction.getQuantity() + qtyUnreserved);
            try {
                trans.update(orderedTransaction, userData);
                updateOnHand(InventorySummaryUpdateOptions.ORDERED_OUT, orderedTransaction, userData);
            } catch (EMCEntityBeanException ex) {
                throw new EMCStockException("Failed to unreserve stock for item: " + referenceBean.checkItemReference(orderedTransaction.getItemId(), userData)[1]);
            }
        }

        return true;
    }

    private void checkStockTaking(String itemId, long dimensionId, EMCUserData userData) throws EMCStockException {
        String existingJournal = stockTakeLoggerBean.checkStockTakeRecord(itemId, dimensionId, userData);
        if (existingJournal != null) {
            throw new EMCStockException("Stock Take is currently in progress - See journal " + existingJournal + ".");
        }
    }

    public EMCTable updateBlanketOrderOnHand(RequirementsPlanningReferenceTypes referenceType, long referenceRecordID, String item, String dimension1, String dimension2, String dimension3, String warehouse, BigDecimal quantity, String uom, EMCUserData userData) throws EMCStockException {
        long itemDimId = dimIdLocal.getDimRecordId(dimension1, dimension2, dimension3, warehouse, userData);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        query.addAnd("referenceRecordID", referenceRecordID);
        query.addAnd("itemId", item);
        query.addAnd("itemDimId", itemDimId);
        InventorySummary summary = (InventorySummary) util.executeSingleResultQuery(query, userData);

        try {
            quantity = uomConv.convertToItemBase(item, quantity, uom, userData);
        } catch (Exception ex) {
            throw new EMCStockException(ex.getMessage());
        }

        if (summary == null) {
            summary = new InventorySummary();
            summary.setReferenceRecordID(referenceRecordID);
            summary.setItemId(item);
            summary.setItemDimId(itemDimId);
            summary.setDimension1(dimension1);
            summary.setDimension2(dimension2);
            summary.setDimension3(dimension3);
            summary.setWarehouse(warehouse);
        }

        switch (referenceType) {
            case SALES_ORDER:
                summary.setQtySOPBlanketOrder((summary.getQtySOPBlanketOrder() == null ? BigDecimal.ZERO : summary.getQtySOPBlanketOrder()).add(quantity));
                if (util.compareDouble(summary.getQtySOPBlanketOrder().doubleValue(), 0) <= 0) {
                    //Delete
                    if (summary.getRecordID() != 0) {
                        try {
                            itemSumLocal.delete(summary, userData);
                        } catch (EMCEntityBeanException ex) {
                            throw new EMCStockException(ex.getMessage());
                        }
                    }
                } else {
                    if (summary.getRecordID() == 0) {
                        try {
                            itemSumLocal.insert(summary, userData);
                        } catch (EMCEntityBeanException ex) {
                            throw new EMCStockException(ex.getMessage());
                        }
                    } else {
                        try {
                            itemSumLocal.update(summary, userData);
                        } catch (EMCEntityBeanException ex) {
                            throw new EMCStockException(ex.getMessage());
                        }
                    }
                }
                break;
            case PURCHASE_ORDER:
                summary.setQtyOSBlanketOrd(summary.getQtyOSBlanketOrd() + quantity.doubleValue());
                if (util.compareDouble(summary.getQtyOSBlanketOrd(), 0) <= 0) {
                    //Delete
                    if (summary.getRecordID() != 0) {
                        try {
                            itemSumLocal.delete(summary, userData);
                        } catch (EMCEntityBeanException ex) {
                            throw new EMCStockException(ex.getMessage());
                        }
                    }
                } else {
                    if (summary.getRecordID() == 0) {
                        try {
                            itemSumLocal.insert(summary, userData);
                        } catch (EMCEntityBeanException ex) {
                            throw new EMCStockException(ex.getMessage());
                        }
                    } else {
                        try {
                            itemSumLocal.update(summary, userData);
                        } catch (EMCEntityBeanException ex) {
                            throw new EMCStockException(ex.getMessage());
                        }
                    }
                }
                break;
            default:
                throw new EMCStockException(referenceType.toString() + " may not have blanket orders.");
        }
        return summary;
    }
}

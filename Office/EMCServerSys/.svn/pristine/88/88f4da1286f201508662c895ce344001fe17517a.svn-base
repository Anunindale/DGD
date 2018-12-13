/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.transactions.pop;

import emc.bus.base.logic.EMCUOMConversionException;
import emc.bus.inventory.dimensions.InventoryItemDimensionCombinationsLocal;
import emc.bus.inventory.dimensions.additionaldimensions.InventoryAdditionalDimensionsLocal;
import emc.bus.inventory.requirementsplanning.InventoryRequirementsPlanningLocal;
import emc.bus.inventory.serialbatch.InventorySerialBatchLocal;
import emc.bus.inventory.transactions.EMCStockException;
import emc.bus.inventory.transactions.InventorySummaryLocal;
import emc.bus.inventory.transactions.TransactionsSuperClass;
import emc.bus.inventory.transactions.datasource.InventorySummaryDSLocal;
import emc.bus.pop.cancelledpurchaseorders.POPCancelledPurchaseOrderMasterLocal;
import emc.entity.inventory.InventoryCostingGroup;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.dimensions.additionaldimensions.InventoryAdditionalDimensions;
import emc.entity.inventory.requirementsplanning.InventoryRequirementsPlanning;
import emc.entity.inventory.serialbatch.InventoryRemoveSerialBatch;
import emc.entity.inventory.serialbatch.InventorySerialBatch;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.inventory.transactions.datasource.InventorySummaryDS;
import emc.entity.pop.POPParameters;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.pop.journals.POPSupplierReceivedJournalLines;
import emc.entity.pop.journals.POPSupplierReceivedJournalMaster;
import emc.entity.pop.posting.POPPurchasePostLines;
import emc.entity.pop.posting.POPPurchasePostMaster;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.costing.CostLevel;
import emc.enums.inventory.inventorytables.InventoryItemTypes;
import emc.enums.inventory.requirementsplanning.RequirementsPlanningReferenceTypes;
import emc.enums.inventory.transactions.InventorySummaryUpdateOptions;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.enums.inventory.transactions.InventoryTransactionStatus;
import emc.enums.inventory.transactions.InventoryTransactionTypes;
import emc.enums.inventory.transactions.InventoryTransactionsRefType;
import emc.enums.pop.posting.SBType;
import emc.enums.pop.supplierreceivedjournals.ReceivedJournalType;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerPOPMessageEnum;
import emc.tables.EMCTable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author rico
 */
@Stateless
public class POPTransactionsLogicBean extends TransactionsSuperClass implements POPTransactionsLogicBeanLocal {
    
    @EJB
    private POPCancelledPurchaseOrderMasterLocal cancelMasterBean;
    @EJB
    private InventorySerialBatchLocal SBBean;
    @EJB
    private emc.bus.pop.POPPurchaseOrderLinesLocal poLine;
    @EJB
    private emc.bus.pop.posting.POPPurchasePostLinesLocal postLinesBean;
    @EJB
    private emc.bus.pop.journals.POPSupplierReceivedJournalLinesLocal recJLine;
    @EJB
    private emc.bus.pop.journals.POPSupplierReceivedJournalMasterLocal recJMast;
    @EJB
    private emc.bus.pop.POPSupplierLocal suppBean;
    @EJB
    private emc.bus.inventory.InventoryItemMasterLocal itemMasterBean;
    @EJB
    private emc.bus.inventory.dimensions.InventoryItemDimensionCombinationsLocal combinationsBean;
    @EJB
    private emc.bus.inventory.logic.InventoryItemDimensionCombinationLogicLocal combinationLogicBean;
    @EJB
    private InventoryItemDimensionCombinationsLocal combBean;
    @EJB
    private InventoryAdditionalDimensionsLocal additionalDimensionsBean;
    @EJB
    private InventorySummaryDSLocal summaryDSBean;
    @EJB
    private InventorySummaryLocal summaryBean;
    @EJB
    private InventoryRequirementsPlanningLocal requirementsPlanningBean;

    /**
     *
     * @param theLine
     * @param theHeader
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    public EMCTable postPOLine(POPPurchaseOrderLines theLine, boolean setQtyToZero, EMCUserData userData) throws EMCStockException {
        InventoryItemTypes itemType = itemMasterBean.getItemType(theLine.getItemId(), userData);
        
        switch (itemType) {
            case BOM:  //Fall through
            case ITEM:
                return postItemPOLine(theLine, setQtyToZero, userData);
            case SERVICE:
                return postServicePOLine(theLine, userData);
            
            default:
                //Do nothing
                return theLine;
        }
    }
    
    private EMCTable postServicePOLine(POPPurchaseOrderLines theLine, EMCUserData userData) {
        //For now, do nothing.
        return theLine;
    }

    /**
     * Posts a Purchase Order line with an item of the 'Item' item type.
     *
     * @param theLine Purchase Order line to post.
     * @param setQtyToZero ?
     * @param userData User data.
     * @return A transaction for the posted line.
     * @throws EMCEntityBeanException
     */
    private EMCTable postItemPOLine(POPPurchaseOrderLines theLine, boolean setQtyToZero, EMCUserData userData) throws EMCStockException {
        //test transId
        Object orderTx = null;
        if (this.isBlank(theLine.getTransactionNumber())) {
            Logger.getLogger("emc").log(Level.SEVERE, "No Transaction Id found for PO line, could not generate Stock transactions", userData);
        } else {
            EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.transactions.InventoryTransactions.class.getName());
            qu.addAnd("transId", theLine.getTransactionNumber());
            //qu.addAnd("itemDimId", dimIdLocal.getDimRecordId(theLine.getItemDimension1(), theLine.getItemDimension2(), theLine.getItemDimension3(), theLine.getWarehouse(), userData));
            //qu.addAnd("itemId", theLine.getItemId());
            qu.addAnd("refNumber", theLine.getPurchaseOrderId());
            qu.addAnd("status", InventoryTransactionStatus.ORDERED.toString());
            
            orderTx = util.executeSingleResultQuery(qu, userData);
            qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.pop.POPPurchaseOrderMaster.class.getName());
            qu.addAnd("purchaseOrderId", theLine.getPurchaseOrderId());
            POPPurchaseOrderMaster puMast = (POPPurchaseOrderMaster) util.executeSingleResultQuery(qu, userData);
            double poLineQtyInBaseUOM = 0;
            double poItemsReceivedInBaseUOM = 0;
            double persistedQty = 0.0;
            double persistedReceivedQty = 0.0;
            String persistedUOM = theLine.getUom();
            if (setQtyToZero) {
                persistedQty = theLine.getQuantity();
                theLine.setQuantity(0.0);
            } else {
                POPPurchaseOrderLines oldLine = (POPPurchaseOrderLines) util.findPersisted(theLine.getClass(), theLine.getRecordID(), userData);
                persistedQty = oldLine.getQuantity();
                persistedReceivedQty = oldLine.getItemsReceived();
            }
            try {
                poLineQtyInBaseUOM = uomConv.convertToItemBase(theLine.getItemId(), theLine.getQuantity(), theLine.getUom(), userData);
                poItemsReceivedInBaseUOM = uomConv.convertToItemBase(theLine.getItemId(), theLine.getItemsReceived(), theLine.getUom(), userData);
                
            } catch (EMCUOMConversionException ex) {
                throw new EMCStockException("Could not convert quantity " + ex.getMessage());
            }
            
            if (orderTx == null) {
                InventoryTransactions newTx = new InventoryTransactions();
                newTx.setTransId(theLine.getTransactionNumber());
                newTx.setCompanyId(util.getCompanyId(emc.entity.inventory.transactions.InventoryTransactions.class.getName(), userData));
                newTx.setDirection(InventoryTransactionDirection.IN.toString());
                newTx.setType(InventoryTransactionTypes.PO.toString());
                newTx.setRefType(InventoryTransactionsRefType.Purchase_Order.toString());
                newTx.setRefNumber(theLine.getPurchaseOrderId());
                newTx.setStatus(InventoryTransactionStatus.ORDERED.toString());
                if (puMast != null) {
                    newTx.setSupplierId(puMast.getSupplier());
                }
                newTx.setQuantity(poLineQtyInBaseUOM - poItemsReceivedInBaseUOM);
                newTx.setItemDimId(dimIdLocal.getDimRecordId(theLine.getItemDimension1(), theLine.getItemDimension2(), theLine.getItemDimension3(),
                                                             theLine.getWarehouse(), userData));
                newTx.setItemId(theLine.getItemId());
                newTx.setCost(theLine.getNetAmount());
                newTx.setExpectedDate(theLine.getDeliveryDate());
                
                try {
                    if (util.compareDouble(newTx.getQuantity(), 0) != 0) {
                        trans.insert(newTx, userData);
                        updateOnHand(InventorySummaryUpdateOptions.ORDERED_IN, newTx, userData);
                        orderTx = newTx;
                    }
                    
                } catch (EMCEntityBeanException entBex) {
                    throw new EMCStockException("Could not insert new inventory transaction " + entBex.getMessage());
                }
                
                
            } else {
                InventoryTransactions tx = (InventoryTransactions) orderTx;
                tx.setExpectedDate(theLine.getDeliveryDate());
                //get Previous PO line

                double persistedInBaseUOM = 0;
                double persistedReceivedInBaseUOM = 0;
                try {
                    persistedInBaseUOM = uomConv.convertToItemBase(theLine.getItemId(), persistedQty, persistedUOM, userData);
                    persistedReceivedInBaseUOM = uomConv.convertToItemBase(theLine.getItemId(), persistedReceivedQty, persistedUOM, userData);
                } catch (EMCUOMConversionException ex) {
                    throw new EMCStockException("Could not convert quantity " + ex.getMessage());
                }
                double delta = poLineQtyInBaseUOM - persistedInBaseUOM;
                tx.setQuantity(tx.getQuantity() + delta);
                tx.setCost(tx.getQuantity() * theLine.getItemPrice() - tx.getQuantity() * theLine.getItemPrice() * theLine.getDiscountPercentage() / 100.0);
                tx.setItemDimId(dimIdLocal.getDimRecordId(theLine.getItemDimension1(), theLine.getItemDimension2(), theLine.getItemDimension3(),
                                                          theLine.getWarehouse(), userData));
                tx.setItemId(theLine.getItemId());
                
                if (util.compareDouble(tx.getQuantity(), 0) < 0) {
                    throw new EMCStockException("Negative Order Qty not allowed");
                }
                try {
                    if (util.compareDouble(tx.getQuantity(), 0) != 0) {
                        trans.update(tx, userData);
                        updateOnHand(InventorySummaryUpdateOptions.ORDERED_IN, tx, userData);
                    } else {
                        trans.delete(tx, userData);
                        //find transaction oldSummary
                        EMCQuery inventSumQu = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
                        inventSumQu.addAnd("inventoryTransRef", tx.getRecordID());
                        InventorySummary sumTx = (InventorySummary) util.executeSingleResultQuery(inventSumQu, userData);
                        itemSumLocal.delete(sumTx, userData);
                    }
                    
                } catch (EMCEntityBeanException entBex) {
                    throw new EMCStockException("Could not update Order stock transaction " + entBex.getMessage());
                }
                
            }
        }
        return (EMCTable) orderTx;
    }

    /**
     * Returns stock to the supplier
     *
     * @param postMaster
     * @param receiveLater
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    public EMCTable postReturnToSupplier(POPPurchasePostMaster postMaster, boolean receiveLater, EMCUserData userData) throws EMCStockException {
        //Get PO Master
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class.getName());
        query.addAnd("purchaseOrderId", postMaster.getPurchaseOrderId());
        POPPurchaseOrderMaster PO = (POPPurchaseOrderMaster) util.executeSingleResultQuery(query, userData);

        //Get Post Lines
        query = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostLines.class.getName());
        query.addAnd("postMasterId", postMaster.getPostMasterId());
        query.addAnd("companyId", util.getCompanyId(POPPurchasePostLines.class.getName(), userData));
        query.addAnd("quantity", 0, EMCQueryConditions.NOT);
        List<POPPurchasePostLines> postLines = util.executeGeneralSelectQuery(query, userData);

        //Get PO Lines
        query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class.getName());
        query.addTableAnd(POPPurchasePostMaster.class.getName(), "purchaseOrderId", POPPurchaseOrderLines.class.getName(), "purchaseOrderId");
        query.addAnd("postMasterId", postMaster.getPostMasterId(), POPPurchasePostMaster.class.getName());
        List poList = util.executeGeneralSelectQuery(query, userData);
        if (poList.isEmpty()) {
            return postMaster;
        }
        Map<String, POPPurchaseOrderLines> POLinesMap = new HashMap<String, POPPurchaseOrderLines>();
        POPPurchaseOrderLines purchaseOrderLine;
        for (Object o : poList) {
            purchaseOrderLine = (POPPurchaseOrderLines) o;
            POLinesMap.put(purchaseOrderLine.getTransactionNumber(), purchaseOrderLine);
        }

        //Loops through post lines
        for (POPPurchasePostLines pline : postLines) {
            InventoryItemTypes itemType = itemMasterBean.getItemType(pline.getItemId(), userData);
            
            switch (itemType) {
                case BOM: //Fall through
                case ITEM:
                    postReturnItemToSupplier(postMaster, pline, POLinesMap, poList, receiveLater, userData);
                    break;
                case SERVICE:
                    postReturnServiceToSupplier(postMaster, pline, POLinesMap, poList, receiveLater, userData);
                    break;
            }
        }
        
        boolean createJournal = false;
        for (POPPurchasePostLines line : postLines) {
            if (util.compareDouble(line.getQuantity(), 0) != 0) {
                createJournal = true;
                break;
            }
        }
        //create Receive Journal
        if (createJournal) {
            if (!createRecieveNoteJournal(postMaster, postLines, PO.getPurchaseOrderId(), PO.getSupplierOrderNumber(), ReceivedJournalType.RETURN_GOODS, userData)) {
                throw new EMCStockException("Could not insert receive Journal");
            }
        }
        if (!receiveLater) {
            if (poList.size() == 0) {
                try {
                    cancelMasterBean.cancelPurchaseOrder(PO, userData);
                } catch (EMCEntityBeanException ex) {
                    throw new EMCStockException("Could not cancel Purchase Order.");
                }
            }
        }
        return postMaster;
    }

    /**
     * Posts a Purchase Order line with an item of the 'Item' item type.
     *
     * @param postMaster Post master record.
     * @param pline Post Lines record.
     * @param POLinesMap Map of purchase order lines.
     * @param poList List of purchase order lines.
     * @param receiveLater Indicates whether stock should be received again
     * later.
     * @param userData User data.
     * @return The line that has been posted.
     * @throws EMCStockException
     */
    private EMCTable postReturnServiceToSupplier(POPPurchasePostMaster postMaster, POPPurchasePostLines pline, Map<String, POPPurchaseOrderLines> POLinesMap, List<POPPurchaseOrderLines> poList, boolean receiveLater, EMCUserData userData) throws EMCStockException {
        try {
            if (util.compareDouble(pline.getQuantity(), 0) == 0) {
                return pline;
            }
            //Get PO Line
            POPPurchaseOrderLines purchaseOrderLine = POLinesMap.get(pline.getTransactionNumber());
            //Checks Qty returned
            if (util.compareDouble((purchaseOrderLine.getItemsReceived() + purchaseOrderLine.getOverReceiveQty()) - pline.getQuantity(), 0) < 0) {
                throw new EMCStockException("You may not return more items than you received.");
            }
            
            if (util.compareDouble(purchaseOrderLine.getOverReceiveQty(), pline.getQuantity()) >= 0) {
                purchaseOrderLine.setOverReceiveQty(purchaseOrderLine.getOverReceiveQty() - pline.getQuantity());
            } else {
                purchaseOrderLine.setItemsReceived(purchaseOrderLine.getItemsReceived() - (pline.getQuantity() - purchaseOrderLine.getOverReceiveQty()));
                purchaseOrderLine.setOverReceiveQty(0);
            }
            
            poLine.update(purchaseOrderLine, userData);

            //Makes post line quantity negative
            pline.setQuantity(pline.getQuantity() * -1);
            
            postLinesBean.update(pline, userData);
            
            return pline;
        } catch (Exception ex) {
            throw new EMCStockException(ex.getMessage());
        }
    }

    /**
     * Posts a Purchase Order line with an item of the 'Item' item type.
     *
     * @param postMaster Post master record.
     * @param pline Post Lines record.
     * @param POLinesMap Map of purchase order lines.
     * @param poList List of purchase order lines.
     * @param receiveLater Indicates whether stock should be received again
     * later.
     * @param userData User data.
     * @return The line that has been posted.
     * @throws EMCStockException
     */
    private EMCTable postReturnItemToSupplier(POPPurchasePostMaster postMaster, POPPurchasePostLines pline, Map<String, POPPurchaseOrderLines> POLinesMap, List<POPPurchaseOrderLines> poList, boolean receiveLater, EMCUserData userData) throws EMCStockException {
        //Just some variables
        List<InventoryRemoveSerialBatch> SBList;
        InventoryTransactions newTx;
        InventoryTransactions txTemplate;
        boolean canceled = false;
        long theDimId = 0;
        if (util.compareDouble(pline.getQuantity(), 0) == 0) {
            return pline;
        }
        //Get PO Line
        POPPurchaseOrderLines purchaseOrderLine = POLinesMap.get(pline.getTransactionNumber());
        //Checks Qty returned
        if (util.compareDouble((purchaseOrderLine.getItemsReceived() + purchaseOrderLine.getOverReceiveQty()) - pline.getQuantity(), 0) < 0) {
            throw new EMCStockException("You may not return more items than you received.");
        }
        //Fetch Serial/Batch Data
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRemoveSerialBatch.class.getName());
        query.addAnd("transId", pline.getTransactionNumber());
        query.addAnd("masterId", postMaster.getPostMasterId());
        query.addAnd("type", SBType.PURCHASEORDER);
        SBList = util.executeGeneralSelectQuery(query, userData);
        //Setup Summay Selection Query
        query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        query.addAnd("itemId", purchaseOrderLine.getItemId());
        query.addAnd("dimension1", purchaseOrderLine.getItemDimension1());
        query.addAnd("dimension2", purchaseOrderLine.getItemDimension2());
        query.addAnd("dimension3", purchaseOrderLine.getItemDimension3());
        query.addAnd("warehouse", purchaseOrderLine.getWarehouse());
        EMCQuery summaryQuery;
        EMCQuery qcStatusQuery;
        //Summary Record
        List<InventorySummary> summaryList;
        Collection<InventorySummaryDS> summaryDSList;
        InventorySummaryDS summaryDS;
        InventoryTransactions summeryTransaction;
        InventoryTransactions splitTransaction;
        InventorySummary splitSummary;
        double summaryQty;
        double qtyToReturn;
        //Sets up newTx template
        txTemplate = new InventoryTransactions();
        txTemplate.setTransId(pline.getTransactionNumber());
        txTemplate.setCompanyId(util.getCompanyId(emc.entity.inventory.transactions.InventoryTransactions.class.getName(), userData));
        txTemplate.setDirection(InventoryTransactionDirection.OUT.toString());
        txTemplate.setType(InventoryTransactionTypes.PO.toString());
        txTemplate.setRefType(InventoryTransactionsRefType.Return.toString());
        txTemplate.setRefNumber(purchaseOrderLine.getPurchaseOrderId());
        txTemplate.setStatus(InventoryTransactionStatus.RETURNED_GOODS.toString());
        txTemplate.setItemDimId(theDimId);
        txTemplate.setItemId(purchaseOrderLine.getItemId());
        txTemplate.setDocumentNo(postMaster.getDocumentNumber());
        txTemplate.setPhysicalDate(postMaster.getPostDate());
        txTemplate.setSupplierId(postMaster.getSupplierId());
        txTemplate.setDocumentDate(Functions.nowDate());
        if (SBList.size() > 0) {
            //Has Serial/Batch
            for (InventoryRemoveSerialBatch curPopSer : SBList) {
                //Update Summary Query
                summaryQuery = query.copyQuery();
                summaryQuery.addAnd("location", curPopSer.getLocation());
                summaryQuery.addAnd("serialNo", curPopSer.getSerial());
                summaryQuery.addAnd("batch", curPopSer.getBatch());
             

                //Fetch Grouped summry record
                userData.setUserData(0, summaryQuery);
                summaryDSList = summaryDSBean.getDataInRange(InventorySummaryDS.class, userData, 0, 1);
                if (summaryDSList == null || summaryDSList.isEmpty()) {
                    throw new EMCStockException("Failed to find matching summary records.");
                }
                summaryDS = summaryDSList.iterator().next();
                //Get correct quantity
                if ("QC".equalsIgnoreCase(curPopSer.getLocation())) {
                    summaryQty = summaryDS.getQcAvailable();
                } else if ("REC".equalsIgnoreCase(summaryDS.getLocation())) {
                    summaryQty = summaryDS.getRecAvailable();
                } else {
                    summaryQty = summaryDS.getPhysicalAvailable();
                }
                //Check Stock Levels
                if (util.compareDouble(curPopSer.getQuantity(), summaryQty) > 0) {
                    throw new EMCStockException("Not enough stock available");
                }
                //Update QCStatus on Summary
                qcStatusQuery = query.copyQuery();
                qcStatusQuery.addAnd("location", curPopSer.getLocation());
                qcStatusQuery.addAnd("serialNo", curPopSer.getSerial());
                qcStatusQuery.addAnd("batch", curPopSer.getBatch());
                qcStatusQuery.addOrderBy("QCStatus", InventorySummary.class.getName(), EMCQueryOrderByDirections.DESC);
                summaryList = util.executeGeneralSelectQuery(qcStatusQuery, userData);
                qtyToReturn = curPopSer.getQuantity();
                try {
                    for (InventorySummary summary : summaryList) {
                        //Get Qty on summary
                        if ("QC".equalsIgnoreCase(curPopSer.getLocation())) {
                            summaryQty = summary.getQcAvailable();
                        } else if ("REC".equalsIgnoreCase(summaryDS.getLocation())) {
                            summaryQty = summary.getRecAvailable();
                        } else {
                            summaryQty = summary.getPhysicalAvailable();
                        }
                        //Compare summary qty to qty to return
                        if (util.compareDouble(qtyToReturn, summaryQty) < 0) {
                            //Split Transaction
                            qcStatusQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
                            qcStatusQuery.addAnd("recordID", summary.getInventoryTransRef());
                            summeryTransaction = (InventoryTransactions) util.executeSingleResultQuery(qcStatusQuery, userData);
                            splitTransaction = (InventoryTransactions) doClone(summeryTransaction, userData);
                            summeryTransaction.setQuantity(summaryQty - qtyToReturn);
                            summeryTransaction.setPhysicalDate(postMaster.getPostDate());
                            splitTransaction.setPhysicalDate(postMaster.getPostDate());
                            splitTransaction.setQuantity(qtyToReturn);
                            trans.update(summeryTransaction, userData);
                            trans.insert(splitTransaction, userData);
                            //Split Summary
                            splitSummary = (InventorySummary) doClone(summary, userData);
                            if ("QC".equalsIgnoreCase(curPopSer.getLocation())) {
                                summary.setQcAvailable(qtyToReturn);
                                splitSummary.setQcAvailable(summaryQty - qtyToReturn);
                                summary.setPhysicalTotal(qtyToReturn);
                                splitSummary.setPhysicalTotal(summaryQty - qtyToReturn);
                            } else if ("REC".equalsIgnoreCase(summaryDS.getLocation())) {
                                summary.setRecAvailable(qtyToReturn);
                                splitSummary.setRecAvailable(summaryQty - qtyToReturn);
                                summary.setPhysicalTotal(qtyToReturn);
                                splitSummary.setPhysicalTotal(summaryQty - qtyToReturn);
                            } else {
                                summary.setPhysicalAvailable(qtyToReturn);
                                splitSummary.setPhysicalAvailable(summaryQty - qtyToReturn);
                                summary.setPhysicalTotal(qtyToReturn);
                                splitSummary.setPhysicalTotal(summaryQty - qtyToReturn);
                            }
                            summary.setInventoryTransRef(splitTransaction.getRecordID());
                            summaryBean.insert(splitSummary, userData);
                            summaryQty = qtyToReturn;
                        }
                        summaryBean.update(summary, userData);
                        //Break when qtyToReturn is consumed
                        qtyToReturn -= summaryQty;
                        if (util.compareDouble(qtyToReturn, 0) == 0) {
                            break;
                        }
                    }
                } catch (EMCEntityBeanException e) {
                    throw new EMCStockException("Failed to update QCStatus on summary: " + e.getMessage());
                }
                //Get Dimension Id
                theDimId = dimIdLocal.getDimRecordId(curPopSer.getBatch(), purchaseOrderLine.getItemDimension1(), purchaseOrderLine.getItemDimension2(),
                                                     purchaseOrderLine.getItemDimension3(), purchaseOrderLine.getWarehouse(), curPopSer.getLocation(), null, curPopSer.getSerial(), userData);

                //Creates new return trans
                newTx = (InventoryTransactions) doClone(txTemplate, userData);
                newTx.setItemDimId(theDimId);
                try {
                    newTx.setQuantity(uomConv.convertToItemBase(purchaseOrderLine.getItemId(), curPopSer.getQuantity(), purchaseOrderLine.getUom(), userData));
                } catch (EMCUOMConversionException ex) {
                    throw new EMCStockException("Failed to do UOM Conversion: " + ex.getMessage());
                }
                newTx.setCost(newTx.getQuantity() * purchaseOrderLine.getItemPrice() - newTx.getQuantity() * purchaseOrderLine.getItemPrice() * purchaseOrderLine.getDiscountPercentage() / 100.0);
                //Saves newTx
                try {
                    if (util.compareDouble(newTx.getQuantity(), 0) != 0) {
                        trans.insert(newTx, userData);
                        updateOnHand(InventorySummaryUpdateOptions.RETURN, newTx, userData);
                    }
                } catch (EMCEntityBeanException entBex) {
                    throw new EMCStockException("Could not insert new inventory transaction " + entBex.getMessage());
                }
                //Deletes Serial Batch Record
                InventoryItemDimensionGroup dimGroup = itemMasterBean.getItemDimensionGroupRecord(purchaseOrderLine.getItemId(), userData);
                if (dimGroup.getBatchNumberActive() && dimGroup.getSerialNumberActive()) {
                    try {
                        SBBean.deleteSB(curPopSer, userData);
                    } catch (EMCEntityBeanException delEx) {
                        throw new EMCStockException("Could not delete serial and batch data: " + delEx.getMessage());
                    }
                }
            }
        } else {
            //Fetch Grouped summry record
            userData.setUserData(0, query);
            summaryDSList = summaryDSBean.getDataInRange(InventorySummaryDS.class, userData, 0, 1);
            if (summaryDSList == null || summaryDSList.isEmpty()) {
                throw new EMCStockException("Failed to find matching summary records.");
            }
            summaryDS = summaryDSList.iterator().next();
            //Get correct quantity
            summaryQty = summaryDS.getPhysicalAvailable();
            //Check Stock Levels
            if (util.compareDouble(pline.getQuantity(), summaryQty) > 0) {
                throw new EMCStockException("Not enough stock available");
            }
            //Update QCStatus on Summary
            qcStatusQuery = query.copyQuery();
            qcStatusQuery.addOrderBy("QCStatus", InventorySummary.class.getName(), EMCQueryOrderByDirections.DESC);
            summaryList = util.executeGeneralSelectQuery(qcStatusQuery, userData);
            qtyToReturn = pline.getQuantity();
            try {
                for (InventorySummary summary : summaryList) {
                    summaryQty = summary.getPhysicalAvailable();
                    //Compare summary qty to qty to return
                    if (util.compareDouble(qtyToReturn, summaryQty) < 0) {
                        //Split Transaction
                        qcStatusQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
                        qcStatusQuery.addAnd("recordID", summary.getInventoryTransRef());
                        summeryTransaction = (InventoryTransactions) util.executeSingleResultQuery(qcStatusQuery, userData);
                        splitTransaction = (InventoryTransactions) doClone(summeryTransaction, userData);
                        summeryTransaction.setQuantity(summaryQty - qtyToReturn);
                        splitTransaction.setQuantity(qtyToReturn);
                        trans.update(summeryTransaction, userData);
                        trans.insert(splitTransaction, userData);
                        //Split Summary
                        splitSummary = (InventorySummary) doClone(summary, userData);
                        summary.setPhysicalAvailable(qtyToReturn);
                        splitSummary.setPhysicalAvailable(summaryQty - qtyToReturn);
                        summary.setPhysicalTotal(qtyToReturn);
                        splitSummary.setPhysicalTotal(summaryQty - qtyToReturn);
                        summary.setInventoryTransRef(splitTransaction.getRecordID());
                        summaryBean.insert(splitSummary, userData);
                    }
                    summaryBean.update(summary, userData);
                    //Break when qtyToReturn is consumed
                    qtyToReturn -= summaryQty;
                    if (util.compareDouble(qtyToReturn, summaryQty) == 0) {
                        break;
                    }
                }
            } catch (EMCEntityBeanException e) {
                throw new EMCStockException("Failed to update QCStatus on summary: " + e.getMessage());
            }
            //Get Dimension Id
            theDimId = dimIdLocal.getDimRecordId(null, purchaseOrderLine.getItemDimension1(), purchaseOrderLine.getItemDimension2(),
                                                 purchaseOrderLine.getItemDimension3(), purchaseOrderLine.getWarehouse(), null, null, null, userData);
            //Creates new return trans
            newTx = (InventoryTransactions) doClone(txTemplate, userData);
            newTx.setItemDimId(theDimId);
            try {
                newTx.setQuantity(uomConv.convertToItemBase(purchaseOrderLine.getItemId(), pline.getQuantity(), purchaseOrderLine.getUom(), userData));
            } catch (EMCUOMConversionException ex) {
                throw new EMCStockException("Failed to do UOM Conversion: " + ex.getMessage());
            }
            newTx.setCost(newTx.getQuantity() * purchaseOrderLine.getItemPrice() - newTx.getQuantity() * purchaseOrderLine.getItemPrice() * purchaseOrderLine.getDiscountPercentage() / 100.0);
            //Saves newTx
            try {
                if (util.compareDouble(newTx.getQuantity(), 0) != 0) {
                    trans.insert(newTx, userData);
                    updateOnHand(InventorySummaryUpdateOptions.RETURN, newTx, userData);
                }
            } catch (EMCEntityBeanException entBex) {
                throw new EMCStockException("Could not insert new inventory transaction " + entBex.getMessage());
            }
        }
        //Makes post line quantity negative
        pline.setQuantity(pline.getQuantity() * -1);
        //Handles replacement stock
        if (receiveLater) {
            updateOrderedTrans(pline, postMaster.getSupplierId(), purchaseOrderLine.getItemPrice(), purchaseOrderLine.getDiscountPercentage(), userData);
        } else {
            //All Stock has been received
            if ((util.compareDouble(purchaseOrderLine.getQuantity(), pline.getQuantity() * -1) == 0 && purchaseOrderLine.getOverReceiveQty() == 0)
                || (util.compareDouble(purchaseOrderLine.getQuantity() + purchaseOrderLine.getOverReceiveQty(), pline.getQuantity() * -1) == 0)) {
                try {
                    cancelMasterBean.cancelPurchaseOrderLine(purchaseOrderLine, userData);
                    canceled = true;
                } catch (EMCEntityBeanException ebx) {
                    throw new EMCStockException("Could not delete Purchase Order");
                }
                poList.remove(purchaseOrderLine);
            } else {
                //Stock still outstanding
                if (util.compareDouble(purchaseOrderLine.getOverReceiveQty(), 0) != 0) {
                    if (util.compareDouble(purchaseOrderLine.getOverReceiveQty(), pline.getQuantity() * -1) >= 0) {
                    } else {
                        purchaseOrderLine.setQuantity(purchaseOrderLine.getQuantity() + (pline.getQuantity() + purchaseOrderLine.getOverReceiveQty()));
                    }
                } else {
                    purchaseOrderLine.setQuantity(purchaseOrderLine.getQuantity() + pline.getQuantity());
                }
            }
        }
        //Updates PO Line
        if (!canceled) {
            if (util.compareDouble(purchaseOrderLine.getOverReceiveQty(), 0) != 0) {
                if (util.compareDouble(purchaseOrderLine.getOverReceiveQty(), pline.getQuantity() * -1) >= 0) {
                    purchaseOrderLine.setOverReceiveQty(purchaseOrderLine.getOverReceiveQty() + pline.getQuantity());
                } else {
                    purchaseOrderLine.setItemsReceived(purchaseOrderLine.getItemsReceived() + (pline.getQuantity() + purchaseOrderLine.getOverReceiveQty()));
                    purchaseOrderLine.setOverReceiveQty(0);
                }
            } else {
                purchaseOrderLine.setItemsReceived(purchaseOrderLine.getItemsReceived() + pline.getQuantity());
            }
            purchaseOrderLine.setReceiveNow(0.0);
            try {
                this.poLine.update(purchaseOrderLine, userData);
                //Reopen Clossed Requirements Planning
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
                query.addAnd("referenceRecordID", purchaseOrderLine.getRecordID());
                InventoryRequirementsPlanning rp = (InventoryRequirementsPlanning) util.executeSingleResultQuery(query, userData);
                if (rp != null && rp.isSupplyClosed()) {
                    rp.setSupplyClosed(false);
                    requirementsPlanningBean.update(rp, userData);
                }
            } catch (EMCEntityBeanException entBex) {
                throw new EMCStockException("Could not update Purchase Order line " + entBex.getMessage());
            }
        }
        //Update post line
        pline.setPoLineRemainingItems(purchaseOrderLine.getQuantity() - purchaseOrderLine.getItemsReceived());
        try {
            this.postLinesBean.update(pline, userData);
        } catch (EMCEntityBeanException entBex) {
            throw new EMCStockException("Could not update Purchase Order post line " + entBex.getMessage());
        }
        updateItemPurchaseAndCost(postMaster, pline, userData);
        
        return pline;
    }
    
    private void updateOrderedTrans(POPPurchasePostLines pline, String suppId, double itemPrice, double discount, EMCUserData userData) throws EMCStockException {
        if (isBlank(pline.getTransactionNumber())) {
            Logger.getLogger("emc").log(Level.SEVERE, "No transaction ID found, could not update ordered transaction");
            throw new EMCStockException("Failed to update ordered transaction");
        }
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
        query.addAnd("transId", pline.getTransactionNumber());
        query.addAnd("refNumber", pline.getPurchaseId());
        query.addAnd("status", InventoryTransactionStatus.ORDERED.toString());
        Object trx = util.executeSingleResultQuery(query, userData);
        if (trx != null) {
            InventoryTransactions tx = (InventoryTransactions) trx;
            tx.setQuantity(tx.getQuantity() - pline.getQuantity());
            tx.setCost(tx.getQuantity() * itemPrice - tx.getQuantity() * itemPrice * discount / 100.0);
            try {
                trans.update(tx, userData);
            } catch (EMCEntityBeanException ex) {
                throw new EMCStockException("Failed to update ordered transaction: " + ex.getMessage());
            }
            updateOnHand(InventorySummaryUpdateOptions.ORDERED_IN, tx, userData);
        }
    }

    /**
     * Does the posting for Purchase Order Post Line
     *
     * @param theLine
     * @param theHeader
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    public EMCTable postPOPostMaster(POPPurchasePostMaster thePostMaster, EMCUserData userData) throws EMCStockException {
        boolean recieved = false;
        EMCQuery q = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class.getName());
        q.addAnd("purchaseOrderId", thePostMaster.getPurchaseOrderId());
        POPPurchaseOrderMaster PO = (POPPurchaseOrderMaster) util.executeSingleResultQuery(q, userData);
        EMCQuery popParmQ = new EMCQuery(enumQueryTypes.SELECT, emc.entity.pop.POPParameters.class.getName());
        POPParameters purchaseParm = (POPParameters) util.executeSingleResultQuery(popParmQ, userData);
        //get Post Lines
        q = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostLines.class.getName());
        q.addAnd("postMasterId", thePostMaster.getPostMasterId());
        q.addAnd("companyId", util.getCompanyId(POPPurchasePostLines.class.getName(), userData));
        List<POPPurchasePostLines> postLines = util.executeGeneralSelectQuery(q, userData);
        //create Receive Journal
        if (!createRecieveNoteJournal(thePostMaster, postLines, PO.getPurchaseOrderId(), PO.getSupplierOrderNumber(), ReceivedJournalType.RECEIVED_NOTE, userData)) {
            throw new EMCStockException("Could not insert receive Journal");
        }

        for (POPPurchasePostLines pline : postLines) {
            InventoryItemTypes itemType = itemMasterBean.getItemType(pline.getItemId(), userData);
            switch (itemType) {
                case BOM: //Fall through
                case ITEM:
                    recieved = postItemPOPostLine(pline, PO, thePostMaster, purchaseParm, recieved, userData);
                    break;
                case SERVICE:
                    recieved = postServicePOPostLine(pline, PO, thePostMaster, purchaseParm, recieved, userData);
                    break;
            }
        } //loop
        //done loop

        return thePostMaster;
    }

    /**
     * Posts a Purchase Order post line containing an item of the 'Item' item
     * type.
     *
     * @param pline Line to post.
     * @param PO Purchase Order master.
     * @param thePostMaster Purchase Order post master.
     * @param purchaseParm Purchasing module parameters
     * @param received Indicates whether stock has been recieved.
     * @param userData User data.
     * @return A boolean indicating whether stock has been received
     * @throws EMCStockException
     */
    private boolean postServicePOPostLine(POPPurchasePostLines pline, POPPurchaseOrderMaster PO, POPPurchasePostMaster thePostMaster, POPParameters purchaseParm, boolean received, EMCUserData userData) throws EMCStockException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class);
        query.addAnd("purchaseOrderId", PO.getPurchaseOrderId());
        query.addAnd("lineNo", pline.getLineNumber());
        
        POPPurchaseOrderLines purchaseOrderLine = (POPPurchaseOrderLines) util.executeSingleResultQuery(query, userData);
        
        double overRecQuantity = (pline.getQuantity() + purchaseOrderLine.getItemsReceived()) - purchaseOrderLine.getQuantity();
        
        if (util.compareDouble(overRecQuantity, 0) > 0) {
            double allowed = purchaseOrderLine.getQuantity() * purchaseParm.getOverPercentage() / 100;
            
            if (purchaseParm.isAllowOverReceive()) {
                if (util.compareDouble(overRecQuantity, allowed) > 0) {
                    throw new EMCStockException(getMessage(ServerPOPMessageEnum.OVER_REC_EXCEEDED, userData, purchaseParm.getOverPercentage()));
                } else {
                    purchaseOrderLine.setItemsReceived(purchaseOrderLine.getQuantity());
                    purchaseOrderLine.setOverReceiveQty(overRecQuantity);
                }
            } else {
                throw new EMCStockException(getMessage(ServerPOPMessageEnum.OVER_REC_NOT_ALLOWED, userData));
            }
        } else {
            purchaseOrderLine.setItemsReceived(purchaseOrderLine.getItemsReceived() + pline.getQuantity());
        }
        
        try {
            poLine.update(purchaseOrderLine, userData);
        } catch (Exception ex) {
            throw new EMCStockException(ex.getMessage());
        }
        
        updateItemPurchaseAndCost(thePostMaster, pline, userData);
        
        return received;
    }

    /**
     * Posts a Purchase Order post line containing an item of the 'Item' item
     * type.
     *
     * @param pline Line to post.
     * @param PO Purchase Order master.
     * @param thePostMaster Purchase Order post master.
     * @param purchaseParm Purchasing module parameters
     * @param recieved Indicates whether stock has been recieved.
     * @param userData User data.
     * @return A boolean indicating whether stock has been received
     * @throws EMCStockException
     */
    private boolean postItemPOPostLine(POPPurchasePostLines pline, POPPurchaseOrderMaster PO, POPPurchasePostMaster thePostMaster, POPParameters purchaseParm, boolean recieved, EMCUserData userData) throws EMCStockException {
        double overRec = 0.0;
        if (util.compareDouble(pline.getQuantity(), 0) != 0) {
            //get PO line
            EMCQuery q = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class);
            q.addAnd("purchaseOrderId", PO.getPurchaseOrderId());
            q.addAnd("transactionNumber", pline.getTransactionNumber());//must be transaction number
            POPPurchaseOrderLines purchaseOrderLine = (POPPurchaseOrderLines) util.executeSingleResultQuery(q, userData);

            //MTO - No QC
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouse.class);
            query.addAnd("mto", true);
            query.addField("warehouseId");
            List<String> mtoWarehouses = util.executeGeneralSelectQuery(query, userData);
            if (mtoWarehouses.contains(purchaseOrderLine.getWarehouse())) {
                userData.setUserData(10, "NO QC");
            } else {
                userData.setUserData(10, "");
            }

            //update order stock tx
            EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.transactions.InventoryTransactions.class);
            qu.addAnd("transId", purchaseOrderLine.getTransactionNumber());
            qu.addAnd("itemDimId", dimIdLocal.getDimRecordId(purchaseOrderLine.getItemDimension1(), purchaseOrderLine.getItemDimension2(), purchaseOrderLine.getItemDimension3(), purchaseOrderLine.getWarehouse(), userData));
            qu.addAnd("itemId", purchaseOrderLine.getItemId());
            qu.addAnd("refNumber", purchaseOrderLine.getPurchaseOrderId());
            qu.addAnd("status", InventoryTransactionStatus.ORDERED.toString());
            
            InventoryTransactions orderTx = (InventoryTransactions) util.executeSingleResultQuery(qu, userData);
            double postLineQtyInBaseUOM = 0;
            try {
                postLineQtyInBaseUOM = uomConv.convertToItemBase(purchaseOrderLine.getItemId(), pline.getQuantity(), purchaseOrderLine.getUom(), userData);
            } catch (EMCUOMConversionException ex) {
                throw new EMCStockException("Could not convert to Item Base UOM " + ex.getMessage());
            }
            if (orderTx != null) {
                double leftqty = orderTx.getQuantity() - postLineQtyInBaseUOM;
                
                if (util.compareDouble(leftqty, 0) < 0) {
                    //handle over receive.
                    if (purchaseParm != null) {
                        if (purchaseParm.isAllowOverReceive()) {
                            double allowed = orderTx.getQuantity() * purchaseParm.getOverPercentage() / 100;
                            if (util.compareDouble(-1 * leftqty, allowed) <= 0) {
                                overRec = -1 * leftqty;
                                try {
                                    //find transaction oldSummary
                                    EMCQuery inventSumQu = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
                                    inventSumQu.addAnd("inventoryTransRef", orderTx.getRecordID());
                                    InventorySummary sumTx = (InventorySummary) util.executeSingleResultQuery(inventSumQu, userData);
                                    itemSumLocal.delete(sumTx, userData);
                                    trans.delete(orderTx, userData);
                                } catch (EMCEntityBeanException entBex) {
                                    throw new EMCStockException("Could not remove order transaction " + entBex.getMessage());
                                }
                            } else {
                                throw new EMCStockException(getMessage(ServerPOPMessageEnum.OVER_REC_EXCEEDED, userData, purchaseParm.getOverPercentage()));
                            }
                        } else {
                            throw new EMCStockException("Purchase Parameters do not allow over receive.");
                        }
                    } else {
                        throw new EMCStockException("Purchase Parameters do not allow over receive.");
                    }
                    //
                } else if (util.compareDouble(leftqty, 0.0) == 0) {
                    //delete tx
                    try {
                        //find transaction oldSummary
                        EMCQuery inventSumQu = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
                        inventSumQu.addAnd("inventoryTransRef", orderTx.getRecordID());
                        InventorySummary sumTx = (InventorySummary) util.executeSingleResultQuery(inventSumQu, userData);
                        itemSumLocal.delete(sumTx, userData);
                        trans.delete(orderTx, userData);
                    } catch (EMCEntityBeanException entBex) {
                        throw new EMCStockException("Could not remove order transaction " + entBex.getMessage());
                    }
                } else if (util.compareDouble(leftqty, 0) > 0) {
                    //update tx
                    orderTx.setQuantity(leftqty);
                    try {
                        trans.update(orderTx, userData);
                    } catch (EMCEntityBeanException entBex) {
                        throw new EMCStockException("Could not insert new inventory transaction " + entBex.getMessage());
                    }
                }
                
            }
            //create receive tx
            //if registerd serial numbers create transactions for each serial number
            EMCQuery serialLines = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.serialbatch.InventorySerialBatch.class.getName());
            serialLines.addAnd("masterId", thePostMaster.getPostMasterId());
            serialLines.addAnd("transId", pline.getTransactionNumber());
            serialLines.addAnd("type", SBType.PURCHASEORDER.toString());
            serialLines.addAnd("companyId", util.getCompanyId(emc.entity.inventory.serialbatch.InventorySerialBatch.class.getName(), userData));
            List<InventorySerialBatch> popSerialLines = util.executeGeneralSelectQuery(serialLines, userData);
            
            if (popSerialLines != null && popSerialLines.size() > 0) {
                
                String curCompId = util.getCompanyId(emc.entity.inventory.transactions.InventoryTransactions.class.getName(), userData);
                for (InventorySerialBatch curPopSer : popSerialLines) {
                    long curDimId = dimIdLocal.getDimRecordId(curPopSer.getBatch(), purchaseOrderLine.getItemDimension1(), purchaseOrderLine.getItemDimension2(), purchaseOrderLine.getItemDimension3(), purchaseOrderLine.getWarehouse(), null, null, curPopSer.getSerial(), userData);
                    //Populate additional dimension table with width
                    if (util.compareDouble(curPopSer.getWidth(), 0) != 0 && !isBlank(curPopSer.getWidthUOM())) {
                        query = new EMCQuery(enumQueryTypes.SELECT, InventoryAdditionalDimensions.class.getName());
                        query.addAnd("itemId", purchaseOrderLine.getItemId());
                        query.addAnd("dimensionId", curDimId);
                        
                        InventoryAdditionalDimensions addDims = (InventoryAdditionalDimensions) util.executeSingleResultQuery(query, userData);
                        
                        try {
                            if (addDims == null) {
                                addDims = new InventoryAdditionalDimensions();
                                addDims.setDimensionId(dimIdLocal.getWidthDimRecordID(purchaseOrderLine.getItemDimension1(),
                                                                                      purchaseOrderLine.getItemDimension2(), purchaseOrderLine.getItemDimension3(),
                                                                                      curPopSer.getBatch(), curPopSer.getSerial(), userData));
                                addDims.setItemId(purchaseOrderLine.getItemId());
                                addDims.setWidth(curPopSer.getWidth());
                                addDims.setWidthUOM(curPopSer.getWidthUOM());
                                
                                additionalDimensionsBean.insert(addDims, userData);
                            }
                        } catch (EMCEntityBeanException entBex) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to update additional dimensions table.", userData);
                            throw new EMCStockException("Failed to update additional dimensions table.");
                        }
                    }
                    InventoryTransactions newTx = new InventoryTransactions();
                    newTx.setTransId(purchaseOrderLine.getTransactionNumber());
                    newTx.setCompanyId(curCompId);
                    newTx.setDirection(InventoryTransactionDirection.IN.toString());
                    newTx.setType(InventoryTransactionTypes.PO.toString());
                    newTx.setRefType(InventoryTransactionsRefType.Receipt.toString());
                    newTx.setRefNumber(purchaseOrderLine.getPurchaseOrderId());
                    newTx.setStatus(InventoryTransactionStatus.RECEIVED.toString());
                    try {
                        newTx.setQuantity(uomConv.convertToItemBase(purchaseOrderLine.getItemId(), curPopSer.getQuantity(), purchaseOrderLine.getUom(), userData));
                    } catch (EMCUOMConversionException exUOM) {
                        throw new EMCStockException("Could not convert to Item Base UOM " + exUOM.getMessage());
                    }
                    newTx.setItemDimId(curDimId);
                    newTx.setItemId(purchaseOrderLine.getItemId());
                    newTx.setDocumentNo(thePostMaster.getDocumentNumber());
                    newTx.setDocumentDate(thePostMaster.getPostDate());
                    newTx.setPhysicalDate(thePostMaster.getPostDate());
                    newTx.setCost(curPopSer.getQuantity() * purchaseOrderLine.getItemPrice() - curPopSer.getQuantity() * purchaseOrderLine.getItemPrice() * purchaseOrderLine.getDiscountPercentage() / 100.0);
                    newTx.setSupplierId(thePostMaster.getSupplierId());
                    try {
                        if (util.compareDouble(newTx.getQuantity(), 0.0) != 0) {
                            trans.insert(newTx, userData);
                            //update on hand
                            updateOnHand(InventorySummaryUpdateOptions.RECEIVED, newTx, userData);
                        }
                        
                    } catch (EMCEntityBeanException entBex) {
                        throw new EMCStockException("Could not insert new inventory transaction " + entBex.getMessage());
                    }
                }
                
            } else {
                InventoryTransactions newTx = new InventoryTransactions();
                newTx.setTransId(purchaseOrderLine.getTransactionNumber());
                newTx.setCompanyId(util.getCompanyId(emc.entity.inventory.transactions.InventoryTransactions.class.getName(), userData));
                newTx.setDirection(InventoryTransactionDirection.IN.toString());
                newTx.setType(InventoryTransactionTypes.PO.toString());
                newTx.setRefType(InventoryTransactionsRefType.Receipt.toString());
                newTx.setRefNumber(purchaseOrderLine.getPurchaseOrderId());
                newTx.setStatus(InventoryTransactionStatus.RECEIVED.toString());
                newTx.setQuantity(postLineQtyInBaseUOM);
                newTx.setItemDimId(dimIdLocal.getDimRecordId(purchaseOrderLine.getItemDimension1(), purchaseOrderLine.getItemDimension2(), purchaseOrderLine.getItemDimension3(), purchaseOrderLine.getWarehouse(), userData));
                newTx.setItemId(purchaseOrderLine.getItemId());
                newTx.setDocumentNo(thePostMaster.getDocumentNumber());
                newTx.setPhysicalDate(thePostMaster.getPostDate());
                newTx.setCost(pline.getQuantity() * purchaseOrderLine.getItemPrice() - pline.getQuantity() * purchaseOrderLine.getItemPrice() * purchaseOrderLine.getDiscountPercentage() / 100.0);
                newTx.setSupplierId(thePostMaster.getSupplierId());
                try {
                    if (util.compareDouble(newTx.getQuantity(), 0.0) != 0) {
                        trans.insert(newTx, userData);
                        //update on hand
                        updateOnHand(InventorySummaryUpdateOptions.RECEIVED, newTx, userData);
                    }
                    
                } catch (EMCEntityBeanException entBex) {
                    throw new EMCStockException("Could not insert new inventory transaction " + entBex.getMessage());
                }
            }

            //do once only
            //update PO line
            purchaseOrderLine.setItemsReceived(purchaseOrderLine.getItemsReceived() + pline.getQuantity() - overRec);
            purchaseOrderLine.setReceiveNow(0.0);
            purchaseOrderLine.setOverReceiveQty(overRec);
            
            try {
                this.poLine.update(purchaseOrderLine, userData);
            } catch (EMCEntityBeanException entBex) {
                throw new EMCStockException("Could not update Purchase Order line " + entBex.getMessage());
            }

            //Update post line
            pline.setPoLineRemainingItems(purchaseOrderLine.getQuantity() - purchaseOrderLine.getItemsReceived());
            
            try {
                this.postLinesBean.update(pline, userData);
            } catch (EMCEntityBeanException entBex) {
                throw new EMCStockException("Could not update Purchase Order post line " + entBex.getMessage());
            }
            
            if (!recieved) {
                if (util.compareDouble(pline.getQuantity(), 0) == 1) {
                    recieved = true;
                }
            }
            
            updateItemPurchaseAndCost(thePostMaster, pline, userData);
        }
        return recieved;
    }

    /**
     * This method is used to create a recieve journal. Returns a boolean
     * indicating whether it succeeded
     */
    private boolean createRecieveNoteJournal(POPPurchasePostMaster postMaster, List<POPPurchasePostLines> postLines,
                                             String purchaseOrderId, String suppOrderNumber, ReceivedJournalType type, EMCUserData userData) throws EMCStockException {
        
        POPPurchaseOrderLines purchaseOrderLine = null;
        POPSupplierReceivedJournalLines journalLine = null;
        
        POPSupplierReceivedJournalMaster journalMaster = new POPSupplierReceivedJournalMaster();
        journalMaster.setSupplierId(postMaster.getSupplierId());
        journalMaster.setSupplierName(suppBean.getSupplierName(postMaster.getSupplierId(), userData));
        journalMaster.setReceivedDate(postMaster.getPostDate());
        journalMaster.setSupplierOrder(suppOrderNumber);
        journalMaster.setPurchaseOrderId(purchaseOrderId);
        journalMaster.setDocumentNumber(postMaster.getDocumentNumber());
        journalMaster.setType(type.toString());
        
        try {
            recJMast.insert(journalMaster, userData);
        } catch (Exception ex) {
            throw new EMCStockException("Failed to create journal master " + ex.getMessage());
        }
        
        int lineNo = 0;
        
        for (POPPurchasePostLines postLine : postLines) {
            if (util.compareDouble(postLine.getQuantity(), 0) == 0) {
                continue;
            }
            
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class.getName());
            query.addAnd("transactionNumber", postLine.getTransactionNumber());
            query.addAnd("purchaseOrderId", postMaster.getPurchaseOrderId());
            
            purchaseOrderLine = (POPPurchaseOrderLines) util.executeSingleResultQuery(query, userData);
            
            journalLine = new POPSupplierReceivedJournalLines();
            journalLine.setLineNo(lineNo);
            journalLine.setQuantity(postLine.getQuantity());
            journalLine.setUom(purchaseOrderLine.getUom());
            journalLine.setDimId(postLine.getDimId());
            journalLine.setReceivedId(journalMaster.getReceivedId());
            journalLine.setItemId(purchaseOrderLine.getItemId());
            journalLine.setPrice(purchaseOrderLine.getItemPrice());
            journalLine.setLineAmount(journalLine.getQuantity() * journalLine.getPrice());
            journalLine.setTransactionNumber(postLine.getTransactionNumber());
            journalLine.setReceivedDate(postMaster.getPostDate());
            journalLine.setStandardLocation(postLine.getStandardLocation());
            
            try {
                this.recJLine.insert(journalLine, userData);
            } catch (Exception ex) {
                throw new EMCStockException("Failed to create journal line " + ex.getMessage());
            }
            lineNo += 10;
        }
        return true;
    }

    /**
     * Updates a blanket orders outstanding quantity
     *
     * @param theLine
     * @param qty
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    @Override
    public EMCTable updateBlanketOrderOutStanding(POPPurchaseOrderLines theLine, double qty, EMCUserData userData) throws EMCStockException {
        return updateBlanketOrderOnHand(RequirementsPlanningReferenceTypes.PURCHASE_ORDER, theLine.getRecordID(), theLine.getItemId(),
                                        theLine.getItemDimension1(), theLine.getItemDimension2(), theLine.getItemDimension3(), theLine.getWarehouse(),
                                        util.getBigDecimal(qty), theLine.getUom(), userData);
    }

    /**
     * This method updates the item master when an item is purchased (On
     * receiving) If necessary, it also updates the item dimension combinations
     * (Depending on item costing group).
     *
     * @param master - The POPPurchasePostMaster of the line containing the item
     * to update. This can be used to find the Purchase Order.
     * @param line - The POPPurchasePostLines instance containing the item to be
     * updated.
     * @param userData
     */
    public boolean updateItemPurchaseAndCost(POPPurchasePostMaster master, POPPurchasePostLines line, EMCUserData userData) {
        boolean success = true;
        
        String purchaseOrderId = master.getPurchaseOrderId();
        double lineNo = line.getLineNumber();
        String itemId = line.getItemId();
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
        query.addAnd("itemId", itemId);
        
        InventoryItemMaster item = (InventoryItemMaster) util.executeSingleResultQuery(query, userData);
        
        query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class.getName());
        query.addAnd("purchaseOrderId", purchaseOrderId);
        query.addAnd("lineNo", lineNo);
        
        POPPurchaseOrderLines purchaseOrderLine = (POPPurchaseOrderLines) util.executeSingleResultQuery(query, userData);
        
        double itemPrice = purchaseOrderLine.getItemPrice();
        item.setLastPurchaseDate(Functions.nowDate());
        item.setLastPurchaseDDate(Functions.calculateDayDiff(purchaseOrderLine.getCreatedDate(), Functions.nowDate()));
        item.setLastPurchasePrice(itemPrice);
        item.setLastPurchasePer(purchaseOrderLine.getUom());
        item.setLastPurchaseSupplier(master.getSupplierId());
        if (util.compareDouble(item.getCostingCurrentCost(), 0.0) == 0 && !isBlank(purchaseOrderLine.getWhoApproved()) && purchaseOrderLine.getUpdateItem()) {
            item.setCostingPrevCost(item.getCostingCurrentCost());
            item.setCostingPrevCostDate(item.getCostingCostDate());
            item.setPrevCostLink(item.getCurrentCostLink());
            item.setCostingCurrentCost(itemPrice);
            item.setCostingCostDate(Functions.nowDate());
            item.setCurrentCostLink("Manual");
        }
        try {
            itemMasterBean.update(item, userData);
        } catch (Exception ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to update Last Purchase information on Item Master", userData);
        }
        
        String costingGroupId = item.getCostingGroup();
        if (!isBlank(costingGroupId)) {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryCostingGroup.class.getName());
            query.addAnd("costingGroupId", costingGroupId);
            
            InventoryCostingGroup costingGroup = (InventoryCostingGroup) util.executeSingleResultQuery(query, userData);
            
            if (costingGroup != null && CostLevel.DIMENSION.equals(CostLevel.fromString(costingGroup.getCostLevel()))) {
                boolean[] activeDims = combinationLogicBean.getActiveDimensions(itemId, userData);
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class.getName());
                query.addAnd("itemId", itemId);
                if (activeDims[0]) {
                    query.addAnd("dimension1Id", purchaseOrderLine.getItemDimension1());
                }
                
                if (activeDims[1]) {
                    query.addAnd("dimension2Id", purchaseOrderLine.getItemDimension2());
                }
                
                if (activeDims[2]) {
                    query.addAnd("dimension3Id", purchaseOrderLine.getItemDimension3());
                }
                
                InventoryItemDimensionCombinations combination = (InventoryItemDimensionCombinations) util.executeSingleResultQuery(query, userData);
                
                if (combination != null) {
                    if (util.compareDouble(combination.getCostPrice(), 0.0) == 0) {
                        combination.setPrevCostDate(combination.getCurrentCostDate());
                        combination.setPrevCostPrice(combination.getCostPrice());
                        combination.setCostPrice(itemPrice);
                        combination.setCurrentCostDate(Functions.nowDate());
                    }
                    combination.setLastPurchasePrice(itemPrice);
                    combination.setLastPurchaseDate(Functions.nowDate());
                    try {
                        combinationsBean.update(combination, userData);
                    } catch (Exception ex) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to update cost on Item Dimension Combinations", userData);
                    }
                } else {
                    if (EMCDebug.getDebug()) {
                        Logger.getLogger("emc").log(Level.WARNING, "Combination not found", userData);
                    }
                }
            }
        }
        
        return success;
    }
}

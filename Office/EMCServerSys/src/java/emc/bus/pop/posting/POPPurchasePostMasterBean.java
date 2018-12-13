/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop.posting;

import emc.bus.base.numbersequences.BaseNumberSequenceLogicLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.pop.POPPurchaseOrderLinesLocal;
import emc.bus.pop.POPPurchaseOrderMasterLocal;
import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.bus.inventory.serialbatch.InventorySerialBatchLocal;
import emc.bus.inventory.transactions.EMCStockException;
import emc.bus.inventory.transactions.ProcessStockTransactionLocal;
import emc.bus.inventory.transactions.TransactionHelper;
import emc.bus.inventory.transactions.TransactionType;
import emc.entity.base.BaseUnitsOfMeasure;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.serialbatch.InventorySerialBatch;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.pop.POPParameters;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.pop.journals.POPSupplierReceivedJournalLines;
import emc.entity.pop.journals.POPSupplierReceivedJournalMaster;
import emc.entity.pop.posting.POPPurchasePostLines;
import emc.entity.pop.posting.POPPurchasePostMaster;
import emc.entity.pop.posting.POPPurchasePostSetupTable;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.pop.posting.DocumentTypes;
import emc.enums.pop.posting.PostingQuantities;
import emc.enums.pop.purchaseorders.PurchaseOrderStatus;
import emc.enums.pop.purchaseorders.PurchaseOrderTypes;
import emc.enums.enumQueryTypes;
import emc.enums.pop.posting.SBType;
import emc.enums.pop.purchaseorders.PurchaseOrderReceivedLabelTypes;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.math.EMCMath;
import emc.reports.pop.grnlabels.LabelsLocal;
import emc.server.commandmanager.EMCCommandManagerLocal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.TransactionSynchronizationRegistry;

/**
 *
 * @author riaan
 */
@Stateless
public class POPPurchasePostMasterBean extends EMCEntityBean implements POPPurchasePostMasterLocal {

    @EJB
    private POPPurchasePostMasterLocal postMasterBean;
    @EJB
    private POPPurchasePostLinesLocal postLinesBean;
    @EJB
    private ProcessStockTransactionLocal stockBean;
    @EJB
    private InventoryDimensionTableLocal dimTableBean;
    @EJB
    private POPPurchaseOrderMasterLocal purchaseOrderMasterBean;
    @EJB
    private POPPurchaseOrderLinesLocal purchaseOrderLinesBean;
    @EJB
    private InventoryReferenceLocal itemRefBean;
    @EJB
    private LabelsLocal labelsBean;
    @EJB
    private BaseNumberSequenceLogicLocal numberSequenceLogicBean;
    @EJB
    private InventorySerialBatchLocal serialBatchBean;
    @Resource
    private TransactionSynchronizationRegistry registry;
    @EJB
    private EMCCommandManagerLocal commandManager;

    /**
     * Creates a new instance of POPPurchasePostMasterBean
     */
    public POPPurchasePostMasterBean() {
    }

    /**
     * Posts a Purchase Order.
     */
    @Override
    public boolean postPurchaseOrder(String purchaseOrderId, EMCUserData userData) {
        //hasBeenPrinted is set on the report bean
        POPPurchaseOrderMaster master = purchaseOrderMasterBean.findPurchaseOrder(purchaseOrderId, userData);

        PurchaseOrderStatus status = PurchaseOrderStatus.fromString(master.getStatus());

        switch (status) {
            case REQUISITION:
                Logger.getLogger("emc").log(Level.INFO, "Purchase Order may not be posted before it is approved.", userData);
                return false;
            case APPROVED:
                master.setStatus(PurchaseOrderStatus.ORDERED.toString());
                master.setOrderedDate(new Date());

                try {
                    purchaseOrderMasterBean.update(master, userData);
                    Logger.getLogger("emc").log(Level.INFO, "Purchase Order posted.", userData);
                    return true;
                } catch (EMCEntityBeanException ex) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to post Purchase Order.", userData);
                    return false;
                }


            default:
                Logger.getLogger("emc").log(Level.INFO, "Purchase Order posted.", userData);
                return true;
        }
    }

    /**
     * Posts a blanket order.
     */
    @Override
    public boolean postBlanketOrder(String purchaseOrderId, EMCUserData userData) {
        return postPurchaseOrder(purchaseOrderId, userData);
    }

    /**
     * Overloaded createPost() method. Takes a setupId as parameter, then
     * retrieves the setup record and call createPost()
     */
    @Override
    public boolean createPost(String query, String setupId, boolean populateAllLines, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery selectQuery = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostSetupTable.class.getName());
        selectQuery.addAnd("postSetupId", setupId);

        Object setup = util.executeSingleResultQuery(selectQuery, userData);

        if (setup != null) {
            return createPost(query, (POPPurchasePostSetupTable) setup, populateAllLines, userData);
        } else {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Setup not found", userData);
            }
            return false;
        }
    }

    @Override
    public boolean createPost(String query, POPPurchasePostSetupTable setup, boolean populateAllLines, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = false;
        //Get and delete previous post masters for setup
        EMCQuery selectQuery = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostMaster.class.getName());
        selectQuery.addAnd("postSetupId", setup.getPostSetupId());

        List<POPPurchasePostMaster> postMasters = (List<POPPurchasePostMaster>) util.executeGeneralSelectQuery(selectQuery, userData);

        if (postMasters != null) {
            for (POPPurchasePostMaster master : postMasters) {
                //try {
                postMasterBean.delete(master, userData);
                //} catch (EMCEntityBeanException ex) {
                //    Logger.getLogger("emc").log(Level.SEVERE, "Failed to delete post master", userData);
                //}
            }
        }

        List<POPPurchaseOrderMaster> purchaseOrders = (List<POPPurchaseOrderMaster>) util.executeGeneralSelectQuery(query, userData);

        List<POPPurchaseOrderLines> purchaseOrderLines = null;
        POPPurchasePostMaster postMaster = null;
        POPPurchasePostLines postLine = null;

        selectQuery = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class);

        if (purchaseOrders != null) {
            if (setup.getDocumentType().equals(DocumentTypes.RETURN_GOODS.toString())) {
                return setupReturnPost(purchaseOrders, setup, userData);
            }
            for (POPPurchaseOrderMaster master : purchaseOrders) {

                postMaster = new POPPurchasePostMaster();
                postMaster.setPostDate(Functions.nowDate());
                postMaster.setPostSetupId(setup.getPostSetupId());
                postMaster.setPurchaseOrderId(master.getPurchaseOrderId());
                postMaster.setSupplierId(master.getSupplier());
                postMaster.setApprovedBy(master.getApprovedBy());
                postMasterBean.insert(postMaster, userData);

                selectQuery.removeAnd("purchaseOrderId");
                selectQuery.addAnd("purchaseOrderId", master.getPurchaseOrderId());

                PostingQuantities quantity = PostingQuantities.fromString(setup.getQuantitySelection());

                switch (quantity) {

                    case RECEIVED:
                        selectQuery.addAnd("receiveNow", 0, EMCQueryConditions.GREATER_THAN);
                        break;

                    default:
                        break;
                }

                purchaseOrderLines = (List<POPPurchaseOrderLines>) util.executeGeneralSelectQuery(selectQuery, userData);

                for (POPPurchaseOrderLines line : purchaseOrderLines) {
                    if (populateAllLines || (line.getQuantity() != line.getItemsReceived()) || setup.getDocumentType().equals(DocumentTypes.RETURN_GOODS.toString())) {
                        postLine = new POPPurchasePostLines();
                        //Copy PO line number to make selection easier when generating report
                        postLine.setLineNumber(line.getLineNo());
                        postLine.setPostMasterId(postMaster.getPostMasterId());
                        postLine.setDimId(dimTableBean.getDimRecordId(line.getItemDimension1(), line.getItemDimension2(), line.getItemDimension3(), line.getWarehouse(), userData));
                        postLine.setItemId(line.getItemId());
                        postLine.setTransactionNumber(line.getTransactionNumber());

                        if (populateAllLines) {
                            postLine.setQuantity(line.getQuantity());
                        } else {
                            switch (quantity) {
                                case ALL:
                                    postLine.setQuantity(line.getQuantity() - line.getItemsReceived());
                                    break;
                                case RECEIVED:
                                    postLine.setQuantity(line.getReceiveNow());
                                    break;
                                default:
                                    break;
                            }
                        }
                        postLinesBean.insert(postLine, userData);
                        ret = true;
                    }
                }
            }
        }
        return ret;
    }

    private boolean setupReturnPost(List<POPPurchaseOrderMaster> purchaseOrders, POPPurchasePostSetupTable setup, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = false;
        EMCQuery journalLinesQuery = new EMCQuery(enumQueryTypes.SELECT, POPSupplierReceivedJournalLines.class.getName());
        journalLinesQuery.addTableAnd(POPSupplierReceivedJournalMaster.class.getName(), "receivedId", POPSupplierReceivedJournalLines.class.getName(), "receivedId");
        journalLinesQuery.addField("transactionNumber");
        journalLinesQuery.addField("itemId");
        journalLinesQuery.addField("dimId");
        journalLinesQuery.addField("documentNumber", POPSupplierReceivedJournalMaster.class.getName());
        journalLinesQuery.addGroupBy(POPSupplierReceivedJournalLines.class.getName(), "itemId");
        journalLinesQuery.addGroupBy(POPSupplierReceivedJournalLines.class.getName(), "dimId");
        journalLinesQuery.addGroupBy(POPSupplierReceivedJournalLines.class.getName(), "transactionNumber");
        journalLinesQuery.addGroupBy(POPSupplierReceivedJournalMaster.class.getName(), "documentNumber");
        journalLinesQuery.addFieldAggregateFunction("quantity", POPSupplierReceivedJournalLines.class.getName(), "SUM");

        EMCQuery lineNumberQuery = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class.getName());
        lineNumberQuery.addField("lineNo");
        List journalList;
        POPPurchasePostMaster postMaster = null;
        POPPurchasePostLines postLines;
        Double lineNumber;
        GregorianCalendar cal = new GregorianCalendar();
        Object[] journals;
        Map<String, POPPurchasePostMaster> previousDocNum = new HashMap<String, POPPurchasePostMaster>();

        for (POPPurchaseOrderMaster purchaseMaster : purchaseOrders) {

            journalLinesQuery.removeAnd("purchaseOrderId", POPSupplierReceivedJournalMaster.class.getName());
            journalLinesQuery.addAnd("purchaseOrderId", purchaseMaster.getPurchaseOrderId(), POPSupplierReceivedJournalMaster.class.getName());
            journalList = util.executeGeneralSelectQuery(journalLinesQuery, userData);

            for (Object linesObject : journalList) {
                journals = (Object[]) linesObject;

                if ((Double) journals[4] > 0) {
                    if (!previousDocNum.containsKey((String) journals[3])) {
                        postMaster = new POPPurchasePostMaster();
                        postMaster.setPurchaseOrderId(purchaseMaster.getPurchaseOrderId());
                        postMaster.setSupplierId(purchaseMaster.getSupplier());
                        postMaster.setDocumentNumber((String) journals[3]);
                        postMaster.setPostDate(cal.getTime());
                        postMaster.setPostSetupId(setup.getPostSetupId());
                        postMaster.setApprovedBy(purchaseMaster.getApprovedBy());
                        postMasterBean.insert(postMaster, userData);
                        previousDocNum.put((String) journals[3], postMaster);
                    } else {
                        postMaster = previousDocNum.get((String) journals[3]);
                    }


                    lineNumberQuery.removeAnd("transactionNumber");
                    lineNumberQuery.addAnd("transactionNumber", journals[0]);
                    lineNumber = (Double) util.executeSingleResultQuery(lineNumberQuery, userData);

                    postLines = new POPPurchasePostLines();
                    postLines.setPostMasterId(postMaster.getPostMasterId());
                    postLines.setPurchaseId(postMaster.getPurchaseOrderId());
                    postLines.setLineNumber(lineNumber);
                    postLines.setItemId((String) journals[1]);
                    postLines.setQuantity((Double) journals[4]);
                    postLines.setDimId((Long) journals[2]);
                    postLines.setTransactionNumber((String) journals[0]);
                    String[] itemRef = itemRefBean.checkItemReference(postLines.getItemId(), userData);
                    postLines.setItemPrimaryReference(isBlank(itemRef[1]) ? itemRef[0] : itemRef[1]);
                    postLines.setNumberLabels(0);

                    postLinesBean.insert(postLines, userData);
                    ret = true;
                }
            }
        }
        return ret;
    }

    /**
     * This method is used to receive a purchase order.
     */
    @Override
    public void receivePurchaseOrder(String query, EMCUserData userData) throws EMCEntityBeanException {
        List<POPPurchasePostMaster> postMasters = (List<POPPurchasePostMaster>) util.executeGeneralSelectQuery(query, userData);

        if (postMasters != null) {
            for (POPPurchasePostMaster postMaster : postMasters) {
                try {
                    stockBean.post(postMaster, new TransactionHelper(TransactionType.POP_POSTMASTER), userData);
                } catch (EMCStockException ex) {
                    throw new EMCEntityBeanException(ex);
                }
                Logger.getLogger("emc").log(Level.INFO, "Purchase Order received. (Delivery Note: " + postMaster.getDocumentNumber() + ")", userData);
            }

        }

    }

    @Override
    public Object validateField(
            String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        POPPurchasePostMaster postMaster = (POPPurchasePostMaster) theRecord;

        Object ret = null;

        if (valid) {
            DocumentTypes docType = DocumentTypes.fromString((String) userData.getUserData(3));

            if (fieldNameToValidate.equals("documentNumber") && docType == DocumentTypes.GOODS_RECEIVED_NOTE) {
                valid = valid && validateDocumentNumber(postMaster, userData);
            }

            ret = valid;
        }

        return ret;
    }

    /**
     * This method is used to validate a document number.
     */
    private boolean validateDocumentNumber(POPPurchasePostMaster postMaster, EMCUserData userData) {

        if (isBlank(postMaster.getDocumentNumber())) {
            Logger.getLogger("emc").log(Level.SEVERE, "Document number is a required field.", userData);
            return false;
        }



        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
        query.addAnd(
                "documentNo", postMaster.getDocumentNumber());
        query.addField(
                "recordID");

        List ret = util.executeGeneralSelectQuery(query, userData);
        if (ret.size() > 0) {
            Logger.getLogger("emc").log(Level.SEVERE, "Document number already used.", userData);
            return false;
        }
        return true;
    }

    /**
     * This method is used to release a blanket order.
     */
    @Override
    public boolean releaseBlanketOrder(String query, EMCUserData userData) throws EMCEntityBeanException {
        List<POPPurchasePostMaster> postMasters = (List<POPPurchasePostMaster>) util.executeGeneralSelectQuery(query, userData);

        for (POPPurchasePostMaster postMaster : postMasters) {
            String purchaseOrderId = postMaster.getPurchaseOrderId();

            EMCQuery poMasterQuery = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class.getName());
            poMasterQuery.addAnd(
                    "purchaseOrderId", purchaseOrderId);

            POPPurchaseOrderMaster purchaseOrderMaster = (POPPurchaseOrderMaster) util.executeSingleResultQuery(poMasterQuery, userData);

            PurchaseOrderStatus status = PurchaseOrderStatus.fromString(purchaseOrderMaster.getStatus());
            if (status.equals(PurchaseOrderStatus.REQUISITION)) {
                //Cannot post before blanket order is approved.  Try the next one.
                Logger.getLogger("emc").log(Level.SEVERE, "Blanket Order may not be posted before it is approved.  Blanket Order: " + purchaseOrderMaster.getPurchaseOrderId(), userData);
                continue;
            }
            POPPurchaseOrderMaster masterCopy = (POPPurchaseOrderMaster) doClone(purchaseOrderMaster, userData);

            masterCopy.setPurchaseOrderId(null);

            masterCopy.setPurchaseOrderType(PurchaseOrderTypes.PURCHASE_ORDER.toString());
            masterCopy.setStatus(PurchaseOrderStatus.REQUISITION.toString());
            masterCopy.setPostVersion(0);

            //External reference to Blanket Order
            masterCopy.setBlanketOrderRef(purchaseOrderMaster.getPurchaseOrderId());

            EMCQuery postLinesQuery = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostLines.class.getName());
            postLinesQuery.addAnd(
                    "postMasterId", postMaster.getPostMasterId());

            List<POPPurchasePostLines> postLines = (List<POPPurchasePostLines>) util.executeGeneralSelectQuery(postLinesQuery, userData);

            String poLinesClassName = POPPurchaseOrderLines.class.getName();

            //Select the items to receive and the items already received from the purchase order.  This ensures that all quantities are checked, even when post lines are deleted.
//            EMCQuery totalQuery = new EMCQuery(enumQueryTypes.SELECT, poLinesClassName);
//            totalQuery.addFieldAggregateFunction("quantity", poLinesClassName, "SUM");
//            totalQuery.addAnd("purchaseOrderId", postMaster.getPurchaseOrderId());
//            
//            Double totalToReceive = (Double)util.executeSingleResultQuery(totalQuery, userData);
//            
//            if (totalToReceive == null) {
//                totalToReceive = 0d;
//            }
//            
//            totalQuery = new EMCQuery(enumQueryTypes.SELECT, poLinesClassName);
//            totalQuery.addFieldAggregateFunction("itemsReceived", poLinesClassName, "SUM");
//            totalQuery.addAnd("purchaseOrderId", postMaster.getPurchaseOrderId());
//            
//            Double totalReceived = (Double)util.executeSingleResultQuery(totalQuery, userData);
//
//            if (totalReceived == null) {
//                totalReceived = 0d;
//            }
//            
//            totalQuery = new EMCQuery(enumQueryTypes.SELECT, poLinesClassName);
//            totalQuery.addFieldAggregateFunction("overReceiveQty", poLinesClassName, "SUM");
//            totalQuery.addAnd("purchaseOrderId", postMaster.getPurchaseOrderId());
//            
//            Double totalOverReceived = (Double)util.executeSingleResultQuery(totalQuery, userData);
//            totalReceived += totalOverReceived == null ? 0 : totalOverReceived;

            double maxOverReleasePer = -1;
            double overReleasedPercentage = -1;
            EMCQuery overRelQuery = new EMCQuery(enumQueryTypes.SELECT, POPParameters.class.getName());

            overRelQuery.addField("allowBlanketOrderOverRelease");

            overRelQuery.addField("blanketOrderOverReleasePercentage");
            List queryResult = util.executeGeneralSelectQuery(overRelQuery);
            if (queryResult.size() != 0) {
                if ((Boolean) ((Object[]) queryResult.get(0))[0]) {
                    maxOverReleasePer = (Double) ((Object[]) queryResult.get(0))[1];
                }
            } else {
                Logger.getLogger("emc").log(Level.WARNING, "Failed to retrieve POPParameters.  Please set up POPParameters.", userData);
            }

            purchaseOrderMasterBean.insert(masterCopy, userData);
            for (POPPurchasePostLines postLine : postLines) {

                EMCQuery poLinesQuery = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class.getName());
                poLinesQuery.addAnd("purchaseOrderId", purchaseOrderId);
                poLinesQuery.addAnd("lineNo", postLine.getLineNumber());

                POPPurchaseOrderLines purchaseOrderLine = (POPPurchaseOrderLines) util.executeSingleResultQuery(poLinesQuery, userData);

                //totalReceived += postLine.getQuantity();
                //totalToReceive += (purchaseOrderLine.getQuantity() - purchaseOrderLine.getItemsReceived());

                if (postLine.getQuantity() > 0) {
                    if (purchaseOrderLine.getQuantity() == purchaseOrderLine.getItemsReceived()) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Blanket order line is fully received. (" + purchaseOrderLine.getPurchaseOrderId() + ":" + purchaseOrderLine.getLineNo() + ".  Purchase orders may no longer be released from this line.", userData);
                        throw new EMCEntityBeanException("Blanket order line fully received.");
                    }

                    if (purchaseOrderLine.getQuantity() < postLine.getQuantity() + purchaseOrderLine.getItemsReceived()) {
                        if (maxOverReleasePer == -1) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Over release on blanket order not allowed.", userData);
                            throw new EMCEntityBeanException("Cannot over release.");
                        } else {
                            EMCQuery decimalsQuery = new EMCQuery(enumQueryTypes.SELECT, BaseUnitsOfMeasure.class.getName());
                            decimalsQuery.addAnd("unit", purchaseOrderLine.getUom());

                            BaseUnitsOfMeasure unit = (BaseUnitsOfMeasure) util.executeSingleResultQuery(decimalsQuery, userData);

                            int decimals = 0;

                            if (unit != null) {
                                decimals = unit.getDecimals();
                            }

                            overReleasedPercentage = (purchaseOrderLine.getItemsReceived() + postLine.getQuantity()) / purchaseOrderLine.getQuantity();
                            overReleasedPercentage = new EMCMath().round((overReleasedPercentage * 100) - 100, decimals);

                            if (overReleasedPercentage > maxOverReleasePer) {
                                Logger.getLogger("emc").log(Level.SEVERE, "Over release quantity too large.", userData);
                                throw new EMCEntityBeanException("Over release quantity too large.");
                            }
                        }
                    }

                    POPPurchaseOrderLines lineCopy = (POPPurchaseOrderLines) doClone(purchaseOrderLine, userData);
                    lineCopy.setQuantity(postLine.getQuantity());
                    lineCopy.setPurchaseOrderId(masterCopy.getPurchaseOrderId());
                    lineCopy.setTransactionNumber(null);
                    lineCopy.setItemsReceived(0);
                    lineCopy.setBlanketOrderLineId(purchaseOrderLine.getRecordID());
                    purchaseOrderLinesBean.calculateLineNetAmount(lineCopy, userData);

                    /*try {
                    //If over released, update quantity to outstanding quantity
                    stockBean.updateBlanketOrderOutStanding(purchaseOrderLine, -(overReleasedPercentage == - 1 ? postLine.getQuantity() : (purchaseOrderLine.getQuantity() - purchaseOrderLine.getItemsReceived())), userData);
                    } catch (EMCStockException ex) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to update onhand quantity while processing Purchase Order - " + purchaseOrderId + " - " + purchaseOrderLine.getLineNo(), userData);
                    throw new EMCEntityBeanException(ex);
                    }*/

                    try {
                        purchaseOrderLinesBean.insert(lineCopy, userData);
                    } catch (EMCEntityBeanException ex) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to release Purchase Order line - " + purchaseOrderId + " - " + purchaseOrderLine.getLineNo(), userData);
                        throw new EMCEntityBeanException(ex);
                    }

                    try {
                        double overReceivedItems = (purchaseOrderLine.getItemsReceived() + postLine.getQuantity()) - purchaseOrderLine.getQuantity();

                        if (overReceivedItems > 0) {
                            purchaseOrderLine.setItemsReceived(purchaseOrderLine.getQuantity());
                            purchaseOrderLine.setOverReceiveQty(overReceivedItems);
                        } else {
                            purchaseOrderLine.setItemsReceived(purchaseOrderLine.getItemsReceived() + lineCopy.getQuantity());
                        }
                        purchaseOrderLinesBean.update(purchaseOrderLine, userData);
                    } catch (EMCEntityBeanException ex) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to update Blanket Order line - " + purchaseOrderId + " - " + purchaseOrderLine.getLineNo(), userData);
                        throw new EMCEntityBeanException(ex);
                    }

                }

            }

            //if (totalReceived != 0) {
            //purchaseOrderMaster.setStatus(totalReceived >= totalToReceive ? PurchaseOrderStatus.RECEIVED.toString() : PurchaseOrderStatus.PARTIALLY_RECEIVED.toString());

            //try {
            //purchaseOrderMasterBean.update(purchaseOrderMaster, userData);
            //} catch (EMCEntityBeanException ex) {
            //Logger.getLogger("emc").log(Level.SEVERE, "Failed to update status on Purchase Order master - " + purchaseOrderId, userData);
            //throw new EMCEntityBeanException(ex);
            //}
            //}
            Logger.getLogger("emc").log(Level.INFO, "Released purchase order - " + masterCopy.getPurchaseOrderId(), userData);
        }

        return true;
    }

    @Override
    public boolean checkBatchSerial(String postSetupId, EMCUserData userData) {
        boolean ret = true;

        String itemMasterClass = InventoryItemMaster.class.getName();
        String postLinesClass = POPPurchasePostLines.class.getName();
        String dimensionGroupClass = InventoryItemDimensionGroup.class.getName();
        String serialBatchClass = InventorySerialBatch.class.getName();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostMaster.class.getName());
        query.addAnd("postSetupId", postSetupId);

        List<POPPurchasePostMaster> postMasters = (List<POPPurchasePostMaster>) util.executeGeneralSelectQuery(query, userData);

        for (POPPurchasePostMaster postMaster : postMasters) {
            query = new EMCQuery(enumQueryTypes.SELECT, postLinesClass);
            query.addAnd("postMasterId", postMaster.getPostMasterId());

            List<POPPurchasePostLines> postLines = (List<POPPurchasePostLines>) util.executeGeneralSelectQuery(query, userData);

            for (POPPurchasePostLines postLine : postLines) {

                EMCQuery itemQuery = new EMCQuery(enumQueryTypes.SELECT, itemMasterClass);
                itemQuery.addField("dimensionGroup");
                itemQuery.addAnd("itemId", postLine.getItemId());
                itemQuery.addAnd("companyId", util.getCompanyId(itemMasterClass, userData));

                query = new EMCQuery(enumQueryTypes.SELECT, dimensionGroupClass);
                query.addAnd("itemDimensionGroupId", itemQuery);

                InventoryItemDimensionGroup dimGroup = (InventoryItemDimensionGroup) util.executeSingleResultQuery(query, userData);

                if (dimGroup != null && dimGroup.getBatchNumberActive() || dimGroup.getSerialNumberActive()) {
                    query = new EMCQuery(enumQueryTypes.SELECT, serialBatchClass);
                    query.addFieldAggregateFunction("quantity", "SUM");
                    query.addAnd("type", SBType.PURCHASEORDER);
                    query.addAnd("transId", postLine.getTransactionNumber());
                    query.addAnd("masterId", postMaster.getPostMasterId());

                    Double result = (Double) util.executeSingleResultQuery(query, userData);

                    String[] refArr = itemRefBean.checkItemReference(postLine.getItemId(), userData);
                    String itemRef = refArr == null ? postLine.getItemId() : refArr[1];

                    if (result == null) {
                        result = 0d;
                    }

                    if (util.compareDouble(result, postLine.getQuantity()) < 0) {
                        //if (result < postLine.getQuantity()) {
                        Logger.getLogger("emc").log(Level.SEVERE, "There are " + postLine.getQuantity() + " items received but " + (result == 0 ? "no" : "only " + result) + " items are registered for the item " + itemRef, userData);
                        ret = false;
                    } else if (util.compareDouble(result, postLine.getQuantity()) > 0) {
                        //} else if (result > postLine.getQuantity()) {
                        Logger.getLogger("emc").log(Level.SEVERE, "There are " + result + " items registered but " + (postLine.getQuantity() == 0 ? "no" : "only " + postLine.getQuantity()) + " items are being received for the item " + itemRef, userData);
                        ret = false;
                    }
                }
            }
        }

        return ret;
    }

    @Override
    public void returnToSupplier(String postMasterQuery, boolean receiveLater, EMCUserData userData) throws EMCEntityBeanException {
        List<POPPurchasePostMaster> postMasters = (List<POPPurchasePostMaster>) util.executeGeneralSelectQuery(postMasterQuery, userData);

        if (postMasters != null) {
            for (POPPurchasePostMaster postMaster : postMasters) {
                try {
                    TransactionHelper txH = new TransactionHelper(TransactionType.POP_POSTMASTER_RETURN);
                    txH.setPOPReturnStockLaterFlag(receiveLater);
                    stockBean.post(postMaster, txH, userData);
                } catch (EMCStockException ex) {
                    throw new EMCEntityBeanException(ex);
                }
            }
            Logger.getLogger("emc").log(Level.INFO, "Stock returned to supplier", userData);
        }
    }

    /**
     * Returns String representations of the labels applicable to the specified
     * post.
     *
     * @param postMasterId Post master id.
     * @param userData User data.
     * @return A Map containing String representations of labels.
     */
    @Override
    public Map<String, String> printLabels(String postMasterId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostMaster.class);
        query.addAnd("postMasterId", postMasterId);

        POPPurchasePostMaster postMaster = (POPPurchasePostMaster) util.executeSingleResultQuery(query, userData);

        query = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostLines.class);
        query.addTableAnd(POPPurchasePostMaster.class.getName(), "postMasterId", POPPurchasePostLines.class.getName(), "postMasterId");
        query.addTableAnd(POPPurchaseOrderLines.class.getName(), "purchaseOrderId", POPPurchasePostMaster.class.getName(), "purchaseOrderId");
        query.addAnd("transactionNumber", POPPurchasePostLines.class.getName(), EMCQueryConditions.EQUALS, "transactionNumber", POPPurchaseOrderLines.class.getName());
        query.addAnd("postMasterId", postMasterId);
        //Only select PO line warehouse and Post line record ID and quantity.
        query.addField("warehouse", POPPurchaseOrderLines.class.getName());
        query.addField("recordID", POPPurchasePostLines.class.getName());

        List<Object[]> postLines = (List<Object[]>) util.executeGeneralSelectQuery(query, userData);

        //Map warehouse -> label relationships.
        Map<String, String> warehouseLabelTypes = new HashMap<String, String>();
        //Keeps a List of Post line record ID's for each label type.
        Map<String, List<Long>> labelLines = new HashMap<String, List<Long>>();

        for (Object[] postLine : postLines) {
            String warehouse = (String) postLine[0];

            String labelType = warehouseLabelTypes.get(warehouse);
            if (labelType == null) {
                EMCQuery warehouseQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouse.class);
                warehouseQuery.addField("purchaseOrderReceivedLabelType");
                warehouseQuery.addAnd("warehouseId", warehouse);

                labelType = (String) util.executeSingleResultQuery(warehouseQuery, userData);
                if (isBlank(labelType)) {
                    labelType = PurchaseOrderReceivedLabelTypes.GRN.toString();
                }
                warehouseLabelTypes.put(warehouse, labelType);
            }

            List<Long> labelData = labelLines.get(labelType);
            if (labelData == null) {
                labelData = new ArrayList<Long>();

                labelLines.put(labelType, labelData);
            }

            labelData.add((Long) postLine[1]);
        }

        Map<String, String> labelData = new HashMap<String, String>();

        if (labelLines.containsKey(PurchaseOrderReceivedLabelTypes.GRN.toString())) {
            labelData.put(PurchaseOrderReceivedLabelTypes.GRN.toString(), getGRNLabels(labelLines.get(PurchaseOrderReceivedLabelTypes.GRN.toString()), userData));
        }

        return labelData;
    }

    /**
     * Generates a GRN labels String.
     *
     * @param postLineRecords List containing record ID's of lines to be
     * included on labels.
     * @param userData User data.
     * @return A String representation of GRN labels.
     */
    private String getGRNLabels(List<Long> postLineRecords, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostLines.class);
        query.addAndInList("recordID", postLineRecords, false, false);
        query.addAnd("quantity", 0, EMCQueryConditions.GREATER_THAN);

        List parameters = new ArrayList();
        //Dummy data in position 1
        parameters.add("");
        parameters.add(query);

        return labelsBean.getReportResult(parameters, userData);
    }

    /**
     * Return the post line with the specified post master id and transaction
     * number.
     *
     * @param postMasterId Post master id to select on.
     * @param transactionNumber Transaction number to select on.
     * @param userData User data.
     * @return The specified post line, or null, if no line matche the specified
     * criteria.
     */
    private POPPurchasePostLines getPostLine(String postMasterId, String transactionNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostLines.class);
        query.addAnd("postMasterId", postMasterId);
        query.addAnd("transactionNumber", transactionNumber);

        return (POPPurchasePostLines) util.executeSingleResultQuery(query, userData);
    }
}

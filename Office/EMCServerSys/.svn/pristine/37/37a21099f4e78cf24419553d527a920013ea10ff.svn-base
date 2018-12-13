/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop;

import emc.bus.base.BaseEmployeeLocal;
import emc.bus.base.logic.BaseUOMLogicLocal;
import emc.bus.base.logic.EMCUOMConversionException;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.logic.InventoryItemDimensionCombinationLogicLocal;
import emc.bus.inventory.logic.InventoryItemLogicLocal;
import emc.bus.inventory.requirementsplanning.InventoryRequirementsPlanningLocal;
import emc.bus.inventory.transactions.EMCStockException;
import emc.bus.inventory.transactions.ProcessStockTransactionLocal;
import emc.bus.inventory.transactions.TransactionHelper;
import emc.bus.inventory.transactions.TransactionType;
import emc.bus.pop.pricearrangements.POPPriceArrangementsLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.pop.POPParameters;
import emc.entity.pop.POPPurchaseOrderApprovalGroupEmployees;
import emc.entity.pop.POPPurchaseOrderApprovalGroups;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryItemTypes;
import emc.enums.inventory.purchasing.InventoryStopPurchasingType;
import emc.enums.inventory.requirementsplanning.RequirementsPlanningFromType;
import emc.enums.pop.purchaseorders.PurchaseOrderStatus;
import emc.enums.pop.purchaseorders.PurchaseOrderTypes;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerPOPMessageEnum;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class POPPurchaseOrderLinesBean extends EMCEntityBean implements POPPurchaseOrderLinesLocal {

    @EJB
    private InventoryItemMasterLocal itemMasterBean;
    @EJB
    private ProcessStockTransactionLocal stockTransaction;
    @EJB
    private InventoryItemDimensionCombinationLogicLocal dimensionLogicBean;
    @EJB
    private InventoryItemLogicLocal itemLogicBean;
    @EJB
    private POPPurchaseOrderMasterLocal purchaseOrderMasterBean;
    @EJB
    private BaseUOMLogicLocal uomLogicBean;
    @EJB
    private POPParametersLocal parameterBean;
    @EJB
    private BaseEmployeeLocal employeeBean;
    @EJB
    private InventoryRequirementsPlanningLocal requirementsPlanningBean;
    @EJB
    private POPPriceArrangementsLocal priceArrangementBean;

    /**
     * Creates a new instance of POPPurchaseOrderLinesBean
     */
    public POPPurchaseOrderLinesBean() {
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        POPPurchaseOrderLines line = (POPPurchaseOrderLines) dobject;
        Object res = null;
        try {

            //purchaseOrderMasterBean.removeApproval(line.getPurchaseOrderId(), userData);
            EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.pop.POPPurchaseOrderMaster.class.getName());
            qu.addAnd("purchaseOrderId", line.getPurchaseOrderId());
            POPPurchaseOrderMaster mst = (POPPurchaseOrderMaster) util.executeSingleResultQuery(qu, userData);

            //Do this before line quantity is cleared.
            doLineDeleteBOUpdate(line, userData);

            if ((mst != null) && (mst.getPurchaseOrderType().equals(emc.enums.pop.purchaseorders.PurchaseOrderTypes.PURCHASE_ORDER.toString()))) {
                TransactionHelper txH = new TransactionHelper(TransactionType.POP_POST_POLINE);
                txH.setPOPsetQtyToZero(true);
                stockTransaction.post(line, txH, userData); //should reverse the stock transaction
            } else {
                if ((mst != null) && (mst.getPurchaseOrderType().equals(emc.enums.pop.purchaseorders.PurchaseOrderTypes.BLANKET_ORDER.toString()))) {
                    TransactionHelper th = new TransactionHelper(TransactionType.POP_UPDATE_BLANCKET_ORDER);
                    th.setPOPBlancketOrderQty(-1 * line.getQuantity());
                    stockTransaction.post(line, th, userData);
                }
            }

            res = super.delete(line, userData);

            purchaseOrderMasterBean.setPOMasterStatus(line.getPurchaseOrderId(), userData);

            if (PurchaseOrderTypes.PURCHASE_ORDER.toString().equals(mst.getPurchaseOrderType())) {
                requirementsPlanningBean.deleteSupply(RequirementsPlanningFromType.PURCHASE_ORDER, line.getRecordID(), userData);
            }
        } catch (Exception ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Could not delete line " + ex, userData);
            throw new EMCEntityBeanException(ex.getMessage());
        }
        return res;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return privateInsert(iobject, true, userData);
    }

    @Override
    public Object insertFromRelease(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return privateInsert(iobject, false, userData);
    }

    private Object privateInsert(Object iobject, boolean doRequirementsPlanning, EMCUserData userData) throws EMCEntityBeanException {
        try {
            POPPurchaseOrderLines line = (POPPurchaseOrderLines) iobject;

            calculateLineNetAmount(line, userData);

            POPPurchaseOrderLines credLine = (POPPurchaseOrderLines) super.insert(iobject, userData);

            EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.pop.POPPurchaseOrderMaster.class.getName());
            qu.addAnd("purchaseOrderId", credLine.getPurchaseOrderId());

            POPPurchaseOrderMaster mst = (POPPurchaseOrderMaster) util.executeSingleResultQuery(qu, userData);

            if ((mst != null) && (mst.getPurchaseOrderType().equals(emc.enums.pop.purchaseorders.PurchaseOrderTypes.PURCHASE_ORDER.toString()))) {
                stockTransaction.post(line, new TransactionHelper(TransactionType.POP_POST_POLINE), userData);
            } else {
                if ((mst != null) && (mst.getPurchaseOrderType().equals(emc.enums.pop.purchaseorders.PurchaseOrderTypes.BLANKET_ORDER.toString()))) {
                    TransactionHelper th = new TransactionHelper(TransactionType.POP_UPDATE_BLANCKET_ORDER);
                    th.setPOPBlancketOrderQty(line.getQuantity());
                    stockTransaction.post(line, th, userData);
                }
            }

            if (doRequirementsPlanning) {
                if (PurchaseOrderTypes.PURCHASE_ORDER.toString().equals(mst.getPurchaseOrderType())) {
                    requirementsPlanningBean.insertSupply(RequirementsPlanningFromType.PURCHASE_ORDER, line.getRecordID(), line.getPurchaseOrderId(), line.getDeliveryDate(), null,
                            line.getItemId(), line.getItemDimension1(), line.getItemDimension2(), line.getItemDimension3(), null, util.getBigDecimal(line.getQuantity() - line.getItemsReceived()), util.getBigDecimal(line.getQuantity()),
                            false, 0, userData);
                }
            }
            return credLine;
        } catch (Exception ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Could not insert line " + ex, userData);
            throw new EMCEntityBeanException(ex.getMessage());
        }
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        POPPurchaseOrderLines line = (POPPurchaseOrderLines) vobject;
        POPPurchaseOrderMaster master = purchaseOrderMasterBean.findPurchaseOrder(line.getPurchaseOrderId(), userData);

        boolean ret = super.doInsertValidation(vobject, userData);

        boolean validDimensions = true;

        validDimensions = validDimensions && validateDimension(line, userData);

        validDimensions = validDimensions && validateWarehouse(line, userData);

        if (!validDimensions) {
            Logger.getLogger("emc").log(Level.SEVERE, "The specified dimensions are not valid for the specified item.", userData);
        }

        ret = ret && validateItem(line, master, false, userData);
        ret = ret && validDimensions;
        ret = ret && checkDimensions(line, userData);
        ret = ret && validateQuantity(line, false, userData);
        ret = ret && validateReason(line, userData);
        ret = ret && validateMillPrices(line, userData);

        if (ret && !isBlank(line.getPurchaseOrderId())) {
            if (master != null && !master.getStatus().equals(PurchaseOrderStatus.REQUISITION.toString()) && !master.getStatus().equals(PurchaseOrderStatus.APPROVED.toString())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Cannot insert a new line in a Purchase Order with a status of ordered or higher.", userData);
                ret = false;
            }

        }

        //ret = doAmountUpdate(line, userData);
        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);

        POPPurchaseOrderLines line = (POPPurchaseOrderLines) vobject;

        POPPurchaseOrderMaster master = purchaseOrderMasterBean.findPurchaseOrder(line.getPurchaseOrderId(), userData);

        ret = ret && validateDimension(line, userData);

        ret = ret && validateWarehouse(line, userData);

        if (!ret) {
            Logger.getLogger("emc").log(Level.SEVERE, "The specified dimensions are not valid for the specified item.", userData);
        }

        ret = ret && validateItem(line, master, false, userData);
        ret = ret && checkDimensions(line, userData);
        ret = ret && validateQuantity(line, true, userData);
        ret = ret && validateReason(line, userData);
        ret = ret && validateMillPrices(line, userData);

        return ret;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        try {
            POPPurchaseOrderLines persisted;
            double persistedNetAmount = 0.0;
            double persistedQty = 0.0;
            double persistedReceiveQty = 0.0;
            POPPurchaseOrderLines line = (POPPurchaseOrderLines) uobject;
            if (util.containsEntity(line, userData)) {
                persisted = (POPPurchaseOrderLines) this.doCloneWithSuperFields(line, userData);
                util.refreshEntity(line, userData);
                persistedNetAmount = line.getNetAmount();
                persistedQty = line.getQuantity();
                persistedReceiveQty = line.getItemsReceived();
                POPPurchaseOrderLines temp = line;
                line = persisted;
                persisted = temp;
            } else {
                persisted = (POPPurchaseOrderLines) util.findPersisted(POPPurchaseOrderLines.class, line.getRecordID(), userData);
                persistedNetAmount = persisted.getNetAmount();
                persistedQty = persisted.getQuantity();
                persistedReceiveQty = persisted.getItemsReceived();
            }

            calculateLineNetAmount(line, userData);
            //stock transaction
            EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.pop.POPPurchaseOrderMaster.class.getName());
            qu.addAnd("purchaseOrderId", line.getPurchaseOrderId());
            POPPurchaseOrderMaster mst = (POPPurchaseOrderMaster) util.executeSingleResultQuery(qu, userData);
            //Change status back to Requesting if price or quantity was changed
            if (PurchaseOrderStatus.ORDERED.toString().equals(mst.getStatus())) {
                if (persistedQty != line.getQuantity() || persistedNetAmount != line.getNetAmount()) {
                    purchaseOrderMasterBean.removeApproval(mst, userData);
                }
            }
            if ((mst != null) && (mst.getPurchaseOrderType().equals(emc.enums.pop.purchaseorders.PurchaseOrderTypes.PURCHASE_ORDER.toString()))) {
                stockTransaction.post(line, new TransactionHelper(TransactionType.POP_POST_POLINE), userData);
            } else {
                if ((mst != null) && (mst.getPurchaseOrderType().equals(emc.enums.pop.purchaseorders.PurchaseOrderTypes.BLANKET_ORDER.toString()))) {
                    double bODelta = line.getQuantity() - persistedQty;
                    if (bODelta != 0.0) {
                        TransactionHelper th = new TransactionHelper(TransactionType.POP_UPDATE_BLANCKET_ORDER);
                        th.setPOPBlancketOrderQty(bODelta);
                        stockTransaction.post(line, th, userData);
                    }
                    bODelta = persistedReceiveQty - line.getItemsReceived();
                    if (bODelta != 0.0) {
                        TransactionHelper th = new TransactionHelper(TransactionType.POP_UPDATE_BLANCKET_ORDER);
                        th.setPOPBlancketOrderQty(bODelta);
                        stockTransaction.post(line, th, userData);
                    }
                }
            }

            if (PurchaseOrderTypes.PURCHASE_ORDER.toString().equals(mst.getPurchaseOrderType())) {
                requirementsPlanningBean.updateSupply(RequirementsPlanningFromType.PURCHASE_ORDER, line.getRecordID(), line.getPurchaseOrderId(), line.getDeliveryDate(), null,
                        line.getItemId(), line.getItemDimension1(), line.getItemDimension2(), line.getItemDimension3(), null, util.getBigDecimal(line.getQuantity() - line.getItemsReceived()), util.getBigDecimal(line.getQuantity()),
                        false, 0, userData);
            }

            //This is used for updating Blanket order line quantities
            double qtyDif = line.getQuantity() - persistedQty;

            //Purchase Order Line updated before blanket order line.
            line = (POPPurchaseOrderLines) super.update(line, userData);

            doLineUpdateBOUpdate(line, qtyDif, userData);

            if (persistedNetAmount != line.getNetAmount()) {
                if (PurchaseOrderStatus.APPROVED.toString().equals(mst.getStatus())) {
                    purchaseOrderMasterBean.removeApproval(line.getPurchaseOrderId(), userData);
                }
            }
            if (!"UPDATE".equals((String) userData.getUserData(4))) {
                purchaseOrderMasterBean.setPOMasterStatus(line.getPurchaseOrderId(), userData);
            }

            return line;
        } catch (EMCStockException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Could not insert line " + ex, userData);
            throw new EMCEntityBeanException(ex.getMessage());
        }
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object ret = super.validateField(fieldNameToValidate, theRecord, userData);
        Boolean valid = (Boolean) ret;

        POPPurchaseOrderLines line = (POPPurchaseOrderLines) theRecord;
        POPPurchaseOrderMaster master = purchaseOrderMasterBean.findPurchaseOrder(line.getPurchaseOrderId(), userData);
        POPParameters params = parameterBean.getPOPParameters(userData);

        //Populate warehouse
        if (fieldNameToValidate.equals("purchaseOrderId")) {
            doHeaderFields(line, userData);
        }

        //Boolean fields
        if (fieldNameToValidate.equals("itemDimension1") || fieldNameToValidate.equals("itemDimension2") || fieldNameToValidate.equals("itemDimension3")) {
            if (line.getItemsReceived() != 0) {
                Logger.getLogger("emc").log(Level.SEVERE, "Dimensions may not be changed after items have been received.", userData);
                valid = false;
            } else {
                valid = valid && validateDimension(line, userData);
            }
        } else {
            if (fieldNameToValidate.equals("quantity")) {
                valid = valid && validateQuantity(line, false, userData);
            } else {
                if (fieldNameToValidate.equals("warehouse")) {
                    valid = valid && validateWarehouse(line, userData);
                }
            }
        }

        if (fieldNameToValidate.equals("quantity") || fieldNameToValidate.equals("itemPrice") || fieldNameToValidate.equals("discountPercentage")) {
            valid = valid && doAmountUpdate(line, fieldNameToValidate, userData);
        }

        if (fieldNameToValidate.equals("itemPrice")) {
            if (valid) {
                setCostChangeFlag(line, master, params, userData);
            }
        }

        if (fieldNameToValidate.equals("reason")) {
            valid = valid && validateReason(line, userData);
        }

        if (valid) {
            ret = line;

            if (fieldNameToValidate.equals("itemDimension1") || fieldNameToValidate.equals("itemDimension2") || fieldNameToValidate.equals("itemDimension3") || fieldNameToValidate.equals("quantity") || fieldNameToValidate.equals("discountPercentage") || fieldNameToValidate.equals("uom")) {
                try {
                    if (!isBlank(line.getItemId())) {
                        if (!updateCost(line, master, params, userData)) {
                            ret = false;
                        }
                        setCostChangeFlag(line, master, params, userData);
                    }
                } catch (EMCEntityBeanException ex) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to convert unit of measure in order to calculate cost.", userData);
                    ret = false;
                }
            }
        } else {
            ret = valid;
        }

        if (fieldNameToValidate.equals("warehouse")) {
            if (!isBlank(line.getPurchaseOrderId())) {
                if (master != null) {
                    if (PurchaseOrderStatus.PARTIALLY_RECEIVED.toString().equals(master.getStatus()) || PurchaseOrderStatus.RECEIVED.toString().equals(master.getStatus())) {
                        Logger.getLogger("emc").log(Level.SEVERE, "You may not change the warehouse after goods were received.", userData);
                        ret = false;
                    }
                }
            }
        }
        if (fieldNameToValidate.equals("fabricPrice") || fieldNameToValidate.equals("printPrice")) {
            if (util.compareDouble(line.getFabricPrice() + line.getPrintPrice(), line.getItemPrice()) > 0) {
                Logger.getLogger("emc").log(Level.SEVERE, "The Fabric Price plus the Print Price may not be greater than the Cost Price.", userData);
                ret = false;
            }
        }

        return ret;
    }

    private void doHeaderFields(POPPurchaseOrderLines line, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class.getName());
        query.addAnd("purchaseOrderId", line.getPurchaseOrderId());
        POPPurchaseOrderMaster master = (POPPurchaseOrderMaster) util.executeSingleResultQuery(query, userData);

        if (line.getPurchaseOrderId() != null) {
            if (isBlank(line.getWarehouse())) {
                if (!isBlank(master.getWarehouse())) {
                    line.setWarehouse(master.getWarehouse());
                }
            }

            if (isBlank(line.getDeliveryDate())) {
                line.setDeliveryDate(master.getRequestedDeliveryDate());
            }
        }
    }

    /**
     * This method validates the dimensions on line's item.
     */
    private boolean validateDimension(POPPurchaseOrderLines line, EMCUserData userData) {
        return dimensionLogicBean.validateDimensionValues(line.getItemId(), line.getItemDimension1(), line.getItemDimension2(), line.getItemDimension3(), userData);
    }

    /**
     * This method is used to validate the warehouse on a line.
     */
    private boolean validateWarehouse(POPPurchaseOrderLines line, EMCUserData userData) {
        boolean ret = true;

        String dimensionGroup = itemMasterBean.getItemDimensionGroup(line.getItemId(), userData);

        if (dimensionGroup != null) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
            query.addField("warehouseActive");
            query.addAnd("itemDimensionGroupId", line.getItemId());

            Boolean warehouseActive = (Boolean) util.executeSingleResultQuery(query, userData);

            if (warehouseActive != null && warehouseActive && isBlank(line.getWarehouse())) {
                ret = false;
                if (!ret) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Warehouse required", userData);
                }
            }
        }
        return ret;
    }

    /**
     * This method checks that all the active dimensions on an item have values
     */
    private boolean checkDimensions(POPPurchaseOrderLines line, EMCUserData userData) {
        return dimensionLogicBean.validateActiveDimensions(line.getItemId(), line.getItemDimension1(), line.getItemDimension2(), line.getItemDimension3(), line.getWarehouse(), true, userData);
    }

    /**
     * This method calculates the net amount of a line
     */
    @Override
    public void calculateLineNetAmount(POPPurchaseOrderLines line, EMCUserData userData) {
        //Add query to get VAT percentage
        double grossAmount = line.getQuantity() * line.getItemPrice();
        line.setNetAmount(grossAmount - (grossAmount * line.getDiscountPercentage() / 100));
    }

    /**
     * This method is used to execute the required logic when a field affecting
     * the total of a purchase order is changed.
     */
    private boolean doAmountUpdate(POPPurchaseOrderLines line, String fieldName, EMCUserData userData) {
        POPPurchaseOrderMaster master = purchaseOrderMasterBean.findPurchaseOrder(line.getPurchaseOrderId(), userData);

        if (master == null) {
            Logger.getLogger("emc").log(Level.WARNING, "Could not validate.  Please save Purchase Order master first.", userData);
            return false;
        }

        PurchaseOrderStatus status = PurchaseOrderStatus.fromString(master.getStatus());

        switch (status) {

            //No break, case should fall through
            case APPROVED:
            case ORDERED:
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderApprovalGroups.class);
                query.addTableAnd(POPPurchaseOrderApprovalGroupEmployees.class.getName(), "purchaseOrderApprovalGroupId", POPPurchaseOrderApprovalGroups.class.getName(), "purchaseOrderApprovalGroupId");
                query.addAnd("priceQtyChange", true, POPPurchaseOrderApprovalGroups.class.getName());
                query.addAnd("employeeId", employeeBean.findEmployee(userData.getUserName(), userData), POPPurchaseOrderApprovalGroupEmployees.class.getName());
                if (util.exists(query, userData)) {
                    Logger.getLogger("emc").log(Level.WARNING, "The purchase order will be unapproved when the line is saved due to the current updates.", userData);
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "You do not have permissions to update the quantity or price at this time.", userData);
                    return false;
                }
            case REQUISITION:
                calculateLineNetAmount(line, userData);
                return true;
            case RECEIVED: //Fall through
            case INVOICED: //Fall through
            case PARTIALLY_RECEIVED:
                Logger.getLogger("emc").log(Level.SEVERE, line.getDisplayLabelForField(fieldName, userData) + " may not be changed after a Purchase Order is received", userData);
                return false;
            default:
                if (line.getRecordID() != 0) {
                    POPPurchaseOrderLines persisted = (POPPurchaseOrderLines) util.findPersisted(POPPurchaseOrderLines.class, line.getRecordID(), userData);

                    if (fieldName.equals("discountPercentage")) {
                        if (line.getDiscountPercentage() < persisted.getDiscountPercentage()) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Discount may not be decreased after Purchase Order is ordered", userData);
                            return false;
                        }
                    } else {
                        if (fieldName.equals("quantity")) {
                            if (line.getQuantity() > persisted.getQuantity()) {
                                Logger.getLogger("emc").log(Level.SEVERE, "Quantity may not be increased after Purchase Order is ordered", userData);
                                return false;
                            }
                        } else {
                            if (fieldName.equals("itemPrice")) {
                                if (line.getItemPrice() > persisted.getItemPrice()) {
                                    Logger.getLogger("emc").log(Level.SEVERE, "Item Price may not be increased after Purchase Order is ordered", userData);
                                    return false;
                                }
                            } else {
                                if (fieldName.equals("uom")) {
                                    Logger.getLogger("emc").log(Level.SEVERE, "Unit of measure may not be changed after purchase order has been ordered.", userData);
                                }
                            }
                        }
                    }
                }
                return true;

        }

    }

    /**
     * This method updates the item price on a line. It assumes that the given
     * line has a valid item id.
     */
    private boolean updateCost(POPPurchaseOrderLines line, POPPurchaseOrderMaster master, POPParameters params, EMCUserData userData) throws EMCEntityBeanException {
        InventoryItemMaster item = itemMasterBean.findItem(line.getItemId(), userData);

        if (item != null) {
        }
        double costPrice = 0;

        BigDecimal price = priceArrangementBean.findItemPrice(null, item, null, null, null, params, null, master.getSupplier(), line.getItemId(), line.getItemDimension1(), line.getItemDimension2(), line.getItemDimension3(), master.getOrderedDate(), new BigDecimal(line.getQuantity()), userData);

        if (price != null && price.compareTo(BigDecimal.ZERO) != 0) {
            costPrice = price.doubleValue();
        }

        if (costPrice == 0) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
            query.addTableAnd(POPPurchaseOrderMaster.class.getName(), "supplierNo", InventoryReference.class.getName(), "supplier");
            query.addAnd("purchaseOrderId", line.getPurchaseOrderId(), POPPurchaseOrderMaster.class.getName());
            query.addAnd("itemId", line.getItemId(), InventoryReference.class.getName());
            query.addAnd("dimension1", line.getItemDimension1(), InventoryReference.class.getName());
            query.addAnd("dimension2", line.getItemDimension2(), InventoryReference.class.getName());
            query.addAnd("dimension3", line.getItemDimension3(), InventoryReference.class.getName());
            InventoryReference supplier = (InventoryReference) util.executeSingleResultQuery(query, userData);
            if (supplier != null) {
                costPrice = supplier.getPurchasePrice();
            }
        }
        if (costPrice != 0) {
            try {
                double baseUnits = uomLogicBean.convertToItemBase(line.getItemId(), line.getQuantity(), line.getUom(), userData);

                if (line.getQuantity() != 0) {
                    line.setItemPrice((baseUnits * costPrice) / line.getQuantity());
                } else {
                    line.setItemPrice(costPrice);
                }

                return doAmountUpdate(line, null, userData);
            } catch (EMCUOMConversionException ex) {
                throw new EMCEntityBeanException(ex);
            }
        }

        return true;
    }

    /**
     * This method is used to validate quantities when a Purchase Order line is
     * updated.
     */
    public boolean validateQuantity(POPPurchaseOrderLines line, boolean updateValidation, EMCUserData userData) {
        if (line.getQuantity() == 0) {
            Logger.getLogger("emc").log(Level.WARNING, "Quantity may not be 0.", userData);
            return false;
        } else {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
            query.addField("purchaseMinOrderQty");
            query.addAnd("itemId", line.getItemId());

            double minQty = (Double) util.executeSingleResultQuery(query, userData);

            if (minQty > line.getQuantity()) {
                Logger.getLogger("emc").log(Level.WARNING, "Quantity smaller than minimum order quantity.", userData);
            }

            return doPOLineQuantityValidation(line, userData);
        }
    }

    /**
     * This method validates quantities on Purchase Order Lines and returns
     * boolean value indicating whether the quantity is valid.
     */
    private boolean doPOLineQuantityValidation(POPPurchaseOrderLines line, EMCUserData userData) {
        boolean ret = true;

        Long blanketOrderLineId = line.getBlanketOrderLineId();

        //Only call after line is saved
        if (blanketOrderLineId != null && line.getRecordID() != 0) {
            POPPurchaseOrderLines persistedLine = (POPPurchaseOrderLines) util.findPersisted(POPPurchaseOrderLines.class, line.getRecordID(), userData);

            double qtyDif = line.getQuantity() - persistedLine.getQuantity();

            //Quantity increased
            if (qtyDif > 0) {
                POPPurchaseOrderLines blanketOrderLine = getBlanketOrderLine(line, userData);

                double itemsReceived = blanketOrderLine.getItemsReceived();

                if (itemsReceived == blanketOrderLine.getQuantity()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Blanket Order Line fully received.  Quantity cannot be updated.", userData);
                    return false;
                }

                try {
                    qtyDif = uomLogicBean.convertFromUOMToUOM(line.getItemId(), line.getUom(), blanketOrderLine.getUom(), qtyDif, userData);
                } catch (EMCUOMConversionException uomEx) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Could not convert from Purchase Order Line UOM (" + line.getUom() + ") to Blanket Order Line UOM(" + blanketOrderLine.getUom() + ").", userData);
                    return false;
                }

                if (itemsReceived + qtyDif > blanketOrderLine.getQuantity()) {
                    double overReceivedItems = (itemsReceived + qtyDif) - blanketOrderLine.getQuantity();

                    double maxOverReceiveItems = getMaxOverReceiveItems(blanketOrderLine, true, blanketOrderLine.getUom(), userData);

                    if (maxOverReceiveItems == -1) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Quantity will lead to Blanket Order over release.  Blanket Order may not be over released.", userData);
                        return false;
                    } else {
                        if (overReceivedItems > maxOverReceiveItems) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Quantity increase exceeds maximum over release quantity on Blanket Order.", userData);
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    //Not over received
                    return true;
                }
            } else {
                return true;
            }
        }

        return ret;
    }

    /**
     * This method returns the maximum number of items that may be over received
     * on a line, in the specified UOM. Returns -1 if over release is not
     * allowed.
     */
    public double getMaxOverReceiveItems(POPPurchaseOrderLines line, boolean isBlanketOrderLine, String uom, EMCUserData userData) {
        double overReleaseQty = -1;
        double maxOverReleasePer = -1;

        EMCQuery overRelQuery = new EMCQuery(enumQueryTypes.SELECT, POPParameters.class.getName());

        if (isBlanketOrderLine) {
            overRelQuery.addField("allowBlanketOrderOverRelease");
            overRelQuery.addField("blanketOrderOverReleasePercentage");
        } else {
            overRelQuery.addField("allowOverReceive");
            overRelQuery.addField("overPercentage");
        }

        List queryResult = util.executeGeneralSelectQuery(overRelQuery);

        if (queryResult.size() != 0) {
            if ((Boolean) ((Object[]) queryResult.get(0))[0]) {
                maxOverReleasePer = (Double) ((Object[]) queryResult.get(0))[1];

                overReleaseQty = line.getQuantity() * maxOverReleasePer / 100;

                try {
                    overReleaseQty = uomLogicBean.convertFromUOMToUOM(line.getItemId(), line.getUom(), uom, overReleaseQty, userData);
                } catch (EMCUOMConversionException uomEx) {
                    if (EMCDebug.getDebug()) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Could not convert from " + line.getUom() + " to " + uom, userData);
                    }
                }
            }
        } else {
            Logger.getLogger("emc").log(Level.WARNING, "Failed to retrieve POPParameters.  Please set up POPParameters.", userData);
        }

        return overReleaseQty;
    }

    /**
     * This method returns the Blanket Order line from which a Purchase Order
     * Line was created. May be null.
     */
    private POPPurchaseOrderLines getBlanketOrderLine(POPPurchaseOrderLines line, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class.getName());
        query.addAnd("recordID", line.getBlanketOrderLineId());

        return (POPPurchaseOrderLines) util.executeSingleResultQuery(query, userData);
    }

    /**
     * This method is used to update a Blanket Order line (if necessary) when
     * the quantity on a Purchase Order line is updated.
     */
    private void doLineUpdateBOUpdate(POPPurchaseOrderLines line, double qtyDif, EMCUserData userData) throws EMCEntityBeanException {
        //Only do if line was created from a blanket order
        Long blanketOrderLineRecId = line.getBlanketOrderLineId();

        //Value in userdata[3] comes from user prompt.  If not there, update BO anyway for backwards compatibility
        if (blanketOrderLineRecId != null && (!(userData.getUserData(3) instanceof Boolean) || (Boolean) userData.getUserData(3))) {
            POPPurchaseOrderMaster blanketOrderMaster = purchaseOrderMasterBean.getBlanketOrder(line.getPurchaseOrderId(), userData);

            //double[] quantities = purchaseOrderMasterBean.getPurchaseOrderQuantities(blanketOrderMaster.getPurchaseOrderId(), userData);
            //double boTotalQuantity = quantities[0];
            //double boReceivedQuantity= quantities[1] + quantities[2];
            POPPurchaseOrderLines blanketOrderLine = getBlanketOrderLine(line, userData);

            if (blanketOrderLine == null) {
                //Blanket order has been deleted, return
                return;
            }

            try {
                qtyDif = uomLogicBean.convertFromUOMToUOM(line.getItemId(), line.getUom(), blanketOrderLine.getUom(), qtyDif, userData);
            } catch (EMCUOMConversionException uomEx) {
                throw new EMCEntityBeanException(uomEx);
            }

            if (qtyDif > 0) {
                //Quantity increased
                double newRecQty = blanketOrderLine.getItemsReceived() + qtyDif;

                double boQty = blanketOrderLine.getQuantity();

                if (newRecQty > boQty) {
                    blanketOrderLine.setItemsReceived(boQty);
                    blanketOrderLine.setOverReceiveQty(newRecQty - boQty);
                } else {
                    blanketOrderLine.setItemsReceived(newRecQty);
                }

                update(blanketOrderLine, userData);

//                if (boReceivedQuantity + qtyDif >= boTotalQuantity) {
//                    blanketOrderMaster.setStatus(PurchaseOrderStatus.RECEIVED.toString());
//                    purchaseOrderMasterBean.update(blanketOrderMaster, userData);
//                } 
            } else {
                if (qtyDif < 0) {
                    //Quantity decreased
                    double newQty = blanketOrderLine.getOverReceiveQty() + qtyDif;

                    if (newQty >= 0) {
                        //Still over received
                        blanketOrderLine.setOverReceiveQty(newQty);
                    } else {
                        blanketOrderLine.setOverReceiveQty(0);
                        //Adding negative value = subtracting
                        blanketOrderLine.setItemsReceived(blanketOrderLine.getItemsReceived() + newQty);
                    }

                    update(blanketOrderLine, userData);

                    /*if (boReceivedQuantity + qtyDif < boTotalQuantity) {
                     blanketOrderMaster.setStatus(PurchaseOrderStatus.PARTIALLY_RECEIVED.toString());
                     purchaseOrderMasterBean.update(blanketOrderMaster, userData);
                     } */
                }
            }
        }
    }

    /**
     * This method is used to update a Blanket Order line (if necessary) when a
     * Purchase Order line is deleted.
     */
    private void doLineDeleteBOUpdate(POPPurchaseOrderLines line, EMCUserData userData) throws EMCEntityBeanException {
        //Only do if line was created from a blanket order
        Long blanketOrderLineRecId = line.getBlanketOrderLineId();

        //Value in userdata[3] comes from user prompt.  If not there, update BO anyway for backwards compatibility
        if (blanketOrderLineRecId != null && (!(userData.getUserData(3) instanceof Boolean) || (Boolean) userData.getUserData(3))) {
            POPPurchaseOrderMaster blanketOrderMaster = purchaseOrderMasterBean.getBlanketOrder(line.getPurchaseOrderId(), userData);

            //double boReceivedQuantity= purchaseOrderMasterBean.getPurchaseOrderQuantities(blanketOrderMaster.getPurchaseOrderId(), userData)[1];
            POPPurchaseOrderLines blanketOrderLine = getBlanketOrderLine(line, userData);

            double newQuantity = line.getQuantity();

            try {
                newQuantity = blanketOrderLine.getOverReceiveQty() - uomLogicBean.convertFromUOMToUOM(line.getItemId(), line.getUom(), blanketOrderLine.getUom(), newQuantity, userData);
            } catch (EMCUOMConversionException uomEx) {
                throw new EMCEntityBeanException(uomEx);
            }

            if (newQuantity >= 0) {
                //Still over received
                blanketOrderLine.setOverReceiveQty(newQuantity);
            } else {
                blanketOrderLine.setOverReceiveQty(0);
                //Adding negative value = subtracting
                blanketOrderLine.setItemsReceived(blanketOrderLine.getItemsReceived() + newQuantity);
            }

            update(blanketOrderLine, userData);

//            if (boReceivedQuantity + newQuantity == 0) {
//                blanketOrderMaster.setStatus(PurchaseOrderStatus.ORDERED.toString());
//                
//                purchaseOrderMasterBean.update(blanketOrderMaster, userData);
//            } 
        }
    }

    private void setCostChangeFlag(POPPurchaseOrderLines line, POPPurchaseOrderMaster master, POPParameters params, EMCUserData userData) {
        if (line.getItemPrice() == 0) {
            line.setCostChange(true);
            line.setWhoApproved(null);//Clear approval
        } else {
            InventoryItemMaster item = itemMasterBean.findItem(line.getItemId(), userData);
            double itemPrice = 0;
            try {
                itemPrice = priceArrangementBean.findItemPrice(null, item, null, null, null, params, null, master.getSupplier(), line.getItemId(), line.getItemDimension1(), line.getItemDimension2(), line.getItemDimension3(), master.getOrderedDate(), new BigDecimal(line.getQuantity()), userData).doubleValue();
            } catch (EMCEntityBeanException ex) {
                //Do nothing.  Continue with old code.
            }

            if (itemPrice == 0) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
                query.addTableAnd(POPPurchaseOrderMaster.class.getName(), "supplierNo", InventoryReference.class.getName(), "supplier");
                query.addAnd("purchaseOrderId", line.getPurchaseOrderId(), POPPurchaseOrderMaster.class.getName());
                query.addAnd("itemId", line.getItemId(), InventoryReference.class.getName());
                query.addAnd("dimension1", line.getItemDimension1(), InventoryReference.class.getName());
                query.addAnd("dimension2", line.getItemDimension2(), InventoryReference.class.getName());
                query.addAnd("dimension3", line.getItemDimension3(), InventoryReference.class.getName());
                InventoryReference supplier = (InventoryReference) util.executeSingleResultQuery(query, userData);
                if (supplier != null) {
                    itemPrice = supplier.getPurchasePrice();
                }
            }

            if (itemPrice == 0) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
                query.addAnd("itemId", line.getItemId());
                itemPrice = ((InventoryItemMaster) util.executeSingleResultQuery(query, userData)).getPurchasePrice();
            }
            if (itemPrice != line.getItemPrice()) {
                line.setCostChange(true);
                line.setWhoApproved(null);//Clear approval
            } else {
                line.setCostChange(false);
            }
        }
    }

    private boolean validateReason(POPPurchaseOrderLines line, EMCUserData userData) {
        if (line.getCostChange()) {
            if (isBlank(line.getReason())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Item Price is not the same as on the item master. Please give a reason for this. (Cost Change Tab)", userData);
                return false;
            }
        } else {
            if (!isBlank(line.getReason())) {
                //Logger.getLogger("emc").log(Level.SEVERE, "No need to set the cost reason field.", userData);
                //return false;
            }
        }
        return true;
    }

    /**
     * This method ensures that the item on the given Purchase Order line may be
     * purchased. Certain parts of this method are only applicable to field
     * validation.
     */
    @Override
    public boolean validateItem(POPPurchaseOrderLines line, POPPurchaseOrderMaster master, boolean fieldValidation, EMCUserData userData) {
        InventoryItemMaster item = itemMasterBean.findItem(line.getItemId(), userData);
        POPParameters params = parameterBean.getPOPParameters(userData);
        boolean updateMaster = false;

        if (!isBlank(item.getProductionBOMId())) {

            if (params.getAllowBOMItemsPurchase()) {
                Logger.getLogger("emc").log(Level.WARNING, "Item is normally manufactured.", userData);
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "You may not purchase items that can be manufactured.", userData);
                return false;
            }
        }

        if (!isPurchaseValid(item)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Phantom item may not be purchased.", userData);
            return false;
        }

        String[] stopPurchasing = itemLogicBean.getStopPurchasingStatus(line.getItemId(), line.getItemDimension1(), line.getItemDimension2(), line.getItemDimension3(), userData);
        if (stopPurchasing[0].equals(InventoryStopPurchasingType.YES.toString())) {
            Logger.getLogger("emc").log(Level.SEVERE, "Purchasing of this item has been stopped.  This item may not be purchased.", userData);
            return false;
        } else if (stopPurchasing[0].equals(InventoryStopPurchasingType.OVERSEE.toString()) && !isBlank(stopPurchasing[1]) && !util.checkObjectsEqual(stopPurchasing[1], master.getApprovalGrp())) {
            Logger.getLogger("emc").log(Level.SEVERE, "The Purchase Order Approval Group has been changed to " + stopPurchasing[1] + " as the entered line requires overseeing", userData);
            master.setApprovalGrp(stopPurchasing[1]);
            updateMaster = true;
        }

        if (fieldValidation) {
            doItem(line, master, item, params, userData);
        }

        if (!"UPDATE".equals((String) userData.getUserData(4))) {
            master.setWidthPlain(item.getSpecWidth() + " " + item.getSpecWidthUOM());
            master.setWeightPlain(item.getSpecWeight() + " " + item.getSpecWeightUOM());
            updateMaster = true;
        }

        if (updateMaster) {
            try {
                purchaseOrderMasterBean.update(master, userData);
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to set Item specs on Master.", userData);
            }
        }

        return true;
    }

    /**
     * This method is used to pull the required fields from the item master
     * table when a supplier id is changed/added on a purchase order.
     */
    private void doItem(POPPurchaseOrderLines line, POPPurchaseOrderMaster master, InventoryItemMaster item, POPParameters params, EMCUserData userData) {
        try {
            BigDecimal price = priceArrangementBean.findItemPrice(null, item, null, null, null, params, null, master.getSupplier(), line.getItemId(), line.getItemDimension1(), line.getItemDimension2(), line.getItemDimension3(), master.getOrderedDate(), new BigDecimal(line.getQuantity()), userData);

            if (price == null || price.compareTo(BigDecimal.ZERO) == 0) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
                query.addTableAnd(POPPurchaseOrderMaster.class.getName(), "supplierNo", InventoryReference.class.getName(), "supplier");
                query.addAnd("purchaseOrderId", line.getPurchaseOrderId(), POPPurchaseOrderMaster.class.getName());
                query.addAnd("itemId", line.getItemId(), InventoryReference.class.getName());
                query.addAnd("dimension1", line.getItemDimension1(), InventoryReference.class.getName());
                query.addAnd("dimension2", line.getItemDimension2(), InventoryReference.class.getName());
                query.addAnd("dimension3", line.getItemDimension3(), InventoryReference.class.getName());
                InventoryReference supplier = (InventoryReference) util.executeSingleResultQuery(query, userData);
                if (supplier != null) {
                    price = util.getBigDecimal(supplier.getPurchasePrice());
                }
            }

            line.setItemPrice(price == null ? 0.0 : price.doubleValue());

        } catch (EMCEntityBeanException ex) {
        }

        String uom = item.getPurchaseUOM();
        line.setUom(uom != null ? uom : item.getBaseUOM());
        line.setQuantity(item.getPurchaseMinOrderQty());
        //calculateLineNetAmount(line,userData);

        String vatCode = item.getPurchaseVatCode();

        if (!isBlank(vatCode)) {
            line.setVatCode(vatCode);
        }

        if (isBlank(line.getWarehouse())) {
            line.setWarehouse(item.getDefaultWarehouse());
        }
    }

    /**
     * This method checks that an item can be purchased. (Is not a phantom)
     */
    private boolean isPurchaseValid(InventoryItemMaster item) {
        return !(item.getItemType().equals(InventoryItemTypes.PHANTOM.toString()));
    }

    private boolean validateMillPrices(POPPurchaseOrderLines line, EMCUserData userData) {
        if (util.compareDouble(line.getFabricPrice(), 0) != 0 || util.compareDouble(line.getPrintPrice(), 0) != 0) {
            if (util.compareDouble(line.getFabricPrice() + line.getPrintPrice(), line.getItemPrice()) != 0) {
                Logger.getLogger("emc").log(Level.SEVERE, "The fabric price and print price does not match the item price.", userData);
                return false;
            }
        }
        return true;
    }

    /**
     * Splits the specified Purchase Order line.
     *
     * @param line Line to split.
     * @param splitQuantity Quantity to split.
     * @param revisedDate Revised date.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws emc.framework.EMCEntityBeanException
     */
    @Override
    public boolean splitLine(POPPurchaseOrderLines line, double splitQuantity, Date revisedDate, EMCUserData userData) throws EMCEntityBeanException {
        //Check that quantity is valid
        if (util.compareDouble(line.getQuantity() - line.getItemsReceived(), splitQuantity) < 0) {
            logMessage(Level.SEVERE, ServerPOPMessageEnum.INVALID_SPLIT_QTY, userData);
            return false;
        } else {
            POPPurchaseOrderMaster master = purchaseOrderMasterBean.findPurchaseOrder(line.getPurchaseOrderId(), userData);

            if (master != null && PurchaseOrderStatus.fromString(master.getStatus()) == PurchaseOrderStatus.RECEIVED) {
                logMessage(Level.SEVERE, ServerPOPMessageEnum.MASTER_RECEIVED, userData);
                return false;
            }

            POPPurchaseOrderLines splitLine = (POPPurchaseOrderLines) util.doClone(line, POPPurchaseOrderLines.class, userData);
            splitLine.setQuantity(splitQuantity);
            splitLine.setItemsReceived(0);
            splitLine.setDeliveryDate(revisedDate);

            this.insert(splitLine, userData);

            line.setQuantity(line.getQuantity() - splitQuantity);
            line.setRevisedDate(revisedDate);

            this.update(line, userData);
        }

        return true;
    }

    /**
     * Returns all lines belonging to the specified purchase order.
     *
     * @param purchaseOrderId Purchase order id.
     * @param userData User data.
     * @return A list containing all the lines that belong to the specified
     * purchase order.
     */
    @Override
    public List<POPPurchaseOrderLines> getPurchaseOrderLines(String purchaseOrderId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class);
        query.addAnd("purchaseOrderId", purchaseOrderId);

        return (List<POPPurchaseOrderLines>) util.executeGeneralSelectQuery(query, userData);
    }
}
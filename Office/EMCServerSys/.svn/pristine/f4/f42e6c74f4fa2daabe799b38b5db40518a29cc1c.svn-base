/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.serialbatch;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.pop.POPParametersLocal;
import emc.entity.base.BaseUnitsOfMeasure;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.serialbatch.InventoryRemoveSerialBatch;
import emc.entity.inventory.serialbatch.SerialBatchSuper;
import emc.entity.pop.posting.POPPurchasePostLines;
import emc.entity.inventory.serialbatch.InventorySerialBatch;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.pop.journals.POPSupplierReceivedJournalMaster;
import emc.entity.pop.posting.POPPurchasePostMaster;
import emc.enums.base.uom.UOMTypes;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.pop.posting.SBType;
import emc.enums.pop.supplierreceivedjournals.ReceivedJournalType;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
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
public class InventorySerialBatchBean extends EMCEntityBean implements InventorySerialBatchLocal {

    @EJB
    private POPParametersLocal param;
    @EJB
    private InventoryItemMasterLocal itemMasterBean;
    private final String add = InventorySerialBatch.class.getName();
    private final String remove = InventoryRemoveSerialBatch.class.getName();

    public InventorySerialBatchBean() {
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        SerialBatchSuper record = (SerialBatchSuper) theRecord;

        if (!checkSerialBatchAllowed(record, fieldNameToValidate, userData)) {
            return false;
        }

        Boolean ret = isSetOnItem(record.getTransId(), fieldNameToValidate, userData.getUserData(7).toString(), userData);
        if (ret) {
            ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        }
        if (ret) {
            return (theRecord instanceof InventoryRemoveSerialBatch ? validateReturnedSerialBatchFiels(record, fieldNameToValidate, userData) : validateSerialBatchFiels(record, fieldNameToValidate, userData));
        }

        return ret;
    }

    private Object validateSerialBatchFiels(SerialBatchSuper record, String fieldNameToValidate, EMCUserData userData) {

        if (fieldNameToValidate.equals("batch")) {
            if (isBlank(record.getBatch())) {
                record.setBatch(null);
                return record;
            }
        }
        if (fieldNameToValidate.equals("serial")) {
            if (isBlank(record.getSerial())) {
                record.setSerial(null);
                return record;
            }
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
            query.addAnd("serialNo", record.getSerial());
            query.addAnd("batch", isBlank(record.getBatch()) ? null : record.getBatch());

            Long count = (Long)util.executeSingleResultQuery(query.getCountQuery(), userData);
            if (count != null && count != 0) {
                Logger.getLogger("emc").log(Level.WARNING, "The serial number is already used.", userData);
                return false;
            }
            query = new EMCQuery(enumQueryTypes.SELECT, InventorySerialBatch.class.getName());
            query.addAnd("serial", record.getSerial());
            query.addAnd("batch", isBlank(record.getBatch()) ? null : record.getBatch());
            if (util.exists(query, userData)) {
                Logger.getLogger("emc").log(Level.WARNING, "The serial number is already used.", userData);
                return false;
            }
            if (!param.serialMoreThanOne(userData)) {
                record.setQuantity(1);
                return record;
            }
        }
        if (fieldNameToValidate.equals("quantity")) {
            if (!isBlank(record.getSerial())) {
                if (record.getQuantity() > 1 && !param.serialMoreThanOne(userData)) {
                    Logger.getLogger("emc").log(Level.WARNING, "Quantity can not be more than one on a serial number.", userData);
                    return false;
                }
            }
            if (record.getQuantity() == 0) {
                Logger.getLogger("emc").log(Level.WARNING, "Quantity can not be zero.", userData);
                return false;
            }
        }

        if (fieldNameToValidate.equals("widthUOM")) {
            if (record.getWidth() == 0) {
                Logger.getLogger("emc").log(Level.SEVERE, "May not enter a width UOM when width is zero.", userData);
                return false;
            } else {
                //Check that UoM is valid
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseUnitsOfMeasure.class.getName());
                query.addAnd("unit", record.getWidthUOM());
                query.addAnd("type", UOMTypes.LENGTH);

                if (util.executeSingleResultQuery(query, userData) == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "UOM must be a valid unit of measure of the " + UOMTypes.LENGTH + " type.", userData);
                    return false;
                }
            }
        }

        return true;
    }

    private Object validateReturnedSerialBatchFiels(SerialBatchSuper record, String fieldNameToValidate, EMCUserData userData) {
        if (fieldNameToValidate.equals("batch")) {
            if (isBlank(record.getBatch())) {
                record.setBatch(null);
                return record;
            }
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySerialBatch.class.getName());
            query.addTableAnd(POPPurchasePostMaster.class.getName(), "masterId", InventorySerialBatch.class.getName(), "postMasterId");
            query.addAnd("documentNumber", userData.getUserData(9), POPPurchasePostMaster.class.getName());
            query.addAnd("transId", userData.getUserData(5), InventorySerialBatch.class.getName());
            query.addAnd("type", SBType.PURCHASEORDER.toString(), InventorySerialBatch.class.getName());
            query.addAnd("batch", record.getBatch(), InventorySerialBatch.class.getName());
            if (!util.exists(query, userData)) {
                Logger.getLogger("emc").log(Level.WARNING, "The selected batch no. has not been received on the selected received note.", userData);
                return false;
            } else {
                record.setSerial(null);
                return record;
            }
        }
        if (fieldNameToValidate.equals("serial")) {
            if (isBlank(record.getSerial())) {
                record.setSerial(null);
                return record;
            }
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySerialBatch.class.getName());
            query.addTableAnd(POPPurchasePostMaster.class.getName(), "masterId", InventorySerialBatch.class.getName(), "postMasterId");
            query.addAnd("documentNumber", userData.getUserData(9), POPPurchasePostMaster.class.getName());
            query.addAnd("transId", userData.getUserData(5), InventorySerialBatch.class.getName());
            query.addAnd("type", SBType.PURCHASEORDER.toString(), InventorySerialBatch.class.getName());
            query.addAnd("serial", record.getSerial(), InventorySerialBatch.class.getName());
            InventorySerialBatch isb = (InventorySerialBatch) util.executeSingleResultQuery(query, userData);
            if (isb == null) {
                Logger.getLogger("emc").log(Level.WARNING, "The selected serial no. has not been received on the selected received note.", userData);
                return false;
            } else {
                record.setBatch(isb.getBatch());
                record.setQuantity(isb.getQuantity());
            }
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryRemoveSerialBatch.class.getName());
            query.addAnd("serial", record.getSerial());
            query.addAnd("batch", isBlank(record.getBatch()) ? null : record.getBatch());
            Object o = util.executeSingleResultQuery(query, userData);
            if (o != null && !param.serialMoreThanOne(userData)) {
                Logger.getLogger("emc").log(Level.WARNING, "The serial number is already used.", userData);
                return false;
            } else {
                return record;
            }
        }
        if (fieldNameToValidate.equals("quantity")) {
            if (!isBlank(record.getSerial())) {
                if (record.getQuantity() > 1 && !param.serialMoreThanOne(userData)) {
                    Logger.getLogger("emc").log(Level.WARNING, "Quantity can not be more than one on a serial number.", userData);
                    return false;
                }
            }
            if (record.getQuantity() == 0) {
                Logger.getLogger("emc").log(Level.WARNING, "Quantity can not be zero.", userData);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        SerialBatchSuper record = (SerialBatchSuper) vobject;

        if (!checkSerialBatchAllowed(record, null, userData)) {
            return false;
        }
        record.setMasterId(userData.getUserData(6).toString());
        record.setType(userData.getUserData(7).toString());

        if (vobject instanceof InventoryRemoveSerialBatch) {
            InventoryItemDimensionGroup dimGroup = itemMasterBean.getItemDimensionGroupRecord((String) userData.getUserData(14), userData);
            if (dimGroup.getSerialNumberActive() || dimGroup.getBatchNumberActive()) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySerialBatch.class.getName());
                query.addTableAnd(POPPurchasePostMaster.class.getName(), "masterId", InventorySerialBatch.class.getName(), "postMasterId");
                query.addAnd("documentNumber", userData.getUserData(9), POPPurchasePostMaster.class.getName());
                query.addAnd("transId", userData.getUserData(5), InventorySerialBatch.class.getName());
                query.addAnd("type", SBType.PURCHASEORDER.toString(), InventorySerialBatch.class.getName());
                query.addAnd("serial", record.getSerial(), InventorySerialBatch.class.getName());
                query.addAnd("batch", record.getBatch(), InventorySerialBatch.class.getName());
                if (!util.exists(query, userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "No such record exists in the Serial Batch table.", userData);
                    return false;
                }
            }
        } else {
            if (!validateWidth(record, userData)) {
                return false;
            } else {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySerialBatch.class.getName());
                query.addAnd("masterId", record.getMasterId());
                query.addAnd("transId", record.getTransId(), EMCQueryConditions.NOT);
                query.addAnd("batch", record.getBatch());
                if (util.exists(query, userData)) {
                    Logger.getLogger("emc").log(Level.WARNING, "The batch number is already used.", userData);
                    return false;
                }
            }
        }

        if (!checkRegQty(vobject, record.getTransId(), record.getQuantity(), record.getRecordID(), userData.getUserData(7).toString(), userData)) {
            Logger.getLogger("emc").log(Level.WARNING, "The registered quantity cannot be greater than the total quantity", userData);
            return false;
        }


        return super.doUpdateValidation(record, userData);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        SerialBatchSuper record = (SerialBatchSuper) vobject;

        if (record.getQuantity() == 0) {
            Logger.getLogger("emc").log(Level.WARNING, "Quantity can not be zero.", userData);
            return false;
        }

        if (!checkSerialBatchAllowed(record, null, userData)) {
            return false;
        }

        record.setMasterId(userData.getUserData(6).toString());
        record.setType(userData.getUserData(7).toString());

        if (vobject instanceof InventoryRemoveSerialBatch) {
            InventoryItemDimensionGroup dimGroup = itemMasterBean.getItemDimensionGroupRecord((String) userData.getUserData(14), userData);
            if (dimGroup.getSerialNumberActive() || dimGroup.getBatchNumberActive()) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySerialBatch.class.getName());
                query.addTableAnd(POPPurchasePostMaster.class.getName(), "masterId", InventorySerialBatch.class.getName(), "postMasterId");
                query.addAnd("documentNumber", userData.getUserData(9), POPPurchasePostMaster.class.getName());
                query.addAnd("transId", userData.getUserData(5), InventorySerialBatch.class.getName());
                query.addAnd("type", SBType.PURCHASEORDER.toString(), InventorySerialBatch.class.getName());
                query.addAnd("serial", record.getSerial(), InventorySerialBatch.class.getName());
                query.addAnd("batch", record.getBatch(), InventorySerialBatch.class.getName());
                if (!util.exists(query, userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "No such record exists in the Serial Batch table.", userData);
                    return false;
                }
            }
        } else {
            if (!validateWidth(record, userData)) {
                return false;
            } else {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySerialBatch.class.getName());
                query.addAnd("masterId", record.getMasterId());
                query.addAnd("transId", record.getTransId(), EMCQueryConditions.NOT);
                query.addAnd("batch", record.getBatch());
                if (util.exists(query, userData)) {
                    Logger.getLogger("emc").log(Level.WARNING, "The batch number is already used.", userData);
                    return false;
                }
            }
        }

        if (!checkRegQty(vobject, record.getTransId(), record.getQuantity(), record.getRecordID(), userData.getUserData(7).toString(), userData)) {
            Logger.getLogger("emc").log(Level.WARNING, "The registered quantity cannot be greater than the total quantity", userData);
            return false;
        }
        return super.doInsertValidation(record, userData);
    }

    /**
     * This checkes if Serial and/or Batch number is set on the item group of the current item.
     * @param transId
     * @param fieldName
     * @param userData
     * @return true if it is set on the item group.
     */
    private boolean isSetOnItem(String transId, String fieldName, String type, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemDimensionGroupId", InventoryItemDimensionGroup.class.getName(), "dimensionGroup");
        if (type.equals(SBType.PURCHASEORDER.toString())) {
            query.addTableAnd(POPPurchasePostLines.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
            query.addAnd("transactionNumber", transId, POPPurchasePostLines.class.getName());
        } else if (type.equals(SBType.JOURNAL.toString())) {
            query.addTableAnd(InventoryJournalLines.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
            query.addAnd("transId", transId, InventoryJournalLines.class.getName());
        }
        query.setSelectDistinctTable(true);
        Object o = util.executeSingleResultQuery(query, userData);
        InventoryItemDimensionGroup group = (InventoryItemDimensionGroup) o;
        if (fieldName.equals("serial")) {
            if (!group.getSerialNumberActive()) {
                Logger.getLogger("emc").log(Level.WARNING, "Serial number is not active on this items item group.", userData);
                return false;
            }
        } else if (fieldName.equals("batch")) {
            if (!group.getBatchNumberActive()) {
                Logger.getLogger("emc").log(Level.WARNING, "Batch number is not active on this items item group.", userData);
                return false;
            }
        } else if (fieldName.equals("location")) {
            if (!group.getLocationActive()) {
                Logger.getLogger("emc").log(Level.WARNING, "Location is not active on this items item group.", userData);
                return false;
            }
        } else if (fieldName.equals("quantity")) {
            if (!group.getBatchNumberActive() && !group.getSerialNumberActive() && !group.getLocationActive()) {
                Logger.getLogger("emc").log(Level.WARNING, "Neither Serial number nor Batch Number nor Location is not active on this items item group.", userData);
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the registered quantity is not greater than the total quantity reseived;
     * @param transId
     * @param userData
     * @return true if the regQty is less than the totalQty
     */
    private boolean checkRegQty(Object theRecord, String transId, double qty, long recordID, String type, EMCUserData userData) {
        double totalQty;
        double regQty;
        EMCQuery query = null;
        if (type.equals(SBType.PURCHASEORDER.toString())) {
            query = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostLines.class.getName());
            query.addAnd("transactionNumber", transId);
            query.addAnd("postMasterId", userData.getUserData().get(6).toString());
        } else if (type.equals(SBType.JOURNAL.toString())) {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class.getName());
            query.addAnd("transId", transId);
            query.addAnd("journalNumber", userData.getUserData().get(6).toString());
        }
        query.addField("quantity");
        query.addAnd("companyId", userData.getCompanyId());
        Object o = util.executeSingleResultQuery(query, userData);
        if (o != null) {
            totalQty = (Double) o;
        } else {
            totalQty = 0;
        }
        totalQty = Math.abs(totalQty);
        query = new EMCQuery(enumQueryTypes.SELECT, (theRecord instanceof InventorySerialBatch) ? add : remove);
        query.addFieldAggregateFunction("quantity", (theRecord instanceof InventorySerialBatch) ? add : remove, "SUM");
        query.addAnd("transId", transId);
        query.addAnd("masterId", userData.getUserData().get(6).toString());
        query.addAnd("type", type);
        query.addAnd("companyId", userData.getCompanyId());
        if (recordID != 0) {
            query.addAnd("recordID", recordID, EMCQueryConditions.NOT);
        }
        o = util.executeSingleResultQuery(query, userData);
        if (o != null) {
            regQty = (Double) o + qty;
        } else {
            regQty = qty;
        }
        regQty = Math.abs(regQty);
        if (util.compareDouble(regQty, totalQty) == 1) {
            return false;
        }
        return true;
    }

    public boolean checkRegQuantity(String masterId, String transId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostLines.class.getName());
        query.addAnd("postMasterId", masterId);
        query.addAnd("transactionNumber", transId);
        query.addField("quantity");
        double total = (Double) util.executeSingleResultQuery(query, userData);
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryRemoveSerialBatch.class.getName());
        query.addFieldAggregateFunction("quantity", InventoryRemoveSerialBatch.class.getName(), "SUM");
        query.addAnd("transId", transId);
        query.addAnd("masterId", masterId);
        query.addAnd("type", SBType.PURCHASEORDER);
        Object o = util.executeSingleResultQuery(query, userData);
        if (o == null && total != 0) {
            return false;
        }
        double regTotal = (Double) o;
        if (util.compareDouble(total, regTotal) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkAllRemovedQty(String masterId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostLines.class.getName());
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", POPPurchasePostLines.class.getName(), "itemId");
        query.addTableAnd(InventoryItemDimensionGroup.class.getName(), "dimensionGroup", InventoryItemMaster.class.getName(), "itemDimensionGroupId");
        query.addAnd("postMasterId", masterId, POPPurchasePostLines.class.getName());
        query.openConditionBracket(EMCQueryBracketConditions.AND);
        query.addOr("batchNumberActive", true, InventoryItemDimensionGroup.class.getName());
        query.addOr("serialNumberActive", true, InventoryItemDimensionGroup.class.getName());
        query.addOr("locationActive", true, InventoryItemDimensionGroup.class.getName());
        query.closeConditionBracket();
        List linesList = util.executeGeneralSelectQuery(query, userData);
        POPPurchasePostLines line;
        for (Object o : linesList) {
            line = (POPPurchasePostLines) o;
            if (util.compareDouble(line.getQuantity(), 0) > 0) {
                if (!checkRegQuantity(line.getPostMasterId(), line.getTransactionNumber(), userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The the registration on item " + line.getItemPrimaryReference() + " is incorrect.", userData);
                    return false;
                }
            }
        }
        return true;
    }

    public void deleteSB(InventoryRemoveSerialBatch record, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySerialBatch.class.getName());
        query.addAnd("serial", record.getSerial());
        query.addAnd("batch", record.getBatch());
        query.addAnd("type", SBType.PURCHASEORDER.toString());
        InventorySerialBatch isb = (InventorySerialBatch) util.executeSingleResultQuery(query, userData);
        if (isb == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "More than one record found for the given Serial Batch record.", userData);
        } else {
            super.delete(record, userData);
            if (util.compareDouble(record.getQuantity(), isb.getQuantity()) == 0) {
                super.delete(isb, userData);
            } else {
                if (util.compareDouble(record.getQuantity(), isb.getQuantity()) == -1) {
                    isb.setQuantity(isb.getQuantity() - record.getQuantity());
                    super.doUpdate(isb, userData);
                } else {
                    throw new EMCEntityBeanException("The returned quantity can't be more than the registered quantity.");
                }
            }
        }
    }

    public void deleteRemovedSB(String postSetupId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRemoveSerialBatch.class.getName());
        query.addTableAnd(POPPurchasePostMaster.class.getName(), "masterId", InventoryRemoveSerialBatch.class.getName(), "postMasterId");
        query.addAnd("postSetupId", postSetupId, POPPurchasePostMaster.class.getName());
        query.addAnd("type", SBType.PURCHASEORDER.toString(), InventoryRemoveSerialBatch.class.getName());
        query.addAnd("createdBy", userData.getUserName(), InventoryRemoveSerialBatch.class.getName());
        InventoryRemoveSerialBatch sb;
        for (Object o : util.executeGeneralSelectQuery(query, userData)) {
            sb = (InventoryRemoveSerialBatch) o;
            try {
                super.delete(sb, userData);
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to delete removed serial batch data.", userData);
                return;
            }
        }
    }

    /** Checks that the given line may be assigned serial and batch numbers.  When passing a null as the fieldToValidate parameter both serial and batch will be checked.  */
    private boolean checkSerialBatchAllowed(SerialBatchSuper serialBatch, String fieldToValidate, EMCUserData userData) {
        POPPurchasePostLines postLine = getPostLine((String) userData.getUserData(6), (String) userData.getUserData(5), userData);

        if (postLine == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "May not register items on a Post line that has not been saved.", userData);
            return false;
        } else if (postLine.getQuantity() == 0) {
            Logger.getLogger("emc").log(Level.SEVERE, "May not register items on a Post line with a zero quantity.", userData);
            return false;
        } else {
            InventoryItemDimensionGroup dimGroup = itemMasterBean.getItemDimensionGroupRecord(postLine.getItemId(), userData);

            if (dimGroup == null) {
                Logger.getLogger("emc").log(Level.SEVERE, "No dimension group found.  May not register items.", userData);
                return false;
            }

            //Used to ensure that all errors are displayed, instead of returning false at the first error.
            boolean ret = true;

            //Check whether batch should be checked
            if ((fieldToValidate == null || fieldToValidate.equals("batch"))) {
                if (isBlank(serialBatch.getBatch()) && dimGroup.getBatchNumberActive()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Batch number is active.  Please specify a batch number.", userData);
                    ret = false;
                } else if (!isBlank(serialBatch.getBatch()) && !dimGroup.getBatchNumberActive()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Batch number is not active.  You may not enter a batch number.", userData);
                    ret = false;
                }
            }

            //Check whether serial should be checked
            if ((fieldToValidate == null || fieldToValidate.equals("serial"))) {
                if (isBlank(serialBatch.getSerial()) && dimGroup.getSerialNumberActive()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Serial number is active.  Please specify a serial number.", userData);
                    ret = false;
                } else if (!isBlank(serialBatch.getSerial()) && !dimGroup.getSerialNumberActive()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Serial number is not active.  You may not enter a serial number.", userData);
                    ret = false;
                }
            }

            if (serialBatch instanceof InventoryRemoveSerialBatch) {
                //Check whether serial should be checked
                if ((fieldToValidate == null || fieldToValidate.equals("location"))) {
                    if (isBlank(((InventoryRemoveSerialBatch) serialBatch).getLocation()) && dimGroup.getLocationActive()) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Location is active.  Please specify a location.", userData);
                        ret = false;
                    } else if (!isBlank(((InventoryRemoveSerialBatch) serialBatch).getLocation()) && !dimGroup.getLocationActive()) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Location is not active.  You may not enter a location.", userData);
                        ret = false;
                    }
                }
            }

            return ret;
        }
    }

    /** Returns the post line that the current serial/batch record is on. */
    private POPPurchasePostLines getPostLine(String masterId, String transId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostLines.class.getName());
        query.addAnd("transactionNumber", transId);
        query.addAnd("postMasterId", masterId);

        return (POPPurchasePostLines) util.executeSingleResultQuery(query, userData);
    }

    /**
     * This method checks whether the width specified on an InventorySerialBatch record is valid.
     * @param serialBatch Record to check.
     * @param userData Userdata.
     * @return A boolean indicating whether the width specified on the given record is valid.
     */
    private boolean validateWidth(SerialBatchSuper serialBatch, EMCUserData userData) {
        if (serialBatch.getWidth() != 0 && isBlank(serialBatch.getWidthUOM())) {
            Logger.getLogger("emc").log(Level.SEVERE, "Please specify a width UOM", userData);
            return false;
        } else if (!isBlank(serialBatch.getWidthUOM()) && serialBatch.getWidth() == 0) {
            Logger.getLogger("emc").log(Level.SEVERE, "You may not specify a width UOM without a width", userData);
            return false;
        } else {
            return true;
        }
    }

   
}   

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.register.normanregister;

import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.bus.inventory.dimensions.additionaldimensions.InventoryAdditionalDimensionsLocal;
import emc.bus.inventory.register.superregister.InventoryRegisterSuperBean;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.dimensions.additionaldimensions.InventoryAdditionalDimensions;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.register.InventoryRegister;
import emc.entity.inventory.register.InventoryRegisterSuper;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.register.RegisterFromTypeEnum;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.enums.inventory.transactions.InventoryTransactionStatus;
import emc.enums.inventory.transactions.InventoryTransactionTypes;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerInventoryMessageEnum;
import emc.tables.EMCTable;
import java.util.ArrayList;
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
public class InventoryRegisterBean extends InventoryRegisterSuperBean implements InventoryRegisterLocal {

    @EJB
    private InventoryDimensionTableLocal dimensionBean;
    @EJB
    private InventoryAdditionalDimensionsLocal additionalDimensionBean;

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (ret) {
            InventoryRegister record = (InventoryRegister) theRecord;
            if (fieldNameToValidate.equals("quantity")) {
                Object qtyRet = validateQuantity(record, userData);
                if (qtyRet instanceof InventoryRegisterSuper) {
                    return (InventoryRegister) qtyRet;
                } else {
                    return false;
                }
            }
            if (fieldNameToValidate.equals("serial") && !isBlank(record.getSerial())) {
                /* EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRegister.class.getName());
                query.addAnd("serial", record.getSerial());
                query.addAnd("companyId", userData.getCompanyId());
                if (util.exists(query, userData)) {
                Logger.getLogger("emc").log(Level.SEVERE, "The entered serial number already exists.", userData);
                return false;
                }*/
            }
            if (fieldNameToValidate.equals("location")) {
                if (record.getLocation().equals("QC")) {
                    logMessage(Level.SEVERE, "Stock may not be moved/received into or out of QC.", userData);
                    return false;
                }
                return validateLocationInWarehouse(record, userData);
            }
        }
        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);
        if (ret) {
            InventoryRegister record = (InventoryRegister) vobject;

            ret = ret && validateWidth(record, userData);

            ret = ret && checkRegisterEditable(record, userData);

            //N & L Mod
            //Pam - Putbacks.  If the batch number that is being put in already
            //exists, but with different dimensions, disallow.  If the batch
            //does not already exist, log a warning.
            if (RegisterFromTypeEnum.fromString(record.getType()) == RegisterFromTypeEnum.JRN) {
                ret = ret && checkBatch(record, userData);
            } else if (RegisterFromTypeEnum.fromString(record.getType()) == RegisterFromTypeEnum.PROD_ASSY) {
                ret = ret && checkBatchUnique(record, userData);
            }
        }
        return ret;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        Object ret = super.update(uobject, userData);
        updateAdditionalDimensions((InventoryRegister) uobject, userData);
        return ret;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);
        if (ret) {
            InventoryRegister record = (InventoryRegister) vobject;

            ret = ret && validateWidth(record, userData);

            ret = ret && checkRegisterEditable(record, userData);

            //N & L Mod
            //Pam - Putbacks.  If the batch number that is being put in already
            //exists, but with different dimensions, disallow.  If the batch
            //does not already exist, log a warning.
            if (RegisterFromTypeEnum.fromString(record.getType()) == RegisterFromTypeEnum.JRN) {
                ret = ret && checkBatch(record, userData);
            } else if (RegisterFromTypeEnum.fromString(record.getType()) == RegisterFromTypeEnum.PROD_ASSY) {
                ret = ret && checkBatchUnique(record, userData);
            }
        }
        return ret;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        Object ret = super.insert(iobject, userData);
        updateAdditionalDimensions((InventoryRegister) iobject, userData);
        return ret;
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doDeleteValidation(vobject, userData);
        if (ret) {
            InventoryRegister record = (InventoryRegister) vobject;

            ret = ret && checkRegisterEditable(record, userData);
        }
        return ret;
    }

    private boolean validateLocationInWarehouse(InventoryRegisterSuper record, EMCUserData userData) {
        if (!isBlank(record.getLocation())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class.getName());
            query.addAnd("locationId", record.getLocation());
            query.addAnd("warehouseId", record.getWarehouse());
            query.addAnd("companyId", userData.getCompanyId());
            if (!util.exists(query, userData)) {
                Logger.getLogger("emc").log(Level.SEVERE, "The selected location is not found in the selected warehouse.", userData);
                return false;
            }
        }
        return true;
    }

    @Override
    protected void updateRegisteredQty(InventoryRegisterSuper record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.UPDATE, InventoryRegister.class.getName());
        query.addSet("registeredQty", record.getRegisteredQty());
        query.addAnd("masterId", record.getMasterId());
        query.addAnd("transId", record.getTransId());
        util.executeUpdate(query, userData);
    }

    @Override
    protected double getRegisteredQty(InventoryRegisterSuper record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRegister.class.getName());
        query.addAnd("masterId", record.getMasterId());
        query.addAnd("transId", record.getTransId());
        query.addAnd("recordID", record.getRecordID(), EMCQueryConditions.NOT);
        query.addFieldAggregateFunction("quantity", "SUM");
        Object res = util.executeSingleResultQuery(query, userData);
        return res == null ? 0.0 : (Double) res;
    }

    /**
     * Checks wat dimensions is active on the given item.
     * @param itemId
     * @param userData
     * @return A list the active dimension column names in it or a blank list.
     */
    @Override
    public List isRegistrationRequired(String itemId, EMCUserData userData) {
        InventoryItemDimensionGroup dimGroup = getDimGroupRecord(itemId, userData);
        List ret = new ArrayList();
        boolean addWidth = false;
        if (dimGroup.getWarehouseActive()) {
            ret.add("warehouse");
        }
        if (dimGroup.getBatchNumberActive()) {
            ret.add("batch");
            addWidth = true;
        }
        if (dimGroup.getSerialNumberActive()) {
            ret.add("serial");
            addWidth = true;
        }
        if (addWidth) {
            ret.add("width");
            ret.add("uom");
        }
        if (dimGroup.getLocationActive()) {
            ret.add("location");
        }
        if (dimGroup.getPalletIdActive()) {
            ret.add("pallet");
        }
        return ret;
    }

    /**
     * This method checks whether the width specified on an InventorySerialBatch record is valid.
     * @param serialBatch Record to check.
     * @param userData Userdata.
     * @return A boolean indicating whether the width specified on the given record is valid.
     */
    private boolean validateWidth(InventoryRegister serialBatch, EMCUserData userData) {
        if (serialBatch.getWidth() != 0 && isBlank(serialBatch.getUom())) {
            Logger.getLogger("emc").log(Level.SEVERE, "Please specify a width UOM", userData);
            return false;
        } else {
            if (!isBlank(serialBatch.getUom()) && serialBatch.getWidth() == 0) {
                Logger.getLogger("emc").log(Level.SEVERE, "You may not specify a width UOM without a width", userData);
                return false;
            } else {
                return true;
            }
        }
    }

    private void updateAdditionalDimensions(InventoryRegister record, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class.getName());
        query.addAnd("journalNumber", record.getMasterId());
        query.addAnd("transId", record.getTransId());
        InventoryJournalLines journalLine = (InventoryJournalLines) util.executeSingleResultQuery(query, userData);
        if (journalLine == null) {
            return;
        } else {
            long dimId = dimensionBean.getWidthDimRecordID(journalLine.getDimension1(), journalLine.getDimension2(), journalLine.getDimension3(), record.getBatch(), record.getSerial(), userData);
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryAdditionalDimensions.class.getName());
            query.addAnd("itemId", journalLine.getItemId());
            query.addAnd("dimensionId", dimId);
            InventoryAdditionalDimensions additionalDimensions = (InventoryAdditionalDimensions) util.executeSingleResultQuery(query, userData);
            if (additionalDimensions == null) {
                additionalDimensions = new InventoryAdditionalDimensions();
                additionalDimensions.setItemId(journalLine.getItemId());
                additionalDimensions.setDimensionId(dimId);
                additionalDimensions.setWidth(record.getWidth());
                additionalDimensions.setWidthUOM(record.getUom());
                additionalDimensionBean.insert(additionalDimensions, userData);
            } else {
                additionalDimensions.setWidth(record.getWidth());
                additionalDimensions.setWidthUOM(record.getUom());
                additionalDimensionBean.update(additionalDimensions, userData);
            }
        }
    }

    /**
     * validates the quantity entered
     * @param record
     * @param userData
     * @return
     */
    protected Object validateQuantity(InventoryRegister record, EMCUserData userData) {
        boolean ret = (Boolean) super.validateQuantity(record, userData);
        if (ret) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRegister.class.getName());
            query.addAnd("masterId", record.getMasterId());
            query.addAnd("transId", record.getTransId());
            query.addAnd("recordID", record.getRecordID(), EMCQueryConditions.NOT);
            query.addFieldAggregateFunction("quantity", "SUM");
            Double queryQty = (Double) util.executeSingleResultQuery(query, userData);
            double regQty = (queryQty == null ? 0d : queryQty) + record.getQuantity();
            double total = record.getTotalQty();
            if (record.getTotalQty() > 0 ? regQty > total : regQty < total) {
                Logger.getLogger("emc").log(Level.SEVERE, "The register quantity will be exceeded.", userData);
                return false;
            } else {
                record.setRegisteredQty(regQty);
                return record;
            }
        }
        return ret;
    }

    /**
     * Checks that the batch on the specified register record has not been used with other
     * dimensions as those specified on the journal line.  (Only dimensions 1 - 3 are checked)
     * This method is N & L specific.
     *
     * @param register Register line to check.
     * @param journalLine Journal line that the specified register line belongs to.
     * @param userData User data.
     * @return A boolean indicating whether the register record may be saved.
     */
    private boolean checkBatch(InventoryRegister register, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class);
        query.addAnd("journalNumber", register.getMasterId());
        query.addAnd("transId", register.getTransId());

        InventoryJournalLines journalLine = (InventoryJournalLines) util.executeSingleResultQuery(query, userData);

        //Check if Batch is active on item
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class);
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemDimensionGroupId", InventoryItemDimensionGroup.class.getName(), "dimensionGroup");
        query.addAnd("itemId", journalLine.getItemId(), InventoryItemMaster.class.getName());
        query.addAnd("batchNumberActive", true, InventoryItemDimensionGroup.class.getName());
        //if active
        if (util.exists(query, userData)) {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimensionTable.class);
            query.addAnd("batchId", register.getBatch());

            //Ignore records without relevant dimension
            if (isBlank(journalLine.getDimension1())) {
                query.addAnd("dimension1Id", null, EMCQueryConditions.IS_BLANK);
            } else {
                query.addAnd("dimension1Id", null, EMCQueryConditions.IS_NOT_BLANK);
            }

            if (isBlank(journalLine.getDimension2())) {
                query.addAnd("dimension2Id", null, EMCQueryConditions.IS_BLANK);
            } else {
                query.addAnd("dimension2Id", null, EMCQueryConditions.IS_NOT_BLANK);
            }

            if (isBlank(journalLine.getDimension3())) {
                query.addAnd("dimension3Id", null, EMCQueryConditions.IS_BLANK);
            } else {
                query.addAnd("dimension3Id", null, EMCQueryConditions.IS_NOT_BLANK);
            }

            List<InventoryDimensionTable> dimensionRecords = (List<InventoryDimensionTable>) util.executeGeneralSelectQuery(query, userData);

            if (dimensionRecords == null || dimensionRecords.isEmpty()) {
                logMessage(Level.WARNING, ServerInventoryMessageEnum.BATCH_NOT_USED, userData);
            } else {
                for (InventoryDimensionTable dimensions : dimensionRecords) {
                    //Stock may have come from a different warehouse than the warehouse on the journal/register - that is okay.
                    if (!util.checkObjectsEqual(dimensions.getDimension1Id(), journalLine.getDimension1()) ||
                            !util.checkObjectsEqual(dimensions.getDimension2Id(), journalLine.getDimension2()) ||
                            !util.checkObjectsEqual(dimensions.getDimension3Id(), journalLine.getDimension3())) {

                        //Check if this stock has actually been issued out
                        query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
                        query.addAnd("itemDimId", dimensions.getRecordID());
                        query.addAnd("itemId", journalLine.getItemId());
                        //In this case, we are only concerned about stock that we've assembled.
                        query.addAnd("direction", InventoryTransactionDirection.IN.toString());
                        query.addAnd("status", InventoryTransactionStatus.RECEIVED.toString());
                        query.addAnd("type", InventoryTransactionTypes.WO.toString());

                        if (util.exists(query, userData)) {
                            logMessage(Level.SEVERE, ServerInventoryMessageEnum.BATCH_USED_WITH_DIFFERENT_DIMS, userData, dimensions.getDimension1Id(), dimensions.getDimension2Id(), dimensions.getDimension3Id());
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean checkRegisterEditable(InventoryRegister register, EMCUserData userData) {
       
        return true;
    }

    private boolean checkBatchUnique(InventoryRegister record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRegister.class);
        query.addAnd("masterId", record.getMasterId());
        query.addAnd("transId", record.getTransId());
        query.addAnd("batch", record.getBatch());
        query.addAnd("serial", record.getSerial());
        query.addAnd("recordID", record.getRecordID(), EMCQueryConditions.NOT);
        if (util.exists(query, userData)) {
            Logger.getLogger("emc").log(Level.SEVERE, "The entered batch is already registered against the selected Assembly line.", userData);
            return false;
        } else return true;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.register.superregister;

import emc.bus.pop.POPParametersLocal;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.entity.inventory.register.InventoryRegisterSuper;
import emc.entity.inventory.register.InventoryStocktakeRegister;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.base.journals.JournalStatus;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.register.RegisterFromTypeEnum;
import emc.enums.sop.salesorders.SalesOrderStatus;
import emc.framework.EMCEntityBean;
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

/**
 *
 * @author wikus
 */
public abstract class InventoryRegisterSuperBean extends EMCEntityBean implements InventoryRegisterSuperLocal {

    @EJB
    protected POPParametersLocal param;

    /**
     * Checks what dimensions is active on the given item.
     *
     * @param itemId
     * @param userData
     * @return A list the active dimension column names in it or a blank list.
     */
    @Override
    public List isRegistrationRequired(String itemId, EMCUserData userData) {
        InventoryItemDimensionGroup dimGroup = getDimGroupRecord(itemId, userData);
        List ret = new ArrayList();
        if (dimGroup.getWarehouseActive()) {
            ret.add("warehouse");
        }
        if (dimGroup.getBatchNumberActive()) {
            ret.add("batch");
        }
        if (dimGroup.getSerialNumberActive()) {
            ret.add("serial");
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
     * Get the InventoryItemDimensionGroup records for the given item.
     *
     * @param itemId
     * @param userData
     * @return
     */
    protected InventoryItemDimensionGroup getDimGroupRecord(String itemId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemDimensionGroupId", InventoryItemDimensionGroup.class.getName(), "dimensionGroup");
        query.addAnd("itemId", itemId, InventoryItemMaster.class.getName());
        InventoryItemDimensionGroup ret = (InventoryItemDimensionGroup) util.executeSingleResultQuery(query, userData);
        return ret;
    }

    /**
     * validates the quantity entered
     *
     * @param record
     * @param userData
     * @return
     */
    protected Object validateQuantity(InventoryRegisterSuper record, EMCUserData userData) {
        if (record.getQuantity() == 0) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.ZERO_REG_QTY, userData);
            return false;
        } else if (!isBlank(record.getSerial()) && !param.serialMoreThanOne(userData) && (record.getQuantity() != 1 || record.getQuantity() != -1)) {
            if (!RegisterFromTypeEnum.PROD_ASSY.toString().equals(record.getType())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Quantity on serial number registration can't be greater than one.", userData);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);
        if (ret) {
            ret = validateSave((InventoryRegisterSuper) vobject, userData);
        }
        return ret;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);
        if (ret) {
            ret = validateSave((InventoryRegisterSuper) vobject, userData);
        }
        return ret;
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doDeleteValidation(vobject, userData);
        if (ret) {
            InventoryRegisterSuper record = (InventoryRegisterSuper) vobject;
            ret = allowUpdates(record.getMasterId(), RegisterFromTypeEnum.fromString(record.getType()), userData);
        }
        return ret;

    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        InventoryRegisterSuper record = (InventoryRegisterSuper) theRecord;
        if (allowUpdates(record.getMasterId(), RegisterFromTypeEnum.fromString(record.getType()), userData)) {
            return super.validateField(fieldNameToValidate, theRecord, userData);
        }
        return false;
    }

    /**
     * Used for update and insert validation
     *
     * @param record
     * @param userData
     * @return
     */
    protected boolean validateSave(InventoryRegisterSuper record, EMCUserData userData) {
        if (!allowUpdates(record.getMasterId(), RegisterFromTypeEnum.fromString(record.getType()), userData)) {
            return false;
        }
        if (!(record instanceof InventoryStocktakeRegister)) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
            query.addAnd("transId", record.getTransId());
            query.addField("itemId");
            String itemId = util.executeSingleResultQuery(query, userData).toString();
            if (isBlank(itemId)) {
                Logger.getLogger("emc").log(Level.SEVERE, "No Transactions found.", userData);
                    return false;
            } else {
                List fields = isRegistrationRequired(itemId, userData);
                if (isBlank(record.getSerial()) && fields.contains("serial")) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Serial Number is mandatory, please enter a value.", userData);
                    return false;
                }
                if (isBlank(record.getBatch()) && fields.contains("batch")) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Batch Number is mandatory, please enter a value.", userData);
                    return false;
                }
                if (isBlank(record.getLocation()) && fields.contains("location")) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Location is mandatory, please enter a value.", userData);
                    return false;
                }
                if (isBlank(record.getWarehouse()) && fields.contains("warehouse")) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Warehouse is mandatory, please enter a value.", userData);
                    return false;
                }
                if (isBlank(record.getPallet()) && fields.contains("pallet")) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Pallet is mandatory, please enter a value.", userData);
                    return false;
                }
            }
        }
        updateRegisteredQty(record, userData);
        return true;
    }

    private boolean allowUpdates(String masterRecordId, RegisterFromTypeEnum type, EMCUserData userData) {
        EMCQuery query;
        String status;
        if (type != null) {
            switch (type) {
                case JRN:
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class.getName());
                    query.addAnd("journalNumber", masterRecordId);
                    query.addField("journalStatus");
                    status = (String) util.executeSingleResultQuery(query, userData);
                    if (status == null) {
                        status = "";
                    }
                    if (JournalStatus.POSTED.toString().equals(status)) {
                        Logger.getLogger("emc").log(Level.SEVERE, "The inventory journal is in the " + status + " status. No Updates are allowed.", userData);
                        return false;
                    }
                    break;
                case RETURN:
                    query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteMaster.class.getName());
                    query.addAnd("invCNNumber", masterRecordId);
                    query.addField("status");
                    status = (String) util.executeSingleResultQuery(query, userData);
                    if (status == null) {
                        status = "";
                    }
                    if (DebtorsInvoiceStatus.POSTED.toString().equals(status) || DebtorsInvoiceStatus.HELD.toString().equals(status) || DebtorsInvoiceStatus.CANCELED.toString().equals(status) || DebtorsInvoiceStatus.DISTRIBUTION.toString().equals(status)) {
                        Logger.getLogger("emc").log(Level.SEVERE, "The credit note is in the " + status + " status. No updates are allowed.", userData);
                        return false;
                    }
                    break;
                case SO:
                    query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderMaster.class.getName());
                    query.addAnd("salesOrderNo", masterRecordId);
                    query.addField("salsesOrderStatus");
                    status = (String) util.executeSingleResultQuery(query, userData);
                    if (status == null) {
                        status = "";
                    }
                    if (SalesOrderStatus.INVOICED.toString().equals(status) || SalesOrderStatus.CANCELLED.toString().equals(status)) {
                        Logger.getLogger("emc").log(Level.SEVERE, "The sales order is in the " + status + " status. No Updates are allowed.", userData);
                        return false;
                    }
                    break;
              
            }
        }
        return true;
    }

    protected abstract void updateRegisteredQty(InventoryRegisterSuper record, EMCUserData userData);
    //EMCQuery query = new EMCQuery(enumQueryTypes.UPDATE, InventoryRegisterSuper.class.getName());
    //query.addSet("registeredQty", record.getRegisteredQty());
    //query.addAnd("masterId", record.getMasterId());
    //query.addAnd("transId", record.getTransId());
    //util.executeUpdate(query, userData);

    protected abstract double getRegisteredQty(InventoryRegisterSuper record, EMCUserData userData);
    //EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRegisterSuper.class.getName());
    //query.addAnd("masterId", record.getMasterId());
    //query.addAnd("transId", record.getTransId());
    //query.addAnd("recordID", record.getRecordID(), EMCQueryConditions.NOT);
    //query.addFieldAggregateFunction("quantity", "SUM");
    //return (Double)util.executeSingleResultQuery(query, userData);
}

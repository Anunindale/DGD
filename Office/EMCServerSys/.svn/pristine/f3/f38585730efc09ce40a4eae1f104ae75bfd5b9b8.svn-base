/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.register.removeregister;

import emc.bus.inventory.journals.InventoryJournalLinesLocal;
import emc.bus.inventory.journals.movement.MovementJournalPostCases;
import emc.bus.inventory.register.superregister.InventoryRegisterSuperBean;
import emc.bus.inventory.transactions.EMCStockException;
import emc.bus.inventory.transactions.ProcessStockTransactionLocal;
import emc.bus.inventory.transactions.TransactionHelper;
import emc.bus.inventory.transactions.TransactionType;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.register.InventoryRegisterSuper;
import emc.entity.inventory.register.InventoryRemoveRegister;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.journals.InventoryJournalTypes;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerInventoryMessageEnum;
import emc.tables.EMCTable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryRemoveRegisterBean extends InventoryRegisterSuperBean implements InventoryRemoveRegisterLocal {

    @EJB
    private InventoryJournalLinesLocal journalLinesBean;
    @EJB
    private ProcessStockTransactionLocal post;

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (ret) {
            InventoryRemoveRegister record = (InventoryRemoveRegister) theRecord;
            EMCQuery query;
            if (fieldNameToValidate.equals("quantity")) {
                Object qtyRet = validateQuantity(record, userData);
                if (qtyRet instanceof InventoryRegisterSuper) {
                    return (InventoryRemoveRegister) qtyRet;
                } else {
                    return false;
                }
            }
            if (fieldNameToValidate.equals("batch")) {
                if (!isBlank(record.getBatch())) {
                    query = buildJournalSummaryQuery(record, userData);
                    query.addAnd("batch", record.getBatch());
                    if (!util.exists(query, userData)) {
                        Logger.getLogger("emc").log(Level.SEVERE, "The given batch number was not found for the given item.", userData);
                        return false;
                    }
                }
            }
            if (fieldNameToValidate.equals("serial")) {
                if (!isBlank(record.getSerial())) {
                    query = buildJournalSummaryQuery(record, userData);
                    if (!isBlank(record.getBatch())) {
                        query.addAnd("batch", record.getBatch());
                    }
                    query.addAnd("serialNo", record.getSerial());
                    if (!util.exists(query, userData)) {
                        Logger.getLogger("emc").log(Level.SEVERE, "The given serial number was not found for the given item.", userData);
                        return false;
                    }
                }
            }
            if (fieldNameToValidate.equals("location")) {
                if (!isBlank(record.getLocation())) {
                    if (record.getLocation().equals("QC")) {
                        logMessage(Level.SEVERE, "Stock may not be moved/received into or out of QC.", userData);
                        return false;
                    }
                    query = buildJournalSummaryQuery(record, userData);
                    if (!isBlank(record.getWarehouse())) {
                        query.addAnd("warehouse", record.getWarehouse());
                    }
                    query.addAnd("location", record.getLocation());
                    if (!util.exists(query, userData)) {
                        Logger.getLogger("emc").log(Level.SEVERE, "The given location was not found for the given item.", userData);
                        return false;
                    }
                }
            }
            if (fieldNameToValidate.equals("warehouse")) {
                if (!isBlank(record.getWarehouse())) {
                    query = buildJournalSummaryQuery(record, userData);
                    query.addAnd("warehouse", record.getWarehouse());
                    if (!util.exists(query, userData)) {
                        Logger.getLogger("emc").log(Level.SEVERE, "The given warehouse was not found for the given item.", userData);
                        return false;
                    }
                }
            }
            if (fieldNameToValidate.equals("pallet")) {
                if (!isBlank(record.getPallet())) {
                    query = buildJournalSummaryQuery(record, userData);
                    query.addAnd("pallet", record.getPallet());
                    if (!util.exists(query, userData)) {
                        Logger.getLogger("emc").log(Level.SEVERE, "The given pallet was not found for the given item.", userData);
                        return false;
                    }
                }
            }
        }
        return ret;
    }

    private EMCQuery buildJournalSummaryQuery(InventoryRemoveRegister record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
        query.addTableAnd(InventoryTransactions.class.getName(), "itemId", InventorySummary.class.getName(), "itemId");
        //       query.addTableAnd(InventoryDimensionTable.class.getName(), "itemDimId", InventoryTransactions.class.getName(), "recordID");
        //       query.addAnd("dimension1Id", InventoryDimensionTable.class.getName(), EMCQueryConditions.EQUALS, "dimension1", InventorySummary.class.getName());
        //       query.addAnd("dimension2Id", InventoryDimensionTable.class.getName(), EMCQueryConditions.EQUALS, "dimension2", InventorySummary.class.getName());
        //       query.addAnd("dimension3Id", InventoryDimensionTable.class.getName(), EMCQueryConditions.EQUALS, "dimension3", InventorySummary.class.getName());
        query.addAnd("transId", record.getTransId(), InventoryTransactions.class.getName());
        return query;
    }

    @Override
    protected void updateRegisteredQty(InventoryRegisterSuper record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.UPDATE, InventoryRemoveRegister.class.getName());
        query.addSet("registeredQty", record.getRegisteredQty());
        query.addAnd("masterId", record.getMasterId());
        query.addAnd("transId", record.getTransId());
        util.executeUpdate(query, userData);
    }

    @Override
    protected double getRegisteredQty(InventoryRegisterSuper record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRemoveRegister.class.getName());
        query.addAnd("masterId", record.getMasterId());
        query.addAnd("transId", record.getTransId());
        query.addAnd("recordID", record.getRecordID(), EMCQueryConditions.NOT);
        query.addFieldAggregateFunction("quantity", "SUM");
        Object res = util.executeSingleResultQuery(query, userData);
        return res == null ? 0.0 : (Double) res;
    }

    /**
     * validates the quantity entered
     * @param record
     * @param userData
     * @return
     */
    protected Object validateQuantity(InventoryRemoveRegister record, EMCUserData userData) {
        boolean ret = (Boolean) super.validateQuantity(record, userData);
        if (ret) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRemoveRegister.class.getName());
            query.addAnd("masterId", record.getMasterId());
            query.addAnd("transId", record.getTransId());
            query.addAnd("recordID", record.getRecordID(), EMCQueryConditions.NOT);
            query.addFieldAggregateFunction("quantity", "SUM");
            Double queryQty = (Double) util.executeSingleResultQuery(query, userData);
            double regQty = (queryQty == null ? 0d : queryQty) + record.getQuantity();
            double total = record.getTotalQty();
            if (util.compareDouble(record.getTotalQty(), 0) > 0 ? util.compareDouble(regQty, total) > 0 : util.compareDouble(regQty, total) < 0) {
                Logger.getLogger("emc").log(Level.SEVERE, "The register quantity will be exceeded.", userData);
                return false;
            } else {
                record.setRegisteredQty(regQty);
                return record;
            }
        }
        return ret;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryRemoveRegister register = (InventoryRemoveRegister) iobject;

        //Fetch journal line
        InventoryJournalLines journalLine = journalLinesBean.findJournalLine(register.getMasterId(), register.getTransId(), userData);

        if (InventoryJournalTypes.MOVEMENT.equals(journalLinesBean.getJournalType(journalLine, userData)) && util.compareDouble(journalLine.getQuantity(), 0) < 0) {
            try {
                TransactionHelper helper = new TransactionHelper(TransactionType.IVENT_POST_JRLINE);
                helper.setRelatedEntity(register);
                helper.setPostCase(MovementJournalPostCases.REGISTER_CAPTURED);
                
                post.post(journalLine, helper, userData);
            } catch (EMCStockException ex) {
                throw new EMCEntityBeanException("Failed to reserve stock.");
            }
        }

        return super.insert(iobject, userData);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret =  super.doInsertValidation(vobject, userData);

        if (ret) {
            InventoryRemoveRegister removeRegister = (InventoryRemoveRegister)vobject;

            if (util.compareDouble(removeRegister.getQuantity(), 0) == 0) {
                logMessage(Level.SEVERE, ServerInventoryMessageEnum.ZERO_REG_QTY, userData);
                return false;
            }
        }
        return ret;
    }

    @Override
    public Object deleteWithoutTx(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return super.delete(dobject, userData);
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryRemoveRegister register = (InventoryRemoveRegister) dobject;

        //Fetch journal line
        InventoryJournalLines journalLine = journalLinesBean.findJournalLine(register.getMasterId(), register.getTransId(), userData);

        if (InventoryJournalTypes.MOVEMENT.equals(journalLinesBean.getJournalType(journalLine, userData)) && util.compareDouble(journalLine.getQuantity(), 0) < 0) {
            try {
                TransactionHelper helper = new TransactionHelper(TransactionType.IVENT_POST_JRLINE);
                helper.setRelatedEntity(register);
                helper.setPostCase(MovementJournalPostCases.REGISTER_DELETED);
                post.post(journalLine, helper, userData);
            } catch (EMCStockException ex) {
                throw new EMCEntityBeanException("Failed to unreserve stock.");
            }
        }

        return super.delete(dobject, userData);
    }

    @Override
    public Object updateWithoutTx(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return super.update(uobject, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryRemoveRegister register = (InventoryRemoveRegister) uobject;

        InventoryRemoveRegister persistedRegister = (InventoryRemoveRegister) util.findDetachedPersisted(register, userData);

        //Fetch journal line
        InventoryJournalLines journalLine = journalLinesBean.findJournalLine(register.getMasterId(), register.getTransId(), userData);

        if (InventoryJournalTypes.MOVEMENT.equals(journalLinesBean.getJournalType(journalLine, userData)) && util.compareDouble(journalLine.getQuantity(), 0) < 0) {
            try {
                if (!util.checkObjectsEqual(persistedRegister.getBatch(), register.getBatch()) || !util.checkObjectsEqual(persistedRegister.getLocation(), register.getLocation()) || !util.checkObjectsEqual(persistedRegister.getPallet(), register.getPallet()) || !util.checkObjectsEqual(persistedRegister.getWarehouse(), register.getWarehouse()) || !util.checkObjectsEqual(persistedRegister.getSerial(), register.getSerial())) {
                    TransactionHelper helper = new TransactionHelper(TransactionType.IVENT_POST_JRLINE);
                    helper.setRelatedEntity(register);
                    helper.setPostCase(MovementJournalPostCases.REG_ITEM_CHANGED);
                    post.post(journalLine, helper, userData);
                } else if (!util.checkObjectsEqual(persistedRegister.getQuantity(), register.getQuantity())) {
                    if (util.compareDouble(register.getQuantity(), 0) < 0) {
                        TransactionHelper helper = new TransactionHelper(TransactionType.IVENT_POST_JRLINE);
                        helper.setRelatedEntity(register);
                        if (register.getQuantity() < persistedRegister.getQuantity()) {
                            helper.setPostCase(MovementJournalPostCases.REG_QTY_UP);
                        } else {
                            helper.setPostCase(MovementJournalPostCases.REG_QTY_DOWN);
                        }
                        
                        post.post(journalLine, helper, userData);
                    }
                }
            } catch (EMCStockException ex) {
                throw new EMCEntityBeanException("Failed to reserve/unreserve stock.");
            }
        }

        return super.update(uobject, userData);
    }

    /**
     * Returns a boolean indicating whether anything has been registered against the specified reference.
     * @param masterId Master id.
     * @param transId Trans id.
     * @param userData User data.
     * @return A boolean indicating whether any registration lines exist.
     */
    public boolean checkRegistrationExists(String masterId, String transId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRemoveRegister.class.getName());
        query.addAnd("transId", transId);
        query.addAnd("masterId", masterId);

        return util.exists(query, userData);
    }
}

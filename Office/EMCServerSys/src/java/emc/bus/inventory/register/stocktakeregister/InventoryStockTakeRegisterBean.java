/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.register.stocktakeregister;

import emc.bus.inventory.journals.InventoryJournalLinesLocal;
import emc.bus.inventory.register.superregister.InventoryRegisterSuperBean;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.register.InventoryRegisterSuper;
import emc.entity.inventory.register.InventoryStocktakeRegister;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryStockTakeRegisterBean extends InventoryRegisterSuperBean implements InventoryStockTakeRegisterLocal {

    @EJB
    private InventoryJournalLinesLocal journalLineBean;

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (valid) {
            InventoryStocktakeRegister register = (InventoryStocktakeRegister) theRecord;
            register.setOriginalCountedQty(register.getQuantity());
            return register;
        }
        return valid;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryStocktakeRegister register = (InventoryStocktakeRegister) uobject;
        InventoryStocktakeRegister oldRegister = (InventoryStocktakeRegister) super.findSQLVersionForEntity(register, userData);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class.getName());
        query.addAnd("journalNumber", register.getMasterId());
        query.addAnd("transId", register.getTransId());
        InventoryJournalLines journalLine = (InventoryJournalLines) util.executeSingleResultQuery(query, userData);
        journalLine.setQuantity(journalLine.getQuantity() - oldRegister.getQuantity() + register.getQuantity());
        if (userData.getUserData() != null && userData.getUserData().size() > 2 && userData.getUserData(2).equals(true)) {
            journalLine.setConfirmedQuantity(journalLine.getQuantity());
        } else {
            journalLine.setCountedQuantity(journalLine.getQuantity());
        }
        journalLineBean.update(journalLine, userData);
        register.setRegisteredQty(getRegisteredQty(register, userData) + register.getQuantity());
        return super.update(register, userData);
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryStocktakeRegister register = (InventoryStocktakeRegister) iobject;
        register.setRegisteredQty(getRegisteredQty(register, userData) + register.getQuantity());
        Object ret = super.insert(register, userData);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class.getName());
        query.addAnd("journalNumber", register.getMasterId());
        query.addAnd("transId", register.getTransId());
        InventoryJournalLines journalLine = (InventoryJournalLines) util.executeSingleResultQuery(query, userData);
        if (journalLine != null) {
            journalLine.setQuantity(journalLine.getQuantity() + register.getRegisteredQty());
            journalLine.setCountedQuantity(journalLine.getQuantity());
            journalLineBean.update(journalLine, userData);
        }
        return ret;
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        Object ret = super.delete(dobject, userData);
        InventoryStocktakeRegister register = (InventoryStocktakeRegister) dobject;
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class.getName());
        query.addAnd("journalNumber", register.getMasterId());
        query.addAnd("transId", register.getTransId());
        InventoryJournalLines journalLine = (InventoryJournalLines) util.executeSingleResultQuery(query, userData);
        journalLine.setQuantity(journalLine.getQuantity() - register.getQuantity());
        journalLine.setCountedQuantity(journalLine.getQuantity());
        journalLineBean.update(journalLine, userData);
        return ret;
    }

    @Override
    protected void updateRegisteredQty(InventoryRegisterSuper record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.UPDATE, InventoryStocktakeRegister.class.getName());
        query.addSet("registeredQty", getRegisteredQty(record, userData) + record.getQuantity());
        query.addAnd("masterId", record.getMasterId());
        query.addAnd("transId", record.getTransId());
        util.executeUpdate(query, userData);
    }

    @Override
    protected double getRegisteredQty(InventoryRegisterSuper record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryStocktakeRegister.class.getName());
        query.addAnd("masterId", record.getMasterId());
        query.addAnd("transId", record.getTransId());
        query.addAnd("recordID", record.getRecordID(), EMCQueryConditions.NOT);
        query.addFieldAggregateFunction("quantity", "SUM");
        Object res = util.executeSingleResultQuery(query, userData);
        return res == null ? 0.0 : (Double) res;
    }
}

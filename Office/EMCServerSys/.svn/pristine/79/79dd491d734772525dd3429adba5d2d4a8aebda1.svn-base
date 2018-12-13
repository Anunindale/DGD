/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.creditors.invoiceregister;

import emc.bus.creditors.creditnoteinvoicelines.CreditorsCreditNoteInvoiceLinesLocal;
import emc.bus.creditors.creditnoteinvoicemaster.CreditorsCreditNoteInvoiceMasterLocal;
import emc.bus.inventory.InventoryLocationLocal;
import emc.bus.inventory.register.superregister.InventoryRegisterSuperBean;
import emc.entity.creditors.CreditorsCreditNoteInvoiceMaster;
import emc.entity.creditors.CreditorsInvoiceRegister;
import emc.entity.inventory.register.InventoryRegisterSuper;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerInventoryMessageEnum;
import emc.messages.ServerSOPMessageEnum;
import emc.tables.EMCTable;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class CreditorsInvoiceRegisterBean extends InventoryRegisterSuperBean implements CreditorsInvoiceRegisterLocal {

    @EJB
    private CreditorsCreditNoteInvoiceMasterLocal creditNoteInvoiceMasterBean;
    @EJB
    private CreditorsCreditNoteInvoiceLinesLocal creditNoteInvoiceLinesBean;
    @EJB
    private InventoryLocationLocal locationBean;

    public CreditorsInvoiceRegisterBean() {
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        if (valid) {
            CreditorsInvoiceRegister register = (CreditorsInvoiceRegister) theRecord;

            if (fieldNameToValidate.equals("quantity")) {
                //Original quantity will be zero for manual invoices
                if (util.compareDouble(register.getOriginalQuantity(), 0) != 0 && register.getOriginalQuantity() > register.getOriginalQuantity()) {
                    logMessage(Level.SEVERE, ServerSOPMessageEnum.REGISTER_QUANTITY_EXCEEDED, userData);
                    return false;
                }
            } else if (fieldNameToValidate.equals("location")) {
                if (!locationBean.checkLocationInWarehouse(register.getWarehouse(), register.getLocation(), userData)) {
                    logMessage(Level.SEVERE, ServerInventoryMessageEnum.LOCATION_NOT_IN_WAREHOUSE, userData, register.getLocation(), register.getWarehouse());
                    return false;
                }
            }
        }

        return valid;
    }

    @Override
    protected void updateRegisteredQty(InventoryRegisterSuper record, EMCUserData userData) {
        double registeredQuantity = getRegisteredQty(record, userData);
        EMCQuery query = new EMCQuery(enumQueryTypes.UPDATE, CreditorsInvoiceRegister.class);
        query.addSet("registeredQty", registeredQuantity);
        query.addSet("totalQty", registeredQuantity);
        query.addAnd("masterId", record.getMasterId());
        query.addAnd("transId", record.getTransId());
        util.executeUpdate(query, userData);
    }

    @Override
    protected double getRegisteredQty(InventoryRegisterSuper record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CreditorsInvoiceRegister.class.getName());
        query.addAnd("masterId", record.getMasterId());
        query.addAnd("transId", record.getTransId());
        query.addAnd("recordID", record.getRecordID(), EMCQueryConditions.NOT);
        query.addFieldAggregateFunction("quantity", "SUM");
        Object res = util.executeSingleResultQuery(query, userData);
        return (res == null ? 0.0 : ((Double) res)) + record.getQuantity();
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        CreditorsInvoiceRegister register = (CreditorsInvoiceRegister) iobject;
        return super.insert(iobject, userData);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean valid = super.doInsertValidation(vobject, userData);

        if (valid) {
            CreditorsInvoiceRegister register = (CreditorsInvoiceRegister) vobject;

            CreditorsCreditNoteInvoiceMaster invoice = creditNoteInvoiceMasterBean.getCreditNoteInvoiceMaster(register.getMasterId(), userData);
            if (!invoice.isStockCreditNoteInvoice()) {
                logMessage(Level.SEVERE, "Register lines may not be captured against non-stock Invoice.", userData);
                return false;
            }

            if (!locationBean.checkLocationInWarehouse(register.getWarehouse(), register.getLocation(), userData)) {
                logMessage(Level.SEVERE, ServerInventoryMessageEnum.LOCATION_NOT_IN_WAREHOUSE, userData, register.getLocation(), register.getWarehouse());
                return false;
            }
        }
        return valid;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        CreditorsInvoiceRegister register = (CreditorsInvoiceRegister) uobject;
        CreditorsInvoiceRegister persisted = (CreditorsInvoiceRegister) util.findDetachedPersisted(register, userData);

        if (register.getQuantity() != persisted.getQuantity()) {
            //Ensure that correct register quantity is reflected on all records.
            register.setRegisteredQty(getRegisteredQty(register, userData));
        }

        return super.update(uobject, userData);
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);

        if (ret) {
            CreditorsInvoiceRegister register = (CreditorsInvoiceRegister) vobject;
            if (!locationBean.checkLocationInWarehouse(register.getWarehouse(), register.getLocation(), userData)) {
                logMessage(Level.SEVERE, ServerInventoryMessageEnum.LOCATION_NOT_IN_WAREHOUSE, userData, register.getLocation(), register.getWarehouse());
                return false;
            }
        }
        return ret;
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        CreditorsInvoiceRegister register = (CreditorsInvoiceRegister) dobject;

        return super.delete(register, userData);
    }

    /** Updates the specified record without doing any validations. */
    @Override
    public Object doUpdate(CreditorsInvoiceRegister register, EMCUserData userData) {
        super.doUpdate(register, userData);

        return register;
    }

    /** Returns all register lines matching the specified criteria.
     *
     * @param creditNote Credit note number.
     * @param transId Transaction id.
     * @param userData User data.
     * @return Register lines for the specified transaction id on the specified Credit Note.
     */
    public List<CreditorsInvoiceRegister> getRegisterLines(String creditNote, String transId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CreditorsInvoiceRegister.class);
        query.addAnd("masterId", creditNote);
        query.addAnd("transId", transId);

        return (List<CreditorsInvoiceRegister>) util.executeGeneralSelectQuery(query, userData);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.creditnoteregister;

import emc.bus.debtors.creditnotes.DebtorsCreditNoteLinesLocal;
import emc.bus.debtors.creditnotes.DebtorsCreditNoteMasterLocal;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryLocationLocal;
import emc.bus.inventory.register.superregister.InventoryRegisterSuperBean;
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.creditnoteregister.DebtorsCreditNoteRegister;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.register.InventoryRegister;
import emc.entity.inventory.register.InventoryRegisterSuper;
import emc.enums.debtors.DebtorsInvoiceCreditNoteType;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerDebtorsMessageEnum;
import emc.messages.ServerInventoryMessageEnum;
import emc.messages.ServerSOPMessageEnum;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class DebtorsCreditNoteRegisterBean extends InventoryRegisterSuperBean implements DebtorsCreditNoteRegisterLocal {

    @EJB
    private DebtorsCreditNoteMasterLocal creditNoteMasterBean;
    @EJB
    private DebtorsCreditNoteLinesLocal creditNoteLinesBean;
    @EJB
    private InventoryLocationLocal locationBean;
    @EJB
    private InventoryItemMasterLocal itemBean;

    /** Creates a new instance of DebtorsCreditNoteRegisterBean. */
    public DebtorsCreditNoteRegisterBean() {
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        if (valid) {
            DebtorsCreditNoteRegister register = (DebtorsCreditNoteRegister) theRecord;

            if (fieldNameToValidate.equals("quantity")) {
                //Original quantity will be zero for manual invoices
                if (util.compareDouble(register.getOriginalQuantity(), 0) != 0 && Math.abs(register.getOriginalQuantity()) > register.getOriginalQuantity()) {
                    logMessage(Level.SEVERE, ServerSOPMessageEnum.REGISTER_QUANTITY_EXCEEDED, userData);
                    return false;
                }

                DebtorsCreditNoteMaster master = creditNoteMasterBean.getCreditNote(register.getMasterId(), userData);
                DebtorsCreditNoteLines line = getCreditNoteLine(register.getMasterId(), register.getTransId(), userData);

                BigDecimal registeredAbs = new BigDecimal(getRegisteredQty(register, userData)).abs();

                //Sales Credit Notes will have a max quantity set on the line.
                if (line.getMaxQuantity() != null && line.getMaxQuantity().compareTo(BigDecimal.ZERO) != 0 && registeredAbs.compareTo(line.getMaxQuantity().abs()) > 0) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.LINE_MAX_EXCEEDED, userData);
                    return false;
                }

                if (DebtorsInvoiceCreditNoteType.fromString(master.getInvCNType()) == DebtorsInvoiceCreditNoteType.MANUAL_CREDIT_NOTE) {
                    //Use < because values are negative.
                    if (util.compareDouble(getRegisteredQty(register, userData), line.getQuantity().doubleValue()) < 0) {
                        logMessage(Level.SEVERE, ServerDebtorsMessageEnum.OVER_REGISTRATION, userData);
                        return false;
                    }
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
        EMCQuery query = new EMCQuery(enumQueryTypes.UPDATE, DebtorsCreditNoteRegister.class);
        query.addSet("registeredQty", registeredQuantity);
        //Also update total quantity, as the total quantity of a Credit Note line should be equal to the total registered quantity.
        query.addSet("totalQty", registeredQuantity);
        query.addAnd("masterId", record.getMasterId());
        query.addAnd("transId", record.getTransId());
        util.executeUpdate(query, userData);
    }

    @Override
    protected double getRegisteredQty(InventoryRegisterSuper record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteRegister.class.getName());
        query.addAnd("masterId", record.getMasterId());
        query.addAnd("transId", record.getTransId());
        query.addAnd("recordID", record.getRecordID(), EMCQueryConditions.NOT);
        query.addFieldAggregateFunction("quantity", "SUM");
        Object res = util.executeSingleResultQuery(query, userData);
        return (res == null ? 0.0 : ((Double) res)) + record.getQuantity();
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCreditNoteRegister register = (DebtorsCreditNoteRegister) iobject;

        //Indicates that Credit Note is being created.  Used on register lines bean insert method.
        if (userData.getUserData(6) != Boolean.TRUE) {
            //Ensure that correct register quantity is reflected on all records.
            register.setRegisteredQty(getRegisteredQty(register, userData));

            DebtorsCreditNoteMaster master = creditNoteMasterBean.getCreditNote(register.getMasterId(), userData);

            //Only update for sales Credit Notes.  The user should edit manual Credit Notes manually.
            if (DebtorsInvoiceCreditNoteType.fromString(master.getInvCNType()) == DebtorsInvoiceCreditNoteType.SALES_CREDIT_NOTE) {
                register.setTotalQty(register.getRegisteredQty());

                DebtorsCreditNoteLines line = getCreditNoteLine(register.getMasterId(), register.getTransId(), userData);
                line.setQuantity(new BigDecimal(register.getTotalQty()));

                creditNoteLinesBean.update(line, userData);
            }
        }

        return super.insert(iobject, userData);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        DebtorsCreditNoteRegister register = (DebtorsCreditNoteRegister) vobject;
        DebtorsCreditNoteMaster creditNote = creditNoteMasterBean.getCreditNote(register.getMasterId(), userData);

        if (!creditNote.isInvoiceStock()) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.NO_STOCK_CREDIT_NOTE, userData);
            return false;
        }

        boolean valid = super.doInsertValidation(vobject, userData);

        if (valid) {
            DebtorsCreditNoteLines line = getCreditNoteLine(register.getMasterId(), register.getTransId(), userData);
            InventoryItemDimensionGroup dimensionGroup = itemBean.getItemDimensionGroupRecord(line.getItemId(), userData);
            //Only check location if it is active
            if (dimensionGroup != null && dimensionGroup.getLocationActive()) {
                if (!locationBean.checkLocationInWarehouse(register.getWarehouse(), register.getLocation(), userData)) {
                    logMessage(Level.SEVERE, ServerInventoryMessageEnum.LOCATION_NOT_IN_WAREHOUSE, userData, register.getLocation(), register.getWarehouse());
                    return false;
                }
            }

            //Cater for Cntrl + Down-arrow
            if (DebtorsInvoiceCreditNoteType.fromString(creditNote.getInvCNType()) == DebtorsInvoiceCreditNoteType.MANUAL_CREDIT_NOTE) {
                //Use < because values are negative.
                if (util.compareDouble(getRegisteredQty(register, userData), line.getQuantity().doubleValue()) < 0) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.OVER_REGISTRATION, userData);
                    return false;
                }
            }
        }

        return valid;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCreditNoteRegister register = (DebtorsCreditNoteRegister) uobject;
        DebtorsCreditNoteRegister persisted = (DebtorsCreditNoteRegister) util.findDetachedPersisted(register, userData);

        if (register.getQuantity() != persisted.getQuantity()) {
            //Ensure that correct register quantity is reflected on all records.
            register.setRegisteredQty(getRegisteredQty(register, userData));

            DebtorsCreditNoteMaster master = creditNoteMasterBean.getCreditNote(register.getMasterId(), userData);

            //Only update for sales Credit Notes.  The user should edit manual Credit Notes manually.
            if (DebtorsInvoiceCreditNoteType.fromString(master.getInvCNType()) == DebtorsInvoiceCreditNoteType.SALES_CREDIT_NOTE) {
                register.setTotalQty(register.getRegisteredQty());

                DebtorsCreditNoteLines line = getCreditNoteLine(register.getMasterId(), register.getTransId(), userData);
                line.setQuantity(new BigDecimal(register.getTotalQty()));

                creditNoteLinesBean.update(line, userData);
            }
        }

        return super.update(uobject, userData);
    }

    /** Returns the Credit Note line matching the specified criteria. */
    private DebtorsCreditNoteLines getCreditNoteLine(String masterId, String transId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteLines.class);
        query.addAnd("invCNNumber", masterId);
        query.addAnd("inventTransId", transId);

        return (DebtorsCreditNoteLines) util.executeSingleResultQuery(query, userData);
    }

    /** Returns all register lines matching the specified criteria.
     *
     * @param creditNote Credit note number.
     * @param transId Transaction id.
     * @param userData User data.
     * @return Register lines for the specified transaction id on the specified Credit Note.
     */
    public List<DebtorsCreditNoteRegister> getRegisterLines(String creditNote, String transId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteRegister.class);
        query.addAnd("masterId", creditNote);
        query.addAnd("transId", transId);

        return (List<DebtorsCreditNoteRegister>) util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCreditNoteRegister register = (DebtorsCreditNoteRegister) dobject;
        DebtorsCreditNoteRegister persisted = (DebtorsCreditNoteRegister) util.findDetachedPersisted(register, userData);

        DebtorsCreditNoteMaster master = creditNoteMasterBean.getCreditNote(register.getMasterId(), userData);

        //Only update for sales Credit Notes.  The user should edit manual Credit Notes manually.
        if (DebtorsInvoiceCreditNoteType.fromString(master.getInvCNType()) == DebtorsInvoiceCreditNoteType.SALES_CREDIT_NOTE) {
            register.setTotalQty(register.getRegisteredQty());

            DebtorsCreditNoteLines line = getCreditNoteLine(register.getMasterId(), register.getTransId(), userData);
            //Use persisted quantity, just in case the user changed the quantity on the record before deleting.
            line.setQuantity(line.getQuantity().subtract(new BigDecimal(persisted.getQuantity())));

            if (line.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
                //Since the register line has already been deleted, logic should not be called recursively.
                creditNoteLinesBean.delete(line, userData);
            } else {
                creditNoteLinesBean.update(line, userData);
            }
        }

        Object ret;
        //Check whether all lines have already been deleted by credit note lines bean.
        if (userData.getUserData(5) != Boolean.TRUE) {
            //Clear registered quantity
            register.setQuantity(0);

            ret = super.delete(dobject, userData);

            updateRegisteredQty(register, userData);
        } else {
            ret = register;
        }

        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);

        if (ret) {
            DebtorsCreditNoteRegister register = (DebtorsCreditNoteRegister) vobject;

            DebtorsCreditNoteLines line = getCreditNoteLine(register.getMasterId(), register.getTransId(), userData);
            InventoryItemDimensionGroup dimensionGroup = itemBean.getItemDimensionGroupRecord(line.getItemId(), userData);
            //Only check location if it is active
            if (dimensionGroup != null && dimensionGroup.getLocationActive()) {
                if (!locationBean.checkLocationInWarehouse(register.getWarehouse(), register.getLocation(), userData)) {
                    logMessage(Level.SEVERE, ServerInventoryMessageEnum.LOCATION_NOT_IN_WAREHOUSE, userData, register.getLocation(), register.getWarehouse());
                    return false;
                }
            }
        }
        return ret;
    }

    /** Updates the specified record without doing any validations. */
    @Override
    public Object doUpdate(DebtorsCreditNoteRegister register, EMCUserData userData) {
        super.doUpdate(register, userData);

        return register;
    }
}

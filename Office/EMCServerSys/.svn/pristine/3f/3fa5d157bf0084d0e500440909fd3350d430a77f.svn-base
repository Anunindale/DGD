
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.creditnotes;

import emc.bus.debtors.creditnoteapprovalgroups.DebtorsCreditNoteApprovalGroupsLocal;
import emc.bus.debtors.creditnotereasons.DebtorsCreditNoteReasonsLocal;
import emc.bus.debtors.creditnoteregister.DebtorsCreditNoteRegisterLocal;
import emc.bus.debtors.customerinvoice.DebtorsCustomerInvoiceLinesLocal;
import emc.bus.inventory.InventoryItemGroupLocal;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.logic.InventoryItemLogicLocal;
import emc.bus.inventory.transactions.EMCStockException;
import emc.bus.inventory.transactions.ProcessStockTransactionLocal;
import emc.bus.inventory.transactions.TransactionHelper;
import emc.bus.inventory.transactions.TransactionType;
import emc.bus.sop.pricearangements.SOPPriceArangementsLocal;
import emc.bus.sop.salesrepcommission.SOPSalesRepCommissionLocal;
import emc.entity.debtors.DebtorsCreditNoteApprovalGroups;
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCreditNoteReasons;
import emc.entity.debtors.creditnoteregister.DebtorsCreditNoteRegister;
import emc.entity.inventory.InventoryItemGroup;
import emc.entity.inventory.InventoryItemMaster;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryItemTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerDebtorsMessageEnum;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @description : Entity bean for DebtorsCreditNoteLines.
 *
 * @date        : 14 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsCreditNoteLinesBean extends EMCEntityBean implements DebtorsCreditNoteLinesLocal {

    @EJB
    private ProcessStockTransactionLocal post;
    @EJB
    private DebtorsCreditNoteMasterLocal masterBean;
    @EJB
    private InventoryItemMasterLocal itemBean;
    @EJB
    private DebtorsCustomerInvoiceLinesLocal invoiceLinesBean;
    @EJB
    private DebtorsCreditNoteRegisterLocal registerBean;
    @EJB
    private DebtorsCreditNoteApprovalGroupsLocal approvalGroupsBean;
    @EJB
    private DebtorsCreditNoteReasonsLocal reasonsBean;
    @EJB
    private SOPSalesRepCommissionLocal commissionBean;
    @EJB
    private InventoryItemGroupLocal itemGroupsBean;
    @EJB
    private SOPPriceArangementsLocal priceArrangementsBean;
    @EJB
    private InventoryItemLogicLocal itemLogicBean;

    /** Creates a new instance of DebtorsCreditNoteLinesBean */
    public DebtorsCreditNoteLinesBean() {
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCreditNoteLines line = (DebtorsCreditNoteLines) iobject;

        DebtorsCreditNoteMaster master = masterBean.getCreditNote(line.getInvCNNumber(), userData);

        invoiceLinesBean.calculateLineTotal(line, master, userData);

        //Transaction id is populated on insert.
        super.insert(line, userData);

        if (master != null && master.isInvoiceStock()) {
            postStockTransaction(line, userData);
        }

        return line;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCreditNoteLines line = (DebtorsCreditNoteLines) uobject;

        DebtorsCreditNoteMaster master = masterBean.getCreditNote(line.getInvCNNumber(), userData);

        invoiceLinesBean.calculateLineTotal(line, master, userData);

        if (master != null) {
            if (master.isInvoiceStock()) {
                postStockTransaction(line, userData);
            }

            DebtorsInvoiceStatus status = DebtorsInvoiceStatus.fromString(master.getStatus());
            if (DebtorsInvoiceStatus.APPROVED.equals(status)) {
                DebtorsCreditNoteReasons reason = reasonsBean.getReason(master.getReasonCode(), userData);

                if (reason == null) {
                    throw new EMCEntityBeanException(getMessage(ServerDebtorsMessageEnum.REASON_NOT_FOUND, userData));
                }
                DebtorsCreditNoteApprovalGroups approvalGroup = approvalGroupsBean.getApprovalGroup(reason.getApprovalGroupId(), userData);

                if (approvalGroup == null) {
                    throw new EMCEntityBeanException(getMessage(ServerDebtorsMessageEnum.APPROVAL_GROUP_NOT_FOUND, userData, reason.getApprovalGroupId()));
                }

                master.setApprovalGroup(approvalGroup.getApprovalGroupId());
                master.setStatus(DebtorsInvoiceStatus.CAPTURED.toString());

                masterBean.update(master, userData);

                logMessage(Level.INFO, ServerDebtorsMessageEnum.UNAPPROVED_CR, userData);
            } else {
                if (DebtorsInvoiceStatus.POSTED.equals(status)) {
                    throw new EMCEntityBeanException("A Credit Note may not be changed after being posted.");
                } else if (DebtorsInvoiceStatus.CANCELED.equals(status) || DebtorsInvoiceStatus.DISTRIBUTION.equals(status)) {
                    throw new EMCEntityBeanException("A Credit Note may not be changed after being cancelled.");
                }
            }
        }

        return super.update(uobject, userData);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);

        if (ret) {
            DebtorsCreditNoteLines line = (DebtorsCreditNoteLines) vobject;
            DebtorsCreditNoteMaster master = masterBean.getCreditNote(line.getInvCNNumber(), userData);
            if (!itemLogicBean.validateActiveDimensions(line, userData) || !itemLogicBean.validateDimensionValues(line, userData)) {
                return false;
            }

            if (!validateItemAgainstRep(line, master, userData)) {
                return false;
            }
        }

        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);

        if (ret) {
            DebtorsCreditNoteLines line = (DebtorsCreditNoteLines) vobject;
            DebtorsCreditNoteMaster master = masterBean.getCreditNote(line.getInvCNNumber(), userData);

            if (!itemLogicBean.validateActiveDimensions(line, userData) || !itemLogicBean.validateDimensionValues(line, userData)) {
                return false;
            }

            if (!validateItemAgainstRep(line, master, userData)) {
                return false;
            }
        }

        return ret;
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doDeleteValidation(vobject, userData);

        if (ret) {
            DebtorsCreditNoteLines line = (DebtorsCreditNoteLines) vobject;
            DebtorsCreditNoteMaster master = masterBean.getCreditNote(line.getInvCNNumber(), userData);

            if (master != null && DebtorsInvoiceStatus.fromString(master.getStatus()) == DebtorsInvoiceStatus.POSTED) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.POSTED_CR_LINE_NO_DELETE, userData);
                return false;
            } else if (master != null && (DebtorsInvoiceStatus.fromString(master.getStatus()) == DebtorsInvoiceStatus.CANCELED || DebtorsInvoiceStatus.fromString(master.getStatus()) == DebtorsInvoiceStatus.CANCELED)) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.CANCELED_CR_LINE_NO_DELETE, userData);
                return false;
            }
        }

        return ret;
    }

    /**
     * This method is used to post an Inventory transaction.  It should be called from insert(), update() and delete().
     * It is exposed, so that it can be used to generate transactions when creating a Credit Note from an Invoice.  When
     * this happens, it will not be possible to select the Credit Note master from the insert method.
     * @param line DebtorsCustomerInvoiceLines instance to post transaction for.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean postStockTransaction(DebtorsCreditNoteLines line, EMCUserData userData) throws EMCEntityBeanException {
        InventoryItemMaster item = itemBean.findItem(line.getItemId(), userData);

        if (item == null) {
            throw new EMCEntityBeanException("Item does not exist - " + line.getItemId());
        } else {
            if (InventoryItemTypes.SERVICE.equals(InventoryItemTypes.fromString(item.getItemType()))) {
                //Do not create transactions for service items.
                return true;
            }
        }

        try {
            post.post(line, new TransactionHelper(TransactionType.DEBTORS_POST_CREDIT_NOTE_LINE), userData);
            return true;
        } catch (EMCStockException ex) {
            ex.printStackTrace();
            throw new EMCEntityBeanException(ex);
        }
    }

    /**
     * Returns the Credit Note lines belonging to the specified Credit Note.
     * @param creditNoteNo Credit Note number.
     * @param userData User data.
     * @return A list of DebtorsCreditNoteLines instances.
     */
    public List<DebtorsCreditNoteLines> getCreditNoteLines(String creditNoteNo, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteLines.class);
        query.addAnd("invCNNumber", creditNoteNo);

        return (List<DebtorsCreditNoteLines>) util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        if (ret) {
            DebtorsCreditNoteLines line = (DebtorsCreditNoteLines) theRecord;
            DebtorsCreditNoteMaster master = masterBean.getCreditNote(line.getInvCNNumber(), userData);

            if (!isBlank(master.getRefInvoiceNo())) {
                //Only quantity may be changed on stock Credit Notes generated from Invoices
                if (master.isInvoiceStock() && !fieldNameToValidate.equals("quantity")) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.ONLY_QTY_CHANGE, userData);
                    return false;
                } else {
                    if (!fieldNameToValidate.equals("quantity") && !fieldNameToValidate.equals("unitPrice")) {
                        logMessage(Level.SEVERE, ServerDebtorsMessageEnum.NO_ITEM_CHANGE, userData);
                        return false;
                    }
                }
            }


            if (fieldNameToValidate.equals("quantity")) {
                //Always have negative quantities on Credit Notes
                line.setQuantity(line.getQuantity().abs().multiply(new BigDecimal(-1)));
            }

            //Cater for itemReference on DS as well
            if (fieldNameToValidate.contains("item") || fieldNameToValidate.contains("dimension") || fieldNameToValidate.equals("quantity")) {
                if (!validateItemAgainstRep(line, master, userData)) {
                    return false;
                }

                InventoryItemMaster item = itemBean.findItem(line.getItemId(), userData);

                if (master.isCommissionApplicable()) {
                    //Only validate item against rep if commission is applicable to the Invoice.
                    if (!commissionBean.validateSalesRep(master.getSalesRep(), master.getCustomerNo(), null, line.getItemId(), item, userData)) {
                        return false;
                    }
                }
                try {
                    line.setUnitPrice(priceArrangementsBean.findItemPrice(master.getCustomerNo(), line.getItemId(), line.getDimension1(), line.getDimension2(), line.getDimension3(), master.getInvoiceDate(), line.getQuantity(), userData));
                } catch (EMCEntityBeanException ex) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to set unit price: " + ex.getMessage(), userData);
                    return false;
                }

                if (item != null && fieldNameToValidate.contains("item")) {
                    line.setVatCode(item.getSellingVatCode());
                }
            }

            return line;
        }

        return ret;
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCreditNoteLines line = (DebtorsCreditNoteLines) dobject;
        DebtorsCreditNoteMaster master = masterBean.getCreditNote(line.getInvCNNumber(), userData);

        if (master.isInvoiceStock()) {
            //Delete transaction
            line.setQuantity(BigDecimal.ZERO);
            postStockTransaction(line, userData);

            List<DebtorsCreditNoteRegister> registerLines = registerBean.getRegisterLines(line.getInvCNNumber(), line.getInventTransId(), userData);

            for (DebtorsCreditNoteRegister registerLine : registerLines) {
                //Do not execute deletion logic recursively from register bean.
                super.doDelete(registerLine, userData);
            }
            //Indicates that all register lines have been deleted.  Checked on DebtorsCreditNoteRegisterBean.
            userData.setUserData(5, true);
        }

        return super.delete(dobject, userData);
    }

    /**
     * Validates the item on the specified line against the Invoice master record for that line.
     * If commission does not apply to the Invoice or to the item on the line, this method will
     * always return true.
     *
     * @param line Line to check
     * @param master Master record to validate against.
     * @param userData User data.
     * @return A boolean indicating whether the item on the specified line may appear on the invoice.
     */
    private boolean validateItemAgainstRep(DebtorsCreditNoteLines line, DebtorsCreditNoteMaster master, EMCUserData userData) {
        InventoryItemGroup itemGroup = itemGroupsBean.findItemsItemGroup(line.getItemId(), userData);

        //Do not do this validation when commission is not applicable to the item on a line.
        if (master.isCommissionApplicable() && itemGroup != null && itemGroup.isCommissionApplicable()) {
            return commissionBean.validateSalesRep(master.getSalesRep(), master.getCustomerNo(), null, line.getItemId(), null, userData);
        } else {
            return true;
        }
    }

    @Override
    public boolean cancelCreditNoteLine(DebtorsCreditNoteLines line, EMCUserData userData) throws EMCEntityBeanException {
        InventoryItemMaster item = itemBean.findItem(line.getItemId(), userData);

        if (item == null) {
            throw new EMCEntityBeanException("Item does not exist - " + line.getItemId());
        } else {
            if (InventoryItemTypes.SERVICE.equals(InventoryItemTypes.fromString(item.getItemType()))) {
                //Do not create transactions for service items.
                return true;
            }
        }

        try {
            post.post(line, new TransactionHelper(TransactionType.DEBTORS_CANCEL_INVOICE_LINE), userData);
            return true;
        } catch (EMCStockException ex) {
            ex.printStackTrace();
            throw new EMCEntityBeanException(ex);
        }
    }
}

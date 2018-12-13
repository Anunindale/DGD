/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.creditors.creditnoteinvoicelines;

import emc.bus.creditors.creditnoteinvoicemaster.CreditorsCreditNoteInvoiceMasterLocal;
import emc.bus.gl.vatcodes.GLVATCodeLocal;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.logic.InventoryItemDimensionCombinationLogicLocal;
import emc.bus.inventory.transactions.EMCStockException;
import emc.bus.inventory.transactions.ProcessStockTransactionLocal;
import emc.bus.inventory.transactions.TransactionHelper;
import emc.bus.inventory.transactions.TransactionType;
import emc.bus.pop.pricearrangements.POPPriceArrangementsLocal;
import emc.entity.creditors.CreditorsCreditNoteInvoiceLines;
import emc.entity.creditors.CreditorsCreditNoteInvoiceMaster;
import emc.entity.inventory.InventoryItemMaster;
import emc.enums.creditors.CreditorsCreditNoteInvoiceStatus;
import emc.enums.creditors.CreditorsCreditNoteInvoiceType;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.dimensions.EnumDimensions;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class CreditorsCreditNoteInvoiceLinesBean extends EMCEntityBean implements CreditorsCreditNoteInvoiceLinesLocal {

    @EJB
    private CreditorsCreditNoteInvoiceMasterLocal masterBean;
    @EJB
    private InventoryItemMasterLocal itemBean;
    @EJB
    private POPPriceArrangementsLocal priceArrangementBean;
    @EJB
    private GLVATCodeLocal vatCodeBean;
    @EJB
    private InventoryItemDimensionCombinationLogicLocal dimensionBean;
    @EJB
    private ProcessStockTransactionLocal transactionBean;

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        CreditorsCreditNoteInvoiceLines record = (CreditorsCreditNoteInvoiceLines) theRecord;

        if (fieldNameToValidate.equals("itemId")) {
            if (!isBlank(record.getItemId())) {
                try {
                    calculateLineTotal(null, record, userData);
                    return record;
                } catch (EMCEntityBeanException ex) {
                    logMessage(Level.SEVERE, "Failed to set unit price: " + ex.getMessage(), userData);
                }
            }
        } else if (fieldNameToValidate.equals("dimension1")) {
            if (!isBlank(record.getDimension1())) {
                if (!isBlank(record.getItemId())) {
                    if (!dimensionBean.isDimensionInUse(record.getDimension1(), record.getItemId(), EnumDimensions.DIMENSION1, userData)) {
                        logMessage(Level.SEVERE, "The selected dimension is not active on the selected item.", userData);
                        return false;
                    } else {
                        try {
                            calculateLineTotal(null, record, userData);
                            return record;
                        } catch (EMCEntityBeanException ex) {
                            logMessage(Level.SEVERE, "Failed to set unit price: " + ex.getMessage(), userData);
                        }
                    }
                }
            }
        } else if (fieldNameToValidate.equals("dimension2")) {
            if (!isBlank(record.getDimension2())) {
                if (!isBlank(record.getItemId())) {
                    if (!dimensionBean.isDimensionInUse(record.getDimension2(), record.getItemId(), EnumDimensions.DIMENSION2, userData)) {
                        logMessage(Level.SEVERE, "The selected dimension is not active on the selected item.", userData);
                        return false;
                    } else {
                        try {
                            calculateLineTotal(null, record, userData);
                            return record;
                        } catch (EMCEntityBeanException ex) {
                            logMessage(Level.SEVERE, "Failed to set unit price: " + ex.getMessage(), userData);
                        }
                    }
                }
            }
        } else if (fieldNameToValidate.equals("dimension3")) {
            if (!isBlank(record.getDimension3())) {
                if (!isBlank(record.getItemId())) {
                    if (!dimensionBean.isDimensionInUse(record.getDimension3(), record.getItemId(), EnumDimensions.DIMENSION3, userData)) {
                        logMessage(Level.SEVERE, "The selected dimension is not active on the selected item.", userData);
                        return false;
                    } else {
                        try {
                            calculateLineTotal(null, record, userData);
                            return record;
                        } catch (EMCEntityBeanException ex) {
                            logMessage(Level.SEVERE, "Failed to set unit price: " + ex.getMessage(), userData);
                        }
                    }
                }
            }
        } else if (fieldNameToValidate.equals("quantity")) {
            CreditorsCreditNoteInvoiceMaster master = masterBean.getCreditNoteInvoiceMaster(record.getCreditNoteInvoiceNumber(), userData);
            switch (CreditorsCreditNoteInvoiceType.fromString(master.getCreditNoteInvoiceType())) {
                case MANUAL_CREDIT_NOTE:
                case PURCHASING_CREDIT_NOTE:
                    //Always have negative quantities on Credit Notes
                    record.setQuantity(record.getQuantity().abs().multiply(new BigDecimal(-1)));
                    break;
            }
            try {
                calculateLineTotal(null, record, userData);
                return record;
            } catch (EMCEntityBeanException ex) {
                logMessage(Level.SEVERE, "Failed to set unit price: " + ex.getMessage(), userData);
            }
        }

        return valid;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        CreditorsCreditNoteInvoiceLines line = (CreditorsCreditNoteInvoiceLines) vobject;

        boolean valid = doSaveValidation(line, userData);

        if (valid) {
            valid = super.doInsertValidation(line, userData);
        }

        return valid;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        CreditorsCreditNoteInvoiceLines line = (CreditorsCreditNoteInvoiceLines) vobject;

        boolean valid = doSaveValidation(line, userData);

        if (valid) {
            valid = super.doUpdateValidation(line, userData);
        }

        return valid;
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        CreditorsCreditNoteInvoiceLines line = (CreditorsCreditNoteInvoiceLines) vobject;


        if (isPosted(null, line, userData)) {
            logMessage(Level.SEVERE, "The selected Credit Note/Invoice has already been posted.", userData);
            return false;
        }

        return super.doDeleteValidation(line, userData);
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        CreditorsCreditNoteInvoiceLines line = (CreditorsCreditNoteInvoiceLines) iobject;
        CreditorsCreditNoteInvoiceMaster master = masterBean.getCreditNoteInvoiceMaster(line.getCreditNoteInvoiceNumber(), userData);

        removeApprovalPosted(master, line, userData);

        calculateLineTotal(master, line, userData);

        super.insert(line, userData);

        doInventoryTransactionPosting(master, line, userData);

        return line;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        CreditorsCreditNoteInvoiceLines line = (CreditorsCreditNoteInvoiceLines) uobject;
        CreditorsCreditNoteInvoiceMaster master = masterBean.getCreditNoteInvoiceMaster(line.getCreditNoteInvoiceNumber(), userData);

        removeApprovalPosted(master, line, userData);

        calculateLineTotal(master, line, userData);

        super.update(line, userData);

        doInventoryTransactionPosting(master, line, userData);

        return line;
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        CreditorsCreditNoteInvoiceLines line = (CreditorsCreditNoteInvoiceLines) dobject;

        removeApprovalPosted(null, line, userData);

        line.setQuantity(BigDecimal.ZERO);

        doInventoryTransactionPosting(null, line, userData);

        return super.delete(line, userData);
    }

    private boolean doSaveValidation(CreditorsCreditNoteInvoiceLines line, EMCUserData userData) {
        if (isPosted(null, line, userData)) {
            logMessage(Level.SEVERE, "The selected Credit Note/Invoice has already been posted.", userData);
            return false;
        }

        if (!dimensionBean.validateActiveDimensions(line.getItemId(), line.getDimension1(), line.getDimension2(), line.getDimension3(), null, false, userData)) {
            logMessage(Level.SEVERE, "The selected dimension set is not active on the selected item.", userData);
            return false;
        }
        return true;
    }

    private void doSave(CreditorsCreditNoteInvoiceLines line, EMCUserData userData) throws EMCEntityBeanException {
    }

    private void calculateLineTotal(CreditorsCreditNoteInvoiceMaster master, CreditorsCreditNoteInvoiceLines line, EMCUserData userData) throws EMCEntityBeanException {
        if (master == null) {
            master = masterBean.getCreditNoteInvoiceMaster(line.getCreditNoteInvoiceNumber(), userData);
        }

        if (isBlank(line.getDiscountPercentage())) {
            line.setDiscountPercentage(BigDecimal.ZERO);
        }

        if (isBlank(line.getVatCode())) {
            line.setVatCode(master.getVatCode());
        }

        if (line.getUnitPrice().compareTo(BigDecimal.ZERO) == 0) {
            line.setUnitPrice(priceArrangementBean.findItemPrice(line.getItemId(), line.getDimension1(), line.getDimension2(), line.getDimension3(), master.getCreditNoteInvoiceDate(), line.getQuantity(), userData));
        }

        BigDecimal grossAmount = util.roundBigDecimal((line.getUnitPrice().multiply(line.getQuantity())));
        BigDecimal discountAmount = util.roundBigDecimal(grossAmount.multiply(line.getDiscountPercentage().divide(new BigDecimal(100))));
        BigDecimal netAmount = grossAmount.subtract(discountAmount);

        line.setDiscountAmount(discountAmount);
        line.setTotalCost(grossAmount.subtract(line.getDiscountAmount()));

        InventoryItemMaster item = itemBean.findItem(line.getItemId(), userData);

        if (item != null) {
            if (util.checkObjectsEqual(item.getPurchaseVatCode(), master.getVatCode())) {
                double vatPercentage = vatCodeBean.getVatPercentage(line.getVatCode(), userData);

                line.setVatAmount(util.roundBigDecimal(netAmount.multiply(new BigDecimal(vatPercentage / 100d))));
            } else {
                //Clear VAT that might have been set previously.
                line.setVatAmount(BigDecimal.ZERO);
            }

            line.setUom(item.getBaseUOM());
        }
    }

    private boolean isPosted(CreditorsCreditNoteInvoiceMaster master, CreditorsCreditNoteInvoiceLines line, EMCUserData userData) {
        if (master == null) {
            master = masterBean.getCreditNoteInvoiceMaster(line.getCreditNoteInvoiceNumber(), userData);
        }

        CreditorsCreditNoteInvoiceStatus status = CreditorsCreditNoteInvoiceStatus.fromString(master.getCreditNoteInvoiceStatus());

        if (status == CreditorsCreditNoteInvoiceStatus.POSTED) {
            return true;
        } else {
            return false;
        }
    }

    private void removeApprovalPosted(CreditorsCreditNoteInvoiceMaster master, CreditorsCreditNoteInvoiceLines line, EMCUserData userData) throws EMCEntityBeanException {
        if (master == null) {
            master = masterBean.getCreditNoteInvoiceMaster(line.getCreditNoteInvoiceNumber(), userData);
        }

        CreditorsCreditNoteInvoiceStatus status = CreditorsCreditNoteInvoiceStatus.fromString(master.getCreditNoteInvoiceStatus());

        if (status == CreditorsCreditNoteInvoiceStatus.APPROVED) {
            master.setCreditNoteInvoiceStatus(CreditorsCreditNoteInvoiceStatus.CAPTURED.toString());
            masterBean.update(master, userData);
        }
    }

    private void doInventoryTransactionPosting(CreditorsCreditNoteInvoiceMaster master, CreditorsCreditNoteInvoiceLines line, EMCUserData userData) throws EMCEntityBeanException {
        if (master == null) {
            master = masterBean.getCreditNoteInvoiceMaster(line.getCreditNoteInvoiceNumber(), userData);
        }

        if (master.isStockCreditNoteInvoice()) {
            TransactionHelper helper = null;

            switch (CreditorsCreditNoteInvoiceType.fromString(master.getCreditNoteInvoiceType())) {
                case MANUAL_CREDIT_NOTE:
                case PURCHASING_CREDIT_NOTE:
                    helper = new TransactionHelper(TransactionType.CREDITORS_POST_CREDIT_NOTE_LINE);
                    break;
                case MANUAL_INVOICE:
                case PURCHASING_INVOICE:
                    helper = new TransactionHelper(TransactionType.CREDITORS_POST_INVOICE_LINE);
                    break;
            }

            try {
                transactionBean.post(line, helper, userData);
            } catch (EMCStockException ex) {
                throw new EMCEntityBeanException(ex);
            }
        }
    }

    @Override
    public List<CreditorsCreditNoteInvoiceLines> getCreditNoteInvoiceLines(String creditNoteInvoiceNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CreditorsCreditNoteInvoiceLines.class);
        query.addAnd("creditNoteInvoiceNumber", creditNoteInvoiceNumber);
        return util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public CreditorsCreditNoteInvoiceLines getInvoiceLine(String invoiceNo, CreditorsCreditNoteInvoiceLines creditNoteLine, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CreditorsCreditNoteInvoiceLines.class);
        query.addAnd("creditNoteInvoiceNumber", invoiceNo);
        query.addAnd("lineNo", creditNoteLine.getLineNo());
        query.addAnd("itemId", creditNoteLine.getItemId());
        query.addAnd("dimension1", creditNoteLine.getDimension1());
        query.addAnd("dimension2", creditNoteLine.getDimension2());
        query.addAnd("dimension3", creditNoteLine.getDimension3());

        return (CreditorsCreditNoteInvoiceLines) util.executeSingleResultQuery(query, userData);
    }
}

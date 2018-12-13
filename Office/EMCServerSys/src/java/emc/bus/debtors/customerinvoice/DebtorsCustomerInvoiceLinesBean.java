/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.customerinvoice;

import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.bus.gl.vatcodes.GLVATCodeLocal;
import emc.bus.inventory.InventoryItemGroupLocal;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.logic.InventoryItemLogicLocal;
import emc.bus.inventory.transactions.EMCStockException;
import emc.bus.inventory.transactions.ProcessStockTransactionLocal;
import emc.bus.inventory.transactions.TransactionHelper;
import emc.bus.inventory.transactions.TransactionType;
import emc.bus.pop.POPDiscountGroupLocal;
import emc.bus.sop.pricearangements.SOPPriceArangementsLocal;
import emc.bus.sop.salesrepcommission.SOPSalesRepCommissionLocal;
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.debtors.superclasses.DebtorsInvoiceLinesSuper;
import emc.entity.debtors.superclasses.DebtorsInvoiceMasterSuper;
import emc.entity.inventory.InventoryItemGroup;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.picking.InventoryPickingListLines;
import emc.entity.pop.POPDiscountGroup;
import emc.entity.sop.SOPSalesOrderLines;
import emc.enums.debtors.DebtorsInvoiceCreditNoteType;
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
 *
 * @author riaan
 */
@Stateless
public class DebtorsCustomerInvoiceLinesBean extends EMCEntityBean implements DebtorsCustomerInvoiceLinesLocal {

    @EJB
    private ProcessStockTransactionLocal post;
    @EJB
    private DebtorsCustomerInvoiceMasterLocal masterBean;
    @EJB
    private InventoryItemMasterLocal itemBean;
    @EJB
    private SOPPriceArangementsLocal priceArrangementsBean;
    @EJB
    private GLVATCodeLocal vatCodeBean;
    @EJB
    private DebtorsParametersLocal parametersBean;
    @EJB
    private POPDiscountGroupLocal discountGroupsBean;
    @EJB
    private SOPSalesRepCommissionLocal commissionBean;
    @EJB
    private InventoryItemGroupLocal itemGroupsBean;
    @EJB
    private InventoryItemLogicLocal itemLogicBean;

    /**
     * Creates a new instance of DebtorsCustomerInvoiceLinesBean.
     */
    public DebtorsCustomerInvoiceLinesBean() {
    }

    /**
     * Returns the invoice lines belonging to the specified invoice.
     *
     * @param invoiceNo Invoice number.
     * @param userData User data.
     * @return A list of DebtorsCustomerInvoiceLines instances.
     */
    public List<DebtorsCustomerInvoiceLines> getInvoiceLines(String invoiceNo, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
        query.addAnd("invCNNumber", invoiceNo);

        return (List<DebtorsCustomerInvoiceLines>) util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCustomerInvoiceLines line = (DebtorsCustomerInvoiceLines) iobject;
        DebtorsCustomerInvoiceMaster master = masterBean.getInvoiceMaster(line.getInvCNNumber(), userData);

        if (line.getUnitPrice().compareTo(BigDecimal.ZERO) == 0) {
            line.setUnitPrice(priceArrangementsBean.findItemPrice(master.getCustomerNo(), line.getItemId(), line.getDimension1(), line.getDimension2(), line.getDimension3(), master.getInvoiceDate(), line.getQuantity(), userData));
        }

        calculateLineTotal(line, master, userData);

        //Trans id populated on insert.
        super.insert(iobject, userData);

        if (master.isInvoiceStock()
                && DebtorsInvoiceCreditNoteType.fromString(master.getInvCNType()) != DebtorsInvoiceCreditNoteType.SALES_INVOICE
                && DebtorsInvoiceCreditNoteType.fromString(master.getInvCNType()) != DebtorsInvoiceCreditNoteType.DISTRIBUTION_INVOICE) {
            if (!postStockTransaction(line, userData)) {
                throw new EMCEntityBeanException("Could not post stock transactions.");
            }
        }

        return line;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCustomerInvoiceLines line = (DebtorsCustomerInvoiceLines) uobject;

        DebtorsCustomerInvoiceLines oldLine = (DebtorsCustomerInvoiceLines) util.findDetachedPersisted(line, userData);

        if (oldLine == null || !util.checkObjectsEqual(line.getItemId(), oldLine.getItemId())
                || !util.checkObjectsEqual(line.getDimension1(), oldLine.getDimension1())
                || !util.checkObjectsEqual(line.getDimension2(), oldLine.getDimension2())
                || !util.checkObjectsEqual(line.getDimension3(), oldLine.getDimension3())
                || !util.checkObjectsEqual(line.getQuantity(), oldLine.getQuantity())) {

            //Only get new price if price is zero.
            if (line.getUnitPrice().compareTo(BigDecimal.ZERO) == 0) {
                DebtorsCustomerInvoiceMaster master = masterBean.getInvoiceMaster(line.getInvCNNumber(), userData);
                line.setUnitPrice(priceArrangementsBean.findItemPrice(master.getCustomerNo(), line.getItemId(), line.getDimension1(), line.getDimension2(), line.getDimension3(), master.getInvoiceDate(), line.getQuantity(), userData));
            }
        }

        DebtorsCustomerInvoiceMaster master = masterBean.getInvoiceMaster(line.getInvCNNumber(), userData);

        if (isBlank(line.getVatCode())) {
            line.setVatCode(master.getVatCode());
        }

        calculateLineTotal(line, master, userData);

        if (master.isInvoiceStock() && DebtorsInvoiceCreditNoteType.fromString(master.getInvCNType()) != DebtorsInvoiceCreditNoteType.SALES_INVOICE) {
            //Check whether transactions posting should be ignored.  A flag is set in position 7
            //of userData on the Debtors transaction logic bean when invoice lines are updated from.
            //a credit note return.
            if (userData.getUserData(7) != Boolean.TRUE && !postStockTransaction(line, userData)) {
                throw new EMCEntityBeanException("Could not post stock transactions.");
            }
        }

        return super.update(uobject, userData);
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean valid = super.doUpdateValidation(vobject, userData);

        if (valid) {
            DebtorsCustomerInvoiceLines line = (DebtorsCustomerInvoiceLines) vobject;
            DebtorsCustomerInvoiceMaster master = masterBean.getInvoiceMaster(line.getInvCNNumber(), userData);

            if (!itemLogicBean.validateActiveDimensions(line, userData) || !itemLogicBean.validateDimensionValues(line, userData)) {
                return false;
            }

            if (!validateItemAgainstRep(line, master, userData)) {
                return false;
            }
        }

        return valid;
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCustomerInvoiceLines line = (DebtorsCustomerInvoiceLines) dobject;

        DebtorsCustomerInvoiceMaster master = masterBean.getInvoiceMaster(line.getInvCNNumber(), userData);

        if (master.isInvoiceStock()) {
            line.setQuantity(BigDecimal.ZERO);
            if (!postStockTransaction(line, userData)
                    && DebtorsInvoiceCreditNoteType.fromString(master.getInvCNType()) != DebtorsInvoiceCreditNoteType.SALES_INVOICE
                    && DebtorsInvoiceCreditNoteType.fromString(master.getInvCNType()) != DebtorsInvoiceCreditNoteType.DISTRIBUTION_INVOICE) {
                throw new EMCEntityBeanException("Could not post stock transactions.");
            }
        }

        return super.delete(dobject, userData);
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean valid = super.doDeleteValidation(vobject, userData);

        if (valid) {
            DebtorsCustomerInvoiceLines line = (DebtorsCustomerInvoiceLines) vobject;
            DebtorsCustomerInvoiceMaster master = masterBean.getInvoiceMaster(line.getInvCNNumber(), userData);

            //Disallow update to generated Invoices. Check type on persisted, just in case it is changed on new record.
            if (DebtorsInvoiceCreditNoteType.fromString(master.getInvCNType()) == DebtorsInvoiceCreditNoteType.SALES_INVOICE) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INV_FROM_SO, userData, "Records");
                return false;
            }

            if (!checkPosted(master, userData)) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INV_POSTED, userData);
                return false;
            } else if (!checkCanceled(master, userData)) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INV_CANCELED, userData);
                return false;
            }
        }

        return valid;
    }

    /**
     * This method is used to post an Inventory transaction. It should be called
     * from insert(), update() and delete() for manual stock Invoices. Stock
     * movements for Sales Invoices will already have taken place against the
     * Sales Order.
     *
     * @param line DebtorsCustomerInvoiceLines instance to post transaction for.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    private boolean postStockTransaction(DebtorsCustomerInvoiceLines line, EMCUserData userData) throws EMCEntityBeanException {
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
            post.post(line, new TransactionHelper(TransactionType.DEBTORS_POST_INVOICE_LINE), userData);
            return true;
        } catch (EMCStockException ex) {
            ex.printStackTrace();
            throw new EMCEntityBeanException(ex);
        }
    }

    /**
     * Calculates and sets the total cost for the specified Invoice/Credit Note
     * line.
     *
     * @param line Invoice/Credit Note line to set total on.
     * @param master Invoice/Credit Note master record.
     * @param userData User data.
     */
    public void calculateLineTotal(DebtorsInvoiceLinesSuper line, DebtorsInvoiceMasterSuper master, EMCUserData userData) throws EMCEntityBeanException {
        if (line.getDiscountPercentage() == null) {
            line.setDiscountPercentage(BigDecimal.ZERO);
        }

        if (isBlank(line.getVatCode())) {
            line.setVatCode(master.getVatCode());
        }

        BigDecimal grossAmount = util.roundBigDecimal((line.getUnitPrice().multiply(line.getQuantity())));
        BigDecimal discountAmount = util.roundBigDecimal(grossAmount.multiply(line.getDiscountPercentage().divide(new BigDecimal(100))));
        BigDecimal netAmount = grossAmount.subtract(discountAmount);

        line.setDiscountAmount(discountAmount);
        line.setTotalCost(grossAmount.subtract(line.getDiscountAmount()));

        InventoryItemMaster item = itemBean.findItem(line.getItemId(), userData);

        if (item != null) {
            if (util.checkObjectsEqual(isBlank(line.getVatCode()) ? item.getSellingVatCode() : line.getVatCode(), master.getVatCode())) {
                double vatPercentage = vatCodeBean.getVatPercentage(line.getVatCode(), userData);

                line.setVatAmount(util.roundBigDecimal(netAmount.multiply(new BigDecimal(vatPercentage / 100d))));
            } else {
                //Clear VAT that might have been set previously.
                line.setVatAmount(BigDecimal.ZERO);
            }
        }
    }

    /**
     * Returns the Invoice line which corresponds to the given Credit Note line.
     *
     * @param invoiceNo Invoice number of line to fetch.
     * @param creditNoteLine Credit Note line to which Invoice line should
     * correspond.
     * @return The specified Invoice line, or null, if it does not exist.
     */
    public DebtorsCustomerInvoiceLines getInvoiceLine(String invoiceNo, DebtorsCreditNoteLines creditNoteLine, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
        query.addAnd("invCNNumber", invoiceNo);
        query.addAnd("lineNo", creditNoteLine.getLineNo());
        query.addAnd("itemId", creditNoteLine.getItemId());
//        query.addAnd("batch", creditNoteLine.getBatch());
        query.addAnd("dimension1", creditNoteLine.getDimension1());
        query.addAnd("dimension2", creditNoteLine.getDimension2());
        query.addAnd("dimension3", creditNoteLine.getDimension3());
//        query.addAnd("location", creditNoteLine.getLocation());
//        query.addAnd("pallet", creditNoteLine.getPallet());
//        query.addAnd("serial", creditNoteLine.getSerial());

        return (DebtorsCustomerInvoiceLines) util.executeSingleResultQuery(query, userData);
    }

    /**
     * Inserts a invoice line for sales orders
     *
     * @param invoiceNumber the invoice master id
     * @param pickingListLine the picking list line that causes this line
     * @param salesOrderId the sales order id
     * @param parameters Debtors parameters.
     * @param userData plain old user data
     * @return The line that has been created.
     * @throws EMCEntityBeanException
     */
    public DebtorsCustomerInvoiceLines createSOInvoiceLine(String invoiceNumber, InventoryPickingListLines pickingListLine, SOPSalesOrderLines salesOrderLine, DebtorsParameters parameters, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCustomerInvoiceLines invoiceLine = new DebtorsCustomerInvoiceLines();
        invoiceLine.setInvCNNumber(invoiceNumber);
        invoiceLine.setLineNo(pickingListLine.getLineNum());
        invoiceLine.setItemId(pickingListLine.getItemId());
        invoiceLine.setDimension1(pickingListLine.getDimension1());
        invoiceLine.setDimension2(pickingListLine.getDimension2());
        invoiceLine.setDimension3(pickingListLine.getDimension3());
        invoiceLine.setWarehouse(pickingListLine.getWarehouse());
        invoiceLine.setBatch(pickingListLine.getBatch());
        invoiceLine.setSerial(pickingListLine.getSerial());
        invoiceLine.setLocation(pickingListLine.getLocation());
        invoiceLine.setPallet(pickingListLine.getPallet());
        invoiceLine.setUom(pickingListLine.getUom());
        invoiceLine.setQuantity(new BigDecimal(pickingListLine.getIssueQty()));
        //Find Sales Order Line
        invoiceLine.setUnitPrice(salesOrderLine.getPrice());
        invoiceLine.setDiscountPercentage(salesOrderLine.getDiscountPerc());
        invoiceLine.setVatCode(salesOrderLine.getVatCode());
        //Recalc VAT on insert.  Do not pull through
        invoiceLine.setStdUnitPrice(salesOrderLine.getPrice());
        invoiceLine.setInventTransId(salesOrderLine.getInventTransId());

        //Do not require credit approval again if line has been approved on Sales Order.
        if (!parameters.isRecheckInvoice()) {
            invoiceLine.setCreditHeldStatus(salesOrderLine.getCreditHeldStatus());
        }

        return invoiceLine;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        if (ret) {
            DebtorsCustomerInvoiceLines line = (DebtorsCustomerInvoiceLines) theRecord;
            DebtorsCustomerInvoiceMaster master = masterBean.getInvoiceMaster(line.getInvCNNumber(), userData);

            if (master == null) {
                return false;
            }

            //Set on first field validation.  Do not override discount if it has already been changed.
            if (!"discountPercentage".equals(fieldNameToValidate) && line.getRecordID() == 0 && line.getDiscountPercentage().compareTo(BigDecimal.ZERO) == 0) {
                POPDiscountGroup discountGroup = discountGroupsBean.getDiscountGroup(master.getDiscountGroup(), userData);

                if (discountGroup != null) {
                    line.setDiscountPercentage(discountGroup.getDiscountPercentage());
                }
            }

            //Disallow update to generated Invoices. Check type on persisted, just in case it is changed on new record.
            if (DebtorsInvoiceCreditNoteType.fromString(master.getInvCNType()) == DebtorsInvoiceCreditNoteType.SALES_INVOICE) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INV_FROM_SO, userData, line.getDisplayLabelForField(fieldNameToValidate, userData));
                return false;
            }

            if (!checkPosted(master, userData)) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INV_POSTED, userData);
                return false;
            } else if (!checkCanceled(master, userData)) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INV_CANCELED, userData);
                return false;
            }

            //Cater for itemReference on DS as well
            if (fieldNameToValidate.contains("item") || fieldNameToValidate.contains("dimension") || fieldNameToValidate.equals("quantity")) {
                if (!validateItemAgainstRep(line, master, userData)) {
                    return false;
                }

                if (!itemLogicBean.validateDimensionValues(line, userData)) {
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

                if (item != null) {
                    line.setVatCode(item.getSellingVatCode());
                }
            }

            return line;
        }

        return ret;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean valid = super.doInsertValidation(vobject, userData);

        if (valid) {
            DebtorsCustomerInvoiceLines line = (DebtorsCustomerInvoiceLines) vobject;
            DebtorsCustomerInvoiceMaster master = masterBean.getInvoiceMaster(line.getInvCNNumber(), userData);

            DebtorsParameters parameters = parametersBean.getDebtorsParameters(userData);

            if (!itemLogicBean.validateActiveDimensions(line, userData) || !itemLogicBean.validateDimensionValues(line, userData)) {
                return false;
            }

            if (parameters == null) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.PARAM_NOT_FOUND, userData);
                return false;
            }

            if (!checkPosted(master, userData)) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INV_POSTED, userData);
                return false;
            } else if (!checkCanceled(master, userData)) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INV_CANCELED, userData);
                return false;
            }

            if (!validateItemAgainstRep(line, master, userData)) {
                return false;
            }

            if (!isBlank(parameters.getDeliveryChargeItem()) && parameters.getDeliveryChargeItem().equals(line.getItemId())) {
                if (masterBean.getDeliveryChargeLine(line.getInvCNNumber(), line.getItemId(), userData) != null) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.DEL_CHARGE_EXISTS, userData);
                    return false;
                }
            }
        }
        return valid;
    }

    /**
     * Returns the highest line number on the specified Invoice.
     *
     * @param invoiceNumber Invoice number.
     * @param userData User data.
     * @return The max line number on the specified Invoice.
     */
    public double getMaxLineNo(String invoiceNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
        query.addFieldAggregateFunction("lineNo", "MAX");
        query.addAnd("invCNNumber", invoiceNumber);

        Double max = (Double) util.executeSingleResultQuery(query, userData);

        if (max == null) {
            max = 0.0;
        }

        return max;
    }

    /**
     * Checks that lines may be added/deleted/changed based on Invoice status.
     *
     * @param master Invoice Master to check.
     * @param userData User data.
     * @return A boolean indicating whether lines may be changed.
     */
    private boolean checkPosted(DebtorsCustomerInvoiceMaster master, EMCUserData userData) {
        DebtorsInvoiceStatus status = DebtorsInvoiceStatus.fromString(master.getStatus());

        if (status == DebtorsInvoiceStatus.POSTED) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkCanceled(DebtorsCustomerInvoiceMaster master, EMCUserData userData) {
        DebtorsInvoiceStatus status = DebtorsInvoiceStatus.fromString(master.getStatus());

        if (status == DebtorsInvoiceStatus.CANCELED || status == DebtorsInvoiceStatus.DISTRIBUTION) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Validates the item on the specified line against the Invoice master
     * record for that line. If commission does not apply to the Invoice or to
     * the item on the line, this method will always return true.
     *
     * @param line Line to check
     * @param master Master record to validate against.
     * @param userData User data.
     * @return A boolean indicating whether the item on the specified line may
     * appear on the invoice.
     */
    private boolean validateItemAgainstRep(DebtorsCustomerInvoiceLines line, DebtorsCustomerInvoiceMaster master, EMCUserData userData) {
        InventoryItemGroup itemGroup = itemGroupsBean.findItemsItemGroup(line.getItemId(), userData);

        //Do not do this validation when commission is not applicable to the item on a line.
        if (master.isCommissionApplicable() && itemGroup != null && itemGroup.isCommissionApplicable()) {
            return commissionBean.validateSalesRep(master.getSalesRep(), master.getCustomerNo(), null, line.getItemId(), null, userData);
        } else {
            return true;
        }
    }

    @Override
    public boolean cancelInvoiceLine(DebtorsCustomerInvoiceLines line, EMCUserData userData) throws EMCEntityBeanException {
        InventoryItemMaster item = itemBean.findItem(line.getItemId(), userData);

        if (item == null) {
            throw new EMCEntityBeanException("Item does not exist - " + line.getItemId());
        } else {
            if (InventoryItemTypes.SERVICE.equals(InventoryItemTypes.fromString(item.getItemType()))) {
                //Do not create transactions for service items.
                return true;
            }
        }

        DebtorsCustomerInvoiceMaster master = masterBean.getInvoiceMaster(line.getInvCNNumber(), userData);

        try {
            if (master.isInvoiceStock()
                    && DebtorsInvoiceCreditNoteType.fromString(master.getInvCNType()) != DebtorsInvoiceCreditNoteType.SALES_INVOICE
                    && DebtorsInvoiceCreditNoteType.fromString(master.getInvCNType()) != DebtorsInvoiceCreditNoteType.DISTRIBUTION_INVOICE) {
                post.post(line, new TransactionHelper(TransactionType.DEBTORS_CANCEL_INVOICE_LINE), userData);
            }
            return true;
        } catch (EMCStockException ex) {
            ex.printStackTrace();
            throw new EMCEntityBeanException(ex);
        }
    }
}

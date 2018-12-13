/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.customerinvoice;

import emc.bus.creditors.settlementdiscountterms.CreditorsSettlementDiscountTermsLocal;
import emc.bus.creditors.termsofpayment.CreditorsTermsOfPaymentLocal;
import emc.bus.debtors.creditheld.DebtorsCreditHeldMasterLocal;
import emc.bus.debtors.logic.creditcheck.DebtorsCreditCheckLogicLocal;
import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.bus.debtors.transactions.logic.DebtorsTransactionLogicLocal;
import emc.bus.gl.financialperiods.GLFinancialPeriodsLocal;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.transactions.InventoryTransactionsLocal;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.bus.sop.salesrepcommission.SOPSalesRepCommissionLocal;
import emc.entity.base.BaseCompanyTable;
import emc.entity.creditors.CreditorsSettlementDiscountTerms;
import emc.entity.creditors.CreditorsTermsOfPayment;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.debtors.creditheld.DebtorsCreditHeldMaster;
import emc.entity.debtors.superclasses.DebtorsInvoiceMasterSuper;
import emc.entity.gl.GLFinancialPeriods;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.creditors.daysmonths.DaysMonths;
import emc.enums.creditors.principle.Principle;
import emc.enums.debtors.creditheld.DebtorsCreditHeldRefType;
import emc.enums.debtors.creditheld.DebtorsCreditHeldStatus;
import emc.enums.debtors.DebtorsInvoiceCreditNoteType;
import emc.enums.debtors.creditheld.DebtorsCreditHeldReason;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryItemTypes;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.enums.inventory.transactions.InventoryTransactionStatus;
import emc.enums.inventory.transactions.InventoryTransactionTypes;
import emc.enums.inventory.transactions.InventoryTransactionsRefType;
import emc.enums.sop.salesorders.SalesOrderDistributionStatus;
import emc.enums.sop.salesorders.SalesOrderStatus;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.debtors.DebtorsInvCNTotalsHelper;
import emc.messages.ServerDebtorsMessageEnum;
import emc.messages.ServerSOPMessageEnum;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class DebtorsCustomerInvoiceMasterBean extends EMCEntityBean implements DebtorsCustomerInvoiceMasterLocal {

    @EJB
    private DebtorsCustomerInvoiceLinesLocal invoiceLinesBean;
    @EJB
    private SOPCustomersLocal customerBean;
    @EJB
    private DebtorsTransactionLogicLocal transLogicBean;
    @EJB
    private CreditorsTermsOfPaymentLocal termsOfPaymentBean;
    @EJB
    private GLFinancialPeriodsLocal financialPeriodsBean;
    @EJB
    private CreditorsSettlementDiscountTermsLocal settlementDiscountTermsBean;
    @EJB
    private DebtorsCreditCheckLogicLocal creditCheckBean;
    @EJB
    private DebtorsParametersLocal parametersBean;
    @EJB
    private DebtorsCreditHeldMasterLocal creditHeldMasterBean;
    @EJB
    private InventoryItemMasterLocal itemBean;
    @EJB
    private InventoryTransactionsLocal transactionsBean;
    @EJB
    private SOPSalesRepCommissionLocal commissionBean;

    /**
     * Creates a new instance of DebtorsCustomerInvoiceMasterBean.
     */
    public DebtorsCustomerInvoiceMasterBean() {
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        if (valid) {
            DebtorsCustomerInvoiceMaster invoiceMaster = (DebtorsCustomerInvoiceMaster) theRecord;

            DebtorsCustomerInvoiceMaster persisted = (DebtorsCustomerInvoiceMaster) util.findDetachedPersisted(invoiceMaster, userData);

            if (persisted != null) {
                //These fields can be changed on posted Invoices and Sales Invoices
                List<String> editableFields = new ArrayList<String>();
                editableFields.add("customerOrderNumber");
                editableFields.add("reference");
                editableFields.add("comments");

                //Disallow update to generated Invoices. Check type on persisted, just in case it is changed on new record.
                if (DebtorsInvoiceCreditNoteType.fromString(persisted.getInvCNType()) == DebtorsInvoiceCreditNoteType.SALES_INVOICE && !editableFields.contains(fieldNameToValidate)) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INV_FROM_SO, userData, invoiceMaster.getDisplayLabelForField(fieldNameToValidate, userData));
                    return false;
                }

                if ("deliveryChargeApplicable".equals(fieldNameToValidate)) {
                    if (!invoiceMaster.isDeliveryChargeApplicable()) {
                        //Clear any delivery charge that might've been saved previously.
                        invoiceMaster.setDeliveryCharge(BigDecimal.ZERO);

                        return invoiceMaster;
                    }
                }

                if ("deliveryCharge".equals(fieldNameToValidate)) {
                    if (DebtorsInvoiceCreditNoteType.fromString(persisted.getInvCNType()) == DebtorsInvoiceCreditNoteType.SALES_INVOICE) {
                      
                    } else {
                        //Check master
                        if (!invoiceMaster.isDeliveryChargeApplicable() && invoiceMaster.getDeliveryCharge().compareTo(BigDecimal.ZERO) > 0) {
                            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.DEL_CHARGE_NOT_ALLOWED, userData);
                            return false;
                        }
                    }
                }

                if (DebtorsInvoiceStatus.fromString(persisted.getStatus()).equals(DebtorsInvoiceStatus.POSTED)
                    || DebtorsInvoiceStatus.fromString(persisted.getStatus()).equals(DebtorsInvoiceStatus.CANCELED)
                    || DebtorsInvoiceStatus.fromString(persisted.getStatus()).equals(DebtorsInvoiceStatus.DISTRIBUTION)) {
                    //These fields may be changed after an Invoice is posted.
                    editableFields.add("printedBy");
                    editableFields.add("printedDate");
                    editableFields.add("printedTime");
                    editableFields.add("lastPrintedBy");
                    editableFields.add("lastPrintedDate");
                    editableFields.add("lastPrintedTime");


                    if (editableFields.contains(fieldNameToValidate)) {
                        return true;
                    } else {
                        if (DebtorsInvoiceStatus.fromString(persisted.getStatus()).equals(DebtorsInvoiceStatus.POSTED)) {
                            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INV_POSTED, userData);
                        } else if (DebtorsInvoiceStatus.fromString(persisted.getStatus()).equals(DebtorsInvoiceStatus.CANCELED) || DebtorsInvoiceStatus.fromString(persisted.getStatus()).equals(DebtorsInvoiceStatus.DISTRIBUTION)) {
                            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INV_CANCELED, userData);
                        }
                        return false;
                    }
                }
            }

            if (fieldNameToValidate.equals("salesRep") && invoiceMaster.getRecordID() != 0 && !util.checkObjectsEqual(invoiceMaster.getSalesRep(), persisted.getSalesRep()) && !isBlank(invoiceMaster.getSalesRep())) {
                List<DebtorsCustomerInvoiceLines> lines = invoiceLinesBean.getInvoiceLines(invoiceMaster.getInvCNNumber(), userData);

                //Only validate rep when commission is applicable
                if (invoiceMaster.isCommissionApplicable()) {
                    for (DebtorsCustomerInvoiceLines line : lines) {
                        if (!commissionBean.validateSalesRep(invoiceMaster.getSalesRep(), invoiceMaster.getCustomerNo(), null, line.getItemId(), null, userData)) {
                            return false;
                        }
                    }
                }
            }

            if (fieldNameToValidate.equals("invoiceStock")) {
                //Check whether lines exist
                if (!invoiceLinesBean.getInvoiceLines(invoiceMaster.getInvCNNumber(), userData).isEmpty()) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.LINES_EXIST, userData, invoiceMaster.getDisplayLabelForField(fieldNameToValidate, userData));
                    return false;
                }
            } else {
                if (fieldNameToValidate.equals("customerNo")) {
                    populateCustomerDetails(invoiceMaster, true, userData);

                    return invoiceMaster;
                } else {
                    if (fieldNameToValidate.equals("invoiceToCustNo")) {
                        if (customerBean.validateInvoiceToCustomer(invoiceMaster.getCustomerNo(), invoiceMaster.getInvoiceToCustNo(), userData)) {
                            populateInvoiceCustomerDetails(invoiceMaster, true, userData);

                            return invoiceMaster;
                        } else {
                            return false;
                        }
                    } else {
                        if (fieldNameToValidate.equals("invoiceDate")) {
                            calculatePaymentDueDate(invoiceMaster, true, userData);
                            calculateDiscountDate(invoiceMaster, true, userData);

                            return invoiceMaster;
                        }
                    }
                }
            }
        }

        return valid;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean valid = super.doInsertValidation(vobject, userData);

        if (valid) {
            DebtorsCustomerInvoiceMaster master = (DebtorsCustomerInvoiceMaster) vobject;
            valid = valid && validateRep(master, userData);
        }
        return valid;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData)
            throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);

        if (ret) {
            DebtorsCustomerInvoiceMaster invoice = (DebtorsCustomerInvoiceMaster) vobject;
            DebtorsCustomerInvoiceMaster persisted = (DebtorsCustomerInvoiceMaster) util.findDetachedPersisted(invoice, userData);

            ret = ret && validateRep(invoice, userData);

            if (!util.checkObjectsEqual(invoice.getInvoiceToCustNo(), persisted.getInvoiceToCustNo())) {
                return ret && customerBean.validateInvoiceToCustomer(invoice.getCustomerNo(), invoice.getInvoiceToCustNo(), userData);
            }
        }

        return ret;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData)
            throws EMCEntityBeanException {
        DebtorsCustomerInvoiceMaster invoiceMaster = (DebtorsCustomerInvoiceMaster) iobject;

        populateCustomerDetails(invoiceMaster, false, userData);
        populateInvoiceCustomerDetails(invoiceMaster, false, userData);
        calculatePaymentDueDate(invoiceMaster, false, userData);
        calculateDiscountDate(invoiceMaster, true, userData);
        invoiceMaster = populateBillingCurrency(invoiceMaster, userData);

        return super.insert(iobject, userData);
    }

    private DebtorsCustomerInvoiceMaster populateBillingCurrency(DebtorsCustomerInvoiceMaster master, EMCUserData userData) {
        EMCQuery compQuery = new EMCQuery(enumQueryTypes.SELECT, BaseCompanyTable.class);
        compQuery.addAnd("companyId", userData.getCompanyId());
        BaseCompanyTable comp = (BaseCompanyTable) util.executeSingleResultQuery(compQuery, userData);
        if (comp != null) {
            master.setBillingCurrency(comp.getCurrency());
        }
        return master;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData)
            throws EMCEntityBeanException {
        DebtorsCustomerInvoiceMaster invoiceMaster = (DebtorsCustomerInvoiceMaster) uobject;
        DebtorsCustomerInvoiceMaster persisted = (DebtorsCustomerInvoiceMaster) util.findDetachedPersisted(invoiceMaster, userData);
        
        if (!util.checkObjectsEqual(invoiceMaster.getCustomerNo(), persisted.getCustomerNo())) {
            populateCustomerDetails(invoiceMaster, true, userData);
        }

        if (!util.checkObjectsEqual(invoiceMaster.getInvoiceToCustNo(), persisted.getInvoiceToCustNo())) {
            populateInvoiceCustomerDetails(invoiceMaster, true, userData);
        }

        if (!util.checkObjectsEqual(invoiceMaster.getTermsCode(), persisted.getTermsCode())) {
            calculatePaymentDueDate(invoiceMaster, true, userData);
        }

        if (!util.checkObjectsEqual(invoiceMaster.getSettlementDiscCode(), persisted.getSettlementDiscCode())) {
            calculateDiscountDate(invoiceMaster, true, userData);
        }

        if (!util.checkObjectsEqual(invoiceMaster.getCustomerNo(), persisted.getCustomerNo())) {
            processCustomerChange(invoiceMaster, userData);
        }

        //Get this before persisting master record, otherwise check on lines bean will not compare the line VAT code with the correct VAT code on the unpersisted master.
        boolean vatCodeChanged = !util.checkObjectsEqual(persisted.getVatCode(), invoiceMaster.getVatCode());

        invoiceMaster = (DebtorsCustomerInvoiceMaster) super.update(uobject, userData);

        //If VAT code changed, VAT should be recalculated and set on lines.  Only calculate this on
        if (vatCodeChanged) {
            List<DebtorsCustomerInvoiceLines> lines = invoiceLinesBean.getInvoiceLines(invoiceMaster.getInvCNNumber(), userData);
            for (DebtorsCustomerInvoiceLines line : lines) {
                line.setVatCode(invoiceMaster.getVatCode());

                invoiceLinesBean.update(line, userData);
            }
        }

        return invoiceMaster;
    }

    /**
     * Posts the specified invoice.
     *
     * @param invoiceNo Invoice number of invoice to post.
     * @param userData User data.
     * @return A boolean indicating whether the invoice was posted succesfully.
     * @throws EMCEntityBeanException
     */
    @Override
    public boolean postInvoice(String invoiceNo, EMCUserData userData)
            throws EMCEntityBeanException {
        DebtorsCustomerInvoiceMaster invoiceMaster = getInvoiceMaster(invoiceNo, userData);

        if (DebtorsInvoiceStatus.fromString(invoiceMaster.getStatus()).equals(DebtorsInvoiceStatus.POSTED)) {
            throw new EMCEntityBeanException("Invoice already posted.");
        } else if (DebtorsInvoiceStatus.fromString(invoiceMaster.getStatus()).equals(DebtorsInvoiceStatus.CANCELED)
                   || DebtorsInvoiceStatus.fromString(invoiceMaster.getStatus()).equals(DebtorsInvoiceStatus.DISTRIBUTION)) {
            throw new EMCEntityBeanException("Invoice already cancelled.");
        } else {
            if (DebtorsInvoiceStatus.HELD.equals(DebtorsInvoiceStatus.fromString(invoiceMaster.getStatus()))) {
                throw new EMCEntityBeanException("Invoice is in the " + DebtorsInvoiceStatus.HELD.toString() + " status.  Cannot post Invoice.");
            }
        }

        //Consolidated Distribution Invoice
        if (!isBlank(invoiceMaster.getDistributionNumber()) && !DebtorsInvoiceCreditNoteType.DISTRIBUTION_INVOICE.toString().equals(invoiceMaster.getInvCNType())) {
            //Check that distribution blanket order has been fully released.
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderMaster.class);
            query.addAnd("salesOrderNo", invoiceMaster.getSalesOrderNo());
            query.addAnd("salsesOrderStatus", SalesOrderStatus.INVOICED.toString(), EMCQueryConditions.NOT);
            query.addAnd("salsesOrderStatus", SalesOrderStatus.CANCELLED.toString(), EMCQueryConditions.NOT);
            if (util.exists(query, userData)) {
                throw new EMCEntityBeanException("The distribution blanket order has not been fully released yet.");
            }

            //Check that all distribution sales orders has been fully invoiced.
            query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderMaster.class);
            query.addAnd("blanketOrderRef", invoiceMaster.getSalesOrderNo());
            query.addAnd("distributionStatus", SalesOrderDistributionStatus.READY.toString());
            query.addAnd("salsesOrderStatus", SalesOrderStatus.INVOICED.toString(), EMCQueryConditions.NOT);
            query.addAnd("salsesOrderStatus", SalesOrderStatus.CANCELLED.toString(), EMCQueryConditions.NOT);
            if (util.exists(query, userData)) {
                throw new EMCEntityBeanException("There are distribution sales orders that have not been invoiced yet.");
            }
        }

        DebtorsParameters parameters = parametersBean.getDebtorsParameters(userData);

        if (parameters == null) {
            throw new EMCEntityBeanException(getMessage(ServerDebtorsMessageEnum.PARAM_NOT_FOUND, userData));
        }

        SOPCustomers cust = customerBean.findCustomer(invoiceMaster.getCustomerNo(), userData);

              if (invoiceMaster.isDeliveryChargeApplicable()) {
            if (isBlank(parameters.getDeliveryChargeItem())) {
                throw new EMCEntityBeanException(getMessage(ServerDebtorsMessageEnum.NO_DEL_CHARGE_ITEM, userData));
            }

            if (invoiceMaster.getDeliveryCharge().compareTo(BigDecimal.ZERO) == 0) {
                throw new EMCEntityBeanException(getMessage(ServerDebtorsMessageEnum.NO_DEL_CHARGE, userData));
            }

            DebtorsCustomerInvoiceLines deliveryChargeLine = getDeliveryChargeLine(invoiceNo, parameters.getDeliveryChargeItem(), userData);

            if (deliveryChargeLine == null) {
                InventoryItemMaster deliveryChargeItem = itemBean.findItem(parameters.getDeliveryChargeItem(), userData);

                if (!validateDeliveryChargeItem(deliveryChargeItem, userData)) {
                    throw new EMCEntityBeanException("Invalid delivery charge item.");
                }

                //Create delivery charge line
                deliveryChargeLine = new DebtorsCustomerInvoiceLines();
                deliveryChargeLine.setItemId(parameters.getDeliveryChargeItem());
                deliveryChargeLine.setQuantity(BigDecimal.ONE);
                deliveryChargeLine.setUom(deliveryChargeItem.getBaseUOM());
                deliveryChargeLine.setWarehouse(deliveryChargeItem.getDefaultWarehouse());
                deliveryChargeLine.setVatCode(deliveryChargeItem.getSellingVatCode());
                deliveryChargeLine.setInvCNNumber(invoiceNo);
                deliveryChargeLine.setLineNo(invoiceLinesBean.getMaxLineNo(invoiceNo, userData) + 10);
                deliveryChargeLine.setUnitPrice(invoiceMaster.getDeliveryCharge());

                invoiceLinesBean.insert(deliveryChargeLine, userData);
            } else {
                deliveryChargeLine.setQuantity(BigDecimal.ONE);
                deliveryChargeLine.setUnitPrice(invoiceMaster.getDeliveryCharge());

                invoiceLinesBean.update(deliveryChargeLine, userData);
            }
        }

        BigDecimal invoiceTotal = getInvoiceTotal(invoiceNo, parameters.isCreditCheckIncludeVAT(), userData);
        //Perform credit check against total - approved amount
        BigDecimal creditTotal = invoiceTotal.subtract(getInvoiceApprovedTotal(invoiceNo, parameters.isCreditCheckIncludeVAT(), userData));

        if (creditTotal.compareTo(invoiceMaster.getDeliveryCharge()) == 0) {
            //Everything has already been approved.  Do not check against delivery charge
            creditTotal = BigDecimal.ZERO;
        }

        DebtorsCreditHeldReason reason = null;
        //If everything has been approved already, do not check
        if (creditTotal.compareTo(BigDecimal.ZERO) == 0 || (!parameters.isCheckOnInvoicePost() || (parameters.isCheckOnInvoicePost() && (reason = creditCheckBean.allowCredit(invoiceMaster.getInvoiceToCustNo(), null, parameters, invoiceMaster.getInvoiceDate(), creditTotal, false, false, true, userData)) == null))) {
            if (transLogicBean.postDebtorsCustomerInvoice(invoiceMaster, userData)) {
                if (!isBlank(invoiceMaster.getSalesOrderNo())) {
                    //If credit check failed, Sales Order should remain in held status.
                    //Check whether an ordered trans still exists for the Sales Order and update Sales Order status.
                   
                }
            }
            return true;

        } else {
            //Create/mark credit held records.
            creditHeldMasterBean.holdCredit(invoiceNo, reason, DebtorsCreditHeldRefType.INVOICE, userData);
            return false;
        }
    }

    /**
     * Returns the total of the lines on the specified Invoice.
     *
     * @param invoiceNo Invoice number to sum on.
     * @param includeVAT Indicates whether VAT should be included in the
     * returned total.
     * @param userData User data.
     * @return The total of the lines on the specified Invoice.
     */
    @Override
    public BigDecimal getInvoiceTotal(String invoiceNo, boolean includeVAT, EMCUserData userData) {
        //Get sum of lines
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
        query.addAnd("invCNNumber", invoiceNo);
        query.addFieldAggregateFunction("totalCost", "SUM");
        query.addFieldAggregateFunction("vatAmount", "SUM");

        Object[] totals = (Object[]) util.executeSingleResultQuery(query, userData);

        if (totals == null) {
            return BigDecimal.ZERO;
        } else {
            BigDecimal lineTotal = totals[0] != null ? (BigDecimal) totals[0] : BigDecimal.ZERO;
            BigDecimal vat = totals[1] != null ? (BigDecimal) totals[1] : BigDecimal.ZERO;

            return includeVAT ? lineTotal.add(vat) : lineTotal;
        }
    }

    @Override
    public BigDecimal getInvoiceOnlyTotal(String invoiceNo, EMCUserData userData) {
        //Get sum of lines
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
        query.addFieldAggregateFunction("totalCost", "SUM");
        query.addAnd("invCNNumber", invoiceNo);

        BigDecimal total = (BigDecimal) util.executeSingleResultQuery(query, userData);

        return total == null ? new BigDecimal(0) : total;
    }

    /**
     * Gets total discount on an invoice.
     *
     * @param invoiceNo Invoice number.
     * @param userData User data.
     * @return Total discount on the specified invoice.
     */
    @Override
    public BigDecimal getDiscountTotal(String invoiceNo, EMCUserData userData) {
        //Get sum of line discounts
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
        query.addField("quantity");
        query.addField("unitPrice");
        query.addField("discountPercentage");
        query.addAnd("invCNNumber", invoiceNo);

        BigDecimal totalDisc = BigDecimal.ZERO;

        List<Object[]> values = (List<Object[]>) util.executeGeneralSelectQuery(query, userData);

        for (Object[] value : values) {
            BigDecimal quantity = (BigDecimal) value[0];
            BigDecimal unitPrice = (BigDecimal) value[1];
            BigDecimal discountPercentage = (BigDecimal) value[2];

            BigDecimal totalBeforeDisc = ((unitPrice.multiply(quantity))).multiply(discountPercentage.divide(new BigDecimal(100)));
            totalDisc = totalDisc.add(totalBeforeDisc);
        }

        return totalDisc;
    }

    @Override
    public BigDecimal getInvoiceOnlyDiscountTotal(String invoiceNo, EMCUserData userData) {
        //Get sum of line discounts
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
        query.addField("quantity");
        query.addField("unitPrice");
        query.addField("discountPercentage");
        query.addAnd("invCNNumber", invoiceNo);

        BigDecimal totalDisc = BigDecimal.ZERO;

        List<Object[]> values = (List<Object[]>) util.executeGeneralSelectQuery(query, userData);

        for (Object[] value : values) {
            BigDecimal quantity = (BigDecimal) value[0];
            BigDecimal unitPrice = (BigDecimal) value[1];
            BigDecimal discountPercentage = (BigDecimal) value[2];

            BigDecimal totalBeforeDisc = ((unitPrice.multiply(quantity))).multiply(discountPercentage.divide(new BigDecimal(100)));
            totalDisc = totalDisc.add(totalBeforeDisc);
        }

        return totalDisc;
    }

    /**
     * Returns the total of the approved credit lines on the specified Invoice.
     *
     * @param invoiceNo Invoice number to sum on.
     * @param includeVAT Indicates whether VAT should be included in the
     * returned total.
     * @param userData User data.
     * @return The total of the approved credit lines on the specified Invoice.
     */
    public BigDecimal getInvoiceApprovedTotal(String invoiceNo, boolean includeVAT, EMCUserData userData) {
        //Get sum of lines
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
        query.addFieldAggregateFunction("totalCost", "SUM");
        query.addFieldAggregateFunction("vatAmount", "SUM");
        query.addAnd("invCNNumber", invoiceNo);
        query.addAnd("creditHeldStatus", DebtorsCreditHeldStatus.APPROVED.toString());

        Object[] totals = (Object[]) util.executeSingleResultQuery(query, userData);

        if (totals == null) {
            return BigDecimal.ZERO;
        } else {
            BigDecimal lineTotal = totals[0] != null ? (BigDecimal) totals[0] : BigDecimal.ZERO;
            BigDecimal vat = totals[1] != null ? (BigDecimal) totals[1] : BigDecimal.ZERO;

            return includeVAT ? lineTotal.add(vat) : lineTotal;
        }
    }

    /**
     * Returns the total VAT of the lines on the specified Invoice.
     *
     * @param invoiceNo Invoice number to sum on.
     * @param userData User data.
     * @return The total of the lines on the specified Invoice.
     */
    @Override
    public BigDecimal getTotalVAT(String invoiceNo, EMCUserData userData) {
        //Get sum of lines
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
        query.addFieldAggregateFunction("vatAmount", "SUM");
        query.addAnd("invCNNumber", invoiceNo);

        BigDecimal total = (BigDecimal) util.executeSingleResultQuery(query, userData);

        return total == null ? new BigDecimal(0) : total;
    }

    public BigDecimal getInvoiceOnlyTotalVAT(String invoiceNo, EMCUserData userData) {
        //Get sum of lines
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
        query.addFieldAggregateFunction("vatAmount", "SUM");
        query.addAnd("invCNNumber", invoiceNo);

        BigDecimal total = (BigDecimal) util.executeSingleResultQuery(query, userData);

        return total == null ? new BigDecimal(0) : total;
    }

    /**
     * Returns the customer invoice record with the given invoice no.
     *
     * @param invoiceNo Invoice number.
     * @param userData User data.
     * @return A DebtorsCustomerInvoiceMaster instance, or null, if none is
     * found.
     */
    @Override
    public DebtorsCustomerInvoiceMaster getInvoiceMaster(String invoiceNo, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceMaster.class);
        query.addAnd("invCNNumber", invoiceNo);

        return (DebtorsCustomerInvoiceMaster) util.executeSingleResultQuery(query, userData);
    }

    /**
     * Populates customer details on the specified DebtorsCustomerInvoiceMaster
     * instance.
     *
     * @param invoiceMaster DebtorsCustomerInvoiceMaster instance to populate.
     * @param overwrite Specifies whether existing values should be overwritten.
     * @param userData User data.
     */
    private void populateCustomerDetails(DebtorsCustomerInvoiceMaster invoiceMaster, boolean overwrite, EMCUserData userData) {
        if (isBlank(invoiceMaster.getCustomerNo())) {
            return;
        }

        SOPCustomers customer = customerBean.findCustomer(invoiceMaster.getCustomerNo(), userData);

        if (isBlank(invoiceMaster.getCustomerTradingAs()) || overwrite) {
            invoiceMaster.setCustomerTradingAs(customer.getTrandingAs());
        }

        if (isBlank(invoiceMaster.getSalesArea()) || overwrite) {
            invoiceMaster.setSalesArea(customer.getSalesArea());
        }

        if (isBlank(invoiceMaster.getSalesGroup()) || overwrite) {
            invoiceMaster.setSalesGroup(customer.getSalesGroup());
        }

        if (isBlank(invoiceMaster.getSalesRegion()) || overwrite) {
            invoiceMaster.setSalesRegion(customer.getSalesRegion());
        }

        if (isBlank(invoiceMaster.getDeliveryAddress1()) || overwrite) {
            invoiceMaster.setDeliveryAddress1(customer.getPhysicalAddresLine1());
        }

        if (isBlank(invoiceMaster.getDeliveryAddress2()) || overwrite) {
            invoiceMaster.setDeliveryAddress2(customer.getPhysicalAddresLine2());
        }

        if (isBlank(invoiceMaster.getDeliveryAddress3()) || overwrite) {
            invoiceMaster.setDeliveryAddress3(customer.getPhysicalAddresLine3());
        }

        if (isBlank(invoiceMaster.getDeliveryAddress4()) || overwrite) {
            invoiceMaster.setDeliveryAddress4(customer.getPhysicalAddresLine4());
        }

        if (isBlank(invoiceMaster.getDeliveryAddress5()) || overwrite) {
            invoiceMaster.setDeliveryAddress5(customer.getPhysicalAddresLine5());
        }

        if (isBlank(invoiceMaster.getDeliveryAddressPostalCode()) || overwrite) {
            invoiceMaster.setDeliveryAddressPostalCode(customer.getPhysicalPostalCode());
        }

        if (isBlank(invoiceMaster.getOrderWarehouse()) || overwrite) {
            invoiceMaster.setOrderWarehouse(customer.getOrderWarehouse());
        }

        if (isBlank(invoiceMaster.getDeliveryTerms()) || overwrite) {
            invoiceMaster.setDeliveryTerms(customer.getDeliveryTerms());
        }

        if (isBlank(invoiceMaster.getDeliveryMethod()) || overwrite) {
            invoiceMaster.setDeliveryMethod(customer.getDeliveryMethod());
        }

        invoiceMaster.setInvoiceToCustNo(customer.getInvoiceToCustomer());

        populateInvoiceCustomerDetails(invoiceMaster, false, userData);

    }

    /**
     * Populates invoice customer details on the specified
     * DebtorsCustomerInvoiceMaster instance.
     *
     * @param invoiceMaster DebtorsCustomerInvoiceMaster instance to populate.
     * @param overwrite Specifies whether existing values should be overwritten.
     * @param userData User data.
     */
    private void populateInvoiceCustomerDetails(DebtorsCustomerInvoiceMaster invoiceMaster, boolean overwrite, EMCUserData userData) {
        if (isBlank(invoiceMaster.getInvoiceToCustNo())) {
            return;
        }

        SOPCustomers customer = customerBean.findCustomer(invoiceMaster.getInvoiceToCustNo(), userData);

        if (isBlank(invoiceMaster.getInvoiceToCustomerName()) || overwrite) {
            invoiceMaster.setInvoiceToCustomerName(customer.getCustomerName());
        }

        if (isBlank(invoiceMaster.getInvoiceToCustomerExportCurrency()) || overwrite) {
            invoiceMaster.setInvoiceToCustomerExportCurrency(customer.getExportCurrency());
        }

        if (isBlank(invoiceMaster.getInvoiceToCustomerCountryOfDestination()) || overwrite) {
            invoiceMaster.setInvoiceToCustomerCountryOfDestination(customer.getCountryOfDestination());
        }

        if (isBlank(invoiceMaster.getInvoiceToCustomerDeliveryInstructions()) || overwrite) {
            invoiceMaster.setInvoiceToCustomerDeliveryInstructions(customer.getDeliveryInstructions());
        }

        if (isBlank(invoiceMaster.getInvoiceToCustomerVatRegistration()) || overwrite) {
            invoiceMaster.setInvoiceToCustomerVatRegistration(customer.getVatRegistration());
        }

        if (isBlank(invoiceMaster.getInvoiceAddress1()) || overwrite) {
            invoiceMaster.setInvoiceAddress1(customer.getPostalAddresLine1());
        }

        if (isBlank(invoiceMaster.getInvoiceAddress2()) || overwrite) {
            invoiceMaster.setInvoiceAddress2(customer.getPostalAddresLine2());
        }

        if (isBlank(invoiceMaster.getInvoiceAddress3()) || overwrite) {
            invoiceMaster.setInvoiceAddress3(customer.getPostalAddresLine3());
        }

        if (isBlank(invoiceMaster.getInvoiceAddress4()) || overwrite) {
            invoiceMaster.setInvoiceAddress4(customer.getPostalAddresLine4());
        }

        if (isBlank(invoiceMaster.getInvoiceAddress5()) || overwrite) {
            invoiceMaster.setInvoiceAddress5(customer.getPostalAddresLine5());
        }

        if (isBlank(invoiceMaster.getInvoiceAddressPostalCode()) || overwrite) {
            invoiceMaster.setInvoiceAddressPostalCode(customer.getPostalPostalCode());
        }

        if (isBlank(invoiceMaster.getVatNo()) || overwrite) {
            invoiceMaster.setVatNo(customer.getVatRegistration());
        }

        if (isBlank(invoiceMaster.getVatCode()) || overwrite) {
            invoiceMaster.setVatCode(customer.getVatCode());
        }

        if (isBlank(invoiceMaster.getTermsCode()) || overwrite) {
            invoiceMaster.setTermsCode(customer.getTermsOfPayment());
        }

        if (isBlank(invoiceMaster.getSettlementDiscCode()) || overwrite) {
            invoiceMaster.setSettlementDiscCode(customer.getSettlementDiscount());
        }

        if (isBlank(invoiceMaster.getPricingGroup()) || overwrite) {
            invoiceMaster.setPricingGroup(customer.getPriceGroup());
        }

        if (isBlank(invoiceMaster.getDiscountGroup()) || overwrite) {
            invoiceMaster.setDiscountGroup(customer.getDiscountGroup());
        }
    }

    /**
     * Calculates the due date for the specified invoice based on the payment
     * terms on the invoice.
     */
    private boolean calculatePaymentDueDate(DebtorsCustomerInvoiceMaster invoiceMaster, boolean overwrite, EMCUserData userData) {
        if (invoiceMaster.getInvoiceDate() == null) {
            logMessage(Level.SEVERE, "Invoice date not specified", userData);
            return false;
        }

        if (isBlank(invoiceMaster.getPaymentDueDate()) || overwrite) {
            CreditorsTermsOfPayment paymentTerms = termsOfPaymentBean.getTermsOfPayment(invoiceMaster.getTermsCode(), userData);

            if (paymentTerms == null) {
                return false;
            }

            Principle principle = Principle.fromString(paymentTerms.getPrinciple());
            DaysMonths daysMonths = DaysMonths.fromString(paymentTerms.getDaysOrMonths());
            Date paymentDueDate = null;

            if (Principle.ACTUAL_DAYS.equals(principle)) {
                int days = 0;

                paymentDueDate = invoiceMaster.getInvoiceDate();

                if (daysMonths.MONTHS.equals(daysMonths)) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(paymentDueDate);
                    calendar.set(Calendar.DAY_OF_MONTH, 1);

                    for (int i = 0; i < paymentTerms.getNumberOf(); i++) {
                        //Add number of days in each month
                        days += calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                        calendar.add(Calendar.MONTH, 1);
                    }
                } else {
                    if (DaysMonths.DAYS.equals(daysMonths)) {
                        days = paymentTerms.getNumberOf();
                    }
                }

                //Add days
                Calendar cal = Calendar.getInstance();
                cal.setTime(paymentDueDate);
                cal.add(Calendar.DAY_OF_MONTH, days);

                paymentDueDate = cal.getTime();
            } else {
                //Get end of invoice period
                GLFinancialPeriods currentPeriod = financialPeriodsBean.findPeriodForDate(invoiceMaster.getInvoiceDate(), userData);

                if (currentPeriod == null) {
                    logMessage(Level.SEVERE, "No current financial period found.", userData);
                    return false;
                }

                if (daysMonths.MONTHS.equals(daysMonths)) {
                    //Get subsequent periods
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
                    query.addAnd("endDate", currentPeriod.getEndDate(), EMCQueryConditions.GREATER_THAN);
                    query.addOrderBy("startDate");

                    List<GLFinancialPeriods> periods = (List<GLFinancialPeriods>) util.executeGeneralSelectQuery(query, userData);

                    if (periods == null || periods.size() < paymentTerms.getNumberOf()) {
                        logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INSUF_PAY_PERIOD, userData);
                        return false;
                    }

                    //Get end date of period
                    paymentDueDate = periods.get(paymentTerms.getNumberOf() - 1).getEndDate();
                } else {
                    int days = paymentTerms.getNumberOf();

                    //Add days
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(currentPeriod.getEndDate());
                    cal.add(Calendar.DAY_OF_MONTH, days);

                    paymentDueDate = cal.getTime();
                }
            }
            invoiceMaster.setPaymentDueDate(paymentDueDate);
        }
        return true;
    }

    /**
     * Calculates the discount date for the specified invoice based on the
     * settlement discount code on the invoice.
     */
    private boolean calculateDiscountDate(DebtorsCustomerInvoiceMaster invoiceMaster, boolean overwrite, EMCUserData userData) {
        if (invoiceMaster.getInvoiceDate() == null) {
            logMessage(Level.SEVERE, "Invoice date not specified", userData);
            return false;
        }

        if (!isBlank(invoiceMaster.getSettlementDiscCode())) {
            CreditorsSettlementDiscountTerms discountTerms = settlementDiscountTermsBean.getSettlementDiscountTerms(invoiceMaster.getSettlementDiscCode(), userData);

            Principle principle = Principle.fromString(discountTerms.getPrinciple());
            DaysMonths daysMonths = DaysMonths.fromString(discountTerms.getDaysOrMonths());

            Date discountDate = null;

            if (Principle.ACTUAL_DAYS.equals(principle)) {
                int days = 0;

                discountDate = invoiceMaster.getInvoiceDate();

                if (daysMonths.MONTHS.equals(daysMonths)) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(discountDate);
                    calendar.set(Calendar.DAY_OF_MONTH, 1);

                    for (int i = 0; i < discountTerms.getNumberOf(); i++) {
                        //Add number of days in each month
                        days += calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                        calendar.add(Calendar.MONTH, 1);
                    }
                } else {
                    if (DaysMonths.DAYS.equals(daysMonths)) {
                        days = discountTerms.getNumberOf();
                    }
                }

                //Add days
                Calendar cal = Calendar.getInstance();
                cal.setTime(discountDate);
                cal.add(Calendar.DAY_OF_MONTH, days);

                discountDate = cal.getTime();
            } else {
                //Get end of invoice period
                GLFinancialPeriods currentPeriod = financialPeriodsBean.findPeriodForDate(invoiceMaster.getInvoiceDate(), userData);

                if (currentPeriod == null) {
                    logMessage(Level.SEVERE, "No current financial period found.", userData);
                    return false;
                }

                if (daysMonths.MONTHS.equals(daysMonths)) {
                    //Get subsequent periods
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
                    query.addAnd("endDate", currentPeriod.getEndDate(), EMCQueryConditions.GREATER_THAN);
                    query.addOrderBy("startDate");

                    List<GLFinancialPeriods> periods = (List<GLFinancialPeriods>) util.executeGeneralSelectQuery(query, userData);

                    if (periods == null || periods.size() < discountTerms.getNumberOf()) {
                        logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INSUF_PAY_PERIOD, userData);
                        return false;
                    }

                    //Get end date of period
                    discountDate = periods.get(discountTerms.getNumberOf() - 1).getEndDate();
                } else {
                    int days = discountTerms.getNumberOf();

                    //Add days
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(currentPeriod.getEndDate());
                    cal.add(Calendar.DAY_OF_MONTH, days);

                    discountDate = cal.getTime();
                }
            }
            invoiceMaster.setSettlementDiscDate(discountDate);
        }
        return true;
    }

    /**
     * Returns a helper class instance containing Invoice/Credit Note totals.
     *
     * @param invoiceNumber Invoice/Credit Note number to get totals for.
     * @param userData User data.
     * @return A DebtorsInvoiceTotalsHelper instance.
     */
    @Override
    public DebtorsInvCNTotalsHelper getInvoiceOnlyTotalHelper(String invoiceNumber, EMCUserData userData) {
        DebtorsInvCNTotalsHelper helper = new DebtorsInvCNTotalsHelper();

        helper.setDiscountTotal(getInvoiceOnlyDiscountTotal(invoiceNumber, userData));
        helper.setSalesTotal(getInvoiceOnlyTotal(invoiceNumber, userData));
        helper.setVatTotal(getInvoiceOnlyTotalVAT(invoiceNumber, userData));
        helper.setInvoiceTotal(helper.getSalesTotal().add(helper.getVatTotal()));

        return helper;
    }

    @Override
    public DebtorsInvCNTotalsHelper getInvoiceTotalHelper(String invoiceNumber, EMCUserData userData) {
        DebtorsInvCNTotalsHelper helper = new DebtorsInvCNTotalsHelper();

        helper.setDiscountTotal(getDiscountTotal(invoiceNumber, userData));
        helper.setSalesTotal(getInvoiceTotal(invoiceNumber, false, userData));
        helper.setVatTotal(getTotalVAT(invoiceNumber, userData));
        helper.setInvoiceTotal(helper.getSalesTotal().add(helper.getVatTotal()));

        return helper;
    }

    @Override
    public void invoicePrinted(String invoiceNo, DebtorsInvoiceCreditNoteType type, EMCUserData userData) throws EMCEntityBeanException {
        //Works for both Invoices and Credit Notes.
        EMCQuery query = null;

        if (type == DebtorsInvoiceCreditNoteType.MANUAL_INVOICE || type == DebtorsInvoiceCreditNoteType.SALES_INVOICE) {
            query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceMaster.class);
        } else {
            query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteMaster.class);
        }

        query.addAnd(
                "invCNNumber", invoiceNo);
        DebtorsInvoiceMasterSuper invoice = (DebtorsInvoiceMasterSuper) util.executeSingleResultQuery(query, userData);

        if (invoice != null) {
            if (isBlank(invoice.getPrintedBy())) {
                invoice.setPrintedBy(userData.getUserName());
                invoice.setPrintedDate(Functions.nowDate());
                invoice.setPrintedTime(Functions.nowDate());
            }
            invoice.setLastPrintedBy(userData.getUserName());
            invoice.setLastPrintedDate(Functions.nowDate());
            invoice.setLastPrintedTime(Functions.nowDate());
            //Disregard update validation
            super.doUpdate(invoice, userData);
        }
    }

 

    /**
     * Returns a boolean indicating whether the specified invoice has any held
     * lines.
     *
     * @param invoiceNumber Invoice number to search for.
     * @param userData User data.
     * @return A boolean indicating whether the specified invoice has any held
     * lines.
     */
    private boolean checkCreditHeld(String invoiceNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
        query.addAnd("creditHeld", true);

        return util.exists(query, userData);
    }

    /**
     * Returns a line for the delivery charge on the specified invoice contains
     * the specified item. This is used to ensure that a delivery charge item is
     * only added to an Invoice once. The specified item must only occur on the
     * invoice once.
     *
     * @param invoiceNumber Invoice number.
     * @param itemId Delivery charge item id.
     * @param userData User data.
     * @return Delivery charge line on the specified Invoice, or null, if it is
     * not found.
     */
    @Override
    public DebtorsCustomerInvoiceLines getDeliveryChargeLine(String invoiceNumber, String itemId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
        query.addAnd("itemId", itemId);
        query.addAnd("invCNNumber", invoiceNumber);

        return (DebtorsCustomerInvoiceLines) util.executeSingleResultQuery(query, userData);
    }

    /**
     * Returns a boolean indicating whether the specified delivery charge item
     * is valid.
     *
     * @param deliveryChargeItem Delivery charge item.
     * @param userData User data.
     * @return A boolean indicating whether the item is valid.
     */
    private boolean validateDeliveryChargeItem(InventoryItemMaster deliveryChargeItem, EMCUserData userData) {
        if (deliveryChargeItem == null) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.DELIVERY_CHARGE_ITEM_NOT_FOUND, userData);
            return false;
        } else {
            if (isBlank(deliveryChargeItem.getSellingVatCode())) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.NO_VAT_CODE_ON_DEL_CHARGE, userData);
                return false;
            } else {
                if (InventoryItemTypes.fromString(deliveryChargeItem.getItemType()) != InventoryItemTypes.SERVICE) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.DEL_ITEM_TYPE, userData, InventoryItemTypes.SERVICE);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Validates the sales rep field on the specified invoice.
     *
     * @param invoiceMaster Invoice to validate.
     * @param userData User data.
     * @return A boolean indicating whether the sales rep field on the specified
     * invoice has a valid value.
     */
    private boolean validateRep(DebtorsCustomerInvoiceMaster invoiceMaster, EMCUserData userData) {
        //Only validate rep when commission is applicable
        if (invoiceMaster.isCommissionApplicable()) {
            return commissionBean.validateSalesRep(invoiceMaster.getSalesRep(), invoiceMaster.getCustomerNo(), null, null, null, userData);
        } else {
            return true;
        }
    }

    /**
     * This method unapproves all credit-approved lines on the specified
     * invoice. If the invoice is subsequently posted, a credit check will be
     * done again. This method should be called when the customer is changed on
     * an invoice.
     *
     * @param invoiceMaster Invoice to be checked.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    private boolean processCustomerChange(DebtorsCustomerInvoiceMaster invoiceMaster, EMCUserData userData) throws EMCEntityBeanException {
        if (DebtorsInvoiceStatus.fromString(invoiceMaster.getStatus()) == DebtorsInvoiceStatus.HELD) {
            DebtorsCreditHeldMaster creditHeldMaster = creditHeldMasterBean.getCreditHeldMaster(invoiceMaster.getInvCNNumber(), DebtorsCreditHeldRefType.INVOICE, userData);
            if (creditHeldMaster != null) {
                creditHeldMasterBean.delete(creditHeldMaster, userData);
            }
        }

        SOPCustomers customer = customerBean.findCustomer(invoiceMaster.getCustomerNo(), userData);
        invoiceMaster.setInvoiceToCustNo(customer.getInvoiceToCustomer());

        invoiceMaster.setStatus(DebtorsInvoiceStatus.CAPTURED.toString());

        List<DebtorsCustomerInvoiceLines> invoiceLines = invoiceLinesBean.getInvoiceLines(invoiceMaster.getInvCNNumber(), userData);
        for (DebtorsCustomerInvoiceLines invoiceLine : invoiceLines) {
            //Reset credit held fields.
            invoiceLine.setCreditHeld(false);
            invoiceLine.setCreditHeldReason(null);
            invoiceLine.setCreditHeldStatus(null);

            invoiceLinesBean.update(invoiceLine, userData);
        }
        return true;
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCustomerInvoiceMaster invoiceMaster = (DebtorsCustomerInvoiceMaster) dobject;

        if (DebtorsInvoiceStatus.fromString(invoiceMaster.getStatus()) == DebtorsInvoiceStatus.HELD) {
            DebtorsCreditHeldMaster creditHeldMaster = creditHeldMasterBean.getCreditHeldMaster(invoiceMaster.getInvCNNumber(), DebtorsCreditHeldRefType.INVOICE, userData);
            if (creditHeldMaster != null) {
                creditHeldMasterBean.delete(creditHeldMaster, userData);
            }
        }

        return super.delete(dobject, userData);
    }

    @Override
    public boolean cancelInvoice(String invCNNumber, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceMaster.class);
        query.addAnd("invCNNumber", invCNNumber);
        DebtorsCustomerInvoiceMaster master = (DebtorsCustomerInvoiceMaster) util.executeSingleResultQuery(query, userData);

        if (master == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Could not find the invoice " + invCNNumber, userData);
            return false;
        }

        switch (DebtorsInvoiceStatus.fromString(master.getStatus())) {
            case CANCELED:
            case DISTRIBUTION:
                Logger.getLogger("emc").log(Level.SEVERE, "The selected invoice has already been cancelled.", userData);
                return false;
            case POSTED:
                Logger.getLogger("emc").log(Level.SEVERE, "The selected invoice has already been posted.", userData);
                return false;
            case APPROVED:
            case HELD:
            case CAPTURED:
                if(master.getInvCNType().equals(DebtorsInvoiceCreditNoteType.SALES_INVOICE.toString())){
                    Logger.getLogger("emc").log(Level.SEVERE, "Can not cancel invoices of type " + DebtorsInvoiceCreditNoteType.SALES_INVOICE.toString() + ".", userData);
                    return false;
                }
                if (master.isInvoiceStock()) {
                    query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
                    query.addAnd("invCNNumber", master.getInvCNNumber());
                    List<DebtorsCustomerInvoiceLines> linesList = util.executeGeneralSelectQuery(query, userData);
                    for (DebtorsCustomerInvoiceLines line : linesList) {
                        invoiceLinesBean.cancelInvoiceLine(line, userData);
                    }
                }
                if (isBlank(master.getDistributionNumber())) {
                    master.setStatus(DebtorsInvoiceStatus.CANCELED.toString());
                } else {
                    master.setStatus(DebtorsInvoiceStatus.DISTRIBUTION.toString());
                }
                update(master, userData);
                break;
        }
        return true;
    }
}
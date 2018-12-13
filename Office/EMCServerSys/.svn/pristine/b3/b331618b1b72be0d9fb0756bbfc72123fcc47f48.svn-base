/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.creditors.creditnoteinvoicemaster;

import emc.bus.base.BaseEmployeeLocal;
import emc.bus.creditors.creditnoteinvoicelines.CreditorsCreditNoteInvoiceLinesLocal;
import emc.bus.creditors.settlementdiscountterms.CreditorsSettlementDiscountTermsLocal;
import emc.bus.creditors.termsofpayment.CreditorsTermsOfPaymentLocal;
import emc.bus.creditors.transactions.CreditorsTransactionsLocal;
import emc.bus.gl.financialperiods.GLFinancialPeriodsLocal;
import emc.bus.inventory.transactions.EMCStockException;
import emc.bus.inventory.transactions.ProcessStockTransactionLocal;
import emc.bus.inventory.transactions.TransactionHelper;
import emc.bus.inventory.transactions.TransactionType;
import emc.entity.creditors.CreditorsApprovalGroupSetup;
import emc.entity.creditors.CreditorsApprovalGroups;
import emc.entity.creditors.CreditorsCreditNoteInvoiceLines;
import emc.entity.creditors.CreditorsCreditNoteInvoiceMaster;
import emc.entity.creditors.CreditorsSettlementDiscountTerms;
import emc.entity.creditors.CreditorsTermsOfPayment;
import emc.entity.gl.GLFinancialPeriods;
import emc.entity.pop.POPSuppliers;
import emc.enums.creditors.CreditorsCreditNoteInvoiceStatus;
import emc.enums.creditors.CreditorsCreditNoteInvoiceType;
import emc.enums.creditors.daysmonths.DaysMonths;
import emc.enums.creditors.principle.Principle;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerDebtorsMessageEnum;
import emc.tables.EMCTable;
import java.util.Calendar;
import java.util.Date;
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
public class CreditorsCreditNoteInvoiceMasterBean extends EMCEntityBean implements CreditorsCreditNoteInvoiceMasterLocal {

    @EJB
    private CreditorsTermsOfPaymentLocal termsOfPaymentBean;
    @EJB
    private GLFinancialPeriodsLocal financialPeriodsBean;
    @EJB
    private CreditorsSettlementDiscountTermsLocal settlementDiscountTermsBean;
    @EJB
    private BaseEmployeeLocal employeeBean;
    @EJB
    private CreditorsCreditNoteInvoiceLinesLocal linesBean;
    @EJB
    private ProcessStockTransactionLocal inventoryTransactionBean;
    @EJB
    private CreditorsTransactionsLocal creditorsTransactionBean;

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        if (valid) {
            CreditorsCreditNoteInvoiceMaster record = (CreditorsCreditNoteInvoiceMaster) theRecord;
            if (fieldNameToValidate.equals("supplierId")) {
                if (!isBlank(record.getSupplierId())) {
                    populateSupplierInfo(record, userData);
                    return record;
                }
            } else if (fieldNameToValidate.equals("creditNoteInvoiceDate")) {
                if (!isBlank(record.getCreditNoteInvoiceDate())) {
                    if (!isBlank(record.getTermsOfPayment())) {
                        calculatePaymentDueDate(record, userData);
                    }
                    if (!isBlank(record.getSettlementDiscount())) {
                        calculateDiscountDate(record, userData);
                    }
                    return record;
                }
            } else if (fieldNameToValidate.equals("termsOfPayment")) {
                if (!isBlank(record.getTermsOfPayment())) {
                    if (!isBlank(record.getCreditNoteInvoiceDate())) {
                        calculatePaymentDueDate(record, userData);
                    }
                    return record;
                }
            } else if (fieldNameToValidate.equals("termsOfPayment")) {
                if (!isBlank(record.getSettlementDiscount())) {
                    if (!isBlank(record.getCreditNoteInvoiceDate())) {
                        calculateDiscountDate(record, userData);
                    }
                    return record;
                }
            } else if (fieldNameToValidate.equals("stockCreditNoteInvoice")) {
                if (!isBlank(record.getCreditNoteInvoiceNumber())) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CreditorsCreditNoteInvoiceLines.class);
                    query.addAnd("creditNoteInvoiceNumber", record.getCreditNoteInvoiceNumber());
                    if (util.exists(query, userData)) {
                        Logger.getLogger("emc").log(Level.SEVERE, "STK may not be changed once the Credit Note/Invoice has lines.", userData);
                        return null;
                    }
                }
            }
        }

        return valid;
    }

    private void populateSupplierInfo(CreditorsCreditNoteInvoiceMaster record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPSuppliers.class);
        query.addAnd("supplierId", record.getSupplierId());

        POPSuppliers supplier = (POPSuppliers) util.executeSingleResultQuery(query, userData);

        if (supplier != null) {
            record.setAddressPhysicalLine1(supplier.getAddressPhysicalLine1());
            record.setAddressPhysicalLine2(supplier.getAddressPhysicalLine2());
            record.setAddressPhysicalLine3(supplier.getAddressPhysicalLine3());
            record.setAddressPhysicalLine4(supplier.getAddressPhysicalLine4());
            record.setAddressPhysicalLine5(supplier.getAddressPhysicalLine5());
            record.setAddressPhysPostalCode(supplier.getAddressPhysPostalCode());
            record.setPostalAddressLine1(supplier.getPostalAddressLine1());
            record.setPostalAddressLine2(supplier.getPostalAddressLine2());
            record.setPostalAddressLine3(supplier.getPostalAddressLine3());
            record.setPostalAddressLine4(supplier.getPostalAddressLine4());
            record.setPostalAddressLine5(supplier.getPostalAddressLine5());
            record.setPostalCode(supplier.getPostalCode());
            record.setVatCode(supplier.getVatCode());
            record.setVatRegistrationNo(supplier.getVatRegistrationNo());
            record.setVatApplicable(supplier.getVatApplicable());
            record.setPriceGroup(supplier.getPriceGroup());
            record.setDiscountGroup(supplier.getDiscountGroup());
            record.setExtraChargeGroup(supplier.getExtraChargeGroup());
            record.setTermsOfPayment(supplier.getTermsOfPayment());
            record.setSettlementDiscount(supplier.getSettlementDiscount());
            record.setSupplierBank(supplier.getBank());
            record.setSupplierBankBranchCode(supplier.getBankBranchCode());
            record.setSupplierBankAccountName(supplier.getBankAccountName());
            record.setSupplierBankAccountNumber(supplier.getBankAccountNumber());
        }

        if (!isBlank(record.getCreditNoteInvoiceDate())) {
            if (!isBlank(record.getTermsOfPayment())) {
                calculatePaymentDueDate(record, userData);
            }
            if (!isBlank(record.getSettlementDiscount())) {
                calculateDiscountDate(record, userData);
            }
        }
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        return super.doInsertValidation(vobject, userData);
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        return super.doUpdateValidation(vobject, userData);
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        return super.doDeleteValidation(vobject, userData);
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        CreditorsCreditNoteInvoiceMaster record = (CreditorsCreditNoteInvoiceMaster) iobject;
        doSave(record, userData);
        return super.insert(record, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        CreditorsCreditNoteInvoiceMaster record = (CreditorsCreditNoteInvoiceMaster) uobject;
        doSave(record, userData);
        return super.update(record, userData);
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        CreditorsCreditNoteInvoiceMaster record = (CreditorsCreditNoteInvoiceMaster) dobject;

        return super.delete(record, userData);
    }

    private void doSave(CreditorsCreditNoteInvoiceMaster creditNoteInvoice, EMCUserData userData) {
        calculatePaymentDueDate(creditNoteInvoice, userData);
        calculateDiscountDate(creditNoteInvoice, userData);
    }

    private void calculatePaymentDueDate(CreditorsCreditNoteInvoiceMaster creditNoteInvoice, EMCUserData userData) {
        if (isBlank(creditNoteInvoice.getCreditNoteInvoiceDate())) {
            logMessage(Level.SEVERE, "Invoice date not specified", userData);
        }

        if (isBlank(creditNoteInvoice.getPaymentDueDate())) {
            CreditorsTermsOfPayment paymentTerms = termsOfPaymentBean.getTermsOfPayment(creditNoteInvoice.getTermsOfPayment(), userData);

            if (paymentTerms == null) {
                return;
            }

            Principle principle = Principle.fromString(paymentTerms.getPrinciple());
            DaysMonths daysMonths = DaysMonths.fromString(paymentTerms.getDaysOrMonths());
            Date paymentDueDate = null;

            if (Principle.ACTUAL_DAYS.equals(principle)) {
                int days = 0;

                paymentDueDate = creditNoteInvoice.getCreditNoteInvoiceDate();

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
                GLFinancialPeriods currentPeriod = financialPeriodsBean.findPeriodForDate(creditNoteInvoice.getCreditNoteInvoiceDate(), userData);

                if (currentPeriod == null) {
                    logMessage(Level.SEVERE, "No current financial period found.", userData);
                }

                if (daysMonths.MONTHS.equals(daysMonths)) {
                    //Get subsequent periods
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
                    query.addAnd("endDate", currentPeriod.getEndDate(), EMCQueryConditions.GREATER_THAN);
                    query.addOrderBy("startDate");

                    List<GLFinancialPeriods> periods = (List<GLFinancialPeriods>) util.executeGeneralSelectQuery(query, userData);

                    if (periods == null || periods.size() < paymentTerms.getNumberOf()) {
                        logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INSUF_PAY_PERIOD, userData);
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
            creditNoteInvoice.setPaymentDueDate(paymentDueDate);
        }
    }

    private void calculateDiscountDate(CreditorsCreditNoteInvoiceMaster invoiceMaster, EMCUserData userData) {
        if (invoiceMaster.getCreditNoteInvoiceDate() == null) {
            logMessage(Level.SEVERE, "Invoice date not specified", userData);
        }

        if (!isBlank(invoiceMaster.getSettlementDiscount())) {
            CreditorsSettlementDiscountTerms discountTerms = settlementDiscountTermsBean.getSettlementDiscountTerms(invoiceMaster.getSettlementDiscount(), userData);

            Principle principle = Principle.fromString(discountTerms.getPrinciple());
            DaysMonths daysMonths = DaysMonths.fromString(discountTerms.getDaysOrMonths());

            Date discountDate = null;

            if (Principle.ACTUAL_DAYS.equals(principle)) {
                int days = 0;

                discountDate = invoiceMaster.getCreditNoteInvoiceDate();

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
                GLFinancialPeriods currentPeriod = financialPeriodsBean.findPeriodForDate(invoiceMaster.getCreditNoteInvoiceDate(), userData);

                if (currentPeriod == null) {
                    logMessage(Level.SEVERE, "No current financial period found.", userData);
                }

                if (daysMonths.MONTHS.equals(daysMonths)) {
                    //Get subsequent periods
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
                    query.addAnd("endDate", currentPeriod.getEndDate(), EMCQueryConditions.GREATER_THAN);
                    query.addOrderBy("startDate");

                    List<GLFinancialPeriods> periods = (List<GLFinancialPeriods>) util.executeGeneralSelectQuery(query, userData);

                    if (periods == null || periods.size() < discountTerms.getNumberOf()) {
                        logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INSUF_PAY_PERIOD, userData);
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
    }

    @Override
    public boolean approveCreditNoteInvoiceMaster(String creditNoteInvoiceNumber, EMCUserData userData) throws EMCEntityBeanException {
        CreditorsCreditNoteInvoiceMaster creditNoteInvoiceMaster = getCreditNoteInvoiceMaster(creditNoteInvoiceNumber, userData);

        if (creditNoteInvoiceMaster == null) {
            logMessage(Level.SEVERE, "Failed to find the Credit Note/Invoice Master for: " + creditNoteInvoiceNumber, userData);
            return false;
        }

        switch (CreditorsCreditNoteInvoiceStatus.fromString(creditNoteInvoiceMaster.getCreditNoteInvoiceStatus())) {
            case CAPTURED:
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CreditorsApprovalGroups.class);
                query.addTableAnd(CreditorsApprovalGroupSetup.class.getName(), "approvalGroupId", CreditorsApprovalGroups.class.getName(), "approvalGroupId");
                query.addAnd("employeeId", employeeBean.findEmployee(userData.getUserName(), userData), CreditorsApprovalGroupSetup.class.getName());
                switch (CreditorsCreditNoteInvoiceType.fromString(creditNoteInvoiceMaster.getCreditNoteInvoiceType())) {
                    case MANUAL_CREDIT_NOTE:
                    case PURCHASING_CREDIT_NOTE:
                        query.addAnd("approveCreditNote", true, CreditorsApprovalGroups.class.getName());
                        break;
                    case MANUAL_INVOICE:
                    case PURCHASING_INVOICE:
                        query.addAnd("approveInvoice", true, CreditorsApprovalGroups.class.getName());
                        break;
                }
                if (util.exists(query, userData)) {
                    creditNoteInvoiceMaster.setCreditNoteInvoiceStatus(CreditorsCreditNoteInvoiceStatus.APPROVED.toString());
                    creditNoteInvoiceMaster.setApprovedDate(Functions.nowDate());
                    creditNoteInvoiceMaster.setApprovedTime(Functions.nowDate());
                    creditNoteInvoiceMaster.setApprovedBy(userData.getUserName());
                    update(creditNoteInvoiceMaster, userData);
                    return true;
                } else {
                    logMessage(Level.SEVERE, "You do not belong to the correct approval group to approve the given order.", userData);
                    return false;
                }
            case APPROVED:
                logMessage(Level.SEVERE, "The Credit Note/Invoice has already been approved.", userData);
                return false;
            case POSTED:
                logMessage(Level.SEVERE, "The Credit Note/Invoice has already been posted.", userData);
                return false;
            default:
                return false;
        }
    }

    @Override
    public boolean postCreditNoteInvoiceMaster(String creditNoteInvoiceNumber, EMCUserData userData) throws EMCEntityBeanException {
        CreditorsCreditNoteInvoiceMaster creditNoteInvoiceMaster = getCreditNoteInvoiceMaster(creditNoteInvoiceNumber, userData);

        if (creditNoteInvoiceMaster == null) {
            logMessage(Level.SEVERE, "Failed to find the Credit Note/Invoice Master for: " + creditNoteInvoiceNumber, userData);
            return false;
        }

        switch (CreditorsCreditNoteInvoiceStatus.fromString(creditNoteInvoiceMaster.getCreditNoteInvoiceStatus())) {
            case CAPTURED:
                logMessage(Level.SEVERE, "The Credit Note/Invoice has not yet been approved.", userData);
                return false;
            case APPROVED:
                List<CreditorsCreditNoteInvoiceLines> linesList = linesBean.getCreditNoteInvoiceLines(creditNoteInvoiceNumber, userData);

                TransactionHelper helper = null;

                switch (CreditorsCreditNoteInvoiceType.fromString(creditNoteInvoiceMaster.getCreditNoteInvoiceType())) {
                    case MANUAL_CREDIT_NOTE:
                    case PURCHASING_CREDIT_NOTE:
                        if (creditNoteInvoiceMaster.isStockCreditNoteInvoice()) {
                            helper = new TransactionHelper(TransactionType.CREDITORS_POST_CREDIT_NOTE);
                            helper.setCreditorsLinesList(linesList);

                            try {
                                inventoryTransactionBean.post(creditNoteInvoiceMaster, helper, userData);
                            } catch (EMCStockException ex) {
                                throw new EMCEntityBeanException(ex);
                            }
                        }
//                        creditorsTransactionBean.postInvoice(creditNoteInvoiceMaster, linesList, userData);
                        break;
                    case MANUAL_INVOICE:
                    case PURCHASING_INVOICE:
                        if (creditNoteInvoiceMaster.isStockCreditNoteInvoice()) {
                            helper = new TransactionHelper(TransactionType.CREDITORS_POST_INVOICE);
                            helper.setCreditorsLinesList(linesList);

                            try {
                                inventoryTransactionBean.post(creditNoteInvoiceMaster, helper, userData);
                            } catch (EMCStockException ex) {
                                throw new EMCEntityBeanException(ex);
                            }
                        }
                        creditorsTransactionBean.postInvoiceCreditNote(creditNoteInvoiceMaster, linesList, userData);
                        break;
                }
                return true;
            case POSTED:
                logMessage(Level.SEVERE, "The Credit Note/Invoice has already been posted.", userData);
                return false;
            default:
                return false;
        }
    }

    public CreditorsCreditNoteInvoiceMaster getCreditNoteInvoiceMaster(String creditNoteInvoiceNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CreditorsCreditNoteInvoiceMaster.class);
        query.addAnd("creditNoteInvoiceNumber", creditNoteInvoiceNumber);
        return (CreditorsCreditNoteInvoiceMaster) util.executeSingleResultQuery(query, userData);
    }
}

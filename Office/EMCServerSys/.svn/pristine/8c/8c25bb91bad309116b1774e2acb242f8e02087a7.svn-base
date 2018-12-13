/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.transactions.logic;

import emc.bus.base.journals.BaseJournalDefinitionLocal;
import emc.bus.creditors.settlementdiscountterms.CreditorsSettlementDiscountTermsLocal;
import emc.bus.debtors.creditnotereasons.DebtorsCreditNoteReasonsLocal;
import emc.bus.debtors.creditnotes.DebtorsCreditNoteLinesLocal;
import emc.bus.debtors.creditnotes.DebtorsCreditNoteMasterLocal;
import emc.bus.debtors.customerinvoice.DebtorsCustomerInvoiceMasterLocal;
import emc.bus.debtors.journals.DebtorsJournalLinesLocal;
import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.bus.debtors.transactions.DebtorsTransactionsLocal;
import emc.bus.debtors.transactionsettlement.DebtorsSettlementDiscHistoryLocal;
import emc.bus.debtors.transactionsettlement.DebtorsTransactionSettlementHistoryLocal;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.transactions.EMCStockException;
import emc.bus.inventory.transactions.ProcessStockTransactionLocal;
import emc.bus.inventory.transactions.TransactionHelper;
import emc.bus.inventory.transactions.TransactionType;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.creditors.CreditorsSettlementDiscountTerms;
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCreditNoteReasons;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.debtors.journals.DebtorsJournalLines;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.entity.debtors.transactionsettlement.DebtorsSettlementDiscHistory;
import emc.entity.debtors.transactionsettlement.DebtorsTransactionSettlement;
import emc.entity.debtors.transactionsettlement.DebtorsTransactionSettlementHistory;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.sop.SOPCustomers;
import emc.enums.base.journals.Modules;
import emc.enums.debtors.DebtorsInvoiceCreditNoteType;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.debtors.journals.DebtorsJournalType;
import emc.enums.debtors.transactions.DebtorsTransactionRefTypes;
import emc.enums.enumQueryTypes;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerDebtorsMessageEnum;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @description : Logic bean for debtors transactions.
 *
 * @date        : 13 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsTransactionLogicBean extends EMCBusinessBean implements DebtorsTransactionLogicLocal {

    @EJB
    private DebtorsTransactionsLocal debtorsTransBean;
    @EJB
    private BaseJournalDefinitionLocal journalDefinitionBean;
    @EJB
    private DebtorsJournalLinesLocal journalLinesBean;
    @EJB
    private DebtorsCustomerInvoiceMasterLocal customerInvoiceMasterBean;
    @EJB
    private DebtorsTransactionSettlementHistoryLocal settlementHistoryBean;
    @EJB
    private DebtorsSettlementDiscHistoryLocal settlementDiscHistoryBean;
    @EJB
    private ProcessStockTransactionLocal post;
    @EJB
    private DebtorsCreditNoteMasterLocal creditNoteMasterBean;
    @EJB
    private DebtorsCreditNoteReasonsLocal creditNoteReasonsBean;
    @EJB
    private DebtorsParametersLocal parametersBean;
    @EJB
    private DebtorsCreditNoteLinesLocal creditNoteLinesBean;
    @EJB
    private InventoryItemMasterLocal itemBean;
    @EJB
    private DebtorsTransactionsLocal transactionsBean;
    @EJB
    private SOPCustomersLocal customerBean;
    @EJB
    private CreditorsSettlementDiscountTermsLocal settlementDiscTermsBean;

    /** Creates a new instance of DebtorsTransactionLogicBean */
    public DebtorsTransactionLogicBean() {
    }

    /**
     * This method posts a customer invoice.
     * @param invoiceMaster Invoice to post
     * @param userData User data
     * @return A boolean indicating whether the invoice was posted succesfully.
     * @throws EMCEntityBeanException
     */
    public boolean postDebtorsCustomerInvoice(DebtorsCustomerInvoiceMaster invoiceMaster, EMCUserData userData) throws EMCEntityBeanException {
        BigDecimal total = customerInvoiceMasterBean.getInvoiceOnlyTotal(invoiceMaster.getInvCNNumber(), userData);
        BigDecimal vat = customerInvoiceMasterBean.getInvoiceOnlyTotalVAT(invoiceMaster.getInvCNNumber(), userData);
        BigDecimal discount = customerInvoiceMasterBean.getInvoiceOnlyDiscountTotal(invoiceMaster.getInvCNNumber(), userData);

        Date now = Functions.nowDate();
        
        //N & L mod.  Set Invoice date to now date.
        invoiceMaster.setInvoiceDate(now);

        DebtorsTransactions trans = new DebtorsTransactions();

        trans.setDebit(total.add(vat));
        trans.setReferenceType(DebtorsTransactionRefTypes.INVOICE.toString());
        trans.setVatAmount(vat);
        trans.setReferenceNumber(invoiceMaster.getInvCNNumber());
        trans.setTransactionDate(invoiceMaster.getInvoiceDate());
        trans.setCustomerId(invoiceMaster.getInvoiceToCustNo());
        trans.setTransactionSource(invoiceMaster.getInvCNNumber());
        trans.setSettlementDiscDate(invoiceMaster.getSettlementDiscDate());
        trans.setPaymentDueDate(invoiceMaster.getPaymentDueDate());
        trans.setCustomerOrderNumber(invoiceMaster.getCustomerOrderNumber());
        trans.setSalesOrderNo(invoiceMaster.getSalesOrderNo());
        trans.setSalesRep(invoiceMaster.getSalesRep());

        CreditorsSettlementDiscountTerms terms = settlementDiscTermsBean.getSettlementDiscountTerms(invoiceMaster.getSettlementDiscCode(), userData);

        if (terms != null) {
            trans.setSettlementDiscPercentage(util.getBigDecimal(terms.getDiscountPercentage()));
        }

        debtorsTransBean.insert(trans, userData);

        invoiceMaster.setStatus(DebtorsInvoiceStatus.POSTED.toString());
        invoiceMaster.setPostedBy(userData.getUserName());
        invoiceMaster.setPostedDate(now);
        invoiceMaster.setPostedTime(now);

        invoiceMaster.setDiscountTotal(discount);
        invoiceMaster.setSalesTotal(total);
        invoiceMaster.setVatTotal(vat);
        invoiceMaster.setInvoiceTotal(trans.getDebit());

        customerInvoiceMasterBean.update(invoiceMaster, userData);

        boolean ret = true;

        //Do not post stock transactions for Sales Stock Invoices.  Movements will have taken place against Sales Order.
        if (invoiceMaster.isInvoiceStock() && DebtorsInvoiceCreditNoteType.fromString(invoiceMaster.getInvCNType()) != DebtorsInvoiceCreditNoteType.SALES_INVOICE) {
            try {
                post.post(invoiceMaster, new TransactionHelper(TransactionType.DEBTORS_POST_INVOICE), userData);
            } catch (EMCStockException ex) {
                throw new EMCEntityBeanException(ex);
            }
        }

        return ret;
    }

    /**
     * Posts a Debtors journal.
     *
     * @param journalMaster Journal to post.
     * @param userData User data.
     * @return A boolean indicating whether the record was succesfully posted.
     * @throws EMCEntityBeanException
     */
    public boolean postDebtorsJournal(DebtorsJournalMaster journalMaster, EMCUserData userData) throws EMCEntityBeanException {
        BaseJournalDefinitionTable defition = journalDefinitionBean.getJournalDefinition(journalMaster, Modules.DEBTORS, userData);

        DebtorsJournalType type = DebtorsJournalType.fromString(defition.getJournalType());

        List<DebtorsJournalLines> lines = journalLinesBean.getJournalLines(journalMaster.getJournalNumber(), userData);

        //First, check whether all lines fall in valid periods and have valid customers.  Either do this here, or duplicate in all posting methods.
        for (DebtorsJournalLines line : lines) {
            SOPCustomers customer = customerBean.findCustomer(line.getCustomerId(), userData);

            if (customer != null) {
                if (!util.checkObjectsEqual(customer.getCustomerId(), customer.getInvoiceToCustomer())) {
                    throw new EMCEntityBeanException(getMessage(ServerDebtorsMessageEnum.INVALID_CUSTOMER, userData, customer.getInvoiceToCustomer()));
                }
            }
        }

        switch (type) {
            case INTEREST:
                return postInterestJournal(journalMaster, lines, userData);
            case JOURNAL_CREDIT:
                return postCreditJournal(journalMaster, lines, userData);
            case JOURNAL_DEBIT:
                return postDebitJournal(journalMaster, lines, userData);
            case PAYMENT:
                return postPaymentJournal(journalMaster, lines, userData);
            case RETURNED_PAYMENTS:
                return postReturnedPaymentJournal(journalMaster, lines, userData);
            case SETTLEMENT_DISCOUNT:
                return postSettlementDiscountJournal(journalMaster, lines, userData);
            case TRANSFER:
                return postTransferJournal(journalMaster, lines, userData);

            default:
                throw new EMCEntityBeanException("Cannot post journal of type: " + type.toString());
        }
    }

    /**
     * Posts a Debtors interest journal.
     *
     * @param journalMaster Journal to post.
     * @param lines Journal lines.
     * @param userData User data.
     * @return A boolean indicating whether the record was succesfully posted.
     * @throws EMCEntityBeanException
     */
    private boolean postInterestJournal(DebtorsJournalMaster journalMaster, List<DebtorsJournalLines> journalLines, EMCUserData userData) throws EMCEntityBeanException {
        for (DebtorsJournalLines journalLine : journalLines) {
            DebtorsTransactions transaction = createTransaction(journalMaster, journalLine, userData);
            debtorsTransBean.insert(transaction, userData);
        }

        return true;
    }

    /**
     * Posts a Debtors credit journal.
     *
     * @param journalMaster Journal to post.
     * @param lines Journal lines.
     * @param userData User data.
     * @return A boolean indicating whether the record was succesfully posted.
     * @throws EMCEntityBeanException
     */
    private boolean postCreditJournal(DebtorsJournalMaster journalMaster, List<DebtorsJournalLines> journalLines, EMCUserData userData) throws EMCEntityBeanException {
        for (DebtorsJournalLines journalLine : journalLines) {
            DebtorsTransactions transaction = createTransaction(journalMaster, journalLine, userData);
            debtorsTransBean.insert(transaction, userData);
        }

        return true;
    }

    /**
     * Posts a Debtors debit journal.
     *
     * @param journalMaster Journal to post.
     * @param lines Journal lines.
     * @param userData User data.
     * @return A boolean indicating whether the record was succesfully posted.
     * @throws EMCEntityBeanException
     */
    private boolean postDebitJournal(DebtorsJournalMaster journalMaster, List<DebtorsJournalLines> journalLines, EMCUserData userData) throws EMCEntityBeanException {
        for (DebtorsJournalLines journalLine : journalLines) {
            DebtorsTransactions transaction = createTransaction(journalMaster, journalLine, userData);
            debtorsTransBean.insert(transaction, userData);
        }

        return true;
    }

    /**
     * Posts a Debtors payment journal.
     *
     * @param journalMaster Journal to post.
     * @param lines Journal lines.
     * @param userData User data.
     * @return A boolean indicating whether the record was succesfully posted.
     * @throws EMCEntityBeanException
     */
    private boolean postPaymentJournal(DebtorsJournalMaster journalMaster, List<DebtorsJournalLines> journalLines, EMCUserData userData) throws EMCEntityBeanException {
        for (DebtorsJournalLines journalLine : journalLines) {
            DebtorsTransactions transaction = createTransaction(journalMaster, journalLine, userData);
            debtorsTransBean.insert(transaction, userData);
        }

        return true;
    }

    /**
     * Posts a Debtors returned payment journal.
     *
     * @param journalMaster Journal to post.
     * @param lines Journal lines.
     * @param userData User data.
     * @return A boolean indicating whether the record was succesfully posted.
     * @throws EMCEntityBeanException
     */
    private boolean postReturnedPaymentJournal(DebtorsJournalMaster journalMaster, List<DebtorsJournalLines> journalLines, EMCUserData userData) throws EMCEntityBeanException {
        for (DebtorsJournalLines journalLine : journalLines) {
            DebtorsTransactions transaction = createTransaction(journalMaster, journalLine, userData);
            debtorsTransBean.insert(transaction, userData);
        }

        return true;
    }

    /**
     * Posts a Debtors settlement discount journal.
     *
     * @param journalMaster Journal to post.
     * @param lines Journal lines.
     * @param userData User data.
     * @return A boolean indicating whether the record was succesfully posted.
     * @throws EMCEntityBeanException
     */
    private boolean postSettlementDiscountJournal(DebtorsJournalMaster journalMaster, List<DebtorsJournalLines> journalLines, EMCUserData userData) throws EMCEntityBeanException {
        for (DebtorsJournalLines journalLine : journalLines) {
            DebtorsTransactions transaction = createTransaction(journalMaster, journalLine, userData);
            debtorsTransBean.insert(transaction, userData);
        }

        return true;
    }

    /**
     * Posts a Debtors transfer journal.
     *
     * @param journalMaster Journal to post.
     * @param lines Journal lines.
     * @param userData User data.
     * @return A boolean indicating whether the record was succesfully posted.
     * @throws EMCEntityBeanException
     */
    private boolean postTransferJournal(DebtorsJournalMaster journalMaster, List<DebtorsJournalLines> journalLines, EMCUserData userData) throws EMCEntityBeanException {
        for (DebtorsJournalLines journalLine : journalLines) {
            DebtorsTransactions transaction = createTransaction(journalMaster, journalLine, userData);
            debtorsTransBean.insert(transaction, userData);
        }

        return true;
    }

    /**
     * Creates a DebtorsTransaction instance from the given DebtorsJournalLines instance.
     * 
     * @param journalMaster Journal master record that the specified line belongs to.
     * @param journalLine Journal line to create transaction from.
     * @param userData User data.
     * @return A DebtorsTransactions instance.
     */
    private DebtorsTransactions createTransaction(DebtorsJournalMaster journalMaster, DebtorsJournalLines journalLine, EMCUserData userData) {
        DebtorsTransactions transaction = new DebtorsTransactions();

        if (journalLine.getLineTotal().compareTo(BigDecimal.ZERO) < 0) {
            //Credit
            transaction.setCredit(journalLine.getLineTotal().abs());
        } else {
            //Debit
            transaction.setDebit(journalLine.getLineTotal());
        }

        BaseJournalDefinitionTable journalDefinition = journalDefinitionBean.getJournalDefinition(journalMaster, Modules.DEBTORS, userData);

        DebtorsJournalType journalType = DebtorsJournalType.fromString(journalDefinition.getJournalType());
        DebtorsTransactionRefTypes transType;

        switch (journalType) {
            case PAYMENT:
                transType = DebtorsTransactionRefTypes.PAYMENT;
                break;
            case INTEREST:
                transType = DebtorsTransactionRefTypes.INTEREST;
                break;
            case JOURNAL_CREDIT:
                transType = DebtorsTransactionRefTypes.CREDIT_JOURNAL;
                break;
            case JOURNAL_DEBIT:
                transType = DebtorsTransactionRefTypes.DEBIT_JOURNAL;
                break;
            case RETURNED_PAYMENTS:
                transType = DebtorsTransactionRefTypes.RETURNED_PAYMENT;
                break;
            case SETTLEMENT_DISCOUNT:
                transType = DebtorsTransactionRefTypes.SETTLEMENT_DISC;
                break;
            case TRANSFER:
            //TODO:  Handle transfer journals.

            default:
                transType = null;
        }

        transaction.setDescription(journalLine.getLineDescription());
        StringBuilder refSB = new StringBuilder(journalMaster.getJournalNumber());
        refSB.append("-");
        refSB.append(journalLine.getLineRef());
        transaction.setReferenceNumber(refSB.toString());
        transaction.setReferenceType(transType.toString());
        transaction.setTransactionDate(journalLine.getLineDate());
        transaction.setTransactionSource(journalMaster.getJournalNumber());

        SOPCustomers customer = customerBean.findCustomer(journalLine.getCustomerId(), userData);

        if (customer != null) {
            transaction.setCustomerId(customer.getInvoiceToCustomer());
        } else {
            transaction.setCustomerId(journalLine.getCustomerId());
        }
        transaction.setVatAmount(journalLine.getVatAmount());

        return transaction;
    }

    /**
     * Posts a settlement.
     * @param sessionId The session id for which settlement records should be allocated.
     * @param customerId Customer id.  Should be unique per session.  This is used when creating a discount transaction.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean allocateSettlement(long sessionId, String customerId, EMCUserData userData) throws EMCEntityBeanException {
        //Totals.  Used for validation at end of allocation.
        BigDecimal debitTotal = new BigDecimal(0);
        BigDecimal creditTotal = new BigDecimal(0);
        BigDecimal discountTotal = new BigDecimal(0);
        BigDecimal rebateTotal = new BigDecimal(0);

        //Get ticked records.
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlement.class);
        query.addAnd("sessionId", sessionId);
        query.addAnd("tick", true);

        List<DebtorsTransactionSettlement> records = (List<DebtorsTransactionSettlement>) util.executeGeneralSelectQuery(query, userData);

        //Keeps track of discounts allocated to each invoice.
        //List<DebtorsSettlementDiscHistory> discHistoryList = new ArrayList<DebtorsSettlementDiscHistory>();

        //Keep track of debits and credits.
        //Each 'match' should create one history record.  Thus, there should be [number of debits] * [number of credits] history records.
        List<DebtorsTransactionSettlement> debits = new ArrayList<DebtorsTransactionSettlement>();
        List<DebtorsTransactionSettlement> credits = new ArrayList<DebtorsTransactionSettlement>();

        Date nowDate = Functions.nowDate();

        for (DebtorsTransactionSettlement settlement : records) {
            DebtorsTransactions transaction = debtorsTransBean.getTransaction(settlement.getDebtorsTransRef(), userData);
            transaction.setLastSettledDate(nowDate);

            if (transaction.getVersion() != settlement.getTransVersionNumber()) {
                throw new EMCEntityBeanException("Settlement record out of sync with transaction.  Refresh settlement.");
            }

            if (settlement.getCredit().compareTo(BigDecimal.ZERO) > 0) {
                transaction.setCreditAmountSettled(transaction.getCreditAmountSettled().add(settlement.getCreditAmountSettled()));

                creditTotal = creditTotal.add(settlement.getCreditAmountSettled());

                credits.add(settlement);
            } else if (settlement.getDebit().compareTo(BigDecimal.ZERO) > 0) {
                transaction.setDebitAmountSettled(transaction.getDebitAmountSettled().add(settlement.getDebitAmountSettled().add(settlement.getDiscTaken().add(settlement.getRebate()))));

                debitTotal = debitTotal.add(settlement.getDebitAmountSettled());
                discountTotal = discountTotal.add(settlement.getDiscTaken());

                debits.add(settlement);

                if ((settlement.getDiscTaken().add(settlement.getDebitAmountSettled().add(settlement.getRebate()))).compareTo(settlement.getDebit()) > 0) {
                    throw new EMCEntityBeanException("Amount settled + discount + rebate may not be more than original debit amount.");
                }

                if (settlement.getDiscTaken().compareTo(BigDecimal.ZERO) > 0) {
                    //Updated to support partial discounts.
                    //if (settlement.getDebitBalance().compareTo(BigDecimal.ZERO) != 0) {
                    //Discount may not be applied, unless transaction is being settled in full
                    //throw new EMCEntityBeanException("A discount may only be applied when a transaction is being settled in full.");
                    //}
                    //Create discount record
//                    DebtorsSettlementDiscHistory discHistory = new DebtorsSettlementDiscHistory();
//                    discHistory.setDiscountAmount(settlement.getDiscTaken());
//                    discHistory.setInvoiceNo(settlement.getReferenceNumber());
//                    discHistory.setTransactionDate(transaction.getTransactionDate());
//                    discHistory.setTransactionCreatedDate(transaction.getCreatedDate());
//                    discHistory.setCustomerId(transaction.getCustomerId());
//                    discHistory.setDiscountOverridden(!util.checkObjectsEqual(settlement.getDiscAvail(), settlement.getDiscTaken()));
//                    discHistory.setDiscountDate(nowDate);
//
//                    discHistoryList.add(discHistory);
                }

                rebateTotal = rebateTotal.add(settlement.getRebate());
            }

            debtorsTransBean.update(transaction, userData);
        }

        //Create history records.
        if (debits.size() == 1) {
            //Do one-to-one, or one debit to many credits.
            for (DebtorsTransactionSettlement debit : debits) {
                for (DebtorsTransactionSettlement credit : credits) {
                    DebtorsTransactionSettlementHistory history = new DebtorsTransactionSettlementHistory();
                    history.setCreditSettled(credit.getCreditAmountSettled());
                    history.setDebitSettled(credit.getCreditAmountSettled());
                    history.setCreditTransRef(credit.getDebtorsTransRef());
                    history.setDebitTransRef(debit.getDebtorsTransRef());
                    history.setCustomerId(customerId);
                    history.setCreditTransactionDate(credit.getTransactionDate());
                    history.setDebitTransactionDate(debit.getTransactionDate());
                    history.setCreditTransactionCreatedDate(credit.getCreatedDate());
                    history.setDebitTransactionCreatedDate(debit.getCreatedDate());
                    history.setSessionId(sessionId);

                    settlementHistoryBean.insert(history, userData);
                }
            }
        } else {
            //Do on credit to many debits
            //Do one-to-one, or one debit to many credits.
            for (DebtorsTransactionSettlement credit : credits) {
                for (DebtorsTransactionSettlement debit : debits) {
                    DebtorsTransactionSettlementHistory history = new DebtorsTransactionSettlementHistory();
                    history.setCreditSettled(debit.getDebitAmountSettled());
                    history.setDebitSettled(debit.getDebitAmountSettled());
                    history.setCreditTransRef(credit.getDebtorsTransRef());
                    history.setDebitTransRef(debit.getDebtorsTransRef());
                    history.setCustomerId(customerId);
                    history.setCreditTransactionDate(credit.getTransactionDate());
                    history.setDebitTransactionDate(debit.getTransactionDate());
                    history.setCreditTransactionCreatedDate(credit.getCreatedDate());
                    history.setDebitTransactionCreatedDate(debit.getCreatedDate());
                    history.setSessionId(sessionId);

                    settlementHistoryBean.insert(history, userData);
                }
            }
        }

        if (creditTotal.compareTo(debitTotal) != 0) {
            throw new EMCEntityBeanException("Settlement does not balance.  Credit total not equal to debit total.");
        }

        SOPCustomers customer = customerBean.findCustomer(customerId, userData);

        if (discountTotal.compareTo(BigDecimal.ZERO) > 0) {
            DebtorsTransactions discountTrans = new DebtorsTransactions();
            discountTrans.setCredit(discountTotal);
            discountTrans.setCreditAmountSettled(discountTotal);

            if (customer != null) {
                discountTrans.setCustomerId(customer.getInvoiceToCustomer());
            } else {
                discountTrans.setCustomerId(customerId);
            }

            discountTrans.setDescription("Settlement Discount");
            discountTrans.setReferenceType(DebtorsTransactionRefTypes.SETTLEMENT_DISC.toString());
            discountTrans.setTransactionDate(Functions.nowDate());
            //Use a dummy source, as it is a mandatory field.  Populate with correct value after insert.
            discountTrans.setTransactionSource("*");

            debtorsTransBean.insert(discountTrans, userData);

            //Do this after insert, as reference will now be available
            discountTrans.setTransactionSource(discountTrans.getReferenceNumber());

            debtorsTransBean.update(discountTrans, userData);

            //Add history record for each debit transaction discount.
            for (DebtorsTransactionSettlement debit : debits) {
                if (debit.getDiscTaken().compareTo(BigDecimal.ZERO) > 0) {
                    DebtorsTransactionSettlementHistory history = new DebtorsTransactionSettlementHistory();
                    history.setCreditSettled(debit.getDiscTaken());
                    history.setDebitSettled(debit.getDiscTaken());
                    history.setCreditTransRef(discountTrans.getRecordID());
                    history.setDebitTransRef(debit.getDebtorsTransRef());
                    history.setCustomerId(customerId);
                    history.setCreditTransactionDate(discountTrans.getTransactionDate());
                    history.setDebitTransactionDate(debit.getTransactionDate());
                    history.setCreditTransactionCreatedDate(discountTrans.getCreatedDate());
                    history.setDebitTransactionCreatedDate(debit.getCreatedDate());
                    history.setSessionId(sessionId);

                    settlementHistoryBean.insert(history, userData);
                }
            }

//            for (DebtorsSettlementDiscHistory discHistory : discHistoryList) {
//                discHistory.setDiscountTransactionRef(discountTrans.getRecordID());
//
//                settlementDiscHistoryBean.insert(discHistory, userData);
//            }
        }

        if (rebateTotal.compareTo(BigDecimal.ZERO) > 0) {
            DebtorsTransactions rebateTrans = new DebtorsTransactions();
            rebateTrans.setCredit(rebateTotal);
            rebateTrans.setCreditAmountSettled(rebateTotal);

            if (customer != null) {
                rebateTrans.setCustomerId(customer.getInvoiceToCustomer());
            } else {
                rebateTrans.setCustomerId(customerId);
            }

            rebateTrans.setDescription("Rebate");
            rebateTrans.setReferenceType(DebtorsTransactionRefTypes.REBATE.toString());
            rebateTrans.setTransactionDate(Functions.nowDate());
            //Use a dummy source, as it is a mandatory field.  Populate with correct value after insert.
            rebateTrans.setTransactionSource("*");

            debtorsTransBean.insert(rebateTrans, userData);

            //Do this after insert, as reference will now be available
            rebateTrans.setTransactionSource(rebateTrans.getReferenceNumber());

            debtorsTransBean.update(rebateTrans, userData);

            //Add history record for each debit transaction rebate.
            for (DebtorsTransactionSettlement debit : debits) {
                if (debit.getRebate().compareTo(BigDecimal.ZERO) > 0) {
                    DebtorsTransactionSettlementHistory history = new DebtorsTransactionSettlementHistory();
                    history.setCreditSettled(debit.getRebate());
                    history.setDebitSettled(debit.getRebate());
                    history.setCreditTransRef(rebateTrans.getRecordID());
                    history.setDebitTransRef(debit.getDebtorsTransRef());
                    history.setCustomerId(customerId);
                    history.setCreditTransactionDate(rebateTrans.getTransactionDate());
                    history.setDebitTransactionDate(debit.getTransactionDate());
                    history.setCreditTransactionCreatedDate(rebateTrans.getCreatedDate());
                    history.setDebitTransactionCreatedDate(debit.getCreatedDate());
                    history.setSessionId(sessionId);

                    settlementHistoryBean.insert(history, userData);
                }
            }
        }

        return true;
    }

    /**
     * This method posts a Credit Note.
     * @param creditNoteMaster Credit Note to post.
     * @param generateNewSTKNumbers Indicates whether new batch numbers should be generated
     * if the credit note is returning goods into stock.
     * @param userData User data.
     * @return A boolean indicating whether the Credit Note was posted successfully.
     * @throws EMCEntityBeanException
     */
    public boolean postDebtorsCreditNote(DebtorsCreditNoteMaster creditNoteMaster, boolean generateNewSTKNumbers, EMCUserData userData) throws EMCEntityBeanException {
        BigDecimal total = creditNoteMasterBean.getCreditNoteTotal(creditNoteMaster.getInvCNNumber(), userData);
        BigDecimal vat = creditNoteMasterBean.getCreditNoteTotalVAT(creditNoteMaster.getInvCNNumber(), userData);
        BigDecimal discount = creditNoteMasterBean.getDiscountTotal(creditNoteMaster.getInvCNNumber(), userData);

        DebtorsCreditNoteLines handlingChargeLine = createHandlingChargeLine(creditNoteMaster, total, userData);

        if (handlingChargeLine != null) {
            this.creditNoteLinesBean.insert(handlingChargeLine, userData);

            //Add handling charge to Credit Note total
            total = total.add(handlingChargeLine.getTotalCost());
        }

        Date now = Functions.nowDate();

        //N & L mod.  Set Credit Note date to now date.
        creditNoteMaster.setInvoiceDate(now);

        DebtorsTransactions trans = new DebtorsTransactions();

        trans.setCredit((total.add(vat)).abs());
        trans.setReferenceType(DebtorsTransactionRefTypes.CREDIT_NOTE.toString());
        trans.setVatAmount(vat);
        trans.setReferenceNumber(creditNoteMaster.getInvCNNumber());
        trans.setTransactionDate(creditNoteMaster.getInvoiceDate());
        trans.setCustomerId(creditNoteMaster.getInvoiceToCustNo());
        trans.setTransactionSource(creditNoteMaster.getInvCNNumber());
        trans.setSalesRep(creditNoteMaster.getSalesRep());

        debtorsTransBean.insert(trans, userData);

        creditNoteMaster.setStatus(DebtorsInvoiceStatus.POSTED.toString());
        creditNoteMaster.setPostedBy(userData.getUserName());
        creditNoteMaster.setPostedDate(now);
        creditNoteMaster.setPostedTime(now);

        creditNoteMaster.setDiscountTotal(discount);
        creditNoteMaster.setSalesTotal(total);
        creditNoteMaster.setVatTotal(vat);
        creditNoteMaster.setInvoiceTotal(trans.getCredit().multiply(new BigDecimal(-1)));
        
        creditNoteMasterBean.update(creditNoteMaster, userData);

        boolean ret = true;

        if (creditNoteMaster.isInvoiceStock()) {
            try {
                TransactionHelper txHelper = new TransactionHelper(TransactionType.DEBTORS_POST_CREDIT_NOTE);
                txHelper.setGenerateNewSTKNumbers(generateNewSTKNumbers);
                post.post(creditNoteMaster, txHelper , userData);
            } catch (EMCStockException ex) {
                throw new EMCEntityBeanException(ex);
            }
        }

        return ret;
    }

    /**
     * Creates and returns handling charge line for the specified Credit Note.
     * @param creditNote Credit Note for which a handling charge line should be created.
     * @param total Total amount for which a handling charge should be calculated.
     * @param userData User data.
     * @return The handling charge line.
     * @throws EMCEntityBeanException
     */
    private DebtorsCreditNoteLines createHandlingChargeLine(DebtorsCreditNoteMaster creditNote, BigDecimal total, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsParameters parameters = parametersBean.getDebtorsParameters(userData);

        //Check reason code for charge
        DebtorsCreditNoteReasons reason = creditNoteReasonsBean.getReason(creditNote.getReasonCode(), userData);

        if (parameters == null) {
            throw new EMCEntityBeanException("No Debtors Parameters found.  Please save Debtors Parameters.");
        } else if (!reason.isHandlingCharge()) {
            return null;
        } else if (isBlank(parameters.getHandlingChargeItem())) {
            throw new EMCEntityBeanException("No Handling Charge item found.  Please capture a Handling Charge item on Debtors Parameters.");
        }

        if (reason == null) {
            throw new EMCEntityBeanException("Reason not found.");
        }

        //Get line number for handling charge line
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteLines.class);
        query.addFieldAggregateFunction("lineNo", "MAX");
        query.addAnd("invCNNumber", creditNote.getInvCNNumber());

        Double maxLineNo = (Double) util.executeSingleResultQuery(query, userData);

        if (maxLineNo == null) {
            maxLineNo = 0d;
        }

        InventoryItemMaster handlingChargeItem = itemBean.findItem(parameters.getHandlingChargeItem(), userData);

        if (isBlank(handlingChargeItem)) {
            throw new EMCEntityBeanException("Handling Charge item does not exist.");
        }

        DebtorsCreditNoteLines handlingChargeLine = new DebtorsCreditNoteLines();
        handlingChargeLine.setItemId(parameters.getHandlingChargeItem());
        handlingChargeLine.setQuantity(BigDecimal.ONE);
        handlingChargeLine.setLineNo(maxLineNo + 10);
        handlingChargeLine.setUom(handlingChargeItem.getBaseUOM());
        handlingChargeLine.setVatCode(handlingChargeItem.getSellingVatCode());
        handlingChargeLine.setInvCNNumber(creditNote.getInvCNNumber());

        if (reason.isHandlingCharge()) {
            //Use absolute value of total.
            handlingChargeLine.setUnitPrice(new BigDecimal(parameters.getHandlingChargePercentage() / 100).multiply(total.abs()));
        }

        return handlingChargeLine;
    }

    /**
     * Deallocates the specified settlement from the given DebtorsTransactionSettlementHistory instance.
     * @param settlementHistory Settlement to deallocate.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean deallocateSettlement(DebtorsTransactionSettlementHistory settlementHistory, EMCUserData userData) throws EMCEntityBeanException {
        if (settlementHistory.isSystemAllocated()) {
            throw new EMCEntityBeanException("Allocations made by the system may not be deallocated.");
        }

        DebtorsTransactions debitTrans = transactionsBean.getTransaction(settlementHistory.getDebitTransRef(), userData);
        DebtorsTransactions creditTrans = transactionsBean.getTransaction(settlementHistory.getCreditTransRef(), userData);

        //Fall over if transactions are not found
        if (debitTrans == null || creditTrans == null) {
            throw new EMCEntityBeanException("Could not find transaction to deallocate against.");
        }

        long sessionId = System.currentTimeMillis();

        SOPCustomers customer = customerBean.findCustomer(settlementHistory.getCustomerId(), userData);

        //Settlement discount & rebate transactions should be not be updated when deallocated.
        //Create new transaction to reverse settled amount
        if (DebtorsTransactionRefTypes.SETTLEMENT_DISC.equals(DebtorsTransactionRefTypes.fromString(creditTrans.getReferenceType()))) {
            DebtorsTransactions reverseDiscountTransaction = new DebtorsTransactions();
            reverseDiscountTransaction.setDebit(settlementHistory.getCreditSettled());
            reverseDiscountTransaction.setDebitAmountSettled(reverseDiscountTransaction.getDebit());
            reverseDiscountTransaction.setDescription("Settlement Discount Reverse");

            if (customer != null) {
                reverseDiscountTransaction.setCustomerId(customer.getInvoiceToCustomer());
            } else {
                reverseDiscountTransaction.setCustomerId(settlementHistory.getCustomerId());
            }

            reverseDiscountTransaction.setReferenceType(DebtorsTransactionRefTypes.SETTLEMENT_DISC.toString());
            reverseDiscountTransaction.setTransactionDate(Functions.nowDate());
            //Use a dummy source, as it is a mandatory field.  Populate with correct value after insert.
            reverseDiscountTransaction.setTransactionSource("*");

            transactionsBean.insert(reverseDiscountTransaction, userData);

            //Do this after insert, as reference will now be available
            reverseDiscountTransaction.setTransactionSource(reverseDiscountTransaction.getReferenceNumber());

            debtorsTransBean.update(reverseDiscountTransaction, userData);

            //Create settlement history record.  This does not go through the normal allocation path.
            DebtorsTransactionSettlementHistory reverseSettlementHist = new DebtorsTransactionSettlementHistory();
            reverseSettlementHist.setCreditSettled(reverseDiscountTransaction.getDebit());
            reverseSettlementHist.setDebitSettled(reverseDiscountTransaction.getDebit());
            reverseSettlementHist.setDebitTransRef(reverseDiscountTransaction.getRecordID());
            reverseSettlementHist.setCreditTransRef(settlementHistory.getCreditTransRef());
            reverseSettlementHist.setCreditTransactionDate(settlementHistory.getCreditTransactionDate());
            reverseSettlementHist.setCreditTransactionCreatedDate(settlementHistory.getCreditTransactionCreatedDate());
            reverseSettlementHist.setDebitTransactionDate(reverseDiscountTransaction.getTransactionDate());
            reverseSettlementHist.setDebitTransactionCreatedDate(reverseDiscountTransaction.getCreatedDate());
            reverseSettlementHist.setCustomerId(settlementHistory.getCustomerId());
            reverseSettlementHist.setSessionId(sessionId);
            //May not be deallocated
            reverseSettlementHist.setSystemAllocated(true);

            settlementHistoryBean.insert(reverseSettlementHist, userData);
        } else if (DebtorsTransactionRefTypes.REBATE.equals(DebtorsTransactionRefTypes.fromString(creditTrans.getReferenceType()))) {
            DebtorsTransactions reverseRebateTransaction = new DebtorsTransactions();
            reverseRebateTransaction.setDebit(settlementHistory.getCreditSettled());
            reverseRebateTransaction.setDebitAmountSettled(reverseRebateTransaction.getDebit());
            reverseRebateTransaction.setDescription("Rebate Reverse");

            if (customer != null) {
                reverseRebateTransaction.setCustomerId(customer.getInvoiceToCustomer());
            } else {
                reverseRebateTransaction.setCustomerId(settlementHistory.getCustomerId());
            }

            reverseRebateTransaction.setReferenceType(DebtorsTransactionRefTypes.REBATE.toString());
            reverseRebateTransaction.setTransactionDate(Functions.nowDate());
            //Source is mandatory.  Use 'dummy' value.
            reverseRebateTransaction.setTransactionSource("*");

            transactionsBean.insert(reverseRebateTransaction, userData);

            //Do this after insert, as reference will now be available
            reverseRebateTransaction.setTransactionSource(reverseRebateTransaction.getReferenceNumber());

            debtorsTransBean.update(reverseRebateTransaction, userData);

            //Create settlement history record.  This does not go through the normal allocation path.
            DebtorsTransactionSettlementHistory reverseSettlementHist = new DebtorsTransactionSettlementHistory();
            reverseSettlementHist.setCreditSettled(reverseRebateTransaction.getDebit());
            reverseSettlementHist.setDebitSettled(reverseRebateTransaction.getDebit());
            reverseSettlementHist.setDebitTransRef(reverseRebateTransaction.getRecordID());
            reverseSettlementHist.setCreditTransRef(settlementHistory.getCreditTransRef());
            reverseSettlementHist.setDebitTransactionDate(reverseRebateTransaction.getTransactionDate());
            reverseSettlementHist.setDebitTransactionCreatedDate(reverseRebateTransaction.getCreatedDate());
            reverseSettlementHist.setCreditTransactionCreatedDate(settlementHistory.getCreditTransactionCreatedDate());
            reverseSettlementHist.setCustomerId(settlementHistory.getCustomerId());
            reverseSettlementHist.setSessionId(sessionId);
            //May not be deallocated
            reverseSettlementHist.setSystemAllocated(true);

            settlementHistoryBean.insert(reverseSettlementHist, userData);
        } else {
            creditTrans.setCreditAmountSettled(creditTrans.getCreditAmountSettled().subtract(settlementHistory.getCreditSettled()));

            transactionsBean.update(creditTrans, userData);
        }

        debitTrans.setDebitAmountSettled(debitTrans.getDebitAmountSettled().subtract(settlementHistory.getDebitSettled()));

        transactionsBean.update(debitTrans, userData);

        settlementHistoryBean.delete(settlementHistory, userData);

        if (DebtorsTransactionRefTypes.SETTLEMENT_DISC.equals(DebtorsTransactionRefTypes.fromString(creditTrans.getReferenceType()))) {
            DebtorsSettlementDiscHistory discHistory = settlementDiscHistoryBean.findSettlementDiscHistory(creditTrans.getRecordID(), debitTrans.getReferenceNumber(), userData);

            if (discHistory != null) {
                settlementDiscHistoryBean.delete(discHistory, userData);
            }
        }

        return true;
    }

    /**
     * Deallocates the specified rebate or settlement discount from the given DebtorsTransactionSettlementHistory instance.
     * @param settlementHistory Settlement to deallocate.
     * @param reverseTransaction The amount on this transaction will be updated when deallocating the specified settlement.
     * @param userData User data.
     * @return A the transaction that was created.  
     * @throws EMCEntityBeanException
     */
    public DebtorsTransactions deallocateSettlement(DebtorsTransactionSettlementHistory settlementHistory, DebtorsTransactions reverseTransaction, EMCUserData userData) throws EMCEntityBeanException {
        if (settlementHistory.isSystemAllocated()) {
            throw new EMCEntityBeanException("Allocations made by the system may not be deallocated.");
        }

        DebtorsTransactions debitTrans = transactionsBean.getTransaction(settlementHistory.getDebitTransRef(), userData);
        DebtorsTransactions creditTrans = transactionsBean.getTransaction(settlementHistory.getCreditTransRef(), userData);

        SOPCustomers customer = customerBean.findCustomer(settlementHistory.getCustomerId(), userData);

        if (customer == null) {
            throw new EMCEntityBeanException("Customer not found");
        }

        //Fall over if transactions are not found
        if (debitTrans == null || creditTrans == null) {
            throw new EMCEntityBeanException("Could not find transaction to deallocate against.");
        }

        boolean newTrans = false;

        if (reverseTransaction == null) {
            newTrans = true;

            reverseTransaction = new DebtorsTransactions();
            reverseTransaction.setTransactionDate(Functions.nowDate());
            reverseTransaction.setCustomerId(customer.getCustomerId());

            DebtorsTransactionRefTypes refType = DebtorsTransactionRefTypes.fromString(creditTrans.getReferenceType());

            if (refType == DebtorsTransactionRefTypes.SETTLEMENT_DISC) {
                reverseTransaction.setDescription("Settlement Discount Reverse");
                reverseTransaction.setReferenceType(DebtorsTransactionRefTypes.SETTLEMENT_DISC.toString());
            } else if (refType == DebtorsTransactionRefTypes.REBATE) {
                reverseTransaction.setDescription("Rebate Reverse");
                reverseTransaction.setReferenceType(DebtorsTransactionRefTypes.REBATE.toString());
            }

            //Use a dummy source, as it is a mandatory field.  Populate with correct value after insert.
            reverseTransaction.setTransactionSource("*");

            transactionsBean.insert(reverseTransaction, userData);

            //Do this after insert, as reference will now be available
            reverseTransaction.setTransactionSource(reverseTransaction.getReferenceNumber());

            debtorsTransBean.update(reverseTransaction, userData);
        }

        reverseTransaction.setDebit(reverseTransaction.getDebit().add(settlementHistory.getCreditSettled()));
        reverseTransaction.setDebitAmountSettled(reverseTransaction.getDebitAmountSettled().add(settlementHistory.getCreditSettled()));

        long sessionId = System.currentTimeMillis();

        //Settlement discount & rebate transactions should be not be updated when deallocated.
        //Create new transaction to reverse settled amount
        if (DebtorsTransactionRefTypes.SETTLEMENT_DISC.equals(DebtorsTransactionRefTypes.fromString(creditTrans.getReferenceType()))) {
            //Create settlement history record.  This does not go through the normal allocation path.
            DebtorsTransactionSettlementHistory reverseSettlementHist = new DebtorsTransactionSettlementHistory();
            reverseSettlementHist.setCreditSettled(settlementHistory.getDebitSettled());
            reverseSettlementHist.setDebitSettled(settlementHistory.getDebitSettled());
            reverseSettlementHist.setDebitTransRef(reverseTransaction.getRecordID());
            reverseSettlementHist.setCreditTransRef(settlementHistory.getCreditTransRef());
            reverseSettlementHist.setCreditTransactionDate(settlementHistory.getCreditTransactionDate());
            reverseSettlementHist.setCreditTransactionCreatedDate(settlementHistory.getCreditTransactionCreatedDate());
            reverseSettlementHist.setDebitTransactionDate(reverseTransaction.getTransactionDate());
            reverseSettlementHist.setDebitTransactionCreatedDate(reverseTransaction.getCreatedDate());
            reverseSettlementHist.setCustomerId(settlementHistory.getCustomerId());
            reverseSettlementHist.setSessionId(sessionId);
            //May not be deallocated
            reverseSettlementHist.setSystemAllocated(true);

            settlementHistoryBean.insert(reverseSettlementHist, userData);
        } else if (DebtorsTransactionRefTypes.REBATE.equals(DebtorsTransactionRefTypes.fromString(creditTrans.getReferenceType()))) {
            //Create settlement history record.  This does not go through the normal allocation path.
            DebtorsTransactionSettlementHistory reverseSettlementHist = new DebtorsTransactionSettlementHistory();
            reverseSettlementHist.setCreditSettled(settlementHistory.getDebitSettled());
            reverseSettlementHist.setDebitSettled(settlementHistory.getDebitSettled());
            reverseSettlementHist.setDebitTransRef(reverseTransaction.getRecordID());
            reverseSettlementHist.setCreditTransRef(settlementHistory.getCreditTransRef());
            reverseSettlementHist.setDebitTransactionDate(reverseTransaction.getTransactionDate());
            reverseSettlementHist.setDebitTransactionCreatedDate(reverseTransaction.getCreatedDate());
            reverseSettlementHist.setCreditTransactionCreatedDate(settlementHistory.getCreditTransactionCreatedDate());
            reverseSettlementHist.setCustomerId(settlementHistory.getCustomerId());
            reverseSettlementHist.setSessionId(sessionId);
            //May not be deallocated
            reverseSettlementHist.setSystemAllocated(true);

            settlementHistoryBean.insert(reverseSettlementHist, userData);
        } else {
            creditTrans.setCreditAmountSettled(creditTrans.getCreditAmountSettled().subtract(settlementHistory.getCreditSettled()));

            transactionsBean.update(creditTrans, userData);
        }

        debitTrans.setDebitAmountSettled(debitTrans.getDebitAmountSettled().subtract(settlementHistory.getDebitSettled()));

        transactionsBean.update(debitTrans, userData);

        settlementHistoryBean.delete(settlementHistory, userData);

        ///if (DebtorsTransactionRefTypes.SETTLEMENT_DISC.equals(DebtorsTransactionRefTypes.fromString(creditTrans.getReferenceType()))) {
        //    DebtorsSettlementDiscHistory discHistory = settlementDiscHistoryBean.findSettlementDiscHistory(creditTrans.getRecordID(), debitTrans.getReferenceNumber(), userData);

        //    if (discHistory != null) {
        //        settlementDiscHistoryBean.delete(discHistory, userData);
        //    }
        //}

        if (!newTrans) {
            transactionsBean.update(reverseTransaction, userData);
        }

        return reverseTransaction;
    }
}

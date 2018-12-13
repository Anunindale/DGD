/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.transactions;

import emc.bus.creditors.settlementdiscountterms.CreditorsSettlementDiscountTermsLocal;
import emc.bus.creditors.termsofpayment.CreditorsTermsOfPaymentLocal;
import emc.bus.debtors.opentransactions.DebtorsOpenTransactionsLocal;
import emc.bus.gl.financialperiods.GLFinancialPeriodsLocal;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.entity.creditors.CreditorsSettlementDiscountTerms;
import emc.entity.creditors.CreditorsTermsOfPayment;
import emc.entity.debtors.DebtorsOpenTransactions;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.gl.GLFinancialPeriods;
import emc.entity.sop.SOPCustomers;
import emc.enums.creditors.daysmonths.DaysMonths;
import emc.enums.creditors.principle.Principle;
import emc.enums.debtors.transactions.DebtorsTransactionRefTypes;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.gl.GLAccountStatus;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerDebtorsMessageEnum;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class DebtorsTransactionsBean extends EMCEntityBean implements DebtorsTransactionsLocal {

    //This is used to prevent transactions from being posted into a closed period.  Use a Map
    private static Map<String, Date> closedDateMap = Collections.synchronizedMap(new HashMap<String, Date>());
    @EJB
    private DebtorsOpenTransactionsLocal openTransBean;
    @EJB
    private SOPCustomersLocal customerBean;
    @EJB
    private CreditorsTermsOfPaymentLocal termsBean;
    @EJB
    private CreditorsSettlementDiscountTermsLocal discountTermsBean;
    @EJB
    private GLFinancialPeriodsLocal financialPeriodsBean;

    /** Creates a new instance of DebtorsTransactionsBean. */
    public DebtorsTransactionsBean() {
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsTransactions trans = (DebtorsTransactions) iobject;

        calculateBalance(trans);

        //Insert first, to get record id.
        super.insert(iobject, userData);

        //Insert open transaction
        syncOpenTransaction(trans, userData);

        return iobject;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsTransactions trans = (DebtorsTransactions) uobject;

        calculateBalance(trans);

        //Update open transaction
        syncOpenTransaction(trans, userData);

        return super.update(uobject, userData);
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsTransactions trans = (DebtorsTransactions) dobject;

        //Delete open transaction
        deleteOpenTransaction(trans, userData);

        return super.delete(dobject, userData);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);

        //Indicates whether transactions are being imported from progress.  Set on ProgressOpenTransactionsImportBean.
        boolean imported = userData.getUserData(2) instanceof Boolean ? (Boolean) userData.getUserData(2) : false;

        //For imported transactions, ignore validations.
        if (!imported) {
            ret = ret && validateTransactionDateAgainstPeriod((DebtorsTransactions) vobject, userData) && checkCustomerNotClosed((DebtorsTransactions) vobject, userData);
        }

        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        return super.doUpdateValidation(vobject, userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (ret) {
            DebtorsTransactions trans = (DebtorsTransactions) theRecord;
            boolean returnRec = false;
            if (trans.getDebit() == null) {
                trans.setDebit(new BigDecimal(0));
                returnRec = true;
            }
            if (trans.getCredit() == null) {
                trans.setCredit(new BigDecimal(0));
                returnRec = true;
            }
            if (returnRec) {
                return trans;
            }
        }
        return ret;
    }

    public BigDecimal calculateReferenceBallance(String refNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactions.class);
        query.addAnd("referenceNumber", refNumber);
        query.addFieldAggregateFunction("debit", "SUM");
        query.addFieldAggregateFunction("credit", "SUM");
        Object[] queryResult = (Object[]) util.executeSingleResultQuery(query, userData);

        return ((BigDecimal) queryResult[0]).subtract((BigDecimal) queryResult[1]);
    }

    public BigDecimal calculateTotalBallance(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactions.class);
        query.addFieldAggregateFunction("debit", "SUM");
        query.addFieldAggregateFunction("credit", "SUM");
        Object[] queryResult = (Object[]) util.executeSingleResultQuery(query, userData);
        return ((BigDecimal) queryResult[0]).subtract((BigDecimal) queryResult[1]);
    }

    /**
     * Ensures that an open transaction record exists which is in sync with the specified transaction.
     * If an open transaction exists, it will be updated; if not, it will be created.  A transaction should be
     * passed to this method after being inserted/updated, as the record ID is used to link the transaction
     * to the open transaction record/
     * @param trans Transaction to sync.
     * @param userData User data.
     * @throws EMCEntityBeanException
     */
    private boolean syncOpenTransaction(DebtorsTransactions trans, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsOpenTransactions openTrans = openTransBean.getOpenTransaction(trans, userData);

        if (openTrans == null) {
            if ((trans.getCredit().compareTo(BigDecimal.ZERO) != 0 && trans.getCreditAmountSettled().compareTo(trans.getCredit()) == 0) ||
                    (trans.getDebit().compareTo(BigDecimal.ZERO) != 0 && trans.getDebitAmountSettled().compareTo(trans.getDebit()) == 0)) {
                //Do not save open transactions, probably a discount transaction which is settled immediately.
                return true;
            }
            //Create new trans
            openTrans = new DebtorsOpenTransactions();
            openTrans.setReferenceNumber(trans.getReferenceNumber());
            openTrans.setReferenceType(trans.getReferenceType());
            openTrans.setReferenceNumber(trans.getReferenceNumber());
            openTrans.setCustomerId(trans.getCustomerId());
            openTrans.setDebtorsTransRef(trans.getRecordID());
            openTrans.setTransactionDate(trans.getTransactionDate());
        }

        //This should be set, regardless of whether the transaction is new or not.
        openTrans.setDebit(trans.getDebit());
        openTrans.setCredit(trans.getCredit());
        openTrans.setDebitAmountSettled(trans.getDebitAmountSettled());
        openTrans.setCreditAmountSettled(trans.getCreditAmountSettled());
        openTrans.setBalance(trans.getBalance());

        if (openTrans.getRecordID() == 0) {
            openTransBean.insert(openTrans, userData);
        } else {
            if ((trans.getCredit().compareTo(BigDecimal.ZERO) != 0 && trans.getCreditAmountSettled().compareTo(trans.getCredit()) == 0) ||
                    (trans.getDebit().compareTo(BigDecimal.ZERO) != 0 && trans.getDebitAmountSettled().compareTo(trans.getDebit()) == 0)) {
                //Fully settled, delete open trans
                deleteOpenTransaction(trans, userData);
            } else {
                openTransBean.update(openTrans, userData);
            }
        }

        return true;
    }

    /**
     * Deletes the debtors open transactions corresponding to the specified transaction.
     * @param trans Transaction.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean deleteOpenTransaction(DebtorsTransactions trans, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsOpenTransactions openTrans = openTransBean.getOpenTransaction(trans, userData);

        if (openTrans != null) {
            openTransBean.delete(openTrans, userData);
        }

        return true;
    }

    /**
     * Selects and returns the transaction with the specified record ID.
     * @param transRecordID Transaction record ID.
     * @param userData User data.
     * @return A DebtorsTransaction instance, or null, if none is found.
     */
    public DebtorsTransactions getTransaction(long transRecordID, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactions.class);
        query.addAnd("recordID", transRecordID);

        return (DebtorsTransactions) util.executeSingleResultQuery(query, userData);
    }

    /** Calculates and sets debit and credit balances on the given transaction. */
    private void calculateBalance(DebtorsTransactions trans) {
        if (trans.getDebit().compareTo(BigDecimal.ZERO) > 0) {
            trans.setBalance(trans.getDebit().subtract(trans.getDebitAmountSettled()));
        } else {
            //Credit balance should be negative.
            trans.setBalance((trans.getCredit().subtract(trans.getCreditAmountSettled())).multiply(new BigDecimal(-1)));
        }
    }

    /**
     * Sets the last closed date for the company identified by the specified userData instance.
     * @param closedDate Closed date.
     * @param userData User data.
     */
    public void setLastClosedDate(Date closedDate, EMCUserData userData) {
        closedDateMap.put(userData.getCompanyId(), closedDate);
    }

    /**
     * Loads the last closed date for the company identified by the specified userData instance.
     * @param userData User data.
     */
    public void loadLastClosedDate(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
        query.addFieldAggregateFunction("endDate", "MAX");
        query.addAnd("companyId", userData.getCompanyId());
        query.addAnd("accountStatus", GLAccountStatus.CLOSED.toString());

        closedDateMap.put(userData.getCompanyId(), (Date) util.executeSingleResultQuery(query, userData));
    }

    /**
     * Checks that the specified transaction does not fall in a closed period.
     * @param transaction Transaction to validate.
     * @param usersData User data.
     * @return A boolean indicating whether the transaction may be persisted.
     */
    private boolean validateTransactionDateAgainstPeriod(DebtorsTransactions transaction, EMCUserData userData) {
        if (!closedDateMap.containsKey(userData.getCompanyId())) {
            //Load data
            loadLastClosedDate(userData);
        }

        Date lastClosedDate = closedDateMap.get(userData.getCompanyId());

        if (lastClosedDate == null) {
            //No closed periods.  Allow persist.
            return true;
        } else {
            if (lastClosedDate.compareTo(transaction.getTransactionDate()) >= 0) {
                //Period closed.  Do not allow persist.
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.CLOSED_PERIOD, userData, transaction.getTransactionDate());
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * This method checks that credit has not been stopped for the customer on the specified transaction.
     * @param trans Transaction to check.
     * @param userData User data.
     * @return A boolean indicating whether this transaction may be inserted for the customer on the specified transaction.
     */
    private boolean checkCustomerNotClosed(DebtorsTransactions trans, EMCUserData userData) {
        //Only check debit transactions
        if (trans.getCredit().compareTo(BigDecimal.ZERO) > 0) {
            return true;
        }

        SOPCustomers customer = customerBean.findCustomer(trans.getCustomerId(), userData);

        if (customer == null) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.CUST_NOT_FOUND, userData, trans.getCustomerId());
            return false;
        } else if (customer.isCreditStoped()) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.CUST_STOPPED, userData, customer.getCustomerId());
            return false;
        } else {
            return true;
        }
    }

    /** This is a temporary method used to calculate due dates on DEBIT transactions without due dates. */
    public boolean calculateDatesAndDisc(EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactions.class);
        query.addAnd("referenceType", DebtorsTransactionRefTypes.INVOICE);
        query.openAndConditionBracket();
        query.addAnd("paymentDueDate", null, EMCQueryConditions.IS_NULL);
        query.addOr("settlementDiscDate", null, EMCQueryConditions.IS_NULL);
        query.closeConditionBracket();
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        
        //Only use transactions for the last couple of months.  This is temporary
        query.addAnd("transactionDate", cal.getTime(), EMCQueryConditions.GREATER_THAN_EQ);

        //Sort by customers to process one customer at a time.
        query.addOrderBy("customerId");

        List<DebtorsTransactions> transactions = (List<DebtorsTransactions>) util.executeGeneralSelectQuery(query, userData);

        SOPCustomers customer = null;
        CreditorsTermsOfPayment terms = null;
        CreditorsSettlementDiscountTerms discTerms = null;

        for (DebtorsTransactions transaction : transactions) {
            if (customer == null || !util.checkObjectsEqual(customer.getCustomerId(), transaction.getCustomerId())) {
                customer = customerBean.findCustomer(transaction.getCustomerId(), userData);
                terms = termsBean.getTermsOfPayment(customer.getTermsOfPayment(), userData);
                discTerms = discountTermsBean.getSettlementDiscountTerms(customer.getSettlementDiscount(), userData);
            }

            if (Functions.checkBlank(transaction.getPaymentDueDate())) {
                if (terms == null) {
                    transaction.setPaymentDueDate(transaction.getTransactionDate());
                } else {
                    transaction.setPaymentDueDate(calculatePaymentDueDate(terms, transaction.getTransactionDate(), userData));
                }
            }

            if (discTerms != null && isBlank(transaction.getSettlementDiscDate())) {
                calculateDiscountInfo(transaction, discTerms, userData);
            }

            this.update(transaction, userData);
        }
        return true;
    }

    /** Calculates payment due date for the specified transaction, based on customer terms. */
    private Date calculatePaymentDueDate(CreditorsTermsOfPayment terms, Date transactionDate, EMCUserData userData) {
        Principle principle = Principle.fromString(terms.getPrinciple());
        DaysMonths daysMonths = DaysMonths.fromString(terms.getDaysOrMonths());
        Date paymentDueDate = null;

        if (Principle.ACTUAL_DAYS.equals(principle)) {
            int days = 0;

            paymentDueDate = transactionDate;

            if (daysMonths.MONTHS.equals(daysMonths)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(paymentDueDate);
                calendar.set(Calendar.DAY_OF_MONTH, 1);

                for (int i = 0; i < terms.getNumberOf(); i++) {
                    //Add number of days in each month
                    days += calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                    calendar.add(Calendar.MONTH, 1);
                }
            } else {
                if (DaysMonths.DAYS.equals(daysMonths)) {
                    days = terms.getNumberOf();
                }
            }

            //Add days
            Calendar cal = Calendar.getInstance();
            cal.setTime(paymentDueDate);
            cal.add(Calendar.DAY_OF_MONTH, days);

            paymentDueDate = cal.getTime();
        } else {
            //Get end of invoice period
            GLFinancialPeriods currentPeriod = financialPeriodsBean.findPeriodForDate(transactionDate, userData);

            if (currentPeriod == null) {
                logMessage(Level.SEVERE, "No current financial period found.", userData);
                return transactionDate;
            }

            if (daysMonths.MONTHS.equals(daysMonths)) {
                //Get subsequent periods
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
                query.addAnd("endDate", currentPeriod.getEndDate(), EMCQueryConditions.GREATER_THAN);
                query.addOrderBy("startDate");

                List<GLFinancialPeriods> periods = (List<GLFinancialPeriods>) util.executeGeneralSelectQuery(query, userData);

                if (periods == null || periods.size() < terms.getNumberOf()) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INSUF_PAY_PERIOD, userData);
                    return transactionDate;
                }

                //Get end date of period
                paymentDueDate = periods.get(terms.getNumberOf() - 1).getEndDate();
            } else {
                int days = terms.getNumberOf();

                //Add days
                Calendar cal = Calendar.getInstance();
                cal.setTime(currentPeriod.getEndDate());
                cal.add(Calendar.DAY_OF_MONTH, days);

                paymentDueDate = cal.getTime();
            }
        }

        return paymentDueDate;
    }

    /**
     * Calculate discount available and discount date and sets values on transaction.
     */
    private void calculateDiscountInfo(DebtorsTransactions transaction, CreditorsSettlementDiscountTerms discountTerms, EMCUserData userData) {
        if (discountTerms == null) {
            return;
        }

        //The following was copied from the customer invoice master bean and modified
        Principle principle = Principle.fromString(discountTerms.getPrinciple());
        DaysMonths daysMonths = DaysMonths.fromString(discountTerms.getDaysOrMonths());

        Date discountDate = null;

        if (Principle.ACTUAL_DAYS.equals(principle)) {
            int days = 0;

            discountDate = transaction.getTransactionDate();

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
            GLFinancialPeriods currentPeriod = financialPeriodsBean.findPeriodForDate(transaction.getTransactionDate(), userData);

            if (currentPeriod == null) {
                logMessage(Level.SEVERE, "No current financial period found.", userData);
                return;
            }

            if (daysMonths.MONTHS.equals(daysMonths)) {
                //Get subsequent periods
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
                query.addAnd("endDate", currentPeriod.getEndDate(), EMCQueryConditions.GREATER_THAN);
                query.addOrderBy("startDate");

                List<GLFinancialPeriods> periods = (List<GLFinancialPeriods>) util.executeGeneralSelectQuery(query, userData);

                if (periods == null || periods.size() < discountTerms.getNumberOf()) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INSUF_PAY_PERIOD, userData);
                    return;
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
        transaction.setSettlementDiscDate(discountDate);
        transaction.setSettlementDiscPercentage(util.getBigDecimal(discountTerms.getDiscountPercentage()));
    }
}

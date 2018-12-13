/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.logic.customerbalance;

import emc.bus.debtors.logic.aging.DebtorsAgingLocal;
import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.entity.debtors.DebtorsOpenTransactions;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.debtors.DebtorsPostDatedPayment;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.entity.sop.SOPCustomers;
import emc.enums.debtors.parameters.DebtorsAgingMode;
import emc.enums.debtors.parameters.DebtorsAgingPeriod;
import emc.enums.debtors.transactions.DebtorsTransactionRefTypes;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.debtors.DebtorsCustomerBalanceHelper;
import emc.messages.ServerDebtorsMessageEnum;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @description : Business bean for the CustomerBalance form.
 *
 * @date : 21 Jun 2010
 *
 * @author : Riaan Nel
 *
 * @version : 1.0
 */
@Stateless
public class DebtorsCustomerBalanceBean extends EMCBusinessBean implements DebtorsCustomerBalanceLocal {

    @EJB
    private DebtorsAgingLocal agingBean;
    @EJB
    private DebtorsParametersLocal parametersBean;

    /**
     * Creates a new instance of DebtorsCustomerBalanceBean
     */
    public DebtorsCustomerBalanceBean() {
    }

    /**
     * Returns a DebtorsCustomerBalanceHelper instance containing information
     * about the specified customer.
     *
     * @param customerId Customer id.
     * @param userData User data.
     * @return A DebtorsCustomerBalanceHelper instance containing information
     * about the specified customer.
     */
    public DebtorsCustomerBalanceHelper getCustomerBalance(String customerId, EMCUserData userData) {
        Date atDate = Functions.nowDate();

        DebtorsCustomerBalanceHelper helper = new DebtorsCustomerBalanceHelper();

        //Use period and mode from Debtors Parameters.
        DebtorsParameters parameters = parametersBean.getDebtorsParameters(userData);

        if (parameters == null) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.PARAM_NOT_FOUND, userData);
            throw new NullPointerException("No Debtors parameters found.");
        }

        DebtorsAgingMode mode = DebtorsAgingMode.fromString(parameters.getDebtorsAgingMode());
        DebtorsAgingPeriod period = DebtorsAgingPeriod.fromString(parameters.getDebtorsAgingPeriod());

        EMCQuery currentBalanceQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsOpenTransactions.class);
        //EMCQuery does not support arithmetic in queries.  Select total and settled amounts and subtract in Java.
        currentBalanceQuery.addFieldAggregateFunction("debit", "SUM");
        currentBalanceQuery.addFieldAggregateFunction("debitAmountSettled", "SUM");
        currentBalanceQuery.addFieldAggregateFunction("credit", "SUM");
        currentBalanceQuery.addFieldAggregateFunction("creditAmountSettled", "SUM");
        currentBalanceQuery.addAnd("customerId", customerId);
        //Exclude future transactions.
        currentBalanceQuery.addAnd("transactionDate", atDate, EMCQueryConditions.LESS_THAN_EQ);

        Object[] balanceArray = (Object[]) util.executeSingleResultQuery(currentBalanceQuery, userData);

        BigDecimal totalDebit = balanceArray[0] != null ? (BigDecimal) balanceArray[0] : BigDecimal.ZERO;
        BigDecimal totalDebitSettled = balanceArray[1] != null ? (BigDecimal) balanceArray[1] : BigDecimal.ZERO;
        BigDecimal totalCredit = balanceArray[2] != null ? (BigDecimal) balanceArray[2] : BigDecimal.ZERO;
        BigDecimal totalCreditSettled = balanceArray[3] != null ? (BigDecimal) balanceArray[3] : BigDecimal.ZERO;

        BigDecimal outstandingDebit = totalDebit.subtract(totalDebitSettled);
        BigDecimal outstandingCredit = totalCredit.subtract(totalCreditSettled);

        BigDecimal customerBalance = outstandingDebit.subtract(outstandingCredit);

        helper.setCurrentBalance(customerBalance);

        if (mode == DebtorsAgingMode.NONE) {
            //For modes other than NONE, credit will already have been allocated in aging.
            helper.setUnallocatedCredits(outstandingCredit.multiply(new BigDecimal(-1)));
        }

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsPostDatedPayment.class);
        query.addFieldAggregateFunction("paymentAmount", "SUM");
        query.addAnd("customer", customerId);
        query.addAnd("processed", false);

        BigDecimal postDatedPaymentTotal = (BigDecimal) util.executeSingleResultQuery(query, userData);

        if (postDatedPaymentTotal == null) {
            postDatedPaymentTotal = BigDecimal.ZERO;
        }
        
        helper.setPostDatedPaymentTotal(postDatedPaymentTotal);
        // calculation of Credits
//        EMCQuery sopCustQuery = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
//        sopCustQuery.addAnd("customerId", customerId);
//        SOPCustomers customer = (SOPCustomers) util.executeSingleResultQuery(sopCustQuery, userData);
//
//        EMCQuery creditQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactions.class);
//        creditQuery.addTableAnd(DebtorsJournalMaster.class.getName(), "transactionSource", DebtorsTransactions.class.getName(), "journalNumber");
//        creditQuery.addFieldAggregateFunction("debit", DebtorsTransactions.class.getName(), "SUM");
//        creditQuery.addFieldAggregateFunction("credit", DebtorsTransactions.class.getName(), "SUM");
//        creditQuery.addAnd("customerId", customer.getInvoiceToCustomer());
//        creditQuery.openConditionBracket(EMCQueryBracketConditions.AND);
//        creditQuery.addOr("referenceType", DebtorsTransactionRefTypes.DEBIT_JOURNAL.toString(), EMCQueryConditions.EQUALS);
//        creditQuery.addOr("referenceType", DebtorsTransactionRefTypes.CREDIT_JOURNAL.toString(), EMCQueryConditions.EQUALS);
//        creditQuery.closeConditionBracket();
//        creditQuery.openConditionBracket(EMCQueryBracketConditions.AND);
//        creditQuery.addOr("journalDefinitionId", parameters.getCreditJournalDef(), DebtorsJournalMaster.class.getName(), EMCQueryConditions.EQUALS);
//        creditQuery.addOr("journalDefinitionId", parameters.getDebitJournalDef(), DebtorsJournalMaster.class.getName(), EMCQueryConditions.EQUALS);
//        creditQuery.closeConditionBracket();
//
//
//
//        List<Object[]> credAmounts = util.executeGeneralSelectQuery(creditQuery, userData);
//        BigDecimal balance = BigDecimal.ZERO;
//        BigDecimal debit = BigDecimal.ZERO;
//        BigDecimal credi = BigDecimal.ZERO;
//        BigDecimal creditAmt = BigDecimal.ZERO;
//        if (credAmounts == null) {
//            balance = BigDecimal.ZERO;
//        }
//        for (Object[] cred : credAmounts) {
//            if (cred.length > 0) {
//                credi = (BigDecimal) cred[1];
//                debit = (BigDecimal) cred[0];
//                if (credi == null) {
//                    credi = BigDecimal.ZERO;
//                }
//                if (debit == null) {
//                    debit = BigDecimal.ZERO;
//                }
//                creditAmt = credi.subtract(debit);
//
//                balance = creditAmt.round(new MathContext(4, RoundingMode.HALF_UP));
//            }
//        }
        
        
             
        helper.setCreditsBalance(getCreditsCustomerBalance(customerId, userData));
 
        helper.setAgingList(agingBean.getDebtorsAging(customerId, atDate, mode, period, parameters, userData));

        return helper;
    }
    
    // Calculate Credits
    @Override
     public BigDecimal getCreditsCustomerBalance(String customerId, EMCUserData userData) {
        Date atDate = Functions.nowDate();
        DebtorsParameters parameters = parametersBean.getDebtorsParameters(userData);
        if (parameters == null) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.PARAM_NOT_FOUND, userData);
            throw new NullPointerException("No Debtors parameters found.");
        }
        
        EMCQuery currentBalanceQuer = new EMCQuery(enumQueryTypes.SELECT, DebtorsOpenTransactions.class);
        currentBalanceQuer.addTableAnd(DebtorsTransactions.class.getName(), "debtorsTransRef", DebtorsOpenTransactions.class.getName(), "recordID");
        currentBalanceQuer.addTableAnd(DebtorsJournalMaster.class.getName(), "transactionSource", DebtorsTransactions.class.getName(), "journalNumber");
        //EMCQuery does not support arithmetic in queries.  Select total and settled amounts and subtract in Java.
        currentBalanceQuer.addFieldAggregateFunction("debit", "SUM");
        currentBalanceQuer.addFieldAggregateFunction("debitAmountSettled", "SUM");
        currentBalanceQuer.addFieldAggregateFunction("credit", "SUM");
        currentBalanceQuer.addFieldAggregateFunction("creditAmountSettled", "SUM");
        currentBalanceQuer.addAnd("customerId", customerId);
        currentBalanceQuer.addAnd("transactionDate", atDate, EMCQueryConditions.LESS_THAN_EQ);
        currentBalanceQuer.openConditionBracket(EMCQueryBracketConditions.AND);
        currentBalanceQuer.addOr("journalDefinitionId", parameters.getCreditJournalDef(), DebtorsJournalMaster.class.getName(), EMCQueryConditions.EQUALS);
        currentBalanceQuer.addOr("journalDefinitionId", parameters.getDebitJournalDef(), DebtorsJournalMaster.class.getName(), EMCQueryConditions.EQUALS);
        currentBalanceQuer.closeConditionBracket();
        Object[] balanceArray1 = (Object[]) util.executeSingleResultQuery(currentBalanceQuer, userData);

        BigDecimal totalDebit1 = balanceArray1[0] != null ? (BigDecimal) balanceArray1[0] : BigDecimal.ZERO;
        BigDecimal totalDebitSettled1 = balanceArray1[1] != null ? (BigDecimal) balanceArray1[1] : BigDecimal.ZERO;
        BigDecimal totalCredit1 = balanceArray1[2] != null ? (BigDecimal) balanceArray1[2] : BigDecimal.ZERO;
        BigDecimal totalCreditSettled1 = balanceArray1[3] != null ? (BigDecimal) balanceArray1[3] : BigDecimal.ZERO;

        BigDecimal outstandingDebit1 = totalDebit1.subtract(totalDebitSettled1);
        BigDecimal outstandingCredit1 = totalCredit1.subtract(totalCreditSettled1);

        BigDecimal customerBalance1 = outstandingCredit1.subtract(outstandingDebit1);
        
        if(customerBalance1 == null){
            customerBalance1 = BigDecimal.ZERO;
        }
        
        
        return customerBalance1;
     }
    
}

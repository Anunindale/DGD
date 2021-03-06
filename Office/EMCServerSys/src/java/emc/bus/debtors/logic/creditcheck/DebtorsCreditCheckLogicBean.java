/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.logic.creditcheck;

import emc.bus.creditors.termsofpayment.CreditorsTermsOfPaymentLocal;
import emc.bus.debtors.creditrating.DebtorsCreditRatingLocal;
import emc.bus.debtors.logic.aging.DebtorsAgingLocal;
import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.bus.gl.financialperiods.GLFinancialPeriodsLocal;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.entity.creditors.CreditorsTermsOfPayment;
import emc.entity.debtors.DebtorsCreditRating;
import emc.entity.debtors.DebtorsOpenTransactions;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.gl.GLFinancialPeriods;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.creditors.daysmonths.DaysMonths;
import emc.enums.debtors.creditheld.DebtorsCreditHeldReason;
import emc.enums.debtors.creditheld.DebtorsCreditHeldStatus;
import emc.enums.debtors.creditstopreasons.DebtorsCreditStopReasons;
import emc.enums.debtors.parameters.DebtorsAgingPeriod;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.enums.sop.SalesDeliveryRules;
import emc.enums.sop.customers.CustomerStatusEnum;
import emc.enums.sop.salesorders.SalesOrderStatus;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerDebtorsMessageEnum;
import emc.server.datehandler.EMCDateHandlerLocal;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;

/**
 * @description : This bean is used to perform credit checking.
 *
 * @date : 29 Jul 2010
 *
 * @author : Riaan Nel
 *
 * @version : 1.0
 */
@Stateless
public class DebtorsCreditCheckLogicBean extends EMCBusinessBean implements DebtorsCreditCheckLogicLocal {

    @EJB
    private SOPCustomersLocal customerBean;
    @EJB
    private DebtorsParametersLocal debtorsParametersBean;
    @EJB
    private CreditorsTermsOfPaymentLocal termsOfPaymentBean;
    @EJB
    private EMCDateHandlerLocal dateHandler;
    @EJB
    private DebtorsCreditRatingLocal creditRatingBean;
    @EJB
    private DebtorsAgingLocal agingBean;
    @EJB
    private GLFinancialPeriodsLocal financialPeriodsBean;

    /**
     * Creates a new instance of DebtorsCreditCheckLogicBean
     */
    public DebtorsCreditCheckLogicBean() {
    }

    /**
     * Returns a Credit Held reason indicating whether the specified customer
     * qualifies for the specified credit amount (null means that the customer
     * qualifies for credit).
     *
     * @param customerId Customer id.
     * @param customer can be null pass in if available
     * @param parameters can be null if not available
     * @param atDate Used to get customer balance.
     * @param value Credit value to check.
     * @param checkOpenSO Indicates whether open sales orders should be checked.
     * @param checkOpenPL Indicates whether open picking lists should be
     * checked.
     * @param logMessages Indicates whether messages should be logged.
     * @param userData User data.
     * @return A reason indicating why the credit check failed, or null, if it
     * succeeded.
     */
    public DebtorsCreditHeldReason allowCredit(String customerId, SOPCustomers customer, DebtorsParameters parameters, Date atDate, BigDecimal value, boolean checkOpenSO, boolean checkOpenPL, boolean logMessages, EMCUserData userData) {
        if (customer == null) {
            customer = customerBean.findCustomer(customerId, userData);
        }

        if (customer == null) {
            if (logMessages) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.CUST_NOT_FOUND, userData);
            }

            return DebtorsCreditHeldReason.CUST_NOT_FOUND;
        }

        CustomerStatusEnum customerStatus = CustomerStatusEnum.fromString(customer.getCustomerStatus());

        if (CustomerStatusEnum.STOPPED.equals(customerStatus) && isBlank(DebtorsCreditStopReasons.fromString(customer.getCreditStopReason()))) {
            //If not one of our codes, return stopped reason; else continue with checking
            if (logMessages) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.CUST_STOPPED, userData, customer.getCustomerId());
            }

            return DebtorsCreditHeldReason.CUST_STOPPED;
        }

        if (CustomerStatusEnum.CLOSED.equals(customerStatus)) {
            if (logMessages) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.CUST_CLOSED, userData);
            }

            return DebtorsCreditHeldReason.CUST_CLOSED;
        }

        if (parameters == null) {
            parameters = debtorsParametersBean.getDebtorsParameters(userData);
        }

        DebtorsCreditRating creditRating = creditRatingBean.getCreditRating(customer.getCreditRating(), userData);

        if (parameters.isCheckTerms()) {
            BigDecimal unAlloc = agingBean.getTotalUnallocatedCredit(customerId, atDate, userData);

            CreditorsTermsOfPayment termsOfPayment = termsOfPaymentBean.getTermsOfPayment(customer.getTermsOfPayment(), userData);

            if (termsOfPayment == null) {
                if (logMessages) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.CUST_TERMS_NOT_FOUND, userData);
                }

                return DebtorsCreditHeldReason.TERMS_NOT_FOUND;
            }

            DaysMonths daysMonths = DaysMonths.fromString(termsOfPayment.getDaysOrMonths());

            int maxDays = 0;

            if (creditRating != null) {
                maxDays = creditRating.getTermsToleranceDays();
            }

            if (DaysMonths.DAYS.equals(daysMonths)) {
                maxDays += termsOfPayment.getNumberOf();
            } else if (DaysMonths.MONTHS.equals(daysMonths)) {
                DebtorsAgingPeriod agingPeriod = DebtorsAgingPeriod.fromString(parameters.getDebtorsAgingPeriod());

                if (agingPeriod == null) {
                    if (logMessages) {
                        logMessage(Level.SEVERE, ServerDebtorsMessageEnum.NO_TERMS, userData);
                    }

                    return DebtorsCreditHeldReason.NO_AGING_PERIOD;
                } else if (agingPeriod == DebtorsAgingPeriod.CALENDAR_MONTHS) {
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
                    cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
                    cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
                    cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));

                    for (int i = 0; i < termsOfPayment.getNumberOf(); i++) {
                        cal.add(Calendar.MONTH, -1);
                    }
                    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));

                    maxDays += (int) dateHandler.getDateDiffInDays(cal.getTime(), atDate, userData);
                } else if (agingPeriod == DebtorsAgingPeriod.FINANCIAL_PERIODS) {
                    GLFinancialPeriods currentPeriod = financialPeriodsBean.findPeriodForDate(atDate, userData);

                    if (currentPeriod == null) {
                        if (logMessages) {
                            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.NO_CURRENT_PERIOD, userData);
                        }
                        return DebtorsCreditHeldReason.NO_AGING_PERIOD;
                    }

                    EMCQuery periodQuery = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
                    periodQuery.addAnd("endDate", atDate, EMCQueryConditions.LESS_THAN);
                    periodQuery.addOrderBy("endDate", GLFinancialPeriods.class.getName(), EMCQueryOrderByDirections.DESC);

                    List<GLFinancialPeriods> financialPeriods = (List<GLFinancialPeriods>) util.executeGeneralSelectQuery(periodQuery, userData);

                    GLFinancialPeriods termsPeriod;
                    if (financialPeriods.size() < termsOfPayment.getNumberOf()) {
                        termsPeriod = financialPeriods.get(financialPeriods.size() - 1);
                    } else {
                        termsPeriod = financialPeriods.get(termsOfPayment.getNumberOf() - 1);
                    }

                    maxDays += (int) dateHandler.getDateDiffInDays(termsPeriod.getStartDate(), atDate, userData);
                }
            }
            EMCQuery openTxQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsOpenTransactions.class);
            openTxQuery.addAnd("customerId", customerId);
            openTxQuery.addAnd("transactionDate", Functions.date2SQLString(new Date(dateHandler.addToDate(atDate, -1 * maxDays))), EMCQueryConditions.LESS_THAN);
            openTxQuery.addAnd("debit", 0.0, EMCQueryConditions.GREATER_THAN);
            openTxQuery.addOrderBy("transactionDate", DebtorsOpenTransactions.class.getName(), EMCQueryOrderByDirections.DESC);
            List<DebtorsOpenTransactions> oldOpen = util.executeGeneralSelectQuery(openTxQuery, userData);
            //old transactions exist and noting to allocate
            if (oldOpen.size() > 0 && unAlloc.equals(BigDecimal.ZERO)) {
                if (logMessages) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.TERMS_EXCEEDED, userData);
                }
                return DebtorsCreditHeldReason.TERMS_EXCEEDED;
            }
            //try to allocate
            for (DebtorsOpenTransactions curTrans : oldOpen) {
                unAlloc = unAlloc.subtract(curTrans.getBalance());
                if (unAlloc.compareTo(BigDecimal.ZERO) < 0) {
                    break;
                }
            }
            //failed to eat up all the debit with the credits.
            if (unAlloc.compareTo(BigDecimal.ZERO) < 0) {
                if (logMessages) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.TERMS_EXCEEDED, userData);
                }
                return DebtorsCreditHeldReason.TERMS_EXCEEDED;
            }

        }

        if (parameters.isCheckBalance()) {
            BigDecimal creditLimit = new BigDecimal(customer.getCreaditLimit());

            if (creditRating != null) {
                BigDecimal tolerance = new BigDecimal(creditRating.getTolerancePercentage()).divide(new BigDecimal(100)).multiply(creditLimit);

                creditLimit = creditLimit.add(tolerance);
            }

            BigDecimal outstandingBalance = value;

            if (checkOpenSO) {
                outstandingBalance = outstandingBalance.add(getOpenSalesOrderValue(customerId, userData));
            }

            //Open works order should only be checked for STK customers.
            //DESP customers will have open picking lists for all open works orders

            //Add customer balance
            outstandingBalance = outstandingBalance.add(agingBean.getBalance(customerId, userData));

            if (creditLimit.subtract(outstandingBalance).compareTo(BigDecimal.ZERO) < 0) {
                //Credit Limit will be exceeded.
                if (logMessages) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.LIMIT_EXCEEDED, userData);
                }

                return DebtorsCreditHeldReason.CREDIT_LIMIT;
            }
        }

        return null;
    }

    /**
     * Returns the total value of unapproved open Sales Orders for the specified
     * customer.
     */
    private BigDecimal getOpenSalesOrderValue(String customerId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderLines.class);
        query.addTableAnd(SOPSalesOrderMaster.class.getName(), "salesOrderNo", SOPSalesOrderLines.class.getName(), "salesOrderNo");
        query.addAnd("salsesOrderStatus", SalesOrderStatus.OPEN.toString(), SOPSalesOrderMaster.class.getName());
        query.openAndConditionBracket();
        query.addAnd("creditHeldStatus", null);
        query.addOr("creditHeldStatus", DebtorsCreditHeldStatus.HELD);
        query.closeConditionBracket();
        query.addAnd("customerNo", customerId, SOPSalesOrderMaster.class.getName());
        query.addFieldAggregateFunction("lineTotal", "SUM");

        BigDecimal total = (BigDecimal) util.executeSingleResultQuery(query, userData);

        return total == null ? BigDecimal.ZERO : total;
    }

    @Override
    public DebtorsCreditHeldReason allowToOrder(String customerId, EMCUserData userData) {

        SOPCustomers customer = customerBean.findCustomer(customerId, userData);


        if (customer == null) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.CUST_NOT_FOUND, userData);
            return DebtorsCreditHeldReason.CUST_NOT_FOUND;
        }

        CustomerStatusEnum customerStatus = CustomerStatusEnum.fromString(customer.getCustomerStatus());

        if (CustomerStatusEnum.STOPPED.equals(customerStatus) && isBlank(DebtorsCreditStopReasons.fromString(customer.getCreditStopReason()))) {
            //If not one of our codes, return stopped reason; else continue with checking
            //logMessage(Level.SEVERE, ServerDebtorsMessageEnum.CUST_STOPPED, userData, customer.getCustomerId());
            logMessage(Level.SEVERE, "Account on hold, please contact accounts on 011 435 0010", userData);
           
            return DebtorsCreditHeldReason.CUST_STOPPED;
        }

        if (CustomerStatusEnum.CLOSED.equals(customerStatus)) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.CUST_CLOSED, userData);
            return DebtorsCreditHeldReason.CUST_CLOSED;
        }
        return null;
    }
}

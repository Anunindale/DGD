/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.logic.aging;

import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.entity.debtors.DebtorsOpenTransactions;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.debtors.transactionsettlement.DebtorsTransactionSettlementHistory;
import emc.entity.gl.GLFinancialPeriods;
import emc.enums.debtors.parameters.DebtorsAgingMode;
import emc.enums.debtors.parameters.DebtorsAgingPeriod;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.debtors.DebtorsAgingHelper;
import emc.helpers.debtors.DebtorsDetailedAgingHelper;
import emc.helpers.debtors.DebtorsDetailedAgingLineDS;
import emc.messages.ServerDebtorsMessageEnum;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @description : Bean used to calculate aging in the Debtors module.
 *
 * @date : 21 Jun 2010
 *
 * @author : Riaan Nel
 *
 * @version : 1.0
 */
@Stateless
public class DebtorsAgingBean extends EMCBusinessBean implements DebtorsAgingLocal {

    @EJB
    private DebtorsParametersLocal debtorsParametersBean;

    /**
     * Creates a new instance of DebtorsAgingBean
     */
    public DebtorsAgingBean() {
    }

    /**
     * Returns a list of DebtorsAgingHelper instances representing aging for
     * either a specified customer or all customers at the specified date.
     *
     * @param customerId Customer id. If this is null, if will be ignored and
     * aging will be done across all transactions.
     * @param atDate Date which falls in the 'current' bin.
     * @param mode Aging mode to be used. If this is null, it will be selected
     * from the DebtorsParameters table.
     * @param period Aging period to be used. If this is null, it will be
     * selected from the DebtorsParameters table.
     * @param userData User data.
     *
     * @return Returns a list of DebtorsAgingHelper instances.
     */
    @Override
    public List<DebtorsAgingHelper> getDebtorsAging(String customerId, Date atDate, DebtorsAgingMode mode, DebtorsAgingPeriod period, DebtorsParameters parameters, EMCUserData userData) {
        if (atDate == null) {
            atDate = Functions.nowDate();
        }

        List<DebtorsAgingHelper> agingList = new ArrayList<DebtorsAgingHelper>();

        if (parameters == null) {
            parameters = debtorsParametersBean.getDebtorsParameters(userData);
        }

        if (parameters == null) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.PARAM_NOT_FOUND, userData);
            return null;
        }

        if (mode == null || period == null) {
            if (mode == null) {
                mode = DebtorsAgingMode.fromString(parameters.getDebtorsAgingMode());
            }

            if (period == null) {
                period = DebtorsAgingPeriod.fromString(parameters.getDebtorsAgingPeriod());
            }
        }

        if (!isBlank(parameters.getAgingCurrentBinName())) {
            DebtorsAgingHelper helper = new DebtorsAgingHelper();
            helper.setBinName(parameters.getAgingCurrentBinName());
            agingList.add(helper);
        }

        if (!isBlank(parameters.getAgingBin1Name())) {
            DebtorsAgingHelper helper = new DebtorsAgingHelper();
            helper.setBinName(parameters.getAgingBin1Name());
            agingList.add(helper);
        }

        if (!isBlank(parameters.getAgingBin2Name())) {
            DebtorsAgingHelper helper = new DebtorsAgingHelper();
            helper.setBinName(parameters.getAgingBin2Name());
            agingList.add(helper);
        }

        if (!isBlank(parameters.getAgingBin3Name())) {
            DebtorsAgingHelper helper = new DebtorsAgingHelper();
            helper.setBinName(parameters.getAgingBin3Name());
            agingList.add(helper);
        }

        if (!isBlank(parameters.getAgingBin4Name())) {
            DebtorsAgingHelper helper = new DebtorsAgingHelper();
            helper.setBinName(parameters.getAgingBin4Name());
            agingList.add(helper);
        }

        if (!isBlank(parameters.getAgingBin5Name())) {
            DebtorsAgingHelper helper = new DebtorsAgingHelper();
            helper.setBinName(parameters.getAgingBin5Name());
            agingList.add(helper);
        }

        if (!isBlank(parameters.getAgingBin6Name())) {
            DebtorsAgingHelper helper = new DebtorsAgingHelper();
            helper.setBinName(parameters.getAgingBin6Name());
            agingList.add(helper);
        }

        //If no bins were set up, return.
        if (agingList.isEmpty()) {
            return agingList;
        }

        //This list will contain a query for each period.
        List<EMCQuery> periodQueries = new ArrayList<EMCQuery>();

        //All period queries will be copied from this query.  Select total and subtract settled amounts.
        EMCQuery templateQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactions.class);
        templateQuery.addFieldAggregateFunction("debit", "SUM");
        templateQuery.addFieldAggregateFunction("debitAmountSettled", "SUM");

        //Customer is optional.
        if (customerId != null) {
            templateQuery.addAnd("customerId", customerId);
        }

        //Ensure that only data which was valid at atDate is selected.  Bev requested that we remove this check.
        //templateQuery.addAnd("createdDate", atDate, EMCQueryConditions.LESS_THAN_EQ);

        if (period.equals(DebtorsAgingPeriod.CALENDAR_MONTHS)) {
            //Add queries for each period
            //Get current month
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(atDate);

            for (int i = 0; i < agingList.size(); i++) {
                EMCQuery query = templateQuery.copyQuery();

                //Start on the first day of the month
                Date firstDay = dateHandler.buildDate(calendar.get(Calendar.YEAR),
                                                      calendar.get(Calendar.MONTH),
                                                      1,
                                                      calendar.get(Calendar.HOUR),
                                                      calendar.get(Calendar.MINUTE),
                                                      calendar.get(Calendar.SECOND));

                Date lastDay = null;

                if (i == 0) {
                    //Current bin.  Only include transactions up to at date.
                    lastDay = atDate;
                } else {
                    //Go to the last day
                    lastDay = dateHandler.buildDate(calendar.get(Calendar.YEAR),
                                                    calendar.get(Calendar.MONTH),
                                                    calendar.getActualMaximum(Calendar.DAY_OF_MONTH),
                                                    calendar.get(Calendar.HOUR),
                                                    calendar.get(Calendar.MINUTE),
                                                    calendar.get(Calendar.SECOND));
                }

                query.addAnd("transactionDate", lastDay, EMCQueryConditions.LESS_THAN_EQ);

                //Last bin should go ignore start of month and select everything older than month.
                if (i != agingList.size() - 1) {
                    query.addAnd("transactionDate", firstDay, EMCQueryConditions.GREATER_THAN_EQ);
                    agingList.get(i).setBinStartDate(firstDay);
                }

                periodQueries.add(query);

                agingList.get(i).setBinEndDate(lastDay);

                //Go to previous month
                calendar.add(Calendar.MONTH, -1);
            }
        } else if (period.equals(DebtorsAgingPeriod.FINANCIAL_PERIODS)) {
            //Get current financial period.
            EMCQuery periodQuery = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
            periodQuery.addAnd("startDate", atDate, EMCQueryConditions.LESS_THAN_EQ);
            periodQuery.addAnd("endDate", atDate, EMCQueryConditions.GREATER_THAN_EQ);
            periodQuery.addField("startDate");
            periodQuery.addField("endDate");
            periodQuery.addOrderBy("startDate", GLFinancialPeriods.class.getName(), EMCQueryOrderByDirections.DESC);

            Object[] currentPeriod = (Object[]) util.executeSingleResultQuery(periodQuery, userData);

            if (currentPeriod != null) {
                //Add current period query
                EMCQuery query = templateQuery.copyQuery();
                query.addAnd("transactionDate", currentPeriod[0], EMCQueryConditions.GREATER_THAN_EQ);
                query.addAnd("transactionDate", atDate, EMCQueryConditions.LESS_THAN_EQ);

                periodQueries.add(query);

                agingList.get(0).setBinStartDate((Date) currentPeriod[0]);
                agingList.get(0).setBinEndDate(atDate);
            } else {
                //Current bin query should select nothing
                EMCQuery query = templateQuery.copyQuery();
                query.addAnd("recordID", 0);

                periodQueries.add(query);
            }

            //Get previous financial periods
            periodQuery.removeAnd("endDate");
            periodQuery.removeAnd("startDate");
            periodQuery.addAnd("endDate", atDate, EMCQueryConditions.LESS_THAN);

            List<Object[]> financialPeriods = (List<Object[]>) util.executeGeneralSelectQuery(periodQuery, userData);

            //Only do this if there is more that one bin
            if (agingList.size() > 1) {
                for (int i = 0; i < agingList.size() - 1; i++) {
                    //Is there another period?
                    if (financialPeriods.size() == i) {
                        break;
                    }

                    currentPeriod = financialPeriods.get(i);

                    EMCQuery query = templateQuery.copyQuery();

                    //Ignore start date for last bin
                    if (i != agingList.size() - 2) {
                        query.addAnd("transactionDate", currentPeriod[0], EMCQueryConditions.GREATER_THAN_EQ);
                        agingList.get(i + 1).setBinStartDate((Date) currentPeriod[0]);
                    }

                    query.addAnd("transactionDate", currentPeriod[1], EMCQueryConditions.LESS_THAN_EQ);

                    periodQueries.add(query);

                    //Set aging bin dates.
                    agingList.get(i + 1).setBinEndDate((Date) currentPeriod[1]);
                }
            }
        }

        //Calculate totals for each bin
        switch (mode) {
            case NONE:
                calculateDebitAgingNONE(periodQueries, agingList, atDate, customerId, userData);
                break;
            case DATE:
                calculateDebitAgingDATE(periodQueries, agingList, atDate, customerId, userData);
                break;
            case OLDEST:
                calculateDebitAgingOLDEST(customerId, periodQueries, agingList, atDate, userData);
                break;
        }

        return agingList;
    }

    /**
     * Calculates the total outstanding debits from the specified queries, and
     * sets bin amounts in the corresponding positions in the aging list.
     *
     * @param periodQueries List of queries selecting debits over a period.
     * @param agingList List of DebtorsAgingHelper instances.
     * @param atDate Date for which aging is being performed.
     * @param customerId Id of the customer for whom aging is being done. If
     * this is null, all transactions will be taken into account.
     * @param userData User data
     */
    private void calculateDebitAgingNONE(List<EMCQuery> periodQueries, List<DebtorsAgingHelper> agingList, Date atDate, String customerId, EMCUserData userData) {
        for (int i = 0; i < periodQueries.size(); i++) {
            //Only sum over debits
            Object[] debitTotals = (Object[]) util.executeSingleResultQuery(periodQueries.get(i), userData);

            if (debitTotals != null) {
                BigDecimal debitTotal = (BigDecimal) debitTotals[0] == null ? BigDecimal.ZERO : (BigDecimal) debitTotals[0];
                BigDecimal debitSettledTotal = (BigDecimal) debitTotals[1] == null ? BigDecimal.ZERO : (BigDecimal) debitTotals[1];

                BigDecimal binAmount = debitTotal.subtract(debitSettledTotal);
                agingList.get(i).setBinAmount(binAmount);
            } else {
                agingList.get(i).setBinAmount(BigDecimal.ZERO);
            }
        }

        //If atDate less than today, ignore updates made to transactions between atDate and today.
        //Only check dates, not time
        Calendar atCalendar = Calendar.getInstance();
        atCalendar.setTime(atDate);
        atCalendar.set(Calendar.HOUR, 0);
        atCalendar.set(Calendar.HOUR_OF_DAY, 0);
        atCalendar.set(Calendar.MINUTE, 0);
        atCalendar.set(Calendar.SECOND, 0);
        atCalendar.set(Calendar.MILLISECOND, 0);

        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(Functions.nowDate());
        nowCalendar.set(Calendar.HOUR, 0);
        nowCalendar.set(Calendar.HOUR_OF_DAY, 0);
        nowCalendar.set(Calendar.MINUTE, 0);
        nowCalendar.set(Calendar.SECOND, 0);
        nowCalendar.set(Calendar.MILLISECOND, 0);

        if (atCalendar.compareTo(nowCalendar) < 0) {
            //For each bin, revert transaction history
            for (DebtorsAgingHelper binHelper : agingList) {
                EMCQuery settledQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlementHistory.class);
                settledQuery.addFieldAggregateFunction("debitSettled", "SUM");
                //Check that transaction existed at atDate.  Bev requested that we remove this check.
                //settledQuery.addAnd("transactionCreatedDate", atDate, EMCQueryConditions.LESS_THAN_EQ);
                settledQuery.openConditionBracket(EMCQueryBracketConditions.NONE);
                settledQuery.addAnd("debitTransactionDate", binHelper.getBinEndDate(), EMCQueryConditions.LESS_THAN_EQ);
                //settledQuery.addAnd("creditTransactionDate", binHelper.getBinEndDate(), EMCQueryConditions.LESS_THAN_EQ);
                settledQuery.closeConditionBracket();

                //Customer is optional
                if (customerId != null) {
                    settledQuery.addAnd("customerId", customerId);
                }

                //Only include transactions settled after atDate.
                settledQuery.addAnd("createdDate", atDate, EMCQueryConditions.GREATER_THAN);

                if (binHelper.getBinStartDate() != null) {
                    settledQuery.openConditionBracket(EMCQueryBracketConditions.AND);
                    settledQuery.addAnd("debitTransactionDate", binHelper.getBinStartDate(), EMCQueryConditions.GREATER_THAN_EQ);
                    //settledQuery.addAnd("creditTransactionDate", binHelper.getBinStartDate(), EMCQueryConditions.GREATER_THAN_EQ);
                    settledQuery.closeConditionBracket();
                }

                BigDecimal totalSettled = (BigDecimal) util.executeSingleResultQuery(settledQuery, userData);
                if (totalSettled == null) {
                    totalSettled = BigDecimal.ZERO;
                }

                //Add settled amount & discount back to bin
                //binHelper.setBinAmount(binHelper.getBinAmount().add(totalSettled).add(totalDiscount));
                binHelper.setBinAmount(binHelper.getBinAmount().add(totalSettled));
            }
        }
    }

    /**
     * Calculates the total outstanding debits minus total outstanding credits
     * from the specified queries, and sets bin amounts in the corresponding
     * positions in the aging list.
     *
     * @param periodQueries List of queries selecting debits over a period.
     * @param agingList List of DebtorsAgingHelper instances.
     * @param atDate Date for which aging is being performed.
     * @param customerId Id of the customer for whom aging is being done. If
     * this is null, all transactions will be taken into account.
     * @param userData User data
     */
    private void calculateDebitAgingDATE(List<EMCQuery> periodQueries, List<DebtorsAgingHelper> agingList, Date atDate, String customerId, EMCUserData userData) {
        for (int i = 0; i < periodQueries.size(); i++) {
            //Calculate sum of debits & credits for each period
            EMCQuery query = periodQueries.get(i);
            query.addFieldAggregateFunction("credit", "SUM");
            query.addFieldAggregateFunction("creditAmountSettled", "SUM");

            Object[] totals = (Object[]) util.executeSingleResultQuery(query, userData);
            if (totals != null) {
                BigDecimal debitTotal = (BigDecimal) totals[0] == null ? BigDecimal.ZERO : (BigDecimal) totals[0];
                BigDecimal debitSettledTotal = (BigDecimal) totals[1] == null ? BigDecimal.ZERO : (BigDecimal) totals[1];
                BigDecimal creditTotal = (BigDecimal) totals[2] == null ? BigDecimal.ZERO : (BigDecimal) totals[2];
                BigDecimal creditSettledTotal = (BigDecimal) totals[3] == null ? BigDecimal.ZERO : (BigDecimal) totals[3];

                //Subtract outstanding credits from outstanding debits
                BigDecimal binAmount = debitTotal.subtract(debitSettledTotal).subtract(creditTotal.subtract(creditSettledTotal));
                agingList.get(i).setBinAmount(binAmount);
            } else {
                agingList.get(i).setBinAmount(BigDecimal.ZERO);
            }
        }
        //If atDate less than today, ignore updates made to transactions between atDate and today.
        //Only check dates, not time
        Calendar atCalendar = Calendar.getInstance();
        atCalendar.setTime(atDate);
        atCalendar.set(Calendar.HOUR, 0);
        atCalendar.set(Calendar.MINUTE, 0);
        atCalendar.set(Calendar.SECOND, 0);
        atCalendar.set(Calendar.MILLISECOND, 0);

        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(Functions.nowDate());
        nowCalendar.set(Calendar.HOUR, 0);
        nowCalendar.set(Calendar.MINUTE, 0);
        nowCalendar.set(Calendar.SECOND, 0);
        nowCalendar.set(Calendar.MILLISECOND, 0);

        if (atCalendar.compareTo(nowCalendar) < 0) {
            int bin = 0;
            //For each bin, revert transaction history
            for (DebtorsAgingHelper binHelper : agingList) {
                //Revert debits and credits seperately.
                EMCQuery settledQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlementHistory.class);
                settledQuery.addFieldAggregateFunction("debitSettled", "SUM");
                //Check that transaction existed at atDate.    Bev requested that we remove this check.
                //settledQuery.addAnd("transactionCreatedDate", atDate, EMCQueryConditions.LESS_THAN_EQ);

                settledQuery.openAndConditionBracket();
                settledQuery.addOr("debitTransactionDate", binHelper.getBinEndDate(), EMCQueryConditions.LESS_THAN_EQ);
                settledQuery.closeConditionBracket();

                //Customer is optional
                if (customerId != null) {
                    settledQuery.addAnd("customerId", customerId);
                }

                //Only include transactions settled after atDate.
                settledQuery.addAnd("createdDate", atDate, EMCQueryConditions.GREATER_THAN);

                if (binHelper.getBinStartDate() != null) {
                    settledQuery.openAndConditionBracket();
                    settledQuery.addOr("debitTransactionDate", binHelper.getBinStartDate(), EMCQueryConditions.GREATER_THAN_EQ);
                    settledQuery.closeConditionBracket();
                }

                BigDecimal debitSettled = (BigDecimal) util.executeSingleResultQuery(settledQuery, userData);
                if (debitSettled == null) {
                    debitSettled = BigDecimal.ZERO;
                }

                settledQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlementHistory.class);
                settledQuery.addFieldAggregateFunction("creditSettled", "SUM");
                //Check that transaction existed at atDate.    Bev requested that we remove this check.
                //settledQuery.addAnd("transactionCreatedDate", atDate, EMCQueryConditions.LESS_THAN_EQ);

                settledQuery.openAndConditionBracket();
                settledQuery.addOr("creditTransactionDate", binHelper.getBinEndDate(), EMCQueryConditions.LESS_THAN_EQ);
                settledQuery.closeConditionBracket();

                //Customer is optional
                if (customerId != null) {
                    settledQuery.addAnd("customerId", customerId);
                }

                //Only include transactions settled after atDate.
                settledQuery.addAnd("createdDate", atDate, EMCQueryConditions.GREATER_THAN);

                if (binHelper.getBinStartDate() != null) {
                    settledQuery.openAndConditionBracket();
                    settledQuery.addOr("creditTransactionDate", binHelper.getBinStartDate(), EMCQueryConditions.GREATER_THAN_EQ);
                    settledQuery.closeConditionBracket();
                }

                BigDecimal creditSettled = (BigDecimal) util.executeSingleResultQuery(settledQuery, userData);
                if (creditSettled == null) {
                    creditSettled = BigDecimal.ZERO;
                }

                //System.out.println("Bin " + (bin++ + 1) + " Debit Added Back: " + debitSettled + " Credit Added Back: " + creditSettled);

                //Add settled amount & discount back to bin and subtract credit total
                //binHelper.setBinAmount(binHelper.getBinAmount().add((debitSettled).add(totalDiscount)).subtract(creditSettled));
                binHelper.setBinAmount(binHelper.getBinAmount().add(debitSettled).subtract(creditSettled));
            }
        }
    }

    /**
     * Calculates the total outstanding debits and allocates outstanding credits
     * to these debits, starting with the oldest bin, and sets bin amounts in
     * the corresponding positions in the aging list.
     *
     * @param customerId Id of the customer for whom aging is being done. If
     * this is null, all transactions will be taken into account.
     * @param periodQueries List of queries selecting debits over a period.
     * @param agingList List of DebtorsAgingHelper instances.
     * @param atDate Date for which aging is being performed.
     * @param userData User data
     */
    private void calculateDebitAgingOLDEST(String customerId, List<EMCQuery> periodQueries, List<DebtorsAgingHelper> agingList, Date atDate, EMCUserData userData) {
        //First calculate total outstanding debits
        calculateDebitAgingNONE(periodQueries, agingList, atDate, customerId, userData);

        //Bev requested that we remove this check.  This will now include all unallocated credits, regardless of
        //whether or not they existed at atDate.
        //query.addAnd("createdDate", atDate, EMCQueryConditions.LESS_THAN_EQ);

        BigDecimal unallocatedCredit = getTotalUnallocatedCredit(customerId, atDate, userData);

        //Start allocating credits to oldest debits
        for (int i = agingList.size() - 1; i >= 0; i--) {
            BigDecimal currentBinAmount = agingList.get(i).getBinAmount();
            if (currentBinAmount.compareTo(BigDecimal.ZERO) > 0) {
                //Debit amount found
                if (currentBinAmount.compareTo(unallocatedCredit) > 0) {
                    //Outstanding debit is more than outstanding credit.
                    //Allocate credit in full.
                    agingList.get(i).setBinAmount(currentBinAmount.subtract(unallocatedCredit));
                    unallocatedCredit = BigDecimal.ZERO;
                } else {
                    //Outstanding debit is less than or equal to outstanding credit.
                    //Allocate bin debit amount.
                    unallocatedCredit = unallocatedCredit.subtract(currentBinAmount);
                    agingList.get(i).setBinAmount(BigDecimal.ZERO);
                }

                if (unallocatedCredit.compareTo(BigDecimal.ZERO) == 0) {
                    //Consumed credit, exit loop.
                    break;
                }
            }
        }

        //If credit remains, allocate all of it to the current bin.
        if (unallocatedCredit.compareTo(BigDecimal.ZERO) > 0 && !agingList.isEmpty()) {
            agingList.get(0).setBinAmount(agingList.get(0).getBinAmount().subtract(unallocatedCredit));
        }

//        This code removed.  As the getTotalUnallocatedCredit() method already returned the full credit amount outstanding at the specified
//        date, this caused double the outstanding credit to be allocated.
//        //If atDate less than today, ignore updates made to transactions between atDate and today.
//        //Only check dates, not time
//        Calendar atCalendar = Calendar.getInstance();
//        atCalendar.setTime(atDate);
//        atCalendar.set(Calendar.HOUR, 0);
//        atCalendar.set(Calendar.MINUTE, 0);
//        atCalendar.set(Calendar.SECOND, 0);
//        atCalendar.set(Calendar.MILLISECOND, 0);
//
//        Calendar nowCalendar = Calendar.getInstance();
//        nowCalendar.setTime(Functions.nowDate());
//        nowCalendar.set(Calendar.HOUR, 0);
//        nowCalendar.set(Calendar.MINUTE, 0);
//        nowCalendar.set(Calendar.SECOND, 0);
//        nowCalendar.set(Calendar.MILLISECOND, 0);
//
//        if (atCalendar.compareTo(nowCalendar) < 0) {
//            EMCQuery creditQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlementHistory.class);
//            creditQuery.addFieldAggregateFunction("creditSettled", "SUM");
//            //Only include transactions that existed on atDate.  Bev requested that we remove this check.
//            //creditQuery.addAnd("transactionCreatedDate", atDate, EMCQueryConditions.LESS_THAN_EQ);
//
//            //Customer is optional
//            if (customerId != null) {
//                creditQuery.addAnd("customerId", customerId);
//            }
//
//            //Only include transactions settled after atDate
//            creditQuery.addAnd("createdDate", atDate, EMCQueryConditions.GREATER_THAN);
//
//            BigDecimal creditSettled = (BigDecimal) util.executeSingleResultQuery(creditQuery, userData);
//            if (creditSettled == null) {
//                creditSettled = BigDecimal.ZERO;
//            }
//
//            //Start allocating credits to oldest debits
//            for (int i = agingList.size() - 1; i >= 0; i--) {
//                BigDecimal currentBinAmount = agingList.get(i).getBinAmount();
//                if (currentBinAmount.compareTo(BigDecimal.ZERO) > 0) {
//                    //Debit amount found
//                    if (currentBinAmount.compareTo(creditSettled) > 0) {
//                        //Outstanding debit is more than outstanding credit.
//                        //Allocate credit in full.
//                        agingList.get(i).setBinAmount(currentBinAmount.subtract(creditSettled));
//                        creditSettled = BigDecimal.ZERO;
//                    } else {
//                        //Outstanding debit is less than or equal to outstanding credit.
//                        //Allocate bin debit amount.
//                        creditSettled = creditSettled.subtract(currentBinAmount);
//                        agingList.get(i).setBinAmount(BigDecimal.ZERO);
//                    }
//
//                    if (creditSettled.compareTo(BigDecimal.ZERO) == 0) {
//                        //Consumed credit, exit loop.
//                        break;
//                    }
//                }
//            }
//
//            //If credit remains, allocate all of it to the current bin.
//            if (creditSettled.compareTo(BigDecimal.ZERO) > 0 && !agingList.isEmpty()) {
//                agingList.get(0).setBinAmount(agingList.get(0).getBinAmount().subtract(creditSettled));
//            }
//        }
    }

    /**
     * This method returns sets of aged transactions.
     *
     * @param customerId Customer for which ageing is being performed.
     * @param atDate Date at which ageing should take place.
     * @param mode Aging mode to be used. If this is null, the aging mode
     * specified on Debtors Parameters will be used.
     * @param period Aging period to be used. If this is null, the aging mode
     * specified on Debtors Parameters will be used.
     * @param includeSettled Indicates whether transactions fully settled in the
     * current month should be included. This is relevant to the customer
     * statement.
     * @param userData User data.
     * @return A List of DebtorsDetailedAgingHelper instances representing
     * transactions which fall into various periods/bins.
     */
    @Override
    public List<DebtorsDetailedAgingHelper> getDetailedDebtorsAging(String customerId, Date atDate, DebtorsAgingMode mode, DebtorsAgingPeriod period, boolean includeSettled, EMCUserData userData) {
        List<DebtorsDetailedAgingHelper> agingList = new ArrayList<DebtorsDetailedAgingHelper>();

        if (atDate == null) {
            atDate = Functions.nowDate();
        }

        //If atDate less than today, ignore updates made to transactions between atDate and today.
        //Only check dates, not time
        Calendar atCalendar = Calendar.getInstance();
        atCalendar.setTime(atDate);
        atCalendar.set(Calendar.HOUR, 0);
        atCalendar.set(Calendar.MINUTE, 0);
        atCalendar.set(Calendar.SECOND, 0);
        atCalendar.set(Calendar.MILLISECOND, 0);

        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(Functions.nowDate());
        nowCalendar.set(Calendar.HOUR, 0);
        nowCalendar.set(Calendar.MINUTE, 0);
        nowCalendar.set(Calendar.SECOND, 0);
        nowCalendar.set(Calendar.MILLISECOND, 0);

        DebtorsParameters parameters = debtorsParametersBean.getDebtorsParameters(userData);

        if (parameters == null) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.PARAM_NOT_FOUND, userData);
            return null;
        }

        if (mode == null || period == null) {
            if (mode == null) {
                mode = DebtorsAgingMode.fromString(parameters.getDebtorsAgingMode());
            }

            if (period == null) {
                period = DebtorsAgingPeriod.fromString(parameters.getDebtorsAgingPeriod());
            }
        }

        if (!isBlank(parameters.getAgingCurrentBinName())) {
            DebtorsDetailedAgingHelper helper = new DebtorsDetailedAgingHelper();
            helper.setBinName(parameters.getAgingCurrentBinName());
            agingList.add(helper);
        }

        if (!isBlank(parameters.getAgingBin1Name())) {
            DebtorsDetailedAgingHelper helper = new DebtorsDetailedAgingHelper();
            helper.setBinName(parameters.getAgingBin1Name());
            agingList.add(helper);
        }

        if (!isBlank(parameters.getAgingBin2Name())) {
            DebtorsDetailedAgingHelper helper = new DebtorsDetailedAgingHelper();
            helper.setBinName(parameters.getAgingBin2Name());
            agingList.add(helper);
        }

        if (!isBlank(parameters.getAgingBin3Name())) {
            DebtorsDetailedAgingHelper helper = new DebtorsDetailedAgingHelper();
            helper.setBinName(parameters.getAgingBin3Name());
            agingList.add(helper);
        }

        if (!isBlank(parameters.getAgingBin4Name())) {
            DebtorsDetailedAgingHelper helper = new DebtorsDetailedAgingHelper();
            helper.setBinName(parameters.getAgingBin4Name());
            agingList.add(helper);
        }

        if (!isBlank(parameters.getAgingBin5Name())) {
            DebtorsDetailedAgingHelper helper = new DebtorsDetailedAgingHelper();
            helper.setBinName(parameters.getAgingBin5Name());
            agingList.add(helper);
        }

        if (!isBlank(parameters.getAgingBin6Name())) {
            DebtorsDetailedAgingHelper helper = new DebtorsDetailedAgingHelper();
            helper.setBinName(parameters.getAgingBin6Name());
            agingList.add(helper);
        }

        //This list will contain a query for each period.
        List<EMCQuery> periodQueries = new ArrayList<EMCQuery>();

        //All period queries will be copied from this query.
        EMCQuery templateQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactions.class);
        templateQuery.addAnd("customerId", customerId);
        //Ensure that only data which was valid at atDate is selected.  Bev requested that we remove this check.
        //templateQuery.addAnd("createdDate", atDate, EMCQueryConditions.LESS_THAN_EQ);
        //When changing the following line, note that the customer statement and detailed aging reports will be affected.
        templateQuery.addOrderBy("transactionDate");

        if (period.equals(DebtorsAgingPeriod.CALENDAR_MONTHS)) {
            //Add queries for each period
            //Get current month
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(atDate);

            //Period in which atDate falls.
            Date currentPeriodStartDate = null;

            for (int i = 0; i < agingList.size(); i++) {
                EMCQuery query = templateQuery.copyQuery();

                //Start on the first day of the month
                Date firstDay = dateHandler.buildDate(calendar.get(Calendar.YEAR),
                                                      calendar.get(Calendar.MONTH),
                                                      1,
                                                      calendar.get(Calendar.HOUR),
                                                      calendar.get(Calendar.MINUTE),
                                                      calendar.get(Calendar.SECOND));

                //Go to the last day
                Date lastDay = dateHandler.buildDate(calendar.get(Calendar.YEAR),
                                                     calendar.get(Calendar.MONTH),
                                                     calendar.getActualMaximum(Calendar.DAY_OF_MONTH),
                                                     calendar.get(Calendar.HOUR),
                                                     calendar.get(Calendar.MINUTE),
                                                     calendar.get(Calendar.SECOND));

                agingList.get(i).setBinStartDate(firstDay);

                if (i == 0) {
                    //Use atDate
                    query.addAnd("transactionDate", atDate, EMCQueryConditions.LESS_THAN_EQ);
                    currentPeriodStartDate = firstDay;
                    agingList.get(i).setBinEndDate(atDate);
                } else {
                    //Use last day of month
                    query.addAnd("transactionDate", lastDay, EMCQueryConditions.LESS_THAN_EQ);
                    agingList.get(i).setBinEndDate(lastDay);
                }


                //Include all transactions in the first (current) bin, otherwise, use only open transactions and transactions settled since at date.
                if (i != 0) {
                    query.openAndConditionBracket();

                    EMCQuery openTransQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsOpenTransactions.class);
                    openTransQuery.addField("debtorsTransRef");
                    openTransQuery.addAnd("customerId", customerId);
                    //Ensure that only data which was valid at atDate is selected.  Bev requested that we remove this check.
                    //openTransQuery.addAnd("createdDate", atDate, EMCQueryConditions.LESS_THAN_EQ);
                    openTransQuery.addOrderBy("transactionDate");

                    query.addAnd("recordID", openTransQuery, EMCQueryConditions.IN);

                    if (includeSettled) {
                        query.openConditionBracket(EMCQueryBracketConditions.OR);
                        query.addAnd("lastSettledDate", currentPeriodStartDate, EMCQueryConditions.GREATER_THAN_EQ);
                        //This line caused transactions to be ignored:  E.g. August transactions settled in October did not appear on a statement produced for the end of August, at the end of October.
                        //query.addAnd("lastSettledDate", atDate, EMCQueryConditions.LESS_THAN_EQ);
                        query.closeConditionBracket();
                    }

                    query.closeConditionBracket();
                }

                //Last bin should go ignore start of month and select everything older than month.
                if (i != agingList.size() - 1) {
                    query.addAnd("transactionDate", firstDay, EMCQueryConditions.GREATER_THAN_EQ);
                }

                periodQueries.add(query);

                //Go to previous month
                calendar.add(Calendar.MONTH, -1);
            }
        } else if (period.equals(DebtorsAgingPeriod.FINANCIAL_PERIODS)) {
            //Get current financial period.
            EMCQuery periodQuery = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
            periodQuery.addAnd("startDate", atDate, EMCQueryConditions.LESS_THAN_EQ);
            periodQuery.addAnd("endDate", atDate, EMCQueryConditions.GREATER_THAN_EQ);
            periodQuery.addField("startDate");
            periodQuery.addField("endDate");
            periodQuery.addOrderBy("endDate", GLFinancialPeriods.class.getName(), EMCQueryOrderByDirections.DESC);

            Object[] currentPeriod = (Object[]) util.executeSingleResultQuery(periodQuery, userData);

            //The start date of the period in which at date falls
            Date currentPeriodStartDate = null;

            if (currentPeriod != null) {
                //Add current period query
                EMCQuery query = templateQuery.copyQuery();
                query.addAnd("transactionDate", currentPeriod[0], EMCQueryConditions.GREATER_THAN_EQ);
                query.addAnd("transactionDate", atDate, EMCQueryConditions.LESS_THAN_EQ);

                periodQueries.add(query);

                agingList.get(0).setBinStartDate((Date) currentPeriod[0]);
                agingList.get(0).setBinEndDate(atDate);

                currentPeriodStartDate = (Date) currentPeriod[0];
            } else {
                //Current bin query should select nothing
                EMCQuery query = templateQuery.copyQuery();
                query.addAnd("recordID", 0);

                periodQueries.add(query);

                //Use atDate as start date.
                currentPeriodStartDate = atDate;
            }

            //Get previous financial periods
            periodQuery.removeAnd("endDate");
            periodQuery.removeAnd("startDate");
            periodQuery.addAnd("endDate", currentPeriod[1], EMCQueryConditions.LESS_THAN);

            List<Object[]> financialPeriods = (List<Object[]>) util.executeGeneralSelectQuery(periodQuery, userData);

            //Only do this if there is more that one bin
            if (agingList.size() > 1) {
                for (int i = 0; i < agingList.size() - 1; i++) {
                    //Is there another period?
                    if (financialPeriods.size() == i) {
                        break;
                    }

                    currentPeriod = financialPeriods.get(i);

                    EMCQuery query = templateQuery.copyQuery();
                    query.addAnd("transactionDate", currentPeriod[0], EMCQueryConditions.GREATER_THAN_EQ);
                    query.addAnd("transactionDate", currentPeriod[1], EMCQueryConditions.LESS_THAN_EQ);

                    query.openAndConditionBracket();

                    EMCQuery openTransQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsOpenTransactions.class);
                    openTransQuery.addField("debtorsTransRef");
                    openTransQuery.addAnd("customerId", customerId);
                    //Ensure that only data which was valid at atDate is selected.  Bev requested that we remove this check.
                    //openTransQuery.addAnd("createdDate", atDate, EMCQueryConditions.LESS_THAN_EQ);
                    openTransQuery.addOrderBy("transactionDate");

                    query.addAnd("recordID", openTransQuery, EMCQueryConditions.IN);

                    if (includeSettled) {
                        query.openConditionBracket(EMCQueryBracketConditions.OR);
                        query.addAnd("lastSettledDate", currentPeriodStartDate, EMCQueryConditions.GREATER_THAN_EQ);
                        //query.addAnd("lastSettledDate", atDate, EMCQueryConditions.LESS_THAN_EQ);
                        query.closeConditionBracket();
                    }

                    query.closeConditionBracket();

                    periodQueries.add(query);
                }
            }
        }

        //All open transactions will be displayed, regardless of aging mode.
        for (int i = 0; i < periodQueries.size(); i++) {
            EMCQuery periodQuery = periodQueries.get(i);

            //Select all transactions in period
            List<DebtorsTransactions> transactions = (List<DebtorsTransactions>) util.executeGeneralSelectQuery(periodQuery, userData);

            for (DebtorsTransactions transaction : transactions) {
                DebtorsDetailedAgingLineDS agingLine = new DebtorsDetailedAgingLineDS();

                agingLine.setTransactionDate(transaction.getTransactionDate());
                agingLine.setTransactionRef(transaction.getReferenceNumber());
                agingLine.setTransactionType(transaction.getReferenceType());
                agingLine.setTransRecordID(transaction.getRecordID());
                agingLine.setCustomerOrderNumber(transaction.getCustomerOrderNumber());
                agingLine.setPaymentDueDate(transaction.getPaymentDueDate());

                if (transaction.getDebit().compareTo(BigDecimal.ZERO) > 0) {
                    //Debit transaction
                    agingLine.setAmount(transaction.getDebit());
                    agingLine.setBalance(transaction.getDebit().subtract(transaction.getDebitAmountSettled()));
                } else {
                    BigDecimal negativeOne = new BigDecimal(-1);
                    //Credit transaction
                    agingLine.setAmount(transaction.getCredit().multiply(negativeOne));
                    agingLine.setBalance((transaction.getCredit().subtract(transaction.getCreditAmountSettled()).multiply(negativeOne)));
                }

                agingList.get(i).addAgingLine(agingLine);
            }
        }

        if (atCalendar.compareTo(nowCalendar) < 0) {
            //Get transactions back in state which they were in at atDate
            //switch (mode) {
            //    case NONE:
            getDetailedDebitAgingNONE(agingList, atDate, userData);
            //        break;
            //    case DATE:
            //        getDetailedDebitAgingDATE(agingList, atDate, userData);
            //        break;
            //    case OLDEST:
            //        getDetailedDebitAgingOLDEST(agingList, atDate, userData);
            //        break;
            //}
        }

        return agingList;
    }

    /**
     * Calculates detailed aging for the NONE mode. This method reverts updates
     * made from atDate until today before returning results.
     *
     * @param agingList List containing DebtorsDetailedAgingHelper instances.
     * @param atDate Date to use for aging.
     * @param userData User data.
     */
    private void getDetailedDebitAgingNONE(List<DebtorsDetailedAgingHelper> agingList, Date atDate, EMCUserData userData) {
        //Get and revert all updates made to all transactions from atDate to today.
        for (DebtorsDetailedAgingHelper agingHelper : agingList) {
            for (DebtorsDetailedAgingLineDS agingLine : agingHelper.getAgingLines()) {
                EMCQuery settlementQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlementHistory.class);
                settlementQuery.addAnd("createdDate", atDate, EMCQueryConditions.GREATER_THAN);

                if (agingLine.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                    //Debit
                    settlementQuery.addFieldAggregateFunction("debitSettled", "SUM");
                    settlementQuery.addAnd("debitTransRef", agingLine.getTransRecordID());
                    //When changing the following line, note that the customer statement and detailed aging reports will be affected.
                    settlementQuery.addOrderBy("debitTransactionDate");
                } else {
                    //Credit
                    settlementQuery.addFieldAggregateFunction("creditSettled", "SUM");
                    settlementQuery.addAnd("creditTransRef", agingLine.getTransRecordID());
                    //When changing the following line, note that the customer statement and detailed aging reports will be affected.
                    settlementQuery.addOrderBy("creditTransactionDate");
                }

                BigDecimal totalSettled = (BigDecimal) util.executeSingleResultQuery(settlementQuery, userData);

                if (totalSettled == null) {
                    totalSettled = BigDecimal.ZERO;
                }

                if (agingLine.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                    //Debit
                    agingLine.setBalance(agingLine.getBalance().add(totalSettled));
                } else {
                    agingLine.setBalance(agingLine.getBalance().subtract(totalSettled));
                }
            }
        }
    }

    /**
     * Calculates detailed aging for the DATE mode. This method reverts updates
     * made from atDate until today (using the getDetailedDebitAgingNONE()
     * method ) and then allocates debit and credit transactions based on the
     * date range in which they fall.
     *
     * @param agingList List containing DebtorsDetailedAgingHelper instances.
     * @param atDate Date to use for aging.
     * @param userData User data.
     */
    private void getDetailedDebitAgingDATE(List<DebtorsDetailedAgingHelper> agingList, Date atDate, EMCUserData userData) {
        getDetailedDebitAgingNONE(agingList, atDate, userData);

        for (DebtorsDetailedAgingHelper agingHelper : agingList) {
//Lines to be removed from aging helper. (Transactions allocated in full)  These should be ignored while processing.
//List<DebtorsDetailedAgingLineDS> toRemove = new ArrayList<DebtorsDetailedAgingLineDS>();

//Get all debit and credit transactions in date range and allocate.  Allocate credits to debits.
//The list of aging lines will be ordered by date.
credit:     for (DebtorsDetailedAgingLineDS agingLine : agingHelper.getAgingLines()) {
                if (agingLine.getAmount().compareTo(BigDecimal.ZERO) < 0 && agingLine.getBalance().compareTo(BigDecimal.ZERO) != 0) {
                    DebtorsDetailedAgingLineDS credit = agingLine;

//Loop again, looking for debits
debit:              for (DebtorsDetailedAgingLineDS otherAgingLine : agingHelper.getAgingLines()) {
                        if (otherAgingLine.getAmount().compareTo(BigDecimal.ZERO) < 0 && otherAgingLine.getBalance().compareTo(BigDecimal.ZERO) != 0) {
                            DebtorsDetailedAgingLineDS debit = otherAgingLine;

                            //Get difference between debit and credit balances.
                            BigDecimal difference = debit.getBalance().add(credit.getBalance());
                            if (difference.compareTo(BigDecimal.ZERO) > 0) {
                                //Debit greater than credit.  Allocate credit in full.
                                credit.setBalance(BigDecimal.ZERO);
                                debit.setBalance(debit.getBalance().add(credit.getBalance()));

                                //toRemove.add(credit);

                                //Credit consumed.  Continue outer loop.
                                continue credit;
                            } else if (difference.compareTo(BigDecimal.ZERO) < 0) {
                                //Credit greater than debit.
                                debit.setBalance(BigDecimal.ZERO);
                                credit.setBalance(credit.getBalance().subtract(debit.getBalance()));

                                //toRemove.add(debit);

                                //Debit consumed
                                continue;
                            } else {
                                //Debit == credit.  Consume both.
                                debit.setBalance(BigDecimal.ZERO);
                                credit.setBalance(BigDecimal.ZERO);

                                //toRemove.add(credit);
                                //toRemove.add(debit);

                                //Credit consumed.  Continue outer loop.
                                continue credit;
                            }
                        } else {
                            //Credit, continue to next line.
                            continue;
                        }
                    }
                } else {
                    //Debit, continue to next line
                    continue;
                }
            }

            //Remove balanced lines.
            //agingHelper.getAgingLines().removeAll(toRemove);
        }
    }

    /**
     * Calculates detailed aging for the OLDEST mode. This method reverts
     * updates made from atDate until today (using the
     * getDetailedDebitAgingNONE() method ) and then allocates debit and credit
     * transactions based on the oldest debits.
     *
     * @param agingList List containing DebtorsDetailedAgingHelper instances.
     * @param atDate Date to use for aging.
     * @param userData User data.
     */
    private void getDetailedDebitAgingOLDEST(List<DebtorsDetailedAgingHelper> agingList, Date atDate, EMCUserData userData) {
        getDetailedDebitAgingNONE(agingList, atDate, userData);

        //Aging lines to remove
        //List<DebtorsDetailedAgingLineDS> toRemove = new ArrayList<DebtorsDetailedAgingLineDS>();

        //All credits
        List<DebtorsDetailedAgingLineDS> credits = new ArrayList<DebtorsDetailedAgingLineDS>();
        //All debits
        List<DebtorsDetailedAgingLineDS> debits = new ArrayList<DebtorsDetailedAgingLineDS>();

        for (DebtorsDetailedAgingHelper agingHelper : agingList) {
            for (DebtorsDetailedAgingLineDS agingLine : agingHelper.getAgingLines()) {
                if (agingLine.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                    //Credit
                    credits.add(agingLine);
                } else {
                    //Debit
                    debits.add(agingLine);
                }
            }
        }

//Start allocating from oldest credits.
credit: for (DebtorsDetailedAgingLineDS credit : credits) {
            if (credit.getBalance().compareTo(BigDecimal.ZERO) != 0) {
//Loop through debits
debit:          for (DebtorsDetailedAgingLineDS debit : debits) {
                    if (debit.getBalance().compareTo(BigDecimal.ZERO) != 0) {
                        //Get difference between debit and credit balances.
                        BigDecimal difference = debit.getBalance().add(credit.getBalance());
                        if (difference.compareTo(BigDecimal.ZERO) > 0) {
                            //Debit greater than credit.  Allocate credit in full.
                            debit.setBalance(debit.getBalance().add(credit.getBalance()));
                            credit.setBalance(BigDecimal.ZERO);

                            //toRemove.add(credit);

                            //Credit consumed.  Continue outer loop.
                            continue credit;
                        } else if (difference.compareTo(BigDecimal.ZERO) < 0) {
                            //Credit greater than debit.
                            credit.setBalance(credit.getBalance().add(debit.getBalance()));
                            debit.setBalance(BigDecimal.ZERO);

                            //toRemove.add(debit);

                            //Debit consumed
                            continue;
                        } else {
                            //Debit == credit.  Consume both.
                            debit.setBalance(BigDecimal.ZERO);
                            credit.setBalance(BigDecimal.ZERO);

                            //toRemove.add(credit);
                            //toRemove.add(debit);

                            //Credit consumed.  Continue outer loop.
                            continue credit;
                        }
                    } else {
                        //Credit, continue to next line.
                        continue;
                    }
                }
            } else {
                //Debit, continue to next line
                continue;
            }
        }

        //Try to remove all records from all bins.
        //TODO: Is there a more efficient way?
        //for (DebtorsDetailedAgingHelper agingHelper : agingList) {
        //    agingHelper.getAgingLines().removeAll(toRemove);
        //}
    }

    /**
     * Returns the total unallocated credit at the specified date.
     *
     * @param customerId Customer id. If this is null, all unallocated credits
     * will be taken into account.
     * @param atDate At date.
     * @param userData User data.
     * @return The total unallocated credit at the specified date.
     */
    @Override
    public BigDecimal getTotalUnallocatedCredit(String customerId, Date atDate, EMCUserData userData) {
        BigDecimal outstandingCredit = null;

        //Get total outstanding credits for customer, which existed at atDate
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsOpenTransactions.class);
        query.addFieldAggregateFunction("credit", "SUM");
        query.addFieldAggregateFunction("creditAmountSettled", "SUM");

        //Customer is optional.
        if (customerId != null) {
            query.addAnd("customerId", customerId);
        }

        //Bev requested that we remove this check.  This will now show all unallocated credits,
        //regardless of whether or not they existed at atDate.
        //query.addAnd("createdDate", atDate, EMCQueryConditions.LESS_THAN_EQ);
        //Check that future payments are not included.
        query.addAnd("transactionDate", atDate, EMCQueryConditions.LESS_THAN_EQ);

        Object[] creditTotals = (Object[]) util.executeSingleResultQuery(query, userData);

        if (creditTotals != null) {
            BigDecimal totalCredit = creditTotals[0] == null ? BigDecimal.ZERO : (BigDecimal) creditTotals[0];
            BigDecimal totalCreditSettled = creditTotals[1] == null ? BigDecimal.ZERO : (BigDecimal) creditTotals[1];

            outstandingCredit = totalCredit.subtract(totalCreditSettled);
        }

        //If atDate less than today, ignore updates made to transactions between atDate and today.
        //Only check dates, not time
        Calendar atCalendar = Calendar.getInstance();
        atCalendar.setTime(atDate);
        atCalendar.set(Calendar.HOUR, 0);
        atCalendar.set(Calendar.MINUTE, 0);
        atCalendar.set(Calendar.SECOND, 0);
        atCalendar.set(Calendar.MILLISECOND, 0);

        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(Functions.nowDate());
        nowCalendar.set(Calendar.HOUR, 0);
        nowCalendar.set(Calendar.MINUTE, 0);
        nowCalendar.set(Calendar.SECOND, 0);
        nowCalendar.set(Calendar.MILLISECOND, 0);

        if (atCalendar.compareTo(nowCalendar) < 0) {
            EMCQuery creditQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlementHistory.class);
            creditQuery.addFieldAggregateFunction("creditSettled", "SUM");
            //Only include transactions that existed on atDate.  Bev requested that we remove this check.
            //creditQuery.addAnd("transactionCreatedDate", atDate, EMCQueryConditions.LESS_THAN_EQ);

            //Only include transactions that fall on or before atDate.
            creditQuery.addAnd("creditTransactionDate", atDate, EMCQueryConditions.LESS_THAN_EQ);

            //Customer Id is optional.
            if (customerId != null) {
                creditQuery.addAnd("customerId", customerId);
            }

            //Only include transactions settled after atDate
            creditQuery.addAnd("createdDate", atDate, EMCQueryConditions.GREATER_THAN);

            BigDecimal creditSettled = (BigDecimal) util.executeSingleResultQuery(creditQuery, userData);
            if (creditSettled == null) {
                creditSettled = BigDecimal.ZERO;
            }

            outstandingCredit = outstandingCredit.add(creditSettled);
        }

        return outstandingCredit;
    }

    /**
     * Returns balance at a specific date for either a specified customer, or
     * all customers.
     *
     * @param atDate Date at which balance should be calculated.
     * @param customerId Customer id. If this is null, a balance will be
     * calculated for all customers.
     * @param openingBalance Indicates whether an opening balance should be
     * retrieved. If this is true, one dat will be subtracted from atDate.
     * @param userData User data.
     * @return Balance at the specified date for either a specified customer, or
     * all customers.
     */
    @Override
    public BigDecimal getBalanceAtDate(Date atDate, String customerId, boolean openingBalance, DebtorsParameters param, EMCUserData userData) {
        BigDecimal balance = BigDecimal.ZERO;

        if (openingBalance) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(atDate);
            cal.add(Calendar.DAY_OF_YEAR, -1);

            atDate = cal.getTime();
        }

        //Use aging to get balance at date.
        List<DebtorsAgingHelper> balanceAgingHelpers = this.getDebtorsAging(customerId, atDate, DebtorsAgingMode.NONE, null, param, userData);

        for (DebtorsAgingHelper balanceAgingHelper : balanceAgingHelpers) {
            balance = balance.add(balanceAgingHelper.getBinAmount());
        }

        //Add unallocated credit to balance
        //Reverse sign.
        balance = balance.add(getTotalUnallocatedCredit(customerId, atDate, userData).multiply(new BigDecimal(-1)));

        return balance;
    }

    /**
     * This method return the outstanding balance of a customer as at now Date.
     * It does not unallocate anything.
     *
     * @param customerId
     * @param userData
     * @return outstanding debit
     */
    @Override
    public BigDecimal getBalance(String customerId, EMCUserData userData) {
        BigDecimal outstandingDebit = null;

        //Get total outstanding credits for customer, which existed at atDate
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsOpenTransactions.class);
        query.addFieldAggregateFunction("debit", "SUM");
        query.addFieldAggregateFunction("debitAmountSettled", "SUM");
        query.addFieldAggregateFunction("credit", "SUM");
        query.addFieldAggregateFunction("creditAmountSettled", "SUM");

        //Customer is optional.
        if (customerId != null) {
            query.addAnd("customerId", customerId);
        }

        query.addAnd("transactionDate", Functions.nowDate(), EMCQueryConditions.LESS_THAN_EQ);

        Object[] debitTotals = (Object[]) util.executeSingleResultQuery(query, userData);

        if (debitTotals != null) {
            BigDecimal totalDebit = debitTotals[0] == null ? BigDecimal.ZERO : (BigDecimal) debitTotals[0];
            BigDecimal totalDebitSettled = debitTotals[1] == null ? BigDecimal.ZERO : (BigDecimal) debitTotals[1];
            BigDecimal totalCredit = debitTotals[2] == null ? BigDecimal.ZERO : (BigDecimal) debitTotals[2];
            BigDecimal totalCreditSettled = debitTotals[3] == null ? BigDecimal.ZERO : (BigDecimal) debitTotals[3];
            outstandingDebit = totalDebit.subtract(totalDebitSettled);
            totalCredit = totalCredit.subtract(totalCreditSettled);
            outstandingDebit = outstandingDebit.subtract(totalCredit);
        }
        return outstandingDebit;
    }
}

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
import emc.messages.ServerDebtorsMessageEnum;
import emc.server.datehandler.EMCDateHandlerLocal;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * This bean is used to get Age Analysis summary totals for the N & L
 * 'Internal Age Analysis' report.  This bean should be called when
 * the 'Internal Age Analysis' parameter is active on the Age Analysis
 * reports.
 * 
 * @author riaan
 */
@Stateless
public class DebtorsInternalAgingBean extends EMCBusinessBean implements DebtorsInternalAgingLocal {

    @EJB
    private DebtorsParametersLocal debtorsParametersBean;
    @EJB
    private EMCDateHandlerLocal dateHandler;

    /** Creates a new instance of DebtorsAgingBean */
    public DebtorsInternalAgingBean() {
    }

    /**
     * Returns a list of DebtorsAgingHelper instances representing aging for either a
     * specified customer or all customers at the specified date.
     *
     * @param customerId Customer id.  If this is null, if will be ignored and aging will
     *                   be done across all transactions.
     * @param atDate Date which falls in the 'current' bin.
     * @param mode Aging mode to be used.  If this is null, it will be selected
     *        from the DebtorsParameters table.
     * @param period Aging period to be used.  If this is null, it will be selected
     *        from the DebtorsParameters table.
     * @param userData User data.
     *
     * @return Returns a list of DebtorsAgingHelper instances.
     */
    public List<DebtorsAgingHelper> getDebtorsInternalAging(String customerId, Date atDate, DebtorsAgingMode mode, DebtorsAgingPeriod period, EMCUserData userData) {
        if (atDate == null) {
            atDate = Functions.nowDate();
        }

        List<DebtorsAgingHelper> agingList = new ArrayList<DebtorsAgingHelper>();

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
            periodQuery.addOrderBy("startDate", GLFinancialPeriods.class.getName(), EMCQueryOrderByDirections.DESC);

            GLFinancialPeriods currentPeriod = (GLFinancialPeriods) util.executeSingleResultQuery(periodQuery, userData);

            if (currentPeriod != null) {
                //Add current period query
                EMCQuery query = templateQuery.copyQuery();
                query.addAnd("transactionDate", currentPeriod.getStartDate(), EMCQueryConditions.GREATER_THAN_EQ);
                query.addAnd("transactionDate", atDate, EMCQueryConditions.LESS_THAN_EQ);

                periodQueries.add(query);

                agingList.get(0).setBinStartDate(currentPeriod.getStartDate());
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

            List<GLFinancialPeriods> financialPeriods = (List<GLFinancialPeriods>) util.executeGeneralSelectQuery(periodQuery, userData);

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
                        query.addAnd("transactionDate", currentPeriod.getStartDate(), EMCQueryConditions.GREATER_THAN_EQ);
                        agingList.get(i + 1).setBinStartDate(currentPeriod.getStartDate());
                    }

                    query.addAnd("transactionDate", currentPeriod.getEndDate(), EMCQueryConditions.LESS_THAN_EQ);

                    periodQueries.add(query);

                    //Set aging bin dates.
                    agingList.get(i + 1).setBinEndDate(currentPeriod.getEndDate());
                }
            }
        }

        //Calculate totals for each bin
        switch (mode) {
            case NONE:
                calculateInternalAgingNONE(periodQueries, agingList, atDate, customerId, userData);
                break;
            case DATE:
                calculateInternalAgingDATE(periodQueries, agingList, atDate, customerId, userData);
                break;
            case OLDEST:
                calculateInternalAgingOLDEST(customerId, periodQueries, agingList, atDate, userData);
                break;
        }

        return agingList;
    }

    /**
     * Calculates the total outstanding debits from the specified queries, and sets
     * bin amounts in the corresponding positions in the aging list.
     * @param periodQueries List of queries selecting debits over a period.
     * @param agingList List of DebtorsAgingHelper instances.
     * @param atDate Date for which aging is being performed.
     * @param customerId Id of the customer for whom aging is being done.
     *                   If this is null, all transactions will be taken into account.
     * @param userData User data
     */
    private void calculateInternalAgingNONE(List<EMCQuery> periodQueries, List<DebtorsAgingHelper> agingList, Date atDate, String customerId, EMCUserData userData) {
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

                //For internal ageing reports, do not deallocate allocations where
                //both the debit and credit transactions fall before atDate.
                settledQuery.addAnd("creditTransactionDate", atDate, EMCQueryConditions.GREATER_THAN);

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
     * Calculates the total outstanding debits minus total outstanding credits from
     * the specified queries, and sets bin amounts in the corresponding positions
     * in the aging list.
     * @param periodQueries List of queries selecting debits over a period.
     * @param agingList List of DebtorsAgingHelper instances.
     * @param atDate Date for which aging is being performed.
     * @param customerId Id of the customer for whom aging is being done.
     *                   If this is null, all transactions will be taken into account.
     * @param userData User data
     */
    private void calculateInternalAgingDATE(List<EMCQuery> periodQueries, List<DebtorsAgingHelper> agingList, Date atDate, String customerId, EMCUserData userData) {
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

                //For internal ageing reports, do not deallocate allocations where
                //both the debit and credit transactions fall before atDate.
                settledQuery.addAnd("creditTransactionDate", atDate, EMCQueryConditions.GREATER_THAN);

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

                //For internal ageing reports, do not deallocate allocations where
                //both the debit and credit transactions fall before atDate.
                settledQuery.addAnd("creditTransactionDate", atDate, EMCQueryConditions.GREATER_THAN);

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
     * @param customerId Id of the customer for whom aging is being done.
     *                   If this is null, all transactions will be taken into account.
     * @param periodQueries List of queries selecting debits over a period.
     * @param agingList List of DebtorsAgingHelper instances.
     * @param atDate Date for which aging is being performed.
     * @param userData User data
     */
    private void calculateInternalAgingOLDEST(String customerId, List<EMCQuery> periodQueries, List<DebtorsAgingHelper> agingList, Date atDate, EMCUserData userData) {
        //First calculate total outstanding debits
        calculateInternalAgingNONE(periodQueries, agingList, atDate, customerId, userData);

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
    }

    /**
     * Returns the total unallocated credit at the specified date.
     * @param customerId Customer id.  If this is null, all unallocated credit
     *                                 will be taken into account.
     * @param atDate At date.
     * @param userData User data.
     * @return The total unallocated credit at the specified date.
     */
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

            //Only include Credit transactions that fall on or before atDate.
            creditQuery.addAnd("creditTransactionDate", atDate, EMCQueryConditions.LESS_THAN_EQ);
            //For internal ageing reports, do not deallocate allocations where
            //both the debit and credit transactions fall before atDate.
            creditQuery.addAnd("debitTransactionDate", atDate, EMCQueryConditions.GREATER_THAN);

            //Customer Id is optional.
            if (customerId != null) {
                creditQuery.addAnd("customerId", customerId);
            }

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
     * @param customerId Customer id.  If this is null, a balance will be calculated
     *                   for all customers.
     * @param openingBalance Indicates whether an opening balance should be retrieved.  If this is true, one dat will be subtracted from atDate.
     * @param userData User data.
     * @return Balance at the specified date for either a specified customer, or
     *         all customers.
     */
    public BigDecimal getBalanceAtDate(Date atDate, String customerId, boolean openingBalance, EMCUserData userData) {
        BigDecimal balance = BigDecimal.ZERO;

        if (openingBalance) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(atDate);
            cal.add(Calendar.DAY_OF_YEAR, -1);

            atDate = cal.getTime();
        }

        //Use aging to get balance at date.
        List<DebtorsAgingHelper> balanceAgingHelpers = this.getDebtorsInternalAging(customerId, atDate, DebtorsAgingMode.NONE, null, userData);

        for (DebtorsAgingHelper balanceAgingHelper : balanceAgingHelpers) {
            balance = balance.add(balanceAgingHelper.getBinAmount());
        }

        //Add unallocated credit to balance
        //Reverse sign.
        balance = balance.add(getTotalUnallocatedCredit(customerId, atDate, userData).multiply(new BigDecimal(-1)));

        return balance;
    }
}

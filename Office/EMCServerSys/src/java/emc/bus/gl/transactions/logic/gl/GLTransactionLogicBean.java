/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.gl.transactions.logic.gl;

import emc.bus.gl.financialperiods.GLFinancialPeriodsLocal;
import emc.bus.gl.transactionconsolidation.GLTransactionConsolidationTempLocal;
import emc.bus.gl.transactiondaysummary.GLTransactionDaySummaryLocal;
import emc.bus.gl.transactionperiodsummary.GLTransactionPeriodSummaryLocal;
import emc.bus.gl.transactions.logic.GLTransactionException;
import emc.bus.gl.transactions.logic.posthelpers.gl.GLJournalPostHelper;
import emc.bus.gl.transactionsdetail.GLTransactionsDetailLocal;
import emc.bus.gl.transactionssummary.GLTransactionsSummaryLocal;
import emc.bus.gl.vatcodes.GLVATCodeLocal;
import emc.entity.gl.GLChartOfAccounts;
import emc.entity.gl.GLFinancialPeriods;
import emc.entity.gl.journals.GLJournalLines;
import emc.entity.gl.journals.GLJournalMaster;
import emc.entity.gl.rules.GLGroupRules;
import emc.entity.gl.transactions.GLTransactionConsolidationTemp;
import emc.entity.gl.transactions.GLTransactionDaySummary;
import emc.entity.gl.transactions.GLTransactionPeriodSummary;
import emc.entity.gl.transactions.GLTransactionsDetail;
import emc.entity.gl.transactions.GLTransactionsSummary;
import emc.enums.enumQueryTypes;
import emc.enums.gl.GroupGranularityTypes;
import emc.enums.gl.TransactionSourceTypes;
import emc.enums.gl.VATInputOutput;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.StopWatchFactory;
import emc.messages.ServerGLMessageEnum;
import emc.server.commandmanager.EMCCommandManagerLocal;
import emc.utility.referencehelper.EMCReferenceHelper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author riaan
 */
@Stateless
public class GLTransactionLogicBean extends EMCBusinessBean implements GLTransactionLogicLocal {

    @EJB
    private GLTransactionsSummaryLocal transactionSummaryBean;
    @EJB
    private GLTransactionsDetailLocal transactionDetailBean;
    @EJB
    private GLTransactionDaySummaryLocal transactionDaySummaryBean;
    @EJB
    private GLTransactionPeriodSummaryLocal transactionPeriodSummaryBean;
    @EJB
    private GLFinancialPeriodsLocal financialPeriodBean;
    @EJB
    private GLVATCodeLocal vatCodeBean;
    @EJB
    private GLTransactionLogicLocal thisBean;
    @EJB
    private GLTransactionConsolidationTempLocal consolidationTempBean;
    @EJB
    private EMCCommandManagerLocal commandManager;

    /** Creates a new instance of GLTransactionLogicBean. */
    public GLTransactionLogicBean() {
    }

    /**
     * Resets all detail transactions to the unprocessed state, clears the
     * transaction summary, period summary and day summary tables and then
     * reprocesses detail transactions.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean regenerateSummaryTables(EMCUserData userData) throws EMCEntityBeanException {
        //Mark detail records as not being processed.
        EMCQuery query = new EMCQuery(enumQueryTypes.UPDATE, GLTransactionsDetail.class);
        query.addSet("processed", false);
        util.executeUpdate(query, userData);

        //Delete transaction summary records.
        query = new EMCQuery(enumQueryTypes.DELETE, GLTransactionsSummary.class);
        util.executeUpdate(query, userData);

        //Delete transaction day summary records
        query = new EMCQuery(enumQueryTypes.DELETE, GLTransactionDaySummary.class);
        util.executeUpdate(query, userData);

        //Delete transaction period summary records
        query = new EMCQuery(enumQueryTypes.DELETE, GLTransactionPeriodSummary.class);
        util.executeUpdate(query, userData);

        //Generate new data for summary tables.
        doGLConsolidation(userData);

        return true;
    }

    /**
     * This method update the transaction summary tables with the totals reflected
     * on the specified transaction.
     * @param callingContainerTransKey Transaction key of the container transaction
     * from which this method is called.  Used to roll back changes.
     * @param transaction GL transaction to consolidate.
     * @param userData User data.
     * @return A boolean indicating that the operation was successful.
     * @throws EMCEntityBeanException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean consolidateTransaction(final Object callingContainerTransKey, GLTransactionsDetail transaction, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLTransactionConsolidationTemp.class);
        query.setNativeForUpdate(true);

        util.executeNativeQuery(query, userData);

        StopWatchFactory fact = new StopWatchFactory();
        fact.start("summ");
        //Write transaction summary
        GLTransactionsSummary transactionSummary = writeTransactionSummary(transaction, userData);
        System.out.println("Consolidated Transaction Summary: " + fact.stop("summ"));
        fact.start("summ");
        //Write day summary
        GLTransactionDaySummary daySummary = writeTransactionDaySummary(transaction, userData);
        System.out.println("Consolidated Day Summary: " + fact.stop("summ"));
        fact.start("summ");
        //Write period summary
        GLTransactionPeriodSummary periodSummary = writeTransactionPeriodSummary(transaction, userData);
        System.out.println("Consolidated Period Summary: " + fact.stop("summ"));

        GLTransactionConsolidationTemp temp = new GLTransactionConsolidationTemp();
        temp.setContainerTransactionRef(callingContainerTransKey.hashCode());
        temp.setCredit(transaction.getCredit());
        temp.setDebit(transaction.getDebit());
        temp.setDaySummaryRef(daySummary.getRecordID());
        temp.setPeriodSummaryRef(periodSummary.getRecordID());
        temp.setTransactionSummaryRef(transactionSummary.getRecordID());

        temp = (GLTransactionConsolidationTemp) consolidationTempBean.insert(temp, userData);

        //No need to include company ID in queries - record ID is always unique.
        query = new EMCQuery(enumQueryTypes.DELETE, GLTransactionConsolidationTemp.class);
        query.addAnd("containerTransactionRef", callingContainerTransKey.hashCode());

        commandManager.setTransactionSucceedQuery(callingContainerTransKey, query.toString());
        commandManager.setTransactionFailQuery(callingContainerTransKey, query.toString());

        //Roll back updates to summary tables.
        query = new EMCQuery("UPDATE GLTransactionsSummary s SET s.credit = s.credit - " + transaction.getCredit() + ", s.debit = s.debit - " + transaction.getDebit() + " WHERE recordID = " + temp.getTransactionSummaryRef());
        commandManager.setTransactionFailQuery(callingContainerTransKey, query.toString());
        query = new EMCQuery("UPDATE GLTransactionDaySummary s SET s.credit = s.credit - " + transaction.getCredit() + ", s.debit = s.debit - " + transaction.getDebit() + " WHERE recordID = " + temp.getDaySummaryRef());
        commandManager.setTransactionFailQuery(callingContainerTransKey, query.toString());
        query = new EMCQuery("UPDATE GLTransactionPeriodSummary s SET s.credit = s.credit - " + transaction.getCredit() + ", s.debit = s.debit - " + transaction.getDebit() + " WHERE recordID = " + temp.getPeriodSummaryRef());
        commandManager.setTransactionFailQuery(callingContainerTransKey, query.toString());

        //Clear records without debit or credit values - assume that they relate to the detail record that failed to post.
        query = new EMCQuery("DELETE FROM GLTransactionsSummary s WHERE s.credit = 0 AND s.debit = 0");
        commandManager.setTransactionFailQuery(callingContainerTransKey, query.toString());
        query = new EMCQuery("DELETE FROM GLTransactionDaySummary s WHERE s.credit = 0 AND s.debit = 0");
        commandManager.setTransactionFailQuery(callingContainerTransKey, query.toString());
        query = new EMCQuery("DELETE FROM GLTransactionPeriodSummary s WHERE s.credit = 0 AND s.debit = 0");
        commandManager.setTransactionFailQuery(callingContainerTransKey, query.toString());

        return true;
    }

    /**
     * Consolidates GL transactions from the GLTransactionsDetail table into the
     * GLTransactionsSummary table.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean doGLConsolidation(EMCUserData userData) throws EMCEntityBeanException {
        //Account, group date/week/period, analysis code 1 - 6
        EMCReferenceHelper<GLTransactionsSummary> transactionSummaryReferenceHelper = new EMCReferenceHelper<GLTransactionsSummary>(8);
        populateTransactionSummaryReferenceHelper(transactionSummaryReferenceHelper, userData);

        //Account, date, analysis code 1 - 6
        EMCReferenceHelper<GLTransactionDaySummary> daySummaryReferenceHelper = new EMCReferenceHelper<GLTransactionDaySummary>(8);
        //Account, period, analysis code 1 - 6
        EMCReferenceHelper<GLTransactionPeriodSummary> periodSummaryReferenceHelper = new EMCReferenceHelper<GLTransactionPeriodSummary>(8);
        //Map account to group granularity
        Map<String, GroupGranularityTypes> accountGranularityMap = new HashMap<String, GroupGranularityTypes>();
        //Keep track of periods so that we don't have to select period information for every single transaction.
        PeriodReferenceHelper periodReferenceHelper = new PeriodReferenceHelper();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLTransactionsDetail.class);
        query.addAnd("processed", false);

        List<GLTransactionsDetail> transactions = (List<GLTransactionsDetail>) util.executeGeneralSelectQuery(query, userData);

        for (GLTransactionsDetail detail : transactions) {
            //Write transaction summary
            writeTransactionSummary(transactionSummaryReferenceHelper, accountGranularityMap, periodReferenceHelper, detail, userData);

            //Write day summary
            writeTransactionDaySummary(daySummaryReferenceHelper, detail, userData);

            //Write period summary
            writeTransactionPeriodSummary(periodSummaryReferenceHelper, periodReferenceHelper, detail, userData);

            detail.setProcessed(true);
            transactionDetailBean.update(detail, userData);
        }

        return true;
    }

    /** Writes the specified information to the transaction summary table. */
    private GLTransactionsSummary writeTransactionSummary(EMCReferenceHelper<GLTransactionsSummary> transactionSummaryReferenceHelper, Map<String, GroupGranularityTypes> accountGranularityMap, PeriodReferenceHelper periodReferenceHelper, GLTransactionsDetail detail, EMCUserData userData) throws EMCEntityBeanException {
        String account = detail.getAccountNumber();
        Object groupField = null;
        String analysisCode1 = detail.getAnalysisCode1();
        String analysisCode2 = detail.getAnalysisCode2();
        String analysisCode3 = detail.getAnalysisCode3();
        String analysisCode4 = detail.getAnalysisCode4();
        String analysisCode5 = detail.getAnalysisCode5();
        String analysisCode6 = detail.getAnalysisCode6();
        Date transactionDate = detail.getTransactionDate();
        BigDecimal credit = detail.getCredit();
        BigDecimal debit = detail.getDebit();

        GroupGranularityTypes accountGranularity = accountGranularityMap.get(account);
        if (accountGranularity == null) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLChartOfAccounts.class);
            query.addTableAnd(GLGroupRules.class.getName(), "groupRules", GLChartOfAccounts.class.getName(), "groupRules");
            query.addField("granularity", GLGroupRules.class.getName());
            query.addAnd("accountNumber", account);

            accountGranularity = GroupGranularityTypes.fromString((String) util.executeSingleResultQuery(query, userData));
            if (accountGranularity == null) {
                accountGranularity = GroupGranularityTypes.NONE;
            }
            accountGranularityMap.put(account, accountGranularity);
        }
        Date groupDate = null;
        int groupWeek = 0;
        String periodId = null;
        switch (accountGranularity) {
            case DAY:
                //Use transaction date.
                groupDate = detail.getTransactionDate();
                groupField = groupDate;
                break;
            case WEEK:
                //Use start date of week.
                groupDate = dateHandler.getFirstDateOfWeek(transactionDate, userData);
                groupWeek = dateHandler.getWeekNumber(transactionDate, userData);
                groupField = groupWeek;
                break;
            case PERIOD:
                //Use start date of period.
                PeriodInfo period = periodReferenceHelper.getPeriod(transactionDate);
                if (period == null) {
                    GLFinancialPeriods periodRecord = financialPeriodBean.findPeriodForDate(transactionDate, userData);
                    if (periodRecord != null) {
                        period = new PeriodInfo(periodRecord.getPeriodId(), periodRecord.getStartDate(), periodRecord.getEndDate());
                        periodReferenceHelper.addPeriod(period);
                    } else {
                        throw new EMCEntityBeanException("No period found for date.");
                    }
                }
                groupDate = period.getStartDate();
                periodId = period.getPeriodId();
                groupField = periodId;
                break;
            case NONE:
                groupDate = transactionDate;
                groupField = detail.getRecordID();  //Unique records on  summary,
                break;
        }
        GLTransactionsSummary summary = transactionSummaryReferenceHelper.get(account, groupField, analysisCode1, analysisCode2, analysisCode3, analysisCode4, analysisCode5, analysisCode6);
        if (summary == null) {
            //Try to select summary
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLTransactionsSummary.class);
            query.addAnd("accountNumber", account);
            query.addAnd("analysisCode1", analysisCode1);
            query.addAnd("analysisCode2", analysisCode2);
            query.addAnd("analysisCode3", analysisCode3);
            query.addAnd("analysisCode4", analysisCode4);
            query.addAnd("analysisCode5", analysisCode5);
            query.addAnd("analysisCode6", analysisCode6);
            query.addAnd("groupGranularity", accountGranularity.toString());
            query.addAnd("groupDate", groupDate);
            //Don't check group week/period.  Assume that granularity and date is sufficient.

            summary = (GLTransactionsSummary) util.executeSingleResultQuery(query, userData);

            //If no summary found, create new
            if (summary == null) {
                summary = new GLTransactionsSummary();
                summary.setAccountNumber(account);
                summary.setAnalysisCode1(analysisCode1);
                summary.setAnalysisCode2(analysisCode1);
                summary.setAnalysisCode3(analysisCode1);
                summary.setAnalysisCode4(analysisCode1);
                summary.setAnalysisCode5(analysisCode1);
                summary.setAnalysisCode6(analysisCode1);
                summary.setGroupGranularity(accountGranularity.toString());

                summary.setGroupDate(groupDate);

                switch (accountGranularity) {
                    case NONE:
                    //Fall through
                    case DAY:
                        //Do nothing.  Group date already set.
                        break;
                    case WEEK:
                        summary.setGroupWeek(dateHandler.getWeekNumber(transactionDate, userData));
                        break;
                    case PERIOD:
                        summary.setGroupPeriod(periodId);
                        break;
                }
                summary.setSourceReference(detail.getSourceReference());
                summary.setText(detail.getText());

                transactionSummaryBean.insert(summary, userData);
            }
            transactionSummaryReferenceHelper.put(summary, account, groupField, analysisCode1, analysisCode2, analysisCode3, analysisCode4, analysisCode5, analysisCode6);
        }

        summary.setCredit(summary.getCredit().add(credit));
        summary.setDebit(summary.getDebit().add(debit));
        summary.setForeignCurrencyCredit(summary.getForeignCurrencyCredit().add(detail.getForeignCurrencyCredit()));
        summary.setForeignCurrencyDebit(summary.getForeignCurrencyDebit().add(detail.getForeignCurrencyDebit()));

        transactionSummaryBean.update(summary, userData);
        return summary;
    }

    /** Writes the specified information to the transaction day summary table. */
    private GLTransactionDaySummary writeTransactionDaySummary(EMCReferenceHelper<GLTransactionDaySummary> daySummaryReferenceHelper, GLTransactionsDetail detail, EMCUserData userData) throws EMCEntityBeanException {
        String account = detail.getAccountNumber();
        String analysisCode1 = detail.getAnalysisCode1();
        String analysisCode2 = detail.getAnalysisCode2();
        String analysisCode3 = detail.getAnalysisCode3();
        String analysisCode4 = detail.getAnalysisCode4();
        String analysisCode5 = detail.getAnalysisCode5();
        String analysisCode6 = detail.getAnalysisCode6();
        Date transactionDate = detail.getTransactionDate();
        BigDecimal credit = detail.getCredit();
        BigDecimal debit = detail.getDebit();

        GLTransactionDaySummary daySummary = daySummaryReferenceHelper.get(account, transactionDate, analysisCode1, analysisCode2, analysisCode3, analysisCode4, analysisCode5, analysisCode6);
        if (daySummary == null) {
            //Try to select summary
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLTransactionDaySummary.class);
            query.addAnd("accountNumber", account);
            query.addAnd("analysisCode1", analysisCode1);
            query.addAnd("analysisCode2", analysisCode2);
            query.addAnd("analysisCode3", analysisCode3);
            query.addAnd("analysisCode4", analysisCode4);
            query.addAnd("analysisCode5", analysisCode5);
            query.addAnd("analysisCode6", analysisCode6);
            query.addAnd("financialDate", transactionDate);
            //Don't check group week/period.  Assume that granularity and date is sufficient.

            daySummary = (GLTransactionDaySummary) util.executeSingleResultQuery(query, userData);

            //If no summary found, create new
            if (daySummary == null) {
                daySummary = new GLTransactionDaySummary();
                daySummary.setFinancialDate(transactionDate);
                daySummary.setAccountNumber(account);
                daySummary.setAnalysisCode1(analysisCode1);
                daySummary.setAnalysisCode2(analysisCode2);
                daySummary.setAnalysisCode3(analysisCode3);
                daySummary.setAnalysisCode4(analysisCode4);
                daySummary.setAnalysisCode5(analysisCode5);
                daySummary.setAnalysisCode6(analysisCode6);

                transactionDaySummaryBean.insert(daySummary, userData);
            }
            daySummaryReferenceHelper.put(daySummary, account, transactionDate, analysisCode1, analysisCode2, analysisCode3, analysisCode4, analysisCode5, analysisCode6);
        }

        daySummary.setCredit(daySummary.getCredit().add(credit));
        daySummary.setDebit(daySummary.getDebit().add(debit));
        return daySummary;
    }

    /** Writes the specified information to the transaction period summary table. */
    private GLTransactionPeriodSummary writeTransactionPeriodSummary(EMCReferenceHelper<GLTransactionPeriodSummary> periodSummaryReferenceHelper, PeriodReferenceHelper periodReferenceHelper, GLTransactionsDetail detail, EMCUserData userData) throws EMCEntityBeanException {
        String account = detail.getAccountNumber();
        String analysisCode1 = detail.getAnalysisCode1();
        String analysisCode2 = detail.getAnalysisCode2();
        String analysisCode3 = detail.getAnalysisCode3();
        String analysisCode4 = detail.getAnalysisCode4();
        String analysisCode5 = detail.getAnalysisCode5();
        String analysisCode6 = detail.getAnalysisCode6();
        Date transactionDate = detail.getTransactionDate();
        BigDecimal credit = detail.getCredit();
        BigDecimal debit = detail.getDebit();
        PeriodInfo period = periodReferenceHelper.getPeriod(transactionDate);
        if (period == null) {
            GLFinancialPeriods periodRecord = financialPeriodBean.findPeriodForDate(transactionDate, userData);
            if (periodRecord != null) {
                period = new PeriodInfo(periodRecord.getPeriodId(), periodRecord.getStartDate(), periodRecord.getEndDate());
                periodReferenceHelper.addPeriod(period);
            } else {
                throw new EMCEntityBeanException("No period found for date.");
            }

        }
        GLTransactionPeriodSummary periodSummary = periodSummaryReferenceHelper.get(account, period, analysisCode1, analysisCode2, analysisCode3, analysisCode4, analysisCode5, analysisCode6);
        if (periodSummary == null) {
            //Try to select summary
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLTransactionPeriodSummary.class);
            query.addAnd("accountNumber", account);
            query.addAnd("analysisCode1", analysisCode1);
            query.addAnd("analysisCode2", analysisCode2);
            query.addAnd("analysisCode3", analysisCode3);
            query.addAnd("analysisCode4", analysisCode4);
            query.addAnd("analysisCode5", analysisCode5);
            query.addAnd("analysisCode6", analysisCode6);
            query.addAnd("financialPeriod", period.getPeriodId());
            //Don't check group week/period.  Assume that granularity and date is sufficient.

            periodSummary = (GLTransactionPeriodSummary) util.executeSingleResultQuery(query, userData);

            //If no summary found, create new
            if (periodSummary == null) {
                periodSummary = new GLTransactionPeriodSummary();
                periodSummary.setFinancialPeriod(period.getPeriodId());
                periodSummary.setAccountNumber(account);
                periodSummary.setAnalysisCode1(analysisCode1);
                periodSummary.setAnalysisCode2(analysisCode2);
                periodSummary.setAnalysisCode3(analysisCode3);
                periodSummary.setAnalysisCode4(analysisCode4);
                periodSummary.setAnalysisCode5(analysisCode5);
                periodSummary.setAnalysisCode6(analysisCode6);

                transactionPeriodSummaryBean.insert(periodSummary, userData);
            }
            periodSummaryReferenceHelper.put(periodSummary, account, period, analysisCode1, analysisCode2, analysisCode3, analysisCode4, analysisCode5, analysisCode6);
        }

        periodSummary.setCredit(periodSummary.getCredit().add(credit));
        periodSummary.setDebit(periodSummary.getDebit().add(debit));
        return periodSummary;
    }

    /** Writes the specified information to the transaction summary table. */
    private GLTransactionsSummary writeTransactionSummary(GLTransactionsDetail detail, EMCUserData userData) throws EMCEntityBeanException {
        String account = detail.getAccountNumber();
        Object groupField = null;
        String analysisCode1 = detail.getAnalysisCode1();
        String analysisCode2 = detail.getAnalysisCode2();
        String analysisCode3 = detail.getAnalysisCode3();
        String analysisCode4 = detail.getAnalysisCode4();
        String analysisCode5 = detail.getAnalysisCode5();
        String analysisCode6 = detail.getAnalysisCode6();
        Date transactionDate = detail.getTransactionDate();
        BigDecimal credit = detail.getCredit();
        BigDecimal debit = detail.getDebit();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLChartOfAccounts.class);
        query.addTableAnd(GLGroupRules.class.getName(), "groupRules", GLChartOfAccounts.class.getName(), "groupRules");
        query.addField("granularity", GLGroupRules.class.getName());
        query.addAnd("accountNumber", account);

        GroupGranularityTypes accountGranularity = GroupGranularityTypes.fromString((String) util.executeSingleResultQuery(query, userData));
        if (accountGranularity == null) {
            accountGranularity = GroupGranularityTypes.NONE;
        }

        Date groupDate = null;
        int groupWeek = 0;
        String periodId = null;
        switch (accountGranularity) {
            case DAY:
                //Use transaction date.
                groupDate = detail.getTransactionDate();
                groupField = groupDate;
                break;
            case WEEK:
                //Use start date of week.
                groupDate = dateHandler.getFirstDateOfWeek(transactionDate, userData);
                groupWeek = dateHandler.getWeekNumber(transactionDate, userData);
                groupField = groupWeek;
                break;
            case PERIOD:
                //Use start date of period.
                GLFinancialPeriods financialPeriod = financialPeriodBean.findPeriodForDate(transactionDate, userData);
                if (financialPeriod == null) {
                    throw new EMCEntityBeanException("No period found for date.");
                }

                groupDate = financialPeriod.getStartDate();
                periodId = financialPeriod.getPeriodId();
                groupField = periodId;
                break;
            case NONE:
                groupDate = transactionDate;
                groupField = detail.getRecordID();  //Unique records on  summary,
                break;
        }

        //Try to select summary
        query = new EMCQuery(enumQueryTypes.SELECT, GLTransactionsSummary.class);
        query.addAnd("accountNumber", account);
        query.addAnd("analysisCode1", analysisCode1);
        query.addAnd("analysisCode2", analysisCode2);
        query.addAnd("analysisCode3", analysisCode3);
        query.addAnd("analysisCode4", analysisCode4);
        query.addAnd("analysisCode5", analysisCode5);
        query.addAnd("analysisCode6", analysisCode6);
        query.addAnd("groupGranularity", accountGranularity.toString());
        query.addAnd("groupDate", groupDate);
        //Don't check group week/period.  Assume that granularity and date is sufficient.

        GLTransactionsSummary summary = (GLTransactionsSummary) util.executeSingleResultQuery(query, userData);

        //If no summary found, create new
        if (summary == null) {
            summary = new GLTransactionsSummary();
            summary.setAccountNumber(account);
            summary.setAnalysisCode1(analysisCode1);
            summary.setAnalysisCode2(analysisCode1);
            summary.setAnalysisCode3(analysisCode1);
            summary.setAnalysisCode4(analysisCode1);
            summary.setAnalysisCode5(analysisCode1);
            summary.setAnalysisCode6(analysisCode1);
            summary.setGroupGranularity(accountGranularity.toString());

            summary.setGroupDate(groupDate);

            switch (accountGranularity) {
                case NONE:
                    //Only set these fields for ungrouped summary records.
                    summary.setExternalReference(detail.getExternalReference());
                    summary.setSourceReference(detail.getSourceReference());
                    summary.setText(detail.getText());
                //Fall through
                case DAY:
                    //Do nothing.  Group date already set.
                    break;
                case WEEK:
                    summary.setGroupWeek(dateHandler.getWeekNumber(transactionDate, userData));
                    break;
                case PERIOD:
                    summary.setGroupPeriod(periodId);
                    break;
            }


            transactionSummaryBean.insert(summary, userData);
        }

        summary.setCredit(summary.getCredit().add(credit));
        summary.setDebit(summary.getDebit().add(debit));
        summary.setForeignCurrencyCredit(summary.getForeignCurrencyCredit().add(detail.getForeignCurrencyCredit()));
        summary.setForeignCurrencyDebit(summary.getForeignCurrencyDebit().add(detail.getForeignCurrencyDebit()));

        transactionSummaryBean.update(summary, userData);
        return summary;
    }

    /** Writes the specified information to the transaction day summary table. */
    private GLTransactionDaySummary writeTransactionDaySummary(GLTransactionsDetail detail, EMCUserData userData) throws EMCEntityBeanException {
        String account = detail.getAccountNumber();
        String analysisCode1 = detail.getAnalysisCode1();
        String analysisCode2 = detail.getAnalysisCode2();
        String analysisCode3 = detail.getAnalysisCode3();
        String analysisCode4 = detail.getAnalysisCode4();
        String analysisCode5 = detail.getAnalysisCode5();
        String analysisCode6 = detail.getAnalysisCode6();
        Date transactionDate = detail.getTransactionDate();
        BigDecimal credit = detail.getCredit();
        BigDecimal debit = detail.getDebit();

        //Try to select summary
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLTransactionDaySummary.class);
        query.addAnd("accountNumber", account);
        query.addAnd("analysisCode1", analysisCode1);
        query.addAnd("analysisCode2", analysisCode2);
        query.addAnd("analysisCode3", analysisCode3);
        query.addAnd("analysisCode4", analysisCode4);
        query.addAnd("analysisCode5", analysisCode5);
        query.addAnd("analysisCode6", analysisCode6);
        query.addAnd("financialDate", transactionDate);
        //Don't check group week/period.  Assume that granularity and date is sufficient.

        GLTransactionDaySummary daySummary = (GLTransactionDaySummary) util.executeSingleResultQuery(query, userData);

        //If no summary found, create new
        if (daySummary == null) {
            daySummary = new GLTransactionDaySummary();
            daySummary.setFinancialDate(transactionDate);
            daySummary.setAccountNumber(account);
            daySummary.setAnalysisCode1(analysisCode1);
            daySummary.setAnalysisCode2(analysisCode2);
            daySummary.setAnalysisCode3(analysisCode3);
            daySummary.setAnalysisCode4(analysisCode4);
            daySummary.setAnalysisCode5(analysisCode5);
            daySummary.setAnalysisCode6(analysisCode6);

            transactionDaySummaryBean.insert(daySummary, userData);
        }

        daySummary.setCredit(daySummary.getCredit().add(credit));
        daySummary.setDebit(daySummary.getDebit().add(debit));
        return daySummary;
    }

    /** Writes the specified information to the transaction period summary table. */
    private GLTransactionPeriodSummary writeTransactionPeriodSummary(GLTransactionsDetail detail, EMCUserData userData) throws EMCEntityBeanException {
        String account = detail.getAccountNumber();
        String analysisCode1 = detail.getAnalysisCode1();
        String analysisCode2 = detail.getAnalysisCode2();
        String analysisCode3 = detail.getAnalysisCode3();
        String analysisCode4 = detail.getAnalysisCode4();
        String analysisCode5 = detail.getAnalysisCode5();
        String analysisCode6 = detail.getAnalysisCode6();
        Date transactionDate = detail.getTransactionDate();
        BigDecimal credit = detail.getCredit();
        BigDecimal debit = detail.getDebit();

        GLFinancialPeriods financialPeriod = financialPeriodBean.findPeriodForDate(transactionDate, userData);
        if (financialPeriod == null) {
            throw new EMCEntityBeanException("No period found for date.");
        }

        //Try to select summary
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLTransactionPeriodSummary.class);
        query.addAnd("accountNumber", account);
        query.addAnd("analysisCode1", analysisCode1);
        query.addAnd("analysisCode2", analysisCode2);
        query.addAnd("analysisCode3", analysisCode3);
        query.addAnd("analysisCode4", analysisCode4);
        query.addAnd("analysisCode5", analysisCode5);
        query.addAnd("analysisCode6", analysisCode6);
        query.addAnd("financialPeriod", financialPeriod.getPeriodId());
        //Don't check group week/period.  Assume that granularity and date is sufficient.

        GLTransactionPeriodSummary periodSummary = (GLTransactionPeriodSummary) util.executeSingleResultQuery(query, userData);

        //If no summary found, create new
        if (periodSummary == null) {
            periodSummary = new GLTransactionPeriodSummary();
            periodSummary.setFinancialPeriod(financialPeriod.getPeriodId());
            periodSummary.setAccountNumber(account);
            periodSummary.setAnalysisCode1(analysisCode1);
            periodSummary.setAnalysisCode2(analysisCode2);
            periodSummary.setAnalysisCode3(analysisCode3);
            periodSummary.setAnalysisCode4(analysisCode4);
            periodSummary.setAnalysisCode5(analysisCode5);
            periodSummary.setAnalysisCode6(analysisCode6);

            transactionPeriodSummaryBean.insert(periodSummary, userData);
        }
        periodSummary.setCredit(periodSummary.getCredit().add(credit));
        periodSummary.setDebit(periodSummary.getDebit().add(debit));
        return periodSummary;
    }

    /**
     * This method posts transactions for a GL journal line.
     * @param postHelper Helper class containing information required for posting.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws emc.bus.gl.transactions.logic.GLTransactionException
     */
    public boolean postGLJournal(GLJournalPostHelper postHelper, EMCUserData userData) throws GLTransactionException {
        GLJournalLines journalLine = postHelper.getJournalLine();
        GLJournalMaster journalMaster = postHelper.getJournalMaster();

        try {
            //Indicates whether the VAT account should be debited or credited
            boolean debitVAT = false;
            BigDecimal totalVAT = BigDecimal.ZERO;
            if (journalLine.isVatIncluded()) {
                totalVAT = journalLine.getVatAmount();
            }
            if (journalLine.getDebit().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal debit = journalLine.getDebit();

                if (journalLine.isVatIncluded()) {
                    debit = debit.subtract(totalVAT);
                    debitVAT = true;
                }

                GLTransactionsDetail debitTransaction = new GLTransactionsDetail();
                debitTransaction.setAccountNumber(journalLine.getAccount());
                debitTransaction.setAnalysisCode1(journalLine.getAnalysisCode1());
                debitTransaction.setAnalysisCode2(journalLine.getAnalysisCode2());
                debitTransaction.setAnalysisCode3(journalLine.getAnalysisCode3());
                debitTransaction.setAnalysisCode4(journalLine.getAnalysisCode4());
                debitTransaction.setAnalysisCode5(journalLine.getAnalysisCode5());
                debitTransaction.setAnalysisCode6(journalLine.getAnalysisCode6());
                debitTransaction.setDebit(debit);
                debitTransaction.setText(journalLine.getText());
                debitTransaction.setSourceReference(journalMaster.getJournalNumber());
                debitTransaction.setTransactionSource(TransactionSourceTypes.GL_JOURNAL.toString());
                debitTransaction.setTransactionDate(journalLine.getLineDate());
                debitTransaction.setTransactionNumber(journalLine.getJournalTransId());
                debitTransaction.setVatCode(journalLine.getVatCode());
                debitTransaction.setExternalReference(journalLine.getExtReference());
                transactionDetailBean.insert(debitTransaction, userData);

                postHelper.addTransaction(debitTransaction);
            }

            if (journalLine.getCredit().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal credit = journalLine.getCredit();

                if (journalLine.isVatIncluded()) {
                    credit = credit.subtract(totalVAT);
                }

                GLTransactionsDetail creditTransaction = new GLTransactionsDetail();
                creditTransaction.setAccountNumber(journalLine.getAccount());

                creditTransaction.setAnalysisCode1(journalLine.getAnalysisCode1());
                creditTransaction.setAnalysisCode2(journalLine.getAnalysisCode2());
                creditTransaction.setAnalysisCode3(journalLine.getAnalysisCode3());
                creditTransaction.setAnalysisCode4(journalLine.getAnalysisCode4());
                creditTransaction.setAnalysisCode5(journalLine.getAnalysisCode5());
                creditTransaction.setAnalysisCode6(journalLine.getAnalysisCode6());
                creditTransaction.setText(journalLine.getText());
                creditTransaction.setCredit(credit);
                creditTransaction.setSourceReference(journalMaster.getJournalNumber());
                creditTransaction.setTransactionSource(TransactionSourceTypes.GL_JOURNAL.toString());
                creditTransaction.setTransactionDate(journalLine.getLineDate());
                creditTransaction.setTransactionNumber(journalLine.getJournalTransId());
                creditTransaction.setVatCode(journalLine.getVatCode());
                creditTransaction.setExternalReference(journalLine.getExtReference());
                transactionDetailBean.insert(creditTransaction, userData);

                postHelper.addTransaction(creditTransaction);
            }

            if (journalLine.isVatIncluded() && totalVAT.compareTo(BigDecimal.ZERO) != 0) {
                String vatAccount;
                VATInputOutput vatInputOutput = VATInputOutput.fromString(journalLine.getVatInputOutput());
                if (vatInputOutput == VATInputOutput.INPUT) {
                    vatAccount = vatCodeBean.getVATInputAccount(journalLine.getAccount(), userData);
                    if (isBlank(vatAccount)) {
                        throw new EMCEntityBeanException(getMessage(ServerGLMessageEnum.NO_VAT_INPUT_ACCOUNT, userData));
                    }
                } else if (vatInputOutput == VATInputOutput.OUTPUT) {
                    vatAccount = vatCodeBean.getVATOutputAccount(journalLine.getAccount(), userData);
                    if (isBlank(vatAccount)) {
                        throw new EMCEntityBeanException(getMessage(ServerGLMessageEnum.NO_VAT_OUTPUT_ACCOUNT, userData));
                    }
                } else {
                    //Do nothing
                    throw new EMCEntityBeanException(getMessage(ServerGLMessageEnum.NO_VAT_INPUT_OUTPUT, userData));
                }

                GLTransactionsDetail vatTransaction = new GLTransactionsDetail();
                vatTransaction.setAccountNumber(vatAccount);
                vatTransaction.setAnalysisCode1(journalLine.getAnalysisCode1());
                vatTransaction.setAnalysisCode2(journalLine.getAnalysisCode2());
                vatTransaction.setAnalysisCode3(journalLine.getAnalysisCode3());
                vatTransaction.setAnalysisCode4(journalLine.getAnalysisCode4());
                vatTransaction.setAnalysisCode5(journalLine.getAnalysisCode5());
                vatTransaction.setAnalysisCode6(journalLine.getAnalysisCode6());
                if (debitVAT) {
                    vatTransaction.setDebit(totalVAT);
                } else {
                    vatTransaction.setCredit(totalVAT);
                }
                
                vatTransaction.setText(journalLine.getText());
                vatTransaction.setSourceReference(journalMaster.getJournalNumber());
                vatTransaction.setTransactionSource(TransactionSourceTypes.GL_JOURNAL.toString());
                vatTransaction.setTransactionDate(journalLine.getLineDate());
                vatTransaction.setTransactionNumber(journalLine.getJournalTransId());
                vatTransaction.setVatCode(journalLine.getVatCode());
                vatTransaction.setExternalReference(journalLine.getExtReference());
                transactionDetailBean.insert(vatTransaction, userData);

                postHelper.addTransaction(vatTransaction);
            }

            //Post transaction to contra account
            if (!isBlank(journalLine.getContraAccount())) {
                GLTransactionsDetail contra = new GLTransactionsDetail();
                contra.setAccountNumber(journalLine.getContraAccount());

                //Only one amount (debit or credit) should be specified.
                contra.setCredit(journalLine.getContraCredit());
                contra.setDebit(journalLine.getContraDebit());

                contra.setAnalysisCode1(journalLine.getAnalysisCode1());
                contra.setAnalysisCode2(journalLine.getAnalysisCode2());
                contra.setAnalysisCode3(journalLine.getAnalysisCode3());
                contra.setAnalysisCode4(journalLine.getAnalysisCode4());
                contra.setAnalysisCode5(journalLine.getAnalysisCode5());
                contra.setAnalysisCode6(journalLine.getAnalysisCode6());
                contra.setText(journalLine.getText());
                contra.setSourceReference(journalMaster.getJournalNumber());
                contra.setTransactionSource(TransactionSourceTypes.GL_JOURNAL.toString());
                contra.setTransactionDate(journalLine.getLineDate());
                contra.setTransactionNumber(journalLine.getJournalTransId());
                contra.setVatCode(journalLine.getVatCode());
                contra.setExternalReference(journalLine.getExtReference());

                transactionDetailBean.insert(contra, userData);

                postHelper.addTransaction(contra);
            }
        } catch (EMCEntityBeanException ex) {
            throw new GLTransactionException(ex);
        }

        return true;
    }

    /**
     * Populates the specified reference helper with summary records for the specified
     * group criteria.  This method is intended to improve performance by selecting
     * data only once, rather than execute a new query whenever a distinct detail
     * record is found.
     * @param referenceHelper Reference helper to populate.
     * @param userData User data.
     */
    private void populateTransactionSummaryReferenceHelper(EMCReferenceHelper<GLTransactionsSummary> referenceHelper, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLTransactionsSummary.class);

        List<GLTransactionsSummary> summaryRecords = (List<GLTransactionsSummary>) util.executeGeneralSelectQuery(query);
        for (GLTransactionsSummary summary : summaryRecords) {
            referenceHelper.put(summary, summary.getAccountNumber(), summary.getGroupDate(), summary.getAnalysisCode1(), summary.getAnalysisCode2(), summary.getAnalysisCode3(), summary.getAnalysisCode4(), summary.getAnalysisCode5(), summary.getAnalysisCode6());
        }
    }
}

class PeriodReferenceHelper {

    private List<PeriodInfo> periods = new ArrayList<PeriodInfo>();

    /** Creates a new instance of PeriodReferenceHelper. */
    public PeriodReferenceHelper() {
    }

    /**
     * Returns the period in which the specified date falls.  If the period has
     * not been added to this reference helper yet, <code>null</code> will be
     * returned.
     * @param date Date to fetch period name for.
     * @return The period in which the specifed date falls, or null,
     * if the period has not been added to this reference helper.
     */
    public PeriodInfo getPeriod(Date date) {
        for (PeriodInfo period : periods) {
            Calendar dateCal = Calendar.getInstance();
            dateCal.setTime(date);
            dateCal.set(Calendar.HOUR_OF_DAY, 0);
            dateCal.set(Calendar.MINUTE, 0);
            dateCal.set(Calendar.SECOND, 0);
            dateCal.set(Calendar.MILLISECOND, 0);

            Calendar startDateCal = Calendar.getInstance();
            startDateCal.setTime(period.getStartDate());
            startDateCal.set(Calendar.HOUR_OF_DAY, 0);
            startDateCal.set(Calendar.MINUTE, 0);
            startDateCal.set(Calendar.SECOND, 0);
            startDateCal.set(Calendar.MILLISECOND, 0);

            Calendar endDateCal = Calendar.getInstance();
            endDateCal.setTime(period.getEndDate());
            endDateCal.set(Calendar.HOUR_OF_DAY, 0);
            endDateCal.set(Calendar.MINUTE, 0);
            endDateCal.set(Calendar.SECOND, 0);
            endDateCal.set(Calendar.MILLISECOND, 0);
            if (endDateCal.getTime().compareTo(dateCal.getTime()) >= 0 && startDateCal.getTime().compareTo(dateCal.getTime()) <= 0) {
                return period;
            }
        }
        return null;
    }

    /**
     * Adds period information to this reference helper.
     * @param periodInfo Period information.
     */
    public void addPeriod(PeriodInfo periodInfo) {
        this.periods.add(periodInfo);
    }
}

class PeriodInfo {

    private String periodId;
    private Date startDate;
    private Date endDate;

    /** Creates a new instance of PeriodInfo. */
    public PeriodInfo() {
    }

    public PeriodInfo(String periodName, Date startDate, Date endDate) {
        this.periodId = periodName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getPeriodId() {
        return periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

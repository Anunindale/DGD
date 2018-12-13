/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.gl.financialperiods;

import emc.bus.debtors.transactions.DebtorsTransactionsLocal;
import emc.entity.base.BaseParameters;
import emc.entity.gl.GLFinancialPeriods;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumPersistOptions;
import emc.enums.enumQueryTypes;
import emc.enums.gl.FinancialPeriodStatus;
import emc.enums.gl.FinancialPeriodTypes;
import emc.enums.gl.financialperiods.GLFinancialPeriodGenerationTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.messages.ServerGLMessageEnum;
import java.util.logging.Level;
import javax.ejb.EJB;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.ejb.Stateless;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author riaan
 */
@Stateless
public class GLFinancialPeriodsBean extends EMCEntityBean implements GLFinancialPeriodsLocal {

    @EJB
    private DebtorsTransactionsLocal debtorsTrans;

    /** Creates a new instance of GLFinancialPeriodsBean. */
    public GLFinancialPeriodsBean() {
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        updateCloseDate(iobject, userData);

        GLFinancialPeriods period = (GLFinancialPeriods) iobject;
        closeOpeningPeriod(period, userData);

        return super.insert(iobject, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        updateCloseDate(uobject, userData);

        GLFinancialPeriods period = (GLFinancialPeriods) uobject;
        closeOpeningPeriod(period, userData);

        return super.update(uobject, userData);
    }

    /**
     * It the specified period is an unclosed opening period, this method will close it.
     * @param period Period to close.
     * @param userData User data
     */
    private void closeOpeningPeriod(GLFinancialPeriods period, EMCUserData userData) {
        //Opening periods are closed automatically
        if (FinancialPeriodTypes.fromString(period.getPeriodType()) == FinancialPeriodTypes.OPENING) {
            period.setAccountStatus(FinancialPeriodStatus.CLOSED.toString());
        }
    }

    private boolean updateCloseDate(Object uobject, EMCUserData userData) {
        GLFinancialPeriods period = (GLFinancialPeriods) uobject;
        GLFinancialPeriods persisted = new GLFinancialPeriods();
        if (period.getRecordID() != 0) {
            persisted = (GLFinancialPeriods) this.findSQLVersionForEntity(period, userData);
        }
        if (isBlank(persisted.getAccountStatus()) || !persisted.getAccountStatus().equals(period.getAccountStatus())) {
            if (FinancialPeriodStatus.CLOSED.equals(FinancialPeriodStatus.fromString(period.getAccountStatus()))) {
                updateSubLedgersSetCloseDate(period.getEndDate(), userData);
            } else {
                EMCQuery findClosedPeriods = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
                findClosedPeriods.addAnd("accountStatus", FinancialPeriodStatus.CLOSED);
                findClosedPeriods.addAnd("endDate", Functions.date2SQLString(period.getStartDate()), EMCQueryConditions.LESS_THAN);
                findClosedPeriods.addOrderBy("endDate", GLFinancialPeriods.class.getName(), EMCQueryOrderByDirections.DESC);
                List<GLFinancialPeriods> foundPeriods = util.executeGeneralSelectQuery(findClosedPeriods, userData);
                if (foundPeriods != null && foundPeriods.size() > 0) {
                    updateSubLedgersSetCloseDate(foundPeriods.get(0).getEndDate(), userData);
                } else {
                    updateSubLedgersSetCloseDate(null, userData);
                }
            }
        }
        return true;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean res = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (res) {
            GLFinancialPeriods period = (GLFinancialPeriods) theRecord;
            //Checks Period Usage.  Account status and period names may still be changed if a period is in use.
            if (period.getRecordID() != 0 && !fieldNameToValidate.equals("accountStatus") && !fieldNameToValidate.equals("periodName")) {
                DefaultMutableTreeNode treeNode = testRelations(enumPersistOptions.DELETE, period, userData);
                Enumeration e;
                for (int i = 0; i < 4; i++) {
                    if (!treeNode.getChildAt(i).isLeaf()) {
                        logMessage(Level.SEVERE, ServerGLMessageEnum.FIN_PERIOD_USED, userData);
                        return false;
                    }
                }
            }
            if (fieldNameToValidate.equals("startDate")) {
                //check if start date falls in existing period
                EMCQuery toCheck = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
                toCheck.addAnd("startDate", period.getStartDate(), EMCQueryConditions.LESS_THAN_EQ);
                toCheck.addAnd("endDate", period.getStartDate(), EMCQueryConditions.GREATER_THAN_EQ);
                toCheck.addAnd("recordID", period.getRecordID(), EMCQueryConditions.NOT);
                //Start date may fall in opening period.
                toCheck.addAnd("periodType", FinancialPeriodTypes.OPENING.toString(), EMCQueryConditions.NOT);

                List result = util.executeGeneralSelectQuery(toCheck, userData);
                if (result.size() > 0) {
                    this.logMessage(Level.SEVERE, ServerGLMessageEnum.FIN_PERIOD_DATE, userData);
                    res = false;
                }
                //If new period, it must follow last period.
                if (period.getRecordID() == 0) {
                    toCheck = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
                    toCheck.addFieldAggregateFunction("endDate", "MAX");
                    //Opening period and normal period may start on the same date.
                    toCheck.addAnd("periodType", FinancialPeriodTypes.OPENING.toString(), EMCQueryConditions.NOT);

                    result = util.executeGeneralSelectQuery(toCheck, userData);
                    if (result.size() > 0 && !isBlank(result.get(0))) {
                        Calendar last = Calendar.getInstance();
                        last.setTime((Date) result.get(0));
                        last.add(Calendar.DATE, 1);
                        if (last.getTime().compareTo(period.getStartDate()) != 0) {
                            res = false;
                            this.logMessage(Level.SEVERE, ServerGLMessageEnum.FIN_PERIOD_MUST_FOLLOW, userData);
                        }
                    }
                }
                if (res && !isBlank(period.getStartDate()) && !isBlank(period.getEndDate())) {
                    if (setNumberOfWeeks(period, userData)) {
                        return period;
                    } else {
                        return false;
                    }
                }
            } else if (fieldNameToValidate.equals("endDate")) {
                //check if start date falls in existing period
                EMCQuery toCheck = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
                toCheck.addAnd("startDate", period.getEndDate(), EMCQueryConditions.LESS_THAN_EQ);
                toCheck.addAnd("endDate", period.getEndDate(), EMCQueryConditions.GREATER_THAN_EQ);
                toCheck.addAnd("recordID", period.getRecordID(), EMCQueryConditions.NOT);
                List result = util.executeGeneralSelectQuery(toCheck, userData);
                if (result.size() > 0) {
                    this.logMessage(Level.SEVERE, ServerGLMessageEnum.FIN_PERIOD_DATE, userData);
                    return false;
                }
                if (dateHandler.getDateDiffInDays(period.getStartDate(), period.getEndDate(), userData) < 7) {
                    this.logMessage(Level.SEVERE, ServerGLMessageEnum.FIN_PERIOD_TOO_SMALL, userData);
                    return false;
                }
                if (res && !isBlank(period.getStartDate()) && !isBlank(period.getEndDate())) {
                    if (setNumberOfWeeks(period, userData)) {
                        return period;
                    } else {
                        return false;
                    }
                }
            //only validate closing
            } else if (fieldNameToValidate.equals("accountStatus")) {
                if (isBlank(period.getStartDate()) || isBlank(period.getEndDate())) {
                    this.logMessage(Level.SEVERE, ServerGLMessageEnum.FIN_PERIOD_DATES_REQ, userData);
                    return false;
                }
                if (FinancialPeriodStatus.CLOSED.equals(FinancialPeriodStatus.fromString(period.getAccountStatus()))) {
                    //check if there are any open periods before this period if so - disallow the close.
                    EMCQuery findOpenPeriods = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
                    findOpenPeriods.addAnd("accountStatus", FinancialPeriodStatus.OPEN);
                    findOpenPeriods.addAnd("endDate", Functions.date2SQLString(period.getStartDate()), EMCQueryConditions.LESS_THAN);
                    List<GLFinancialPeriods> foundPeriods = util.executeGeneralSelectQuery(findOpenPeriods, userData);
                    if (foundPeriods != null && foundPeriods.size() > 0) {
                        this.logMessage(Level.SEVERE, ServerGLMessageEnum.FIN_PERIOD_CLOSE, userData);
                        return false;
                    }
                } else if (FinancialPeriodStatus.OPEN.equals(FinancialPeriodStatus.fromString(period.getAccountStatus()))) {
                    EMCQuery findOpenPeriods = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
                    findOpenPeriods.addAnd("accountStatus", FinancialPeriodStatus.CLOSED);
                    findOpenPeriods.addAnd("startDate", Functions.date2SQLString(period.getEndDate()), EMCQueryConditions.GREATER_THAN);
                    List<GLFinancialPeriods> foundPeriods = util.executeGeneralSelectQuery(findOpenPeriods, userData);
                    if (foundPeriods != null && foundPeriods.size() > 0) {
                        this.logMessage(Level.SEVERE, ServerGLMessageEnum.FIN_PERIOD_CLOSE, userData);
                        return false;
                    }
                }
            }
        }
        return res;
    }

    private boolean setNumberOfWeeks(GLFinancialPeriods period, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseParameters.class);
        BaseParameters parm = (BaseParameters) util.executeSingleResultQuery(query, userData);
        if (parm == null) {
            this.logMessage(Level.SEVERE, ServerGLMessageEnum.FIN_FAILED_DM_PARM, userData);
            return false;
        }

        Calendar fromCal = Calendar.getInstance();
        fromCal.setFirstDayOfWeek(parm.getFirstDayOfWeek());
        fromCal.setMinimalDaysInFirstWeek(parm.getMinDaysInFirstWeek());
        fromCal.setTime(period.getStartDate());
        fromCal.set(Calendar.DAY_OF_WEEK, parm.getFirstDayOfWeek());

        Calendar toCal = Calendar.getInstance();
        toCal.setFirstDayOfWeek(parm.getFirstDayOfWeek());
        toCal.setMinimalDaysInFirstWeek(parm.getMinDaysInFirstWeek());
        toCal.setTime(period.getEndDate());
        toCal.set(Calendar.DAY_OF_WEEK, parm.getFirstDayOfWeek());
        toCal.add(Calendar.DATE, 6);
        if (toCal.getTime().compareTo(period.getEndDate()) > 0) {
            toCal.add(Calendar.DATE, -7);
        }

        int numweeks = 0;
        while (fromCal.before(toCal)) {
            numweeks++;
            fromCal.add(Calendar.DATE, 7);
        }

        period.setNumberOfWeeks(numweeks);
        return true;
    }

    @Override
    public GLFinancialPeriods findFinancialPeriod(String periodId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
        query.addAnd("periodId", periodId);
        return (GLFinancialPeriods) util.executeSingleResultQuery(query, userData);
    }

    @Override
    public List<GLFinancialPeriods> findAllPeriodsBetween(String from, String to, EMCUserData userData) {
        EMCQuery fromQuery = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
        fromQuery.addAnd("periodId", from);
        fromQuery.addField("startDate");
        EMCQuery toQuery = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
        toQuery.addAnd("periodId", to);
        toQuery.addField("endDate");
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
        query.addAnd("startDate", fromQuery, EMCQueryConditions.GREATER_THAN_EQ);
        query.addAnd("endDate", toQuery, EMCQueryConditions.LESS_THAN_EQ);
        query.addOrderBy("startDate");
        return util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public GLFinancialPeriods findPeriodForDate(Date theDate, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
        query.addAnd("startDate", Functions.date2String(theDate), EMCQueryConditions.LESS_THAN_EQ);
        query.addAnd("endDate", Functions.date2String(theDate), EMCQueryConditions.GREATER_THAN_EQ);
        return (GLFinancialPeriods) util.executeSingleResultQuery(query, userData);
    }

    @Override
    public List<GLFinancialPeriods> findAllPeriodsBetweenDates(Date from, Date to, EMCUserData userData) {
        EMCQuery fromQuery = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
        fromQuery.addAnd("startDate", Functions.date2String(from), EMCQueryConditions.LESS_THAN_EQ);
        fromQuery.addAnd("endDate", Functions.date2String(from), EMCQueryConditions.GREATER_THAN_EQ);
        fromQuery.addField("startDate");
        checkCompanyId(fromQuery, userData);
        Date fromDate = (Date) util.executeSingleResultQuery(fromQuery, userData);
        if (isBlank(fromDate)) {
            fromDate = null;
            this.logMessage(Level.SEVERE, ServerGLMessageEnum.NOT_OPENING_PERIOD, userData);
        }
        EMCQuery toQuery = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
        toQuery.addAnd("startDate", Functions.date2String(to), EMCQueryConditions.LESS_THAN_EQ);
        toQuery.addAnd("endDate", Functions.date2String(to), EMCQueryConditions.GREATER_THAN_EQ);
        toQuery.addField("endDate");
        checkCompanyId(toQuery, userData);
        Date toDate = (Date) util.executeSingleResultQuery(toQuery, userData);
        if (isBlank(toDate)) {
            fromDate = null;
            this.logMessage(Level.SEVERE, ServerGLMessageEnum.NOT_CLOSING_PERIOD, userData);
        }
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
        query.addAnd("startDate", fromDate, EMCQueryConditions.GREATER_THAN_EQ);
        query.addAnd("endDate", toDate, EMCQueryConditions.LESS_THAN_EQ);
        query.addOrderBy("startDate");
        return util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public int findWeekInPeriod(Date theDate, GLFinancialPeriods period, int weekStart, int minDaysInFirstWeek) {
        if (period.getStartDate().after(theDate) || period.getEndDate().before(theDate)) {
            return -1;
        }
        if (period.getStartDate().equals(theDate)) {
            return 0;
        }
        if (period.getEndDate().equals(theDate)) {
            return period.getNumberOfWeeks() - 1;
        }

        Calendar fromCal = Calendar.getInstance();
        fromCal.setFirstDayOfWeek(weekStart);
        fromCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
        fromCal.setTime(period.getStartDate());
        fromCal.set(Calendar.DAY_OF_WEEK, weekStart);

        Calendar toCal = Calendar.getInstance();
        toCal.setFirstDayOfWeek(weekStart);
        toCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
        toCal.setTime(theDate);
        toCal.set(Calendar.DAY_OF_WEEK, weekStart);

        int numweeks = 0;
        while (fromCal.before(toCal)) {
            numweeks++;
            fromCal.add(Calendar.DATE, 7);
        }

        if (numweeks > period.getNumberOfWeeks() - 1) {
            numweeks--;
        }

        return numweeks;
    }

    private boolean updateSubLedgersSetCloseDate(Date toSet, EMCUserData userData) {
        debtorsTrans.setLastClosedDate(toSet, userData);
        return true;
    }

    /**
     * Updates the subledgers to any period changes.
     * @param userData
     */
    @Override
    public boolean updateSubLedgersCloseDates(EMCUserData userData) {
        debtorsTrans.loadLastClosedDate(userData);
        return true;
    }

    /**
     * Generates financial periods.
     * @param generationTypeStr This identifieds a generation type.  It must be
     * a valid string representation of the values in the GLFinancialPeriodGenerationTypes
     * enum.
     * @param from From date.
     * @param to To date.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws emc.framework.EMCEntityBeanException
     */
    @Override
    public boolean generateFinancialPeriods(final String generationTypeStr, final Date from, final Date to, EMCUserData userData) throws EMCEntityBeanException {
        DecimalFormat monthFormat = new DecimalFormat("00");
        Calendar fromCal = dateHandler.getCalendarWithEMCSettings(userData);
        fromCal.setTime(from);
        dateHandler.clearTimeFields(fromCal, userData);
        Calendar toCal = dateHandler.getCalendarWithEMCSettings(userData);
        toCal.setTime(to);
        dateHandler.clearTimeFields(toCal, userData);

        String year = Functions.date2String(from, "yy");
        int currentPeriod = 0;

        GLFinancialPeriodGenerationTypes generationType = GLFinancialPeriodGenerationTypes.fromString(generationTypeStr);
        //Generate opening period.
        GLFinancialPeriods period = new GLFinancialPeriods();
        period.setStartDate(from);
        period.setEndDate(from);
        period.setPeriodId(year + "-00");
        period.setPeriodName("Opening " + year);
        period.setPeriodType(FinancialPeriodTypes.OPENING.toString());
        period.setPeriodQuarter((int) Math.ceil((fromCal.get(Calendar.MONTH) + 1) / 3.0));   //Divide month by 3 (length of a quarter) to get quarter
        period.setNumberOfWeeks(1);

        insert(period, userData);

        Calendar periodCal = dateHandler.getCalendarWithEMCSettings(userData);
        periodCal.setTime(from);
        dateHandler.clearTimeFields(periodCal, userData);

        while (periodCal.compareTo(toCal) < 0) {
            period = new GLFinancialPeriods();
            period.setStartDate(periodCal.getTime());

            int weeksInPeriod = 0;

            switchCase:
            switch (generationType) {
                case MONTHLY:
                    //Go to start of month
                    periodCal.set(Calendar.DAY_OF_MONTH, 1);

                    int month = periodCal.get(Calendar.MONTH);
                    //Add all weeks in this month.
                    while (periodCal.get(Calendar.MONTH) == month) {
                        periodCal.add(Calendar.WEEK_OF_YEAR, 1);
                        weeksInPeriod++;
                        if (periodCal.compareTo(toCal) >= 0) {
                            //We've reached the last day, no need for further processing.
                            break switchCase;
                        }
                    }

                    //We've advanced one month; go back to last day of previous month.
                    periodCal.set(Calendar.DAY_OF_MONTH, 1);
                    periodCal.add(Calendar.DATE, -1);
                    break;
            }

            if (periodCal.compareTo(toCal) < 0) {
                period.setEndDate(periodCal.getTime());
                period.setPeriodId(year + "-" + monthFormat.format(++currentPeriod));
                period.setPeriodName(Functions.date2String(periodCal.getTime(), "MMMM ") + year);
            } else {
                //Last period
                period.setEndDate(toCal.getTime());
                period.setPeriodId(year + "-" + monthFormat.format(++currentPeriod));
                period.setPeriodName(Functions.date2String(toCal.getTime(), "MMMM ") + year);
            }
            period.setNumberOfWeeks(weeksInPeriod);

            //Go to start of next period
            periodCal.add(Calendar.DATE, 1);

            period.setPeriodType(FinancialPeriodTypes.NORMAL.toString());
            period.setPeriodQuarter((int) Math.ceil((periodCal.get(Calendar.MONTH) + 1) / 3.0));   //Divide month by 3 (length of a quarter) to get quarter
            period.setAccountStatus(FinancialPeriodStatus.OPEN.toString());

            insert(period, userData);
        }
        if (currentPeriod > 12) {
            logMessage(Level.WARNING, ServerGLMessageEnum.GENERATED_MORE_THAN_12_MONTHS, userData);
        }
        return true;
    }
}

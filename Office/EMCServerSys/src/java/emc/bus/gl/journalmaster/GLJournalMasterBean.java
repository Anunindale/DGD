/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.gl.journalmaster;

import emc.bus.base.journals.BaseJournalDefinitionLocal;
import emc.bus.base.journals.superclass.JournalMasterSuperBean;
import emc.bus.gl.financialperiods.GLFinancialPeriodsLocal;
import emc.bus.gl.journallines.GLJournalLinesLocal;
import emc.bus.gl.transactions.logic.GLTransactionPostLocal;
import emc.bus.gl.transactions.logic.GLTransactionPostType;
import emc.bus.gl.transactions.logic.posthelpers.gl.GLJournalPostHelper;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.base.journals.superclass.JournalMasterSuperClass;
import emc.entity.gl.GLFinancialPeriods;
import emc.entity.gl.journals.GLJournalLines;
import emc.entity.gl.journals.GLJournalMaster;
import emc.enums.base.journals.JournalStatus;
import emc.enums.base.journals.Modules;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.EMCMessageEnum;
import emc.messages.ServerGLMessageEnum;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class GLJournalMasterBean extends JournalMasterSuperBean implements GLJournalMasterLocal {

    @EJB
    private GLJournalLinesLocal journalLinesBean;
    @EJB
    private GLTransactionPostLocal transactionPostBean;
    @EJB
    private GLFinancialPeriodsLocal financialPeriodBean;
    @EJB
    private BaseJournalDefinitionLocal journalDefinitionBean;

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        GLJournalMaster master = (GLJournalMaster) iobject;
        master.setJournalStatus(JournalStatus.CAPTURED.toString());
        return super.insert(master, userData);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);

        GLJournalMaster journalMaster = (GLJournalMaster) vobject;
        ret = ret && validateVAT(journalMaster, userData);

        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);

        GLJournalMaster journalMaster = (GLJournalMaster) vobject;
        ret = ret && validateVAT(journalMaster, userData);

        return ret;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object ret = super.validateField(fieldNameToValidate, theRecord, userData);

        if (ret instanceof GLJournalMaster) {
            GLJournalMaster journalMaster = (GLJournalMaster) theRecord;

            if (fieldNameToValidate.equals("vatIncluded")) {
                if (!journalMaster.isVatIncluded()) {
                    journalMaster.setVatCode(null);
                    journalMaster.setVatInputOutput(null);
                    return journalMaster;
                }
            } else if (fieldNameToValidate.equals("journalDefinition")) {
                BaseJournalDefinitionTable definition = journalDefinitionBean.getJournalDefinition(journalMaster.getJournalDefinitionId(), Modules.GL, userData);

                if (definition != null) {
                    journalMaster.setJournalContraAccount(definition.getJournalContraAccount());
                    journalMaster.setJournalContraType(definition.getJournalContraType());
                }
            }
        }

        return ret;
    }

    /**
     * Checks that the VAT fields on the specified record have valid values.
     * @param record Record to check.
     * @param userData User data.
     * @return A boolean indicating whether the VAT fields on the specified
     * record have valid values.
     */
    private boolean validateVAT(GLJournalMaster record, EMCUserData userData) {
        if (record.isVatIncluded() && (isBlank(record.getVatCode()) || isBlank(record.getVatInputOutput()))) {
            logMessage(Level.SEVERE, ServerGLMessageEnum.VAT_INCLUDED, userData);
            return false;
        } else if (!record.isVatIncluded() && (!isBlank(record.getVatCode()) || !isBlank(record.getVatInputOutput()))) {
            logMessage(Level.SEVERE, ServerGLMessageEnum.VAT_NOT_INCLUDED, userData);
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected Modules getModule() {
        return Modules.GL;
    }

    @Override
    protected Class getJournalClass() {
        return GLJournalMaster.class;
    }

    @Override
    protected boolean doPost(JournalMasterSuperClass journalMaster, EMCUserData userData) throws EMCEntityBeanException {
        if (!checkBalanceWithinPeriods((GLJournalMaster) journalMaster, userData)) {
            throw new EMCEntityBeanException(getMessage(ServerGLMessageEnum.JOURNAL_NOT_BALANCE, userData));
        }

        List<GLJournalLines> lines = journalLinesBean.getJournalLines(journalMaster.getJournalNumber(), userData);

        for (GLJournalLines line : lines) {
            if (util.checkObjectsEqual(line.getAccount(), line.getContraAccount())) {
                throw new EMCEntityBeanException(getMessage(ServerGLMessageEnum.INVALID_CONTRA_ACCOUNT, userData, line.getDisplayLabelForField("account", userData), line.getDisplayLabelForField("contraAccount", userData), line.getDescription()));
            }
            GLJournalPostHelper postHelper = new GLJournalPostHelper();
            postHelper.setJournalLine(line);
            postHelper.setJournalMaster((GLJournalMaster) journalMaster);
            transactionPostBean.postTransactions(GLTransactionPostType.POST_GL_JOURNAL, postHelper, userData);
        }

        journalMaster.setJournalStatus(JournalStatus.POSTED.toString());
        journalMaster.setJournalPostedBy(userData.getUserName());
        journalMaster.setJournalPostedDate(Functions.nowDate());
        this.update(journalMaster, userData);

        return true;
    }

    @Override
    protected EMCMessageEnum getValidateSuccessMessage() {
        return ServerGLMessageEnum.VALIDATION_SUCCESS;
    }

    @Override
    protected EMCMessageEnum getValidateFailMessage() {
        return ServerGLMessageEnum.VALIDATION_FAIL;
    }

    /**
     * Fetches and returns the specified journal.
     * @param journalNumber Journal number.
     * @param userData User data.
     * @return The specified journal, or null, if it's not found.
     */
    public GLJournalMaster getJournalMaster(String journalNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLJournalMaster.class);
        query.addAnd("journalNumber", journalNumber);

        return (GLJournalMaster) util.executeSingleResultQuery(query, userData);
    }

    /**
     * Returns totals for each period on the specified journal.
     *
     * @param journalNumber
     * @param userData
     * @return Journal totals.
     */
    public List<Object[]> getJournalTotals(String journalNumber, EMCUserData userData) {
        //Get min and max dates on journal in order to get all periods on the journal
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLJournalLines.class);
        query.addAnd("journalNumber", journalNumber);
        query.addFieldAggregateFunction("lineDate", "MIN");
        query.addFieldAggregateFunction("lineDate", "MAX");

        Object[] results = (Object[]) util.executeSingleResultQuery(query, userData);
        if (results == null || results.length == 0 || results[0] == null || results[1] == null) {
            //Assume that there are no rows - nothing to validate
            return null;
        } else {
            List<Object[]> totalList = new ArrayList<Object[]>();
            //Check all periods on journal
            List<GLFinancialPeriods> periods = financialPeriodBean.findAllPeriodsBetweenDates((Date) results[0], (Date) results[1], userData);

            for (GLFinancialPeriods period : periods) {
                query = new EMCQuery(enumQueryTypes.SELECT, GLJournalLines.class);
                query.addAnd("journalNumber", journalNumber);
                query.addAnd("lineDate", period.getStartDate(), EMCQueryConditions.GREATER_THAN_EQ);
                query.addAnd("lineDate", period.getEndDate(), EMCQueryConditions.LESS_THAN_EQ);

                //Check that lines in period exist
                if (!util.exists(query, userData)) {
                    continue;
                }

                query.addFieldAggregateFunction("debit", "SUM");
                query.addFieldAggregateFunction("credit", "SUM");
                query.addFieldAggregateFunction("contraDebit", "SUM");
                query.addFieldAggregateFunction("contraCredit", "SUM");

                Object[] totals = (Object[]) util.executeSingleResultQuery(query, userData);
                if (totals != null && totals.length != 0 && totals[0] != null && totals[1] != null && totals[2] != null && totals[3] != null) {
                    totalList.add(new Object[]{period.getPeriodId(), ((BigDecimal) totals[0]).add((BigDecimal) totals[2]), ((BigDecimal) totals[1]).add((BigDecimal) totals[3])});
                }
            }
            return totalList;
        }
    }

    /**
     * Checks that all lines on the specified journal balance within their financial
     * periods.
     * @param master Journal master to be checked.
     * @param userData User data.
     * @return A boolean indicating whether the journal balances within financial
     * periods.
     */
    private boolean checkBalanceWithinPeriods(GLJournalMaster master, EMCUserData userData) {
        //Get min and max dates on journal in order to get all periods on the journal
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLJournalLines.class);
        query.addAnd(
                "journalNumber", master.getJournalNumber());
        query.addFieldAggregateFunction(
                "lineDate", "MIN");
        query.addFieldAggregateFunction(
                "lineDate", "MAX");

        Object[] results = (Object[]) util.executeSingleResultQuery(query, userData);







        if (results == null || results.length == 0 || results[0] == null || results[1] == null) {
            //Assume that there are no rows - nothing to validate
            return true;
        } else {
            //Check all periods on journal
            List<GLFinancialPeriods> periods = financialPeriodBean.findAllPeriodsBetweenDates((Date) results[0], (Date) results[1], userData);

            for (GLFinancialPeriods period : periods) {
                query = new EMCQuery(enumQueryTypes.SELECT, GLJournalLines.class);
                query.addAnd("journalNumber", master.getJournalNumber());
                query.addAnd("lineDate", period.getStartDate(), EMCQueryConditions.GREATER_THAN_EQ);
                query.addAnd("lineDate", period.getEndDate(), EMCQueryConditions.LESS_THAN_EQ);

                //Check that lines in period exist
                if (!util.exists(query, userData)) {
                    continue;
                }

                query.addFieldAggregateFunction("debit", "SUM");
                query.addFieldAggregateFunction("credit", "SUM");
                query.addFieldAggregateFunction("contraDebit", "SUM");
                query.addFieldAggregateFunction("contraCredit", "SUM");

                Object[] totals = (Object[]) util.executeSingleResultQuery(query, userData);
                if (totals == null || totals.length == 0 || totals[0] == null || totals[1] == null || totals[2] == null || totals[3] == null) {
                    //This should not happen - it would mean that there are lines without debit/credit values.
                    return false;
                } else {
                    BigDecimal debitTotal = ((BigDecimal) totals[0]).add((BigDecimal) totals[2]);
                    BigDecimal creditTotal = ((BigDecimal) totals[1]).add((BigDecimal) totals[3]);

                    if (debitTotal.compareTo(creditTotal) != 0) {
                        logMessage(Level.SEVERE, ServerGLMessageEnum.PERIOD_NOT_BALANCE, userData, period.getPeriodName());
                        return false;
                    }
                }
            }
        }
        return true;
    }
}

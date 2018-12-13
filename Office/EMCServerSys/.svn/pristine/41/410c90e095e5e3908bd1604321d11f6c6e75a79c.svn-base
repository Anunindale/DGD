/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.gl.journallines;

import emc.bus.base.journals.BaseJournalDefinitionLocal;
import emc.bus.gl.chartofaccounts.GLChartOfAccountsLocal;
import emc.bus.gl.journalmaster.GLJournalMasterLocal;
import emc.bus.gl.vatcodes.GLVATCodeLocal;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.gl.GLChartOfAccounts;
import emc.entity.gl.journals.GLJournalLines;
import emc.entity.gl.journals.GLJournalMaster;
import emc.enums.base.journals.JournalStatus;
import emc.enums.base.journals.Modules;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerBaseMessageEnum;
import emc.messages.ServerGLMessageEnum;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class GLJournalLinesBean extends EMCEntityBean implements GLJournalLinesLocal {

    @EJB
    private GLVATCodeLocal vatCodeBean;
    @EJB
    private GLJournalMasterLocal journalMasterBean;
    @EJB
    private GLChartOfAccountsLocal chartOfAccountsBean;
    @EJB
    private BaseJournalDefinitionLocal journalDefinitionBean;

    /** Creates a new instance of GLJournalLinesBean. */
    public GLJournalLinesBean() {
    }

    /**
     * Returns a List containing the lines on the specified journal.
     * @param journalNumber Journal number.
     * @param userData User data.
     * @return A List containing the lines on the specified journal.
     */
    public List<GLJournalLines> getJournalLines(String journalNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLJournalLines.class);
        query.addAnd("journalNumber", journalNumber);

        return (List<GLJournalLines>) util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        GLJournalLines journalLine = (GLJournalLines) iobject;

        GLJournalMaster journalMaster = journalMasterBean.getJournalMaster(journalLine.getJournalNumber(), userData);

        calculateLineVAT(journalLine, userData);
        checkUnapproveJournal(journalMaster, journalLine, userData);
        setValuesFromMaster(journalMaster, journalLine, userData);

        return super.insert(journalLine, userData);
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        GLJournalLines journalLine = (GLJournalLines) dobject;

        GLJournalMaster journalMaster = journalMasterBean.getJournalMaster(journalLine.getJournalNumber(), userData);

        checkUnapproveJournal(journalMaster, journalLine, userData);

        return super.delete(dobject, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        GLJournalLines journalLine = (GLJournalLines) uobject;

        GLJournalMaster journalMaster = journalMasterBean.getJournalMaster(journalLine.getJournalNumber(), userData);

        calculateLineVAT(journalLine, userData);
        checkUnapproveJournal(journalMaster, journalLine, userData);

        return super.update(journalLine, userData);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);

        if (ret) {
            GLJournalLines journalLine = (GLJournalLines) vobject;
            ret = ret && validateVAT(journalLine, userData);
            ret = ret && validateContraAccount(journalLine, userData);
            ret = ret && validateAnalysisCodes(journalLine, userData);
            ret = ret && validateAccount(journalLine, userData);
        }

        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);

        if (ret) {
            GLJournalLines journalLine = (GLJournalLines) vobject;
            ret = ret && validateVAT(journalLine, userData);
            ret = ret && validateContraAccount(journalLine, userData);
            ret = ret && validateAnalysisCodes(journalLine, userData);
            ret = ret && validateAccount(journalLine, userData);
        }

        return ret;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object ret = super.validateField(fieldNameToValidate, theRecord, userData);

        if (ret == Boolean.TRUE) {
            GLJournalLines journalLine = (GLJournalLines) theRecord;

            //If no analysis codes have been specified and line has not been saved yet, populate fields from master
            if (journalLine.getRecordID() == 0 && isBlank(journalLine.getAnalysisCode1()) && isBlank(journalLine.getAnalysisCode2()) &&
                    isBlank(journalLine.getAnalysisCode3()) && isBlank(journalLine.getAnalysisCode4()) &&
                    isBlank(journalLine.getAnalysisCode5()) && isBlank(journalLine.getAnalysisCode6())) {
                GLJournalMaster journalMaster = journalMasterBean.getJournalMaster(journalLine.getJournalNumber(), userData);

                if (isBlank(journalLine.getAnalysisCode1()) && !isBlank(journalMaster.getAnalysisCode1())) {
                    journalLine.setAnalysisCode1(journalMaster.getAnalysisCode1());
                }
                if (isBlank(journalLine.getAnalysisCode2()) && !isBlank(journalMaster.getAnalysisCode2())) {
                    journalLine.setAnalysisCode2(journalMaster.getAnalysisCode2());
                }
                if (isBlank(journalLine.getAnalysisCode3()) && !isBlank(journalMaster.getAnalysisCode3())) {
                    journalLine.setAnalysisCode3(journalMaster.getAnalysisCode3());
                }
                if (isBlank(journalLine.getAnalysisCode4()) && !isBlank(journalMaster.getAnalysisCode4())) {
                    journalLine.setAnalysisCode4(journalMaster.getAnalysisCode4());
                }
                if (isBlank(journalLine.getAnalysisCode5()) && !isBlank(journalMaster.getAnalysisCode5())) {
                    journalLine.setAnalysisCode5(journalMaster.getAnalysisCode5());
                }
                if (isBlank(journalLine.getAnalysisCode6()) && !isBlank(journalMaster.getAnalysisCode6())) {
                    journalLine.setAnalysisCode6(journalMaster.getAnalysisCode6());
                }
            }

            if ("vatIncluded".equals(fieldNameToValidate)) {
                calculateLineVAT(journalLine, userData);
                if (!journalLine.isVatIncluded()) {
                    journalLine.setVatCode(null);
                    return journalLine;
                }
            } else if ("debit".equals(fieldNameToValidate) || "credit".equals(fieldNameToValidate) || "vatCode".equals(fieldNameToValidate)) {
                if (!validateDebitCreditAmounts(journalLine, userData)) {
                    return false;
                }
                calculateLineVAT(journalLine, userData);
                checkSetContraAmount(journalLine, userData);
            } else if ("contraDebit".equals(fieldNameToValidate) || "contraCredit".equals(fieldNameToValidate)) {
                if (!validateContraDebitCreditAmounts(journalLine, userData)) {
                    return false;
                }
            } else if ("account".equals(fieldNameToValidate)) {
                setAnalysisCodesFromAccount(journalLine, userData);
            } else if (fieldNameToValidate.equals("contraAccount") || fieldNameToValidate.equals("contraType")) {
                GLJournalMaster journalMaster = journalMasterBean.getJournalMaster(journalLine.getJournalNumber(), userData);
                BaseJournalDefinitionTable definition = journalDefinitionBean.getJournalDefinition(journalMaster.getJournalDefinitionId(), Modules.GL, userData);

                if (definition != null && definition.getJournalContraFixed()) {
                    if ((fieldNameToValidate.equals("contraAccount") && !util.checkObjectsEqual(journalMaster.getJournalContraAccount(), journalLine.getContraAccount())) ||
                            (fieldNameToValidate.equals("contraType") && !util.checkObjectsEqual(journalMaster.getJournalContraType(), journalLine.getContraType()))) {
                        logMessage(Level.SEVERE, ServerBaseMessageEnum.CONTRA_FIXED, userData);
                        return false;
                    }
                }

                checkSetContraAmount(journalLine, userData);
            }

            return journalLine;
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
    private boolean validateVAT(GLJournalLines record, EMCUserData userData) {
        if (record.isVatIncluded() && isBlank(record.getVatCode())) {
            logMessage(Level.SEVERE, ServerGLMessageEnum.VAT_INCLUDED, userData);
            return false;
        } else if (!record.isVatIncluded() && !isBlank(record.getVatCode())) {
            logMessage(Level.SEVERE, ServerGLMessageEnum.VAT_NOT_INCLUDED, userData);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks that the contra account and amount fields on the specified journal
     * line are valid.  If contra account is blank, only one amount (debit or
     * credit) can be specified.  If contra account is not blank, both debit
     * and credit should be specified.  If account and contra account is the
     * same, log a warning message.
     * @param journalLine Journal line to validate.
     * @param userData User data.
     * @return A boolean indicating whether the specified line is valid.
     */
    private boolean validateContraAccount(GLJournalLines journalLine, EMCUserData userData) {
        if (util.checkObjectsEqual(journalLine.getAccount(), journalLine.getContraAccount())) {
            logMessage(Level.SEVERE, ServerGLMessageEnum.INVALID_CONTRA_ACCOUNT, userData, journalLine.getDisplayLabelForField("account", userData), journalLine.getDisplayLabelForField("contraAccount", userData), journalLine.getDescription() == null ? "" : journalLine.getDescription());
            return false;
        }

        if (!isBlank(journalLine.getContraAccount())) {
            if (!chartOfAccountsBean.canPostToAccount(journalLine.getContraAccount(), journalLine, userData)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates the account on the specified journal line; i.e., checks that journal
     * transactions may be posted to the account.
     * @param journalLine Journal line to validate.
     * @param userData User data.
     * @return A boolean indicating whether transactions may be posted to the specified account.
     */
    private boolean validateAccount(GLJournalLines journalLine, EMCUserData userData) {
        return chartOfAccountsBean.canPostToAccount(journalLine.getAccount(), journalLine, userData);
    }

    /**
     * Calculates the VAT amount included in the totals on the specified line.
     * @param line Line on which VAT should be calculated.
     * @param userData User data.
     */
    private void calculateLineVAT(GLJournalLines line, EMCUserData userData) {
        if (line.isVatIncluded() && !isBlank(line.getVatCode())) {
            BigDecimal vatPercentage = new BigDecimal(vatCodeBean.getVatPercentage(line.getVatCode(), userData));

            BigDecimal total = line.getDebit().compareTo(BigDecimal.ZERO) > 0 ? line.getDebit() : line.getCredit();
            BigDecimal oneHundred = new BigDecimal(100);
            //Round to as many decimals as possible when dividing, only round total VAT amount at end.
            BigDecimal vatAmount = (total.divide(vatPercentage.add(oneHundred), 10, RoundingMode.HALF_UP)).multiply(vatPercentage);
            line.setVatAmount(util.roundBigDecimal(vatAmount));
        } else {
            line.setVatAmount(BigDecimal.ZERO);
        }
    }

    /**
     * Checks whether the journal master that the specified line belongs to
     * should be unapproved due to the specified line being added/deleted/changed.
     * If the journal should be unapproved, this method will unapprove it.
     * @param journalMaster Journal master.
     * @param journalLine Journal line.
     * @param userData User data.
     * @throws emc.framework.EMCEntityBeanException
     */
    private void checkUnapproveJournal(GLJournalMaster journalMaster, GLJournalLines journalLine, EMCUserData userData) throws EMCEntityBeanException {
        JournalStatus status = JournalStatus.fromString(journalMaster.getJournalStatus());

        if (status == JournalStatus.APPROVED) {
            journalMasterBean.unApproveJournal(journalLine.getJournalNumber(), userData);
        }
    }

    /**
     * Sets journal line values from the specified journal master. Values to be set
     * includes VAT fields.  This method should only be called when a journal line
     * is being inserted.
     * @param journalMaster Journal master.
     * @param journalLine Journal line.
     * @param userData User data.
     */
    private void setValuesFromMaster(GLJournalMaster journalMaster, GLJournalLines journalLine, EMCUserData userData) {
        journalLine.setVatIncluded(journalMaster.isVatIncluded());
        journalLine.setVatCode(journalMaster.getVatCode());
        journalLine.setVatInputOutput(journalMaster.getVatInputOutput());

    }

    /**
     * Checks that the analysis codes on the specified journal line are valid.
     * @param journalLine Journal line to check.
     * @param userData User data.
     * @return A boolean indicating whether the analysis codes on the specified
     * journal line are valid.
     */
    private boolean validateAnalysisCodes(final GLJournalLines journalLine, final EMCUserData userData) {
        return chartOfAccountsBean.validateAnalysisCodes(journalLine.getAccount(), null, journalLine.getAnalysisCode1(), journalLine.getAnalysisCode2(), journalLine.getAnalysisCode3(), journalLine.getAnalysisCode4(), journalLine.getAnalysisCode5(), journalLine.getAnalysisCode6(), userData);
    }

    /**
     * Sets the analysis codes on the specified journal line to the codes specified
     * on the account referenced by the journal line.
     * @param journalLine Journal line on which analysis codes should be set.
     * @param userData User data.
     */
    private void setAnalysisCodesFromAccount(final GLJournalLines journalLine, final EMCUserData userData) {
        GLChartOfAccounts account = chartOfAccountsBean.getAccount(journalLine.getAccount(), userData);
        //Override anything currently on the account.
        journalLine.setAnalysisCode1(account.getAnalysisCode1());
        journalLine.setAnalysisCode2(account.getAnalysisCode2());
        journalLine.setAnalysisCode3(account.getAnalysisCode3());
        journalLine.setAnalysisCode4(account.getAnalysisCode4());
        journalLine.setAnalysisCode5(account.getAnalysisCode5());
        journalLine.setAnalysisCode6(account.getAnalysisCode6());
    }

    /**
     * Checks whether a contra amount should be set on the specified journal line.
     * If so, this method will set the contra debit/credit amount using the
     * debit/credit amounts specified on the line.  This method should be called
     * when debit/credit amounts or the contra account is changed.
     *
     * @param journalLine Journal line to check.
     * @param userData User data.
     */
    private void checkSetContraAmount(GLJournalLines journalLine, EMCUserData userData) {
        if (!isBlank(journalLine.getContraAccount())) {
            if (journalLine.getDebit().compareTo(BigDecimal.ZERO) > 0) {
                journalLine.setContraCredit(journalLine.getDebit());
            } else {
                journalLine.setContraDebit(journalLine.getCredit());
            }
        }
    }

    /**
     * Checks that the specified journal line does not have both a debit and
     * a credit amount specified.
     * @param journalLine Journal line to check.
     * @param userData User data.
     * @return A boolean indicating whether the amounts on the specified line are
     * valid.
     */
    private boolean validateDebitCreditAmounts(GLJournalLines journalLine, EMCUserData userData) {
        if (journalLine.getDebit().compareTo(BigDecimal.ZERO) != 0 && journalLine.getCredit().compareTo(BigDecimal.ZERO) != 0) {
            logMessage(Level.SEVERE, ServerGLMessageEnum.DEBIT_AND_CREDIT_SPECIFIED, userData);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks that the specified journal line does not have both a debit and
     * a credit amount specified against the contra account.
     * @param journalLine Journal line to check.
     * @param userData User data.
     * @return A boolean indicating whether the amounts on the specified line are
     * valid.
     */
    private boolean validateContraDebitCreditAmounts(GLJournalLines journalLine, EMCUserData userData) {
        if (journalLine.getContraDebit().compareTo(BigDecimal.ZERO) != 0 && journalLine.getContraCredit().compareTo(BigDecimal.ZERO) != 0) {
            logMessage(Level.SEVERE, ServerGLMessageEnum.CONTRA_DEBIT_AND_CREDIT_SPECIFIED, userData);
            return false;
        } else {
            return true;
        }
    }
}

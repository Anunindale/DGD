/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.gl.chartofaccounts;

import emc.bus.gl.grouprules.GLGroupRulesLocal;
import emc.entity.gl.GLChartOfAccounts;
import emc.entity.gl.journals.GLJournalLines;
import emc.entity.gl.rules.GLGroupRules;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerGLMessageEnum;
import emc.tables.EMCTable;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class GLChartOfAccountsBean extends EMCEntityBean implements GLChartOfAccountsLocal {

    @EJB
    private GLGroupRulesLocal groupRulesBean;
    
    /** Creates a new instance of GLChartOfAccountsBean. */
    public GLChartOfAccountsBean() {
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);

        if (ret) {
            GLChartOfAccounts account = (GLChartOfAccounts) vobject;

            ret = ret && validateAnalysisCodes(account.getAccountNumber(), account.getGroupRules(), account.getAnalysisCode1(), account.getAnalysisCode2(), account.getAnalysisCode3(), account.getAnalysisCode4(), account.getAnalysisCode5(), account.getAnalysisCode6(), userData);
        }

        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);

        if (ret) {
            GLChartOfAccounts account = (GLChartOfAccounts) vobject;

            ret = ret && validateAnalysisCodes(account.getAccountNumber(), account.getGroupRules(), account.getAnalysisCode1(), account.getAnalysisCode2(), account.getAnalysisCode3(), account.getAnalysisCode4(), account.getAnalysisCode5(), account.getAnalysisCode6(), userData);
        }

        return ret;
    }

    /**
     * Checks that the specified analysis codes conform to the group rules for the
     * specified account.
     * @param account Account number.
     * @param groupRules Group rules.  If not specified, this will be selected
     * using the account number.
     * @param analysisCode1 Analysis code 1.
     * @param analysisCode2 Analysis code 2.
     * @param analysisCode3 Analysis code 3.
     * @param analysisCode4 Analysis code 4.
     * @param analysisCode5 Analysis code 5.
     * @param analysisCode6 Analysis code 6.
     * @param userData User data.
     * @return A boolean indicating whether the specified group rules are valid
     * for the specified account.
     */
    @Override
    public boolean validateAnalysisCodes(final String account, final String groupRules, final String analysisCode1, final String analysisCode2, final String analysisCode3, final String analysisCode4, final String analysisCode5, final String analysisCode6, final EMCUserData userData) {
        GLGroupRules groupRulesRecord;
        if (isBlank(groupRules)) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLGroupRules.class);
            query.addTableAnd(GLChartOfAccounts.class.getName(), "groupRules", GLGroupRules.class.getName(), "groupRules");
            query.addAnd("accountNumber", account, GLChartOfAccounts.class.getName());

            groupRulesRecord = (GLGroupRules) util.executeSingleResultQuery(query, userData);
        } else {
            groupRulesRecord = groupRulesBean.getGroupRules(groupRules, userData);
        }

        if (groupRulesRecord == null) {
            logMessage(Level.WARNING, ServerGLMessageEnum.NO_GROUP_RULES_FOR_ACCOUNT, userData, account);
            return true;
        } else {
            //Check that all required codes on group rules have values.
            if (groupRulesRecord.getAnalysisCode1() && isBlank(analysisCode1)) {
                logMessage(Level.SEVERE, ServerGLMessageEnum.INVALID_ANALYSIS_CODES, userData, account, "1");
                return false;
            }
            if (groupRulesRecord.getAnalysisCode2() && isBlank(analysisCode2)) {
                logMessage(Level.SEVERE, ServerGLMessageEnum.INVALID_ANALYSIS_CODES, userData, account, "2");
                return false;
            }
            if (groupRulesRecord.getAnalysisCode3() && isBlank(analysisCode3)) {
                logMessage(Level.SEVERE, ServerGLMessageEnum.INVALID_ANALYSIS_CODES, userData, account, "3");
                return false;
            }
            if (groupRulesRecord.getAnalysisCode4() && isBlank(analysisCode4)) {
                logMessage(Level.SEVERE, ServerGLMessageEnum.INVALID_ANALYSIS_CODES, userData, account, "4");
                return false;
            }
            if (groupRulesRecord.getAnalysisCode5() && isBlank(analysisCode5)) {
                logMessage(Level.SEVERE, ServerGLMessageEnum.INVALID_ANALYSIS_CODES, userData, account, "5");
                return false;
            }
            if (groupRulesRecord.getAnalysisCode6() && isBlank(analysisCode6)) {
                logMessage(Level.SEVERE, ServerGLMessageEnum.INVALID_ANALYSIS_CODES, userData, account, "6");
                return false;
            }

            return true;
        }
    }

    /**
     * Returns the specified account.
     * @param accountNumber Account number to search for.
     * @param userData User data.
     * @return The account with the specified account number, or null, if the
     * account does not exist.
     */
    @Override
    public GLChartOfAccounts getAccount(final String accountNumber, final EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLChartOfAccounts.class);
        query.addAnd("accountNumber", accountNumber);

        return (GLChartOfAccounts) util.executeSingleResultQuery(query, userData);
    }

    /**
     * Returns a boolean indicating whether a transaction from the specified source may
     * be posted to the specified account.  This method will log a message if the
     * transaction may not be posted to the account.
     *
     * @param accountNumber Account number of account to post to.
     * @param transactionSource Source of transaction (e.g., GL journal)
     * @param userData User data.
     * @return A boolean indicating whether a transaction may be posted to the account.
     */
    @Override
    public boolean canPostToAccount(final String accountNumber, final Object transactionSource, final EMCUserData userData) {
        return canPostToAccount(getAccount(accountNumber, userData), transactionSource, userData);
    }

    /**
     * Returns a boolean indicating whether a transaction from the specified source may
     * be posted to the specified account.  This method will log a message if the
     * transaction may not be posted to the account.
     *
     * @param accountNumber Account number of account to post to.
     * @param transactionSource Source of transaction (e.g., GL journal)
     * @param userData User data.
     * @return A boolean indicating whether a transaction may be posted to the account.
     */
    @Override
    public boolean canPostToAccount(final GLChartOfAccounts account, final Object transactionSource, final EMCUserData userData) {
        if (account.isAccountClosed()) {
            logMessage(Level.SEVERE, ServerGLMessageEnum.ACC_CLOSED, userData, account.getAccountNumber());
            return false;
        }
        if (account.isLocked()) {
            //Check here for types not allowed
            if (transactionSource instanceof GLJournalLines) {
                logMessage(Level.SEVERE, ServerGLMessageEnum.ACC_LOCKED, userData, account.getAccountNumber());
                return false;
            }
        }

        return true;
    }
}

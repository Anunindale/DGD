/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.gl.chartofaccounts;

import emc.entity.gl.GLChartOfAccounts;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface GLChartOfAccountsLocal extends EMCEntityBeanLocalInterface {

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
    public boolean validateAnalysisCodes(final String account, final String groupRules, final String analysisCode1, final String analysisCode2, final String analysisCode3, final String analysisCode4, final String analysisCode5, final String analysisCode6, final EMCUserData userData);

    /**
     * Returns the specified account.
     * @param accountNumber Account number to search for.
     * @param userData User data.
     * @return The account with the specified account number, or null, if the
     * account does not exist.
     */
    public emc.entity.gl.GLChartOfAccounts getAccount(final java.lang.String accountNumber, final emc.framework.EMCUserData userData);

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
    public boolean canPostToAccount(final String accountNumber, final Object transactionSource, final EMCUserData userData);
    
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
    public boolean canPostToAccount(final GLChartOfAccounts account, final Object transactionSource, final EMCUserData userData);
}

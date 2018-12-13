package emc.bus.gl.transactionperiodsummary;

import emc.entity.gl.GLFinancialPeriods;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

@Local
/** 
 *
 * @author claudette
 */
public interface GLTransactionPeriodSummaryLocal extends EMCEntityBeanLocalInterface {

    public void setUniqueKey(emc.entity.gl.transactions.GLTransactionPeriodSummary summary, emc.framework.EMCUserData userData);

    /**
     * Creates opening balances for all accounts with transactions falling
     * in the year before the specified opening period.
     * @param openingPeriodId Opening period for which balances should be saved.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean generateOpeningBalances(String openingPeriodId, EMCUserData userData) throws EMCEntityBeanException;
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.logic.aging;

import emc.enums.debtors.parameters.DebtorsAgingMode;
import emc.enums.debtors.parameters.DebtorsAgingPeriod;
import emc.framework.EMCUserData;
import emc.helpers.debtors.DebtorsAgingHelper;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface DebtorsInternalAgingLocal {

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
    public List<DebtorsAgingHelper> getDebtorsInternalAging(String customerId, Date atDate, DebtorsAgingMode mode, DebtorsAgingPeriod period, EMCUserData userData);

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
    public BigDecimal getBalanceAtDate(Date atDate, String customerId, boolean openingBalance, EMCUserData userData);

    /**
     * Returns the total unallocated credit at the specified date.
     * @param customerId Customer id.  If this is null, all unallocated credit
     *                                 will be taken into account.
     * @param atDate At date.
     * @param userData User data.
     * @return The total unallocated credit at the specified date.
     */
    public BigDecimal getTotalUnallocatedCredit(String customerId, Date atDate, EMCUserData userData);
}

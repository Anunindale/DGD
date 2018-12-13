/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.logic.aging;

import emc.enums.debtors.parameters.DebtorsAgingMode;
import emc.enums.debtors.parameters.DebtorsAgingPeriod;
import emc.framework.EMCUserData;
import emc.helpers.debtors.DebtorsDetailedAgingHelper;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsAgingBean.
 *
 * @date : 21 Jun 2010
 *
 * @author : Riaan Nel
 *
 * @version : 1.0
 */
@Local
public interface DebtorsAgingLocal {

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
    public java.util.List<emc.helpers.debtors.DebtorsAgingHelper> getDebtorsAging(java.lang.String customerId, java.util.Date atDate, emc.enums.debtors.parameters.DebtorsAgingMode mode, emc.enums.debtors.parameters.DebtorsAgingPeriod period, emc.entity.debtors.DebtorsParameters parameters, emc.framework.EMCUserData userData);

    /**
     * Returns the total unallocated credit at the specified date.
     *
     * @param customerId Customer id.
     * @param atDate At date.
     * @param userData User data.
     * @return The total unallocated credit at the specified date.
     */
    public BigDecimal getTotalUnallocatedCredit(String customerId, Date atDate, EMCUserData userData);

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
    public List<DebtorsDetailedAgingHelper> getDetailedDebtorsAging(String customerId, Date atDate, DebtorsAgingMode mode, DebtorsAgingPeriod period, boolean includeSettled, EMCUserData userData);

    /**
     * This method return the outstanding balance of a customer as at now Date.
     * It does not unallocate anything.
     *
     * @param customerId
     * @param userData
     * @return outstanding debit
     */
    public BigDecimal getBalance(String customerId, EMCUserData userData);

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
    public java.math.BigDecimal getBalanceAtDate(java.util.Date atDate, java.lang.String customerId, boolean openingBalance, emc.entity.debtors.DebtorsParameters param, emc.framework.EMCUserData userData);
}

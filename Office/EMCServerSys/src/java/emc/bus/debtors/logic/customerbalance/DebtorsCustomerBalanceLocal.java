/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.logic.customerbalance;

import emc.framework.EMCUserData;
import emc.helpers.debtors.DebtorsCustomerBalanceHelper;
import javax.ejb.Local;

/**
 * @description : Local interface for CustomerBalanceBean.
 *
 * @date        : 21 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsCustomerBalanceLocal {

    /**
     * Returns a DebtorsCustomerBalanceHelper instance containing information about the specified customer.
     * @param customerId Customer id.
     * @param userData User data.
     * @return A DebtorsCustomerBalanceHelper instance containing information about the specified customer.
     */
    public DebtorsCustomerBalanceHelper getCustomerBalance(String customerId, EMCUserData userData);

    public java.math.BigDecimal getCreditsCustomerBalance(java.lang.String customerId, emc.framework.EMCUserData userData);

}

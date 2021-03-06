/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.logic.creditcheck;

import emc.entity.debtors.DebtorsParameters;
import emc.entity.sop.SOPCustomers;
import emc.enums.debtors.creditheld.DebtorsCreditHeldReason;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsCreditCheckLogicBean.
 *
 * @date        : 29 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsCreditCheckLogicLocal {

    /**
     * Returns a Credit Held reason indicating whether the specified customer qualifies for the specified credit amount (null means that the customer qualifies for credit).
     * @param customerId Customer id.
     * @param customer can be null pass in if available
     * @param parameters can be null if not available
     * @param atDate Used to get customer balance.
     * @param value Credit value to check.
     * @param checkOpenSO Indicates whether open sales orders should be checked.
     * @param checkOpenPL Indicates whether open picking lists should be checked.
     * @param logMessages Indicates whether messages should be logged.
     * @param userData User data.
     * @return A reason indicating why the credit check failed, or null, if it succeeded.
     */
    public DebtorsCreditHeldReason allowCredit(String customerId, SOPCustomers customer, DebtorsParameters parameters, Date atDate, BigDecimal value, boolean checkOpenSO, boolean checkOpenPL, boolean logMessages, EMCUserData userData);

    public emc.enums.debtors.creditheld.DebtorsCreditHeldReason allowToOrder(java.lang.String customerId, emc.framework.EMCUserData userData);
}

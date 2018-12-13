/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.postdatedpayment;

import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.Date;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsPostDatedPaymentBean.
 *
 * @date        : 19 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsPostDatedPaymentLocal extends EMCEntityBeanLocalInterface {

    /**
     * Creates a payment journal for all post dated payments in the specified date range.
     * @param from From date.
     * @param to To date.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean createPaymentJournal(Date from, Date to, String journalDefinition, EMCUserData userData) throws EMCEntityBeanException;

}

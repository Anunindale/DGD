/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.creditors.termsofpayment;

import emc.entity.creditors.CreditorsTermsOfPayment;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface CreditorsTermsOfPaymentLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns the CreditorsTermsOfPayment record with the specified id.
     * @param termsId Terms id.
     * @param userData User data.
     * @return The CreditorsTermsOfPayment record with the specified id, or null,
     *         if nothing is found.
     */
    public CreditorsTermsOfPayment getTermsOfPayment(String termsId, EMCUserData userData);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.creditors.termsofpayment;

import emc.entity.creditors.CreditorsTermsOfPayment;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;
 
/**
 *
 * @author riaan
 */
@Stateless
public class CreditorsTermsOfPaymentBean extends EMCEntityBean implements CreditorsTermsOfPaymentLocal {

    /** Creates a new instance of CreditorsTermsOfPayment bean */
    public CreditorsTermsOfPaymentBean() {
        
    }

    /**
     * Returns the CreditorsTermsOfPayment record with the specified id.
     * @param termsId Terms id.
     * @param userData User data.
     * @return The CreditorsTermsOfPayment record with the specified id, or null,
     *         if nothing is found.
     */
    public CreditorsTermsOfPayment getTermsOfPayment(String termsId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CreditorsTermsOfPayment.class);
        query.addAnd("termsOfPaymentId", termsId);

        return (CreditorsTermsOfPayment)util.executeSingleResultQuery(query, userData);
    }
}

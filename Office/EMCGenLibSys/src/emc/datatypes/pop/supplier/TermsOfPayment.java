/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.pop.supplier;

import emc.datatypes.EMCString;
import emc.entity.creditors.CreditorsTermsOfPayment;

/**
 *
 * @author wikus
 */
public class TermsOfPayment extends EMCString {

    /** Creates a new instance of TermOfPayment */
    public TermsOfPayment() {
        this.setEmcLabel("Terms");
        this.setRelatedTable(CreditorsTermsOfPayment.class.getName());
        this.setRelatedField("termsOfPaymentId");
        this.setEditable(true);
    }
}

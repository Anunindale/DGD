/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.creditors.termsofpayment.foreignkeys;

import emc.datatypes.creditors.settlementdicountgroups.foreignkeys.*;

/**
 * @description : Not mandatory data type.
 *
 * @date        : 28 Apr 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class TermsOfPaymentFKNotMandatory extends TermsOfPaymentFK {

    /** Creates a new instance of TermsOfPaymentFK. */
    public TermsOfPaymentFKNotMandatory() {
        this.setMandatory(false);
    }
}

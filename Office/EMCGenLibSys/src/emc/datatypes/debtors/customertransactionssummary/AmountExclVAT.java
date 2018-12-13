/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.customertransactionssummary;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author riaan
 */
public class AmountExclVAT extends EMCBigDecimal {

    /** Creates a new instance of AmountExclVAT */
    public AmountExclVAT() {
        this.setEmcLabel("Excl VAT");
    }
}

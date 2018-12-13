/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.invoicemastersuper;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class InvCNNumber extends EMCString {

    /** Creates a new instance of InvCNNumber. */
    public InvCNNumber() {
        this.setMandatory(true);
        this.setEmcLabel("Number");
    	this.setColumnWidth(68);
        this.setNumberSeqAllowed(true);
    }
}

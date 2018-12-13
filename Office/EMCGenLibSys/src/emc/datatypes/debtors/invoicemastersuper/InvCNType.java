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
public class InvCNType extends EMCString {

    /** Creates a new instance of InvCNType. */
    public InvCNType() {
        this.setMandatory(true);
        this.setEmcLabel("Type");
    	this.setColumnWidth(87);
        this.setEditable(false);
    }
}

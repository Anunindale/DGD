/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.creditheld;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class CreditHeldStatus extends EMCString {

    /** Creates a new instance of CreditHeldStatus. */
    public CreditHeldStatus() {
    	this.setColumnWidth(40);
        this.setEmcLabel("Status");
    }
}

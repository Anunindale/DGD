/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.creditnotes;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class ReturnOption extends EMCString {

    /** Creates a new instance of ReturnOption. */
    public ReturnOption() {
    	this.setColumnWidth(51);
        this.setEmcLabel("Return Option");
    }
}

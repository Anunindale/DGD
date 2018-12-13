/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.allocationimportfaillog;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class Credit extends EMCString {

    /** Creates a new instance of Credit. */
    public Credit() {
    	this.setColumnWidth(81);
        this.setEmcLabel("Credit");
    }
}

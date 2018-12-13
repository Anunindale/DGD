/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.allocationimport;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class ImportStatus extends EMCString {

    /** Creates a new instance of ImportStatus. */
    public ImportStatus() {
        this.setEmcLabel("Status");
    	this.setColumnWidth(67);
        this.setEditable(false);
    }
}

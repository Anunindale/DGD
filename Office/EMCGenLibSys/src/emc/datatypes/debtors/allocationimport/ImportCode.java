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
public class ImportCode extends EMCString {

    /** Creates a new instance of ImportCode. */
    public ImportCode() {
        this.setEmcLabel("Code");
    	this.setColumnWidth(36);
        this.setMandatory(true);
    }
}

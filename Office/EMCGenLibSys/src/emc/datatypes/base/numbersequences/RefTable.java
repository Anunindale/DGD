/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.numbersequences;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class RefTable extends EMCString { 

    /** Creates a new instance of RefTable */
    public RefTable() {
        this.setEmcLabel("Table");
        this.setColumnWidth(178);
        this.setEditable(true);
        this.setMandatory(true);
    }
}

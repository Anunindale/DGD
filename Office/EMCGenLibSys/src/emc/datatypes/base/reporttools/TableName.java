/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.reporttools;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class TableName extends EMCString {

    /** Creates a new instance of TableName */
    public TableName() {
        this.setEmcLabel("Table");
        this.setMandatory(true);
        this.setColumnWidth(40);
    }
}

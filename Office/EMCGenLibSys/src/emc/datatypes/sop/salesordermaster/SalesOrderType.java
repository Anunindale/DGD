/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.salesordermaster;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class SalesOrderType extends EMCString {

    /** Creates a new instance of SalesOrderType. */
    public SalesOrderType() {
    	this.setColumnWidth(67);
        this.setEmcLabel("Type");
    }
}

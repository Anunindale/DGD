/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.salesordermaster;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class SalesOrderStatus extends EMCString {

    public SalesOrderStatus() {
        this.setEmcLabel("Status");
    	this.setColumnWidth(72);
        this.setEditable(false);
    }
}

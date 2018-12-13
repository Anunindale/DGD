/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.reallocation;

import emc.datatypes.sop.salesordermaster.foreignkeys.SalesOrderNoFKNM;

/**
 *
 * @author riaan
 */
public class ToSalesOrderId extends SalesOrderNoFKNM {

    /** Creates a new instance of toSalesOrderId. */
    public ToSalesOrderId() {
    	this.setColumnWidth(65);
        this.setEmcLabel("To SO");
    }
}

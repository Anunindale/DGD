/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.customers;

import emc.datatypes.systemwide.Name;

/**
 * @description : Data type for customerName on SOPCustomers.
 *
 * @date        : 05 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class CustomerName extends Name {

    /** Creates a new instance of CustomerName */
    public CustomerName() {
        this.setMandatory(true);
    	this.setColumnWidth(213);
        this.setStringSize(30);
    }
}

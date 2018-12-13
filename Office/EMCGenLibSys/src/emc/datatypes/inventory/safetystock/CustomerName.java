/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.safetystock;

import emc.datatypes.datasources.sop.CustomerNameDS;

/**
 *
 * @author wikus
 */
public class CustomerName extends CustomerNameDS {

    public CustomerName() {
        super("customerId");
    	this.setColumnWidth(34);
        this.setEditable(false);
    }
}

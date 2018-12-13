/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.salesordermaster;

import emc.datatypes.datasources.sop.CustomerNameDS;

/**
 *
 * @author riaan
 */
public class CustomerName extends CustomerNameDS {

    /** Creates a new instance of CustomerName. */
    public CustomerName() {
        super("customerNo");
        this.setEmcLabel("Name");
    	this.setColumnWidth(192);
        this.setEditable(false);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.pricearangements;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class CustomerType extends EMCString{

    public CustomerType() {
        this.setEmcLabel("Type");
    	this.setColumnWidth(53);
        this.setMandatory(true);
    }

}

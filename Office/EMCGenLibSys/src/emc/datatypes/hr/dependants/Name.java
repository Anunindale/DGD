/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.dependants;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Name extends EMCString{

    public Name() {
        this.setEmcLabel("Name");
        this.setMandatory(false);
    	this.setColumnWidth(116);
        this.setLowerCaseAllowed(true);
    }

}

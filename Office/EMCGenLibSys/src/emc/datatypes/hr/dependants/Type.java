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
public class Type extends EMCString{

    public Type() {
        this.setEmcLabel("Type");
    	this.setColumnWidth(67);
        this.setMandatory(true);
    }



}

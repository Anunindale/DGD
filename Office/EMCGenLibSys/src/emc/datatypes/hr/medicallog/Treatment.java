/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.medicallog;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Treatment extends EMCString {

    public Treatment() {
    	this.setColumnWidth(266);
        this.setEmcLabel("Treatment");
    }
}

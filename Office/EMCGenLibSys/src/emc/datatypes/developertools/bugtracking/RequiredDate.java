/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.developertools.bugtracking;

import emc.datatypes.EMCDate;

/**
 *
 * @author rico
 */
public class RequiredDate extends EMCDate {
    public RequiredDate(){
    	this.setColumnWidth(59);
        this.setEmcLabel("Required");
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.employeetable;

import emc.datatypes.EMCString;

/**
 *
 * @author rico
 */
public class EmployeeId extends EMCString {
    public EmployeeId(){
        this.setEmcLabel("Employee Id");
        this.setColumnWidth(70);
        this.setMandatory(true);
        this.setNumberSeqAllowed(true);
        
    }

}

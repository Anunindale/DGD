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
public class Surname extends EMCString {
    
    public Surname(){
        this.setEmcLabel("Surname");
        this.setColumnWidth(155);
        this.setMandatory(true);
        this.setStringSize(40);
        this.setLowerCaseAllowed(true);
    } 

}

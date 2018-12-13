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
public class InternalTelephone extends EMCString {
    public InternalTelephone(){
        this.setEmcLabel("Internal Phone");
        this.setColumnWidth(30);
    }

}

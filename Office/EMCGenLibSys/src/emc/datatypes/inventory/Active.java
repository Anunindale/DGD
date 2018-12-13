/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory;

import emc.datatypes.EMCBoolean;

/**
 *
 * @author rico
 */
public class Active extends EMCBoolean {
    public Active(){
        this.setEmcLabel("Active");
        this.setColumnWidth(10);
    }

}

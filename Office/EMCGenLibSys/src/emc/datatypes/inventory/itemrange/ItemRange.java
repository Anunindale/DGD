/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemrange;

import emc.datatypes.EMCString;

/**
 *
 * @author rico
 */
public class ItemRange extends EMCString{
    public ItemRange(){
        this.setEmcLabel("Range");
        this.setMandatory(true);
        this.setColumnWidth(60);
    }

}

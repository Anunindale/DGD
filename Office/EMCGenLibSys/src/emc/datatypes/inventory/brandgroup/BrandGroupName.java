/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.brandgroup;

import emc.datatypes.EMCString;

/**
 *
 * @author rico
 */
public class BrandGroupName extends EMCString {
    public BrandGroupName(){
        this.setEmcLabel("Brand Group Name");
        this.setColumnWidth(100);
        this.setStringSize(100);
    }
}

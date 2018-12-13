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
public class BrandGroupId extends EMCString {
    public BrandGroupId(){
        this.setMandatory(true);
        this.setEmcLabel("Brand Grp Id");
        this.setColumnWidth(50);
    }

}

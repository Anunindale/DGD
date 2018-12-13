/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemmaster;

import emc.datatypes.EMCString;
import emc.entity.inventory.InventoryBrandGroup;

/**
 *
 * @author riaan
 */
public class BrandGroup extends EMCString {

    /** Created a new instance of BrandGroup */
    public BrandGroup() {
        this.setEmcLabel("Brand Group");
        this.setColumnWidth(80);
        this.setRelatedTable(InventoryBrandGroup.class.getName());
        this.setRelatedField("brandGroupId");
    }
}

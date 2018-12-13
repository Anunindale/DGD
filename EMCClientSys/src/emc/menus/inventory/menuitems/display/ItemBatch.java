/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory.menuitems.display;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class ItemBatch extends EMCMenuItem {

    //Creates a new instance of ItemBatch */
    public ItemBatch() {
        this.setClassPath("emc.forms.inventory.display.itembatch.ItemBatchForm");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Item Batches");
        this.setToolTipText("Edit item batches");
    }
}

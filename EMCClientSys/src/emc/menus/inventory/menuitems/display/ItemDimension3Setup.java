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
public class ItemDimension3Setup extends EMCMenuItem {

    /** Creates a new instance of ItemDimension3Setup */
    public ItemDimension3Setup() {
        this.setClassPath("emc.forms.inventory.display.itemdimensionsetup.ItemDimension3SetupForm");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Item Colours");
        this.setToolTipText("Setup colours on an item");
    }
}

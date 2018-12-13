/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.inventory.menuitems.output;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class ItemList extends EMCMenuItem {

    /** Creates a new instance of ItemList */
    public ItemList() {
       this.setClassPath("emc.forms.inventory.output.itemmaster.ItemMasterPrintForm");
       this.setMenuItemType(enumMenuItems.OUTPUT);
       this.setMenuItemName("Item List");
       this.setToolTipText("Prints a list of all items");
    }
}

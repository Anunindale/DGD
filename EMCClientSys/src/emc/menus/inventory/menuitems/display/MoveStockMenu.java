/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.inventory.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.inventory.display.movestock.InventoryMoveStockForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class MoveStockMenu extends EMCMenuItem {

    /** Creates a new instance of Classifications */
    public MoveStockMenu() {
        this.setClassPath(InventoryMoveStockForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Move Stock");
        this.setToolTipText("Move stock in a warehouse");
    }
}

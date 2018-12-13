/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.inventory.menuitems.display;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author rico
 */
public class Transactions extends EMCMenuItem {
    public Transactions() {
        this.setClassPath("emc.forms.inventory.display.transactions.InventoryTransactionsForm");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Transactions");
        this.setToolTipText("Inventory Transactions view");
    }

}

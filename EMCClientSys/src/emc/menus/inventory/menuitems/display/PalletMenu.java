/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.inventory.display.pallet.InventoryPalletForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class PalletMenu extends EMCMenuItem {

    /** Creates a new instance of PalletMenu */
    public PalletMenu() {
        this.setClassPath(InventoryPalletForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Pallet");
        this.setToolTipText("Edit pallets");
    }
}

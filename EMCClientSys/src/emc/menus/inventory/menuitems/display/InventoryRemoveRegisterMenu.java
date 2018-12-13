/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory.menuitems.display;

import emc.app.components.inventory.register.forms.remove.RemoveRegisterForm;
import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class InventoryRemoveRegisterMenu extends EMCMenuItem {

    public InventoryRemoveRegisterMenu() {
        this.setClassPath(RemoveRegisterForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Register: Issue");
        this.setToolTipText("Register Items");
    }
}

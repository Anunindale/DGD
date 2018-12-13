/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.inventory.display.dimensions.whereused.InventoryDimension1WhereUsedForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class InventoryDimension1WhereUsedMenu extends EMCMenuItem {

    /** Creates a new instance of  InventoryDimension1WhereUsedMenu*/
    public InventoryDimension1WhereUsedMenu() {
        this.setClassPath(InventoryDimension1WhereUsedForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Where Used - Configurations");
        this.setToolTipText("Where are configurations used");
    }
}

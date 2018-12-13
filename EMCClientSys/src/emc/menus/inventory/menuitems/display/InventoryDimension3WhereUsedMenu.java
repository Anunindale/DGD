/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.inventory.display.dimensions.whereused.InventoryDimension3WhereUsedForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class InventoryDimension3WhereUsedMenu extends EMCMenuItem {

    /** Creates a new instance of  InventoryDimension3WhereUsedMenu*/
    public InventoryDimension3WhereUsedMenu() {
        this.setClassPath(InventoryDimension3WhereUsedForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Where Used - Colour");
        this.setToolTipText("Where are colours used");
    }
}

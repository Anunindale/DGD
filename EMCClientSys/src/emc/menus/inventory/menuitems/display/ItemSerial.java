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
public class ItemSerial extends EMCMenuItem {

    /** Creates a new instance of ItemSerial */
    public ItemSerial() {
        this.setClassPath("emc.forms.inventory.display.itemserial.ItemSerialForm");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Item Serial");
        this.setToolTipText("Edit item serial numbers");
    }
}

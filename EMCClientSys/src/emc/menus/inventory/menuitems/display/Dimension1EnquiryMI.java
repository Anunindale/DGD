/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory.menuitems.display;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author claudette
 */
public class Dimension1EnquiryMI extends EMCMenuItem {

    public Dimension1EnquiryMI() {
        this.setClassPath("emc.forms.inventory.display.dimension1enquiry.InventoryDimension1EnquiryForm");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Where Used - Multi Colours");
        this.setToolTipText("View Configurations");
    }
}

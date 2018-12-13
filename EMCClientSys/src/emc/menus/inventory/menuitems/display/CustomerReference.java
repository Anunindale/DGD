/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.inventory.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.inventory.display.inventoryreference.CustomerReferenceForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class CustomerReference extends EMCMenuItem {
    
    public CustomerReference() {
        this.setClassPath(CustomerReferenceForm.class.getName());
        this.setMenuItemName("Customer Item Reference");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Setup customer item references.");
    }

}

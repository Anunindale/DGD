/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.debtors.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.debtors.display.courier.CourierForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class DebtorsCourier extends EMCMenuItem {

    /** Creates a new instance of DebtorsCourier */
    public DebtorsCourier() {
        this.setClassPath(CourierForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Couriers");
        this.setToolTipText("Create and edit couriers.");
    }
}

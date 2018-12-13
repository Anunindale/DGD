/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.inventory.menuitems.display;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class Parameters extends EMCMenuItem {

    public Parameters() {
        this.setClassPath("emc.forms.inventory.display.parameters.ParametersForm");
        this.setMenuItemName("Parameters");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Setup and change the Inventory Parameters");
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.forms.base.display.suburb.BaseSuburbForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class BaseSuburbMenu extends EMCMenuItem {

    /** Creates a new instance of  EMCMenuItem*/
    public BaseSuburbMenu() {
        this.setClassPath(BaseSuburbForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Suburb");
        this.setToolTipText("Setup and manage suburbs");
    }
}

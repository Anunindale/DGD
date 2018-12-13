/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.trec.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.trec.display.loadcompatibility.LoadCompatibilityForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author rico
 */
public class TRECLoadCompMenu extends EMCMenuItem {

    public TRECLoadCompMenu() {
        this.setClassPath(LoadCompatibilityForm.class.getName());
        this.setMenuItemName("Load Compatibility");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Setup and manage Load Compatibility.");
    }
}

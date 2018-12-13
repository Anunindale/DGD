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
public class Dimension1GroupSetup extends EMCMenuItem {

    /** Creates a new instance of Dimension1GroupSetup */
    public Dimension1GroupSetup() {
        this.setClassPath("emc.forms.inventory.display.dimension1groupsetup.Dimension1GroupSetupForm");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Config Group Setup");
        this.setToolTipText("Setup Config Groups");
    }
}
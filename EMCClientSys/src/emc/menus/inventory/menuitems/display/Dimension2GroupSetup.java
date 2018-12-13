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
public class Dimension2GroupSetup extends EMCMenuItem {

    /** Creates a new instance of Dimension2GroupSetup */
    public Dimension2GroupSetup() {
        this.setClassPath("emc.forms.inventory.display.dimension2groupsetup.Dimension2GroupSetupForm");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Size Group Setup");
        this.setToolTipText("Setup Size Groups");
    }
}
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
public class Dimension3GroupSetup  extends EMCMenuItem {

    /** Creates a new instance of Dimension3GroupSetup */
    public Dimension3GroupSetup() {
        this.setClassPath("emc.forms.inventory.display.dimension3groupsetup.Dimension3GroupSetupForm");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Colour Group Setup");
        this.setToolTipText("Setup Colour Groups");
    }
}

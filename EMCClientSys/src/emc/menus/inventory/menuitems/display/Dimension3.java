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
public class Dimension3 extends EMCMenuItem {

    /** Creates a new instance of Dimension3 */
    public Dimension3() {
        this.setClassPath("emc.forms.inventory.display.dimensions.Dimension3Form");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Colours");
        this.setToolTipText("Edit colours");
    }
}

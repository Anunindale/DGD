/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.developertools;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class EnumMethodMaker extends EMCMenuItem {
    /** Creates a new instance of EnumMethodMaker */
    public EnumMethodMaker() {
        this.setClassPath("emc.forms.developertools.display.enummethodmaker.EnumMethodMaker");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Enum Method Maker");
        this.setToolTipText("Display the Enum Method Maker form");
    }
}

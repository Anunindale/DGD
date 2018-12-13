/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.inventory.menuitems.display;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author rico
 */
public class AgeBinsMenuItem extends EMCMenuItem {
    public AgeBinsMenuItem(){
        this.setClassPath(emc.forms.inventory.display.ageingbins.AgeingBinsForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Age Bins");
        this.setToolTipText("Edit Age Bins for Report Purposes");
    }

}

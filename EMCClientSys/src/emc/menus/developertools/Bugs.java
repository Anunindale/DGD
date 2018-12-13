/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.developertools;

import emc.enums.enumMenuItems;
import emc.forms.developertools.display.bugtracking.BugsForm;
import emc.framework.EMCMenuItem;

/**
 * @Name         : Bugs
 *
 * @Date         : 14 Aug 2009
 *
 * @Description  : Menu item for the Bugs form in the Developer Tools module.
 *
 * @author       : riaan
 */
public class Bugs extends EMCMenuItem {

    /** Creates a new instance of Bugs. */
    public  Bugs() {
        this.setClassPath(BugsForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Tasks");
        this.setToolTipText("Log Tasks and Bugs");
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.hr.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.hr.display.absenteeismlog.HRAbsenteeismLogForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class HRAbsenteeismLogMenu extends EMCMenuItem {

    /** Creates a new instance of  HRAbsenteeismTypeMenu*/
    public HRAbsenteeismLogMenu() {
        this.setClassPath(HRAbsenteeismLogForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Absenteeism Log");
        this.setToolTipText("Setup and edit absenteeism");
    }
}

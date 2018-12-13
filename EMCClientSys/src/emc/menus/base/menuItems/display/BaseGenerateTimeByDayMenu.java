/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.forms.base.display.timebyday.resources.GenerateTimesForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class BaseGenerateTimeByDayMenu extends EMCMenuItem {

    /** Creates a new instance of  BaseTimeByDayMenu*/
    public BaseGenerateTimeByDayMenu() {
        this.setClassPath(GenerateTimesForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Generate Time By Day");
        this.setToolTipText("Generate Time By Day");
        this.setDoNotOpenForm(true);
    }
}

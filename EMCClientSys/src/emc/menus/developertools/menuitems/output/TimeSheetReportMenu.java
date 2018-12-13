/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.developertools.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.developertools.output.TimeSheetReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class TimeSheetReportMenu extends EMCMenuItem {

    public TimeSheetReportMenu() {
        this.setClassPath(TimeSheetReportForm.class.getName());
        this.setMenuItemName("Time Sheet");
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setToolTipText("Print time sheets.");
    }
}

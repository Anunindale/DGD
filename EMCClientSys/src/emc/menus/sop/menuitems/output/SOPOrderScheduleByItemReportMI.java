/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.sop.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.sop.output.SOPOrderScheduleByItemReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class SOPOrderScheduleByItemReportMI extends EMCMenuItem {

    /** Creates a new instance of SOPOrderScheduleReportMI. */
    public SOPOrderScheduleByItemReportMI() {
        this.setClassPath(SOPOrderScheduleByItemReportForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setMenuItemName("Order Schedule by Item");
        this.setToolTipText("View SOP Order Schedule by Item Report.");
    }
}

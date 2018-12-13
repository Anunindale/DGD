/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.workflow.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.workflow.display.activityalert.WFActivityAlertForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author rico
 */
public class WorkFlowActivityAlertsMenu extends EMCMenuItem {

    public WorkFlowActivityAlertsMenu() {
        this.setClassPath(WFActivityAlertForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Activity Alerts");
        this.setToolTipText("Setup an manage who receives activity alerts");
    }
}

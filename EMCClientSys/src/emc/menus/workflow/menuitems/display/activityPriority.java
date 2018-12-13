/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.workflow.menuitems.display;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author rico
 */
public class activityPriority extends EMCMenuItem {
    public activityPriority(){
       this.setClassPath("emc.forms.workflow.display.activitypriority.wfacpriorityForm");
       this.setMenuItemType(enumMenuItems.DISPLAY);
       this.setMenuItemName("Activity Priority");
       this.setToolTipText("Levels of Priority");
    }
   
}

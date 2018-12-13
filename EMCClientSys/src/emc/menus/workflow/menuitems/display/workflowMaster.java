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
public class workflowMaster extends EMCMenuItem {
    public workflowMaster(){
       this.setClassPath("emc.forms.workflow.display.workflow.wfMasterForm");
       this.setMenuItemType(enumMenuItems.DISPLAY);
       this.setMenuItemName("Work Flow");
       this.setToolTipText("Create, Edit additional WorkFlows");
    }
}
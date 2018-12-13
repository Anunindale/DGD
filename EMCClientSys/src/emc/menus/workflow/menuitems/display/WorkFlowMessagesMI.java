/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.workflow.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.workflow.display.messages.WorkFlowMessagesForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author Owner
 */
public class WorkFlowMessagesMI extends EMCMenuItem {

    public WorkFlowMessagesMI() {
        this.setClassPath(WorkFlowMessagesForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Messages");
        this.setToolTipText("Edit EMC Messages");
    }
}

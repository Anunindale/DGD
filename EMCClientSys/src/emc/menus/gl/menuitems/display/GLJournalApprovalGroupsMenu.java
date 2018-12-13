/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.gl.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.gl.display.journalapprovalgroups.GLJournalApprovalGroupsForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class GLJournalApprovalGroupsMenu extends EMCMenuItem {

    public GLJournalApprovalGroupsMenu() {
        this.setClassPath(GLJournalApprovalGroupsForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Journal Approval Groups");
        this.setToolTipText("Edit journal approval groups");
    }
}


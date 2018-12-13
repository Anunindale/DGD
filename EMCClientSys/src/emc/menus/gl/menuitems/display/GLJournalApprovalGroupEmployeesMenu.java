/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.gl.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.gl.display.journalapprovalgroupemployees.GLJournalApprovalGroupEmployeesForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class GLJournalApprovalGroupEmployeesMenu extends EMCMenuItem {

    public GLJournalApprovalGroupEmployeesMenu() {
        this.setClassPath(GLJournalApprovalGroupEmployeesForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Journal Approval Group Employees");
        this.setToolTipText("Edit journal approval group employees");
    }
}

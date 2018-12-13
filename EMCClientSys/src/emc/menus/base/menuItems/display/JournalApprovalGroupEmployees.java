/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.forms.base.display.journalapprovalgroupemployees.JournalApprovalGroupEmployeesForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class JournalApprovalGroupEmployees extends EMCMenuItem {

    /** Creates a new instance of JournalApprovalGroups */
    public JournalApprovalGroupEmployees() {
        this.setClassPath(JournalApprovalGroupEmployeesForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Journal Approval Group Employees");
        this.setToolTipText("Edit journal approval group employees");
    }
}
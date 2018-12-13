/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.forms.base.display.journalaccessgroups.JournalAccessGroupEmployeesForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class JournalAccessGroupEmployeesMI extends EMCMenuItem {
    
    /** Creates a new instance of JournalAccessGroupDefinitionsMI. */
    public JournalAccessGroupEmployeesMI() {
       this.setClassPath(JournalAccessGroupEmployeesForm.class.getName());
       this.setMenuItemType(enumMenuItems.DISPLAY);
       this.setMenuItemName("Journal Access Group Employees");
       this.setToolTipText("View and edit journal access group employees.");
    }
}

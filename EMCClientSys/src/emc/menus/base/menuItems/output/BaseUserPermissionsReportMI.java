/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base.menuItems.output;

import emc.enums.enumMenuItems;
import emc.forms.base.output.permissions.BaseUserPermissionsReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class BaseUserPermissionsReportMI extends EMCMenuItem {

    /** Creates a new instance of BaseUserPermissionsReportMI. */
    public BaseUserPermissionsReportMI() {
        this.setClassPath(BaseUserPermissionsReportForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setMenuItemName("User Permissions");
        this.setToolTipText("Prints a List of User Permissions.");
    }
}

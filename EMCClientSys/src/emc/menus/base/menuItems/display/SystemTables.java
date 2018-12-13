/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class SystemTables extends EMCMenuItem {

    /** Creates a new instance of SystemTables */
    public SystemTables() {
        this.setClassPath("emc.forms.base.display.systemtables.SystemTablesForm");
       this.setMenuItemType(enumMenuItems.DISPLAY);
       this.setMenuItemName("System Tables");
       this.setToolTipText("Edit EMC System Tables");
    }
}

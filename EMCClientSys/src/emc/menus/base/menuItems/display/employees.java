/*
 * employees.java
 *
 * Created on 11 December 2007, 08:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author rico
 */
public class employees extends EMCMenuItem {
    
    /** Creates a new instance of employees */
    public employees() {
       this.setClassPath("emc.forms.base.display.employees.employeeform");
       this.setMenuItemType(enumMenuItems.DISPLAY);
       this.setMenuItemName("Employees");
       this.setToolTipText("Add, Edit Employee Details");
    }
    
}

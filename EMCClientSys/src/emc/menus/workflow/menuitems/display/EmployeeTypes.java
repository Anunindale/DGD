/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.workflow.menuitems.display;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class EmployeeTypes extends EMCMenuItem {
    
    public EmployeeTypes() {
       this.setClassPath("emc.forms.workflow.display.employeetypes.EmployeeTypesForm");
       this.setMenuItemType(enumMenuItems.DISPLAY);
       this.setMenuItemName("Employee Types");
       this.setToolTipText("Types of employees employed by the company");
    }
}

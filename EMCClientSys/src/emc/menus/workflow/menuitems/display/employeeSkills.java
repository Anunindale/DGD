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
public class employeeSkills extends EMCMenuItem {
    public employeeSkills(){
       this.setClassPath("emc.forms.workflow.display.employeeskills.employeeSkillForm");
       this.setMenuItemType(enumMenuItems.DISPLAY);
       this.setMenuItemName("Employee Skills");
       this.setToolTipText("Link employees to skills");
    }
}

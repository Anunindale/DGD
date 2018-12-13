/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.base.menuItems.output;

import emc.enums.enumMenuItems;
import emc.forms.base.output.bdloggenericreport.BaseDBLogGenericReportForm;
import emc.forms.base.output.tasksheets.BaseTaskSheetsReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author rico
 */
public class BaseTaskSheetsReportMI extends EMCMenuItem {
    
    /** Creates a new instance of Postal codes menu item */
    public BaseTaskSheetsReportMI() {
       this.setClassPath(BaseTaskSheetsReportForm.class.getName());
       this.setMenuItemType(enumMenuItems.OUTPUT);
       this.setMenuItemName("Task Sheets");
       this.setToolTipText("Prints a generic report from the Task sheets");
    }
    
}
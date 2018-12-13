package emc.menus.hr.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.hr.display.jobstatus.HRJobStatusForm;
import emc.framework.EMCMenuItem;

/** 
*
* @author claudette
*/
public class HRJobStatusMI extends EMCMenuItem{

    /** Creates a new instance of HRJobStatusMI. */
    public HRJobStatusMI() {
        this.setClassPath(HRJobStatusForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Job Status");
        this.setToolTipText("View and Edit Job Status data");
    }
}
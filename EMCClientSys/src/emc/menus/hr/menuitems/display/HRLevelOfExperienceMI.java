package emc.menus.hr.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.hr.display.levelofexperience.HRLevelOfExperienceForm;
import emc.framework.EMCMenuItem;

/** 
*
* @author claudette
*/
public class HRLevelOfExperienceMI extends EMCMenuItem{

    /** Creates a new instance of HRLevelOfExperienceMI. */
    public HRLevelOfExperienceMI() {
        this.setClassPath(HRLevelOfExperienceForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Level Of Experience");
        this.setToolTipText("View and Edit Level Of Experience data");
    }
}
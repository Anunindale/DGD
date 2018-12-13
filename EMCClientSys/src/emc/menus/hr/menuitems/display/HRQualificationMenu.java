/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.hr.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.hr.display.qualification.HRQualificationForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class HRQualificationMenu extends EMCMenuItem {

    /** Creates a new instance of  HRNationalityMenu*/
    public HRQualificationMenu() {
        this.setClassPath(HRQualificationForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Qualification");
        this.setToolTipText("Setup and edit qualifications");
    }
}

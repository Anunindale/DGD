package emc.menus.gl.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.gl.display.groupcompanyaccount.GLGroupCompanyAccountForm;
import emc.framework.EMCMenuItem;

/** 
 *
 * @author claudette
 */
public class GLGroupCompanyAccountMI extends EMCMenuItem {

    /** Creates a new instance of GLGroupCompanyAccountMI. */
    public GLGroupCompanyAccountMI() {
        this.setClassPath(GLGroupCompanyAccountForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Group Company Account");
        this.setToolTipText("View and Edit Group Company Account data");
    }
}
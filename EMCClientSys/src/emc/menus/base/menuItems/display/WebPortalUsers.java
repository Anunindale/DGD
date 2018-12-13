/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.forms.base.display.webportalusers.WebPortalUsersForm;
import emc.framework.EMCMenuItem;

/**
 * @description Menu item for the Base Users table.
 *
 * @version     1.0 6 April 2010
 *
 * @author      Riaan Nel
 */
public class WebPortalUsers extends EMCMenuItem {

    /** Creates a new instance of WebPortalUsers */
    public WebPortalUsers() {
       this.setClassPath(WebPortalUsersForm.class.getName());
       this.setMenuItemType(enumMenuItems.DISPLAY);
       this.setMenuItemName("Web Portal Users");
       this.setToolTipText("Edit EMC Web Portal Users");
    }
}

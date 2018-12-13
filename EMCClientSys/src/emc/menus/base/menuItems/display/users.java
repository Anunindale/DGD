/*
 * users.java
 *
 * Created on 16 September 2007, 08:13
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
public class users extends EMCMenuItem {
   
    public users() {
       this.setClassPath(emc.forms.base.display.users.usersform.class.getName());
       this.setMenuItemType(enumMenuItems.DISPLAY);
       this.setMenuItemName("Users");
       this.setToolTipText("Add, remove users, reset password");
    }
   
}

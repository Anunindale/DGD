/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.forms.base.display.databaseconnection.BaseDataBaseConnectionForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class BaseDBConnectMenu extends EMCMenuItem {

    /** Creates a new instance of  EMCMenuItem*/
    public BaseDBConnectMenu() {
        this.setClassPath(BaseDataBaseConnectionForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("DB Connections");
        this.setToolTipText("Setup Database connections");
    }
}

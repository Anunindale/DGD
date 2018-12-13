/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.base.menuItems.action;

import emc.enums.enumMenuItems;
import emc.forms.base.action.ClearDBLogForm;
import emc.framework.EMCMenuItem;

/**
 * Menu item for DB Log maintenance form.
 *
 * @author riaan
 */
public class ClearDBLog extends EMCMenuItem {

    /** Creates a new instance of ClearDBLog. */
    public ClearDBLog() {
        this.setClassPath(ClearDBLogForm.class.getName());
        this.setMenuItemType(enumMenuItems.ACTION);
        this.setMenuItemName("Clear DB Log");
        this.setToolTipText("Clear DB Log");
    }
}

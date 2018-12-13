/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.gl.menuitems.display;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class GLChartOfAccountsMenu extends EMCMenuItem {

    /** Creates a new instance of GLCurrency */
    public GLChartOfAccountsMenu() {
        this.setClassPath(emc.forms.gl.display.chartofaccounts.GLChartOfAccountsForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Chart of Accounts");
        this.setToolTipText("Edit Chart of Accounts");
    }
}

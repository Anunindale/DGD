/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.gl.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.gl.display.chartofaccountsbalances.GLCharOfAccountsBalancesForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class GLChartOfAccountsBalancesMenu extends EMCMenuItem {

    /** Creates a new instance of GLCurrency */
    public GLChartOfAccountsBalancesMenu() {
        this.setClassPath(GLCharOfAccountsBalancesForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("COA Balances");
        this.setToolTipText("Edit Chart of Account Balances");
    }
}

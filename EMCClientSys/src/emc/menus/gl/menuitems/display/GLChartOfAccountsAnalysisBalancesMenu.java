/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.gl.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.gl.display.chartofaccountsanalysisbalances.GLChartOfAccountsAnalysisBalancesForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class GLChartOfAccountsAnalysisBalancesMenu extends EMCMenuItem {

    /** Creates a new instance of GLCurrency */
    public GLChartOfAccountsAnalysisBalancesMenu() {
        this.setClassPath(GLChartOfAccountsAnalysisBalancesForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("COA Analysis Balances");
        this.setToolTipText("Edit Chart of Account Analysis Balances");
    }
}

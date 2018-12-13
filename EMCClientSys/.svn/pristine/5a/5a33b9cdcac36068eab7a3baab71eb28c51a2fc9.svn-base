/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.gl;

import emc.framework.EMCMenu;
import emc.menus.gl.menuitems.display.GLChartOfAccountsAnalysisBalancesMenu;
import emc.menus.gl.menuitems.display.GLChartOfAccountsBalancesMenu;
import emc.menus.gl.menuitems.display.GLTransactionDaySummaryMI;
import emc.menus.gl.menuitems.display.GLTransactionPeriodSummaryMI;
import emc.menus.gl.menuitems.display.GLTransactionsDetailMI;
import emc.menus.gl.menuitems.display.GLTransactionsSummaryMI;

/**
 *
 * @author riaan
 */
public class GLEnquiry extends EMCMenu {

    /** Creates a new instance of GLEnquiry*/
    public GLEnquiry() {
        this.setMenuName("Enquiry");
//        this.setMenuList(new GLChartOfAccountsAnalysisBalancesMenu());
//        this.setMenuList(new GLChartOfAccountsBalancesMenu());
        this.setMenuList(new GLTransactionDaySummaryMI());
        this.setMenuList(new GLTransactionsDetailMI());
        this.setMenuList(new GLTransactionPeriodSummaryMI());
        this.setMenuList(new GLTransactionsSummaryMI());
    }
}

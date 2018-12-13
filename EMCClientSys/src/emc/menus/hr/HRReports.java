/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.hr;

import emc.framework.EMCMenu;
import emc.menus.hr.menuitems.output.HRDisciplinaryActionReportMenu;
import emc.menus.hr.menuitems.output.HREmployeeSummaryMI;

/**
 *
 * @author wikus
 */
public class HRReports extends EMCMenu {

    /**
     * Creates a new instance of HRSetup
     */
    public HRReports() {
        this.setMenuName("Reports");
        this.setMenuList(new HRDisciplinaryActionReportMenu());
        this.setMenuList(new HREmployeeSummaryMI());
    }
}

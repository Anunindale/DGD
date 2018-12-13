/*
 * glReports.java
 *
 * Created on 27 September 2007, 09:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.menus.gl;

import emc.framework.EMCMenu;
import emc.menus.gl.menuitems.output.GLChartOfAccountsReportMI;
import emc.menus.gl.menuitems.output.GLJournalReportMI;

/**
 *
 * @author rico
 */
public class glReports extends EMCMenu {

    /** Creates a new instance of glReports */
    public glReports() {
        this.setMenuName("Reports");
        this.setMenuList(new GLChartOfAccountsReportMI());
        this.setMenuList((new GLJournalReportMI()));
    }
}

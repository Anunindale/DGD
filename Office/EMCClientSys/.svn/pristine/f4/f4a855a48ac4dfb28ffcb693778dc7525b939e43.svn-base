/*
 * glMainMenu.java
 *
 * Created on 19 September 2007, 03:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.menus.gl;

import emc.enums.modules.enumEMCModules;
import emc.framework.EMCMenu;
import emc.menus.gl.menuitems.display.GLChartOfAccountsMenu;

/**
 *
 * @author rico
 */
public class glMainMenu extends EMCMenu {

    private glReports glRep = new glReports();
    private GLSetup glSetup = new GLSetup();
    private GLEnquiry glEnquiry = new GLEnquiry();

    /** Creates a new instance of glMainMenu */
    public glMainMenu() {
        this.setEmcModule(enumEMCModules.GENERAL_LEDGER);
        this.setMenuName(this.getEmcModule().toString());
        this.setMenuList(new GLChartOfAccountsMenu());
        this.setMenuList(glEnquiry);
        this.setMenuList(new GLJournalsMenu());
        this.setMenuList(new GLMaintenanceMenu());
        this.setMenuList(new GLPeriodicMenu());
        this.setMenuList(glRep);
        this.setMenuList(glSetup);
        this.setMenuList(new GLSystemMenu());
    }
}

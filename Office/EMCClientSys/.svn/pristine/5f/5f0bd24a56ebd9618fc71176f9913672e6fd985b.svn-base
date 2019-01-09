/*
 * baseMainMenu.java
 *
 * Created on 17 September 2007, 03:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.menus.base;

import emc.framework.EMCMenu;
import emc.menus.base.menuItems.display.companies;
import emc.menus.base.menuItems.display.employees;
import emc.menus.base.menuItems.display.licence;
import emc.menus.base.menuItems.display.users;

/**
 *
 * @author rico
 */
public class baseMainMenu extends EMCMenu {

    private users userdisplay = new users();
    private licence mlicence = new licence();
    private baseReports globalrep = new baseReports();
    private companies mcomp = new companies();
    private employees memployee = new employees();
    private BaseAdmin admin = new BaseAdmin();
    private BaseSetupMenu setup = new BaseSetupMenu();
    private BaseSystem system = new BaseSystem();
    private baseImport importMenu = new baseImport();

    public baseMainMenu() {
        this.setMenuName("Base");
        this.setMenuList(mcomp);
        this.setMenuList(memployee);
        this.setMenuList(mlicence);
        this.setMenuList(userdisplay);
        this.setMenuList(admin);
        this.setMenuList(new BaseEnquiry());
        this.setMenuList(importMenu);
        this.setMenuList(new BaseMaintenanceMenu());
        this.setMenuList(new BasePeriodicMenu());
        this.setMenuList(globalrep);
        this.setMenuList(setup);
        this.setMenuList(system);
    }
}

/*
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.menus.hr;

import emc.enums.modules.enumEMCModules;
import emc.framework.EMCMenu;
import emc.menus.base.menuItems.display.employees;

/**
 *
 * @author wikus
 */
public class HRMainMenu extends EMCMenu {

    /** Creates a new instance of HRMainMenu */
    public HRMainMenu() {
        this.setEmcModule(enumEMCModules.HR);
        this.setMenuName(this.getEmcModule().toString());

        this.setMenuList(new employees());
        this.setMenuList(new HREnquiry());
        this.setMenuList(new HRFrequently());
        this.setMenuList(new HRReports());
        this.setMenuList(new HRSetup());
        this.setMenuList(new HRSystemMenu());
    }
}

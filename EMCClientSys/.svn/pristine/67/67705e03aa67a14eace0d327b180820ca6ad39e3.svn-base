/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base;

import emc.enums.modules.enumEMCModules;
import emc.framework.EMCMenu;
import emc.menus.base.menuItems.action.BaseFixEmpId;
import emc.menus.base.menuItems.action.ClearDBLog;
import emc.menus.base.menuItems.display.BaseIndexMI;

/**
 *
 * @author Wikus
 */
public class BaseMaintenanceMenu extends EMCMenu {

    /** Creates a new instance of . BaseMaintenanceMenu*/
    public BaseMaintenanceMenu() {
        this.setEmcModule(enumEMCModules.BASE);
        this.setMenuName("Maintenance");
        this.setMenuList(new BaseFixEmpId());
        this.setMenuList(new ClearDBLog());
        this.setMenuList(new BaseIndexMI());
    }
}

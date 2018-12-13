/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.developertools;

import emc.enums.modules.enumEMCModules;
import emc.framework.EMCMenu;
import emc.menus.developertools.menuitems.display.DevTimeSheetMI;

/**
 * @Name : DeveloperToolsMainMenu
 *
 * @Date : 14 Aug 2009
 *
 * @Description : Main menu for the Developer Tools module.
 *
 * @author : riaan
 */
public class DeveloperToolsMainMenu extends EMCMenu {

    /**
     * Creates a new instance of DeveloperToolsMainMenu.
     */
    public DeveloperToolsMainMenu() {
        this.setEmcModule(enumEMCModules.DEVELOPERTOOLS);
        this.setMenuName("Developer Tools");
        this.setMenuList(new Bugs());
        this.setMenuList(new DevEntityLogMenu());
        this.setMenuList(new DevDeploymentUpdateMenu());
        this.setMenuList(new DevTimeSheetMI());
        this.setMenuList(new Maintenance());
        this.setMenuList(new DevToolsReports());
        this.setMenuList(new DevToolsSetup());
        this.setMenuList(new DevToolsSystemMenu());
        this.setMenuList(new DevTools());
    }
}

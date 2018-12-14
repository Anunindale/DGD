/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.dangerousgoods.menuitems;

import emc.enums.modules.enumEMCModules;
import emc.framework.EMCMenu;
import emc.menus.dangerousgoods.menuitems.display.DGCargoCheckMenu;
import emc.menus.dangerousgoods.menuitems.display.DGDContactsMI;
import emc.menus.dangerousgoods.menuitems.display.DGDVehiclesMI;
import emc.menus.dangerousgoods.menuitems.display.DGDeclarationLinesMI;
import emc.menus.dangerousgoods.menuitems.display.DGDeclarationMasterMI;

/**
 *
 * @author rico
 */
public class DGMainMenu extends EMCMenu {

    /** Creates a new instance of CRMMainMenu */
    public DGMainMenu() {
        this.setEmcModule(enumEMCModules.DG);
        this.setMenuName(this.getEmcModule().toString());
        this.setMenuList(new DGCargoCheckMenu());
        this.setMenuList(new DGDContactsMI());
        this.setMenuList(new DGDVehiclesMI());
        this.setMenuList(new DGDeclarationLinesMI());
        this.setMenuList(new DGSetupMenu());
    }
}

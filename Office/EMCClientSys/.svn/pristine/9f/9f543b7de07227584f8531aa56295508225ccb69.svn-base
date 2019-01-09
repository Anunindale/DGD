/*
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.menus.trec;

import emc.enums.modules.enumEMCModules;
import emc.framework.EMCMenu;
import emc.menus.dangerousgoods.menuitems.display.DGCargoCheckMenu;
import emc.menus.trec.menuitems.display.TRECEditTRECMI;
import emc.menus.trec.menuitems.display.TRECTrecCardsMenu;

/**
 *
 * @author rico
 */
public class TRECMainMenu extends EMCMenu {

    /**
     * Creates a new instance of TRECMainMenu
     */
    public TRECMainMenu() {
        this.setEmcModule(enumEMCModules.TREC);
        this.setMenuName(this.getEmcModule().toString());
        this.setMenuList(new TRECTrecCardsMenu());
        this.setMenuList(new DGCargoCheckMenu());
        this.setMenuList(new TRECReports());
        this.setMenuList(new TRECSetup());
        this.setMenuList(new TRECSystemMenu());
    }
}

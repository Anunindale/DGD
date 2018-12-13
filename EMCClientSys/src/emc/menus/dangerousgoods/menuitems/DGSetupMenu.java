/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.dangerousgoods.menuitems;

import emc.enums.modules.enumEMCModules;
import emc.framework.EMCMenu;
import emc.menus.dangerousgoods.menuitems.display.numbersequence.DGDNumberSequenceMI;

/**
 *
 * @author pj
 */
public class DGSetupMenu extends EMCMenu{
    
    DGSetupMenu()
    {
        this.setMenuName("Setup");
        this.setEmcModule(enumEMCModules.DG);
        this.setMenuList(new DGDNumberSequenceMI());
    }
    
}

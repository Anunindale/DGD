/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.pop;

import emc.framework.EMCMenu;
import emc.menus.pop.menuitems.display.CancelledPO;

/**
 *
 * @author rico
 */
public class POPPeriodic extends EMCMenu{
    private CancelledPO canPO = new CancelledPO();
    public POPPeriodic(){
        this.setMenuName("Periodic");
        this.setMenuList(canPO);
    }

}

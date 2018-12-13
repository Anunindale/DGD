/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.pop;

import emc.framework.EMCMenu;
import emc.menus.pop.menuitems.display.MillPurchaseOrders;
import emc.menus.pop.menuitems.display.POPPlannedPurchaseOrderReleaseMenu;

/**
 *
 * @author rico
 */
public class POPSystem extends EMCMenu {

    private POPPostingMenu posting = new POPPostingMenu();

    public POPSystem() {
        this.setMenuName("System");
        this.setMenuList(posting);
        this.setMenuList(new MillPurchaseOrders());
        this.setMenuList(new POPPlannedPurchaseOrderReleaseMenu());
    }
}

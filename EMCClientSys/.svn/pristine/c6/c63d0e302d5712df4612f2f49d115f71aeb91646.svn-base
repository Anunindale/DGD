/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory;

import emc.framework.EMCMenu;
import emc.menus.inventory.menuitems.display.MoveReservedStockMenu;
import emc.menus.inventory.menuitems.display.MoveStockMenu;
import emc.menus.inventory.menuitems.display.MoveStockSummaryMenu;

/**
 *
 * @author riaan
 */
public class InventoryLocation extends EMCMenu {

    private MoveStockMenu moveStock = new MoveStockMenu();
    private MoveStockSummaryMenu moveSummary = new MoveStockSummaryMenu();

    /** Creates a new instance of InventoryReports */
    public InventoryLocation() {
        this.setMenuName("Location");
        this.setMenuList(moveStock);
        this.setMenuList(new MoveReservedStockMenu());
        this.setMenuList(moveSummary);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory;

import emc.framework.EMCMenu;
import emc.menus.inventory.menuitems.action.GenerateJournalMI;
import emc.menus.inventory.menuitems.action.InventoryMaintenanceMI;
import emc.menus.inventory.menuitems.action.RequirementsPlanningMaintenanceMI;
import emc.menus.inventory.menuitems.action.SettlementActionMenu;
import emc.menus.inventory.menuitems.display.InventorySafetyStockMI;
import emc.menus.inventory.menuitems.display.StocktakeUnreservedMI;

/**
 *
 * @author rico
 */
public class InventoryMaintenance extends EMCMenu {

    private StocktakeUnreservedMI stocktake = new StocktakeUnreservedMI();

    /**
     * Creates a new instance of InventoryReports
     */
    public InventoryMaintenance() {
        this.setMenuName("Maintenance");
        this.setMenuList(new SettlementActionMenu());
        this.setMenuList(stocktake);
        this.setMenuList(new RequirementsPlanningMaintenanceMI());
        this.setMenuList(new GenerateJournalMI());
        this.setMenuList(new InventorySafetyStockMI());
        this.setMenuList(new InventoryMaintenanceMI());
    }
}

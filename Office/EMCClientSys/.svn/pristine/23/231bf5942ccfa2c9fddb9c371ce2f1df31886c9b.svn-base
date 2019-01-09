/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory;

import emc.framework.EMCMenu;
import emc.menus.inventory.menuitems.display.GenerateStockCountJournalMenu;
import emc.menus.inventory.menuitems.display.InventoryMessagesMI;
import emc.menus.inventory.menuitems.display.InventoryRegisterMenu;
import emc.menus.inventory.menuitems.display.InventoryRemoveRegisterMenu;
import emc.menus.inventory.menuitems.display.InventoryStockTakeRegisterMenu;
import emc.menus.inventory.menuitems.display.InventoryView;
import emc.menus.inventory.menuitems.display.StockTakeCaptureMenu;

/**
 *
 * @author rico
 */
public class InventorySystem extends EMCMenu {

    public InventorySystem() {
        this.setMenuName("System");
        this.setMenuList(new InventoryRegisterMenu());
        this.setMenuList(new InventoryRemoveRegisterMenu());
        this.setMenuList(new InventoryStockTakeRegisterMenu());
        this.setMenuList(new GenerateStockCountJournalMenu());
        this.setMenuList(new StockTakeCaptureMenu());
        this.setMenuList(new InventoryView());
        this.setMenuList(new InventoryMessagesMI());
    }
}

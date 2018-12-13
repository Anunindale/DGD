/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory;

import emc.framework.EMCMenu;
import emc.menus.inventory.menuitems.display.AgeBinsMenuItem;
import emc.menus.inventory.menuitems.display.Barcodes;
import emc.menus.inventory.menuitems.display.BrandGroups;
import emc.menus.inventory.menuitems.display.CostingGroups;
import emc.menus.inventory.menuitems.display.InventoryNumberSequenceMenu;
import emc.menus.inventory.menuitems.display.InventoryProductGroupMenu;
import emc.menus.inventory.menuitems.display.InventorySafetyStockGenerationRulesMI;
import emc.menus.inventory.menuitems.display.ItemBatch;
import emc.menus.inventory.menuitems.display.ItemGroups;
import emc.menus.inventory.menuitems.display.ItemRanges;
import emc.menus.inventory.menuitems.display.ItemSerial;
import emc.menus.inventory.menuitems.display.LocationMenu;
import emc.menus.inventory.menuitems.display.PalletMenu;
import emc.menus.inventory.menuitems.display.Warehouse;
import emc.menus.inventory.menuitems.display.Parameters;

/**
 *
 * @author riaan
 */
public class InventoryControlSetup extends EMCMenu {

    /** Creates a new instance of InventoryControlSetup */
    public InventoryControlSetup() {
        this.setMenuName("Setup");

        //this.setMenuList(barcodes);
        this.setMenuList(new InventoryControlClassifications());
        this.setMenuList(new InventoryControlDimensions());
        this.setMenuList(new InventoryItemAccess());
        this.setMenuList(new InventoryControlReference());
        this.setMenuList(new InventoryJournalSetup());
        this.setMenuList(new AgeBinsMenuItem());
        this.setMenuList(new Barcodes());
        this.setMenuList(new BrandGroups());
        this.setMenuList(new CostingGroups());
        this.setMenuList(new ItemBatch());
        this.setMenuList(new ItemGroups());
        this.setMenuList(new ItemRanges());
        this.setMenuList(new ItemSerial());
        this.setMenuList(new LocationMenu());
        this.setMenuList(new InventoryNumberSequenceMenu());
        this.setMenuList(new PalletMenu());
        this.setMenuList(new Parameters());
        this.setMenuList(new InventoryProductGroupMenu());
        this.setMenuList(new InventorySafetyStockGenerationRulesMI());
        this.setMenuList(new Warehouse());

    }
}

/*
 * inventoryControlMainMenu.java
 *
 * Created on 29 November 2007, 11:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package emc.menus.inventory;

import emc.enums.modules.enumEMCModules;
import emc.framework.EMCMenu;
import emc.menus.inventory.menuitems.display.ItemMaster;

/**
 *
 * @author rico
 */
public class InventoryControlMainMenu extends EMCMenu {
    
    private ItemMaster itemMaster = new ItemMaster();
    private InventoryJournals journals = new InventoryJournals();
    private InventoryControlSetup setup = new InventoryControlSetup();
    private InventoryReports reports = new InventoryReports();
    private InventoryEnquiry enq = new InventoryEnquiry();
    private InventoryFrequency freq = new InventoryFrequency();
    
    /** Creates a new instance of inventoryControlMainMenu */
    public InventoryControlMainMenu() {
        this.setEmcModule(enumEMCModules.INVENTORY);
        this.setMenuName(this.getEmcModule().toString());
        this.setMenuList(itemMaster);
        this.setMenuList(journals);
        this.setMenuList(enq);
        this.setMenuList(freq);
        this.setMenuList(new InventoryMaintenance()); 
        this.setMenuList(reports);
        this.setMenuList(setup);
        this.setMenuList(new InventorySystem());
    }
    
}

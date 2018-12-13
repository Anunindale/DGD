/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory;

import emc.framework.EMCMenu;
import emc.menus.inventory.menuitems.display.InventoryBatchConsolidationMI;
import emc.menus.inventory.menuitems.display.Journals;

/**
 *
 * @author riaan
 */
public class InventoryJournals extends EMCMenu {

    private Journals journals = new Journals();

    /**
     * Creates a new instance of InventoryJournals
     */
    public InventoryJournals() {
        this.setMenuName("Journals");
        this.setMenuList(journals);
        this.setMenuList(new InventoryBatchConsolidationMI());
    }
}

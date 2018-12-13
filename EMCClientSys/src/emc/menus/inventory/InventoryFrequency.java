/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory;

import emc.framework.EMCMenu;

/**
 *
 * @author rico
 */
public class InventoryFrequency extends EMCMenu {

    private InventoryLocation location = new InventoryLocation();

    public InventoryFrequency() {
        this.setMenuName("Frequently");
        this.setMenuList(location);
    }
}

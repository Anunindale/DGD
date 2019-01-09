/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.inventory;

import emc.framework.EMCMenu;
import emc.menus.inventory.menuitems.display.CustomerReference;
import emc.menus.inventory.menuitems.display.Reference;
import emc.menus.inventory.menuitems.display.ReferenceType;
import emc.menus.inventory.menuitems.display.SupplierReferenceItem;

/**
 *
 * @author wikus
 */
public class InventoryControlReference extends EMCMenu {
    
    private Reference ref = new Reference();
    private ReferenceType refType = new ReferenceType();
    private SupplierReferenceItem suppRef = new SupplierReferenceItem();
    
    public InventoryControlReference() {
        this.setMenuList(ref);
        this.setMenuList(refType);
        this.setMenuList(new CustomerReference());
        this.setMenuList(suppRef);
        this.setMenuName("Item Reference");
    }

}

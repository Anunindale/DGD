/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.inventory;

import emc.enums.inventory.inventorytables.InventoryItemTypes;
import javax.swing.JComboBox;

/**
 *
 * @author rico
 */ 
public class InventoryType extends JComboBox {

    public InventoryType() {
        super(new String[]{InventoryItemTypes.ITEM.toString(), InventoryItemTypes.BOM.toString(),
            InventoryItemTypes.PHANTOM.toString(), InventoryItemTypes.SERVICE.toString(),
            InventoryItemTypes.PLANNING.toString()
        });
    }
}

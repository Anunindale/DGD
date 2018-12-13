/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.itemreference;

import emc.datatypes.EMCString;
import emc.datatypes.systemwide.ItemInterface;

/**
 *
 * @author rico
 */
public class ItemReferenceId extends EMCString implements ItemInterface {

    public ItemReferenceId() {
        this.setEmcLabel("Item Reference");
        this.setColumnWidth(50);
        this.setMandatory(true);
        this.setStringSize(15);
    }
}

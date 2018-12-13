/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.itemmaster;

import emc.datatypes.inventory.itemclassification.Classification;
import emc.entity.inventory.classifications.InventoryItemClassification6;

/**
 *
 * @author rico
 */
public class Classification6 extends Classification {

    public Classification6() {
        this.setEmcLabel("Class Group 6");
        this.setMandatory(false);
        this.setRelatedTable(InventoryItemClassification6.class.getName());
        this.setRelatedField("classification");
    }
}

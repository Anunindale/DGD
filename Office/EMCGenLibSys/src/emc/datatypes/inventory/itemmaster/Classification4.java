/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.itemmaster;

import emc.datatypes.inventory.itemclassification.Classification;
import emc.entity.inventory.classifications.InventoryItemClassification4;

/**
 *
 * @author rico
 */
public class Classification4 extends Classification {

    public Classification4() {
        this.setEmcLabel("Class Group 4");
        this.setMandatory(false);
        this.setRelatedTable(InventoryItemClassification4.class.getName());
        this.setRelatedField("classification");
    }
}

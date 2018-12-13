/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.itemmaster;

import emc.datatypes.inventory.itemclassification.Classification;
import emc.entity.inventory.classifications.InventoryItemClassification2;

/**
 *
 * @author rico
 */
public class Classification2 extends Classification {

    public Classification2() {
        this.setEmcLabel("Class Group 2");
        this.setMandatory(false);
        this.setRelatedTable(InventoryItemClassification2.class.getName());
        this.setRelatedField("classification");
    }
}

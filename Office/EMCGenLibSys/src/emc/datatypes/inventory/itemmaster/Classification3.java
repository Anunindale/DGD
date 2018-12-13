/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.itemmaster;

import emc.datatypes.inventory.itemclassification.Classification;
import emc.entity.inventory.classifications.InventoryItemClassification3;

/**
 *
 * @author rico
 */
public class Classification3 extends Classification {

    public Classification3() {
        this.setEmcLabel("Class Group 3");
        this.setMandatory(false);
        this.setRelatedTable(InventoryItemClassification3.class.getName());
        this.setRelatedField("classification");
    }
}

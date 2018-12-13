/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemmaster;

import emc.datatypes.inventory.itemclassification.Classification;
import emc.entity.inventory.classifications.InventoryItemClassification1;

/**
 *
 * @author rico
 */
public class Classification1 extends Classification{
    public Classification1(){
        this.setEmcLabel("Class Group  1");
        this.setMandatory(false);
        this.setRelatedTable(InventoryItemClassification1.class.getName());
        this.setRelatedField("classification");
    }

}

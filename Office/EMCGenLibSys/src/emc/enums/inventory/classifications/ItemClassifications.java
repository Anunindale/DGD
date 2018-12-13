/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.classifications;

import emc.entity.inventory.classifications.InventoryItemClassification1;
import emc.entity.inventory.classifications.InventoryItemClassification2;
import emc.entity.inventory.classifications.InventoryItemClassification3;
import emc.entity.inventory.classifications.InventoryItemClassification4;
import emc.entity.inventory.classifications.InventoryItemClassification5;
import emc.entity.inventory.classifications.InventoryItemClassification6;

/**
 *
 * @author riaan
 */
public enum ItemClassifications {

    CLASSIFICATION_1(0, "CLASSIFICATION_1", InventoryItemClassification1.class.getName()),
    CLASSIFICATION_2(1, "CLASSIFICATION_2", InventoryItemClassification2.class.getName()),
    CLASSIFICATION_3(2, "CLASSIFICATION_3", InventoryItemClassification3.class.getName()),
    CLASSIFICATION_4(3, "CLASSIFICATION_4", InventoryItemClassification4.class.getName()),
    CLASSIFICATION_5(4, "CLASSIFICATION_5", InventoryItemClassification5.class.getName()),
    CLASSIFICATION_6(5, "CLASSIFICATION_6", InventoryItemClassification6.class.getName());
    
    
    //Enum variables
    final int id;
    final String label;
    final String entityClassName;
    
    /** Creates a new instance of ItemClassifications */
    ItemClassifications(final int id, final String label, final String entityClassName) {
        this.id = id;
        this.label = label;
        this.entityClassName = entityClassName;
    }
    
    public int getId() {
        return id;
    } 
    
    @Override 
    public String toString() {
        return label;
    }
    
    public String getEntityClassName() {
        return this.entityClassName;
    }
    
    public static ItemClassifications fromString(final String str) {
        for (ItemClassifications e : ItemClassifications.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static ItemClassifications fromId(final int id) {
        for (ItemClassifications e : ItemClassifications.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
    
}

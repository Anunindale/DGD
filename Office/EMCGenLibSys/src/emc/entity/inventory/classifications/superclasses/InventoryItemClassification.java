/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.classifications.superclasses;

import emc.datatypes.base.numbersequences.Description;
import emc.datatypes.inventory.itemclassification.Classification;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;

/**
 *
 * @author riaan
 */
@Entity
public class InventoryItemClassification extends EMCEntityClass {

    private String classification;
    private String description;

    /** Creates a new instance of InventoryItemClassification */
    public InventoryItemClassification() {
    }

    public String getClassification() {
        return this.classification;
    }

    public void setClassification(String classifiction) {
        this.classification = classifiction;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("classification", new Classification());
        toBuild.put("description", new Description());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("classification");
        toBuild.add("description");
        return toBuild;
    }
}

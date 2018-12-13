/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.crm;

import emc.datatypes.EMCDataType;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;

/**
 *
 * @author wikus
 */
@Entity
public class CRMClassificationSuper extends EMCEntityClass {

    private String classification;
    private String description;

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.dimension1enquiry;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.dimension1enquiry.Description;
import emc.datatypes.inventory.dimension1enquiry.Dimension1;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author claudette
 */
@Entity
@Table(name = "InventoryDimension1Enquiry", uniqueConstraints = {@UniqueConstraint(columnNames = {"dimension1", "sessionId", "companyId"})})
public class InventoryDimension1Enquiry extends EMCEntityClass {

    private String dimension1;
    private String description;
    private long sessionId;

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("dimension1", new Dimension1());
        toBuild.put("description", new Description());

        return toBuild;
    }

    @Override
    public List<String> getDefaultLookupFields() {

        List<String> fields = super.getDefaultLookupFields();
        fields.add("dimension1");
        fields.add("description");
        fields.add("sessionId");
        return fields;
    }

    public String getDimension1() {
        return dimension1;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }
}

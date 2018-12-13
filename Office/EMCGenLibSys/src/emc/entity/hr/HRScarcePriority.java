/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.scarcepriority.ScarcePriority;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "HRScarcePriority", uniqueConstraints = {@UniqueConstraint(columnNames = {"scarcePriority", "companyId"})})
public class HRScarcePriority extends EMCEntityClass {

    private String scarcePriority;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScarcePriority() {
        return scarcePriority;
    }

    public void setScarcePriority(String scarcePriority) {
        this.scarcePriority = scarcePriority;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("scarcePriority", new ScarcePriority());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("scarcePriority");
        toBuild.add("description");
        return toBuild;
    }
}

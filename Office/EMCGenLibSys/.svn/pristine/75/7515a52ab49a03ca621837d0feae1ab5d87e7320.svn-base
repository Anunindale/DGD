/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.absenteeismtype.AbsenteeismType;
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
@Table(name = "HRAbsenteeismType", uniqueConstraints = {@UniqueConstraint(columnNames = {"absenteeismType", "companyId"})})
public class HRAbsenteeismType extends EMCEntityClass {

    private String absenteeismType;
    private String description;

    public String getAbsenteeismType() {
        return absenteeismType;
    }

    public void setAbsenteeismType(String absenteeismType) {
        this.absenteeismType = absenteeismType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("absenteeismType");
        toBuild.add("description");
        return toBuild;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("absenteeismType", new AbsenteeismType());
        toBuild.put("description", new Description());
        return toBuild;
    }
}

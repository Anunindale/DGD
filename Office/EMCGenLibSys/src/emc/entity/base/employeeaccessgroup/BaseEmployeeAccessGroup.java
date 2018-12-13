/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.employeeaccessgroup;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.employeeaccessgroup.AccessAll;
import emc.datatypes.base.employeeaccessgroup.AccessGroup;
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
@Table(name = "BaseEmployeeAccessGroup", uniqueConstraints = {@UniqueConstraint(columnNames = {"accessGroup", "companyId"})})
public class BaseEmployeeAccessGroup extends EMCEntityClass {

    private String accessGroup;
    private String description;
    private boolean accessAll;

    public boolean isAccessAll() {
        return accessAll;
    }

    public void setAccessAll(boolean accessAll) {
        this.accessAll = accessAll;
    }

    public String getAccessGroup() {
        return accessGroup;
    }

    public void setAccessGroup(String accessGroup) {
        this.accessGroup = accessGroup;
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
        toBuild.put("accessGroup", new AccessGroup());
        toBuild.put("description", new Description());
        toBuild.put("accessAll", new AccessAll());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("accessGroup");
        toBuild.add("description");
        return toBuild;
    }
}

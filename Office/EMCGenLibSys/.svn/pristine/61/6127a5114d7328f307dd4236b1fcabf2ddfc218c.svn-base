/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.union.Union;
import emc.datatypes.systemwide.Description;
import emc.datatypes.systemwide.Telephone;
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
@Table(name = "HRUnions", uniqueConstraints = {@UniqueConstraint(columnNames = {"unionId", "companyId"})})
public class HRUnions extends EMCEntityClass {

    private String unionId;
    private String description;
    private String contactNum;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("unionId", new Union());
        toBuild.put("description", new Description());
        toBuild.put("contactNum", new Telephone());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("unionId");
        toBuild.add("description");
        return toBuild;
    }
}

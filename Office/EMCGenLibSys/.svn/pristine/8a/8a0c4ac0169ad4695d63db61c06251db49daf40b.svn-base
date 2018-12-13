package emc.entity.gl.rules;

import emc.datatypes.EMCDataType;
import emc.datatypes.gl.groupcompanyaccount.Description;
import emc.datatypes.gl.groupcompanyaccount.GroupRule;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

/** 
 *
 * @author claudette
 */
@Entity
@Table(name = "GLGroupCompanyAccount", uniqueConstraints = {@UniqueConstraint(columnNames = {"groupRule", "companyId"})})
public class GLGroupCompanyAccount extends EMCEntityClass {

    private String groupRule;
    private String description;

    /** Creates a new instance of GLGroupCompanyAccount. */
    public GLGroupCompanyAccount() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("groupRule", new GroupRule());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("groupRule");
        toBuild.add("description");
        return toBuild;
    }

    public String getGroupRule() {
        return this.groupRule;
    }

    public void setGroupRule(String groupRule) {
        this.groupRule = groupRule;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
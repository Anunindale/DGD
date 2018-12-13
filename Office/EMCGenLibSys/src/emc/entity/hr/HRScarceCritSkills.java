package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.scarcecritskills.Description;
import emc.datatypes.hr.scarcecritskills.ScarceCritSkills;
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
@Table(name = "HRScarceCritSkills", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "scarceCritSkills"})})
public class HRScarceCritSkills extends EMCEntityClass {

    private String scarceCritSkills;
    private String description;

    /** Creates a new instance of HRScarceCritSkills. */
    public HRScarceCritSkills() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("scarceCritSkills", new ScarceCritSkills());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("scarceCritSkills");
        toBuild.add("description");
        return toBuild;
    }

    public String getScarceCritSkills() {
        return this.scarceCritSkills;
    }

    public void setScarceCritSkills(String scarceCritSkills) {
        this.scarceCritSkills = scarceCritSkills;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
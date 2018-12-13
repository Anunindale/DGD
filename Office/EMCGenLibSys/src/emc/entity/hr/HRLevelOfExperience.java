package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.levelofexperience.Description;
import emc.datatypes.hr.levelofexperience.LevelOfExp;
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
@Table(name = "HRLevelOfExperience", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "levelOfExp"})})
public class HRLevelOfExperience extends EMCEntityClass {

    private String levelOfExp;
    private String description;

    /** Creates a new instance of HRLevelOfExperience. */
    public HRLevelOfExperience() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("levelOfExp", new LevelOfExp());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("levelOfExp");
        toBuild.add("description");
        return toBuild;
    }

    public String getLevelOfExp() {
        return this.levelOfExp;
    }

    public void setLevelOfExp(String levelOfExp) {
        this.levelOfExp = levelOfExp;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
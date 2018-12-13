package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.explevel.Description;
import emc.datatypes.hr.explevel.ExpLevelId;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/** 
*
* @author claudette
*/
@Entity
@Table(name="HRExperienceLevel", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "expLevelId"})})
public class HRExperienceLevel extends EMCEntityClass {

    private String expLevelId;
    private String description;

    /** Creates a new instance of HRExperienceLevel. */
    public HRExperienceLevel() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("expLevelId", new ExpLevelId());
        toBuild.put("description", new Description());
        return toBuild;
    }

    public String getExpLevelId() {
        return this.expLevelId;
    }

    public void setExpLevelId(String expLevelId) {
        this.expLevelId = expLevelId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
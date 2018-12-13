package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.edulevel.Description;
import emc.datatypes.hr.edulevel.EduLevelId;
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
@Table(name = "HREducationLevel", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "eduLevelId"})})
public class HREducationLevel extends EMCEntityClass {

    private String eduLevelId;
    private String description;

    /** Creates a new instance of HREducationLevel. */
    public HREducationLevel() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("eduLevelId", new EduLevelId());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("eduLevelId");
        toBuild.add("description");
        return toBuild;
    }

    public String getEduLevelId() {
        return this.eduLevelId;
    }

    public void setEduLevelId(String eduLevelId) {
        this.eduLevelId = eduLevelId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.pivotalstudyfield.Description;
import emc.datatypes.hr.pivotalstudyfield.PivotalStudyFieldId;
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
@Table(name = "HRPivotalStudyField", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "pivotalStudyFieldId"})})
public class HRPivotalStudyField extends EMCEntityClass {

    private String pivotalStudyFieldId;
    private String description;

    /** Creates a new instance of HRPivotalStudyField. */
    public HRPivotalStudyField() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("pivotalStudyFieldId", new PivotalStudyFieldId());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("pivotalStudyFieldId");
        toBuild.add("description");
        return toBuild;
    }

    public String getPivotalStudyFieldId() {
        return this.pivotalStudyFieldId;
    }

    public void setPivotalStudyFieldId(String pivotalStudyFieldId) {
        this.pivotalStudyFieldId = pivotalStudyFieldId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.pivotalprogram.Description;
import emc.datatypes.hr.pivotalprogram.PivotalProgramId;
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
@Table(name = "HRPivotalProgram", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "pivotalProgramId"})})
public class HRPivotalProgram extends EMCEntityClass {

    private String pivotalProgramId;
    private String description;

    /** Creates a new instance of HRPivotalProgram. */
    public HRPivotalProgram() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("pivotalProgramId", new PivotalProgramId());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("pivotalProgramId");
        toBuild.add("description");
        return toBuild;
    }

    public String getPivotalProgramId() {
        return this.pivotalProgramId;
    }

    public void setPivotalProgramId(String pivotalProgramId) {
        this.pivotalProgramId = pivotalProgramId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
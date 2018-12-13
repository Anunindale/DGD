package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.learningprogram.Description;
import emc.datatypes.hr.learningprogram.LearningProgramId;
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
@Table(name = "HRLearningProgram", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "learningProgramId"})})
public class HRLearningProgram extends EMCEntityClass {

    private String learningProgramId;
    private String description;

    /** Creates a new instance of HRLearningProgram. */
    public HRLearningProgram() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("learningProgramId", new LearningProgramId());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("learningProgramId");
        toBuild.add("description");
        return toBuild;
    }

    public String getLearningProgramId() {
        return this.learningProgramId;
    }

    public void setLearningProgramId(String learningProgramId) {
        this.learningProgramId = learningProgramId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
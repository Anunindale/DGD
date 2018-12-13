package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.traininglevel.Description;
import emc.datatypes.hr.traininglevel.TrainingLevelId;
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
@Table(name = "HRTrainingLevel", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "trainingLevelId"})})
public class HRTrainingLevel extends EMCEntityClass {

    private String trainingLevelId;
    private String description;

    /** Creates a new instance of HRTrainingLevel. */
    public HRTrainingLevel() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("trainingLevelId", new TrainingLevelId());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("trainingLevelId");
        toBuild.add("description");
        return toBuild;
    }

    public String getTrainingLevelId() {
        return this.trainingLevelId;
    }

    public void setTrainingLevelId(String trainingLevelId) {
        this.trainingLevelId = trainingLevelId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
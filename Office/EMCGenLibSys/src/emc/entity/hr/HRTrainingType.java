package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.trainingtype.Description;
import emc.datatypes.hr.trainingtype.TrainingTypeId;
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
@Table(name = "HRTrainingType", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "trainingTypeId"})})
public class HRTrainingType extends EMCEntityClass {

    private String trainingTypeId;
    private String description;

    /** Creates a new instance of HRTrainingType. */
    public HRTrainingType() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("trainingTypeId", new TrainingTypeId());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("trainingTypeId");
        toBuild.add("description");
        return toBuild;
    }

    public String getTrainingTypeId() {
        return this.trainingTypeId;
    }

    public void setTrainingTypeId(String trainingTypeId) {
        this.trainingTypeId = trainingTypeId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
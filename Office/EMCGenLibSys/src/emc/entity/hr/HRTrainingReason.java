package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.trainingreason.Description;
import emc.datatypes.hr.trainingreason.TrainingReasonId;
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
@Table(name = "HRTrainingReason", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "trainingReasonId"})})
public class HRTrainingReason extends EMCEntityClass {

    private String trainingReasonId;
    private String description;

    /** Creates a new instance of HRTrainingReason. */
    public HRTrainingReason() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("trainingReasonId", new TrainingReasonId());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("trainingReasonId");
        toBuild.add("description");
        return toBuild;
    }

    public String getTrainingReasonId() {
        return this.trainingReasonId;
    }

    public void setTrainingReasonId(String trainingReasonId) {
        this.trainingReasonId = trainingReasonId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.jobstatus.Description;
import emc.datatypes.hr.jobstatus.JobStatusId;
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
@Table(name="HRJobStatus", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "jobStatusId"})})
public class HRJobStatus extends EMCEntityClass {

    private String jobStatusId;
    private String description;

    /** Creates a new instance of HRJobStatus. */
    public HRJobStatus() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("jobStatusId", new JobStatusId());
        toBuild.put("description", new Description());
        return toBuild;
    }

    public String getJobStatusId() {
        return this.jobStatusId;
    }

    public void setJobStatusId(String jobStatusId) {
        this.jobStatusId = jobStatusId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
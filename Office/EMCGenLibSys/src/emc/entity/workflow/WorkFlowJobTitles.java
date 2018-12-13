/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.workflow;

import emc.datatypes.hr.edulevel.EduLevelId;
import emc.datatypes.hr.jobcatagory.foreignkey.JobCatagoryFK;
import emc.datatypes.hr.joblevel.foreignkey.JobLevelFK;
import emc.datatypes.hr.ofocode.foreignkey.OFOCodeFk;
import emc.datatypes.systemwide.Description;
import emc.datatypes.workflow.jobtitles.JobTitles;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "WorkFlowJobTitles", uniqueConstraints = {@UniqueConstraint(columnNames = {"jobTitle", "companyId"})})
public class WorkFlowJobTitles extends EMCEntityClass implements Serializable {

    private String jobTitle;
    private String description;
    private String jobLevel;
    private String jobCatagory;
    private String ofoCode;
    private String eduLevel;

    public WorkFlowJobTitles() {
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobCatagory() {
        return jobCatagory;
    }

    public void setJobCatagory(String jobCatagory) {
        this.jobCatagory = jobCatagory;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public String getOfoCode() {
        return ofoCode;
    }

    public void setOfoCode(String ofoCode) {
        this.ofoCode = ofoCode;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("jobTitle", new JobTitles());
        toBuild.put("description", new Description());
        toBuild.put("jobCatagory", new JobCatagoryFK());
        toBuild.put("jobLevel", new JobLevelFK());
        toBuild.put("ofoCode", new OFOCodeFk());
        toBuild.put("eduLevel", new EduLevelId());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("jobTitle");
        toBuild.add("description");
        return toBuild;
    }

    public String getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel;
    }
}

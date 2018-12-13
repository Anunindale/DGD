/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.developertools;

import emc.framework.EMCEntityClass;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "DevDeploymentUpdateLogLines")
public class DevDeploymentUpdateLogLines extends EMCEntityClass {

    private long masterRecordID;
    private String project;
    private String projectDescription;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public long getMasterRecordID() {
        return masterRecordID;
    }

    public void setMasterRecordID(long masterRecordID) {
        this.masterRecordID = masterRecordID;
    }
}

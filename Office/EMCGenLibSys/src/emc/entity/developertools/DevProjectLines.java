/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.developertools;

import emc.datatypes.EMCDataType;
import emc.datatypes.developertools.projects.ClassPath;
import emc.datatypes.developertools.projects.ProjectLayer;
import emc.datatypes.developertools.projects.ProjectType;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "DevProjectLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"masterRecordID", "classpath", "companyId"})})
public class DevProjectLines extends EMCEntityClass {

    private long masterRecordID;
    private String classpath;
    private String projectType;
    private String projectLayer;

    public String getClasspath() {
        return classpath;
    }

    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }

    public String getProjectLayer() {
        return projectLayer;
    }

    public void setProjectLayer(String projectLayer) {
        this.projectLayer = projectLayer;
    }

    public long getMasterRecordID() {
        return masterRecordID;
    }

    public void setMasterRecordID(long masterRecordID) {
        this.masterRecordID = masterRecordID;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("classpath", new ClassPath());
        toBuild.put("projectType", new ProjectType());
        toBuild.put("projectLayer", new ProjectLayer());
        return toBuild;
    }
}

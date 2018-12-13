/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.developertools;

import emc.datatypes.EMCDataType;
import emc.datatypes.developertools.entitylog.LogDate;
import emc.datatypes.developertools.entitylog.LogQuery;
import emc.datatypes.developertools.entitylog.QueryType;
import emc.datatypes.developertools.projects.foreignkeys.ProjectNameFKNM;
import emc.datatypes.sop.customers.foreignkeys.CustomerIdFK;
import emc.enums.developertools.DevQueryType;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "DevEntityLog")
public class DevEntityLog extends EMCEntityClass {

    @Temporal(TemporalType.DATE)
    private Date logDate;
    private String queryType = DevQueryType.QUERY.toString();
    private String queryLog;
    private String projectName;
    private String customerId;
    private boolean runBeforeDeploy;
    private boolean ranQuery;

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getQueryLog() {
        return queryLog;
    }

    public void setQueryLog(String queryLog) {
        this.queryLog = queryLog;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public boolean isRunBeforeDeploy() {
        return runBeforeDeploy;
    }

    public void setRunBeforeDeploy(boolean runBeforeDeploy) {
        this.runBeforeDeploy = runBeforeDeploy;
    }

    public boolean isRanQuery() {
        return ranQuery;
    }

    public void setRanQuery(boolean ranQuery) {
        this.ranQuery = ranQuery;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("logDate", new LogDate());
        toBuild.put("queryType", new QueryType());
        toBuild.put("queryLog", new LogQuery());
        toBuild.put("projectName", new ProjectNameFKNM());
        toBuild.put("customerId", new CustomerIdFK());
        return toBuild;
    }
}

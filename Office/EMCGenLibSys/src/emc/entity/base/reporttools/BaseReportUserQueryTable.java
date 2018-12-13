/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.base.reporttools;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.reporttools.reportuserquery.UserRecordName;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "BaseReportUserQueryTable", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "userRecordName", "createdBy", "reportId"})})
public class BaseReportUserQueryTable extends EMCEntityClass {

    @Column(length = 50)
    private String userRecordName;
    @Column(length = 10000)
    private String tablesTree;
    @Column(length = 50)
    private String reportId;
    //Timestamp indicating when the query was last executed
    private long lastExecTimestamp;
    //XML Representation of report parameters.
    private String reportParameters;
            
    /** Creates a new instance of BaseReportUserQueryTable. */
    public BaseReportUserQueryTable() {
        
    }

    public String getUserRecordName() {
        return userRecordName;
    }

    public void setUserRecordName(String userRecordName) {
        this.userRecordName = userRecordName;
    }

    public String getTablesTree() {
        return tablesTree;
    }

    public void setTablesTree(String tablesTree) {
        this.tablesTree = tablesTree;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public long getLastExecTimestamp() {
        return lastExecTimestamp;
    }

    public void setLastExecTimestamp(long lastExecTimestamp) {
        this.lastExecTimestamp = lastExecTimestamp;
    }

    public String getReportParameters() {
        return reportParameters;
    }

    public void setReportParameters(String reportParameters) {
        this.reportParameters = reportParameters;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap<String, EMCDataType> theMap = super.buildFieldList();
        theMap.put("userRecordName", new UserRecordName());
        return theMap;
    }    
}

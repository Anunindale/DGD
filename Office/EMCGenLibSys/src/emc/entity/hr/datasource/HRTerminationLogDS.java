/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.hr.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.datasources.base.employeeNameDS;
import emc.entity.hr.HRTerminationLog;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class HRTerminationLogDS extends HRTerminationLog {

    private String employeeName;

    public HRTerminationLogDS() {
        this.setDataSource(true);
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("employeeName", new employeeNameDS());
        return toBuild;
    }
}

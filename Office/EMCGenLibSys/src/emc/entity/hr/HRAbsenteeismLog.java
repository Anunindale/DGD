/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.datatypes.hr.absenteeismlog.AbsenteeismFromDate;
import emc.datatypes.hr.absenteeismlog.AbsenteeismToDate;
import emc.datatypes.hr.absenteeismtype.datasource.AbsenteeismTypeFK;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "HRAbsenteeismLog", uniqueConstraints = {@UniqueConstraint(columnNames = {"employeeNumber", "absentFromDate", "companyId"})})
public class HRAbsenteeismLog extends EMCEntityClass {

    private String employeeNumber;
    @Temporal(TemporalType.DATE)
    private Date absentFromDate;
    @Temporal(TemporalType.DATE)
    private Date absentToDate;
    private String absenteeismType;
    private String description;
    private int absenteeismTime;

    public Date getAbsentFromDate() {
        return absentFromDate;
    }

    public void setAbsentFromDate(Date absentFromDate) {
        this.absentFromDate = absentFromDate;
    }

    public Date getAbsentToDate() {
        return absentToDate;
    }

    public void setAbsentToDate(Date absentToDate) {
        this.absentToDate = absentToDate;
    }

    public int getAbsenteeismTime() {
        return absenteeismTime;
    }

    public void setAbsenteeismTime(int absenteeismTime) {
        this.absenteeismTime = absenteeismTime;
    }

    public String getAbsenteeismType() {
        return absenteeismType;
    }

    public void setAbsenteeismType(String absenteeismType) {
        this.absenteeismType = absenteeismType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("employeeNumber", new EmployeeIdFK());
        toBuild.put("absentFromDate", new AbsenteeismFromDate());
        toBuild.put("absentToDate", new AbsenteeismToDate());
        toBuild.put("absenteeismType", new AbsenteeismTypeFK());
        toBuild.put("description", new Description());

        return toBuild;
    }
}

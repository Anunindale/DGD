/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.datatypes.hr.actionresult.foreignkeys.ResultFK;
import emc.datatypes.hr.diciplaneryaction.BroughtBy;
import emc.datatypes.hr.diciplaneryaction.Offence;
import emc.datatypes.hr.diciplaneryaction.OffenceDate;
import emc.datatypes.hr.diciplaneryaction.Status;
import emc.datatypes.hr.disiplinarylevel.foreignkey.LevelIdFK;
import emc.enums.hr.HRStatuses;
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
@Table(name = "HRDiciplaneryActions", uniqueConstraints = {@UniqueConstraint(columnNames = {"employeeNumber", "offenceDate", "companyId"})})
public class HRDiciplaneryActions extends EMCEntityClass {

    private String employeeNumber;
    @Temporal(TemporalType.DATE)
    private Date offenceDate;
    private String broughtBy;
    private String offence;
    private String diciplaneryResult;
    private String status = HRStatuses.CAPTURED.toString();
    private String resultLevel;

    public String getBroughtBy() {
        return broughtBy;
    }

    public void setBroughtBy(String broughtBy) {
        this.broughtBy = broughtBy;
    }

    public String getDiciplaneryResult() {
        return diciplaneryResult;
    }

    public void setDiciplaneryResult(String diciplaneryResult) {
        this.diciplaneryResult = diciplaneryResult;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getOffence() {
        return offence;
    }

    public void setOffence(String offence) {
        this.offence = offence;
    }

    public Date getOffenceDate() {
        return offenceDate;
    }

    public void setOffenceDate(Date offenceDate) {
        this.offenceDate = offenceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResultLevel() {
        return resultLevel;
    }

    public void setResultLevel(String resultLevel) {
        this.resultLevel = resultLevel;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("employeeNumber", new EmployeeIdFK());
        toBuild.put("offenceDate", new OffenceDate());
        toBuild.put("broughtBy", new BroughtBy());
        toBuild.put("offence", new Offence());
        toBuild.put("diciplaneryResult", new ResultFK());
        toBuild.put("status", new Status());
        toBuild.put("resultLevel", new LevelIdFK());
        return toBuild;
    }
}

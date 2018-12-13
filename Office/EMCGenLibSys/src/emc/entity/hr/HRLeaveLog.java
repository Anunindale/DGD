/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.datatypes.hr.leavelog.NumberOfDays;
import emc.datatypes.hr.leavelog.ReturnDate;
import emc.datatypes.hr.leavelog.StartDate;
import emc.datatypes.hr.leavetype.foreignkey.LeaveTypeFK;
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
@Table(name = "HRLeaveLog", uniqueConstraints = {@UniqueConstraint(columnNames = {"employeeNumber", "startDate", "companyId"})})
public class HRLeaveLog extends EMCEntityClass {

    private String employeeNumber;
    private String leaveType;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date returnDate;
    private double numberOfDays;

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public double getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(double numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("employeeNumber", new EmployeeIdFK());
        toBuild.put("leaveType", new LeaveTypeFK());
        toBuild.put("startDate", new StartDate());
        toBuild.put("returnDate", new ReturnDate());
        toBuild.put("numberOfDays", new NumberOfDays());
        return toBuild;
    }
}

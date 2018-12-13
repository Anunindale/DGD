/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.datatypes.hr.actionresult.foreignkeys.ResultFK;
import emc.datatypes.hr.diciplaneryaction.Status;
import emc.datatypes.hr.grievences.Grievence;
import emc.datatypes.hr.grievences.ReceivedDate;
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
@Table(name = "HRGrievences", uniqueConstraints = {@UniqueConstraint(columnNames = {"employeeNumber", "receivedDate", "companyId"})})
public class HRGrievences extends EMCEntityClass {

    private String employeeNumber;
    @Temporal(TemporalType.DATE)
    private Date receivedDate;
    private String grievence;
    private String grievenceResult;
    private String status;

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getGrievence() {
        return grievence;
    }

    public void setGrievence(String grievence) {
        this.grievence = grievence;
    }

    public String getGrievenceResult() {
        return grievenceResult;
    }

    public void setGrievenceResult(String grievenceResult) {
        this.grievenceResult = grievenceResult;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("employeeNumber", new EmployeeIdFK());
        toBuild.put("receivedDate", new ReceivedDate());
        toBuild.put("grievence", new Grievence());
        toBuild.put("grievenceResult", new ResultFK());
        toBuild.put("status", new Status());
        return toBuild;
    }
}

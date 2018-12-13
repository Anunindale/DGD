/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.datatypes.hr.terminationlog.TerminationDate;
import emc.datatypes.hr.terminationtype.foreignkey.TerminationTypeFK;
import emc.datatypes.systemwide.Comments;
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
@Table(name = "HRTerminationLog", uniqueConstraints = {@UniqueConstraint(columnNames = {"employeeNumber", "terminationDate", "companyId"})})
public class HRTerminationLog extends EMCEntityClass {

    private String employeeNumber;
    private String terminationType;
    @Temporal(TemporalType.DATE)
    private Date terminationDate;
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getTerminationType() {
        return terminationType;
    }

    public void setTerminationType(String terminationType) {
        this.terminationType = terminationType;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("employeeNumber", new EmployeeIdFK());
        toBuild.put("terminationType", new TerminationTypeFK());
        toBuild.put("terminationDate", new TerminationDate());
        toBuild.put("comment", new Comments());
        return toBuild;
    }
}

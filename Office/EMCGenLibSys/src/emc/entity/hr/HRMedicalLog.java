/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.datatypes.hr.medicallog.Treatment;
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
@Table(name = "HRMedicalLog")
public class HRMedicalLog extends EMCEntityClass {

    private String employeeId;
    @Temporal(TemporalType.DATE)
    private Date visitDate;
    private String problem;
    private String treatement;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getTreatement() {
        return treatement;
    }

    public void setTreatement(String treatement) {
        this.treatement = treatement;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("treatement", new Treatment());
        toBuild.put("employeeId", new EmployeeIdFK());
        return toBuild;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.datatypes.hr.education.DateCompleted;
import emc.datatypes.hr.education.EducationType;
import emc.datatypes.hr.institution.foreignkey.InstitutionFK;
import emc.datatypes.hr.qualification.foreignkey.QualificationIdFK;
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
@Table(name = "HREmployeeEducation", uniqueConstraints = {@UniqueConstraint(columnNames = {"employeeNumber", "qualification", "companyId"})})
public class HREmployeeEducation extends EMCEntityClass {

    private String employeeNumber;
    private String educationType;
    private String institution;
    @Temporal(TemporalType.DATE)
    private Date dateCompleted;
    private String qualification;

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getEducationType() {
        return educationType;
    }

    public void setEducationType(String educationType) {
        this.educationType = educationType;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("employeeNumber", new EmployeeIdFK());
        toBuild.put("educationType", new EducationType());
        toBuild.put("institution", new InstitutionFK());
        toBuild.put("dateCompleted", new DateCompleted());
        toBuild.put("qualification", new QualificationIdFK());
        return toBuild;
    }
}

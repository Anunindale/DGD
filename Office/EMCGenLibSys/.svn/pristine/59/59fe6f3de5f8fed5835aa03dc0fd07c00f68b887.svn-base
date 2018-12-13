/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.datatypes.hr.course.foreignkey.CourseIdFK;
import emc.datatypes.hr.institution.foreignkey.InstitutionFK;
import emc.datatypes.hr.training.Certificate;
import emc.datatypes.hr.training.DateCompleted;
import emc.datatypes.hr.training.DateStarted;
import emc.datatypes.workflow.employeeskills.Rating;
import emc.datatypes.workflow.employeeskills.Skill;
import emc.enums.hr.HRTrainingStatuses;
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
@Table(name = "HREmployeeTraining", uniqueConstraints = {@UniqueConstraint(columnNames = {"employeeNumber", "certificate", "companyId"})})
public class HREmployeeTraining extends EMCEntityClass {

    private String employeeNumber;
    private String institution;
    private String course;
    private String certificate;
    private String skill;
    private String rating;
    private String status = HRTrainingStatuses.PROPOSED.toString();
    @Temporal(TemporalType.DATE)
    private Date dateStarted;
    @Temporal(TemporalType.DATE)
    private Date dateCompleted;
    private double courseCost;

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
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

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Date getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(Date dateStarted) {
        this.dateStarted = dateStarted;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCourseCost() {
        return courseCost;
    }

    public void setCourseCost(double courseCost) {
        this.courseCost = courseCost;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("employeeNumber", new EmployeeIdFK());
        toBuild.put("institution", new InstitutionFK());
        toBuild.put("dateCompleted", new DateCompleted());
        toBuild.put("dateStarted", new DateStarted());
        toBuild.put("certificate", new Certificate());
        toBuild.put("skill", new Skill());
        toBuild.put("rating", new Rating());
        toBuild.put("course", new CourseIdFK());

        return toBuild;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.employeetable.Department;
import emc.datatypes.base.employeetable.JobTitle;
import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.datatypes.hr.branch.foreignkey.BranchFK;
import emc.datatypes.hr.grade.foreignkey.GradeFK;
import emc.datatypes.hr.internalemploymenthistory.FromDate;
import emc.datatypes.hr.internalemploymenthistory.ToDate;
import emc.datatypes.hr.section.foreignkey.SectionFK;
import emc.datatypes.systemwide.CompanyId;
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
@Table(name = "HRInternalEmploymentHistory", uniqueConstraints = {@UniqueConstraint(columnNames = {"employeeNumber", "company", "jobTitle", "department", "branch", "sectionId", "companyId"})})
public class HRInternalEmploymentHistory extends EMCEntityClass {

    private String employeeNumber;
    private String company;
    private String jobTitle;
    private String grade;
    private String department;
    private String branch;
    private String sectionId;
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Temporal(TemporalType.DATE)
    private Date toDate;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("employeeNumber", new EmployeeIdFK());
        toBuild.put("company", new CompanyId());
        toBuild.put("jobTitle", new JobTitle());
        toBuild.put("grade", new GradeFK());
        toBuild.put("department", new Department());
        toBuild.put("branch", new BranchFK());
        toBuild.put("sectionId", new SectionFK());
        toBuild.put("fromDate", new FromDate());
        toBuild.put("toDate", new ToDate());
        return toBuild;
    }
}

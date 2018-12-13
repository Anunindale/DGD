/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.workflow;

import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.datatypes.workflow.employeeskills.EmployeeSkillDate;
import emc.datatypes.workflow.employeeskills.Rating;
import emc.datatypes.workflow.employeeskills.Skill;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "WFEmployeeSkills", uniqueConstraints = {@UniqueConstraint(columnNames = {"employeeNumber", "skill", "companyId"})})
public class WorkFlowEmployeeSkills extends EMCEntityClass implements Serializable {

    private String employeeNumber;
    private String skill;
    private String rating;
    @Temporal(TemporalType.DATE)
    private Date ratingDate;

    public WorkFlowEmployeeSkills() {

    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Date getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(Date ratingDate) {
        this.ratingDate = ratingDate;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("employeeNumber", new EmployeeIdFK());
        toBuild.put("skill", new Skill());
        toBuild.put("rating", new Rating());
        toBuild.put("ratingDate", new EmployeeSkillDate());
        
        return toBuild;
    }
    
}

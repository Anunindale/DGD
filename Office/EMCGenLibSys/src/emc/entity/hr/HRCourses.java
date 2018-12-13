/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.course.CourseLevel;
import emc.datatypes.hr.course.Duration;
import emc.datatypes.hr.course.NQFLevel;
import emc.datatypes.hr.course.SAQARegistered;
import emc.datatypes.hr.educationpriority.foreignkey.EducationPriorityFK;
import emc.datatypes.hr.proposedtraining.Course;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "HRCourses", uniqueConstraints = {@UniqueConstraint(columnNames = {"course", "companyId"})})
public class HRCourses extends EMCEntityClass {

    private String course;
    private String description;
    private String courseLevel;
    private boolean saqaRegistered;
    private String priority;
    private String nqfLevel;
    private double duration;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getNqfLevel() {
        return nqfLevel;
    }

    public void setNqfLevel(String nqfLevel) {
        this.nqfLevel = nqfLevel;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isSaqaRegistered() {
        return saqaRegistered;
    }

    public void setSaqaRegistered(boolean saqaRegistered) {
        this.saqaRegistered = saqaRegistered;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("course", new Course());
        toBuild.put("description", new Description());
        toBuild.put("priority", new EducationPriorityFK());
        toBuild.put("saqaRegistered", new SAQARegistered());
        toBuild.put("nqfLevel", new NQFLevel());
        toBuild.put("courseLevel", new CourseLevel());
        toBuild.put("duration", new Duration());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("course");
        toBuild.add("description");
        return toBuild;
    }
}

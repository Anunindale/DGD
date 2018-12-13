/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.course.foreignkey;

import emc.datatypes.hr.course.CourseId;
import emc.entity.hr.HRCourses;

/**
 *
 * @author wikus
 */
public class CourseIdFK extends CourseId {

    public CourseIdFK() {
        this.setRelatedTable(HRCourses.class.getName());
        this.setRelatedField("course");
        this.setMandatory(false);
    }
}

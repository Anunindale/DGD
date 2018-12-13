/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.educationpriority.foreignkey;

import emc.datatypes.hr.educationpriority.EducationPriority;
import emc.entity.hr.HREducationPriority;

/**
 *
 * @author wikus
 */
public class EducationPriorityFK extends EducationPriority {

    public EducationPriorityFK() {
        this.setRelatedTable(HREducationPriority.class.getName());
        this.setRelatedField("priority");
        this.setMandatory(false);
    }
}

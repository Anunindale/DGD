/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.proposedtraining;

import emc.datatypes.hr.training.*;
import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Course extends EMCString {

    public Course() {
        this.setEmcLabel("Course");
        this.setMandatory(true);
    }
}

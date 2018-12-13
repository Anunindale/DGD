/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.educationpriority;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class EducationPriority extends EMCString {

    public EducationPriority() {
        this.setEmcLabel("Priority");
        this.setMandatory(true);
    }
}

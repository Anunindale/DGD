/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.grade;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Grade extends EMCString{

    public Grade() {
        this.setEmcLabel("Grade");
        this.setMandatory(true);
    }

}

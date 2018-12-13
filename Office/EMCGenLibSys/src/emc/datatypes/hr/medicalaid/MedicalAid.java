/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.medicalaid;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class MedicalAid extends EMCString {

    public MedicalAid() {
        this.setEmcLabel("Medical Aid");
        this.setMandatory(true);
    }

}

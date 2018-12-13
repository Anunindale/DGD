/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.nationality;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Nationality extends EMCString {

    public Nationality() {
        this.setEmcLabel("Nationality");
        this.setMandatory(true);
    }

}

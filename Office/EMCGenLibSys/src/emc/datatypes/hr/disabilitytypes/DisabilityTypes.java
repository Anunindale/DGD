/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.disabilitytypes;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class DisabilityTypes extends EMCString {

    public DisabilityTypes() {
        this.setEmcLabel("Type");
        this.setMandatory(true);
    }
}

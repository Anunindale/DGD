/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.ofocode;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class OFOCode extends EMCString {

    public OFOCode() {
        this.setEmcLabel("OFO Code");
        this.setMandatory(true);
    }
}

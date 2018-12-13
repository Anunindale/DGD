/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.trec.erg;

import emc.datatypes.EMCString;

/**
 *
 * @author stu
 */
public class ErgNumber extends EMCString {

    public ErgNumber() {
        this.setEmcLabel("Erg");
        this.setMandatory(true);
        this.setStringSize(25);
        this.setColumnWidth(6);
        this.setNumberSeqAllowed(false);
    }
}

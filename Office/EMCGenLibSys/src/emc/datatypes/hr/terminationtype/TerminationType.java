/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.terminationtype;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class TerminationType extends EMCString {

    public TerminationType() {
        this.setEmcLabel("Termination Type");
        this.setMandatory(true);
    }


}

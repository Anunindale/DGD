/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.scarcepriority;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class ScarcePriority extends EMCString {

    public ScarcePriority() {
        this.setEmcLabel("Scarce Priority");
        this.setMandatory(true);
    }

}

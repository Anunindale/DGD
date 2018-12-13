/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.pop.costchange;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class ReasonId extends EMCString {

    public ReasonId() {
        this.setEmcLabel("Reason");
        this.setMandatory(true);
    }
}

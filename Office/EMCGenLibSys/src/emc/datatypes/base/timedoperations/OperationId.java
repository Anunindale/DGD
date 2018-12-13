/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.timedoperations;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class OperationId extends EMCString {

    public OperationId() {
        this.setEmcLabel("Operation");
        this.setMandatory(true);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.pop.purchaseorderlines;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class DesignCode extends EMCString {

    public DesignCode() {
        this.setEmcLabel("% Cover");
        this.setLowerCaseAllowed(true);
        this.setMandatory(false);
    }
}

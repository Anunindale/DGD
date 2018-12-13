/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.rebatecodes;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class RebateCode extends EMCString {

    /** Creates a new instance of RebateCode. */
    public RebateCode() {
        this.setEmcLabel("Rebate Code");
        this.setMandatory(true);
    }
}

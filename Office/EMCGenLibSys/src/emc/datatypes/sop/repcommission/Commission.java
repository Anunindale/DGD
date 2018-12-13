/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.repcommission;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author wikus
 */
public class Commission extends EMCBigDecimal {

    public Commission() {
        this.setEmcLabel("Commission %");
        this.setMinValue(0);
    }
}

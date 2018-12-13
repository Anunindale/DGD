/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.repgroupsetup;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author wikus
 */
public class DefaultCommission extends EMCBigDecimal {

    public DefaultCommission() {
        this.setEmcLabel("Default Commission");
        this.setMinValue(0);
    }
}

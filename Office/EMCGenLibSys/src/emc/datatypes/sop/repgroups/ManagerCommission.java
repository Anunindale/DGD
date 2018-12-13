/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.repgroups;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author wikus
 */
public class ManagerCommission extends EMCBigDecimal {

    public ManagerCommission() {
        this.setEmcLabel("Rep Commission");
        this.setMinValue(0);
    }
}

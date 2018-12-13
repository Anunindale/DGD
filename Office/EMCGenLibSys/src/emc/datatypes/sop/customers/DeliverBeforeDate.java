/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.customers;

import emc.datatypes.EMCInt;

/**
 *
 * @author wikus
 */
public class DeliverBeforeDate extends EMCInt {

    public DeliverBeforeDate() {
        this.setEmcLabel("Deliver Before");
        this.setMinValue(0);
        this.setMaxValue(31);
    }

}

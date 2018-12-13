/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.salesordermaster;

import emc.datatypes.pop.deliverymodes.foreignkeys.DeliveryModeIdFK;

/**
 *
 * @author riaan
 */
public class DeliveryMethod extends DeliveryModeIdFK {

    /** Creates a new instance of DeliveryMethod. */
    public DeliveryMethod() {
        this.setEmcLabel("Delivery Method");
    }
}

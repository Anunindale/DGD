/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.deliverymodes;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class DeliveryModeId extends EMCString {

    /** Creates a new instance of DeliveryModeId */
    public DeliveryModeId() {
        this.setEmcLabel("Delivery Mode");
        this.setMandatory(true);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.customers;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class DeliveryRules extends EMCString {
    
    public DeliveryRules() {
        this.setEmcLabel("Delivery Rules");
        this.setMandatory(true);
    }
}

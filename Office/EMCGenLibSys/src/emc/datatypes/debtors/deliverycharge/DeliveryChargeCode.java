/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.deliverycharge;

import emc.datatypes.EMCString;

/**
 * @description : Data type for deliveryChargeCode on DebtorsDeliveryCharge.
 *
 * @date        : 07 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class DeliveryChargeCode extends EMCString {

    /** Creates a new instance of DeliveryChargeCode */
    public DeliveryChargeCode() {
        this.setMandatory(true);
        this.setEmcLabel("Delivery Charge Code");
    }
}

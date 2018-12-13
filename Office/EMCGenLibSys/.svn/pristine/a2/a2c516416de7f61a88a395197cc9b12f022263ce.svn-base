/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.deliverycharge.foreignkeys;

import emc.datatypes.debtors.deliverycharge.DeliveryChargeCode;
import emc.entity.debtors.DebtorsDeliveryCharge;

/**
 * @description : Foreign key data type to deliveryChargeCode on DebtorsDeliveryCharge.
 *
 * @date        : 07 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class DeliveryChargeCodeFK extends DeliveryChargeCode {

    /** Creates a new instance of DeliveryChargeCodeFK */
    public DeliveryChargeCodeFK() {
        this.setRelatedTable(DebtorsDeliveryCharge.class.getName());
        this.setRelatedField("deliveryChargeCode");
        this.setMandatory(false);
    }
}

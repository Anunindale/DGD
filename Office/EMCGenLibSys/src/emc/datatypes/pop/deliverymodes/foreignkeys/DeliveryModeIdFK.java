/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.deliverymodes.foreignkeys;

import emc.datatypes.pop.deliverymodes.DeliveryModeId;
import emc.entity.pop.POPDeliveryModes;

/**
 *
 * @author riaan
 */
public class DeliveryModeIdFK extends DeliveryModeId {

    /** Creates a new instance of DeliveryModeIdFK */
    public DeliveryModeIdFK() {
        this.setMandatory(false);
        this.setRelatedTable(POPDeliveryModes.class.getName());
        this.setRelatedField("deliveryModeId");
    }
}

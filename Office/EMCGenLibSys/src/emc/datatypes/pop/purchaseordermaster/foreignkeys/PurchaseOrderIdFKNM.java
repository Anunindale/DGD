/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.purchaseordermaster.foreignkeys;

import emc.datatypes.pop.purchaseordermaster.PurchaseOrderId;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class PurchaseOrderIdFKNM extends PurchaseOrderId {

    /** Creates a new instance of PurchaseOrderIdFK */
    public PurchaseOrderIdFKNM() {
        this.setMandatory(false);
        this.setRelatedTable(POPPurchaseOrderMaster.class.getName());
        this.setRelatedField("purchaseOrderId");
        this.setNumberSeqAllowed(false);
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
    }
    
}

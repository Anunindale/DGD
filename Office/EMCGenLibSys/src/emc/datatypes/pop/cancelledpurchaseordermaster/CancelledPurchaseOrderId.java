/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.cancelledpurchaseordermaster;

import emc.datatypes.pop.purchaseordermaster.PurchaseOrderId;
import emc.entity.pop.POPPurchaseOrderMaster;

/**
 *
 * @author riaan
 */
public class CancelledPurchaseOrderId extends PurchaseOrderId {

    /** Creates a new instance of CancelledPurchaseOrderId. */
    public CancelledPurchaseOrderId() {
        this.setRelatedTable(POPPurchaseOrderMaster.class.getName());
        this.setRelatedField("purchaseOrderId");
        this.setNumberSeqAllowed(false);
    }
}

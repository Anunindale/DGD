/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.creditors.creditnoteinvoice;

import emc.datatypes.pop.purchaseordermaster.foreignkeys.PurchaseOrderIdFKNM;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class PurchaseOrderId extends PurchaseOrderIdFKNM{

    public PurchaseOrderId() {
        this.setDeleteAction(enumDeleteUpdateOptions.IGNORE);
    }

}

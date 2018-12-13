/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.purchaseordermaster.foreignkeys;

import emc.enums.datatypes.CallBeanLogicOptions;

/**
 *
 * @author riaan
 */
public class PurchaseOrderIdFKCascadeUseBean extends PurchaseOrderIdFKCascade {

    /** Creates a new instance of PurchaseOrderIdFKCascadeUseBean. */
    public PurchaseOrderIdFKCascadeUseBean() {
        this.setCallBeanOptions(CallBeanLogicOptions.UPDATE_AND_DELETE);
    }
}

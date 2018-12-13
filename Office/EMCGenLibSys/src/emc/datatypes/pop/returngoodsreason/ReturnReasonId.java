/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.pop.returngoodsreason;

import emc.datatypes.pop.purchasepostsetup.*;
import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class ReturnReasonId extends EMCString {

    /** Creates a new instance of PostSetupId */
    public ReturnReasonId() {
        this.setEmcLabel("Return Reason");
        this.setMandatory(true);
        this.setEditable(true);
    }
}

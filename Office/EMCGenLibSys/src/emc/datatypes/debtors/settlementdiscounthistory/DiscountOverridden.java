/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.settlementdiscounthistory;

import emc.datatypes.EMCBoolean;

/**
 *
 * @author riaan
 */
public class DiscountOverridden extends EMCBoolean {

    /** Creates a new instance of DiscountOverriden. */
    public DiscountOverridden() {
        this.setEmcLabel("Ovr.");
        this.setEditable(false);
    }
}

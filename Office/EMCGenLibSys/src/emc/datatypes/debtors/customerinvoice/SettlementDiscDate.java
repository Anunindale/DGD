/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.customerinvoice;

import emc.datatypes.EMCDate;

/**
 *
 * @author riaan
 */
public class SettlementDiscDate extends EMCDate {

    /** Creates a new instance of SettlementDiscDate */
    public SettlementDiscDate() {
        this.setEmcLabel("Settlement Disc. Date");
        this.setEditable(false);
    }
}

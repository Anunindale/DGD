/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.settlementdiscounthistory;

import emc.datatypes.EMCDate;

/**
 *
 * @author riaan
 */
public class DiscountDate extends EMCDate {

    /** Creates a new instance of DiscountDate. */
    public DiscountDate() {
        this.setEmcLabel("Date");
        this.setEditable(false);
    }
}

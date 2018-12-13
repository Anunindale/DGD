/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.creditnotes;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author riaan
 */
public class Quantity extends EMCBigDecimal {

    /** Creates a new instance of Quantity. */
    public Quantity() {
        this.setEmcLabel("Quantity");
        this.setMinValue(-10000000);
        this.setColumnWidth(53);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.transactionsettlement;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author riaan
 */
public class Rebate extends EMCBigDecimal {

    /** Creates a new instance of Rebate. */
    public Rebate() {
    	this.setColumnWidth(52);
        this.setEmcLabel("Rebate");
    }
}

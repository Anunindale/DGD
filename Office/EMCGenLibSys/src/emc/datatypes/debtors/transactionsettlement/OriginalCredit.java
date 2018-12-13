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
public class OriginalCredit extends EMCBigDecimal {

    /** Creates a new instance of OriginalCredit. */
    public OriginalCredit() {
        this.setEmcLabel("Credit");
    	this.setColumnWidth(81);
        this.setEditable(false);
    }
}

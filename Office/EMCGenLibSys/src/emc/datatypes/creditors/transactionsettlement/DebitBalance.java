/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.creditors.transactionsettlement;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author Owner
 */
public class DebitBalance extends EMCBigDecimal {

    public DebitBalance() {

        this.setColumnWidth(79);
        this.setEmcLabel("Debit Balance");
        this.setEditable(false);

    }
}

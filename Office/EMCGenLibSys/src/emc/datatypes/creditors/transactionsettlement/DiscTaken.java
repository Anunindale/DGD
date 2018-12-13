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
public class DiscTaken extends EMCBigDecimal {

    public DiscTaken() {
        this.setColumnWidth(59);
        this.setEmcLabel("Disc. Taken");
    }
}

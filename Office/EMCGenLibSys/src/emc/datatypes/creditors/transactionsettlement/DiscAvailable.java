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
public class DiscAvailable extends EMCBigDecimal {

    public DiscAvailable(){
        this.setColumnWidth(56);
        this.setEmcLabel("Disc. Avail.");
        this.setEditable(false);
    }

}

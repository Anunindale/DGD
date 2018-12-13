/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.blanketorderstatusenquiry;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author riaan
 */
public class QuantityToPack extends EMCBigDecimal {

    /** Creates a new instance of Balance. */
    public QuantityToPack() {
    	this.setColumnWidth(66);
        this.setEmcLabel("To Pack");
    }
}

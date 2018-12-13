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
public class Balance extends EMCBigDecimal {

    /** Creates a new instance of Balance. */
    public Balance() {
    	this.setColumnWidth(64);
        this.setEmcLabel("To Call Off");
    }
}

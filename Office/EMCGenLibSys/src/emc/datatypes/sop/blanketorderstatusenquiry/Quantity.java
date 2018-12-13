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
public class Quantity extends EMCBigDecimal{

    /** Creates a new instance of Quantity. */
    public Quantity() {
    	this.setColumnWidth(81);
        this.setEmcLabel("Line Quantity");
    }
}

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
public class PackedQuantity extends EMCBigDecimal {

    /** Creates a new instance of PackedQuantity. */
    public PackedQuantity() {
        this.setEmcLabel("Packed");
    	this.setColumnWidth(67);
        this.setAllowSort(false);
    }
}

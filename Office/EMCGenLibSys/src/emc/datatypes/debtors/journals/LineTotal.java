/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.journals;

import emc.datatypes.EMCBigDecimal;

/**
 * @description : Data type for lineTotal on DebtorsJournalLines.
 *
 * @date        : 04 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class LineTotal extends EMCBigDecimal {

    /** Creates a new instance of LineAmount */
    public LineTotal() {
        this.setEmcLabel("Total");
        this.setEditable(false);
    	this.setColumnWidth(81);
        this.setMinValue(-10000000000d);
    }
}

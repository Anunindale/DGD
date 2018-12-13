/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.journals;

import emc.datatypes.EMCString;

/**
 * @description : Data type for lineReference on DebtorsJournalLines.
 *
 * @date        : 04 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class LineReference extends EMCString {

    /** Creates a new instance of LineReference */
    public LineReference() {
        this.setEmcLabel("Reference");
        this.setMandatory(true);
    	this.setColumnWidth(66);
        this.setStringSize(9);
    }
}

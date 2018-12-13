/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.journals.superclass;

import emc.datatypes.gl.vatcodes.foreignkeys.VATCodeFK;

/**
 * @description : Data type for vatCode on DebtorsJournalLines.
 *
 * @date        : 04 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class VATCode extends VATCodeFK {

    /** Creates a new instance of VATCode */
    public VATCode() {
    	this.setColumnWidth(63);
        
    }
}

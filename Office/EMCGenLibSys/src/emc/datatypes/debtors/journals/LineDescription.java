/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.journals;

import emc.datatypes.EMCString;
import emc.datatypes.systemwide.Description;

/**
 * @description : Data type for lineDescription on DebtorsJournalLines.
 *
 * @date        : 04 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class LineDescription extends Description {

    /** Creates a new instance of LineDescription */
    public LineDescription() {
    	this.setColumnWidth(164);
        this.setEmcLabel("Description");
    }
}

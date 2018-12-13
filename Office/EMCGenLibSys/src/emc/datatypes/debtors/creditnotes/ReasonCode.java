/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.creditnotes;

import emc.datatypes.debtors.creditnotereasons.foreignkeys.ReasonCodeFK;

/**
 *
 * @author riaan
 */
public class ReasonCode extends ReasonCodeFK {

    /** Creates a new instance of ReasonCode. */
    public ReasonCode() {
    	this.setColumnWidth(46);
        
    }
}

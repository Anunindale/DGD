/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.gl.journalmaster;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class JournalNumber extends EMCString{

    public JournalNumber() {
        this.setEmcLabel("Journal Number");
        this.setMandatory(true);
    	this.setColumnWidth(103);
        this.setNumberSeqAllowed(true);
    }

}

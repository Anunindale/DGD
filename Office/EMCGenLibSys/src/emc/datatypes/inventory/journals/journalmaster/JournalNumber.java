/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.journals.journalmaster;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class JournalNumber extends EMCString {

    /** Creates a new instance of JournalNumber */
    public JournalNumber() {
        this.setEmcLabel("Journal Number");
        this.setMandatory(true);
        this.setEditable(true);
    	this.setColumnWidth(114);
        this.setNumberSeqAllowed(true);
    }
}

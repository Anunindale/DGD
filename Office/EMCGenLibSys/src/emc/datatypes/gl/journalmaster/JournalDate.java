/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.gl.journalmaster;

import emc.datatypes.EMCDate;

/**
 *
 * @author riaan
 */
public class JournalDate extends EMCDate {

    /** Creates a new instance of JournalDate */
    public JournalDate() {
        this.setEmcLabel("Journal Date");
        this.setMandatory(true);
        this.setColumnWidth(80);
    }
}

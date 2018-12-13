/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.reallocation;

import emc.datatypes.inventory.transactions.datasource.SerialNo;

/**
 *
 * @author riaan
 */
public class Serial extends SerialNo {

    /** Creates a new instance of Serial. */
    public Serial() {
        this.setEmcLabel("Serial");
        this.setEditable(false);
    }
}

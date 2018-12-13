/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.reallocation;

/**
 *
 * @author riaan
 */
public class Batch extends emc.datatypes.inventory.transactions.datasource.Batch {

    /** Creates a new instance of Batch. */
    public Batch() {
        this.setEmcLabel("Batch");
        this.setEditable(false);
    }
}

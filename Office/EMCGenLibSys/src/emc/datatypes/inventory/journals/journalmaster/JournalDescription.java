/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.journals.journalmaster;

/**
 *
 * @author rico
 */
public class JournalDescription extends emc.datatypes.systemwide.Description {
    public JournalDescription(){
    	this.setColumnWidth(200);
        this.setMandatory(true);
    }
}

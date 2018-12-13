/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.journals.journalmaster;

import emc.datatypes.EMCString;

/**
 *
 * @author rico
 */
public class JournalStatus extends EMCString{
    public JournalStatus(){
    	this.setColumnWidth(78);
        setEmcLabel("Status");
    }
}

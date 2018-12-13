/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.journals.journalmaster;

import emc.datatypes.EMCDate;


/**
 *
 * @author rico
 */
public class JournalCreatedDate extends EMCDate{
    public JournalCreatedDate(){
    	this.setColumnWidth(81);
        this.setEmcLabel("Created Date");
        
    }

}

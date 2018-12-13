/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.journals.datasource;

import emc.entity.inventory.journals.InventoryJournalMaster;

/**
 *
 * @author riaan
 */
public class InventoryJournalMasterDS extends InventoryJournalMaster {

    private String journalType;
        
    public InventoryJournalMasterDS() {
        this.setDataSource(true);
    }
    
    public String getJournalType() {
        return journalType;
    }

    public void setJournalType(String journalType) {
        this.journalType = journalType;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.journals.journaldefinition.foreignkeys;

import emc.datatypes.base.journals.journaldefinition.JournalDefinitionId;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class JournalDefinitionIdFK extends JournalDefinitionId {
    
    /** Creates a new instance of JournalDefinitionIdFK */
    public JournalDefinitionIdFK() {
        this.setRelatedTable(BaseJournalDefinitionTable.class.getName());
        this.setRelatedField("journalDefinitionId");
        this.setColumnWidth(83);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
    }

}

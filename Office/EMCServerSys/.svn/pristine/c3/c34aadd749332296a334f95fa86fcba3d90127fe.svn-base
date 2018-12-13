/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.base.journals;

import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.base.journals.superclass.JournalMasterSuperClass;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.enums.base.journals.Modules;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface BaseJournalDefinitionLocal extends EMCEntityBeanLocalInterface {
    emc.entity.base.journals.BaseJournalDefinitionTable getJournalDefinition(InventoryJournalMaster master, emc.framework.EMCUserData userData);
    BaseJournalDefinitionTable getJournalDefinition(String journalNumber, EMCUserData userData);

    BaseJournalDefinitionTable getJournalDefinition(JournalMasterSuperClass journal, Modules journalModule, EMCUserData userData);
    
    /**
     * Returns a JournalDefinitionTable instance.
     * @param journalDefinitionId Id of journal definition to search for.
     * @param module Journal definition module.
     * @param userData User data.
     * @return A JournalDefinitionTable instance, or null, if nothing is found.
     */
    public BaseJournalDefinitionTable getJournalDefinition(String journalDefinitionId, Modules module, EMCUserData userData);
}

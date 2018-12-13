/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.inventory.display.journaldefinitions;

import emc.enums.inventory.journals.InventoryJournalTypes;
import emc.enums.base.journals.Modules;
import emc.enums.inventory.journals.MovementDirections;
import emc.forms.app.journals.journaldefinitions.JournalDefinitionsForm;
import emc.framework.EMCUserData;

/**
 *
 * @author riaan
 */
public class JournalDefinitionForm  extends JournalDefinitionsForm {
    
    public JournalDefinitionForm(EMCUserData userData){
        super(userData, Modules.INVENTORY);
    }

    @Override
    protected String[] getJournalDirections() {
        return new String[] {MovementDirections.IN.toString(), MovementDirections.OUT.toString()};
    }

    @Override
    protected String[] getJournalTypes() {
        return new String[] {InventoryJournalTypes.MOVEMENT.toString(), InventoryJournalTypes.STOCKTAKE.toString(), InventoryJournalTypes.TRANSFER.toString()};
    }
}


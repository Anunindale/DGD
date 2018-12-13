/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.numbersequence;

import emc.enums.modules.enumEMCModules;
import emc.forms.app.numbersequence.NumberSequenceForm;
import emc.framework.EMCUserData;

/**
 *
 * @author wikus
 */
public class InventoryNumberSequenceForm extends NumberSequenceForm {

    public InventoryNumberSequenceForm(EMCUserData userData) {
        super( enumEMCModules.INVENTORY, userData);
    }
}

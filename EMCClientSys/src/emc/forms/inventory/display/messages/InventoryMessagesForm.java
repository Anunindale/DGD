/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.messages;

import emc.enums.modules.enumEMCModules;
import emc.forms.app.messages.MessagesForm;
import emc.framework.EMCUserData;

/**
 *
 * @author Owner
 */
public class InventoryMessagesForm extends MessagesForm {

    public InventoryMessagesForm(EMCUserData userData) {
        super(enumEMCModules.INVENTORY, userData);
    }
}

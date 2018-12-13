/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.creditors.display.messages;

import emc.enums.modules.enumEMCModules;
import emc.forms.app.messages.MessagesForm;
import emc.framework.EMCUserData;

/**
 *
 * @author Owner
 */
public class CreditorsMessagesForm extends MessagesForm {

    public CreditorsMessagesForm(EMCUserData userData) {
        super(enumEMCModules.CREDITORS, userData);

    }
}

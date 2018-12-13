/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.hr.display.messages;

import emc.enums.modules.enumEMCModules;
import emc.forms.app.messages.MessagesForm;
import emc.framework.EMCUserData;

/**
 *
 * @author Owner
 */
public class HRMessagesForm extends MessagesForm {

    public HRMessagesForm(EMCUserData userData) {

        super(enumEMCModules.HR, userData);

    }
}

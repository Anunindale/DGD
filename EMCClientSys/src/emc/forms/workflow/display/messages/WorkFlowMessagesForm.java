/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.workflow.display.messages;

import emc.enums.modules.enumEMCModules;
import emc.forms.app.messages.MessagesForm;
import emc.framework.EMCUserData;

/**
 *
 * @author Owner
 */
public class WorkFlowMessagesForm extends MessagesForm {

    public WorkFlowMessagesForm(EMCUserData userData) {
        super(enumEMCModules.WORKFLOW, userData);

    }
}

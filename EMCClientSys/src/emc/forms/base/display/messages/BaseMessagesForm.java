/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.messages;

import emc.enums.modules.enumEMCModules;
import emc.forms.app.messages.MessagesForm;
import emc.framework.EMCUserData;

/**
 * @name        MessagesForm
 *
 * @date        20 Apr 2009
 *
 * @desciption  This form provides a user interface to the BaseMessages records for the Base module.
 *
 * @author      riaan
 */
public class BaseMessagesForm extends MessagesForm {

    /** Creates a new instance of BaseMessagesForm. */
    public BaseMessagesForm(EMCUserData userData) {
        super(enumEMCModules.BASE, userData);
    }
}


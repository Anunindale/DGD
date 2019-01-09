/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.sendmsgtoall.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class SendOKAll extends emcJButton {

    private emcDataRelationManagerUpdate dataRelation;
    private emcJTextArea message;
    private emcJLabel sendResult;

    public SendOKAll(emcDataRelationManagerUpdate dataRelation, emcJTextArea message, emcJLabel sendResult) {
        super("OK");
        this.dataRelation = dataRelation;
        this.message = message;
        this.sendResult = sendResult;

    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        EMCCommandClass cmd = new EMCCommandClass();
        cmd.setCommandId(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId());
        cmd.setModuleNumber(enumEMCModules.BASE.getId());
        cmd.setMethodId(emc.methods.base.ServerBaseMethods.ADMIN_MESSAGE_TO_ALL_USERS.toString());
        List toSend = new ArrayList();
        try {
            toSend.add(message.getText());
            List result = EMCWSManager.executeGenericWS(cmd, toSend, dataRelation.getTheForm().getUserData());
            sendResult.setText("Message sent.");
            message.setText("");
        } catch (Exception e) {
            sendResult.setText("Message failed. " + e.getMessage());
        }

    }
}

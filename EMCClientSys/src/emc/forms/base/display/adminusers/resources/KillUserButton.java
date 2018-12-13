/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.adminusers.resources;

import emc.app.components.emcDialogue;
import emc.app.components.emcMenuButton;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCMenuItem;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author rico
 */
public class KillUserButton extends emcMenuButton {

    private BaseInternalFrame form;
    private emcDRMViewOnly dataRelation;

    public KillUserButton(String label, EMCMenuItem buttonitem, BaseInternalFrame form, int relatedFormIndex, boolean lines) {
        super(label, buttonitem, form, relatedFormIndex, lines);
        this.form = form;
        dataRelation = (emcDRMViewOnly) form.getDataManager();
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        emcDialogue resetOK = new emcDialogue("Kill Session", "Kill session " + dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), "userName") + ": Are you sure?",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);
        if (resetOK.getDialogueResult() == JOptionPane.YES_OPTION) {
            EMCCommandClass cmd = new EMCCommandClass();
            cmd.setCommandId(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId());
            cmd.setModuleNumber(enumEMCModules.BASE.getId());
            cmd.setMethodId(emc.methods.base.ServerBaseMethods.KILL_USER_SESSION.toString());
            List toSend = new ArrayList();
            try {
                toSend.add(dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), "sessionNumber"));
                List result = EMCWSManager.executeGenericWS(cmd, toSend, form.getUserData());
                Logger.getLogger("emc").log(Level.INFO, "Kill message sent to " + dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), "userName"), form.getUserData());
            } catch (Exception ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to send Kill message.", form.getUserData());
            }
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.journal.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.gl.ServerGLMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;

/**
 *
 * @author riaan
 */
public class ApproveButton extends emcMenuButtonList {

    private emcDataRelationManagerUpdate journalsDRM;

    /** Creates a new instance of ApproveButton. */
    public ApproveButton(emcDataRelationManagerUpdate journalsDRM) {
        super("Approval", journalsDRM.getTheForm());
        this.journalsDRM = journalsDRM;

        this.addMenuItem("Approve", null, 0, false);
        this.addMenuItem("Unapprove", null, 0, false);
    }

    @Override
    public void executeCmd(String theCmd) {
        if ("Approve".equals(theCmd)) {
            if (EMCDialogFactory.createQuestionDialog(journalsDRM.getTheForm(), "Approve Journal?", "Approve Journal?") == JOptionPane.YES_OPTION) {
                EMCUserData userData = journalsDRM.getUserData();
                EMCCommandClass cmd = new EMCCommandClass(ServerGLMethods.APPROVE_JOURNAL);

                List toSend = new ArrayList();
                toSend.add(journalsDRM.getLastFieldValueAt("journalNumber"));

                toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                if (toSend != null && toSend.size() > 1 && toSend.get(1) == Boolean.TRUE) {
                    utilFunctions.logMessage(Level.INFO, "Journal approved successfully.", userData);
                    journalsDRM.refreshRecordIgnoreFocus(journalsDRM.getLastRowAccessed());
                } else {
                    utilFunctions.logMessage(Level.SEVERE, "Failed to approve journal.", userData);
                }
            }
        } else if ("Unapprove".equals(theCmd)) {
            if (EMCDialogFactory.createQuestionDialog(journalsDRM.getTheForm(), "Unapprove Journal?", "Unapprove Journal?") == JOptionPane.YES_OPTION) {
                EMCUserData userData = journalsDRM.getUserData();
                EMCCommandClass cmd = new EMCCommandClass(ServerGLMethods.UNAPPROVE_JOURNAL);

                List toSend = new ArrayList();
                toSend.add(journalsDRM.getLastFieldValueAt("journalNumber"));

                toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                if (toSend != null && toSend.size() > 1 && toSend.get(1) == Boolean.TRUE) {
                    utilFunctions.logMessage(Level.INFO, "Journal unapproved successfully.", userData);
                    journalsDRM.refreshRecordIgnoreFocus(journalsDRM.getLastRowAccessed());
                } else {
                    utilFunctions.logMessage(Level.SEVERE, "Failed to unapprove journal.", userData);
                }
            }
        }
    }
}

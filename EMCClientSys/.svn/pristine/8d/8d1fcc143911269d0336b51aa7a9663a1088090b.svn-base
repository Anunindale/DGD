/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.journal.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
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
public class PostButton extends emcJButton {

    private emcDataRelationManagerUpdate journalsDRM;

    /** Creates a new instance of PostButton. */
    public PostButton(emcDataRelationManagerUpdate journalsDRM) {
        super("Post");
        this.journalsDRM = journalsDRM;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        if (EMCDialogFactory.createQuestionDialog(journalsDRM.getTheForm(), "Post Journal?", "Post Journal?") == JOptionPane.YES_OPTION) {
            EMCUserData userData = journalsDRM.getUserData();
            EMCCommandClass cmd = new EMCCommandClass(ServerGLMethods.POST_JOURNAL);

            List toSend = new ArrayList();
            toSend.add(journalsDRM.getRowCache(journalsDRM.getLastRowAccessed()));

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

            if (toSend != null && toSend.size() > 1 && toSend.get(1) == Boolean.TRUE) {
                utilFunctions.logMessage(Level.INFO, "Journal posted successfully.", userData);
                journalsDRM.refreshRecordIgnoreFocus(journalsDRM.getLastRowAccessed());
            } else {
                utilFunctions.logMessage(Level.SEVERE, "Failed to post journal.", userData);
            }
        }
    }
}

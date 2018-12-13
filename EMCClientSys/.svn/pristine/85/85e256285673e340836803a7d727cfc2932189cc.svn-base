/*
 * setPassword.java
 *
 * Created on 22 November 2007, 01:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.forms.personal.display.users.resources;

import emc.app.components.emcDialogue;
import emc.app.components.emcJButton;
import emc.app.components.emcJPasswordField;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author wikus
 */
public class setPassword extends emcJButton {

    private emcDataRelationManagerUpdate dataRelation;
    private emcJPasswordField newpwd;
    private emcJPasswordField reppwd;

    /** Creates a new instance of setPassword */
    public setPassword(String label, emcDataRelationManagerUpdate dataRelation, emcJPasswordField newpwd, emcJPasswordField reppwd) {
        super(label);
        this.dataRelation = dataRelation;
        this.newpwd = newpwd;
        this.reppwd = reppwd;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        String pword = new String(newpwd.getPassword());
        String checkpword = new String(reppwd.getPassword());
        if (pword.equals(checkpword)) {
            emcDialogue resetOK = new emcDialogue("Click a button", "Set User Password. Are you sure?",
                    JOptionPane.QUESTION_MESSAGE,
                    JOptionPane.YES_NO_OPTION);
            if (resetOK.getDialogueResult() == JOptionPane.YES_OPTION) {

                dataRelation.setFieldValueAt(dataRelation.getLastRowAccessed(), "userPassword", pword);
                dataRelation.updatePersist(dataRelation.getLastRowAccessed());
            } else {
                //do nothing
            }
        } else {
            emcDialogue resetOK = new emcDialogue("Data Error", "New Password does not match confirmed Password.",
                    JOptionPane.ERROR_MESSAGE,
                    JOptionPane.DEFAULT_OPTION);

        }

    }
}

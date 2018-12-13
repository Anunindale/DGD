/*
 * resetPasswordButton.java
 *
 * Created on 21 November 2007, 11:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.forms.personal.display.users.resources;

import emc.app.components.emcDialogue;
import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author wikus
 */
public class resetPasswordButton extends emcJButton {

    private emcDataRelationManagerUpdate dataRelation;

    /** Creates a new instance of resetPasswordButton */
    public resetPasswordButton(String label, emcDataRelationManagerUpdate dataRelation) {
        super(label);
        this.dataRelation = dataRelation;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {

        emcDialogue resetOK = new emcDialogue("Click a button", "Reset User Password to emc123: Are you sure?",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);
        if (resetOK.getDialogueResult() == JOptionPane.YES_OPTION) {
            dataRelation.setFieldValueAt(dataRelation.getLastRowAccessed(), "userPassword", "emc123");
            dataRelation.updatePersist(dataRelation.getLastRowAccessed());
        } else {
            //do nothing
        }

    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.posting.resources;

import emc.app.components.emcDialogue;
import emc.app.components.emcMenuButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.enums.pop.posting.DocumentTypes;
import emc.forms.pop.display.posting.PostingForm;
import emc.framework.EMCCommandClass;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author riaan
 */
public class CancelButton extends emcMenuButton {

    private PostingForm form;

    /** Creates a new instance of CancelButton */
    public CancelButton(PostingForm form) {
        super("Cancel", null, form, 0, false);
        this.form = form;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        emcDialogue dialog = new emcDialogue("Are You Sure", "Cancel Post?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (dialog.getDialogueResult() == 0) {
            if (form.getFormType().equals(DocumentTypes.RETURN_GOODS.toString())) {
                emcDataRelationManagerUpdate drm = (emcDataRelationManagerUpdate) form.getDataManager();
                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.DELETE_POST_DATA.toString());
                List toSend = new ArrayList();
                toSend.add(drm.getFieldValueAt(drm.getLastRowAccessed(), "postSetupId"));
                EMCWSManager.executeGenericWS(cmd, toSend, form.getUserData());
            }
            form.dispose();
        }
    }
}

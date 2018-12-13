/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.journals.resources;

import emc.app.components.emcDialogue;
import emc.app.components.emcMenuButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.framework.EMCCommandClass;
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
public class PostButton extends emcMenuButton {

    private emcDataRelationManagerUpdate drm;

    public PostButton(emcDataRelationManagerUpdate drm) {
        super("Post");
        this.drm = drm;

    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        //prompt user
        super.doActionPerformed(evt);
        InventoryJournalMaster jMast = (InventoryJournalMaster) drm.getRowCache(-1);
        int dialogResult = new emcDialogue("Post Journal", "You are about to update stock levels, proceed?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION).getDialogueResult();

        if (dialogResult == JOptionPane.YES_OPTION) {
            EMCCommandClass cmd = new EMCCommandClass(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId(), emc.enums.modules.enumEMCModules.INVENTORY.getId(), emc.methods.inventory.ServerInventoryMethods.POST_INVENTORYJOURNAL.toString());
            List toSend = new ArrayList();
            toSend.add(jMast);
            toSend = EMCWSManager.executeGenericWS(cmd, toSend, drm.getUserData());
            if (toSend.size() > 1 && (Boolean) toSend.get(1)) {
                drm.refreshRecordIgnoreFocus(-1);
                Logger.getLogger("emc").log(Level.INFO, "Journal Posted", drm.getUserData());
            }

        } else if (dialogResult == JOptionPane.NO_OPTION) {
        }

    }
}

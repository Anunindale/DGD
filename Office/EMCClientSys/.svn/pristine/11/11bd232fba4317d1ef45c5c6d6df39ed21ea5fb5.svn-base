/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.journals.resources;

import emc.app.components.emcDialogue;
import emc.app.components.emcMenuButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.enums.base.journals.JournalStatus;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Riaan
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
        
        if (JournalStatus.POSTED.equals(JournalStatus.fromString((String)drm.getLastFieldValueAt("journalStatus")))) {
            Logger.getLogger("emc").log(Level.SEVERE, "Journal already posted.", drm.getUserData());
            return;
        }
        
        DebtorsJournalMaster jMast = (DebtorsJournalMaster) drm.getRowCache(-1);

        int dialogResult = new emcDialogue("Post Journal", "Are you sure that you want to post the selected journal?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION).getDialogueResult();

        if (dialogResult == JOptionPane.YES_OPTION) {
            EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.POST_DEBTORS_JOURNAL.toString());
            List toSend = new ArrayList();
            toSend.add(jMast);
            toSend = EMCWSManager.executeGenericWS(cmd, toSend, drm.getUserData());
            if (toSend.size() > 1 && (Boolean) toSend.get(1)) {
                drm.refreshRecordIgnoreFocus(-1);
                Logger.getLogger("emc").log(Level.INFO, "Journal Posted", drm.getUserData());
            }

        } 
    }
}

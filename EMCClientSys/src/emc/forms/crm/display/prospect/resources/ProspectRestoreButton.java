/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.crm.display.prospect.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.enums.crm.CRMProspectStatus;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author rico
 */
public class ProspectRestoreButton extends emcJButton {

    private emcDataRelationManagerUpdate dataManager;

    public ProspectRestoreButton(emcDataRelationManagerUpdate dataManager) {
        super("Activate");
        this.dataManager = dataManager;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        if ((Long)dataManager.getLastFieldValueAt("recordID") == 0) {
            return;
        }


        if (dataManager.getLastFieldValueAt("prosStatus").equals(CRMProspectStatus.ACTIVE.toString())) {
            Logger.getLogger("emc").log(Level.SEVERE, "The prospect is active.");
            return;
        }


        if (EMCDialogFactory.createQuestionDialog(dataManager.getTheForm(), "Activate?",
                "Are you sure you want to activate the selected Prospect") == JOptionPane.YES_OPTION) {

            //do Restore
            dataManager.setFieldValueAt(dataManager.getLastRowAccessed(), "prosStatus", CRMProspectStatus.ACTIVE.toString());
            dataManager.setFieldValueAt(dataManager.getLastRowAccessed(), "reasonCode", null);
            dataManager.updatePersist(dataManager.getLastRowAccessed());
            dataManager.refreshData();
        }
    }


}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.crm.display.prospect.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.enums.crm.CRMProspectStatus;
import emc.functions.Functions;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author rico
 */
public class ProspectCloseButton extends emcJButton {

    private emcDataRelationManagerUpdate dataManager;

    public ProspectCloseButton(emcDataRelationManagerUpdate dataManager) {
        super("Deactivate");
        this.dataManager = dataManager;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        if ((Long)dataManager.getLastFieldValueAt("recordID") == 0) {
            return;
        }


        if (dataManager.getLastFieldValueAt("prosStatus").equals(CRMProspectStatus.CLOSED.toString())) {
            Logger.getLogger("emc").log(Level.SEVERE, "The prospect is inactive.");
            return;
        }


        if (EMCDialogFactory.createQuestionDialog(dataManager.getTheForm(), "Deactivate?",
                "Are you sure you want to deactivate the selected Prospect") == JOptionPane.YES_OPTION) {

            String reason = new ProspectCloseReasonDialog(dataManager.getUserData()).getReason();
            if (Functions.checkBlank(reason)) {
                return;
            }

            //do Close
            dataManager.setFieldValueAt(dataManager.getLastRowAccessed(), "prosStatus", CRMProspectStatus.CLOSED.toString());
            dataManager.setFieldValueAt(dataManager.getLastRowAccessed(), "reasonCode", reason);
            dataManager.updatePersist(dataManager.getLastRowAccessed());
            dataManager.refreshData();
        }
    }


}

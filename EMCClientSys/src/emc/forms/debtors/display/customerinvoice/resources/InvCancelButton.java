/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.customerinvoice.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
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
 * @author wikus
 */
public class InvCancelButton extends emcJButton {

    private emcDataRelationManagerUpdate dataManager;

    public InvCancelButton(emcDataRelationManagerUpdate dataManager) {
        super("Cancel");
        this.dataManager = dataManager;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        if (EMCDialogFactory.createQuestionDialog(utilFunctions.findEMCDesktop(this), "Cancel Invoice", "Are you sure you want to cancel the invoice?") == JOptionPane.YES_OPTION) {
            EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.CANCEL_CUSTOMER_INVOICE);

            List toSend = new ArrayList();
            toSend.add(dataManager.getLastFieldValueAt("invCNNumber"));

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, dataManager.getUserData());

            if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                Logger.getLogger("emc").log(Level.INFO, "Invoice Cancelled.", dataManager.getUserData());

                dataManager.refreshRecordIgnoreFocus(dataManager.getLastRowAccessed());
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to cancel the invoice.", dataManager.getUserData());
            }
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.transactions.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcMenuButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author riaan
 */
public class DeallocateButton extends emcMenuButton {

    private emcDataRelationManagerUpdate drm;

    /** Creates a new instance of DeallocateButton. */
    public DeallocateButton(BaseInternalFrame form, emcDataRelationManagerUpdate drm) {
        super("Deallocate", null, form, 0, false);
        this.drm = drm;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        EMCUserData userData = drm.getUserData().copyUserData();

        if (EMCDialogFactory.createQuestionDialog(drm.getTheForm(), "Deallocate Transaction?", "Are you sure that you want to reverse all the allocations made against this transaction?") == JOptionPane.YES_OPTION) {
            EMCCommandClass command;

            if (((BigDecimal) drm.getLastFieldValueAt("debit")).compareTo(BigDecimal.ZERO) > 0) {
                command = new EMCCommandClass(ServerDebtorsMethods.DEALLOCATE_DEBIT);
            } else {
                command = new EMCCommandClass(ServerDebtorsMethods.DEALLOCATE_CREDIT);
            }

            List toSend = new ArrayList();
            //Send record ID to server
            toSend.add(drm.getLastFieldValueAt("recordID"));

            toSend = EMCWSManager.executeGenericWS(command, toSend, drm.getUserData());

            if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                Logger.getLogger("emc").log(Level.INFO, "Successfully deallocated transaction.", drm.getUserData());
                drm.refreshData();
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to deallocate transaction.", drm.getUserData());
            }
        }
    }
}

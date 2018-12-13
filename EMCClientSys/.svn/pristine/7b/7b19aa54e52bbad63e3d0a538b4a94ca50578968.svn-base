/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.transactionsettlementhistory.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
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
 * @description : This button is used to deallocate transactions.
 *
 * @date        : 27 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class DeallocateButton extends emcJButton {

    private emcDataRelationManagerUpdate drm;

    /** Creates a new instance of DeallocateButton */
    public DeallocateButton(BaseInternalFrame form, emcDataRelationManagerUpdate drm) {
        super("Deallocate");

        this.drm = drm;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        if (EMCDialogFactory.createQuestionDialog(drm.getTheForm(), "Deallocate?", "Deallocate selected settlement?") == JOptionPane.YES_OPTION) {
            EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.DEALLOCATE_SETTLEMENT);

            List toSend = new ArrayList();
            //Send settlement history record to server.
            toSend.add(drm.getRowCache(drm.getLastRowAccessed()));

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, drm.getUserData());

            if (toSend != null && toSend.size() > 0 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                Logger.getLogger("emc").log(Level.INFO, "Successfully deallocated.", drm.getUserData());
                drm.refreshData();
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to deallocate settlement.", drm.getUserData());
            }
        }
    }
}

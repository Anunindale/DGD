/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.debtors.display.creditheld.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author riaan
 */
public class CreditHeldMasterApproveButton extends emcJButton {

    private CreditHeldMasterDRM drm;
    
    /** Creates a new instance of MasterApproveButton. */
    public CreditHeldMasterApproveButton(CreditHeldMasterDRM drm) {
        super("Approve");
        this.drm = drm;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        String reference = (String)drm.getLastFieldValueAt("reference");
        EMCUserData userData = drm.getUserData();

        if (Functions.checkBlank(reference)) {
            Logger.getLogger("emc").log(Level.SEVERE, "No Credit Held Master selected", userData);
        }
        if (EMCDialogFactory.createQuestionDialog(drm.getTheForm(), "Approve All", "Do you wish to approve all the lines on the selected Credit Held Master?") == JOptionPane.YES_OPTION) {
            EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.APPROVE_CREDIT_HELD_MASTER);

            List toSend = new ArrayList();
            toSend.add(reference);
            toSend.add(drm.getLastFieldValueAt("referenceType"));

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

            if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean)toSend.get(1)) {
                Logger.getLogger("emc").log(Level.INFO, "Successfully approved Credit Held Master.");
                drm.refreshDataIgnoreFocus();
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to approve Credit Held Master", userData);
            }
        }
    }
}

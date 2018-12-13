/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.debtors.display.allocationimport.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;

/**
 *
 * @author riaan
 */
public class ClearLogButton extends emcJButton {

    private emcDataRelationManagerUpdate drm;

    /** Creates a new instance of ClearLogButton. */
    public ClearLogButton(emcDataRelationManagerUpdate drm) {
        super("Clear Log");
        this.drm = drm;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        if (EMCDialogFactory.createQuestionDialog(drm.getTheForm(), "Clear Log?", "Are you sure that you want to clear the log for the selected import?") == JOptionPane.YES_OPTION) {
            EMCUserData userData = drm.getUserData();
            EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.CLEAR_FAIL_LOG);

            List toSend = new ArrayList();
            toSend.add(drm.getLastFieldValueAt("importCode"));

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

            if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean)toSend.get(1)) {
                utilFunctions.logMessage(Level.INFO, "Successfully cleared log.", userData);
            } else {
                utilFunctions.logMessage(Level.SEVERE, "Failed to clear log.", userData);

            }


        }
    }
}

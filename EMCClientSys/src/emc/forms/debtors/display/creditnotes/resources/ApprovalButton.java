/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.debtors.display.creditnotes.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @description : This button is used to approve/unapprove Credit Notes.
 *
 * @date        : 19 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class ApprovalButton extends emcMenuButtonList {

    private emcDataRelationManagerUpdate drm;
    
    /** Creates a new instance of ApprovalButton */
    public ApprovalButton(emcDataRelationManagerUpdate drm, EMCUserData userData) {
        super("Approval", drm.getTheForm());

        this.drm = drm;

        this.addMenuItem("Approve", null, 0, false);
    }

    @Override
    public void executeCmd(String theCmd) {
        if (theCmd.equals("Approve")) {
            EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.APPROVE_CREDIT_NOTE);

            List toSend = new ArrayList();
            toSend.add(drm.getLastFieldValueAt("invCNNumber"));

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, drm.getUserData());

            if (toSend != null && toSend.size() > 1 && toSend.get(1) == Boolean.TRUE) {
                Logger.getLogger("emc").log(Level.INFO, "Successfully approved Credit Note.", drm.getUserData());
                drm.refreshRecordIgnoreFocus(drm.getLastRowAccessed());
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to approve Credit Note.", drm.getUserData());
            }
        }
    }
}

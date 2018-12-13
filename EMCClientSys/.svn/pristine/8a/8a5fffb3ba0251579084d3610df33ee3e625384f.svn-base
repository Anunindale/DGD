/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.crm.display.prospect.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.crm.CRMProspectCloseReason;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.crm.menuitems.display.CRMProspectCloseReasonMI;
import emc.methods.crm.ServerCRMMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rico
 */
public class ProspectCloseReasonDialog extends emcJDialog {

    private EMCLookup lkpReason;
    private String reason;
    private EMCUserData userData;

    public ProspectCloseReasonDialog(EMCUserData userData) {
        super(null, "Deactivate Reason", true);
        this.userData = userData;
        initFrame();
        pack();
        setVisible(true);
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(lookupPane(), BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcJTabbedPane lookupPane() {
        lkpReason = new EMCLookup(new CRMProspectCloseReasonMI());
        lkpReason.setPopup(new EMCLookupPopup(new CRMProspectCloseReason(), "reasonId", userData));
        Component[][] comp = {{new emcJLabel("Reason"), lkpReason}};
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Reason", emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true));
        return tabbed;
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                reason = (String) lkpReason.getValue();
                if (Functions.checkBlank(reason)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select the reason.", userData);
                    reason = null;
                } else {
                    EMCCommandClass cmd = new EMCCommandClass(ServerCRMMethods.VALIDATE_CLOSE_REASON);
                    List toSend = new ArrayList();
                    toSend.add(reason);
                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
                    if (toSend.size() > 0 && (Boolean) toSend.get(1)) {
                        ProspectCloseReasonDialog.this.dispose();
                    } else {
                       reason = null;
                    }
                }
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                reason = null;
                ProspectCloseReasonDialog.this.dispose();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    public String getReason() {
        return reason;
    }
}


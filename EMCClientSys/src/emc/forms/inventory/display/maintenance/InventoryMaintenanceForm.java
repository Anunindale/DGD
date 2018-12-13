/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.maintenance;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
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
public class InventoryMaintenanceForm extends BaseInternalFrame {

    private EMCUserData userData;

    public InventoryMaintenanceForm(EMCUserData userData) {
        super("Maintenance", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);

        this.userData = userData.copyUserData();

        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Maintenance", overviewPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcJPanel overviewPane() {
        Component[][] comp = {{new emcJLabel("Maintenance Tasks for the Inventory Module", false)}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel buttonPane() {
        emcJButton btnFixBO = new emcJButton("Fix BO") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                int fix = EMCDialogFactory.createQuestionDialog(utilFunctions.findEMCDesktop(this), "Fix BO", "Are you sure you want to run the fix for Blanket Orders?");

                if (fix == JOptionPane.YES_OPTION) {
                    EMCCommandClass cmd = new EMCCommandClass(ServerInventoryMethods.FIX_BLANKET_ORDER_SUMMARIES);

                    List toSend = new ArrayList();

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                    if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                        Logger.getLogger("emc").log(Level.INFO, "Blanket Order Summaries fixed.");
                    } else {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to fix the Blanket Order Summaries.");
                    }
                }
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                InventoryMaintenanceForm.this.dispose();
            }
        };


        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnFixBO);
        buttonList.add(btnCancel);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}

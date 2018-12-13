/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.budgetperiod.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.documents.EMCBigDecimalDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.frame.EMCDesktop;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.gl.ServerGLMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;

/**
 *
 * @author claudette
 */
public class GLSplitAmountsDialog extends emcJDialog {

    private emcDataRelationManagerUpdate dataManager;
    private EMCUserData userData;
    private emcJTextField txtAmount = new emcJTextField(new EMCBigDecimalDocument());
    private long lineRecordId;

    public GLSplitAmountsDialog(long lineRecordId, EMCDesktop owner, emcDataRelationManagerUpdate dataManager) {
        super(owner, "Split Amounts", true);
        this.userData = dataManager.getUserData().copyUserDataAndDataList();
        this.dataManager = dataManager;
        this.setLayout(new BorderLayout());
        this.setSize(200, 200);
        this.lineRecordId = lineRecordId;
        initDialog();
        this.pack();
        this.setVisible(true);
    }

    private void initDialog() {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Amounts", createAmountsPane());
        this.add(tabs, BorderLayout.CENTER);
        this.add(createButtonsPane(), BorderLayout.EAST);
    }

    private emcJPanel createAmountsPane() {
        Component[][] comp = {{new emcJLabel("Amount to split"), txtAmount}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel createButtonsPane() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                if (txtAmount.getText().equals("")) {
                    utilFunctions.logMessage(Level.SEVERE, "Amount has to be filled in", userData);
                } else {
                    EMCCommandClass cmd = new EMCCommandClass(ServerGLMethods.SPLIT_AMOUNTS);
                    List toSend = new ArrayList();
                    toSend.add(lineRecordId);
                    toSend.add(new BigDecimal(txtAmount.getText()));
                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                    if (toSend != null && toSend.size() > 1 && toSend.get(1) == Boolean.TRUE) {
                        utilFunctions.logMessage(Level.INFO, "Amounts has successfully been split", userData);
                        dispose();
                    } else {
                        utilFunctions.logMessage(Level.INFO, "Amounts could not have been split.", userData);
                    }
                }
            }
        };


        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                if (EMCDialogFactory.createQuestionDialog(this, "Cancel", "Are you sure that you want to cancel?") == JOptionPane.YES_OPTION) {
                    GLSplitAmountsDialog.this.dispose();
                }
            }
        };
        buttons.add(btnOK);
        buttons.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
}

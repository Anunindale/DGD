/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.budgetmodel.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.documents.EMCStringDocument;
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
import emc.datatypes.EMCString;
import emc.entity.gl.GLChartOfAccounts;
import emc.enums.enumQueryTypes;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.methods.gl.ServerGLMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;

/**
 *
 * @author claudette
 */
public class GLAddAccountsDialog extends emcJDialog {

    private EMCUserData userData;
    private emcDataRelationManagerUpdate dataManager;
    private emcJTextField txtAccounts = new emcJTextField();
    private String modelId;

    public GLAddAccountsDialog(String modelId, EMCDesktop owner, emcDataRelationManagerUpdate dataManager) {
        super(owner, "Add Accounts", true);
        this.userData = dataManager.getUserData().copyUserDataAndDataList();
        this.dataManager = dataManager;
        this.modelId = modelId;

        EMCString dataType = new EMCString();

        EMCStringDocument document = new EMCStringDocument() {

            @Override
            protected boolean allowSpecialValues() {
                return true;
            }
        };
        document.setDataType(dataType);
        dataType.setStringSize(50);
        txtAccounts.setDocument(document);
        initFrame();
        this.pack();
        this.setVisible(true);
    }

    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Accounts", addAccountsPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabs, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcJPanel addAccountsPane() {
        Component[][] comp = {{new emcJLabel("Accounts to add"), txtAccounts}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel buttonPane() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                if (txtAccounts.getText().equals("")) {
                    utilFunctions.logMessage(Level.SEVERE, "There has to be atleast one account number", userData);
                } else if (modelId.equals("") || modelId.equals(null)) {
                    utilFunctions.logMessage(Level.SEVERE, "There has to be a model selected", userData);
                } else {
                    EMCCommandClass cmd = new EMCCommandClass(ServerGLMethods.GET_ACCOUNTS);
                    List toSend = new ArrayList();
                    toSend.add(modelId);
                    toSend.add(new String(txtAccounts.getText()));
                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                    if (toSend != null && toSend.size() > 1 && toSend.get(1) == Boolean.TRUE) {
                        utilFunctions.logMessage(Level.INFO, "Accounts have been added to model", userData);
                        dispose();
                    } else {
                        utilFunctions.logMessage(Level.INFO, "Accounts could not be added to model.", userData);
                    }
                }
                super.doActionPerformed(evt);
            }
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLChartOfAccounts.class);
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                if (EMCDialogFactory.createQuestionDialog(this, "Cancel", "Are you sure that you want to cancel?") == JOptionPane.YES_OPTION) {
                    GLAddAccountsDialog.this.dispose();
                }
            }
        };

        buttons.add(btnOK);
        buttons.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
}

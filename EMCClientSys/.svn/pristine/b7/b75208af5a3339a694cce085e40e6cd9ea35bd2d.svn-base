/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.base.emailing;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJPasswordField;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.base.BaseParameters;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.base.ServerBaseMethods;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author kapeshi
 */
public class EMCEmailSendForm extends BaseInternalFrame {

    private String maxNumberAllowed;
    private emcJTextField txtMaxNumberAllowed;
    private int emailCount;
    private String approvalPassword;
    private String previewFrom;
    private String previewCc;
    private String previewBcc;
    private String previewSubject;
    private String previewTo;
    private String previewMessage;
    private String originalMessage;
    private String recipient;
    private String originalCc;
    private String originalBcc;
    private String originalSubject;
    private emcJTextField txtFrom;
    private emcJTextField txtTo;
    private emcJTextField txtCC;
    private emcJTextField txtBCC;
    private emcJTextField txtSubject;
    private emcJTextArea txaMessage;
    private emcJPasswordField txtApprovalPassword;
    private emcJButton btnCancel;
    private EMCEmailFormManager formManager;
    private EMCUserData userData;
    private emcDataRelationManagerUpdate formDataManager;
    private String templateId;
    private emcJLabel labelFrom;
    private emcJLabel labelTo;
    private emcJLabel labelSubject;
    private emcJLabel labelCC;
    private emcJLabel labelBCC;
    private emcJPanel panelEmail;

    public EMCEmailSendForm(EMCUserData userData) {
        super("Email Preview", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 540);
        this.recipient = userData.getUserData(0).toString();
        if (!Functions.checkBlank(userData.getUserData(1))) {
            this.originalCc = userData.getUserData(1).toString();
        }
        if (!Functions.checkBlank(userData.getUserData(2))) {
            this.originalBcc = userData.getUserData(2).toString();
        }
        if (!Functions.checkBlank(userData.getUserData(3))) {
            this.originalSubject = userData.getUserData(3).toString();
        }
        if (!Functions.checkBlank(userData.getUserData(4))) {
            this.originalMessage = userData.getUserData(4).toString();
        }
        if (!Functions.checkBlank(userData.getUserData(5))) {
            this.templateId = userData.getUserData(5).toString();
        }
        if (!Functions.checkBlank(userData.getUserData(6))) {
            this.previewFrom = userData.getUserData(6).toString();
        }
        if (!Functions.checkBlank(userData.getUserData(7))) {
            this.previewTo = userData.getUserData(7).toString();
        }
        if (!Functions.checkBlank(userData.getUserData(8))) {
            this.previewCc = userData.getUserData(8).toString();
        }
        if (!Functions.checkBlank(userData.getUserData(9))) {
            this.previewBcc = userData.getUserData(9).toString();
        }
        if (!Functions.checkBlank(userData.getUserData(10))) {
            this.previewSubject = userData.getUserData(10).toString();
        }
        if (!Functions.checkBlank(userData.getUserData(11))) {
            this.previewMessage = userData.getUserData(11).toString();
        }
        if (!Functions.checkBlank(userData.getUserData(12))) {
            this.emailCount = ((Integer) userData.getUserData(12)).intValue();
        }
        this.formManager = (EMCEmailFormManager) userData.getUserData(13);
        this.formDataManager = (emcDataRelationManagerUpdate) userData.getUserData().remove(14);
        this.userData = formDataManager.getUserData().copyUserDataAndDataList();
        fetchEmailParameters(formDataManager.getUserData());
        initFrame();
    }

    private void initFrame() {
        emcJPanel mainPanel = new emcJPanel(new BorderLayout());
        mainPanel.add(countPane(), "North");
        mainPanel.add(initComponents(), "Center");
        mainPanel.add(emailPreviewPane(), "South");
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(mainPanel, "Center");
        contentPane.add(buttonPane(), "East");
        setContentPane(contentPane);
    }

    private emcJPanel emailPreviewPane() {
        txaMessage = new emcJTextArea(10, 43);
        txaMessage.setEditable(false);
        txaMessage.setText(previewMessage);
        txaMessage.setLineWrap(true);
        txaMessage.setWrapStyleWord(true);
        txtMaxNumberAllowed = new emcJTextField();
        txtMaxNumberAllowed.setText(maxNumberAllowed);
        txtMaxNumberAllowed.setEnabled(false);
        txtApprovalPassword = new emcJPasswordField();
        if (emailCount <= Integer.parseInt(maxNumberAllowed)) {
            txtApprovalPassword.setEnabled(false);
        }
        emcJLabel messageLabel = new emcJLabel("Message");
        emcJLabel noteLabel = new emcJLabel("Note: This password is set on Base Parameters.");
        noteLabel.setOpaque(true);
        noteLabel.setForeground(Color.RED);
        noteLabel.setFont(new Font("Arial", 0, 11));
        Component comp[][] = {
            {
                txaMessage, messageLabel
            }, {
                new emcJLabel("Max number of Emails allowed without password"), txtMaxNumberAllowed
            }, {
                new emcJLabel("Approval Password"), txtApprovalPassword
            }, {
                noteLabel
            }
        };
        return emcSetGridBagConstraints.createSimplePanel(comp, 0, false);
    }

    private emcJPanel countPane() {
        emcJPanel centerPanel = new emcJPanel();
        centerPanel.setLayout(new GridLayout());
        add(centerPanel, "Center");
        JLabel countLabel = new JLabel(emailCount + " EMAIL(S) TO BE SENT.");
        countLabel.setFont(new Font("Arial", 1, 14));
        countLabel.setHorizontalAlignment(0);
        countLabel.setVerticalAlignment(0);
        centerPanel.add(countLabel);
        centerPanel.setBorder(new EtchedBorder(Color.black, Color.black));
        return centerPanel;
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (emailCount <= Integer.parseInt(maxNumberAllowed)) {
                    formManager.sendEmail(recipient, originalCc, originalBcc, originalSubject, originalMessage, templateId);
                    EMCEmailSendForm.this.dispose();
                } else {
                    if (Functions.checkBlank(txtApprovalPassword.getText())) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Please enter the approval password.", userData);
                        return;
                    }
                    if (txtApprovalPassword.getText().equals(approvalPassword)) {
                        formManager.sendEmail(recipient, originalCc, originalBcc, originalSubject, originalMessage, templateId);
                        Logger.getLogger("emc").log(Level.INFO, emailCount + " Email(s) Sent.", userData);
                        EMCEmailSendForm.this.dispose();
                    } else if (EMCDialogFactory.createQuestionDialog(utilFunctions.findBaseInternalFrame(this), "Password", "The password you entered is incorrect. Do you want to cancel the sending of the Email?") == JOptionPane.YES_OPTION) {
                        EMCEmailSendForm.this.dispose();
                    } else {
                        return;
                    }
                }
            }

        };
        btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                EMCEmailSendForm.this.dispose();
            }

        };
        java.util.List buttonList = new ArrayList();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private void fetchEmailParameters(EMCUserData userData) {
        EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.GET_BASE_PARAMETERS);
        java.util.List toSend = new ArrayList();
        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
        if (toSend.size() > 1 && (toSend.get(1) instanceof BaseParameters)) {
            BaseParameters param = (BaseParameters) toSend.get(1);
            if (param != null) {
                maxNumberAllowed = param.getMaxAllowedSMSEmailWithoutPassword();
                approvalPassword = param.getSmsEmailApprovalPassword();
            }
        }

        if (Functions.checkBlank(maxNumberAllowed)) {
            maxNumberAllowed = "0";
        }
    }

    private emcJPanel initComponents() {
        panelEmail = new emcJPanel();
        labelFrom = new emcJLabel();
        txtFrom = new emcJTextField();
        txtFrom.setText(previewFrom);
        txtFrom.setEditable(false);
        labelTo = new emcJLabel();
        txtTo = new emcJTextField();
        txtTo.setText(previewTo);
        txtTo.setEditable(false);
        labelCC = new emcJLabel();
        txtCC = new emcJTextField();
        txtCC.setText(previewCc);
        txtCC.setEditable(false);
        labelBCC = new emcJLabel();
        txtBCC = new emcJTextField();
        txtBCC.setText(previewBcc);
        txtBCC.setEditable(false);
        labelSubject = new emcJLabel();
        txtSubject = new emcJTextField();
        txtSubject.setText(previewSubject);
        txtSubject.setEditable(false);
        panelEmail.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        labelFrom.setText("From:");
        labelTo.setText("To:");
        labelCC.setText("CC:");
        labelBCC.setText("BCC:");
        labelSubject.setText("Subject:");
        GroupLayout panelEmailLayout = new GroupLayout(panelEmail);
        panelEmail.setLayout(panelEmailLayout);
        panelEmailLayout.setHorizontalGroup(panelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(panelEmailLayout.createSequentialGroup().addContainerGap().addGroup(panelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(labelFrom).addComponent(labelTo).addComponent(labelCC).addComponent(labelBCC).addComponent(labelSubject)).addGap(32, 32, 32).addGroup(panelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addComponent(txtSubject).addComponent(txtTo).addComponent(txtBCC, javax.swing.GroupLayout.Alignment.LEADING).addComponent(txtCC, javax.swing.GroupLayout.Alignment.LEADING).addComponent(txtFrom, -1, 438, 32767)).addContainerGap()));
        panelEmailLayout.setVerticalGroup(panelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(panelEmailLayout.createSequentialGroup().addContainerGap().addGroup(panelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(panelEmailLayout.createSequentialGroup().addComponent(txtFrom, -2, -1, -2).addGap(18, 18, 18).addComponent(txtTo, -2, -1, -2)).addGroup(panelEmailLayout.createSequentialGroup().addComponent(labelFrom).addGap(28, 28, 28).addComponent(labelTo))).addGap(18, 18, 18).addGroup(panelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(labelCC).addComponent(txtCC, -2, -1, -2)).addGap(18, 18, 18).addGroup(panelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(txtBCC, -2, -1, -2).addComponent(labelBCC)).addGroup(panelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(panelEmailLayout.createSequentialGroup().addGap(16, 16, 16).addComponent(labelSubject)).addGroup(panelEmailLayout.createSequentialGroup().addGap(18, 18, 18).addComponent(txtSubject, -2, -1, -2))).addContainerGap(27, 32767)));
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(panelEmail, -2, -1, -2).addContainerGap(72, 32767)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(panelEmail, -2, -1, -2).addContainerGap(159, 32767)));
        return panelEmail;
    }

}

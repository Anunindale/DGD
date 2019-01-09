/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.base.sms;

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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author kapeshi
 */
public class EMCSMSSendForm extends BaseInternalFrame {

    private String maxNumberAllowed;
    private emcJTextField txtMaxNumberAllowed;
    private int smsCount;
    private String approvalPassword;
    private emcJTextArea txaMessage;
    private String previewMessage;
    private String originalMessage;
    private EMCUserData userData;
    private emcDataRelationManagerUpdate formDataManager;
    private String recipient;
    private emcJPasswordField txtApprovalPassword;
    private emcJButton btnCancel;
    private EMCSMSFormManager formManager;
    private String templateId;

    public EMCSMSSendForm(EMCUserData userData) {
        super("SMS Preview", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 380);
        this.recipient = userData.getUserData(0).toString();
        if (!Functions.checkBlank(userData.getUserData(1))) {
            this.originalMessage = userData.getUserData(1).toString();
        }
        if (!Functions.checkBlank(userData.getUserData(2))) {
            this.previewMessage = userData.getUserData(2).toString();
        }
        if (!Functions.checkBlank(userData.getUserData(3))) {
            this.smsCount = ((Integer) userData.getUserData(3)).intValue();
        }
        if (!Functions.checkBlank(userData.getUserData(4))) {
            this.templateId = userData.getUserData(4).toString();
        }
        this.formManager = (EMCSMSFormManager) userData.getUserData(5);
        this.formDataManager = (emcDataRelationManagerUpdate) userData.getUserData().remove(6);
        this.userData = this.formDataManager.getUserData().copyUserDataAndDataList();
        fetchSMSParameters(this.formDataManager.getUserData());
        initFrame();
    }

    private void initFrame() {
        emcJPanel mainPanel = new emcJPanel(new BorderLayout());
        mainPanel.add(countPane(), "North");
        mainPanel.add(smsPreviewPane(), "Center");
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(mainPanel, "Center");
        contentPane.add(buttonPane(), "East");
        setContentPane(contentPane);
    }

    private emcJPanel smsPreviewPane() {
        txaMessage = new emcJTextArea(10, 43);
        txaMessage.setEditable(false);
        txaMessage.setText(previewMessage);
        txaMessage.setLineWrap(true);
        txaMessage.setWrapStyleWord(true);
        txtMaxNumberAllowed = new emcJTextField();
        txtMaxNumberAllowed.setText(maxNumberAllowed);
        txtMaxNumberAllowed.setEnabled(false);
        txtApprovalPassword = new emcJPasswordField();
        if (smsCount <= Integer.parseInt(maxNumberAllowed)) {
            txtApprovalPassword.setEnabled(false);
        }
        emcJLabel messageLabel = new emcJLabel("Message");
        emcJLabel noteLabel = new emcJLabel("Note: This password is set on Base Parameters.");
        noteLabel.setOpaque(true);
        noteLabel.setForeground(Color.RED);
        noteLabel.setFont(new Font("Arial", 0, 11));
        Component comp[][] = {
            {txaMessage, messageLabel},
            {new emcJLabel("Max number of SMS allowed without password"), txtMaxNumberAllowed},
            {new emcJLabel("Approval Password"), txtApprovalPassword},
            {noteLabel}
        };
        return emcSetGridBagConstraints.createSimplePanel(comp, 0, false);
    }

    private emcJPanel countPane() {
        emcJPanel centerPanel = new emcJPanel();
        centerPanel.setLayout(new GridLayout());
        add(centerPanel, "Center");
        JLabel countLabel = new JLabel(smsCount + " SMS(s) TO BE SENT.");
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
                if (smsCount <= Integer.parseInt(maxNumberAllowed)) {
                    formManager.sendSMS(recipient, originalMessage, templateId);
                    Logger.getLogger("emc").log(Level.INFO, smsCount + " SMS(s) Sent", userData);
                    EMCSMSSendForm.this.dispose();
                } else {
                    if (Functions.checkBlank(txtApprovalPassword.getText())) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Please enter the approval password.", userData);
                        return;
                    }
                    if (txtApprovalPassword.getText().equals(approvalPassword)) {
                        formManager.sendSMS(recipient, originalMessage, templateId);
                        Logger.getLogger("emc").log(Level.INFO, smsCount + " SMS(s) Sent", userData);
                        EMCSMSSendForm.this.dispose();
                    } else if (EMCDialogFactory.createQuestionDialog(utilFunctions.findBaseInternalFrame(this), "Password", "The password you entered is incorrect. Do you want to cancel the sending of the SMS?") == JOptionPane.YES_OPTION) {
                        EMCSMSSendForm.this.dispose();
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
                EMCSMSSendForm.this.dispose();
            }

        };
        java.util.List buttonList = new ArrayList();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private void fetchSMSParameters(EMCUserData userData) {
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
    }

}

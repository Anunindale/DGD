/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.base.sms;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.datatypes.EMCDataType;
import emc.datatypes.EMCString;
import emc.entity.base.BaseSMSTemplate;
import emc.enums.enumQueryTypes;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.base.menuItems.display.BaseSMSTempaltesMenu;
import emc.methods.base.ServerBaseMethods;
import emc.methods.developertools.ServerDeveloperToolMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

/**
 *
 * @author wikus
 */
public class EMCSMSForm extends BaseInternalFrame {

    private EMCUserData userData;
    private emcJComboBox cbRecipient;
    private emcJTextArea txaMessage;
    private EMCSMSFormManager formManager;
    private int maxSMSLength;
    private int currentSMSLength;
    private emcJLabel messageLengthLabel;
    private String templateId;
    emcDataRelationManagerUpdate formDataManager;

    public EMCSMSForm(EMCUserData userData) {
        super("SMS", true, true, true, true, userData);
        this.setBounds(20, 20, 600, 450);
        this.userData = userData.copyUserData();

        formDataManager = (emcDataRelationManagerUpdate) userData.getUserData().remove(0);
        String smsField = (String) userData.getUserData().remove(0);
        String postalCodeField = (String) userData.getUserData().remove(0);
        String referenceField = (String) userData.getUserData().remove(0);

        EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.GET_MAX_SMS_LENGTH);
        List toSend = new ArrayList();
        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
        if (toSend.size() > 1 && toSend.get(1) instanceof Integer) {
            maxSMSLength = (Integer) toSend.get(1);
        } else {
            maxSMSLength = 0;
        }
        currentSMSLength = 0;

        initFrame();

        formManager = new EMCSMSFormManager(formDataManager, smsField, postalCodeField, referenceField);
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Send SMS", smsPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcJPanel smsPane() {
        cbRecipient = new emcJComboBox(new String[]{"This Record", "Selected Data"});
        cbRecipient.setSelectedItem("This Record");

        txaMessage = new emcJTextArea();
        txaMessage.setDocument(new EMCStringDocument() {

            @Override
            public void insertString(int offset, String stringToInsert, AttributeSet attributes) throws BadLocationException {
                if (!Functions.checkBlank(templateId) && (stringToInsert.equals("[") || stringToInsert.equals("]"))) {
                    Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to use square bracket \"" + stringToInsert + "\" on a loaded template message.", userData);
                    return;
                }
                if (getLength() + stringToInsert.length() > maxSMSLength) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Max message length reached.", userData);
                    return;
                } else {
                    currentSMSLength = getLength() + stringToInsert.length();
                    messageLengthLabel.setText("Characters: " + currentSMSLength + "/" + maxSMSLength);
                    messageLengthLabel.repaint();
                }

                super.insertString(offset, stringToInsert, attributes);
            }

            @Override
            public void remove(int offs, int len) throws BadLocationException {
                currentSMSLength = getLength() - len;
                messageLengthLabel.setText("Characters: " + currentSMSLength + "/" + maxSMSLength);
                messageLengthLabel.repaint();

                super.remove(offs, len);
            }

            @Override
            public EMCDataType getDataType() {
                EMCString dt = (EMCString) getDefaultDataType();
                dt.setLowerCaseAllowed(true);
                dt.setStringSize(maxSMSLength);
                return dt;
            }

        });

        EMCLookup lkpTemplate = createTemplateLookup();

        Component[][] mailSetupComp = {{new emcJLabel("Recipient"), cbRecipient},
                                       {new emcJLabel("Template"), lkpTemplate}};

        messageLengthLabel = new emcJLabel("Characters: " + +currentSMSLength + "/" + maxSMSLength);

        emcJLabel messageLabel = new emcJLabel("Message");
        messageLabel.setUsePreferedDimensions(true);
        messageLabel.setPreferredSize(new Dimension(100, 100));

        Component[][] frameComp = {{emcSetGridBagConstraints.createSimplePanel(mailSetupComp, GridBagConstraints.NONE, true)},
                                   {txaMessage, messageLabel},
                                   {messageLengthLabel}};

        return emcSetGridBagConstraints.createSimplePanel(frameComp, GridBagConstraints.NONE, false);
    }

    private EMCLookup createTemplateLookup() {
        EMCLookup theLookup = new EMCLookup(new BaseSMSTempaltesMenu()) {

            @Override
            public void setValue(Object value) {
                EMCCommandClass cmd = new EMCCommandClass(ServerDeveloperToolMethods.EXECUTE_EMCQUERY);
                List toSend = new ArrayList();
                EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, BaseSMSTemplate.class);
                qu.addAnd("templateId", value);
                toSend.add(qu);
                toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
                if (toSend.size() > 1) {
                    BaseSMSTemplate template = (BaseSMSTemplate) toSend.get(1);
                    txaMessage.setText(template.getTemplate());
                    templateId = (String) value;
                }
            }

        };
        theLookup.setPopup(new EMCLookupPopup(new BaseSMSTemplate(), "templateId", userData));
        return theLookup;
    }

    private emcJPanel buttonPane() {
        emcJButton btnSend = new emcJButton("Send") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                //getFormManager().sendSMS(cbRecipient.getSelectedItem().toString(), txaMessage.getText(), templateId);
                getFormManager().previewSMS(cbRecipient.getSelectedItem().toString(), txaMessage.getText(), templateId, EMCSMSForm.this);
            }

        };
        /*
         * emcJButton btnPreview = new emcJButton("Preview") {
         *
         * @Override
         * public void doActionPerformed(ActionEvent evt) {
         * super.doActionPerformed(evt);
         * getFormManager().previewSMS(cbRecipient.getSelectedItem().toString(), txaMessage.getText(), templateId, EMCSMSForm.this);
         * }
         * };
         */
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                EMCSMSForm.this.dispose();
            }

        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnSend);
        //buttonList.add(btnPreview);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    /**
     * @return the formManager
     */
    public EMCSMSFormManager getFormManager() {
        return formManager;
    }

}

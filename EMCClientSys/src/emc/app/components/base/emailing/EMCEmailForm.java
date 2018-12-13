/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.base.emailing;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJLabel;
import emc.app.components.emcJList;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcfilepicker.EMCFilePicker;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.reporttools.parameters.ReportParameterObject;
import emc.app.reporttools.parameters.ReportParameterObjectMap;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.datatypes.EMCString;
import emc.entity.base.BaseEmailTemplates;
import emc.entity.crm.CRMDocuments;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.base.menuItems.display.BaseEMailTempaltesMenu;
import emc.menus.base.menuItems.display.CRMDocumentsMenu;
import emc.methods.base.ServerBaseMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;

/**
 *
 * @author wikus
 */
public class EMCEmailForm extends BaseInternalFrame {

    private EMCUserData userData;
    private emcJComboBox cbRecipient;
    private emcJTextField txtCCRecipient;
    private emcJTextField txtBCCRecipient;
    private EMCLookup lkpTemplate;
    private emcJTextField txtSubject;
    private emcJTextArea txaMessage;
    private EMCLookup lkpDocuments;
    private EMCFilePicker fpDocuments;
    private emcJList lsAttachment;
    private emcJButton btnAddAttachment;
    private emcJButton btnRemoveAttachment;
    private EMCEmailFormManager formManager;
    private String templateId;
    emcJButton addButton;

    public EMCEmailForm(EMCUserData userData) {
        super("Email", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 600);
        String buttonCMD = (String) userData.getUserData().remove(0);
        emcDataRelationManagerUpdate formDataManager = (emcDataRelationManagerUpdate) userData.getUserData().remove(0);
        String emailField = (String) userData.getUserData().remove(0);
        String referenceField = (String) userData.getUserData().remove(0);
        Map<String, EMCReportEmailHelper> reportHelperMap = (Map<String, EMCReportEmailHelper>) userData.getUserData().remove(0);
        this.userData = userData.copyUserDataAndDataList();
        initFrame(buttonCMD, reportHelperMap);
        formManager = new EMCEmailFormManager(buttonCMD, formDataManager, emailField, referenceField, reportHelperMap);
    }

    private void initFrame(String buttonCmd, Map<String, EMCReportEmailHelper> reportHelperMap) {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Send Email", emailPane());

        if (!"General".equals(buttonCmd) && !reportHelperMap.isEmpty()) {
            for (EMCReportEmailHelper helper : reportHelperMap.values()) {
                if (helper.getReportParameterObjects() != null) {
                    //TODO:  This only works for one report.  Add support for more than one report.
                    tabbed.add("Parameters", createParametersTab(helper.getReportParameterObjects()));
                    break;
                }
            }
        }
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcJPanel emailPane() {
        cbRecipient = new emcJComboBox(new String[]{"This Record", "Selected Data"});
        cbRecipient.setSelectedItem("This Record");
        EMCString stringDataType = new EMCString();
        stringDataType.setLowerCaseAllowed(true);
        stringDataType.setStringSize(100);
        EMCStringDocument ccStringDoc = new EMCStringDocument();
        ccStringDoc.setDataType(stringDataType);
        txtCCRecipient = new emcJTextField(ccStringDoc);
        EMCStringDocument bccStringDoc = new EMCStringDocument();
        bccStringDoc.setDataType(stringDataType);
        txtBCCRecipient = new emcJTextField(bccStringDoc);
        lkpTemplate = createTemplateLookup();
        txtSubject = new emcJTextField();
        txaMessage = new emcJTextArea();
        txaMessage.addKeyListener(new KeyAdapter() {

            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ((c == '[') || (c == ']')) {
                    Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to use square bracket \"" + c + "\" on a loaded template message.", userData);
                    e.consume();  // ignore event
                }
            }

        });
        lkpDocuments = new EMCLookup(new CRMDocumentsMenu());
        lkpDocuments = new EMCLookup(new CRMDocumentsMenu());
        lkpDocuments.setPopup(new EMCLookupPopup(new CRMDocuments(), "documentId", userData));
        fpDocuments = new EMCFilePicker();
        fpDocuments.setFileSelectionMode(JFileChooser.FILES_ONLY);
        btnAddAttachment = createAddAttachmentButton();
        btnRemoveAttachment = createRemoveAttachmentButton();
        lsAttachment = new emcJList(new DefaultListModel());
        Component[][] mailSetupComp = {{new emcJLabel("Recipient"), cbRecipient},
                                       {new emcJLabel("Cc"), txtCCRecipient},
                                       {new emcJLabel("Bcc"), txtBCCRecipient},
                                       {new emcJLabel()},
                                       {new emcJLabel("Template"), lkpTemplate},
                                       {new emcJLabel()},
                                       {new emcJLabel("Subject"), txtSubject}};
        Component[][] attachmentSetupComp = {{new emcJLabel("Documents"), lkpDocuments},
                                             {new emcJLabel()},
                                             {new emcJLabel("Local File"), fpDocuments, new emcJLabel(), btnAddAttachment, new emcJLabel(), btnRemoveAttachment}};
        emcJLabel messageLabel = new emcJLabel("Message");
        messageLabel.setUsePreferedDimensions(true);
        messageLabel.setPreferredSize(new Dimension(100, 100));
        emcJLabel attchmentLabel = new emcJLabel("Attachment");
        attchmentLabel.setUsePreferedDimensions(true);
        attchmentLabel.setPreferredSize(new Dimension(50, 50));
        Component[][] frameComp = {{emcSetGridBagConstraints.createSimplePanel(mailSetupComp, GridBagConstraints.NONE, true)},
                                   {txaMessage, messageLabel},
                                   {emcSetGridBagConstraints.createSimplePanel(attachmentSetupComp, GridBagConstraints.NONE, true)},
                                   {lsAttachment, attchmentLabel}};
        return emcSetGridBagConstraints.createSimplePanel(frameComp, GridBagConstraints.NONE, false);
    }

    private emcJPanel createParametersTab(ReportParameterObjectMap parameterObjectMap) {
        List<String> orderedKeys = parameterObjectMap.getOrderedKeys();

        Component[][] components = new Component[parameterObjectMap.size()][2];

        for (int i = 0; i < components.length; i++) {
            String key = orderedKeys.get(i);
            ReportParameterObject parameterObject = parameterObjectMap.get(key);
            if (parameterObject.getEditorComponent() instanceof emcJTextArea) {
                components[i][0] = parameterObject.getEditorComponent();
                components[i][1] = new emcJLabel(parameterObject.getLabel());
            } else {
                components[i][0] = new emcJLabel(parameterObject.getLabel());
                components[i][1] = parameterObject.getEditorComponent();
            }
        }

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }

    private EMCLookup createTemplateLookup() {
        EMCLookup theLookup = new EMCLookup(new BaseEMailTempaltesMenu()) {

            @Override
            public void setValue(Object value) {
                EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.CHECK_TEMPLATE);
                List toSend = new ArrayList();
                toSend.add(value);
                toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
                if (toSend.size() > 1) {
                    BaseEmailTemplates template = (BaseEmailTemplates) toSend.get(1);
                    txtSubject.setText(template.getSubject());
                    txaMessage.setText(template.getTemplate());
                    templateId = (String) value;

                    List received = new ArrayList();
                    EMCCommandClass cmdT = new EMCCommandClass(ServerBaseMethods.GET_DOCUMENTS);
                    List toAttached = new ArrayList();
                    toAttached.add(templateId);
                    received = EMCWSManager.executeGenericWS(cmdT, toAttached, userData);

                    if (received != null && received.size() > 1 && received.get(1) instanceof List) {
                        DefaultListModel listModel = (DefaultListModel) lsAttachment.getModel();
                        listModel.removeAllElements();
                        formManager.resetDocToAttach();
                        received = (List) received.get(1);
                        for (Object document : received) {
                            lkpDocuments.setValue(document);
                            addButton.doClick();
                        }
                    }
                }
            }

        };
        theLookup.setPopup(new EMCLookupPopup(new BaseEmailTemplates(), "templateId", userData));
        return theLookup;
    }

    private emcJButton createAddAttachmentButton() {
        addButton = new emcJButton("Add") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                DefaultListModel listModel = (DefaultListModel) lsAttachment.getModel();
                String document = (String) lkpDocuments.getValue();
                if (!Functions.checkBlank(document)) {
                    listModel.addElement(document);
                    formManager.addDocToAttach(document);
                    lkpDocuments.setValue(null);
                }
                document = (String) fpDocuments.getValue();
                if (!Functions.checkBlank(document)) {
                    listModel.addElement(document);
                    formManager.addDocToUpload(document);
                    fpDocuments.setFilePath(null);
                }
            }

        };
        addButton.setPreferredSize(new Dimension(100, 25));
        return addButton;
    }

    private emcJButton createRemoveAttachmentButton() {
        emcJButton theButton = new emcJButton("Remove") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                DefaultListModel listModel = (DefaultListModel) lsAttachment.getModel();
                int toRemove = lsAttachment.getSelectedIndex();
                String removed = (String) listModel.remove(toRemove);
                lsAttachment.setSelectedIndex(listModel.getSize() - 1 < toRemove ? listModel.getSize() - 1 : toRemove);
                formManager.removeDocToUpload(removed);
                formManager.removeDocToAttach(removed);
            }

        };
        theButton.setPreferredSize(new Dimension(100, 25));
        return theButton;
    }

    private emcJPanel buttonPane() {
        emcJButton btnSend = new emcJButton("Send") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                getFormManager().previewEmail(cbRecipient.getSelectedItem().toString(), txtCCRecipient.getText(), txtBCCRecipient.getText(), txtSubject.getText(), txaMessage.getText(), templateId);
            }

        };
        emcJButton btnPreview = new emcJButton("Preview") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

            }

        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                EMCEmailForm.this.dispose();
            }

        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnSend);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    /**
     * @return the formManager
     */
    public EMCEmailFormManager getFormManager() {
        return formManager;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.base.sms;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.excelexport.EMCExcelExport;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.base.EMCSms;
import emc.menus.base.menuItems.action.EMCSMSReview;
import emc.methods.base.ServerBaseMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author wikus
 * @author kapeshi
 */
public class EMCSMSFormManager {

    private emcDataRelationManagerUpdate formDataManager;
    private String numberField;
    private String postalCodeField;
    private String referenceField;
    private String recordField;

    public EMCSMSFormManager(emcDataRelationManagerUpdate formDataManager, String emailField, String postalCode, String reference) {
        this.formDataManager = formDataManager;
        this.numberField = emailField;
        this.postalCodeField = postalCode;
        this.referenceField = reference;
        this.recordField = "recordID";
    }

    public void sendSMS(String recipient, String message, String templateId) {
        List toSend = new ArrayList();
        EMCCommandClass cmd;
        EMCSms sms = new EMCSms();

        if (recipient.toString().equals("Selected Data")) {
            cmd = new EMCCommandClass(ServerBaseMethods.SEND_BATCH_SMS);

            EMCQuery formQuery = ((EMCQuery) formDataManager.getUserData().getUserData(0)).copyQuery();
            formQuery.clearFieldList();
            formQuery.addField(postalCodeField);
            formQuery.addField(numberField);
            formQuery.addField(referenceField);
            formQuery.addField(recordField);
            formQuery.addAnd(numberField, "", EMCQueryConditions.IS_NOT_BLANK);

            sms.addRecipient(formQuery);
        } else {
            cmd = new EMCCommandClass(ServerBaseMethods.SEND_SINGLE_SMS);

            sms.addRecipient((String) (Functions.checkBlank(formDataManager.getLastFieldValueAt(postalCodeField)) ? "" : formDataManager.getLastFieldValueAt(postalCodeField)),
                             (String) (Functions.checkBlank(formDataManager.getLastFieldValueAt(numberField)) ? "" : formDataManager.getLastFieldValueAt(numberField)),
                             (String) (Functions.checkBlank(formDataManager.getLastFieldValueAt(referenceField)) ? "" : formDataManager.getLastFieldValueAt(referenceField)),
                             (Long) (Functions.checkBlank(formDataManager.getLastFieldValueAt(recordField)) ? "" : formDataManager.getLastFieldValueAt(recordField)));
        }

        sms.setMessage(Functions.checkBlank(message) ? "" : message);

        sms.setTemplate(templateId);
        String entityName = formDataManager.getEntityClass().toString();
        String[] parts = entityName.split(" ");
        entityName = parts[1];

        sms.setSourceTable(entityName);
        sms.setSourceEntity(formDataManager.getRowCache(formDataManager.getLastRowAccessed()));

        toSend.add(sms);

        EMCCommandClass validate = new EMCCommandClass(ServerBaseMethods.VALIDATE_SMS);
        List toValidate = new ArrayList();
        boolean send = false;
        toValidate.add(sms);
        toValidate = EMCWSManager.executeGenericWS(validate, toValidate, formDataManager.getUserData());
        if (toValidate.size() > 1 && toValidate.get(1) instanceof Boolean && (Boolean) toValidate.get(1)) {
            send = true;
        }
        if (send) {
            toSend = EMCWSManager.executeGenericWS(cmd, toSend, formDataManager.getUserData());

            if (toSend.size() > 1) {
                Object returned = toSend.get(1);
                if (returned instanceof List) {
                    EMCSMSFilePickerDialog dialog = new EMCSMSFilePickerDialog(utilFunctions.findEMCDesktop(formDataManager.getTheForm()), formDataManager.getUserData());

                    if (dialog.getDialogResult() == JOptionPane.YES_OPTION) {
                        new EMCExcelExport().createExcelExportFile((List<String>) returned, dialog.getFileLocation(), dialog.getFileName(), formDataManager.getUserData());

                        Logger.getLogger("emc").log(Level.INFO, "SMS File created. The fields are seperated by a \'|\' (Vertical Bar).", formDataManager.getUserData());
                    }
                }
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to send SMS.", formDataManager.getUserData());
            }
        }
    }

    public void previewSMS(String recipient, String message, String templateId, BaseInternalFrame frame) {
        List toSend = new ArrayList();
        EMCCommandClass cmd;
        EMCSms sms = new EMCSms();

        if (recipient.toString().equals("Selected Data")) {
            cmd = new EMCCommandClass(ServerBaseMethods.PREVIEW_BATCH_SMS);

            EMCQuery formQuery = ((EMCQuery) formDataManager.getUserData().getUserData(0)).copyQuery();
            formQuery.clearFieldList();
            formQuery.addField(postalCodeField);
            formQuery.addField(numberField);
            formQuery.addField(referenceField);
            formQuery.addField(recordField);
            formQuery.addAnd(numberField, "", EMCQueryConditions.IS_NOT_BLANK);

            sms.addRecipient(formQuery);
        } else {
            cmd = new EMCCommandClass(ServerBaseMethods.PREVIEW_SINGLE_SMS);

            sms.addRecipient((String) (Functions.checkBlank(formDataManager.getLastFieldValueAt(postalCodeField)) ? "" : formDataManager.getLastFieldValueAt(postalCodeField)),
                             (String) (Functions.checkBlank(formDataManager.getLastFieldValueAt(numberField)) ? "" : formDataManager.getLastFieldValueAt(numberField)),
                             (String) (Functions.checkBlank(formDataManager.getLastFieldValueAt(referenceField)) ? "" : formDataManager.getLastFieldValueAt(referenceField)),
                             (Long) (Functions.checkBlank(formDataManager.getLastFieldValueAt(recordField)) ? "" : formDataManager.getLastFieldValueAt(recordField)));
        }

        sms.setMessage(Functions.checkBlank(message) ? "" : message);

        sms.setTemplate(templateId);
        String entityName = formDataManager.getEntityClass().toString();
        String[] parts = entityName.split(" ");
        entityName = parts[1];

        sms.setSourceTable(entityName);
        sms.setSourceEntity(formDataManager.getRowCache(formDataManager.getLastRowAccessed()));

        toSend.add(sms);

        EMCCommandClass validate = new EMCCommandClass(ServerBaseMethods.VALIDATE_SMS);
        List toValidate = new ArrayList();
        boolean send = false;
        toValidate.add(sms);
        toValidate = EMCWSManager.executeGenericWS(validate, toValidate, formDataManager.getUserData());
        if (toValidate.size() > 1 && toValidate.get(1) instanceof Boolean && (Boolean) toValidate.get(1)) {
            send = true;
        }

        if (send) {
            toSend = EMCWSManager.executeGenericWS(cmd, toSend, formDataManager.getUserData());
            if (toSend.size() > 1) {
                Object returned = toSend.get(1);
                int count = 0;
                if (returned instanceof List) {
                    List txt = (List) returned;
                    StringBuilder msg = new StringBuilder();
                    count = Integer.parseInt((String) txt.remove(txt.size() - 1));
                    for (Object o : txt) {
                        msg.append((String) o);
                        msg.append("\n");
                        msg.append("\n");
                    }
                    //MessagePopup popup = new MessagePopup(frame, msg.toString());
                    //popup.show();
                    EMCSMSReview theMenu = new EMCSMSReview();
                    theMenu.setDoNotOpenForm(false);

                    EMCUserData generatedUD = formDataManager.getUserData().copyUserData();
                    generatedUD.setUserData(0, recipient);
                    generatedUD.setUserData(1, message);
                    generatedUD.setUserData(2, msg);
                    generatedUD.setUserData(3, count);
                    generatedUD.setUserData(4, templateId);
                    generatedUD.setUserData(5, this);
                    generatedUD.setUserData(6, formDataManager);
                    formDataManager.getTheForm().getDeskTop().createAndAdd(theMenu, -1, -1, generatedUD, null, 0);
                }
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to preview SMS.", formDataManager.getUserData());
            }
        }
    }

    /*
     * private static class MessagePopup extends Popup
     * implements WindowFocusListener {
     *
     * private final JDialog dialog;
     * private final boolean send = false;
     *
     * public MessagePopup(BaseInternalFrame base, String message) {
     * JPanel myPanel = new JPanel(new BorderLayout());
     * JTextArea textArea;
     * JScrollPane vertical;
     *
     * myPanel.setPreferredSize(new Dimension(350, 200));
     * textArea = new JTextArea(10, 33);
     * textArea.setEditable(false);
     * textArea.setText(message);
     * textArea.setLineWrap(true);
     * textArea.setWrapStyleWord(true);
     *
     *
     * vertical = new JScrollPane(textArea);
     * vertical.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
     * vertical.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
     *
     * myPanel.add(vertical,BorderLayout.CENTER);
     * List<emcJButton> buttonList = new ArrayList<emcJButton>();
     * emcJButton OKButton = new emcJButton("OK"){
     * = true;
     * };
     * emcJButton CancelButton = new emcJButton("Cancel"){
     * //this.dialog.setVisible(false);
     * //dialog.removeWindowFocusListener(this);
     *
     * };
     * buttonList.add(OKButton);
     * buttonList.add(CancelButton);
     * myPanel.add(emcSetGridBagConstraints.createButtonPanel(buttonList),BorderLayout.EAST);
     *
     * dialog = new JOptionPane().createDialog(base, "Preview");
     * dialog.setModal(false);
     * dialog.setContentPane(myPanel);
     * dialog.setSize(400, 200);
     * }
     *
     * @Override
     * public void show() {
     * dialog.addWindowFocusListener(this);
     * dialog.setVisible(true);
     * }
     *
     * @Override
     * public void hide() {
     * dialog.setVisible(false);
     * dialog.removeWindowFocusListener(this);
     * }
     *
     * public void windowGainedFocus(WindowEvent e) {
     * // BlaBlaBla
     * }
     *
     * public void windowLostFocus(WindowEvent e) {
     * hide();
     * }
     *
     *
     * public boolean isSend() {
     * return send;
     * }
     * }
     */
}

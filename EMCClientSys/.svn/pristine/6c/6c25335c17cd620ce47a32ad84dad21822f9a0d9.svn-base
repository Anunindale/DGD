/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.base.emailing;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.config.emcAppConstants;
import emc.app.reporttools.parameters.ReportParameterObjectMap;
import emc.app.wsmanager.EMCReportWSManager;
import emc.app.wsmanager.EMCWSManager;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.base.EMCEmail;
import emc.methods.base.ServerBaseMethods;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JDialog;
import javax.swing.Popup;
import emc.menus.base.menuItems.action.EMCEmailReview;
import java.lang.Integer;

/**
 *
 * @author wikus
 */
public class EMCEmailFormManager {

    private List<String> docsToUplaod;
    private List<String> docsToAttatch;
    private String buttonCMD;
    private emcDataRelationManagerUpdate formDataManager;
    private String emailField;
    private String referenceField;
    private Map<String, EMCReportEmailHelper> reportHelperMap;
    private String recordField;

    public EMCEmailFormManager(String buttonCMD, emcDataRelationManagerUpdate formDataManager, String emailField, String referenceField, Map<String, EMCReportEmailHelper> reportHelperMap) {
        this.buttonCMD = buttonCMD;
        this.formDataManager = formDataManager;
        this.emailField = emailField;
        this.referenceField = referenceField;
        this.reportHelperMap = reportHelperMap;
        this.recordField = "recordID";
    }

    public void addDocToUpload(String path) {
        if (docsToUplaod == null) {
            docsToUplaod = new ArrayList<String>();
        }
        docsToUplaod.add(path);
    }

    public void removeDocToUpload(String path) {
        if (docsToUplaod != null) {
            docsToUplaod.remove(path);
        }
    }

    public void resetDocToUpload() {
        docsToUplaod = new ArrayList<String>();
    }

    public void addDocToAttach(String path) {
        if (docsToAttatch == null) {
            docsToAttatch = new ArrayList<String>();
        }
        docsToAttatch.add(path);
    }

    public void removeDocToAttach(String path) {
        if (docsToAttatch != null) {
            docsToAttatch.remove(path);
        }
    }

    public void resetDocToAttach() {
        if (docsToAttatch != null) {
            docsToAttatch = new ArrayList<String>();
        }
    }

    public void sendEmail(String recipient, String cc, String bcc, String subject, String message, String templateId) {
        String[] ccArray = new String[0];
        String[] bccArray = new String[0];
        if (!Functions.checkBlank(cc)) {
            ccArray = cc.split(";");
        }
        if (!Functions.checkBlank(bcc)) {
            bccArray = bcc.split(";");
        }

        if (buttonCMD.equals("General")) {
            sendMail(recipient, ccArray, bccArray, subject, message, null, null, null, templateId);
        } else {
            sendReportMail(recipient, ccArray, bccArray, subject, message, templateId);
        }
    }

    public void previewEmail(String recipient, String cc, String bcc, String subject, String message, String templateId) {
        String[] ccArray = new String[0];
        String[] bccArray = new String[0];
        if (!Functions.checkBlank(cc)) {
            ccArray = cc.split(";");
        }
        if (!Functions.checkBlank(bcc)) {
            bccArray = bcc.split(";");
        }
        EMCUserData createUD = formDataManager.getUserData().copyUserData();
        createUD.setUserData(0, recipient);
        createUD.setUserData(1, cc);
        createUD.setUserData(2, bcc);
        createUD.setUserData(3, subject);
        createUD.setUserData(4, message);
        createUD.setUserData(5, templateId);

        if (buttonCMD.equals("General")) {

            previewMail(recipient, ccArray, bccArray, subject, message, null, null, templateId, createUD);
        } else {
            sendReportMail(recipient, ccArray, bccArray, subject, message, templateId);
        }
    }

    private void sendMail(String recipient, String[] ccList, String[] bccList, String subject, String message, String emailAddress, String reference, Long recordID, String templateId) {
        EMCCommandClass cmd;

        EMCEmail email = new EMCEmail();

        if (recipient.toString().equals("Selected Data")) {
            cmd = new EMCCommandClass(ServerBaseMethods.SEND_BATCH_EMAILS);

            EMCQuery formQuery = ((EMCQuery) formDataManager.getUserData().getUserData(0)).copyQuery();
            formQuery.clearFieldList();

            if (!Functions.checkBlank(emailAddress)) {
                formQuery.addCustomField(emailAddress, true);
            } else {
                formQuery.addField(emailField);
                formQuery.addAnd(emailField, "", EMCQueryConditions.IS_NOT_BLANK);
            }
            if (!Functions.checkBlank(reference)) {
                formQuery.addCustomField(reference, true);
            } else {
                formQuery.addField(referenceField);
            }
            formQuery.addField(recordField);

            email.addRecipient(formQuery);
        } else {
            cmd = new EMCCommandClass(ServerBaseMethods.SEND_SINGLE_EMAILS);
            email.setMalPerRecipient(true);

            email.addRecipient((Functions.checkBlank(reference) ? (String) formDataManager.getLastFieldValueAt(referenceField) : reference),
                    (Functions.checkBlank(emailAddress) ? (String) formDataManager.getLastFieldValueAt(emailField) : emailAddress),
                    (Long) (Functions.checkBlank(recordID) ? formDataManager.getLastFieldValueAt(recordField) : recordID));
        }

        if (ccList != null) {
            for (String cc : ccList) {
                email.addCC(cc);
            }
        }

        if (bccList != null) {
            for (String bcc : bccList) {
                email.addBCC(bcc);
            }
        }

        email.setSubject(subject);
        email.setMessage(message);

        if (docsToUplaod != null) {
            String newPath;
            for (String path : docsToUplaod) {
                if (Functions.checkBlank((newPath = uploadFile(path)))) {
                    return;
                }
                email.addAttachment(newPath);
            }
        }
        if (docsToAttatch != null) {
            for (String doc : docsToAttatch) {
                email.addAttachment("~EMCDoc~" + doc);
            }
        }

        email.setTemplate(templateId);
        String entityName = formDataManager.getEntityClass().toString();
        String[] parts = entityName.split(" ");
        entityName = parts[1];

        email.setSourceTable(entityName);
        email.setSourceEntity(formDataManager.getRowCache(formDataManager.getLastRowAccessed()));

        List toSend = new ArrayList();
        toSend.add(email);

        EMCCommandClass validate = new EMCCommandClass(ServerBaseMethods.VALIDATE_EMAILS);
        List toValidate = new ArrayList();
        boolean send = false;
        toValidate.add(email);
        toValidate.add(true);
        toValidate = EMCWSManager.executeGenericWS(validate, toValidate, formDataManager.getUserData());
        if (toValidate.size() > 1 && toValidate.get(1) instanceof Boolean && (Boolean) toValidate.get(1)) {
            send = true;
        }
        if (send) {
            toSend = EMCWSManager.executeGenericWS(cmd, toSend, formDataManager.getUserData());
            if (toSend.size() > 1 && (Boolean) toSend.get(1)) {
                Logger.getLogger("emc").log(Level.INFO, "Message Sent", formDataManager.getUserData());
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to send message", formDataManager.getUserData());
            }
        }
    }

    private void sendReportMail(String recipient, String[] cc, String[] bcc, String subject, String message, String templateId) {
        List<String> copyDocsToUpload;
        if (docsToUplaod == null) {
            copyDocsToUpload = null;
            docsToUplaod = new ArrayList<String>();
        } else {
            copyDocsToUpload = new ArrayList<String>(docsToUplaod);
        }

        EMCReportEmailHelper helper = reportHelperMap.get(buttonCMD);
        int startLoop;
        int endLoop;
        if (recipient.toString().equals("Selected Data")) {
            startLoop = 0;
            endLoop = formDataManager.getRowCount();
        } else {
            startLoop = formDataManager.getLastRowAccessed();
            endLoop = formDataManager.getLastRowAccessed() + 1;
        }
        List toSend;
        EMCQuery query;
        for (int row = startLoop; row < endLoop; row++) {
            //Don't create report if customer has no email
            if (Functions.checkBlank(formDataManager.getFieldValueAt(row, emailField))) {
                Logger.getLogger("emc").log(Level.WARNING, "Failed to send email of " + (String) formDataManager.getFieldValueAt(row, referenceField) + ". Email address is blank.", formDataManager.getUserData());
                continue;
            }
            query = helper.getReportQuery().copyQuery();
            query.removeAnd(helper.getReportQueryField(), helper.getReportQueryClass().toString());
            query.addAnd(helper.getReportQueryField(), formDataManager.getFieldValueAt(row, helper.getFormField()), helper.getReportQueryClass().getName());
            toSend = new ArrayList();
            toSend.add(query);

            ReportParameterObjectMap parameterObjects = helper.getReportParameterObjects();
            if (parameterObjects != null) {
                Map<String, Object> parameters = new HashMap<String, Object>();
                for (String key : parameterObjects.getOrderedKeys()) {
                    Object value = parameterObjects.get(key).getValue();
                    if (value != null) {
                        parameters.put(key, value);
                    }
                }
                helper.getReportConfig().setParameters(parameters);
            }

            //delete temp files
            deleteReportTempFiles();

            EMCReportWSManager.generateReport(helper.getCommandClass(), helper.getJasperInfo(), helper.getReportId(), toSend, helper.getReportConfig(), true, formDataManager.getUserData());
            File f = new File(System.getProperty("user.home") + File.separator + emcAppConstants.reportTempDirectoryPath() + File.separator + helper.getJasperInfo().getReportTitle() + ".pdf");
            if (f.exists()) {
                docsToUplaod.add(System.getProperty("user.home") + File.separator + emcAppConstants.reportTempDirectoryPath() + File.separator + helper.getJasperInfo().getReportTitle() + ".pdf");
                sendMail("This Record", cc, bcc, subject, message, (String) formDataManager.getFieldValueAt(row, emailField), (String) formDataManager.getFieldValueAt(row, referenceField), (Long) formDataManager.getFieldValueAt(row, recordField), templateId);
                docsToUplaod = new ArrayList<String>();
                if (copyDocsToUpload != null) {
                    docsToUplaod.addAll(copyDocsToUpload);
                }
            }
        }
        if (copyDocsToUpload != null) {
            docsToUplaod = new ArrayList<String>(copyDocsToUpload);
        } else {
            docsToUplaod = null;
        }
    }

    private String uploadFile(String filePath) {
        EMCUserData userData = formDataManager.getUserData().copyUserDataAndDataList();
        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        ZipOutputStream zout = null;
        try {
            //Reads Client File
            File theFile = new File(filePath);
            in = new FileInputStream(theFile);
            out = new ByteArrayOutputStream();
            zout = new ZipOutputStream(out);
            zout.putNextEntry(new ZipEntry("0"));
            int i;
            while ((i = in.read()) != -1) {
                zout.write(i);
            }
            zout.closeEntry();
            zout.flush();
            //Sends to Server
            List udList = new ArrayList();
            udList.add(Functions.getEMCModule(formDataManager.getEntityClass()).toString());
            udList.add(filePath.substring(filePath.lastIndexOf(File.separator) + 1));
            userData.setUserData(udList);
            if (EMCWSManager.uploadFile(out.toByteArray(), userData)) {
                return Functions.getEMCModule(formDataManager.getEntityClass()) + "/" + filePath.substring(filePath.lastIndexOf(File.separator) + 1);
            } else {
                throw new IOException("Failed to upload file.");
            }
        } catch (IOException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to upload file: " + ex.getMessage(), userData);
            return "";
        } finally {
            //Close All Streams
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (zout != null) {
                    zout.close();
                }
            } catch (IOException ex) {
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to close all streams: " + ex.getMessage(), userData);
                }
            }
        }
    }

    private void previewMail(String recipient, String[] ccList, String[] bccList, String subject, String message, String emailAddress, String reference, String templateId, EMCUserData createUD) {
        EMCCommandClass cmd;

        EMCEmail email = new EMCEmail();

        if (recipient.toString().equals("Selected Data")) {
            cmd = new EMCCommandClass(ServerBaseMethods.PREVIEW_BATCH_EMAILS);

            EMCQuery formQuery = ((EMCQuery) formDataManager.getUserData().getUserData(0)).copyQuery();
            formQuery.clearFieldList();

            if (!Functions.checkBlank(emailAddress)) {
                formQuery.addCustomField(emailAddress, true);
            } else {
                formQuery.addField(emailField);
                formQuery.addAnd(emailField, "", EMCQueryConditions.IS_NOT_BLANK);
            }
            if (!Functions.checkBlank(reference)) {
                formQuery.addCustomField(reference, true);
            } else {
                formQuery.addField(referenceField);
            }
            formQuery.addField(recordField);

            email.addRecipient(formQuery);
        } else {
            cmd = new EMCCommandClass(ServerBaseMethods.PREVIEW_SINGLE_EMAILS);
            email.setMalPerRecipient(true);

            email.addRecipient((Functions.checkBlank(reference) ? (String) formDataManager.getLastFieldValueAt(referenceField) : reference),
                    (Functions.checkBlank(emailAddress) ? (String) formDataManager.getLastFieldValueAt(emailField) : emailAddress),
                    (Long) (Functions.checkBlank(formDataManager.getLastFieldValueAt(recordField)) ? "" : formDataManager.getLastFieldValueAt(recordField)));
        }

        if (ccList != null) {
            for (String cc : ccList) {
                email.addCC(cc);
            }
        }

        if (bccList != null) {
            for (String bcc : bccList) {
                email.addBCC(bcc);
            }
        }

        email.setSubject(subject);
        email.setMessage(message);

        if (docsToUplaod != null) {
            String newPath;
            for (String path : docsToUplaod) {
                /*if (Functions.checkBlank((newPath = uploadFile(path)))) {
                 return;
                 }*/
                email.addAttachment(path);
            }
        }
        if (docsToAttatch != null) {
            for (String doc : docsToAttatch) {
                email.addAttachment("~EMCDoc~" + doc);
            }
        }

        email.setTemplate(templateId);
        String entityName = formDataManager.getEntityClass().toString();
        String[] parts = entityName.split(" ");
        entityName = parts[1];

        email.setSourceTable(entityName);
        email.setSourceEntity(formDataManager.getRowCache(formDataManager.getLastRowAccessed()));

        List toSend = new ArrayList();
        toSend.add(email);

        EMCCommandClass validate = new EMCCommandClass(ServerBaseMethods.VALIDATE_EMAILS);
        List toValidate = new ArrayList();
        boolean send = false;
        toValidate.add(email);
        toValidate = EMCWSManager.executeGenericWS(validate, toValidate, formDataManager.getUserData());
        if (toValidate.size() > 1 && toValidate.get(1) instanceof Boolean && (Boolean) toValidate.get(1)) {
            send = true;
        }
        if (send) {
            toSend = EMCWSManager.executeGenericWS(cmd, toSend, formDataManager.getUserData());
            if (toSend.size() > 1 && toSend.get(1) instanceof List) {
                //GET RESULT
                List<Object> r = (List<Object>) toSend.get(1);
                int emailCount = Integer.parseInt((String) r.remove(r.size() - 1));
                //FROM
                String from = (String) r.get(0);
                //TO
                Object[] data = (Object[]) r.get(1);
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < data.length; i++) {
                    result.append((String) data[i]);
                    if (i < data.length - 1) {
                        result.append(",");
                    }
                }
                String to = result.toString();
                //CC
                data = (Object[]) r.get(2);
                result = new StringBuilder();
                for (int i = 0; i < data.length; i++) {
                    result.append((String) data[i]);
                    if (i < data.length - 1) {
                        result.append(",");
                    }
                }
                String cc = result.toString();
                //BCC
                data = (Object[]) r.get(3);
                result = new StringBuilder();
                for (int i = 0; i < data.length; i++) {
                    result.append((String) data[i]);
                    if (i < data.length - 1) {
                        result.append(",");
                    }
                }
                String bcc = result.toString();
                //SUBJECT
                String sub = (String) r.get(4);
                //MESSAGE
                String msg = (String) r.get(5);
                //ATTACHMENT
                data = (Object[]) r.get(6);
                result = new StringBuilder();
                for (int i = 0; i < data.length; i++) {
                    result.append((String) data[i]);
                    if (i < data.length - 1) {
                        result.append(",");
                    }
                }
                String attachment = result.toString();
                // RECONSTRUCT MSG
                if (!Functions.checkBlank(attachment)) {
                    result = new StringBuilder();
                    result.append(msg);
                    result.append("\n");
                    result.append("\n");
                    result.append("Attachment Files:");
                    result.append("\n");
                    result.append(attachment);
                    msg = result.toString();
                }
                /*JDialog dialog;
                 dialog = new JOptionPane().createDialog(frame, "Preview");
                 dialog = new EMCEmailPreview(formDataManager.getUserData(), from, to, cc, bcc, sub, msg);
                 dialog.setLocationRelativeTo(frame);

                 MessagePopup popup = new MessagePopup(dialog);
                 popup.show();*/

                EMCEmailReview theMenu = new EMCEmailReview();
                theMenu.setDoNotOpenForm(false);

                createUD.setUserData(6, from);
                createUD.setUserData(7, to);
                createUD.setUserData(8, cc);
                createUD.setUserData(9, bcc);
                createUD.setUserData(10, sub);
                createUD.setUserData(11, msg);
                createUD.setUserData(12, emailCount);
                createUD.setUserData(13, this);
                createUD.setUserData(14, formDataManager);
                formDataManager.getTheForm().getDeskTop().createAndAdd(theMenu, -1, -1, createUD, null, 0);

            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to preview message", formDataManager.getUserData());
            }
        }
    }

    private static class MessagePopup extends Popup
            implements WindowFocusListener {

        private final JDialog dialog;

        public MessagePopup(JDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void show() {
            dialog.addWindowFocusListener(this);
            dialog.setVisible(true);
        }

        @Override
        public void hide() {
            dialog.setVisible(false);
            dialog.removeWindowFocusListener(this);
        }

        public void windowGainedFocus(WindowEvent e) {
            // BlaBlaBla
        }

        public void windowLostFocus(WindowEvent e) {
            hide();
        }
    }

    public void deleteReportTempFiles() {
        File directory = new File(System.getProperty("user.home") + File.separator + emcAppConstants.reportTempDirectoryPath());
        if (directory.exists()) {
            File[] files = directory.listFiles();
            for (File f : files) {
                if (f.exists()) {
                    f.delete();
                }
            }
            directory.delete();
        }
    }
}

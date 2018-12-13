/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.mailmanager;

import emc.bus.base.BaseDocRefLocal;
import emc.bus.base.BaseFilePathsLocal;
import emc.bus.base.query.BaseQueryConstructionHelper;
import emc.bus.base.query.BaseQueryTableLocal;
import emc.bus.crm.correspondencelog.CRMCorrespondenceLogLocal;
import emc.bus.crm.documents.CRMDocumentsLocal;
import emc.entity.base.BaseEmailTemplates;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.base.BaseFilePaths;
import emc.entity.base.BaseMailReturnAddressSetup;
import emc.entity.base.BaseMailSetup;
import emc.entity.base.query.BaseQueryTable;
import emc.entity.crm.CRMCorrespondenceLog;
import emc.entity.workflow.WorkFlowActivityGroupEmp;
import emc.entity.workflow.WorkFlowActivityGroups;
import emc.enums.base.docref.DocRefSummary;
import emc.enums.base.query.BaseQueryTypeEnum;
import emc.enums.base.query.BaseTemplateTypeEnum;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.xml.EMCXMLHandler;
import emc.helpers.base.EMCEmail;
import emc.server.filehandeler.EMCFileHandlerLocal;
import emc.tables.EMCTable;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author rico
 */
@Stateless
public class EMCMailManagerBean extends EMCBusinessBean implements EMCMailManagerLocal {

    @EJB
    private BaseFilePathsLocal filePathBean;
    @EJB
    private CRMDocumentsLocal documentsBean;
    @EJB
    private CRMCorrespondenceLogLocal correspondenceBean;
    @EJB
    private EMCFileHandlerLocal fileHandlerBean;
    @EJB
    private BaseDocRefLocal docRefBean;
    @EJB
    private BaseQueryTableLocal queryBean;
    @EJB
    private EMCMailManagerLocal thisBean;

    @Override
    public boolean sendEmail(EMCEmail message, EMCUserData userData) {
        boolean sent = true;
        List<Object[]> recipients = null;
        List<String> errorList = new ArrayList();
        String[] stringCc;
        String[] stringBcc;
        String[] attachmentFileNames = null;
        try {
            //Recipients
            switch (message.getRecipientType()) {
                case EMCEmail.ACTIVITY_GROUP:
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivityGroups.class);
                    query.addTableAnd(WorkFlowActivityGroupEmp.class.getName(), "activityGroup", WorkFlowActivityGroups.class.getName(), "activityGroup");
                    query.addTableAnd(BaseEmployeeTable.class.getName(), "employeeNumber", WorkFlowActivityGroupEmp.class.getName(), "employeeNumber");
                    query.addStringNotBlank("emailAddress", BaseEmployeeTable.class.getName(), EMCQueryBracketConditions.AND);
                    query.addAnd("activityGroup", (String) message.getReciptients().get(0));
                    query.addField("emailAddress", BaseEmployeeTable.class.getName());
                    query.addField("employeeNumber", BaseEmployeeTable.class.getName());
                    recipients = util.executeGeneralSelectQuery(query, userData);
                    break;
                case EMCEmail.REFERENCE_ADDRESSES:
                    recipients = new ArrayList();
                    for (Object o : message.getReciptients()) {
                        recipients.add((Object[]) o);
                    }
                    break;
                case EMCEmail.QUERY:
                    recipients = util.executeGeneralSelectQuery((EMCQuery) message.getReciptients().get(0), userData);
                    break;
            }

            if (recipients == null || recipients.isEmpty()) {
                Logger.getLogger("emc").log(Level.SEVERE, "No Email Recipient.", userData);
                return false;
            }

            //Email Template
            BaseEmailTemplates template = null;
            if (!isBlank(message.getTemplate())) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseEmailTemplates.class);
                query.addAnd("templateId", message.getTemplate());
                template = (BaseEmailTemplates) util.executeSingleResultQuery(query, userData);
                if (template == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to find email template: " + message.getTemplate() + ". The email has not been sent.", userData);
                    return false;
                }
            }

            //CC
            stringCc = message.getCc().toArray(new String[0]);

            //BCC
            stringBcc = message.getBcc().toArray(new String[0]);

            //Attachments
            attachmentFileNames = findAttachments(message.getAttachmentList(), userData);

            //Checking Entity vs DS
            Class theTableClass = null;
            if (message.getSourceTable() != null && message.getSourceEntity() != null) {
                EMCTable theTable = (EMCTable) message.getSourceEntity();
                theTableClass = theTable.getClass();
                if (theTable.isDataSource()) {
                    theTableClass = theTableClass.getSuperclass();
                }
                String entityName = theTableClass.toString();
                String[] parts = entityName.split(" ");
                entityName = parts[1];
                message.setSourceTable(entityName);
            }

            //Check Sending Option
            if (message.isMalPerRecipient() || (template != null && !isBlank(template.getQueryName()))) {
                String savedMessage = "";
                if (!isBlank(message.getMessage())) {
                    savedMessage = message.getMessage();
                }
                //Single Recipient Per Mail
                for (Object[] to : recipients) {
                    if (template != null) {
                        //Resolve Message Template
                        if (!isBlank(template.getQueryName())) {
                            if (!isBlank(savedMessage) && !isBlank(message.getTemplate())) {
                                BaseQueryConstructionHelper templateHelper = queryBean.constructAndReturnQuery(savedMessage, template.getQueryName(), BaseQueryTypeEnum.MERGE_TEMPLATE.toString(), BaseTemplateTypeEnum.EMAIL.toString(), message.getSourceTable(), message.getSourceRecordID() == 0 ? (Long) to[2] : message.getSourceRecordID(), true, userData);
                                message.setMessage(templateHelper.getMessageText());
                                if (isBlank(templateHelper.getMessageText())) {
                                    errorList.addAll(templateHelper.getErrorList());
                                }
                            } else {
                                BaseQueryConstructionHelper templateHelper = queryBean.constructAndReturnQuery(template.getTemplate(), template.getQueryName(), BaseQueryTypeEnum.MERGE_TEMPLATE.toString(), BaseTemplateTypeEnum.EMAIL.toString(), message.getSourceTable(), message.getSourceRecordID() == 0 ? (Long) to[2] : message.getSourceRecordID(), true, userData);
                                message.setMessage(templateHelper.getMessageText());
                                if (isBlank(templateHelper.getMessageText())) {
                                    errorList.addAll(templateHelper.getErrorList());
                                }
                            }
                        } else {
                            if (!isBlank(savedMessage) && !isBlank(message.getTemplate())) {
                                message.setMessage(savedMessage);
                            } else {
                                message.setMessage(template.getTemplate());
                            }
                        }

                        message.setSubject(template.getSubject());
                    } else {
                        message.setMessage(savedMessage);
                    }

                    //Update CRM
                    long crmLogrecordId = thisBean.updateCorrespondenceLog((String) to[1], (String) to[0], message.getSubject(), attachmentFileNames, userData);

                    if (isBlank(message.getMessage()) && !errorList.isEmpty()) {
                        logMessage(Level.SEVERE, "Failed to merge the query: " + errorList.toString(), userData);
                        return false;
                    }
                    //Send Email
                    try {
                        postMail(new String[]{(String) to[0]}, stringCc, stringBcc, message.getSubject() + " - [Ref: " + crmLogrecordId + "]", new EMCXMLHandler().reinstateNewLines(message.getMessage()), null, null, attachmentFileNames, userData);
                    } catch (MessagingException ex) {
                        thisBean.deleteInNewTx(crmLogrecordId, userData);
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to send email to " + (String) to[0] + ": " + ex.toString(), userData);
                        deleteUploadedFiles(attachmentFileNames, message.getAttachmentList());
                        sent = false;
                    }
                }
            } else {
                //All Recipient In Mail
                String[] stringTo = new String[recipients.size()];
                int i = 0;

                for (Object[] to : recipients) {
                    //Update CRM
                    thisBean.updateCorrespondenceLog((String) to[1], (String) to[0], message.getSubject(), attachmentFileNames, userData);

                    //Populate Mail Reciptients
                    stringTo[i++] = (String) to[0];
                }

                if (template != null) {
                    //Resolve Message Template
                    message.setMessage(template.getTemplate());
                    message.setSubject(template.getSubject());
                }

                //Send Email
                try {
                    postMail(stringTo, stringCc, stringBcc, message.getSubject(), new EMCXMLHandler().reinstateNewLines(message.getMessage() == null ? "" : message.getMessage()), null, null, attachmentFileNames, userData);
                } catch (MessagingException ex) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to send email: " + ex.toString(), userData);
                    deleteUploadedFiles(attachmentFileNames, message.getAttachmentList());
                    sent = false;
                }
            }
        } finally {
            deleteUploadedFiles(attachmentFileNames, message.getAttachmentList());
        }
        return sent;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public long updateCorrespondenceLog(String reference, String resipient, String subject, String[] attachments, EMCUserData userData) {
        CRMCorrespondenceLog log = new CRMCorrespondenceLog();
        log.setReferenceNumber(reference);
        log.setResipient(resipient);
        log.setEventDate(Functions.nowDate());
        log.setEventType("Email");
        log.setEventDescription(subject);
        try {
            correspondenceBean.insert(log, userData);
        } catch (EMCEntityBeanException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to populate CRM Log: " + ex.toString(), userData);
            return 0l;
        }

        if (attachments != null) {
            for (String attatchment : attachments) {
                try {
                    fileHandlerBean.attachFileToRecord(log, "Email Attachment", new File(attatchment), attatchment.substring(1 + attatchment.lastIndexOf(File.separator)), userData);
                } catch (EMCEntityBeanException ex) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed ", userData);
                }
            }
        }

        return log.getRecordID();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteInNewTx(Long recordID, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.DELETE, CRMCorrespondenceLog.class);
        query.addAnd("recordID", recordID);
        util.executeUpdate(query, userData);
    }

    private String[] findAttachments(List<String> attachmentList, EMCUserData userData) {
        String[] fileArray;
        String[] attachments = new String[attachmentList.size()];
        BaseFilePaths path;
        String module;
        String fileName;
        int count = 0;
        List<String> doNotDel = new ArrayList<String>();
        boolean delete = true;
        for (String file : attachmentList) {
            if (file.startsWith("~EMCDoc~")) {
                file = file.replace("~EMCDoc~", "");
                module = enumEMCModules.CRM.toString();
                fileName = documentsBean.getDocument(file, userData);
                if (fileName == null) {
                    continue;
                }
                delete = false;
            } else {
                fileArray = file.split("/");
                module = fileArray[0];
                fileName = fileArray[1];
                delete = true;
            }
            path = filePathBean.getModuleFilePath(module, userData);
            if (path != null) {
                attachments[count] = path.getFilePath() + File.separator + fileName;
                if (!delete) {
                    doNotDel.add(path.getFilePath() + File.separator + fileName);
                }
                count++;
            }
        }
        attachmentList = doNotDel;
        return attachments;
    }

    private void deleteUploadedFiles(String[] attachmentFileNames, List<String> attachmentList) {
        File theFile;
        List<String> doDel = new ArrayList<String>();
        if (attachmentList != null) {
            for (String s : attachmentList) {
                if (!s.startsWith("~EMCDoc~")) {
                        doDel.add(s.substring(s.lastIndexOf("/") + 1));
                }
            }
        }
        if (attachmentFileNames != null) {
            for (String attachment : attachmentFileNames) {
                if (doDel.contains(attachment.substring(attachment.lastIndexOf(File.separator) + 1))) {
                    theFile = new File(attachment);
                    try {
                        if (theFile.exists()) {
                            if (!theFile.delete()) {
                                System.out.println("Something went wrong in deleting the file." + attachment);
                                //try once more perhaps it was busy
                                theFile.delete();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Failed to delete the file.");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Use this method to send mail.
     *
     * @param to                  - Array of recipients currently
     * @param cc                  - Array of recipients currently
     * @param bcc                 - Array of recipients currently
     * @param subject             - Mail subject as String
     * @param message             - The message
     * @param signaturePath       - Path to Sig
     * @param from                - return email Address
     * @param attachmentFileNames String Array Attachment file names
     * @param userData            - Plain old User Data
     *
     * @throws MessagingException - catch and throw EMCEntitybean if needed.
     */
    @Override
    public void postMail(String to[], String cc[], String bcc[], String subject, String message, String signaturePath, String from, Object[] attachmentFileNames, EMCUserData userData) throws MessagingException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseMailSetup.class.getName());
        query.addAnd("companyId", userData.getCompanyId());
        BaseMailSetup setup = (BaseMailSetup) util.executeSingleResultQuery(query, userData);
        if (setup == null) {
            logMessage(Level.SEVERE, "Failed to send email. No Mail setting found for your company.", userData);
            return;
        }
        query = new EMCQuery(enumQueryTypes.SELECT, BaseMailReturnAddressSetup.class);
        query.addAnd("companyId", userData.getCompanyId());
        query.addAnd("userId", userData.getUserName());
        BaseMailReturnAddressSetup returnAddress = (BaseMailReturnAddressSetup) util.executeSingleResultQuery(query, userData);
        BaseFilePaths baseModulePath = filePathBean.getModuleFilePath(enumEMCModules.BASE.toString(), userData);
        if (isBlank(from)) {
            if (returnAddress == null) {
                from = setup.getFromEmailAddress();
            } else {
                from = returnAddress.getEmailAddress();
            }
        }

        //Set the host smtp address
        Properties props = new Properties();
        props.put("mail.smtp.host", setup.getSmtpAddress());
        props.put("mail.smtp.port", setup.getSmtpPort());
        if (setup.isSmtpAuthenticationRequired()) {
            props.put("mail.smtp.auth", "true");
        }

        // create some properties and get the default Session
        Session session = Session.getInstance(props, new SimpleAuthenticator(setup.getSmtpUserName(), setup.getSmtpPassword()));
        Transport transport = session.getTransport("smtp");
        transport.connect(setup.getSmtpAddress(), setup.getSmtpUserName(), setup.getSmtpPassword());

        Message msg;
        InternetAddress[] addressArray;
        InternetAddress addressFrom;
        Multipart multipart;
        MimeBodyPart messagePart;
        MimeBodyPart attachmentPart;
        FileDataSource fileDataSource;
        String name;

        //To Each Recipient
        // create a message
        msg = new MimeMessage(session);

        // set the from and to address
        addressFrom = new InternetAddress(from);
        msg.setFrom(addressFrom);

        boolean foundRecipient = false;

        //To
        if (to != null && to.length > 0) {
            for (int i = 0; i < to.length; i++) {
                if (!isBlank(to[i])) {
                    msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
                    foundRecipient = true;
                }
            }
        }

        if (!foundRecipient) {
            return;
        }

        //CC
        if (cc != null && cc.length > 0) {
            for (int i = 0; i < cc.length; i++) {
                if (!isBlank(cc[i])) {
                    msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i]));
                }
            }
        }

        //BCC
        if (bcc != null && bcc.length > 0) {
            for (int i = 0; i < bcc.length; i++) {
                if (!isBlank(bcc[i])) {
                    msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i]));
                }
            }

        }

        //Log To
        if (!isBlank(setup.getLogToEmailAddress())) {
            msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(setup.getLogToEmailAddress()));
        }

        // set the Date: header
        msg.setSentDate(new Date());

        // Setting the Subject and Content Type
        msg.setSubject(subject);
        if (attachmentFileNames == null) {
            msg.setContent(message, "text/plain");
        } else {
            multipart = new MimeMultipart();
            List docs = new ArrayList();
            if (returnAddress != null && baseModulePath != null) {
                docs = docRefBean.getAttachment(returnAddress.getRecordID(), BaseMailReturnAddressSetup.class, DocRefSummary.SIGNATURE.toString(), null, userData);
                if (docs == null) {
                    docs = new ArrayList();
                }
                if (docs.size() > 0) {
                    docs.remove(0);//remove base reference
                }
            }
            // Set the email message text.
            messagePart = new MimeBodyPart();
            message = message.replace("\n", "<br>");
            String htmlString;
            if (!docs.isEmpty()) {
                htmlString = message + "<br> <img src=\"cid:" + docs.get(0) + "@" + setup.getRecordID() + "\">";
            } else {
                htmlString = message;
            }
            messagePart.setContent(htmlString, "text/html");
            //add it to multi-part
            multipart.addBodyPart(messagePart);
            //add the signature
            if (!docs.isEmpty()) {
                messagePart = new MimeBodyPart();
                DataSource fds = new FileDataSource(baseModulePath.getFilePath() + File.separator + docs.get(0));
                messagePart.setDataHandler(new DataHandler(fds));
                messagePart.setHeader("Content-Type", "image/jpeg");//support jpeg signatures
                messagePart.setHeader("name", "test.jpg");
                messagePart.setHeader("Content-ID", "<" + docs.get(0) + "@" + setup.getRecordID() + ">");
                // add it to multi-part
                multipart.addBodyPart(messagePart);
            }
            //add other attachments
            for (int i = 0; i < attachmentFileNames.length; i++) {
                attachmentPart = new MimeBodyPart();
                fileDataSource = new FileDataSource(attachmentFileNames[i].toString()) {

                    @Override
                    public String getContentType() {
                        return "application/octet-stream";
                    }

                };
                attachmentPart.setDataHandler(new DataHandler(fileDataSource));
                name = attachmentFileNames[i].toString();
                name = name.substring(name.lastIndexOf(File.separator) + 1, name.length());
                attachmentPart.setFileName(name);

                //add this attachment to multipart
                multipart.addBodyPart(attachmentPart);
            }
            msg.setContent(multipart);
        }
        msg.saveChanges();
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
        transport = null;
    }

    @Override
    public Object previewEmail(EMCEmail message, EMCUserData userData) {
        List<Object> returned = new ArrayList();
        List<String> errorList = new ArrayList();
        List<Object[]> recipients = null;
        String[] stringCc;
        String[] stringBcc;
        String[] attachmentFileNames = null;
        try {
            //Recipients
            switch (message.getRecipientType()) {
                case EMCEmail.ACTIVITY_GROUP:
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivityGroups.class);
                    query.addTableAnd(WorkFlowActivityGroupEmp.class.getName(), "activityGroup", WorkFlowActivityGroups.class.getName(), "activityGroup");
                    query.addTableAnd(BaseEmployeeTable.class.getName(), "employeeNumber", WorkFlowActivityGroupEmp.class.getName(), "employeeNumber");
                    query.addStringNotBlank("emailAddress", BaseEmployeeTable.class.getName(), EMCQueryBracketConditions.AND);
                    query.addAnd("activityGroup", (String) message.getReciptients().get(0));
                    query.addField("emailAddress", BaseEmployeeTable.class.getName());
                    query.addField("employeeNumber", BaseEmployeeTable.class.getName());
                    recipients = util.executeGeneralSelectQuery(query, userData);
                    break;
                case EMCEmail.REFERENCE_ADDRESSES:
                    recipients = new ArrayList();
                    for (Object o : message.getReciptients()) {
                        recipients.add((Object[]) o);
                    }
                    break;
                case EMCEmail.QUERY:
                    recipients = util.executeGeneralSelectQuery((EMCQuery) message.getReciptients().get(0), userData);
                    break;
            }

            if (recipients == null || recipients.isEmpty()) {
                Logger.getLogger("emc").log(Level.SEVERE, "No Email Recipient.", userData);
                return null;
            }

            //Email Template
            BaseEmailTemplates template = null;
            if (!isBlank(message.getTemplate())) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseEmailTemplates.class);
                query.addAnd("templateId", message.getTemplate());
                template = (BaseEmailTemplates) util.executeSingleResultQuery(query, userData);
                if (template == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to find email template: " + message.getTemplate() + ". The email has not been sent.", userData);
                    return null;
                }
            }

            //CC
            stringCc = message.getCc().toArray(new String[0]);

            //BCC
            stringBcc = message.getBcc().toArray(new String[0]);

            //Attachments
            attachmentFileNames = findAttachments(message.getAttachmentList(), userData);

            //Checking Entity vs DS
            Class theTableClass = null;
            if (message.getSourceTable() != null && message.getSourceEntity() != null) {
                EMCTable theTable = (EMCTable) message.getSourceEntity();
                theTableClass = theTable.getClass();
                if (theTable.isDataSource()) {
                    theTableClass = theTableClass.getSuperclass();
                }
                String entityName = theTableClass.toString();
                String[] parts = entityName.split(" ");
                entityName = parts[1];
                message.setSourceTable(entityName);
            }

            //Check Sending Option
            if (message.isMalPerRecipient() || (template != null && !isBlank(template.getQueryName()))) {
                //Single Recipient Per Mail
                for (Object[] to : recipients) {
                    if (template != null) {
                        //Resolve Message Template
                        if (!isBlank(template.getQueryName())) {
                            if (!isBlank(message.getMessage()) && !isBlank(message.getTemplate())) {
                                BaseQueryConstructionHelper templateHelper = queryBean.constructAndReturnQuery(message.getMessage(), template.getQueryName(), BaseQueryTypeEnum.MERGE_TEMPLATE.toString(), BaseTemplateTypeEnum.EMAIL.toString(), message.getSourceTable(), message.getSourceRecordID() == 0 ? (Long) to[2] : message.getSourceRecordID(), true, userData);
                                message.setMessage(templateHelper.getMessageText());
                                if (isBlank(templateHelper.getMessageText())) {
                                    errorList.addAll(templateHelper.getErrorList());
                                }
                            } else {
                                BaseQueryConstructionHelper templateHelper = queryBean.constructAndReturnQuery(template.getTemplate(), template.getQueryName(), BaseQueryTypeEnum.MERGE_TEMPLATE.toString(), BaseTemplateTypeEnum.EMAIL.toString(), message.getSourceTable(), message.getSourceRecordID() == 0 ? (Long) to[2] : message.getSourceRecordID(), true, userData);
                                message.setMessage(templateHelper.getMessageText());
                                if (isBlank(templateHelper.getMessageText())) {
                                    errorList.addAll(templateHelper.getErrorList());
                                }
                            }
                        } else {
                            if (!isBlank(message.getMessage()) && !isBlank(message.getTemplate())) {
                                //keep message.getMessage
                            } else {
                                message.setMessage(template.getTemplate());
                            }
                        }

                        message.setSubject(template.getSubject());
                    }

                    //Send Email
                    String from;
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseMailSetup.class.getName());
                    query.addAnd("companyId", userData.getCompanyId());
                    BaseMailSetup setup = (BaseMailSetup) util.executeSingleResultQuery(query, userData);
                    if (setup == null) {
                        logMessage(Level.SEVERE, "Failed to send email. No Mail setting found for your company.", userData);
                        return null;
                    }
                    query = new EMCQuery(enumQueryTypes.SELECT, BaseMailReturnAddressSetup.class);
                    query.addAnd("companyId", userData.getCompanyId());
                    query.addAnd("userId", userData.getUserName());
                    BaseMailReturnAddressSetup returnAddress = (BaseMailReturnAddressSetup) util.executeSingleResultQuery(query, userData);

                    if (returnAddress == null) {
                        from = setup.getFromEmailAddress();
                    } else {
                        from = returnAddress.getEmailAddress();
                    }

                    if (isBlank(message.getMessage())) {
                        logMessage(Level.SEVERE, "Failed to merge the query: " + errorList.toString(), userData);
                        return null;
                    }
                    returned.add(from);
                    returned.add(new String[]{(String) to[0]});
                    returned.add(stringCc);
                    returned.add(stringBcc);
                    returned.add(message.getSubject());
                    returned.add(new EMCXMLHandler().reinstateNewLines(message.getMessage()));
                    returned.add(attachmentFileNames);
                    break;
                }
            } else {
                //All Recipient In Mail
                String[] stringTo = new String[recipients.size()];
                int i = 0;
                for (Object[] to : recipients) {
                    //Populate Mail Reciptients
                    stringTo[i++] = (String) to[0];
                }

                if (template != null) {
                    //Resolve Message Template
                    if (!isBlank(message.getMessage()) && !isBlank(message.getTemplate())) {
                        //keep message.getmessage
                    } else {
                        message.setMessage(template.getTemplate());
                    }
                    message.setSubject(template.getSubject());
                }

                //Send Email
                String from;
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseMailSetup.class.getName());
                query.addAnd("companyId", userData.getCompanyId());
                BaseMailSetup setup = (BaseMailSetup) util.executeSingleResultQuery(query, userData);
                if (setup == null) {
                    logMessage(Level.SEVERE, "Failed to send email. No Mail setting found for your company.", userData);
                    return null;
                }
                query = new EMCQuery(enumQueryTypes.SELECT, BaseMailReturnAddressSetup.class);
                query.addAnd("companyId", userData.getCompanyId());
                query.addAnd("userId", userData.getUserName());
                BaseMailReturnAddressSetup returnAddress = (BaseMailReturnAddressSetup) util.executeSingleResultQuery(query, userData);

                if (returnAddress == null) {
                    from = setup.getFromEmailAddress();
                } else {
                    from = returnAddress.getEmailAddress();
                }
                returned.add(from);
                returned.add(stringTo);
                returned.add(stringCc);
                returned.add(stringBcc);
                returned.add(message.getSubject());
                returned.add(new EMCXMLHandler().reinstateNewLines(message.getMessage()));
                returned.add(attachmentFileNames);
            }
        } finally {
            //
        }
        returned.add(String.valueOf(recipients.size()));
        return returned;
    }

    @Override
    public boolean validateEmail(EMCEmail message, EMCUserData userData) {
        BaseEmailTemplates template = null;
        EMCQuery query = null;
        boolean noTemplate = false;
        if (isBlank(message.getTemplate())) {
            if (isBlank(message.getMessage())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Please select an Email Template or type a new text in the Message box.", userData);
                String[] attachmentFileNames = findAttachments(message.getAttachmentList(), userData);
                deleteUploadedFiles(attachmentFileNames, message.getAttachmentList());
                return false;
            } else {
                noTemplate = true;
            }
        } else {
            query = new EMCQuery(enumQueryTypes.SELECT, BaseEmailTemplates.class);
            query.addAnd("templateId", message.getTemplate());
            template = (BaseEmailTemplates) util.executeSingleResultQuery(query, userData);
            if (template == null) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to find email template: " + message.getTemplate() + ". The email has not been sent.", userData);
                String[] attachmentFileNames = findAttachments(message.getAttachmentList(), userData);
                deleteUploadedFiles(attachmentFileNames, message.getAttachmentList());
                return false;
            }
        }

        if (EMCEmail.QUERY == message.getRecipientType()) {
            int count = 0;
            List<Object[]> recipients = util.executeGeneralSelectQuery((EMCQuery) message.getReciptients().get(0), userData);
            for (Object[] to : recipients) {
                Object[] o = new String[]{(String) to[0]};
                for (int i = 0; i < o.length; i++) {
                    if (!isBlank(o[i])) {
                        count = 10;
                    }
                }
            }
            if (count == 0) {
                Logger.getLogger("emc").log(Level.SEVERE, "All recipients emails are blank. Failed to preview email.", userData);
                String[] attachmentFileNames = findAttachments(message.getAttachmentList(), userData);
                deleteUploadedFiles(attachmentFileNames, message.getAttachmentList());
                return false;
            }
        } else if (EMCEmail.REFERENCE_ADDRESSES == message.getRecipientType()) {
            int count = 0;
            List<Object[]> recipients = new ArrayList();
            for (Object o : message.getReciptients()) {
                recipients.add((Object[]) o);
            }
            for (Object[] to : recipients) {
                Object[] o = new String[]{(String) to[0]};
                for (int i = 0; i < o.length; i++) {
                    if (!isBlank(o[i])) {
                        count = 10;
                    }
                }
            }
            if (count == 0) {
                Logger.getLogger("emc").log(Level.SEVERE, "The recipient(s) email(s) is/are missing. Failed to preview email.", userData);
                String[] attachmentFileNames = findAttachments(message.getAttachmentList(), userData);
                deleteUploadedFiles(attachmentFileNames, message.getAttachmentList());
                return false;
            }
        }

        if (!noTemplate) {
            query = new EMCQuery(enumQueryTypes.SELECT, BaseQueryTable.class);
            query.addAnd("queryName", template.getQueryName());
            BaseQueryTable queryRec = (BaseQueryTable) util.executeSingleResultQuery(query, userData);
            if (queryRec != null) {
                Class theTableClass = null;
                if (message.getSourceTable() != null && message.getSourceEntity() != null) {
                    EMCTable theTable = (EMCTable) message.getSourceEntity();
                    theTableClass = theTable.getClass();
                    if (theTable.isDataSource()) {
                        theTableClass = theTableClass.getSuperclass();
                    }
                    String entityName = theTableClass.toString();
                    String[] parts = entityName.split(" ");
                    entityName = parts[1];
                    message.setSourceTable(entityName);
                }

                if (message.getSourceTable() != null && !queryRec.getMainEntityClass().equals(message.getSourceTable())) {
                    String mainTable = queryRec.getMainEntityClass().substring(queryRec.getMainEntityClass().lastIndexOf('.') + 1).trim();
                    String sourceTable = message.getSourceTable().substring(message.getSourceTable().lastIndexOf('.') + 1).trim();
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to load the template. The selected template is related to " + mainTable + " only, Please select a template backed by " + sourceTable, userData);
                    String[] attachmentFileNames = findAttachments(message.getAttachmentList(), userData);
                    deleteUploadedFiles(attachmentFileNames, message.getAttachmentList());
                    return false;
                }
            }
        }

        return true;
    }

}

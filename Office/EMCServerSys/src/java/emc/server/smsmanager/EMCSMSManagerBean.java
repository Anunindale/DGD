/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.smsmanager;

import emc.bus.base.parameters.BaseParametersLocal;
import emc.bus.base.query.BaseQueryConstructionHelper;
import emc.bus.base.query.BaseQueryTableLocal;
import emc.bus.crm.correspondencelog.CRMCorrespondenceLogLocal;
import emc.entity.base.BaseCountries;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.base.BaseParameters;
import emc.entity.base.BasePostalCodes;
import emc.entity.base.BaseSMSTemplate;
import emc.entity.base.query.BaseQueryTable;
import emc.entity.crm.CRMCorrespondenceLog;
import emc.entity.workflow.WorkFlowActivityGroupEmp;
import emc.entity.workflow.WorkFlowActivityGroups;
import emc.enums.base.query.BaseQueryTypeEnum;
import emc.enums.base.query.BaseTemplateTypeEnum;
import emc.enums.base.sms.SMSFileOutputFields;
import emc.enums.base.sms.SMSSendingOptions;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.base.EMCSms;
import emc.server.filehandeler.EMCFileHandlerLocal;
import emc.tables.EMCTable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author wikus
 */
@Stateless
public class EMCSMSManagerBean extends EMCBusinessBean implements EMCSMSManagerLocal {

    @EJB
    private BaseParametersLocal baseParamBean;
    @EJB
    private CRMCorrespondenceLogLocal correspondenceBean;
    @EJB
    private EMCFileHandlerLocal fileHandlerBean;
    @EJB
    private BaseQueryTableLocal queryBean;
    @EJB
    private EMCSMSManagerLocal thisBean;

    @Override
    public Object sendSMS(EMCSms message, EMCUserData userData) throws EMCEntityBeanException {
        List<Object[]> recipients = new ArrayList();
        switch (message.getRecipientType()) {
            case EMCSms.ACTIVITY_GROUP:
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivityGroups.class);
                query.addTableAnd(WorkFlowActivityGroupEmp.class.getName(), "activityGroup", WorkFlowActivityGroups.class.getName(), "activityGroup");
                query.addTableAnd(BaseEmployeeTable.class.getName(), "employeeNumber", WorkFlowActivityGroupEmp.class.getName(), "employeeNumber");
                query.addStringNotBlank("emailAddress", BaseEmployeeTable.class.getName(), EMCQueryBracketConditions.AND);
                query.addAnd("activityGroup", (String) message.getReciptients().get(0));
                query.addField("addressPhysPostalCode", BaseEmployeeTable.class.getName());
                query.addField("cellPhoneNumber", BaseEmployeeTable.class.getName());
                query.addField("employeeNumber", BaseEmployeeTable.class.getName());
                recipients = util.executeGeneralSelectQuery(query, userData);
                break;
            case EMCSms.REFERENCE_NUMBER:
                for (Object o : message.getReciptients()) {
                    recipients.add((Object[]) o);
                }
                break;
            case EMCSms.QUERY:
                recipients = util.executeGeneralSelectQuery((EMCQuery) message.getReciptients().get(0), userData);
                break;
        }

        //SMS Template
        BaseSMSTemplate template = null;
        if (!isBlank(message.getTemplate())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseSMSTemplate.class);
            query.addAnd("templateId", message.getTemplate());
            template = (BaseSMSTemplate) util.executeSingleResultQuery(query, userData);
            if (template == null) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to find sms template: " + message.getTemplate() + ". The sms has not been sent.", userData);
                return false;
            }
        }

        //Check Entity vs DS
        if (message.getSourceTable() != null && message.getSourceEntity() != null) {
            EMCTable theTable = (EMCTable) message.getSourceEntity();
            Class theTableClass = theTable.getClass();
            if (theTable.isDataSource()) {
                theTableClass = theTableClass.getSuperclass();
            }
            String entityName = theTableClass.toString();
            String[] parts = entityName.split(" ");
            entityName = parts[1];
            message.setSourceTable(entityName);
        }

        BaseParameters param = baseParamBean.getBaseParameters(userData);
        if (param == null) {
            throw new EMCEntityBeanException("The Base Parameters are not set up.");
        }

        if (isBlank(param.getSmsSendingOption())) {
            throw new EMCEntityBeanException("The SMS Sending Option is not specified in the Base Parameters.");
        }

        int lineNo = 1;
        List<String> smsList = new ArrayList();
        String savedMessage = "";
        if (!isBlank(message.getMessage())) {
            savedMessage = message.getMessage();
        }
        for (Object[] recipient : recipients) {
            if (template != null) {
                //Resolve Message Template
                if (!isBlank(template.getQueryName())) {
                    if (!isBlank(savedMessage) && !isBlank(message.getTemplate())) {
                        BaseQueryConstructionHelper templateHelper = queryBean.constructAndReturnQuery(savedMessage, template.getQueryName(), BaseQueryTypeEnum.MERGE_TEMPLATE.toString(), BaseTemplateTypeEnum.SMS.toString(), message.getSourceTable(), message.getSourceRecordID() == 0 ? (Long) recipient[3] : message.getSourceRecordID(), true, userData);
                        message.setMessage(templateHelper.getMessageText());
                    } else {
                        BaseQueryConstructionHelper templateHelper = queryBean.constructAndReturnQuery(template.getTemplate(), template.getQueryName(), BaseQueryTypeEnum.MERGE_TEMPLATE.toString(), BaseTemplateTypeEnum.SMS.toString(), message.getSourceTable(), message.getSourceRecordID() == 0 ? (Long) recipient[3] : message.getSourceRecordID(), true, userData);
                        message.setMessage(templateHelper.getMessageText());
                    }
                } else {
                    if (!isBlank(savedMessage) && !isBlank(message.getTemplate())) {
                        message.setMessage(savedMessage);
                    } else {
                        message.setMessage(template.getTemplate());
                    }
                }
            } else {
                message.setMessage(savedMessage);
            }
            //Avoid empty SMSs
            if (isBlank(message.getMessage())) {
                continue;
            }

            switch (SMSSendingOptions.fromString(param.getSmsSendingOption())) {
                case EMAIL_SENDING:
                    throw new EMCEntityBeanException("EMC does not support sending SMSs via Email at this point.");
                case FILE_SENDING:
                    smsList.add(createSMSFile(recipient, message.getMessage(), param, lineNo, userData));
                    break;
                case URL_SENDING:
                    smsList.add(doURLCall(recipient, message.getMessage(), param, userData));
                    break;
                default:
                    throw new EMCEntityBeanException("Failed to amp the SMS Sending Option.");
            }

            lineNo++;
        }

        return smsList;
    }

    @Override
    public Object previewSMS(EMCSms message, EMCUserData userData) throws EMCEntityBeanException {
        List<Object[]> recipients = new ArrayList();
        List<String> errorList = new ArrayList();
        List<String> smsList = new ArrayList();
        switch (message.getRecipientType()) {
            case EMCSms.ACTIVITY_GROUP:
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivityGroups.class);
                query.addTableAnd(WorkFlowActivityGroupEmp.class.getName(), "activityGroup", WorkFlowActivityGroups.class.getName(), "activityGroup");
                query.addTableAnd(BaseEmployeeTable.class.getName(), "employeeNumber", WorkFlowActivityGroupEmp.class.getName(), "employeeNumber");
                query.addStringNotBlank("emailAddress", BaseEmployeeTable.class.getName(), EMCQueryBracketConditions.AND);
                query.addAnd("activityGroup", (String) message.getReciptients().get(0));
                query.addField("addressPhysPostalCode", BaseEmployeeTable.class.getName());
                query.addField("cellPhoneNumber", BaseEmployeeTable.class.getName());
                query.addField("employeeNumber", BaseEmployeeTable.class.getName());
                recipients = util.executeGeneralSelectQuery(query, userData);
                break;
            case EMCSms.REFERENCE_NUMBER:
                for (Object o : message.getReciptients()) {
                    recipients.add((Object[]) o);
                }
                break;
            case EMCSms.QUERY:
                recipients = util.executeGeneralSelectQuery((EMCQuery) message.getReciptients().get(0), userData);
                break;
        }

        //SMS Template
        BaseSMSTemplate smsT = null;
        if (!isBlank(message.getTemplate())) {
            EMCQuery tQ = new EMCQuery(enumQueryTypes.SELECT, BaseSMSTemplate.class);
            tQ.addAnd("templateId", message.getTemplate());
            smsT = (BaseSMSTemplate) util.executeSingleResultQuery(tQ, userData);
            if (smsT == null) {
                errorList.add("Failed to find template:" + message.getTemplate());
            }
        }

        //Check Entity vs DS
        if (message.getSourceTable() != null && message.getSourceEntity() != null) {
            EMCTable theTable = (EMCTable) message.getSourceEntity();
            Class theTableClass = theTable.getClass();
            if (theTable.isDataSource()) {
                theTableClass = theTableClass.getSuperclass();
            }
            String entityName = theTableClass.toString();
            String[] parts = entityName.split(" ");
            entityName = parts[1];
            message.setSourceTable(entityName);
        }
        int count = 0;

        String savedMessage = "";
        if (!isBlank(message.getMessage())) {
            savedMessage = message.getMessage();
        }

        for (Object[] recipient : recipients) {
            String msg = "";
            if (smsT != null) {
                if (isBlank(smsT.getQueryName())) {
                    if (!isBlank(savedMessage) && !isBlank(message.getTemplate())) {
                        msg = savedMessage;
                    } else {
                        msg = smsT.getTemplate();
                    }
                } else {
                    if (!isBlank(savedMessage) && !isBlank(message.getTemplate())) {
                        BaseQueryConstructionHelper helper = queryBean.constructAndReturnQuery(savedMessage, smsT.getQueryName(), BaseQueryTypeEnum.MERGE_TEMPLATE.toString(), BaseTemplateTypeEnum.SMS.toString(), message.getSourceTable(), message.getSourceRecordID() == 0 ? (Long) recipient[3] : message.getSourceRecordID(), true, userData);
                        msg = helper.getMessageText();
                        if (isBlank(helper.getMessageText())) {
                            errorList.addAll(helper.getErrorList());
                        }
                    } else {
                        BaseQueryConstructionHelper helper = queryBean.constructAndReturnQuery(smsT.getTemplate(), smsT.getQueryName(), BaseQueryTypeEnum.MERGE_TEMPLATE.toString(), BaseTemplateTypeEnum.SMS.toString(), message.getSourceTable(), message.getSourceRecordID() == 0 ? (Long) recipient[3] : message.getSourceRecordID(), true, userData);
                        msg = helper.getMessageText();
                        if (isBlank(helper.getMessageText())) {
                            errorList.addAll(helper.getErrorList());
                        }
                    }
                }
            } else {
                msg = savedMessage;
            }
            smsList.add(msg);
            if (count == 10) {
                break;
            }
            count++;

        }
        smsList.add(String.valueOf(recipients.size()));
        if (!errorList.isEmpty()) {
            errorList.add(String.valueOf(recipients.size()));
            return errorList;
        } else {
            return smsList;
        }

    }

    @Override
    public boolean validateSMS(EMCSms message, EMCUserData userData) throws EMCEntityBeanException {
        BaseSMSTemplate template = null;
        EMCQuery query = null;
        boolean noTemplate = false;
        if (isBlank(message.getTemplate())) {
            if (isBlank(message.getMessage())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Please select an SMS Template or type a new text in the Message box.", userData);
                return false;
            } else {
                noTemplate = true;
            }
        } else {
            query = new EMCQuery(enumQueryTypes.SELECT, BaseSMSTemplate.class);
            query.addAnd("templateId", message.getTemplate());
            template = (BaseSMSTemplate) util.executeSingleResultQuery(query, userData);
            if (template == null) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to find sms template: " + message.getTemplate() + ". The sms has not been sent.", userData);
                return false;
            }

        }

        if (!noTemplate) {
            query = new EMCQuery(enumQueryTypes.SELECT, BaseQueryTable.class);
            query.addAnd("queryName", template.getQueryName());
            BaseQueryTable queryRec = (BaseQueryTable) util.executeSingleResultQuery(query, userData);

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
            if (queryRec != null) {
                if (message.getSourceTable() != null && !queryRec.getMainEntityClass().equals(message.getSourceTable())) {
                    String mainTable = queryRec.getMainEntityClass().substring(queryRec.getMainEntityClass().lastIndexOf('.') + 1).trim();
                    String sourceTable = message.getSourceTable().substring(message.getSourceTable().lastIndexOf('.') + 1).trim();
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to load the template. The selected template is related to " + mainTable + " only, Please select a template backed by " + sourceTable, userData);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Batch send URL manager
     *
     * @param theQuery
     * @param message
     * @param param
     * @param userData
     *
     * @return
     */
    private String doURLCall(Object[] data, String message, BaseParameters param, EMCUserData userData) {
        String urllCall = "";
        Long crmLogrecordId = Long.valueOf("0");

        if (!isBlank((String) data[1])) {
            try {
                String number = resolveNumber((String) data[0], (String) data[1], param, userData);
                urllCall = URLCall(number, message, param, userData);
                crmLogrecordId = thisBean.updateCorrespondenceLog((String) data[2], (String) data[1], message, userData);
            } catch (Exception ex) {
                thisBean.deleteInNewTx(crmLogrecordId, userData);
            }
        }

        return urllCall;
    }

    /**
     * Does single URL call
     *
     * @param number
     * @param message
     * @param param
     * @param userData
     *
     * @return
     */
    private String URLCall(String number, String message, BaseParameters param, EMCUserData userData) {
        String toPost = param.getSmsURL() + "&" + param.getSmsMessageURLParm() + "=" + message.replace(" ", "%20") + "&" + param.getSmsNumberURLParm() + "=" + number;
        StringBuilder result = new StringBuilder();
        if (param.isTestSMS()) {
            result.append("Test URL: " + toPost);
        } else {
            try {
                URL url = new URL(toPost);

                BufferedReader rd = new BufferedReader(new InputStreamReader(url.openStream()));

                String line;

                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

            } catch (Exception ex) {
                this.logMessage(Level.SEVERE, ex.getMessage(), userData);
            }
        }
        return result.toString();
    }

    /**
     * Multiple send
     *
     * @param theQuery
     * @param message
     * @param param
     * @param userData
     *
     * @return
     */
    private String createSMSFile(Object[] data, String message, BaseParameters param, int lineNo, EMCUserData userData) {
        String sms = "";
        Long crmLogrecordId = Long.valueOf("0");

        if (!isBlank((String) data[1])) {
            try {
                sms = buildSMSFileLine((String) data[0], (String) data[1], message, lineNo, param, userData);
                thisBean.updateCorrespondenceLog((String) data[2], (String) data[1], message, userData);
            } catch (Exception ex) {
                thisBean.deleteInNewTx(crmLogrecordId, userData);
            }
        }

        return sms;
    }

    /**
     * Resolves the country code
     *
     * @param postalCode
     * @param number
     * @param param
     * @param userData
     *
     * @return
     */
    private String resolveNumber(String postalCode, String number, BaseParameters param, EMCUserData userData) {
        if (param.isIncludeCountryCodeInNumber()) {
            String countryCode = null;
            if (!isBlank(postalCode)) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCountries.class);
                query.addTableAnd(BasePostalCodes.class.getName(), "Code", BaseCountries.class.getName(), "country");
                query.addAnd("code", postalCode, BasePostalCodes.class.getName());
                query.addField("numberCode");
                countryCode = (String) util.executeSingleResultQuery(query, userData);
            }
            if (isBlank(countryCode)) {
                countryCode = param.getDefaultCountryCode();
            }
            if (!isBlank(countryCode)) {
                number = countryCode + number.substring(1, number.length());
            }
        }
        return number;
    }

    /**
     * Build file sms
     *
     * @param postalCode
     * @param number
     * @param message
     * @param rowCount
     * @param param
     * @param userData
     *
     * @return
     */
    private String buildSMSFileLine(String postalCode, String number, String message, int rowCount, BaseParameters param, EMCUserData userData) {
        StringBuilder line = new StringBuilder();
        number = resolveNumber(postalCode, number, param, userData);
        switch (SMSFileOutputFields.fromString(param.getSmsFileOutputField1())) {
            case MESSAGE:
                line.append(message);
                break;
            case NUMBER:
                line.append(number);
                break;
            case ROW_COUNT:
                line.append(rowCount);
                break;
        }
        line.append("|");

        switch (SMSFileOutputFields.fromString(param.getSmsFileOutputField2())) {
            case MESSAGE:
                line.append(message);
                break;
            case NUMBER:
                line.append(number);
                break;
            case ROW_COUNT:
                line.append(rowCount);
                break;
        }
        line.append("|");

        switch (SMSFileOutputFields.fromString(param.getSmsFileOutputField3())) {
            case MESSAGE:
                line.append(message);
                break;
            case NUMBER:
                line.append(number);
                break;
            case ROW_COUNT:
                line.append(rowCount);
                break;
        }

        return line.toString();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public long updateCorrespondenceLog(String reference, String resipient, String message, EMCUserData userData) {
        CRMCorrespondenceLog log = new CRMCorrespondenceLog();
        boolean attach = false;
        String subject = "";
        if (message.length() <= 255) {
            subject = message;
        } else {
            subject = message.substring(0, 250);
            attach = true;
        }
        log.setReferenceNumber(reference);
        log.setResipient(resipient);
        log.setEventDate(Functions.nowDate());
        log.setEventType("SMS");
        log.setEventDescription(subject);
        try {
            correspondenceBean.insert(log, userData);
        } catch (EMCEntityBeanException ex) {
            this.logMessage(Level.SEVERE, "Failed to populate CRM Log: " + ex.toString(), userData);
            return 0l;
        }
        if (attach) {
            try {
                List messageList = new ArrayList();
                messageList.add(message);
                fileHandlerBean.attachFileToRecord(log, "SMS", messageList, log.getRecordID() + "_" + "SMS.txt", userData);
            } catch (EMCEntityBeanException ex) {
                this.logMessage(Level.SEVERE, "Failed to attach SMS to Correspondence Log", userData);
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

    @Override
    public int getMaxSMSLength(EMCUserData userData) throws EMCEntityBeanException {
        BaseParameters param = baseParamBean.getBaseParameters(userData);
        if (param == null) {
            throw new EMCEntityBeanException("The Base Parameters are not set up.");
        }
        return param.getMaxMessageLength();
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.autoemailsms;

import emc.bus.base.query.BaseQueryConstructionHelper;
import emc.bus.base.query.BaseQueryTableLocal;
import emc.bus.workflow.WFActivityLocal;
import emc.entity.workflow.WorkFlowActivity;
import emc.enums.base.query.BaseQueryTypeEnum;
import emc.enums.base.query.BaseTemplateTypeEnum;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.workflow.enumActivitySMSEmailState;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.helpers.base.EMCEmail;
import emc.helpers.base.EMCSms;
import emc.server.mailmanager.EMCMailManagerLocal;
import emc.server.smsmanager.EMCSMSManagerLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author rico
 */
@Stateless
public class BaseAutoEmailSMSBean extends EMCBusinessBean implements BaseAutoEmailSMSLocal {

    @EJB
    private EMCSMSManagerLocal smsBean;
    @EJB
    private WFActivityLocal activityBean;
    @EJB
    private BaseAutoEmailSMSLocal thisBean;
    @EJB
    private BaseQueryTableLocal queryBean;
    @EJB
    private EMCMailManagerLocal mailBean;
    private static boolean working = false;//force single fire

    @Override
    public boolean fireAutoSend(EMCUserData userData) {
        boolean ret = true;
        List<String> errorList = new ArrayList();

        EMCQuery templateQuery;
        EMCQuery recipientQuery;

        if (!working) {
            working = true;
            //select email records
            try {
                //EMAIL
                EMCQuery wfAcQ = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
                wfAcQ.addAnd("requiredCompletionDate", this.dateHandler.nowDate(), EMCQueryConditions.LESS_THAN_EQ);
                wfAcQ.addAnd("emailStatus", enumActivitySMSEmailState.TO_SEND.toString());
                List<WorkFlowActivity> emailList = util.executeGeneralSelectQuery(wfAcQ, userData);
                util.detachEntities(userData);//dont want auto persist
                for (WorkFlowActivity act : emailList) {
                    //Reset act update queries
                    templateQuery = null;
                    recipientQuery = null;

                    if (!isBlank(act.getEmailTemplate())) {
//                        EMCQuery tQ = new EMCQuery(enumQueryTypes.SELECT, BaseEmailTemplates.class);
//                        tQ.addAnd("templateId", act.getEmailTemplate());
//                        BaseEmailTemplates emailT = (BaseEmailTemplates) util.executeSingleResultQuery(tQ, userData);
//                        if (emailT == null) {
//                            errorList.add("Could not find template:" + act.getEmailTemplate());
//                            handleError(errorList);
//                            continue;
//                        }
//                        if (isBlank(emailT.getQueryName())) {
//                            message = emailT.getTemplate();
//                        } else {
//                            BaseQueryConstructionHelper helper = queryBean.constructAndReturnQuery(emailT.getTemplate(),emailT.getQueryName(), BaseQueryTypeEnum.MERGE_TEMPLATE.toString(), BaseTemplateTypeEnum.EMAIL.toString(), act.getSourceTable(), act.getSourceRecordId(), false, userData);
//                            message = helper.getMessageText();
//                            if(isBlank(helper.getMessageText())){
//                               errorList.addAll(helper.getErrorList());
//                               handleError(errorList);
//                               continue;
//                            }
//                        }

                        if (!isBlank(act.getEmailRecipientQueryName())) {
                            BaseQueryConstructionHelper helperRec = queryBean.constructAndReturnQuery(null, act.getEmailRecipientQueryName(), BaseQueryTypeEnum.RECIPIENT.toString(), BaseTemplateTypeEnum.EMAIL.toString(), act.getSourceTable(), act.getSourceRecordId(), false, userData);
                            EMCQuery emailRecQ = helperRec.getTheQuery();
                            if (emailRecQ != null) {
                                //resolve cc, bcc
                                List<Object[]> repList = util.executeGeneralSelectQuery(emailRecQ, userData);

                                //Set Resi[pient Query for Act Update
                                recipientQuery = emailRecQ;

                                for (Object[] a : repList) {
                                    EMCEmail email = new EMCEmail();
                                    email.addRecipient((String) a[0], (String) a[1]);
                                    email.setTemplate(act.getEmailTemplate());
                                    email.setSourceTable(act.getSourceTable());
                                    email.setSourceRecordID(act.getSourceRecordId());

                                    mailBean.sendEmail(email, userData);
                                }
                                thisBean.updateActivity(act, templateQuery, recipientQuery, userData);
                            } else {
                                if (helperRec.getErrorList() != null) {
                                    errorList.addAll(helperRec.getErrorList());
                                }
                                errorList.add("No recipient query specified.");
                                handleError(errorList);
                            }
                        }//not blank recipient

                    }//not blank template
                }

                //SMS
                wfAcQ = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
                wfAcQ.addAnd("requiredCompletionDate", this.dateHandler.nowDate(), EMCQueryConditions.LESS_THAN_EQ);
                wfAcQ.addAnd("smsStatus", enumActivitySMSEmailState.TO_SEND.toString());
                List<WorkFlowActivity> smsList = util.executeGeneralSelectQuery(wfAcQ, userData);
                util.detachEntities(userData);//don't want auto persist
                for (WorkFlowActivity act : smsList) {
                    //Reset act update queries
                    templateQuery = null;
                    recipientQuery = null;

                    if (!isBlank(act.getSmsTemplate())) {
//                        EMCQuery tQ = new EMCQuery(enumQueryTypes.SELECT, BaseSMSTemplate.class);
//                        tQ.addAnd("templateId", act.getSmsTemplate());
//                        BaseSMSTemplate smsT = (BaseSMSTemplate) util.executeSingleResultQuery(tQ, userData);
//                        if (smsT == null) {
//                            errorList.add("Failed to find template:" + act.getSmsTemplate());
//                            handleError(errorList);
//                            continue;
//                        }
//                        String message = "";
//                        if (isBlank(smsT.getQueryName())) {
//                            message = smsT.getTemplate();
//                        } else {
//                            BaseQueryConstructionHelper helper = queryBean.constructAndReturnQuery(smsT.getTemplate(), smsT.getQueryName(), BaseQueryTypeEnum.MERGE_TEMPLATE.toString(), BaseTemplateTypeEnum.SMS.toString(), act.getSourceTable(), act.getSourceRecordId(), false, userData);
//                            message = helper.getMessageText();
//                            if (isBlank(helper.getMessageText())) {
//                                errorList.addAll(helper.getErrorList());
//                                handleError(errorList);
//                                continue;
//                            }
//                        }
                        if (!isBlank(act.getSmsRecipientQueryName())) {
                            BaseQueryConstructionHelper helperRec = queryBean.constructAndReturnQuery(null, act.getSmsRecipientQueryName(), BaseQueryTypeEnum.RECIPIENT.toString(), BaseTemplateTypeEnum.SMS.toString(), act.getSourceTable(), act.getSourceRecordId(), false, userData);
                            EMCQuery smsRecQ = helperRec.getTheQuery();

                            if (smsRecQ != null) {
                                System.out.println("smsRecQ:" + smsRecQ);
                                //resolve cc, bcc
                                List<Object[]> repList = util.executeGeneralSelectQuery(smsRecQ, userData);

                                //Set Resi[pient Query for Act Update
                                recipientQuery = smsRecQ;

                                for (Object[] a : repList) {
                                    EMCSms sms = new EMCSms();
                                    sms.addRecipient((String) a[0], (String) a[1], (String) a[2], 0L);
                                    sms.setTemplate(act.getSmsTemplate());
                                    sms.setSourceTable(act.getSourceTable());
                                    sms.setSourceRecordID(act.getSourceRecordId());

                                    //send sms
                                    smsBean.sendSMS(sms, userData);
                                }
                                thisBean.updateActivity(act, templateQuery, recipientQuery, userData);
                            } else {
                                if (helperRec.getErrorList() != null) {
                                    errorList.addAll(helperRec.getErrorList());
                                }
                                errorList.add("No recipient query specified.");
                                handleError(errorList);
                            }
                        }//not blank recipient

                    }//not blank recipient
                }
            } catch (Exception e) {
                e.printStackTrace();
                //errorList.add(e.getMessage());
                //errorList.add(e.getCause().toString());
                handleError(errorList);
                //do something
            }
            working = false;
        }
        return ret;
    }

    private void handleError(List<String> errorList) {
        System.out.println("Something went wrong with auto sms/email:");
        for (String error : errorList) {
            System.out.println(error);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateActivity(WorkFlowActivity act, EMCQuery templateQuery, EMCQuery recipientQuery, EMCUserData userData) throws EMCEntityBeanException {

        if (act.isAutoCompleteActivity()) {
            act.setStartDate(dateHandler.nowDate());
            act.setCompletionDate(dateHandler.nowDate());
            act.setClosedDate(dateHandler.nowDate());
        }
        if (act.getSmsStatus() != null && (act.getSmsStatus().equals(enumActivitySMSEmailState.TO_SEND.toString()))) {
            act.setSmsStatus(enumActivitySMSEmailState.SENT.toString());
        }
        if (act.getEmailStatus() != null && (act.getEmailStatus().equals(enumActivitySMSEmailState.TO_SEND.toString()))) {
            act.setEmailStatus(enumActivitySMSEmailState.SENT.toString());
        }

        if (templateQuery != null) {
            act.setTemplateQuery(templateQuery.toString());
        }
        if (recipientQuery != null) {
            act.setRecipientQuery(recipientQuery.toString());
        }

        activityBean.update(act, userData);
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.commandmanager.crm;

import emc.bus.crm.classification.CRMClassification1Local;
import emc.bus.crm.classification.CRMClassification2Local;
import emc.bus.crm.classification.CRMClassification3Local;
import emc.bus.crm.correspondencelog.CRMCorrespondenceLogLocal;
import emc.bus.crm.documents.CRMDocumentsLocal;
import emc.bus.crm.interest.CRMInterestLocal;
import emc.bus.crm.interestgroup.CRMInterestGroupLocal;
import emc.bus.crm.parameters.CRMParametersLocal;
import emc.bus.crm.prospect.CRMProspectLocal;
import emc.bus.crm.prospectclosereason.CRMProspectCloseReasonLocal;
import emc.commands.EMCCommands;
import emc.entity.crm.CRMClassification1;
import emc.entity.crm.CRMClassification2;
import emc.entity.crm.CRMClassification3;
import emc.entity.crm.CRMCorrespondenceLog;
import emc.entity.crm.CRMDocuments;
import emc.entity.crm.CRMInterest;
import emc.entity.crm.CRMInterestGroup;
import emc.entity.crm.CRMParameters;
import emc.entity.crm.CRMProspect;
import emc.entity.crm.CRMProspectCloseReason;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.methods.crm.ClientCRMMethods;
import emc.methods.crm.ServerCRMMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class CRMMethodMapperBean implements CRMMethodMapperBeanLocal {

    @EJB
    private CRMInterestLocal interestBean;
    @EJB
    private CRMProspectLocal prospectBean;
    @EJB
    private CRMDocumentsLocal documentsBean;
    @EJB
    private CRMParametersLocal paramBean;
    @EJB
    private CRMCorrespondenceLogLocal correspondenceLogBean;
    @EJB
    private CRMProspectCloseReasonLocal closeReasonBean;
    @EJB
    private CRMInterestGroupLocal interestGroupBean;
    @EJB
    private CRMClassification1Local classification1Bean;
    @EJB
    private CRMClassification2Local classification2Bean;
    @EJB
    private CRMClassification3Local classification3Bean;

    public CRMMethodMapperBean() {
    }

    public List mapMethodCRM(EMCCommandClass cmd, List<Object> dataList, EMCUserData userData) throws EMCEntityBeanException {
        List<Object> theDataList = new ArrayList();
        EMCCommandClass retCmd = new EMCCommandClass();
        retCmd.setCommandId(EMCCommands.CLIENT_GENERAL_COMMAND.getId());
        retCmd.setModuleNumber(enumEMCModules.CRM.getId());
        retCmd.setMethodId(ClientCRMMethods.GENERAL_METHOD.toString());
        switch (ServerCRMMethods.fromString(cmd.getMethodId())) {
            //CRMInterest
            case INSERT_CRMINTEREST:
                theDataList.add(interestBean.insert((CRMInterest) dataList.get(1), userData));
                break;
            case UPDATE_CRMINTEREST:
                theDataList.add(interestBean.update((CRMInterest) dataList.get(1), userData));
                break;
            case DELETE_CRMINTEREST:
                theDataList.add(interestBean.delete((CRMInterest) dataList.get(1), userData));
                break;
            case GETNUMROWS_CRMINTEREST:
                theDataList.add(interestBean.getNumRows(CRMInterest.class, userData));
                break;
            case GETDATA_CRMINTEREST:
                theDataList = (List<Object>) interestBean.getDataInRange(CRMInterest.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_CRMINTEREST:
                theDataList.add(interestBean.validateField(dataList.get(1).toString(), (CRMInterest) dataList.get(2), userData));
                break;
            //CRMProspect
            case INSERT_CRMPROSPECT:
                theDataList.add(prospectBean.insert((CRMProspect) dataList.get(1), userData));
                break;
            case UPDATE_CRMPROSPECT:
                theDataList.add(prospectBean.update((CRMProspect) dataList.get(1), userData));
                break;
            case DELETE_CRMPROSPECT:
                theDataList.add(prospectBean.delete((CRMProspect) dataList.get(1), userData));
                break;
            case GETNUMROWS_CRMPROSPECT:
                theDataList.add(prospectBean.getNumRows(CRMProspect.class, userData));
                break;
            case GETDATA_CRMPROSPECT:
                theDataList = (List<Object>) prospectBean.getDataInRange(CRMProspect.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case CREATE_PROSPECT_JOB:
                prospectBean.createJob((CRMProspect) dataList.get(1), userData);
                break;
            case VALIDATEFIELD_CRMPROSPECT:
                theDataList.add(prospectBean.validateField(dataList.get(1).toString(), (CRMProspect) dataList.get(2), userData));
                break;
            //CRMDocuments
            case INSERT_CRMDOCUMENTS:
                theDataList.add(documentsBean.insert((CRMDocuments) dataList.get(1), userData));
                break;
            case UPDATE_CRMDOCUMENTS:
                theDataList.add(documentsBean.update((CRMDocuments) dataList.get(1), userData));
                break;
            case DELETE_CRMDOCUMENTS:
                theDataList.add(documentsBean.delete((CRMDocuments) dataList.get(1), userData));
                break;
            case GETNUMROWS_CRMDOCUMENTS:
                theDataList.add(documentsBean.getNumRows(CRMDocuments.class, userData));
                break;
            case GETDATA_CRMDOCUMENTS:
                theDataList = (List<Object>) documentsBean.getDataInRange(CRMDocuments.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_CRMDOCUMENTS:
                theDataList.add(documentsBean.validateField(dataList.get(1).toString(), (CRMDocuments) dataList.get(2), userData));
                break;
            case VALIDATE_DOCUMENT:
                theDataList.add(documentsBean.validateSelectedDocument(dataList.get(1).toString(), userData));
                break;
            //CRMParameters
            case INSERT_CRMPARAMETERS:
                theDataList.add(paramBean.insert((CRMParameters) dataList.get(1), userData));
                break;
            case UPDATE_CRMPARAMETERS:
                theDataList.add(paramBean.update((CRMParameters) dataList.get(1), userData));
                break;
            case DELETE_CRMPARAMETERS:
                theDataList.add(paramBean.delete((CRMParameters) dataList.get(1), userData));
                break;
            case GETNUMROWS_CRMPARAMETERS:
                theDataList.add(paramBean.getNumRows(CRMParameters.class, userData));
                break;
            case GETDATA_CRMPARAMETERS:
                theDataList = (List<Object>) paramBean.getDataInRange(CRMParameters.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_CRMPARAMETERS:
                theDataList.add(paramBean.validateField(dataList.get(1).toString(), (CRMParameters) dataList.get(2), userData));
                break;
            //CRMCorrespondenceLog
            case INSERT_CRMCORRESPONDENCELOG:
                theDataList.add(correspondenceLogBean.insert((CRMCorrespondenceLog) dataList.get(1), userData));
                break;
            case UPDATE_CRMCORRESPONDENCELOG:
                theDataList.add(correspondenceLogBean.update((CRMCorrespondenceLog) dataList.get(1), userData));
                break;
            case DELETE_CRMCORRESPONDENCELOG:
                theDataList.add(correspondenceLogBean.delete((CRMCorrespondenceLog) dataList.get(1), userData));
                break;
            case GETNUMROWS_CRMCORRESPONDENCELOG:
                theDataList.add(correspondenceLogBean.getNumRows(CRMCorrespondenceLog.class, userData));
                break;
            case GETDATA_CRMCORRESPONDENCELOG:
                theDataList = (List<Object>) correspondenceLogBean.getDataInRange(CRMCorrespondenceLog.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_CRMCORRESPONDENCELOG:
                theDataList.add(correspondenceLogBean.validateField(dataList.get(1).toString(), (CRMCorrespondenceLog) dataList.get(2), userData));
                break;
            //CRMInterestGroup
            case INSERT_CRMINTERESTGROUP:
                theDataList.add(interestGroupBean.insert((CRMInterestGroup) dataList.get(1), userData));
                break;
            case UPDATE_CRMINTERESTGROUP:
                theDataList.add(interestGroupBean.update((CRMInterestGroup) dataList.get(1), userData));
                break;
            case DELETE_CRMINTERESTGROUP:
                theDataList.add(interestGroupBean.delete((CRMInterestGroup) dataList.get(1), userData));
                break;
            case GETNUMROWS_CRMINTERESTGROUP:
                theDataList.add(interestGroupBean.getNumRows(CRMInterestGroup.class, userData));
                break;
            case GETDATA_CRMINTERESTGROUP:
                theDataList = (List<Object>) interestGroupBean.getDataInRange(CRMInterestGroup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_CRMINTERESTGROUP:
                theDataList.add(interestGroupBean.validateField(dataList.get(1).toString(), (CRMInterestGroup) dataList.get(2), userData));
                break;
            //CRMProspectCloseReason
            //CRMInterestGroup
            case INSERT_CRMPROSPECTCLOSEREASON:
                theDataList.add(closeReasonBean.insert((CRMProspectCloseReason) dataList.get(1), userData));
                break;
            case UPDATE_CRMPROSPECTCLOSEREASON:
                theDataList.add(closeReasonBean.update((CRMProspectCloseReason) dataList.get(1), userData));
                break;
            case DELETE_CRMPROSPECTCLOSEREASON:
                theDataList.add(closeReasonBean.delete((CRMProspectCloseReason) dataList.get(1), userData));
                break;
            case GETNUMROWS_CRMPROSPECTCLOSEREASON:
                theDataList.add(closeReasonBean.getNumRows(CRMProspectCloseReason.class, userData));
                break;
            case GETDATA_CRMPROSPECTCLOSEREASON:
                theDataList = (List<Object>) closeReasonBean.getDataInRange(CRMProspectCloseReason.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_CRMPROSPECTCLOSEREASON:
                theDataList.add(closeReasonBean.validateField(dataList.get(1).toString(), (CRMProspectCloseReason) dataList.get(2), userData));
                break;
            case VALIDATE_CLOSE_REASON:
                theDataList.add(closeReasonBean.validateCancelReason(dataList.get(1).toString(), userData));
                break;
            //CRMClassification1
            case INSERT_CRMCLASSIFICATION1:
                theDataList.add(classification1Bean.insert((CRMClassification1) dataList.get(1), userData));
                break;
            case UPDATE_CRMCLASSIFICATION1:
                theDataList.add(classification1Bean.update((CRMClassification1) dataList.get(1), userData));
                break;
            case DELETE_CRMCLASSIFICATION1:
                theDataList.add(classification1Bean.delete((CRMClassification1) dataList.get(1), userData));
                break;
            case GETNUMROWS_CRMCLASSIFICATION1:
                theDataList.add(classification1Bean.getNumRows(CRMClassification1.class, userData));
                break;
            case GETDATA_CRMCLASSIFICATION1:
                theDataList = (List<Object>) classification1Bean.getDataInRange(CRMClassification1.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_CRMCLASSIFICATION1:
                theDataList.add(classification1Bean.validateField(dataList.get(1).toString(), (CRMClassification1) dataList.get(2), userData));
                break;
            //CRMClassification2
            case INSERT_CRMCLASSIFICATION2:
                theDataList.add(classification2Bean.insert((CRMClassification2) dataList.get(1), userData));
                break;
            case UPDATE_CRMCLASSIFICATION2:
                theDataList.add(classification2Bean.update((CRMClassification2) dataList.get(1), userData));
                break;
            case DELETE_CRMCLASSIFICATION2:
                theDataList.add(classification2Bean.delete((CRMClassification2) dataList.get(1), userData));
                break;
            case GETNUMROWS_CRMCLASSIFICATION2:
                theDataList.add(classification2Bean.getNumRows(CRMClassification2.class, userData));
                break;
            case GETDATA_CRMCLASSIFICATION2:
                theDataList = (List<Object>) classification2Bean.getDataInRange(CRMClassification2.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_CRMCLASSIFICATION2:
                theDataList.add(classification2Bean.validateField(dataList.get(1).toString(), (CRMClassification2) dataList.get(2), userData));
                break;
            //CRMClassification3
            case INSERT_CRMCLASSIFICATION3:
                theDataList.add(classification3Bean.insert((CRMClassification3) dataList.get(1), userData));
                break;
            case UPDATE_CRMCLASSIFICATION3:
                theDataList.add(classification3Bean.update((CRMClassification3) dataList.get(1), userData));
                break;
            case DELETE_CRMCLASSIFICATION3:
                theDataList.add(classification3Bean.delete((CRMClassification3) dataList.get(1), userData));
                break;
            case GETNUMROWS_CRMCLASSIFICATION3:
                theDataList.add(classification3Bean.getNumRows(CRMClassification3.class, userData));
                break;
            case GETDATA_CRMCLASSIFICATION3:
                theDataList = (List<Object>) classification3Bean.getDataInRange(CRMClassification3.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_CRMCLASSIFICATION3:
                theDataList.add(classification3Bean.validateField(dataList.get(1).toString(), (CRMClassification3) dataList.get(2), userData));
                break;


            default:
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Mapper: Method not found", userData);
                }
                break;
        }
        theDataList.add(0, retCmd);
        return theDataList;
    }
}

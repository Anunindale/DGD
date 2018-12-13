/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.crm.prospect;

import emc.bus.crm.parameters.CRMParametersLocal;
import emc.bus.workflow.WFJobMasterLocal;
import emc.bus.workflow.WFMasterLocal;
import emc.entity.crm.CRMInterest;
import emc.entity.crm.CRMInterestGroup;
import emc.entity.crm.CRMParameters;
import emc.entity.crm.CRMProspect;
import emc.entity.workflow.WorkFlowJobLines;
import emc.entity.workflow.WorkFlowJobMaster;
import emc.entity.workflow.WorkFlowMaster;
import emc.enums.crm.CRMProspectActRule;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
import java.util.Date;
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
public class CRMProspectBean extends EMCEntityBean implements CRMProspectLocal {

    @EJB
    private CRMParametersLocal paramBean;
    @EJB
    private WFMasterLocal workFlowBean;
    @EJB
    private WFJobMasterLocal jobMasterBean;

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        return doSaveValidation((CRMProspect) vobject, userData) && super.doInsertValidation(vobject, userData);
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        return doSaveValidation((CRMProspect) vobject, userData) && super.doUpdateValidation(vobject, userData);
    }

    private boolean doSaveValidation(CRMProspect record, EMCUserData userData) {
        if (isBlank(record.getTelNum()) && isBlank(record.getCellNum()) && isBlank(record.getEmail()) && isBlank(record.getPostalAddress1())) {
            Logger.getLogger("emc").log(Level.SEVERE, "No contact details specified.", userData);
            return false;
        } else if (!isBlank(record.getPostalAddress1()) && isBlank(record.getPostalPostalCode())) {
            Logger.getLogger("emc").log(Level.SEVERE, "Postal Code is a mandatory field, please select a value.", userData);
            return false;
        }
        return true;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        CRMProspect prospect = (CRMProspect) iobject;
        //UM specific

        //end UM specific
        super.insert(iobject, userData);
        createJob((CRMProspect) iobject, userData);
        return iobject;
    }

    public void createJob(CRMProspect prospect, EMCUserData userData) throws EMCEntityBeanException {
        CRMParameters param = paramBean.getParameter(userData);
        //check if job exists.
        EMCQuery jobQuery = new EMCQuery(enumQueryTypes.SELECT, WorkFlowJobMaster.class);
        jobQuery.addAnd("designNo", prospect.getProspectId());
        if (util.exists(jobQuery, userData)) {
            Logger.getLogger("emc").log(Level.WARNING, "A Task exists for prospect " + prospect.getProspectId(), userData);
            return;
        }

        if (param == null || isBlank(param.getProspectsWorkFlowId())) {
            Logger.getLogger("emc").log(Level.WARNING, "No prospect work flow set up in crm parameters.", userData);
            return;
        }
        String workflowId = param.getProspectsWorkFlowId();
        //check if interest group is setup and if so if workflow is set on that.
        if (!isBlank(prospect.getInterest())) {
            EMCQuery interestQ = new EMCQuery(enumQueryTypes.SELECT, CRMInterest.class);
            interestQ.addAnd("interest", prospect.getInterest());
            CRMInterest interest = (CRMInterest) util.executeSingleResultQuery(interestQ, userData);
            if (interest != null && !isBlank(interest.getInterestGroup())) {
                EMCQuery interestGQ = new EMCQuery(enumQueryTypes.SELECT, CRMInterestGroup.class);
                interestGQ.addAnd("interestGroupId", interest.getInterestGroup());
                CRMInterestGroup interestG = (CRMInterestGroup) util.executeSingleResultQuery(interestGQ, userData);
                if (interestG != null && !isBlank(interestG.getWorkFlowId())) {
                    workflowId = interestG.getWorkFlowId();
                }
            }
        }
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowMaster.class.getName());
        query.addAnd("workFlowId", workflowId);
        WorkFlowMaster wfMaster = (WorkFlowMaster) util.executeSingleResultQuery(query, userData);
        if (wfMaster == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to get work flow.", userData);
            return;
        }
        WorkFlowJobMaster jobMaster = workFlowBean.createJob(wfMaster, prospect.getProspectId(), userData);
        //Rule handling
        if (!isBlank(param.getProspectActRule())) {
            switch (CRMProspectActRule.fromString(param.getProspectActRule())) {
                case UMSCHOOL: //if School is set

                    //end UM specific based on school
                    break;
                default:
                    this.logMessage(Level.WARNING, "No logic implementation for rule:" + param.getProspectActRule(), userData);
                    break;
            }//end switch
        }//end if rule is not blank
        if (jobMaster != null) {
            jobMaster.setSourceTable(CRMProspect.class.getName());
            jobMaster.setSourceRecordId(prospect.getRecordID());
            jobMaster.setManagerResponsible(userData.getUserName());
            jobMaster.setStartedDate(new Date());
            jobMaster.setStartedBy(userData.getUserName());
            jobMasterBean.update(jobMaster, userData);
        } else {
            throw new EMCEntityBeanException("Failed to create Job Master");
        }
        //Logger.getLogger("emc").log(Level.INFO, "Job created for prospect " + prospect.getProspectId() + ".", userData);
    }
}

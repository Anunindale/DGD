/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.workflow;

import emc.bus.base.BaseDocRefLocal;
import emc.bus.base.BaseEmployeeLocal;
import emc.constants.systemConstants;
import emc.entity.base.BaseDocuRefTable;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.workflow.WorkFlowActivity;
import emc.entity.workflow.WorkFlowActivityGroupEmp;
import emc.entity.workflow.WorkFlowJobLines;
import emc.entity.workflow.WorkFlowJobMaster;
import emc.entity.workflow.WorkFlowMaster;
import emc.enums.basetables.docuref.DocurefTypes;
import emc.enums.enumQueryTypes;
import emc.enums.enumWFPrimaryIndicators;
import emc.enums.workflow.enumActivitySMSEmailState;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.server.datehandler.EMCDateHandlerLocal;
import emc.tables.EMCTable;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author rico
 */
@Stateless
public class WFActivityBean extends EMCEntityBean implements WFActivityLocal {

    @EJB
    private BaseDocRefLocal baseDocRefBean;
    @EJB
    private WFJobLinesLocal wFJobLinesBean;
    @EJB
    private EvaluateJobLinesLocal evaluateJobLines;
    @EJB
    private BaseEmployeeLocal baseEmployeeBean;
    @EJB
    private EMCDateHandlerLocal dateHandlerBean;
    @EJB
    private WFMasterLocal workFlowBean;
    @EJB
    private WFJobMasterLocal jobMasterBean;
    @PersistenceContext
    private EntityManager em;

    public WFActivityBean() {
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        WorkFlowActivity toBeTested = (WorkFlowActivity) theRecord;
        //Always set manager and emp to true so that anyone can update activity
        boolean manager = true;//checkManager(toBeTested, userData);
        boolean employee = true;//checkEmpResp(toBeTested, userData);
        WorkFlowActivity persisted = (WorkFlowActivity) em.find(WorkFlowActivity.class, toBeTested.getRecordID());
        //if closed or completed no changes allowed
        if (persisted != null) {
            if (persisted.getClosedDate() != null) {
                Logger.getLogger("emc").log(Level.SEVERE, "Activity may not be changed.", userData);
                return false;

            }
            //update manager to allow change of manager by manager
            manager = checkManager(persisted, userData);
        }
        //employee number
        if (fieldNameToValidate.equals("employeeNumber")) {
            if (((WorkFlowActivity) theRecord).getEmployeeNumber() == null || ((WorkFlowActivity) theRecord).getEmployeeNumber().equals("")) {
                Logger.getLogger("emc").log(Level.SEVERE, "Employee number is a required field.", userData);
                return false;
            }

            if (manager) {
                return true;
            } else {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
                String userManager = baseEmployeeBean.findEmployee(userData.getUserName(), userData);
                query.addAnd("employeeNumber", toBeTested.getEmployeeNumber());
                query.addAnd("manager", userManager);

                if (util.exists(query, userData)) {
                    return true;
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Only the manager responsible for a activity or the manager of the employee may change employee responsible", userData);
                    return false;
                }
            }
        }
        if (fieldNameToValidate.equals("employeeNumber") && !manager) {
            Logger.getLogger("emc").log(Level.SEVERE, "Only the manager responsible for a activity may change employee responsible", userData);
            return false;
        }
        //required Completion Date
        //if (fieldNameToValidate.equals("requiredCompletionDate") && manager) {
        //    return true;
        //}
        //if (fieldNameToValidate.equals("requiredCompletionDate") && !manager) {
        //    Logger.getLogger("emc").log(Level.SEVERE, "Only the manager responsible for a activity may change required Date", userData);
        //     return false;
        //}
        //priority ok
        //Manager Resp
         /*if(fieldNameToValidate.equals("managerResponsible") && manager){
         return true;
         }*/
        if (fieldNameToValidate.equals("managerResponsible") && !manager) {
            Logger.getLogger("emc").log(Level.SEVERE, "Only the manager responsible or the manager of the employee may reasign responsibility", userData);
            return false;
        }
        //Activity Group
        if (fieldNameToValidate.equals("activityGroup") && manager) {
            return true;
        }
        if (fieldNameToValidate.equals("activityGroup") && !manager) {
            Logger.getLogger("emc").log(Level.SEVERE, "Only the manager responsible or the manager of the employee for a activity may change group", userData);
            return false;
        }
        //Activity Group
        if (fieldNameToValidate.equals("activityGroup") && manager) {
            return true;
        }
        if (fieldNameToValidate.equals("activityGroup") && !manager) {
            Logger.getLogger("emc").log(Level.SEVERE, "Only the manager responsible or the manager of the employee for a activity may change group", userData);
            return false;
        }
        //Description 
        if (fieldNameToValidate.equals("description") && manager) {
            return true;
        }
        if (fieldNameToValidate.equals("description") && !manager) {
            Logger.getLogger("emc").log(Level.SEVERE, "Only the manager responsible or the manager of the employee for a activity may change Description", userData);
            return false;
        }
        //type
        if (fieldNameToValidate.equals("type") && manager) {
            return true;
        }
        if (fieldNameToValidate.equals("type") && !manager) {
            Logger.getLogger("emc").log(Level.SEVERE, "Only the manager responsible or the manager of the employee for a activity may change type", userData);
            return false;
        }
        //category
        if (fieldNameToValidate.equals("category") && manager) {
            return true;
        }
        if (fieldNameToValidate.equals("category") && !manager) {
            Logger.getLogger("emc").log(Level.SEVERE, "Only the manager responsible or the manager of the employee for a activity may change Category", userData);
            return false;
        }
        //status 
        if (fieldNameToValidate.equals("status") && employee) {
            return true;
        }
        if (fieldNameToValidate.equals("status") && !employee) {
            Logger.getLogger("emc").log(Level.SEVERE, "Only the employee responsible or the manager of the employee for a activity change status", userData);
            return false;
        }
        //Skill
        if (fieldNameToValidate.equals("skillRequired") && manager) {
            return true;
        }
        if (fieldNameToValidate.equals("skillRequired") && !manager) {
            Logger.getLogger("emc").log(Level.SEVERE, "Only the manager responsible for a activity or the manager of the employee may change the skill", userData);
            return false;
        }
        //strart Date
        if (fieldNameToValidate.equals("startDate") && employee) {
            ret = true;
            if (toBeTested.getStartDate() != null) {
                Calendar cal = new GregorianCalendar(TimeZone.getTimeZone(systemConstants.getTimeZone()));
                Calendar startDate = new GregorianCalendar(TimeZone.getTimeZone(systemConstants.getTimeZone()));
                startDate.setTime(toBeTested.getStartDate());
                if (cal.get(Calendar.YEAR) > startDate.get(Calendar.YEAR) || (cal.get(Calendar.MONTH) > startDate.get(Calendar.MONTH))
                        || (cal.get(Calendar.DAY_OF_MONTH) > startDate.get(Calendar.DAY_OF_MONTH))) {
                    Logger.getLogger("emc").log(Level.WARNING, "Start Date is in the past.", userData);
                }
                if (startDate.get(Calendar.YEAR) > cal.get(Calendar.YEAR) || (startDate.get(Calendar.MONTH) > cal.get(Calendar.MONTH))
                        || (startDate.get(Calendar.DAY_OF_MONTH) > cal.get(Calendar.DAY_OF_MONTH))) {

                    Logger.getLogger("emc").log(Level.WARNING, "Start Date is in the future.", userData);
                }
            }
            return ret;
        }
        if (fieldNameToValidate.equals("startDate") && !employee) {
            Logger.getLogger("emc").log(Level.SEVERE, "Only the employee responsible for a activity may start it", userData);
            return false;
        }
        //completion Date 
        if (fieldNameToValidate.equals("completionDate") && employee) {
            ret = true;
            if (toBeTested.getStartDate() == null) {
                ret = false;
                Logger.getLogger("emc").log(Level.SEVERE, "No Start Date", userData);
            }
            if (toBeTested.getStartDate().after(toBeTested.getCompletionDate())) {
                ret = false;
                Logger.getLogger("emc").log(Level.SEVERE, "Start Date after completion date", userData);
            }
            if (toBeTested.isOutputFileRequired()) {
                ret = this.baseDocRefBean.isDocumentOfTypeAttached(WorkFlowActivity.class.getSimpleName(), String.valueOf(toBeTested.getRecordID()), DocurefTypes.FILE.toString(), userData);
                if (!ret) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Output File required none found.  File must be of type 'F'", userData);
                }
            }
            return ret;
        }
        if (fieldNameToValidate.equals("completionDate") && !employee) {
            Logger.getLogger("emc").log(Level.SEVERE, "Only the employee responsible for an activity may complete it", userData);
            return false;
        }
        //startTime
        if (fieldNameToValidate.equals("startTime") && employee) {
            return true;
        }
        if (fieldNameToValidate.equals("startTime") && !employee) {
            Logger.getLogger("emc").log(Level.SEVERE, "Only the employee responsible for an activity may change start time", userData);
            return false;
        }
        //closed on
        if (fieldNameToValidate.equals("closedDate") && manager) {
            if (toBeTested.getClosedDate().before(toBeTested.getCompletionDate())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Closed date may not be before completion date", userData);
                return false;
            } else {
                return true;
            }
        }
        if (fieldNameToValidate.equals("closedDate") && !manager) {
            Logger.getLogger("emc").log(Level.SEVERE, "Only the manager responsible for an activity may close it", userData);
            return false;
        }
        //outputFileReq
        if (fieldNameToValidate.equals("outputFileRequired") && !manager) {
            Logger.getLogger("emc").log(Level.SEVERE, "Only the manager responsible for an activity may change outputFile required", userData);
            return false;
        }
        //closed by manager
        if (fieldNameToValidate.equals("closedByManager") && !manager) {
            Logger.getLogger("emc").log(Level.SEVERE, "Only the manager responsible for an activity may change close criteria", userData);
            return false;
        }
        return ret;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        WorkFlowActivity toBeTested = (WorkFlowActivity) iobject;
        if (toBeTested.getStartDate() != null) {
            toBeTested.setStartTime(Functions.nowDate());
        }
        if (toBeTested.getCompletionDate() != null) {
            toBeTested.setCompletionTime(Functions.nowDate());
        }
        if ((toBeTested.getActivityId() == null) || (toBeTested.getActivityId().equals(""))) {
            toBeTested.setActivityId(toBeTested.getEmployeeNumber() + Functions.nowDateString(emc.constants.systemConstants.fullSystemDateFormat()));
        }
        return super.insert(iobject, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {

        WorkFlowActivity toBeTested = (WorkFlowActivity) uobject;
        WorkFlowActivity persisted = (WorkFlowActivity) em.find(WorkFlowActivity.class, toBeTested.getRecordID());
        WorkFlowJobLines jobLine = null;
        //see if connected to job
        if (persisted.getReferenceSource() != null && persisted.getReferenceSource().equals(WorkFlowJobLines.class.getSimpleName())) {
            if (persisted.getReference() != null) {
                jobLine = (WorkFlowJobLines) em.find(WorkFlowJobLines.class, Long.parseLong(persisted.getReference()));

            }
        }
        //start Date
        if (toBeTested.getStartDate() != null) {
            if (!toBeTested.getStartDate().equals(persisted.getStartDate())) {
                toBeTested.setStartTime(Functions.nowDate());
                if (jobLine != null) {
                    //update the job line
                    jobLine.setStartDate(toBeTested.getStartDate());
                    jobLine.setStartTime(toBeTested.getStartTime());
                    wFJobLinesBean.update(jobLine, userData);
                }
            }
        }
        //if completed and not manager close req close act
        if (toBeTested.getCompletionDate() != null) {
            if (!toBeTested.getCompletionDate().equals(persisted.getCompletionDate())) {
                toBeTested.setCompletionTime(Functions.nowDate());
                if (!toBeTested.getClosedByManager()) {
                    toBeTested.setClosedDate(Functions.nowDate());
                    toBeTested.setClosedTime(Functions.nowDate());
                    toBeTested.setClosedBy(userData.getUserName());
                }
            }
        }

        //closed Date
        if (toBeTested.getClosedDate() != null) {
            if (persisted.getClosedDate() == null) {
                //create activities if job
                if (jobLine != null) {
                    //update the job line
                    jobLine.setClosedDate(toBeTested.getClosedDate());
                    jobLine.setClosedTime(toBeTested.getClosedTime());
                    jobLine.setClosedBy(toBeTested.getClosedBy());
                    jobLine = (WorkFlowJobLines) wFJobLinesBean.update(jobLine, userData);
                    //update File back to wFJobLineBean
                    cpFileActToJobLine(jobLine, toBeTested, userData);
                    //
                    String sqlQuery = "SELECT u FROM WorkFlowJobMaster u WHERE u.companyId = '" + userData.getCompanyId() + "' AND u.designNo = '"
                            + jobLine.getDesignNo() + "'";
                    Query qr = em.createQuery(sqlQuery);
                    List qrResult = qr.getResultList();
                    Iterator it = qrResult.iterator();
                    if (it.hasNext()) {
                        WorkFlowJobMaster master = (WorkFlowJobMaster) it.next();
                        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
                        evaluateJobLines.evaluateLines(0, 0,
                                enumWFPrimaryIndicators.STAGEGATE, root, master, userData);
                        //find the next line of this activity
                        sqlQuery = "SELECT u FROM WorkFlowJobLines u WHERE u.companyId = '" + userData.getCompanyId() + "' AND u.designNo = '"
                                + jobLine.getDesignNo() + "' AND u.lineNo = '" + jobLine.getNextLineNo() + "'";
                        qr = em.createQuery(sqlQuery);
                        qrResult = qr.getResultList();
                        it = qrResult.iterator();
                        if (it.hasNext()) {
                            WorkFlowJobLines theLineRead = (WorkFlowJobLines) it.next();
                            //find this line in the tree and create next activities
                            findJobLineInTree(root, theLineRead, jobLine, userData);
                        }
                    }
                }
            }

            //update the status back to the jobline if changed
            if ((toBeTested.getStatus() != null) && (!toBeTested.getStatus().equals(persisted.getStatus()))) {
                if (jobLine != null) {
                    jobLine.setStatus(toBeTested.getStatus());
                    wFJobLinesBean.update(jobLine, userData);
                }
            }
        }
        return super.update(uobject, userData);
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) {
        WorkFlowActivity wfa = (WorkFlowActivity) vobject;
        if (wfa.getClosedDate() != null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Cannot delete a closed activity", userData);
            return false;
        }
        return true;
    }

    private void findJobLineInTree(DefaultMutableTreeNode node, WorkFlowJobLines jobline, WorkFlowJobLines toBeUpdated, EMCUserData userData) {
        Enumeration e = node.children();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) e.nextElement();
            if (jobline.equals(child.getUserObject())) {
                //look down one level
                boolean created = createActivityDown(node, toBeUpdated, userData);

            } else {
                //search along the node
                findJobLineInTree(child, jobline, toBeUpdated, userData);
            }
        }
    }

    private boolean checkIfActMayBeCreated(String designNo, double lineNo, double lineNoToUpdate, EMCUserData userData) {
        String sqlQuery = "SELECT u FROM WorkFlowJobLines u WHERE u.companyId = '" + userData.getCompanyId() + "' AND u.designNo = '"
                + designNo + "' AND u.nextLineNo = '" + lineNo + "'";
        Query qr = em.createQuery(sqlQuery);
        List qrResult = qr.getResultList();
        Iterator it = qrResult.iterator();
        while (it.hasNext()) {
            WorkFlowJobLines theLineRead = (WorkFlowJobLines) it.next();
            if ((theLineRead.getCompletionDate() == null) && (theLineRead.getClosedDate() == null)) {
                if (theLineRead.getLineNo() != lineNoToUpdate) {
                    return false;
                }
            }
        }
        return true;

    }

    private boolean createActivityDown(DefaultMutableTreeNode node, WorkFlowJobLines toBeUpdated, EMCUserData userData) {
        boolean ret = false;
        Enumeration e = node.children();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) e.nextElement();
            WorkFlowJobLines jobLine = (WorkFlowJobLines) child.getUserObject();
            if ((jobLine.getReference() == null) && checkIfActMayBeCreated(jobLine.getDesignNo(), jobLine.getLineNo(), toBeUpdated.getLineNo(), userData)) {
                copyJobLineToActivityPersist("", jobLine, userData);
                ret = true;
            }
        }
        return ret;
    }

    private boolean checkManager(WorkFlowActivity toBeTested, EMCUserData userData) {

        if (toBeTested.getManagerResponsible() == null || toBeTested.getManagerResponsible().equals("")) {
            return true;
        }
        String employee = baseEmployeeBean.findEmployee(userData.getUserName(), userData);
        if (employee.equalsIgnoreCase(toBeTested.getManagerResponsible())) {
            return true;
        }

        return false;

    }

    private boolean checkEmpResp(WorkFlowActivity toBeTested, EMCUserData userData) {
        boolean ret = false;
        String employee = baseEmployeeBean.findEmployee(userData.getUserName(), userData);
        if (toBeTested.getEmployeeNumber() == null || toBeTested.getEmployeeNumber().equals("")) {
            return true;
        }
        if (employee.equalsIgnoreCase(toBeTested.getEmployeeNumber())) {
            ret = true;
        }
        return ret;
    }

    @Override
    public WorkFlowActivity copyJobLineToActivity(String ActivityNo, WorkFlowJobLines theLine, EMCUserData userData) {
        WorkFlowActivity newAct = new WorkFlowActivity();
        newAct.setCompanyId(userData.getCompanyId());
        newAct.setCreatedBy(userData.getUserName());
        newAct.setActivityId(ActivityNo);
        if ((theLine.getEmployeeId() == null) || (theLine.getEmployeeId().length() == 0)) {
            newAct.setEmployeeNumber(theLine.getManagerResponsible());
        } else {
            newAct.setEmployeeNumber(theLine.getEmployeeId());
        }
        //Todo Make sure all the fields are populated
        newAct.setDuration(theLine.getDuration());
        newAct.setHoursWorkEstimated(theLine.getHoursWorkEstimated());
        newAct.setDescription(theLine.getDescriptionOfActivity());
        newAct.setManagerResponsible(theLine.getManagerResponsible());
        newAct.setReferenceSource(WorkFlowJobLines.class.getSimpleName());
        newAct.setReference(String.valueOf(theLine.getRecordID()));
        newAct.setActivityGroup(theLine.getActivityGroup());
        newAct.setCategory(theLine.getActivityCategory());
        newAct.setSkillRequired(theLine.getSkill());
        newAct.setStatus(theLine.getStatus());
        newAct.setType(theLine.getActivityType());
        String description = "Internal Notes: " + theLine.getInternalNotes() + "\n" + "External Notes: " + theLine.getExternalNotes();
        newAct.setDetailedDescription(description);
        newAct.setOutputFileRequired(theLine.isOutputFileRequired());
        newAct.setBillable(theLine.isBillable());
        int days = (int) java.lang.Math.ceil(theLine.getDuration()) + (int) java.lang.Math.ceil(theLine.getLeadTime()) - 1; //activity to be completed today
        if (days < 0) {
            days = 0;
        }
        newAct.setRequiredCompletionDate(dateHandlerBean.calculateEndDate("Default", newAct.getStartDate() == null ? dateHandlerBean.nowDate() : newAct.getStartDate(), days, userData));
        newAct.setExpectedCompletionDate(newAct.getRequiredCompletionDate());
        newAct.setClosedByManager(theLine.getClosedByManager());

        if (theLine.isSendEmail()) {
            newAct.setEmailStatus(enumActivitySMSEmailState.TO_SEND.toString());

            if (theLine.isSendActivityGroupEmail()) {
                newAct.setEmailActivityGroup(true);
                newAct.setEmailTemplate(theLine.getActivityGroupEmailTemplate());
            } else {
                newAct.setEmailTemplate(theLine.getEmailTemplate());
                newAct.setEmailRecipientQueryName(theLine.getEmailRecipientQueryName());
            }

            newAct.setAutoCompleteActivity(theLine.isAutoCompleteEmailActivity());
        }

        if (theLine.isSendSms()) {
            newAct.setSmsStatus(enumActivitySMSEmailState.TO_SEND.toString());

            if (theLine.isSendActivityGroupSms()) {
                newAct.setSmsActivityGroup(true);
                newAct.setSmsTemplate(theLine.getActivityGroupSmsTemplate());
            } else {
                newAct.setSmsTemplate(theLine.getSmsTemplate());
                newAct.setSmsRecipientQueryName(theLine.getSmsRecipientQueryName());
            }
            
            newAct.setAutoCompleteActivity(theLine.isAutoCompleteSMSActivity());
        }

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowJobMaster.class.getName());
        query.addAnd("designNo", theLine.getDesignNo());

        WorkFlowJobMaster theMaster = (WorkFlowJobMaster) util.executeSingleResultQuery(query, userData);
        newAct.setSourceTable(theMaster.getSourceTable());
        newAct.setSourceRecordId(theMaster.getSourceRecordId());
        return newAct;
    }

    @Override
    public void copyJobLineToActivityPersist(String ActivityNo, WorkFlowJobLines theLine, EMCUserData userData) {
        try {
            Double x = new Double(theLine.getLineNo());
            ActivityNo = theLine.getDesignNo() + x.toString();
            WorkFlowActivity newAct = this.copyJobLineToActivity(ActivityNo, theLine, userData);
            newAct = (WorkFlowActivity) this.insert(newAct, userData);
            cpFileJobLineToAct(theLine, newAct, userData);
            theLine.setReference(String.valueOf(newAct.getRecordID()));
            theLine.setReferenceSource(WorkFlowActivity.class.getSimpleName());
            theLine.setRequiredCompletionDate(newAct.getRequiredCompletionDate());
            wFJobLinesBean.update(theLine, userData);

            if (theLine.getActivityGroup() != null) {
                String employee = newAct.getEmployeeNumber();
                //get Act group
                String sqlQuery = "SELECT p FROM WorkFlowActivityGroupEmp p WHERE p.companyId = '" + userData.getCompanyId() + "' AND p.activityGroup = '" + theLine.getActivityGroup() + "'";
                Query qr = em.createQuery(sqlQuery);
                List qrResult = qr.getResultList();
                Iterator it = qrResult.iterator();
                int count = 0;
                while (it.hasNext()) {
                    WorkFlowActivityGroupEmp actEmp = (WorkFlowActivityGroupEmp) it.next();
                    if (!actEmp.getEmployeeNumber().equals(employee)) {
                        try {
                            count++;
                            WorkFlowActivity curAct = this.copyJobLineToActivity(ActivityNo + Integer.toString(count), theLine, userData);
                            curAct.setEmployeeNumber(actEmp.getEmployeeNumber());
                            curAct.setReference("");
                            curAct.setReferenceSource("");
                            curAct = (WorkFlowActivity) this.insert(curAct, userData);

                            cpFileJobLineToAct(theLine, curAct, userData);
                        } catch (EMCEntityBeanException ex) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to create Activity", userData);
                        }
                    }
                }
            }
        } catch (EMCEntityBeanException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to create Activity", userData);
        }

    }

    private void cpFileJobLineToAct(WorkFlowJobLines theLine, WorkFlowActivity act, EMCUserData userData) {
        String sqlQuery = "SELECT p FROM BaseDocuRefTable p WHERE p.companyId = '" + userData.getCompanyId() + "' AND p.refRecId = '" + String.valueOf(theLine.getRecordID())
                + "' AND p.refTableName = '" + WorkFlowJobLines.class.getSimpleName() + "'";
        Query qr = em.createQuery(sqlQuery);
        List qrResult = qr.getResultList();
        Iterator it = qrResult.iterator();
        while (it.hasNext()) {
            BaseDocuRefTable theDoc = (BaseDocuRefTable) it.next();
            if (theDoc.getType().equals(emc.enums.basetables.docuref.DocurefTypes.TEMPLATE.toString()) && theDoc.getAttachmentFileName() != null) {
                try {
                    //copy the document
                    BaseDocuRefTable newDoc = new BaseDocuRefTable();
                    newDoc.setNote(theDoc.getNote());
                    newDoc.setAttachmentFileName(theDoc.getAttachmentFileName());
                    newDoc.setType(theDoc.getType());
                    newDoc.setRefRecId(String.valueOf(act.getRecordID()));
                    newDoc.setRefTableName(WorkFlowActivity.class.getSimpleName());
                    newDoc.setCompanyId(userData.getCompanyId());
                    newDoc.setCreatedBy(userData.getUserName());
                    baseDocRefBean.insert(newDoc, userData);
                } catch (EMCEntityBeanException ex) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to copy Document", userData);
                }
            }
        }

    }

    private void cpFileActToJobLine(WorkFlowJobLines theLine, WorkFlowActivity act, EMCUserData userData) {
        String sqlQuery = "SELECT p FROM BaseDocuRefTable p WHERE p.companyId = '" + userData.getCompanyId() + "' AND p.refRecId = '" + String.valueOf(act.getRecordID())
                + "' AND p.refTableName = '" + WorkFlowActivity.class.getSimpleName() + "'";
        Query qr = em.createQuery(sqlQuery);
        List qrResult = qr.getResultList();
        Iterator it = qrResult.iterator();
        while (it.hasNext()) {
            BaseDocuRefTable theDoc = (BaseDocuRefTable) it.next();
            if (theDoc.getType().equals(DocurefTypes.FILE.toString()) || theDoc.getType().equals(DocurefTypes.NOTE.toString())) {
                try {
                    //copy the document
                    BaseDocuRefTable newDoc = new BaseDocuRefTable();
                    newDoc.setNote(theDoc.getNote());
                    newDoc.setAttachmentFileName(theDoc.getAttachmentFileName());
                    newDoc.setType(theDoc.getType());
                    newDoc.setRefRecId(String.valueOf(theLine.getRecordID()));
                    newDoc.setRefTableName(WorkFlowJobLines.class.getSimpleName());
                    newDoc.setCompanyId(userData.getCompanyId());
                    newDoc.setCreatedBy(userData.getUserName());
                    baseDocRefBean.insert(newDoc, userData);
                } catch (EMCEntityBeanException ex) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to copy Document", userData);
                }
            }
        }

    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void insertInNewTx(WorkFlowActivity activity, EMCUserData userData) throws EMCEntityBeanException {
        this.insert(activity, userData);
    }

    @Override
    public boolean createNewActivity(WorkFlowActivity activity, EMCUserData userData) throws EMCEntityBeanException {
        try {
            this.insert(activity, userData);
        } catch (EMCEntityBeanException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to create new Activity: " + ex.toString(), userData);
            throw ex;
        }

        return true;
    }

    @Override
    public boolean launchWorkFlow(String workFlow, String jobDescription, String sourceTable, long sourceRecordID, String manager, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowMaster.class);
        query.addAnd("workFlowId", workFlow);
        WorkFlowMaster wfMaster = (WorkFlowMaster) util.executeSingleResultQuery(query, userData);

        if (wfMaster != null) {
            WorkFlowJobMaster jobMaster = workFlowBean.createJob(wfMaster, jobDescription, userData);
            if (jobMaster != null) {
                jobMaster.setSourceTable(sourceTable);
                jobMaster.setSourceRecordId(sourceRecordID);
                jobMaster.setManagerResponsible(manager);
                jobMaster.setStartedDate(new Date());
                jobMaster.setStartedBy(userData.getUserName());
                jobMaster.setTargetCompletionDate(Functions.nowDateAddSub(7));
                try {
                    jobMasterBean.update(jobMaster, userData);
                } catch (EMCEntityBeanException ex) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to create launch Work Flow: " + ex.toString(), userData);
                    throw ex;
                }
            }
        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find work flow " + workFlow, userData);
            return false;
        }

        return true;
    }

    public boolean reassignActivity(long actRecId, String empId, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("recordID", actRecId);
        WorkFlowActivity act = (WorkFlowActivity) util.executeSingleResultQuery(query, userData);

        if (act == null) {
            throw new EMCEntityBeanException("Failed to find the activity.");
        }

        act.setEmployeeNumber(empId);

        update(act, userData);

        return true;
    }
}

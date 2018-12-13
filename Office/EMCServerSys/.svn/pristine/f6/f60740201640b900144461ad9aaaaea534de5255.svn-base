/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.workflow;

import emc.bus.base.BaseDocRefLocal;
import emc.entity.base.BaseDocuRefTable;
import emc.entity.workflow.WorkFlowJobLines;
import emc.entity.workflow.WorkFlowJobMaster;
import emc.entity.workflow.WorkFlowLines;
import emc.entity.workflow.WorkFlowMaster;
import emc.enums.enumWFPrimaryIndicators;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Marius-Gert Coetzee
 */
@Stateless
public class WFMasterBean extends EMCEntityBean implements WFMasterLocal {

    @EJB
    private BaseDocRefLocal baseDocRefBean;
    @EJB
    private WFEvaluateLinesLocal wFEvaluateLinesBean;
    @EJB
    private WFJobLinesLocal wFJobLinesBean;
    @EJB
    private WFJobMasterLocal wFJobMasterBean;
    @EJB
    private WFLinesLocal wFLinesBean;
    @PersistenceContext
    private EntityManager em;

    public WFMasterBean() {
    }

    public void copyWF(WorkFlowMaster theMaster, String newWFId, EMCUserData userData) {
        try {
            WorkFlowMaster copiedWFMaster = (WorkFlowMaster) this.doClone(theMaster, userData);
            copiedWFMaster.setWorkFlowId(newWFId);
            this.insert(copiedWFMaster, userData);
            String sqlQuery = "SELECT u FROM WorkFlowLines u WHERE u.companyId = '" + userData.getCompanyId() + "' AND u.workFlowId = '" + theMaster.getWorkFlowId() + "' ORDER BY u.LineNo";
            Query qr = em.createQuery(sqlQuery);
            List qrResult = qr.getResultList();
            Iterator it = qrResult.iterator();
            while (it.hasNext()) {
                wFLinesBean.copyWFLine((WorkFlowLines) it.next(), newWFId, userData);
            }
            Logger.getLogger("emc").log(Level.INFO, "Successfully copied WF", userData);
        } catch (EMCEntityBeanException ex) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger(WFMasterBean.class.getName()).log(Level.SEVERE, "Failed to copy workflow", userData);
            }
        }
    }

    private WorkFlowJobMaster copyWFtoJobMaster(WorkFlowMaster theMaster, String newJobId, EMCUserData userData) {
        WorkFlowJobMaster newJobMaster = new WorkFlowJobMaster();
        newJobMaster.setCompanyId(userData.getCompanyId());
        newJobMaster.setCreatedBy(userData.getUserName());
        newJobMaster.setDescription(theMaster.getWorkFlowDescription());
        newJobMaster.setDesignNo(newJobId);
        newJobMaster.setWorkFlowId(theMaster.getWorkFlowId());

        return newJobMaster;
    }

    private WorkFlowJobLines copyWFLinestoJobMaster(WorkFlowLines theLine, String newJobId, EMCUserData userData) {
        WorkFlowJobLines newLine = new WorkFlowJobLines();
        newLine.setCompanyId(userData.getCompanyId());
        newLine.setCreatedBy(userData.getUserName());
        newLine.setDesignNo(newJobId);
        newLine.setLineNo(theLine.getLineNo());
        newLine.setNextLineNo(theLine.getNextLineNo());
        newLine.setDescriptionOfActivity(theLine.getDescriptionOfActivity());
        newLine.setPrimaryIndicator(theLine.getPrimaryIndicator());
        newLine.setActivityCategory(theLine.getActivityCategory());
        newLine.setActivityGroup(theLine.getActivityGroup());
        newLine.setActivityType(theLine.getActivityType());
        newLine.setEmployeeId(theLine.getEmployeeId());
        newLine.setInternalNotes(theLine.getInternalNotes());
        newLine.setExternalNotes(theLine.getExternalNotes());
        newLine.setLeadTime(theLine.getLeadTime());
        newLine.setDuration(theLine.getDuration());
        newLine.setSkill(theLine.getSkill());
        newLine.setManagerResponsible(theLine.getManagerResponsible());
        newLine.setReferenceSource(theLine.getReferenceSource());
        newLine.setBillable(theLine.isBillable());
        newLine.setCompletionRules(theLine.getCompletionRules());
        newLine.setOutputFileRequired(theLine.isOutputFileRequired());
        newLine.setHoursWorkEstimated(theLine.getHoursWorkEstimated());
        newLine.setClosedByManager(theLine.getClosedByManager());
        newLine.setSendEmail(theLine.isSendEmail());
        newLine.setEmailTemplate(theLine.getEmailTemplate());
        newLine.setEmailRecipientQueryName(theLine.getEmailRecipientQueryName());
        newLine.setSendActivityGroupEmail(theLine.isSendActivityGroupEmail());
        newLine.setActivityGroupEmailTemplate(theLine.getActivityGroupEmailTemplate());
        newLine.setAutoCompleteEmailActivity(theLine.isAutoCompleteEmailActivity());
        newLine.setSendSms(theLine.isSendSms());
        newLine.setSmsTemplate(theLine.getSmsTemplate());
        newLine.setSmsRecipientQueryName(theLine.getSmsRecipientQueryName());
        newLine.setSendActivityGroupSms(theLine.isSendActivityGroupSms());
        newLine.setActivityGroupSmsTemplate(theLine.getActivityGroupSmsTemplate());
        newLine.setAutoCompleteSMSActivity(theLine.isAutoCompleteSMSActivity());

        return newLine;
    }

    public WorkFlowJobMaster createJob(WorkFlowMaster theMaster, String newJobId, EMCUserData userData) {
        WorkFlowJobMaster jobMaster = null;
        try {
            jobMaster = copyWFtoJobMaster(theMaster, newJobId, userData);
            wFJobMasterBean.insert(jobMaster, userData);
            String sqlQuery = "SELECT u FROM WorkFlowLines u WHERE u.companyId = '" + userData.getCompanyId() + "' AND u.workFlowId = '" + theMaster.getWorkFlowId() + "' ORDER BY u.LineNo";
            Query qr = em.createQuery(sqlQuery);
            List qrResult = qr.getResultList();
            Iterator it = qrResult.iterator();
            while (it.hasNext()) {
                WorkFlowLines theWFLine = (WorkFlowLines) it.next();
                WorkFlowJobLines newJobLine = copyWFLinestoJobMaster(theWFLine, newJobId, userData);
                newJobLine = (WorkFlowJobLines) wFJobLinesBean.insert(newJobLine, userData);
                //copy document if it exists
                cpFileWFLineToJobLine(theWFLine, newJobLine, userData);
            }

        } catch (EMCEntityBeanException ex) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to create Task", userData);
            }
        }

        return jobMaster;
    }

    private void cpFileWFLineToJobLine(WorkFlowLines theLine, WorkFlowJobLines jobLine, EMCUserData userData) {
        String sqlQuery = "SELECT p FROM BaseDocuRefTable p WHERE p.companyId = '" + userData.getCompanyId() + "' AND p.refRecId = '" + String.valueOf(theLine.getRecordID()) +
                "' AND p.refTableName = '" + WorkFlowLines.class.getSimpleName() + "'";
        Query qr = em.createQuery(sqlQuery);
        List qrResult = qr.getResultList();
        Iterator it = qrResult.iterator();
        while (it.hasNext()) {
            BaseDocuRefTable theDoc = (BaseDocuRefTable) it.next();
            if (theDoc.getType().equals(emc.enums.basetables.docuref.DocurefTypes.TEMPLATE.toString()) && theDoc.getAttachmentFileName() != null) { //also copy notes
                //copy the document
                try {
                    //also copy notes
                    //copy the document
                    BaseDocuRefTable newDoc = new BaseDocuRefTable();
                    newDoc.setNote(theDoc.getNote());
                    newDoc.setAttachmentFileName(theDoc.getAttachmentFileName());
                    newDoc.setType(theDoc.getType());
                    newDoc.setRefRecId(String.valueOf(jobLine.getRecordID()));
                    newDoc.setRefTableName(WorkFlowJobLines.class.getSimpleName());
                    newDoc.setCompanyId(userData.getCompanyId());
                    newDoc.setCreatedBy(userData.getUserName());
                    newDoc.setSummary(theDoc.getSummary());
                    baseDocRefBean.insert(newDoc, userData);
                } catch (EMCEntityBeanException ex) {
                    if (EMCDebug.getDebug()) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to copy document to Task", userData);
                    }
                }
            }
        }

    }

    public String evaluateWFLines(WorkFlowMaster theMaster, EMCUserData userData) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();

        wFEvaluateLinesBean.evaluateLines(0, 0,
                enumWFPrimaryIndicators.STAGEGATE, root, theMaster, userData);
        return wFEvaluateLinesBean.encodeWFTree(root);
    }

    public void allDocumentsAttached(WorkFlowMaster theMaster, EMCUserData userData) {
        String sqlQuery = "SELECT u FROM WorkFlowLines u WHERE u.companyId = '" + userData.getCompanyId() + "' AND u.workFlowId = '" +
                theMaster.getWorkFlowId() + "' ORDER BY u.LineNo";
        Query qr = em.createQuery(sqlQuery);
        List qrResult = qr.getResultList();
        Iterator it = qrResult.iterator();
        boolean foundSome = false;
        while (it.hasNext()) {
            WorkFlowLines theLine = (WorkFlowLines) it.next();
            List x = new ArrayList();
            x.add(0, "SELECT p FROM BaseDocuRefTable p WHERE p.companyId = '" + userData.getCompanyId() + "' AND p.refRecId = '" + String.valueOf(theLine.getRecordID()) +
                    "' AND p.refTableName = '" + WorkFlowLines.class.getSimpleName() + "'");
            userData.setUserData(x);
            Collection<BaseDocuRefTable> col = baseDocRefBean.getDataInRange(BaseDocuRefTable.class, userData, 0, 1000);
            Iterator colit = col.iterator();
            while (colit.hasNext()) {
                BaseDocuRefTable theDoc = (BaseDocuRefTable) colit.next();
                String note = theDoc.getNote();
                if (note != null && !note.equals("")) {
                    int toplace = 25;
                    if (note.length() < 26) {
                        toplace = note.length() - 1;
                    }
                    note = note.substring(0, toplace);
                } else {
                    note = "";
                }
                String filename = theDoc.getAttachmentFileName();
                if (filename == null) {
                    filename = "none";
                }
                Logger.getLogger("emc").log(Level.INFO, "Document Note: " + note + "... File: " + filename, userData);
                foundSome = true;
            }
        }
        if (!foundSome) {
            Logger.getLogger("emc").log(Level.INFO, "No documents are attached to this Work Flow.", userData);
        }
    }
}

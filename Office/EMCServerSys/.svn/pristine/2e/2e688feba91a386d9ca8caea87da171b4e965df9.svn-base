/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.workflow;

import emc.bus.base.BaseDocRefLocal;
import emc.datatypes.workflow.activities.ClosedByManager;
import emc.entity.base.BaseDocuRefTable;
import emc.entity.workflow.WorkFlowActivity;
import emc.entity.workflow.WorkFlowJobLines;
import emc.entity.workflow.WorkFlowJobMaster;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Marius-Gert Coetzee
 */
@Stateless
public class WFJobLinesBean extends EMCEntityBean implements WFJobLinesLocal {

    @EJB
    private BaseDocRefLocal baseDocRefBean;
    @EJB
    private WFActivityLocal activityBean;
    @PersistenceContext
    private EntityManager em;

    public WFJobLinesBean() {
    }

    @Override
    public void redoJobLines(String thenumbers, String stageGateLineNo, String designNo, EMCUserData userData) {
        String linenum = "";
        String toLinenum = "";
        boolean toFlag = false;
        //parse the string
        for (int j = 0; j < thenumbers.length(); j++) {
            char x = thenumbers.charAt(j);
            String y = "" + thenumbers.charAt(j);
            if (Character.isDigit(x) || y.equals(".")) {
                if (toFlag) {
                    toLinenum += x;
                } else {
                    linenum += x;
                }
            } else {
                String temp = "" + x;
                if (temp.equals("-")) {
                    toLinenum = "";
                    toFlag = true;
                }
                if (temp.equals(",")) {
                    //do the actual copy
                    createJobLine(linenum, toLinenum, designNo, stageGateLineNo, userData);
                    linenum = "";
                    toLinenum = "";
                    toFlag = false;
                }
            }

        }
        //last entry
        createJobLine(linenum, toLinenum, designNo, stageGateLineNo, userData);
        //ensure that if the line selected is a stage gate that that line is auto created
        String sqlQuery = "SELECT u FROM WorkFlowJobLines u WHERE u.lineNo = '" + stageGateLineNo + "' AND u.designNo = '" + designNo +
                "' AND u.companyId = '" + userData.getCompanyId() + "'";
        Query qr = em.createQuery(sqlQuery);
        List qrResult = qr.getResultList();
        Iterator it = qrResult.iterator();
        if (it.hasNext()) {
            WorkFlowJobLines jobLine = (WorkFlowJobLines) it.next();
            if (jobLine.getPrimaryIndicator().equals("Stage Gate")) {
                Double lineNumber = Double.parseDouble(stageGateLineNo);
                double lineNo = lineNumber.doubleValue() + lineNumber.doubleValue() / 1000;

                sqlQuery = "SELECT u FROM WorkFlowJobLines u WHERE u.lineNo = '" + Double.toString(lineNo) + "' AND u.designNo = '" + designNo +
                        "' AND u.companyId = '" + userData.getCompanyId() + "'";
                Query qrTemp = em.createQuery(sqlQuery);
                List qrTempResult = qrTemp.getResultList();
                Iterator itTemp = qrTempResult.iterator();
                if (!itTemp.hasNext()) {
                    createJobLine(stageGateLineNo, "", designNo, stageGateLineNo, userData);
                }

            }
        }
    }

    private void createJobLine(String from, String to, String designNo, String stageGateLineNo, EMCUserData userData) {
        if (EMCDebug.getDebug()) {
            Logger.getLogger("emc").log(Level.INFO, "from: " + from + " to:" + to, userData);
        }
        boolean dofrom = true;
        boolean doto = true;
        if (from.length() == 0) {
            dofrom = false;
        }
        if (to.length() == 0) {
            doto = false;
        }
        if (dofrom && !doto) {
            String sqlQuery = "SELECT u FROM WorkFlowJobLines u WHERE u.lineNo = '" + from + "' AND u.designNo = '" + designNo +
                    "' AND u.companyId = '" + userData.getCompanyId() + "'";
            Query qr = em.createQuery(sqlQuery);
            List qrResult = qr.getResultList();
            Iterator it = qrResult.iterator();
            if (it.hasNext()) {
                WorkFlowJobLines jobLine = (WorkFlowJobLines) it.next();
                //copy the line
                copyJobLineAndPersist(jobLine, stageGateLineNo, userData);
            }
        } else if (dofrom && doto) {
            String sqlQuery = "SELECT u FROM WorkFlowJobLines u WHERE u.designNo = '" + designNo +
                    "' AND u.companyId = '" + userData.getCompanyId() + "' AND u.lineNo >= '" + from +
                    "' AND u.lineNo <= '" + to + "'";
            Query qr = em.createQuery(sqlQuery);
            List qrResult = qr.getResultList();
            Iterator it = qrResult.iterator();
            while (it.hasNext()) {
                WorkFlowJobLines jobLine = (WorkFlowJobLines) it.next();
                //copy the line
                copyJobLineAndPersist(jobLine, stageGateLineNo, userData);
            }
        }
    }

    private void copyJobLineAndPersist(WorkFlowJobLines jobLine, String StageGateNo, EMCUserData userData) {
        try {
            WorkFlowJobLines newLine = new WorkFlowJobLines();
            Double lineNumber = Double.parseDouble(StageGateNo);
            double lineNo = lineNumber.doubleValue() + jobLine.getLineNo() / 1000;
            newLine.setCompanyId(userData.getCompanyId());
            newLine.setCreatedBy(userData.getUserName());
            newLine.setDesignNo(jobLine.getDesignNo());
            newLine.setLineNo(lineNo);
            if (jobLine.getNextLineNo() == 0) {
                newLine.setNextLineNo(jobLine.getNextLineNo());
            } else {
                newLine.setNextLineNo(lineNumber.doubleValue() + jobLine.getNextLineNo() / 1000);
            }
            newLine.setDescriptionOfActivity(jobLine.getDescriptionOfActivity());
            newLine.setPrimaryIndicator(jobLine.getPrimaryIndicator());
            newLine.setActivityCategory(jobLine.getActivityCategory());
            newLine.setActivityGroup(jobLine.getActivityGroup());
            newLine.setActivityType(jobLine.getActivityType());
            newLine.setEmployeeId(jobLine.getEmployeeId());
            newLine.setInternalNotes(jobLine.getInternalNotes());
            newLine.setExternalNotes(jobLine.getExternalNotes());
            newLine.setDuration(jobLine.getDuration());
            newLine.setSkill(jobLine.getSkill());
            newLine.setManagerResponsible(jobLine.getManagerResponsible());
            newLine.setReferenceSource(jobLine.getReferenceSource());
            newLine.setBillable(jobLine.isBillable());
            newLine.setCompletionRules(jobLine.getCompletionRules());
            newLine.setOutputFileRequired(jobLine.isOutputFileRequired());
            newLine.setHoursWorkEstimated(jobLine.getHoursWorkEstimated());
            newLine.setSendEmail(jobLine.isSendEmail());
            newLine.setEmailTemplate(jobLine.getEmailTemplate());
            newLine.setEmailRecipientQueryName(jobLine.getEmailRecipientQueryName());
            newLine.setSendActivityGroupEmail(jobLine.isSendActivityGroupEmail());
            newLine.setActivityGroupEmailTemplate(jobLine.getActivityGroupEmailTemplate());
            newLine.setAutoCompleteEmailActivity(jobLine.isAutoCompleteEmailActivity());
            newLine.setSendSms(jobLine.isSendSms());
            newLine.setSmsTemplate(jobLine.getSmsTemplate());
            newLine.setSmsRecipientQueryName(jobLine.getSmsRecipientQueryName());
            newLine.setSendActivityGroupSms(jobLine.isSendActivityGroupSms());
            newLine.setActivityGroupSmsTemplate(jobLine.getActivityGroupSmsTemplate());
            newLine.setAutoCompleteSMSActivity(jobLine.isAutoCompleteSMSActivity());

            newLine = (WorkFlowJobLines) this.insert(newLine, userData);
            //also copy the attachments
            cpFileJobLineToJobLine(jobLine, newLine, userData);
        } catch (EMCEntityBeanException ex) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to copy TaskLine", userData);
            }
        }
    }

    private void cpFileJobLineToJobLine(WorkFlowJobLines theLine, WorkFlowJobLines newLine, EMCUserData userData) {
        String sqlQuery = "SELECT p FROM BaseDocuRefTable p WHERE p.companyId = '" + userData.getCompanyId() + "' AND p.refRecId = '" + String.valueOf(theLine.getRecordID()) +
                "' AND p.refTableName = '" + WorkFlowJobLines.class.getSimpleName() + "'";
        Query qr = em.createQuery(sqlQuery);
        List qrResult = qr.getResultList();
        Iterator it = qrResult.iterator();
        while (it.hasNext()) {
            BaseDocuRefTable theDoc = (BaseDocuRefTable) it.next();
            if (theDoc.getType() != null) {
                if (theDoc.getType().equals("T") && theDoc.getAttachmentFileName() != null) { //Redo document should copy all notes and docs
                    //copy the document
                    try {
                        //Redo document should copy all notes and docs
                        //copy the document
                        BaseDocuRefTable newDoc = new BaseDocuRefTable();
                        newDoc.setNote(theDoc.getNote());
                        newDoc.setAttachmentFileName(theDoc.getAttachmentFileName());
                        newDoc.setType(theDoc.getType());
                        newDoc.setRefRecId(String.valueOf(newLine.getRecordID()));
                        newDoc.setRefTableName(WorkFlowJobLines.class.getSimpleName());
                        newDoc.setCompanyId(userData.getCompanyId());
                        newDoc.setCreatedBy(userData.getUserName());
                        baseDocRefBean.insert(newDoc, userData);
                    } catch (EMCEntityBeanException ex) {
                        if (EMCDebug.getDebug()) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to copy document", userData);
                        }
                    }
                }
            }
        }

    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        WorkFlowJobLines jl = (WorkFlowJobLines) dobject;
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("reference", jl.getRecordID());
        query.addAnd("closedByManager", false);
        List<WorkFlowActivity> jobActivity = util.executeGeneralSelectQuery(query, userData);
        for (WorkFlowActivity activity : jobActivity) {
            activityBean.delete(activity, userData);
        }
        return super.delete(dobject, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {

        WorkFlowJobLines lines = (WorkFlowJobLines) uobject;
        if (lines.getClosedByManager()) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowJobLines.class);
            query.addAnd("closedByManager", false);
            query.addAnd("recordID", lines.getRecordID(), EMCQueryConditions.NOT);
            if (!util.exists(query, userData)) {
                query = new EMCQuery(enumQueryTypes.UPDATE, WorkFlowJobMaster.class);
                query.addSet("completed", Functions.nowDate());
                query.addAnd("designNo", lines.getDesignNo());
                util.executeUpdate(query, userData);
            }
        }
        return super.update(uobject, userData);
    }
}

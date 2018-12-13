/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.workflow;

import emc.bus.base.BaseDocRefLocal;
import emc.entity.base.BaseDocuRefTable;
import emc.entity.workflow.WorkFlowLines;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Marius-Gert Coetzee
 */
@Stateless
public class WFLinesBean extends EMCEntityBean implements WFLinesLocal {

    @EJB
    private BaseDocRefLocal baseDocRefBean;
    @PersistenceContext
    private EntityManager em;

    public WFLinesBean() {

    }

    public void copyWFLine(WorkFlowLines theLine, String WFId, EMCUserData userData) {
        try {
            WorkFlowLines copiedWFLines = (WorkFlowLines) this.doClone(theLine, userData);
            copiedWFLines.setWorkFlowId(WFId);
            this.insert(copiedWFLines, userData);
            copyWorkFlowLinesDocRef(theLine, copiedWFLines, userData);
        } catch (EMCEntityBeanException ex) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to copy WF line", userData);
            }
        }
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean allowPersist = true;
        WorkFlowLines toValidate = (WorkFlowLines) vobject;
        allowPersist = super.doUpdateValidation(vobject, userData);
        if (toValidate.getLineNo() == 0) {
            Logger.getLogger("emc").log(Level.SEVERE, "Line No may not be 0.", userData);
            allowPersist = false;
        }
        if ((toValidate.getNextLineNo() == 0) && (!toValidate.getPrimaryIndicator().equals("Stage Gate"))) {
            Logger.getLogger("emc").log(Level.SEVERE, "Next Line No may not be 0", userData);
            allowPersist = false;
        }
        return allowPersist;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean allowPersist = true;
        allowPersist = super.doInsertValidation(vobject, userData);
        WorkFlowLines toValidate = (WorkFlowLines) vobject;
        if (toValidate.getLineNo() == 0) {
            Logger.getLogger("emc").log(Level.SEVERE, "Line No may not be 0.", userData);
            allowPersist = false;
        }
        if ((toValidate.getNextLineNo() == 0) && (!toValidate.getPrimaryIndicator().equals("Stage Gate"))) {
            Logger.getLogger("emc").log(Level.SEVERE, "Next Line No may not be 0", userData);
            allowPersist = false;
        }
        if ((toValidate.getManagerResponsible() == null) || (toValidate.getManagerResponsible().length() == 0)) {
            Logger.getLogger("emc").log(Level.SEVERE, "No Task Manager Assigned.", userData);
            allowPersist = false;
        }
        if (toValidate.getDuration() < 0) {
            Logger.getLogger("emc").log(Level.SEVERE, "Duration cannot be negative.", userData);
            allowPersist = false;
        }
        if (toValidate.getHoursWorkEstimated() <= 0) {
            Logger.getLogger("emc").log(Level.SEVERE, "Hours work estimated may not be zero or negative.", userData);
            allowPersist = false;
        }
        if ((toValidate.getDescriptionOfActivity() == null) || (toValidate.getDescriptionOfActivity().length() == 0)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Activity Description may not be blank.", userData);
            allowPersist = false;
        }
        if ((toValidate.getPrimaryIndicator() == null) || (toValidate.getPrimaryIndicator().length() == 0)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Primary indicator may not be blank.", userData);
            allowPersist = false;
        }
        if ((toValidate.getActivityType() == null) || (toValidate.getActivityType().length() == 0)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Activity Type may not be blank.", userData);
            allowPersist = false;
        }
        if ((toValidate.getActivityCategory() == null) || (toValidate.getActivityCategory().length() == 0)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Activity Category may not be blank.", userData);
            allowPersist = false;
        }

        return allowPersist;
    }

    private void copyWorkFlowLinesDocRef(WorkFlowLines theLine, WorkFlowLines theNewLine, EMCUserData userData) {
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
                    newDoc.setRefRecId(String.valueOf(theNewLine.getRecordID()));
                    newDoc.setRefTableName(WorkFlowLines.class.getSimpleName());
                    newDoc.setCompanyId(userData.getCompanyId());
                    newDoc.setCreatedBy(userData.getUserName());
                    newDoc.setSummary(theDoc.getSummary());
                    baseDocRefBean.insert(newDoc, userData);

                } catch (EMCEntityBeanException ex) {
                    if (EMCDebug.getDebug()) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to copy attached document", userData);
                    }
                }
            }
        }
    }
}

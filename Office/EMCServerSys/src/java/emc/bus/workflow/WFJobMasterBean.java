/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.workflow;

import emc.bus.base.BaseDocRefLocal;
import emc.entity.base.BaseDocuRefTable;
import emc.entity.workflow.WorkFlowJobLines;
import emc.entity.workflow.WorkFlowJobMaster;
import emc.enums.enumPersistOptions;
import emc.enums.enumWFPrimaryIndicators;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
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
public class WFJobMasterBean extends EMCEntityBean implements WFJobMasterLocal {
    @EJB
    private BaseDocRefLocal baseDocRefBean;
    @EJB
    private EvaluateJobLinesLocal evaluateJobLines;
    @EJB
    private WFActivityLocal wFActivityBean;
    @PersistenceContext
    private EntityManager em;
    public WFJobMasterBean(){
        
    }
    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData){
        WorkFlowJobMaster toBeTested = (WorkFlowJobMaster) theRecord;
        WorkFlowJobMaster persistedRecord = (WorkFlowJobMaster)em.find(theRecord.getClass(),toBeTested.getRecordID());
        if(fieldNameToValidate.equals("startedDate")){
            boolean ret = true;
            if(persistedRecord == null){
                Logger.getLogger("emc").log(Level.SEVERE,"Unsaved Task cannot be started", userData);
                return false;
            }
            if(persistedRecord.getStartedDate() != null){
                Logger.getLogger("emc").log(Level.SEVERE,"Task started date may not be changed", userData);
                return false;
            }
            if(toBeTested.getManagerResponsible()==null){
                Logger.getLogger("emc").log(Level.SEVERE,"No Manager asssigned to the Task", userData);
                return false;
            }
            if(toBeTested.getStartedDate() != null){
                //evaluate all the lines 
                 String sqlQuery = "SELECT u FROM WorkFlowJobLines u WHERE u.companyId = '"
                   + userData.getCompanyId() +"' AND u.designNo = '" +
                   toBeTested.getDesignNo() + "' ORDER BY u.lineNo";                
                Query qr = em.createQuery(sqlQuery);
                List qrResult = qr.getResultList();
                Iterator it = qrResult.iterator(); 
                while(it.hasNext())
                {
                    WorkFlowJobLines theLine = (WorkFlowJobLines) it.next();
                    ret = evaluateJobLines.evaluateWFLine(theLine, userData);
                }
                if(!ret){
                    Logger.getLogger("emc").log(Level.WARNING,"Could not start Task", userData);
                }
                  
                return ret;
            }
        }
        if(fieldNameToValidate.equals("designNo")){
            if((persistedRecord != null) && (persistedRecord.getStartedDate()!=null)){
                    Logger.getLogger("emc").log(Level.SEVERE,"May not change the Design Number once Task is started.",userData);
                    return false;
            }
        }
        return super.validateField(fieldNameToValidate, theRecord, userData);
    }
   

   
    private boolean findPrimary(DefaultMutableTreeNode node, EMCUserData userData){
        Enumeration e = node.children();
        boolean res = false;
        if(e.hasMoreElements()){
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) e.nextElement(); 
            WorkFlowJobLines newJobLine = (WorkFlowJobLines) n.getUserObject();
            if(newJobLine.getPrimaryIndicator().equals("Stage Gate")){
                res = findPrimary(n,userData);
                if(!res){
                   createActivity(newJobLine,userData);//only Stage Gate no deeper nodes
                }
            } else {
                //create the activity and seek further nodes
                if(newJobLine.getPrimaryIndicator().equals("Primary")){
                    createActivity(newJobLine,userData);
                }
                findPrimary(n,userData);
                res = true;
            }
        }
        return res;
    }
    private void createActivity(WorkFlowJobLines newJobLine, EMCUserData userData){
         wFActivityBean.copyJobLineToActivityPersist("",newJobLine, userData);
    }
    
    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean goOn = true;
        if(userData.getUserData()!=null && userData.getUserData().size()>= 6){
                if(userData.getUserData().get(5).toString().equals(enumPersistOptions.TEST_UPDATE_DELETE.toString())){
                  goOn = false;
                }
            }
        if(goOn){
            WorkFlowJobMaster toBeTested = (WorkFlowJobMaster) uobject;
            WorkFlowJobMaster persistedRecord = (WorkFlowJobMaster)em.find(uobject.getClass(),toBeTested.getRecordID());

            //If persisted == toBeTested and there is a start date, assume that the record is being inserted and started in the same transaction.
            if((persistedRecord.getStartedDate() == null && toBeTested.getStartedDate() != null) || (toBeTested == persistedRecord && toBeTested.getStartedDate() != null)) {
                //store the record
                toBeTested.setStartedBy(userData.getUserName());
                //create activities
                DefaultMutableTreeNode root = new DefaultMutableTreeNode();
                evaluateJobLines.evaluateLines(0, 0, 
                        enumWFPrimaryIndicators.STAGEGATE, root, toBeTested, userData);
                findPrimary(root,userData);
                Date expectCom = new Date();
                evaluateJobLines.encodeWFTree(root, toBeTested, expectCom, userData);
                toBeTested.setTargetCompletionDate(expectCom);
            }
        }
        return super.update(uobject, userData);
    }

    
    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        WorkFlowJobMaster workflowJobMaster = (WorkFlowJobMaster) vobject;
//        if(workflowJobMaster.getRecordID()!=0 && workflowJobMaster.getStartedDate()!=null){
//            return false;
//        }
        return super.doDeleteValidation(vobject, userData);
    }
    public String evaluateWFJobLines(WorkFlowJobMaster theMaster, EMCUserData userData){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        
        evaluateJobLines.evaluateLines(0, 0, 
                enumWFPrimaryIndicators.STAGEGATE, root, theMaster, userData);
        return evaluateJobLines.encodeWFTree(root,theMaster,new Date(), userData);
    }
    public void allDocumentsAttached(WorkFlowJobMaster theMaster, EMCUserData userData){
        String sqlQuery = "SELECT u FROM WorkFlowJobLines u WHERE u.companyId = '"
                   + userData.getCompanyId() +"' AND u.designNo = '" +
                   theMaster.getDesignNo() + "' ORDER BY u.lineNo";                
        Query qr = em.createQuery(sqlQuery);
        List qrResult = qr.getResultList();
        Iterator it = qrResult.iterator(); 
        boolean foundSome = false;
        while(it.hasNext())
        {
            WorkFlowJobLines theLine = (WorkFlowJobLines) it.next();
            List x = new ArrayList();
            x.add(0,"SELECT p FROM BaseDocuRefTable p WHERE p.companyId = '"+ userData.getCompanyId() 
                    +"' AND p.refRecId = '" + String.valueOf(theLine.getRecordID()) +
                    "' AND p.refTableName = '"+ WorkFlowJobLines.class.getSimpleName() + "'");
            userData.setUserData(x);
            Collection <BaseDocuRefTable> col = baseDocRefBean.getDataInRange(BaseDocuRefTable.class, userData, 0, 1000);
            Iterator colit = col.iterator();
            while(colit.hasNext()){
                BaseDocuRefTable theDoc = (BaseDocuRefTable) colit.next();
                String note = theDoc.getNote();
                if(note != null){
                    int toplace = 25;
                    if(note.length() < 26) toplace = note.length() -1;
                    note = note.substring(0, toplace);
                }else{
                    note = "";
                }
                String filename = theDoc.getAttachmentFileName();
                if(filename == null){
                    filename = "none";
                }
                Logger.getLogger("emc").log(Level.INFO,"Document Note: "+ note + "... File: " + filename,userData);
                foundSome = true;
            }
        }
        if(!foundSome){
            Logger.getLogger("emc").log(Level.INFO,"No documents are attached to this Task.",userData);
        }
    }
    
 
}

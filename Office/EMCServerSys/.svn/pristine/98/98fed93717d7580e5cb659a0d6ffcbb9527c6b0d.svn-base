/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.workflow;

import emc.entity.workflow.WorkFlowLines;
import emc.entity.workflow.WorkFlowMaster;
import emc.enums.enumWFPrimaryIndicators;
import emc.framework.EMCUserData;
import emc.functions.xml.EMCXMLHandler;
import emc.wf.common.EvaluationTree;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author rico
 */
@Stateless 
public class WFEvaluateLinesBean implements WFEvaluateLinesLocal {
    @PersistenceContext
    private EntityManager em;
    private double stageGateEnd;
    private EMCXMLHandler xmlHandler = new EMCXMLHandler();
    public WFEvaluateLinesBean(){
        
    }
    public void evaluateLines(double firstLine, double nextLine,enumWFPrimaryIndicators piIndicator, 
            DefaultMutableTreeNode root,WorkFlowMaster theMaster, EMCUserData userData){
        switch(piIndicator){
            case STAGEGATE: processStageGate(root,theMaster,userData); break;
            case PRIMARY:  processPrimary(firstLine, nextLine,root,theMaster,userData); break;
            case SECONDARY1: processSecondary1(firstLine, nextLine,root,theMaster,userData);break;
            case SECONDARY2: processSecondary2(firstLine, nextLine,root,theMaster,userData);break;
            case SECONDARY3: processSecondary3(firstLine, nextLine,root,theMaster,userData);break;
        }
    }
    private void processPrimary(double firstLine, double nextLine,DefaultMutableTreeNode root,
            WorkFlowMaster theMaster,EMCUserData userData){
        double currentFirstLine = firstLine;
        double currentLastLine = nextLine;
        String sqlQuery = "SELECT u FROM WorkFlowLines u WHERE u.companyId = '"
                   + userData.getCompanyId() +"' AND u.workFlowId = '" +
                   theMaster.getWorkFlowId() +"' AND u.LineNo > '" +
                   firstLine + "' AND u.LineNo < '"+
                   nextLine + "' AND u.primaryIndicator = 'Primary' ORDER BY u.LineNo";                
        Query qr = em.createQuery(sqlQuery);
        List qrResult = qr.getResultList();
  
        if(qrResult.size() == 1){
             WorkFlowLines primary = (WorkFlowLines)qrResult.get(0);
             currentFirstLine = primary.getLineNo();
             evaluateWFLine(primary,userData);
             root.add(new DefaultMutableTreeNode(WFLinesToXMLNode(primary)));
             evaluateLines(currentFirstLine,currentLastLine,convertPIString2enum("Secondary 1"),
                        root.getLastLeaf(), theMaster,userData);
        }
        if(qrResult.size() > 1){
            for(int j = 0; j < qrResult.size(); j++)
            {
                WorkFlowLines primary = (WorkFlowLines)qrResult.get(j);
                currentFirstLine = primary.getLineNo();
                evaluateWFLine(primary,userData);
                root.add(new DefaultMutableTreeNode(WFLinesToXMLNode(primary)));
                if(j+1 < qrResult.size()){
                    primary = (WorkFlowLines)qrResult.get(j+1);
                    currentLastLine = primary.getLineNo();
                }
                else{
                    currentLastLine = nextLine;
                }
                evaluateLines(currentFirstLine,currentLastLine,convertPIString2enum("Secondary 1"),
                        root.getLastLeaf(), theMaster,userData);

            }
        }
        
    }
    private enumWFPrimaryIndicators convertPIString2enum(String piIndicator){
        if(piIndicator.matches("Stage Gate")){
            return enumWFPrimaryIndicators.STAGEGATE;
        }
        if(piIndicator.matches("Primary")){
            return enumWFPrimaryIndicators.PRIMARY;
        }
        if(piIndicator.matches("Secondary 1")){
            return enumWFPrimaryIndicators.SECONDARY1;
        }
        if(piIndicator.matches("Secondary 2")){
            return enumWFPrimaryIndicators.SECONDARY2;
        }
        if(piIndicator.matches("Secondary 3")){
            return enumWFPrimaryIndicators.SECONDARY3;
        }
        return enumWFPrimaryIndicators.PRIMARY;
    }
    private void processStageGate(DefaultMutableTreeNode root,WorkFlowMaster theMaster,EMCUserData userData){
        double firstLine = 0;
        double currentStageGate = Double.MAX_VALUE; 
        String sqlQuery = "SELECT u FROM WorkFlowLines u WHERE u.companyId = '"
                   + userData.getCompanyId() +"' AND u.workFlowId = '" +
                   theMaster.getWorkFlowId() + "' AND u.primaryIndicator = 'Stage Gate' ORDER BY u.LineNo";                
        Query qr = em.createQuery(sqlQuery);
        List qrResult = qr.getResultList();
        Iterator it = qrResult.iterator(); 
        if(!it.hasNext()) Logger.getLogger("emc").log(Level.SEVERE,"No Stage Gate found on WF",userData);
        while(it.hasNext())
        {
            WorkFlowLines gate = (WorkFlowLines)it.next();
            currentStageGate = gate.getLineNo();
            root.add(new DefaultMutableTreeNode(WFLinesToXMLNode(gate)));
            this.stageGateEnd = currentStageGate;
            evaluateWFLine(gate,userData);
            evaluateLines(firstLine,currentStageGate,convertPIString2enum("Primary"),
                    root.getLastLeaf(), theMaster,userData);
            firstLine = currentStageGate;
        }
        currentStageGate = Double.MAX_VALUE; 
        this.stageGateEnd = currentStageGate;
        evaluateLines(firstLine,currentStageGate,convertPIString2enum("Primary"),
                    root, theMaster,userData);
    }
    private void processSecondary1(double firstLine, double nextLine,DefaultMutableTreeNode root,
            WorkFlowMaster theMaster,EMCUserData userData){
        double currentFirstLine = firstLine;
        double currentLastLine = nextLine;
        String sqlQuery = "SELECT u FROM WorkFlowLines u WHERE u.companyId = '"
                   + userData.getCompanyId() +"' AND u.workFlowId = '" +
                   theMaster.getWorkFlowId() +"' AND u.LineNo > '" +
                   firstLine + "' AND u.LineNo < '"+
                   nextLine + "' AND u.primaryIndicator = 'Secondary 1' ORDER BY u.LineNo";                
        Query qr = em.createQuery(sqlQuery);
        List qrResult = qr.getResultList();
        if(qrResult.size() == 1){
             WorkFlowLines primary = (WorkFlowLines)qrResult.get(0);
             currentFirstLine = primary.getLineNo();
             evaluateWFLine(primary,userData);
             root.add(new DefaultMutableTreeNode(WFLinesToXMLNode(primary)));
             evaluateLines(currentFirstLine,currentLastLine,convertPIString2enum("Secondary 2"),
                        root.getLastLeaf(), theMaster,userData);
        }
        if(qrResult.size() > 1){
            for(int j = 0; j < qrResult.size(); j++)
            {
                WorkFlowLines primary = (WorkFlowLines)qrResult.get(j);
                currentFirstLine = primary.getLineNo();
                evaluateWFLine(primary,userData);
                root.add(new DefaultMutableTreeNode(WFLinesToXMLNode(primary)));
                if(j+1 < qrResult.size()){
                    primary = (WorkFlowLines)qrResult.get(j+1);
                    currentLastLine = primary.getLineNo();
                }
                else{
                    currentLastLine = nextLine;
                }
                evaluateLines(currentFirstLine,currentLastLine,convertPIString2enum("Secondary 2"),
                        root.getLastLeaf(), theMaster,userData);

            }
        }
    }
    private void processSecondary2(double firstLine, double nextLine,DefaultMutableTreeNode root,
            WorkFlowMaster theMaster,EMCUserData userData){
         double currentFirstLine = firstLine;
        double currentLastLine = nextLine;
        String sqlQuery = "SELECT u FROM WorkFlowLines u WHERE u.companyId = '"
                   + userData.getCompanyId() +"' AND u.workFlowId = '" +
                   theMaster.getWorkFlowId() +"' AND u.LineNo > '" +
                   firstLine + "' AND u.LineNo < '"+
                   nextLine + "' AND u.primaryIndicator = 'Secondary 2' ORDER BY u.LineNo";                
        Query qr = em.createQuery(sqlQuery);
        List qrResult = qr.getResultList();
       if(qrResult.size() == 1){
             WorkFlowLines primary = (WorkFlowLines)qrResult.get(0);
             currentFirstLine = primary.getLineNo();
             evaluateWFLine(primary,userData);
             root.add(new DefaultMutableTreeNode(WFLinesToXMLNode(primary)));
             evaluateLines(currentFirstLine,currentLastLine,convertPIString2enum("Secondary 3"),
                        root.getLastLeaf(), theMaster,userData);
        }
        if(qrResult.size() > 1){
            for(int j = 0; j < qrResult.size(); j++)
            {
                WorkFlowLines primary = (WorkFlowLines)qrResult.get(j);
                currentFirstLine = primary.getLineNo();
                evaluateWFLine(primary,userData);
                root.add(new DefaultMutableTreeNode(WFLinesToXMLNode(primary)));
                if(j+1 < qrResult.size()){
                    primary = (WorkFlowLines)qrResult.get(j+1);
                    currentLastLine = primary.getLineNo();
                }
                else{
                    currentLastLine = nextLine;
                }
                evaluateLines(currentFirstLine,currentLastLine,convertPIString2enum("Secondary 3"),
                        root.getLastLeaf(), theMaster,userData);

            }
        }
    }
    private void processSecondary3(double firstLine, double nextLine,DefaultMutableTreeNode root,
            WorkFlowMaster theMaster,EMCUserData userData){
 
        String sqlQuery = "SELECT u FROM WorkFlowLines u WHERE u.companyId = '"
                   + userData.getCompanyId() +"' AND u.workFlowId = '" +
                   theMaster.getWorkFlowId() +"' AND u.LineNo > '" +
                   firstLine + "' AND u.LineNo < '"+
                   nextLine + "' AND u.primaryIndicator = 'Secondary 3' ORDER BY u.LineNo";                
        Query qr = em.createQuery(sqlQuery);
        List qrResult = qr.getResultList();
        Iterator it = qrResult.iterator(); 
        while(it.hasNext())
        {
            WorkFlowLines primary = (WorkFlowLines)it.next();
            evaluateWFLine(primary,userData);
            root.add(new DefaultMutableTreeNode(WFLinesToXMLNode(primary)));
            
        }
    }
    private EvaluationTree WFLinesToXMLNode(WorkFlowLines line){
        EvaluationTree node = new EvaluationTree();
        node.setDescription(line.getDescriptionOfActivity());
        node.setLineNo(new Double(line.getLineNo()));
        node.setNextLineNo(new Double(line.getNextLineNo()));
        node.setPrimaryIndicator(line.getPrimaryIndicator());
        return node;
        
    }
    public String encodeWFTree(DefaultMutableTreeNode root){
         StringBuffer xml = new StringBuffer();
         encode(root, xml);
         xml.insert(0, "<tree>\r\n");
         xml.append("\r\n</tree>");
        return xml.toString();
    }
    private void encode(DefaultMutableTreeNode node, StringBuffer xml) {
        Enumeration e = node.children();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) e.nextElement();

            if (xml.length() > 0) {
                xml.append("\r\n");
            }
           
            xml.append(encode((EvaluationTree) n.getUserObject()));
            if (n.getChildCount() == 0) {
                xml.append("</evaluationtree>");
            } else {
                encode(n, xml);
                xml.append("\r\n</evaluationtree>");
            }
        }
    }
    private String encode (EvaluationTree node){
        if (node != null) {
            return "<evaluationtree description='" + xmlHandler.removeAllSpecialXMLChar(node.getDescription()) 
                    + "' lineno='" + node.getLineNo().toString() +"' nextlineno= '" +
                    node.getNextLineNo().toString() + "' primaryindicator= '" + node.getPrimaryIndicator() + "'>";
        }
        return "";
        
    }
    
    private void validNextLine(WorkFlowLines theLine, WorkFlowLines nextLine,EMCUserData userData){
      
        if(nextLine.getLineNo() < theLine.getLineNo()){
            Logger.getLogger("emc").log(Level.SEVERE,theLine.getLineNo() + " " + 
                    theLine.getDescriptionOfActivity() + ": Next Line Number may not be less than Line number",userData);
        }
        if((theLine.getNextLineNo() > this.stageGateEnd) && (!theLine.getPrimaryIndicator().equals("Stage Gate"))){
             Logger.getLogger("emc").log(Level.SEVERE,theLine.getLineNo() + " " + 
                    theLine.getDescriptionOfActivity() + ": Next Line Number may not point beyond current stage gate",userData);
        }
    
    }
    private void validatePrimary(WorkFlowLines theLine, WorkFlowLines nextLine,EMCUserData userData){
        if((this.convertPIString2enum(nextLine.getPrimaryIndicator()).equals(enumWFPrimaryIndicators.PRIMARY))
            || (this.convertPIString2enum(nextLine.getPrimaryIndicator()).equals(enumWFPrimaryIndicators.STAGEGATE)) ||
            (this.convertPIString2enum(nextLine.getPrimaryIndicator()).equals(enumWFPrimaryIndicators.SECONDARY1))){
            
        }else{
            Logger.getLogger("emc").log(Level.SEVERE,theLine.getLineNo() + " " + 
                    theLine.getDescriptionOfActivity() + ": A Primary may only have a SG,P or S1 as Next Line No.",userData);  
        }
    }
    private void validateSec1(WorkFlowLines theLine, WorkFlowLines nextLine,EMCUserData userData){
        if((this.convertPIString2enum(nextLine.getPrimaryIndicator()).equals(enumWFPrimaryIndicators.PRIMARY))
            || (this.convertPIString2enum(nextLine.getPrimaryIndicator()).equals(enumWFPrimaryIndicators.STAGEGATE)) ||
            (this.convertPIString2enum(nextLine.getPrimaryIndicator()).equals(enumWFPrimaryIndicators.SECONDARY2))){
            
        }else{
            Logger.getLogger("emc").log(Level.SEVERE,theLine.getLineNo() + " " + 
                    theLine.getDescriptionOfActivity() + ": A S1 may only have a SG,P or S2 as Next Line No.",userData);  
        }
    }
    private void validateSec2(WorkFlowLines theLine, WorkFlowLines nextLine,EMCUserData userData){
        if((this.convertPIString2enum(nextLine.getPrimaryIndicator()).equals(enumWFPrimaryIndicators.PRIMARY))
            || (this.convertPIString2enum(nextLine.getPrimaryIndicator()).equals(enumWFPrimaryIndicators.STAGEGATE)) ||
            (this.convertPIString2enum(nextLine.getPrimaryIndicator()).equals(enumWFPrimaryIndicators.SECONDARY3))){
            
        }else{
            Logger.getLogger("emc").log(Level.SEVERE,theLine.getLineNo() + " " + 
                    theLine.getDescriptionOfActivity() + ": A S2 may only have a SG,P or S3 as Next Line No.",userData);  
        }
    }
    private void validateSec3(WorkFlowLines theLine, WorkFlowLines nextLine,EMCUserData userData){
        if((this.convertPIString2enum(nextLine.getPrimaryIndicator()).equals(enumWFPrimaryIndicators.PRIMARY))
            || (this.convertPIString2enum(nextLine.getPrimaryIndicator()).equals(enumWFPrimaryIndicators.STAGEGATE)) ||
            (this.convertPIString2enum(nextLine.getPrimaryIndicator()).equals(enumWFPrimaryIndicators.SECONDARY3))){
            
        }else{
            Logger.getLogger("emc").log(Level.SEVERE,theLine.getLineNo() + " " + 
                    theLine.getDescriptionOfActivity() + ": A S3 may only have a SG,P or S3 as Next Line No.",userData);  
        }
    }
    private void validateSG(WorkFlowLines theLine, WorkFlowLines nextLine,EMCUserData userData){
        if((this.convertPIString2enum(nextLine.getPrimaryIndicator()).equals(enumWFPrimaryIndicators.PRIMARY))
            || (this.convertPIString2enum(nextLine.getPrimaryIndicator()).equals(enumWFPrimaryIndicators.STAGEGATE))){
            
        }else{
            Logger.getLogger("emc").log(Level.SEVERE,theLine.getLineNo() + " " + 
                    theLine.getDescriptionOfActivity() + ": A SG may only have a SG and P as Next Line No.",userData);  
        }
    }
    public void evaluateWFLine(WorkFlowLines theLine, EMCUserData userData){
        if((theLine.getManagerResponsible() == null) || (theLine.getManagerResponsible().length() == 0))
        {
            Logger.getLogger("emc").log(Level.SEVERE,theLine.getLineNo() + " " + 
                    theLine.getDescriptionOfActivity() + ": Manager Responsible must be assigned",userData);
        }
        //test next line entry
        String sqlQuery = "SELECT u FROM WorkFlowLines u WHERE u.companyId = '"
                   + userData.getCompanyId() +"' AND u.workFlowId = '" +
                   theLine.getWorkFlowId() +"' AND u.LineNo = '" + theLine.getNextLineNo() + "'";
        Query qr = em.createQuery(sqlQuery);
        List qrResult = qr.getResultList();
        Iterator it = qrResult.iterator(); 
        if(it.hasNext()){
            WorkFlowLines theLineRead = (WorkFlowLines)it.next();
            switch(this.convertPIString2enum(theLine.getPrimaryIndicator())){
                case STAGEGATE: validNextLine(theLine,theLineRead,userData); 
                                 validateSG(theLine,theLineRead,userData);break;
                case PRIMARY:  validNextLine(theLine,theLineRead,userData);
                               validatePrimary(theLine,theLineRead,userData); break;
                case SECONDARY1: validNextLine(theLine,theLineRead,userData);
                                 validateSec1(theLine,theLineRead,userData);break;
                case SECONDARY2: validNextLine(theLine,theLineRead,userData);
                                 validateSec2(theLine,theLineRead,userData);break;
                case SECONDARY3: validNextLine(theLine,theLineRead,userData);
                                 validateSec3(theLine,theLineRead,userData);break;
                default: break;
            }
            
        }
        else{
            if(theLine.getNextLineNo() != 0){
               Logger.getLogger("emc").log(Level.SEVERE,theLine.getLineNo() + " " + 
                    theLine.getDescriptionOfActivity() + ": Next Line does not exist.",userData);  
            }
        }
        /*if(theLine.isOutputFileRequired()){
            long ref = theLine.getRecordID();
            String refs = String.valueOf(ref);
           String sqlQuery =  "SELECT u FROM BaseDocuRefTable u WHERE u.companyId = '"
                       + userData.getCompanyId() +"' AND u.refTableName = '" + "WorkFlowLines"
                        + "' AND u.refRecId = '" +refs + "'";
            Query qr = em.createQuery(sqlQuery);
            List qrResult = qr.getResultList();
            Iterator it = qrResult.iterator(); 
            if(!it.hasNext()){
               Logger.getLogger("emc").log(Level.WARNING,theLine.getLineNo() + " " + 
                    theLine.getDescriptionOfActivity() + ": Requires output file but none is attached.",userData); 
            }
        }*/
        if(theLine.getDuration() < 0){
            Logger.getLogger("emc").log(Level.SEVERE,theLine.getLineNo() + " " + 
                    theLine.getDescriptionOfActivity() + ": Duration not valid.",userData); 
        }
        if(theLine.getHoursWorkEstimated() <= 0){
            Logger.getLogger("emc").log(Level.SEVERE,theLine.getLineNo() + " " + 
                    theLine.getDescriptionOfActivity() + ": Estimated hours not valid.",userData); 
        }
        
    }
    
}

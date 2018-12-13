/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.workflow.display.jobs;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class jobsMasterDRM extends emcDataRelationManagerUpdate {

    public jobsMasterDRM(emcGenericDataSourceUpdate dataSource,EMCUserData userData) {
        super(dataSource,userData);
    }
    

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
         formUserData = super.generateRelatedFormUserData(formUserData, Index);
         Object obj1 = null;
         String obj1Str = "";
         List x = new ArrayList();
        switch(Index){
             case 0: //activites for this job
             obj1 = super.getFieldValueAt(this.getLastRowAccessed(),"designNo"); //design no
              obj1Str = "";
              if(obj1 != null){
                  obj1Str = obj1.toString();
              }
             
              x.add(0,"SELECT p FROM WorkFlowActivity AS p,WorkFlowJobLines AS t WHERE p.recordID = t.reference AND p.companyId ='"+
                      formUserData.getCompanyId()+ "' AND t.designNo = '"+ obj1Str + "'");
              x.add(1,"SELECT COUNT(*) FROM WorkFlowActivity AS p,WorkFlowJobLines AS t WHERE p.recordID = t.reference AND p.companyId ='"+
                      formUserData.getCompanyId()+ "' AND t.designNo = '"+ obj1Str + "'");
              formUserData.setUserData(x); 
              break;
             case 1: 
                /* obj1 = super.getFieldValueAt(this.getLastRowAccessed(),0); //design no
              obj1Str = "";
              if(obj1 != null){
                  obj1Str = obj1.toString();
              }
             
              x.add(0,"SELECT p FROM BaseDocuRefTable AS p,WorkFlowJobLines AS t WHERE p.refRecId = t.recordID"); // AND p.companyId ='"+
                      //formUserData.getCompanyId()+ "' AND t.designNo = '"+ obj1Str + "'");// AND p.refTableName = '" 
                     // + WorkFlowJobLines.class.getSimpleName() + "'");
              x.add(1,"SELECT COUNT(*) FROM BaseDocuRefTable AS p,WorkFlowJobLines AS t WHERE p.refRecId = t.recordID");
              formUserData.setUserData(x); 
                 break;*/
             default: break;
         }
         return formUserData;
    }
     @Override
    public EMCUserData generateRelatedLinesUserData(EMCUserData linesUserData){
         Object obj1 = super.getFieldValueAt(this.getLastRowAccessed(),this.getHeaderColumnID());
          if(obj1== null) return linesUserData;    
          List x = new ArrayList();
          x.add(0,"SELECT u FROM WorkFlowJobLines u WHERE u.companyId = '"
                   + linesUserData.getCompanyId() +"' AND u.designNo = '" +
                   obj1.toString() + "' ORDER BY u.lineNo");
          x.add(1,"SELECT COUNT(*) FROM WorkFlowJobLines u WHERE u.companyId = '"
                   + linesUserData.getCompanyId() +"' AND u.designNo = '" +
                   obj1.toString() + "'");
         linesUserData.setUserData(x); 
         return linesUserData;
     }
}

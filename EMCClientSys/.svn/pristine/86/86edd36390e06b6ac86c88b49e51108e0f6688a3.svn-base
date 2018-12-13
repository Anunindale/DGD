/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.workflow.display.workflow;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class wfMasterDRM extends emcDataRelationManagerUpdate {

    public wfMasterDRM(emcGenericDataSourceUpdate dataSource,EMCUserData userData) {
        super(dataSource,userData);
    }
   
    @Override
    public EMCUserData generateRelatedLinesUserData(EMCUserData linesUserData){
         Object obj1 = super.getFieldValueAt(this.getLastRowAccessed(),this.getHeaderColumnID());
          if(obj1== null) return linesUserData;    
          List x = new ArrayList();
          x.add(0,"SELECT u FROM WorkFlowLines u WHERE u.companyId = '"
                   + linesUserData.getCompanyId() +"' AND u.workFlowId = '" +
                   obj1.toString() + "' ORDER BY u.LineNo");
          x.add(1,"SELECT COUNT(*) FROM WorkFlowLines u WHERE u.companyId = '"
                   + linesUserData.getCompanyId() +"' AND u.workFlowId = '" +
                   obj1.toString() + "'");
         linesUserData.setUserData(x); 
         return linesUserData;
     }
}

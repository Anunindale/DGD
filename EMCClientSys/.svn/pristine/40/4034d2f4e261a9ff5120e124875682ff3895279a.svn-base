/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.pop.display.purchaseorders.approvalgroups;


import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class PurchaseOrderApprovalGroupsFormDRM extends emcDataRelationManagerUpdate {
    
    /** Creates a new instance of PurchaseOrderApprovalGroupsFormDRM */
    public PurchaseOrderApprovalGroupsFormDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
         super(tableDataSource, userData);
    }
    
    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index){
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        Object groupId;
        Object description;
        List x;
         switch(Index){
             case 0: 
              groupId = super.getFieldValueAt(this.getLastRowAccessed(),"purchaseOrderApprovalGroupId");
              description = super.getFieldValueAt(this.getLastRowAccessed(),"description");
              if(groupId !=null && description != null){
                  x = new ArrayList();
                  x.add(0,"SELECT u FROM POPPurchaseOrderApprovalGroupEmployees u WHERE u.companyId = '"
                           + formUserData.getCompanyId() +"' AND u.purchaseOrderApprovalGroupId = '" +
                           groupId.toString() + "'");
                  x.add(1,"SELECT COUNT(*) FROM POPPurchaseOrderApprovalGroupEmployees u WHERE u.companyId = '"
                           + formUserData.getCompanyId() +"' AND u.purchaseOrderApprovalGroupId = '" +
                           groupId.toString() + "'");
                  x.add(2,description.toString());
                  x.add(3,groupId.toString());
                  formUserData.setUserData(x); 
              }
              break;
             default: break;
         }
         return formUserData;
     }
}
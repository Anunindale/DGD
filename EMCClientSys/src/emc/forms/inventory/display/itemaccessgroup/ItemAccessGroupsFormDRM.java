/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.inventory.display.itemaccessgroup;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class ItemAccessGroupsFormDRM extends emcDataRelationManagerUpdate {
    
    /** Creates a new instance of JournalApprovalGroupsFormDRM */
    public ItemAccessGroupsFormDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
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
              groupId = super.getFieldValueAt(this.getLastRowAccessed(),"itemAccessGroupId");
              description = super.getFieldValueAt(this.getLastRowAccessed(),"description");
              if(groupId !=null && description != null){
                  x = new ArrayList();
                  x.add(0,"SELECT u FROM InventoryItemAccessGroupEmployees u WHERE u.companyId = '"
                           + formUserData.getCompanyId() +"' AND u.itemAccessGroupId = '" +
                           groupId.toString() + "'");
                  x.add(1,"SELECT COUNT(*) FROM InventoryItemAccessGroupEmployees u WHERE u.companyId = '"
                           + formUserData.getCompanyId() +"' AND u.itemAccessGroupId = '" +
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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.workflow.display.jobs;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rico
 */
public class jobsLinesDRM extends emcDataRelationManagerUpdate {

    public jobsLinesDRM(emcGenericDataSourceUpdate dataSource,EMCUserData userData) {
        super(dataSource,userData);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        Object obj1 = null;
        String obj1Str;
         List x = new ArrayList();
        switch(Index){
             case 0: 
              obj1 = super.getFieldValueAt(this.getLastRowAccessed(),"reference");
              obj1Str = "";
              if(obj1 != null){
                  obj1Str = obj1.toString();
              }
             
              x.add(0,"SELECT u FROM WorkFlowActivity u WHERE u.companyId = '"
                       + formUserData.getCompanyId() +"' AND u.recordID = '" +
                       obj1Str + "'");
              x.add(1,"SELECT COUNT(*) FROM WorkFlowActivity u WHERE u.companyId = '"
                       + formUserData.getCompanyId() +"' AND u.recordID = '" +
                       obj1Str + "'");
              formUserData.setUserData(x); 
              break;
             case 1: 
              
                 break;
             default: break;
         }
         return formUserData;
    }
    @Override
    public int getColumnRelation(String columnIndex){
        if(columnIndex.equals("overdueFlag")){
            return -2;
        }else if(columnIndex.equals("cFlag")){
            return -3;
        }else if(columnIndex.equals("documentFlag")){
            return -4;
        }else{
            return super.getColumnRelation(columnIndex);
        }
        
     }
    @Override
    public Object getFieldValueAt(int rowIndex, String columnIndex) {
        if(this.getColumnRelation(columnIndex) == -2){
            Boolean ret =false;
            Date req = (Date)this.getFieldValueAt(rowIndex, "requiredCompletionDate");
            Date closed = (Date)this.getFieldValueAt(rowIndex, "closedDate");
            Date completed = (Date)this.getFieldValueAt(rowIndex, "completionDate");
            if(req != null){
                req = Functions.DateAddSub(req, 1); //add 1 day
                if(req != null && closed == null && completed == null){
                    if(req.before(Functions.nowDate())){
                        ret = true;

                    }
                }
             }
             return ret;
        }
        if(this.getColumnRelation(columnIndex) == -3){
            Boolean ret =false;
            Date req = (Date)this.getFieldValueAt(rowIndex, "closedDate");
            //Date req2 = (Date)this.getFieldValueAt(rowIndex, "completionDate");
            if(req != null){
                ret = true;
            }
             return ret;
        }
        if(this.getColumnRelation(columnIndex) == -4){
            Boolean ret =false;
            String refRecId = emc.app.util.utilFunctions.getTheRecordId(this.getRowCache(rowIndex));
            String tableName = this.getRowCache(rowIndex).getClass().getSimpleName();
            EMCCommandClass cmd = new EMCCommandClass();
            cmd.setCommandId(EMCCommands.SERVER_GENERAL_COMMAND.getId());
            cmd.setModuleNumber(enumEMCModules.BASE.getId());
            cmd.setMethodId(emc.methods.base.ServerBaseMethods.ISDOCUMENTATTACHED.toString());
            List toSend = new ArrayList();
            toSend.add(tableName);
            toSend.add(refRecId);
            List result = EMCWSManager.executeGenericWS(cmd, toSend, this.getUserData());
            ret = (Boolean)result.get(1);
            return ret;
        }
        return super.getFieldValueAt(rowIndex, columnIndex);
    }
}

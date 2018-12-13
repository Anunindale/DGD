/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.workflow.display.activities;

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
public class activitiesDRM extends emcDataRelationManagerUpdate {
    public activitiesDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData){
        super(tableDataSource,userData);
    }
    
    @Override
    public int getColumnRelation(String columnIndex){
       if(columnIndex.equals("overdueFlag")){
           return -2;
       }else if(columnIndex.equals("clFlag")){
           return -3;
       }else if(columnIndex.equals("documentFlag")){
           return -4;
       } else if(columnIndex.equals("emp")) {
           return -5;
       } else if(columnIndex.equals("man")) {
           return -6;
       }
       else{
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
            Date req2 = (Date)this.getFieldValueAt(rowIndex, "completionDate");
            if(req != null || req2 != null){
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
        if (this.getColumnRelation(columnIndex) == -5) {
            String ret = "";
            EMCCommandClass cmd = new EMCCommandClass();
            cmd.setCommandId(EMCCommands.SERVER_GENERAL_COMMAND.getId());
            cmd.setModuleNumber(enumEMCModules.BASE.getId());
            cmd.setMethodId(emc.methods.base.ServerBaseMethods.FIND_EMPLOYEENAMEANDSURNAME.toString());
            List toSend = new ArrayList();
            Object objectToSend = super.getFieldValueAt(rowIndex, "employeeNumber");
            if (objectToSend == null || objectToSend.toString().equals("")) {
                ret = "";
            } else {
                toSend.add(objectToSend);
                List result = EMCWSManager.executeGenericWS(cmd, toSend, this.getUserData());
                ret = result.get(1).toString();
            }
            return ret;
        }
        if (this.getColumnRelation(columnIndex) == -6) {
            String ret = "";
            EMCCommandClass cmd = new EMCCommandClass();
            cmd.setCommandId(EMCCommands.SERVER_GENERAL_COMMAND.getId());
            cmd.setModuleNumber(enumEMCModules.BASE.getId());
            cmd.setMethodId(emc.methods.base.ServerBaseMethods.FIND_EMPLOYEENAMEANDSURNAME.toString());
            List toSend = new ArrayList();
            Object objectToSend = super.getFieldValueAt(rowIndex, "managerResponsible");
            if (objectToSend == null || objectToSend.toString().equals("")) {
                ret = "";
            } else {
                toSend.add(objectToSend);
                List result = EMCWSManager.executeGenericWS(cmd, toSend, this.getUserData());
                ret = result.get(1).toString();
            }
            return ret;
        }
        return super.getFieldValueAt(rowIndex, columnIndex);
    }
    
}

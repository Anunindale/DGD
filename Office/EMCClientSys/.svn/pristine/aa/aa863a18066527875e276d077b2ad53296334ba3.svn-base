/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.workflow.display.workflow;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class WFLinesDRM extends emcDataRelationManagerUpdate {

    public WFLinesDRM(emcGenericDataSourceUpdate dataSource,EMCUserData userData) {
        super(dataSource,userData);
    }

    @Override
    public int getColumnRelation(String columnIndex){
        if(columnIndex.equals("documentFlag")){
            return -2;
        }else{
            return super.getColumnRelation(columnIndex);
        }
        
     }
    @Override
    public Object getFieldValueAt(int rowIndex, String columnIndex) {
        
        if(this.getColumnRelation(columnIndex) == -2){
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

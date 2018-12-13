/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.trec.display.chemicals.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import emc.methods.EMCMethodEnum;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class TRECEncDRM extends emcDataRelationManagerUpdate{
    private EMCMethodEnum method;

    public TRECEncDRM(emcGenericDataSourceUpdate tableDataSource,EMCMethodEnum method,EMCUserData userData){
        super(tableDataSource,userData);
        this.method = method;
    }
    //Done to save the encrypted field 
    @Override
    public void updatePersist(int rowIndex) {
        if(rowIndex == -1) rowIndex = this.getLastRowAccessed();
        super.updatePersist(rowIndex);
        
        Object row = this.getRowCache(rowIndex);
        if(this.getLastUpdateStatus()){
           if(((EMCEntityClass)(row)).getRecordID() == 0){
                   ((EMCEntityClass)(row)).setRecordID((Long)this.getFieldValueAt(rowIndex, "recordID"));
           }
           doSpecialEncryptionSaving(row);
        }
    }
    private void doSpecialEncryptionSaving(Object row){
        EMCCommandClass cmd = new EMCCommandClass(method);
        List toSend = new ArrayList();
        toSend.add(row);
        EMCWSManager.executeGenericWS(cmd,toSend ,this.getUserData());
    }
    @Override
    public boolean persistOnClosing() {
         Object row = this.getRowCache(-1);
        boolean ret = super.persistOnClosing();
        if(ret){
            if(((EMCEntityClass)(row)).getRecordID() == 0){
                   ((EMCEntityClass)(row)).setRecordID((Long)this.getFieldValueAt(this.getLastRowAccessed(), "recordID"));
            }
            doSpecialEncryptionSaving(row);
        }
        return ret;
    }
   
    
    
}

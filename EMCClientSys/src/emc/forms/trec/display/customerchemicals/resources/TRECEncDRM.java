/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.customerchemicals.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import emc.methods.EMCMethodEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rico
 */
public class TRECEncDRM extends emcDataRelationManagerUpdate {

    private EMCMethodEnum method;

    public TRECEncDRM(emcGenericDataSourceUpdate tableDataSource, EMCMethodEnum method, EMCUserData userData) {
        super(tableDataSource, userData);
        this.method = method;
    }
    //Done to save the encrypted field 

    @Override
    public void updatePersist(int rowIndex) {
        if (rowIndex == -1) {
            rowIndex = this.getLastRowAccessed();
        }
        Object row = this.getRowCache(rowIndex);
        super.updatePersist(rowIndex);
        if (this.getLastUpdateStatus()) {
            if (((EMCEntityClass) (row)).getRecordID() == 0) {
                ((EMCEntityClass) (row)).setRecordID((Long) this.getFieldValueAt(rowIndex, "recordID"));
            }
            doSpecialEncryptionSaving(row);
        }
    }

    private void doSpecialEncryptionSaving(Object row) {
        EMCCommandClass cmd = new EMCCommandClass(method);
        List toSend = new ArrayList();
        toSend.add(row);
        EMCWSManager.executeGenericWS(cmd, toSend, this.getUserData());
    }

    @Override
    public boolean persistOnClosing() {
        Object row = this.getRowCache(-1);
        boolean ret = super.persistOnClosing();
        if (ret) {
            if (((EMCEntityClass) (row)).getRecordID() == 0) {
                ((EMCEntityClass) (row)).setRecordID((Long) this.getFieldValueAt(this.getLastRowAccessed(), "recordID"));
            }
            doSpecialEncryptionSaving(row);
        }
        return ret;
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
        Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to add records to this table.", getUserData());
    }
}

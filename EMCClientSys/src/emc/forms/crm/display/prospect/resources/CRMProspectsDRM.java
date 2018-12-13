/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.crm.display.prospect.resources;

import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.crm.CRMProspect;
import emc.entity.sop.SOPCustomers;
import emc.entity.workflow.WorkFlowActivity;
import emc.entity.workflow.WorkFlowJobLines;
import emc.enums.crm.CRMProspectStatus;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.base.ServerBaseMethods;
import emc.methods.sop.ServerSOPMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class CRMProspectsDRM extends emcDataRelationManagerUpdate {

    private emcJButton btnEmail;
    private emcJButton btnSMS;
    private emcJButton btnClose;
    private emcJButton btnRestore;

    public CRMProspectsDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public void doRelation(int rowIndex) {
        super.doRelation(rowIndex);
        if (btnEmail != null) {
            btnEmail.setEnabled(!Functions.checkBlank(getLastFieldValueAt("email")));
        }
        if (btnSMS != null) {
            btnSMS.setEnabled(!Functions.checkBlank(getLastFieldValueAt("cellNum")));
        }
        if(btnRestore != null){
            btnRestore.setEnabled(getLastFieldValueAt("prosStatus").equals(CRMProspectStatus.CLOSED.toString()));
        }
        if(btnClose != null){
            btnClose.setEnabled(getLastFieldValueAt("prosStatus").equals(CRMProspectStatus.ACTIVE.toString()));
        }
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        EMCUserData userData = super.generateRelatedFormUserData(formUserData, Index);
        EMCQuery query;
        List x;
        EMCCommandClass cmd;
        switch (Index) {
            case 0:
                query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class.getName());
                query.addTableAnd(WorkFlowJobLines.class.getName(), "recordID", WorkFlowActivity.class.getName(), "reference");
                query.addAnd("designNo", getLastFieldValueAt("prospectId"), WorkFlowJobLines.class.getName());
                x = new ArrayList();
                x.add(query);
                userData.setUserData(x);
                break;
          
            case 2:
                cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.SOP.getId(), ServerSOPMethods.POPULATE_CUSTOMER_FROM_PROSPECT.toString());
                x = new ArrayList();
                x.add(this.getRowCache(this.getLastRowAccessed()));
                SOPCustomers customer = (SOPCustomers) EMCWSManager.executeGenericWS(cmd, x, userData).get(1);
                query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class.getName());
                query.addAnd("recordID", 0);
                x = new ArrayList();
                x.add(query);
                x.add(customer);
                userData.setUserData(x);
                break;
            case 13:
                query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
                query.addAnd("sourceRecordId", getLastFieldValueAt("recordID"));
                List udList = new ArrayList();
                udList.add(0, query);
                udList.add(1, "");
                //Find employee Id
                String employeeNumber = userData.getUserName();
                cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.FIND_EMPLOYEE.toString());
                List toSend = new ArrayList();
                toSend.add(employeeNumber);
                List retData = EMCWSManager.executeGenericWS(cmd, toSend, userData);
                if (retData.size() > 0) {
                    employeeNumber = retData.get(1).toString();
                }
                udList.add(2, employeeNumber);
                udList.add(3, CRMProspect.class.getName());
                udList.add(4, getLastFieldValueAt("recordID"));
                userData.setUserData(udList);
                break;
        }

        return userData;
    }


    @Override
    public void deleteRowCache(int rowIndex) {
        //this due to activities existing and tasks. Clear that up on delete and this can be enabled again.
        Logger.getLogger("emc").log(Level.SEVERE, "May not delete a prospect, use Deactivate.");
    }

    public void setBtnEmail(emcJButton btnEmail) {
        this.btnEmail = btnEmail;
    }

    public void setBtnSMS(emcJButton btnSMS) {
        this.btnSMS = btnSMS;
    }

    /**
     * @param close the close to set
     */
    public void setBtnClose(emcJButton close) {
        this.btnClose = close;
    }

    /**
     * @param restore the restore to set
     */
    public void setBtnRestore(emcJButton restore) {
        this.btnRestore = restore;
    }
}

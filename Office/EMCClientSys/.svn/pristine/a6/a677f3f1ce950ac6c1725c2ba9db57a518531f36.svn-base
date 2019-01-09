/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.purchaseorders.resources;

import emc.app.components.emcMenuButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.pop.POPPurchaseOrderApprovalGroups;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class ApprovalReqDisplay extends emcMenuButton {

    private boolean all = true;
    private String label;
    private emcDataRelationManagerUpdate drm;
    private String employeeNumber;

    public ApprovalReqDisplay(String label, emcDataRelationManagerUpdate drm) {
        super(label);
        this.label = label;
        this.drm = drm;
    }

    private void getEmployeeNumber() {
        //The employee number of the current user
        employeeNumber = "";

        //Copy of userData
        EMCUserData copyUD = drm.getUserData().copyUserData();

        //Gets the employee number of the current user
        EMCCommandClass cmd = new EMCCommandClass();
        cmd.setCommandId(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId());
        cmd.setModuleNumber(enumEMCModules.BASE.getId());
        cmd.setMethodId(emc.methods.base.ServerBaseMethods.FIND_EMPLOYEE.toString());
        List toSend = new ArrayList();
        toSend.add(copyUD.getUserName());

        List retData = EMCWSManager.executeGenericWS(cmd, toSend, copyUD);
        if (retData.size() > 0) {
            this.employeeNumber = retData.get(1).toString();
        }
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);
        if (employeeNumber == null) {
            getEmployeeNumber();
        }
        if (all) {
            all = false;
            this.setText("All PO's");
            //create query
            EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.pop.POPPurchaseOrderMaster.class.getName());
            qu.addTableAnd(emc.entity.pop.POPPurchaseOrderApprovalGroups.class.getName(), "approvalGrp", POPPurchaseOrderMaster.class.getName(), "purchaseOrderApprovalGroupId");
            qu.addTableAnd(emc.entity.pop.POPPurchaseOrderApprovalGroupEmployees.class.getName(), "purchaseOrderApprovalGroupId", POPPurchaseOrderApprovalGroups.class.getName(), "purchaseOrderApprovalGroupId");
            qu.addAnd("employeeId", employeeNumber, emc.entity.pop.POPPurchaseOrderApprovalGroupEmployees.class.getName());
            qu.addAnd("companyId", drm.getUserData().getCompanyId());
            qu.addAnd("companyId", drm.getUserData().getCompanyId(), emc.entity.pop.POPPurchaseOrderApprovalGroups.class.getName());
            qu.addAnd("companyId", drm.getUserData().getCompanyId(), emc.entity.pop.POPPurchaseOrderApprovalGroupEmployees.class.getName());
            qu.addAnd("status", emc.enums.pop.purchaseorders.PurchaseOrderStatus.REQUISITION.toString());
            qu.addOrderBy("purchaseOrderId");
            //set query
            drm.getUserData().setUserData(0, qu.toString());
            drm.getUserData().setUserData(1, qu.getCountQuery());
            drm.setUserData(drm.getUserData());

        } else {
            all = true;
            this.setText(label);
            //create query
            EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.pop.POPPurchaseOrderMaster.class.getName());
            qu.addAnd("companyId", drm.getUserData().getCompanyId());
            qu.addOrderBy("purchaseOrderId");
            //set query
            drm.getUserData().setUserData(0, qu.toString());
            drm.getUserData().setUserData(1, qu.getCountQuery());
            drm.setUserData(drm.getUserData());
        }
    }
}

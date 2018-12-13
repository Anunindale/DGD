/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.timedoperationmanager;

import emc.entity.base.timedoperations.BaseTimedOperations;
import emc.enums.base.timedoperations.EnumTimedOperationStatus;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.server.timedoperationmanager.base.BaseTimedOperationsManagerLocal;
import emc.server.timedoperationmanager.inventory.InventoryTimedOperationsManagerLocal;
import emc.server.timedoperationmanager.sop.SOPTimedOperationManagerLocal;
import emc.server.timedoperationmanager.workflow.WorkFlowTimedOperationsManagerLocal;
import emc.timedoperations.gl.GLConsolidationTimedOperationLocal;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timer;

/**
 *
 * @author rico
 */
@Stateless
public class EMCModuleTimedOperationManagerBean extends EMCBusinessBean implements EMCModuleTimedOperationManagerLocal {

    @EJB
    private BaseTimedOperationsManagerLocal base;
    @EJB
    private GLConsolidationTimedOperationLocal gl;
    @EJB
    private InventoryTimedOperationsManagerLocal inventory;
    @EJB
    private WorkFlowTimedOperationsManagerLocal workFlow;
    @EJB
    private SOPTimedOperationManagerLocal sop;

    public EMCModuleTimedOperationManagerBean() {
    }

    @Override
    public void startAllTimers(EMCUserData userData) {
        EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, BaseTimedOperations.class.getName());
        qu.addAnd("currentStatus", EnumTimedOperationStatus.STARTED.toString());
        List<BaseTimedOperations> operations = util.executeGeneralSelectQuery(qu);
        for (BaseTimedOperations op : operations) {
            startTimer(op, userData);
        }
        this.logMessage(Level.INFO, "Timed Operations Started.", userData);
    }

    @Override
    public Timer startTimer(BaseTimedOperations op, EMCUserData userData) {
        Timer t;
        if (enumEMCModules.fromString(op.getTheModule()).getId() == enumEMCModules.BASE.getId()) {
            t = base.startTimer(op, userData);
        } else if (enumEMCModules.fromString(op.getTheModule()).getId() == enumEMCModules.GENERAL_LEDGER.getId()) {
            t = gl.startTimer(op, userData);
        } else if (enumEMCModules.fromString(op.getTheModule()).getId() == enumEMCModules.INVENTORY.getId()) {
            t = inventory.startTimer(op, userData);
        } else if (enumEMCModules.fromString(op.getTheModule()).getId() == enumEMCModules.SALESORDERPROS.getId()
                || enumEMCModules.fromString(op.getTheModule()).getId() == enumEMCModules.SOP.getId()) {
            t = sop.startTimer(op, userData);
        } else if (enumEMCModules.fromString(op.getTheModule()).getId() == enumEMCModules.WORKFLOW.getId()) {
            t = workFlow.startTimer(op, userData);
        } else {
            this.logMessage(Level.SEVERE, "Failed to find an implementation for module: " + op.getTheModule() + " during execution of loading of timed operations.", userData);
            t = null;
        }

        return t;
    }

    @Override
    public Timer stopTimer(BaseTimedOperations op, EMCUserData userData) {
        Timer t;
        if (enumEMCModules.fromString(op.getTheModule()).getId() == enumEMCModules.BASE.getId()) {
            t = base.stopTimer(op, userData);
        } else if (enumEMCModules.fromString(op.getTheModule()).getId() == enumEMCModules.GENERAL_LEDGER.getId()) {
            t = gl.stopTimer(op, userData);
        } else if (enumEMCModules.fromString(op.getTheModule()).getId() == enumEMCModules.INVENTORY.getId()) {
            t = inventory.stopTimer(op, userData);
        }  else if (enumEMCModules.fromString(op.getTheModule()).getId() == enumEMCModules.SALESORDERPROS.getId()
                || enumEMCModules.fromString(op.getTheModule()).getId() == enumEMCModules.SOP.getId()) {
            t = sop.stopTimer(op, userData);
        } else if (enumEMCModules.fromString(op.getTheModule()).getId() == enumEMCModules.WORKFLOW.getId()) {
            t = workFlow.stopTimer(op, userData);
        } else {
            this.logMessage(Level.SEVERE, "Failed to find an implementation for module: " + op.getTheModule() + " during execution of loading of timed operations.", userData);
            t = null;
        }
        return t;
    }
}

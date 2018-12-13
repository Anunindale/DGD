/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.workflow.display.workflow.resources;

import emc.app.components.emcMenuButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class allDocumentsButton extends emcMenuButton {

    private emcDataRelationManagerUpdate masterRel;

    public allDocumentsButton(String label, emcDataRelationManagerUpdate masterRel) {
        super(label, null, null, 0, false);
        this.masterRel = masterRel;

    }

    @Override
    public void doActionPerformed(ActionEvent evt) {

        emc.entity.workflow.WorkFlowMaster master =
                (emc.entity.workflow.WorkFlowMaster) masterRel.getRowCache(masterRel.getLastRowAccessed());

        EMCCommandClass cmd = new EMCCommandClass();
        cmd.setCommandId(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId());
        cmd.setModuleNumber(enumEMCModules.WORKFLOW.getId());
        cmd.setMethodId(emc.methods.workflow.ServerWorkFlowMethods.ALL_DOCUMENTS_ATTATCHED_WFMASTER.toString());
        List toSend = new ArrayList();
        toSend.add(master);
        EMCWSManager.executeGenericWS(cmd, toSend, masterRel.getUserData());

    }
}

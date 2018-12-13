/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.workflow.display.workflow.resources;

import emc.app.components.emcDialogue;
import emc.app.components.emcMenuButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.workflow.WorkFlowMaster;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCMenuItem;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class copyWFButton extends emcMenuButton {

    private emcDataRelationManagerUpdate manager;

    public copyWFButton(String label, EMCMenuItem buttonitem,
                        BaseInternalFrame form, int relatedFormIndex, emcDataRelationManagerUpdate manager) {
        super(label, buttonitem, form, relatedFormIndex, false);
        this.manager = manager;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        emcDialogue dialog = new emcDialogue(this, "New Work Flow Id", "ID of the new WF?");
        String wfId = dialog.getInputDialogueResult();
        if ((wfId != null) && (wfId.length() > 0)) {
            EMCCommandClass cmd = new EMCCommandClass();
            cmd.setCommandId(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId());
            cmd.setModuleNumber(enumEMCModules.WORKFLOW.getId());
            cmd.setMethodId(emc.methods.workflow.ServerWorkFlowMethods.COPY_WORKFLOW.toString());
            List toSend = new ArrayList();
            toSend.add((WorkFlowMaster) manager.getRowCache(manager.getLastRowAccessed()));
            toSend.add(wfId);
            EMCWSManager.executeGenericWS(cmd, toSend, manager.getUserData());
            manager.refreshData();
        }
    }
}

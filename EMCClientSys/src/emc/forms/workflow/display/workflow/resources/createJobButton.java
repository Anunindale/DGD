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
import emc.entity.workflow.WorkFlowJobMaster;
import emc.entity.workflow.WorkFlowMaster;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCMenuItem;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rico
 */
public class createJobButton extends emcMenuButton {

    private emcDataRelationManagerUpdate manager;
    private BaseInternalFrame form;
    private EMCMenuItem buttonitem;

    public createJobButton(String label, EMCMenuItem buttonitem,
            BaseInternalFrame form, int relatedFormIndex, emcDataRelationManagerUpdate manager) {
        super(label, buttonitem, form, relatedFormIndex, false);
        this.manager = manager;
        this.form = form;
        this.buttonitem = buttonitem;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        emcDialogue dialog = new emcDialogue(this, "New Task Id", "ID of the created Task?");
        String wfId = dialog.getInputDialogueResult();
        if ((wfId != null) && (wfId.length() > 0)) {
            EMCCommandClass cmd = new EMCCommandClass();
            cmd.setCommandId(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId());
            cmd.setModuleNumber(enumEMCModules.WORKFLOW.getId());
            cmd.setMethodId(emc.methods.workflow.ServerWorkFlowMethods.CREATE_TASK.toString());
            List toSend = new ArrayList();
            toSend.add((WorkFlowMaster) manager.getRowCache(manager.getLastRowAccessed()));
            toSend.add(wfId);
            toSend = EMCWSManager.executeGenericWS(cmd, toSend, manager.getUserData());

            if (toSend.size() > 1 && toSend.get(1) instanceof WorkFlowJobMaster) {
                Logger.getLogger("emc").log(Level.INFO, "Successfully created Task", manager.getUserData());

            }
            if (buttonitem != null) {
                form.getDeskTop().createAndAdd(buttonitem, -1, -1, null, null, 0);
            }
        }
    }
}

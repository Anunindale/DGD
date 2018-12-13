/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.workflow.display.jobs.resources;

import emc.app.components.emcDialogue;
import emc.app.components.emcMenuButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rico
 */
public class redoRangeButton extends emcMenuButton {

    private emcDataRelationManagerUpdate dataRelation;
    private emcDataRelationManagerUpdate headerRelation;

    public redoRangeButton(String label, emcDataRelationManagerUpdate dataRelation,
                           emcDataRelationManagerUpdate headerRelation) {
        super(label, null, null, 0, false);
        this.dataRelation = dataRelation;

        this.headerRelation = headerRelation;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {

        emc.entity.workflow.WorkFlowJobLines jobline =
                (emc.entity.workflow.WorkFlowJobLines) dataRelation.getRowCache(dataRelation.getLastRowAccessed());
        if (jobline.getClosedDate() == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Can only redo on a Closed JobLine", dataRelation.getUserData());
        } else {
            emcDialogue dialog = new emcDialogue(this, "Enter Data", "Please enter the line numbers you want to repeat \n eg: 10-100,200");
            String wfId = dialog.getInputDialogueResult();

            if ((wfId != null) && (wfId.length() > 0)) {
                boolean doFlag = true;
                for (int j = 0; j < wfId.length(); j++) {
                    char x = wfId.charAt(j);
                    String y = "" + x;
                    if (!Character.isDigit(x) && !y.equals(".") && !y.equals("-") && !y.equals(",")) {
                        doFlag = false;
                    }
                }
                if (doFlag) {
                    EMCCommandClass cmd = new EMCCommandClass();
                    cmd.setCommandId(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId());
                    cmd.setModuleNumber(enumEMCModules.WORKFLOW.getId());
                    cmd.setMethodId(emc.methods.workflow.ServerWorkFlowMethods.REDO_WFJOBLINES.toString());
                    List toSend = new ArrayList();
                    toSend.add(wfId);
                    toSend.add(String.valueOf(jobline.getLineNo()));
                    toSend.add(jobline.getDesignNo());

                    List result = EMCWSManager.executeGenericWS(cmd, toSend, dataRelation.getUserData());

                    headerRelation.tableRowChanged(headerRelation.getLastRowAccessed());
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Range entered may only contain digits, '-,.'", dataRelation.getUserData());
                }
            }

        }


    }
}

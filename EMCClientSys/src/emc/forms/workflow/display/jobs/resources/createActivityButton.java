/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.workflow.display.jobs.resources;

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
public class createActivityButton extends emcMenuButton {

    private emcDataRelationManagerUpdate dataRelation;
    private emcDataRelationManagerUpdate headerRelation;

    public createActivityButton(String label, emcDataRelationManagerUpdate dataRelation,
                                emcDataRelationManagerUpdate headerRelation) {
        super(label, null, null, 0, false);
        this.dataRelation = dataRelation;
        this.headerRelation = headerRelation;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {

        emc.entity.workflow.WorkFlowJobLines jobline =
                (emc.entity.workflow.WorkFlowJobLines) dataRelation.getRowCache(dataRelation.getLastRowAccessed());
        if (jobline.getReference() != null) {

            Logger.getLogger("emc").log(Level.SEVERE, "Activity exists", dataRelation.getUserData());
        } else {


            EMCCommandClass cmd = new EMCCommandClass();
            cmd.setCommandId(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId());
            cmd.setModuleNumber(enumEMCModules.WORKFLOW.getId());
            cmd.setMethodId(emc.methods.workflow.ServerWorkFlowMethods.COPY_WFJOBLINE_ACTIVITY_PERSIST.toString());
            List toSend = new ArrayList();
            toSend.add(jobline);
            EMCWSManager.executeGenericWS(cmd, toSend, dataRelation.getUserData());

            headerRelation.tableRowChanged(headerRelation.getLastRowAccessed());

        }


    }
}

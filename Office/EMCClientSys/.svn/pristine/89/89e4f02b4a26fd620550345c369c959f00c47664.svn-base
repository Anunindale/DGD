/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.itemdimensionsetup.resources;

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
public class RemoveInActive extends emcMenuButton {

    private emcDataRelationManagerUpdate dataRelation;
    private String className;

    public RemoveInActive(emcDataRelationManagerUpdate drm, String className) {
        super("Rem. Inactive", drm);
        dataRelation = drm;
        this.className = className;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);
        EMCCommandClass cmd = new EMCCommandClass();
        cmd.setCommandId(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId());
        cmd.setModuleNumber(enumEMCModules.INVENTORY.getId());
        cmd.setMethodId(emc.methods.inventory.ServerInventoryMethods.REMOVE_INACTIVE.toString());
        List toSend = new ArrayList();
        toSend.add(className);
        EMCWSManager.executeGenericWS(cmd, toSend, dataRelation.getUserData());
        dataRelation.refreshData();
    }
}

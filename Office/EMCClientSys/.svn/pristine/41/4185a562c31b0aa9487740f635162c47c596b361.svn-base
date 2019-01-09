/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.journals.resources;

import emc.app.components.emcMenuButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.inventory.journals.InventoryJournalMaster;
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
public class ValidateButton extends emcMenuButton {

    private emcDataRelationManagerUpdate drm;

    public ValidateButton(emcDataRelationManagerUpdate drm) {
        super("Validate");
        this.drm = drm;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);
        //prompt user
        InventoryJournalMaster jMast = (InventoryJournalMaster) drm.getRowCache(-1);
        EMCCommandClass cmd = new EMCCommandClass(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId(), emc.enums.modules.enumEMCModules.INVENTORY.getId(), emc.methods.inventory.ServerInventoryMethods.VALIDATE_INVENTORYJOURNAL.toString());
        List toSend = new ArrayList();
        toSend.add(jMast);
        toSend = EMCWSManager.executeGenericWS(cmd, toSend, drm.getUserData());
        if (toSend.size() > 1 && (Boolean) toSend.get(1)) {
            Logger.getLogger("emc").log(Level.INFO, "Journal Validated", drm.getUserData());
        }


    }
}

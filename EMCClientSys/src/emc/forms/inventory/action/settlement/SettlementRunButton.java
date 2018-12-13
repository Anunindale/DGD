/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.action.settlement;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCEntityClass;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author rico
 */
public class SettlementRunButton extends emcJButton {

    emcDataRelationManagerUpdate drm;

    /** Creates a new instance of FirmButton
     *
     *  @param plannedWODRM The data relation manager of the Planned Works Orders form.
     */
    public SettlementRunButton(emcDataRelationManagerUpdate drm) {
        super("Run");
        this.drm = drm;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        int lastRowAccessed = drm.getLastRowAccessed();
        EMCEntityClass entity = (EMCEntityClass) drm.getRowCache(lastRowAccessed);

        if (entity.getRecordID() != 0) {
            if (EMCDialogFactory.createQuestionDialog(drm.getTheForm(), "Run Stock Settlement?", "Are you sure you want to run the stock settlement routine?") == JOptionPane.YES_OPTION) {
                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.RUN_STOCKSETTLEMENT.toString());

                List toSend = new ArrayList();
                toSend.add(entity);

                EMCWSManager.executeGenericWS(cmd, toSend, drm.getUserData());

                drm.refreshRecord(lastRowAccessed);

                cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.SET_CLOSE_DATE_NULL.toString());
                EMCWSManager.executeGenericWS(cmd, new ArrayList<Object>(), drm.getUserData());
            }
        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "Cannot run close on unsaved settlement record.", drm.getUserData());
        }
    }
}

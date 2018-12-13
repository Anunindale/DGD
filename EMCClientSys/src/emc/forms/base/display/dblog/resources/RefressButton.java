/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.dblog.resources;

import emc.app.components.emcJButton;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.base.ServerBaseMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class RefressButton extends emcJButton {

    EMCUserData userData;

    public RefressButton(EMCUserData userData) {
        super("Commit");
        this.userData = userData;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.REFRESS.toString());
        boolean suc = (Boolean) EMCWSManager.executeGenericWS(cmd, new ArrayList(), userData).get(1);
        if (suc) {
            utilFunctions.logMessage(Level.INFO, "EMC DB Logger refreshed.", userData);
        } else {
            utilFunctions.logMessage(Level.INFO, "Refresh failed, please try again later.", userData);
        }
    }
}

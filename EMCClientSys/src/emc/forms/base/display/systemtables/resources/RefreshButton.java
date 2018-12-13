/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.systemtables.resources;

import emc.app.components.emcMenuButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCMenuItem;
import emc.methods.base.ServerBaseMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rico
 */
public class RefreshButton extends emcMenuButton {

    private BaseInternalFrame theFrame;

    public RefreshButton(String label, EMCMenuItem buttonitem, BaseInternalFrame form, int relatedFormIndex, boolean lines) {
        super(label, buttonitem, form, relatedFormIndex, lines);
        theFrame = form;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.REFRESS_BASESYSTEMTABLE.toString());
        EMCWSManager.executeGenericWS(cmd, new ArrayList(), theFrame.getUserData());
        Logger.getLogger("emc").log(Level.INFO, "Refreshed system tables on server.", theFrame.getUserData());
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.users.resources;

import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.base.ServerBaseMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Name         : CopyPermissionsDialogOKButton
 *
 * @Date         : 24 Aug 2009
 *
 * @Description  : This button makes a server call, attempting to copy permissions from one user to another. 
 *
 * @author       : riaan
 */
public class CopyPermissionsDialogOKButton extends emcJButton {

    private CopyPermissionsDialog dialog;

    /** Creates a new instance of CopyPermissionsDialogOKButton. */
    public CopyPermissionsDialogOKButton(CopyPermissionsDialog dialog) {
        super("Copy Permissions");
        this.dialog = dialog;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        EMCUserData userData = dialog.usersForm.getUserData().copyUserData();
        String selectedUser = (String) dialog.lkpToUser.getValue();

        if (Functions.checkBlank(selectedUser)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Please select a user to copy permissions to.", userData);
        } else {
            EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.COPY_USER_PERMISSIONS.toString());

            List toSend = new ArrayList();
            //From user
            emcDataRelationManagerUpdate drm = (emcDataRelationManagerUpdate) dialog.usersForm.getDataManager();
            toSend.add(drm.getLastFieldValueAt("userId"));
            //To user
            toSend.add(selectedUser);

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

            if (toSend.size() > 1 && (Boolean) toSend.get(1)) {
                Logger.getLogger("emc").log(Level.INFO, "Permissions copied succesfully.", userData);
                dialog.dispose();
                //Refresh data
                ((emcDataRelationManagerUpdate) dialog.usersForm.getDataManager()).refreshData();
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to copy permissions.", userData);
            }
        }
    }
}

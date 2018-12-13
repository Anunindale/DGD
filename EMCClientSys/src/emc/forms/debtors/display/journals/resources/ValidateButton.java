/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.journals.resources;

import emc.app.components.emcMenuButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Riaan
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
        DebtorsJournalMaster jMast = (DebtorsJournalMaster) drm.getRowCache(-1);
        EMCCommandClass cmd = new EMCCommandClass(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.VALIDATE_DEBTORS_JOURNAL.toString());
        List toSend = new ArrayList();
        toSend.add(jMast);
        toSend = EMCWSManager.executeGenericWS(cmd, toSend, drm.getUserData());
        if (toSend.size() > 1 && (Boolean) toSend.get(1)) {
            Logger.getLogger("emc").log(Level.INFO, "Journal validated successfully.", drm.getUserData());
        }
    }
}

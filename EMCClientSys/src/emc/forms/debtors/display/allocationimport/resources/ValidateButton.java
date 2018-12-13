/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.allocationimport.resources;

import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.forms.debtors.display.allocationimport.DebtorsAllocationImportForm;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riaan
 */
public class ValidateButton extends emcJButton {

    private emcDataRelationManagerUpdate importDRM;

    /** Creates a new instance of ValidateButton. */
    public ValidateButton(emcDataRelationManagerUpdate importDRM) {
        super("Validate");
        this.importDRM = importDRM;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        EMCUserData userData = importDRM.getUserData();

        List<String> lines = ((DebtorsAllocationImportForm) importDRM.getTheForm()).readData();

        if (lines == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to read import data from file.", userData);
            return;
        }

        EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.VALIDATE_ALLOCATION_IMPORT);

        List toSend = new ArrayList();
        toSend.add(importDRM.getRowCache(importDRM.getLastRowAccessed()));
        toSend.add(lines);

        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

        if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
            utilFunctions.logMessage(Level.INFO, "Successfully validated allocation.", userData);
            importDRM.refreshRecord(importDRM.getLastRowAccessed());
        } else {
            utilFunctions.logMessage(Level.SEVERE, "Failed to validate allocation. ", userData);
        }
    }
}
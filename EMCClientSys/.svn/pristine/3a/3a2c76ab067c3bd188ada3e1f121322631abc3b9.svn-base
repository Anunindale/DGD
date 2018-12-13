/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.allocationimport.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.forms.debtors.display.allocationimport.DebtorsAllocationImportForm;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author riaan
 */
public class ImportButton extends emcJButton {

    private emcDataRelationManagerUpdate importFormDRM;

    /** Creates a new instance of ImportButton. */
    public ImportButton(emcDataRelationManagerUpdate importFormDRM) {
        super("Import");

        this.importFormDRM = importFormDRM;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        EMCUserData userData = importFormDRM.getUserData();

        if (EMCDialogFactory.createQuestionDialog(importFormDRM.getTheForm(), "Import Allocation?", "Are you sure that you want to import the selected file?") == JOptionPane.YES_OPTION) {
            List<String> lines = ((DebtorsAllocationImportForm) importFormDRM.getTheForm()).readData();

            if (lines == null) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to read import data from file.", userData);
                return;
            }

            EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.IMPORT_SETTLEMENT);

            List toSend = new ArrayList();
            toSend.add(importFormDRM.getRowCache(importFormDRM.getLastRowAccessed()));
            toSend.add(lines);

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

            if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                utilFunctions.logMessage(Level.INFO, "Successfully imported allocation.", userData);
                importFormDRM.refreshRecord(importFormDRM.getLastRowAccessed());
            } else {
                utilFunctions.logMessage(Level.SEVERE, "Failed to import allocation. ", userData);
            }
        }
    }
}
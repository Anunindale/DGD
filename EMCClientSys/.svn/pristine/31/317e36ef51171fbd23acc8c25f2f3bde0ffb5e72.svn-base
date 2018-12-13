/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.pricearangement.resources;

import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.functions.Functions;
import emc.methods.sop.ServerSOPMethods;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author wikus
 */
public class ImportPriceArangementsButton extends emcJButton {

    private emcDataRelationManagerUpdate dataManager;

    public ImportPriceArangementsButton(emcDataRelationManagerUpdate dataManager) {
        super("Import");
        this.dataManager = dataManager;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);
        String path = new ImportExportFileDialog(JFileChooser.FILES_ONLY).getPath();
        if (path == null) return;
        importFile(path);
    }

    private void importFile(String path) {
        File theFile = null;
        BufferedReader in = null;
        List dataToImport = new ArrayList<String>();
        try {
            theFile = new File(path);
            in = new BufferedReader(new FileReader(theFile));
            String read;
            while (!Functions.checkBlank((read = in.readLine()))) {
                dataToImport.add(read);
            }
        } catch (IOException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to import Price List: " + ex.getMessage(), dataManager.getUserData());
            return;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to close stream: " + ex.getMessage(), dataManager.getUserData());
            }
        }
        if (dataToImport.size() > 1) {
            dataToImport.remove(0);
            EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.IMPORT_PRICE_ARANGEMENTS);
            dataToImport = EMCWSManager.executeGenericWS(cmd, dataToImport, dataManager.getUserData());
            if (dataToImport.size() > 1) {
                Logger.getLogger("emc").log(Level.INFO, "Price List imported", dataManager.getUserData());
                dataManager.refreshData();
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to import Price List.", dataManager.getUserData());
            }
        }
    }
}

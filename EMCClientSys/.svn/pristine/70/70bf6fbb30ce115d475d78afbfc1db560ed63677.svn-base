/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.treccards.resources;

import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.functions.Functions;
import emc.methods.trec.ServerTRECMethods;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class BackupTRECButton extends emcMenuButtonList {

    private TRECMasterDRM dataManager;

    public BackupTRECButton(BaseInternalFrame form, TRECMasterDRM dataManager) {
        super("Backup", form);
        this.dataManager = dataManager;
        this.addMenuItem("Export", null, 0, false);
        this.addMenuItem("Import", null, 0, false);
    }

    @Override
    public void executeCmd(String theCmd) {
        if (theCmd.equals("Export")) {
            exportData();
        } else if (theCmd.equals("Import")) {
            importData();
        } else {
            super.executeCmd(theCmd);
        }
    }

    private void exportData() {
        BackupTRECDialog dialog = new BackupTRECDialog(true, dataManager.getUserData());
        String filePath = dialog.getFilePath();
        if (filePath != null) {
            EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.TREC.getId(), ServerTRECMethods.EXPORT_TREC_DATA.toString());
            List serverList = EMCWSManager.executeGenericWS(cmd, new ArrayList<Object>(), dataManager.getUserData());
            if (serverList.size() > 1) {
                List<String> trecData = (List<String>) serverList.get(1);
                if (!trecData.isEmpty()) {
                    File theFile = null;
                    FileWriter out = null;
                    BufferedWriter writer = null;
                    try {
                        theFile = new File(filePath + File.separator + "EMC_TREC_Backup_(" + Functions.nowDateString("yyyy-MM-dd") + ").emc");
                        out = new FileWriter(theFile);
                        writer = new BufferedWriter(out);
                        for (String s : trecData) {
                            writer.write(s);
                        }
                        writer.flush();
                    } catch (IOException ex) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to write backup file: " + ex.getMessage(), dataManager.getUserData());
                        return;
                    } finally {
                        try {
                            if (out != null) out.close();
                            if (writer != null) writer.close();
                        } catch (IOException e) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to close streams.", dataManager.getUserData());
                        }
                    }
                    Logger.getLogger("emc").log(Level.INFO, "TREC Data Exported.", dataManager.getUserData());
                }
            }
        }
    }

    private void importData() {
        BackupTRECDialog dialog = new BackupTRECDialog(false, dataManager.getUserData());
        String filePath = dialog.getFilePath();
        if (filePath != null) {
            String s = null;
            List trecData = new ArrayList();
            File theFile = null;
            FileReader in = null;
            BufferedReader reader = null;
            try {
                theFile = new File(filePath);
                in = new FileReader(theFile);
                reader = new BufferedReader(in);
                while ((s = reader.readLine()) != null) {
                    trecData.add(s);
                }
            } catch (IOException ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to read backup file: " + ex.getMessage(), dataManager.getUserData());
                return;
            } finally {
                try {
                    if (in != null) in.close();
                    if (reader != null) reader.close();
                } catch (IOException e) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to close streams.", dataManager.getUserData());
                }
            }
            if (!trecData.isEmpty()) {
                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.TREC.getId(), ServerTRECMethods.IMPORT_TREC_DATA.toString());
                List serverList = new ArrayList();
                serverList.add(trecData);
                serverList.add(dialog.getOveride());
                serverList = EMCWSManager.executeGenericWS(cmd, serverList, dataManager.getUserData());
                if (serverList.size() > 1) {
                    boolean imported = (Boolean) serverList.get(1);
                    if (imported) {
                        Logger.getLogger("emc").log(Level.INFO, "TREC Data Imported.", dataManager.getUserData());
                        dataManager.refreshData();
                    }
                }
            }
        }
    }
}

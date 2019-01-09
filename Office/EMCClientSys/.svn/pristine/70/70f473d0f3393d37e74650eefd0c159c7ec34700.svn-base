/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.action.copytabledata;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.tables.EMCTablesComboBox;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.base.copytabledata.BaseCopyTableData;
import emc.enums.modules.enumEMCModules;
import emc.forms.base.action.copytabledata.resources.BaseCopyTableDataBackupFileSelectionDialog;
import emc.forms.base.action.copytabledata.resources.BaseCopyTableDataCopanySelectionDialog;
import emc.forms.base.action.copytabledata.resources.BaseCopyTableDataImportFileSelectionDialog;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.base.ServerBaseMethods;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author wikus
 */
public class BaseCopyTableDataForm extends BaseInternalFrame {

    private EMCUserData userData;
    private emcDataRelationManagerUpdate dataManager;

    public BaseCopyTableDataForm(EMCUserData userData) {
        super("Copy table Data", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new BaseCopyTableData(), userData), userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("tableName");
        dataManager.setFormTextId2("copyAttachments");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Tables", tablePane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("tableName");
        keys.add("copyAttachments");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        table.setComboBoxLookupToColumn("tableName", new EMCTablesComboBox(userData));

        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel buttonPane() {
        emcJButton btnAddRelated = new emcJButton("Add Related") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (EMCDialogFactory.createQuestionDialog(utilFunctions.findBaseInternalFrame(this), "Add Related Tables", "Are you sure you want to add all the related tables?") == JOptionPane.YES_OPTION) {
                    EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.ADD_RELATED_TABLES);

                    List toSend = new ArrayList();

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                    if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                        Logger.getLogger("emc").log(Level.INFO, "Successfully add the related tables.", userData);
                        dataManager.refreshData();
                    } else {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to add the related tables.", userData);
                    }
                }
            }
        };
        emcJButton btnClear = new emcJButton("Clear Data") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (EMCDialogFactory.createQuestionDialog(utilFunctions.findBaseInternalFrame(this), "Clear Table Data", "Are you sure you want to clear the table data?") == JOptionPane.YES_OPTION) {
                    BaseCopyTableDataCopanySelectionDialog dialog = new BaseCopyTableDataCopanySelectionDialog(utilFunctions.findEMCDesktop(this), userData);
                    String company = dialog.getCompany();
                    if (Functions.checkBlank(company)) {
                        return;
                    }

                    EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.CLEAR_TABLE_DATA);

                    List toSend = new ArrayList();
                    toSend.add(company);

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
                    if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                        Logger.getLogger("emc").log(Level.INFO, "Successfully cleared table data.", userData);
                    } else {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to clear table data.", userData);
                    }
                }
            }
        };
        emcJButton btnCopy = new emcJButton("Copy") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (EMCDialogFactory.createQuestionDialog(utilFunctions.findBaseInternalFrame(this), "Copy Table Data", "Are you sure you want to copy the table data?") == JOptionPane.YES_OPTION) {

                    BaseCopyTableDataCopanySelectionDialog dialog = new BaseCopyTableDataCopanySelectionDialog(utilFunctions.findEMCDesktop(this), userData);
                    String company = dialog.getCompany();
                    if (Functions.checkBlank(company)) {
                        return;
                    }

                    EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.COPY_TABLE_DATA);

                    List toSend = new ArrayList();
                    toSend.add(company);

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                    if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                        Logger.getLogger("emc").log(Level.INFO, "Successfully copied table data.", userData);
                    } else {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to copy table data.", userData);
                    }
                }
            }
        };
        emcJButton btnBackup = new emcJButton("Backup") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (EMCDialogFactory.createQuestionDialog(utilFunctions.findBaseInternalFrame(this), "Backup Table Data", "Are you sure you want to backup the table data?") == JOptionPane.YES_OPTION) {

                    EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.BACKUP_TABLE_DATA);

                    List toSend = new ArrayList();

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                    if (toSend.size() > 1 && toSend.get(1) instanceof String) {
                        Logger.getLogger("emc").log(Level.INFO, "Successfully backed up the table data.", userData);

                        List udList = new ArrayList();
                        udList.add(enumEMCModules.BASE.toString());
                        udList.add(toSend.get(1).toString());
                        userData.setUserData(udList);
                        byte[] backupFileData = EMCWSManager.downloadFile(userData);
                        if (backupFileData == null) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to download the backup file.", userData);
                            return;
                        }
                        cmd = new EMCCommandClass(ServerBaseMethods.DELETE_BACKUP_FILE);
                        EMCWSManager.executeGenericWS(cmd, new ArrayList<Object>(), userData);

                        BaseCopyTableDataBackupFileSelectionDialog dialog = new BaseCopyTableDataBackupFileSelectionDialog(utilFunctions.findEMCDesktop(this), userData);
                        File backupDir = dialog.getBackupDirectory();
                        if (backupDir == null) {
                            return;
                        }

                        File backupFile = new File(backupDir.getPath() + File.separator + toSend.get(1).toString());
                        ByteArrayInputStream in = null;
                        ZipInputStream zin = null;
                        FileOutputStream out = null;

                        try {
                            in = new ByteArrayInputStream(backupFileData);
                            zin = new ZipInputStream(in);
                            zin.getNextEntry();
                            out = new FileOutputStream(backupFile);
                            int i;
                            while ((i = zin.read()) != -1) {
                                out.write(i);
                            }
                            out.flush();
                        } catch (IOException ex) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to save downloaded the backup file.", userData);
                            return;
                        } finally {
                            try {
                                if (in != null) {
                                    in.close();
                                }
                                if (zin != null) {
                                    zin.close();
                                }
                                if (out != null) {
                                    out.close();
                                }
                            } catch (IOException ex) {
                                if (EMCDebug.getDebug()) {
                                    Logger.getLogger("emc").log(Level.WARNING, "Failed to close all streams: " + ex.getMessage(), userData);
                                }
                            }
                        }
                        Logger.getLogger("emc").log(Level.INFO, "Successfully saved backup file.", userData);
                    } else {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to backup the table data.", userData);
                    }
                }
            }
        };
        emcJButton btnImport = new emcJButton("Import") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (EMCDialogFactory.createQuestionDialog(utilFunctions.findBaseInternalFrame(this), "Import Table Data", "Are you sure you want to import the table data?") == JOptionPane.YES_OPTION) {

                    BaseCopyTableDataImportFileSelectionDialog dialog = new BaseCopyTableDataImportFileSelectionDialog(utilFunctions.findEMCDesktop(this), userData);
                    File backupFile = dialog.getBackupFlie();

                    if (backupFile != null) {
                        FileInputStream in = null;
                        ByteArrayOutputStream out = null;
                        ZipOutputStream zout = null;
                        try {
                            in = new FileInputStream(backupFile);
                            out = new ByteArrayOutputStream();
                            zout = new ZipOutputStream(out);
                            zout.putNextEntry(new ZipEntry("0"));
                            int i;
                            while ((i = in.read()) != -1) {
                                zout.write(i);
                            }
                            zout.closeEntry();
                            zout.flush();
                        } catch (IOException ex) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to upload import file.", userData);
                            return;
                        } finally {
                            //Close All Streams
                            try {
                                if (in != null) {
                                    in.close();
                                }
                                if (out != null) {
                                    out.close();
                                }
                                if (zout != null) {
                                    zout.close();
                                }
                            } catch (IOException ex) {
                                if (EMCDebug.getDebug()) {
                                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to close all streams: " + ex.getMessage(), userData);
                                }
                            }
                        }
                        //Sends to Server
                        List udList = new ArrayList();
                        udList.add(enumEMCModules.BASE.toString());
                        udList.add(backupFile.getPath().substring(backupFile.getPath().lastIndexOf(File.separator) + 1));
                        userData.setUserData(udList);

                        if (EMCWSManager.uploadFile(out.toByteArray(), userData)) {
                            EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.IMPORT_TABLE_DATA);

                            List toSend = new ArrayList();
                            toSend.add(backupFile.getPath().substring(backupFile.getPath().lastIndexOf(File.separator) + 1));

                            toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                            if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                                Logger.getLogger("emc").log(Level.INFO, "Successfully imported the table data.", userData);
                            } else {
                                Logger.getLogger("emc").log(Level.SEVERE, "Failed to import the table data.", userData);
                            }
                        } else {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to upload import file.", userData);
                        }
                    }
                }
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnAddRelated);
        buttonList.add(btnClear);
        buttonList.add(btnCopy);
        buttonList.add(btnBackup);
        buttonList.add(btnImport);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}

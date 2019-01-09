/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.action.setaexport;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcfilepicker.EMCFilePicker;
import emc.app.components.queryconstructor.EMCQueryConstructorDialog;
import emc.app.excelexport.EMCExcelExport;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.base.BaseEmployeeTable;
import emc.enums.enumQueryTypes;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.base.ServerBaseMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author wikus
 */
public class BaseSetaExportForm extends BaseInternalFrame {

    private emcJTextField txtExportFileName;
    private EMCFilePicker fpExportFileLocation;
    private EMCQuery selectionQuery;
    private EMCUserData userData;

    public BaseSetaExportForm(EMCUserData userData) {
        super("SETA Export", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        this.userData = userData.copyUserData();
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Export", selectionPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcJPanel selectionPane() {
        txtExportFileName = new emcJTextField();
        fpExportFileLocation = new EMCFilePicker();
        fpExportFileLocation.setSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        Component[][] comp = {{new emcJLabel("Export File Name"), txtExportFileName},
            {new emcJLabel("Export File Location"), fpExportFileLocation}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel buttonPane() {
        emcJButton btnExport = new emcJButton("Export") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (Functions.checkBlank(txtExportFileName.getText())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please specify a export file name.", userData);
                    return;
                }
                if (Functions.checkBlank(fpExportFileLocation.getFilePath())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please specify a export file location.", userData);
                    return;
                }
                if (selectionQuery == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please specify the selection criteria.", userData);
                    return;
                }
                if (EMCDialogFactory.createQuestionDialog(utilFunctions.findBaseInternalFrame(this), "Export", "Are you sure you want to create the SETA export file?") == JOptionPane.YES_OPTION) {
                    EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.CREATE_SETA_EXPORT_FILE);

                    List toSend = new ArrayList();
                    toSend.add(selectionQuery);

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                    if (toSend.size() > 1 && toSend.get(1) instanceof List) {
                        new EMCExcelExport().createExcelExportFile((List<String>) toSend.get(1), fpExportFileLocation.getFile(), txtExportFileName.getText(), userData);

                        Logger.getLogger("emc").log(Level.INFO, "SETA export file created. The fields are seperated by a \'|\' (Vertical Bar).", userData);
                    } else {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to create SETA export file.", userData);
                    }
                }
            }
        };

        emcJButton btnSelection = new emcJButton("Selection") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (selectionQuery == null) {
                    selectionQuery = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
                    selectionQuery.addAnd("terminationDate", Functions.nowDate());
                }

                selectionQuery = new EMCQueryConstructorDialog(utilFunctions.findEMCDesktop(this), selectionQuery, userData).getQuery();
            }
        };

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnExport);
        buttonList.add(btnSelection);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}

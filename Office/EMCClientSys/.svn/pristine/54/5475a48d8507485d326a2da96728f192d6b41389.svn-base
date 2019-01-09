/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.pricearangement.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcfilepicker.EMCFilePicker;
import emc.functions.Functions;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author wikus
 */
public class ImportExportFileDialog extends emcJDialog {

    private int fileType;
    private EMCFilePicker fpImportExport;
    private emcJTextField txtFileName;
    private String path = null;

    public ImportExportFileDialog(int fileType) {
        super(null, fileType == JFileChooser.DIRECTORIES_ONLY ? "Export File To" : "File To Import", true);
        this.fileType = fileType;
        initFrame();
        pack();
        this.setVisible(true);
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Import/Export", selectionPane());
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcJPanel selectionPane() {
        Component[][] comp;
        fpImportExport = new EMCFilePicker();
        fpImportExport.setFileSelectionMode(fileType);
        if (fileType == JFileChooser.DIRECTORIES_ONLY) {
            txtFileName = new emcJTextField();
            comp = new Component[][]{{new emcJLabel("Export File To"), fpImportExport},
                        {new emcJLabel("File Name"), txtFileName}};
        } else {
            comp = new Component[][]{{new emcJLabel("File To Import"), fpImportExport}};
        }
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                String path = fpImportExport.getFilePath();
                if (Functions.checkBlank(path)) {
                    Logger.getLogger("emc").log(Level.WARNING, "No file selected.");
                    return;
                }
                if (fileType == JFileChooser.DIRECTORIES_ONLY) {
                    String name = txtFileName.getText();
                    if (Functions.checkBlank(name)) {
                        Logger.getLogger("emc").log(Level.WARNING, "No file name selected.");
                        return;
                    }
                    ImportExportFileDialog.this.path = path + File.separator + name + ".cvs";
                } else {
                    ImportExportFileDialog.this.path = path;
                }
                ImportExportFileDialog.this.dispose();
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                ImportExportFileDialog.this.path = null;
                ImportExportFileDialog.this.dispose();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    public String getPath() {
        return path;
    }
}

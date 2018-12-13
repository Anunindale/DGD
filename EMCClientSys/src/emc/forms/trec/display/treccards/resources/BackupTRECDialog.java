/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.treccards.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcfilepicker.EMCFilePicker;
import emc.app.components.emctable.emcYesNoComponent;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author wikus
 */
public class BackupTRECDialog extends emcJDialog {

    private String filePath;
    private boolean overide;
    private EMCFilePicker fpFilePath;
    private emcYesNoComponent ynOveride;
    private EMCUserData userData;
    private boolean exportTREC;

    public BackupTRECDialog(boolean exportTREC, EMCUserData userData) {
        super(null, exportTREC ? "Export TREC" : "Import TREC", true);
        this.setBounds(20, 20, 400, exportTREC ? 150 : 180);
        this.userData = userData;
        this.exportTREC = exportTREC;
        initFrame();
        this.setVisible(true);
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbedPane(), BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcJTabbedPane tabbedPane() {
        fpFilePath = new EMCFilePicker();
        fpFilePath.setFileSelectionMode(exportTREC ? JFileChooser.DIRECTORIES_ONLY : JFileChooser.FILES_ONLY);
        ynOveride = new emcYesNoComponent();
        Component[][] comp = null;
        if (exportTREC) {
            comp = new Component[][]{{new emcJLabel("File"), fpFilePath}};
        } else {
            comp = new Component[][]{{new emcJLabel("File"), fpFilePath}, {new emcJLabel("Drop Tables"), ynOveride}};
        }
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Backup", emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, exportTREC ? "Export TREC" : "Import TREC"));
        return tabbed;
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                filePath = fpFilePath.getFilePath();
                overide = ynOveride.getSelectedItem().equals("Yes");
                if (Functions.checkBlank(filePath)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "No file selected. Please select a file to upload.", userData);
                } else {
                    BackupTRECDialog.this.dispose();
                }

            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                filePath = null;
                BackupTRECDialog.this.dispose();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean getOveride() {
        return overide;
    }
}

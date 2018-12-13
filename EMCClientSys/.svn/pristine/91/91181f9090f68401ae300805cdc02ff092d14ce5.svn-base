/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.action.copytabledata.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcfilepicker.EMCFilePicker;
import emc.app.frame.EMCDesktop;
import emc.framework.EMCUserData;
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
public class BaseCopyTableDataImportFileSelectionDialog extends emcJDialog {

    private EMCUserData userData;
    private EMCFilePicker fpBackupFlie;

    public BaseCopyTableDataImportFileSelectionDialog(EMCDesktop owner, EMCUserData userData) {
        super(owner, "Import EMC Backup", true);
        this.userData = userData.copyUserData();
        initDialog();
        this.pack();
        this.setVisible(true);
    }

    private void initDialog() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Import", selectionPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcJPanel selectionPane() {
        fpBackupFlie = new EMCFilePicker();
        fpBackupFlie.setSelectionMode(JFileChooser.FILES_ONLY);

        Component[][] comp = {{new emcJLabel("Backup File"), fpBackupFlie}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (fpBackupFlie.getFile() == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please selecct a EMC Backup file to import.", userData);
                    return;
                }
                BaseCopyTableDataImportFileSelectionDialog.this.dispose();
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                BaseCopyTableDataImportFileSelectionDialog.this.dispose();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    public File getBackupFlie() {
        return fpBackupFlie.getFile();
    }
}

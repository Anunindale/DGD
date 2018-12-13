/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.base.sms;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcfilepicker.EMCFilePicker;
import emc.app.frame.EMCDesktop;
import emc.framework.EMCUserData;
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
import javax.swing.JOptionPane;

/**
 *
 * @author wikus
 */
public class EMCSMSFilePickerDialog extends emcJDialog {

    private EMCUserData userData;
    private EMCFilePicker fpFileLocation;
    private emcJTextField txtFileName;
    private int dialogResult = JOptionPane.CLOSED_OPTION;

    public EMCSMSFilePickerDialog(EMCDesktop desktop, EMCUserData userData) {
        super(desktop, "Save SMS Output File", true);
        this.userData = userData.copyUserData();
        initDialog();
        this.pack();
        this.setVisible(true);
    }

    private void initDialog() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("SMS File", selectionPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(selectionPane(), BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcJPanel selectionPane() {
        fpFileLocation = new EMCFilePicker();
        fpFileLocation.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        txtFileName = new emcJTextField();

        Component[][] comp = {{new emcJLabel("File Location"), fpFileLocation},
                              {new emcJLabel("File Name"), txtFileName}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Output File");
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                if (Functions.checkBlank(fpFileLocation.getFilePath())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select the file location.", userData);
                    return;
                }
                if (Functions.checkBlank(txtFileName.getText())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please specify the file name.", userData);
                    return;
                }

                dialogResult = JOptionPane.YES_OPTION;

                EMCSMSFilePickerDialog.this.dispose();
            }

        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                dialogResult = JOptionPane.CANCEL_OPTION;

                EMCSMSFilePickerDialog.this.dispose();
            }

        };

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    public int getDialogResult() {
        return dialogResult;
    }

    public String getFileName() {
        return txtFileName.getText();
    }

    public File getFileLocation() {
        return fpFileLocation.getFile();
    }

}

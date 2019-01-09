/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.journals.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePicker;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.forms.inventory.display.journals.JournalLinesDRM;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class MassLineDateUpdateForm extends emcJDialog {

    private JournalLinesDRM drm;
    private String journalNumber;
    private EMCDatePicker dpTheDate;
    private EMCUserData userData;

    public MassLineDateUpdateForm(JournalLinesDRM drm, String journalNumber, EMCUserData userData) {
        super();
        this.setModal(true);
        this.drm = drm;
        this.journalNumber = journalNumber;
        this.userData = userData.copyUserDataAndDataList();
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbedPane(), BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
        this.setTitle("Update Line Date");
        this.setSize(350, 125);
        this.setVisible(true);
    }

    private emcJTabbedPane tabbedPane() {
        dpTheDate = new EMCDatePicker();
        Component[][] comp = {{new emcJLabel("Line Date"), dpTheDate}};
        emcJPanel datePane = emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Line Date", datePane);
        return tabbed;
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                Date theDate = dpTheDate.getDate();
                if (!Functions.checkBlank(theDate)) {
                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.MASS_UPDATE_LINE_DATE.toString());
                    List toSend = new ArrayList();
                    toSend.add(journalNumber);
                    toSend.add(theDate);
                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
                    if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                        Logger.getLogger("emc").log(Level.INFO, "Line date updated.", userData);
                        drm.refreshData();
                        MassLineDateUpdateForm.this.dispose();
                    } else {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to update line date.", userData);
                    }
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select the new date.", userData);
                }
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                MassLineDateUpdateForm.this.dispose();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}

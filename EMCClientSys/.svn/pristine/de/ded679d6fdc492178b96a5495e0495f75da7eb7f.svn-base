/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.timebyday.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePicker;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
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

/**
 *
 * @author wikus
 */
public class GenerateTimesForm extends BaseInternalFrame {

    private EMCUserData userData;
    private EMCDatePicker dpFromDate;
    private EMCDatePicker dpToDate;
    private emcDataRelationManagerUpdate dataManager;

    public GenerateTimesForm(EMCUserData userData) {
        super("Generate Times", true, true, true, true, userData);
        this.setBounds(20, 20, 400, 200);
        this.dataManager = (emcDataRelationManagerUpdate) userData.getUserData().remove(0);
        this.userData = userData.copyUserDataAndDataList();
        initFrame();

    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbedPane(), BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcJTabbedPane tabbedPane() {
        dpFromDate = new EMCDatePicker();
        dpToDate = new EMCDatePicker();
        Component[][] comp = {{new emcJLabel("From Date"), dpFromDate},
                              {new emcJLabel("To Date"), dpToDate}};
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Generate", emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true));
        return tabbed;
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (Functions.checkBlank(dpFromDate.getDate())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select the from date.", userData);
                } else if (Functions.checkBlank(dpToDate.getDate())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select the to date.", userData);
                } else if (!dpFromDate.getDate().before(dpToDate.getDate())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The from date has to be before the to date.", userData);
                } else {
                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.GENERATE_TIMES.toString());
                    List toSend = new ArrayList();
                    toSend.add(dpFromDate.getDate());
                    toSend.add(dpToDate.getDate());
                    EMCWSManager.executeGenericWS(cmd, toSend, userData);
                    dataManager.refreshData();
                    GenerateTimesForm.this.dispose();
                }
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                GenerateTimesForm.this.dispose();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.createNLonHandJournal;

import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.developertools.ServerDeveloperToolMethods;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class createNLJournalForm extends BaseInternalFrame {

    emcJTextField journalNumber = new emcJTextField();
    emcJButton okButton;
    emcJButton okTrims;
    emcJButton okElastic;
    EMCUserData userData;

    public createNLJournalForm(EMCUserData userData) {
        super("N&L create On Hand Journal", true, true, true, true, userData);
        this.setBounds(20, 20, 500, 200);
        this.userData = userData;
        initFrame();
    }

    private void initFrame() {
        Component[][] components = new Component[][]{
            {new emcJLabel("Journal Number"), journalNumber}};
        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        emcJTabbedPane theTab = new emcJTabbedPane();
        theTab.add("Create Journal", thePanel);

        okButton = new emcJButton("OK Martin") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                if (!Functions.checkBlank(journalNumber.getText())) {
                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.DEVELOPERTOOLS.getId(), ServerDeveloperToolMethods.FINISH_IMPORT.toString());
                    List toSend = new ArrayList();
                    toSend.add(journalNumber.getText());
                    EMCWSManager.executeGenericWS(cmd, toSend, userData);
                }
            }
        };

        okTrims = new emcJButton("OK Trims") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                if (!Functions.checkBlank(journalNumber.getText())) {
                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.DEVELOPERTOOLS.getId(), ServerDeveloperToolMethods.FINISH_IMPORT_SIZE.toString());
                    List toSend = new ArrayList();
                    toSend.add(journalNumber.getText());
                    EMCWSManager.executeGenericWS(cmd, toSend, userData);
                }
            }
        };

        okElastic = new emcJButton("OK Elastic") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                if (!Functions.checkBlank(journalNumber.getText())) {
                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.DEVELOPERTOOLS.getId(), ServerDeveloperToolMethods.FINISH_IMPORT_ELASTIC.toString());
                    List toSend = new ArrayList();
                    toSend.add(journalNumber.getText());
                    EMCWSManager.executeGenericWS(cmd, toSend, userData);
                }
            }
        };

        List<emcJButton> butList = new ArrayList();
        butList.add(okButton);
        butList.add(okTrims);
        butList.add(okElastic);
        butList.add(new emcJButton("Fix Costs") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.FIX_COST_ROUTINE.toString());

                List toSend = new ArrayList();

                EMCWSManager.executeGenericWS(cmd, toSend, userData);
            }
        });
        butList.add(new emcJButton("Fix Transactions") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.FIX_TRANS_ROUTINE.toString());

                List toSend = new ArrayList();

                EMCWSManager.executeGenericWS(cmd, toSend, userData);
            }
        });

        emcJPanel butPanel = emcSetGridBagConstraints.createButtonPanel(butList);
        this.add(theTab, BorderLayout.CENTER);
        this.add(butPanel, BorderLayout.EAST);
    }
}

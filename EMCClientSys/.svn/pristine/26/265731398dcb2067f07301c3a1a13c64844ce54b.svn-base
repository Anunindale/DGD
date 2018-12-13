/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.timedoperations;

import emc.app.components.base.EMCTimedOperationEnumDropDown;
import emc.app.components.documents.EMCTimeFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.base.timedoperations.BaseTimedOperations;
import emc.enums.base.timedoperations.EnumTimedOperationStatus;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
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
public class BaseTimedOperationSetupForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;

    public BaseTimedOperationSetupForm(EMCUserData userData) {
        super("Timed Operations", true, true, true, true, userData);
        this.setBounds(20, 20, 800, 350);
        try {
            dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.BASE.getId(), new BaseTimedOperations(), userData), userData);
            this.setDataManager(dataManager);
            dataManager.setTheForm(this);
            dataManager.setFormTextId1("operationEnumId");
            dataManager.setFormTextId2("currentStatus");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Timed Operations", tablePane());
        tabbed.add("Execution Log", executionLogPanePane());
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("theModule");
        keys.add("operationEnumId");
        keys.add("startExecutionDate");
        keys.add("startExecutionTime");
        keys.add("idleDurationHour");
        keys.add("idleDurationMin");
        keys.add("idleDurationSec");
        keys.add("currentStatus");
        keys.add("fireOnStartup");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        table.setColumnEditable("theModule", false);
        table.setColumnEditable("currentStatus", false);
        setupLookup(table);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLookup(emcJTableUpdate table) {
        table.setComboBoxLookupToColumn("operationEnumId", new EMCTimedOperationEnumDropDown());
    }

    private emcJPanel buttonPane() {
        emcJButton btnStart = new emcJButton("Start") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (dataManager.getLastFieldValueAt("currentStatus").equals(EnumTimedOperationStatus.STARTED.toString())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Operation is already running", dataManager.getUserData());
                } else {
                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.START_TIMED_OPERATION.toString());
                    List toSend = new ArrayList();
                    toSend.add(dataManager.getLastFieldValueAt("operationEnumId"));
                    EMCWSManager.executeGenericWS(cmd, toSend, dataManager.getUserData());
                    dataManager.refreshRecordIgnoreFocus(dataManager.getLastRowAccessed());
                }
            }
        };
        emcJButton btnStop = new emcJButton("Stop") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (dataManager.getLastFieldValueAt("currentStatus").equals(EnumTimedOperationStatus.STOPED.toString())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Operation has already been stopped.", dataManager.getUserData());
                } else {
                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.STOP_TIMED_OPERATION.toString());
                    List toSend = new ArrayList();
                    toSend.add(dataManager.getLastFieldValueAt("operationEnumId"));
                    EMCWSManager.executeGenericWS(cmd, toSend, dataManager.getUserData());
                    dataManager.refreshRecordIgnoreFocus(dataManager.getLastRowAccessed());
                }
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnStart);
        buttonList.add(btnStop);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private emcJPanel executionLogPanePane() {
        EMCDatePickerFormComponent dpLastExecutedDate = new EMCDatePickerFormComponent(dataManager, "lastExecutedDate");
        dpLastExecutedDate.setEnabled(false);
        emcJTextField txtLastExecutedTime = new emcJTextField();
        txtLastExecutedTime.setDocument(new EMCTimeFormDocument(txtLastExecutedTime, dataManager, "lastExecutedTime"));
        txtLastExecutedTime.setEditable(false);


        EMCDatePickerFormComponent dpLastExecutedEndDate = new EMCDatePickerFormComponent(dataManager, "lastExecutedEndDate");
        dpLastExecutedEndDate.setEnabled(false);
        emcJTextField txtLastExecutedEndTime = new emcJTextField();
        txtLastExecutedEndTime.setDocument(new EMCTimeFormDocument(txtLastExecutedEndTime, dataManager, "lastExecutedEndTime"));
        txtLastExecutedEndTime.setEditable(false);

        Component[][] comp = {{new emcJLabel("Last Executed Date"), dpLastExecutedDate, new emcJLabel("Last Executed Time"), txtLastExecutedTime},
            {new emcJLabel("Last Completed Date"), dpLastExecutedEndDate, new emcJLabel("Last Completed Time"), txtLastExecutedEndTime}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Execution Log");
    }
}

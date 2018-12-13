/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.timesheet.resources;

import emc.app.components.documents.EMCIntegerDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePicker;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.frame.EMCDesktop;
import emc.entity.developertools.bugtracking.DevBugsTable;
import emc.entity.sop.SOPCustomers;
import emc.enums.developertools.DevBugType;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.devtools.DevTimeSheetTaskHelper;
import emc.menus.debtors.menuitems.display.DebtorsCustomers;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class DevTaskCreationDialog extends emcJDialog {

    private emcJComboBox cbModule;
    private emcJComboBox cbType;
    private emcJTextField txtPriority;
    private EMCLookup lkpClient;
    private EMCLookup lkpTask;
    private EMCDatePicker dpRequireDate;
    private DevTimeSheetTaskHelper helper;
    private EMCUserData userData;

    public DevTaskCreationDialog(EMCDesktop desktop, EMCUserData userData) {
        super(desktop, "Tasks", true);

        this.userData = userData;

        initDialog();

        this.pack();
        this.setVisible(true);
    }

    private void initDialog() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Tasks", selectionPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcJPanel selectionPane() {
        List<String> modules = new ArrayList<String>();
        for (enumEMCModules m : enumEMCModules.values()) {
            modules.add(m.toString());
        }

        modules.add("Not Applicable");
        Collections.sort(modules);

        cbModule = new emcJComboBox(modules.toArray(new String[]{}));

        List<String> type = new ArrayList<String>();
        for (DevBugType t : DevBugType.values()) {
            type.add(t.toString());
        }

        //type.add("Not Applicable");
        Collections.sort(type);

        cbType = new emcJComboBox(type.toArray(new String[]{}));

        txtPriority = new emcJTextField(new EMCIntegerDocument());

        lkpClient = new EMCLookup(new DebtorsCustomers());
        lkpClient.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", userData));

        dpRequireDate = new EMCDatePicker();
        
        lkpTask = new EMCLookup(new emc.menus.developertools.Bugs());
        List<String> keys = new ArrayList<String>();
        keys.add("bugNumber");
        keys.add("summary");
        lkpTask.setPopup(new EMCLookupPopup(new DevBugsTable(), "bugNumber", keys, userData));
        
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevBugsTable.class);
        String uName = userData.getUserName();
        query.addAnd("responsible", uName);
        
        lkpTask.setTheQuery(query);
        
        

        Component[][] comp = {{new emcJLabel("Module"), cbModule},
            {new emcJLabel("Type"), cbType},
            {new emcJLabel("Priority"), txtPriority},
            {new emcJLabel("Client"), lkpClient},
            {new emcJLabel("Required Date"), dpRequireDate},
            {new emcJLabel("Parent Task"), lkpTask}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                if (cbModule.getSelectedItem() == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select the module.");
                    return;
                }

                if (cbType.getSelectedItem() == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select the type.");
                    return;
                }

                if (Functions.checkBlank(txtPriority.getText())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select the priority.");
                    return;
                }

                if (Functions.checkBlank(lkpClient.getValue())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select the client.");
                    return;
                }

                if (dpRequireDate.getDate() == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please enter the required date.");
                    return;
                }

                helper = new DevTimeSheetTaskHelper();
                helper.setModule((String) cbModule.getSelectedItem());
                helper.setType((String) cbType.getSelectedItem());
                helper.setPriority(txtPriority.getText());
                helper.setClient((String) lkpClient.getValue());
                helper.setRequiredDate(dpRequireDate.getDate());
                helper.setParentTask(lkpTask.getValue().toString().isEmpty() ? "0" : (String) lkpTask.getValue());

                DevTaskCreationDialog.this.dispose();
            }
        };

        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                DevTaskCreationDialog.this.dispose();
            }
        };

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    public DevTimeSheetTaskHelper getHelper() {
        return helper;
    }
}


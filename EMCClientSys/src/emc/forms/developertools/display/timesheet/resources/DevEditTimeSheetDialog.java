/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.timesheet.resources;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.documents.EMCTimeDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePicker;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.frame.EMCDesktop;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.developertools.DevTimeSheet;
import emc.entity.developertools.bugtracking.DevBugsTable;
import emc.enums.enumQueryTypes;
import emc.framework.EMCCommandClass;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.developertools.Bugs;
import emc.methods.developertools.ServerDeveloperToolMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
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
public class DevEditTimeSheetDialog extends emcJDialog {

    private DevTimeSheet entity;
    private EMCUserData userData;
    private EMCDatePicker dpSessionDate;
    private emcJTextField txtStartTime;
    private emcJTextField txtEndTime;
    private EMCLookup txtSummary;
    private emcJTextArea txtDescription;
    private DevTimeSheetDRM dataManager;

    public DevEditTimeSheetDialog(DevTimeSheetDRM dataManager, DevTimeSheet entity, EMCDesktop desktop, EMCUserData userData) {
        super(desktop, "Edit Session", true);

        this.dataManager = dataManager;
        this.entity = entity;
        this.userData = userData.copyUserData();

        initDialog();

        this.pack();
        this.setVisible(true);
    }

    private void initDialog() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Edit Session", selectionPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcJPanel selectionPane() {
        dpSessionDate = new EMCDatePicker();
        dpSessionDate.setDate(entity.getWorkDate());

        txtStartTime = new emcJTextField();
        txtStartTime.setDocument(new EMCTimeDocument(txtStartTime));
        txtStartTime.setText(Functions.date2String(entity.getWorkStartTime(), "HH:mm"));

        txtEndTime = new emcJTextField();
        txtEndTime.setDocument(new EMCTimeDocument(txtEndTime));
        txtEndTime.setText(Functions.date2String(entity.getWorkEndTime(), "HH:mm"));

        txtSummary = new EMCLookup(new Bugs());
        txtSummary.setPopup(new EMCLookupPopup(new DevBugsTable(), "summary", userData));
        txtSummary.setValue(entity.getTaskSummary());
        
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevBugsTable.class);
        query.addAnd("completed", null);
        query.openAndConditionBracket();
        query.addOr("responsible", dataManager.getLookup().getValue());
        if (!Functions.checkBlank(dataManager.getGeneralUser())) {
            query.addOr("responsible", dataManager.getGeneralUser());
        }
        query.closeConditionBracket();

        txtSummary.setTheQuery(query);

        txtDescription = new emcJTextArea(new EMCStringDocument());
        txtDescription.setText(entity.getTaskDetail());

        emcJLabel descriptionLabel = new emcJLabel("Description");
        descriptionLabel.setUsePreferedDimensions(true);
        descriptionLabel.setPreferredSize(new Dimension(350, 150));

        Component[][] comp = {{new emcJLabel("Session Date"), dpSessionDate},
            {new emcJLabel("Start Time"), txtStartTime},
            {new emcJLabel("End Time"), txtEndTime},
            {new emcJLabel("Summary"), txtSummary},
            {txtDescription, descriptionLabel}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);

    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                if (dpSessionDate.getDate() == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please enter the Session Date.", userData);
                    return;
                } else if (Functions.checkBlank(txtStartTime.getText())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please enter the Session Start Time.", userData);
                    return;
                } else if (Functions.checkBlank(txtEndTime.getText())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please enter the Session End Time.", userData);
                    return;
                } else if (Functions.checkBlank(txtSummary.getValue())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please enter a task summary.", userData);
                    return;
                }

                entity.setWorkDate(dpSessionDate.getDate());

                String time = txtStartTime.getText();
                String[] timeSplit = time.split(":");
                Date d = new Date(0, 0, 0, Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1]));
                entity.setWorkStartTime(d);

                time = txtEndTime.getText();
                timeSplit = time.split(":");
                d = new Date(0, 0, 0, Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1]));
                entity.setWorkEndTime(d);

                entity.setTaskSummary((String) txtSummary.getValue());

                entity.setTaskDetail(txtDescription.getText());

//                long taskRecId = dataManager.createTask(entity);
//
//                if (taskRecId == -1) {
//                    return;
//                }
//
//                entity.setTaskRecordId(taskRecId);

                EMCCommandClass cmd = new EMCCommandClass(ServerDeveloperToolMethods.UPDATE_DEVTIMESHEETDS);

                List toSend = new ArrayList();
                toSend.add(entity);

                toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
                if (toSend.size() > 1 && toSend.get(1) instanceof EMCEntityClass) {
                    DevEditTimeSheetDialog.this.dispose();
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to update the session.", userData);
                }
            }
        };

        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                DevEditTimeSheetDialog.this.dispose();
            }
        };

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}

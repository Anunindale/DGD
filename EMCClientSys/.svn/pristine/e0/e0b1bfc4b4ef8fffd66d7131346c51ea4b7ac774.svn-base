/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.timesheet.resources;

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
import emc.app.components.lookup.EMCLookupDocument;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.frame.EMCDesktop;
import emc.app.wsmanager.EMCWSManager;
import emc.datatypes.developertools.bugtracking.TaskSummary;
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
import javax.swing.SwingUtilities;

/**
 *
 * @author wikus
 */
public class DevAddTimeSheetDialog extends emcJDialog {
    
    private String sessionId;
    private EMCUserData userData;
    private EMCDatePicker dpSessionDate;
    private emcJTextField txtStartTime;
    private emcJTextField txtEndTime;
    private EMCLookup lkpSummary;
    private emcJTextArea txtDescription;
    private DevTimeSheetDRM dataManager;
    
    public DevAddTimeSheetDialog(DevTimeSheetDRM dataManager, String sessionId, EMCDesktop desktop, EMCUserData userData) {
        super(desktop, "Add Session", true);
        
        this.dataManager = dataManager;
        this.sessionId = sessionId;
        this.userData = userData.copyUserData();
        
        initDialog();
        
        populateDefaults();
        
        this.pack();
        this.setVisible(true);
    }
    
    private void initDialog() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Add Session", selectionPane());
        
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        
        this.setContentPane(contentPane);
    }
    
    private emcJPanel selectionPane() {
        dpSessionDate = new EMCDatePicker();
        
        txtStartTime = new emcJTextField();
        txtStartTime.setDocument(new EMCTimeDocument(txtStartTime));
        
        txtEndTime = new emcJTextField();
        txtEndTime.setDocument(new EMCTimeDocument(txtEndTime));
        
        lkpSummary = new EMCLookup(new Bugs());
        lkpSummary.setPopup(new EMCLookupPopup(new DevBugsTable(), "summary", userData));
        EMCLookupDocument summaryDoc = new EMCLookupDocument(lkpSummary);
        summaryDoc.setDataType(new TaskSummary());
        lkpSummary.setDocument(summaryDoc);
        
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevBugsTable.class);
        query.addAnd("completed", null);
//        query.openAndConditionBracket();
//        query.addOr("responsible", dataManager.getLookup().getValue());
//        if (!Functions.checkBlank(dataManager.getGeneralUser())) {
//            query.addOr("responsible", dataManager.getGeneralUser());
//        }
//        query.closeConditionBracket();
        
        lkpSummary.setTheQuery(query);
        
        EMCLookupDocument doc = new EMCLookupDocument(lkpSummary);
        doc.setDataType(new TaskSummary());
        
        lkpSummary.setDocument(doc);
        
        txtDescription = new emcJTextArea();
        emcJLabel descriptionLabel = new emcJLabel("Description");
        descriptionLabel.setUsePreferedDimensions(true);
        descriptionLabel.setPreferredSize(new Dimension(350, 75));
        
        Component[][] comp = {{new emcJLabel("Session Date"), dpSessionDate},
                              {new emcJLabel("Start Time"), txtStartTime},
                              {new emcJLabel("End Time"), txtEndTime},
                              {new emcJLabel("Summary"), lkpSummary},
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
                } else if (Functions.checkBlank(lkpSummary.getValue())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please enter a task summary.", userData);
                    return;
                }
                
                DevTimeSheet entity = new DevTimeSheet();
                entity.setEmployeeId(sessionId);
                entity.setWorkDate(dpSessionDate.getDate());
                
                String time = txtStartTime.getText();
                String[] timeSplit = time.split(":");
                Date d = new Date(0, 0, 0, Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1]));
                entity.setWorkStartTime(d);
                
                time = txtEndTime.getText();
                timeSplit = time.split(":");
                d = new Date(0, 0, 0, Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1]));
                entity.setWorkEndTime(d);
                
                String taskSum = (String) lkpSummary.getValue();
                taskSum = taskSum.replaceAll("\\(", "").replaceAll("\\)", "");
                entity.setTaskSummary(taskSum/*(String) lkpSummary.getValue()*/);
                
                entity.setTaskDetail(txtDescription.getText());
                
                long taskRecId = dataManager.createTask(entity);
                
                if (taskRecId == -1) {
                    return;
                }
                
                entity.setTaskRecordId(taskRecId);
                
                EMCCommandClass cmd = new EMCCommandClass(ServerDeveloperToolMethods.INSERT_DEVTIMESHEET);
                
                List toSend = new ArrayList();
                toSend.add(entity);
                
                toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
                
                if (toSend.size() > 1 && toSend.get(1) instanceof EMCEntityClass) {
                    DevAddTimeSheetDialog.this.dispose();
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to add the new session.", userData);
                }
            }

        };
        
        emcJButton btnCancel = new emcJButton("Cancel") {
            
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                
                DevAddTimeSheetDialog.this.dispose();
            }

        };
        
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);
        
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
    
    private void populateDefaults() {
        if (dataManager.getSelectedDate() != null) {
            dpSessionDate.setDate(dataManager.getSelectedDate());
        } else {
            dpSessionDate.setDate(Functions.nowDate());
        }
        
        EMCCommandClass cmd = new EMCCommandClass(ServerDeveloperToolMethods.GET_LAST_TASK_END_TIME);
        
        List toSend = new ArrayList();
        toSend.add(dpSessionDate.getDate());
        
        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
        
        if (toSend.size() > 1 && toSend.get(1) instanceof Date) {
            txtStartTime.setText(Functions.date2String((Date) toSend.get(1), "HH:mm"));
            SwingUtilities.invokeLater(new Runnable() {
                
                @Override
                public void run() {
                    txtEndTime.requestFocus();
                }

            });
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                
                @Override
                public void run() {
                    txtStartTime.requestFocus();
                }

            });
        }
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.tasks;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePicker;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.developertools.bugtracking.DevBugsTable;
import emc.entity.sop.SOPCustomers;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import emc.methods.developertools.ServerDeveloperToolMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;

/**
 *
 * @author Pierre
 */
public class CloseTasksForm extends BaseInternalFrame {
   
   private EMCLookup lkpClient;
   private EMCDatePicker fromDatePicker;
   private EMCDatePicker toDatePicker;
   private EMCLookup lkpTask;
   private EMCUserData userData; 
   
   public CloseTasksForm (EMCUserData userData) {
       super("Close Tasks", true, true, true, true, userData);
       this.userData = userData;
       this.setBounds(20, 20, 420, 180);
       this.initFrame();
 
   }
   
   public void initFrame() {
    this.add(buttonPanel(),BorderLayout.EAST);
    this.add(selectionPane(),BorderLayout.WEST);
   }
   
   private emcJPanel buttonPanel() {
    List <emcJButton> buttons = new ArrayList<emcJButton>();
    buttons.add(new emcJButton("Close"){
     @Override
     public void doActionPerformed(ActionEvent evt) {
      super.doActionPerformed(evt);
      
      EMCCommandClass cmd;
      List toSend;
      if (Functions.checkBlank(lkpTask.getValue()) &&  !Functions.checkBlank(fromDatePicker.getValue()) && !Functions.checkBlank(toDatePicker.getValue())) {
            if (Functions.checkBlank(lkpClient.getValue())) {
           utilFunctions.logMessage(Level.SEVERE, "Please enter a client.", userData);
           return;
          }  
        cmd = new EMCCommandClass(ServerDeveloperToolMethods.CLOSE_TASKS_BUG_LIST_FROM_DATE);
        toSend = new ArrayList();
        toSend.add(lkpClient.getValue().toString());
        toSend.add(fromDatePicker.getDate());
        toSend.add(toDatePicker.getDate());
        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
      }
      else if (!Functions.checkBlank(lkpTask.getValue())) {
       cmd = new EMCCommandClass(ServerDeveloperToolMethods.CLOSE_TASKS_BUG_LIST_FROM_NUMBER);
       toSend = new ArrayList();
       toSend.add(lkpClient.getValue().toString());
       toSend.add(lkpTask.getValue().toString());
       toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
      }
      else  {
       utilFunctions.logMessage(Level.SEVERE, "Please enter a task number or dates", userData);   
      return;
      }
   
      

      if (toSend != null && toSend.size() > 1 && toSend.get(1) == Boolean.TRUE) { 
       utilFunctions.logMessage(Level.INFO, "Tasks closed succesfully", userData);
     
      }
      else {
       utilFunctions.logMessage(Level.SEVERE, "Failed to close tasks", userData);
      }
     }
    });
    buttons.add(new emcJButton("Cancel"){
    @Override
    public void doActionPerformed (ActionEvent evt) {
     super.doActionPerformed(evt);
     if (EMCDialogFactory.createQuestionDialog(CloseTasksForm.this, "Cancel", "Are you sure you want to cancel?") == JOptionPane.YES_OPTION) {
       CloseTasksForm.this.dispose();
      }
     }
    });
   return emcSetGridBagConstraints.createButtonPanel(buttons);

   }
   
   private Component selectionPane() {

    lkpClient = new EMCLookup(new SOPCustomersMenu());
    lkpClient.setPopup(new EMCLookupPopup(new SOPCustomers(),"customerId",userData));
    fromDatePicker = new EMCDatePicker();
    toDatePicker = new EMCDatePicker();
    lkpTask = new EMCLookup(new emc.menus.developertools.Bugs());
    lkpTask.setPopup(new EMCLookupPopup(new DevBugsTable(), "bugNumber", userData));
    Component[][] comp = {
        {new emcJLabel("Client"),lkpClient},
        {new emcJLabel("From"),fromDatePicker},
        {new emcJLabel("To"),toDatePicker},
        {new emcJLabel("Task Number"),lkpTask}
    };
    return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);

   }
   
}

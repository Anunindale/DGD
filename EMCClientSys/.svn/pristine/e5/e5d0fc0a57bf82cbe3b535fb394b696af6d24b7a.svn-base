/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.action.requirementsplanning;

import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCMultiProcessingThreadManager;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
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
public class InventoryRequirementsPlanningMaintenance extends BaseInternalFrame {

    private EMCUserData userData;
    private emcYesNoComponent ynCleanup;
    private emcYesNoComponent ynRepopulate;
    private boolean busy = false;

    public InventoryRequirementsPlanningMaintenance(EMCUserData userData) {
        super("Requirements Planning Maintenance", true, true, true, true, userData);
        this.setBounds(20, 20, 450, 150);
        this.userData = userData.copyUserDataAndDataList();
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(selectionPane(), BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcJTabbedPane selectionPane() {
        ynCleanup = new emcYesNoComponent() {

            @Override
            public void setSelectedItem(Object anObject) {
                super.setSelectedItem(anObject);
                if (ynRepopulate != null && !busy) {
                    busy = true;
                    if ("Yes".equals(anObject)) {
                        ynRepopulate.setSelectedItem("No");
                    } else {
                        ynRepopulate.setSelectedItem("Yes");
                    }
                    busy = false;
                }
            }
        };
        ynCleanup.setSelectedItem("Yes");

        ynRepopulate = new emcYesNoComponent() {

            @Override
            public void setSelectedItem(Object anObject) {
                super.setSelectedItem(anObject);
                if (ynCleanup != null && !busy) {
                    busy = true;
                    if ("Yes".equals(anObject)) {
                        ynCleanup.setSelectedItem("No");
                    } else {
                        ynCleanup.setSelectedItem("Yes");
                    }
                    busy = false;
                }
            }
        };
        ynRepopulate.setSelectedItem("No");

        Component[][] comp = {{new emcJLabel("Clean Up Planning"), ynCleanup},
            {new emcJLabel("Repopulate Planning"), ynRepopulate}};

        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Maintenance", emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true));

        return tabbed;
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (ynCleanup.getSelectedItem().equals("Yes")) {
                    EMCCommandClass cmd = new EMCCommandClass(ServerInventoryMethods.CLEAN_UP_REQUIREMENTS);

                    List toSend = new ArrayList();

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                    if (toSend.size() > 1 && (Boolean) toSend.get(1)) {
                        Logger.getLogger("emc").log(Level.INFO, "Requirements Planning Cleaned Up.", userData);
                    } else {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to clean up Requirements Planning.", userData);
                    }
                } else if (ynRepopulate.getSelectedItem().equals("Yes")) {
                    System.out.println(new Date().toString());
                    //Clear Table
                    EMCCommandClass cmd = new EMCCommandClass(ServerInventoryMethods.CLEAR_TABLE_FOR_REPOPULATION);
                    List<Object> toSend = new ArrayList<Object>();
                    EMCWSManager.executeGenericWS(cmd, new ArrayList<Object>(), userData);
                    //Get Forecast Items
                    cmd = new EMCCommandClass(ServerInventoryMethods.GET_FORECAST_ITEMS);
                    toSend = new ArrayList<Object>();
                    toSend = EMCWSManager.executeGenericWS(cmd, new ArrayList<Object>(), userData);
                    //Prepare for forecast
                    EMCMultiProcessingThreadManager multiThead = new EMCMultiProcessingThreadManager();
                    List<List<Object>> multiProcessList = new ArrayList<List<Object>>();
                    List<String> itemList;
                    if (toSend.size() > 1 && toSend.get(1) instanceof List && !((List) toSend.get(1)).isEmpty()) {
                        itemList = (List<String>) toSend.get(1);
                        for (String item : itemList) {
                            toSend = new ArrayList<Object>();
                            toSend.add(item);
                            multiProcessList.add(toSend);
                        }
                        cmd = new EMCCommandClass(ServerInventoryMethods.REPOPULATE_WITH_FORECAST);
                        //Populate with forecast
                        try {
                            multiThead.executeMultiProcessingWS(cmd, multiProcessList, userData);
                        } catch (IllegalThreadStateException ex) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute multi thread process: " + ex.getMessage());
                            return;
                        }
                    }

                    //Get Sales Items
                    cmd = new EMCCommandClass(ServerInventoryMethods.GET_SALES_ITEMS);
                    toSend = new ArrayList<Object>();
                    toSend = EMCWSManager.executeGenericWS(cmd, new ArrayList<Object>(), userData);
                    //Prepare for sales
                    multiThead = new EMCMultiProcessingThreadManager();
                    multiProcessList = new ArrayList<List<Object>>();
                    if (toSend.size() > 1 && toSend.get(1) instanceof List && !((List) toSend.get(1)).isEmpty()) {
                        itemList = (List<String>) toSend.get(1);
                        for (String item : itemList) {
                            toSend = new ArrayList<Object>();
                            toSend.add(item);
                            multiProcessList.add(toSend);
                        }
                        cmd = new EMCCommandClass(ServerInventoryMethods.REPOPULATE_WITH_SALES);
                        //Populate with Sales
                        try {
                            multiThead.executeMultiProcessingWS(cmd, multiProcessList, userData);
                        } catch (IllegalThreadStateException ex) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute multi thread process: " + ex.getMessage());
                            return;
                        }
                    }

                    //Get Planned Purchase Items
                    cmd = new EMCCommandClass(ServerInventoryMethods.GET_PLANNED_PURCHASING_ITEMS);
                    toSend = new ArrayList<Object>();
                    toSend = EMCWSManager.executeGenericWS(cmd, new ArrayList<Object>(), userData);
                    //Prepare for Planned Purchase
                    multiThead = new EMCMultiProcessingThreadManager();
                    multiProcessList = new ArrayList<List<Object>>();
                    if (toSend.size() > 1 && toSend.get(1) instanceof List && !((List) toSend.get(1)).isEmpty()) {
                        itemList = (List<String>) toSend.get(1);
                        for (String item : itemList) {
                            toSend = new ArrayList<Object>();
                            toSend.add(item);
                            multiProcessList.add(toSend);
                        }
                        cmd = new EMCCommandClass(ServerInventoryMethods.REPOPULATE_WITH_PLANNED_PURCHASING);
                        //Populate with Planned Purchase
                        try {
                            multiThead.executeMultiProcessingWS(cmd, multiProcessList, userData);
                        } catch (IllegalThreadStateException ex) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute multi thread process: " + ex.getMessage());
                            return;
                        }
                    }

                    //Get Purchase Items
                    cmd = new EMCCommandClass(ServerInventoryMethods.GET_PURCHASING_ITEMS);
                    toSend = new ArrayList<Object>();
                    toSend = EMCWSManager.executeGenericWS(cmd, new ArrayList<Object>(), userData);
                    //Prepare for Purchase
                    multiThead = new EMCMultiProcessingThreadManager();
                    multiProcessList = new ArrayList<List<Object>>();
                    if (toSend.size() > 1 && toSend.get(1) instanceof List && !((List) toSend.get(1)).isEmpty()) {
                        itemList = (List<String>) toSend.get(1);
                        for (String item : itemList) {
                            toSend = new ArrayList<Object>();
                            toSend.add(item);
                            multiProcessList.add(toSend);
                        }
                        cmd = new EMCCommandClass(ServerInventoryMethods.REPOPULATE_WITH_PURCHASING);
                        //Populate with Purchase
                        try {
                            multiThead.executeMultiProcessingWS(cmd, multiProcessList, userData);
                        } catch (IllegalThreadStateException ex) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute multi thread process: " + ex.getMessage());
                            return;
                        }
                    }

                    //Get Planned Production Items
                    cmd = new EMCCommandClass(ServerInventoryMethods.GET_PLANNED_PRODUCTION_ITEMS);
                    toSend = new ArrayList<Object>();
                    toSend = EMCWSManager.executeGenericWS(cmd, new ArrayList<Object>(), userData);
                    //Prepare for Planned Production
                    multiThead = new EMCMultiProcessingThreadManager();
                    multiProcessList = new ArrayList<List<Object>>();
                    if (toSend.size() > 1 && toSend.get(1) instanceof List && !((List) toSend.get(1)).isEmpty()) {
                        itemList = (List<String>) toSend.get(1);
                        for (String item : itemList) {
                            toSend = new ArrayList<Object>();
                            toSend.add(item);
                            multiProcessList.add(toSend);
                        }
                        cmd = new EMCCommandClass(ServerInventoryMethods.REPOPULATE_WITH_PLANNED_PPRODUCTION);
                        //Populate with Planned Production
                        try {
                            multiThead.executeMultiProcessingWS(cmd, multiProcessList, userData);
                        } catch (IllegalThreadStateException ex) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute multi thread process: " + ex.getMessage());
                            return;
                        }
                    }

                    //Get Production Items
                    cmd = new EMCCommandClass(ServerInventoryMethods.GET_PRODUCTION_ITEMS);
                    toSend = new ArrayList<Object>();
                    toSend = EMCWSManager.executeGenericWS(cmd, new ArrayList<Object>(), userData);
                    //Prepare for Production
                    multiThead = new EMCMultiProcessingThreadManager();
                    multiProcessList = new ArrayList<List<Object>>();
                    if (toSend.size() > 1 && toSend.get(1) instanceof List && !((List) toSend.get(1)).isEmpty()) {
                        itemList = (List<String>) toSend.get(1);
                        for (String item : itemList) {
                            toSend = new ArrayList<Object>();
                            toSend.add(item);
                            multiProcessList.add(toSend);
                        }
                        cmd = new EMCCommandClass(ServerInventoryMethods.REPOPULATE_WITH_PRODUCTION);
                        //Populate with  Production
                        try {
                            multiThead.executeMultiProcessingWS(cmd, multiProcessList, userData);
                        } catch (IllegalThreadStateException ex) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute multi thread process: " + ex.getMessage());
                            return;
                        }
                    }
                    Logger.getLogger("emc").log(Level.INFO, "Table repopulated.", userData);
                    System.out.println(new Date().toString());
                }
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                InventoryRequirementsPlanningMaintenance.this.dispose();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}

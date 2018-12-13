/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.safetystock.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePicker;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.frame.EMCDesktop;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.inventory.InventoryItemGroup;
import emc.entity.inventory.InventorySafetyStockGenerationRules;
import emc.entity.sop.SOPCustomers;
import emc.enums.inventory.safetystock.SafetyStockType;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.menus.debtors.menuitems.display.DebtorsMarketingGroup;
import emc.menus.inventory.menuitems.display.ItemGroups;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author wikus
 */
public class InventorySafetyStockGenerationDialog extends emcJDialog {

    private EMCUserData userData;
    private emcDataRelationManagerUpdate dataManager;
    //Components
    private EMCLookup lkpCustomerGroup;
    private EMCLookup lkpCustomer;
    private EMCLookup lkpItemGroup;
    private EMCLookup lkpItem;
    private EMCDatePicker dpDeleteHistoryOlderThan;
    private emcJComboBox cbSafetyStockType;
    //delete
    private boolean forDeletion;

    public InventorySafetyStockGenerationDialog(EMCDesktop owner, emcDataRelationManagerUpdate dataManager, boolean forDeletion, EMCUserData userData) {
        super(owner, forDeletion ? "Delete Safety Stock" : "Generate Safety Stock", true);
        this.userData = userData.copyUserDataAndDataList();
        this.dataManager = dataManager;
        this.forDeletion = forDeletion;
        initDialog();
        this.pack();
        this.setVisible(true);
    }

    private void initDialog() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Generate", generationPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcJPanel generationPane() {
        lkpCustomerGroup = new EMCLookup(new DebtorsMarketingGroup());
        lkpCustomerGroup.setPopup(new EMCLookupPopup(new emc.entity.debtors.DebtorsMarketingGroup(), "marketingGroup", userData));

        lkpCustomer = new EMCLookup(new SOPCustomersMenu());
        lkpCustomer.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", userData));

        lkpItemGroup = new EMCLookup(new ItemGroups());
        lkpItemGroup.setPopup(new EMCLookupPopup(new InventoryItemGroup(), "itemGroup", userData));

        lkpItem = EMCItemLookupFactory.createItemFormLookup(userData);

        dpDeleteHistoryOlderThan = new EMCDatePicker();

        cbSafetyStockType = new emcJComboBox(SafetyStockType.values());
        cbSafetyStockType.setSelectedItem(SafetyStockType.SS.toString());

        emcJPanel thePanel;
        if (forDeletion) {
            Component[][] comp = {{new emcJLabel(dataManager.getFieldEmcLabel("customerGroup")), lkpCustomerGroup},
                                  {new emcJLabel(dataManager.getFieldEmcLabel("customer")), lkpCustomer},
                                  {new emcJLabel(dataManager.getFieldEmcLabel("itemGroup")), lkpItemGroup},
                                  {new emcJLabel(dataManager.getFieldEmcLabel("item")), lkpItem},
                                  {new emcJLabel()},
                                  {new emcJLabel("Safety Stock Type"), cbSafetyStockType},
                                  {new emcJLabel("Delete History Older Than"), dpDeleteHistoryOlderThan}
            };

            thePanel = emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
        } else {
            Component[][] comp = {{new emcJLabel(dataManager.getFieldEmcLabel("customerGroup")), lkpCustomerGroup},
                                  {new emcJLabel(dataManager.getFieldEmcLabel("customer")), lkpCustomer},
                                  {new emcJLabel(dataManager.getFieldEmcLabel("itemGroup")), lkpItemGroup},
                                  {new emcJLabel(dataManager.getFieldEmcLabel("item")), lkpItem},
                                  {new emcJLabel()},
                                  {new emcJLabel("Delete History Older Than"), dpDeleteHistoryOlderThan}
            };

            thePanel = emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
        }
        
        return thePanel;
    }

    private emcJPanel buttonPane() {
        emcJButton btnGenerate = new emcJButton(forDeletion ? "Delete" : "Generate") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (forDeletion) {
                    if (EMCDialogFactory.createQuestionDialog(InventorySafetyStockGenerationDialog.this, "Delete Safety Stock?", "Are you sure you want to delete the safety stock records?") != JOptionPane.YES_OPTION) {
                        return;
                    }
                } else {
                    if (EMCDialogFactory.createQuestionDialog(InventorySafetyStockGenerationDialog.this, "Generate Safety Stock?", "Are you sure you want to generate safety stock records?") != JOptionPane.YES_OPTION) {
                        return;
                    }
                }

                InventorySafetyStockGenerationRules rules = new InventorySafetyStockGenerationRules();
                rules.setCustomerGroup((String) lkpCustomerGroup.getValue());
                rules.setCustomer((String) lkpCustomer.getValue());
                rules.setItemGroup((String) lkpItemGroup.getValue());
                rules.setItem((String) lkpItem.getValue());


                EMCCommandClass cmd;
                if (forDeletion) {
                    cmd = new EMCCommandClass(ServerInventoryMethods.DELETE_SAFETY_STOCK);
                } else {
                    cmd = new EMCCommandClass(ServerInventoryMethods.GENERATE_SAFETY_STOCK);
                }

                List toSend = new ArrayList();
                toSend.add(cbSafetyStockType.getSelectedItem());
                toSend.add(rules);
                toSend.add(dpDeleteHistoryOlderThan.getDate());

                toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                if (toSend.size() > 1 && (Boolean) toSend.get(1)) {
                    if (forDeletion) {
                        Logger.getLogger("emc").log(Level.INFO, "Safety Stock Deleted Successfully.", userData);
                    } else {
                        Logger.getLogger("emc").log(Level.INFO, "Safety Stock Generated Successfully.", userData);
                    }
                    dataManager.refreshData();

                    InventorySafetyStockGenerationDialog.this.dispose();
                }
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                InventorySafetyStockGenerationDialog.this.dispose();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnGenerate);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}

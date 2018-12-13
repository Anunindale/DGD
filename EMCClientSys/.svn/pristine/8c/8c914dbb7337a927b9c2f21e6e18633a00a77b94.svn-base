/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.batchconsolidation.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.documents.EMCBigDecimalDocument;
import emc.app.components.documents.EMCIntegerDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.frame.EMCDesktop;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryParameters;
import emc.entity.inventory.InventoryProductGroup;
import emc.entity.inventory.InventoryWarehouse;
import emc.enums.enumQueryTypes;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.inventory.menuitems.display.InventoryProductGroupMenu;
import emc.menus.inventory.menuitems.display.LocationMenu;
import emc.menus.inventory.menuitems.display.Warehouse;
import emc.methods.developertools.ServerDeveloperToolMethods;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author wikus
 */
public class InventoryBatchConsolidationCreateDialog extends emcJDialog {
    
    private emcDataRelationManagerUpdate dataManager;
    private EMCUserData userData;
    private EMCLookup lkpWarehouse;
    private EMCLookup lkpLocation;
    private EMCLookup lkpProductGroup;
    private emcJTextField txtConsolidationQty;
    private emcJTextField txtMaxCrateQty;
    private emcJTextField txtCratesToVisit;
    private boolean createdConsolidation = false;
    
    public InventoryBatchConsolidationCreateDialog(EMCDesktop desktop, emcDataRelationManagerUpdate dataManager, EMCUserData userData) {
        super(desktop, "Create Batch Consolidation", true);
        
        InventoryBatchConsolidationCreateDialog.this.dataManager = dataManager;
        InventoryBatchConsolidationCreateDialog.this.userData = userData.copyUserDataAndDataList();
        
        initDialog();
        
        InventoryBatchConsolidationCreateDialog.this.pack();
        InventoryBatchConsolidationCreateDialog.this.setVisible(true);
    }
    
    private void initDialog() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Create Consolidation", selectionPane());
        
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        
        this.setContentPane(contentPane);
    }
    
    private Component selectionPane() {
        lkpWarehouse = new EMCLookup(new Warehouse()) {
            @Override
            public void setValue(Object value) {
                super.setValue(value);
                
                if (lkpLocation != null) {
                    if (Functions.checkBlank(value)) {
                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class);
                        lkpLocation.setTheQuery(query);
                    } else {
                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class);
                        query.addAnd("warehouseId", value);
                        lkpLocation.setTheQuery(query);
                    }
                }
            }
        };
        lkpWarehouse.setPopup(new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", userData));
        
        lkpLocation = new EMCLookup(new LocationMenu());
        lkpLocation.setPopup(new EMCLookupPopup(new InventoryLocation(), "locationId", userData));
        
        lkpProductGroup = new EMCLookup(new InventoryProductGroupMenu());
        lkpProductGroup.setPopup(new EMCLookupPopup(new InventoryProductGroup(), "productGroupId", userData));
        
        if (!Functions.checkBlank(dataManager.getLastFieldValueAt("warehouse"))) {
            lkpWarehouse.setValue(dataManager.getLastFieldValueAt("warehouse"));
        }
        
        if (!Functions.checkBlank(dataManager.getLastFieldValueAt("location"))) {
            lkpLocation.setValue(dataManager.getLastFieldValueAt("location"));
        }
        
        if (!Functions.checkBlank(dataManager.getLastFieldValueAt("productGroup"))) {
            lkpProductGroup.setValue(dataManager.getLastFieldValueAt("productGroup"));
        }
        
        txtConsolidationQty = new emcJTextField(new EMCBigDecimalDocument());
        txtMaxCrateQty = new emcJTextField(new EMCBigDecimalDocument());
        txtCratesToVisit = new emcJTextField(new EMCIntegerDocument());
        
        InventoryParameters param = getInventoryParameters();
        
        if (param != null) {
            if (param.getConsolidationCrateQuantity() != null) {
                txtConsolidationQty.setText(param.getConsolidationCrateQuantity().toString());
            } else {
                txtConsolidationQty.setText(BigDecimal.ZERO.toString());
            }
            if (param.getMaxCrateQuantity() != null) {
                txtMaxCrateQty.setText(param.getMaxCrateQuantity().toString());
            } else {
                txtMaxCrateQty.setText(BigDecimal.ZERO.toString());
            }
            txtCratesToVisit.setText(Integer.valueOf(param.getMaxNumberOfCrates()).toString());
        }
        
        Component[][] comp = {{new emcJLabel("Warehouse"), lkpWarehouse},
                              {new emcJLabel("Location"), lkpLocation},
                              {new emcJLabel("Product Group"), lkpProductGroup},
                              {new emcJLabel()},
                              {new emcJLabel("Consolidate crates with quantity less than"), txtConsolidationQty},
                              {new emcJLabel("Max crate quantity"), txtMaxCrateQty},
                              {new emcJLabel("Max number of crates to visit"), txtCratesToVisit}};
        
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }
    
    private Component buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                
                int createConsolidation = EMCDialogFactory.createQuestionDialog(utilFunctions.findEMCDesktop(this), "Create Consolidation", "Are you sure you want to create the batch consolidation journals?");
                
                if (createConsolidation == JOptionPane.YES_OPTION) {
                    EMCCommandClass cmd = new EMCCommandClass(ServerInventoryMethods.CREATE_BATCH_CONSOLIDATION);
                    
                    List toSend = new ArrayList();
                    toSend.add(dataManager.getLastFieldValueAt("consolidationNumber"));
                    toSend.add(Functions.checkBlank(lkpWarehouse.getValue()) ? "" : lkpWarehouse.getValue());
                    toSend.add(Functions.checkBlank(lkpLocation.getValue()) ? "" : lkpLocation.getValue());
                    toSend.add(Functions.checkBlank(lkpProductGroup.getValue()) ? "" : lkpProductGroup.getValue());
                    toSend.add(Functions.checkBlank(txtConsolidationQty.getText()) ? BigDecimal.ZERO : new BigDecimal(txtConsolidationQty.getText()));
                    toSend.add(Functions.checkBlank(txtMaxCrateQty) ? BigDecimal.ZERO : new BigDecimal(txtMaxCrateQty.getText()));
                    toSend.add(Functions.checkBlank(txtCratesToVisit) ? 0 : Integer.valueOf(txtCratesToVisit.getText()));
                    
                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
                    
                    if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                        dataManager.refreshRecordIgnoreFocus(dataManager.getLastRowAccessed());
                        
                        Logger.getLogger("emc").log(Level.INFO, "Batch consolidation journals created.", userData);
                        
                        InventoryBatchConsolidationCreateDialog.this.dispose();
                        
                        createdConsolidation = true;
                    } else {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to create batch consolidation journals.", userData);
                    }
                }
                
                
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                InventoryBatchConsolidationCreateDialog.this.dispose();
            }
        };
        
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);
        
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
    
    public boolean createdConsolidation() {
        return createdConsolidation;
    }
    
    private InventoryParameters getInventoryParameters() {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryParameters.class);
        
        EMCCommandClass cmd = new EMCCommandClass(ServerDeveloperToolMethods.EXECUTE_EMCQUERY);
        
        List toSend = new ArrayList();
        toSend.add(query);
        
        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
        
        if (toSend.size() > 1 && toSend.get(1) instanceof InventoryParameters) {
            return (InventoryParameters) toSend.get(1);
        } else {
            return null;
        }
    }
}

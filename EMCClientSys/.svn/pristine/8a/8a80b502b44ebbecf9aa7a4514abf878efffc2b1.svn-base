/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.batchconsolidation;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.stock.StockDRM;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.inventory.emcStockButton;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.reporttools.JasperInformation;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCReportWSManager;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.inventory.InventoryBatchConsolidationLines;
import emc.entity.inventory.InventoryBatchConsolidationMaster;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryProductGroup;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.datasource.InventoryBatchConsolidationLinesDS;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.inventory.display.batchconsolidation.resources.InventoryBatchConsolidationCreateDialog;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.inventory.menuitems.display.InventoryProductGroupMenu;
import emc.menus.inventory.menuitems.display.ItemMaster;
import emc.menus.inventory.menuitems.display.LocationMenu;
import emc.menus.inventory.menuitems.display.Warehouse;
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
import javax.swing.JSplitPane;

/**
 *
 * @author wikus
 */
public class InventoryBatchConsolidationForm extends BaseInternalFrame {

    //Master
    private EMCUserData masterUD;
    private emcDataRelationManagerUpdate masterDRM;
    //Lines
    private EMCUserData linesUD;
    private StockDRM linesDRM;

    public InventoryBatchConsolidationForm(EMCUserData userData) {
        super("Batch Consolidation", true, true, true, true, userData);
        this.setBounds(20, 20, 1000, 600);
        //Master
        masterUD = userData.copyUserDataAndDataList();
        masterDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new InventoryBatchConsolidationMaster(), masterUD), masterUD);
        masterDRM.setTheForm(InventoryBatchConsolidationForm.this);
        masterDRM.setFormTextId1("consolidationNumber");
        masterDRM.setFormTextId2("description");
        //Lines
        linesUD = userData.copyUserData();
        StockDRMParameters stockParam = new StockDRMParameters("itemId", "dimension1", "dimension2", "dimension3", "transId", "serialNo", "fromBatch", "warehouse", "fromLocation");
        linesDRM = new StockDRM(new emcGenericDataSourceUpdate(new InventoryBatchConsolidationLinesDS(), linesUD), stockParam, linesUD) {
            @Override
            public void insertRowCache(int rowIndex, boolean addRowAfter) {
                Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to add rows to this table.", masterUD);
            }

            @Override
            public void deleteRowCache(int rowIndex) {
                Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to remove rows from this table.", masterUD);
            }

            @Override
            public void updatePersist(int rowIndex) {
                super.updatePersist(rowIndex);
                if (getLastUpdateStatus()) {
                    rowIndex = getLastRowAccessed();

                    masterDRM.refreshRecordIgnoreFocus(masterDRM.getLastRowAccessed());

                    linesDRM.setSelectedRow(rowIndex);
                }
            }
        };
        linesDRM.setTheForm(InventoryBatchConsolidationForm.this);
        linesDRM.setFormTextId1("itemReference");
        linesDRM.setFormTextId2("itemDescription");
        //Link
        masterDRM.setHeaderColumnID("consolidationNumber");
        masterDRM.setLinesTable(linesDRM);
        linesDRM.setRelationColumnToHeader("consolidationNumber");
        linesDRM.setHeaderTable(masterDRM);

        InventoryBatchConsolidationForm.this.setDataManager(masterDRM);

        emcJSplitPane topBottonSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, masterPane(), linesPane());
        topBottonSplit.setDividerLocation(250);
        InventoryBatchConsolidationForm.this.setContentPane(topBottonSplit);
    }

    private emcJPanel masterPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", masterOverviewPane());
        tabbed.add("Detail", masterDetailPane());

        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        thePanel.add(tabbed, BorderLayout.CENTER);
        thePanel.add(masterButtonPane(), BorderLayout.EAST);

        return thePanel;
    }

    private emcJPanel linesPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", linesOverviewPane());
        tabbed.add("Detail", linesDetailPane());

        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        thePanel.add(tabbed, BorderLayout.CENTER);
        thePanel.add(linesButtonPane(), BorderLayout.EAST);

        return thePanel;
    }

    private emcTablePanelUpdate masterOverviewPane() {
        List<String> keys = new ArrayList<String>();
        keys.add("consolidationNumber");
        keys.add("description");
        keys.add("createdDate");
        keys.add("createdBy");
        keys.add("postedDate");
        keys.add("consolidationStatus");

        emcTableModelUpdate model = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);

        table.setColumnEditable("createdDate", false);
        table.setColumnEditable("createdBy", false);
        table.setColumnEditable("postedDate", false);
        table.setColumnEditable("consolidationStatus", false);

        masterDRM.setMainTableComponent(table);
        masterDRM.setTablePanel(tableScroll);

        return tableScroll;
    }

    private emcJPanel masterDetailPane() {
        EMCLookupFormComponent lkpWarehouse = new EMCLookupFormComponent(new Warehouse(), masterDRM, "warehouse");
        lkpWarehouse.setPopup(new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", masterUD));

        EMCLookupFormComponent lkpLocation = new EMCLookupFormComponent(new LocationMenu(), masterDRM, "location");
        lkpLocation.setPopup(new EMCLookupPopup(new InventoryLocation(), "locationId", masterUD));

        EMCLookupFormComponent lkpProductGroup = new EMCLookupFormComponent(new InventoryProductGroupMenu(), masterDRM, "productGroup");
        lkpProductGroup.setPopup(new EMCLookupPopup(new InventoryProductGroup(), "productGroupId", masterUD));

        Component[][] comp = {{new emcJLabel("Warehouse"), lkpWarehouse, new emcJLabel("Location"), lkpLocation},
                              {new emcJLabel("Product Group"), lkpProductGroup}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Detail");
    }

    private emcJPanel masterButtonPane() {
        emcJButton btnCreate = new emcJButton("Create") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                InventoryBatchConsolidationCreateDialog createDialog = new InventoryBatchConsolidationCreateDialog(utilFunctions.findEMCDesktop(this), masterDRM, masterUD);
                if (createDialog.createdConsolidation()) {
                    printPickingList();
                }
            }
        };
        emcJButton btnApprove = new emcJButton("Approve") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                int proceed = EMCDialogFactory.createQuestionDialog(utilFunctions.findEMCDesktop(this), "Approve", "Are you sure you want to approve the batch consolidation?");

                if (proceed == JOptionPane.YES_OPTION) {
                    boolean approveConsolidation = true;

                    EMCCommandClass cmd = new EMCCommandClass(ServerInventoryMethods.FETCH_MOVEMENT_JOURNAL_FOR_CONSOLIDATION_APPROVAL);

                    List toSend = new ArrayList();
                    toSend.add(masterDRM.getLastFieldValueAt("consolidationNumber"));

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);
                    if (toSend.size() > 1 && toSend.get(1) instanceof List) {
                        List<String> journals = (List<String>) toSend.get(1);


                        for (String journal : journals) {
                            cmd = new EMCCommandClass(ServerInventoryMethods.APPROVE_JOURNAL);

                            toSend = new ArrayList();
                            toSend.add(journal);

                            toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);

                            if (!(toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1))) {
                                Logger.getLogger("emc").log(Level.SEVERE, "Failed to approve movement journal " + journal, masterUD);
                                approveConsolidation = false;
                            }
                        }
                    }

                    cmd = new EMCCommandClass(ServerInventoryMethods.FETCH_TRANSFER_JOURNAL_FOR_CONSOLIDATION_APPROVAL);

                    toSend = new ArrayList();
                    toSend.add(masterDRM.getLastFieldValueAt("consolidationNumber"));

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);
                    if (toSend.size() > 1 && toSend.get(1) instanceof List) {
                        List<String> journals = (List<String>) toSend.get(1);


                        for (String journal : journals) {
                            cmd = new EMCCommandClass(ServerInventoryMethods.APPROVE_JOURNAL);

                            toSend = new ArrayList();
                            toSend.add(journal);

                            toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);

                            if (!(toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1))) {
                                Logger.getLogger("emc").log(Level.SEVERE, "Failed to approve movement journal " + journal, masterUD);
                                approveConsolidation = false;
                            }
                        }
                    }

                    if (approveConsolidation) {
                        cmd = new EMCCommandClass(ServerInventoryMethods.APPROVE_CONSOLIDATION);

                        toSend = new ArrayList();
                        toSend.add(masterDRM.getLastFieldValueAt("consolidationNumber"));

                        toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);

                        if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                            Logger.getLogger("emc").log(Level.INFO, "Batch Consolidation approved.", masterUD);
                            masterDRM.refreshRecordIgnoreFocus(masterDRM.getLastRowAccessed());
                        } else {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to approve batch consolidation.", masterUD);
                        }
                    }
                }
            }
        };
        emcJButton btnUnApprove = new emcJButton("Unapprove") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                int proceed = EMCDialogFactory.createQuestionDialog(utilFunctions.findEMCDesktop(this), "Unapprove", "Are you sure you want to unapprove the batch consolidation?");

                if (proceed == JOptionPane.YES_OPTION) {
                    boolean unapproveConsolidation = true;

                    EMCCommandClass cmd = new EMCCommandClass(ServerInventoryMethods.FETCH_MOVEMENT_JOURNAL_FOR_CONSOLIDATION_UN_APPROVAL);

                    List toSend = new ArrayList();
                    toSend.add(masterDRM.getLastFieldValueAt("consolidationNumber"));

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);
                    if (toSend.size() > 1 && toSend.get(1) instanceof List) {
                        List<String> journals = (List<String>) toSend.get(1);


                        for (String journal : journals) {
                            cmd = new EMCCommandClass(ServerInventoryMethods.UNAPPROVE_JOURNAL);

                            toSend = new ArrayList();
                            toSend.add(journal);

                            toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);

                            if (!(toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1))) {
                                Logger.getLogger("emc").log(Level.SEVERE, "Failed to unapprove movement journal " + journal, masterUD);
                                unapproveConsolidation = false;
                            }
                        }
                    }

                    cmd = new EMCCommandClass(ServerInventoryMethods.FETCH_TRANSFER_JOURNAL_FOR_CONSOLIDATION_UN_APPROVAL);

                    toSend = new ArrayList();
                    toSend.add(masterDRM.getLastFieldValueAt("consolidationNumber"));

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);
                    if (toSend.size() > 1 && toSend.get(1) instanceof List) {
                        List<String> journals = (List<String>) toSend.get(1);


                        for (String journal : journals) {
                            cmd = new EMCCommandClass(ServerInventoryMethods.UNAPPROVE_JOURNAL);

                            toSend = new ArrayList();
                            toSend.add(journal);

                            toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);

                            if (!(toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1))) {
                                Logger.getLogger("emc").log(Level.SEVERE, "Failed to unapprove movement journal " + journal, masterUD);
                                unapproveConsolidation = false;
                            }
                        }
                    }

                    if (unapproveConsolidation) {
                        cmd = new EMCCommandClass(ServerInventoryMethods.UN_APPROVE_CONSOLIDATION);

                        toSend = new ArrayList();
                        toSend.add(masterDRM.getLastFieldValueAt("consolidationNumber"));

                        toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);

                        if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                            Logger.getLogger("emc").log(Level.INFO, "Batch Consolidation unapproved.", masterUD);
                            masterDRM.refreshRecordIgnoreFocus(masterDRM.getLastRowAccessed());
                        } else {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to unapprove batch consolidation.", masterUD);
                        }
                    }
                }
            }
        };
        emcJButton btnPost = new emcJButton("Post") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);


                int proceed = EMCDialogFactory.createQuestionDialog(utilFunctions.findEMCDesktop(this), "Post", "Are you sure you want to post the batch consolidation?");

                if (proceed == JOptionPane.YES_OPTION) {
                    boolean postConsolidation = true;

                    EMCCommandClass cmd = new EMCCommandClass(ServerInventoryMethods.FETCH_MOVEMENT_JOURNAL_FOR_CONSOLIDATION_POSTING);

                    List toSend = new ArrayList();
                    toSend.add(masterDRM.getLastFieldValueAt("consolidationNumber"));

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);
                    if (toSend.size() > 1 && toSend.get(1) instanceof List) {
                        List<String> journals = (List<String>) toSend.get(1);


                        for (String journal : journals) {
                            cmd = new EMCCommandClass(ServerInventoryMethods.POST_INVENTORYJOURNAL_FROM_NUMBER);

                            toSend = new ArrayList();
                            toSend.add(journal);

                            toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);

                            if (!(toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1))) {
                                Logger.getLogger("emc").log(Level.SEVERE, "Failed to post journal " + journal, masterUD);
                                postConsolidation = false;
                            }
                        }
                    }

                    cmd = new EMCCommandClass(ServerInventoryMethods.FETCH_TRANSFER_JOURNAL_FOR_CONSOLIDATION_POSTING);

                    toSend = new ArrayList();
                    toSend.add(masterDRM.getLastFieldValueAt("consolidationNumber"));

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);
                    if (toSend.size() > 1 && toSend.get(1) instanceof List) {
                        List<String> journals = (List<String>) toSend.get(1);


                        for (String journal : journals) {
                            cmd = new EMCCommandClass(ServerInventoryMethods.POST_INVENTORYJOURNAL_FROM_NUMBER);

                            toSend = new ArrayList();
                            toSend.add(journal);

                            toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);

                            if (!(toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1))) {
                                Logger.getLogger("emc").log(Level.SEVERE, "Failed to post journal " + journal, masterUD);
                                postConsolidation = false;
                            }
                        }
                    }

                    if (postConsolidation) {
                        cmd = new EMCCommandClass(ServerInventoryMethods.POST_CONSOLIDATION);

                        toSend = new ArrayList();
                        toSend.add(masterDRM.getLastFieldValueAt("consolidationNumber"));

                        toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);

                        if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                            Logger.getLogger("emc").log(Level.INFO, "Batch Consolidation posted.", masterUD);
                            masterDRM.refreshRecordIgnoreFocus(masterDRM.getLastRowAccessed());
                        } else {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to post batch consolidation.", masterUD);
                        }
                    }
                }
            }
        };
        emcJButton btnPrint = new emcJButton("Print") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                printPickingList();
            }
        };

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnCreate);
        buttonList.add(btnApprove);
        buttonList.add(btnUnApprove);
        buttonList.add(btnPost);
        buttonList.add(btnPrint);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private emcTablePanelUpdate linesOverviewPane() {
        List<String> keys = new ArrayList<String>();
        keys.add("lineNo");
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("quantity");
        keys.add("shortPicked");
        keys.add("fromBatch");
        keys.add("toBatch");

        emcTableModelUpdate model = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);

        table.setColumnEditable("lineNo", false);
        table.setColumnEditable("itemReference", false);
        table.setColumnEditable("itemDescription", false);
        table.setColumnEditable("dimension1", false);
        table.setColumnEditable("dimension3", false);
        table.setColumnEditable("dimension2", false);
        table.setColumnEditable("quantity", false);
        table.setColumnEditable("fromBatch", false);
        table.setColumnEditable("toBatch", false);

        EMCLookupJTableComponent lkpItem = new EMCLookupJTableComponent(new ItemMaster());
        lkpItem.setPopup(new EMCLookupPopup(new InventoryItemMaster(), "itemId", linesUD));
        table.setLookupToColumn("itemReference", lkpItem);

        EMCLookupJTableComponent lkpDim1 = new EMCLookupJTableComponent(new Dimension1());
        lkpDim1.setPopup(new EMCLookupPopup(new InventoryDimension1(), "dimensionId", linesUD));
        table.setLookupToColumn("dimension1", lkpDim1);

        EMCLookupJTableComponent lkpDim2 = new EMCLookupJTableComponent(new Dimension2());
        lkpDim2.setPopup(new EMCLookupPopup(new InventoryDimension2(), "dimensionId", linesUD));
        table.setLookupToColumn("dimension2", lkpDim2);

        EMCLookupJTableComponent lkpDim3 = new EMCLookupJTableComponent(new Dimension3());
        lkpDim3.setPopup(new EMCLookupPopup(new InventoryDimension3(), "dimensionId", linesUD));
        table.setLookupToColumn("dimension3", lkpDim3);

        linesDRM.setMainTableComponent(table);
        linesDRM.setTablePanel(tableScroll);

        return tableScroll;
    }

    private emcJPanel linesDetailPane() {
        EMCLookupFormComponent lkpWarehouse = new EMCLookupFormComponent(new Warehouse(), linesDRM, "warehouse");
        lkpWarehouse.setPopup(new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", linesUD));
        lkpWarehouse.setEnabled(false);

        EMCLookupFormComponent lkpFromLocation = new EMCLookupFormComponent(new LocationMenu(), linesDRM, "fromLocation");
        lkpFromLocation.setPopup(new EMCLookupPopup(new InventoryLocation(), "locationId", linesUD));
        lkpFromLocation.setEnabled(false);

        EMCLookupFormComponent lkpToLocation = new EMCLookupFormComponent(new LocationMenu(), linesDRM, "fromLocation");
        lkpToLocation.setPopup(new EMCLookupPopup(new InventoryLocation(), "locationId", linesUD));
        lkpToLocation.setEnabled(false);

        Component[][] comp = {{new emcJLabel("Warehouse"), lkpWarehouse},
                              {new emcJLabel("From Location"), lkpFromLocation, new emcJLabel("To Location"), lkpToLocation}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Detail");
    }

    private emcJPanel linesButtonPane() {
        emcStockButton btnStock = new emcStockButton(InventoryBatchConsolidationForm.this, true);

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnStock);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private void printPickingList() {
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_BATCH_CONSOLIDATION_PICKING_LIST.toString());

        ArrayList toSend = new ArrayList();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationMaster.class);
        query.addTableAnd(InventoryBatchConsolidationLines.class.getName(), "consolidationNumber", InventoryBatchConsolidationMaster.class.getName(), "consolidationNumber");
        query.addTableAnd(InventoryJournalLines.class.getName(), "transferJournalLineRecordId", InventoryBatchConsolidationLines.class.getName(), "recordID");
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventoryJournalLines.class.getName(), "itemId");
        query.addAnd("consolidationNumber", masterDRM.getLastFieldValueAt("consolidationNumber"));
        toSend.add(query);

        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/inventory/batchconsolidation/BatchConsolidationPickingListReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.inventory.batchconsolidation.BatchConsolidationPickingListReportDS");
        jasperInfo.setReportTitle("Batch Consolidation Picking List");

        EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.INVENTORY_BATCH_CONSOLIDATION_PICKING_LIST, toSend, masterUD);
    }
}

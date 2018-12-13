
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.movestock;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJProgressBar;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emcpicker.EMCPickerButton;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.inventory.emcStockButton;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.movestock.InventoryMoveStockLines;
import emc.entity.inventory.movestock.InventoryMoveStockMaster;
import emc.entity.inventory.movestock.InventoryMoveStockMasterDS;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.inventory.menuitems.display.ItemMaster;
import emc.menus.inventory.menuitems.display.LocationMenu;
import emc.menus.inventory.menuitems.display.Warehouse;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JSplitPane;

/**
 *
 * @author wikus
 */
public class InventoryMoveStockForm extends BaseInternalFrame {

    private MoveStockMasterDRM masterDRM;
    private MoveStockLinesDRM linesDRM;
    private EMCUserData masterUD;
    private EMCUserData linesUD;
    private EMCLookup lkpLocation;
    private EMCLookup lkpWarehouse;
    private MovementLRM lrm;

    public InventoryMoveStockForm(EMCUserData userData) {
        super("Move Stock", true, true, true, true, userData);
        setBounds(20, 20, 900, 650);
        try {
            masterUD = userData.copyUserDataAndDataList();
            linesUD = userData.copyUserDataAndDataList();
            StockDRMParameters param = new StockDRMParameters("itemId", "dimension1", "dimension2", "dimension3", null, "serial", "batch", "warehouse", "location");
            masterDRM = new MoveStockMasterDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), new InventoryMoveStockMasterDS(), masterUD), param, masterUD);
            linesDRM = new MoveStockLinesDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), new InventoryMoveStockLines(), linesUD), linesUD);
            this.setDataManager(masterDRM);
            masterDRM.setTheForm(this);
            masterDRM.setFormTextId1("itemReference");
            masterDRM.setFormTextId2("itemDescription");
            masterDRM.setLinesTable(linesDRM);
            masterDRM.setHeaderColumnID("recordID");
            linesDRM.setTheForm(this);
            linesDRM.setFormTextId1("toLocation");
            linesDRM.setFormTextId2("quantity");
            linesDRM.setHeaderTable(masterDRM);
            linesDRM.setRelationColumnToHeader("masterId");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJSplitPane split = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, masterPane(), linesPane());
        split.setDividerLocation(450);
        this.add(split);
    }

    private emcJPanel masterPane() {
        emcJPanel thePanel = new emcJPanel(new BorderLayout(1, 1));
        thePanel.add(controllPane(), BorderLayout.NORTH);
        thePanel.add(masterTablePane(), BorderLayout.CENTER);
        return thePanel;
    }

    private emcJPanel controllPane() {
        emcJPanel thePanel = new emcJPanel(new BorderLayout(1, 1));
        EMCLookupPopup warehousePop = new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", masterUD);
        lkpWarehouse = new EMCLookup(new Warehouse()) {

            @Override
            protected EMCPickerButton makeButton() {
                EMCPickerButton b = super.makeButton();
                b.addFocusListener(new FocusAdapter() {

                    @Override
                    public void focusLost(FocusEvent e) {
                        super.focusLost(e);
                        lrm.doRelation(lkpWarehouse);
                    }
                });
                return b;
            }

            @Override
            protected emcJTextField makeTextField() {
                emcJTextField t = super.makeTextField();
                t.addFocusListener(new FocusAdapter() {

                    @Override
                    public void focusLost(FocusEvent e) {
                        super.focusLost(e);
                        lrm.doRelation(lkpWarehouse);
                    }
                });
                return t;
            }
        };
        lkpWarehouse.setPopup(warehousePop);
        EMCLookupPopup locationPop = new EMCLookupPopup(new InventoryLocation(), "locationId", masterUD);
        lkpLocation = new EMCLookup(new LocationMenu());
        lkpLocation.setPopup(locationPop);
        Component[][] comp = {{new emcJLabel()}, {new emcJLabel("Warehouse"), lkpWarehouse, new emcJLabel("Location"), lkpLocation}, {new emcJLabel()}};
        emcJPanel lookupPane = emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
        lookupPane.setPreferredSize(new Dimension(lookupPane.getPreferredSize().width, 80));
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        emcJButton btnMove = new emcJButton("Move") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                Object warehouse = masterDRM.getFieldValueAt(masterDRM.getLastRowAccessed(), "warehouse");
                Object location = masterDRM.getFieldValueAt(masterDRM.getLastRowAccessed(), "location");
                Object sessionId = masterDRM.getFieldValueAt(masterDRM.getLastRowAccessed(), "masterSessionId");
                if (!Functions.checkBlank(warehouse) && !Functions.checkBlank(location)) {
                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.MOVE_STOCK.toString());
                    List toSend = new ArrayList();
                    toSend.add(warehouse);
                    toSend.add(location);
                    toSend.add(sessionId);
                    List ret = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);
                    if (ret.size() > 1) {
                        boolean moved = (Boolean) ret.get(1);
                        if (moved) {
                            masterDRM.refreshDataIgnoreFocus();
                        }
                    }
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "There is nothing to move.", masterDRM);
                }
            }
        };
        final emcJProgressBar progressBar = new emcJProgressBar();
        emcJButton btnFetch = new emcJButton("Get Data") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                //super.doMouseClicked(evt);
                Object warehouse = lkpWarehouse.getValue();
                Object location = lkpLocation.getValue();
                if (Functions.checkBlank(warehouse) || Functions.checkBlank(location)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Both location and warehouse needs to be specified before data can be fetched", masterUD);
                } else {
                    new DataFetchThread(progressBar, masterDRM, warehouse.toString(), location.toString());
                }
            }
        };
        emcStockButton stockButton = new emcStockButton(this, false, new boolean[]{false, true, true});
        buttonList.add(btnFetch);
        buttonList.add(btnMove);
        buttonList.add(stockButton);
        emcJPanel buttonPane = emcSetGridBagConstraints.createButtonPanel(buttonList);
        buttonPane.setBorder(lookupPane.getBorder());
        thePanel.add(lookupPane, BorderLayout.CENTER);
        thePanel.add(buttonPane, BorderLayout.EAST);
        thePanel.add(progressBar, BorderLayout.SOUTH);
        thePanel.setBorder(BorderFactory.createTitledBorder("Setup"));
        return thePanel;
    }

    private emcJTabbedPane masterTablePane() {
        List keys = new ArrayList();
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("batch");
        keys.add("serial");
        keys.add("pallet");
        keys.add("availableQty");
        keys.add("toLocation");
        keys.add("quantity");
        keys.add("qCStatus");
        keys.add("groupLine");
        keys.add("split");
//        keys.add("posted");
        emcTableModelUpdate model = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        //Lookups
        EMCLookupJTableComponent lkpToLocation = new EMCLookupJTableComponent(new LocationMenu());
        EMCLookupPopup toPop = new EMCLookupPopup(new InventoryLocation(), "locationId", linesUD);
        lkpToLocation.setPopup(toPop);
        table.setLookupToColumn("toLocation", lkpToLocation);
        lrm = new MovementLRM();
        lrm.addLookup(lkpLocation, "location");
        lrm.addLookup(lkpToLocation, "location");
        lrm.addLookup(lkpWarehouse, "warehouse");
        //Lookups
        //GoToMainTable
        table.setColumnCellEditor("itemReference", new EMCGoToMainTableEditor(new EMCStringDocument(), new ItemMaster()));
        table.setColumnCellEditor("dimension1", new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension1()));
        table.setColumnCellEditor("dimension2", new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension2()));
        table.setColumnCellEditor("dimension3", new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension3()));
        //GoToMainTable
        table.setColumnEditable("itemReference", false);
        table.setColumnEditable("itemDescription", false);
        table.setColumnEditable("dimension1", false);
        table.setColumnEditable("dimension3", false);
        table.setColumnEditable("dimension2", false);
        table.setColumnEditable("serial", false);
        table.setColumnEditable("batch", false);
        table.setColumnEditable("pallet", false);
        table.setColumnEditable("availableQty", false);
        masterDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Stock", tableScroll);
        return tabbed;
    }

    private emcJTabbedPane linesPane() {
        List keys = new ArrayList();
        keys.add("toLocation");
        keys.add("quantity");
        emcTableModelUpdate model = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        //Lookups
        EMCLookupJTableComponent lkpToLocation = new EMCLookupJTableComponent(new LocationMenu());
        EMCLookupPopup toPop = new EMCLookupPopup(new InventoryLocation(), "locationId", linesUD);
        lkpToLocation.setPopup(toPop);
        table.setLookupToColumn("toLocation", lkpToLocation);
        lrm.addLookup(lkpToLocation, "location");
        lrm.initializeLookups();
        //Lookups
        linesDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Stock Split", tableScroll);
        return tabbed;
    }

    @Override
    public EMCQuery getSearchQuery() {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryMoveStockMaster.class.getName());
        query.addTableAnd(InventoryDimensionTable.class.getName(), "dimensionId", InventoryMoveStockMaster.class.getName(), "recordID");
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventoryMoveStockMaster.class.getName(), "itemId");
        query.addLeftOuterJoin(InventoryDimensionTable.class, "dimension1Id", InventoryDimension1.class, "dimensionId");
        query.addLeftOuterJoin(InventoryDimensionTable.class, "dimension2Id", InventoryDimension2.class, "dimensionId");
        query.addLeftOuterJoin(InventoryDimensionTable.class, "dimension3Id", InventoryDimension3.class, "dimensionId");
        query.addAnd("warehouseId", lkpWarehouse.getValue(), InventoryDimensionTable.class.getName());
        query.addAnd("locationId", lkpLocation.getValue(), InventoryDimensionTable.class.getName());
        query.addAnd("posted", false, InventoryMoveStockMaster.class.getName());
        query.addAnd("masterSessionId", masterUD.getSessionID());
        query.addOrderBy("itemReference", InventoryItemMaster.class.getName());
        query.addOrderBy("sortCode", InventoryDimension1.class.getName());
        query.addOrderBy("sortCode", InventoryDimension3.class.getName());
        query.addOrderBy("sortCode", InventoryDimension1.class.getName());
        query.addOrderBy(new String[]{"batchId", "itemSerialId", "palletId"}, InventoryDimensionTable.class.getName());
        return query;
    }
}

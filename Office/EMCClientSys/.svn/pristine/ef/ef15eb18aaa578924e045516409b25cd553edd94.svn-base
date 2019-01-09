/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.journals.generatestocktake;

import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePicker;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.inventory.lookups.itemreference.ItemReferencePopup;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.reporttools.JasperInformation;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCReportWSManager;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.inventory.InventoryItemGroup;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.inventory.InventoryProductGroup;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.datasource.InventoryStocktakeCaptureDS;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.forms.inventory.display.journals.JournalsDRM;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.inventory.helper.GenerateStockTake;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.inventory.menuitems.display.InventoryProductGroupMenu;
import emc.menus.inventory.menuitems.display.ItemGroups;
import emc.menus.inventory.menuitems.display.ItemMaster;
import emc.menus.inventory.menuitems.display.JournalDefinitions;
import emc.menus.inventory.menuitems.display.LocationMenu;
import emc.menus.inventory.menuitems.display.Warehouse;
import emc.methods.inventory.ServerInventoryMethods;
import emc.reporttools.EMCReportDimensionSetup;
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
public class GenerateStockTakeJournalForm extends BaseInternalFrame {

    private JournalsDRM drm;
    private EMCUserData userData;
    private EMCLookup lkpItemGroup;
    private EMCLookup lkpProductGroup;
    private EMCLookup lkpItemId;
    private EMCLookup lkpDimension1;
    private EMCLookup lkpDimension2;
    private EMCLookup lkpDimension3;
    private EMCLookup lkpWarehouse;
    private EMCLookup lkpLocation;
    private EMCLookup lkpJournalDefenition;
    private emcYesNoComponent printCountSheet;
    private emcYesNoComponent splitPerWarehouse;
    private emcYesNoComponent splitPerLocation;
    private EMCDatePicker dpLastMovementDateInclude;
    private EMCDatePicker dpLastMovementDateExclude;

    public GenerateStockTakeJournalForm(EMCUserData userData) {
        super("Generate Stock Take", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 500);
        this.drm = (JournalsDRM) userData.getUserData(2);
        this.userData = userData;
        this.userData.setUserData(null);

        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Generate Journal", contentPane());
        emcJPanel mainPane = new emcJPanel(new BorderLayout());
        mainPane.add(tabbed, BorderLayout.CENTER);
        mainPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(mainPane);
    }

    private emcJPanel contentPane() {
        EMCLookupPopup popItemGroup = new EMCLookupPopup(new InventoryItemGroup(), "itemGroup", userData);
        lkpItemGroup = new EMCLookup(new ItemGroups());
        lkpItemGroup.setPopup(popItemGroup);

        lkpProductGroup = new EMCLookup(new InventoryProductGroupMenu());
        lkpProductGroup.setPopup(new EMCLookupPopup(new InventoryProductGroup(), "productGroupId", userData));

        ItemReferencePopup popItemId = new ItemReferencePopup("reference", userData);
        lkpItemId = new EMCLookup(new ItemMaster());
        lkpItemId.setPopup(popItemId);

        lkpDimension1 = new EMCLookup(new Dimension1());
        lkpDimension1.setPopup(new EMCLookupPopup(new InventoryDimension1(), "dimensionId", userData));

        lkpDimension2 = new EMCLookup(new Dimension2());
        lkpDimension2.setPopup(new EMCLookupPopup(new InventoryDimension2(), "dimensionId", userData));

        lkpDimension3 = new EMCLookup(new Dimension3());
        lkpDimension3.setPopup(new EMCLookupPopup(new InventoryDimension3(), "dimensionId", userData));

        EMCLookupPopup popWarehouse = new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", userData);
        lkpWarehouse = new EMCLookup(new Warehouse());
        lkpWarehouse.setPopup(popWarehouse);

        EMCLookupPopup popLocation = new EMCLookupPopup(new InventoryLocation(), "locationId", userData);
        lkpLocation = new EMCLookup(new LocationMenu());
        lkpLocation.setPopup(popLocation);

        EMCLookupPopup popJournalDefenition = new EMCLookupPopup(new BaseJournalDefinitionTable(), "journalDefinitionId", userData);
        lkpJournalDefenition = new EMCLookup(new JournalDefinitions());
        lkpJournalDefenition.setPopup(popJournalDefenition);

        dpLastMovementDateInclude = new EMCDatePicker();

        dpLastMovementDateExclude = new EMCDatePicker();

        printCountSheet = new emcYesNoComponent();

        splitPerWarehouse = new emcYesNoComponent();

        splitPerLocation = new emcYesNoComponent();



        Component[][] comp = {{new emcJLabel("Item Group"), lkpItemGroup},
            {new emcJLabel("Product Group"), lkpProductGroup},
            {new emcJLabel("Item Id"), lkpItemId},
            {new emcJLabel("Config"), lkpDimension1},
            {new emcJLabel("Colour"), lkpDimension3},
            {new emcJLabel("Size"), lkpDimension2},
            {new emcJLabel("Warehouse"), lkpWarehouse},
            {new emcJLabel("Location"), lkpLocation},
            {new emcJLabel("No Movement Since"), dpLastMovementDateInclude},
            {new emcJLabel("Moved Since"), dpLastMovementDateExclude},
            {new emcJLabel("Journal Definition"), lkpJournalDefenition},
            {new emcJLabel("Print Count Sheet"), printCountSheet},
            {new emcJLabel("Split Journals Per Warehouse"), splitPerWarehouse},
            {new emcJLabel("Split Journals Per Location"), splitPerLocation}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel buttonPane() {
        emcJButton btnOk = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                GenerateStockTake helper = GenerateStockTakeJournalForm.this.populateGeneration();
                List toSend = new ArrayList();
                toSend.add(helper);
                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.GENERATE_STOCKTAKE.toString());
                List result = EMCWSManager.executeGenericWS(cmd, toSend, GenerateStockTakeJournalForm.this.userData);
                if (result != null && result.size() > 1) {
                    drm.refreshDataIgnoreFocus();
                    if (helper.isPrintCountSheet()) {
                        Logger.getLogger("emc").log(Level.INFO, "Stock take journals generated. Printing count sheets.", userData);
                    } else {
                        Logger.getLogger("emc").log(Level.INFO, "Stock take journals generated.", userData);
                    }
                    List<String> journalNumberList = (List<String>) result.get(1);
                    if (!journalNumberList.isEmpty()) {
                        cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.PRINT_STOCK_TAKE_REPORT.toString());
                        JasperInformation jasperInfo = new JasperInformation();
                        jasperInfo.setJasperTemplate("/emc/reports/inventory/stocktake/InventoryStockTakeReport.jrxml");
                        jasperInfo.setXmlNodePath("/emcmsg/emc.entity.inventory.journals.datasource.InventoryStocktakeCaptureDS");
                        jasperInfo.setReportTitle("Stock Take");
                        jasperInfo.addParameter("titleP", "Count Sheet");
                        EMCQuery query = new InventoryStocktakeCaptureDS().getQuery();
                        query.removeAnd("journalNumber", InventoryJournalLines.class.getName());
                        query.addAndInList("journalNumber", InventoryJournalLines.class.getName(), journalNumberList, true, false);
                        toSend = new ArrayList();
                        toSend.add(query);
                        toSend.add(new EMCReportDimensionSetup());
                        EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.INVENTORY_STOCK_TAKE, toSend, GenerateStockTakeJournalForm.this.userData);
                    }
                    GenerateStockTakeJournalForm.this.dispose();
                }
            }
        };

        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                GenerateStockTakeJournalForm.this.dispose();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOk);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private GenerateStockTake populateGeneration() {
        GenerateStockTake toGenerate = new GenerateStockTake();
        //Item Group
        Object groupObject = lkpItemGroup.getValue();
        if (!Functions.checkBlank(groupObject)) {
            String itemGroup = (String) groupObject;
            toGenerate.addItemGroup(itemGroup);
        }
        //Product Group
        Object productGroupObject = lkpProductGroup.getValue();
        if (!Functions.checkBlank(productGroupObject)) {
            String productGroup = (String) productGroupObject;
            toGenerate.addProductGroup(productGroup);
        }
        //ItemId
        Object itemObject = lkpItemId.getValue();
        if (!Functions.checkBlank(itemObject)) {
            String itemId = (String) itemObject;
            toGenerate.addItemId(itemId);
        }
        //Dim1
        Object dim1Object = lkpDimension1.getValue();
        if (!Functions.checkBlank(dim1Object)) {
            String dim1 = (String) dim1Object;
            toGenerate.addDimension1(dim1);
        }
        //Dim2
        Object dim2Object = lkpDimension2.getValue();
        if (!Functions.checkBlank(dim2Object)) {
            String dim2 = (String) dim2Object;
            toGenerate.addDimension2(dim2);
        }
        //Dim3
        Object dim3Object = lkpDimension3.getValue();
        if (!Functions.checkBlank(dim3Object)) {
            String dim3 = (String) dim3Object;
            toGenerate.addDimension3(dim3);
        }
        //Warehouse
        Object warehouseObject = lkpWarehouse.getValue();
        if (!Functions.checkBlank(warehouseObject)) {
            String warehouse = (String) warehouseObject;
            toGenerate.addWarehouse(warehouse);
        }
        //Location
        Object locationObject = lkpLocation.getValue();
        if (!Functions.checkBlank(locationObject)) {
            String location = (String) locationObject;
            toGenerate.addLocation(location);
        }
        //Journal Definition
        Object definitionObject = lkpJournalDefenition.getValue();
        if (!Functions.checkBlank(definitionObject)) {
            String definition = (String) definitionObject;
            toGenerate.setJournalDefinition(definition);
        }
        if (dpLastMovementDateInclude.getDate() != null) {
            toGenerate.setNoMovementSince(dpLastMovementDateInclude.getDate());
        }
        if (dpLastMovementDateExclude.getDate() != null) {
            toGenerate.setMovementSince(dpLastMovementDateExclude.getDate());
        }
        //Print Count Sheet
        toGenerate.setPrintCountSheet(printCountSheet.getSelectedItem().equals("Yes") ? true : false);
        //Split Per Warehouse
        toGenerate.setSplitPerWarehouse(splitPerWarehouse.getSelectedItem().equals("Yes") ? true : false);
        //Split Per Location
        toGenerate.setSplitPerLocation(splitPerLocation.getSelectedItem().equals("Yes") ? true : false);
        return toGenerate;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.pricearangement.customer;

import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.stock.ItemLookupRelationManager;
import emc.app.components.emctable.stock.ItemLookupRelationManagerParameters;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.formlookup.controllookup.StockControlLookupDRM;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension1SetupDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension2SetupDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension3SetupDS;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPPriceArangements;
import emc.entity.sop.datasource.SOPPriceArangementsDS;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.sop.display.pricearangement.resources.ExportPriceArangementsButton;
import emc.forms.sop.display.pricearangement.resources.ImportPriceArangementsButton;
import emc.forms.sop.display.pricearangement.resources.PriceArangementQuerySwitchButton;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class SOPPriceArangementCustomerForm extends BaseInternalFrame {

    private StockControlLookupDRM dataManager;
    private EMCUserData userData;

    public SOPPriceArangementCustomerForm(EMCUserData userData) {
        super("Price List - Customer", true, true, true, true, userData);
        this.setBounds(20, 20, 900, 340);
        this.userData = userData.copyUserDataAndDataList();
        StockDRMParameters param = new StockDRMParameters("itemId", "dimesnion1", "dimension2", "dimension3", null);
        dataManager = new StockControlLookupDRM(new emcGenericDataSourceUpdate(enumEMCModules.SOP.getId(), new SOPPriceArangementsDS(), userData), param, userData) {
            @Override
            public void copyRecord(int rowIndex) {
                //Used to check whether row was added succesfully
                int rowCount = getRowCount();

                super.copyRecord(rowIndex);

                //This form is special.  Almost all fields form part of the unique constraint, but should be pulled through to new line
                if (rowCount < getRowCount()) {
                    this.setFieldValueAt(rowIndex + 1, "itemId", this.getFieldValueAt(rowIndex, "itemId"));
                    this.setFieldValueAt(rowIndex + 1, "dimension1", this.getFieldValueAt(rowIndex, "dimension1"));
                    this.setFieldValueAt(rowIndex + 1, "dimension2", this.getFieldValueAt(rowIndex, "dimension2"));
                    this.setFieldValueAt(rowIndex + 1, "dimension3", this.getFieldValueAt(rowIndex, "dimension3"));
                    this.setFieldValueAt(rowIndex + 1, "fromDate", this.getFieldValueAt(rowIndex, "fromDate"));
                    this.setFieldValueAt(rowIndex + 1, "toDate", this.getFieldValueAt(rowIndex, "toDate"));
                    this.setFieldValueAt(rowIndex + 1, "quantity", this.getFieldValueAt(rowIndex, "quantity"));
                    this.setFieldValueAt(rowIndex + 1, "customerId", this.getFieldValueAt(rowIndex, "customerId"));
                }
            }
        };
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("customerType");
        dataManager.setFormTextId2("itemReference");
        initFrame();
    }

    private void initFrame() {
        this.setLayout(new BorderLayout());
        emcJPanel contentPane = new emcJPanel(new BorderLayout());

        contentPane.add(lookupPane(), BorderLayout.NORTH);
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", overviewTab());
        contentPane.add(tabbed, BorderLayout.CENTER);

        this.add(contentPane, BorderLayout.CENTER);
        this.add(buttonPane(), BorderLayout.EAST);
    }

    private emcJPanel lookupPane() {
        emcJTextField txtName = new emcJTextField();
        EMCControlLookupComponent lkpCustomer = new EMCControlLookupComponent(new SOPCustomersMenu(), dataManager, "customerId", txtName, "customerName", SOPPriceArangements.class.getName());
        lkpCustomer.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", userData));

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPPriceArangements.class);
        query.addOrderBy("customerType");
        query.addOrderBy("customerId");
        query.addOrderBy("priceGroup");
        query.addOrderBy("itemId");
        query.addOrderBy("dimension1SortCode");
        query.addOrderBy("dimension3SortCode");
        query.addOrderBy("dimension2SortCode");
        query.addOrderBy("fromDate");
        query.addOrderBy("toDate");
        query.addOrderBy("quantity");
        lkpCustomer.setFormQuery(query);

        dataManager.setLookup(lkpCustomer);
        Component[][] comp = {{new emcJLabel("Customer"), lkpCustomer, new emcJLabel("Name"), txtName}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Customer");
    }

    private emcJPanel overviewTab() {
        List<String> keys = new ArrayList<String>();
        keys.add("itemBrand");
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("fromDate");
        keys.add("toDate");
        keys.add("quantity");
        keys.add("price");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {
//            @Override
//            public TableCellRenderer getCellRenderer(int row, int column) {
//                DefaultTableCellRenderer renderor = (DefaultTableCellRenderer) super.getCellRenderer(row, column);
//                Object o = dataManager.getFieldValueAt(row, "toDate");
//                if (o != null && Functions.nowDate().after((Date) o)) {
//                    renderor.setForeground(Color.LIGHT_GRAY);
//                } else {
//                    renderor.setForeground(Color.BLACK);
//                }
//                return renderor;
//            }
        };
        setupLookups(table);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLookups(final emcJTableUpdate table) {
        EMCLookupJTableComponent lkpItemRef = EMCItemLookupFactory.createItemLookup(userData);
        table.setLookupToColumn("itemReference", lkpItemRef);

        table.setColumnEditable("itemDescription", false);

        EMCLookupJTableComponent lkpDimension1 = new EMCLookupJTableComponent(new Dimension1());
        lkpDimension1.setPopup(new EMCLookupPopup(new InventoryItemDimension1SetupDS(), "dimensionId", userData));
        table.setLookupToColumn("dimension1", lkpDimension1);

        EMCLookupJTableComponent lkpDimension2 = new EMCLookupJTableComponent(new Dimension2());
        lkpDimension2.setPopup(new EMCLookupPopup(new InventoryItemDimension2SetupDS(), "dimensionId", userData));
        table.setLookupToColumn("dimension2", lkpDimension2);

        EMCLookupJTableComponent lkpDimension3 = new EMCLookupJTableComponent(new Dimension3());
        lkpDimension3.setPopup(new EMCLookupPopup(new InventoryItemDimension3SetupDS(), "dimensionId", userData));
        table.setLookupToColumn("dimension3", lkpDimension3);

        ItemLookupRelationManagerParameters params = new ItemLookupRelationManagerParameters();

        params.setItemField("itemId");
        params.setDim1Field("dimension1");
        params.setDim2Field("dimension2");
        params.setDim3Field("dimension3");

        params.setTableItemLookup(lkpItemRef);
        params.setTableDim1Lookup(lkpDimension1);
        params.setTableDim2Lookup(lkpDimension2);
        params.setTableDim3Lookup(lkpDimension3);

        dataManager.addItemLookupRelationManager(new ItemLookupRelationManager(dataManager, params));
    }

    private emcJPanel buttonPane() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(new ImportPriceArangementsButton(dataManager));
        buttonList.add(new ExportPriceArangementsButton(dataManager));
        buttonList.add(new PriceArangementQuerySwitchButton(dataManager));
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}

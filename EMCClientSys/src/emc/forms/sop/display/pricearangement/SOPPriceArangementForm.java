/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.pricearangement;

import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.stock.ItemLookupRelationManager;
import emc.app.components.emctable.stock.ItemLookupRelationManagerParameters;
import emc.app.components.emctable.stock.StockDRM;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.datatypes.EMCDataType;
import emc.datatypes.EMCString;
import emc.datatypes.datasources.DSRelation;
import emc.datatypes.datasources.QueryManipulator;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension1SetupDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension2SetupDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension3SetupDS;
import emc.entity.pop.POPPriceGroup;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.datasource.SOPPriceArangementsDS;
import emc.enums.modules.enumEMCModules;
import emc.enums.sop.pricearangement.PricingCustomerType;
import emc.forms.sop.display.pricearangement.resources.ExportPriceArangementsButton;
import emc.forms.sop.display.pricearangement.resources.ImportPriceArangementsButton;
import emc.forms.sop.display.pricearangement.resources.PriceArangementQuerySwitchButton;
import emc.framework.EMCMenuItem;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.pop.menuitems.display.PriceGroups;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class SOPPriceArangementForm extends BaseInternalFrame {

    private StockDRM dataManager;
    private EMCUserData userData;
    private emcJComboBox cbCustomerType;

    public SOPPriceArangementForm(EMCUserData userData) {
        super("Price List", true, true, true, true, userData);
        this.setBounds(20, 20, 1000, 340);
        this.userData = userData.copyUserDataAndDataList();
        StockDRMParameters param = new StockDRMParameters("itemId", "dimension1", "dimension2", "dimension3", null);
        dataManager = new StockDRM(new emcGenericDataSourceUpdate(enumEMCModules.SOP.getId(), new SOPPriceArangementsDS(), userData), param, userData) {

            @Override
            public void doRelation(int rowIndex) {
                super.doRelation(rowIndex);
                emcJTableUpdate table = this.getMainTableComponent();
                if (table != null) {
                    cbCustomerType.setSelectedItem(((StockDRM) getDataManager()).getLastFieldValueAt("customerType"));
                }
            }

            @Override
            public String getColumnName(String columnIndex) {
                if (columnIndex.equals("customerDisplayField")) {
                    return "Customer";
                }
                return super.getColumnName(columnIndex);
            }

            @Override
            public EMCDataType getDataType(String fieldKey) {
                if (fieldKey.equals("customerDisplayField")) {
                    EMCDataType dt = null;
                    if (PricingCustomerType.ALL.toString().equals((String) getLastFieldValueAt("customerType"))) {
                        dt = super.getDataType(fieldKey);
                    } else if (PricingCustomerType.CUSTOMER.toString().equals((String) getLastFieldValueAt("customerType"))) {
                        dt = super.getDataType("customerId");
                    } else if (PricingCustomerType.GROUP.toString().equals((String) getLastFieldValueAt("customerType"))) {
                        dt = super.getDataType("priceGroup");
                    }
                    DSRelation dsr = new DSRelation();
                    dsr.setSearchCriteria(new QueryManipulator() {

                        @Override
                        public EMCQuery manipulateQuery(EMCQuery query, String PKField, String FKField, Object theValue, String queryCondition, EMCUserData userData) {
                            query.openAndConditionBracket();
                            query.addOr("priceGroup", theValue);
                            query.addOr("customerId", theValue);
                            query.closeConditionBracket();
                            return query;
                        }
                    });
                    dt.setDsRelation(dsr);
                    if (PricingCustomerType.CUSTOMER.toString().equals((String) getLastFieldValueAt("customerType"))) {
                        dt.setRelatedTable(SOPCustomers.class.getName());
                        dt.setRelatedField("customerId");
                    } else if (PricingCustomerType.GROUP.toString().equals((String) getLastFieldValueAt("customerType"))) {
                        dt.setRelatedTable(POPPriceGroup.class.getName());
                        dt.setRelatedField("priceGroupId");
                    }
                    return dt;
                }
                return super.getDataType(fieldKey);
            }

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
                    this.setFieldValueAt(rowIndex + 1, "customerType", this.getFieldValueAt(rowIndex, "customerType"));
                    this.setFieldValueAt(rowIndex + 1, "priceGroup", this.getFieldValueAt(rowIndex, "priceGroup"));
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
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", overviewTab());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate overviewTab() {
        List<String> keys = new ArrayList<String>();
        keys.add("customerType");
        keys.add("customerDisplayField");
        keys.add("customerName");
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
        final EMCLookupJTableComponent lkpCustomer = new EMCLookupJTableComponent(new SOPCustomersMenu());
        lkpCustomer.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", userData));
        final EMCLookupJTableComponent lkpPriceGroup = new EMCLookupJTableComponent(new PriceGroups());
        lkpPriceGroup.setPopup(new EMCLookupPopup(new POPPriceGroup(), "priceGroupId", userData));
        table.setLookupToColumn("customerDisplayField", lkpCustomer);
        cbCustomerType = new emcJComboBox(PricingCustomerType.values()) {

            @Override
            public void setSelectedItem(Object anObject) {
                super.setSelectedItem(anObject);
                switch (PricingCustomerType.fromString(anObject.toString())) {
                    case ALL:
                        table.setColumnEditable("customerDisplayField", false);
                        break;
                    case CUSTOMER:
                        table.setColumnEditable("customerDisplayField", true);
                        table.setLookupToColumn("customerDisplayField", lkpCustomer);
                        break;
                    case GROUP:
                        table.setColumnEditable("customerDisplayField", true);
                        table.setLookupToColumn("customerDisplayField", lkpPriceGroup);
                        break;
                }
            }
        };
        table.setComboBoxLookupToColumn("customerType", cbCustomerType);
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
        
        table.setColumnEditable("itemBrand", false);
    }

    private emcJPanel buttonPane() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(new ImportPriceArangementsButton(dataManager));
        buttonList.add(new ExportPriceArangementsButton(dataManager));
        buttonList.add(new PriceArangementQuerySwitchButton(dataManager));
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}

package emc.forms.pop.display.pricearrangements;

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
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.components.lookup.factory.EMCDimensionLookupFactory;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.pop.POPPriceGroup;
import emc.entity.pop.POPSuppliers;
import emc.entity.pop.datasource.pricearrangements.POPPriceArrangementDS;
import emc.enums.pop.pricearrangement.PricingSupplierType;
import emc.forms.pop.display.pricearrangements.resources.PriceArangementQuerySwitchButton;
import emc.framework.EMCUserData;
import emc.menus.pop.menuitems.display.PriceGroups;
import emc.menus.pop.menuitems.display.Suppliers;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author riaan
 */
public class POPPriceArrangementsForm extends BaseInternalFrame {

    private StockDRM drm;
    private emcJComboBox cmbPricingType = new emcJComboBox(PricingSupplierType.values());
    private EMCLookupJTableComponent lkpItemRef;
    private EMCLookupJTableComponent lkpSupplier;
    private EMCLookupJTableComponent lkpPriceGroup;
    private EMCLookupJTableComponent lkpDimension1;
    private EMCLookupJTableComponent lkpDimension2;
    private EMCLookupJTableComponent lkpDimension3;
    private EMCLookupTableCellEditor edtSupplier;
    private EMCLookupTableCellEditor edtPriceGroup;

    /**
     * Creates a new instance of POPPriceArrangementsForm.
     */
    public POPPriceArrangementsForm(EMCUserData userData) {
        super("Price List", true, true, true, true, userData);
        this.setBounds(20, 20, 1000, 340);
        StockDRMParameters param = new StockDRMParameters("itemId", "dimension1", "dimension2", "dimension3", null);

        drm = new StockDRM(new emcGenericDataSourceUpdate(new POPPriceArrangementDS(), userData), param, userData);
        drm.setTheForm(this);
        this.setDataManager(drm);
        drm.setFormTextId1("supplierType");
        drm.setFormTextId2("priceGroup");

        initLookups(userData);
        initLookupCellEditors(userData);

        this.initFrame();
    }

    /**
     * Initializes lookups.
     */
    private void initLookups(EMCUserData userData) {
        this.lkpItemRef = EMCItemLookupFactory.createItemLookup(userData);
        this.lkpDimension1 = EMCDimensionLookupFactory.createDimension1JTableLookup(userData);
        this.lkpDimension2 = EMCDimensionLookupFactory.createDimension2JTableLookup(userData);
        this.lkpDimension3 = EMCDimensionLookupFactory.createDimension3JTableLookup(userData);

        this.lkpPriceGroup = new EMCLookupJTableComponent(new PriceGroups());
        this.lkpPriceGroup.setPopup(new EMCLookupPopup(new POPPriceGroup(), "priceGroupId", userData));

        this.lkpSupplier = new EMCLookupJTableComponent(new Suppliers());
        this.lkpSupplier.setPopup(new EMCLookupPopup(new POPSuppliers(), "supplierId", userData));

        ItemLookupRelationManagerParameters params = new ItemLookupRelationManagerParameters();

        params.setItemField("itemId");
        params.setDim1Field("dimension1");
        params.setDim2Field("dimension2");
        params.setDim3Field("dimension3");

        params.setTableItemLookup(this.lkpItemRef);
        params.setTableDim1Lookup(this.lkpDimension1);
        params.setTableDim2Lookup(this.lkpDimension2);
        params.setTableDim3Lookup(this.lkpDimension3);

        drm.addItemLookupRelationManager(new ItemLookupRelationManager(drm, params));
    }

    /**
     * Initializes lookup cell editors.
     */
    private void initLookupCellEditors(EMCUserData userData) {
        edtPriceGroup = new EMCLookupTableCellEditor(lkpPriceGroup);
        edtSupplier = new EMCLookupTableCellEditor(lkpSupplier);
    }

    /**
     * Initializes the frame.
     */
    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Overview", createOverviewTab());
        this.add(tabs, BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);
    }

    /**
     * Creates the Overview Tab.
     */
    private emcJPanel createOverviewTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));
        List<String> keys = new ArrayList<String>();
        keys.add("supplierType");
        keys.add("supplierDisplayField");
        keys.add("supplierName");
        keys.add("itemBrand");
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("dimension1");
        keys.add("dimension2");
        keys.add("dimension3");
        keys.add("fromDate");
        keys.add("toDate");
        keys.add("quantity");
        keys.add("price");

        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        final emcJTableUpdate table = new emcJTableUpdate(model) {
            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if (column == this.getEmcTableModel().getColumnByFieldName("supplierDisplayField")) {
                    PricingSupplierType type = PricingSupplierType.fromString((String) drm.getFieldValueAt(row, "supplierType"));

                    if (type == PricingSupplierType.GROUP) {
                        return edtPriceGroup;
                    } else if (type == PricingSupplierType.SUPPLIER) {
                        return edtSupplier;
                    } else {
                        //Column not editable
                        return null;
                    }
                } else {
                    return super.getCellEditor(row, column);
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return super.isCellEditable(row, column);
            }
        };

        table.setComboBoxLookupToColumn("supplierType", this.cmbPricingType);

        table.setLookupToColumn("itemReference", this.lkpItemRef);
        table.setLookupToColumn("dimension1", this.lkpDimension1);
        table.setLookupToColumn("dimension2", this.lkpDimension2);
        table.setLookupToColumn("dimension3", this.lkpDimension3);
        table.setColumnEditable("itemBrand", false);

        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setMainTableComponent(table);
        drm.setTablePanel(tableScroll);

        panel.add(tableScroll);
        return panel;
    }

    /**
     * Creates buttons panel.
     */
    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        buttons.add(new emcJButton("Import"));
        buttons.add(new emcJButton("Export"));
        buttons.add(new PriceArangementQuerySwitchButton(drm));

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.pricearrangements.item;

import emc.app.components.controllookup.EMCControlLookupPanel;
import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.stock.ItemLookupRelationManager;
import emc.app.components.emctable.stock.ItemLookupRelationManagerParameters;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.formlookup.controllookup.StockControlLookupDRM;
import emc.app.components.formlookup.controllookup.itemlookup.EMCItemControlLookup;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.components.lookup.factory.EMCDimensionLookupFactory;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.pop.POPPriceGroup;
import emc.entity.pop.POPSuppliers;
import emc.entity.pop.datasource.pricearrangements.POPPriceArrangementDS;
import emc.entity.pop.pricearrangements.POPPriceArrangements;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.pop.pricearrangement.PricingSupplierType;
import emc.forms.pop.display.pricearrangements.resources.PriceArangementQuerySwitchButton;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.pop.menuitems.display.PriceGroups;
import emc.menus.pop.menuitems.display.Suppliers;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author riaan
 */
public class POPItemPriceArrangementForm extends BaseInternalFrame {

    private StockControlLookupDRM drm;
    private emcJComboBox cmbPricingType = new emcJComboBox(PricingSupplierType.values());
    private EMCLookupJTableComponent lkpSupplier;
    private EMCLookupJTableComponent lkpPriceGroup;
    private EMCItemControlLookup lkpItemRef;
    private EMCLookupJTableComponent lkpDimension1;
    private EMCLookupJTableComponent lkpDimension2;
    private EMCLookupJTableComponent lkpDimension3;
    private EMCLookupTableCellEditor edtSupplier;
    private EMCLookupTableCellEditor edtPriceGroup;
    private emcJTextField txtDesc = new emcJTextField();

    /**
     * Creates a new instance of POPItemPriceArrangementForm.
     */
    public POPItemPriceArrangementForm(EMCUserData userData) {
        super("Price List - Item", true, true, true, true, userData);
        this.setBounds(20, 20, 1000, 340);

        StockDRMParameters parameters = new StockDRMParameters("itemId", "dimension1", "dimension2", "dimension3", null);
        parameters.setItemReferenceField("itemReference");

        this.drm = new StockControlLookupDRM(
                new emcGenericDataSourceUpdate(new POPPriceArrangementDS(), userData), parameters, userData);
        this.setDataManager(drm);
        drm.setTheForm(this);
        drm.setFormTextId1("itemReference");
        drm.setFormTextId2("price");

        this.txtDesc.setEditable(false);

        initLookups(userData);
        initLookupCellEditors();

        initFrame();
    }

    /**
     * Initializes lookups.
     */
    private void initLookups(EMCUserData userData) {
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

        lkpItemRef = EMCItemLookupFactory.createItemControllLookup(drm, txtDesc, POPPriceArrangements.class.getName(), userData);
        lkpItemRef.setReferenceField("itemReference");

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPriceArrangements.class);
        query.addAnd("toDate", Functions.nowDate(), EMCQueryConditions.GREATER_THAN_EQ);
        query.addOrderBy("supplierType");
        query.addOrderBy("supplierId");
        query.addOrderBy("priceGroup");
        query.addOrderBy("itemId");
        query.addOrderBy("dimension1SortCode");
        query.addOrderBy("dimension3SortCode");
        query.addOrderBy("dimension2SortCode");
        query.addOrderBy("fromDate");
        query.addOrderBy("toDate");
        query.addOrderBy("quantity");

        lkpItemRef.setFormQuery(query);

        params.setFormItemLookup(this.lkpItemRef);
        params.setTableDim1Lookup(this.lkpDimension1);
        params.setTableDim2Lookup(this.lkpDimension2);
        params.setTableDim3Lookup(this.lkpDimension3);

        drm.addItemLookupRelationManager(new ItemLookupRelationManager(drm, params));
    }

    /**
     * Initializes lookup cell editors.
     */
    private void initLookupCellEditors() {
        edtPriceGroup = new EMCLookupTableCellEditor(lkpPriceGroup);
        edtSupplier = new EMCLookupTableCellEditor(lkpSupplier);
    }

    private void initFrame() {
        List<String> keys = new ArrayList<String>();
        keys.add("supplierType");
        keys.add("supplierDisplayField");
        keys.add("supplierName");
        keys.add("itemBrand");
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
        };

        table.setComboBoxLookupToColumn("supplierType", this.cmbPricingType);

        table.setLookupToColumn("dimension1", this.lkpDimension1);
        table.setLookupToColumn("dimension2", this.lkpDimension2);
        table.setLookupToColumn("dimension3", this.lkpDimension3);

        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setMainTableComponent(table);
        drm.setTablePanel(tableScroll);

        drm.setLookup(lkpItemRef);

        this.setLayout(new BorderLayout());

        this.add(new EMCControlLookupPanel("Overview", "Item", lkpItemRef, "Description", txtDesc, tableScroll, "Overview"), BorderLayout.CENTER);

        PriceArangementQuerySwitchButton viewButton = new PriceArangementQuerySwitchButton(drm);

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(viewButton);

        this.add(emcSetGridBagConstraints.createButtonPanel(buttonList), BorderLayout.EAST);
    }
}

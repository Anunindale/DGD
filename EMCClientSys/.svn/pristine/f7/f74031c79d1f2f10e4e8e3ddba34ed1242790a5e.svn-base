/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.pricearrangements.supplier;

import emc.app.components.controllookup.EMCControlLookupPanel;
import emc.app.components.emcJButton;
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

/**
 *
 * @author riaan
 */
public class POPSupplierPriceArrangementForm extends BaseInternalFrame {

    private StockControlLookupDRM drm;
    private EMCControlLookupComponent lkpSupplier;
    private EMCLookupJTableComponent lkpPriceGroup;
    private EMCLookupJTableComponent lkpItemRef;
    private EMCLookupJTableComponent lkpDimension1;
    private EMCLookupJTableComponent lkpDimension2;
    private EMCLookupJTableComponent lkpDimension3;
    private emcJTextField txtDesc = new emcJTextField();

    /**
     * Creates a new instance of POPItemPriceArrangementForm.
     */
    public POPSupplierPriceArrangementForm(EMCUserData userData) {
        super("Price List - Supplier", true, true, true, true, userData);
        this.setBounds(20, 20, 1000, 340);

        StockDRMParameters parameters = new StockDRMParameters("itemId", "dimension1", "dimension2", "dimension3", null);
        parameters.setItemReferenceField("itemReference");

        this.drm = new StockControlLookupDRM(
                new emcGenericDataSourceUpdate(new POPPriceArrangementDS(), userData), parameters, userData) {
            @Override
            public void updatePersist(int rowIndex) {
                //Always use supplier as type
                if (rowIndex == -1) {
                    rowIndex = getLastRowAccessed();
                }
                if (this.getFieldValueAt(rowIndex, "supplierType") == null) {
                    this.setFieldValueAt(rowIndex, "supplierType", PricingSupplierType.SUPPLIER.toString());
                }

                super.updatePersist(rowIndex);
            }
        };
        this.setDataManager(drm);
        drm.setTheForm(this);
        drm.setFormTextId1("itemReference");
        drm.setFormTextId2("price");

        this.txtDesc.setEditable(false);

        initLookups(userData);
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

        this.lkpSupplier = new EMCControlLookupComponent(new Suppliers(), drm, "supplierId", txtDesc, "supplierName", POPPriceArrangements.class.getName());
        this.lkpSupplier.setPopup(new EMCLookupPopup(new POPSuppliers(), "supplierId", userData));

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPriceArrangements.class);
        query.addAnd("toDate", Functions.nowDate(), EMCQueryConditions.GREATER_THAN_EQ);
        query.addOrderBy("dimension1SortCode");
        query.addOrderBy("dimension3SortCode");
        query.addOrderBy("dimension2SortCode");
        query.addOrderBy("fromDate");
        query.addOrderBy("toDate");
        query.addOrderBy("quantity");
        lkpSupplier.setFormQuery(query);

        ItemLookupRelationManagerParameters params = new ItemLookupRelationManagerParameters();

        params.setItemField("itemId");
        params.setDim1Field("dimension1");
        params.setDim2Field("dimension2");
        params.setDim3Field("dimension3");

        lkpItemRef = EMCItemLookupFactory.createItemLookup(userData);

        params.setTableItemLookup(this.lkpItemRef);
        params.setTableDim1Lookup(this.lkpDimension1);
        params.setTableDim2Lookup(this.lkpDimension2);
        params.setTableDim3Lookup(this.lkpDimension3);

        drm.addItemLookupRelationManager(new ItemLookupRelationManager(drm, params));
    }

    private void initFrame() {
        List<String> keys = new ArrayList<String>();
        keys.add("itemBrand");
        keys.add("itemReference");
        keys.add("dimension1");
        keys.add("dimension2");
        keys.add("dimension3");
        keys.add("fromDate");
        keys.add("toDate");
        keys.add("quantity");
        keys.add("price");

        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        final emcJTableUpdate table = new emcJTableUpdate(model);

        table.setLookupToColumn("itemReference", this.lkpItemRef);
        table.setLookupToColumn("dimension1", this.lkpDimension1);
        table.setLookupToColumn("dimension2", this.lkpDimension2);
        table.setLookupToColumn("dimension3", this.lkpDimension3);

        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setMainTableComponent(table);
        drm.setTablePanel(tableScroll);

        drm.setLookup(lkpSupplier);

        this.setLayout(new BorderLayout());

        this.add(new EMCControlLookupPanel("Overview", "Supplier", lkpSupplier, "Name", txtDesc, tableScroll, "Overview"), BorderLayout.CENTER);

        PriceArangementQuerySwitchButton viewButton = new PriceArangementQuerySwitchButton(drm);

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(viewButton);

        this.add(emcSetGridBagConstraints.createButtonPanel(buttonList), BorderLayout.EAST);
    }
}

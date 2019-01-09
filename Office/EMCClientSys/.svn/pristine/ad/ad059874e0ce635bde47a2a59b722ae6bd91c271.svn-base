/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.inventoryreference;

import emc.app.components.documents.EMCDoubleFormDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.datatypes.inventory.inventoryreference.supplier.SupplierItemDesc;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.datasource.InventoryReferenceDS;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class SupplierReferenceFormSupp extends BaseInternalFrame {

    private EMCControlLookupComponentDRM dataRelation;
    private EMCUserData copyUD;
    //Lookups
    private EMCControlLookupComponent lkpSupp;
    private emcJTextField txtSuppName;
    private EMCLookupTableCellEditor edtItem;
    private EMCLookupTableCellEditor edtDim1;
    private EMCLookupTableCellEditor edtDim2;
    private EMCLookupTableCellEditor edtDim3;

    public SupplierReferenceFormSupp(EMCUserData userData) {
        super("Supplier Reference", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 300);
        try {
            if (userData.getUserData(0) == null) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
                query.addAnd("refType", InventoryReferenceTypes.CUSTOMER);

                userData.setUserData(0, query);
            }

            copyUD = userData.copyUserDataAndDataList();

            InventoryReferenceDS instance = new InventoryReferenceDS();
            instance.getFieldDataTypeMapper().put("alternativeDescription", new SupplierItemDesc());

            dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), instance, userData), userData) {

                @Override
                public void updatePersist(int rowIndex) {
                    if (rowIndex == -1) {
                        rowIndex = getLastRowAccessed();
                    }
                    
                    if (this.getFieldValueAt(rowIndex, "refType") == null) {
                        this.setFieldValueAt(rowIndex, "refType", InventoryReferenceTypes.SUPPLIER.toString());
                    }
                    super.updatePersist(rowIndex);
                }
            };
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("itemPrimaryReference");
            dataRelation.setFormTextId2("supplierNo");
        } catch (Exception e) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        thePanel.add(lookupPane(), BorderLayout.NORTH);
        tabbed.add("Supplier Reference", tablePane());
        tabbed.add("Pricing", pricingPane());
        thePanel.add(tabbed, BorderLayout.CENTER);
        this.setContentPane(thePanel);
    }

    private emcJPanel tablePane() {
        emcJPanel thePanel = new emcJPanel(new BorderLayout(1, 1));
        thePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Supplier Reference"));

        List keys = new ArrayList();
        keys.add("itemPrimaryReference");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("reference");
        keys.add("alternativeDescription");
        //      keys.add("oldNinianRef");
        emcTableModelUpdate m = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(m);
        setupTableLookups();
        table.setLookupCellEditor(0, edtItem);
        table.setLookupCellEditor(1, edtDim1);
        table.setLookupCellEditor(2, edtDim3);
        table.setLookupCellEditor(3, edtDim2);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataRelation.setTablePanel(tableScroll);
        thePanel.add(tableScroll);
        return thePanel;
    }

    private void setupTableLookups() {
        SupplierReferenceLookupRM lrm = new SupplierReferenceLookupRM(dataRelation);

        EMCLookupJTableComponent lkpItem = EMCItemLookupFactory.createItemLookup(copyUD);
        edtItem = new EMCLookupTableCellEditor(lkpItem);

        EMCLookupJTableComponent lkpDim1 = new EMCLookupJTableComponent(new Dimension1());
        EMCLookupPopup popDim1 = new EMCLookupPopup(new InventoryDimension1(), "dimensionId", copyUD);
        lkpDim1.setPopup(popDim1);
        edtDim1 = new EMCLookupTableCellEditor(lkpDim1);

        EMCLookupJTableComponent lkpDim2 = new EMCLookupJTableComponent(new Dimension2());
        EMCLookupPopup popDim2 = new EMCLookupPopup(new InventoryDimension2(), "dimensionId", copyUD);
        lkpDim2.setPopup(popDim2);
        edtDim2 = new EMCLookupTableCellEditor(lkpDim2);

        EMCLookupJTableComponent lkpDim3 = new EMCLookupJTableComponent(new Dimension3());
        EMCLookupPopup popDim3 = new EMCLookupPopup(new InventoryDimension3(), "dimensionId", copyUD);
        lkpDim3.setPopup(popDim3);
        edtDim3 = new EMCLookupTableCellEditor(lkpDim3);

        lrm.addLookup(lkpItem, "item");
        lrm.addLookup(lkpDim1, "dim1");
        lrm.addLookup(lkpDim2, "dim2");
        lrm.addLookup(lkpDim3, "dim3");
        lrm.initializeLookups();
    }

    private emcJPanel lookupPane() {
        txtSuppName = new emcJTextField();
        txtSuppName.setEditable(false);
        lkpSupp = EMCItemLookupFactory.createItemControllLookup(dataRelation, txtSuppName, InventoryReference.class.getName(), copyUD);
        dataRelation.setLookup(lkpSupp);
        Component[][] comp = {{new emcJLabel("Supplier"), lkpSupp, new emcJLabel("Name"), txtSuppName}};
        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
        thePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Supplier"));
        return thePanel;
    }

    private Component pricingPane() {
        emcJTextField txtPurchsePrice = new emcJTextField(new EMCDoubleFormDocument(dataRelation, "purchasePrice"));
        EMCDatePickerFormComponent dpPurchasePrice = new EMCDatePickerFormComponent(dataRelation, "purchasePriceDate");
        Component[][] comp = {{new emcJLabel("Purchase Price"), txtPurchsePrice},
            {new emcJLabel("Purchase Price Date"), dpPurchasePrice}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Pricing");
    }
}

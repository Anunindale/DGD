/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.inventoryreference;

import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.formlookup.controllookup.itemlookup.EMCItemControlLookup;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.datatypes.inventory.customerreference.CustomerItemDesc;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.datasource.InventoryReferenceDS;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.sop.SOPCustomers;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.enums.modules.enumEMCModules;
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
 * @author Riaan
 */
public class CustomerReferenceForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM dataRelation;
    private EMCUserData copyUD;
    //Lookups
    private EMCItemControlLookup lkpItem;
    private emcJTextField txtItemDesc;
    private EMCLookupJTableComponent tblLkpDim1;
    private EMCLookupJTableComponent tblLkpDim2;
    private EMCLookupJTableComponent tblLkpDim3;
    private EMCLookupJTableComponent tblLkpCustomerId;

    public CustomerReferenceForm(EMCUserData userData) {
        super("Customer Reference", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 300);
        try {
            if (userData.getUserData(0) == null) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
                query.addAnd("refType", InventoryReferenceTypes.CUSTOMER);

                userData.setUserData(0, query);
            }

            copyUD = userData.copyUserDataAndDataList();

            InventoryReferenceDS instance = new InventoryReferenceDS();
            instance.getFieldDataTypeMapper().put("alternativeDescription", new CustomerItemDesc());
            dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), instance, userData), userData) {

                @Override
                public void updatePersist(int rowIndex) {
                    if (rowIndex == -1) {
                        rowIndex = getLastRowAccessed();
                    }
                    
                    if (this.getFieldValueAt(rowIndex, "refType") == null) {
                        this.setFieldValueAt(rowIndex, "refType", InventoryReferenceTypes.CUSTOMER.toString());
                    }
                    super.updatePersist(rowIndex);
                }
            };
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("reference");
            dataRelation.setFormTextId2("customerNo");
        } catch (Exception e) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        thePanel.add(lookupPane(), BorderLayout.NORTH);
        thePanel.add(tablePane(), BorderLayout.CENTER);
        tabbed.add("Customer Reference", thePanel);
        this.add(tabbed);
    }

    private emcJPanel tablePane() {
        emcJPanel thePanel = new emcJPanel(new BorderLayout(1, 1));
        thePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Supplier Reference"));

        List keys = new ArrayList();
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("customerNo");
        keys.add("reference");
        keys.add("alternativeDescription");

        emcTableModelUpdate m = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(m);
        setupTableLookups();

        table.setLookupToColumn("dimension1", tblLkpDim1);
        table.setLookupToColumn("dimension2", tblLkpDim2);
        table.setLookupToColumn("dimension3", tblLkpDim3);

        table.setLookupToColumn("customerNo", tblLkpCustomerId);

        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataRelation.setTablePanel(tableScroll);
        thePanel.add(tableScroll);
        return thePanel;
    }

    private void setupTableLookups() {
        SupplierReferenceLookupRM lrm = new SupplierReferenceLookupRM(dataRelation);

        tblLkpDim1 = new EMCLookupJTableComponent(new Dimension1());
        EMCLookupPopup popDim1 = new EMCLookupPopup(new InventoryDimension1(), "dimensionId", copyUD);
        tblLkpDim1.setPopup(popDim1);

        tblLkpDim2 = new EMCLookupJTableComponent(new Dimension2());
        EMCLookupPopup popDim2 = new EMCLookupPopup(new InventoryDimension2(), "dimensionId", copyUD);
        tblLkpDim2.setPopup(popDim2);

        tblLkpDim3 = new EMCLookupJTableComponent(new Dimension3());
        EMCLookupPopup popDim3 = new EMCLookupPopup(new InventoryDimension3(), "dimensionId", copyUD);
        tblLkpDim3.setPopup(popDim3);

        tblLkpCustomerId = new EMCLookupJTableComponent(new SOPCustomersMenu());
        tblLkpCustomerId.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", copyUD));

        lrm.addLookup(lkpItem, "item");
        lrm.addLookup(tblLkpDim1, "dim1");
        lrm.addLookup(tblLkpDim2, "dim2");
        lrm.addLookup(tblLkpDim3, "dim3");

        lrm.initializeLookups();
    }

    private emcJPanel lookupPane() {
        txtItemDesc = new emcJTextField();
        txtItemDesc.setEditable(false);
        lkpItem = EMCItemLookupFactory.createItemControllLookup(dataRelation, txtItemDesc, InventoryReference.class.getName(), copyUD);
        lkpItem.setReferenceField("itemPrimaryReference");

        dataRelation.setLookup(lkpItem);
        lkpItem.setFormQuery((EMCQuery) getUserData().getUserData(0));

        Component[][] comp = {{new emcJLabel("Item"), lkpItem, new emcJLabel("Description"), txtItemDesc}};
        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
        thePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Customer"));
        return thePanel;
    }
}

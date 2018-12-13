/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.discountsetup;

import emc.app.components.emcJComboBox;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.datatypes.EMCDataType;
import emc.datatypes.sop.discountsetup.datasource.CustomerField;
import emc.datatypes.sop.discountsetup.datasource.ItemField;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.pop.POPDiscountGroup;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.datasource.SOPDiscountSetupDS;
import emc.enums.sop.discountsetup.CustomerSelectionType;
import emc.enums.sop.discountsetup.ItemSelectionType;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.pop.menuitems.display.DiscountGroups;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author riaan
 */
public class SOPDiscountSetupForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate drm;
    private EMCLookupTableCellEditor edCustomer;
    private EMCLookupTableCellEditor edCustomerDiscGroup;
    private EMCLookupTableCellEditor edItem;
    private EMCLookupTableCellEditor edItemDiscGroup;
    private EMCLookupJTableComponent lkpDimension1;
    private EMCLookupJTableComponent lkpDimension2;
    private EMCLookupJTableComponent lkpDimension3;
    private emcJComboBox cmbCustSelectType;
    private emcJComboBox cmbItemSelectType;

    /** Creates a new instance of SOPDiscountSetup. */
    public SOPDiscountSetupForm(EMCUserData userData) {
        super("Discount Setup", true, true, true, true, userData);
        this.setBounds(20, 20, 800, 400);

        this.drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new SOPDiscountSetupDS(), userData), userData) {

            @Override
            public EMCDataType getDataType(String fieldKey) {
                EMCDataType dt;
                if (fieldKey.equals("customerField")) {
                    dt = new CustomerField();
                    if (CustomerSelectionType.CUSTOMER.toString().equals((String) getLastFieldValueAt("customerSelectType"))) {
                        dt.setRelatedField("customerId");
                        dt.setRelatedTable(SOPCustomers.class.getName());
                    } else if (CustomerSelectionType.GROUP.toString().equals((String) getLastFieldValueAt("customerSelectType"))) {
                        dt.setRelatedField("discountGroupId");
                        dt.setRelatedTable(POPDiscountGroup.class.getName());
                    }
                } else if (fieldKey.equals("itemField")) {
                    dt = new ItemField();
                    if (ItemSelectionType.ITEM.toString().equals((String) getLastFieldValueAt("itemSelectType"))) {
                        dt.setRelatedField("itemReference");
                        dt.setRelatedTable(InventoryItemMaster.class.getName());
                    } else if (ItemSelectionType.GROUP.toString().equals((String) getLastFieldValueAt("itemSelectType"))) {
                        dt.setRelatedField("discountGroupId");
                        dt.setRelatedTable(POPDiscountGroup.class.getName());
                    }
                } else {
                    dt = super.getDataType(fieldKey);
                }
                return dt;
            }
        };

        this.drm.setFormTextId1("customerSelectType");
        this.drm.setFormTextId2("itemSelectType");

        this.drm.setTheForm(this);
        this.setDataManager(this.drm);

        this.setupComboBoxes();
        this.setupLookups(userData);
        this.initForm();
    }

    /** Sets up lookups. */
    private void setupLookups(EMCUserData userData) {
        EMCLookupJTableComponent lkpCustomer = new EMCLookupJTableComponent(new SOPCustomersMenu());
        lkpCustomer.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", userData));
        edCustomer = new EMCLookupTableCellEditor(lkpCustomer);

        EMCLookupJTableComponent lkpCustomerDiscGroup = new EMCLookupJTableComponent(new DiscountGroups());
        lkpCustomerDiscGroup.setPopup(new EMCLookupPopup(new POPDiscountGroup(), "discountGroupId", userData));
        edCustomerDiscGroup = new EMCLookupTableCellEditor(lkpCustomerDiscGroup);

        EMCLookupJTableComponent lkpItem = EMCItemLookupFactory.createItemLookup(userData);
        edItem = new EMCLookupTableCellEditor(lkpItem);

        EMCLookupJTableComponent lkpItemDiscGroup = new EMCLookupJTableComponent(new DiscountGroups());
        lkpItemDiscGroup.setPopup(new EMCLookupPopup(new POPDiscountGroup(), "discountGroupId", userData));
        edItemDiscGroup = new EMCLookupTableCellEditor(lkpItemDiscGroup);

        lkpDimension1 = new EMCLookupJTableComponent(new Dimension1());
        lkpDimension1.setPopup(new EMCLookupPopup(new InventoryDimension1(), "dimensionId", userData));

        lkpDimension2 = new EMCLookupJTableComponent(new Dimension2());
        lkpDimension2.setPopup(new EMCLookupPopup(new InventoryDimension2(), "dimensionId", userData));

        lkpDimension3 = new EMCLookupJTableComponent(new Dimension3());
        lkpDimension3.setPopup(new EMCLookupPopup(new InventoryDimension3(), "dimensionId", userData));
    }

    /** Sets up comboboxes. */
    private void setupComboBoxes() {
        this.cmbCustSelectType = new emcJComboBox(CustomerSelectionType.values());
        this.cmbItemSelectType = new emcJComboBox(ItemSelectionType.values());
    }

    /** Initializes the form. */
    private void initForm() {
        emcJTabbedPane tabs = new emcJTabbedPane();

        tabs.add("Overview", createOverviewTab());

        this.add(tabs, BorderLayout.CENTER);
    }

    /** Creates the overview tab. */
    private emcJPanel createOverviewTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));

        List<String> keys = new ArrayList<String>();
        keys.add("customerSelectType");
        keys.add("customerField");
        keys.add("itemSelectType");
        keys.add("itemField");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("fromDate");
        keys.add("toDate");
        keys.add("discountPercentage");

        final emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                String columnName = model.getFieldKey(column);

                if ("customerField".equals(columnName)) {
                    String selectType = (String) SOPDiscountSetupForm.this.drm.getLastFieldValueAt("customerSelectType");

                    if (CustomerSelectionType.CUSTOMER.toString().equalsIgnoreCase(selectType)) {
                        return edCustomer;
                    } else if (CustomerSelectionType.GROUP.toString().equalsIgnoreCase(selectType)) {
                        return edCustomerDiscGroup;
                    } else {
                        //Do not allow editing
                        return null;
                    }
                } else if ("itemField".equals(columnName)) {
                    String selectType = (String) SOPDiscountSetupForm.this.drm.getLastFieldValueAt("itemSelectType");

                    if (ItemSelectionType.ITEM.toString().equalsIgnoreCase(selectType)) {
                        return edItem;
                    } else if (ItemSelectionType.GROUP.toString().equalsIgnoreCase(selectType)) {
                        return edItemDiscGroup;
                    } else {
                        //Do not allow editing
                        return null;
                    }
                } else {
                    return super.getCellEditor(row, column);
                }
            }
        };

        table.setLookupToColumn("dimension1", lkpDimension1);
        table.setLookupToColumn("dimension2", lkpDimension2);
        table.setLookupToColumn("dimension3", lkpDimension3);

        table.setComboBoxLookupToColumn("customerSelectType", cmbCustSelectType);
        table.setComboBoxLookupToColumn("itemSelectType", cmbItemSelectType);

        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);

        drm.setTablePanel(tableScroll);

        panel.add(tableScroll);
        return panel;
    }
}

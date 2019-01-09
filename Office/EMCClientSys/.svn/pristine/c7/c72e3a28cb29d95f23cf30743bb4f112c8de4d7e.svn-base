/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.creditnoteregister;

import emc.app.components.documents.EMCDoubleFormDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.inventory.register.forms.superclass.RegisterDRM;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.factory.EMCDimensionLookupFactory;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.lookup.popup.EMCMultiValuePopup;
import emc.app.inventory.register.RegisterFormInterface;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.debtors.creditnoteregister.DebtorsCreditNoteRegister;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryWarehouse;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.register.RegisterFormTypeEnum;
import emc.enums.inventory.transactions.TransEnum;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.inventory.menuitems.display.Warehouse;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JSplitPane;

/**
 *
 * @author riaan
 */
public class DebtorsCreditNoteRegisterForm extends BaseInternalFrame implements RegisterFormInterface {

    private RegisterDRM dataRelation;
    private EMCUserData userData;
    private List keys;
    private EMCQuery locationLookupQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class);

    private emcDataRelationManagerUpdate linesDRM;
    
    // <editor-fold defaultstate="collapsed" desc="Title Text Fields">
    private emcJTextField txtItem = new emcJTextField();
    private emcJTextField txtDesc = new emcJTextField();
    private emcJTextField txtDimension1 = new emcJTextField();
    private emcJTextField txtDimension2 = new emcJTextField();
    private emcJTextField txtDimension3 = new emcJTextField();
    private emcJTextField txtTotalQty = new emcJTextField();
    private emcJTextField txtRegisteredQty = new emcJTextField();
    private String itemIdForLookup;
    // </editor-fold>

    /** Creates a new instance of DebtorsCreditNoteRegisterForm. */
    public DebtorsCreditNoteRegisterForm(EMCUserData userData) {
        super("Register", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 550);
        this.keys = (List) userData.getUserData(12);
        userData.getUserData().remove(12);

        linesDRM = (emcDataRelationManagerUpdate)userData.getUserData().remove(12);
        
        //These fields do not apply
        this.keys.remove("width");
        this.keys.remove("uom");
        this.userData = userData.copyUserDataAndDataList();
        dataRelation = new RegisterDRM(
                new emcGenericDataSourceUpdate(new DebtorsCreditNoteRegister(), userData), false, userData) {

            @Override
            public void doRelation(int rowIndex) {
                super.doRelation(rowIndex);

                if (rowIndex == 0) {
                    rowIndex = getLastRowAccessed();
                }

                locationLookupQuery.removeAnd("warehouseId");
                locationLookupQuery.addAnd("warehouseId", this.getFieldValueAt(rowIndex, "warehouse"));
            }

            @Override
            public void updatePersist(int rowIndex) {
                super.updatePersist(rowIndex);

                if (getLastUpdateStatus()) {
                    linesDRM.refreshRecord(linesDRM.getLastRowAccessed());
                }
            }

            @Override
            public void deleteRowCache(int rowIndex) {
                super.deleteRowCache(rowIndex);

                if (this.getRowCount() == 1 && (Long)this.getLastFieldValueAt("recordID") == 0) {
                    //All lines deleted, refresh data on Credit Note lines
                    linesDRM.refreshData();
                } else {
                    //There are still registered items.
                    linesDRM.refreshRecord(linesDRM.getLastRowAccessed());
                }
            }

        };
        this.setDataManager(dataRelation);
        dataRelation.setTheForm(this);
        dataRelation.setFormTextId1("masterId");
        dataRelation.setFormTextId2("transId");
        setTitleValues();
        initFrame();
    }

    private void setTitleValues() {
        Object item = userData.getUserData(12);
        Object desc = userData.getUserData(5);
        Object dim1 = userData.getUserData(6);
        Object dim2 = userData.getUserData(7);
        Object dim3 = userData.getUserData(8);
        Object qty = userData.getUserData(9);

        itemIdForLookup = (String) userData.getUserData(4);
        txtItem.setText(Functions.checkBlank(item) ? "" : item.toString());
        txtDesc.setText(Functions.checkBlank(desc) ? "" : desc.toString());
        txtDimension1.setText(Functions.checkBlank(dim1) ? "" : dim1.toString());
        txtDimension2.setText(Functions.checkBlank(dim2) ? "" : dim2.toString());
        txtDimension3.setText(Functions.checkBlank(dim3) ? "" : dim3.toString());
        txtTotalQty.setText(Functions.checkBlank(qty) ? "" : qty.toString());
        dataRelation.setMasterId(userData.getUserData(2).toString());
        dataRelation.setTransId(userData.getUserData(3).toString());
        dataRelation.setWarehouse(userData.getUserData(10) == null ? "" : userData.getUserData(10).toString());
        dataRelation.setType(userData.getUserData(11).toString());
        dataRelation.setFormType(RegisterFormTypeEnum.RETURN.toString());
        dataRelation.setFieldValueAt(dataRelation.getLastRowAccessed(), "totalQty", Double.valueOf(Functions.checkBlank(qty) ? 0 : Double.valueOf(qty.toString())));
    }

    private void initFrame() {
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, titlePane(), tablePane());
        topBottomSplit.setDividerLocation(200);
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Register", topBottomSplit);
        thePanel.add(tabbed);
        this.add(thePanel);
    }

    private emcJPanel titlePane() {
        txtItem.setEditable(false);
        txtDesc.setEditable(false);
        txtDimension1.setEditable(false);
        txtDimension2.setEditable(false);
        txtDimension3.setEditable(false);
        txtTotalQty.setEditable(false);
        txtTotalQty.setDocument(new EMCDoubleFormDocument(dataRelation, "totalQty"));
        txtRegisteredQty.setEditable(false);
        txtRegisteredQty.setDocument(new EMCDoubleFormDocument(dataRelation, "registeredQty"));
        Component[][] comps = {{new emcJLabel("Item Id"), txtItem, new emcJLabel("Description"), txtDesc},
            {new emcJLabel("Config"), txtDimension1, new emcJLabel("Colour"), txtDimension3},
            {new emcJLabel("Size"), txtDimension2},
            {new emcJLabel("Total Qty"), txtTotalQty, new emcJLabel("Registered Qty"), txtRegisteredQty}};
        return emcSetGridBagConstraints.createSimplePanel(comps, GridBagConstraints.NONE, true, "Item");
    }

    private emcJPanel tablePane() {
        keys.add("quantity");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLookups(table);

        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataRelation.setTablePanel(tableScroll);
        emcJPanel thePanel = new emcJPanel(new GridLayout());
        thePanel.add(tableScroll);
        return thePanel;
    }

    private void setupLookups(emcJTableUpdate table) {
        EMCUserData lkpUD = userData.copyUserData();

        //Allow summary ds bean to do grouping.
        lkpUD.setUserData(2, TransEnum.ADD_EXTRA.toString());

        Map<String, String> formKeys = new HashMap<String, String>();
        if (this.keys.contains("warehouse")) {
           formKeys.put("warehouse", "warehouse");
        }
        if (this.keys.contains("batch")) {
            formKeys.put("batch", "batch");
        }
        if (this.keys.contains("serial")) {
            formKeys.put("serial", "serialNo");
        }
        if (this.keys.contains("location")) {
            formKeys.put("location", "location");
        }
        if (this.keys.contains("pallet")) {
            formKeys.put("pallet", "pallet");
        }

        boolean location = false;
        for (String key : formKeys.keySet()) {
            //Location may be changed
            if (!key.equals("location")) {
                //    table.setColumnEditable(key, false);
                //    continue;
            } else {
                //Location is displayed on the form
                location = true;
            }
        }

        EMCLookupJTableComponent lkpWarehouse = new EMCLookupJTableComponent(new Warehouse());
        lkpWarehouse.setPopup(new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", userData));

        table.setLookupToColumn("warehouse", lkpWarehouse);
        
        if (location) {
            EMCLookupJTableComponent lkpLocation = EMCDimensionLookupFactory.createLocationJTableLookup(userData);
            lkpLocation.setTheQuery(locationLookupQuery);

            table.setLookupToColumn("location", lkpLocation);
        }
    }

    @Override
    public String getItemId() {
        return itemIdForLookup;
    }

    @Override
    public String getDimension1() {
        return Functions.checkBlank(txtDimension1.getText()) ? null : txtDimension1.getText();
    }

    @Override
    public String getDimension2() {
        return Functions.checkBlank(txtDimension2.getText()) ? null : txtDimension2.getText();
    }

    public String getDimension3() {
        return Functions.checkBlank(txtDimension3.getText()) ? null : txtDimension3.getText();
    }

    @Override
    public void populateUserDataForPermissions(EMCUserData userData) {
        for (int i = 0; i < 12; i++) {
            userData.setUserData(i, "");
        }

        userData.setUserData(9, 0d);
        userData.setUserData(12, new ArrayList());
    }
}

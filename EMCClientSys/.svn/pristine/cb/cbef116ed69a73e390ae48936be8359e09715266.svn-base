/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.inventory.register.forms.remove;

import emc.app.components.documents.EMCDoubleFormDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.inventory.register.forms.superclass.RegisterDRM;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCMultiValuePopup;
import emc.app.inventory.register.RegisterFormInterface;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.inventory.register.InventoryRemoveRegister;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.datasource.InventorySummaryDS;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.register.RegisterFormTypeEnum;
import emc.enums.inventory.transactions.TransEnum;
import emc.enums.modules.enumEMCModules;
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
 * @author wikus
 */
public class RemoveRegisterForm extends BaseInternalFrame implements RegisterFormInterface {

    private RegisterDRM dataRelation;
    private EMCUserData userData;
    private List keys;
    private EMCQuery lookupQuery;

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

    public RemoveRegisterForm(EMCUserData userData) {
        super("Register Remove Items", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 550);
        try {
            this.keys = (List) userData.getUserData(12);
            userData.getUserData().remove(12);
            //These fields do not apply
            this.keys.remove("width");
            this.keys.remove("uom");
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new RegisterDRM(
                    new emcGenericDataSourceUpdate(enumEMCModules.INVENTORY.getId(), new InventoryRemoveRegister(), userData), false, userData) {

                @Override
                public void setUserData(EMCUserData userData) {
                    super.setUserData(userData);
                    RemoveRegisterForm.this.userData = userData;

                    RemoveRegisterForm.this.setTitleValues();
                }
            };
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("masterId");
            dataRelation.setFormTextId2("transId");
        } catch (Exception ex) {
        }
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
        dataRelation.setFormType(RegisterFormTypeEnum.MUST_EXIST.toString());
        dataRelation.setFieldValueAt(dataRelation.getLastRowAccessed(), "totalQty", Double.valueOf(Functions.checkBlank(qty) ? 0 : Double.valueOf(qty.toString())));

        this.lookupQuery = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
        this.lookupQuery.addAnd("itemId", itemIdForLookup);
        if (!Functions.checkBlank(dim1)) {
            this.lookupQuery.addAnd("dimension1", dim1);
        }
        if (!Functions.checkBlank(dim2)) {
            this.lookupQuery.addAnd("dimension2", dim2);
        }
        if (!Functions.checkBlank(dim3)) {
            this.lookupQuery.addAnd("dimension3", dim3);
        }

        //TODO Hack - Ignores very small positive values
        this.lookupQuery.addOrHavingAggregateFunction("SUM", InventorySummary.class.getName(), "physicalTotal", EMCQueryConditions.GREATER_THAN, 0.0000001);
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
        this.setTablePanel(tableScroll);
        emcJPanel thePanel = new emcJPanel(new GridLayout());
        thePanel.add(tableScroll);
        return thePanel;
    }

    private void setupLookups(emcJTableUpdate table) {
        EMCUserData lkpUD = userData.copyUserData();

        //Allow summary ds bean to do grouping.
        lkpUD.setUserData(2, TransEnum.ADD_EXTRA.toString());

        List keys = new ArrayList();
        Map<String, String> formKeys = new HashMap<String, String>();
        if (this.keys.contains("warehouse")) {
            keys.add("warehouse");
            formKeys.put("warehouse", "warehouse");
        }
        if (this.keys.contains("batch")) {
            keys.add("batch");
            formKeys.put("batch", "batch");
        }
        if (this.keys.contains("serial")) {
            keys.add("serialNo");
            formKeys.put("serial", "serialNo");
        }
        if (this.keys.contains("location")) {
            keys.add("location");
            formKeys.put("location", "location");
        }
        if (this.keys.contains("pallet")) {
            keys.add("pallet");
            formKeys.put("pallet", "pallet");
        }

        keys.add("physicalAvailable");
        keys.add("qcAvailable");
        keys.add("recAvailable");

        EMCLookupJTableComponent registerLookup = new EMCLookupJTableComponent(new Warehouse());
        registerLookup.setPopup(new EMCMultiValuePopup(new InventorySummaryDS(), "warehouse", keys, dataRelation, formKeys, lkpUD, true));

        registerLookup.setTheQuery(this.lookupQuery);

        //Only show lookup on first column, then disable others.
        table.setLookupToColumn(this.keys.get(0).toString(), registerLookup);

        for (String key : formKeys.keySet()) {
            //Don't disable first column
            if (key.equals(this.keys.get(0))) {
                continue;
            }
            table.setColumnEditable(key, false);
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

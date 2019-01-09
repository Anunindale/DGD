/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.inventoryreference;

import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.datasource.InventoryReferenceDS;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.CustomerReference;
import emc.menus.inventory.menuitems.display.SupplierReferenceItem;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class ReferenceForm extends BaseInternalFrame {

    private emcJPanel pnlReference = new emcJPanel();
    private emcJPanel pnlItem = new emcJPanel();
    private emcJTextField itemDescription = new emcJTextField();
    private emcJLabel lblItem = new emcJLabel("Item No");
    private emcJLabel lblDescription = new emcJLabel("Description");
    //DataSource
    private InventoryReferenceDRM dataRelation;
    private EMCControlLookupComponent lookupItem;
    private EMCLookupJTableComponent lookupRefType;
    private EMCLookupJTableComponent lookupSupplierNo;
    private EMCLookupJTableComponent lookupCustomerNo;
    private EMCLookupTableCellEditor refEditor;
    private EMCLookupTableCellEditor suppEditor;
    private EMCLookupTableCellEditor custEditor;
    private emcTableModelUpdate m;

    public ReferenceForm(EMCUserData userData) {
        super("Item Reference", true, true, true, true, userData);
        EMCUserData copyUD = userData.copyUserDataAndDataList();

        EMCQuery query = (EMCQuery)copyUD.getUserData(0);
        InventoryReferenceDS instance = new InventoryReferenceDS();
        if (query == null) {
            query = instance.getQuery();
            copyUD.setUserData(0, query);
        }
        
        //Only show records without dimensions
        query.addAnd("dimension1", null);
        query.addAnd("dimension2", null);
        query.addAnd("dimension3", null);

        this.setBounds(20, 20, 750, 290);
        try {

            dataRelation = new InventoryReferenceDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), instance, userData), userData);

            this.setDataManager(dataRelation);

            lookupItem = EMCItemLookupFactory.createItemControllLookup(dataRelation, itemDescription, InventoryReference.class.getName(), copyUD);
            lookupItem.setFormQuery(query);
            lookupRefType = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.ReferenceType());
            List refKeys = new ArrayList();
            refKeys.add("type");
            refKeys.add("description");
            EMCLookupPopup refPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryReferenceType(), "type", refKeys, copyUD);
            lookupRefType.setPopup(refPopup);
            refEditor = new EMCLookupTableCellEditor(lookupRefType);

            lookupSupplierNo = new EMCLookupJTableComponent(new emc.menus.pop.menuitems.display.Suppliers());
            List suppKeys = new ArrayList();
            suppKeys.add("supplierId");
            suppKeys.add("supplierName");
            EMCLookupPopup suppPopup = new EMCLookupPopup(enumEMCModules.POP.getId(), new emc.entity.pop.POPSuppliers(), "supplierId", suppKeys, copyUD);
            lookupSupplierNo.setPopup(suppPopup);
            suppEditor = new EMCLookupTableCellEditor(lookupSupplierNo);

            lookupCustomerNo = new EMCLookupJTableComponent(new emc.menus.sop.menuitems.display.SOPCustomersMenu());
            List custKeys = new ArrayList();
            custKeys.add("customerId");
            custKeys.add("customerName");
            EMCLookupPopup custPopup = new EMCLookupPopup(enumEMCModules.SOP.getId(), new emc.entity.sop.SOPCustomers(), "customerId", custKeys, copyUD);
            lookupCustomerNo.setPopup(custPopup);
            custEditor = new EMCLookupTableCellEditor(lookupCustomerNo);

            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("itemId");
            dataRelation.setFormTextId2("reference");
        } catch (Exception e) {
            e.printStackTrace();
        }
        itemDescription.setEditable(false);
        initFrame();
    }

    private void tabItemPane() {
        pnlItem.setBorder(javax.swing.BorderFactory.createTitledBorder("Item"));
        GridBagConstraints gbc;
        gbc = emcSetGridBagConstraints.createStandard(0, 0, 0.1, GridBagConstraints.FIRST_LINE_START);
        pnlItem.add(this.lblItem, gbc);
        gbc = emcSetGridBagConstraints.changePosition(gbc, 1, 0, 0.1);
        pnlItem.add(this.lookupItem, gbc);
        gbc = emcSetGridBagConstraints.changePosition(gbc, 2, 0, 0.1);
        pnlItem.add(this.lblDescription, gbc);
        gbc = emcSetGridBagConstraints.changePosition(gbc, 2, 0, 0.1);
        pnlItem.add(this.itemDescription, gbc);
    }

    private void tabReferencePane() {
        GridBagConstraints gbc;
        List keys = new ArrayList();
        keys.add("reference");
        keys.add("refType");
        keys.add("customerNo");
        keys.add("supplierNo");
        keys.add("alternativeDescription");
        m = new emcTableModelUpdate(this.dataRelation, keys);

        emcJTableUpdate table = new emcJTableUpdate(m) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //Do not allow user to edit primary reference column.
                if (column == 1 &&
                        (String.valueOf(getValueAt(row, column)).equals(InventoryReferenceTypes.PRIMARY.toString()) ||
                        String.valueOf(getValueAt(row, column)).equals(InventoryReferenceTypes.DEFAULT.toString()))) {
                    return false;
                } else {
                    return super.isCellEditable(row, column);
                }
            }
        };

        //Lookups
        table.setLookupCellEditor(1, refEditor);
        table.setLookupCellEditor(2, custEditor);
        table.setLookupCellEditor(3, suppEditor);
  
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        pnlReference.setLayout(
                new GridBagLayout());

        gbc = emcSetGridBagConstraints.createStandard(0, 0, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        pnlReference.add(pnlItem, gbc);
        gbc = emcSetGridBagConstraints.changePosition(gbc, 0, 1, 1.0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        pnlReference.add(tableScroll, gbc);
        this.setTablePanel(tableScroll);
    }

    private emcJPanel buttonPane() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();
        buttons.add(new emcMenuButton("Supplier Ref.", new SupplierReferenceItem(), this, 0, false));
        buttons.add(new emcMenuButton("Customer Ref.", new CustomerReference(), this, 1, false));

//        if (getUserData().getUserName().equals("emc")) {
//            buttons.add(new emcJButton("Populate Table") {
//
//                @Override
//                public void doActionPerformed(ActionEvent evt) {
//                    super.doActionPerformed(evt);
//
//                    EMCCommandClass cmd = new EMCCommandClass(ServerInventoryMethods.IMPORT_REFERENCES);
//
//                    EMCWSManager.executeGenericWS(cmd, new ArrayList(), getUserData());
//                }
//            });
//        }
        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }

    private void initFrame() {
        emcJTabbedPane tabbedPane = new emcJTabbedPane();
        tabItemPane();
        tabReferencePane();
        tabbedPane.add("Item References", this.pnlReference);
        emcJPanel mainPane = new emcJPanel(new BorderLayout(1, 1));
        mainPane.add(tabbedPane, BorderLayout.CENTER);
        mainPane.add(buttonPane(), BorderLayout.EAST);
        this.add(mainPane);
    }
}

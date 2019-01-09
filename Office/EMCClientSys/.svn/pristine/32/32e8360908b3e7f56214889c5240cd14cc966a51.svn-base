/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.warehouses;

import emc.app.components.EMCFormComboBox;
import emc.app.components.documents.EMCBigDecimalFormDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcHelpFile;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.inventory.InventoryWarehouseType;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.inventory.InventoryLocation;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.printtypes.StockTakePrintType;
import emc.enums.modules.enumEMCModules;
import emc.enums.pop.purchaseorders.PurchaseOrderReceivedLabelTypes;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.inventory.menuitems.display.LocationMenu;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.BorderFactory;

/**
 *
 * @author riaan
 */
public class WarehouseForm extends BaseInternalFrame {

    private emcJPanel pnlWarehouses = new emcJPanel();
    private emcJLabel lblAddressLine1 = new emcJLabel("Address Line 1");
    private emcJTextField txtAddressLine1 = new emcJTextField();
    private emcJLabel lblAddressLine2 = new emcJLabel("Address Line 2");
    private emcJTextField txtAddressLine2 = new emcJTextField();
    private emcJLabel lblAddressLine3 = new emcJLabel("Address Line 3");
    private emcJTextField txtAddressLine3 = new emcJTextField();
    private emcJLabel lblAddressLine4 = new emcJLabel("Address Line 4");
    private emcJTextField txtAddressLine4 = new emcJTextField();
    private emcJLabel lblAddressLine5 = new emcJLabel("Address Line 5");
    private emcJTextField txtAddressLine5 = new emcJTextField();
    private emcJLabel lblAddressPostalCode = new emcJLabel("Postal Code");
    private EMCLookupFormComponent lkpAddressPostalCode;
    //DataSource
    private emcDataRelationManagerUpdate dataRelation;
    private EMCUserData copyUD;

    public WarehouseForm(EMCUserData userData) {
        super("Warehouses", true, true, true, true, userData);
        this.setBounds(20, 20, 620, 290);
        this.setHelpFile(new emcHelpFile("Inventory/InventoryWarehouses.html"));
        try {
            copyUD = userData.copyUserData();
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryWarehouse(), userData), userData) {

                @Override
                public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
                    EMCUserData formUD = super.generateRelatedFormUserData(formUserData, Index);
                    Object warehouse = this.getFieldValueAt(this.getLastRowAccessed(), "warehouseId");
                    if (!Functions.checkBlank(warehouse)) {
                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class.getName());
                        query.addAnd("warehouseId", warehouse);
                        query.addOrderBy("locationId");
                        query.addAnd("companyId", formUD.getCompanyId());
                        List x = new ArrayList();
                        x.add(0, query);
                        x.add(1, "");
                        x.add(2, this.getFieldValueAt(this.getLastRowAccessed(), "description"));
                        x.add(3, warehouse);
                        formUD.setUserData(x);
                    }
                    return formUD;
                }
            };
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("warehouseId");
            dataRelation.setFormTextId2("description");

            txtAddressLine1.setDocument(new EMCStringFormDocument(dataRelation, "addressLine1"));
            txtAddressLine2.setDocument(new EMCStringFormDocument(dataRelation, "addressLine2"));
            txtAddressLine3.setDocument(new EMCStringFormDocument(dataRelation, "addressLine3"));
            txtAddressLine4.setDocument(new EMCStringFormDocument(dataRelation, "addressLine4"));
            txtAddressLine5.setDocument(new EMCStringFormDocument(dataRelation, "addressLine5"));

        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Failed to create WarehouseForm", userData);
            }
        }
        initFrame();
    }

    private void tabWarehouses() {
        List keys = new ArrayList();
        keys.add("warehouseId");
        keys.add("description");
        keys.add("type");
        keys.add("RECAvailable");
        keys.add("QCAvailable");
        keys.add("mto");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlWarehouses.setLayout(new GridLayout(1, 1));

        toptable.setComboBoxLookupToColumn(2, new InventoryWarehouseType());

        pnlWarehouses.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private emcJPanel printDetail() {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        String[] comboPrintArray = new String[services.length];
        for (int i = 0; i < services.length; i++) {
            comboPrintArray[i] = services[i].getName();
        }
        final EMCFormComboBox printerCombo = new EMCFormComboBox(comboPrintArray, dataRelation, "printerName");
        Component[][] components = {
            {new emcJLabel("Pick List Printer"), printerCombo}};

        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        thePanel.setBorder(BorderFactory.createTitledBorder("Printer"));

        return thePanel;
    }

    private emcJPanel pnlAddress() {
        List postalCodeKeys = new ArrayList();
        postalCodeKeys.add("code");
        postalCodeKeys.add("suburb");
        postalCodeKeys.add("country");

        lkpAddressPostalCode = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.PostalCodes(),
                dataRelation, "postalCode");
        EMCLookupPopup postalCodePopup = new EMCLookupPopup(enumEMCModules.BASE.getId(), new emc.entity.base.BasePostalCodes(),
                "code", postalCodeKeys, copyUD);
        lkpAddressPostalCode.setPopup(postalCodePopup);
        dataRelation.setDocument(lkpAddressPostalCode.getDocument());
        lkpAddressPostalCode.setPreferredSize(new java.awt.Dimension(80, 25));

        Component[][] components = {
            {new emcJLabel(), new emcJLabel("Physical Address")},
            {lblAddressLine1, txtAddressLine1},
            {lblAddressLine2, txtAddressLine2},
            {lblAddressLine3, txtAddressLine3},
            {lblAddressLine4, txtAddressLine4},
            {lblAddressLine5, txtAddressLine5},
            {lblAddressPostalCode, lkpAddressPostalCode}};

        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        thePanel.setBorder(BorderFactory.createTitledBorder("Delivery"));

        return thePanel;
    }

    private void initFrame() {
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabWarehouses();
        tabbedPanetop.add("Warehouses", this.pnlWarehouses);
        tabbedPanetop.add("Address", pnlAddress());
        tabbedPanetop.add("Printer", printDetail());
        tabbedPanetop.add("Goods Received", createGoodsReceivedPanel());
        tabbedPanetop.add("Stock Take", stockTakePane());
        thePanel.add(tabbedPanetop, BorderLayout.CENTER);
        thePanel.add(buttonPane(), BorderLayout.EAST);
        this.add(thePanel);
    }

    private emcJPanel buttonPane() {
        emcMenuButton btnLocation = new emcMenuButton("Location", new LocationMenu(), this, 0, false);
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnLocation);
        if (copyUD.getUserName().equals("emc")) {
            buttonList.add(new emcJButton("Generate") {

                @Override
                public void doActionPerformed(ActionEvent evt) {
                    super.doActionPerformed(evt);
                    EMCCommandClass cmd = new EMCCommandClass(ServerInventoryMethods.POPULATE_SYSTEM_LOCATIONS);
                    EMCWSManager.executeGenericWS(cmd, new ArrayList<Object>(), copyUD);
                }
            });
        }
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private emcJPanel stockTakePane() {
        emcJTextField txtQuantityDiff = new emcJTextField(new EMCBigDecimalFormDocument(dataRelation, "stockTakeQuantityDiff"));
        emcJTextField txtValueDiff = new emcJTextField(new EMCBigDecimalFormDocument(dataRelation, "stockTakeValueDiff"));
        EMCFormComboBox cbPrintType = new EMCFormComboBox(StockTakePrintType.values(), dataRelation, "stockTakePrintType");
        Component[][] comp = {{new emcJLabel("Recount if quantity difference exceeds %"), txtQuantityDiff},
            {new emcJLabel("Recount if value difference exceeds"), txtValueDiff},
            {new emcJLabel("Count Sheet Print Type"), cbPrintType}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Stock Take");
    }

    /** Creates the Goods Received panel. */
    private emcJPanel createGoodsReceivedPanel() {
        emcJLabel lblLabelType = new emcJLabel(dataRelation.getColumnName("purchaseOrderReceivedLabelType"));
        EMCFormComboBox cmbLabelType = new EMCFormComboBox(PurchaseOrderReceivedLabelTypes.values(), dataRelation, "purchaseOrderReceivedLabelType");

        Component[][] components = new Component[][]{
            {lblLabelType, cmbLabelType}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }
}

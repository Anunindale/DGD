/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.barcodes;

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
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCDebug;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author wikus
 */
public class BarcodeForm extends BaseInternalFrame {

    //DataSource
    private emcDataRelationManagerUpdate dataRelation;
    private EMCUserData copyUD;
    private emcJPanel pnlBarcodes = new emcJPanel();
    //Lookups
    private EMCLookupTableCellEditor edtItem;
    private EMCLookupTableCellEditor edtDim1;
    private EMCLookupTableCellEditor edtDim2;
    private EMCLookupTableCellEditor edtDim3;

    public BarcodeForm(EMCUserData userData) {
        super("Bar Codes", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        try {
            copyUD = userData.copyUserData();
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.datasource.InventoryBarcodesDS(), userData), userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("barcode");
            dataRelation.setFormTextId2("itemPrimaryReference");

        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Failed to create WarehouseForm", userData);
            }
        }
        initFrame();
    }

    private void tabBarcodes() {
        List keys = new ArrayList();
        keys.add("itemPrimaryReference");
        keys.add("itemDesc");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("barcode");
        keys.add("barcodeType");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(m) {

            @Override
            public boolean isCellEditable(int arg0, int arg1) {
                if (arg1 == 1) return false;
                return super.isCellEditable(arg0, arg1);
            }
        };
        setupLookups();
        table.setLookupCellEditor(0, edtItem);
        table.setLookupCellEditor(2, edtDim1);
        table.setLookupCellEditor(3, edtDim3);
        table.setLookupCellEditor(4, edtDim2);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(table);
        pnlBarcodes.setLayout(new GridLayout(1, 1));
        pnlBarcodes.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabBarcodes();
        tabbedPanetop.add("Barcodes", this.pnlBarcodes);
        this.add(tabbedPanetop);
    }

    private void setupLookups() {
        BarcodeLookupRM lrm = new BarcodeLookupRM();
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
    }
}


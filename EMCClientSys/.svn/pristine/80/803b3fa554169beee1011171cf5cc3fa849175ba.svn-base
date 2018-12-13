/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.onhand;

import emc.app.components.documents.EMCBigDecimalFormDocument;
import emc.app.components.documents.EMCDoubleFormDocument;
import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.inventory.emcStockButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.inventory.transactions.datasource.InventorySummaryDS;
import emc.enums.modules.enumEMCModules;
import emc.forms.app.ActiveDimColumnData;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.ItemMaster;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;

/**
 *
 * @author rico
 */
public class OnHandForm extends BaseInternalFrame {

    private emcJPanel pnlOverview = new emcJPanel();
    private emcJPanel pnlButtons;
    private emcJPanel thePanel = new emcJPanel();
    private emcJPanel purchasePanel = new emcJPanel();
    //DataSource
    private OnHandDRM dataRelation;

    public OnHandForm(EMCUserData userData) {
        super("On Hand", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 400);
        boolean opendFromMenu;
        if (userData.getUserData(0) == null) {
            opendFromMenu = true;
        } else {
            opendFromMenu = false;
        }
        InventorySummaryDS entityInstance = new InventorySummaryDS();
        if (userData.getUserData(1) == null || !(userData.getUserData(1) instanceof Boolean)) {
            userData.setUserData(0, entityInstance.getQuery());
            userData.setUserData(1, true);
        } else {
            userData.setUserData(0, userData.getUserData(0));
            userData.setUserData(1, true);
        }
        StockDRMParameters param = new StockDRMParameters("itemId", "dimension1", "dimension2", "dimension3", "inventoryTransRef", "serialNo", "batch", "warehouse", "location");
        dataRelation = new OnHandDRM(new emcGenericDataSourceUpdate(
                enumEMCModules.INVENTORY.getId(), entityInstance, opendFromMenu, userData), param, !opendFromMenu, userData);
        this.setDataManager(dataRelation);
        //add the form to the data relation
        dataRelation.setTheForm(this);
        dataRelation.setFormTextId1("itemPrimaryReference");
        dataRelation.setFormTextId2("description");

        initFrame();

        if (opendFromMenu) {
            ActiveDimColumnData data = new ActiveDimColumnData();
            data.setDimention1(true);
            data.setDimention2(true);
            data.setDimention3(true);
            data.setWarehouse(true);
            data.setBatch(true);
            data.setSerialNo(true);
            data.setLocation(true);
            data.setPallet(true);
            dataRelation.setChecked();
            dataRelation.setTableColumns(data);
            dataRelation.getTableScroll().makeSearchVisible();
        }
    }

    private void tabOverview() {
        List keys = new ArrayList();
        keys.add("itemPrimaryReference");
        keys.add("physicalTotal");
        keys.add("physicalReserved");
        keys.add("physicalAvailable");
        keys.add("orderedTotal");
        // keys.add("orderedReserved");
        //keys.add("orderedAvailable");
        keys.add("orderedOut");
        // keys.add("recAvailable");
        // keys.add("qcAvailable");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys) {

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        toptable.setAllowOrderOfTable(false);
        toptable.setLookupCellEditor(0, new EMCGoToMainTableEditor(new EMCStringDocument(), new ItemMaster()));
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlOverview.setLayout(new GridLayout(1, 1));
        pnlOverview.add(topscroll);
        dataRelation.setTablePanel(topscroll);
    }

    private void tabButtons() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        
        buttonList.add(new emcStockButton(this, false));
     
        pnlButtons = emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private emcJPanel tabPurchase() {
        emcJTextField txtOSBlanketOrd = new emcJTextField();
        txtOSBlanketOrd.setEditable(false);
        txtOSBlanketOrd.setDocument(new EMCDoubleFormDocument(dataRelation, "qtyOSBlanketOrd"));
        emcJTextField txtOrderedTotal = new emcJTextField();
        txtOrderedTotal.setEditable(false);
        txtOrderedTotal.setDocument(new EMCDoubleFormDocument(dataRelation, "orderedTotal"));
        emcJTextField txtOrderedReserved = new emcJTextField();
        txtOrderedReserved.setEditable(false);
        txtOrderedReserved.setDocument(new EMCDoubleFormDocument(dataRelation, "orderedReserved"));
        Component[][] components = {
            {new emcJLabel("OutSt. Blanket Order"), txtOSBlanketOrd},
            {new emcJLabel(dataRelation.getDataType("orderedTotal").getEmcLabel()), txtOrderedTotal},
            {new emcJLabel(dataRelation.getDataType("orderedReserved").getEmcLabel()), txtOrderedReserved}};

        emcJPanel pnlAccounting = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        pnlAccounting.setBorder(BorderFactory.createTitledBorder("Purchase"));

        return pnlAccounting;
    }

    private emcJPanel notPanel() {
        emcJTextField txtREC = new emcJTextField(new EMCDoubleFormDocument(dataRelation, "recAvailable"));
        txtREC.setEditable(false);

        emcJTextField txtQC = new emcJTextField(new EMCDoubleFormDocument(dataRelation, "qcAvailable"));
        txtQC.setEditable(false);
        Component[][] components = {
            {new emcJLabel("Held in REC"), txtREC},
            {new emcJLabel("Held in QC"), txtQC}};
        emcJPanel notPanel = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        notPanel.setBorder(BorderFactory.createTitledBorder("Stock Not Available"));
        return notPanel;
    }

    /** Creates the sales panel. */
    private emcJPanel createSalesPanel() {
        Component[][] components = new Component[][]{
            {new emcJLabel(dataRelation.getColumnName("qtySOPBlanketOrder")), new emcJTextField(new EMCBigDecimalFormDocument(dataRelation, "qtySOPBlanketOrder"))}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }

    private void initFrame() {
        emcJPanel mainPanel = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabButtons();
        tabOverview();
        purchasePanel = tabPurchase();
        thePanel.setLayout(new BorderLayout());
        thePanel.add(this.pnlOverview, BorderLayout.CENTER);
        tabbedPanetop.add("Overview", thePanel);
        tabbedPanetop.add("Purchase", purchasePanel);
        tabbedPanetop.add("Sales", createSalesPanel());
        tabbedPanetop.add("Not Available", notPanel());
        mainPanel.add(tabbedPanetop, BorderLayout.CENTER);
        mainPanel.add(pnlButtons, BorderLayout.EAST);
        this.add(mainPanel);
    }
}

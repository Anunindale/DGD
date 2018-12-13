package emc.forms.inventory.display.transactions;

import emc.app.components.documents.EMCDoubleFormDocument;
import emc.app.components.documents.EMCStringDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcHelpFile;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.inventory.emcStockButton;
import emc.app.components.queryswitchbutton.EMCQuerySwitchButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.datatypes.EMCDataType;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.entity.inventory.movestock.InventoryMoveStockSummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.inventory.transactions.InventoryTransactionTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.app.ActiveDimColumnData;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.menus.debtors.menuitems.display.DebtorsCreditNotes;
import emc.menus.debtors.menuitems.display.DebtorsCustomerInvoices;
import emc.menus.inventory.menuitems.display.ItemMaster;
import emc.menus.inventory.menuitems.display.Journals;
import emc.menus.inventory.menuitems.display.MoveStockSummaryMenu;
import emc.menus.inventory.menuitems.display.Transactions;
import emc.menus.pop.menuitems.display.PurchaseOrders;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author wikus
 */
public class InventoryTransactionsForm extends BaseInternalFrame {

    private emcJPanel thePanel = new emcJPanel();
    private emcJPanel pnlForm = new emcJPanel();
    private emcJPanel pnlButtons = new emcJPanel();
    //DataSource
    private TransactionDRM dataRelation;

    public InventoryTransactionsForm(EMCUserData userData) {
        super("Transactions", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 290);
        this.setHelpFile(new emcHelpFile("Inventory/InventoryTransactionEnums.html"));
        boolean opendFromMenu;
        if (userData.getUserData(0) == null) {
            opendFromMenu = true;
        } else {
            opendFromMenu = false;
        }
        StockDRMParameters param = new StockDRMParameters("itemId", "config", "size", "colour", "transId");
        dataRelation = new TransactionDRM(new emcGenericDataSourceUpdate(
                enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.transactions.datasource.InventoryTransactionsDS(), opendFromMenu, userData), param, !opendFromMenu, userData);
        this.setDataManager(dataRelation);
        dataRelation.setTheForm(this);
        dataRelation.setFormTextId1("itemRef");
        dataRelation.setFormTextId2("refType");
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

    private void initFrame() {
        emcJTabbedPane tabbedPane = new emcJTabbedPane();
        tabButtons();
        tabForm();
        thePanel.setLayout(new BorderLayout());
        thePanel.add(pnlForm, BorderLayout.CENTER);
        thePanel.add(pnlButtons, BorderLayout.EAST);
        tabbedPane.add("Overview", this.thePanel);
        tabbedPane.add("Reference", referencePane());
        tabbedPane.add("Costing", createCostingTab());
        this.add(tabbedPane);
    }

    private void tabButtons() {
        pnlButtons.setLayout(new GridBagLayout());
        pnlButtons.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

        GridBagConstraints gbc;
        int y = 0;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlButtons.add(new emcStockButton(this, false), gbc);

        EMCQuerySwitchButton btnSwitch = new EMCQuerySwitchButton("Open Trans", dataRelation);
        btnSwitch.addQuery("Open Trans", InventoryTransactions.class.getName(), "closedFlag", EMCQueryConditions.EQUALS, false);
        btnSwitch.addQuery("Closed Trans", InventoryTransactions.class.getName(), "closedFlag", EMCQueryConditions.EQUALS, true);
        btnSwitch.addQuery("All Trans", InventoryTransactions.class.getName(), "closedFlag", null, null);
        y++;
        gbc.gridy = y;
        pnlButtons.add(btnSwitch, gbc);

        y++;
        gbc.gridy = y;
        pnlButtons.add(new emcMenuButton("Settlement", new Transactions(), this, 0, false), gbc);

        y++;
        pnlButtons.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
        pnlButtons.setPreferredSize(new Dimension(120, 25));
    }

    private void tabForm() {
        List keys = new ArrayList();
        keys.add("itemRef");
        keys.add("expectedDate");
        keys.add("physicalDate");
        //keys.add("financialDate");
        keys.add("refType");
        keys.add("refNumber");
        //keys.add("type");
        keys.add("status");
        keys.add("direction");
        keys.add("quantity");
        //keys.add("cost");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys) {

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        emcJTableUpdate table = new emcJTableUpdate(m);
        table.setColumnCellEditor("refNumber", new EMCGoToMainTableEditor(new EMCStringDocument(), null) {

            @Override
            public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
                InventoryTransactionTypes type = InventoryTransactionTypes.fromString(dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), "type").toString());
                EMCDataType dt = dataRelation.getDataType("refNumber");
                switch (type) {
                    case PO:
                        this.changeMenuItem(new PurchaseOrders());
                        dt.setRelatedTable(POPPurchaseOrderMaster.class.getName());
                        dt.setRelatedField("purchaseOrderId");
                        break;
                    case JR:
                        this.changeMenuItem(new Journals());
                        dt.setRelatedTable(InventoryJournalMaster.class.getName());
                        dt.setRelatedField("journalNumber");
                        break;
                   case MO:
                        this.changeMenuItem(new MoveStockSummaryMenu());
                        dt.setRelatedTable(InventoryMoveStockSummary.class.getName());
                        dt.setRelatedField("transId");
                        break;
                    case INV:
                        this.changeMenuItem(new DebtorsCustomerInvoices());
                        dt.setRelatedTable(DebtorsCustomerInvoiceMaster.class.getName());
                        dt.setRelatedField("invCNNumber");
                        break;
                    case CN:
                        this.changeMenuItem(new DebtorsCreditNotes());
                        dt.setRelatedTable(DebtorsCreditNoteMaster.class.getName());
                        dt.setRelatedField("invCNNumber");
                        break;
                }
                return super.getTableCellEditorComponent(arg0, arg1, arg2, arg3, arg4);
            }
        });
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        pnlForm.setLayout(new GridLayout(1, 1));
        pnlForm.add(tableScroll);
        dataRelation.setTablePanel(tableScroll);
    }

    private emcJPanel referencePane() {
        emcJTextField txtDocNumber = new emcJTextField();
        txtDocNumber.setDocument(new EMCStringFormDocument(dataRelation, "documentNo"));
        emcJTextField txtTransId = new emcJTextField();
        txtTransId.setDocument(new EMCStringFormDocument(dataRelation, "transId"));
        emcJTextField txtItemRef = new emcJTextField();
        txtItemRef.setDocument(new EMCStringFormDocument(dataRelation, "itemRef"));
        txtItemRef.enableRightClickPopup(dataRelation, new ItemMaster(), "itemId");
        emcJTextField txtType = new emcJTextField(new EMCStringFormDocument(dataRelation, "type"));
        txtTransId.setEditable(false);
        txtDocNumber.setEditable(false);
        txtItemRef.setEditable(false);
        txtType.setEditable(false);
        Component[][] components = {
            {new emcJLabel(dataRelation.getDataType("documentNo").getEmcLabel()), txtDocNumber},
            {new emcJLabel(dataRelation.getDataType("transId").getEmcLabel()), txtTransId},
            {new emcJLabel(dataRelation.getDataType("itemRef").getEmcLabel()), txtItemRef},
            {new emcJLabel(dataRelation.getColumnName("type")), txtType}};

        emcJPanel refPanel = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, false);
        refPanel.setBorder(BorderFactory.createTitledBorder("Reference"));

        return refPanel;
    }

    /** Creates the costing tab. */
    private emcJPanel createCostingTab() {
        EMCDatePickerFormComponent financialDate = new EMCDatePickerFormComponent(dataRelation, "financialDate");
        emcJTextField txtValue = new emcJTextField(new EMCDoubleFormDocument(dataRelation, "cost"));

        financialDate.setEnabled(false);
        txtValue.setEditable(false);

        Component[][] components = {
            {new emcJLabel(dataRelation.getColumnName("financialDate")), financialDate},
            {new emcJLabel(dataRelation.getColumnName("cost")), txtValue}};

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, false, "Costing");
    }
}

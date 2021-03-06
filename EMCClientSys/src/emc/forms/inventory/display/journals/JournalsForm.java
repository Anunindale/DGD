/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.journals;

import emc.app.components.documents.EMCDoubleFormDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.inventory.emcStockButton;
import emc.app.components.inventory.journals.ContraTypesFormDropdown;
import emc.app.components.inventory.register.EMCRegisterButton;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.components.queryswitchbutton.EMCQuerySwitchButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.gl.GLChartOfAccounts;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.dimensions.InventoryItemDimension1Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension2Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension3Setup;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.journals.InventoryJournalTypes;
import emc.enums.base.journals.JournalStatus;
import emc.enums.base.journals.Modules;
import emc.enums.inventory.register.RegisterFormTypeEnum;
import emc.enums.inventory.register.RegisterFromTypeEnum;
import emc.enums.modules.enumEMCModules;
import emc.forms.inventory.display.journals.resources.ApprovalButton;
import emc.forms.inventory.display.journals.resources.MassLineDateUpdateForm;
import emc.framework.EMCMenuItem;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import emc.forms.inventory.display.journals.resources.PostButton;
import emc.forms.inventory.display.journals.resources.PrintJournalButton;
import emc.forms.inventory.display.journals.resources.StockCountButtonList;
import emc.forms.inventory.display.journals.resources.StockTakeButton;
import emc.forms.inventory.display.journals.resources.ValidateButton;
import emc.framework.EMCCommandClass;
import emc.functions.Functions;
import emc.menus.inventory.menuitems.display.InventSerialBatchMenu;
import emc.menus.inventory.menuitems.display.JournalDefinitions;
import emc.menus.inventory.menuitems.display.LocationMenu;
import emc.methods.base.ServerBaseMethods;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riaan
 */
public class JournalsForm extends BaseInternalFrame {

    // <editor-fold defaultstate="collapsed" desc="Master Overview Tab">
    private emcJTableUpdate tblMaster;
    private EMCLookupJTableComponent lkpJournalDefinitionId;
    private EMCLookupTableCellEditor journalDefinitionIdEditor;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Master Detail Tab">
    private emcJTextArea txaJournalText = new emcJTextArea();
    private emcJLabel lblJournalPosting = new emcJLabel("Journal Posting");
    private emcJTextField txtJournalPosting = new emcJTextField();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Master Accounting Tab">
    private emcJLabel lblJournalContractType = new emcJLabel("Journal Contra Type");
    private emcJLabel lblJournalContractAccount = new emcJLabel("Journal Contra Account");
    private emcJLabel lblJournalEnteredBy = new emcJLabel("Journal Entered By");
    private emcJTextField txtJournalEnteredBy = new emcJTextField();
    private emcJLabel lblJournalEnteredDate;
    private EMCDatePickerFormComponent journalEnteredDate;
    private emcJLabel lblJournalApprovedBy = new emcJLabel("Journal Approved By");
    private emcJTextField txtJournalApprovedBy = new emcJTextField();
    private emcJLabel lblJournalApprovedDate;
    private EMCDatePickerFormComponent journalApprovedDate;
    private emcJLabel lblJournalPostedBy = new emcJLabel("Journal Posted By");
    private emcJTextField txtJournalPostedBy = new emcJTextField();
    private emcJLabel lblJournalPostedDate;
    private EMCDatePickerFormComponent journalPostedDate;
    private EMCLookupFormComponent lkpJournalContraAccount;
    private ContraTypesFormDropdown cmbContraType;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Lines Overview Tab">
    private emcJTableUpdate tblLines;
    private EMCLookupJTableComponent lkpLinesTableDimension1;
    private EMCLookupTableCellEditor edtLinesDimension1;
    private EMCLookupJTableComponent lkpLinesTableDimension2;
    private EMCLookupTableCellEditor edtLinesDimension2;
    private EMCLookupJTableComponent lkpLinesTableDimension3;
    private EMCLookupTableCellEditor edtLinesDimension3;
    private EMCLookupJTableComponent lkpLinesTableWarehouse;
    private EMCLookupTableCellEditor edtLinesWarehouse;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Lines General Tab">
    private emcJLabel lblLinesExtRef = new emcJLabel("Ext Ref");
    private emcJTextField txtLinesExtRef = new emcJTextField();
    private emcJLabel lblLinesJournalContraType = new emcJLabel("Contra Type");
    private ContraTypesFormDropdown cmbLinesJournalContraType;
    private emcJLabel lblLinesJournalContraAccount = new emcJLabel("Contra Account");
    private EMCLookupFormComponent lkpJournalLineContraAccount;
    private emcJTextArea txaLinesJournalText = new emcJTextArea();
    private emcJLabel lblLinesTotalCost;
    private emcJTextField txtLinesTotalCost = new emcJTextField();
    private emcJLabel lblLinesInventTransId;
    private emcJTextField txtLinesInventTransId = new emcJTextField();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Lines DimensionTransfer Tab">
    private emcJLabel lblLinesCountQOH = new emcJLabel("Count QOH");
    private emcJTextField txtLinesCountQOH = new emcJTextField();
    private emcJLabel lblLinesCountedQuantity = new emcJLabel("Counted Quantity");
    private emcJTextField txtLinesCountedQuantity = new emcJTextField();
    private emcJLabel lblLinesConfirmedQuantity = new emcJLabel("Confirmed Quantity");
    private emcJTextField txtLinesConfirmedQuantity = new emcJTextField();
    private emcJLabel lblLinesItem;
    private EMCLookupFormComponent lkpLinesItem;
    private emcJLabel lblLinesDescription;
    private emcJTextField txtLinesDescription = new emcJTextField();
    private emcJLabel lblLinesUom;
    private emcJTextField txtLinesUom = new emcJTextField();
    private emcJLabel lblLinesDimension1;
    private EMCLookupFormComponent lkpLinesDimension1;
    private emcJLabel lblLinesDimension2;
    private EMCLookupFormComponent lkpLinesDimension2;
    private emcJLabel lblLinesDimension3;
    private EMCLookupFormComponent lkpLinesDimension3;
    private emcJLabel lblLinesWarehouse;
    private EMCLookupFormComponent lkpLinesWarehouse;
    private emcJLabel lblLinesBatch;
    private EMCLookupFormComponent lkpLinesBatch;
    private emcJLabel lblLinesSerial;
    private EMCLookupFormComponent lkpLinesSerial;
    private emcJLabel lblLinesToItem;
    private EMCLookupFormComponent lkpLinesToItem;
    private emcJLabel lblLinesToDescription;
    private emcJTextField txtLinesToDescription = new emcJTextField();
    private emcJLabel lblLinesToUom;
    private emcJTextField txtLinesToUom = new emcJTextField();
    private emcJLabel lblLinesToDimension1;
    private EMCLookupFormComponent lkpLinesToDimension1;
    private emcJLabel lblLinesToDimension2;
    private EMCLookupFormComponent lkpLinesToDimension2;
    private emcJLabel lblLinesToDimension3;
    private EMCLookupFormComponent lkpLinesToDimension3;
    private emcJLabel lblLinesToWarehouse;
    private EMCLookupFormComponent lkpLinesToWarehouse;
    private emcJLabel lblLinesToBatch;
    private EMCLookupFormComponent lkpLinesToBatch;
    private emcJLabel lblLinesToSerial;
    private EMCLookupFormComponent lkpLinesToSerial;
    private EMCLookupFormComponent lkpLocation;
    private EMCLookupFormComponent lkpToLocation;
    private EMCLookupFormComponent lkpPallet;
    private EMCLookupFormComponent lkpToPallet;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Lines Stock Count Tab">
    private emcJLabel lblLinesCountedBy;
    private emcJTextField txtLinesCountedBy = new emcJTextField();
    private emcJLabel lblLinesCountedDate;
    private EMCDatePickerFormComponent linesCountedDate;
    private emcJLabel lblLinesConfirmedBy;
    private emcJTextField txtLinesConfirmedBy = new emcJTextField();
    private emcJLabel lblLinesConfirmedDate = new emcJLabel();
    private EMCDatePickerFormComponent linesConfirmedDate;
    // </editor-fold>
    private emcJTabbedPane masterTabs;
    private emcJTabbedPane linesTabs;
    private EMCUserData copyUD;
    private JournalsDRM masterDRM;
    private JournalLinesDRM linesDRM;
    private JournalLinesLookupRelationManager originalItemLookupRelationManager;
    private JournalLinesLookupRelationManager toItemLookupRelationManager;
    private EMCUserData userData;
    private EMCUserData linesUD;
    private EMCRegisterButton recordButton;
    private StockTakeButton stockTake;
    private StockCountButtonList btnStockCount;
    private List journalAccessDefs = new ArrayList();

    public JournalsForm(EMCUserData userData) {
        super("Journals", true, true, true, true, userData);
        //this.setHelpFile(new emcHelpFile("../Inventory/InventoryJournals.html"));
        this.setBounds(20, 20, 850, 700);

        this.userData = userData;
        EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.GET_ACCESS_DEFS);
        journalAccessDefs = EMCWSManager.executeGenericWS(cmd, journalAccessDefs, userData);
        journalAccessDefs = (List) journalAccessDefs.get(1);
        if (journalAccessDefs.size() == 0) {
            journalAccessDefs.add("NoneFoundSoThere");
            Logger.getLogger("emc").log(Level.WARNING, "You do not have access to any journal definitions.", userData);
        }
        EMCQuery query;
        if (userData.getUserData(0) instanceof EMCQuery) {
            query = (EMCQuery) userData.getUserData(0);
        } else {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class.getName());
            query.addAnd("journalStatus", JournalStatus.POSTED, EMCQueryConditions.NOT);
            query.addAnd("companyId", getUserData().getCompanyId());
        }
        query.addAndInList("journalDefinitionId", journalAccessDefs, true, false);
        query.addOrderBy("journalNumber");
        //explicitly set query to only show for this definition access
        userData.setUserData(0, query);
        //this.setHelpFile(new emcHelpFile("Inventory/InventoryItemRanges.html"));
        try {
            copyUD = userData.copyUserDataAndDataList();
            linesUD = userData.copyUserData();

            setupDRM(userData);

            createLabels();
            createDatePickers();
            initializeLookups();
            setupLookupRelations();
            setDocuments();
            setupDropdowns();
            setupUneditableFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
        masterDRM.doRelation(0);
    }

    private void setupDRM(EMCUserData userData) {
        masterDRM = new JournalsDRM(new emcGenericDataSourceUpdate(
                enumEMCModules.INVENTORY.getId(),
                new emc.entity.inventory.journals.datasource.InventoryJournalMasterDS(), copyUD), copyUD);
        this.setDataManager(masterDRM);

        StockDRMParameters param = new StockDRMParameters("itemId", "dimension1", "dimension2", "dimension3", "transId");
        linesDRM = new JournalLinesDRM(new emcGenericDataSourceUpdate(enumEMCModules.INVENTORY.getId(),
                new emc.entity.inventory.journals.datasource.InventoryJournalLinesDS(), linesUD), param, linesUD);

        masterDRM.setHeaderColumnID("journalNumber");
        masterDRM.setTheForm(this);
        masterDRM.setFormTextId1("journalNumber");
        masterDRM.setFormTextId2("journalDescription");
        masterDRM.setLinesTable(linesDRM);

        linesDRM.setTheForm(this);
        linesDRM.setFormTextId1("journalNumber");
        linesDRM.setFormTextId2("itemId");
        linesDRM.setHeaderTable(masterDRM);
        linesDRM.setRelationColumnToHeader("journalNumber");
    }

    private void createLabels() {
        lblJournalEnteredDate = new emcJLabel(masterDRM.getFieldEmcLabel("journalEnteredDate"));
        lblJournalApprovedDate = new emcJLabel(masterDRM.getFieldEmcLabel("journalApprovedDate"));
        lblJournalPostedDate = new emcJLabel(masterDRM.getFieldEmcLabel("journalPostedDate"));

        lblLinesTotalCost = new emcJLabel(linesDRM.getFieldEmcLabel("totalCost"));
        lblLinesInventTransId = new emcJLabel(linesDRM.getFieldEmcLabel("inventTransId"));

        lblLinesCountedBy = new emcJLabel(linesDRM.getFieldEmcLabel("countedBy"));
        lblLinesCountedDate = new emcJLabel(linesDRM.getFieldEmcLabel("countedDate"));
        lblLinesConfirmedBy = new emcJLabel(linesDRM.getFieldEmcLabel("confirmedBy"));
        lblLinesConfirmedDate = new emcJLabel(linesDRM.getFieldEmcLabel("confirmedDate"));

        lblLinesItem = new emcJLabel(linesDRM.getFieldEmcLabel("itemId"));
        lblLinesDescription = new emcJLabel(linesDRM.getFieldEmcLabel("description"));
        lblLinesUom = new emcJLabel(linesDRM.getFieldEmcLabel("uom"));
        lblLinesDimension1 = new emcJLabel(linesDRM.getFieldEmcLabel("dimension1"));
        lblLinesDimension2 = new emcJLabel(linesDRM.getFieldEmcLabel("dimension2"));
        lblLinesDimension3 = new emcJLabel(linesDRM.getFieldEmcLabel("dimension3"));
        lblLinesWarehouse = new emcJLabel(linesDRM.getFieldEmcLabel("warehouse"));
        lblLinesBatch = new emcJLabel(linesDRM.getFieldEmcLabel("batch"));
        lblLinesSerial = new emcJLabel(linesDRM.getFieldEmcLabel("serial"));

        lblLinesToItem = new emcJLabel(linesDRM.getFieldEmcLabel("toItemId"));
        lblLinesToDescription = new emcJLabel(linesDRM.getFieldEmcLabel("toDescription"));
        lblLinesToUom = new emcJLabel(linesDRM.getFieldEmcLabel("toUom"));
        lblLinesToDimension1 = new emcJLabel(linesDRM.getFieldEmcLabel("toDimension1"));
        lblLinesToDimension2 = new emcJLabel(linesDRM.getFieldEmcLabel("toDimension2"));
        lblLinesToDimension3 = new emcJLabel(linesDRM.getFieldEmcLabel("toDimension3"));
        lblLinesToWarehouse = new emcJLabel(linesDRM.getFieldEmcLabel("toWarehouse"));
        lblLinesToBatch = new emcJLabel(linesDRM.getFieldEmcLabel("toBatch"));
        lblLinesToSerial = new emcJLabel(linesDRM.getFieldEmcLabel("toSerial"));
    }

    private void initializeLookups() {
        List<String> contraAccountKeys = new ArrayList<String>();
        contraAccountKeys.add("accountNumber");
        contraAccountKeys.add("description");
        lkpJournalLineContraAccount = new EMCLookupFormComponent(new emc.menus.gl.menuitems.display.GLChartOfAccountsMenu(), linesDRM, "contraAccount", "accountNumber");
        lkpJournalContraAccount = new EMCLookupFormComponent(new emc.menus.gl.menuitems.display.GLChartOfAccountsMenu(), masterDRM, "journalContraAccount", "accountNumber");
        EMCLookupPopup contraAccountPopup = new EMCLookupPopup(enumEMCModules.GENERAL_LEDGER.getId(), new GLChartOfAccounts(), "accountNumber", contraAccountKeys, copyUD);
        lkpJournalContraAccount.setPopup(contraAccountPopup);
        lkpJournalLineContraAccount.setPopup(contraAccountPopup);
        linesDRM.setDocument(lkpJournalLineContraAccount.getDocument());
        masterDRM.setDocument(lkpJournalContraAccount.getDocument());

        List<String> definitionKeys = new ArrayList<String>();
        definitionKeys.add("journalDefinitionId");
        definitionKeys.add("journalDescription");

        lkpJournalDefinitionId = new EMCLookupJTableComponent(new JournalDefinitions());
        EMCLookupPopup journalDefinitionPopup = new EMCLookupPopup(new BaseJournalDefinitionTable(),
                "journalDefinitionId", definitionKeys, copyUD);
        lkpJournalDefinitionId.setPopup(journalDefinitionPopup);
        EMCQuery jlookupQ = new EMCQuery(enumQueryTypes.SELECT, BaseJournalDefinitionTable.class);
        jlookupQ.addAnd("companyId", userData.getCompanyId());
        jlookupQ.addAndInList("journalDefinitionId", journalAccessDefs, true, false);
        jlookupQ.addAnd("journalModule", Modules.INVENTORY.toString());
        lkpJournalDefinitionId.setTheQuery(jlookupQ);
        journalDefinitionIdEditor = new EMCLookupTableCellEditor(lkpJournalDefinitionId);

        //Warehouse
        List<String> warehouseKeys = new ArrayList<String>();
        warehouseKeys.add("warehouseId");
        warehouseKeys.add("description");

        EMCMenuItem warehouseItem = new emc.menus.inventory.menuitems.display.Warehouse();

        lkpLinesWarehouse = new EMCLookupFormComponent(warehouseItem, linesDRM, "warehouse");
        lkpLinesToWarehouse = new EMCLookupFormComponent(warehouseItem, linesDRM, "toWarehouse");

        EMCLookupPopup warehousePopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryWarehouse(),
                "warehouseId", warehouseKeys, copyUD);

        lkpLinesWarehouse.setPopup(warehousePopup);
        lkpLinesToWarehouse.setPopup(warehousePopup);

        linesDRM.setDocument(lkpLinesWarehouse.getDocument());
        linesDRM.setDocument(lkpLinesToWarehouse.getDocument());

        lkpLinesTableWarehouse = new EMCLookupJTableComponent(warehouseItem);
        lkpLinesTableWarehouse.setPopup(warehousePopup);
        edtLinesWarehouse = new EMCLookupTableCellEditor(lkpLinesTableWarehouse);
        //Batch
        List<String> batchKeys = new ArrayList<String>();
        batchKeys.add("batch");

        EMCMenuItem batchItem = new emc.menus.inventory.menuitems.display.Transactions();

        lkpLinesBatch = new EMCLookupFormComponent(batchItem, linesDRM, "batch");
        lkpLinesToBatch = new EMCLookupFormComponent(batchItem, linesDRM, "toBatch");

        EMCLookupPopup batchPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.transactions.InventorySummary(),
                "batch", batchKeys, copyUD);

        EMCLookupPopup batchToPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.transactions.InventorySummary(),
                "batch", batchKeys, copyUD);

        lkpLinesBatch.setPopup(batchPopup);
        lkpLinesToBatch.setPopup(batchToPopup);

        EMCQuery batchQuery = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
        batchQuery.addAnd("batch", null, EMCQueryConditions.NOT);
        batchQuery.addTableAnd(InventoryDimensionTable.class.getName(), "itemDimId", InventorySummary.class.getName(), "recordID");
        batchQuery.addAnd("companyId", userData.getCompanyId());

        lkpLinesBatch.setTheQuery(batchQuery.copyQuery());
        lkpLinesToBatch.setTheQuery(batchQuery.copyQuery());

        linesDRM.setDocument(lkpLinesBatch.getDocument());
        linesDRM.setDocument(lkpLinesToBatch.getDocument());

        //Serial
        List<String> serialKeys = new ArrayList<String>();
        serialKeys.add("serialNo");

        EMCLookupPopup serialPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.transactions.InventorySummary(),
                "serialNo", serialKeys, copyUD);

        EMCLookupPopup serialToPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.transactions.InventorySummary(),
                "serialNo", serialKeys, copyUD);

        EMCMenuItem serialItem = new emc.menus.inventory.menuitems.display.Transactions();

        lkpLinesSerial = new EMCLookupFormComponent(serialItem, linesDRM, "serial");
        lkpLinesToSerial = new EMCLookupFormComponent(serialItem, linesDRM, "toSerial");

        lkpLinesSerial.setPopup(serialPopup);
        lkpLinesToSerial.setPopup(serialToPopup);

        EMCQuery serialQuery = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
        serialQuery.addAnd("serialNo", null, EMCQueryConditions.NOT);
        serialQuery.addTableAnd(InventoryDimensionTable.class.getName(), "itemDimId", InventorySummary.class.getName(), "recordID");
        serialQuery.addAnd("companyId", userData.getCompanyId());

        lkpLinesSerial.setTheQuery(serialQuery.copyQuery());
        lkpLinesToSerial.setTheQuery(serialQuery.copyQuery());

        linesDRM.setDocument(lkpLinesSerial.getDocument());
        linesDRM.setDocument(lkpLinesToSerial.getDocument());

        //Item lookup
        lkpLinesItem = EMCItemLookupFactory.createItemFormLookup(copyUD, linesDRM, "itemPrimaryReference");
        linesDRM.setDocument(lkpLinesItem.getDocument());

        lkpLinesToItem = EMCItemLookupFactory.createItemFormLookup(copyUD, linesDRM, "itemToPrimaryReference");
        linesDRM.setDocument(lkpLinesToItem.getDocument());

        //Dimension lookups
        List<String> dimensionKeys = new ArrayList<String>();
        dimensionKeys.add("dimensionId");
        dimensionKeys.add("description");

        EMCMenuItem dimension1Item = new emc.menus.inventory.menuitems.display.Dimension1();

        EMCLookupPopup dimension1Popup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.datasource.InventoryItemDimension1SetupDS(),
                "dimensionId", dimensionKeys, copyUD);
        dimension1Popup.setPreferredWidth(500);

        lkpLinesTableDimension1 = new EMCLookupJTableComponent(dimension1Item);
        lkpLinesTableDimension1.setPopup(dimension1Popup);
        edtLinesDimension1 = new EMCLookupTableCellEditor(lkpLinesTableDimension1);

        lkpLinesDimension1 = new EMCLookupFormComponent(dimension1Item, linesDRM, "dimension1");
        lkpLinesDimension1.setPopup(dimension1Popup);
        linesDRM.setDocument(lkpLinesDimension1.getDocument());

        EMCLookupPopup toDimension1Popup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.datasource.InventoryItemDimension1SetupDS(),
                "dimensionId", dimensionKeys, copyUD);
        toDimension1Popup.setPreferredWidth(500);

        lkpLinesToDimension1 = new EMCLookupFormComponent(dimension1Item, linesDRM, "toDimension1");
        lkpLinesToDimension1.setPopup(toDimension1Popup);
        linesDRM.setDocument(lkpLinesToDimension1.getDocument());

        EMCMenuItem dimension2Item = new emc.menus.inventory.menuitems.display.Dimension2();

        EMCLookupPopup dimension2Popup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.datasource.InventoryItemDimension2SetupDS(),
                "dimensionId", dimensionKeys, copyUD);

        lkpLinesTableDimension2 = new EMCLookupJTableComponent(dimension2Item);
        lkpLinesTableDimension2.setPopup(dimension2Popup);
        edtLinesDimension2 = new EMCLookupTableCellEditor(lkpLinesTableDimension2);

        lkpLinesDimension2 = new EMCLookupFormComponent(dimension2Item, linesDRM, "dimension2");
        lkpLinesDimension2.setPopup(dimension2Popup);
        linesDRM.setDocument(lkpLinesDimension2.getDocument());

        EMCLookupPopup toDimension2Popup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.datasource.InventoryItemDimension2SetupDS(),
                "dimensionId", dimensionKeys, copyUD);

        lkpLinesToDimension2 = new EMCLookupFormComponent(dimension2Item, linesDRM, "toDimension2");
        lkpLinesToDimension2.setPopup(toDimension2Popup);
        linesDRM.setDocument(lkpLinesToDimension2.getDocument());

        EMCMenuItem dimension3Item = new emc.menus.inventory.menuitems.display.Dimension3();

        EMCLookupPopup dimension3Popup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.datasource.InventoryItemDimension3SetupDS(),
                "dimensionId", dimensionKeys, copyUD);

        lkpLinesTableDimension3 = new EMCLookupJTableComponent(dimension3Item);
        lkpLinesTableDimension3.setPopup(dimension3Popup);
        edtLinesDimension3 = new EMCLookupTableCellEditor(lkpLinesTableDimension3);

        lkpLinesDimension3 = new EMCLookupFormComponent(dimension3Item, linesDRM, "dimension3");
        lkpLinesDimension3.setPopup(dimension3Popup);
        linesDRM.setDocument(lkpLinesDimension3.getDocument());

        EMCLookupPopup toDimension3Popup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.datasource.InventoryItemDimension3SetupDS(),
                "dimensionId", dimensionKeys, copyUD);

        lkpLinesToDimension3 = new EMCLookupFormComponent(dimension3Item, linesDRM, "toDimension3");
        lkpLinesToDimension3.setPopup(toDimension3Popup);
        linesDRM.setDocument(lkpLinesToDimension3.getDocument());

        EMCQuery dimension1Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension1Setup.class.getName());
        dimension1Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemDimension1Setup.class.getName(), "itemId");
        dimension1Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryItemDimension1Setup.class.getName(), "dimension1Id");
        dimension1Query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
        dimension1Query.addGroupBy("dimensionId");

        EMCQuery dimension2Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension2Setup.class.getName());
        dimension2Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemDimension2Setup.class.getName(), "itemId");
        dimension2Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryItemDimension2Setup.class.getName(), "dimension2Id");
        dimension2Query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
        dimension2Query.addGroupBy("dimensionId");

        EMCQuery dimension3Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension3Setup.class.getName());
        dimension3Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemDimension3Setup.class.getName(), "itemId");
        dimension3Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryItemDimension3Setup.class.getName(), "dimension3Id");
        dimension3Query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
        dimension3Query.addGroupBy("dimensionId");

        lkpLinesTableDimension1.setTheQuery(dimension1Query);
        lkpLinesTableDimension2.setTheQuery(dimension2Query);
        lkpLinesTableDimension3.setTheQuery(dimension3Query);
        lkpLinesDimension1.setTheQuery(dimension1Query);
        lkpLinesDimension2.setTheQuery(dimension2Query);
        lkpLinesDimension3.setTheQuery(dimension3Query);

        EMCQuery toDimension1Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension1Setup.class.getName());
        toDimension1Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemDimension1Setup.class.getName(), "itemId");
        toDimension1Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryItemDimension1Setup.class.getName(), "dimension1Id");
        toDimension1Query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
        toDimension1Query.addGroupBy("dimensionId");

        EMCQuery toDimension2Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension2Setup.class.getName());
        toDimension2Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemDimension2Setup.class.getName(), "itemId");
        toDimension2Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryItemDimension2Setup.class.getName(), "dimension2Id");
        toDimension2Query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
        toDimension2Query.addGroupBy("dimensionId");

        EMCQuery toDimension3Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension3Setup.class.getName());
        toDimension3Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemDimension3Setup.class.getName(), "itemId");
        toDimension3Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryItemDimension3Setup.class.getName(), "dimension3Id");
        toDimension3Query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
        toDimension3Query.addGroupBy("dimensionId");

        lkpLinesToDimension1.setTheQuery(toDimension1Query);
        lkpLinesToDimension2.setTheQuery(toDimension2Query);
        lkpLinesToDimension3.setTheQuery(toDimension3Query);

        EMCQuery locationQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class.getName());
        locationQuery.addAnd("warehouseId", "");
        EMCLookupPopup locationPop = new EMCLookupPopup(new InventoryLocation(), "locationId", userData);
        lkpLocation = new EMCLookupFormComponent(new LocationMenu(), linesDRM, "location");
        lkpLocation.setPopup(locationPop);
        lkpLocation.setTheQuery(locationQuery);

        EMCQuery toLocationQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class.getName());
        toLocationQuery.addAnd("warehouseId", "");
        EMCLookupPopup toLocationPop = new EMCLookupPopup(new InventoryLocation(), "locationId", userData);
        lkpToLocation = new EMCLookupFormComponent(new LocationMenu(), linesDRM, "toLocation");
        lkpToLocation.setPopup(toLocationPop);
        lkpToLocation.setTheQuery(toLocationQuery);

        EMCQuery palletQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
        toLocationQuery.addAnd("warehouseId", "");
        EMCLookupPopup palletPop = new EMCLookupPopup(new InventorySummary(), "pallet", userData);
        lkpPallet = new EMCLookupFormComponent(null, linesDRM, "pallet");
        lkpPallet.setTheQuery(palletQuery);
        lkpPallet.setPopup(palletPop);

        EMCQuery palletToQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
        toLocationQuery.addAnd("warehouseId", "");
        EMCLookupPopup palletToPop = new EMCLookupPopup(new InventorySummary(), "pallet", userData);
        lkpToPallet = new EMCLookupFormComponent(null, linesDRM, "pallet");
        lkpToPallet.setTheQuery(palletToQuery);
        lkpToPallet.setPopup(palletToPop);
    }

    private void setupLookupRelations() {
        originalItemLookupRelationManager = new JournalLinesLookupRelationManager("itemId", "dimension1", "dimension2", "dimension3", "warehouse", "location");

        linesDRM.addLookupRelationManager(originalItemLookupRelationManager);

        originalItemLookupRelationManager.addLookup(lkpLinesItem, "item");
        originalItemLookupRelationManager.addLookup(lkpLinesDimension1, "dimension1");
        originalItemLookupRelationManager.addLookup(lkpLinesDimension2, "dimension2");
        originalItemLookupRelationManager.addLookup(lkpLinesDimension3, "dimension3");
        originalItemLookupRelationManager.addLookup(lkpLinesBatch, "batch");
        originalItemLookupRelationManager.addLookup(lkpLinesSerial, "serial");
        originalItemLookupRelationManager.addLookup(lkpLinesWarehouse, "warehouse");
        originalItemLookupRelationManager.addLookup(lkpLocation, "location");

        originalItemLookupRelationManager.initializeLookups();

        originalItemLookupRelationManager.addLookup(lkpLinesTableDimension1, "tableDimension1");
        originalItemLookupRelationManager.addLookup(lkpLinesTableDimension2, "tableDimension2");
        originalItemLookupRelationManager.addLookup(lkpLinesTableDimension3, "tableDimension3");

        //To item
        toItemLookupRelationManager = new JournalLinesLookupRelationManager("toItemId", "toDimension1", "toDimension2", "toDimension3", "toWarehouse", "toLocation");

        linesDRM.addLookupRelationManager(toItemLookupRelationManager);

        toItemLookupRelationManager.addLookup(lkpLinesToItem, "item");
        toItemLookupRelationManager.addLookup(lkpLinesToDimension1, "dimension1");
        toItemLookupRelationManager.addLookup(lkpLinesToDimension2, "dimension2");
        toItemLookupRelationManager.addLookup(lkpLinesToDimension3, "dimension3");
        toItemLookupRelationManager.addLookup(lkpLinesToBatch, "batch");
        toItemLookupRelationManager.addLookup(lkpLinesToSerial, "serial");
        toItemLookupRelationManager.addLookup(lkpLinesToWarehouse, "warehouse");
        toItemLookupRelationManager.addLookup(lkpToLocation, "location");

        toItemLookupRelationManager.initializeLookups();
    }

    private void setupDropdowns() {
        cmbContraType = new ContraTypesFormDropdown(masterDRM, "journalContraType");
        cmbLinesJournalContraType = new ContraTypesFormDropdown(linesDRM, "contraType");
    }

    private void createDatePickers() {
        linesCountedDate = new EMCDatePickerFormComponent(linesDRM, "countedDate");
        linesConfirmedDate = new EMCDatePickerFormComponent(linesDRM, "confirmedDate");

        journalEnteredDate = new EMCDatePickerFormComponent(masterDRM, "createdDate");
        journalApprovedDate = new EMCDatePickerFormComponent(masterDRM, "journalApprovedDate");
        journalPostedDate = new EMCDatePickerFormComponent(masterDRM, "journalPostedDate");
    }

    private void setupUneditableFields() {
        txtJournalApprovedBy.setEditable(false);
        txtJournalEnteredBy.setEditable(false);
        txtJournalPostedBy.setEditable(false);
        journalApprovedDate.setEnabled(false);
        journalEnteredDate.setEnabled(false);
        journalPostedDate.setEnabled(false);

        txtJournalPosting.setEditable(false);

        txtLinesTotalCost.setEditable(false);
        txtLinesInventTransId.setEditable(false);

        txtLinesCountedBy.setEditable(false);
        linesCountedDate.setEnabled(false);
        txtLinesConfirmedBy.setEditable(false);
        linesConfirmedDate.setEnabled(false);

        txtLinesDescription.setEditable(false);
        txtLinesUom.setEditable(false);
        txtLinesToDescription.setEditable(false);
        txtLinesToUom.setEditable(false);
    }

    private void setDocuments() {
        txaJournalText.setDocument(new EMCStringFormDocument(masterDRM, "journalText"));
        txtJournalPosting.setDocument(new EMCStringFormDocument(masterDRM, "journalPosting"));
        txtJournalEnteredBy.setDocument(new EMCStringFormDocument(masterDRM, "createdBy"));
        txtJournalApprovedBy.setDocument(new EMCStringFormDocument(masterDRM, "journalApprovedBy"));
        txtJournalPostedBy.setDocument(new EMCStringFormDocument(masterDRM, "journalPostedBy"));

        txtLinesExtRef.setDocument(new EMCStringFormDocument(linesDRM, "journalLineExtRef"));

        txtLinesCountQOH.setDocument(new EMCDoubleFormDocument(linesDRM, "countQOH"));
        txtLinesCountQOH.setEditable(false);
        txtLinesCountedQuantity.setDocument(new EMCDoubleFormDocument(linesDRM, "countedQuantity"));
        txtLinesCountedQuantity.setEditable(false);
        txtLinesConfirmedQuantity.setDocument(new EMCDoubleFormDocument(linesDRM, "confirmedQuantity"));
        txtLinesConfirmedQuantity.setEditable(false);
        txaLinesJournalText.setDocument(new EMCStringFormDocument(linesDRM, "lineText"));

        txtLinesTotalCost.setDocument(new EMCDoubleFormDocument(linesDRM, "totalCost"));
        txtLinesInventTransId.setDocument(new EMCStringFormDocument(linesDRM, "transId"));

        txtLinesCountedBy.setDocument(new EMCStringFormDocument(linesDRM, "countedBy"));
        txtLinesConfirmedBy.setDocument(new EMCStringFormDocument(linesDRM, "confirmedBy"));

        txtLinesDescription.setDocument(new EMCStringFormDocument(linesDRM, "description"));
        txtLinesUom.setDocument(new EMCStringFormDocument(linesDRM, "uom"));

        txtLinesToDescription.setDocument(new EMCStringFormDocument(linesDRM, "toDescription"));
        txtLinesToUom.setDocument(new EMCStringFormDocument(linesDRM, "toUom"));
    }

    private void initFrame() {
        masterTabs = setupMaster();
        linesTabs = setupLines();
        masterTabs.setRelationManager(masterDRM);
        linesTabs.setRelationManager(linesDRM);
        this.setLayout(new GridLayout(1, 1));

        emcJPanel pnlMaster = new emcJPanel();
        pnlMaster.setLayout(new BorderLayout());
        emcJPanel pnlButtons = masterButtons();

        pnlMaster.add(masterTabs, BorderLayout.CENTER);
        pnlMaster.add(pnlButtons, BorderLayout.EAST);

        this.add(pnlMaster);

        emcJPanel pnlLines = new emcJPanel(new BorderLayout());
        pnlLines.add(linesTabs, BorderLayout.CENTER);
        pnlLines.add(pnlLineButtons(), BorderLayout.EAST);
        
        this.add(pnlLines);

        emcJSplitPane topBottomSplit;
        topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, pnlMaster, pnlLines);
        topBottomSplit.setDividerSize(8);
        topBottomSplit.setContinuousLayout(true);
        topBottomSplit.setOneTouchExpandable(false);
        topBottomSplit.setDividerLocation(300);

        this.add(topBottomSplit);
    }

    private emcJTabbedPane setupMaster() {
        emcJTabbedPane masterTabs = new emcJTabbedPane();

        masterTabs.add("Overview", masterOverviewTab());
        masterTabs.add("Detail", masterDetailTab());
        masterTabs.add("Accounting", masterAccountingTab());

        return masterTabs;
    }

    private emcJPanel masterOverviewTab() {
        emcJPanel pnlOverview = new emcJPanel();
        pnlOverview.setLayout(new GridLayout(1, 1));


        List masterKeys = new ArrayList();
        masterKeys.add("journalDefinitionId");
        masterKeys.add("journalNumber");
        masterKeys.add("journalDescription");
        masterKeys.add("journalStatus");
        masterKeys.add("createdDate");
        masterKeys.add("createdBy");

        emcTableModelUpdate m = new emcTableModelUpdate(masterDRM, masterKeys) {

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                if (columnIndex >= 3) {
                    return false;
                }
                return super.isCellEditable(rowIndex, columnIndex);
            }
        };

        tblMaster = new emcJTableUpdate(m);
        masterDRM.setMainTableComponent(tblMaster);

        tblMaster.setLookupCellEditor(0, journalDefinitionIdEditor);

        emcTablePanelUpdate linesScroll = new emcTablePanelUpdate(tblMaster);
        masterDRM.setTablePanel(linesScroll);
        pnlOverview.add(linesScroll);

        return pnlOverview;
    }

    private emcJPanel masterDetailTab() {
        emcJPanel pnlDetail = new emcJPanel();
        pnlDetail.setLayout(new GridBagLayout());
        pnlDetail.setBorder(BorderFactory.createTitledBorder("Detail"));

        emcJPanel pnlJournalText = new emcJPanel();
        pnlJournalText.setBorder(BorderFactory.createTitledBorder("Journal Text"));
        pnlJournalText.setLayout(new GridLayout(1, 1));

        emcJScrollPane journalTextPane = new emcJScrollPane(txaJournalText);
        journalTextPane.setPreferredSize(new Dimension(70, 70));
        pnlJournalText.add(journalTextPane);
        int y = 0;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1.5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;

        pnlDetail.add(pnlJournalText, gbc);

        y++;

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;

        pnlDetail.add(emcSetGridBagConstraints.createSimplePanel(new Component[][]{{lblJournalPosting, txtJournalPosting}}, GridBagConstraints.BOTH, false), gbc);

        y++;

        gbc = emcSetGridBagConstraints.endPanel(y);

        pnlDetail.add(new emcJLabel(), gbc);

        return pnlDetail;
    }

    private emcJPanel masterAccountingTab() {
        Component[][] components = {
            {lblJournalContractType, cmbContraType},
            {lblJournalContractAccount, lkpJournalContraAccount},
            {lblJournalEnteredBy, txtJournalEnteredBy},
            {lblJournalEnteredDate, journalEnteredDate},
            {lblJournalApprovedBy, txtJournalApprovedBy},
            {lblJournalApprovedDate, journalApprovedDate},
            {lblJournalPostedBy, txtJournalPostedBy},
            {lblJournalPostedDate, journalPostedDate},};

        emcJPanel pnlAccounting = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        pnlAccounting.setBorder(BorderFactory.createTitledBorder("Accounting"));

        return pnlAccounting;
    }

    private emcJPanel masterButtons() {
        emcJPanel pnlButtons = new emcJPanel();
        pnlButtons.setLayout(new GridBagLayout());
        pnlButtons.setPreferredSize(new Dimension(120, 100));
        int y = 0;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc = emcSetGridBagConstraints.setGrid(gbc, 0, y);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        ValidateButton btnValidate = new ValidateButton(this.masterDRM);
        pnlButtons.add(btnValidate, gbc);
        y++;

        gbc = emcSetGridBagConstraints.setGrid(gbc, 0, y);
        emcMenuButtonList btnApprove = new ApprovalButton(this);
        pnlButtons.add(btnApprove, gbc);
        y++;

        gbc = emcSetGridBagConstraints.setGrid(gbc, 0, y);
        PostButton btnPost = new PostButton(this.masterDRM);

        pnlButtons.add(btnPost, gbc);

        y++;

        gbc = emcSetGridBagConstraints.setGrid(gbc, 0, y);
        btnStockCount = new StockCountButtonList(this);
        pnlButtons.add(btnStockCount, gbc);

        y++;

        gbc = emcSetGridBagConstraints.setGrid(gbc, 0, y);
        pnlButtons.add(new PrintJournalButton(masterDRM), gbc);
        y++;


        gbc = emcSetGridBagConstraints.setGrid(gbc, 0, y);
        pnlButtons.add(new emcJButton("Re-Date Lines") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                String journalNumber = (String) masterDRM.getLastFieldValueAt("journalNumber");
                if (!Functions.checkBlank(journalNumber) && !JournalStatus.POSTED.toString().equals(masterDRM.getLastFieldValueAt("journalStatus"))) {
                    new MassLineDateUpdateForm(linesDRM, journalNumber, userData);
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Journal has not been saved or has already been posted", userData);
                }
            }
        }, gbc);
        y++;

        gbc = emcSetGridBagConstraints.setGrid(gbc, 0, y);
        //Set up queries for Query Switch Button
        EMCQuerySwitchButton querySwitchButton = new EMCQuerySwitchButton("Active Journals", masterDRM);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class.getName());
        query.addAnd("journalStatus", JournalStatus.POSTED, EMCQueryConditions.NOT);
        query.addAnd("companyId", getUserData().getCompanyId());
        query.addAndInList("journalDefinitionId", journalAccessDefs, true, false);
        query.addOrderBy("journalNumber");


        querySwitchButton.addQuery("Active Journals", query);

        query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class.getName());
        query.addAnd("companyId", getUserData().getCompanyId());
        query.addAndInList("journalDefinitionId", journalAccessDefs, true, false);
        query.addOrderBy("journalNumber");


        querySwitchButton.addQuery("All Journals", query);

        pnlButtons.add(querySwitchButton, gbc);

        y++;

        gbc = emcSetGridBagConstraints.endPanel(y);

        pnlButtons.add(new emcJLabel(), gbc);

        return pnlButtons;
    }

    private emcJTabbedPane setupLines() {
        emcJTabbedPane linesTabs = new emcJTabbedPane();

        linesTabs.add("Overview", linesOverviewTab());
        linesTabs.add("General", linesGeneralTab());
        linesTabs.add("Dimensions/Transfer", linesDimensionTransferTab());
        linesTabs.add("Stock Count", linesStockCountTab());

        return linesTabs;
    }

    private emcJPanel linesOverviewTab() {
        emcJPanel pnlOverview = new emcJPanel();
        pnlOverview.setLayout(new BorderLayout());

        List linesKeys = new ArrayList();
        linesKeys.add("lineDate");
        linesKeys.add("itemPrimaryReference");
        linesKeys.add("description");
        linesKeys.add("dimension1");
        linesKeys.add("dimension3");
        linesKeys.add("dimension2");
        linesKeys.add("warehouse");
        linesKeys.add("quantity");
        linesKeys.add("uom");
        linesKeys.add("cost");
        linesKeys.add("totalCost");

        emcTableModelUpdate m = new emcTableModelUpdate(linesDRM, linesKeys);
        tblLines = new emcJTableUpdate(m) {

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                if (columnIndex == 2 || columnIndex == 8 || columnIndex == 10) {
                    return false;
                } else {
                    return super.isCellEditable(rowIndex, columnIndex);
                }
            }
        };
        linesDRM.setMainTableComponent(tblLines);
        emcTablePanelUpdate linesScroll = new emcTablePanelUpdate(tblLines);

        tblLines.setLookupCellEditor(1, EMCItemLookupFactory.createItemLookupEditor(copyUD));
        tblLines.setLookupCellEditor(3, edtLinesDimension1);
        tblLines.setLookupCellEditor(4, edtLinesDimension3);
        tblLines.setLookupCellEditor(5, edtLinesDimension2);
        tblLines.setLookupCellEditor(6, edtLinesWarehouse);

        linesDRM.setTablePanel(linesScroll);

        pnlOverview.add(linesScroll, BorderLayout.CENTER);

        return pnlOverview;
    }

    private emcJPanel linesGeneralTab() {
        emcJPanel pnlAdditional = new emcJPanel();
        pnlAdditional.setBorder(BorderFactory.createTitledBorder("General"));

        pnlAdditional.setLayout(new GridLayout(1, 2));

        Component[][] components = {
            {lblLinesExtRef, txtLinesExtRef},
            {lblLinesJournalContraType, cmbLinesJournalContraType},
            {lblLinesJournalContraAccount, lkpJournalLineContraAccount},
            {lblLinesTotalCost, txtLinesTotalCost},
            {lblLinesInventTransId, txtLinesInventTransId}};

        emcJPanel pnlLeft = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.HORIZONTAL, true);
        pnlLeft.setBorder(BorderFactory.createTitledBorder("Misc"));

        emcJPanel pnlRight = new emcJPanel();
        pnlRight.setBorder(BorderFactory.createTitledBorder("Journal Text"));
        pnlRight.setLayout(new GridLayout(1, 1));

        emcJScrollPane journalScroll = new emcJScrollPane(txaLinesJournalText);
        pnlRight.add(journalScroll);

        pnlAdditional.add(pnlLeft);
        pnlAdditional.add(pnlRight);

        return pnlAdditional;
    }

    private emcJPanel linesDimensionTransferTab() {
        Component[][] components = {
            {lblLinesItem, lkpLinesItem},
            {lblLinesDescription, txtLinesDescription},
            {lblLinesUom, txtLinesUom},
            {lblLinesDimension1, lkpLinesDimension1},
            {lblLinesDimension3, lkpLinesDimension3},
            {lblLinesDimension2, lkpLinesDimension2},
            {lblLinesBatch, lkpLinesBatch},
            {lblLinesSerial, lkpLinesSerial},
            {lblLinesWarehouse, lkpLinesWarehouse},
            {new emcJLabel("Location"), lkpLocation},
            {new emcJLabel("Pallet"), lkpPallet}};

        emcJPanel pnlFrom = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        pnlFrom.setBorder(BorderFactory.createTitledBorder("From"));

        components = new Component[][]{
                    {lblLinesToItem, lkpLinesToItem},
                    {lblLinesToDescription, txtLinesToDescription},
                    {lblLinesToUom, txtLinesToUom},
                    {lblLinesToDimension1, lkpLinesToDimension1},
                    {lblLinesToDimension3, lkpLinesToDimension3},
                    {lblLinesToDimension2, lkpLinesToDimension2},
                    {lblLinesToBatch, lkpLinesToBatch},
                    {lblLinesToSerial, lkpLinesToSerial},
                    {lblLinesToWarehouse, lkpLinesToWarehouse},
                    {new emcJLabel("To Location"), lkpToLocation},
                    {new emcJLabel("To Pallet"), lkpToPallet}};

        emcJPanel pnlTo = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        pnlTo.setBorder(BorderFactory.createTitledBorder("To"));

        emcJPanel pnlDimensionTransfer = new emcJPanel();
        pnlDimensionTransfer.setLayout(new GridLayout(1, 2));
        pnlDimensionTransfer.setBorder(BorderFactory.createTitledBorder("Dimension/Transfer"));
        pnlDimensionTransfer.add(pnlFrom);
        pnlDimensionTransfer.add(pnlTo);

        return pnlDimensionTransfer;
    }

    private emcJPanel linesStockCountTab() {
        Component[][] components = {
            {lblLinesCountQOH, txtLinesCountQOH, new emcJLabel(), new emcJLabel()},
            {lblLinesCountedQuantity, txtLinesCountedQuantity},
            {lblLinesCountedBy, txtLinesCountedBy},
            {lblLinesCountedDate, linesCountedDate},
            {lblLinesConfirmedQuantity, txtLinesConfirmedQuantity},
            {lblLinesConfirmedBy, txtLinesConfirmedBy},
            {lblLinesConfirmedDate, linesConfirmedDate}};

        emcJPanel pnlStockCount = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        pnlStockCount.setBorder(BorderFactory.createTitledBorder("Stock Count"));

        return pnlStockCount;
    }

    public void enableRegisterButton(boolean enable) {
        if (recordButton != null) {
            recordButton.setEnabled(enable);
        }
    }

    public void enableStockTakeButton(boolean enable) {
        if (stockTake != null) {
            stockTake.setEnabled(enable);
        }
    }

    public void enableStockCountButton(boolean enable) {
        if (btnStockCount != null) {
            btnStockCount.setItemEnabled("Recount Sheet", enable);
            btnStockCount.setItemEnabled("Count Sheet", enable);
            btnStockCount.setItemEnabled("Variance", enable);

        }
    }

    public void enableStockCountButtonPosted() {
        if (btnStockCount != null) {
            btnStockCount.setItemEnabled("Recount Sheet", false);
            btnStockCount.setItemEnabled("Count Sheet", false);
            btnStockCount.setItemEnabled("Variance", true);

        }
    }

    /**
     * Creates the panel for buttons on the lines
     * @return the newly created panel
     */
    private emcJPanel pnlLineButtons() {
        emcJPanel pane = new emcJPanel();
        pane.setLayout(new GridBagLayout());
        pane.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        GridBagConstraints gbc;
        int y = 0;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        InventSerialBatchMenu sbMenu = new InventSerialBatchMenu();
        sbMenu.setDoNotOpenForm(false);

        recordButton = new EMCRegisterButton(RegisterFormTypeEnum.MUST_EXIST, RegisterFromTypeEnum.JRN, linesDRM, "journalNumber", "transId",
                "itemId", "description", "dimension1", "dimension2", "dimension3", "warehouse", "quantity", "itemPrimaryReference") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                JournalsForm.this.doSaveOnButtonPress();
                if (linesDRM.getLastUpdateStatus()) {
                    Object itemId = linesDRM.getFieldValueAt(linesDRM.getLastRowAccessed(), "itemId");
                    JournalStatus status = JournalStatus.fromString(linesDRM.getHeaderTable().getFieldValueAt(linesDRM.getHeaderTable().getLastRowAccessed(), "journalStatus").toString());
                    if (!Functions.checkBlank(itemId)) {
                        super.setTypeAccordingToQuantity(InventoryJournalTypes.STOCKTAKE.toString().equals((String) masterDRM.getLastFieldValueAt("journalType")));
                        super.doActionPerformed(evt);
                    } else {
                        if (Functions.checkBlank(itemId)) {
                            Logger.getLogger("emc").log(Level.INFO, "No Item selected.", copyUD);
                        }
                    }
                }
            }
        };
        pane.add(recordButton, gbc);
        stockTake = new StockTakeButton(this);
        y++;
        gbc.gridy = y;
        pane.add(stockTake, gbc);
        y++;
        gbc.gridy = y;
        pane.add(new emcStockButton(this, true), gbc);
        y++;
        gbc.gridy = y;
        pane.add(new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (InventoryJournalTypes.STOCKTAKE.toString().equals(masterDRM.getLastFieldValueAt("journalType"))) {
                    Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to remove line from a stock take journal here.", userData);
                    return;
                }

                EMCCommandClass cmd = new EMCCommandClass(ServerInventoryMethods.DELETE_INVENTORYJOURNALLINESDS);

                List toSend = new ArrayList();
                toSend.add(linesDRM.getRowCache(linesDRM.getLastRowAccessed()));

                toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                if (EMCCommands.SERVER_EXCEPTION_OCCURRED.equals(toSend.get(0))) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to cancel journal line.", userData);
                } else {
                    Logger.getLogger("emc").log(Level.INFO, "Journal line canceled.", userData);
                    linesDRM.refreshData();
                }
            }
        }, gbc);
        y++;
        pane.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));



        pane.setPreferredSize(new Dimension(120, 25));
        return pane;
    }

    /** This method is used to set the enabled state of a tab. */
    protected void setLinesTabEnabled(int index, boolean enabled) {
        if (linesTabs != null) {
            linesTabs.setEnabledAt(index, enabled);
        }
    }
}
 


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.posting;


import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.pop.posting.QuantitySelectionDropdown;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.datatypes.EMCDouble;
import emc.datatypes.pop.purchasepostmaster.DocumentNumFKNM;
import emc.entity.base.reporttools.BaseReportPrintOptions;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.pop.journals.POPSupplierReceivedJournalMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.enums.pop.posting.DocumentTypes;
import emc.enums.pop.posting.PostingQuantities;
import emc.enums.pop.purchaseorders.PurchaseOrderStatus;
import emc.forms.pop.display.posting.resources.CancelButton;
import emc.forms.pop.display.posting.resources.PostButton;
import emc.forms.pop.display.purchaseorders.PurchaseOrderMasterDataRelationManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.base.menuItems.display.ReportPrintOptionMenu;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.inventory.menuitems.display.ItemMaster;
import emc.menus.inventory.menuitems.display.LocationMenu;
import emc.menus.pop.menuitems.display.PostSerialBatchMenu;
import emc.menus.pop.menuitems.display.PurchaseOrders;
import emc.menus.pop.menuitems.display.SupplierReceivedJournals;
import emc.menus.pop.menuitems.display.Suppliers;
import emc.methods.developertools.ServerDeveloperToolMethods;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JSplitPane;

/**
 *
 * @author riaan
 */
public class PostingForm extends BaseInternalFrame {

    // <editor-fold defaultstate="collapsed" desc="Master Overview Tab">
    private emcJTableUpdate tblMaster;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Setup Tab">
    private emcJLabel lblPost = new emcJLabel("Post");
    private emcYesNoComponent ysnPost;
    private emcJLabel lblPrint = new emcJLabel("Print");
    private emcYesNoComponent ysnPrint;
    private emcJLabel lblPrinterSetup = new emcJLabel("Printer Setup");
    private emcJLabel lblQuantitySelection = new emcJLabel("Quantity Selection");
    private emcJLabel lblDocumentType = new emcJLabel("Document Type");
    //private DocumentTypesDropdown cmbDocumentTypes;
    private QuantitySelectionDropdown cmbQuantity;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Lines Overview Tab">
    private emcJTableUpdate tblLines;
    // </editor-fold>
    private EMCUserData copyUD1;
    private EMCUserData copyUD2;
    private PurchasePostMasterDRM masterDRM;
    private PurchasePostLinesDRM linesDRM;
    private PurchasePostSetupDRM setupDRM;
    private PurchaseOrderMasterDataRelationManager purchaseOrderMasterDataRelationManager;
    private String formType;
    //Location Lookup
    private EMCLookupJTableComponent lkpLocation;

    /** Creates a new instance of PostingForm */
    public PostingForm(EMCUserData userData) {
        super("POST - " + userData.getUserData(3), true, true, true, true, userData);
        this.setBounds(20, 20, 700, 600);
        copyUD1 = userData.copyUserData();
        copyUD2 = userData.copyUserData();
        formType = (String) userData.getUserData(3);
        try {

            purchaseOrderMasterDataRelationManager = (PurchaseOrderMasterDataRelationManager) userData.getUserData(2);
            userData.setUserData(2, null);

            setupDRM = new PurchasePostSetupDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.POP.getId(),
                    new emc.entity.pop.posting.POPPurchasePostSetupTable(), userData), userData);

            setupDRM.setTheForm(this);

            setupDRM.setFormTextId1("postSetupId");
            setupDRM.setFormTextId2("companyId");

            masterDRM = new PurchasePostMasterDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.POP.getId(),
                    new emc.entity.pop.posting.datasource.POPPurchasePostMasterDS(), copyUD1), copyUD1);

            masterDRM.setTheForm(this);

            masterDRM.setFormTextId1("postMasterId");
            masterDRM.setFormTextId2("purchaseOrderId");

            setupDRM.setMasterDRM(masterDRM);

            linesDRM = new PurchasePostLinesDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.POP.getId(),
                    new emc.entity.pop.posting.datasource.POPPurchasePostLinesDS(), copyUD2), formType, copyUD2) {

                @Override
                public void doRelation(int rowIndex) {
                    super.doRelation(rowIndex);
                    if (lkpLocation != null) {
                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class);
                        query.addAnd("warehouseId", this.getLastFieldValueAt("warehouse"));
                        lkpLocation.setTheQuery(query);
                    }
                }
            };

            linesDRM.setTheForm(this);

            linesDRM.setFormTextId2("itemId");
            linesDRM.setHeaderTable(masterDRM);

            this.setDataManager(masterDRM);

            masterDRM.setHeaderColumnID("postMasterId");
            masterDRM.setLinesTable(linesDRM);

            linesDRM.setFormTextId1("lineNumber");
            linesDRM.setRelationColumnToHeader("postMasterId");

            if ("quantity".equals(formType)) {
                EMCDouble dt = (EMCDouble) linesDRM.getDataType("quantity");
                dt.setMaxValue(0d);
                dt.setMinValue(Double.MIN_VALUE);
            }

            if (DocumentTypes.RETURN_GOODS.toString().equals(formType)) {
                DocumentNumFKNM dt = (DocumentNumFKNM) masterDRM.getDataType("documentNumber");
                dt.setRelatedTable(POPSupplierReceivedJournalMaster.class.getName());
                dt.setRelatedField("documentNumber");
            }

            setupYesNoComponentsAndDropdowns();

            initFrame();

            disableQuantitySelection();
        } catch (Exception ex) {
            if (EMCDebug.getDebug()) {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Failed to construct Posting Form", userData);
            }
        }

    }

    private void setupYesNoComponentsAndDropdowns() {
        ysnPost = new emcYesNoComponent(setupDRM, "post");
        ysnPrint = new emcYesNoComponent(setupDRM, "print");
        //cmbDocumentTypes = new DocumentTypesDropdown(setupDRM, "documentType");
        cmbQuantity = new QuantitySelectionDropdown(setupDRM, "quantitySelection");

        //Refresh setup DRM in order to display values
        setupDRM.doRelation(0);
    }

    /** Initializes the form. */
    private void initFrame() {
        emcJTabbedPane masterTabs = setupMaster();
        emcJPanel pnlMasterButtons = setupMasterButtons();
        emcJPanel pnlMaster = new emcJPanel();
        pnlMaster.setLayout(new BorderLayout());
        pnlMaster.add(masterTabs, BorderLayout.CENTER);
        pnlMaster.add(pnlMasterButtons, BorderLayout.EAST);

        emcJPanel pnlLines = new emcJPanel();
        pnlLines.setLayout(new BorderLayout());
        pnlLines.add(setupLines(), BorderLayout.CENTER);
        pnlLines.add(setupLinesButtons(), BorderLayout.EAST);
        this.setLayout(new GridLayout(1, 1));

        this.add(pnlMaster);
        this.add(pnlLines);

        emcJSplitPane topBottomSplit;
        topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, pnlMaster, pnlLines);
        topBottomSplit.setDividerSize(8);
        topBottomSplit.setContinuousLayout(true);
        topBottomSplit.setOneTouchExpandable(false);
        topBottomSplit.setDividerLocation(270);

        this.add(topBottomSplit);
    }

    /** Creates the master tabbed pane. */
    private emcJTabbedPane setupMaster() {
        emcJTabbedPane masterTabs = new emcJTabbedPane();

        //Overview
        emcJPanel pnlMaster = new emcJPanel();
        pnlMaster.setLayout(new GridLayout(1, 1));

        List masterKeys = new ArrayList();
        masterKeys.add("purchaseOrderId");
        masterKeys.add("supplierId");
        masterKeys.add("supplierName");
        masterKeys.add("documentNumber");
        masterKeys.add("postDate");

        emcTableModelUpdate m = new emcTableModelUpdate(masterDRM, masterKeys);

        tblMaster = new emcJTableUpdate(m) {

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                if (columnIndex == 3 && !DocumentTypes.RETURN_GOODS.toString().equals(formType)) {
                    return super.isCellEditable(rowIndex, columnIndex);
                } else {
                    return false;
                }

            }
        };
        tblMaster.setLookupCellEditor(m.getColumnByFieldName("purchaseOrderId"), new EMCGoToMainTableEditor(new EMCStringDocument(), new PurchaseOrders()));
        tblMaster.setLookupCellEditor(m.getColumnByFieldName("supplierId"), new EMCGoToMainTableEditor(new EMCStringDocument(), new Suppliers()));
        tblMaster.setLookupCellEditor(m.getColumnByFieldName("documentNumber"), new EMCGoToMainTableEditor(new EMCStringDocument(), new SupplierReceivedJournals()));
        masterDRM.setMainTableComponent(tblMaster);
        emcTablePanelUpdate masterScroll = new emcTablePanelUpdate(tblMaster);
        this.setTablePanel(masterScroll);
        pnlMaster.add(masterScroll);

        masterTabs.add("Overview", pnlMaster);
        masterTabs.add("Setup", setupMasterSetup());

        return masterTabs;
    }

    /** Creates the lines tabbed pane. */
    private emcJTabbedPane setupLines() {
        emcJTabbedPane linesTabs = new emcJTabbedPane();

        //Overview
        emcJPanel pnlLines = new emcJPanel();
        pnlLines.setLayout(new GridLayout(1, 1));

        List linesKeys = new ArrayList();
        linesKeys.add("itemPrimaryReference");
        linesKeys.add("itemDesc");
        linesKeys.add("dimension1Id");
        linesKeys.add("dimension3Id");
        linesKeys.add("dimension2Id");
        linesKeys.add("quantity");
        if (DocumentTypes.GOODS_RECEIVED_NOTE.toString().equals(formType)) {
            linesKeys.add("numberLabels");
            linesKeys.add("standardLocation");
        }

        emcTableModelUpdate m = new emcTableModelUpdate(linesDRM, linesKeys);

        tblLines = new emcJTableUpdate(m) {

            @Override
            public boolean isCellEditable(int arg0, int arg1) {
                if (arg1 == 5 || arg1 == 6 || arg1 == 7) {
                    return true;
                }

                return false;
            }
        };
        tblLines.setLookupCellEditor(m.getColumnByFieldName("itemPrimaryReference"), new EMCGoToMainTableEditor(new EMCStringDocument(), new ItemMaster()));
        tblLines.setLookupCellEditor(m.getColumnByFieldName("dimension1Id"), new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension1()));
        tblLines.setLookupCellEditor(m.getColumnByFieldName("dimension2Id"), new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension2()));
        tblLines.setLookupCellEditor(m.getColumnByFieldName("dimension3Id"), new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension3()));

        if (DocumentTypes.GOODS_RECEIVED_NOTE.toString().equals(formType)) {
            lkpLocation = new EMCLookupJTableComponent(new LocationMenu());
            lkpLocation.setPopup(new EMCLookupPopup(new InventoryLocation(), "locationId", copyUD1));
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class);
            query.addAnd("warehouseId", linesDRM.getLastFieldValueAt("warehouse"));
            lkpLocation.setTheQuery(query);
            tblLines.setLookupToColumn("standardLocation", lkpLocation);
        }

        linesDRM.setMainTableComponent(tblLines);
        emcTablePanelUpdate linesScroll = new emcTablePanelUpdate(tblLines);

        this.setTablePanel(linesScroll);
        pnlLines.add(linesScroll);

        linesTabs.add("Overview", pnlLines);

        return linesTabs;
    }

    private emcJPanel setupMasterSetup() {
        Component[][] components = {
            {lblPost, ysnPost},
            {lblPrint, ysnPrint},
            //{lblPrinterSetup},
            {lblQuantitySelection, cmbQuantity}, //                                    {lblDocumentType, cmbDocumentTypes},
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.HORIZONTAL, false);
    }

    private emcJPanel setupMasterButtons() {
        emcMenuButton btnPost = new PostButton("Post", null, this, 0, false, masterDRM, purchaseOrderMasterDataRelationManager);
        emcMenuButton btnCancel = new CancelButton(this);
        emcJButton btnPrintSettings = new emcJButton("Print Settings") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                EMCUserData userData = masterDRM.getUserData().copyUserDataAndDataList();

                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportPrintOptions.class.getName());
                query.addAnd("createdBy", userData.getUserName());
                query.addAnd("reportId", formType);
                query.addAnd("companyId", userData.getCompanyId());

                userData.setUserData(0, query.toString());
                userData.setUserData(1, query.getCountQuery());
                userData.setUserData(2, formType);

                ReportPrintOptionMenu repM = new ReportPrintOptionMenu();
                repM.setDoNotOpenForm(false);
                PostingForm.this.getDeskTop().createAndAdd(repM, -1, -1, userData, PostingForm.this, -1001);
            }
        };
        emcJButton btnPrintLabelSettings = new emcJButton("Label Settings") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                EMCUserData userData = masterDRM.getUserData().copyUserDataAndDataList();

                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportPrintOptions.class.getName());
                query.addAnd("createdBy", userData.getUserName());
                query.addAnd("reportId", EnumReports.POP_LABELS.toString());
                query.addAnd("companyId", userData.getCompanyId());

                userData.setUserData(0, query.toString());
                userData.setUserData(1, query.getCountQuery());
                userData.setUserData(2, EnumReports.POP_LABELS.toString());

                ReportPrintOptionMenu repM = new ReportPrintOptionMenu();
                repM.setDoNotOpenForm(false);
                PostingForm.this.getDeskTop().createAndAdd(repM, -1, -1, userData, PostingForm.this, -1001);
            }
        };
        emcJButton btnPopulateAllLines = new emcJButton("Entire Order") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                setupDRM.generatePost(true);
                setupDRM.refreshData();
                masterDRM.refreshData();
                linesDRM.refreshData();
            }
        };


        emcJPanel pnlButtons = new emcJPanel();

        pnlButtons.setLayout(new GridBagLayout());
        pnlButtons.setPreferredSize(new Dimension(125, 25));
        int y = 0;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc = emcSetGridBagConstraints.setGrid(gbc, 0, y);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        y++;
        gbc.gridy = y;
        pnlButtons.add(btnPost, gbc);

        y++;
        gbc.gridy = y;
        pnlButtons.add(btnCancel, gbc);

        y++;
        gbc.gridy = y;
        pnlButtons.add(btnPrintSettings, gbc);

        if (DocumentTypes.GOODS_RECEIVED_NOTE.toString().equals(formType)) {
            y++;
            gbc.gridy = y;
            pnlButtons.add(btnPrintLabelSettings, gbc);
        }

        y++;
        gbc.gridy = y;
        pnlButtons.add(btnPopulateAllLines, gbc);

        y++;
        gbc = emcSetGridBagConstraints.endPanel(y);

        pnlButtons.add(new emcJLabel(), gbc);

        return pnlButtons;
    }

    private emcJPanel setupLinesButtons() {
        emcJPanel pnlButtons = new emcJPanel();

        pnlButtons.setLayout(new GridBagLayout());
        pnlButtons.setPreferredSize(new Dimension(125, 25));
        int y = 0;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc = emcSetGridBagConstraints.setGrid(gbc, 0, y);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        PostSerialBatchMenu postBM = new PostSerialBatchMenu();
        postBM.setDoNotOpenForm(false);
        emcMenuButton recordButton = new emcMenuButton("Register", postBM, this, 0, true) {

            @Override
            public boolean isVisible() {
                if (DocumentTypes.GOODS_RECEIVED_NOTE.toString().equals(formType) || DocumentTypes.RETURN_GOODS.toString().equals(formType)) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void doActionPerformed(ActionEvent evt) {
                Object itemId = linesDRM.getFieldValueAt(linesDRM.getLastRowAccessed(), "itemId");
                if (!Functions.checkBlank(itemId)) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
                    query.addTableAnd(InventoryItemMaster.class.getName(), "itemDimensionGroupId", InventoryItemDimensionGroup.class.getName(), "dimensionGroup");
                    query.addAnd("itemId", itemId, InventoryItemMaster.class.getName());
                    query.addField("serialNumberActive");
                    query.addField("batchNumberActive");
                    query.addField("locationActive");
                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.DEVELOPERTOOLS.getId(), ServerDeveloperToolMethods.TESTQUERY.toString());
                    List toSend = new ArrayList();
                    toSend.add(query.toString());
                    List ret = EMCWSManager.executeGenericWS(cmd, toSend, copyUD2);
                    try {
                        Object[] retArray = (Object[]) ret.get(1);
                        if ((Boolean) retArray[0] || (Boolean) retArray[1] || (DocumentTypes.RETURN_GOODS.toString().equals(formType) && (Boolean) retArray[2])) {

                            super.doActionPerformed(evt);
                        } else {
                            Logger.getLogger("emc").log(Level.INFO, "The selected item does not require registration.", copyUD2);
                        }
                    } catch (Exception ex) {
                    }
                } else {
                    Logger.getLogger("emc").log(Level.INFO, "No Item selected.", copyUD2);
                }

            }
        };
        gbc.gridy = y;
        pnlButtons.add(recordButton, gbc);


        y++;
        gbc = emcSetGridBagConstraints.endPanel(y);
        pnlButtons.add(new emcJLabel(), gbc);

        return pnlButtons;
    }

    /** Disables the quantity selection combo box based on the status of the purchase order */
    public void disableQuantitySelection() {
        //When setting up permissions, purchase order data relation manager may be null.
        if (this.purchaseOrderMasterDataRelationManager == null) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.WARNING, "Purchase Order DRM is null.  This is only valid if you are busy setting up permissions.", getUserData());
            }
            return;
        }
        String status = String.valueOf(this.purchaseOrderMasterDataRelationManager.getFieldValueAt(this.purchaseOrderMasterDataRelationManager.getLastRowAccessed(), "status"));

        if (status.equals(PurchaseOrderStatus.REQUISITION.toString()) || status.equals(PurchaseOrderStatus.APPROVED.toString())) {
            cmbQuantity.setSelectedItem(PostingQuantities.ALL.toString());
            cmbQuantity.setEnabled(false);
        }
    }

    @Override
    public boolean doSaveOnClose() {
        boolean ret = super.doSaveOnClose();
        if (ret && DocumentTypes.RETURN_GOODS.toString().equals(formType)) {
            EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.DELETE_POST_DATA.toString());
            List toSend = new ArrayList();
            toSend.add(masterDRM.getFieldValueAt(masterDRM.getLastRowAccessed(), "postSetupId"));
            EMCWSManager.executeGenericWS(cmd, toSend, copyUD1);
        }
        return ret;
    }

    public String getFormType() {
        return formType;
    }

    @Override
    public HashMap<String, List> getFormDataSourcesAndButtons(EMCUserData userData) {
        HashMap<String, List> ret = super.getFormDataSourcesAndButtons(userData);

        List dataSources = ret.get("DATASOURCES");
        //Add where and order tables manually
        dataSources.add(setupDRM.getEntityClass());

        return ret;
    }

    @Override
    public void populateUserDataForPermissions(EMCUserData userData) {
        //Document type
        userData.setUserData(3, "");
        //Purchase Order DRM
        userData.setUserData(2, null);
    }
}

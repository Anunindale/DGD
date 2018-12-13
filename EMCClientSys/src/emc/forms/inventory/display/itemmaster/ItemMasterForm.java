/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.itemmaster;

import emc.app.components.EMCFormComboBox;
import emc.app.components.documents.EMCBigDecimalFormDocument;
import emc.app.components.documents.EMCDoubleFormDocument;
import emc.app.components.documents.EMCIntegerFormDocument;
import emc.app.components.documents.EMCLongFormDocument;
import emc.app.components.emcHelpFile;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcDialogue;
import emc.app.components.emcMenuButton;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.inventory.InventoryItemSubstituteRulesDropDown;
import emc.app.components.inventory.InventoryType;
import emc.app.components.inventory.emcStockButton;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.inventory.InventoryProductGroup;
import emc.entity.inventory.dimensions.InventoryItemDimension1Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension2Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension3Setup;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.pop.POPDiscountGroup;
import emc.entity.pop.POPExtraChargeGroup;
import emc.entity.pop.POPPriceGroup;
import emc.entity.pop.POPPurchaseOrderApprovalGroups;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryStatus;
import emc.enums.inventory.purchasing.InventoryStopPurchasingType;
import emc.enums.modules.enumEMCModules;
import emc.enums.trec.TRECWebStoreTypeEnum;
import emc.framework.EMCCommandClass;
import emc.framework.EMCMenuItem;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.inventory.menuitems.display.InventoryProductGroupMenu;
import emc.menus.pop.menuitems.display.DiscountGroups;
import emc.menus.pop.menuitems.display.ExtraChargeGroups;
import emc.menus.pop.menuitems.display.POPItemPriceArrangementsMI;
import emc.menus.pop.menuitems.display.PriceGroups;
import emc.menus.pop.menuitems.display.PurchaseOrderApprovalGroup;
import emc.menus.sop.menuitems.display.DiscountSetupMI;
import emc.menus.sop.menuitems.display.SOPPriceArangementItemMenu;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author riaan
 */
public class ItemMasterForm extends BaseInternalFrame {

    //Class variables
    //Buttons panel
    private emcJPanel pnlButtons = new emcJPanel();
    //Controls used on the overview tab
    //Panel
    private emcJPanel pnlOverview = new emcJPanel();
    //Lookups
    private EMCLookupJTableComponent lkpBrandGroup;
    private EMCLookupTableCellEditor brandGroupEditor;
    private EMCLookupJTableComponent lkpItemGroup;
    private EMCLookupTableCellEditor itemGroupEditor;
    //Controls used on the Definition tab
    //Panel
    emcJPanel pnlDefinition = new emcJPanel();
    //Text fields
    private EMCFormComboBox cmbStatus;
    //Lookups
    private EMCLookupFormComponent lkpDimension2Group;
    private EMCLookupFormComponent lkpDimension1Group;
    private EMCLookupFormComponent lkpDimension3Group;
    private EMCLookupFormComponent lkpDimGrpForm;
    private EMCLookupJTableComponent lkpItemRange;
    private EMCLookupTableCellEditor itemRangeEditor;
    private EMCLookupFormComponent lkpBaseUOM;
    private EMCLookupFormComponent lkpDefaultWHSE;
    //Text area
    private emcJTextArea txaDetailedDescription = new emcJTextArea();
    //Scrollpane
    private emcJScrollPane descPane = new emcJScrollPane(txaDetailedDescription);
    //Labels
    private emcJLabel lblStatus = new emcJLabel("Status");
    private emcJLabel lblDetailedDescription = new emcJLabel("Detailed Description");
    private emcJLabel lblBaseUOM = new emcJLabel("Base UOM");
    private emcJLabel lblDefaultWHSE = new emcJLabel("Default WHSE");
    private emcJLabel lblSizeGroup = new emcJLabel("Size Group");
    private emcJLabel lblColourGroup = new emcJLabel("Colour Group");
    private emcJLabel lblConfigGroup = new emcJLabel("Config Group");
    private emcJLabel lblDimentionGrpSetup = new emcJLabel("Dimension Group");
    //Controls used on the purchase tab
    //Panel
    private emcJPanel pnlPurchase = new emcJPanel();
    //Text fields
    private emcJTextField txtPurchasePrice = new emcJTextField();
    private emcJTextField txtPurchasePer = new emcJTextField();
    private emcJTextField txtPurchaseLTime = new emcJTextField();
    private emcJTextField txtPurchaseMinOrderQty = new emcJTextField();
    private EMCDatePickerFormComponent lastPurchaseDate = new EMCDatePickerFormComponent();
    private emcJTextField txtLastPurchasePrice = new emcJTextField();
    private emcJTextField txtLastPurchasePer = new emcJTextField();
    private emcJTextField txtLastPurchaseDTime = new emcJTextField();
    private emcJTextField txtLastPurchaseSupplier = new emcJTextField();
    private emcJTextField txtMinOrderQty = new emcJTextField();
    private emcJTextField txtMaxOrderQty = new emcJTextField();
    private emcJTextField txtMultOrderQty = new emcJTextField();
    //Lookups
    private EMCLookupFormComponent lkpPurchaseSupplierDefault;
    private EMCLookupFormComponent lkpPurchaseUOM;
    private EMCLookupFormComponent lkpPurchaseVatCode;
    private EMCLookupFormComponent lkpPurchasePriceGroup;
    private EMCLookupFormComponent lkpPurchaseDiscountGroup;
    private EMCLookupFormComponent lkpPurchaseExtraChargeGroup;
    //Yes no component
    private EMCFormComboBox cbStopPurchase;
    //Labels
    private emcJLabel lblPurchasePrice = new emcJLabel("Purchase Price");
    private emcJLabel lblPurchasePer = new emcJLabel("Per");
    private emcJLabel lblPurchaseVatCode = new emcJLabel("VAT Code");
    private emcJLabel lblPurchasePriceGroup = new emcJLabel("Price Group");
    private emcJLabel lblPurchaseDiscountGroup = new emcJLabel("Discount Group");
    private emcJLabel lblPurchaseExtraChargeGroup = new emcJLabel("Extra Charge Group");
    private emcJLabel lblPurchaseSupplier = new emcJLabel("Supplier(def)");
    private emcJLabel lblPurchaseLTime = new emcJLabel("L/Time (days)");
    private emcJLabel lblPurchaseUOM = new emcJLabel("UOM");
    private emcJLabel lblPurchaseMinOrderQty = new emcJLabel("Min. Order Qty(UOM)");
    private emcJLabel lblLastPurchaseDate = new emcJLabel("Last Purchase Date");
    private emcJLabel lblLastPurchaseSupplier = new emcJLabel("Last Purchase Supplier");
    private emcJLabel lblLastPurchasePrice = new emcJLabel("Last Purchase Price");
    private emcJLabel lblLastPurchasePer = new emcJLabel("Last Purchase Per");
    private emcJLabel lblLastPurchaseDTime = new emcJLabel("Last Purchase D/Time");
    private emcJLabel lblStopPurchase = new emcJLabel("Stop Purchase");
    private emcJLabel lblWebStoreType = new emcJLabel("Web Store Type");
    //Controls used on the sales tab
    //Panel
    private emcJPanel pnlSales = new emcJPanel();
    //Text fields
    private emcJTextField txtSellingPrice;
    private emcJTextField txtSalesPer;
    private EMCDatePickerFormComponent txtSalesPriceUpdateDate;
    private emcJTextField txtSalesMinOrderQty;
    private emcJTextField txtSalesCommissionGroup;
    private emcJTextField txtSalesRoyaltyGroup;
    private EMCFormComboBox cmbwebStoreType;
    //Lookups
    private EMCLookupFormComponent lkpSalesUOM;
    private EMCLookupFormComponent lkpSalesVatCode;
    private EMCLookupFormComponent lkpSalesPriceGroup;
    private EMCLookupFormComponent lkpSalesDiscountGroup;
    private EMCLookupFormComponent lkpSalesExtraChargeGroup;
    //Yes no components
    private emcYesNoComponent yncCustomerRestricted;
    private emcYesNoComponent yncStopSelling;
    //Labels
    private emcJLabel lblSellingPrice = new emcJLabel("Selling Price");
    private emcJLabel lblSalesPer = new emcJLabel("Per");
    private emcJLabel lblSalesVatCode = new emcJLabel("VAT Code");
    private emcJLabel lblSalesPriceGroup = new emcJLabel("Price Group");
    private emcJLabel lblSalesDiscountGroup = new emcJLabel("Discount Group");
    private emcJLabel lblSalesExtraChargeGroup = new emcJLabel("Extra Charge Group");
    private emcJLabel lblSalesPriceUpdateDate = new emcJLabel("Price Update Date");
    private emcJLabel lblSalesUOM = new emcJLabel("UOM");
    private emcJLabel lblSalesMinOrderQty = new emcJLabel("Min. Order Qty");
    private emcJLabel lblSalesCommissionGroup = new emcJLabel("Commission Group");
    private emcJLabel lblSalesRoyaltyGroup = new emcJLabel("Royalty Group");
    private emcJLabel lblCustomerRestricted = new emcJLabel("Cust. Restricted");
    private emcJLabel lblStopSelling = new emcJLabel("Stop Selling");
    //Controls used on the costing tab
    //Panel
    private emcJPanel pnlCosting = new emcJPanel();
    //Text fields
    private emcJTextField txtCurrentCost = new emcJTextField();
    private EMCDatePickerFormComponent CostDate;
    private emcJTextField txtPrevCost = new emcJTextField();
    private EMCDatePickerFormComponent prevCostDate;
    private emcJTextField txtWeightedAve = new emcJTextField();
    //Lookup
    private EMCLookupFormComponent lkpCostingGroup;
    //Labels
    private emcJLabel lblCurrentCost = new emcJLabel("Current Cost");
    private emcJLabel lblCostDate = new emcJLabel("Cost Date");
    private emcJLabel lblPrevCost = new emcJLabel("Prev cost");
    private emcJLabel lblPrevCostDate = new emcJLabel("Prev Cost Date");
    private emcJLabel lblWeightedAve = new emcJLabel("Weighted Ave");
    private emcJLabel lblCostingGroup = new emcJLabel("Costing Group");
    //Controls used on the specification tab
    //Panel
    private emcJPanel pnlSpecification = new emcJPanel();
    //Text fields
    private emcJTextField txtBatchGroup = new emcJTextField();
    private emcJTextField txtWarrantyExpiryGroup = new emcJTextField();
    private emcJTextField txtBarCodeGroup = new emcJTextField();
    private emcJTextField txtWidth = new emcJTextField();
    private emcJTextField txtLength = new emcJTextField();
    private emcJTextField txtHeight = new emcJTextField();
    private emcJTextField txtWeight = new emcJTextField();
    //Lookups
    private EMCLookupFormComponent lkpWidthUOM;
    private EMCLookupFormComponent lkpLengthUOM;
    private EMCLookupFormComponent lkpHeightUOM;
    private EMCLookupFormComponent lkpWeightUOM;
    //Labels
    private emcJLabel lblBatchGroup = new emcJLabel("Batch Group");
    private emcJLabel lblWarrantyExpiryGroup = new emcJLabel("Warranty/Expiry Group");
    private emcJLabel lblBarCodeGroup = new emcJLabel("Bar Code Group");
    private emcJLabel lblWidth = new emcJLabel("Width");
    private emcJLabel lblLength = new emcJLabel("Length");
    private emcJLabel lblHeight = new emcJLabel("Height");
    private emcJLabel lblWeight = new emcJLabel("Weight");
    private emcJLabel lblWidthUOM = new emcJLabel("UOM");
    private emcJLabel lblLengthUOM = new emcJLabel("UOM");
    private emcJLabel lblHeightUOM = new emcJLabel("UOM");
    private emcJLabel lblWeightUOM = new emcJLabel("UOM");
    //Controls used on the planning tab
    //Panel
    private emcJPanel pnlPlanning = new emcJPanel();
    //Text fields
    //Yes No components
    private emcYesNoComponent yncAllowSubstitute;
    //Dropdowns
    private InventoryItemSubstituteRulesDropDown cmbSubstituteRule;
    //Lookups
    private EMCLookupFormComponent lkpSubstituteItem;
    private EMCLookupFormComponent lkpSubstituteDimension1;
    private EMCLookupFormComponent lkpSubstituteDimension2;
    private EMCLookupFormComponent lkpSubstituteDimension3;
    //Labels
    private emcJLabel lblPlanning = new emcJLabel("Planning Group");
    private emcJLabel lblAllowSubstitute = new emcJLabel("Allow substitute");
    private emcJLabel lblSubstituteRule = new emcJLabel("Substitute Rule");
    private emcJLabel lblSubstituteItem = new emcJLabel("Substitute Item");
    private emcJLabel lblSubstituteDimension1 = new emcJLabel("Substitute Config");
    private emcJLabel lblSubstituteDimension2 = new emcJLabel("Substitute Size");
    private emcJLabel lblSubstituteDimension3 = new emcJLabel("Substitute Colour");
    private emcJLabel lblWarrantyGroup = new emcJLabel("Warranty Group");
    //Controls used on the classification tab
    //Panel
    private emcJPanel pnlClassification = new emcJPanel();
    //Lookups
    private EMCLookupFormComponent lkpClassGroup1;
    private EMCLookupFormComponent lkpClassGroup2;
    private EMCLookupFormComponent lkpClassGroup3;
    private EMCLookupFormComponent lkpClassGroup4;
    private EMCLookupFormComponent lkpClassGroup5;
    private EMCLookupFormComponent lkpClassGroup6;
    //Labels
    private emcJLabel lblClassGroup1 = new emcJLabel("Class Group 1");
    private emcJLabel lblClassGroup2 = new emcJLabel("Class Group 2");
    private emcJLabel lblClassGroup3 = new emcJLabel("Class Group 3");
    private emcJLabel lblClassGroup4 = new emcJLabel("Class Group 4");
    private emcJLabel lblClassGroup5 = new emcJLabel("Class Group 5");
    private emcJLabel lblClassGroup6 = new emcJLabel("Class Group 6");
    //Controls used on the production tab
    //Panel
    private emcJPanel pnlProduction = new emcJPanel();
    //Text fields
    private emcJTextField txtStdCost = new emcJTextField();
    private emcJTextField txtStdCostDate = new emcJTextField();
    private emcJTextField txtProdLTime;
    private emcJTextField txtMinProdQty = new emcJTextField();
    private emcJTextField txtdrawingPattern = new emcJTextField();
    private emcJTextField txtProdRef1 = new emcJTextField();
    private emcJTextField txtProdRef2 = new emcJTextField();
    private emcJTextField txtPhantom = new emcJTextField();
    private emcJTextField txtBinItem = new emcJTextField();
    private emcJTextField txtScrap = new emcJTextField();
    //Lookups
    private EMCLookupFormComponent lkpProductionUOM;
    //Yes no component
    private emcYesNoComponent yncStopProduction;
    //Labels
    private emcJLabel lblBOMId = new emcJLabel("BOM ID");
    private emcJLabel lblRoutingId = new emcJLabel("Routing ID");
    private emcJLabel lblStdCost = new emcJLabel("Std. Cost");
    private emcJLabel lblStdCostDate = new emcJLabel("Std. Cost Date");
    private emcJLabel lblProdLTime = new emcJLabel("Prod L/Time (Days)");
    private emcJLabel lblMinProdQty = new emcJLabel("Min Prod Qty");
    private emcJLabel lbldrawingPattern = new emcJLabel("Drawing/Pattern");
    private emcJLabel lblProdRef1 = new emcJLabel("Prod Ref 1");
    private emcJLabel lblProdRef2 = new emcJLabel("Prod Ref 2");
    private emcJLabel lblPhantom = new emcJLabel("Phantom");
    private emcJLabel lblBinItem = new emcJLabel("Bin Item");
    private emcJLabel lblStopProduction = new emcJLabel("Stop Production");
    private emcJLabel lblScrap = new emcJLabel("Scrap %");
    //The data relation manager used for this form
    private ItemMasterDRM dataRelation;
    //Copy of user data
    private EMCUserData copyUD;
    //Lookup relation manager
    private ItemMasterLookupRelationManager lookupRelationManager;

    /**
     * Creates a new instance of ItemMasterForm
     */
    public ItemMasterForm(EMCUserData userData) {
        //Calls the super class constructor
        super("Item Master", true, true, true, true, userData);
        this.setBounds(20, 20, 820, 530);
        this.setHelpFile(new emcHelpFile("Inventory/InventoryItemMaster.html"));

        //Sets up the data relation manager for this form
        StockDRMParameters param = new StockDRMParameters("itemId", null, null, null, null);
        dataRelation = new ItemMasterDRM(new emcGenericDataSourceUpdate(
                enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.datasource.InventoryItemMasterDS(), userData), param, userData);
        this.setDataManager(dataRelation);

        //Creates a copy of the given user data
        copyUD = userData.copyUserData();
        copyUD.setUserData(null);

        //Add the form to the data relation manager
        dataRelation.setTheForm(this);
        dataRelation.setFormTextId1("itemId");
        dataRelation.setFormTextId2("description");

        //Initializes YesNoComponents
        initializeYesNoComponents();

        //Sets up the lookups on the form
        setupLookups();
        setDocuments();
        setupLookupRelations();

        //Initialize dropdown
        initializeDropdowns();

        setComponentsNotEditable();

        //Initializes the frame
        initFrame();
    }

    private void setComponentsNotEditable() {
        txtPurchasePer.setEditable(false);
        lastPurchaseDate.setEnabled(false);
        txtLastPurchaseSupplier.setEditable(false);
        txtLastPurchasePer.setEditable(false);
        txtLastPurchasePrice.setEditable(false);
        txtLastPurchaseDTime.setEditable(false);
        //txtCurrentCost.setEditable(false);
        //CostDate.setEditable(false);
        txtPrevCost.setEditable(false);
        prevCostDate.setEnabled(false);
        txtWeightedAve.setEditable(false);
        txtBatchGroup.setEditable(false);
        txtWarrantyExpiryGroup.setEditable(false);
        txtBarCodeGroup.setEditable(false);

        txtStdCost.setEditable(false);
        txtStdCostDate.setEditable(false);
        txtMinProdQty.setEditable(false);
        txtdrawingPattern.setEditable(false);
        txtProdRef1.setEditable(false);
        txtProdRef2.setEditable(false);
        txtPhantom.setEditable(false);
        txtBinItem.setEditable(false);
        yncStopProduction.setEnabled(false);
    }

    //This method is used to initialize YesNoComponents
    private void initializeYesNoComponents() {
        this.yncCustomerRestricted = new emcYesNoComponent(dataRelation, "customerRestricted");
        this.yncStopSelling = new emcYesNoComponent(dataRelation, "stopSelling");
        this.yncStopProduction = new emcYesNoComponent(dataRelation, "stopProduction");

        this.yncAllowSubstitute = new emcYesNoComponent(dataRelation, "planningAllowSubstitute");
    }

    /**
     * Initialize dropdowns
     */
    private void initializeDropdowns() {
        cbStopPurchase = new EMCFormComboBox(InventoryStopPurchasingType.values(), dataRelation, "stopPurchase");
        cmbSubstituteRule = new InventoryItemSubstituteRulesDropDown(dataRelation, "planningSubstituteRule");
    }

    private void setDocuments() {
        this.cmbStatus = new EMCFormComboBox(new String[]{InventoryStatus.ACTIVE.toString(), InventoryStatus.INDESIGN.toString(),
                    InventoryStatus.REJECTED.toString(), InventoryStatus.STOPPED.toString()
                }, dataRelation, "status");
        this.txaDetailedDescription.setDocument(new EMCStringFormDocument(dataRelation, "detailedDescription"));
        this.txtPurchasePrice.setDocument(new EMCDoubleFormDocument(dataRelation, "purchasePrice"));
        this.txtCurrentCost.setDocument(new EMCDoubleFormDocument(dataRelation, "costingCurrentCost"));
        this.txtPrevCost.setDocument(new EMCDoubleFormDocument(dataRelation, "costingPrevCost"));
        this.txtWeightedAve.setDocument(new EMCDoubleFormDocument(dataRelation, "costingWeightedAve"));
        this.txtPurchasePer.setDocument(new EMCStringFormDocument(dataRelation, "baseUOM"));
        this.txtPurchaseLTime.setDocument(new EMCDoubleFormDocument(dataRelation, "purchaseLeadTime"));
        this.txtPurchaseMinOrderQty.setDocument(new EMCDoubleFormDocument(dataRelation, "purchaseMinOrderQty"));
        this.txtWidth.setDocument(new EMCDoubleFormDocument(dataRelation, "specWidth"));
        this.txtWeight.setDocument(new EMCDoubleFormDocument(dataRelation, "specWeight"));
        this.txtHeight.setDocument(new EMCDoubleFormDocument(dataRelation, "specHeight"));
        this.txtLength.setDocument(new EMCDoubleFormDocument(dataRelation, "specLength"));
        this.txtScrap.setDocument(new EMCDoubleFormDocument(dataRelation, "scrap"));

        this.txtLastPurchaseDTime.setDocument(new EMCLongFormDocument(dataRelation, "lastPurchaseDDate"));
        this.txtLastPurchasePer.setDocument(new EMCStringFormDocument(dataRelation, "lastPurchasePer"));
        this.txtLastPurchasePrice.setDocument(new EMCDoubleFormDocument(dataRelation, "lastPurchasePrice"));
        this.txtLastPurchaseSupplier.setDocument(new EMCStringFormDocument(dataRelation, "lastPurchaseSupplier"));

        this.txtMultOrderQty.setDocument(new EMCDoubleFormDocument(dataRelation, "multOrderQty"));
        this.txtMinOrderQty.setDocument(new EMCDoubleFormDocument(dataRelation, "minOrderQty"));
        this.txtMaxOrderQty.setDocument(new EMCDoubleFormDocument(dataRelation, "maxOrderQty"));
    }

    //This method is used to set up the lookups on the form
    private void setupLookups() {
        //Date picker
        this.lastPurchaseDate = new EMCDatePickerFormComponent(dataRelation, "lastPurchaseDate");
        this.CostDate = new EMCDatePickerFormComponent(dataRelation, "costingCostDate");
        this.prevCostDate = new EMCDatePickerFormComponent(dataRelation, "costingPrevCostDate");

        //Substitute item lookup
        List<String> itemKeys = new ArrayList<String>();
        itemKeys.add("itemId");
        itemKeys.add("description");
        itemKeys.add("brandGroup");
        itemKeys.add("itemRange");

        lkpSubstituteItem = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.ItemMaster(), dataRelation, "subItemPrimaryReference");
        EMCLookupPopup substituteItemPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryItemMaster(),
                "itemId", itemKeys, copyUD);
        lkpSubstituteItem.setPopup(substituteItemPopup);
        dataRelation.setDocument(lkpSubstituteItem.getDocument());


        //Substitute dimensions
        List<String> dimensionKeys = new ArrayList<String>();
        dimensionKeys.add("dimensionId");
        dimensionKeys.add("description");

        lkpSubstituteDimension1 = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.Dimension1(), dataRelation, "planningSubstituteDimension1");
        lkpSubstituteDimension2 = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.Dimension2(), dataRelation, "planningSubstituteDimension2");
        lkpSubstituteDimension3 = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.Dimension3(), dataRelation, "planningSubstituteDimension3");

        EMCLookupPopup substituteDimension1Popup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.datasource.InventoryItemDimension1SetupDS(),
                "dimensionId", dimensionKeys, copyUD);
        substituteDimension1Popup.setPreferredWidth(500);
        EMCLookupPopup substituteDimension2Popup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.datasource.InventoryItemDimension2SetupDS(),
                "dimensionId", dimensionKeys, copyUD);
        EMCLookupPopup substituteDimension3Popup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.datasource.InventoryItemDimension3SetupDS(),
                "dimensionId", dimensionKeys, copyUD);

        lkpSubstituteDimension1.setPopup(substituteDimension1Popup);
        lkpSubstituteDimension2.setPopup(substituteDimension2Popup);
        lkpSubstituteDimension3.setPopup(substituteDimension3Popup);

        dataRelation.setDocument(lkpSubstituteDimension1.getDocument());
        dataRelation.setDocument(lkpSubstituteDimension2.getDocument());
        dataRelation.setDocument(lkpSubstituteDimension3.getDocument());

        //Size Group Lookup
        //Keys for the size group lookup
        List sizeGroupKeys = new ArrayList<String>();
        sizeGroupKeys.add("dimensionGroupId");
        sizeGroupKeys.add("description");

        lkpDimension2Group = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.Dimension2Groups(),
                dataRelation, "dimension2Group");
        EMCLookupPopup sizeGroupPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.InventoryDimension2Group(),
                "dimensionGroupId", sizeGroupKeys, copyUD);
        lkpDimension2Group.setPopup(sizeGroupPopup);
        dataRelation.setDocument(lkpDimension2Group.getDocument());

        List colourGroupKeys = new ArrayList<String>();
        colourGroupKeys.add("dimensionGroupId");
        colourGroupKeys.add("description");

        lkpDimension3Group = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.Dimension3Groups(),
                dataRelation, "dimension3Group");
        EMCLookupPopup colourGroupPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.InventoryDimension3Group(),
                "dimensionGroupId", colourGroupKeys, copyUD);
        lkpDimension3Group.setPopup(colourGroupPopup);
        dataRelation.setDocument(lkpDimension3Group.getDocument());

        List configGroupKeys = new ArrayList<String>();
        configGroupKeys.add("dimensionGroupId");
        configGroupKeys.add("description");

        lkpDimension1Group = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.Dimension1Groups(),
                dataRelation, "dimension1Group");
        EMCLookupPopup configGroupPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.InventoryDimension1Group(),
                "dimensionGroupId", configGroupKeys, copyUD);
        lkpDimension1Group.setPopup(configGroupPopup);
        dataRelation.setDocument(lkpDimension1Group.getDocument());

        //Item Range Lookup
        //Keys for the size group lookup
        List itemRangeKeys = new ArrayList<String>();
        itemRangeKeys.add("itemRangeId");
        itemRangeKeys.add("description");

        lkpItemRange = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.ItemRanges());
        EMCLookupPopup itemRangePopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryItemRange(),
                "itemRangeId", itemRangeKeys, copyUD);
        lkpItemRange.setPopup(itemRangePopup);
        itemRangeEditor = new EMCLookupTableCellEditor(lkpItemRange);

        //Costing Group Lookup
        //Keys for the costing group lookup
        List costingGroupKeys = new ArrayList<String>();
        costingGroupKeys.add("costingGroupId");
        costingGroupKeys.add("description");

        lkpCostingGroup = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.CostingGroups(),
                dataRelation, "costingGroup");
        EMCLookupPopup costingGroupPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryCostingGroup(),
                "costingGroupId", costingGroupKeys, copyUD);
        lkpCostingGroup.setPopup(costingGroupPopup);
        dataRelation.setDocument(lkpCostingGroup.getDocument());

        List defaultWHSEKeys = new ArrayList<String>();
        defaultWHSEKeys.add("warehouseId");
        defaultWHSEKeys.add("description");

        lkpDefaultWHSE = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.Warehouse(),
                dataRelation, "defaultWarehouse");
        EMCLookupPopup defaultWHSEPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryWarehouse(),
                "warehouseId", defaultWHSEKeys, copyUD);
        lkpDefaultWHSE.setPopup(defaultWHSEPopup);
        dataRelation.setDocument(lkpDefaultWHSE.getDocument());

        //Supplier Lookups
        //Keys for the supplier lookups
        List supplierKeys = new ArrayList<String>();
        supplierKeys.add("supplierId");
        supplierKeys.add("supplierName");
        supplierKeys.add("supplierGroup");
        supplierKeys.add("telephone");

        EMCLookupPopup supplierPopup = new EMCLookupPopup(enumEMCModules.POP.getId(), new emc.entity.pop.POPSuppliers(),
                "supplierId", supplierKeys, copyUD);

        //Supplier Default
        lkpPurchaseSupplierDefault = new EMCLookupFormComponent(new emc.menus.pop.menuitems.display.Suppliers(),
                dataRelation, "purchaseDefaultSupplier");

        lkpPurchaseSupplierDefault.setPopup(supplierPopup);
        dataRelation.setDocument(lkpPurchaseSupplierDefault.getDocument());

        //Last purchase supplier
//        lkpLastPurchaseSupplier = new emcLookupFormComponent(new emc.menus.base.menuItems.display.UnitsOfMeasure(),
//                dataRelation,"lastPurchaseSupplier");
//        
//        lkpLastPurchaseSupplier.setPopup(supplierPopup);
//        dataRelation.setDocument(lkpLastPurchaseSupplier.getDocument());
//        
        //Brand Group Lookup
        //Keys for the brand group lookup
        List brandGroupKeys = new ArrayList<String>();
        brandGroupKeys.add("brandGroupId");
        brandGroupKeys.add("brandGroupName");
        brandGroupKeys.add("brandGroupDescription");

        //Brand Group
        lkpBrandGroup = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.BrandGroups());
        EMCLookupPopup brandGroupPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryBrandGroup(),
                "brandGroupId", brandGroupKeys, copyUD);
        lkpBrandGroup.setPopup(brandGroupPopup);
        brandGroupEditor = new EMCLookupTableCellEditor(lkpBrandGroup);

        //Units of Measure Lookups
        //Keys for the brand group lookup
        List uomKeys = new ArrayList<String>();
        uomKeys.add("unit");
        uomKeys.add("description");

        //Popup
        EMCLookupPopup uomPopup = new EMCLookupPopup(enumEMCModules.BASE.getId(), new emc.entity.base.BaseUnitsOfMeasure(),
                "unit", uomKeys, copyUD);

        //Base UOM
        lkpBaseUOM = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.UnitsOfMeasure(),
                dataRelation, "baseUOM");
        lkpBaseUOM.setPopup(uomPopup);
        dataRelation.setDocument(lkpBaseUOM.getDocument());

        //Purchase UOM
        lkpPurchaseUOM = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.UnitsOfMeasure(),
                dataRelation, "purchaseUOM");
        lkpPurchaseUOM.setPopup(uomPopup);
        dataRelation.setDocument(lkpPurchaseUOM.getDocument());

        //Production UOM
        lkpProductionUOM = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.UnitsOfMeasure(),
                dataRelation, "productionUOM");
        lkpProductionUOM.setPopup(uomPopup);
        dataRelation.setDocument(lkpProductionUOM.getDocument());

        //Production BOM
        List bomKeys = new ArrayList();
        bomKeys.add("bomId");
        bomKeys.add("description");
        bomKeys.add("bomType");

        //Sales UOM
        lkpSalesUOM = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.UnitsOfMeasure(),
                dataRelation, "sellingUOM");
        lkpSalesUOM.setPopup(uomPopup);
        dataRelation.setDocument(lkpSalesUOM.getDocument());

        //Sales Price Group
        lkpSalesPriceGroup = new EMCLookupFormComponent(new PriceGroups(), dataRelation, "sellingPriceGroup");
        lkpSalesPriceGroup.setPopup(new EMCLookupPopup(new POPPriceGroup(), "priceGroupId", copyUD));

        //Sales Discount Group
        lkpSalesDiscountGroup = new EMCLookupFormComponent(new DiscountGroups(), dataRelation, "sellingDiscountGroup");
        lkpSalesDiscountGroup.setPopup(new EMCLookupPopup(new POPDiscountGroup(), "discountGroupId", copyUD));

        //Sales Discount Group
        lkpSalesExtraChargeGroup = new EMCLookupFormComponent(new ExtraChargeGroups(), dataRelation, "sellingExtraChargeGroup");
        lkpSalesExtraChargeGroup.setPopup(new EMCLookupPopup(new POPExtraChargeGroup(), "extraChargeGroupId", copyUD));

        //Specification Width UOM
        lkpWidthUOM = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.UnitsOfMeasure(),
                dataRelation, "specWidthUOM");
        lkpWidthUOM.setPopup(uomPopup);
        dataRelation.setDocument(lkpWidthUOM.getDocument());

        //Specification Length UOM
        lkpLengthUOM = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.UnitsOfMeasure(),
                dataRelation, "specLengthUOM");
        lkpLengthUOM.setPopup(uomPopup);
        dataRelation.setDocument(lkpLengthUOM.getDocument());

        //Specification Height UOM
        lkpHeightUOM = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.UnitsOfMeasure(),
                dataRelation, "specHeightUOM");
        lkpHeightUOM.setPopup(uomPopup);
        dataRelation.setDocument(lkpHeightUOM.getDocument());

        //Specification Weight UOM
        lkpWeightUOM = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.UnitsOfMeasure(),
                dataRelation, "specWeightUOM");
        lkpWeightUOM.setPopup(uomPopup);
        dataRelation.setDocument(lkpWeightUOM.getDocument());

        //item Group
        lkpItemGroup = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.ItemGroups());
        List dimGroupKeys = new ArrayList();
        dimGroupKeys.add("itemDimensionGroupId");
        dimGroupKeys.add("description");
        EMCLookupPopup dimGroupPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.InventoryItemDimensionGroup(),
                "itemDimensionGroupId", dimGroupKeys, copyUD);
        List itemGroupKeys = new ArrayList();
        itemGroupKeys.add("itemGroup");
        itemGroupKeys.add("description");
        EMCLookupPopup itemGroupPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryItemGroup(),
                "itemGroup", itemGroupKeys, copyUD);

        lkpItemGroup.setPopup(itemGroupPopup);
        itemGroupEditor = new EMCLookupTableCellEditor(lkpItemGroup);
        lkpDimGrpForm = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.DimensionGroupSetup(), dataRelation, "dimensionGroup");
        lkpDimGrpForm.setPopup(dimGroupPopup);
        //Vat codes
        //Keys for the vat code lookups
        List vatCodeKeys = new ArrayList<String>();
        vatCodeKeys.add("vatId");
        vatCodeKeys.add("description");

        lkpPurchaseVatCode = new EMCLookupFormComponent(new emc.menus.gl.menuitems.display.GLVATCode(),
                dataRelation, "purchaseVatCode");
        EMCLookupPopup vatCodePopup = new EMCLookupPopup(enumEMCModules.GENERAL_LEDGER.getId(), new emc.entity.gl.GLVATCode(),
                "vatId", vatCodeKeys, copyUD);
        lkpPurchaseVatCode.setPopup(vatCodePopup);
        dataRelation.setDocument(lkpPurchaseVatCode.getDocument());

        lkpSalesVatCode = new EMCLookupFormComponent(new emc.menus.gl.menuitems.display.GLVATCode(),
                dataRelation, "sellingVatCode");
        lkpSalesVatCode.setPopup(vatCodePopup);
        dataRelation.setDocument(lkpSalesVatCode.getDocument());

        //Keys for the purchase price group
        List priceGroupKeys = new ArrayList<String>();
        priceGroupKeys.add("priceGroupId");
        priceGroupKeys.add("description");

        lkpPurchasePriceGroup = new EMCLookupFormComponent(new emc.menus.pop.menuitems.display.PriceGroups(),
                dataRelation, "purchasePriceGroup");
        EMCLookupPopup purchasePriceGroupPopup = new EMCLookupPopup(enumEMCModules.POP.getId(), new emc.entity.pop.POPPriceGroup(),
                "priceGroupId", priceGroupKeys, copyUD);
        lkpPurchasePriceGroup.setPopup(purchasePriceGroupPopup);
        dataRelation.setDocument(lkpPurchasePriceGroup.getDocument());

        //Keys for the purchase discount group
        List discountGroupKeys = new ArrayList<String>();
        discountGroupKeys.add("discountGroupId");
        discountGroupKeys.add("description");

        lkpPurchaseDiscountGroup = new EMCLookupFormComponent(new emc.menus.pop.menuitems.display.DiscountGroups(),
                dataRelation, "purchaseDiscountGroup");
        EMCLookupPopup purchaseDiscountGroupPopup = new EMCLookupPopup(enumEMCModules.POP.getId(), new emc.entity.pop.POPDiscountGroup(),
                "discountGroupId", discountGroupKeys, copyUD);
        lkpPurchaseDiscountGroup.setPopup(purchaseDiscountGroupPopup);
        dataRelation.setDocument(lkpPurchaseDiscountGroup.getDocument());

        //Keys for the purchase extra charge group
        List extraChargeGroupKeys = new ArrayList<String>();
        extraChargeGroupKeys.add("extraChargeGroupId");
        extraChargeGroupKeys.add("description");

        lkpPurchaseExtraChargeGroup = new EMCLookupFormComponent(new emc.menus.pop.menuitems.display.ExtraChargeGroups(),
                dataRelation, "purchaseExtraChargeGroup");
        EMCLookupPopup purchaseExtraChargeGroupPopup = new EMCLookupPopup(enumEMCModules.POP.getId(), new emc.entity.pop.POPExtraChargeGroup(),
                "extraChargeGroupId", extraChargeGroupKeys, copyUD);
        lkpPurchaseExtraChargeGroup.setPopup(purchaseExtraChargeGroupPopup);
        dataRelation.setDocument(lkpPurchaseExtraChargeGroup.getDocument());


        //Classification groups
        List classGroupKeys = new ArrayList<String>();
        classGroupKeys.add("classification");
        classGroupKeys.add("description");

        EMCLookupPopup itemClassificationGroupPopup1 = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.classifications.InventoryItemClassification1(),
                "classification", classGroupKeys, copyUD);
        EMCLookupPopup itemClassificationGroupPopup2 = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.classifications.InventoryItemClassification2(),
                "classification", classGroupKeys, copyUD);
        EMCLookupPopup itemClassificationGroupPopup3 = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.classifications.InventoryItemClassification3(),
                "classification", classGroupKeys, copyUD);
        EMCLookupPopup itemClassificationGroupPopup4 = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.classifications.InventoryItemClassification4(),
                "classification", classGroupKeys, copyUD);
        EMCLookupPopup itemClassificationGroupPopup5 = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.classifications.InventoryItemClassification5(),
                "classification", classGroupKeys, copyUD);
        EMCLookupPopup itemClassificationGroupPopup6 = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.classifications.InventoryItemClassification6(),
                "classification", classGroupKeys, copyUD);

        lkpClassGroup1 = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.Classifications1(),
                dataRelation, "classificationClassGroup1");
        lkpClassGroup2 = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.Classifications2(),
                dataRelation, "classificationClassGroup2");
        lkpClassGroup3 = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.Classifications3(),
                dataRelation, "classificationClassGroup3");
        lkpClassGroup4 = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.Classifications4(),
                dataRelation, "classificationClassGroup4");
        lkpClassGroup5 = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.Classifications5(),
                dataRelation, "classificationClassGroup5");
        lkpClassGroup6 = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.Classifications6(),
                dataRelation, "classificationClassGroup6");

        lkpClassGroup1.setPopup(itemClassificationGroupPopup1);
        lkpClassGroup2.setPopup(itemClassificationGroupPopup2);
        lkpClassGroup3.setPopup(itemClassificationGroupPopup3);
        lkpClassGroup4.setPopup(itemClassificationGroupPopup4);
        lkpClassGroup5.setPopup(itemClassificationGroupPopup5);
        lkpClassGroup6.setPopup(itemClassificationGroupPopup6);

        dataRelation.setDocument(lkpClassGroup1.getDocument());
        dataRelation.setDocument(lkpClassGroup2.getDocument());
        dataRelation.setDocument(lkpClassGroup3.getDocument());
        dataRelation.setDocument(lkpClassGroup4.getDocument());
        dataRelation.setDocument(lkpClassGroup5.getDocument());
        dataRelation.setDocument(lkpClassGroup6.getDocument());

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

        lkpSubstituteDimension1.setTheQuery(dimension1Query);
        lkpSubstituteDimension2.setTheQuery(dimension2Query);
        lkpSubstituteDimension3.setTheQuery(dimension3Query);
    }

    private void setupLookupRelations() {
        lookupRelationManager = new ItemMasterLookupRelationManager();

        dataRelation.setLookupRelationManager(lookupRelationManager);

        lookupRelationManager.addLookup(lkpSubstituteItem, "item");
        lookupRelationManager.addLookup(lkpSubstituteDimension1, "dimension1");
        lookupRelationManager.addLookup(lkpSubstituteDimension2, "dimension2");
        lookupRelationManager.addLookup(lkpSubstituteDimension3, "dimension3");

        lookupRelationManager.initializeLookups();
    }

    //This method is used to initialize the frame
    private void initFrame() {
        //Tabbed pane used on the form
        emcJTabbedPane tabbedPaneTop = new emcJTabbedPane();

        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new BorderLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        int y = 0;

        //Sets up the tabs on the form
        tabOverview();
        tabDefinition();
        tabPurchase();
        tabSales();
        tabCosting();
        tabSpecification();
        tabPlanning();
        tabClassification();
        tabProduction();
        initButtons();

        //Adds the tabs to the tabbed pane
        tabbedPaneTop.add(this.pnlOverview, "Overview");
        tabbedPaneTop.add(this.pnlDefinition, "Definition");
        tabbedPaneTop.add(this.pnlPurchase, "Purchase");
        tabbedPaneTop.add(this.pnlSales, "Sales");
        tabbedPaneTop.add(this.pnlCosting, "Costing");
        tabbedPaneTop.add(this.pnlSpecification, "Specification");
        tabbedPaneTop.add(this.pnlPlanning, "Planning");
        tabbedPaneTop.add(this.pnlClassification, "Classification");
        tabbedPaneTop.add(this.pnlProduction, "Production");

        thePanel.add(tabbedPaneTop, BorderLayout.CENTER);
        thePanel.add(pnlButtons, BorderLayout.EAST);
        this.add(thePanel);
    }

    //This method is used  to set up the overview tab
    private void tabOverview() {
        List keys = new ArrayList();
        keys.add("itemId");
        keys.add("itemReference");
        keys.add("description");
        keys.add("itemGroup");
        keys.add("itemType");
        keys.add("brandGroup");
        keys.add("itemRange");

        //keys.add("dimensionGroup");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);

        //Adds the item range lookup to the table
        toptable.setLookupCellEditor(6, itemRangeEditor);
        toptable.setComboBoxLookupToColumn(4, new InventoryType());
        toptable.setLookupCellEditor(3, this.itemGroupEditor);
        toptable.setLookupCellEditor(5, brandGroupEditor);

        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlOverview.setLayout(new GridLayout(1, 1));
        pnlOverview.add(topscroll);
        this.setTablePanel(topscroll);
    }

    //Sets up the definition tab
    private void tabDefinition() {
        //Sets the layout and border of the panel
        this.pnlDefinition.setLayout(new GridBagLayout());
        this.pnlDefinition.setBorder(javax.swing.BorderFactory.createTitledBorder("Definition"));

        //GridBagConstraints used for this tab
        GridBagConstraints gbc = new GridBagConstraints();

        //int used for layout
        int y = 0;

        //Left hand side panel
        emcJPanel pnlLeft = new emcJPanel();
        pnlLeft.setLayout(new GridBagLayout());

        //Right hand side panel
        emcJPanel pnlRight = new emcJPanel();

        //Increment y
        y++;

        //Status
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(lblStatus, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.NONE, GridBagConstraints.REMAINDER);
        pnlLeft.add(cmbStatus, gbc);

        //Increment y
        y++;

        //Blank line
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(new emcJLabel(), gbc);

        //Increment y
        y++;

        //Detailed description label
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        gbc.gridwidth = 2;
        pnlLeft.add(lblDetailedDescription, gbc);

        //Increment y
        y++;

        //Detailed description text area
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 0, y, 0.2, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, 2);
        gbc.ipady = 70;
        descPane.setPreferredSize(new Dimension(70, 70));
        pnlLeft.add(descPane, gbc);

        //Reset y
        y = 0;

        //Components used on the right hand side panel
        Component[][] components = new Component[][]{
            {this.lblBaseUOM, this.lkpBaseUOM},
            {this.lblDefaultWHSE, this.lkpDefaultWHSE},
            {new emcJLabel()},
            {this.lblDimentionGrpSetup, this.lkpDimGrpForm},
            {this.lblConfigGroup, this.lkpDimension1Group},
            {this.lblColourGroup, this.lkpDimension3Group},
            {this.lblSizeGroup, this.lkpDimension2Group}};

        //Creates the right hand side panel
        pnlRight = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.HORIZONTAL, false);

        //Resets gbc 
        gbc = new GridBagConstraints();

        //Resets y
        y = 0;

        //Left panel
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 0, y, 1, GridBagConstraints.FIRST_LINE_START,
                GridBagConstraints.NONE, 1);
        gbc.insets = new Insets(0, 10, 0, 0);
        pnlDefinition.add(pnlLeft, gbc);

        //Right panel
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 2, GridBagConstraints.LINE_START,
                GridBagConstraints.NONE, 1);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        pnlDefinition.add(pnlRight, gbc);

        //Increment y
        y++;

        //Ends the panel
        gbc = emcSetGridBagConstraints.endPanel(y);
        this.pnlDefinition.add(new emcJLabel(), gbc);
    }

    //Sets up the purchase tab
    private void tabPurchase() {
        EMCDatePickerFormComponent dpPurchasePrice = new EMCDatePickerFormComponent(dataRelation, "purchasePriceDate");


        EMCLookupFormComponent lkpOverseePurchaseOrderGroup = new EMCLookupFormComponent(new PurchaseOrderApprovalGroup(), dataRelation, "overseePurchaseGroup");
        lkpOverseePurchaseOrderGroup.setPopup(new EMCLookupPopup(new POPPurchaseOrderApprovalGroups(), "purchaseOrderApprovalGroupId", copyUD));



        //Left hand side controls
        Component[][] components = new Component[][]{
            {this.lblPurchasePrice, this.txtPurchasePrice, this.lblLastPurchaseDate, this.lastPurchaseDate},
            {new emcJLabel("Purchase Price Date"), dpPurchasePrice, this.lblLastPurchaseSupplier, this.txtLastPurchaseSupplier},
            {this.lblPurchasePer, this.txtPurchasePer, this.lblLastPurchasePrice, this.txtLastPurchasePrice},
            {this.lblPurchaseVatCode, this.lkpPurchaseVatCode, this.lblLastPurchasePer, this.txtLastPurchasePer},
            {this.lblPurchasePriceGroup, this.lkpPurchasePriceGroup, this.lblLastPurchaseDTime, this.txtLastPurchaseDTime},
            {this.lblPurchaseDiscountGroup, this.lkpPurchaseDiscountGroup, new emcJLabel(), new emcJLabel()},
            {this.lblPurchaseExtraChargeGroup, this.lkpPurchaseExtraChargeGroup, new emcJLabel(dataRelation.getFieldEmcLabel("minOrderQty")), this.txtMinOrderQty},
            {new emcJLabel(), new emcJLabel(), new emcJLabel(dataRelation.getFieldEmcLabel("multOrderQty")), this.txtMultOrderQty},
            {this.lblPurchaseSupplier, this.lkpPurchaseSupplierDefault, new emcJLabel(dataRelation.getFieldEmcLabel("maxOrderQty")), this.txtMaxOrderQty},
            {this.lblPurchaseLTime, this.txtPurchaseLTime, new emcJLabel(), new emcJLabel()},
            {this.lblPurchaseUOM, this.lkpPurchaseUOM, new emcJLabel(), new emcJLabel()},
            {this.lblPurchaseMinOrderQty, this.txtPurchaseMinOrderQty, new emcJLabel(), new emcJLabel()},
            {new emcJLabel(), new emcJLabel(), new emcJLabel(), new emcJLabel()},
            {this.lblStopPurchase, this.cbStopPurchase, new emcJLabel("Oversee Group"), lkpOverseePurchaseOrderGroup}};

        this.pnlPurchase = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true, "Purchase");
    }

    //Sets up the sales tab
    private void tabSales() {
        txtSellingPrice = new emcJTextField(new EMCDoubleFormDocument(dataRelation, "salesSellingPrice"));
        txtSalesPer = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "sellingPer"));
        txtSalesPriceUpdateDate = new EMCDatePickerFormComponent(dataRelation, "sellingPriceUpdateDate");
        txtSalesPriceUpdateDate.setEnabled(false);
        txtSalesMinOrderQty = new emcJTextField(new EMCDoubleFormDocument(dataRelation, "sellingMinOrderQty"));
        txtSalesCommissionGroup = new emcJTextField(new EMCStringFormDocument(dataRelation, "sellingCommissionGroup"));
        txtSalesRoyaltyGroup = new emcJTextField(new EMCStringFormDocument(dataRelation, "sellingRoyaltyGroup"));
        cmbwebStoreType = new EMCFormComboBox(TRECWebStoreTypeEnum.values(), dataRelation, "webStoreType");
        //Sets the layout and border of the panel;
        this.pnlSales.setLayout(new GridBagLayout());
        this.pnlSales.setBorder(javax.swing.BorderFactory.createTitledBorder("Sales"));
        EMCLookupFormComponent lkpProductGroup = new EMCLookupFormComponent(new InventoryProductGroupMenu(), dataRelation, "productGroup");
        lkpProductGroup.setPopup(new EMCLookupPopup(new InventoryProductGroup(), "productGroupId", copyUD));

        //GridBagConstraints used for this tab
        GridBagConstraints gbc = new GridBagConstraints();

        //int used for layout
        int y = 0;

        //Left hand side controls
        Component[][] components = new Component[][]{
            {this.lblSellingPrice, this.txtSellingPrice},
            {this.lblSalesPer, this.txtSalesPer},
            {this.lblSalesVatCode, this.lkpSalesVatCode},
            {new emcJLabel("Product Group"), lkpProductGroup},
            {this.lblSalesPriceGroup, this.lkpSalesPriceGroup},
            {this.lblSalesDiscountGroup, this.lkpSalesDiscountGroup},
            {this.lblSalesExtraChargeGroup, this.lkpSalesExtraChargeGroup},
            {new emcJLabel()},
            {this.lblSalesPriceUpdateDate, this.txtSalesPriceUpdateDate},
            {this.lblSalesUOM, this.lkpSalesUOM},
            {this.lblSalesMinOrderQty, this.txtSalesMinOrderQty},
            {this.lblWebStoreType, this.cmbwebStoreType}};

        //Creates a panel containing all the required information
        emcJPanel pnlLeft = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.HORIZONTAL, false);

        //Left
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 0, y, 0.1, GridBagConstraints.FIRST_LINE_START,
                GridBagConstraints.HORIZONTAL, 1);
        gbc.insets = new Insets(0, 10, 0, 0);
        pnlSales.add(pnlLeft, gbc);

        //Seperator label
        emcJLabel lblSeperator = new emcJLabel();
        lblSeperator.setPreferredSize(new Dimension(60, 85));

        //Sets the preferred size of lblCustomerRestricted
        this.lblCustomerRestricted.setPreferredSize(new Dimension(100, 25));

        emcYesNoComponent ynAllowKimbling = new emcYesNoComponent(dataRelation, "allowKimbling");

        //Right hand side controls
        components = new Component[][]{
                    {this.lblSalesCommissionGroup, this.txtSalesCommissionGroup},
                    //{this.lblSalesRoyaltyGroup, this.txtSalesRoyaltyGroup},
                    {this.lblCustomerRestricted, this.yncCustomerRestricted},
                    {lblSeperator},
                    {this.lblStopSelling, this.yncStopSelling},
                    {lblSeperator},
                    {new emcJLabel("Allow Kimbling"), ynAllowKimbling}};

        //Creates a panel containing all the required information
        emcJPanel pnlRight = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.HORIZONTAL, false);

        //Right panel
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.FIRST_LINE_END,
                GridBagConstraints.HORIZONTAL, 1);
        pnlSales.add(pnlRight, gbc);

        //Increments y
        y++;

        //Ends the panel
        gbc = emcSetGridBagConstraints.endPanel(y);
        this.pnlSales.add(new emcJLabel(), gbc);
    }

    //Sets up the costing tab
    private void tabCosting() {
        //Sets the layout and border of the panel
        this.pnlCosting.setLayout(new GridBagLayout());
        this.pnlCosting.setBorder(javax.swing.BorderFactory.createTitledBorder("Costing"));

        //GridBagConstraints used for this tab
        GridBagConstraints gbc = new GridBagConstraints();

        //int used for layout
        int y = 0;


        //Left hand side controls
        Component[][] components = new Component[][]{
            {this.lblCurrentCost, this.txtCurrentCost},
            {this.lblCostDate, this.CostDate},
            {new emcJLabel()},
            {this.lblPrevCost, this.txtPrevCost},
            {this.lblPrevCostDate, this.prevCostDate},
            {new emcJLabel()},
            {this.lblWeightedAve, this.txtWeightedAve},
            {this.lblCostingGroup, this.lkpCostingGroup}};

        //Left hand side panel
        emcJPanel pnlLeft = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, false);

        //Left panel
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 0, y, 0.1, GridBagConstraints.FIRST_LINE_START,
                GridBagConstraints.HORIZONTAL, 1);
        gbc.insets = new Insets(0, 10, 0, 0);
        pnlCosting.add(pnlLeft, gbc);

        //Adds a blank label to the right hand side of the tab
        emcJLabel lblBlank = new emcJLabel();
        lblBlank.setSize(pnlLeft.getWidth(), 10);
        pnlCosting.add(lblBlank);

        //Increment y
        y++;

        //Ends the panel
        gbc = emcSetGridBagConstraints.endPanel(y);
        this.pnlCosting.add(new emcJLabel(), gbc);
    }

    //Sets up the specification tab
    private void tabSpecification() {
        //Sets the layout and border of the panel
        this.pnlSpecification.setLayout(new GridBagLayout());
        this.pnlSpecification.setBorder(javax.swing.BorderFactory.createTitledBorder("Specification"));

        //GridBagConstraints used for this tab
        GridBagConstraints gbc = new GridBagConstraints();

        //int used for layout
        int y = 0;

        //Insets
        gbc.insets = new Insets(0, 10, 0, 0);

        //Batch group
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 0, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 2);
        pnlSpecification.add(lblBatchGroup, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 2, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, 2);
        pnlSpecification.add(txtBatchGroup, gbc);

        //Increment y
        y++;

        //Warranty/Expiry Group
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 0, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 2);
        pnlSpecification.add(lblWarrantyExpiryGroup, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 2, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, 2);
        pnlSpecification.add(txtWarrantyExpiryGroup, gbc);

        //Increment y
        y++;

        //Bar Code Group
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 0, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 2);
        pnlSpecification.add(lblBarCodeGroup, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 2, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, 2);
        pnlSpecification.add(txtBarCodeGroup, gbc);

        //Increment y
        y++;

        //Blank row
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 0, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 1);
        pnlSpecification.add(new emcJLabel(), gbc);

        //Increment y
        y++;

        //Width
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 0, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 1);
        pnlSpecification.add(lblWidth, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 1);
        pnlSpecification.add(txtWidth, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 2, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 1);
        pnlSpecification.add(lblWidthUOM, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 3, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, 1);
        pnlSpecification.add(lkpWidthUOM, gbc);

        //Increment y
        y++;

        //Length
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 0, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 1);
        pnlSpecification.add(lblLength, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 1);
        pnlSpecification.add(txtLength, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 2, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 1);
        pnlSpecification.add(lblLengthUOM, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 3, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, 1);
        pnlSpecification.add(lkpLengthUOM, gbc);

        //Increment y
        y++;

        //Height
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 0, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 1);
        pnlSpecification.add(lblHeight, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 1);
        pnlSpecification.add(txtHeight, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 2, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 1);
        pnlSpecification.add(lblHeightUOM, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 3, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, 1);
        pnlSpecification.add(lkpHeightUOM, gbc);

        //Increment y
        y++;

        //Weight
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 0, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 1);
        pnlSpecification.add(lblWeight, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 1);
        pnlSpecification.add(txtWeight, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 2, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 1);
        pnlSpecification.add(lblWeightUOM, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 3, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, 1);
        pnlSpecification.add(lkpWeightUOM, gbc);

        //GridbagConstraints for ending the final row
        gbc = emcSetGridBagConstraints.endRow(4, y);

        //End the final row
        this.pnlSpecification.add(new emcJLabel(), gbc);

        //Increment y
        y++;

        //Ends the panel
        gbc = emcSetGridBagConstraints.endPanel(y);
        pnlSpecification.add(new emcJLabel(), gbc);
    }

    //Sets up the planning tab
    private void tabPlanning() {
        //Sets the layout and border of the panel
        this.pnlPlanning.setLayout(new GridBagLayout());
        this.pnlPlanning.setBorder(javax.swing.BorderFactory.createTitledBorder("Planning"));
        emcJTextField txtSafetyStock = new emcJTextField(new EMCBigDecimalFormDocument((dataRelation), "safetyStock"));
        emcJTextField txtEconomicOrderQty = new emcJTextField(new EMCBigDecimalFormDocument((dataRelation), "economicOrderQuantity"));

        //GridBagConstraints used for this tab
        GridBagConstraints gbc = new GridBagConstraints();

        //int used for layout
        int y = 0;

        //Insets
        gbc.insets = new Insets(0, 10, 0, 0);

        //Left hand side controls
        Component[][] components = new Component[][]{
            {new emcJLabel()},
            {this.lblAllowSubstitute, this.yncAllowSubstitute},
            {this.lblSubstituteRule, this.cmbSubstituteRule},
            {this.lblSubstituteItem, this.lkpSubstituteItem},
            {this.lblSubstituteDimension1, this.lkpSubstituteDimension1},
            {this.lblSubstituteDimension3, this.lkpSubstituteDimension3},
            {this.lblSubstituteDimension2, this.lkpSubstituteDimension2},
            {new emcJLabel(dataRelation.getColumnName("safetyStock")), txtSafetyStock},
            {new emcJLabel(dataRelation.getColumnName("economicOrderQuantity")), txtEconomicOrderQty}};

        //Left hand side panel
        emcJPanel pnlLeft = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, false);

        //Left panel
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 0, y, 0.1, GridBagConstraints.FIRST_LINE_START,
                GridBagConstraints.HORIZONTAL, 1);
        pnlPlanning.add(pnlLeft, gbc);

        //Adds a blank label to the right hand side of the tab
        emcJLabel lblBlank = new emcJLabel();
        lblBlank.setSize(pnlLeft.getWidth(), 10);
        pnlPlanning.add(lblBlank);

        //Increment y
        y++;

        //Ends the panel
        gbc = emcSetGridBagConstraints.endPanel(y);
        this.pnlPlanning.add(new emcJLabel(), gbc);
    }

    //Sets up the classification tab
    private void tabClassification() {
        //Sets the layout and border of the panel
        this.pnlClassification.setLayout(new GridBagLayout());
        this.pnlClassification.setBorder(javax.swing.BorderFactory.createTitledBorder("Classification"));

        //GridBagConstraints used for this tab
        GridBagConstraints gbc = new GridBagConstraints();

        //int used for layout
        int y = 0;

        //Insets
        gbc.insets = new Insets(0, 10, 0, 0);

        //Left hand side controls
        Component[][] components = new Component[][]{
            {this.lblClassGroup1, this.lkpClassGroup1},
            {this.lblClassGroup2, this.lkpClassGroup2},
            {this.lblClassGroup3, this.lkpClassGroup3},
            {this.lblClassGroup4, this.lkpClassGroup4},
            {this.lblClassGroup5, this.lkpClassGroup5},
            {this.lblClassGroup6, this.lkpClassGroup6}};

        //Left hand side panel
        emcJPanel pnlLeft = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, false);

        //Left panel
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 0, y, 0.1, GridBagConstraints.FIRST_LINE_START,
                GridBagConstraints.HORIZONTAL, 1);
        pnlClassification.add(pnlLeft, gbc);

        //Adds a blank label to the right hand side of the tab
        emcJLabel lblBlank = new emcJLabel();
        lblBlank.setSize(pnlLeft.getWidth(), 10);
        pnlClassification.add(lblBlank);

        //Increment y
        y++;

        //Ends the panel
        gbc = emcSetGridBagConstraints.endPanel(y);
        this.pnlClassification.add(new emcJLabel(), gbc);
    }

    //Sets up the production tab
    private void tabProduction() {

        txtProdLTime = new emcJTextField(new EMCDoubleFormDocument(dataRelation, "productionLeadTime"));


        //Sets the layout and border of the panel
        this.pnlProduction.setLayout(new GridBagLayout());
        this.pnlProduction.setBorder(javax.swing.BorderFactory.createTitledBorder("Production"));

        //GridBagConstraints used for this tab
        GridBagConstraints gbc = new GridBagConstraints();

        //Int used for layout
        int y = 0;

        //Left hand side controls
        Component[][] components = new Component[][]{
            {this.lblStdCost, this.txtStdCost},
            {this.lblStdCostDate, this.txtStdCostDate},
            {new emcJLabel()},
            {this.lblProdLTime, this.txtProdLTime},
            {this.lblMinProdQty, this.txtMinProdQty},
            {this.lblStopProduction, this.yncStopProduction}};

        //Left hand side panel
        emcJPanel pnlLeft = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.HORIZONTAL, false);

        //Left panel
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 0, y, 0.1, GridBagConstraints.FIRST_LINE_START,
                GridBagConstraints.HORIZONTAL, 1);
        gbc.insets = new Insets(0, 10, 0, 0);
        pnlProduction.add(pnlLeft, gbc);

        //Label used for spacing
        emcJLabel lblSpacing = new emcJLabel();
        lblSpacing.setPreferredSize(new Dimension(10, 35));

        //Left hand side controls
        components = new Component[][]{
                    {this.lblScrap, this.txtScrap},
                    {this.lbldrawingPattern, this.txtdrawingPattern},
                    {this.lblProdRef1, this.txtProdRef1},
                    {this.lblProdRef2, this.txtProdRef2},
                    {lblSpacing},
                    {this.lblPhantom, this.txtPhantom},
                    {this.lblBinItem, this.txtBinItem}};

        //Right hand side panel
        emcJPanel pnlRight = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.HORIZONTAL, false);

        //Right panel
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.FIRST_LINE_END,
                GridBagConstraints.HORIZONTAL, 1);
        gbc.insets = new Insets(0, 10, 0, 0);
        pnlProduction.add(pnlRight, gbc);

        //Increment y
        y++;

        //Ends the panel
        gbc = emcSetGridBagConstraints.endPanel(y);
        this.pnlProduction.add(new emcJLabel(), gbc);
    }

    private void initButtons() {
        pnlButtons.setLayout(new GridBagLayout());
        pnlButtons.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        int y = 0;
        GridBagConstraints localg;
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;
        emcMenuButtonList buttonList = new emcMenuButtonList("Dimensions", this) {

            @Override
            public void executeCmd(String theCmd) {
                if (theCmd.equals("Config") && Functions.checkBlank(dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), "dimension1Group"))) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Config is not active on the selected item.", dataRelation.getUserData());
                    return;
                } else {
                    if (theCmd.equals("Colour") && Functions.checkBlank(dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), "dimension3Group"))) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Colour is not active on the selected item.", dataRelation.getUserData());
                        return;
                    } else {
                        if (theCmd.equals("Size") && Functions.checkBlank(dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), "dimension2Group"))) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Size is not active on the selected item.", dataRelation.getUserData());
                            return;
                        } else {
                            if (theCmd.equals("Generate Comb.")) {
                                emcDataRelationManagerUpdate dataRelation = ItemMasterForm.this.dataRelation;
                                int lastRow = dataRelation.getLastRowAccessed();
                                Object itemId = dataRelation.getFieldValueAt(lastRow, "itemId");
                                Object description = dataRelation.getFieldValueAt(lastRow, "description");

                                EMCUserData formUserData = ItemMasterForm.this.getUserData();

                                if (itemId != null && description != null) {
                                    String message;

                                    EMCCommandClass checkCmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.CHECK_COMBINATIONS_EXIST.toString());
                                    List toSend = new ArrayList();
                                    toSend.add(itemId);

                                    List ret = EMCWSManager.executeGenericWS(checkCmd, toSend, getUserData());

                                    if (ret != null && ret.size() > 1 && (Boolean) ret.get(1)) {
                                        message = "Combinations already exist for the given item.  Inactive dimensions on Item Dimension Setup will be deleted.  \nRegenerate combinations?";
                                    } else {
                                        message = "Inactive dimensions on Item Dimension Setup will be deleted.  Generate Combinations?";
                                    }

                                    emcDialogue dialog = new emcDialogue("Are You Sure", message, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
                                    if (dialog.getDialogueResult() == 0) {
                                        EMCCommandClass ccmd = new EMCCommandClass();
                                        ccmd.setCommandId(EMCCommands.SERVER_GENERAL_COMMAND.getId());
                                        ccmd.setModuleNumber(enumEMCModules.INVENTORY.getId());
                                        ccmd.setMethodId(ServerInventoryMethods.GENERATE_ITEM_DIM_COMBINATIONS.toString());

                                        toSend = new ArrayList();
                                        toSend.add(itemId);

                                        ret = EMCWSManager.executeGenericWS(ccmd, toSend, formUserData);

                                        if ((Boolean) ret.get(1)) {
                                            //Open combinations form
                                            super.executeCmd("Combinations");
                                        }
                                    }
                                }
                            } else {
                                super.executeCmd(theCmd);
                            }
                        }
                    }
                }
            }
        };

        buttonList.addMenuItem(
                "Config", new emc.menus.inventory.menuitems.display.ItemDimension1Setup(), 0, false);
        buttonList.addMenuItem(
                "Colour", new emc.menus.inventory.menuitems.display.ItemDimension3Setup(), 2, false);
        buttonList.addMenuItem(
                "Size", new emc.menus.inventory.menuitems.display.ItemDimension2Setup(), 1, false);
        buttonList.addMenuItem(
                "Combinations", new emc.menus.inventory.menuitems.display.ItemDimensionCombinations(), 3, false);
        buttonList.addMenuItem(
                "Generate Comb.", new emc.menus.inventory.menuitems.display.ItemDimensionCombinations(), 5, false);
        pnlButtons.add(buttonList, localg);
        y++;
        //  localg = emcSetGridBagConstraints.createStandard(0,y,1.0,GridBagConstraints.LINE_START);
        //  localg.fill = GridBagConstraints.HORIZONTAL;
        //  pnlButtons.add(new GenerateCombinationsButton(getUserData(), dataRelation, 3), localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;

        pnlButtons.add(new emcMenuButton("Item Reference", new emc.menus.inventory.menuitems.display.Reference(), this, 4, false), localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;

        pnlButtons.add(new emcStockButton(this, false, new boolean[]{false,
                    true, true
                }), localg);
      
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;
        emcMenuButtonList btnPriceList = new emcMenuButtonList("Price List", this);
        btnPriceList.addMenuItem("Selling", new SOPPriceArangementItemMenu(), 6, false);
        btnPriceList.addMenuItem("Purchasing", new POPItemPriceArrangementsMI(), 17, false);
        pnlButtons.add(btnPriceList, localg);

        EMCMenuItem discountSetupItem = new DiscountSetupMI();
        emcMenuButtonList discountButton = new emcMenuButtonList("Discounts", this);
        discountButton.addMenuItem("Item", discountSetupItem, 7, false);
        discountButton.addMenuItem("All Appl.", discountSetupItem, 8, false);

        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;
        pnlButtons.add(discountButton, localg);

        y++;
        pnlButtons.add(
                new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
        pnlButtons.setPreferredSize(
                new Dimension(120, 25));
    }
}

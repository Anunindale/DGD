/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.purchaseorders;

import emc.app.components.pop.purchaseorders.PurchaseOrderTypeDropDown;
import emc.app.components.documents.EMCDoubleFormDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcMenuButton;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.inventory.emcStockButton;
import emc.app.components.inventory.lookups.itemreference.ItemReferencePopup;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.components.queryswitchbutton.EMCQuerySwitchButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.inventory.dimensions.InventoryItemDimension1Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension2Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension3Setup;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.pop.POPPurchaseOrderApprovalGroups;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.enums.pop.purchaseorders.PurchaseOrderCancelledStatus;
import emc.enums.pop.purchaseorders.PurchaseOrderStatus;
import emc.forms.pop.display.purchaseorders.resources.ApprovalButton;
import emc.forms.pop.display.purchaseorders.resources.CancelButton;
import emc.forms.pop.display.purchaseorders.resources.CancelLineButton;
import emc.forms.pop.display.purchaseorders.resources.CostChangeButton;
import emc.forms.pop.display.purchaseorders.resources.EnquiryButton;
import emc.forms.pop.display.purchaseorders.resources.PostingButtonList;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCMenuItem;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.pop.menuitems.display.MillPurchaseOrders;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author riaan
 */
public class PurchaseOrderForm extends BaseInternalFrame {

    private EMCUserData copyUD;
    private EMCUserData linesUD;
    private EMCUserData masterData;
    private PurchaseOrderMasterDataRelationManager masterDRM;
    private PurchaseOrderLinesDRM linesDRM;
    private PurchaseOrderLinesLookupRelationManager lookupRelationManager;
    // <editor-fold defaultstate="collapsed" desc="Master Overview Tab">
    private emcJTableUpdate tblMaster;
    private EMCLookupJTableComponent lkpTableSupplier;
    private EMCLookupTableCellEditor edtTableSupplier;
    private EMCLookupJTableComponent lkpTableCurrency;
    private EMCLookupTableCellEditor edtTableCurrency;
    private EMCLookupJTableComponent lkpTableApprovalGroup;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Master Additional Tab"> 
    private emcJLabel lblContactPerson = new emcJLabel("Contact person");
    private emcJTextField txtContactPerson = new emcJTextField();
    private emcJLabel lblContactTelNo = new emcJLabel("Contact No");
    private emcJTextField txtContactTelNo = new emcJTextField();
    private emcJLabel lblContactEmail = new emcJLabel("Contact Email");
    private emcJTextField txtContactEmail = new emcJTextField();
    private emcJLabel lblVatCode = new emcJLabel("VAT Code");
    private EMCLookupFormComponent lkpVatCode;
    //private emcJLabel lblVatGroup = new emcJLabel("VAT Group");
    //private emcLookupFormComponent lkpVatGroup;
    private emcJLabel lblVatApplicable;
    private emcYesNoComponent ysnVatApplicable;
    //private emcJLabel lblBuyerGroup = new emcJLabel("Buyer Group");
    private EMCLookupFormComponent lkpBuyerGroup;
    private emcJLabel lblWarehouse = new emcJLabel("Warehouse");
    private EMCLookupFormComponent lkpWarehouse;
    private emcJLabel lblOrderedBy = new emcJLabel("Ordered By");
    private EMCLookupFormComponent lkpOrderedBy;
    private emcJLabel lblApprovedBy = new emcJLabel("Approved By");
    private EMCLookupFormComponent lkpApprovedBy;
    private emcJLabel lblSupplierOrderNumber;
    private emcJTextField txtSupplierOrderNumber = new emcJTextField();
    private emcJLabel lblExternalRef;
    private emcJTextField txtExternalRef = new emcJTextField();
    private emcJLabel lblExternalRefType;
    private emcJTextField txtExternalRefType = new emcJTextField();
    private emcJLabel lblInternalRef;
    private emcJTextField txtInternalRef = new emcJTextField();
    private emcJLabel lblInternalRefType;
    private emcJTextField txtInternalRefType = new emcJTextField();
    private emcJLabel lblBlanketOrderRef;
    private emcJTextField txtBlanketOrderRef = new emcJTextField();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Master Delivery Tab">
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
    private emcJLabel lblRequestedDeliveryDate = new emcJLabel("Requested Delivery Date");
    private EMCDatePickerFormComponent requestedDeliveryDate;
    private emcJLabel lblLatestDeliveryDate = new emcJLabel("Latest Delivery Date");
    private EMCDatePickerFormComponent latestDeliveryDate;
    private emcJLabel lblConfirmedDeliveryDate = new emcJLabel("Confirmed Delivery Date");
    private EMCDatePickerFormComponent confirmedDeliveryDate;
    private emcJLabel lblActualDeliveryDate = new emcJLabel("Actual Delivery Date");
    private EMCDatePickerFormComponent actualDeliveryDate;
    private emcJLabel lblDeliveryMode = new emcJLabel("Delivery Mode");
    private EMCLookupFormComponent lkpDeliveryMode;
    private emcJLabel lblDeliveryTerms = new emcJLabel("Delivery Terms");
    private EMCLookupFormComponent lkpDeliveryTerms;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Master Payment Tab">
    private emcJLabel lblPayment = new emcJLabel("Payment");
    private emcJTextField txtPayment = new emcJTextField();
    private emcJLabel lblPaymentDueDate = new emcJLabel("Payment Due Date");
    private EMCDatePickerFormComponent paymentDueDate;
    private emcJLabel lblMethodOfPayment = new emcJLabel("Method of Payment");
    private emcJTextField txtMethodOfPayment = new emcJTextField();
    private emcJLabel lblPriceGroup = new emcJLabel("Price Group");
    private EMCLookupFormComponent lkpPriceGroup;
    private emcJLabel lblTotalDiscPercentage = new emcJLabel("Tot Disc Percentage");
    private emcJTextField txtTotalDiscPercentage = new emcJTextField();
    private emcJLabel lblLinesDiscount = new emcJLabel("Lines discount");
    private emcJTextField txtLinesDiscount = new emcJTextField();
    private emcJLabel lblMiscChargesGroup = new emcJLabel("Misc Charges Group");
    private EMCLookupFormComponent lkpMiscChargesGroup;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Master Quality Tab">
    private EMCLookupFormComponent lkpQualityTestType;
    private emcYesNoComponent qualityTestReq;
    private emcYesNoComponent qualityReportReq;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Master Comments Tab">
    private emcJTextArea txaMasterComments = new emcJTextArea();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Lines Overview Tab">
    private emcJTableUpdate tblLines;
    private EMCLookupJTableComponent lkpTableUom;
    private EMCLookupTableCellEditor edtTableUom;
    private EMCLookupJTableComponent lkpTableItemId;
    private EMCLookupTableCellEditor edtTableItemId;
    private EMCLookupJTableComponent lkpTableItemDimension1;
    private EMCLookupTableCellEditor edtTableItemDimension1;
    private EMCLookupJTableComponent lkpTableItemDimension2;
    private EMCLookupTableCellEditor edtTableItemDimension2;
    private EMCLookupJTableComponent lkpTableItemDimension3;
    private EMCLookupTableCellEditor edtTableItemDimension3;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Lines Additional Tab">
    private emcJLabel lblLinesWarehouse = new emcJLabel("Warehouse");
    private EMCLookupFormComponent lkpLinesWarehouse;
    //private emcJLabel lblLinesBatch = new emcJLabel("Batch No");
    //private EMCLookupFormComponent lkpLinesBatch;
    //private emcJLabel lblLinesSerial = new emcJLabel("Serial No");
    //private EMCLookupFormComponent lkpLinesSerial;
    private emcJLabel lblLinesVatCode = new emcJLabel("VAT Code");
    private EMCLookupFormComponent lkpLinesVatCode;
    private emcJLabel lblLinesDeliveryDate = new emcJLabel("Delivery Date");
    private EMCDatePickerFormComponent linesDeliveryDate;
    private emcJLabel lblLinesConfirmedDate = new emcJLabel("Confirmed Date");
    private EMCDatePickerFormComponent linesConfirmedDate;
    private emcJTextArea txaComments = new emcJTextArea();
    private EMCLookupFormComponent lkpReason;
    private emcJLabel lblReason = new emcJLabel("Cost Ch. Reason");
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Lines Item Tab">
    //private emcJLabel lblLinesItemName = new emcJLabel("Item Name");
    //private emcJTextField txtLinesItemName = new emcJTextField();
    private emcJLabel lblLinesItemPrice = new emcJLabel("Item Price");
    private emcJTextField txtLinesItemPrice = new emcJTextField();
    private emcJLabel lblLinesItemDimension1 = new emcJLabel("Item Config");
    private EMCLookupFormComponent lkpLinesItemDimension1;
    private emcJLabel lblLinesItemDimension3 = new emcJLabel("Item Colour");
    private EMCLookupFormComponent lkpLinesItemDimension3;
    private emcJLabel lblLinesItemDimension2 = new emcJLabel("Item Size");
    private EMCLookupFormComponent lkpLinesItemDimension2;
    private emcJLabel lblLinesTransactionNumber;
    private emcJTextField txtLinesTransactionNumber = new emcJTextField();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Lines Quantity Tab">
    private emcJLabel lblLinesQuantity = new emcJLabel("Quantity");
    private emcJTextField txtLinesQuantity = new emcJTextField();
    private emcJLabel lblLinesUnitOfMeasure = new emcJLabel("Unit of Measure");
    private EMCLookupFormComponent lkpLinesUnitOfMeasure;
    private emcJLabel lblLinesItemsInvoiced = new emcJLabel("Qty Invoiced");
    private emcJTextField txtLinesItemsInvoiced = new emcJTextField();
    private emcJLabel lblLinesItemsReceived = new emcJLabel("Qty Received");
    private emcJTextField txtLinesItemsReceived = new emcJTextField();
    private emcJLabel lblLinesItemsRemaining = new emcJLabel("Qty Outstanding");
    private emcJTextField txtLinesItemsRemaining = new emcJTextField();
    private emcJLabel lblLinesReceiveNow = new emcJLabel("Receive Now");
    private emcJTextField txtLinesReceiveNow = new emcJTextField();
    private emcJLabel lblLinesOverRecQty;
    private emcJTextField txtLinesOverRecQty = new emcJTextField();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Buttons">
    private EMCQuerySwitchButton querySwitchButton;
    // </editor-fold>
    //Opened from Aggregate Works Order enquiry.  Disable buttons.
    private boolean enableActionButtons = true;

    /**
     * Creates a new instance of PurchaseOrderForm
     */
    public PurchaseOrderForm(EMCUserData userData) {
        super("Purchase Orders", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 600);
        //this.setHelpFile(new emcHelpFile(".html"));

        //Passed from Aggregate Works Order form.
        if (userData.getUserData(3) instanceof Boolean) {
            this.enableActionButtons = (Boolean) userData.getUserData(3);
        }

        try {
            copyUD = userData.copyUserData();
            linesUD = userData.copyUserData();
            masterData = userData.copyUserDataAndDataList();
            masterDRM = new PurchaseOrderMasterDataRelationManager(new emcGenericDataSourceUpdate(
                    enumEMCModules.POP.getId(),
                    new emc.entity.pop.POPPurchaseOrderMaster(), masterData), masterData);
            this.setDataManager(masterDRM);

            StockDRMParameters param = new StockDRMParameters("itemId", "itemDimension1", "itemDimension2", "itemDimension3", "transactionNumber");
            linesDRM = new PurchaseOrderLinesDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.POP.getId(),
                    new emc.entity.pop.datasource.POPPurchaseOrderLinesDS(), linesUD), param, linesUD);

            masterDRM.setHeaderColumnID("purchaseOrderId");
            masterDRM.setTheForm(this);
            masterDRM.setFormTextId1("purchaseOrderId");
            masterDRM.setFormTextId2("supplier");
            masterDRM.setLinesTable(linesDRM);

            linesDRM.setTheForm(this);
            linesDRM.setFormTextId1("purchaseOrderId");
            linesDRM.setFormTextId2("lineNo");
            linesDRM.setHeaderTable(masterDRM);
            linesDRM.setRelationColumnToHeader("purchaseOrderId");

            createLabels();
            setDocuments();
            setupLookups();
            setupLookupRelations();

            makeComponentsUneditable();

            initButtons();

            initFrame();

            //Ensure that cancel buttons are in the correct state
            masterDRM.doRelation(0);
            linesDRM.doRelation(0);
        } catch (Exception ex) {
            if (EMCDebug.getDebug()) {
                ex.printStackTrace();
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Failed to construct PurchaseOrderForm", userData);
            }
        }
    }

    /**
     * Creates labels.
     */
    private void createLabels() {
        this.lblSupplierOrderNumber = new emcJLabel(masterDRM.getFieldEmcLabel("supplierOrderNumber"));
        this.lblVatApplicable = new emcJLabel(masterDRM.getFieldEmcLabel("vatApplicable"));
        this.lblExternalRef = new emcJLabel(masterDRM.getFieldEmcLabel("externalRef"));
        this.lblInternalRef = new emcJLabel(masterDRM.getFieldEmcLabel("internalRef"));
        this.lblInternalRefType = new emcJLabel(masterDRM.getFieldEmcLabel("internalRefType"));
        this.lblBlanketOrderRef = new emcJLabel(masterDRM.getFieldEmcLabel("blanketOrderRef"));
        this.lblLinesTransactionNumber = new emcJLabel(linesDRM.getFieldEmcLabel("transactionNumber"));
        this.lblLinesOverRecQty = new emcJLabel(linesDRM.getFieldEmcLabel("overReceiveQty"));
        this.lblExternalRefType = new emcJLabel(masterDRM.getColumnName("externalRefType"));
    }

    private void setDocuments() {
        //Master
        txtContactPerson.setDocument(new EMCStringFormDocument(masterDRM, "contactPerson"));
        txtContactTelNo.setDocument(new EMCStringFormDocument(masterDRM, "contactNo"));
        txtContactEmail.setDocument(new EMCStringFormDocument(masterDRM, "contactEmail"));
        txtAddressLine1.setDocument(new EMCStringFormDocument(masterDRM, "addressLine1"));
        txtAddressLine2.setDocument(new EMCStringFormDocument(masterDRM, "addressLine2"));
        txtAddressLine3.setDocument(new EMCStringFormDocument(masterDRM, "addressLine3"));
        txtAddressLine4.setDocument(new EMCStringFormDocument(masterDRM, "addressLine4"));
        txtAddressLine5.setDocument(new EMCStringFormDocument(masterDRM, "addressLine5"));
        txtPayment.setDocument(new EMCDoubleFormDocument(masterDRM, "payment"));
        txtMethodOfPayment.setDocument(new EMCStringFormDocument(masterDRM, "methodOfPayment"));
        txtTotalDiscPercentage.setDocument(new EMCDoubleFormDocument(masterDRM, "totalDiscountPercentage"));
        txtLinesDiscount.setDocument(new EMCDoubleFormDocument(masterDRM, "linesDiscount"));
        txtSupplierOrderNumber.setDocument(new EMCStringFormDocument(masterDRM, "supplierOrderNumber"));
        txtExternalRef.setDocument(new EMCStringFormDocument(masterDRM, "externalRef"));
        txtBlanketOrderRef.setDocument(new EMCStringFormDocument(masterDRM, "blanketOrderRef"));

        txaMasterComments.setDocument(new EMCStringFormDocument(masterDRM, "comments"));
        //Lines
        txaComments.setDocument(new EMCStringFormDocument(linesDRM, "comments"));
        txtLinesItemPrice.setDocument(new EMCDoubleFormDocument(linesDRM, "itemCost"));
        //txtLinesItemName.setDocument(new EMCStringFormDocument(linesDRM, "itemName"));
        txtLinesQuantity.setDocument(new EMCDoubleFormDocument(linesDRM, "quantity"));
        txtLinesItemsInvoiced.setDocument(new EMCDoubleFormDocument(linesDRM, "itemsInvoiced"));
        txtLinesItemsReceived.setDocument(new EMCDoubleFormDocument(linesDRM, "itemsReceived"));
        linesDRM.setQtyOutstanding(txtLinesItemsRemaining);
        txtLinesReceiveNow.setDocument(new EMCDoubleFormDocument(linesDRM, "receiveNow"));

        txtLinesTransactionNumber.setDocument(new EMCStringFormDocument(linesDRM, "transactionNumber"));

        txtLinesOverRecQty.setDocument(new EMCDoubleFormDocument(linesDRM, "overReceiveQty"));

        txtExternalRefType.setDocument(new EMCStringFormDocument(masterDRM, "externalRefType"));

        txtInternalRef.setDocument(new EMCStringFormDocument(masterDRM, "internalRef"));
        txtInternalRefType.setDocument(new EMCStringFormDocument(masterDRM, "internalRefType"));
    }

    private void setupLookups() {
        ysnVatApplicable = new emcYesNoComponent(masterDRM, "vatApplicable");

        requestedDeliveryDate = new EMCDatePickerFormComponent(masterDRM, "requestedDeliveryDate");
        latestDeliveryDate = new EMCDatePickerFormComponent(masterDRM, "latestExpectedDeliveryDate");
        confirmedDeliveryDate = new EMCDatePickerFormComponent(masterDRM, "confirmedDeliveryDate");
        actualDeliveryDate = new EMCDatePickerFormComponent(masterDRM, "actualDeliveryDate");
        paymentDueDate = new EMCDatePickerFormComponent(masterDRM, "paymentDueDate");

        linesDeliveryDate = new EMCDatePickerFormComponent(linesDRM, "deliveryDate");
        linesConfirmedDate = new EMCDatePickerFormComponent(linesDRM, "confirmedDate");

        actualDeliveryDate.setEnabled(false);

        //Delivery Mode
        List<String> deliveryModeKeys = new ArrayList<String>();
        deliveryModeKeys.add("deliveryModeId");
        deliveryModeKeys.add("description");

        lkpDeliveryMode = new EMCLookupFormComponent(new emc.menus.pop.menuitems.display.DeliveryModes(), masterDRM, "modeOfDelivery", "deliveryModeId");
        EMCLookupPopup deliveryModePopup = new EMCLookupPopup(enumEMCModules.POP.getId(), new emc.entity.pop.POPDeliveryModes(),
                                                              "deliveryModeId", deliveryModeKeys, copyUD);
        lkpDeliveryMode.setPopup(deliveryModePopup);
        masterDRM.setDocument(lkpDeliveryMode.getDocument());

        //Delivery Terms
        List<String> deliveryTermsKeys = new ArrayList<String>();
        deliveryTermsKeys.add("deliveryTermsId");
        deliveryTermsKeys.add("description");

        lkpDeliveryTerms = new EMCLookupFormComponent(new emc.menus.pop.menuitems.display.DeliveryTerms(), masterDRM, "deliveryTerms", "deliveryTermsId");
        EMCLookupPopup deliveryTermsPopup = new EMCLookupPopup(enumEMCModules.POP.getId(), new emc.entity.pop.POPDeliveryTerms(),
                                                               "deliveryTermsId", deliveryTermsKeys, copyUD);
        lkpDeliveryTerms.setPopup(deliveryTermsPopup);
        masterDRM.setDocument(lkpDeliveryTerms.getDocument());

        //Employee keys
        List<String> employeeKeys = new ArrayList<String>();
        employeeKeys.add("employeeNumber");
        employeeKeys.add("surname");
        employeeKeys.add("forenames");
        employeeKeys.add("emailAddress");
        employeeKeys.add("internalPhoneNumber");

        EMCMenuItem empItem = new emc.menus.base.menuItems.display.employees();

        lkpOrderedBy = new EMCLookupFormComponent(empItem, masterDRM, "orderedBy", "employeeNumber");
        EMCLookupPopup empPopup = new EMCLookupPopup(enumEMCModules.BASE.getId(), new emc.entity.base.BaseEmployeeTable(),
                                                     "employeeNumber", employeeKeys, copyUD);
        lkpOrderedBy.setPopup(empPopup);
        masterDRM.setDocument(lkpOrderedBy.getDocument());

        lkpApprovedBy = new EMCLookupFormComponent(empItem, masterDRM, "approvedBy");
        lkpApprovedBy.setPopup(empPopup);
        masterDRM.setDocument(lkpApprovedBy.getDocument());
        lkpApprovedBy.setEnabled(false);

        //Warehouse lookup
        List<String> warehouseKeys = new ArrayList<String>();
        warehouseKeys.add("warehouseId");
        warehouseKeys.add("description");

        EMCMenuItem warehouseItem = new emc.menus.inventory.menuitems.display.Warehouse();

        lkpWarehouse = new EMCLookupFormComponent(warehouseItem, masterDRM, "warehouse", "warehouseId");
        EMCLookupPopup warehousePopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryWarehouse(),
                                                           "warehouseId", warehouseKeys, copyUD);
        lkpWarehouse.setPopup(warehousePopup);
        masterDRM.setDocument(lkpWarehouse.getDocument());

        //Master supplier
        List supplierKeys = new ArrayList<String>();
        supplierKeys.add("supplierId");
        supplierKeys.add("supplierName");

        lkpTableSupplier = new EMCLookupJTableComponent(new emc.menus.pop.menuitems.display.Suppliers());
        EMCLookupPopup supplierPopup = new EMCLookupPopup(enumEMCModules.POP.getId(), new emc.entity.pop.POPSuppliers(),
                                                          "supplierId", supplierKeys, copyUD);
        lkpTableSupplier.setPopup(supplierPopup);
        edtTableSupplier = new EMCLookupTableCellEditor(lkpTableSupplier);
        //Master Approval Group
        lkpTableApprovalGroup = new EMCLookupJTableComponent(new emc.menus.pop.menuitems.display.PurchaseOrderApprovalGroup());
        EMCLookupPopup appGrpPopup = new EMCLookupPopup(new emc.entity.pop.POPPurchaseOrderApprovalGroups(), "purchaseOrderApprovalGroupId", this.copyUD);
        lkpTableApprovalGroup.setPopup(appGrpPopup);

        //Master currency
        List currencyKeys = new ArrayList<String>();
        currencyKeys.add("currency");
        currencyKeys.add("descriptiveName");

        lkpTableCurrency = new EMCLookupJTableComponent(new emc.menus.gl.menuitems.display.GLCurrency());
        EMCLookupPopup currencyPopup = new EMCLookupPopup(enumEMCModules.GENERAL_LEDGER.getId(), new emc.entity.gl.GLCurrency(),
                                                          "currency", currencyKeys, copyUD);
        lkpTableCurrency.setPopup(currencyPopup);
        edtTableCurrency = new EMCLookupTableCellEditor(lkpTableCurrency);

        //Master VAT code
        List vatCodeKeys = new ArrayList();
        vatCodeKeys.add("vatId");
        vatCodeKeys.add("description");
//
//        lkpVatCode = new emcLookupFormComponent(new emc.menus.gl.menuitems.display.GLVATCode(),
//                masterDRM, "vatCode");
        EMCLookupPopup vatCodePopup = new EMCLookupPopup(enumEMCModules.GENERAL_LEDGER.getId(), new emc.entity.gl.GLVATCode(),
                                                         "vatId", vatCodeKeys, copyUD);
//        lkpVatCode.setPopup(vatCodePopup);
//        masterDRM.setDocument(lkpVatCode.getDocument());

        //Lines VAT code
        lkpLinesVatCode = new EMCLookupFormComponent(new emc.menus.gl.menuitems.display.GLVATCode(),
                                                     linesDRM, "vatCode");
        lkpLinesVatCode.setPopup(vatCodePopup);
        linesDRM.setDocument(lkpLinesVatCode.getDocument());

        //Lines Price Reason
        List<String> reasonKeys = new ArrayList<String>();
        reasonKeys.add("reasonId");
        reasonKeys.add("description");
        EMCLookupPopup reasonPopup = new EMCLookupPopup(new emc.entity.pop.costchange.POPCostChangeReason(), "reasonId", reasonKeys, copyUD);
        lkpReason = new EMCLookupFormComponent(new emc.menus.pop.menuitems.display.CostChangeReasonMenu(), linesDRM, "reason");
        lkpReason.setPopup(reasonPopup);
        linesDRM.setDocument(lkpReason.getDocument());

        //Master postal code
        List postalCodeKeys = new ArrayList();
        postalCodeKeys.add("code");
        postalCodeKeys.add("suburb");
        postalCodeKeys.add("country");

        lkpAddressPostalCode = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.PostalCodes(),
                                                          masterDRM, "addressPostalCode", "code");
        EMCLookupPopup postalCodePopup = new EMCLookupPopup(enumEMCModules.BASE.getId(), new emc.entity.base.BasePostalCodes(),
                                                            "code", postalCodeKeys, copyUD);
        lkpAddressPostalCode.setPopup(postalCodePopup);
        masterDRM.setDocument(lkpAddressPostalCode.getDocument());
        lkpAddressPostalCode.setPreferredSize(new java.awt.Dimension(80, 25));

        //Master price group
        List priceGroupKeys = new ArrayList();
        priceGroupKeys.add("priceGroupId");
        priceGroupKeys.add("description");

        lkpPriceGroup = new EMCLookupFormComponent(new emc.menus.pop.menuitems.display.PriceGroups(),
                                                   masterDRM, "priceGroup", "priceGroupId");
        EMCLookupPopup priceGroupPopup = new EMCLookupPopup(enumEMCModules.POP.getId(), new emc.entity.pop.POPPriceGroup(),
                                                            "priceGroupId", priceGroupKeys, copyUD);
        lkpPriceGroup.setPopup(priceGroupPopup);
        masterDRM.setDocument(lkpPriceGroup.getDocument());

        //Master Quality Test Type
        List qualityKeys = new ArrayList();
        qualityKeys.add("testTypeId");
        qualityKeys.add("description");

        lkpQualityTestType = new EMCLookupFormComponent(new emc.menus.pop.menuitems.display.QualityTestTypes(),
                                                        masterDRM, "qualityTest", "testTypeId");
        EMCLookupPopup qualityPopup = new EMCLookupPopup(enumEMCModules.POP.getId(), new emc.entity.pop.POPQualityTestType(),
                                                         "testTypeId", qualityKeys, copyUD);
        lkpQualityTestType.setPopup(qualityPopup);
        masterDRM.setDocument(lkpQualityTestType.getDocument());

        //Lines warehouse
        //List warehouseLineKeys = new ArrayList();
        //warehouseKeys.add("warehouseId");
        //warehouseKeys.add("description");

        lkpLinesWarehouse = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.Warehouse(),
                                                       linesDRM, "warehouse");
        //emcLookupPopup warehouseLinePopup = new emcLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryWarehouse(),
        //"warehouseId", warehouseLineKeys, copyUD);
        lkpLinesWarehouse.setPopup(warehousePopup);
        linesDRM.setDocument(lkpLinesWarehouse.getDocument());

        //Lines unit of measure
        List uomKeys = new ArrayList();
        uomKeys.add("unit");
        uomKeys.add("description");

        lkpLinesUnitOfMeasure = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.UnitsOfMeasure(),
                                                           linesDRM, "uom");
        EMCLookupPopup unitOfMeasurePopup = new EMCLookupPopup(enumEMCModules.BASE.getId(), new emc.entity.base.BaseUnitsOfMeasure(),
                                                               "unit", uomKeys, copyUD);
        lkpLinesUnitOfMeasure.setPopup(unitOfMeasurePopup);
        linesDRM.setDocument(lkpLinesUnitOfMeasure.getDocument());

        lkpTableUom = new EMCLookupJTableComponent(new emc.menus.base.menuItems.display.UnitsOfMeasure());

        lkpTableUom.setPopup(unitOfMeasurePopup);
        edtTableUom = new EMCLookupTableCellEditor(lkpTableUom);

        //Lines Item
        ItemReferencePopup itemPopup = new ItemReferencePopup("itemId", copyUD);
        lkpTableItemId = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.ItemMaster());
        lkpTableItemId.setPopup(itemPopup);
        edtTableItemId = new EMCLookupTableCellEditor(lkpTableItemId);

        //Lines item config
        List itemConfigKeys = new ArrayList();
        itemConfigKeys.add("dimensionId");
        itemConfigKeys.add("description");

        lkpLinesItemDimension1 = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.Dimension1(),
                                                            linesDRM, "itemDimension1");
        EMCLookupPopup itemConfigPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.datasource.InventoryItemDimension1SetupDS(),
                                                            "dimensionId", itemConfigKeys, copyUD);
        itemConfigPopup.setPreferredWidth(500);
        lkpLinesItemDimension1.setPopup(itemConfigPopup);
        linesDRM.setDocument(lkpLinesItemDimension1.getDocument());

        lkpTableItemDimension1 = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.Dimension1());
        lkpTableItemDimension1.setPopup(itemConfigPopup);
        edtTableItemDimension1 = new EMCLookupTableCellEditor(lkpTableItemDimension1);

        //Lines item size
        List itemSizeKeys = new ArrayList();
        itemSizeKeys.add("dimensionId");
        itemSizeKeys.add("description");

        lkpLinesItemDimension2 = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.Dimension2(),
                                                            linesDRM, "itemDimension2");
        EMCLookupPopup itemSizePopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.datasource.InventoryItemDimension2SetupDS(),
                                                          "dimensionId", itemSizeKeys, copyUD);
        lkpLinesItemDimension2.setPopup(itemSizePopup);
        linesDRM.setDocument(lkpLinesItemDimension2.getDocument());

        lkpTableItemDimension2 = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.Dimension2());
        lkpTableItemDimension2.setPopup(itemSizePopup);
        edtTableItemDimension2 = new EMCLookupTableCellEditor(lkpTableItemDimension2);

        //Lines item colour
        List itemColourKeys = new ArrayList();
        itemColourKeys.add("dimensionId");
        itemColourKeys.add("description");

        lkpLinesItemDimension3 = new EMCLookupFormComponent(new emc.menus.inventory.menuitems.display.Dimension3(),
                                                            linesDRM, "itemDimension3");
        EMCLookupPopup itemColourPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.datasource.InventoryItemDimension3SetupDS(),
                                                            "dimensionId", itemColourKeys, copyUD);
        lkpLinesItemDimension3.setPopup(itemColourPopup);
        linesDRM.setDocument(lkpLinesItemDimension3.getDocument());

        lkpTableItemDimension3 = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.Dimension3());
        lkpTableItemDimension3.setPopup(itemColourPopup);
        edtTableItemDimension3 = new EMCLookupTableCellEditor(lkpTableItemDimension3);

        EMCQuery dimension1Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension1Setup.class.getName());
        //dimension1Query.addTable(InventoryItemDimensionCombinations.class.getName());
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

        lkpTableItemDimension1.setTheQuery(dimension1Query);
        lkpTableItemDimension2.setTheQuery(dimension2Query);
        lkpTableItemDimension3.setTheQuery(dimension3Query);
        lkpLinesItemDimension1.setTheQuery(dimension1Query);
        lkpLinesItemDimension2.setTheQuery(dimension2Query);
        lkpLinesItemDimension3.setTheQuery(dimension3Query);
    }

    private void setupLookupRelations() {
        lookupRelationManager = new PurchaseOrderLinesLookupRelationManager();

        linesDRM.setLookupRelationManager(lookupRelationManager);

        lookupRelationManager.addLookup(lkpTableItemId, "item");
        lookupRelationManager.addLookup(lkpLinesItemDimension1, "dimension1");
        lookupRelationManager.addLookup(lkpLinesItemDimension2, "dimension2");
        lookupRelationManager.addLookup(lkpLinesItemDimension3, "dimension3");

        lookupRelationManager.initializeLookups();

        lookupRelationManager.addLookup(lkpTableItemDimension1, "tableDimension1");
        lookupRelationManager.addLookup(lkpTableItemDimension2, "tableDimension2");
        lookupRelationManager.addLookup(lkpTableItemDimension3, "tableDimension3");
    }

    /**
     * Initializes buttons.
     */
    private void initButtons() {
        this.querySwitchButton = new EMCQuerySwitchButton("Active PO's", masterDRM);

        //Get initial query
        EMCQuery query = new POPPurchaseOrderMaster().getQuery();
        query.addAnd("status", PurchaseOrderStatus.RECEIVED.toString(), EMCQueryConditions.NOT);
        query.openAndConditionBracket();
        query.addAnd("cancelledStatus", PurchaseOrderCancelledStatus.PARTIALLY_CANCELLED.toString());
        query.addOr("cancelledStatus", null);
        query.closeConditionBracket();
        querySwitchButton.addQuery("Active PO's", query);

        query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class.getName());
        query.addAnd("companyId", getUserData().getCompanyId());
        query.addOrderBy("purchaseOrderId");

        querySwitchButton.addQuery("All PO's", query);

        //Get employee number
        //The employee number of the current user
        String employeeNumber = "";

        //Copy of userData
        EMCUserData copyUD = masterDRM.getUserData().copyUserData();

        //Gets the employee number of the current user
        EMCCommandClass cmd = new EMCCommandClass();
        cmd.setCommandId(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId());
        cmd.setModuleNumber(enumEMCModules.BASE.getId());
        cmd.setMethodId(emc.methods.base.ServerBaseMethods.FIND_EMPLOYEE.toString());
        List toSend = new ArrayList();
        toSend.add(copyUD.getUserName());

        List retData = EMCWSManager.executeGenericWS(cmd, toSend, copyUD);
        if (retData.size() > 0) {
            employeeNumber = retData.get(1).toString();
        }

        query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class.getName());
        query.addTableAnd(emc.entity.pop.POPPurchaseOrderApprovalGroups.class.getName(), "approvalGrp", POPPurchaseOrderMaster.class.getName(), "purchaseOrderApprovalGroupId");
        query.addTableAnd(emc.entity.pop.POPPurchaseOrderApprovalGroupEmployees.class.getName(), "purchaseOrderApprovalGroupId", POPPurchaseOrderApprovalGroups.class.getName(), "purchaseOrderApprovalGroupId");
        query.addAnd("employeeId", employeeNumber, emc.entity.pop.POPPurchaseOrderApprovalGroupEmployees.class.getName());
        query.addAnd("companyId", masterDRM.getUserData().getCompanyId());
        query.addAnd("companyId", masterDRM.getUserData().getCompanyId(), emc.entity.pop.POPPurchaseOrderApprovalGroups.class.getName());
        query.addAnd("companyId", masterDRM.getUserData().getCompanyId(), emc.entity.pop.POPPurchaseOrderApprovalGroupEmployees.class.getName());
        query.addAnd("status", emc.enums.pop.purchaseorders.PurchaseOrderStatus.REQUISITION.toString());
        query.addOrderBy("purchaseOrderId");

        querySwitchButton.addQuery("Awaiting Approval", query);
    }

    private void initFrame() {
        emcJTabbedPane masterTabs = setupMaster();
        masterTabs.setRelationManager(masterDRM);
        emcJPanel pnlMasterButtons = createMasterButtons();
        emcJPanel pnlMaster = new emcJPanel();
        pnlMaster.setLayout(new BorderLayout());
        pnlMaster.add(masterTabs, BorderLayout.CENTER);
        pnlMaster.add(pnlMasterButtons, BorderLayout.EAST);

        emcJTabbedPane linesTabs = setupLines();
        linesTabs.setRelationManager(linesDRM);
        emcJPanel pnlLines = new emcJPanel(new BorderLayout());
        pnlLines.add(linesTabs, BorderLayout.CENTER);
        pnlLines.add(createLineButtons(), BorderLayout.EAST);

        this.setLayout(new GridLayout(1, 1));

        this.add(pnlMaster);
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

        //Overview
        emcJPanel pnlMaster = new emcJPanel();
        pnlMaster.setLayout(new GridLayout(1, 1));

        List masterKeys = new ArrayList();
        masterKeys.add("purchaseOrderId");
        masterKeys.add("supplier");
        masterKeys.add("status");
        masterKeys.add("currency");
        masterKeys.add("purchaseOrderType");
        masterKeys.add("approvalGrp");
        masterKeys.add("requestedDeliveryDate");
        masterKeys.add("cancelled");

        emcTableModelUpdate m = new emcTableModelUpdate(masterDRM, masterKeys);

        tblMaster = new emcJTableUpdate(m) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 2) {
                    return false;
                } else {
                    return super.isCellEditable(row, column);
                }
            }
        };

        masterDRM.setMainTableComponent(tblMaster);
        emcTablePanelUpdate masterScroll = new emcTablePanelUpdate(tblMaster);
        this.setTablePanel(masterScroll);

        //Add lookups to table
        tblMaster.setLookupCellEditor(1, edtTableSupplier);
        tblMaster.setLookupCellEditor(3, edtTableCurrency);
        tblMaster.setLookupToColumn("approvalGrp", lkpTableApprovalGroup);
        tblMaster.setComboBoxLookupToColumn(4, new PurchaseOrderTypeDropDown());

        pnlMaster.add(masterScroll);
        masterTabs.add("Purchase Order Master", pnlMaster);
        masterTabs.add("Additional", createMasterAdditionalPanel());
        masterTabs.add("Delivery", createMasterDeliveryPanel());
        masterTabs.add("Payment", createMasterPaymentPanel());
        masterTabs.add("Quality", createQualityPanel());
        masterTabs.add("Comments", createMasterCommentsPanel());

        return masterTabs;
    }

    private emcJPanel createQualityPanel() {
        qualityTestReq = new emcYesNoComponent(masterDRM, "qualityTestReq");
        qualityReportReq = new emcYesNoComponent(masterDRM, "qualityReportReq");
        Component[][] components = {{new emcJLabel("Test Req."), qualityTestReq},
                                    {new emcJLabel("Test Report Req."), qualityReportReq},
                                    {new emcJLabel("Required Test Type"), lkpQualityTestType},};

        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        thePanel.setBorder(BorderFactory.createTitledBorder("Quality Test"));

        return thePanel;
    }

    private emcJPanel createMasterAdditionalPanel() {
        EMCDatePickerFormComponent orderedDate = new EMCDatePickerFormComponent(masterDRM, "orderedDate");
        orderedDate.setEnabled(false);
        Component[][] components = {
            {lblContactPerson, txtContactPerson, lblOrderedBy, lkpOrderedBy},
            {lblContactTelNo, txtContactTelNo, new emcJLabel("Ordered Date"), orderedDate},
            {lblContactEmail, txtContactEmail, lblApprovedBy, lkpApprovedBy},
            {new emcJLabel()},
            {lblInternalRef, txtInternalRef, lblExternalRef, txtExternalRef},
            {lblInternalRefType, txtInternalRefType, lblExternalRefType, txtExternalRefType},
            {lblSupplierOrderNumber, txtSupplierOrderNumber},
            {lblBlanketOrderRef, txtBlanketOrderRef}};

        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        thePanel.setBorder(BorderFactory.createTitledBorder("Additional"));

        return thePanel;
    }

    private emcJPanel createMasterDeliveryPanel() {
        Component[][] components = {
            {new emcJLabel(), new emcJLabel("Delivery Address")},
            {lblAddressLine1, txtAddressLine1, lblWarehouse, lkpWarehouse},
            {lblAddressLine2, txtAddressLine2, lblRequestedDeliveryDate, requestedDeliveryDate},
            {lblAddressLine3, txtAddressLine3, lblLatestDeliveryDate, latestDeliveryDate},
            {lblAddressLine4, txtAddressLine4, lblConfirmedDeliveryDate, confirmedDeliveryDate},
            {lblAddressLine5, txtAddressLine5, lblActualDeliveryDate, actualDeliveryDate},
            {lblAddressPostalCode, lkpAddressPostalCode, lblDeliveryTerms, lkpDeliveryTerms},
            {new emcJLabel(), new emcJLabel(), lblDeliveryMode, lkpDeliveryMode}};

        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        thePanel.setBorder(BorderFactory.createTitledBorder("Delivery"));

        return thePanel;
    }

    private emcJPanel createMasterPaymentPanel() {
        Component[][] components = {
            {lblPayment, txtPayment, lblTotalDiscPercentage, txtTotalDiscPercentage},
            {lblPaymentDueDate, paymentDueDate},
            {lblMethodOfPayment, txtMethodOfPayment, lblLinesDiscount, txtLinesDiscount},
            {new emcJLabel()},
            {lblPriceGroup, lkpPriceGroup},
            {lblVatApplicable, ysnVatApplicable}};

        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        thePanel.setBorder(BorderFactory.createTitledBorder("Payment"));

        return thePanel;
    }

    private emcJPanel createMasterButtons() {
        emcJPanel pnlButtons = new emcJPanel();
        pnlButtons.setLayout(new GridBagLayout());
        pnlButtons.setPreferredSize(new Dimension(125, 25));
        pnlButtons.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

        int y = 0;
        GridBagConstraints gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        PostingButtonList postList = new PostingButtonList(this);
        postList.setEnabled(enableActionButtons);
        pnlButtons.add(postList, gbc);

//        y++;
//        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
        ApprovalButton appButton = new ApprovalButton(this);
//        appButton.setEnabled(enableActionButtons);
//        pnlButtons.add(appButton, gbc);
        //Approve
        emcJButton btnApprove = new emcJButton("Approve") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                masterDRM.generateRelatedFormUserData(masterData, -1005);

//                if (masterDRM.getLastFieldValueAt("status").equals(PurchaseOrderStatus.APPROVED.toString()) && masterDRM.getRowCount() >= masterDRM.getLastRowAccessed() + 1) {
//                    masterDRM.setSelectedRow(masterDRM.getLastRowAccessed() + 1);
//                }

            }
        };
        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlButtons.add(btnApprove, gbc);

        //Unapprove
        emcJButton btnUnApprove = new emcJButton("Unapprove") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                masterDRM.generateRelatedFormUserData(masterData, -1006);

//                if (masterDRM.getLastFieldValueAt("status").equals(PurchaseOrderStatus.APPROVED.toString()) && masterDRM.getRowCount() >= masterDRM.getLastRowAccessed() + 1) {
//                    masterDRM.setSelectedRow(masterDRM.getLastRowAccessed() + 1);
//                }

            }
        };
        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlButtons.add(btnUnApprove, gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlButtons.add(new EnquiryButton(this), gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        CancelButton cancelButton = new CancelButton(masterDRM);
        cancelButton.setEnabled(enableActionButtons);
        pnlButtons.add(cancelButton, gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        CostChangeButton costButton = new CostChangeButton(this);
        costButton.setEnabled(enableActionButtons);
        pnlButtons.add(costButton, gbc);
        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlButtons.add(querySwitchButton, gbc);
        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MillPurchaseOrders menu = new MillPurchaseOrders();
        menu.setDoNotOpenForm(false);
        pnlButtons.add(new emcMenuButton("Mill", menu, this, 5, false), gbc);

        y++;
        pnlButtons.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));

        return pnlButtons;
    }

    /**
     * Creates master comments panel.
     */
    private emcJPanel createMasterCommentsPanel() {
        Component[][] components = {
            {txaMasterComments, new emcJLabel("Comments")}};

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true, "Comments");
    }

    private emcJTabbedPane setupLines() {
        emcJTabbedPane linesTabs = new emcJTabbedPane();

        //Overview
        emcJPanel pnlLines = new emcJPanel();

        List linesKeys = new ArrayList();
        //linesKeys.add("lineNo");
        linesKeys.add("itemReference");
        linesKeys.add("itemDescription");
        //linesKeys.add("itemName");
        linesKeys.add("itemDimension1");
        linesKeys.add("itemDimension3");
        linesKeys.add("itemDimension2");
        linesKeys.add("quantity");
        linesKeys.add("uom");
        
        linesKeys.add("itemPrice");
        linesKeys.add("discountPercentage");
        linesKeys.add("netAmount");

        emcTableModelUpdate m = new emcTableModelUpdate(linesDRM, linesKeys);

        tblLines = new emcJTableUpdate(m);
        tblLines.setColumnEditable("itemDescription", false);
        tblLines.setColumnEditable("netAmount", false);
        linesDRM.setMainTableComponent(tblLines);
        emcTablePanelUpdate linesScroll = new emcTablePanelUpdate(tblLines);

        //Add lookups to table
        tblLines.setLookupCellEditor(0, edtTableItemId);
        tblLines.setLookupCellEditor(2, edtTableItemDimension1);
        tblLines.setLookupCellEditor(4, edtTableItemDimension2);
        tblLines.setLookupCellEditor(3, edtTableItemDimension3);
        tblLines.setLookupCellEditor(6, edtTableUom);

        pnlLines.setLayout(new BorderLayout());
        pnlLines.add(linesScroll, BorderLayout.CENTER);

        linesTabs.add("Purchase Order Lines", pnlLines);
        linesTabs.add("Dimensions", createLinesDimensionPanel());
        linesTabs.add("Detail", createLinesDetailPanel());
        linesTabs.add("Quantity", createLinesQuantityPanel());
        linesTabs.add("Cost Change", createCostChangePanel());

        return linesTabs;
    }

    private emcJPanel createCostChangePanel() {
        emcJTextField approvedBy = new emcJTextField(new EMCStringFormDocument(linesDRM, "whoApproved"));
        approvedBy.setEditable(false);
        Component[][] comp = {{lblReason, lkpReason}, {new emcJLabel("Approved By"), approvedBy}};
        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
        thePanel.setBorder(BorderFactory.createTitledBorder("Cost Change"));
        return thePanel;
    }

    private emcJPanel createLineButtons() {
        //Creates a new panel for the buttons on the lines
        emcJPanel buttonPane = new emcJPanel();
        buttonPane.setLayout(new GridBagLayout());
        buttonPane.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

        GridBagConstraints gbc;

        int y = 0;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttonPane.add(new emcStockButton(this, true), gbc);

//        y++;
//        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        buttonPane.add(new SplitButton(linesDRM), gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttonPane.add(new CancelLineButton(linesDRM), gbc);

        y++;
        buttonPane.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
        buttonPane.setPreferredSize(new Dimension(120, 50));
        return buttonPane;
    }

    private emcJPanel createLinesDimensionPanel() {
        Component[][] components = {
            {lblLinesWarehouse, lkpLinesWarehouse/*, lblLinesBatch, lkpLinesBatch*/},
            {lblLinesItemDimension1, lkpLinesItemDimension1/*, lblLinesSerial, lkpLinesSerial*/},
            {lblLinesItemDimension3, lkpLinesItemDimension3},
            {lblLinesItemDimension2, lkpLinesItemDimension2},
            {new emcJLabel()},
            {lblLinesTransactionNumber, txtLinesTransactionNumber}};

        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        thePanel.setBorder(BorderFactory.createTitledBorder("Dimensions"));

        return thePanel;
    }

    private emcJPanel createLinesDetailPanel() {
        Component[][] components = {
            {lblLinesDeliveryDate, linesDeliveryDate, lblLinesVatCode, lkpLinesVatCode},
            {lblLinesConfirmedDate, linesConfirmedDate}};

        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        thePanel.setBorder(BorderFactory.createTitledBorder("Detail"));

        emcJPanel pnlComments = new emcJPanel();
        pnlComments.setBorder(BorderFactory.createTitledBorder("Comments"));
        pnlComments.setLayout(new GridLayout(1, 1));

        emcJScrollPane commentsPane = new emcJScrollPane(txaComments);
        commentsPane.setPreferredSize(new Dimension(70, 70));
        pnlComments.add(commentsPane);
        int y = components.length;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1.5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;

        thePanel.add(pnlComments, gbc);

        return thePanel;
    }

    private emcJPanel createLinesQuantityPanel() {
        Component[][] components = {
            //{lblLinesItemName, txtLinesItemName},
            {lblLinesQuantity, txtLinesQuantity},
            {lblLinesUnitOfMeasure, lkpLinesUnitOfMeasure},
            {lblLinesItemsInvoiced, txtLinesItemsInvoiced},
            {lblLinesOverRecQty, txtLinesOverRecQty},
            {lblLinesItemsReceived, txtLinesItemsReceived},
            {lblLinesItemsRemaining, txtLinesItemsRemaining},
            {lblLinesReceiveNow, txtLinesReceiveNow}};

        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        thePanel.setBorder(BorderFactory.createTitledBorder("Quantity"));

        return thePanel;
    }

    /**
     * This method disables certain components until they are functional in EMC
     */
    private void makeComponentsUneditable() {
        txtPayment.setEditable(false);
        paymentDueDate.setEnabled(false);
        txtMethodOfPayment.setEditable(false);
        txtTotalDiscPercentage.setEditable(false);
        txtLinesDiscount.setEditable(false);
        lkpPriceGroup.setEnabled(false);

        txtBlanketOrderRef.setEditable(false);

        txtLinesItemsInvoiced.setEditable(false);
        txtLinesItemsReceived.setEditable(false);
        txtLinesItemsRemaining.setEditable(false);

        txtLinesTransactionNumber.setEditable(false);

        txtLinesOverRecQty.setEditable(false);
    }
}

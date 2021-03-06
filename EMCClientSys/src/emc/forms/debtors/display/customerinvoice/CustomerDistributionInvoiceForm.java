/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.customerinvoice;

import emc.app.components.documents.EMCBigDecimalFormDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.documents.EMCTimeFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
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
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.inventory.emcStockButton;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.queryswitchbutton.EMCQuerySwitchButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.base.BasePostalCodes;
import emc.entity.base.BaseUnitsOfMeasure;
import emc.entity.creditors.CreditorsSettlementDiscountTerms;
import emc.entity.creditors.CreditorsTermsOfPayment;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsSalesArea;
import emc.entity.debtors.DebtorsSalesGroup;
import emc.entity.debtors.DebtorsSalesRegion;
import emc.entity.debtors.datasource.DebtorsCustomerInvoiceLinesDS;
import emc.entity.debtors.datasource.DebtorsCustomerInvoiceMasterDS;
import emc.entity.gl.GLVATCode;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.dimensions.InventoryItemDimension1Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension2Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension3Setup;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension1SetupDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension2SetupDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension3SetupDS;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.pop.POPDeliveryModes;
import emc.entity.pop.POPDeliveryTerms;
import emc.entity.pop.POPDiscountGroup;
import emc.entity.pop.POPExtraChargeGroup;
import emc.entity.pop.POPPriceGroup;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesRepCommission;
import emc.entity.sop.SOPSalesRepGroups;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.debtors.display.customerinvoice.resources.CustomerInvoiceLinesDRM;
import emc.forms.debtors.display.customerinvoice.resources.CustomerInvoiceLinesLRM;
import emc.forms.debtors.display.customerinvoice.resources.CustomerInvoiceMasterDRM;
import emc.forms.debtors.display.customerinvoice.resources.PostButton;
import emc.forms.debtors.display.customerinvoice.resources.TotalsButton;
import emc.framework.EMCMenuItem;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.PostalCodes;
import emc.menus.base.menuItems.display.UnitsOfMeasure;
import emc.menus.base.menuItems.display.employees;
import emc.menus.creditors.menuitems.display.SettlementDiscountTerms;
import emc.menus.creditors.menuitems.display.TermsOfPayment;
import emc.menus.debtors.menuitems.display.DebtorsSalesAreaMenu;
import emc.menus.debtors.menuitems.display.DebtorsSalesGroupMenu;
import emc.menus.debtors.menuitems.display.DebtorsSalesRegionMenu;
import emc.menus.inventory.menuitems.display.ItemBatch;
import emc.menus.inventory.menuitems.display.ItemSerial;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.inventory.menuitems.display.LocationMenu;
import emc.menus.inventory.menuitems.display.PalletMenu;
import emc.menus.inventory.menuitems.display.Warehouse;
import emc.menus.pop.menuitems.display.DeliveryModes;
import emc.menus.pop.menuitems.display.DeliveryTerms;
import emc.menus.pop.menuitems.display.DiscountGroups;
import emc.menus.pop.menuitems.display.ExtraChargeGroups;
import emc.menus.pop.menuitems.display.PriceGroups;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JSplitPane;

/**
 *
 * @author riaan
 */
public class CustomerDistributionInvoiceForm extends BaseInternalFrame {

    // <editor-fold defaultstate="collapsed" desc="Master Overview Tab">
    private EMCLookupJTableComponent tblLkpCustomer;
    private EMCLookupJTableComponent tblLkpInvoiceToCustomer;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Master References Tab">
    //private emcJLabel lblRefInvoiceNo;
    //private emcJTextField txtRefInvoiceNo = new emcJTextField();
    private emcJLabel lblCustomerContact;
    private emcJTextField txtCustomerContact = new emcJTextField();
    private emcJLabel lblCustomerOrderNumber;
    private emcJTextField txtCustomerOrderNumber = new emcJTextField();
    private emcJLabel lblReference;
    private emcJTextField txtReference = new emcJTextField();
    private emcJLabel lblSalesOrderNo;
    private emcJTextField txtSalesOrderNo = new emcJTextField();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Master Address Tab">
    private emcJLabel lblCustomerName;
    private emcJTextField txtCustomerName = new emcJTextField();
    private emcJLabel lblInvoiceToCustomerName;
    private emcJTextField txtInvoiceToCustomerName = new emcJTextField();
    private emcJLabel lblDeliveryAddress1;
    private emcJTextField txtDeliveryAddress1 = new emcJTextField();
    private emcJLabel lblDeliveryAddress2;
    private emcJTextField txtDeliveryAddress2 = new emcJTextField();
    private emcJLabel lblDeliveryAddress3;
    private emcJTextField txtDeliveryAddress3 = new emcJTextField();
    private emcJLabel lblDeliveryAddress4;
    private emcJTextField txtDeliveryAddress4 = new emcJTextField();
    private emcJLabel lblDeliveryAddress5;
    private emcJTextField txtDeliveryAddress5 = new emcJTextField();
    private emcJLabel lblDeliveryAddressPostalCode;
    private EMCLookupFormComponent lkpDeliveryAddressPostalCode;
    private emcJLabel lblInvoiceAddress1;
    private emcJTextField txtInvoiceAddress1 = new emcJTextField();
    private emcJLabel lblInvoiceAddress2;
    private emcJTextField txtInvoiceAddress2 = new emcJTextField();
    private emcJLabel lblInvoiceAddress3;
    private emcJTextField txtInvoiceAddress3 = new emcJTextField();
    private emcJLabel lblInvoiceAddress4;
    private emcJTextField txtInvoiceAddress4 = new emcJTextField();
    private emcJLabel lblInvoiceAddress5;
    private emcJTextField txtInvoiceAddress5 = new emcJTextField();
    private emcJLabel lblInvoiceAddressPostalCode;
    private EMCLookupFormComponent lkpInvoiceAddressPostalCode;
    private emcJLabel lblOrderWarehouse;
    private EMCLookupFormComponent lkpOrderWarehouse;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Master Delivery Tab">
    private emcJLabel lblDeliveryMethod;
    private EMCLookupFormComponent lkpDeliveryMethod;
    private emcJLabel lblDeliveryTerms;
    private EMCLookupFormComponent lkpDeliveryTerms;
    private emcJLabel lblNumberOfCartons;
    private emcJTextField txtNumberOfCartons = new emcJTextField();
    private emcJLabel lblTotalWeight;
    private emcJTextField txtTotalWeight = new emcJTextField();
    private emcJLabel lblDeliveryCharge;
    private emcJTextField txtDeliveryCharge = new emcJTextField();
    private emcJLabel lblDeliveryChargeApplicable;
    private emcYesNoComponent ysnDeliveryChargeApplicable;
    private emcJLabel lblWaybillNo;
    private emcJTextField txtWaybillNo = new emcJTextField();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Master Price/Payment Tab">
    private emcJLabel lblPriceGroup;
    private EMCLookupFormComponent lkpPriceGroup;
    private emcJLabel lblDiscountGroup;
    private EMCLookupFormComponent lkpDiscountGroup;
    private emcJLabel lblExtraChargeGroup;
    private EMCLookupFormComponent lkpExtraChargeGroup;
    private emcJLabel lblTermsCode;
    private EMCLookupFormComponent lkpTermsCode;
    private emcJLabel lblSettlementDiscCode;
    private EMCLookupFormComponent lkpSettlementDiscCode;
    private emcJLabel lblPaymentDueDate;
    private EMCDatePickerFormComponent dpkPaymentDueDate;
    private emcJLabel lblSettlementDiscDate;
    private EMCDatePickerFormComponent dpkSettlementDiscDate;
    private emcJLabel lblMasterVATCode;
    private EMCLookupFormComponent lkpMasterVATCode;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Master Sales Tab">
    private emcJLabel lblSalesArea;
    private EMCLookupFormComponent lkpSalesArea;
    private emcJLabel lblSalesGroup;
    private EMCLookupFormComponent lkpSalesGroup;
    private emcJLabel lblSalesRegion;
    private EMCLookupFormComponent lkpSalesRegion;
    private emcJLabel lblSalesRep;
    private EMCLookupFormComponent lkpSalesRep;
    private emcJLabel lblCommissionApplicable;
    private emcYesNoComponent ysnCommissionApplicable;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Master Additional Tab">
    private emcJLabel lblApprovedDate;
    private EMCDatePickerFormComponent dpkApprovedDate;
    private emcJLabel lblApprovedTime;
    private emcJTextField txtApprovedTime = new emcJTextField();
    private emcJLabel lblApprovedBy;
    private emcJTextField txtApprovedBy = new emcJTextField();
    private emcJLabel lblPostedDate;
    private EMCDatePickerFormComponent dpkPostedDate;
    private emcJLabel lblPostedTime;
    private emcJTextField txtPostedTime = new emcJTextField();
    private emcJLabel lblPostedBy;
    private emcJTextField txtPostedBy = new emcJTextField();
    private emcJLabel lblPrintedDate;
    private EMCDatePickerFormComponent dpkPrintedDate;
    private emcJLabel lblPrintedTime;
    private emcJTextField txtPrintedTime = new emcJTextField();
    private emcJLabel lblPrintedBy;
    private emcJTextField txtPrintedBy = new emcJTextField();
    private emcJLabel lblLastPrintedDate;
    private EMCDatePickerFormComponent dpkLastPrintedDate;
    private emcJLabel lblLastPrintedTime;
    private emcJTextField txtLastPrintedTime = new emcJTextField();
    private emcJLabel lblLastPrintedBy;
    private emcJTextField txtLastPrintedBy = new emcJTextField();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Master Comments Tab">
    private emcJLabel lblComments;
    private emcJTextArea txaComments = new emcJTextArea();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Master Buttons">
    private EMCQuerySwitchButton querySwitchButton;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Lines Overview Tab">
    private EMCLookupJTableComponent tblLkpItem;
    private EMCLookupJTableComponent tblLkpDimension1;
    private EMCLookupJTableComponent tblLkpDimension2;
    private EMCLookupJTableComponent tblLkpDimension3;
    private EMCLookupJTableComponent tblLkpUOM;
    private EMCLookupJTableComponent tblLkpWarehouse;
    private EMCLookupJTableComponent tblLkpLocation;
    private EMCLookupJTableComponent tblLkpBatch;
    private EMCLookupJTableComponent tblLkpSerial;
    private EMCLookupJTableComponent tblLkpPallet;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Lines Pricing Tab">
    private emcJLabel lblStdUnitPrice;
    private emcJTextField txtStdUnitPrice = new emcJTextField();
    private emcJLabel lblVatCode;
    private EMCLookupFormComponent lkpVatCode;
    private emcJLabel lblVatAmount;
    private emcJTextField txtVatAmount = new emcJTextField();
    private emcJLabel lblCostAtAverage;
    private emcJTextField txtCostAtAverage = new emcJTextField();
    private emcJLabel lblCostAdjustment;
    private emcJTextField txtCostAdjustment = new emcJTextField();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Lines Dimension Tab">
    private emcJLabel lblItem;
    private EMCLookupFormComponent frmLkpItem;
    private emcJLabel lblDimension1;
    private EMCLookupFormComponent frmLkpDimension1;
    private emcJLabel lblDimension2;
    private EMCLookupFormComponent frmLkpDimension2;
    private emcJLabel lblDimension3;
    private EMCLookupFormComponent frmLkpDimension3;
    private emcJLabel lblWarehouse;
    private EMCLookupFormComponent frmLkpWarehouse;
    private emcJLabel lblBatch;
    private EMCLookupFormComponent frmLkpBatch;
    private emcJLabel lblSerial;
    private EMCLookupFormComponent frmLkpSerial;
    private emcJLabel lblLocation;
    private EMCLookupFormComponent frmLkpLocation;
    private emcJLabel lblPallet;
    private EMCLookupFormComponent frmLkpPallet;
    // </editor-fold>
    private CustomerInvoiceMasterDRM masterDRM;
    private CustomerInvoiceLinesDRM linesDRM;
    private CustomerInvoiceLinesLRM linesLRM;

    /**
     * Creates a new instance of CustomerInvoiceForm
     */
    public CustomerDistributionInvoiceForm(EMCUserData userData) {
        super("Customer Distribution Invoices", true, true, true, true, userData);
        this.setBounds(20, 20, 870, 720);


        DebtorsCustomerInvoiceMasterDS masterEntity = new DebtorsCustomerInvoiceMasterDS();
        EMCQuery formQuery = masterEntity.getQuery();
        formQuery.addAnd("distributionNumber", null, EMCQueryConditions.IS_NOT_BLANK);
        userData.setUserData(0, formQuery);

        masterDRM = new CustomerInvoiceMasterDRM(new emcGenericDataSourceUpdate(enumEMCModules.DEBTORS.getId(), masterEntity, userData), userData);
        StockDRMParameters param = new StockDRMParameters("itemId", "dimension1", "dimension2", "dimension3", "inventTransId");

        //Select no data.  Initially, all invoices lines were selected, as query did not include an Invoice Number.
        //This led to a large number of log messages that caused the client to freeze.
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
        query.addAnd("invCNNumber", "");
        EMCUserData linesUD = userData.copyUserData();
        linesUD.setUserData(0, query);
        linesDRM = new CustomerInvoiceLinesDRM(new emcGenericDataSourceUpdate(enumEMCModules.DEBTORS.getId(), new DebtorsCustomerInvoiceLinesDS(), linesUD), param, linesUD);

        this.setDataManager(masterDRM);

        masterDRM.setHeaderColumnID("invCNNumber");
        masterDRM.setTheForm(this);
        masterDRM.setFormTextId1("invCNNumber");
        masterDRM.setFormTextId2("customerNo");
        masterDRM.setLinesTable(linesDRM);

        linesDRM.setTheForm(this);
        linesDRM.setFormTextId1("invCNNumber");
        linesDRM.setFormTextId2("itemReference");
        linesDRM.setHeaderTable(masterDRM);
        linesDRM.setRelationColumnToHeader("invCNNumber");

        setDocuments(userData);
        initLabels();
        initDatePickers();
        initLookups(userData.copyUserData());
        initLookupRelationManager();
        initYesNoComponents();
        initButtons();
        disableComponents();
        setupFrame();
    }

    /**
     * Sets documents on text fields.
     */
    private void setDocuments(EMCUserData userData) {
        this.txtCustomerContact.setDocument(new EMCStringFormDocument(masterDRM, "customerContact"));
        this.txtCustomerOrderNumber.setDocument(new EMCStringFormDocument(masterDRM, "customerOrderNumber"));
        //this.txtRefInvoiceNo.setDocument(new EMCStringFormDocument(masterDRM, "refInvoiceNo"));
        this.txtReference.setDocument(new EMCStringFormDocument(masterDRM, "reference"));
        this.txtSalesOrderNo.setDocument(new EMCStringFormDocument(masterDRM, "salesOrderNo"));
        this.txtSalesOrderNo.setEditable(false);

        this.txtCustomerName.setDocument(new EMCStringFormDocument(masterDRM, "customerTradingAs"));
        this.txtInvoiceToCustomerName.setDocument(new EMCStringFormDocument(masterDRM, "invoiceToCustomerName"));
        this.txtDeliveryAddress1.setDocument(new EMCStringFormDocument(masterDRM, "deliveryAddress1"));
        this.txtDeliveryAddress2.setDocument(new EMCStringFormDocument(masterDRM, "deliveryAddress2"));
        this.txtDeliveryAddress3.setDocument(new EMCStringFormDocument(masterDRM, "deliveryAddress3"));
        this.txtDeliveryAddress4.setDocument(new EMCStringFormDocument(masterDRM, "deliveryAddress4"));
        this.txtDeliveryAddress5.setDocument(new EMCStringFormDocument(masterDRM, "deliveryAddress5"));

        this.txtInvoiceAddress1.setDocument(new EMCStringFormDocument(masterDRM, "invoiceAddress1"));
        this.txtInvoiceAddress2.setDocument(new EMCStringFormDocument(masterDRM, "invoiceAddress2"));
        this.txtInvoiceAddress3.setDocument(new EMCStringFormDocument(masterDRM, "invoiceAddress3"));
        this.txtInvoiceAddress4.setDocument(new EMCStringFormDocument(masterDRM, "invoiceAddress4"));
        this.txtInvoiceAddress5.setDocument(new EMCStringFormDocument(masterDRM, "invoiceAddress5"));

        this.txtApprovedBy.setDocument(new EMCStringFormDocument(masterDRM, "approvedBy"));
        this.txtApprovedTime.setDocument(new EMCTimeFormDocument(txtApprovedTime, masterDRM, "approvedTime"));

        this.txtPostedBy.setDocument(new EMCStringFormDocument(masterDRM, "postedBy"));
        this.txtPostedTime.setDocument(new EMCTimeFormDocument(txtApprovedTime, masterDRM, "postedTime"));

        this.txtPrintedTime.setDocument(new EMCTimeFormDocument(txtApprovedTime, masterDRM, "printedTime"));

        this.txtLastPrintedTime.setDocument(new EMCTimeFormDocument(txtApprovedTime, masterDRM, "lastPrintedTime"));

        this.txtPrintedBy.setDocument(new EMCStringFormDocument(masterDRM, "printedBy"));
        this.txtLastPrintedBy.setDocument(new EMCStringFormDocument(masterDRM, "lastPrintedBy"));

        this.txtStdUnitPrice.setDocument(new EMCBigDecimalFormDocument(linesDRM, "stdUnitPrice"));
        this.txtVatAmount.setDocument(new EMCBigDecimalFormDocument(linesDRM, "vatAmount"));
        this.txtCostAtAverage.setDocument(new EMCBigDecimalFormDocument(linesDRM, "costAtAverage"));
        this.txtCostAdjustment.setDocument(new EMCBigDecimalFormDocument(linesDRM, "costAdjustment"));

        this.txtNumberOfCartons.setDocument(new EMCBigDecimalFormDocument(masterDRM, "numberOfCartons"));
        this.txtTotalWeight.setDocument(new EMCBigDecimalFormDocument(masterDRM, "totalWeight"));
        this.txtDeliveryCharge.setDocument(new EMCBigDecimalFormDocument(masterDRM, "deliveryCharge"));
        this.txtWaybillNo.setDocument(new EMCStringFormDocument(masterDRM, "waybillNumber"));

        this.txaComments.setDocument(new EMCStringFormDocument(masterDRM, "comments"));
    }

    /**
     * Initializes labels.
     */
    private void initLabels() {
        this.lblCustomerContact = new emcJLabel(masterDRM.getColumnName("customerContact"));
        this.lblCustomerOrderNumber = new emcJLabel(masterDRM.getColumnName("customerOrderNumber"));
        //this.lblRefInvoiceNo = new emcJLabel(masterDRM.getColumnName("refInvoiceNo"));
        this.lblReference = new emcJLabel(masterDRM.getColumnName("reference"));
        this.lblSalesOrderNo = new emcJLabel(masterDRM.getColumnName("salesOrderNo"));

        //Hard-coded Strings are deliberate.  Customer name labels on form should be more specific than that on the grid.
        this.lblCustomerName = new emcJLabel("Customer Name");
        this.lblInvoiceToCustomerName = new emcJLabel("Invoice To Customer Name");
        this.lblDeliveryAddress1 = new emcJLabel(masterDRM.getColumnName("deliveryAddress1"));
        this.lblDeliveryAddress2 = new emcJLabel(masterDRM.getColumnName("deliveryAddress2"));
        this.lblDeliveryAddress3 = new emcJLabel(masterDRM.getColumnName("deliveryAddress3"));
        this.lblDeliveryAddress4 = new emcJLabel(masterDRM.getColumnName("deliveryAddress4"));
        this.lblDeliveryAddress5 = new emcJLabel(masterDRM.getColumnName("deliveryAddress5"));
        this.lblDeliveryAddressPostalCode = new emcJLabel(masterDRM.getColumnName("deliveryAddressPostalCode"));

        this.lblInvoiceAddress1 = new emcJLabel(masterDRM.getColumnName("invoiceAddress1"));
        this.lblInvoiceAddress2 = new emcJLabel(masterDRM.getColumnName("invoiceAddress2"));
        this.lblInvoiceAddress3 = new emcJLabel(masterDRM.getColumnName("invoiceAddress3"));
        this.lblInvoiceAddress4 = new emcJLabel(masterDRM.getColumnName("invoiceAddress4"));
        this.lblInvoiceAddress5 = new emcJLabel(masterDRM.getColumnName("invoiceAddress5"));
        this.lblInvoiceAddressPostalCode = new emcJLabel(masterDRM.getColumnName("invoiceAddressPostalCode"));

        this.lblOrderWarehouse = new emcJLabel(masterDRM.getColumnName("orderWarehouse"));
        this.lblDeliveryMethod = new emcJLabel(masterDRM.getColumnName("deliveryMethod"));
        this.lblDeliveryTerms = new emcJLabel(masterDRM.getColumnName("deliveryTerms"));

        this.lblPriceGroup = new emcJLabel(masterDRM.getColumnName("pricingGroup"));
        this.lblDiscountGroup = new emcJLabel(masterDRM.getColumnName("discountGroup"));
        this.lblExtraChargeGroup = new emcJLabel(masterDRM.getColumnName("extraChargeGroup"));
        this.lblTermsCode = new emcJLabel(masterDRM.getColumnName("termsCode"));
        this.lblSettlementDiscCode = new emcJLabel(masterDRM.getColumnName("settlementDiscCode"));
        this.lblSettlementDiscDate = new emcJLabel(masterDRM.getColumnName("settlementDiscDate"));
        this.lblPaymentDueDate = new emcJLabel(masterDRM.getColumnName("paymentDueDate"));
        this.lblCustomerContact = new emcJLabel(masterDRM.getColumnName("customerContact"));

        this.lblSalesArea = new emcJLabel(masterDRM.getColumnName("salesArea"));
        this.lblSalesGroup = new emcJLabel(masterDRM.getColumnName("salesGroup"));
        this.lblSalesRegion = new emcJLabel(masterDRM.getColumnName("salesRegion"));

        this.lblSalesRep = new emcJLabel(masterDRM.getColumnName("salesRep"));
        this.lblCommissionApplicable = new emcJLabel(masterDRM.getColumnName("commissionApplicable"));

        this.lblApprovedBy = new emcJLabel(masterDRM.getColumnName("approvedBy"));
        this.lblApprovedDate = new emcJLabel(masterDRM.getColumnName("approvedDate"));
        this.lblApprovedTime = new emcJLabel(masterDRM.getColumnName("approvedTime"));

        this.lblPostedBy = new emcJLabel(masterDRM.getColumnName("postedBy"));
        this.lblPostedDate = new emcJLabel(masterDRM.getColumnName("postedDate"));
        this.lblPostedTime = new emcJLabel(masterDRM.getColumnName("postedTime"));

        this.lblPrintedBy = new emcJLabel(masterDRM.getColumnName("printedBy"));
        this.lblPrintedDate = new emcJLabel(masterDRM.getColumnName("printedDate"));
        this.lblPrintedTime = new emcJLabel(masterDRM.getColumnName("printedTime"));

        this.lblLastPrintedBy = new emcJLabel(masterDRM.getColumnName("lastPrintedBy"));
        this.lblLastPrintedDate = new emcJLabel(masterDRM.getColumnName("lastPrintedDate"));
        this.lblLastPrintedTime = new emcJLabel(masterDRM.getColumnName("lastPrintedTime"));

        this.lblMasterVATCode = new emcJLabel(masterDRM.getColumnName("vatCode"));

        this.lblComments = new emcJLabel(masterDRM.getColumnName("comments"));

        this.lblVatCode = new emcJLabel(linesDRM.getColumnName("vatCode"));
        this.lblVatAmount = new emcJLabel(linesDRM.getColumnName("vatAmount"));
        this.lblStdUnitPrice = new emcJLabel(linesDRM.getColumnName("stdUnitPrice"));
        this.lblCostAtAverage = new emcJLabel(linesDRM.getColumnName("costAtAverage"));
        this.lblCostAdjustment = new emcJLabel(linesDRM.getColumnName("costAdjustment"));

        this.lblItem = new emcJLabel(linesDRM.getColumnName("itemReference"));
        this.lblDimension1 = new emcJLabel(linesDRM.getColumnName("dimension1"));
        this.lblDimension2 = new emcJLabel(linesDRM.getColumnName("dimension2"));
        this.lblDimension3 = new emcJLabel(linesDRM.getColumnName("dimension3"));
        this.lblWarehouse = new emcJLabel(linesDRM.getColumnName("warehouse"));
        this.lblBatch = new emcJLabel(linesDRM.getColumnName("batch"));
        this.lblSerial = new emcJLabel(linesDRM.getColumnName("serial"));
        this.lblLocation = new emcJLabel(linesDRM.getColumnName("location"));
        this.lblPallet = new emcJLabel(linesDRM.getColumnName("pallet"));

        this.lblNumberOfCartons = new emcJLabel(masterDRM.getColumnName("numberOfCartons"));
        this.lblTotalWeight = new emcJLabel(masterDRM.getColumnName("totalWeight"));
        this.lblDeliveryCharge = new emcJLabel(masterDRM.getColumnName("deliveryCharge"));
        this.lblDeliveryChargeApplicable = new emcJLabel(masterDRM.getColumnName("deliveryChargeApplicable"));
        this.lblWaybillNo = new emcJLabel(masterDRM.getColumnName("waybillNumber"));
    }

    /**
     * Initializes data pickers.
     */
    private void initDatePickers() {
        this.dpkPaymentDueDate = new EMCDatePickerFormComponent(masterDRM, "paymentDueDate");
        this.dpkSettlementDiscDate = new EMCDatePickerFormComponent(masterDRM, "settlementDiscDate");

        this.dpkApprovedDate = new EMCDatePickerFormComponent(masterDRM, "approvedDate");
        this.dpkPostedDate = new EMCDatePickerFormComponent(masterDRM, "postedDate");
        this.dpkPrintedDate = new EMCDatePickerFormComponent(masterDRM, "printedDate");
        this.dpkLastPrintedDate = new EMCDatePickerFormComponent(masterDRM, "lastPrintedDate");
    }

    /**
     * Initializes Yes/No components.
     */
    private void initYesNoComponents() {
        this.ysnCommissionApplicable = new emcYesNoComponent(masterDRM, "commissionApplicable");
        this.ysnDeliveryChargeApplicable = new emcYesNoComponent(masterDRM, "deliveryChargeApplicable");
    }

    /**
     * Initializes lookups.
     */
    private void initLookups(EMCUserData userData) {
        EMCMenuItem postalCodesMenu = new PostalCodes();

        EMCLookupPopup postalCodePopup = new EMCLookupPopup(new BasePostalCodes(), "code", userData);

        this.lkpDeliveryAddressPostalCode = new EMCLookupFormComponent(postalCodesMenu, masterDRM, "deliveryAddressPostalCode");
        this.lkpInvoiceAddressPostalCode = new EMCLookupFormComponent(postalCodesMenu, masterDRM, "invoiceAddressPostalCode");

        this.lkpDeliveryAddressPostalCode.setPopup(postalCodePopup);
        this.lkpInvoiceAddressPostalCode.setPopup(postalCodePopup);

        EMCMenuItem custMenu = new SOPCustomersMenu();

        EMCLookupPopup customerPopup = new EMCLookupPopup(new SOPCustomers(), "customerId", userData);
        this.tblLkpCustomer = new EMCLookupJTableComponent(custMenu);
        this.tblLkpInvoiceToCustomer = new EMCLookupJTableComponent(custMenu);
        this.tblLkpCustomer.setPopup(customerPopup);
        this.tblLkpInvoiceToCustomer.setPopup(customerPopup);

        EMCLookupPopup orderWarehousePopup = new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", userData);
        this.lkpOrderWarehouse = new EMCLookupFormComponent(new Warehouse(), masterDRM, "orderWarehouse");
        this.lkpOrderWarehouse.setPopup(orderWarehousePopup);

        EMCLookupPopup deliveryTermsPopup = new EMCLookupPopup(new POPDeliveryTerms(), "deliveryTermsId", userData);
        this.lkpDeliveryTerms = new EMCLookupFormComponent(new DeliveryTerms(), masterDRM, "deliveryTerms");
        this.lkpDeliveryTerms.setPopup(deliveryTermsPopup);

        EMCLookupPopup deliveryMethodPopup = new EMCLookupPopup(new POPDeliveryModes(), "deliveryModeId", userData);
        this.lkpDeliveryMethod = new EMCLookupFormComponent(new DeliveryModes(), masterDRM, "deliveryMethod");
        this.lkpDeliveryMethod.setPopup(deliveryMethodPopup);

        EMCLookupPopup priceGroupPopup = new EMCLookupPopup(new POPPriceGroup(), "priceGroupId", userData);
        this.lkpPriceGroup = new EMCLookupFormComponent(new PriceGroups(), masterDRM, "pricingGroup");
        this.lkpPriceGroup.setPopup(priceGroupPopup);

        EMCLookupPopup discountGroupPopup = new EMCLookupPopup(new POPDiscountGroup(), "discountGroupId", userData);
        this.lkpDiscountGroup = new EMCLookupFormComponent(new DiscountGroups(), masterDRM, "discountGroup");
        this.lkpDiscountGroup.setPopup(discountGroupPopup);

        EMCLookupPopup extraChargeGroupPopup = new EMCLookupPopup(new POPExtraChargeGroup(), "extraChargeGroupId", userData);
        this.lkpExtraChargeGroup = new EMCLookupFormComponent(new ExtraChargeGroups(), masterDRM, "extraChargeGroup");
        this.lkpExtraChargeGroup.setPopup(extraChargeGroupPopup);

        EMCLookupPopup termsCodePopup = new EMCLookupPopup(new CreditorsTermsOfPayment(), "termsOfPaymentId", userData);
        this.lkpTermsCode = new EMCLookupFormComponent(new TermsOfPayment(), masterDRM, "termsCode");
        this.lkpTermsCode.setPopup(termsCodePopup);

        EMCLookupPopup settlementDiscCodePopup = new EMCLookupPopup(new CreditorsSettlementDiscountTerms(), "settlementDiscountTermId", userData);
        this.lkpSettlementDiscCode = new EMCLookupFormComponent(new SettlementDiscountTerms(), masterDRM, "settlementDiscCode");
        this.lkpSettlementDiscCode.setPopup(settlementDiscCodePopup);

        EMCLookupPopup salesAreaPopup = new EMCLookupPopup(new DebtorsSalesArea(), "salesArea", userData);
        this.lkpSalesArea = new EMCLookupFormComponent(new DebtorsSalesAreaMenu(), masterDRM, "salesArea");
        this.lkpSalesArea.setPopup(salesAreaPopup);

        EMCLookupPopup salesGroupPopup = new EMCLookupPopup(new DebtorsSalesGroup(), "salesGroup", userData);
        this.lkpSalesGroup = new EMCLookupFormComponent(new DebtorsSalesGroupMenu(), masterDRM, "salesGroup");
        this.lkpSalesGroup.setPopup(salesGroupPopup);

        EMCLookupPopup salesRegionPopup = new EMCLookupPopup(new DebtorsSalesRegion(), "salesRegion", userData);
        this.lkpSalesRegion = new EMCLookupFormComponent(new DebtorsSalesRegionMenu(), masterDRM, "salesRegion");
        this.lkpSalesRegion.setPopup(salesRegionPopup);

        this.lkpSalesRep = new EMCLookupFormComponent(new employees(), masterDRM, "salesRep");
        //Copied from Sales Order form
        lkpSalesRep.setPopup(new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", userData) {

            @Override
            public void show(Component invoker, int x, int y) {
                //Only Works for N & L as their customerItemField1 is the customer Id and is always populated
                EMCQuery custQuery = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
                custQuery.addAnd("customerId", masterDRM.getLastFieldValueAt("customerNo"));
                custQuery.addField("salesRep");

                EMCQuery repQuery = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepCommission.class);
                repQuery.addAnd("customerItemField1", masterDRM.getLastFieldValueAt("customerNo"));
                repQuery.addField("repId");

                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
                query.addTableAnd(SOPSalesRepGroups.class.getName(), "employeeNumber", BaseEmployeeTable.class.getName(), "groupManager");
                query.openConditionBracket(EMCQueryBracketConditions.AND);
                query.addOr("employeeNumber", custQuery, EMCQueryConditions.IN);
                query.addOr("employeeNumber", repQuery, EMCQueryConditions.IN);
                query.closeConditionBracket();

                this.setQuery(query);

                super.show(invoker, x, y);
            }
        });

        EMCLookupPopup vatCodePopup = new EMCLookupPopup(new GLVATCode(), "vatId", userData);
        this.lkpVatCode = new EMCLookupFormComponent(new emc.menus.gl.menuitems.display.GLVATCode(), linesDRM, "vatCode");
        this.lkpVatCode.setPopup(vatCodePopup);

        this.lkpMasterVATCode = new EMCLookupFormComponent(new emc.menus.gl.menuitems.display.GLVATCode(), masterDRM, "vatCode");
        this.lkpMasterVATCode.setPopup(vatCodePopup);

        this.tblLkpItem = EMCItemLookupFactory.createItemLookup(userData);

        this.frmLkpItem = EMCItemLookupFactory.createItemFormLookup(userData, linesDRM, "itemReference");

        EMCLookupPopup dimension1Popup = new EMCLookupPopup(new InventoryItemDimension1SetupDS(), "dimensionId", userData);
        dimension1Popup.setPreferredWidth(500);
        this.frmLkpDimension1 = new EMCLookupFormComponent(new Dimension1(), linesDRM, "dimension1");
        this.frmLkpDimension1.setPopup(dimension1Popup);

        this.tblLkpDimension1 = new EMCLookupJTableComponent(new Dimension1());
        this.tblLkpDimension1.setPopup(dimension1Popup);

        EMCLookupPopup dimension2Popup = new EMCLookupPopup(new InventoryItemDimension2SetupDS(), "dimensionId", userData);
        this.frmLkpDimension2 = new EMCLookupFormComponent(new Dimension2(), linesDRM, "dimension2");
        this.frmLkpDimension2.setPopup(dimension2Popup);

        this.tblLkpDimension2 = new EMCLookupJTableComponent(new Dimension2());
        this.tblLkpDimension2.setPopup(dimension2Popup);

        EMCLookupPopup dimension3Popup = new EMCLookupPopup(new InventoryItemDimension3SetupDS(), "dimensionId", userData);
        this.frmLkpDimension3 = new EMCLookupFormComponent(new Dimension3(), linesDRM, "dimension3");
        this.frmLkpDimension3.setPopup(dimension3Popup);

        this.tblLkpDimension3 = new EMCLookupJTableComponent(new Dimension3());
        this.tblLkpDimension3.setPopup(dimension3Popup);

        EMCLookupPopup warehousePopup = new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", userData);
        this.frmLkpWarehouse = new EMCLookupFormComponent(new Warehouse(), linesDRM, "warehouse");
        this.frmLkpWarehouse.setPopup(warehousePopup);

        this.tblLkpWarehouse = new EMCLookupJTableComponent(new Warehouse());
        this.tblLkpWarehouse.setPopup(warehousePopup);

        EMCLookupPopup uomPopup = new EMCLookupPopup(new BaseUnitsOfMeasure(), "unit", userData);
        this.tblLkpUOM = new EMCLookupJTableComponent(new UnitsOfMeasure());
        this.tblLkpUOM.setPopup(uomPopup);

        //Batch
        List<String> batchKeys = new ArrayList<String>();
        batchKeys.add("batch");

        EMCLookupPopup batchPopup = new EMCLookupPopup(new InventorySummary(),
                "batch", batchKeys, userData);
        this.frmLkpBatch = new EMCLookupFormComponent(new ItemBatch(), linesDRM, "batch");
        this.frmLkpBatch.setPopup(batchPopup);

        this.tblLkpBatch = new EMCLookupJTableComponent(new ItemBatch());
        this.tblLkpBatch.setPopup(batchPopup);

        //Serial
        List<String> serialKeys = new ArrayList<String>();
        serialKeys.add("serialNo");

        EMCLookupPopup serialPopup = new EMCLookupPopup(new InventorySummary(),
                "serialNo", serialKeys, userData);
        this.frmLkpSerial = new EMCLookupFormComponent(new ItemSerial(), linesDRM, "serial");
        this.frmLkpSerial.setPopup(serialPopup);

        this.tblLkpSerial = new EMCLookupJTableComponent(new ItemSerial());
        this.tblLkpSerial.setPopup(serialPopup);

        //Location
        List<String> locationKeys = new ArrayList<String>();
        locationKeys.add("location");

        EMCLookupPopup locationPopup = new EMCLookupPopup(new InventoryLocation(), "locationId", userData);
        this.frmLkpLocation = new EMCLookupFormComponent(new LocationMenu(), linesDRM, "location");
        this.frmLkpLocation.setPopup(locationPopup);

        this.tblLkpLocation = new EMCLookupJTableComponent(new LocationMenu());
        this.tblLkpLocation.setPopup(locationPopup);

        //Pallet
        List<String> palletKeys = new ArrayList<String>();
        palletKeys.add("pallet");

        EMCLookupPopup palletPopup = new EMCLookupPopup(new InventorySummary(), "pallet", palletKeys, userData);
        this.frmLkpPallet = new EMCLookupFormComponent(new PalletMenu(), linesDRM, "pallet");
        this.frmLkpPallet.setPopup(palletPopup);

        this.tblLkpPallet = new EMCLookupJTableComponent(new PalletMenu());
        this.tblLkpPallet.setPopup(palletPopup);

        EMCQuery dimension1Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension1Setup.class);
        dimension1Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemDimension1Setup.class.getName(), "itemId");
        dimension1Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryItemDimension1Setup.class.getName(), "dimension1Id");
        dimension1Query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
        dimension1Query.addGroupBy("dimensionId");

        EMCQuery dimension2Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension2Setup.class);
        dimension2Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemDimension2Setup.class.getName(), "itemId");
        dimension2Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryItemDimension2Setup.class.getName(), "dimension2Id");
        dimension2Query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
        dimension2Query.addGroupBy("dimensionId");

        EMCQuery dimension3Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension3Setup.class);
        dimension3Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemDimension3Setup.class.getName(), "itemId");
        dimension3Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryItemDimension3Setup.class.getName(), "dimension3Id");
        dimension3Query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
        dimension3Query.addGroupBy("dimensionId");

        tblLkpDimension1.setTheQuery(dimension1Query);
        tblLkpDimension2.setTheQuery(dimension2Query);
        tblLkpDimension3.setTheQuery(dimension3Query);
        frmLkpDimension1.setTheQuery(dimension1Query);
        frmLkpDimension2.setTheQuery(dimension2Query);
        frmLkpDimension3.setTheQuery(dimension3Query);

        EMCQuery locationQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class);
        locationQuery.addAnd("warehouseId", "");
        frmLkpLocation.setTheQuery(locationQuery);
        tblLkpLocation.setTheQuery(locationQuery);

        EMCQuery serialQuery = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        serialQuery.addAnd("serialNo", null, EMCQueryConditions.NOT);
        serialQuery.addTableAnd(InventoryDimensionTable.class.getName(), "itemDimId", InventorySummary.class.getName(), "recordID");
        serialQuery.addAnd("companyId", userData.getCompanyId());
        frmLkpSerial.setTheQuery(serialQuery);
        tblLkpSerial.setTheQuery(serialQuery);

        EMCQuery batchQuery = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        batchQuery.addAnd("batch", null, EMCQueryConditions.NOT);
        batchQuery.addTableAnd(InventoryDimensionTable.class.getName(), "itemDimId", InventorySummary.class.getName(), "recordID");
        batchQuery.addAnd("companyId", userData.getCompanyId());
        frmLkpBatch.setTheQuery(batchQuery);
        tblLkpBatch.setTheQuery(batchQuery);

        EMCQuery palletQuery = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        palletQuery.addAnd("pallet", null, EMCQueryConditions.NOT);
        palletQuery.addTableAnd(InventoryDimensionTable.class.getName(), "itemDimId", InventorySummary.class.getName(), "recordID");
        palletQuery.addAnd("companyId", userData.getCompanyId());
        frmLkpPallet.setTheQuery(palletQuery);
        tblLkpPallet.setTheQuery(palletQuery);
    }

    /**
     * Creates and sets an ItemLookupRelationManager on the data relation
     * manager.
     */
    public void initLookupRelationManager() {
        //The following has been copied from the picking list form
        linesLRM = new CustomerInvoiceLinesLRM(linesDRM);

        linesDRM.setLRM(linesLRM);

        linesLRM.addLookup(frmLkpItem, "item");
        linesLRM.addLookup(frmLkpDimension1, "dimension1");
        linesLRM.addLookup(frmLkpDimension2, "dimension2");
        linesLRM.addLookup(frmLkpDimension3, "dimension3");
        linesLRM.addLookup(frmLkpBatch, "batch");
        linesLRM.addLookup(frmLkpSerial, "serial");
        linesLRM.addLookup(frmLkpWarehouse, "warehouse");
        linesLRM.addLookup(frmLkpLocation, "location");

        linesLRM.initializeLookups();

        linesLRM.addLookup(tblLkpDimension1, "tableDimension1");
        linesLRM.addLookup(tblLkpDimension2, "tableDimension2");
        linesLRM.addLookup(tblLkpDimension3, "tableDimension3");
        linesLRM.addLookup(tblLkpWarehouse, "tableWarehouse");
    }

    /**
     * Initializes buttons.
     */
    private void initButtons() {
        querySwitchButton = new EMCQuerySwitchButton("Open Invoices", masterDRM);

        EMCQuery openQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceMaster.class);
        openQuery.addAnd("status", DebtorsInvoiceStatus.POSTED.toString(), EMCQueryConditions.NOT);
        openQuery.addAnd("status", DebtorsInvoiceStatus.CANCELED.toString(), EMCQueryConditions.NOT);
        openQuery.addAnd("status", DebtorsInvoiceStatus.DISTRIBUTION.toString(), EMCQueryConditions.NOT);
        openQuery.addAnd("distributionNumber", null, EMCQueryConditions.IS_NOT_BLANK);
        querySwitchButton.addQuery("Open Invoices", openQuery);

        EMCQuery allQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceMaster.class);
        allQuery.addAnd("distributionNumber", null, EMCQueryConditions.IS_NOT_BLANK);
        querySwitchButton.addQuery("All Invoices", allQuery);
    }

    /**
     * Disables components which should not be editable.
     */
    private void disableComponents() {
    }

    /**
     * Sets up the frame.
     */
    private void setupFrame() {
        emcJTabbedPane masterTabs = createMasterTabs();
        masterTabs.setRelationManager(masterDRM);
        emcJPanel pnlMaster = new emcJPanel();
        pnlMaster.setLayout(new BorderLayout());
        pnlMaster.add(masterTabs, BorderLayout.CENTER);
        pnlMaster.add(createMasterButtons(), BorderLayout.EAST);

        emcJTabbedPane linesTabs = createLinesTabs();
        linesTabs.setRelationManager(linesDRM);
        emcJPanel pnlLines = new emcJPanel(new BorderLayout());
        pnlLines.add(linesTabs, BorderLayout.CENTER);
        pnlLines.add(createLinesButtons(), BorderLayout.EAST);

        this.setLayout(new GridLayout(1, 1));

        this.add(pnlMaster);
        this.add(pnlLines);

        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, pnlMaster, pnlLines);
        topBottomSplit.setDividerSize(8);
        topBottomSplit.setContinuousLayout(true);
        topBottomSplit.setOneTouchExpandable(false);
        topBottomSplit.setDividerLocation(350);

        this.add(topBottomSplit);
    }

    /**
     * Creates master tabs.
     */
    private emcJTabbedPane createMasterTabs() {
        emcJTabbedPane masterTabs = new emcJTabbedPane();

        masterTabs.add("Overview", createMasterOverviewTab());
        masterTabs.add("References", createMasterReferencesTab());
        masterTabs.add("Address", createMasterAddressTab());
        masterTabs.add("Delivery", createMasterDeliveryTab());
        masterTabs.add("Price/Payment", createMasterPricePaymentTab());
        masterTabs.add("Sales", createMasterSalesTab());
        masterTabs.add("Additional", createMasterAdditionalTab());
        masterTabs.add("Comments", createMasterCommentsTab());

        return masterTabs;
    }

    /**
     * Creates master overview tab.
     */
    private emcJPanel createMasterOverviewTab() {
        emcJPanel pnlOverview = new emcJPanel();

        List keys = new ArrayList();
        keys.add("invCNNumber");
        keys.add("invoiceDate");
        keys.add("invCNType");
        keys.add("customerNo");
        keys.add("customerTradingAs");
        keys.add("invoiceToCustNo");
        keys.add("invoiceStock");
        keys.add("status");

        emcTableModelUpdate m = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate tblMaster = new emcJTableUpdate(m);

        tblMaster.setLookupToColumn("customerNo", tblLkpCustomer);
        tblMaster.setLookupToColumn("invoiceToCustNo", tblLkpInvoiceToCustomer);

        masterDRM.setMainTableComponent(tblMaster);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(tblMaster);
        pnlOverview.setLayout(new GridLayout(1, 1));
        pnlOverview.add(topscroll);
        masterDRM.setTablePanel(topscroll);

        return pnlOverview;
    }

    /**
     * Creates master references tab.
     */
    public emcJPanel createMasterReferencesTab() {
        Component[][] components = new Component[][]{
            {lblCustomerContact, txtCustomerContact},
            {lblCustomerOrderNumber, txtCustomerOrderNumber},
            //{lblRefInvoiceNo, txtRefInvoiceNo},
            {lblReference, txtReference},
            {lblSalesOrderNo, txtSalesOrderNo}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true, "References");
    }

    /**
     * Creates master address tab.
     */
    private emcJPanel createMasterAddressTab() {
        Component[][] leftComponents = new Component[][]{
            {lblCustomerName, txtCustomerName},
            {lblDeliveryAddress1, txtDeliveryAddress1},
            {lblDeliveryAddress2, txtDeliveryAddress2},
            {lblDeliveryAddress3, txtDeliveryAddress3},
            {lblDeliveryAddress4, txtDeliveryAddress4},
            {lblDeliveryAddress5, txtDeliveryAddress5},
            {lblDeliveryAddressPostalCode, lkpDeliveryAddressPostalCode}
        };

        emcJPanel pnlLeft = emcSetGridBagConstraints.createSimplePanel(leftComponents, GridBagConstraints.NONE, true);

        Component[][] rightComponents = new Component[][]{
            {lblInvoiceToCustomerName, txtInvoiceToCustomerName},
            {lblInvoiceAddress1, txtInvoiceAddress1},
            {lblInvoiceAddress2, txtInvoiceAddress2},
            {lblInvoiceAddress3, txtInvoiceAddress3},
            {lblInvoiceAddress4, txtInvoiceAddress4},
            {lblInvoiceAddress5, txtInvoiceAddress5},
            {lblInvoiceAddressPostalCode, lkpInvoiceAddressPostalCode},
            //Hack to make components line up
            {new JLabel()},
            {new JLabel()},
            {new JLabel()}
        };

        emcJPanel pnlRight = emcSetGridBagConstraints.createSimplePanel(rightComponents, GridBagConstraints.NONE, true);

        emcJPanel addressPanel = new emcJPanel(new GridLayout(1, 2));
        addressPanel.add(pnlLeft);
        addressPanel.add(pnlRight);

        addressPanel.setBorder(BorderFactory.createTitledBorder("Address"));

        return addressPanel;
    }

    /**
     * Creates master delivery tab.
     */
    private emcJPanel createMasterDeliveryTab() {
        emcJTextField txtDistributionNumber = new emcJTextField(new EMCStringFormDocument(masterDRM, "distributionNumber"));
        txtDistributionNumber.setEditable(false);
        Component[][] components = new Component[][]{
            {lblDeliveryMethod, lkpDeliveryMethod},
            {lblDeliveryTerms, lkpDeliveryTerms},
            {new emcJLabel()},
            {new emcJLabel("Distribution Number"), txtDistributionNumber},
            {new emcJLabel()},
            {lblNumberOfCartons, txtNumberOfCartons},
            {lblTotalWeight, txtTotalWeight},
            {lblDeliveryChargeApplicable, ysnDeliveryChargeApplicable},
            {lblDeliveryCharge, txtDeliveryCharge},
            {lblWaybillNo, txtWaybillNo}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }

    /**
     * Creates master price/payment tab.
     */
    private emcJPanel createMasterPricePaymentTab() {
        Component[][] components = new Component[][]{
            {lblPriceGroup, lkpPriceGroup, lblPaymentDueDate, dpkPaymentDueDate},
            {lblDiscountGroup, lkpDiscountGroup, lblExtraChargeGroup, lkpExtraChargeGroup},
            {lblTermsCode, lkpTermsCode, lblMasterVATCode, lkpMasterVATCode},
            {lblSettlementDiscCode, lkpSettlementDiscCode},
            {lblSettlementDiscDate, dpkSettlementDiscDate},};

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true, "Price/Payment");
    }

    /**
     * Creates master sales tab.
     */
    private emcJPanel createMasterSalesTab() {
        Component[][] components = new Component[][]{
            {lblSalesArea, lkpSalesArea},
            {lblSalesGroup, lkpSalesGroup},
            {lblSalesRegion, lkpSalesRegion},
            {lblSalesRep, lkpSalesRep},
            {lblCommissionApplicable, ysnCommissionApplicable}};

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }

    /**
     * Creates master additional tab.
     */
    private emcJPanel createMasterAdditionalTab() {
        Component[][] components = new Component[][]{
            {lblApprovedBy, txtApprovedBy, lblPostedBy, txtPostedBy},
            {lblApprovedDate, dpkApprovedDate, lblPostedDate, dpkPostedDate},
            {lblApprovedTime, txtApprovedTime, lblPostedTime, txtPostedTime},
            {new emcJLabel()},
            {lblPrintedBy, txtPrintedBy, lblLastPrintedBy, txtLastPrintedBy},
            {lblPrintedDate, dpkPrintedDate, lblLastPrintedDate, dpkLastPrintedDate},
            {lblPrintedTime, txtPrintedTime, lblLastPrintedTime, txtLastPrintedTime},};

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true, "Additional");
    }

    /**
     * Creates the master comments tab.
     */
    private emcJPanel createMasterCommentsTab() {
        Component[][] components = new Component[][]{
            {txaComments, lblComments}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }

    /**
     * Creates lines tabs.
     */
    private emcJTabbedPane createLinesTabs() {
        emcJTabbedPane linesTabs = new emcJTabbedPane();
        linesTabs.add("Overview", createLinesOverviewTab());
        linesTabs.add("Pricing", createLinesPricingTab());
        linesTabs.add("Dimensions", createLinesDimensionsTab());

        return linesTabs;
    }

    /**
     * Creates the lines overview panel.
     */
    private emcJPanel createLinesOverviewTab() {
        emcJPanel pnlOverview = new emcJPanel();

        List keys = new ArrayList();
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        //keys.add("warehouse");
        //keys.add("batch");
        //keys.add("serial");
        //keys.add("location");
        //keys.add("pallet");
        keys.add("quantity");
        keys.add("uom");
        keys.add("unitPrice");
        keys.add("discountPercentage");
        keys.add("totalCost");

        emcTableModelUpdate m = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate tblLines = new emcJTableUpdate(m);

        linesDRM.setMainTableComponent(tblLines);

        tblLines.setLookupToColumn("itemReference", tblLkpItem);
        tblLines.setLookupToColumn("dimension1", tblLkpDimension1);
        tblLines.setLookupToColumn("dimension2", tblLkpDimension2);
        tblLines.setLookupToColumn("dimension3", tblLkpDimension3);
        tblLines.setLookupToColumn("uom", tblLkpUOM);
        //tblLines.setLookupToColumn("warehouse", tblLkpWarehouse);
        //tblLines.setLookupToColumn("batch", tblLkpBatch);
        //tblLines.setLookupToColumn("serial", tblLkpSerial);
        //tblLines.setLookupToColumn("location", tblLkpLocation);
        //tblLines.setLookupToColumn("pallet", tblLkpPallet);

        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(tblLines);

        linesDRM.setTablePanel(topscroll);

        pnlOverview.setLayout(new GridLayout(1, 1));
        pnlOverview.add(topscroll);

        return pnlOverview;
    }

    private emcJPanel createLinesPricingTab() {
        Component[][] components = new Component[][]{
            {lblVatCode, lkpVatCode},
            {lblVatAmount, txtVatAmount},
            {lblStdUnitPrice, txtStdUnitPrice},
            {lblCostAtAverage, txtCostAtAverage},
            {lblCostAdjustment, txtCostAdjustment}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true, "Pricing");
    }

    private emcJPanel createLinesDimensionsTab() {
        Component[][] components = new Component[][]{
            {lblItem, frmLkpItem},
            {lblDimension1, frmLkpDimension1},
            {lblDimension2, frmLkpDimension2},
            {lblDimension3, frmLkpDimension3},
            {lblWarehouse, frmLkpWarehouse},
            {lblBatch, frmLkpBatch},
            {lblSerial, frmLkpSerial},
            {lblLocation, frmLkpLocation},
            {lblPallet, frmLkpPallet},};

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true, "Dimensions");
    }

    /**
     * Creates master buttons.
     */
    private emcJPanel createMasterButtons() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();

        buttonList.add(new PostButton(masterDRM));
//        buttonList.add(new InvCancelButton(masterDRM));
//        buttonList.add(new CreditNoteButton(masterDRM));
        buttonList.add(querySwitchButton);
//        buttonList.add(new InvoicePrintButton(this));
        buttonList.add(new TotalsButton(masterDRM, true));

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    /**
     * Creates lines buttons.
     */
    private emcJPanel createLinesButtons() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();

        buttonList.add(new emcStockButton(this, true, new boolean[]{false, true, true}));

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}

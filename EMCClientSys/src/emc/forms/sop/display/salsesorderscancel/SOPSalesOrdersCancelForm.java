/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.salsesorderscancel;

import emc.app.components.documents.EMCBigDecimalFormDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BasePostalCodes;
import emc.entity.creditors.CreditorsSettlementDiscountTerms;
import emc.entity.creditors.CreditorsTermsOfPayment;
import emc.entity.debtors.DebtorsSalesArea;
import emc.entity.debtors.DebtorsSalesGroup;
import emc.entity.debtors.DebtorsSalesRegion;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.pop.POPDeliveryModes;
import emc.entity.pop.POPDeliveryTerms;
import emc.entity.pop.POPDiscountGroup;
import emc.entity.pop.POPExtraChargeGroup;
import emc.entity.pop.POPPriceGroup;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.entity.sop.datasource.SOPSalesOrderCancelLinesDS;
import emc.entity.sop.datasource.SOPSalesOrderCancelMasterDS;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.PostalCodes;
import emc.menus.creditors.menuitems.display.SettlementDiscountTerms;
import emc.menus.creditors.menuitems.display.TermsOfPayment;
import emc.menus.debtors.menuitems.display.DebtorsSalesAreaMenu;
import emc.menus.debtors.menuitems.display.DebtorsSalesGroupMenu;
import emc.menus.debtors.menuitems.display.DebtorsSalesRegionMenu;
import emc.menus.gl.menuitems.display.GLVATCode;
import emc.menus.inventory.menuitems.display.Warehouse;
import emc.menus.pop.menuitems.display.DeliveryModes;
import emc.menus.pop.menuitems.display.DeliveryTerms;
import emc.menus.pop.menuitems.display.DiscountGroups;
import emc.menus.pop.menuitems.display.ExtraChargeGroups;
import emc.menus.pop.menuitems.display.PriceGroups;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JSplitPane;

/**
 *
 * @author wikus
 */
public class SOPSalesOrdersCancelForm extends BaseInternalFrame {

    private emcDRMViewOnly masterDRM;
    private EMCUserData masterUD;
    private emcDRMViewOnly linesDRM;
    private EMCUserData linesUD;

    public SOPSalesOrdersCancelForm(EMCUserData userData) {
        super("Cancelled Sales Orders", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 650);

        //Master
        masterUD = userData.copyUserDataAndDataList();
        masterDRM = new emcDRMViewOnly(new emcGenericDataSourceUpdate(enumEMCModules.SOP.getId(), new SOPSalesOrderCancelMasterDS(), masterUD), masterUD);
        masterDRM.setTheForm(this);
        this.setDataManager(masterDRM);
        masterDRM.setFormTextId1("salesOrderNo");
        masterDRM.setFormTextId2("customerName");
        //Lines
        linesUD = userData.copyUserData();
        linesDRM = new emcDRMViewOnly(new emcGenericDataSourceUpdate(enumEMCModules.SOP.getId(), new SOPSalesOrderCancelLinesDS(), linesUD), linesUD);
        linesDRM.setTheForm(this);
        linesDRM.setFormTextId1("itemReference");
        linesDRM.setFormTextId2("itemDescription");
        //Link
        masterDRM.setLinesTable(linesDRM);
        masterDRM.setHeaderColumnID("salesOrderNo");
        linesDRM.setHeaderTable(masterDRM);
        linesDRM.setRelationColumnToHeader("salesOrderNo");
        //createForm
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, masterPane(), linesPane());
        topBottomSplit.setDividerLocation(270);
        contentPane.add(topBottomSplit, BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcJPanel masterPane() {
        emcJPanel thePane = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", masterOverviewTab());
        tabbed.add("Reference", masterReferenceTab());
        tabbed.add("Address", masterAddressTab());
        tabbed.add("Delivery", masterDeliveryTab());
        tabbed.add("Price/Pmnt", masterPricePmntTab());
        tabbed.add("Comments", masterCommentsTab());
        thePane.add(tabbed, BorderLayout.CENTER);
        return thePane;
    }

    private emcTablePanelUpdate masterOverviewTab() {
        List keys = new ArrayList();
        keys.add("salesOrderNo");
        keys.add("requiredDate");
        keys.add("customerNo");
        keys.add("customerName");
        keys.add("invoiceToCustomerNo");
        keys.add("invoiceStatus");
        keys.add("salsesOrderStatus");
        keys.add("cancelReason");
        emcTableModelUpdate model = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        masterDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        masterDRM.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel masterAddressTab() {
        //Delivery
        emcJTextField txtDeliveryAddress1 = new emcJTextField(new EMCStringFormDocument(masterDRM, "deliveryAddress1"));
        emcJTextField txtDeliveryAddress2 = new emcJTextField(new EMCStringFormDocument(masterDRM, "deliveryAddress2"));
        emcJTextField txtDeliveryAddress3 = new emcJTextField(new EMCStringFormDocument(masterDRM, "deliveryAddress3"));
        emcJTextField txtDeliveryAddress4 = new emcJTextField(new EMCStringFormDocument(masterDRM, "deliveryAddress4"));
        emcJTextField txtDeliveryAddress5 = new emcJTextField(new EMCStringFormDocument(masterDRM, "deliveryAddress5"));
        EMCLookupFormComponent lkpDeliveryPostalCode = new EMCLookupFormComponent(new PostalCodes(), masterDRM, "deliveryAddressCode");
        lkpDeliveryPostalCode.setPopup(new EMCLookupPopup(new BasePostalCodes(), "code", masterUD));
        txtDeliveryAddress1.setEditable(false);
        txtDeliveryAddress2.setEditable(false);
        txtDeliveryAddress3.setEditable(false);
        txtDeliveryAddress4.setEditable(false);
        txtDeliveryAddress5.setEditable(false);
        lkpDeliveryPostalCode.setEnabled(false);
        Component[][] deliveryComp = {{new emcJLabel("Delivery Address Line 1"), txtDeliveryAddress1},
            {new emcJLabel("Delivery Address Line 2"), txtDeliveryAddress2},
            {new emcJLabel("Delivery Address Line 3"), txtDeliveryAddress3},
            {new emcJLabel("Delivery Address Line 4"), txtDeliveryAddress4},
            {new emcJLabel("Delivery Address Line 5"), txtDeliveryAddress5},
            {new emcJLabel("Delivery Postal Code"), lkpDeliveryPostalCode}};
        emcJPanel deliveryPanel = emcSetGridBagConstraints.createSimplePanel(deliveryComp, GridBagConstraints.NONE, true);
        //Invoice
        emcJTextField txtInvoiceAddress1 = new emcJTextField(new EMCStringFormDocument(masterDRM, "invoiceAddress1"));
        emcJTextField txtInvoiceAddress2 = new emcJTextField(new EMCStringFormDocument(masterDRM, "invoiceAddress2"));
        emcJTextField txtInvoiceAddress3 = new emcJTextField(new EMCStringFormDocument(masterDRM, "invoiceAddress3"));
        emcJTextField txtInvoiceAddress4 = new emcJTextField(new EMCStringFormDocument(masterDRM, "invoiceAddress4"));
        emcJTextField txtInvoiceAddress5 = new emcJTextField(new EMCStringFormDocument(masterDRM, "invoiceAddress5"));
        EMCLookupFormComponent lkpInvoiceInvoiceCode = new EMCLookupFormComponent(new PostalCodes(), masterDRM, "invoiceAddressCode");
        lkpInvoiceInvoiceCode.setPopup(new EMCLookupPopup(new BasePostalCodes(), "code", masterUD));
        txtInvoiceAddress1.setEditable(false);
        txtInvoiceAddress2.setEditable(false);
        txtInvoiceAddress3.setEditable(false);
        txtInvoiceAddress4.setEditable(false);
        txtInvoiceAddress5.setEditable(false);
        lkpInvoiceInvoiceCode.setEnabled(false);
        Component[][] invoiceComp = {{new emcJLabel("Invoice Address Line 1"), txtInvoiceAddress1},
            {new emcJLabel("Invoice Address Line 2"), txtInvoiceAddress2},
            {new emcJLabel("Invoice Address Line 3"), txtInvoiceAddress3},
            {new emcJLabel("Invoice Address Line 4"), txtInvoiceAddress4},
            {new emcJLabel("Invoice Address Line 5"), txtInvoiceAddress5},
            {new emcJLabel("Invoice Postal Code"), lkpInvoiceInvoiceCode}};
        emcJPanel invoicePanel = emcSetGridBagConstraints.createSimplePanel(invoiceComp, GridBagConstraints.NONE, true);
        Component[][] addreddComp = {{deliveryPanel, invoicePanel}};
        return emcSetGridBagConstraints.createSimplePanel(addreddComp, GridBagConstraints.NONE, true, "Address");
    }

    private emcJPanel masterDeliveryTab() {
        EMCLookupFormComponent lkpOrderWarehouse = new EMCLookupFormComponent(new Warehouse(), masterDRM, "orderWarehouse");
        lkpOrderWarehouse.setPopup(new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", masterUD));
        EMCLookupFormComponent lkpDeliveryMethod = new EMCLookupFormComponent(new DeliveryModes(), masterDRM, "deliveryMethod");
        lkpDeliveryMethod.setPopup(new EMCLookupPopup(new POPDeliveryModes(), "deliveryModeId", masterUD));
        EMCLookupFormComponent lkpDeliveryTerms = new EMCLookupFormComponent(new DeliveryTerms(), masterDRM, "deliveryTerms");
        lkpDeliveryTerms.setPopup(new EMCLookupPopup(new POPDeliveryTerms(), "deliveryTermsId", masterUD));
        lkpOrderWarehouse.setEnabled(false);
        lkpDeliveryMethod.setEnabled(false);
        lkpDeliveryTerms.setEnabled(false);
        Component[][] comp = {{new emcJLabel("Order Warehouse"), lkpOrderWarehouse},
            {new emcJLabel("Delivery Method"), lkpDeliveryMethod},
            {new emcJLabel("Delivery Terms"), lkpDeliveryTerms}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Delivery");
    }

    private emcJPanel masterReferenceTab() {
        emcJTextField txtCustomerOrderNo = new emcJTextField(new EMCStringFormDocument(masterDRM, "customerOrderNo"));
        emcJTextField txtReference = new emcJTextField(new EMCStringFormDocument(masterDRM, "reference"));
        txtCustomerOrderNo.setEditable(false);
        txtReference.setEditable(false);
        Component[][] comp = {
            {new emcJLabel("Customer Order No"), txtCustomerOrderNo},
            {new emcJLabel("Reference"), txtReference}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Reference");
    }

    private emcJPanel masterPricePmntTab() {
        emcJTextField txtVatNo = new emcJTextField(new EMCStringFormDocument(masterDRM, "vatNo"));
        EMCLookupFormComponent lkpVatCode = new EMCLookupFormComponent(new GLVATCode(), masterDRM, "vatCode");
        lkpVatCode.setPopup(new EMCLookupPopup(new emc.entity.gl.GLVATCode(), "vatId", masterUD));
        EMCLookupFormComponent lkpTermsOfPayment = new EMCLookupFormComponent(new TermsOfPayment(), masterDRM, "termsCode");
        lkpTermsOfPayment.setPopup(new EMCLookupPopup(new CreditorsTermsOfPayment(), "termsOfPaymentId", masterUD));
        EMCLookupFormComponent lkpSettlementDiscount = new EMCLookupFormComponent(new SettlementDiscountTerms(), masterDRM, "settlementDiscountCode");
        lkpSettlementDiscount.setPopup(new EMCLookupPopup(new CreditorsSettlementDiscountTerms(), "settlementDiscountTermId", masterUD));
        EMCLookupFormComponent lkpSalesGroup = new EMCLookupFormComponent(new DebtorsSalesGroupMenu(), masterDRM, "salesGroup");
        lkpSalesGroup.setPopup(new EMCLookupPopup(new DebtorsSalesGroup(), "salesGroup", masterUD));
        EMCLookupFormComponent lkpSalesRegion = new EMCLookupFormComponent(new DebtorsSalesRegionMenu(), masterDRM, "salesRegion");
        lkpSalesRegion.setPopup(new EMCLookupPopup(new DebtorsSalesRegion(), "salesRegion", masterUD));
        EMCLookupFormComponent lkpSalesArea = new EMCLookupFormComponent(new DebtorsSalesAreaMenu(), masterDRM, "salesArea");
        lkpSalesArea.setPopup(new EMCLookupPopup(new DebtorsSalesArea(), "salesArea", masterUD));
        EMCLookupFormComponent lkpPriceGroup = new EMCLookupFormComponent(new PriceGroups(), masterDRM, "pricingGroup");
        lkpPriceGroup.setPopup(new EMCLookupPopup(new POPPriceGroup(), "priceGroupId", masterUD));
        EMCLookupFormComponent lkpDiscountGroup = new EMCLookupFormComponent(new DiscountGroups(), masterDRM, "discountGroup");
        lkpDiscountGroup.setPopup(new EMCLookupPopup(new POPDiscountGroup(), "discountGroupId", masterUD));
        EMCLookupFormComponent lkpExtraChargeGroup = new EMCLookupFormComponent(new ExtraChargeGroups(), masterDRM, "extraChargeGroup");
        lkpExtraChargeGroup.setPopup(new EMCLookupPopup(new POPExtraChargeGroup(), "extraChargeGroupId", masterUD));
        txtVatNo.setEditable(false);
        lkpTermsOfPayment.setEnabled(false);
        lkpVatCode.setEnabled(false);
        lkpSettlementDiscount.setEnabled(false);
        lkpSalesGroup.setEnabled(false);
        lkpPriceGroup.setEnabled(false);
        lkpSalesArea.setEnabled(false);
        lkpDiscountGroup.setEnabled(false);
        lkpSalesRegion.setEnabled(false);
        lkpExtraChargeGroup.setEnabled(false);
        Component[][] comp = {{new emcJLabel("VAT No"), txtVatNo, new emcJLabel("Terms Of Payment"), lkpTermsOfPayment},
            {new emcJLabel("VAT Code"), lkpVatCode, new emcJLabel("Settlement Discount"), lkpSettlementDiscount},
            {new emcJLabel()},
            {new emcJLabel("Sales Group"), lkpSalesGroup, new emcJLabel("Price Group"), lkpPriceGroup},
            {new emcJLabel("Sales Area"), lkpSalesArea, new emcJLabel("Discount Group"), lkpDiscountGroup},
            {new emcJLabel("Sales Region"), lkpSalesRegion, new emcJLabel("Exta Charge Group"), lkpExtraChargeGroup}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Price/Pmnt");
    }

    private Component masterCommentsTab() {
        emcJTextArea txaComments = new emcJTextArea(new EMCStringFormDocument(masterDRM, "comments"));
        txaComments.setEditable(false);
        Component[][] comp = {{txaComments, new emcJLabel("Comments")}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Comments");
    }

    private emcJPanel linesPane() {
        emcJPanel thePane = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", linesOverviewTab());
        tabbed.add("Price", linesPricingTab());
        thePane.add(tabbed, BorderLayout.CENTER);
        return thePane;
    }

    private emcTablePanelUpdate linesOverviewTab() {
        List keys = new ArrayList();
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("warehouse");
        keys.add("quantity");
        keys.add("uom");
        keys.add("price");
        keys.add("discountPerc");
        keys.add("cancelReason");
        emcTableModelUpdate model = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        linesDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        linesDRM.setTablePanel(tableScroll);
        return tableScroll;
    }

    private Component linesPricingTab() {
        EMCLookupFormComponent lkpVatCode = new EMCLookupFormComponent(new GLVATCode(), linesDRM, "vatCode");
        lkpVatCode.setPopup(new EMCLookupPopup(new emc.entity.gl.GLVATCode(), "vatId", linesUD));
        emcJTextField txtVatAmount = new emcJTextField(new EMCBigDecimalFormDocument(linesDRM, "vatAmount"));
        emcJTextField txtStdUnitPrice = new emcJTextField(new EMCBigDecimalFormDocument(linesDRM, "stdUnitPrice"));
        emcJTextField txtCostAtAverage = new emcJTextField(new EMCBigDecimalFormDocument(linesDRM, "costAtAverage"));
        emcJTextField txtCostAdjustment = new emcJTextField(new EMCBigDecimalFormDocument(linesDRM, "costAdjustment"));
        EMCDatePickerFormComponent dpRequiredDate = new EMCDatePickerFormComponent(linesDRM, "requiredDate");
        lkpVatCode.setEnabled(false);
        txtVatAmount.setEditable(false);
        txtStdUnitPrice.setEditable(false);
        txtCostAtAverage.setEditable(false);
        txtCostAdjustment.setEditable(false);
        dpRequiredDate.setEnabled(false);
        Component[][] comp = {{new emcJLabel("VAT Code"), lkpVatCode, new emcJLabel("VAT Amount"), txtVatAmount},
            {new emcJLabel()},
            {new emcJLabel("Std. Unit Price"), txtStdUnitPrice},
            {new emcJLabel("Cost @ Average"), txtCostAtAverage},
            {new emcJLabel("Cost Adjustment"), txtCostAdjustment},
            {new emcJLabel()},
            {new emcJLabel("Required Date"), dpRequiredDate}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Pricing");
    }
}

/*
 * To change emcJLabel  template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.creditors.display.invoice;

import emc.app.components.dialogs.EMCDialogFactory;
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
import emc.app.components.inventory.register.EMCRegisterButton;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.lookup.popup.EMCMultiValuePopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.base.BasePostalCodes;
import emc.entity.base.BaseUnitsOfMeasure;
import emc.entity.creditors.CreditorsCreditNoteInvoiceMaster;
import emc.entity.creditors.CreditorsSettlementDiscountTerms;
import emc.entity.creditors.CreditorsTermsOfPayment;
import emc.entity.creditors.datasource.CreditorsCreditNoteInvoiceLinesDS;
import emc.entity.creditors.datasource.CreditorsCreditNoteInvoiceMasterDS;
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
import emc.entity.pop.POPDiscountGroup;
import emc.entity.pop.POPExtraChargeGroup;
import emc.entity.pop.POPPriceGroup;
import emc.entity.pop.POPSuppliers;
import emc.enums.creditors.CreditorsCreditNoteInvoiceType;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.register.RegisterFormTypeEnum;
import emc.enums.inventory.register.RegisterFromTypeEnum;
import emc.forms.creditors.display.invoice.resources.CreditorsInvoiceLinesDRM;
import emc.forms.creditors.display.invoice.resources.CreditorsInvoiceLinesLRM;
import emc.forms.creditors.display.invoice.resources.CreditorsInvoiceMasterDRM;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.PostalCodes;
import emc.menus.base.menuItems.display.UnitsOfMeasure;
import emc.menus.creditors.menuitems.display.SettlementDiscountTerms;
import emc.menus.creditors.menuitems.display.TermsOfPayment;
import emc.menus.gl.menuitems.display.GLVATCode;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.inventory.menuitems.display.ItemBatch;
import emc.menus.inventory.menuitems.display.ItemSerial;
import emc.menus.inventory.menuitems.display.LocationMenu;
import emc.menus.inventory.menuitems.display.PalletMenu;
import emc.menus.inventory.menuitems.display.Warehouse;
import emc.menus.pop.menuitems.display.DiscountGroups;
import emc.menus.pop.menuitems.display.ExtraChargeGroups;
import emc.menus.pop.menuitems.display.PriceGroups;
import emc.menus.pop.menuitems.display.Suppliers;
import emc.methods.creditors.ServerCreditorsMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

/**
 *
 * @author wikus
 */
public class CreditorsInvoiceForm extends BaseInternalFrame {

    private EMCUserData masterUD;
    private CreditorsInvoiceMasterDRM masterDRM;
    private EMCUserData linesUD;
    private CreditorsInvoiceLinesDRM linesDRM;
    //Lookups for LRM
    private EMCLookupJTableComponent lkpItem;
    private EMCLookupJTableComponent lkpDimension1;
    private EMCLookupJTableComponent lkpDimension2;
    private EMCLookupJTableComponent lkpDimension3;
    private EMCLookupFormComponent frmLkpItem;
    private EMCLookupFormComponent frmLkpDimension1;
    private EMCLookupFormComponent frmLkpDimension2;
    private EMCLookupFormComponent frmLkpDimension3;
    private EMCLookupFormComponent frmLkpWarehouse;
    private EMCLookupFormComponent frmLkpBatch;
    private EMCLookupFormComponent frmLkpSerial;
    private EMCLookupFormComponent frmLkpLocation;
    private EMCLookupFormComponent frmLkpPallet;

    public CreditorsInvoiceForm(EMCUserData userData) {
        super("Creditors Invoice", true, true, true, true, userData);
        this.setBounds(20, 20, 850, 600);
        //Master
        masterUD = userData.copyUserDataAndDataList();

        if (masterUD.getUserData(0) == null) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CreditorsCreditNoteInvoiceMaster.class);
            query.openConditionBracket(EMCQueryBracketConditions.NONE);
            query.addOr("creditNoteInvoiceType", CreditorsCreditNoteInvoiceType.MANUAL_INVOICE.toString());
            query.addOr("creditNoteInvoiceType", CreditorsCreditNoteInvoiceType.PURCHASING_INVOICE.toString());
            query.closeConditionBracket();
            masterUD.setUserData(0, query);
        }

        masterDRM = new CreditorsInvoiceMasterDRM(new emcGenericDataSourceUpdate(new CreditorsCreditNoteInvoiceMasterDS(), masterUD), masterUD);
        masterDRM.setTheForm(this);
        this.setDataManager(masterDRM);
        masterDRM.setFormTextId1("creditNoteInvoiceNumber");
        masterDRM.setFormTextId2("supplierId");
        //Lines
        linesUD = userData.copyUserData();
        StockDRMParameters param = new StockDRMParameters("itemId", "dimension1", "dimension2", "dimension3", "inventoryTransRef", "serial", "batch", "warehouse", "location");
        linesDRM = new CreditorsInvoiceLinesDRM(new emcGenericDataSourceUpdate(new CreditorsCreditNoteInvoiceLinesDS(), linesUD), param, linesUD);
        linesDRM.setTheForm(this);
        linesDRM.setFormTextId1("lineNo");
        linesDRM.setFormTextId2("itemReference");
        //Link
        masterDRM.setLinesTable(linesDRM);
        masterDRM.setHeaderColumnID("creditNoteInvoiceNumber");
        linesDRM.setHeaderTable(masterDRM);
        linesDRM.setRelationColumnToHeader("creditNoteInvoiceNumber");
        //Form
        emcJSplitPane masterLineSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, masterPane(), linesPane());
        masterLineSplit.setDividerLocation(250);
        this.setContentPane(masterLineSplit);
    }

    private emcJPanel masterPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", masterOverviewPane());
        tabbed.add("Address", masterAddressPane());
        tabbed.add("Price/Payment", masterPricePaymentPane());
        tabbed.add("Banking", masterBankingPane());
        tabbed.add("Additional", masterAdditionalTab());
        tabbed.add("Comments", masterCommentsTab());

        emcJPanel thePane = new emcJPanel(new BorderLayout());
        thePane.add(tabbed, BorderLayout.CENTER);
        thePane.add(masterButtonPane(), BorderLayout.EAST);

        return thePane;
    }

    private emcTablePanelUpdate masterOverviewPane() {
        List<String> keys = new ArrayList<String>();
        keys.add("creditNoteInvoiceNumber");
        keys.add("creditNoteInvoiceDate");
        keys.add("creditNoteInvoiceType");
        keys.add("supplierId");
        keys.add("supplierName");
        keys.add("stockCreditNoteInvoice");
        keys.add("creditNoteInvoiceStatus");
        emcTableModelUpdate model = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupMasterTableLookups(table);
        masterDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        masterDRM.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupMasterTableLookups(emcJTableUpdate table) {
        table.setColumnEditable("creditNoteInvoiceType", false);

        EMCLookupJTableComponent lkpSupplier = new EMCLookupJTableComponent(new Suppliers());
        lkpSupplier.setPopup(new EMCLookupPopup(new POPSuppliers(), "supplierId", masterUD));
        table.setLookupToColumn("supplierId", lkpSupplier);

        table.setColumnEditable("supplierName", false);

        table.setColumnEditable("creditNoteInvoiceStatus", false);
    }

    private emcJPanel masterAddressPane() {
        Map<String, String> fieldMap = new HashMap<String, String>();
        fieldMap.put("addressPhysicalLine2", "suburb");
        fieldMap.put("addressPhysicalLine3", "city");
        fieldMap.put("addressPhysicalLine4", "provence");
        fieldMap.put("addressPhysicalLine5", "country");
        fieldMap.put("physicalPostalCode", "code");
        emcJTextField txtPhysicalStreetAdress = new emcJTextField(new EMCStringFormDocument(masterDRM, "addressPhysicalLine1"));
        EMCLookupFormComponent lkpPhysicalSuburb = new EMCLookupFormComponent(new PostalCodes(), masterDRM, "addressPhysicalLine2");
        lkpPhysicalSuburb.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "suburb", masterDRM, fieldMap, masterUD));
        EMCLookupFormComponent lkpPhysicalCity = new EMCLookupFormComponent(new PostalCodes(), masterDRM, "addressPhysicalLine3");
        lkpPhysicalCity.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "city", masterDRM, fieldMap, masterUD));
        EMCLookupFormComponent lkpPhysicalProvence = new EMCLookupFormComponent(new PostalCodes(), masterDRM, "addressPhysicalLine4");
        lkpPhysicalProvence.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "provence", masterDRM, fieldMap, masterUD));
        EMCLookupFormComponent lkpPhysicalCountry = new EMCLookupFormComponent(new PostalCodes(), masterDRM, "addressPhysicalLine5");
        lkpPhysicalCountry.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "country", masterDRM, fieldMap, masterUD));
        EMCLookupFormComponent lkpPhysicalCode = new EMCLookupFormComponent(new PostalCodes(), masterDRM, "addressPhysPostalCode");
        lkpPhysicalCode.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "code", masterDRM, fieldMap, masterUD));
        Component[][] leftCom = {{new emcJLabel("Street"), txtPhysicalStreetAdress},
            {new emcJLabel("Suburb"), lkpPhysicalSuburb},
            {new emcJLabel("City"), lkpPhysicalCity},
            {new emcJLabel("Province"), lkpPhysicalProvence},
            {new emcJLabel("Country"), lkpPhysicalCountry},
            {new emcJLabel("Postal Code"), lkpPhysicalCode}};
        emcJPanel leftPane = emcSetGridBagConstraints.createSimplePanel(leftCom, GridBagConstraints.NONE, true, "Physical Address");

        fieldMap = new HashMap<String, String>();
        fieldMap.put("postalAddressLine2", "suburb");
        fieldMap.put("postalAddressLine3", "city");
        fieldMap.put("postalAddressLine4", "provence");
        fieldMap.put("postalAddressLine5", "country");
        fieldMap.put("postalPostalCode", "code");
        emcJTextField txtPostalStreetAdress = new emcJTextField(new EMCStringFormDocument(masterDRM, "postalAddressLine1"));
        EMCLookupFormComponent lkpPostalSuburb = new EMCLookupFormComponent(new PostalCodes(), masterDRM, "postalAddressLine2");
        lkpPostalSuburb.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "suburb", masterDRM, fieldMap, masterUD));
        EMCLookupFormComponent lkpPostalCity = new EMCLookupFormComponent(new PostalCodes(), masterDRM, "postalAddressLine3");
        lkpPostalCity.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "city", masterDRM, fieldMap, masterUD));
        EMCLookupFormComponent lkpPostalProvence = new EMCLookupFormComponent(new PostalCodes(), masterDRM, "postalAddressLine4");
        lkpPostalProvence.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "provence", masterDRM, fieldMap, masterUD));
        EMCLookupFormComponent lkpPostalCountry = new EMCLookupFormComponent(new PostalCodes(), masterDRM, "postalAddressLine5");
        lkpPostalCountry.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "country", masterDRM, fieldMap, masterUD));
        EMCLookupFormComponent lkpPostalCode = new EMCLookupFormComponent(new PostalCodes(), masterDRM, "postalCode");
        lkpPostalCode.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "code", masterDRM, fieldMap, masterUD));
        Component[][] rightCom = {{new emcJLabel("Street"), txtPostalStreetAdress},
            {new emcJLabel("Suburb"), lkpPostalSuburb},
            {new emcJLabel("City"), lkpPostalCity},
            {new emcJLabel("Province"), lkpPostalProvence},
            {new emcJLabel("Country"), lkpPostalCountry},
            {new emcJLabel("Postal Code"), lkpPostalCode}};
        emcJPanel rightPane = emcSetGridBagConstraints.createSimplePanel(rightCom, GridBagConstraints.NONE, true, "Postal Address");

        EMCQuery suburbQuery = new EMCQuery(enumQueryTypes.SELECT, BasePostalCodes.class);
        suburbQuery.addOrderBy("suburb");
        suburbQuery.addOrderBy("country");
        suburbQuery.addOrderBy("provence");
        suburbQuery.addOrderBy("city");
        suburbQuery.addOrderBy("code");
        lkpPhysicalSuburb.setTheQuery(suburbQuery);
        lkpPostalSuburb.setTheQuery(suburbQuery);

        EMCQuery cityQuery = new EMCQuery(enumQueryTypes.SELECT, BasePostalCodes.class);
        cityQuery.addOrderBy("city");
        cityQuery.addOrderBy("country");
        cityQuery.addOrderBy("provence");
        cityQuery.addOrderBy("suburb");
        cityQuery.addOrderBy("code");
        lkpPhysicalCity.setTheQuery(cityQuery);
        lkpPostalCity.setTheQuery(cityQuery);

        EMCQuery provenceQuery = new EMCQuery(enumQueryTypes.SELECT, BasePostalCodes.class);
        provenceQuery.addOrderBy("provence");
        provenceQuery.addOrderBy("country");
        provenceQuery.addOrderBy("city");
        provenceQuery.addOrderBy("suburb");
        provenceQuery.addOrderBy("code");
        lkpPhysicalProvence.setTheQuery(provenceQuery);
        lkpPostalProvence.setTheQuery(provenceQuery);

        EMCQuery countryQuery = new EMCQuery(enumQueryTypes.SELECT, BasePostalCodes.class);
        countryQuery.addOrderBy("country");
        countryQuery.addOrderBy("provence");
        countryQuery.addOrderBy("city");
        countryQuery.addOrderBy("suburb");
        countryQuery.addOrderBy("code");
        lkpPhysicalCountry.setTheQuery(countryQuery);
        lkpPostalCountry.setTheQuery(countryQuery);

        Component[][] comp = {{leftPane, rightPane}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel masterPricePaymentPane() {
        emcYesNoComponent ynVatApplicable = new emcYesNoComponent(masterDRM, "vatApplicable");

        EMCLookupFormComponent lkpVatCode = new EMCLookupFormComponent(new GLVATCode(), masterDRM, "vatCode");
        lkpVatCode.setPopup(new EMCLookupPopup(new emc.entity.gl.GLVATCode(), "vatId", masterUD));

        emcJTextField txtRegistrationNo = new emcJTextField(new EMCStringFormDocument(masterDRM, "vatRegistrationNo"));

        EMCLookupFormComponent lkpPriceGroup = new EMCLookupFormComponent(new PriceGroups(), masterDRM, "priceGroup");
        lkpPriceGroup.setPopup(new EMCLookupPopup(new POPPriceGroup(), "priceGroupId", masterUD));

        EMCLookupFormComponent lkpDiscountGroup = new EMCLookupFormComponent(new DiscountGroups(), masterDRM, "discountGroup");
        lkpDiscountGroup.setPopup(new EMCLookupPopup(new POPDiscountGroup(), "discountGroupId", masterUD));

        EMCLookupFormComponent lkpExtraChargeGroup = new EMCLookupFormComponent(new ExtraChargeGroups(), masterDRM, "extraChargeGroup");
        lkpExtraChargeGroup.setPopup(new EMCLookupPopup(new POPExtraChargeGroup(), "extraChargeGroup", masterUD));

        EMCLookupFormComponent lkpTermsOfPayment = new EMCLookupFormComponent(new TermsOfPayment(), masterDRM, "termsOfPayment");
        lkpTermsOfPayment.setPopup(new EMCLookupPopup(new CreditorsTermsOfPayment(), "termsOfPaymentId", masterUD));

        EMCDatePickerFormComponent dpPaymentDueDate = new EMCDatePickerFormComponent(masterDRM, "paymentDueDate");

        EMCLookupFormComponent lkpSettlementDiscount = new EMCLookupFormComponent(new SettlementDiscountTerms(), masterDRM, "settlementDiscount");
        lkpSettlementDiscount.setPopup(new EMCLookupPopup(new CreditorsSettlementDiscountTerms(), "settlementDiscountTermId", masterUD));

        EMCDatePickerFormComponent dpSettlementDiscountDate = new EMCDatePickerFormComponent(masterDRM, "settlementDiscDate");

        Component[][] comp = {{new emcJLabel(masterDRM.getFieldEmcLabel("vatApplicable")), ynVatApplicable, new emcJLabel(masterDRM.getFieldEmcLabel("priceGroup")), lkpPriceGroup},
            {new emcJLabel(masterDRM.getFieldEmcLabel("vatCode")), lkpVatCode, new emcJLabel(masterDRM.getFieldEmcLabel("discountGroup")), lkpDiscountGroup},
            {new emcJLabel(masterDRM.getFieldEmcLabel("vatRegistrationNo")), txtRegistrationNo, new emcJLabel(masterDRM.getFieldEmcLabel("extraChargeGroup")), lkpExtraChargeGroup},
            {new emcJLabel()},
            {new emcJLabel(masterDRM.getFieldEmcLabel("termsOfPayment")), lkpTermsOfPayment, new emcJLabel(masterDRM.getFieldEmcLabel("settlementDiscount")), lkpSettlementDiscount},
            {new emcJLabel(masterDRM.getFieldEmcLabel("paymentDueDate")), dpPaymentDueDate, new emcJLabel(masterDRM.getFieldEmcLabel("settlementDiscDate")), dpSettlementDiscountDate}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Price/Payment");
    }

    private Component masterBankingPane() {
        emcJTextField txtBank = new emcJTextField(new EMCStringFormDocument(masterDRM, "supplierBank"));
        emcJTextField txtBranch = new emcJTextField(new EMCStringFormDocument(masterDRM, "supplierBankBranchCode"));
        emcJTextField txtAccountName = new emcJTextField(new EMCStringFormDocument(masterDRM, "supplierBankAccountName"));
        emcJTextField txtAccountNumber = new emcJTextField(new EMCStringFormDocument(masterDRM, "supplierBankAccountNumber"));

        Component[][] comp = {{new emcJLabel(masterDRM.getFieldEmcLabel("supplierBank")), txtBank},
            {new emcJLabel(masterDRM.getFieldEmcLabel("supplierBankBranchCode")), txtBranch},
            {new emcJLabel(masterDRM.getFieldEmcLabel("supplierBankAccountName")), txtAccountName},
            {new emcJLabel(masterDRM.getFieldEmcLabel("supplierBankAccountNumber")), txtAccountNumber}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Banking");
    }

    private emcJPanel masterAdditionalTab() {
        emcJTextField txtApprovedBy = new emcJTextField(new EMCStringFormDocument(masterDRM, "approvedBy"));
        txtApprovedBy.setEditable(false);
        EMCDatePickerFormComponent dpApprovedDate = new EMCDatePickerFormComponent(masterDRM, "approvedDate");
        dpApprovedDate.setEnabled(false);
        emcJTextField txtApprovedTime = new emcJTextField();
        txtApprovedTime.setDocument(new EMCTimeFormDocument(txtApprovedTime, masterDRM, "approvedTime"));
        txtApprovedTime.setEditable(false);

        emcJTextField txtPostedBy = new emcJTextField(new EMCStringFormDocument(masterDRM, "postedBy"));
        txtPostedBy.setEditable(false);
        EMCDatePickerFormComponent dpPostedDate = new EMCDatePickerFormComponent(masterDRM, "postedDate");
        dpPostedDate.setEnabled(false);
        emcJTextField txtPostedTime = new emcJTextField();
        txtPostedTime.setDocument(new EMCTimeFormDocument(txtApprovedTime, masterDRM, "postedTime"));
        txtPostedTime.setEditable(false);

        emcJTextField txtPrintedBy = new emcJTextField(new EMCStringFormDocument(masterDRM, "printedBy"));
        txtPrintedBy.setEditable(false);
        EMCDatePickerFormComponent dpPrintedDate = new EMCDatePickerFormComponent(masterDRM, "printedDate");
        dpPrintedDate.setEnabled(false);
        emcJTextField txtPrintedTime = new emcJTextField();
        txtPrintedTime.setDocument(new EMCTimeFormDocument(txtApprovedTime, masterDRM, "printedTime"));
        txtPrintedTime.setEditable(false);

        emcJTextField txtLastPrintedBy = new emcJTextField(new EMCStringFormDocument(masterDRM, "lastPrintedBy"));
        txtLastPrintedBy.setEditable(false);
        EMCDatePickerFormComponent dpLastPrintedDate = new EMCDatePickerFormComponent(masterDRM, "lastPrintedDate");
        dpLastPrintedDate.setEnabled(false);
        emcJTextField txtLastPrintedTime = new emcJTextField();
        txtLastPrintedTime.setDocument(new EMCTimeFormDocument(txtApprovedTime, masterDRM, "lastPrintedTime"));
        txtLastPrintedTime.setEditable(false);

        Component[][] comp = {{new emcJLabel(masterDRM.getFieldEmcLabel("approvedBy")), txtApprovedBy, new emcJLabel(masterDRM.getFieldEmcLabel("postedBy")), txtPostedBy},
            {new emcJLabel(masterDRM.getFieldEmcLabel("approvedDate")), dpApprovedDate, new emcJLabel(masterDRM.getFieldEmcLabel("postedDate")), dpPostedDate},
            {new emcJLabel(masterDRM.getFieldEmcLabel("approvedTime")), txtApprovedTime, new emcJLabel(masterDRM.getFieldEmcLabel("postedTime")), txtPostedTime},
            {new emcJLabel()},
            {new emcJLabel(masterDRM.getFieldEmcLabel("printedBy")), txtPrintedBy, new emcJLabel(masterDRM.getFieldEmcLabel("lastPrintedBy")), txtLastPrintedBy},
            {new emcJLabel(masterDRM.getFieldEmcLabel("printedDate")), dpPrintedDate, new emcJLabel(masterDRM.getFieldEmcLabel("lastPrintedDate")), dpLastPrintedDate},
            {new emcJLabel(masterDRM.getFieldEmcLabel("printedTime")), txtPrintedTime, new emcJLabel(masterDRM.getFieldEmcLabel("lastPrintedTime")), txtLastPrintedTime}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Additional");
    }

    private emcJPanel masterCommentsTab() {
        emcJTextArea txaComments = new emcJTextArea(new EMCStringFormDocument(masterDRM, "comments"));

        Component[][] comp = {{txaComments, new emcJLabel(masterDRM.getFieldEmcLabel("comments"))}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Comments");
    }

    private emcJPanel masterButtonPane() {
        emcJButton btnPost = new emcJButton("Post") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                if (masterDRM.getLastUpdateStatus() && (Long) masterDRM.getLastFieldValueAt("recordID") != 0L) {
                    if (EMCDialogFactory.createQuestionDialog(this, "Post", "Are you sure you want to post the selected credit note/invoice?") == JOptionPane.YES_OPTION) {

                        EMCCommandClass cmd = new EMCCommandClass(ServerCreditorsMethods.POST_CREDITNOTE_INVOICE_MASTER);

                        List toSend = new ArrayList();
                        toSend.add(masterDRM.getLastFieldValueAt("creditNoteInvoiceNumber"));

                        toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);
                        if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                            Logger.getLogger("emc").log(Level.INFO, "Credit Note/Invoice Posted");
                            masterDRM.refreshRecord(masterDRM.getLastRowAccessed());
                        }
                    }
                }
            }
        };
        emcJButton btnApprove = new emcJButton("Approve") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                if (masterDRM.getLastUpdateStatus() && (Long) masterDRM.getLastFieldValueAt("recordID") != 0L) {
                    if (EMCDialogFactory.createQuestionDialog(this, "Approval", "Are you sure you want to approve the selected credit note/invoice?") == JOptionPane.YES_OPTION) {

                        EMCCommandClass cmd = new EMCCommandClass(ServerCreditorsMethods.APPROVE_CREDITNOTE_INVOICE_MASTER);

                        List toSend = new ArrayList();
                        toSend.add(masterDRM.getLastFieldValueAt("creditNoteInvoiceNumber"));

                        toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);
                        if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                            Logger.getLogger("emc").log(Level.INFO, "Credit Note/Invoice Approved");
                            masterDRM.refreshRecord(masterDRM.getLastRowAccessed());
                        }
                    }
                }
            }
        };

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnPost);
        buttonList.add(btnApprove);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private emcJPanel linesPane() {
        setupItemDimLookups();

        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", linesOverviewPane());
        tabbed.add("Pricing", linesPricingPane());
        tabbed.add("Dimensions", linesDimensionsPane());

        emcJPanel thePane = new emcJPanel(new BorderLayout());
        thePane.add(tabbed, BorderLayout.CENTER);
        thePane.add(linesButtonPane(), BorderLayout.EAST);

        return thePane;
    }

    private void setupItemDimLookups() {
        this.lkpItem = EMCItemLookupFactory.createItemLookup(linesUD);

        this.frmLkpItem = EMCItemLookupFactory.createItemFormLookup(linesUD, linesDRM, "itemReference");

        EMCLookupPopup dimension1Popup = new EMCLookupPopup(new InventoryItemDimension1SetupDS(), "dimensionId", linesUD);
        dimension1Popup.setPreferredWidth(500);
        this.frmLkpDimension1 = new EMCLookupFormComponent(new Dimension1(), linesDRM, "dimension1");
        this.frmLkpDimension1.setPopup(dimension1Popup);

        this.lkpDimension1 = new EMCLookupJTableComponent(new Dimension1());
        this.lkpDimension1.setPopup(dimension1Popup);

        EMCLookupPopup dimension2Popup = new EMCLookupPopup(new InventoryItemDimension2SetupDS(), "dimensionId", linesUD);
        this.frmLkpDimension2 = new EMCLookupFormComponent(new Dimension2(), linesDRM, "dimension2");
        this.frmLkpDimension2.setPopup(dimension2Popup);

        this.lkpDimension2 = new EMCLookupJTableComponent(new Dimension2());
        this.lkpDimension2.setPopup(dimension2Popup);

        EMCLookupPopup dimension3Popup = new EMCLookupPopup(new InventoryItemDimension3SetupDS(), "dimensionId", linesUD);
        this.frmLkpDimension3 = new EMCLookupFormComponent(new Dimension3(), linesDRM, "dimension3");
        this.frmLkpDimension3.setPopup(dimension3Popup);

        this.lkpDimension3 = new EMCLookupJTableComponent(new Dimension3());
        this.lkpDimension3.setPopup(dimension3Popup);

        EMCLookupPopup warehousePopup = new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", linesUD);
        this.frmLkpWarehouse = new EMCLookupFormComponent(new Warehouse(), linesDRM, "warehouse");
        this.frmLkpWarehouse.setPopup(warehousePopup);

        //Batch
        List<String> batchKeys = new ArrayList<String>();
        batchKeys.add("batch");

        EMCLookupPopup batchPopup = new EMCLookupPopup(new InventorySummary(),
                "batch", batchKeys, linesUD);
        this.frmLkpBatch = new EMCLookupFormComponent(new ItemBatch(), linesDRM, "batch");
        this.frmLkpBatch.setPopup(batchPopup);

        //Serial
        List<String> serialKeys = new ArrayList<String>();
        serialKeys.add("serialNo");

        EMCLookupPopup serialPopup = new EMCLookupPopup(new InventorySummary(), "serialNo", serialKeys, linesUD);
        this.frmLkpSerial = new EMCLookupFormComponent(new ItemSerial(), linesDRM, "serial");
        this.frmLkpSerial.setPopup(serialPopup);

        //Location
        List<String> locationKeys = new ArrayList<String>();
        locationKeys.add("location");

        EMCLookupPopup locationPopup = new EMCLookupPopup(new InventoryLocation(), "locationId", linesUD);
        this.frmLkpLocation = new EMCLookupFormComponent(new LocationMenu(), linesDRM, "location");
        this.frmLkpLocation.setPopup(locationPopup);

        //Pallet
        List<String> palletKeys = new ArrayList<String>();
        palletKeys.add("pallet");

        EMCLookupPopup palletPopup = new EMCLookupPopup(new InventorySummary(), "pallet", palletKeys, linesUD);
        this.frmLkpPallet = new EMCLookupFormComponent(new PalletMenu(), linesDRM, "pallet");
        this.frmLkpPallet.setPopup(palletPopup);

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

        lkpDimension1.setTheQuery(dimension1Query);
        lkpDimension2.setTheQuery(dimension2Query);
        lkpDimension3.setTheQuery(dimension3Query);
        frmLkpDimension1.setTheQuery(dimension1Query);
        frmLkpDimension2.setTheQuery(dimension2Query);
        frmLkpDimension3.setTheQuery(dimension3Query);

        EMCQuery locationQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class);
        locationQuery.addAnd("warehouseId", "");
        frmLkpLocation.setTheQuery(locationQuery);

        EMCQuery serialQuery = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        serialQuery.addAnd("serialNo", null, EMCQueryConditions.NOT);
        serialQuery.addTableAnd(InventoryDimensionTable.class.getName(), "itemDimId", InventorySummary.class.getName(), "recordID");
        serialQuery.addAnd("companyId", linesUD.getCompanyId());
        frmLkpSerial.setTheQuery(serialQuery);

        EMCQuery batchQuery = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        batchQuery.addAnd("batch", null, EMCQueryConditions.NOT);
        batchQuery.addTableAnd(InventoryDimensionTable.class.getName(), "itemDimId", InventorySummary.class.getName(), "recordID");
        batchQuery.addAnd("companyId", linesUD.getCompanyId());
        frmLkpBatch.setTheQuery(batchQuery);

        EMCQuery palletQuery = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        palletQuery.addAnd("pallet", null, EMCQueryConditions.NOT);
        palletQuery.addTableAnd(InventoryDimensionTable.class.getName(), "itemDimId", InventorySummary.class.getName(), "recordID");
        palletQuery.addAnd("companyId", linesUD.getCompanyId());
        frmLkpPallet.setTheQuery(palletQuery);

        CreditorsInvoiceLinesLRM linesLRM = new CreditorsInvoiceLinesLRM("itemId", "dimension1", "dimension2", "dimension3", "warehouse");

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

        linesLRM.addLookup(lkpDimension1, "tableDimension1");
        linesLRM.addLookup(lkpDimension2, "tableDimension2");
        linesLRM.addLookup(lkpDimension3, "tableDimension3");
    }

    private emcTablePanelUpdate linesOverviewPane() {
        List<String> keys = new ArrayList<String>();
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("quantity");
        keys.add("uom");
        keys.add("unitPrice");
        keys.add("discountPercentage");
        keys.add("totalCost");
        emcTableModelUpdate model = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLinesTableLookups(table);
        linesDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        linesDRM.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLinesTableLookups(emcJTableUpdate table) {
        table.setLookupToColumn("itemReference", lkpItem);
        table.setColumnEditable("itemDescription", false);
        table.setLookupToColumn("dimension1", lkpDimension1);
        table.setLookupToColumn("dimension2", lkpDimension2);
        table.setLookupToColumn("dimension3", lkpDimension3);

        EMCLookupPopup uomPopup = new EMCLookupPopup(new BaseUnitsOfMeasure(), "unit", linesUD);
        EMCLookupJTableComponent lkpUOM = new EMCLookupJTableComponent(new UnitsOfMeasure());
        lkpUOM.setPopup(uomPopup);
        table.setLookupToColumn("uom", lkpUOM);

        table.setColumnEditable("totalCost", false);
    }

    private emcJPanel linesPricingPane() {
        emcJTextField txtQuantity = new emcJTextField(new EMCBigDecimalFormDocument(linesDRM, "quantity"));

        emcJTextField txtStandardUnitPrice = new emcJTextField(new EMCBigDecimalFormDocument(linesDRM, "stdUnitPrice"));
        txtStandardUnitPrice.setEditable(false);
        emcJTextField txtUnitPrice = new emcJTextField(new EMCBigDecimalFormDocument(linesDRM, "unitPrice"));

        emcJTextField txtDiscountPerc = new emcJTextField(new EMCBigDecimalFormDocument(linesDRM, "discountPercentage"));
        emcJTextField txtDiscountAmount = new emcJTextField(new EMCBigDecimalFormDocument(linesDRM, "discountAmount"));
        txtDiscountAmount.setEditable(false);

        EMCLookupFormComponent lkpVatCode = new EMCLookupFormComponent(new GLVATCode(), linesDRM, "vatCode");
        lkpVatCode.setPopup(new EMCLookupPopup(new emc.entity.gl.GLVATCode(), "vatId", linesUD));
        emcJTextField txtVatAmount = new emcJTextField(new EMCBigDecimalFormDocument(linesDRM, "vatAmount"));
        txtVatAmount.setEditable(false);

        emcJTextField txtTotalCost = new emcJTextField(new EMCBigDecimalFormDocument(linesDRM, "totalCost"));
        txtTotalCost.setEditable(false);

        Component[][] comp = {{new emcJLabel(linesDRM.getFieldEmcLabel("quantity")), txtQuantity},
            {new emcJLabel(linesDRM.getFieldEmcLabel("unitPrice")), txtUnitPrice, new emcJLabel(linesDRM.getFieldEmcLabel("stdUnitPrice")), txtStandardUnitPrice},
            {new emcJLabel(linesDRM.getFieldEmcLabel("discountPercentage")), txtDiscountPerc, new emcJLabel(linesDRM.getFieldEmcLabel("discountAmount")), txtDiscountAmount},
            {new emcJLabel(linesDRM.getFieldEmcLabel("vatCode")), lkpVatCode, new emcJLabel(linesDRM.getFieldEmcLabel("vatAmount")), txtVatAmount},
            {new emcJLabel(linesDRM.getFieldEmcLabel("totalCost")), txtTotalCost}
        };

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Pricing");
    }

    private emcJPanel linesDimensionsPane() {
        Component[][] components = new Component[][]{
            {new emcJLabel(linesDRM.getFieldEmcLabel("itemReference")), frmLkpItem},
            {new emcJLabel(linesDRM.getFieldEmcLabel("dimension1")), frmLkpDimension1},
            {new emcJLabel(linesDRM.getFieldEmcLabel("dimension2")), frmLkpDimension2},
            {new emcJLabel(linesDRM.getFieldEmcLabel("dimension3")), frmLkpDimension3},
            {new emcJLabel(linesDRM.getFieldEmcLabel("warehouse")), frmLkpWarehouse},
            {new emcJLabel(linesDRM.getFieldEmcLabel("location")), frmLkpLocation},
            {new emcJLabel(linesDRM.getFieldEmcLabel("batch")), frmLkpBatch},
            {new emcJLabel(linesDRM.getFieldEmcLabel("serial")), frmLkpSerial},
            {new emcJLabel(linesDRM.getFieldEmcLabel("pallet")), frmLkpPallet}};

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true, "Dimensions");
    }

    private emcJPanel linesButtonPane() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();

        buttonList.add(new emcStockButton(this, true, new boolean[]{false, true, true}));
        buttonList.add(new EMCRegisterButton(RegisterFormTypeEnum.FIRST_TIME, RegisterFromTypeEnum.DEBTORS_RECEIVE, linesDRM, "creditNoteInvoiceNumber", "inventoryTransRef", "itemId", "itemDescription", "dimension1", "dimension2", "dimension3", "warehouse", "quantity", "itemReference"));

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.suppliers;

import emc.app.components.documents.EMCDoubleFormDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcMenuButton;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.debtors.DebtorsClosedReason;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.debtors.menuitems.display.DebtorsCreditCloseReasonMenu;
import emc.menus.inventory.menuitems.display.SupplierReferenceSupp;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author riaan
 */
public class SuppliersForm extends BaseInternalFrame {

    //Panels used on form
    private emcJPanel overviewPanel = new emcJPanel();
    private emcJPanel contactPanel = new emcJPanel();
    private emcJPanel addressPanel = new emcJPanel();
    private emcJPanel tradingPanel = new emcJPanel();
    private emcJPanel bankPanel = new emcJPanel();
    //Labels used on the form
    //Bank tab
    private emcJLabel lblBankName = new emcJLabel("Bank Name");
    private emcJLabel lblBankBranchCode = new emcJLabel("Bank Branch Code");
    private emcJLabel lblBankAccountNo = new emcJLabel("Bank Account No");
    private emcJLabel lblBankAccountName = new emcJLabel("Bank Account Name");
    //Address tab  
    private emcJLabel lblPhysicalAdrs1 = new emcJLabel("Address Line1");
    private emcJLabel lblPhysicalAdrs2 = new emcJLabel("Address Line2");
    private emcJLabel lblPhysicalAdrs3 = new emcJLabel("Address Line3");
    private emcJLabel lblPhysicalAdrs4 = new emcJLabel("Address Line4");
    private emcJLabel lblPhysicalAdrs5 = new emcJLabel("Address Line5");
    private emcJLabel lblPostalAdrs1 = new emcJLabel("Address Line1");
    private emcJLabel lblPostalAdrs2 = new emcJLabel("Address Line2");
    private emcJLabel lblPostalAdrs3 = new emcJLabel("Address Line3");
    private emcJLabel lblPostalAdrs4 = new emcJLabel("Address Line4");
    private emcJLabel lblPostalAdrs5 = new emcJLabel("Address Line5");
    private emcJLabel lblPhysPostCode = new emcJLabel("Postal Code");
    private emcJLabel lblPostCode = new emcJLabel("Postal Code");
    //Trading tab
    private emcJLabel lblTerms = new emcJLabel("Terms");
    private emcJLabel lblSettleDisc = new emcJLabel("Settle Discount");
    private emcJLabel lblCreditLimit = new emcJLabel("Credit Limit");
    private emcJLabel lblVatRegistration = new emcJLabel("VAT Registration");
    private emcJLabel lblVatApplicable;
    private emcJLabel lblCurrency = new emcJLabel("Currency");
    private emcJLabel lblPriceGroup = new emcJLabel("Price Group");
    private emcJLabel lblDiscGroup = new emcJLabel("Discount Group");
    private emcJLabel lblExtraChargeGroup = new emcJLabel("Extra Charge Group");
    private emcJLabel lblLTime = new emcJLabel("L/Time(Days)");
    //Contact tab
    private emcJLabel lblId = new emcJLabel("ID");
    private emcJLabel lblContactName = new emcJLabel("Name");
    private emcJLabel lblGroup = new emcJLabel("Group");
    private emcJLabel lblTelephone = new emcJLabel("Telephone");
    private emcJLabel lblCellNo = new emcJLabel("Cell No");
    private emcJLabel lblEmergencyNo = new emcJLabel("Emergency No");
    private emcJLabel lblFaxNo = new emcJLabel("Fax No");
    private emcJLabel lblEmail = new emcJLabel("Email");
    private emcJLabel lblWebsite = new emcJLabel("Website");
    private emcJLabel lblOrderContactName = new emcJLabel("Name");
    private emcJLabel lblOrderContactTelephone = new emcJLabel("Telephone");
    private emcJLabel lblOrderContactEmail = new emcJLabel("Email");
    private emcJLabel lblAccountContactName = new emcJLabel("Name");
    private emcJLabel lblAccountContactTelephone = new emcJLabel("Telephone");
    private emcJLabel lblAccountContactEmail = new emcJLabel("Email");
    private emcJLabel lblOrderContactCellphone = new emcJLabel("Cellphone");
    private emcJLabel lblAccountContactCellphoneNo = new emcJLabel("Cellphone");
    //Text Fields used on the form
    //Bank tab
    private emcJTextField txtBankName = new emcJTextField();
    private emcJTextField txtBankBranchCode = new emcJTextField();
    private emcJTextField txtBankAccountNo = new emcJTextField();
    private emcJTextField txtBankAccountName = new emcJTextField();
    //Address tab
    private emcJTextField txtPostalAdrs1 = new emcJTextField();
    private emcJTextField txtPostalAdrs2 = new emcJTextField();
    private emcJTextField txtPostalAdrs3 = new emcJTextField();
    private emcJTextField txtPostalAdrs4 = new emcJTextField();
    private emcJTextField txtPostalAdrs5 = new emcJTextField();
    private emcJTextField txtPhysicalAdrs1 = new emcJTextField();
    private emcJTextField txtPhysicalAdrs2 = new emcJTextField();
    private emcJTextField txtPhysicalAdrs3 = new emcJTextField();
    private emcJTextField txtPhysicalAdrs4 = new emcJTextField();
    private emcJTextField txtPhysicalAdrs5 = new emcJTextField();
    //Trading tab
    private emcJTextField txtCreditLimit = new emcJTextField();
    private emcJTextField txtVatRegistration = new emcJTextField();
    private emcJTextField txtLTime = new emcJTextField();
    //Contact tab
    private emcJTextField txtId = new emcJTextField();
    private emcJTextField txtContactName = new emcJTextField();
    private emcJTextField txtTelephone = new emcJTextField();
    private emcJTextField txtCellNo = new emcJTextField();
    private emcJTextField txtEmergencyNo = new emcJTextField();
    private emcJTextField txtFaxNo = new emcJTextField();
    private emcJTextField txtEmail = new emcJTextField();
    private emcJTextField txtWebsite = new emcJTextField();
    private emcJTextField txtOrderContactName = new emcJTextField();
    private emcJTextField txtOrderContactTelephone = new emcJTextField();
    private emcJTextField txtOrderContactEmail = new emcJTextField();
    private emcJTextField txtAccountContactName = new emcJTextField();
    private emcJTextField txtAccountContactTelephone = new emcJTextField();
    private emcJTextField txtAccountContactEmail = new emcJTextField();
    private emcJTextField txtOrderContactCellphone = new emcJTextField();
    private emcJTextField txtAccountContactCellphoneNo = new emcJTextField();
    //Lookups
    private EMCLookupJTableComponent tblLookupSupplierGroup;
    private EMCLookupTableCellEditor supplierGroupEditor;
    private EMCLookupFormComponent lookupSupplierGroup;
    private EMCLookupJTableComponent tblLookupCurrency;
    private EMCLookupTableCellEditor currencyEditor;
    private EMCLookupFormComponent lookupTradingCurrency;
    private EMCLookupFormComponent lookupTradingPriceGroup;
    private EMCLookupFormComponent lookupTradingDiscountGroup;
    private EMCLookupFormComponent lookupTradingExtraChargeGroup;
    private EMCLookupFormComponent lookupTradingTermsOfPayment;
    private EMCLookupFormComponent lookupTradingSettlementDiscountTerms;
    private emcYesNoComponent ysnVatApplicable;
    private EMCLookupFormComponent physPostCodeLookup;
    private EMCLookupFormComponent postCodeLookup;
    //DataSource
    private SuppliersDRM dataRelation;
    //Copy of user data
    EMCUserData copyUD;

    /** Creates a new instance of SuppliersForm */
    public SuppliersForm(EMCUserData userData) {
        //Calls the super class constructor
        super("Suppliers", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 400);

        //Sets up the data relation manager for this form
        dataRelation = new SuppliersDRM(new emcGenericDataSourceUpdate(
                enumEMCModules.POP.getId(), new emc.entity.pop.POPSuppliers(), userData), userData);
        this.setDataManager(dataRelation);

        //Creates a copy of the given user data
        copyUD = userData.copyUserData();
        copyUD.setUserData(null);

        //Add the form to the data relation manager
        dataRelation.setTheForm(this);
        dataRelation.setFormTextId1("supplierId");
        dataRelation.setFormTextId2("supplierName");

        //Sets up lookups
        setupLookups();

        //Sets the documents on the form
        setDocuments();
        setupComponents();
        //Initializes the frame
        initFrame();
    }

    private void setupComponents() {
        lblVatApplicable = new emcJLabel(dataRelation.getFieldEmcLabel("vatApplicable"));
        ysnVatApplicable = new emcYesNoComponent(dataRelation, "vatApplicable");
    }

    //This method is used to configure lookups
    private void setupLookups() {


        //Supplier Group Lookup
        //Keys for the supplier group lookup
        List supGroupKeys = new ArrayList<String>();
        supGroupKeys.add("supplierGroupId");
        supGroupKeys.add("description");

        tblLookupSupplierGroup = new EMCLookupJTableComponent(new emc.menus.pop.menuitems.display.SupplierGroups());
        EMCLookupPopup supplierGroupPopup = new EMCLookupPopup(enumEMCModules.POP.getId(), new emc.entity.pop.POPSupplierGroup(),
                "supplierGroupId", supGroupKeys, copyUD);
        tblLookupSupplierGroup.setPopup(supplierGroupPopup);
        supplierGroupEditor = new EMCLookupTableCellEditor(tblLookupSupplierGroup);

        lookupSupplierGroup = new EMCLookupFormComponent(new emc.menus.pop.menuitems.display.SupplierGroups(),
                dataRelation, "supplierGroup");
        lookupSupplierGroup.setPopup(supplierGroupPopup);

        //Keys for the currency lookup on the table
        List currencyKeys = new ArrayList();
        currencyKeys.add("currency");
        currencyKeys.add("descriptiveName");

        tblLookupCurrency = new EMCLookupJTableComponent(new emc.menus.gl.menuitems.display.GLCurrency());
        EMCLookupPopup currencyPopup = new EMCLookupPopup(enumEMCModules.GENERAL_LEDGER.getId(), new emc.entity.gl.GLCurrency(),
                "currency", currencyKeys, copyUD);
        tblLookupCurrency.setPopup(currencyPopup);
        currencyEditor = new EMCLookupTableCellEditor(tblLookupCurrency);

        lookupTradingCurrency = new EMCLookupFormComponent(new emc.menus.gl.menuitems.display.GLCurrency(), dataRelation,
                "supplierCurrency");
        lookupTradingCurrency.setPopup(currencyPopup);

        //Keys for the price group lookup
        List priceGroupKeys = new ArrayList();
        priceGroupKeys.add("priceGroupId");
        priceGroupKeys.add("description");

        lookupTradingPriceGroup = new EMCLookupFormComponent(new emc.menus.pop.menuitems.display.PriceGroups(),
                dataRelation, "priceGroup");
        EMCLookupPopup priceGroupPopup = new EMCLookupPopup(enumEMCModules.POP.getId(), new emc.entity.pop.POPPriceGroup(),
                "priceGroupId", priceGroupKeys, copyUD);
        lookupTradingPriceGroup.setPopup(priceGroupPopup);

        //Keys for the discount group lookup
        List discountGroupKeys = new ArrayList();
        discountGroupKeys.add("discountGroupId");
        discountGroupKeys.add("description");

        lookupTradingDiscountGroup = new EMCLookupFormComponent(new emc.menus.pop.menuitems.display.DiscountGroups(), dataRelation,
                "discountGroup");
        EMCLookupPopup discountGroupPopup = new EMCLookupPopup(enumEMCModules.POP.getId(), new emc.entity.pop.POPDiscountGroup(),
                "discountGroupId", discountGroupKeys, copyUD);
        lookupTradingDiscountGroup.setPopup(discountGroupPopup);

        //Keys for the extra charge group lookup
        List extraChargeGroupKeys = new ArrayList();
        extraChargeGroupKeys.add("extraChargeGroupId");
        extraChargeGroupKeys.add("description");

        lookupTradingExtraChargeGroup = new EMCLookupFormComponent(new emc.menus.pop.menuitems.display.ExtraChargeGroups(), dataRelation,
                "extraChargeGroup");
        EMCLookupPopup extraChargeGroupPopup = new EMCLookupPopup(enumEMCModules.POP.getId(), new emc.entity.pop.POPExtraChargeGroup(),
                "extraChargeGroupId", extraChargeGroupKeys, copyUD);
        lookupTradingExtraChargeGroup.setPopup(extraChargeGroupPopup);

        //Keys for the terms of payment lookup
        List termsOfPaymentKeys = new ArrayList();
        termsOfPaymentKeys.add("termsOfPaymentId");
        termsOfPaymentKeys.add("description");

        lookupTradingTermsOfPayment = new EMCLookupFormComponent(new emc.menus.creditors.menuitems.display.TermsOfPayment(), dataRelation,
                "termsOfPayment");
        EMCLookupPopup termsOfPaymentPopup = new EMCLookupPopup(enumEMCModules.POP.getId(), new emc.entity.creditors.CreditorsTermsOfPayment(),
                "termsOfPaymentId", termsOfPaymentKeys, copyUD);
        lookupTradingTermsOfPayment.setPopup(termsOfPaymentPopup);

        //Keys for the settlement discount terms lookup
        List settlementDiscountKeys = new ArrayList();
        settlementDiscountKeys.add("settlementDiscountTermId");
        settlementDiscountKeys.add("description");

        lookupTradingSettlementDiscountTerms = new EMCLookupFormComponent(new emc.menus.creditors.menuitems.display.SettlementDiscountTerms(), dataRelation,
                "settlementDiscount");
        EMCLookupPopup settlementDiscountTermsPopup = new EMCLookupPopup(enumEMCModules.CREDITORS.getId(), new emc.entity.creditors.CreditorsSettlementDiscountTerms(),
                "settlementDiscountTermId", settlementDiscountKeys, copyUD);
        lookupTradingSettlementDiscountTerms.setPopup(settlementDiscountTermsPopup);

        //Keys for the postal code lookups
        List postalCodeKeys = new ArrayList();
        postalCodeKeys.add("code");
        postalCodeKeys.add("suburb");
        postalCodeKeys.add("country");

        //Postal code lookups
        physPostCodeLookup = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.PostalCodes(),
                dataRelation, "addressPhysPostalCode");

        postCodeLookup = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.PostalCodes(),
                dataRelation, "postalCode");

        EMCLookupPopup postCodePopup = new EMCLookupPopup(enumEMCModules.BASE.getId(), new emc.entity.base.BasePostalCodes(),
                "code", postalCodeKeys, copyUD);
        physPostCodeLookup.setPopup(postCodePopup);
        postCodeLookup.setPopup(postCodePopup);
    }

    //This method is used to set documents on the form
    private void setDocuments() {

        this.txtId.setDocument(new EMCStringFormDocument(dataRelation, "supplierId"));
        this.txtContactName.setDocument(new EMCStringFormDocument(dataRelation, "supplierName"));
        this.txtTelephone.setDocument(new EMCStringFormDocument(dataRelation, "telephone"));
        this.txtCellNo.setDocument(new EMCStringFormDocument(dataRelation, "cellNo"));
        this.txtEmergencyNo.setDocument(new EMCStringFormDocument(dataRelation, "emergencyNo"));
        this.txtFaxNo.setDocument(new EMCStringFormDocument(dataRelation, "fax"));
        this.txtEmail.setDocument(new EMCStringFormDocument(dataRelation, "email"));
        this.txtWebsite.setDocument(new EMCStringFormDocument(dataRelation, "website"));
        this.txtOrderContactName.setDocument(new EMCStringFormDocument(dataRelation, "contactOrders"));
        this.txtOrderContactTelephone.setDocument(new EMCStringFormDocument(dataRelation, "contactOrdersPhone"));
        this.txtOrderContactEmail.setDocument(new EMCStringFormDocument(dataRelation, "contactOrdersEmail"));
        this.txtAccountContactName.setDocument(new EMCStringFormDocument(dataRelation, "contactAccounts"));
        this.txtAccountContactTelephone.setDocument(new EMCStringFormDocument(dataRelation, "contactAccountsPhone"));
        this.txtAccountContactEmail.setDocument(new EMCStringFormDocument(dataRelation, "contactAccountsEmail"));
        this.txtPostalAdrs1.setDocument(new EMCStringFormDocument(dataRelation, "postalAddressLine1"));
        this.txtPostalAdrs2.setDocument(new EMCStringFormDocument(dataRelation, "postalAddressLine2"));
        this.txtPostalAdrs3.setDocument(new EMCStringFormDocument(dataRelation, "postalAddressLine3"));
        this.txtPostalAdrs4.setDocument(new EMCStringFormDocument(dataRelation, "postalAddressLine4"));
        this.txtPostalAdrs5.setDocument(new EMCStringFormDocument(dataRelation, "postalAddressLine5"));
        this.txtPhysicalAdrs1.setDocument(new EMCStringFormDocument(dataRelation, "addressPhysicalLine1"));
        this.txtPhysicalAdrs2.setDocument(new EMCStringFormDocument(dataRelation, "addressPhysicalLine2"));
        this.txtPhysicalAdrs3.setDocument(new EMCStringFormDocument(dataRelation, "addressPhysicalLine3"));
        this.txtPhysicalAdrs4.setDocument(new EMCStringFormDocument(dataRelation, "addressPhysicalLine4"));
        this.txtPhysicalAdrs5.setDocument(new EMCStringFormDocument(dataRelation, "addressPhysicalLine5"));
        this.txtCreditLimit.setDocument(new EMCDoubleFormDocument(dataRelation, "creditLimit"));
        this.txtVatRegistration.setDocument(new EMCStringFormDocument(dataRelation, "vatRegistrationNo"));
        this.txtLTime.setDocument(new EMCDoubleFormDocument(dataRelation, "standardLeadTime"));
        this.txtBankName.setDocument(new EMCStringFormDocument(dataRelation, "bank"));
        this.txtBankBranchCode.setDocument(new EMCStringFormDocument(dataRelation, "bankBranchCode"));
        this.txtBankAccountNo.setDocument(new EMCStringFormDocument(dataRelation, "bankAccountNumber"));
        this.txtBankAccountName.setDocument(new EMCStringFormDocument(dataRelation, "bankAccountName"));
    }

    //This method is used to initialize the frame
    private void initFrame() {
        //Tabbed pane used on the form
        emcJTabbedPane tabbedPaneTop = new emcJTabbedPane();

        //Sets up the tabs
        tabOverview();
        tabContact();
        tabAddress();
        tabTrading();
        //tabBank();


        //Adds the tabs to the form
        tabbedPaneTop.add(this.overviewPanel, "Overview");
        tabbedPaneTop.add(this.contactPanel, "Contact");
        tabbedPaneTop.add(this.addressPanel, "Address");
        tabbedPaneTop.add(this.tradingPanel, "Trading");
        //tabbedPaneTop.add(this.bankPanel, "Bank");
        tabbedPaneTop.add(tabStatus(), "Status");
        tabbedPaneTop.add(tabCompanyInfo(), "Company Info");

        emcJPanel mainPane = new emcJPanel(new BorderLayout(1, 1));
        mainPane.add(tabbedPaneTop, BorderLayout.CENTER);
        mainPane.add(buttonPane(), BorderLayout.EAST);

        //Adds the tabbed  pane to the form
        this.add(mainPane);
    }

    private emcJPanel buttonPane() {
        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new GridBagLayout());
        thePanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        GridBagConstraints gbc;
        int y = 0;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        thePanel.add(new emcMenuButton("Item Ref.", new SupplierReferenceSupp(), this, 0, false), gbc);
        thePanel.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
        thePanel.setPreferredSize(new Dimension(120, 25));
        return thePanel;
    }

    //This method is used  to set up the overview tab
    private void tabOverview() {
        List keys = new ArrayList();
        keys.add("supplierId");
        keys.add("supplierName");
        keys.add("supplierGroup");
        keys.add("supplierCurrency");
        keys.add("telephone");
        keys.add("email");
        keys.add("status");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);

        toptable.setLookupCellEditor(2, supplierGroupEditor);
        toptable.setLookupCellEditor(3, currencyEditor);

        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        overviewPanel.setLayout(new GridLayout(1, 1));
        overviewPanel.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private emcJPanel tabCompanyInfo() {
        //Company
        emcJTextField txtCompanyRegistration = new emcJTextField(new EMCStringFormDocument(dataRelation, "companyRegistrationNumber"));
        emcJTextField txtVatRegistation = new emcJTextField(new EMCStringFormDocument(dataRelation, "vatRegistrationNo"));
        EMCLookupFormComponent lkpVatCode = new EMCLookupFormComponent(new emc.menus.gl.menuitems.display.GLVATCode(), dataRelation, "vatCode");
        lkpVatCode.setPopup(new EMCLookupPopup(new emc.entity.gl.GLVATCode(), "vatId", copyUD));
        //Banking
        emcJTextField txtCompanyBankName = new emcJTextField(new EMCStringFormDocument(dataRelation, "bank"));
        emcJTextField txtBranchCode = new emcJTextField(new EMCStringFormDocument(dataRelation, "bankBranchCode"));
        emcJTextField txtAccountName = new emcJTextField(new EMCStringFormDocument(dataRelation, "bankAccountName"));
        emcJTextField txtAccountNumber = new emcJTextField(new EMCStringFormDocument(dataRelation, "bankAccountNumber"));

        emcJLabel lblResponsiblePersonName = new emcJLabel(dataRelation.getColumnName("personResponsible"));
        emcJTextField txtResponsiblePersonName = new emcJTextField(new EMCStringFormDocument(dataRelation, "personResponsible"));
        emcJLabel lblResponsiblePersonTelephone = new emcJLabel(dataRelation.getColumnName("personResponsibleTelNo"));
        emcJTextField txtResponsiblePersonTelephone = new emcJTextField(new EMCStringFormDocument(dataRelation, "personResponsibleTelNo"));
        emcJLabel lblResponsiblePersonCell = new emcJLabel(dataRelation.getColumnName("personResponsibleCellNo"));
        emcJTextField txtResponsiblePersonCell = new emcJTextField(new EMCStringFormDocument(dataRelation, "personResponsibleCellNo"));
        emcJLabel lblResponsiblePersonEmail = new emcJLabel(dataRelation.getColumnName("personResponsibleEmail"));
        emcJTextField txtResponsiblePersonEmail = new emcJTextField(new EMCStringFormDocument(dataRelation, "personResponsibleEmail"));

        Component[][] comp = {{new emcJLabel("Company Registration"), txtCompanyRegistration, new emcJLabel("Bank Name"), txtCompanyBankName,},
            {new emcJLabel("VAT Registration"), txtVatRegistation, new emcJLabel("Branch Code"), txtBranchCode},
            {new emcJLabel("VAT Code"), lkpVatCode, new emcJLabel("Account Name"), txtAccountName},
            {new emcJLabel(), new emcJLabel(), new emcJLabel("Account Number"), txtAccountNumber},
            {new emcJLabel()},
            {lblResponsiblePersonName, txtResponsiblePersonName},
            {lblResponsiblePersonTelephone, txtResponsiblePersonTelephone},
            {lblResponsiblePersonCell, txtResponsiblePersonCell},
            {lblResponsiblePersonEmail, txtResponsiblePersonEmail}
        };
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Company Info");
    }

    private emcJPanel tabStatus() {
        emcJLabel lblStatus = new emcJLabel(dataRelation.getColumnName("status"));
        emcJTextField txtStatus = new emcJTextField(new EMCStringFormDocument(dataRelation, "status"));

        emcJLabel lblCreatedDate = new emcJLabel(dataRelation.getColumnName("createdDate"));
        EMCDatePickerFormComponent dpkCreatedDate = new EMCDatePickerFormComponent(dataRelation, "createdDate");
        dpkCreatedDate.setEnabled(false);
        emcJLabel lblCreatedBy = new emcJLabel("Created By");
        emcJTextField txtCreatedBy = new emcJTextField(new EMCStringFormDocument(dataRelation, "createdBy"));
        txtCreatedBy.setEditable(false);

        emcJLabel lblModifiedDate = new emcJLabel(dataRelation.getColumnName("modifiedDate"));
        EMCDatePickerFormComponent dpkModifiedDate = new EMCDatePickerFormComponent(dataRelation, "modifiedDate");
        dpkModifiedDate.setEnabled(false);
        emcJLabel lblModifiedBy = new emcJLabel("Modified By");
        emcJTextField txtModifiedBy = new emcJTextField(new EMCStringFormDocument(dataRelation, "modifiedBy"));
        txtModifiedBy.setEditable(false);

        emcJLabel lblClosedDate = new emcJLabel(dataRelation.getColumnName("closedDate"));
        EMCDatePickerFormComponent dpkClosedDate = new EMCDatePickerFormComponent(dataRelation, "closedDate");
        dpkClosedDate.setEnabled(false);

        emcJLabel lblClosedBy = new emcJLabel(dataRelation.getColumnName("closedBy"));
        emcJTextField txtClosedBy = new emcJTextField(new EMCStringFormDocument(dataRelation, "closedBy"));
        txtClosedBy.setEditable(false);

        emcJLabel lblClosedReason = new emcJLabel(dataRelation.getColumnName("closedReason"));
        EMCLookupFormComponent lkpClosedReason = new EMCLookupFormComponent(new DebtorsCreditCloseReasonMenu(), dataRelation, "closedReason");
        lkpClosedReason.setPopup(new EMCLookupPopup(new DebtorsClosedReason(), "closedReasonId", copyUD));

        emcJLabel lblAccountOpenedDate = new emcJLabel(dataRelation.getColumnName("accountOpened"));
        EMCDatePickerFormComponent dpkAccountOpenedDate = new EMCDatePickerFormComponent(dataRelation, "accountOpened");

        Component[][] components = new Component[][]{
            {lblStatus, txtStatus},
            {new emcJLabel()},
            {lblCreatedDate, dpkCreatedDate},
            {lblCreatedBy, txtCreatedBy},
            {new emcJLabel()},
            {lblAccountOpenedDate, dpkAccountOpenedDate},
            {new emcJLabel()},
            {lblModifiedDate, dpkModifiedDate},
            {lblModifiedBy, txtModifiedBy},
            {lblClosedBy, txtClosedBy},
            {lblClosedDate, dpkClosedDate},
            {lblClosedReason, lkpClosedReason}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }

    //This method is used to set up the contact tab
    private void tabContact() {
        //Sets the layout and border of the panel
        this.contactPanel.setLayout(new GridBagLayout());
        this.contactPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Contact"));

        //Panels used to seperate fields on the form
        emcJPanel pnlLeft = new emcJPanel();
        emcJPanel pnlRight = new emcJPanel();
        pnlLeft.setLayout(new GridBagLayout());
        pnlRight.setLayout(new GridBagLayout());

        //GridbagConstraints used for this tab
        GridBagConstraints gbc = new GridBagConstraints();

        //int used for layout
        int y = 0;

        //Left panel

        //Contact
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(new emcJLabel(), gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlLeft.add(new emcJLabel("Contact"), gbc);

        //Increment y
        y++;

        //ID
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(this.lblId, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlLeft.add(txtId, gbc);

        //Increment y
        y++;

        //Name
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(this.lblContactName, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlLeft.add(txtContactName, gbc);

        //Increment y
        y++;

        //Group
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(this.lblGroup, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlLeft.add(lookupSupplierGroup, gbc);

        //Increment y
        y++;

        //Telephone
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(this.lblTelephone, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlLeft.add(txtTelephone, gbc);

        //Increment y
        y++;

        //Cell No
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(this.lblCellNo, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlLeft.add(txtCellNo, gbc);

        //Increment y
        y++;

        //Emergency No
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(this.lblEmergencyNo, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlLeft.add(txtEmergencyNo, gbc);

        //Increment y
        y++;

        //Fax No
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(this.lblFaxNo, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlLeft.add(txtFaxNo, gbc);

        //Increment y
        y++;

        //Email
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(this.lblEmail, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlLeft.add(txtEmail, gbc);

        //Increment y
        y++;

        //Website
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(this.lblWebsite, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlLeft.add(txtWebsite, gbc);

        //Increment y
        y++;

        //End the panel
        gbc = emcSetGridBagConstraints.endPanel(y);
        pnlLeft.add(new emcJLabel(), gbc);

        //Right panel

        //Reset y
        y = 0;

        //Order contact
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlRight.add(new emcJLabel(), gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlRight.add(new emcJLabel("Order Contact"), gbc);

        //Increment y
        y++;

        //Name
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlRight.add(this.lblOrderContactName, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlRight.add(txtOrderContactName, gbc);

        //Increment y
        y++;

        //Telephone 
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlRight.add(this.lblOrderContactTelephone, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlRight.add(txtOrderContactTelephone, gbc);

        //Increment y
        y++;

        //Email 
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlRight.add(this.lblOrderContactEmail, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlRight.add(txtOrderContactEmail, gbc);
        y++;
        //Cell No
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlRight.add(this.lblOrderContactCellphone, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlRight.add(txtOrderContactCellphone, gbc);

        //Increment y
        y++;

        //Empty space
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlRight.add(new emcJLabel(), gbc);

        //Increment y
        y++;

        //Account Contact
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlRight.add(new emcJLabel(), gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlRight.add(new emcJLabel("Account Contact"), gbc);

        //Increment y
        y++;

        //Name
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlRight.add(this.lblAccountContactName, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlRight.add(txtAccountContactName, gbc);

        //Increment y
        y++;

        //Telephone 
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlRight.add(this.lblAccountContactTelephone, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlRight.add(txtAccountContactTelephone, gbc);

        //Increment y
        y++;

        //Email 
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlRight.add(this.lblAccountContactEmail, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlRight.add(txtAccountContactEmail, gbc);

        y++;
        //CellPhone

        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlRight.add(this.lblAccountContactCellphoneNo, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlRight.add(txtAccountContactCellphoneNo, gbc);

        //Increment y
        y++;

        //End the panel
        gbc = emcSetGridBagConstraints.endPanel(y);
        pnlRight.add(new emcJLabel(), gbc);

        //Left panel
        gbc = emcSetGridBagConstraints.setGrid(gbc, 0, 0);
        gbc = emcSetGridBagConstraints.setWeightXAndY(gbc, 1, 1);
        gbc = emcSetGridBagConstraints.setGridWidthAndHeigth(gbc, 1, 0);
        gbc.fill = GridBagConstraints.BOTH;

        //Adds pnlLeft to the main panel
        this.contactPanel.add(pnlLeft, gbc);

        //Resets gbc
        gbc = new GridBagConstraints();

        //Right panel
        gbc = emcSetGridBagConstraints.setGrid(gbc, 1, 0);
        gbc = emcSetGridBagConstraints.setWeightXAndY(gbc, 1, 0);
        gbc = emcSetGridBagConstraints.setGridWidthAndHeigth(gbc, 1, 0);
        gbc.fill = GridBagConstraints.BOTH;

        //Adds pnlRight to the main panel
        this.contactPanel.add(pnlRight, gbc);
    }

    //Sets up the address tab
    private void tabAddress() {
        //Sets the layout and border of the panel
        this.addressPanel.setLayout(new GridBagLayout());
        this.addressPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Contact"));

        //GridbagConstraints used for this tab
        GridBagConstraints gbc = new GridBagConstraints();

        //int used for layout
        int y = 0;

        //Panels used to seperate fields on the tab
        emcJPanel pnlPostalAddress = new emcJPanel();
        emcJPanel pnlPhysicalAddress = new emcJPanel();
        pnlPostalAddress.setLayout(new GridBagLayout());
        pnlPhysicalAddress.setLayout(new GridBagLayout());

        //Postal address panel

        //Empty space
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlPostalAddress.add(new emcJLabel(), gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlPostalAddress.add(new emcJLabel("Postal address"), gbc);

        //Line 1
        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlPostalAddress.add(this.lblPostalAdrs1, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlPostalAddress.add(txtPostalAdrs1, gbc);

        //Line 2
        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlPostalAddress.add(this.lblPostalAdrs2, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlPostalAddress.add(txtPostalAdrs2, gbc);

        //Line 3
        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlPostalAddress.add(this.lblPostalAdrs3, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlPostalAddress.add(txtPostalAdrs3, gbc);

        //Line 4
        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlPostalAddress.add(this.lblPostalAdrs4, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlPostalAddress.add(txtPostalAdrs4, gbc);

        //Line 5
        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlPostalAddress.add(this.lblPostalAdrs5, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlPostalAddress.add(txtPostalAdrs5, gbc);

        //Postal code
        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlPostalAddress.add(this.lblPostCode, gbc);
        gbc = emcSetGridBagConstraints.createStandard(1, y, 0.1, GridBagConstraints.LINE_START);
        postCodeLookup.setPreferredSize(new java.awt.Dimension(80, 25));
        pnlPostalAddress.add(postCodeLookup, gbc);

        //Physical address panel      
        y = 0;

        //Empty space
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlPhysicalAddress.add(new emcJLabel(), gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlPhysicalAddress.add(new emcJLabel("Physical address"), gbc);

        //Line 1
        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlPhysicalAddress.add(this.lblPhysicalAdrs1, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlPhysicalAddress.add(txtPhysicalAdrs1, gbc);

        //Line 2
        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlPhysicalAddress.add(this.lblPhysicalAdrs2, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlPhysicalAddress.add(txtPhysicalAdrs2, gbc);

        //Line 3
        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlPhysicalAddress.add(this.lblPhysicalAdrs3, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlPhysicalAddress.add(txtPhysicalAdrs3, gbc);

        //Line 4
        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlPhysicalAddress.add(this.lblPhysicalAdrs4, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlPhysicalAddress.add(txtPhysicalAdrs4, gbc);

        //Line 5
        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlPhysicalAddress.add(this.lblPhysicalAdrs5, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlPhysicalAddress.add(txtPhysicalAdrs5, gbc);

        //Postal code
        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlPhysicalAddress.add(this.lblPhysPostCode, gbc);
        gbc = emcSetGridBagConstraints.createStandard(1, y, 0.1, GridBagConstraints.LINE_START);
        physPostCodeLookup.setPreferredSize(new java.awt.Dimension(80, 25));
        pnlPhysicalAddress.add(physPostCodeLookup, gbc);

        //Add the panels
        y = 0;
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 0, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 2);
        addressPanel.add(pnlPostalAddress, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, 2);
        addressPanel.add(pnlPhysicalAddress, gbc);

        //End addressPanelPanel panel
        y++;
        gbc = emcSetGridBagConstraints.endPanel(y);
        addressPanel.add(new emcJLabel(), gbc);
    }

    //Sets up the trading tab
    private void tabTrading() {
        //Sets the layout and border of the panel
        tradingPanel.setLayout(new GridBagLayout());
        tradingPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Trading"));

        //The panels used to seperate fields on the form
        emcJPanel pnlLeft = new emcJPanel();
        emcJPanel pnlRight = new emcJPanel();
        pnlLeft.setLayout(new GridBagLayout());
        pnlRight.setLayout(new GridBagLayout());

        //GridbagConstraints used for this tab
        GridBagConstraints gbc = new GridBagConstraints();

        //int used for layout
        int y = 0;

        //Terms
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(this.lblTerms, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlLeft.add(lookupTradingTermsOfPayment, gbc);

        //Increment y
        y++;

        //Settle disc
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(this.lblSettleDisc, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlLeft.add(lookupTradingSettlementDiscountTerms, gbc);

        //Increment y
        y++;

        //Credit Limit
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(this.lblCreditLimit, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlLeft.add(txtCreditLimit, gbc);

        //Increment y
        y++;

        //Empty space
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(new emcJLabel(), gbc);

        //Increment y
        y++;

        //VAT registration
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(this.lblVatRegistration, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlLeft.add(txtVatRegistration, gbc);

        y++;

        //VAT registration
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(this.lblVatApplicable, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlLeft.add(ysnVatApplicable, gbc);

        //Increment y
        y++;

        //Currency
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlLeft.add(this.lblCurrency, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlLeft.add(lookupTradingCurrency, gbc);

        //Increment y
        y++;

        //End the panel
        gbc = emcSetGridBagConstraints.endPanel(y);
        pnlLeft.add(new emcJLabel(), gbc);

        //Reset y
        y = 0;

        //Price group
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlRight.add(this.lblPriceGroup, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlRight.add(lookupTradingPriceGroup, gbc);

        //Increment y
        y++;

        //Disc group
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlRight.add(this.lblDiscGroup, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlRight.add(lookupTradingDiscountGroup, gbc);

        //Increment y
        y++;

        //Extra charge group
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlRight.add(this.lblExtraChargeGroup, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlRight.add(lookupTradingExtraChargeGroup, gbc);

        //Increment y
        y++;

        //Empty space
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlRight.add(new emcJLabel(), gbc);

        //Increment y
        y++;

        //L/Time
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        pnlRight.add(this.lblLTime, gbc);
        gbc = emcSetGridBagConstraints.changePosPlusFill(gbc, 1, y, 0.9, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        pnlRight.add(txtLTime, gbc);

        //Increment y
        y++;

        //End the panel
        gbc = emcSetGridBagConstraints.endPanel(y);
        pnlRight.add(new emcJLabel(), gbc);

        //Left panel
        gbc = emcSetGridBagConstraints.setGrid(gbc, 0, 0);
        gbc = emcSetGridBagConstraints.setWeightXAndY(gbc, 1, 1);
        gbc = emcSetGridBagConstraints.setGridWidthAndHeigth(gbc, 1, 0);
        gbc.fill = GridBagConstraints.BOTH;

        //Adds pnlLeft to the main panel
        this.tradingPanel.add(pnlLeft, gbc);

        //Resets gbc
        gbc = new GridBagConstraints();

        //Right panel
        gbc = emcSetGridBagConstraints.setGrid(gbc, 1, 0);
        gbc = emcSetGridBagConstraints.setWeightXAndY(gbc, 1, 0);
        gbc = emcSetGridBagConstraints.setGridWidthAndHeigth(gbc, 1, 0);
        gbc.fill = GridBagConstraints.BOTH;

        //Adds pnlRight to the main panel
        this.tradingPanel.add(pnlRight, gbc);

    }

    //Sets up the bank tab
    private void tabBank() {
        //Sets the layout and border of the panel
        bankPanel.setLayout(new GridBagLayout());
        bankPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Bank"));

        //GridbagConstraints used for this tab
        GridBagConstraints gbc = new GridBagConstraints();

        //int used for layout
        int y = 0;

        //Top
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        bankPanel.add(new emcJLabel(), gbc);
        gbc = emcSetGridBagConstraints.changePosition(gbc, 1, y, 0.9);
        bankPanel.add(new emcJLabel("Bank"), gbc);

        //Increments y
        y++;

        //Bank name
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        bankPanel.add(this.lblBankName, gbc);
        gbc = emcSetGridBagConstraints.changePosition(gbc, 1, y, 0.9);
        bankPanel.add(this.txtBankName, gbc);

        //Increments y
        y++;

        //Bank branch code
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        bankPanel.add(this.lblBankBranchCode, gbc);
        gbc = emcSetGridBagConstraints.changePosition(gbc, 1, y, 0.9);
        bankPanel.add(this.txtBankBranchCode, gbc);

        //Increments y
        y++;

        //Bank account number
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        bankPanel.add(this.lblBankAccountNo, gbc);
        gbc = emcSetGridBagConstraints.changePosition(gbc, 1, y, 0.9);
        bankPanel.add(this.txtBankAccountNo, gbc);

        //Increments y
        y++;

        //Bank account name
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        bankPanel.add(this.lblBankAccountName, gbc);
        gbc = emcSetGridBagConstraints.changePosition(gbc, 1, y, 0.9);
        bankPanel.add(this.txtBankAccountName, gbc);

        //Increments y
        y++;

        //Ends the panel
        gbc = emcSetGridBagConstraints.endPanel(y);
        bankPanel.add(new emcJLabel(), gbc);

    }
}

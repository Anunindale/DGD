package emc.forms.sop.display.customers;

import emc.app.components.EMCFormComboBox;
import emc.app.components.base.emailing.EMCEmailButton;
import emc.app.components.documents.EMCBigDecimalFormDocument;
import emc.app.components.documents.EMCDoubleFormDocument;
import emc.app.components.documents.EMCIntegerFormDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.locate.LocateButton;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.parameters.DateParameterObject;
import emc.app.reporttools.parameters.EMCJComboBoxParameterObject;
import emc.app.reporttools.parameters.ReportParameterObjectMap;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.base.BaseCountries;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.base.BasePostalCodes;
import emc.entity.creditors.CreditorsSettlementDiscountTerms;
import emc.entity.creditors.CreditorsTermsOfPayment;
import emc.entity.debtors.DebtorsClosedReason;
import emc.entity.debtors.DebtorsCreditRating;
import emc.entity.debtors.DebtorsCreditStopReason;
import emc.entity.debtors.DebtorsRebateCodes;
import emc.entity.sop.SOPCustomerGroup;
import emc.entity.sop.SOPCustomers;
import emc.entity.debtors.DebtorsSalesArea;
import emc.entity.debtors.DebtorsSalesRegion;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.pop.POPDeliveryModes;
import emc.entity.pop.POPDeliveryTerms;
import emc.entity.pop.POPDiscountGroup;
import emc.entity.pop.POPExtraChargeGroup;
import emc.entity.pop.POPPriceGroup;
import emc.entity.sop.SOPCustomerClassification1;
import emc.entity.sop.SOPCustomerClassification2;
import emc.entity.sop.SOPCustomerClassification3;
import emc.entity.sop.SOPCustomerClassification4;
import emc.entity.sop.SOPCustomerClassification5;
import emc.entity.sop.SOPCustomerClassification6;
import emc.entity.sop.SOPSalesRepGroups;
import emc.enums.base.calendar.WeekDays;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.debtors.DebtorsPricingOn;
import emc.enums.debtors.customerstatement.IgnoreBalanceEnum;
import emc.enums.debtors.parameters.DebtorsAgingMode;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.enums.sop.SalesDeliveryRules;
import emc.enums.sop.commission.SOPCommissionTypes;
import emc.forms.sop.display.customers.resources.ActivityButton;
import emc.forms.sop.display.customers.resources.CustomerDRM;
import emc.forms.sop.display.customers.resources.TransSettlementButton;
import emc.framework.EMCCommandClass;
import emc.framework.EMCMenuItem;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.Countries;
import emc.menus.base.menuItems.display.PostalCodes;
import emc.menus.base.menuItems.display.employees;
import emc.menus.creditors.menuitems.display.SettlementDiscountTerms;
import emc.menus.creditors.menuitems.display.TermsOfPayment;
import emc.menus.debtors.menuitems.display.DebtorsCourier;
import emc.menus.debtors.menuitems.display.DebtorsCreditCloseReasonMenu;
import emc.menus.debtors.menuitems.display.DebtorsCreditController;
import emc.menus.debtors.menuitems.display.DebtorsCreditInsurer;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import emc.menus.debtors.menuitems.display.DebtorsCreditRatingMenu;
import emc.menus.debtors.menuitems.display.DebtorsCreditStopReasonMenu;
import emc.menus.debtors.menuitems.display.DebtorsCustomerBalance;
import emc.menus.debtors.menuitems.display.DebtorsCustomerClassification1Menu;
import emc.menus.debtors.menuitems.display.DebtorsCustomerClassification2Menu;
import emc.menus.debtors.menuitems.display.DebtorsCustomerClassification3Menu;
import emc.menus.debtors.menuitems.display.DebtorsCustomerClassification4Menu;
import emc.menus.debtors.menuitems.display.DebtorsCustomerClassification5Menu;
import emc.menus.debtors.menuitems.display.DebtorsCustomerClassification6Menu;
import emc.menus.debtors.menuitems.display.DebtorsDeliveryCharge;
import emc.menus.debtors.menuitems.display.DebtorsMarketingGroup;
import emc.menus.debtors.menuitems.display.DebtorsRebateCodesMI;
import emc.menus.sop.menuitems.display.SOPCustomerGroupMenu;
import emc.menus.debtors.menuitems.display.DebtorsSalesAreaMenu;
import emc.menus.debtors.menuitems.display.DebtorsSalesRegionMenu;
import emc.menus.debtors.menuitems.display.DebtorsTransactionsMenu;
import emc.menus.gl.menuitems.display.GLCurrency;
import emc.menus.gl.menuitems.display.GLVATCode;
import emc.menus.inventory.menuitems.display.Warehouse;
import emc.menus.pop.menuitems.display.DeliveryModes;
import emc.menus.pop.menuitems.display.DeliveryTerms;
import emc.menus.pop.menuitems.display.DiscountGroups;
import emc.menus.pop.menuitems.display.ExtraChargeGroups;
import emc.menus.pop.menuitems.display.PriceGroups;
import emc.menus.sop.menuitems.display.DiscountSetupMI;
import emc.menus.sop.menuitems.display.SOPPriceArangementCustomerMenu;
import emc.menus.sop.menuitems.display.SOPPriceArangementMenu;
import emc.menus.sop.menuitems.display.SOPSalesRepCommissionMenu;
import emc.menus.sop.menuitems.display.SOPSalesRepGroupsMenu;
import emc.methods.creditors.ServerCreditorsMethods;
import emc.methods.debtors.ServerDebtorsMethods;
import emc.methods.sop.ServerSOPMethods;
import emc.reporttools.EMCReportConfig;
import emc.reporttools.ReportEnumInterface;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @Author wikus
 */
public class SOPCustomerForm extends BaseInternalFrame {

    private EMCUserData userData;
    private CustomerDRM dataManager;
    private EMCEmailButton emailButton;

    public SOPCustomerForm(EMCUserData userData) {
        super("Customer", true, true, true, true, userData);
        this.setBounds(20, 20, 850, 480);
        SOPCustomers prospectCustomer = null;
        if (userData.getUserData(1) instanceof SOPCustomers) {
            prospectCustomer = (SOPCustomers) userData.getUserData().remove(1);
        }
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new CustomerDRM(new emcGenericDataSourceUpdate(enumEMCModules.SOP.getId(), new SOPCustomers(), userData), userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("customerId");
        dataManager.setFormTextId2("customerName");

        setupEmailButton();
        initFrame();

        if (prospectCustomer != null) {
            if ((Long) dataManager.getLastFieldValueAt("recordID") != 0l) {
                dataManager.insertRowCache(1, true);
            }
            dataManager.setRowCache(prospectCustomer, 0);
        }
    }

    /**
     * Sets up the email button.
     */
    private void setupEmailButton() {
        this.emailButton = new EMCEmailButton(dataManager, "accountContactEmail", "customerId");

        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/debtors/custmerstatement/DebtorsCustometStatementReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.debtors.customerstatement.DebtorsCustomerStatementReportDS");
        jasperInfo.setReportTitle("Customer Statement");

        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_CUSTOMER_STATEMENT.toString());

        Map<String, Object> parameters = new HashMap<String, Object>();
        EMCReportConfig config = new EMCReportConfig(EnumReports.DEBTORS_CUSTOMER_STATEMENT, ReportEnumInterface.FROM_CLIENT, null, parameters);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);

        ReportParameterObjectMap parametersObjectMap = new ReportParameterObjectMap();
        parametersObjectMap.put("atDate", new DateParameterObject("At Date"));
        String[] agingModes = new String[]{DebtorsAgingMode.DATE.toString(), DebtorsAgingMode.NONE.toString(), DebtorsAgingMode.OLDEST.toString()};
        parametersObjectMap.put("unallocatedCreditAgingMode", new EMCJComboBoxParameterObject("Unallocated Credit Aging Mode", agingModes));

        String[] balanceParameters = new String[]{"", IgnoreBalanceEnum.CREDIT_BALANCE.toString(), IgnoreBalanceEnum.ZERO_BALANCE.toString(), IgnoreBalanceEnum.BOTH.toString()};
        parametersObjectMap.put("ignore", new EMCJComboBoxParameterObject("Ignore Customers", balanceParameters));

        this.emailButton.addReport("Statement", EnumReports.DEBTORS_CUSTOMER_STATEMENT, jasperInfo, cmd, config, query, "customerId", SOPCustomers.class, "customerId", parametersObjectMap);
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", overviewTab());
        tabbed.add("Address", addressTab());
        tabbed.add("Contact", contactTab());
        tabbed.add("Credit Info", creditPaymentTab());
        tabbed.add("Status", statusTab());
        tabbed.add("Pricing", pricingTab());
        tabbed.add("Sales", salesTab());
        tabbed.add("Delivery", deliveryTab());
        tabbed.add("Company Info", companyInfoTab());
        tabbed.add("Classifications", classificationsTab());
        tabbed.add("Import/Export", exportTab());
        this.add(tabbed, BorderLayout.CENTER);

        this.add(createButtons(), BorderLayout.EAST);
    }

    private emcTablePanelUpdate overviewTab() {
        List keys = new ArrayList();
        keys.add("customerId");
        //keys.add("customerComapny");
        keys.add("customerName");
        keys.add("trandingAs");
        keys.add("invoiceToCustomer");
        keys.add("customerGroup");
        keys.add("marketingGroup");
        keys.add("customerStatus");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        //Lookups
        EMCLookupJTableComponent lkpCustomer = new EMCLookupJTableComponent(new SOPCustomersMenu());
        lkpCustomer.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", userData));
        table.setLookupToColumn("invoiceToCustomer", lkpCustomer);
        EMCLookupJTableComponent lkpCustomerGroup = new EMCLookupJTableComponent(new SOPCustomerGroupMenu());
        lkpCustomerGroup.setPopup(new EMCLookupPopup(new SOPCustomerGroup(), "customerGroup", userData));
        table.setLookupToColumn("customerGroup", lkpCustomerGroup);
        EMCLookupJTableComponent lkpMarkitingGroup = new EMCLookupJTableComponent(new DebtorsMarketingGroup());
        lkpMarkitingGroup.setPopup(new EMCLookupPopup(new emc.entity.debtors.DebtorsMarketingGroup(), "marketingGroup", userData));
        table.setLookupToColumn("marketingGroup", lkpMarkitingGroup);
        //Lookups
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel addressTab() {
        //Physical
        emcJTextField txtPhysicalAddress1 = new emcJTextField(new EMCStringFormDocument(dataManager, "physicalAddresLine1"));
        emcJTextField txtPhysicalAddress2 = new emcJTextField(new EMCStringFormDocument(dataManager, "physicalAddresLine2"));
        emcJTextField txtPhysicalAddress3 = new emcJTextField(new EMCStringFormDocument(dataManager, "physicalAddresLine3"));
        emcJTextField txtPhysicalAddress4 = new emcJTextField(new EMCStringFormDocument(dataManager, "physicalAddresLine4"));
        emcJTextField txtPhysicalAddress5 = new emcJTextField(new EMCStringFormDocument(dataManager, "physicalAddresLine5"));
        EMCLookupFormComponent lkpPhysicalPostalCode = new EMCLookupFormComponent(new PostalCodes(), dataManager, "physicalPostalCode");
        lkpPhysicalPostalCode.setPopup(new EMCLookupPopup(new BasePostalCodes(), "code", userData));
        Component[][] physicalComp = {{new emcJLabel("Physical Address Line 1"), txtPhysicalAddress1},
            {new emcJLabel("Physical Address Line 2"), txtPhysicalAddress2},
            {new emcJLabel("Physical Address Line 3"), txtPhysicalAddress3},
            {new emcJLabel("Physical Address Line 4"), txtPhysicalAddress4},
            {new emcJLabel("Physical Address Line 5"), txtPhysicalAddress5},
            {new emcJLabel("Physical Postal Code"), lkpPhysicalPostalCode}};
        emcJPanel physicalPanel = emcSetGridBagConstraints.createSimplePanel(physicalComp, GridBagConstraints.NONE, true);
        //Postal
        emcJTextField txtPostalAddress1 = new emcJTextField(new EMCStringFormDocument(dataManager, "postalAddresLine1"));
        emcJTextField txtPostalAddress2 = new emcJTextField(new EMCStringFormDocument(dataManager, "postalAddresLine2"));
        emcJTextField txtPostalAddress3 = new emcJTextField(new EMCStringFormDocument(dataManager, "postalAddresLine3"));
        emcJTextField txtPostalAddress4 = new emcJTextField(new EMCStringFormDocument(dataManager, "postalAddresLine4"));
        emcJTextField txtPostalAddress5 = new emcJTextField(new EMCStringFormDocument(dataManager, "postalAddresLine5"));
        EMCLookupFormComponent lkpPostalPostalCode = new EMCLookupFormComponent(new PostalCodes(), dataManager, "postalPostalCode");
        lkpPostalPostalCode.setPopup(new EMCLookupPopup(new BasePostalCodes(), "code", userData));
        Component[][] postalComp = {{new emcJLabel("Postal Address Line 1"), txtPostalAddress1},
            {new emcJLabel("Postal Address Line 2"), txtPostalAddress2},
            {new emcJLabel("Postal Address Line 3"), txtPostalAddress3},
            {new emcJLabel("Postal Address Line 4"), txtPostalAddress4},
            {new emcJLabel("Postal Address Line 5"), txtPostalAddress5},
            {new emcJLabel("Postal Postal Code"), lkpPostalPostalCode}};
        emcJPanel postalPanel = emcSetGridBagConstraints.createSimplePanel(postalComp, GridBagConstraints.NONE, true);
        //Address
        Component[][] addreddComp = {{physicalPanel, postalPanel}};
        return emcSetGridBagConstraints.createSimplePanel(addreddComp, GridBagConstraints.NONE, true, "Address");
    }

    private Component contactTab() {
        //Customer
        emcJTextField txtTelephone = new emcJTextField(new EMCStringFormDocument(dataManager, "telephoneNumber"));
        emcJTextField txtCell = new emcJTextField(new EMCStringFormDocument(dataManager, "cellNumber"));
        emcJTextField txtEmergency = new emcJTextField(new EMCStringFormDocument(dataManager, "emergencyNumber"));
        emcJTextField txtFax = new emcJTextField(new EMCStringFormDocument(dataManager, "faxNumber"));
        emcJTextField txtEmail = new emcJTextField(new EMCStringFormDocument(dataManager, "email"));
        emcJTextField txtWebsite = new emcJTextField(new EMCStringFormDocument(dataManager, "website"));
        //Order Contact
        emcJTextField txtOrderContactName = new emcJTextField(new EMCStringFormDocument(dataManager, "orderContactName"));
        emcJTextField txtOrderContactTelephone = new emcJTextField(new EMCStringFormDocument(dataManager, "orderContactTelephoneNumber"));
        emcJTextField txtOrderContactCell = new emcJTextField(new EMCStringFormDocument(dataManager, "orderContactCellNumber"));
        emcJTextField txtOrderContactEmail = new emcJTextField(new EMCStringFormDocument(dataManager, "orderContactEmail"));
        //Account Contact
        emcJTextField txtAccountContactName = new emcJTextField(new EMCStringFormDocument(dataManager, "accountContactName"));
        emcJTextField txtAccountContactTelephone = new emcJTextField(new EMCStringFormDocument(dataManager, "accountContactTelephoneNumber"));
        emcJTextField txtAccountContactCell = new emcJTextField(new EMCStringFormDocument(dataManager, "accountContactCellNumber"));
        emcJTextField txtAccountContactEmail = new emcJTextField(new EMCStringFormDocument(dataManager, "accountContactEmail"));
        Component[][] comp = {{new emcJLabel("Telephone No"), txtTelephone, new emcJLabel("Order Contact Name"), txtOrderContactName},
            {new emcJLabel("Emergency No"), txtEmergency, new emcJLabel("Order Contact Telephone No"), txtOrderContactTelephone},
            {new emcJLabel("Email"), txtEmail, new emcJLabel("Order Contact Cellphone No"), txtOrderContactCell},
            {new emcJLabel(), new emcJLabel(), new emcJLabel("Order Contact Email"), txtOrderContactEmail},
            {new emcJLabel()},
            {new emcJLabel("Cellphone No"), txtCell, new emcJLabel("Account Contact Name"), txtAccountContactName},
            {new emcJLabel("Fax No"), txtFax, new emcJLabel("Account Contact Telephone No"), txtAccountContactTelephone},
            {new emcJLabel("Website"), txtWebsite, new emcJLabel("Account Contact Cellphone No"), txtAccountContactCell},
            {new emcJLabel(), new emcJLabel(), new emcJLabel("Account Contact Email"), txtAccountContactEmail},
            {new emcJLabel()}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Contact");
    }

    private Component creditPaymentTab() {
        //Credit
        EMCLookupFormComponent lkpCreditRating = new EMCLookupFormComponent(new DebtorsCreditRatingMenu(), dataManager, "creditRating");
        lkpCreditRating.setPopup(new EMCLookupPopup(new DebtorsCreditRating(), "creditRating", userData));
        emcJTextField txtCreditLimit = new emcJTextField(new EMCDoubleFormDocument(dataManager, "creaditLimit"));
        emcYesNoComponent ynCreditStoped = new emcYesNoComponent(dataManager, "creditStoped");
        EMCLookupFormComponent lkpCreditStopReason = new EMCLookupFormComponent(new DebtorsCreditStopReasonMenu(), dataManager, "creditStopReason");
        lkpCreditStopReason.setPopup(new EMCLookupPopup(new DebtorsCreditStopReason(), "reason", userData));
        EMCDatePickerFormComponent dpCreditStopDate = new EMCDatePickerFormComponent(dataManager, "dateStoped");
        dpCreditStopDate.setEnabled(false);
        emcJTextField txtCreditStopedBy = new emcJTextField(new EMCStringFormDocument(dataManager, "stoppedBy"));
        txtCreditStopedBy.setEditable(false);
        //Payment
        EMCLookupFormComponent lkpTermsOfPayment = new EMCLookupFormComponent(new TermsOfPayment(), dataManager, "termsOfPayment");
        lkpTermsOfPayment.setPopup(new EMCLookupPopup(new CreditorsTermsOfPayment(), "termsOfPaymentId", userData));
        EMCLookupFormComponent lkpSettlementDiscount = new EMCLookupFormComponent(new SettlementDiscountTerms(), dataManager, "settlementDiscount");
        lkpSettlementDiscount.setPopup(new EMCLookupPopup(new CreditorsSettlementDiscountTerms(), "settlementDiscountTermId", userData));
        //Surety
        emcYesNoComponent ynSurety = new emcYesNoComponent(dataManager, "surety");
        emcJTextField txtSuretyAmount = new emcJTextField(new EMCDoubleFormDocument(dataManager, "suretyAmount"));
        EMCDatePickerFormComponent dpSuretyExpiryDate = new EMCDatePickerFormComponent(dataManager, "suretyExpiryDate");

        emcJLabel lblCreditController = new emcJLabel("Credit Controller");
        EMCLookupFormComponent lkpCreditController = new EMCLookupFormComponent(new DebtorsCreditController(), dataManager, "creditController");
        lkpCreditController.setPopup(new EMCLookupPopup(new emc.entity.debtors.DebtorsCreditController(), "creditControllerId", userData));

        emcJLabel lblCreditInsuredBy = new emcJLabel(dataManager.getColumnName("creditInsuredBy"));
        EMCLookupFormComponent lkpCreditInsuredBy = new EMCLookupFormComponent(new DebtorsCreditInsurer(), dataManager, "creditInsuredBy");
        lkpCreditInsuredBy.setPopup(new EMCLookupPopup(new emc.entity.debtors.DebtorsCreditInsurer(), "creditInsurerId", userData));

        emcJLabel lblInsurerFileRef = new emcJLabel(dataManager.getColumnName("insurerFileRef"));
        emcJTextField txtInsurerFileRef = new emcJTextField(new EMCStringFormDocument(dataManager, "insurerFileRef"));

        emcJLabel lblInsuredDocRef = new emcJLabel(dataManager.getColumnName("insuredDocRef"));
        emcJTextField txtInsuredDocRef = new emcJTextField(new EMCStringFormDocument(dataManager, "insuredDocRef"));

        emcJLabel lblInsuredAmount = new emcJLabel(dataManager.getColumnName("insuredAmount"));
        emcJTextField txtInsuredAmount = new emcJTextField(new EMCBigDecimalFormDocument(dataManager, "insuredAmount"));

        emcJLabel lblInsuredExpiryDate = new emcJLabel(dataManager.getColumnName("creditInsuredExpiryDate"));
        EMCDatePickerFormComponent dpkInsuredExpiryDate = new EMCDatePickerFormComponent(dataManager, "creditInsuredExpiryDate");

        emcJLabel lblDiscretionaryAmount = new emcJLabel(dataManager.getColumnName("discretionaryAmount"));
        emcJTextField txtDiscretionaryAmount = new emcJTextField(new EMCBigDecimalFormDocument(dataManager, "discretionaryAmount"));

        emcJLabel lblGrantedBy = new emcJLabel(dataManager.getColumnName("grantedBy"));
        EMCLookupFormComponent lkpGrantedBy = new EMCLookupFormComponent(new employees(), dataManager, "grantedBy");
        lkpGrantedBy.setPopup(new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", userData));

        emcJLabel lblDiscretionaryExpiryDate = new emcJLabel(dataManager.getColumnName("discretionaryAmountExpiryDate"));
        EMCDatePickerFormComponent dpkDiscretionaryExpiryDate = new EMCDatePickerFormComponent(dataManager, "discretionaryAmountExpiryDate");

        Component[][] comp = {{new emcJLabel("Credit Rating"), lkpCreditRating, new emcJLabel("Stopped Credit"), ynCreditStoped},
            {new emcJLabel("Credit Limit"), txtCreditLimit, new emcJLabel("Stopped Reason"), lkpCreditStopReason},
            {new emcJLabel(), new emcJLabel(), new emcJLabel("Stopped By"), txtCreditStopedBy},
            {lblCreditController, lkpCreditController, new emcJLabel("Stopped Date"), dpCreditStopDate},
            {new emcJLabel()},
            {lblDiscretionaryAmount, txtDiscretionaryAmount, new emcJLabel("Terms Of Payment"), lkpTermsOfPayment},
            {lblDiscretionaryExpiryDate, dpkDiscretionaryExpiryDate, new emcJLabel("Settlement Discount"), lkpSettlementDiscount},
            {lblGrantedBy, lkpGrantedBy},
            {new emcJLabel()},
            {lblCreditInsuredBy, lkpCreditInsuredBy, new emcJLabel("Surety"), ynSurety},
            {lblInsuredExpiryDate, dpkInsuredExpiryDate, new emcJLabel("Surety Expiry Date"), dpSuretyExpiryDate},
            {lblInsuredAmount, txtInsuredAmount, new emcJLabel("Surety Amount"), txtSuretyAmount},
            {lblInsurerFileRef, txtInsurerFileRef},
            {lblInsuredDocRef, txtInsuredDocRef}
        };

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Credit/Payment");
    }

    private emcJPanel statusTab() {
        emcJLabel lblStatus = new emcJLabel(dataManager.getColumnName("customerStatus"));
        emcJTextField txtStatus = new emcJTextField(new EMCStringFormDocument(dataManager, "customerStatus"));

        emcJLabel lblCreatedDate = new emcJLabel(dataManager.getColumnName("createdDate"));
        EMCDatePickerFormComponent dpkCreatedDate = new EMCDatePickerFormComponent(dataManager, "createdDate");
        dpkCreatedDate.setEnabled(false);
        emcJLabel lblCreatedBy = new emcJLabel("Created By");
        emcJTextField txtCreatedBy = new emcJTextField(new EMCStringFormDocument(dataManager, "createdBy"));
        txtCreatedBy.setEditable(false);

        emcJLabel lblModifiedDate = new emcJLabel(dataManager.getColumnName("modifiedDate"));
        EMCDatePickerFormComponent dpkModifiedDate = new EMCDatePickerFormComponent(dataManager, "modifiedDate");
        dpkModifiedDate.setEnabled(false);
        emcJLabel lblModifiedBy = new emcJLabel("Modified By");
        emcJTextField txtModifiedBy = new emcJTextField(new EMCStringFormDocument(dataManager, "modifiedBy"));
        txtModifiedBy.setEditable(false);

        emcJLabel lblClosedDate = new emcJLabel(dataManager.getColumnName("closedDate"));
        EMCDatePickerFormComponent dpkClosedDate = new EMCDatePickerFormComponent(dataManager, "closedDate");
        dpkClosedDate.setEnabled(false);

        emcJLabel lblClosedBy = new emcJLabel(dataManager.getColumnName("closedBy"));
        emcJTextField txtClosedBy = new emcJTextField(new EMCStringFormDocument(dataManager, "closedBy"));
        txtClosedBy.setEditable(false);

        emcJLabel lblClosedReason = new emcJLabel(dataManager.getColumnName("closedReason"));
        EMCLookupFormComponent lkpClosedReason = new EMCLookupFormComponent(new DebtorsCreditCloseReasonMenu(), dataManager, "closedReason");
        lkpClosedReason.setPopup(new EMCLookupPopup(new DebtorsClosedReason(), "closedReasonId", userData));

        emcJLabel lblAccountOpenedDate = new emcJLabel(dataManager.getColumnName("accountOpenedDate"));
        EMCDatePickerFormComponent dpkAccountOpenedDate = new EMCDatePickerFormComponent(dataManager, "accountOpenedDate");

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

    private Component pricingTab() {
        EMCLookupFormComponent lkpPriceGroup = new EMCLookupFormComponent(new PriceGroups(), dataManager, "priceGroup");
        lkpPriceGroup.setPopup(new EMCLookupPopup(new POPPriceGroup(), "priceGroupId", userData));

        EMCLookupFormComponent lkpDiscountGroup = new EMCLookupFormComponent(new DiscountGroups(), dataManager, "discountGroup");
        lkpDiscountGroup.setPopup(new EMCLookupPopup(new POPDiscountGroup(), "discountGroupId", userData));
        EMCLookupFormComponent lkpExtraChargeGroup = new EMCLookupFormComponent(new ExtraChargeGroups(), dataManager, "extaChargeGroup");
        lkpExtraChargeGroup.setPopup(new EMCLookupPopup(new POPExtraChargeGroup(), "extraChargeGroupId", userData));
        EMCFormComboBox cbPricingOn = new EMCFormComboBox(DebtorsPricingOn.values(), dataManager, "priceOn");

        EMCLookupFormComponent lkpRebateCode = new EMCLookupFormComponent(new DebtorsRebateCodesMI(), dataManager, "rebateCode");
        lkpRebateCode.setPopup(new EMCLookupPopup(new DebtorsRebateCodes(), "rebateCode", userData));

        Component[][] comp = {{new emcJLabel("Price group"), lkpPriceGroup},
            {new emcJLabel("Pricing On"), cbPricingOn},
            {new emcJLabel("Discount Group"), lkpDiscountGroup},
            {new emcJLabel("Extra Charge Group"), lkpExtraChargeGroup},
            {new emcJLabel(dataManager.getColumnName("rebateCode")), lkpRebateCode}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Pricing");
    }

    private Component salesTab() {
        //Sales
//        EMCLookupFormComponent lkpSalesGroup = new EMCLookupFormComponent(new DebtorsSalesGroupMenu(), dataManager, "salesGroup");
//        lkpSalesGroup.setPopup(new EMCLookupPopup(new DebtorsSalesGroup(), "salesGroup", userData));
        EMCLookupFormComponent lkpSalesRegion = new EMCLookupFormComponent(new DebtorsSalesRegionMenu(), dataManager, "salesRegion");
        lkpSalesRegion.setPopup(new EMCLookupPopup(new DebtorsSalesRegion(), "salesRegion", userData));
        EMCLookupFormComponent lkpSalesArea = new EMCLookupFormComponent(new DebtorsSalesAreaMenu(), dataManager, "salesArea");
        lkpSalesArea.setPopup(new EMCLookupPopup(new DebtorsSalesArea(), "salesArea", userData));
        EMCLookupFormComponent lkpSalesRep = new EMCLookupFormComponent(new SOPSalesRepGroupsMenu(), dataManager, "salesRep");
        lkpSalesRep.setPopup(new EMCLookupPopup(new SOPSalesRepGroups(), "groupManager", userData));
        //EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
        //query.addTableAnd(SOPSalesRepGroups.class.getName(), "employeeNumber", BaseEmployeeTable.class.getName(), "groupManager");
        //lkpSalesRep.setTheQuery(query);
        EMCFormComboBox cbRepService = new EMCFormComboBox(SOPCommissionTypes.values(), dataManager, "repService");

        emcJLabel lblExport = new emcJLabel(dataManager.getColumnName("export"));
        emcYesNoComponent ysnExport = new emcYesNoComponent(dataManager, "export");

        EMCLookupFormComponent lkpOrderWarehouse = new EMCLookupFormComponent(new Warehouse(), dataManager, "orderWarehouse");
        lkpOrderWarehouse.setPopup(new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", userData));
        emcYesNoComponent ynAllowSubstitute = new emcYesNoComponent(dataManager, "allowSubstituteItems");
        emcYesNoComponent ynRequireKimbling = new emcYesNoComponent(dataManager, "requireKimbling");
        emcYesNoComponent ynUseShipToKimbal = new emcYesNoComponent(dataManager, "useShipToKimble");
        emcYesNoComponent ynServiced = new emcYesNoComponent(dataManager, "serviced");

        Component[][] comp = {{new emcJLabel("Rep Service"), cbRepService, new emcJLabel("Allow Substitute Items"), ynAllowSubstitute},
            {new emcJLabel("Sales Rep"), lkpSalesRep, new emcJLabel("Order Warehouse"), lkpOrderWarehouse},
            {new emcJLabel("Sales Area"), lkpSalesArea, new emcJLabel("Require Kimbling"), ynRequireKimbling},
            {new emcJLabel("Sales Region"), lkpSalesRegion, new emcJLabel("Use Ship to for Kimbling"), ynUseShipToKimbal},
            {new emcJLabel()},
            {lblExport, ysnExport, new emcJLabel(dataManager.getColumnName("serviced")), ynServiced}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Sales/Delivery");
    }

    private emcJPanel deliveryTab() {
        emcJPanel deliveryTab = new emcJPanel(new BorderLayout());

        //Delivery
        EMCLookupFormComponent lkpDeliveryMethod = new EMCLookupFormComponent(new DeliveryModes(), dataManager, "deliveryMethod");
        lkpDeliveryMethod.setPopup(new EMCLookupPopup(new POPDeliveryModes(), "deliveryModeId", userData));
        EMCLookupFormComponent lkpDeliveryTerms = new EMCLookupFormComponent(new DeliveryTerms(), dataManager, "deliveryTerms");
        lkpDeliveryTerms.setPopup(new EMCLookupPopup(new POPDeliveryTerms(), "deliveryTermsId", userData));

        EMCFormComboBox cbDayOfWeek = new EMCFormComboBox(WeekDays.values(), dataManager, "deliveryDayOfWeek");
        emcJTextField txtLeadTime = new emcJTextField(new EMCIntegerFormDocument(dataManager, "deliveryLeadTime"));
        emcYesNoComponent ynBooking = new emcYesNoComponent(dataManager, "deliveryBookingRequired");
        emcYesNoComponent ynPartialDelivery = new emcYesNoComponent(dataManager, "allowPartialDelivery");

        emcJLabel lblDeliverBeforeDate = new emcJLabel(dataManager.getColumnName("deliverBeforeDate"));
        emcJTextField txtDeliverBeforeDate = new emcJTextField(new EMCIntegerFormDocument(dataManager, "deliverBeforeDate"));

        emcJLabel lblDeliveryCharge = new emcJLabel(dataManager.getColumnName("deliveryCharge"));
        emcYesNoComponent ysnDeliveryCharge = new emcYesNoComponent(dataManager, "deliveryCharge");

        emcJLabel lblDeliveryChargeCode = new emcJLabel(dataManager.getColumnName("deliveryChargeCode"));
        EMCLookupFormComponent lkpDeliveryChargeCode = new EMCLookupFormComponent(new DebtorsDeliveryCharge(), dataManager, "deliveryChargeCode");
        lkpDeliveryChargeCode.setPopup(new EMCLookupPopup(new emc.entity.debtors.DebtorsDeliveryCharge(), "deliveryChargeCode", userData));

        emcJLabel lblNominatedCourier = new emcJLabel(dataManager.getColumnName("nominatedCourier"));
        EMCLookupFormComponent lkpNominatedCourier = new EMCLookupFormComponent(new DebtorsCourier(), dataManager, "nominatedCourier");
        lkpNominatedCourier.setPopup(new EMCLookupPopup(new emc.entity.debtors.DebtorsCourier(), "courierId", userData));

        emcJLabel lblDeliveryInstructions = new emcJLabel(dataManager.getColumnName("deliveryInstructions"));
        emcJTextArea txaDeliveryInstructions = new emcJTextArea(new EMCStringFormDocument(dataManager, "deliveryInstructions"));

        EMCFormComboBox cbDeliveryRules = new EMCFormComboBox(SalesDeliveryRules.values(), dataManager, "deliveryRules");

        Component[][] components = new Component[][]{
            {new emcJLabel("Delivery Method"), lkpDeliveryMethod, new emcJLabel("Allow Partial Delivery"), ynPartialDelivery},
            {new emcJLabel("Delivery Lead Time"), txtLeadTime, lblDeliverBeforeDate, txtDeliverBeforeDate},
            {new emcJLabel("Delivery Terms"), lkpDeliveryTerms},
            {new emcJLabel("Delivery Day Of Week"), cbDayOfWeek, lblDeliveryCharge, ysnDeliveryCharge},
            {new emcJLabel("Delivery Booking Required"), ynBooking, lblDeliveryChargeCode, lkpDeliveryChargeCode},
            {new emcJLabel()},
            {lblNominatedCourier, lkpNominatedCourier, new emcJLabel("Delivery Rules"), cbDeliveryRules}
        };

        deliveryTab.add(emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true), BorderLayout.NORTH);

        components = new Component[][]{{txaDeliveryInstructions, lblDeliveryInstructions}};

        deliveryTab.add(emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true), BorderLayout.CENTER);

        return deliveryTab;
    }

    private Component companyInfoTab() {
        //Company
        emcJTextField txtCompanyRegistration = new emcJTextField(new EMCStringFormDocument(dataManager, "companyRegistrationNumber"));
        emcJTextField txtVatRegistation = new emcJTextField(new EMCStringFormDocument(dataManager, "vatRegistration"));
        EMCLookupFormComponent lkpVatCode = new EMCLookupFormComponent(new GLVATCode(), dataManager, "vatCode");
        lkpVatCode.setPopup(new EMCLookupPopup(new emc.entity.gl.GLVATCode(), "vatId", userData));
        //Banking
        emcJTextField txtBankName = new emcJTextField(new EMCStringFormDocument(dataManager, "bankName"));
        emcJTextField txtBranchCode = new emcJTextField(new EMCStringFormDocument(dataManager, "bankBranchCode"));
        emcJTextField txtAccountName = new emcJTextField(new EMCStringFormDocument(dataManager, "bankAccountName"));
        emcJTextField txtAccountNumber = new emcJTextField(new EMCStringFormDocument(dataManager, "bankAccountNumber"));

        emcJLabel lblResponsiblePersonName = new emcJLabel(dataManager.getColumnName("responsiblePerson"));
        emcJTextField txtResponsiblePersonName = new emcJTextField(new EMCStringFormDocument(dataManager, "responsiblePerson"));
        emcJLabel lblResponsiblePersonTelephone = new emcJLabel(dataManager.getColumnName("responsiblePersonTelNo"));
        emcJTextField txtResponsiblePersonTelephone = new emcJTextField(new EMCStringFormDocument(dataManager, "responsiblePersonTelNo"));
        emcJLabel lblResponsiblePersonCell = new emcJLabel(dataManager.getColumnName("responsiblePersonCellNo"));
        emcJTextField txtResponsiblePersonCell = new emcJTextField(new EMCStringFormDocument(dataManager, "responsiblePersonCellNo"));
        emcJLabel lblResponsiblePersonEmail = new emcJLabel(dataManager.getColumnName("responsiblePersonEmail"));
        emcJTextField txtResponsiblePersonEmail = new emcJTextField(new EMCStringFormDocument(dataManager, "responsiblePersonEmail"));

        Component[][] comp = {{new emcJLabel("Company Registration"), txtCompanyRegistration, new emcJLabel("Bank Name"), txtBankName,},
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

    private Component classificationsTab() {
        emcJLabel lblClassification1 = new emcJLabel(dataManager.getColumnName("classificationGroup1"));
        EMCLookupFormComponent lkpClassification1 = new EMCLookupFormComponent(new DebtorsCustomerClassification1Menu(), dataManager, "classificationGroup1");
        lkpClassification1.setPopup(new EMCLookupPopup(new SOPCustomerClassification1(), "classification1", userData));

        emcJLabel lblClassification2 = new emcJLabel(dataManager.getColumnName("classificationGroup2"));
        EMCLookupFormComponent lkpClassification2 = new EMCLookupFormComponent(new DebtorsCustomerClassification2Menu(), dataManager, "classificationGroup2");
        lkpClassification2.setPopup(new EMCLookupPopup(new SOPCustomerClassification2(), "classification2", userData));

        emcJLabel lblClassification3 = new emcJLabel(dataManager.getColumnName("classificationGroup3"));
        EMCLookupFormComponent lkpClassification3 = new EMCLookupFormComponent(new DebtorsCustomerClassification3Menu(), dataManager, "classificationGroup3");
        lkpClassification3.setPopup(new EMCLookupPopup(new SOPCustomerClassification3(), "classification3", userData));

        emcJLabel lblClassification4 = new emcJLabel(dataManager.getColumnName("classificationGroup4"));
        EMCLookupFormComponent lkpClassification4 = new EMCLookupFormComponent(new DebtorsCustomerClassification4Menu(), dataManager, "classificationGroup4");
        lkpClassification4.setPopup(new EMCLookupPopup(new SOPCustomerClassification4(), "classification4", userData));

        emcJLabel lblClassification5 = new emcJLabel(dataManager.getColumnName("classificationGroup5"));
        EMCLookupFormComponent lkpClassification5 = new EMCLookupFormComponent(new DebtorsCustomerClassification5Menu(), dataManager, "classificationGroup5");
        lkpClassification5.setPopup(new EMCLookupPopup(new SOPCustomerClassification5(), "classification5", userData));

        emcJLabel lblClassification6 = new emcJLabel(dataManager.getColumnName("classificationGroup6"));
        EMCLookupFormComponent lkpClassification6 = new EMCLookupFormComponent(new DebtorsCustomerClassification6Menu(), dataManager, "classificationGroup6");
        lkpClassification6.setPopup(new EMCLookupPopup(new SOPCustomerClassification6(), "classification6", userData));

        Component[][] comp = {
            {lblClassification1, lkpClassification1},
            {lblClassification2, lkpClassification2},
            {lblClassification3, lkpClassification3},
            {lblClassification4, lkpClassification4},
            {lblClassification5, lkpClassification5},
            {lblClassification6, lkpClassification6}
        };

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Classifications");
    }

    private emcJPanel exportTab() {
        emcJLabel lblCountryOfDestination = new emcJLabel(dataManager.getColumnName("countryOfDestination"));
        emcJLabel lblCurrency = new emcJLabel(dataManager.getColumnName("exportCurrency"));

        EMCLookupFormComponent lkpCountryOfDestination = new EMCLookupFormComponent(new Countries(), dataManager, "countryOfDestination");
        lkpCountryOfDestination.setPopup(new EMCLookupPopup(new BaseCountries(), "Code", userData));

        EMCLookupFormComponent lkpCurrency = new EMCLookupFormComponent(new GLCurrency(), dataManager, "exportCurrency");
        lkpCurrency.setPopup(new EMCLookupPopup(new emc.entity.gl.GLCurrency(), "currency", userData));

        Component[][] components = new Component[][]{
            {lblCountryOfDestination, lkpCountryOfDestination},
            {lblCurrency, lkpCurrency}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true, "Export");
    }

    /**
     * Creates buttons.
     */
    private emcJPanel createButtons() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        DebtorsCustomerBalance balanceItem = new DebtorsCustomerBalance();
        balanceItem.setDoNotOpenForm(false);
        buttons.add(new emcMenuButton("Balance", balanceItem, this, 1, false));

        buttons.add(new emcMenuButton("Transactions", new DebtorsTransactionsMenu(), this, 4, false));

        //buttons.add(new emcMenuButton("Post Dated Pmt.", new DebtorsPostDatedPayments(), this, 8, false));

        buttons.add(new TransSettlementButton(this, dataManager, 5));
        buttons.add(new ActivityButton(dataManager));

        emcMenuButtonList priceListButton = new emcMenuButtonList("Price List", this);
        priceListButton.addMenuItem("Customer", new SOPPriceArangementCustomerMenu(), 6, false);
        priceListButton.addMenuItem("All Appl.", new SOPPriceArangementMenu(), 7, false);
        buttons.add(priceListButton);

        buttons.add(emailButton);

        EMCMenuItem discountSetupItem = new DiscountSetupMI();
        emcMenuButtonList discountButton = new emcMenuButtonList("Discounts", this);
        discountButton.addMenuItem("Customer", discountSetupItem, 9, false);
        discountButton.addMenuItem("All Appl.", discountSetupItem, 10, false);

        buttons.add(discountButton);

        buttons.add(new emcMenuButton("Rep Enquiry", new SOPSalesRepCommissionMenu(), this, 11, false));
        buttons.add(new LocateButton(dataManager, "physicalAddresLine1", "physicalAddresLine2", "physicalAddresLine3", "physicalAddresLine4", "physicalAddresLine5", "physicalPostalCode"));

        emcJButton btnWebUser = new emcJButton("Web User") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.SOP.getId(), ServerSOPMethods.CREATE_WEB_USER.toString());
                SOPCustomers customer = (SOPCustomers) dataManager.getRowCache(-1);
                String password = generateString(new Random(), "123456789BCDFGHJKLMNPQRSTVWXYZ", 8);
                List toSend = new ArrayList();
                toSend.add(customer);
                toSend.add(password);
                toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                    Logger.getLogger("emc").log(Level.INFO, "Web User Created", userData);
                }
            }
        };
        buttons.add(btnWebUser);

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }

    public static String generateString(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.debtors.printinstruction;

import emc.bus.base.BaseCompanyLocal;
import emc.bus.base.BaseEmployeeLocal;
import emc.bus.creditors.settlementdiscountterms.CreditorsSettlementDiscountTermsLocal;
import emc.bus.debtors.basket.DebtorsBasketMasterLocal;
import emc.bus.debtors.creditnotereasons.DebtorsCreditNoteReasonsLocal;
import emc.bus.debtors.customerinvoice.DebtorsCustomerInvoiceMasterLocal;
import emc.bus.debtors.logic.customerbalance.DebtorsCustomerBalanceLocal;
import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.bus.inventory.InventoryParametersLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryDimension1Local;
import emc.bus.inventory.dimensions.InventoryDimension3Local;
import emc.bus.pop.POPDeliveryModesLocal;
import emc.entity.base.BaseCompanyTable;
import emc.entity.base.BaseCountries;
import emc.entity.base.BasePostalCodes;
import emc.entity.base.reporttools.BaseReportText;
import emc.entity.debtors.DebtorsBasketLines;
import emc.entity.debtors.DebtorsBasketMaster;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.entity.debtors.superclasses.DebtorsInvoiceMasterSuper;
import emc.entity.inventory.InventoryParameters;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.sop.SOPCustomers;
import emc.enums.debtors.invoice.DebtorsInvoicePrintType;
import emc.enums.debtors.transactions.DebtorsTransactionRefTypes;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author chris
 */
@Stateless
public class PrintInstructionReportBean extends EMCReportBean implements PrintInstructionReportLocal {

    @EJB
    private DebtorsBasketMasterLocal basketMasterBean;
    @EJB
    private InventoryReferenceLocal itemRefBean;
    @EJB
    private BaseCompanyLocal companyBean;
    @EJB
    private DebtorsParametersLocal parametersBean;
    @EJB
    private CreditorsSettlementDiscountTermsLocal settlementDiscTermsBean;
    @EJB
    private InventoryDimension1Local dimension1Bean;
    @EJB
    private InventoryDimension3Local dimension3Bean;
    @EJB
    private DebtorsCreditNoteReasonsLocal creditNoteReasonsBean;
    @EJB
    private BaseEmployeeLocal empBean;
    @EJB
    private POPDeliveryModesLocal delModeBean;
    @EJB
    private DebtorsCustomerInvoiceMasterLocal invoiceMasterBean;
    @EJB
    private DebtorsCustomerBalanceLocal customerBalanceBean;
    @EJB
    private InventoryParametersLocal invParamBean;
   
    @Override
    public List<Object> getReportResult(List<Object> parameters, EMCUserData userData) {
        return super.getReportResult(parameters, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        List reportList = new ArrayList();
        DebtorsBasketMaster basketMaster;
        DebtorsInvoiceMasterSuper invoiceMaster;
        invoiceMaster = new DebtorsInvoiceMasterSuper();
        PrintInstructionReportDS ds;
        BaseCompanyTable thisCompany = null;
        EMCQuery query;
        List<DebtorsBasketLines> basketLinesList;
        Map<String, List<String>> itemMap = new HashMap<String, List<String>>();
        List<String> item;
        Object[] custRef;
        DebtorsInvoicePrintType printType = DebtorsInvoicePrintType.PRINT_INSTRUCTION; //add this type to enum //DebtorsInvoicePrintType.fromString((String) userData.getUserData(0));
        Map<String, String> creditNoteReasons = new HashMap<String, String>();
        Map<String, String> dim1Map = new HashMap<String, String>();
        Map<String, String> dim3Map = new HashMap<String, String>();
        Map<String, String> countriesMap = new HashMap<String, String>();
        String faxNo = null;

        String countryOfOrigin = null;
        boolean printConfirmationFields = false;
        boolean printPrices = true;

        DebtorsParameters debtorsParam = parametersBean.getDebtorsParameters(userData);
        InventoryParameters invParam = null;
        try {
            invParam = invParamBean.getInventoryParameters(userData);
        } catch (EMCEntityBeanException ex) {
            Logger.getLogger(PrintInstructionReportBean.class.getName()).log(Level.SEVERE, "Please set inventory parameters", ex);
        }
        String cashTermsDesc = null;

        BaseReportText uniqueFooter = null;
        

        for (Object o : queryResult) {
            basketMaster = (DebtorsBasketMaster) o;

            String user = isBlank(basketMaster.getPostedBy()) ? userData.getUserName() : basketMaster.getPostedBy();

            faxNo = companyBean.getFaxNumber(user, enumEMCModules.DEBTORS, userData);

            String discountDate = null;
            BigDecimal discountAvailable = BigDecimal.ZERO;


            query = new EMCQuery(enumQueryTypes.SELECT, DebtorsBasketLines.class);

            //DebtorsInvoiceStatus status = DebtorsInvoiceStatus.fromString(basketMaster.getStatus());
           
            query.addAnd("basketId", basketMaster.getBasketId());
            query.openAndConditionBracket();
            query.addOr("itemId", debtorsParam.getDefaultItem(),EMCQueryConditions.EQUALS);
            query.addOr("itemId", invParam.getDefaultReminderItem(),EMCQueryConditions.EQUALS);
            query.closeConditionBracket();
            query.addOrderBy("lineNumber");
            basketLinesList = util.executeGeneralSelectQuery(query, userData);
            EMCQuery custQuery = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
            custQuery.addAnd("customerId", basketMaster.getCustomerId());
            SOPCustomers customer = (SOPCustomers) util.executeSingleResultQuery(custQuery, userData);
            if (customer == null) {
                System.out.print("Customer not found:" + basketMaster.getCustomerId());
                return reportList;

            }
            for (DebtorsBasketLines basketLine : basketLinesList) {
                if (thisCompany == null) {
                    thisCompany = companyBean.getUserCompany(userData);

                    query = new EMCQuery(enumQueryTypes.SELECT, BasePostalCodes.class);
                    query.addTableAnd(BaseCountries.class.getName(), "country", BasePostalCodes.class.getName(), "Code");
                    query.addField("Name", BaseCountries.class.getName());
                    query.addAnd("code", thisCompany.getCoPostCode());

                    countryOfOrigin = (String) util.executeSingleResultQuery(query, userData);
                }
                ds = new PrintInstructionReportDS();

                ds.setAuthorizationClaimNumber("");
                ds.setSalesRep("");
                ds.setSalesRepName("");

                StringBuilder specialInstructions = new StringBuilder("<b>Special Instructions:</b>&nbsp;&nbsp;");


                List<String> addressList = concertinaAddress(thisCompany.getCoPhysAdrs1(), thisCompany.getCoPhysAdrs2(),
                        thisCompany.getCoPhysAdrs3(), thisCompany.getCoPhysAdrs4(), thisCompany.getCoPhysAdrs5(), thisCompany.getCoPhysPostCode());

                ds.setCompanyPhysicalAddress1(addressList.get(0));
                ds.setCompanyPhysicalAddress2(addressList.get(1));
                ds.setCompanyPhysicalAddress3(addressList.get(2));
                ds.setCompanyPhysicalAddress4(addressList.get(3));
                ds.setCompanyPhysicalAddress5(addressList.get(4));
                ds.setCompanyPhysicalAddressCode(addressList.get(5));

                addressList = concertinaAddress(thisCompany.getCoPostAdrs1(), thisCompany.getCoPostAdrs2(),
                        thisCompany.getCoPostAdrs3(), thisCompany.getCoPostAdrs4(), thisCompany.getCoPostAdrs5(),
                        thisCompany.getCoPostCode());

                ds.setCompanyPostalAddress1(addressList.get(0));
                ds.setCompanyPostalAddress2(addressList.get(1));
                ds.setCompanyPostalAddress3(addressList.get(2));
                ds.setCompanyPostalAddress4(addressList.get(3));
                ds.setCompanyPostalAddress5(addressList.get(4));
                ds.setCompanyPostalAddressCode(addressList.get(5));
                ds.setCompanyPhone(thisCompany.getCoPhoneNr());
                ds.setCompanyFax(faxNo);
                ds.setCompanyRegistration(thisCompany.getCoRegNr());
                ds.setCompanyVATNumber(thisCompany.getCoVatRegNr());

                ds.setPrintConfimationFields(printConfirmationFields);
                ds.setPrintPrices(printPrices);

                ds.setInvoiceType(printType.toString());

                ds.setInvoiceNumber(basketMaster.getBasketId());
                if (!isBlank(basketMaster.getModifiedDate())) {
                ds.setInvoiceDate(Functions.date2String(basketMaster.getModifiedDate(), "dd/MM/yy"));
                }

                ds.setAccountNo(basketMaster.getCustomerId());

                //Use trading as instead of customer name
                ds.setCustomerName(customer.getTrandingAs());
                ds.setCustomerVATNo(customer.getVatRegistration());
                //create key for invoice
                String recId = String.valueOf(basketMaster.getRecordID());
                String key = "1";
                for (int i = recId.length(); i > 0; i--) {
                    key += recId.charAt(i -1);
                }
                key = Integer.toString(Integer.parseInt(key),16).toUpperCase();
                ds.setInvoiceKey(key);  

                addressList = concertinaAddress(customer.getPostalAddresLine1(), customer.getPostalAddresLine2(),
                        customer.getPostalAddresLine3(), customer.getPostalAddresLine4(), customer.getPostalAddresLine5(),
                        customer.getPostalPostalCode());
                ds.setCustomerInvoiceAddress1(addressList.get(0));
                ds.setCustomerInvoiceAddress2(addressList.get(1));
                ds.setCustomerInvoiceAddress3(addressList.get(2));
                ds.setCustomerInvoiceAddress4(addressList.get(3));
                ds.setCustomerInvoiceAddress5(addressList.get(4));
                ds.setCustomerInvoiceAddressCode(addressList.get(5));
                addressList = concertinaAddress(customer.getPhysicalAddresLine1(), customer.getPhysicalAddresLine2(),
                        customer.getPhysicalAddresLine3(), customer.getPhysicalAddresLine4(), customer.getPhysicalAddresLine5(),
                        customer.getPhysicalPostalCode());
                ds.setCustomerDeliveryAddress1(addressList.get(0));
                ds.setCustomerDeliveryAddress2(addressList.get(1));
                ds.setCustomerDeliveryAddress3(addressList.get(2));
                ds.setCustomerDeliveryAddress4(addressList.get(3));
                ds.setCustomerDeliveryAddress5(addressList.get(4));
                ds.setCustomerDeliveryAddressCode(addressList.get(5));
                ds.setSalesOrderNo("");
                ds.setDocumentNo("");
                ds.setCustomerOrderNo("");
                ds.setReference("");
                ds.setDeliveryMethod("");
                ds.setDeliveryMethodDescription("");


               // if(!isBlank(basketMaster.getModifiedDate())){
               // ds.setDeliveryDate(Functions.date2String(basketMaster.getModifiedDate()));
                //}



                item = itemMap.get(basketLine.getItemId());
                if (item == null) {
                    item = itemRefBean.findReferenceAndDesc(basketLine.getItemId(), InventoryReferenceTypes.PRIMARY, userData);
                    itemMap.put(basketLine.getItemId(), item);
                }
                ds.setItemReference(item.get(0));
                ds.setItemDescription(item.get(1));

                custRef = itemRefBean.getCustomerReference(basketLine.getItemId(), basketLine.getDimension1(), basketLine.getDimension2(), basketLine.getDimension3(), basketMaster.getCustomerId(), userData);
                if (custRef != null && custRef.length > 0) {
                    ds.setCustomerReference((String) custRef[0]);
                }

                ds.setDimension1(basketLine.getDimension1());
                if (!isBlank(basketLine.getDimension1())) {
                    String dimDesc = dim1Map.get(basketLine.getDimension1());

                    if (dimDesc == null) {
                        InventoryDimension1 dim = dimension1Bean.getDimension1(basketLine.getDimension1(), userData);

                        if (dim != null) {
                            dimDesc = dim.getDescription();
                            dim1Map.put(basketLine.getDimension1(), dimDesc);
                        }
                    }

                    ds.setItemDescription(ds.getItemDescription() + "<br />" + dimDesc);
                } else if (!isBlank(basketLine.getDimension3())) {
                    String dimDesc = dim1Map.get(basketLine.getDimension1());

                    if (dimDesc == null) {
                        InventoryDimension3 dim = dimension3Bean.getDimension3Record(basketLine.getDimension3(), userData);

                        if (dim != null) {
                            dimDesc = dim.getDescription();
                            dim3Map.put(basketLine.getDimension3(), dimDesc);
                        }
                    }

                    ds.setItemDescription(ds.getItemDescription() + "<br />" + dimDesc);
                }
                ds.setDimension2(basketLine.getDimension2());
                ds.setDimension3(basketLine.getDimension3());
                if (!isBlank(basketLine.getPrice())) {
                    ds.setItemPrice(basketLine.getPrice());
                }
                if (!isBlank(basketLine.getQuantity())) {
                    ds.setQuantity(basketLine.getQuantity());
                }
                if (!isBlank(basketLine.getVat())) {
                    ds.setVat(basketLine.getVat());
                }
                if (!isBlank(basketLine.getDiscountPercentage())) {
                    ds.setDiscountPerc(basketLine.getDiscountPercentage());
                }
                if (!isBlank(basketLine.getTotalPrice())) {
                    ds.setLineTotal(basketLine.getTotalPrice());

                    //totalCost on Invoice lines exludes VAT
                    ds.setNetAmount(basketLine.getTotalPrice());
                }
                if (!isBlank(basketLine.getDiscountAmount())) {
                    ds.setDiscountValue(basketLine.getDiscountAmount());
                }

                ds.setDiscountDate(discountDate);
                if (!isBlank(discountAvailable)) {
                    ds.setDiscountAvailable(discountAvailable);
                }

                ds.setNumberOfCartons(BigDecimal.ZERO);
                ds.setTotalWeight(BigDecimal.ZERO);
                ds.setDeliveryCharge(BigDecimal.ZERO);
                ds.setWaybillNumber("");

                ds.setInvoiceToCustomerName(customer.getCustomerName());

                ds.setExportCurrency(customer.getExportCurrency());
                ds.setBillingCurrency("");
                ds.setExportersCode("");
                ds.setSwiftCode("");

                ds.setCountryOfDestination("");
                ds.setCountryOfOrigin("");

                //Banking info
                //This field uses HTML markup to ensure that headings are displayed in bold, but still utilize space as effectively as possible.
                StringBuilder bankingInfo = new StringBuilder("<b>Banking Details:</b>&nbsp;&nbsp;");
                bankingInfo.append(thisCompany.getCoBank());
                bankingInfo.append("&nbsp;&nbsp;<b>Branch:</b>&nbsp;&nbsp;");
                bankingInfo.append(thisCompany.getCoBankBranch());
                bankingInfo.append("&nbsp;&nbsp;<b>Account:</b>&nbsp;&nbsp;");
                bankingInfo.append(thisCompany.getCoBankAccNo());
                bankingInfo.append("&nbsp;&nbsp;<b>SWIFT Code:</b>&nbsp;&nbsp;");
                bankingInfo.append(thisCompany.getSwiftCode() == null ? "" : thisCompany.getSwiftCode());

                specialInstructions.append("<br/><b>Delivery Instructions:</b>&nbsp;&nbsp;" + (customer.getDeliveryInstructions() == null ? "" : customer.getDeliveryInstructions()));
                specialInstructions.append("<br/>");    //Blank line
                specialInstructions.append("<br/>");
                specialInstructions.append(bankingInfo.toString());

                ds.setSpecialInstructions(specialInstructions.toString());

                ds.setInvoiceCurrency(thisCompany.getCurrency());
                           
            
//            EMCQuery creditQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactions.class);
//            creditQuery.addTableAnd(DebtorsJournalMaster.class.getName(), "transactionSource", DebtorsTransactions.class.getName(), "journalNumber");
//            creditQuery.addFieldAggregateFunction("debit", DebtorsTransactions.class.getName(), "SUM");
//            creditQuery.addFieldAggregateFunction("credit", DebtorsTransactions.class.getName(), "SUM");
//            creditQuery.addAnd("customerId", customer.getInvoiceToCustomer());
//            creditQuery.openConditionBracket(EMCQueryBracketConditions.AND);
//            creditQuery.addOr("referenceType", DebtorsTransactionRefTypes.DEBIT_JOURNAL.toString(), EMCQueryConditions.EQUALS);
//            creditQuery.addOr("referenceType", DebtorsTransactionRefTypes.CREDIT_JOURNAL.toString(), EMCQueryConditions.EQUALS);
//            creditQuery.closeConditionBracket();
//            creditQuery.openConditionBracket(EMCQueryBracketConditions.AND);
//            creditQuery.addOr("journalDefinitionId", debtorsParam.getCreditJournalDef(),DebtorsJournalMaster.class.getName(), EMCQueryConditions.EQUALS);
//            creditQuery.addOr("journalDefinitionId", debtorsParam.getDebitJournalDef(),DebtorsJournalMaster.class.getName(), EMCQueryConditions.EQUALS);
//            creditQuery.closeConditionBracket();
//
//            
//            
//            List<Object[]> credAmounts =  util.executeGeneralSelectQuery(creditQuery, userData);
//            BigDecimal balance =  BigDecimal.ZERO;
//            BigDecimal debit = BigDecimal.ZERO;
//            BigDecimal credi = BigDecimal.ZERO;
//            BigDecimal creditAmt = BigDecimal.ZERO;
//            if (credAmounts == null) {
//                balance = BigDecimal.ZERO;
//            }
//            for (Object[] cred: credAmounts){
//             if (cred.length>0){
//               credi = (BigDecimal) cred[1]; 
//               debit = (BigDecimal) cred[0];
//               if (credi == null){
//                   credi= BigDecimal.ZERO;
//               }if (debit == null){
//                   debit = BigDecimal.ZERO;
//               }
//               creditAmt = credi.subtract(debit);
//               
//               balance= creditAmt.round(new MathContext(4, RoundingMode.HALF_UP)) ;
//             }ds.setCreditAvailable(balance);
//            }
            
            ds.setCreditAvailable(customerBalanceBean.getCreditsCustomerBalance(customer.getInvoiceToCustomer(), userData));

                if (uniqueFooter != null) {
                    ds.setUniqueFooter(uniqueFooter.getText());
                }

                reportList.add(ds);
            }

        }

        return reportList;
    }
}

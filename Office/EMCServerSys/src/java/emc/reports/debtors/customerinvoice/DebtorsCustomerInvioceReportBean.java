/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.debtors.customerinvoice;

import emc.bus.base.BaseCompanyLocal;
import emc.bus.base.BaseEmployeeLocal;
import emc.bus.creditors.settlementdiscountterms.CreditorsSettlementDiscountTermsLocal;
import emc.bus.debtors.creditnotereasons.DebtorsCreditNoteReasonsLocal;
import emc.bus.debtors.customerinvoice.DebtorsCustomerInvoiceMasterLocal;
import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryDimension1Local;
import emc.bus.inventory.dimensions.InventoryDimension3Local;
import emc.bus.pop.POPDeliveryModesLocal;
import emc.entity.base.BaseCompanyTable;
import emc.entity.base.BaseCountries;
import emc.entity.base.BasePostalCodes;
import emc.entity.base.reporttools.BaseReportText;
import emc.entity.creditors.CreditorsSettlementDiscountTerms;
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCreditNoteReasons;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.debtors.superclasses.DebtorsInvoiceLinesSuper;
import emc.entity.debtors.superclasses.DebtorsInvoiceMasterSuper;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.enums.base.reporttools.BaseReportSectionEnum;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.debtors.DebtorsInvoiceCreditNoteType;
import emc.enums.debtors.invoice.DebtorsInvoicePrintType;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.math.BigDecimal;
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
 * @author wikus
 */
@Stateless
public class DebtorsCustomerInvioceReportBean extends EMCReportBean implements DebtorsCustomerInvioceReportLocal {

    @EJB
    private DebtorsCustomerInvoiceMasterLocal invoiceMasterBean;
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

    @Override
    public List<Object> getReportResult(List<Object> parameters, EMCUserData userData) {
        return super.getReportResult(parameters, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        List reportList = new ArrayList();
        DebtorsInvoiceMasterSuper invoiceMaster;
        DebtorsCustomerInvioceReportDS ds;
        BaseCompanyTable thisCompany = null;
        EMCQuery query;
        List<DebtorsInvoiceLinesSuper> invoiceLinesList;
        Map<String, List<String>> itemMap = new HashMap<String, List<String>>();
        List<String> item;
        Object[] custRef;
        DebtorsInvoicePrintType printType = DebtorsInvoicePrintType.fromString((String) userData.getUserData(0));
        Map<String, String> creditNoteReasons = new HashMap<String, String>();
        Map<String, String> dim1Map = new HashMap<String, String>();
        Map<String, String> dim3Map = new HashMap<String, String>();
        Map<String, String> countriesMap = new HashMap<String, String>();
        String faxNo = null;

        String countryOfOrigin = null;
        boolean printConfirmationFields = false;
        boolean printPrices = true;

        DebtorsParameters debtorsParam = null;
        String cashTermsDesc = null;

        BaseReportText uniqueFooter = null;
        if (printType != null) {
            switch (printType) {
                case CREDIT_NOTE:
                case CREDIT_NOTE_COPY:
                case PROFORMA_CREDIT_NOTE:
                    uniqueFooter = reportTextBean.getReportText(EnumReports.DEBTORS_CREDIT_NOTE, BaseReportSectionEnum.FOOTER, userData);
                    break;
                case TAX_INVOICE:
                case TAX_INVOICE_COPY:
                case PROFORMA_INVOICE:
                    uniqueFooter = reportTextBean.getReportText(EnumReports.DEBTORS_CUSTOMER_INVOICE, BaseReportSectionEnum.FOOTER, userData);
                    break;
                case DELIVERY_NOTE:
                    uniqueFooter = reportTextBean.getReportText(EnumReports.DEBTORS_DELIVERY_NOTE, BaseReportSectionEnum.FOOTER, userData);
                    break;
            }
        }

        if (DebtorsInvoicePrintType.DELIVERY_NOTE.equals(printType)) {
            printConfirmationFields = true;

            DebtorsParameters parameters = parametersBean.getDebtorsParameters(userData);
            if (parameters != null) {
                printPrices = parameters.isPrintPricesOnDeliveryNote();
            }
        }

        for (Object o : queryResult) {
            invoiceMaster = (DebtorsInvoiceMasterSuper) o;

            String user = isBlank(invoiceMaster.getPostedBy()) ? userData.getUserName() : invoiceMaster.getPostedBy();

            faxNo = companyBean.getFaxNumber(user, enumEMCModules.DEBTORS, userData);

            String discountDate = null;
            BigDecimal discountAvailable = BigDecimal.ZERO;

            DebtorsInvoiceCreditNoteType invoiceType = DebtorsInvoiceCreditNoteType.fromString(invoiceMaster.getInvCNType());

            if (invoiceType == DebtorsInvoiceCreditNoteType.MANUAL_INVOICE || invoiceType == DebtorsInvoiceCreditNoteType.SALES_INVOICE || invoiceType == DebtorsInvoiceCreditNoteType.DISTRIBUTION_INVOICE) {
                if (invoiceMaster.getSettlementDiscDate() != null) {
                    discountDate = Functions.date2String(invoiceMaster.getSettlementDiscDate(), "dd/MM/yy");
                }

                discountAvailable = calculateSettlementDiscAvailable(invoiceMaster, userData);

                query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
            } else {
                query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteLines.class);
            }

            DebtorsInvoiceStatus status = DebtorsInvoiceStatus.fromString(invoiceMaster.getStatus());

            query.addAnd("invCNNumber", invoiceMaster.getInvCNNumber());
            query.addOrderBy("lineNo");
            invoiceLinesList = util.executeGeneralSelectQuery(query, userData);
            for (DebtorsInvoiceLinesSuper invoiceLines : invoiceLinesList) {
                if (thisCompany == null) {
                    thisCompany = companyBean.getUserCompany(userData);

                    query = new EMCQuery(enumQueryTypes.SELECT, BasePostalCodes.class);
                    query.addTableAnd(BaseCountries.class.getName(), "country", BasePostalCodes.class.getName(), "Code");
                    query.addField("Name", BaseCountries.class.getName());
                    query.addAnd("code", thisCompany.getCoPostCode());

                    countryOfOrigin = (String) util.executeSingleResultQuery(query, userData);
                }
                ds = new DebtorsCustomerInvioceReportDS();

                ds.setAuthorizationClaimNumber((invoiceMaster.getAuthorizationNo() == null ? "" : invoiceMaster.getAuthorizationNo()) + "/" + (invoiceMaster.getClaimNo() == null ? "" : invoiceMaster.getClaimNo()));
                ds.setSalesRep(invoiceMaster.getSalesRep());
                ds.setSalesRepName(empBean.findEmployeeNameAndSurname(invoiceMaster.getSalesRep(), userData));

                StringBuilder specialInstructions = new StringBuilder("<b>Special Instructions:</b>&nbsp;&nbsp;");

                if (invoiceMaster instanceof DebtorsCreditNoteMaster) {
                    String reasonDesc = creditNoteReasons.get(invoiceMaster.getReasonCode());

                    if (reasonDesc == null) {
                        DebtorsCreditNoteReasons reason = creditNoteReasonsBean.getReason(invoiceMaster.getReasonCode(), userData);
                        if (reason == null) {
                            reasonDesc = invoiceMaster.getReasonCode();
                        } else {
                            reasonDesc = reason.getDescription();
                        }

                        creditNoteReasons.put(invoiceMaster.getReasonCode(), reasonDesc);
                    }

                    if (reasonDesc != null) {
                        specialInstructions.append(reasonDesc);
                        specialInstructions.append("<br />");
                    }
                }

                if (invoiceMaster.getComments() != null) {
                    specialInstructions.append(invoiceMaster.getComments().replaceAll("\\n", "<br />"));
                }

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

                //Print invoices that have not been posted as Pro Forma invoices
                if ((DebtorsInvoicePrintType.TAX_INVOICE.equals(printType) || DebtorsInvoicePrintType.TAX_INVOICE_COPY.equals(printType))
                        && !DebtorsInvoiceStatus.POSTED.equals(status) && !DebtorsInvoiceStatus.CANCELED.equals(status) && !DebtorsInvoiceStatus.DISTRIBUTION.equals(status)) {
                    ds.setInvoiceType(DebtorsInvoicePrintType.PROFORMA_INVOICE.toString());
                } else if ((DebtorsInvoicePrintType.CREDIT_NOTE.equals(printType) || DebtorsInvoicePrintType.CREDIT_NOTE_COPY.equals(printType))
                        && !DebtorsInvoiceStatus.POSTED.equals(status) && !DebtorsInvoiceStatus.CANCELED.equals(status) && !DebtorsInvoiceStatus.DISTRIBUTION.equals(status)) {
                    ds.setInvoiceType(DebtorsInvoicePrintType.PROFORMA_CREDIT_NOTE.toString());
                } else {
                    if (DebtorsInvoiceStatus.DISTRIBUTION.toString().equals(invoiceMaster.getStatus())) {
                        switch (printType) {
                            case TAX_INVOICE:
                                ds.setInvoiceType(DebtorsInvoicePrintType.DIST_INVOICE.toString());
                                break;
                            case TAX_INVOICE_COPY:
                                ds.setInvoiceType(DebtorsInvoicePrintType.DIST_INVOICE_COPY.toString());
                                break;
                            default:
                                ds.setInvoiceType(printType.toString());
                                break;
                        }
                    }else if(DebtorsInvoiceStatus.CANCELED.toString().equals(invoiceMaster.getStatus())){
                        switch(printType){
                            case TAX_INVOICE:
                                ds.setInvoiceType(DebtorsInvoicePrintType.CANCELLED.toString());
                                break;
                            case TAX_INVOICE_COPY:
                                ds.setInvoiceType(DebtorsInvoicePrintType.CANCELLED_COPY.toString());
                                break;
                            default:
                                ds.setInvoiceType(printType.toString());
                                break;
                        }
                        
                    }else {
                        ds.setInvoiceType(printType.toString());
                    }
                }

                ds.setInvoiceNumber(invoiceMaster.getInvCNNumber());
                ds.setInvoiceDate(Functions.date2String(invoiceMaster.getInvoiceDate(), "dd/MM/yy"));

                ds.setAccountNo(invoiceMaster.getCustomerNo());
                //Use trading as instead of customer name
                ds.setCustomerName(invoiceMaster.getCustomerTradingAs());
                ds.setCustomerVATNo(invoiceMaster.getInvoiceToCustomerVatRegistration());
                //create key for invoice
                
                String recId = String.valueOf(invoiceMaster.getRecordID());
                String key = "1";
                for (int i = recId.length(); i > 0; i--) {
                    key += recId.charAt(i -1);
                }
                key = Integer.toString(Integer.parseInt(key),16).toUpperCase();
                ds.setInvoiceKey(key);                

                addressList = concertinaAddress(invoiceMaster.getInvoiceAddress1(), invoiceMaster.getInvoiceAddress2(),
                        invoiceMaster.getInvoiceAddress3(), invoiceMaster.getInvoiceAddress4(), invoiceMaster.getInvoiceAddress5(),
                        invoiceMaster.getInvoiceAddressPostalCode());
                ds.setCustomerInvoiceAddress1(addressList.get(0));
                ds.setCustomerInvoiceAddress2(addressList.get(1));
                ds.setCustomerInvoiceAddress3(addressList.get(2));
                ds.setCustomerInvoiceAddress4(addressList.get(3));
                ds.setCustomerInvoiceAddress5(addressList.get(4));
                ds.setCustomerInvoiceAddressCode(addressList.get(5));
                addressList = concertinaAddress(invoiceMaster.getDeliveryAddress1(), invoiceMaster.getDeliveryAddress2(),
                        invoiceMaster.getDeliveryAddress3(), invoiceMaster.getDeliveryAddress4(), invoiceMaster.getDeliveryAddress5(),
                        invoiceMaster.getDeliveryAddressPostalCode());
                ds.setCustomerDeliveryAddress1(addressList.get(0));
                ds.setCustomerDeliveryAddress2(addressList.get(1));
                ds.setCustomerDeliveryAddress3(addressList.get(2));
                ds.setCustomerDeliveryAddress4(addressList.get(3));
                ds.setCustomerDeliveryAddress5(addressList.get(4));
                ds.setCustomerDeliveryAddressCode(addressList.get(5));
                ds.setSalesOrderNo(invoiceMaster.getSalesOrderNo());
                ds.setDocumentNo(invoiceMaster.getInvCNNumber());
                ds.setCustomerOrderNo(invoiceMaster.getCustomerOrderNumber());
                ds.setReference(DebtorsInvoicePrintType.CREDIT_NOTE.equals(printType) || DebtorsInvoicePrintType.CREDIT_NOTE_COPY.equals(printType) || DebtorsInvoicePrintType.PROFORMA_CREDIT_NOTE.equals(printType) ? invoiceMaster.getRefInvoiceNo() : invoiceMaster.getReference());
                ds.setDeliveryMethod(invoiceMaster.getDeliveryMethod());
                ds.setDeliveryMethodDescription(delModeBean.findDescription(invoiceMaster.getDeliveryMethod(), userData));
                ds.setDeliveryDate(Functions.date2String(invoiceMaster.getInvoiceDate()));
                item = itemMap.get(invoiceLines.getItemId());
                if (item == null) {
                    item = itemRefBean.findReferenceAndDesc(invoiceLines.getItemId(), InventoryReferenceTypes.PRIMARY, userData);
                    itemMap.put(invoiceLines.getItemId(), item);
                }
                ds.setItemReference(item.get(0));
                ds.setItemDescription(item.get(1));

                custRef = itemRefBean.getCustomerReference(invoiceLines.getItemId(), invoiceLines.getDimension1(), invoiceLines.getDimension2(), invoiceLines.getDimension3(), invoiceMaster.getCustomerNo(), userData);
                if (custRef != null && custRef.length > 0) {
                    ds.setCustomerReference((String) custRef[0]);
                }

                ds.setDimension1(invoiceLines.getDimension1());
                if (!isBlank(invoiceLines.getDimension1())) {
                    String dimDesc = dim1Map.get(invoiceLines.getDimension1());

                    if (dimDesc == null) {
                        InventoryDimension1 dim = dimension1Bean.getDimension1(invoiceLines.getDimension1(), userData);

                        if (dim != null) {
                            dimDesc = dim.getDescription();
                            dim1Map.put(invoiceLines.getDimension1(), dimDesc);
                        }
                    }

                    ds.setItemDescription(ds.getItemDescription() + "<br />" + dimDesc);
                } else if (!isBlank(invoiceLines.getDimension3())) {
                    String dimDesc = dim1Map.get(invoiceLines.getDimension1());

                    if (dimDesc == null) {
                        InventoryDimension3 dim = dimension3Bean.getDimension3Record(invoiceLines.getDimension3(), userData);

                        if (dim != null) {
                            dimDesc = dim.getDescription();
                            dim3Map.put(invoiceLines.getDimension3(), dimDesc);
                        }
                    }

                    ds.setItemDescription(ds.getItemDescription() + "<br />" + dimDesc);
                }
                ds.setDimension2(invoiceLines.getDimension2());
                ds.setDimension3(invoiceLines.getDimension3());
                ds.setItemPrice(invoiceLines.getUnitPrice());
                ds.setQuantity(invoiceLines.getQuantity());
                ds.setVat(invoiceLines.getVatAmount());
                ds.setDiscountPerc(invoiceLines.getDiscountPercentage());
                ds.setLineTotal(invoiceLines.getTotalCost());

                //totalCost on Invoice lines exludes VAT
                ds.setNetAmount(invoiceLines.getTotalCost());
                ds.setDiscountValue(invoiceLines.getDiscountAmount());

                ds.setDiscountDate(discountDate);
                ds.setDiscountAvailable(discountAvailable);
                if (discountAvailable.compareTo(BigDecimal.ZERO) > 0) {
                    ds.setSettlementDiscountDetails("Settlement disc R " + util.getBigDecimal(discountAvailable.doubleValue()) + (discountDate == null ? "" : " if paid by " + discountDate));
                }

                ds.setNumberOfCartons(invoiceMaster.getNumberOfCartons() == null ? BigDecimal.ZERO : invoiceMaster.getNumberOfCartons());
                ds.setTotalWeight(invoiceMaster.getTotalWeight() == null ? BigDecimal.ZERO : invoiceMaster.getTotalWeight());
                ds.setDeliveryCharge(invoiceMaster.getDeliveryCharge() == null ? BigDecimal.ZERO : invoiceMaster.getDeliveryCharge());
                ds.setWaybillNumber(invoiceMaster.getWaybillNumber());

                ds.setInvoiceToCustomerName(invoiceMaster.getInvoiceToCustomerName());

                ds.setExportCurrency(invoiceMaster.getInvoiceToCustomerExportCurrency());
                ds.setBillingCurrency(invoiceMaster.getBillingCurrency());
                ds.setExportersCode(thisCompany.getExportersCode());
                ds.setSwiftCode(thisCompany.getSwiftCode());

                String countryOfDestination = countriesMap.get(invoiceMaster.getInvoiceToCustomerCountryOfDestination());
                if (countryOfDestination == null && !isBlank(invoiceMaster.getInvoiceToCustomerCountryOfDestination())) {
                    query = new EMCQuery(enumQueryTypes.SELECT, BaseCountries.class);
                    query.addField("Name");
                    query.addAnd("Code", invoiceMaster.getInvoiceToCustomerCountryOfDestination());

                    countryOfDestination = (String) util.executeSingleResultQuery(query, userData);
                    countriesMap.put(invoiceMaster.getInvoiceToCustomerCountryOfDestination(), countryOfDestination);
                } else {
                    countryOfDestination = countriesMap.get(invoiceMaster.getInvoiceToCustomerCountryOfDestination());
                }

                ds.setCountryOfDestination(countryOfDestination);
                ds.setCountryOfOrigin(countryOfOrigin);

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

                specialInstructions.append("<br/><b>Delivery Instructions:</b>&nbsp;&nbsp;" + (invoiceMaster.getInvoiceToCustomerDeliveryInstructions() == null ? "" : invoiceMaster.getInvoiceToCustomerDeliveryInstructions()));
                specialInstructions.append("<br/>");    //Blank line
                specialInstructions.append("<br/>");
                specialInstructions.append(bankingInfo.toString());

                ds.setSpecialInstructions(specialInstructions.toString());

                ds.setInvoiceCurrency(thisCompany.getCurrency());

                if (debtorsParam == null) {
                    debtorsParam = parametersBean.getDebtorsParameters(userData);
                }
                if (debtorsParam != null && !isBlank(debtorsParam.getCashTermsCode())) {
                    if (util.checkObjectsEqual(invoiceMaster.getTermsCode(), debtorsParam.getCashTermsCode())) {
                        ///The report should check whether this field is null - if not, print "CASH".
                        ds.setCashTermsOfPayment(debtorsParam.getCashTermsCode());
                    }
                }

                if (uniqueFooter != null) {
                    ds.setUniqueFooter(uniqueFooter.getText());
                }

                reportList.add(ds);
            }

            try {
                //Do not update printed dates for copies or delivery notes.
                if (!DebtorsInvoicePrintType.PROFORMA_INVOICE.equals(printType) && !DebtorsInvoicePrintType.DELIVERY_NOTE.equals(printType) && !DebtorsInvoicePrintType.PROFORMA_CREDIT_NOTE.equals(printType)) {
                    invoiceMasterBean.invoicePrinted(invoiceMaster.getInvCNNumber(), invoiceType, userData);
                }
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to update print dates.", userData);
                return new ArrayList<Object>();
            }
        }

        return reportList;
    }

    /**
     * Calculates the settlement discount available on an invoice.
     */
    private BigDecimal calculateSettlementDiscAvailable(DebtorsInvoiceMasterSuper invoiceMaster, EMCUserData userData) {
        CreditorsSettlementDiscountTerms settlementDiscTerms = settlementDiscTermsBean.getSettlementDiscountTerms(invoiceMaster.getSettlementDiscCode(), userData);

        if (settlementDiscTerms != null) {
            BigDecimal total = invoiceMasterBean.getInvoiceTotal(invoiceMaster.getInvCNNumber(), false, userData);
            BigDecimal vat = invoiceMasterBean.getTotalVAT(invoiceMaster.getInvCNNumber(), userData);

            total = total.add(vat);

            return total.multiply(new BigDecimal(settlementDiscTerms.getDiscountPercentage()).divide(new BigDecimal(100)));
        } else {
            return BigDecimal.ZERO;
        }
    }
}

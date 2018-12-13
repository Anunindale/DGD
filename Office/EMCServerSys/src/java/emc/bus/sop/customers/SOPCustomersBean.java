package emc.bus.sop.customers;

import emc.bus.base.BaseCompanyLocal;
import emc.bus.base.webportal.BaseWebPortalUsersLocal;
import emc.bus.crm.prospect.CRMProspectLocal;
import emc.bus.debtors.creditnotes.DebtorsCreditNoteMasterLocal;
import emc.bus.debtors.customerinvoice.DebtorsCustomerInvoiceMasterLocal;
import emc.bus.debtors.logic.aging.DebtorsAgingLocal;
import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.bus.sop.pricearangements.SOPPriceArangementsLocal;
import emc.bus.trec.treccards.TRECTrecCardsLinesLocal;
import emc.bus.trec.treccards.TRECTrecCardsMasterLocal;
import emc.entity.base.webportal.BaseWebPortalUsers;
import emc.entity.crm.CRMProspect;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.entity.trec.TRECTrecCardsLines;
import emc.entity.trec.TRECTrecCardsMaster;
import emc.enums.base.webportalusers.WebPortalUsersReferenceType;
import emc.enums.crm.CRMTransfers;
import emc.enums.debtors.transactions.DebtorsTransactionRefTypes;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.enums.sop.commission.SOPCommissionTypes;
import emc.enums.sop.customers.CustomerStatusEnum;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.base.EMCEmail;
import emc.helpers.customeractivity.CustomerActivityHelper;
import emc.helpers.debtors.DebtorsWebRegistrationHelper;
import emc.messages.ServerDebtorsMessageEnum;
import emc.server.mailmanager.EMCMailManagerLocal;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @Author wikus
 */
@Stateless
public class SOPCustomersBean extends EMCEntityBean implements SOPCustomersLocal {

    @EJB
    private CRMProspectLocal prospectBean;
    @EJB
    private DebtorsAgingLocal agingBean;
    @EJB
    private DebtorsCustomerInvoiceMasterLocal invoiceMasterBean;
    @EJB
    private DebtorsCreditNoteMasterLocal creditNoteMasterBean;
    @EJB
    private EMCMailManagerLocal mailManager;
    @EJB
    private BaseWebPortalUsersLocal webPortalUsersBean;
    @EJB
    private BaseCompanyLocal companyBean;
    @EJB
    private DebtorsParametersLocal debtorsParametersBean;
    @EJB
    private SOPPriceArangementsLocal priceArrangmentsBean;
    @EJB
    private TRECTrecCardsMasterLocal trecCardMasterBean;
    @EJB
    private TRECTrecCardsLinesLocal trecCardLinesBean;

    public SOPCustomers createCustomerFromProspect(CRMProspect prospect, EMCUserData userData) {
        SOPCustomers customer = new SOPCustomers();
        customer.setCustomerComapny(prospect.getCompany());
        customer.setCustomerName(prospect.getName());
        customer.setPhysicalAddresLine1(prospect.getPostalAddress1());
        customer.setPhysicalAddresLine2(prospect.getPostalAddress2());
        customer.setPhysicalAddresLine3(prospect.getPostalAddress3());
        customer.setPhysicalAddresLine4(prospect.getPostalAddress4());
        customer.setPhysicalAddresLine5(prospect.getPostalAddress5());
        customer.setPhysicalPostalCode(prospect.getPostalPostalCode());
        customer.setProspectId(prospect.getProspectId());
        customer.setTelephoneNumber(prospect.getTelNum());
        customer.setCellNumber(prospect.getCellNum());
        customer.setEmail(prospect.getEmail());
        customer.setEmergencyNumber(prospect.getTelNum());
        return customer;
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doDeleteValidation(vobject, userData);

        if (!ret) {
            //Assume that deletion failed because customer has been used
            logMessage(Level.SEVERE, "Customers may not be deleted after being used.  Close the customer.", userData);
        }

        return ret;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);

        if (ret) {
            SOPCustomers customer = (SOPCustomers) vobject;

            if (SOPCommissionTypes.SINGLE.toString().equals(customer.getRepService()) && isBlank(customer.getSalesRep())) {
                logMessage(Level.SEVERE, "Please select the Sales Rep for the customer.", userData);
                return false;
            }

            if (!Functions.checkBlank(customer.getCreditInsuredBy())) {
                return this.validateInsuranceFields(customer, userData);
            }

            ret = ret && validateInvoiceToCustomer(customer.getCustomerId(), customer.getInvoiceToCustomer(), userData);
        }

        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);

        if (ret) {
            SOPCustomers customer = (SOPCustomers) vobject;
            SOPCustomers persisted = (SOPCustomers) util.findDetachedPersisted(customer, userData);

            if (SOPCommissionTypes.SINGLE.toString().equals(customer.getRepService()) && isBlank(customer.getSalesRep())) {
                logMessage(Level.SEVERE, "Please select the Sales Rep for the customer.", userData);
                return false;
            }

            if (customer.isCreditStoped() && isBlank(customer.getCreditStopReason())) {
                logMessage(Level.SEVERE, "Credit may not be stopped without giving a reason.", userData);
                return false;
            }

            if (!util.checkObjectsEqual(customer.getInvoiceToCustomer(), persisted.getInvoiceToCustomer())) {
                return this.validateInvoiceToCustomer(customer.getCustomerId(), customer.getInvoiceToCustomer(), userData);
            }

            if (!Functions.checkBlank(customer.getCreditInsuredBy())) {
                return this.validateInsuranceFields(customer, userData);
            }


        }

        return ret;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        SOPCustomers customer = (SOPCustomers) iobject;

        //Check whether invoice customer is blank, and user has specified that
        //this customer should be its own invoice to customer.  The boolean flag
        //in position 3 of userData will be set by the customer form's DRM.
        boolean setInvoiceCustToSelf = false;
        if (isBlank(customer.getInvoiceToCustomer()) && userData.getUserData(3) == Boolean.TRUE) {
            setInvoiceCustToSelf = true;
        }

        customer = (SOPCustomers) super.insert(iobject, userData);

        if (setInvoiceCustToSelf) {
            customer.setInvoiceToCustomer(customer.getCustomerId());
            //Update directly.  The doUpdateValidation() method on this class
            //will fall over when it tries to fetch a persisted version of the
            //customer record from SQL.  
            super.doUpdate(customer, userData);
        }

        if (!isBlank(customer.getProspectId())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CRMProspect.class.getName());
            query.addAnd("prospectId", customer.getProspectId());
            CRMProspect prospect = (CRMProspect) util.executeSingleResultQuery(query, userData);
            if (prospect != null) {
                prospect.setTransferedTo(CRMTransfers.CUSTOMERS.toString());
                prospect.setTransferedRecordId(customer.getCustomerId());
                prospect.setTransfered(true);
                prospectBean.update(prospect, userData);
            }
        }

        if (!isBlank(customer.getClosedReason())) {
            this.customerClosed(customer, userData);
        }

        if (customer.isCreditStoped()) {
            this.customerStopped(customer, userData);
        }

        if (isBlank(customer.getTrandingAs())) {
            customer.setTrandingAs(customer.getCustomerName());
        }
        if (isBlank(customer.getEmergencyNumber())) {
            customer.setEmergencyNumber(customer.getTelephoneNumber());
        }

        return customer;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        SOPCustomers customer = (SOPCustomers) uobject;
        SOPCustomers persisted = (SOPCustomers) util.findDetachedPersisted(customer, userData);

        if (!util.checkObjectsEqual(customer.getClosedReason(), persisted.getClosedReason())) {
            if (isBlank(customer.getClosedReason())) {
                customerUnclosed(customer, userData);
            } else {
                customerClosed(customer, userData);
            }
        }

        if (!util.checkObjectsEqual(customer.isCreditStoped(), persisted.isCreditStoped())) {
            if (customer.isCreditStoped()) {
                customerStopped(customer, userData);
            } else {
                customerUnstopped(customer, userData);
            }
        }
        if (!util.checkObjectsEqual(customer.getEmail(), persisted.getEmail())) {
            if (customer.getEmail() != null) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseWebPortalUsers.class);
                query.addAnd("linkToSourceRecId", persisted.getRecordID());
                query.addAnd("userId", persisted.getEmail());
                BaseWebPortalUsers user = (BaseWebPortalUsers) util.executeSingleResultQuery(query, userData);
                if (user != null) {
                    user.setUserId(customer.getEmail());
                    userData.setUserData(5, Boolean.TRUE);
                    webPortalUsersBean.update(user, userData);
                }
            }
        }


        if (!util.checkObjectsEqual(customer.getPriceGroup(), persisted.getPriceGroup())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
            query.addAnd("invoiceToCustomer", customer.getCustomerId());
            query.addAnd("customerId", customer.getInvoiceToCustomer(), EMCQueryConditions.NOT);

            List<SOPCustomers> shipToCustomers = (List<SOPCustomers>) util.executeGeneralSelectQuery(query, userData);

            for (SOPCustomers shipToCustomer : shipToCustomers) {
                shipToCustomer.setPriceGroup(customer.getPriceGroup());

                this.update(shipToCustomer, userData);
            }
        }

        if (isBlank(customer.getTrandingAs())) {
            customer.setTrandingAs(customer.getCustomerName());
        }
        if (isBlank(customer.getEmergencyNumber())) {
            customer.setEmergencyNumber(customer.getTelephoneNumber());
        }


        if (userData.getUserData(4) != null && userData.getUserData(4) == Boolean.TRUE) {
            updadteInvoiceCustomerInfo(customer, userData);
            updadteInvoiceInvoiceToInfo(customer, userData);
            updadteCreditNoteCustomerInfo(customer, userData);
            updadteCreditNoteInvoiceToInfo(customer, userData);
            if (!util.checkObjectsEqual(customer.getCustomerName(), persisted.getCustomerName())) {
                // update TrecCards Master
                EMCQuery masterQuery = new EMCQuery(enumQueryTypes.SELECT, TRECTrecCardsMaster.class);
                masterQuery.addAnd("customerId", customer.getCustomerId());
                List<TRECTrecCardsMaster> masterList = util.executeGeneralSelectQuery(masterQuery, userData);

                for (TRECTrecCardsMaster master : masterList) {
                    master.setTrecCompanyName(customer.getCustomerName());

                    EMCQuery linesQuery = new EMCQuery(enumQueryTypes.SELECT, TRECTrecCardsLines.class);
                    linesQuery.addAnd("masterId", master.getMasterId());
                    List<TRECTrecCardsLines> lineList = util.executeGeneralSelectQuery(linesQuery, userData);

                    trecCardMasterBean.update(master, userData);

                    for (TRECTrecCardsLines line : lineList) {
                        trecCardLinesBean.updateLinesFields(master.getMasterId(), master.getEmergencyNumber(), master.getTrecCompanyName(), userData);

                    }
                }
            }
        }

        return super.update(uobject, userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (ret) {
            SOPCustomers customer = (SOPCustomers) theRecord;

            if (CustomerStatusEnum.CLOSED.equals(CustomerStatusEnum.fromString(customer.getCustomerStatus())) && !"closedReason".equals(fieldNameToValidate)) {
                logMessage(Level.SEVERE, "Closed customers may not be changed.", userData);
                return false;
            }

            if (fieldNameToValidate.equals("customerId")) {
                if (isBlank(customer.getInvoiceToCustomer()) && !isBlank(customer.getCustomerId())) {
                    customer.setInvoiceToCustomer(customer.getCustomerId());
                    return customer;
                }
            }

            if (fieldNameToValidate.equals("closedReason")) {
                if (!isBlank(customer.getClosedReason())) {
                    customer.setClosedBy(userData.getUserName());
                    customer.setClosedDate(new Date());
                } else {
                    customer.setClosedBy(null);
                    customer.setClosedDate(null);
                }
                return customer;
            } else if (fieldNameToValidate.equals("invoiceToCustomer")) {
                return this.validateInvoiceToCustomer(customer.getCustomerId(), customer.getInvoiceToCustomer(), userData);
            }

            if (fieldNameToValidate.equals("repService")) {
                if (SOPCommissionTypes.MULTIPLE.toString().equals(customer.getRepService())) {
                    customer.setSalesRep(null);
                    return customer;
                }
            }

            if (fieldNameToValidate.equals("salesRep")) {
                if (SOPCommissionTypes.MULTIPLE.toString().equals(customer.getRepService())) {
                    logMessage(Level.SEVERE, "A sales rep is not required with the Multiple rep service.", userData);
                    customer.setSalesRep(null);
                    return customer;
                }
            }

            if (fieldNameToValidate.equals("priceGroup")) {
                if (!isBlank(customer.getInvoiceToCustomer()) && !util.checkObjectsEqual(customer.getCustomerId(), customer.getInvoiceToCustomer())) {
                    SOPCustomers invoiceTo = findCustomer(customer.getInvoiceToCustomer(), userData);

                    if (invoiceTo == null) {
                        logMessage(Level.SEVERE, "Invoice to customer not found.", userData);
                        return false;
                    }
                    if (!util.checkObjectsEqual(invoiceTo.getPriceGroup(), customer.getPriceGroup())) {
                        logMessage(Level.SEVERE, "Price group on Ship To customers may not differ from that on the Invoice To customer.", userData);
                        return false;
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Returns a customer record for the customer with the specified customer
     * id.
     *
     * @param customerId Id of customer to search for.
     * @param userData User data.
     * @return A customer record for the customer with the specified customer id
     * or null, if no record is found.
     */
    public SOPCustomers findCustomer(String customerId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
        query.addAnd("customerId", customerId);

        return (SOPCustomers) util.executeSingleResultQuery(query, userData);
    }

    /**
     * Returns a CustomerActivityHelper instance containing customer activity
     * information.
     *
     * @param customerId Customer id.
     * @param userData User data.
     * @return A CustomerActivityHelper instance containing customer activity
     * information for the specified customer.
     */
    public CustomerActivityHelper getCustomerActivity(String customerId, EMCUserData userData) {
        CustomerActivityHelper activityHelper = new CustomerActivityHelper();

        SOPCustomers customer = findCustomer(customerId, userData);

        activityHelper.setCustomerId(customerId);
        activityHelper.setCustomerName(customer.getCustomerName());

        EMCQuery openSalesOrdersQuery = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderMaster.class);
        openSalesOrdersQuery.addAnd("salsesOrderStatus", emc.enums.sop.salesorders.SalesOrderStatus.INVOICED.toString(), EMCQueryConditions.NOT);
        openSalesOrdersQuery.addAnd("salsesOrderStatus", emc.enums.sop.salesorders.SalesOrderStatus.CANCELLED.toString(), EMCQueryConditions.NOT);
        openSalesOrdersQuery.addAnd("customerNo", customerId);

        EMCQuery openSalesCountQuery = openSalesOrdersQuery.copyQuery();
        openSalesCountQuery.addFieldAggregateFunction("recordID", "COUNT");

        Long numberOfOpenSalesOrders = (Long) util.executeSingleResultQuery(openSalesCountQuery, userData);

        if (numberOfOpenSalesOrders == null) {
            activityHelper.setNumberOfOpenOrders(0);
            activityHelper.setOpenOrderValue(BigDecimal.ZERO);
        } else {
            //Select all Sales Order no's
            openSalesOrdersQuery.addField("salesOrderNo");

            EMCQuery openSalesOrderTotalQuery = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderLines.class);
            openSalesOrderTotalQuery.addFieldAggregateFunction("lineTotal", "SUM");
            openSalesOrderTotalQuery.addAnd("salesOrderNo", openSalesOrdersQuery, EMCQueryConditions.IN);

            BigDecimal openSalesOrderTotal = (BigDecimal) util.executeSingleResultQuery(openSalesOrderTotalQuery, userData);

            activityHelper.setOpenOrderValue(openSalesOrderTotalQuery == null ? BigDecimal.ZERO : openSalesOrderTotal);
            activityHelper.setNumberOfOpenOrders(numberOfOpenSalesOrders.intValue());
        }

        EMCQuery lastInvoiceQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactions.class);
        lastInvoiceQuery.addAnd("referenceType", DebtorsTransactionRefTypes.INVOICE.toString());
        lastInvoiceQuery.addAnd("customerId", customerId);
        lastInvoiceQuery.addOrderBy("transactionDate", DebtorsTransactions.class.getName(), EMCQueryOrderByDirections.DESC);

        //May be more than one trans - take the top one.
        List<DebtorsTransactions> invoiceTransactions = (List<DebtorsTransactions>) util.executeGeneralSelectQuery(lastInvoiceQuery, userData);

        if (invoiceTransactions.size() > 0) {
            DebtorsTransactions invoiceTransaction = invoiceTransactions.get(0);
            activityHelper.setLastInvoiceNo(invoiceTransaction.getReferenceNumber());
            activityHelper.setLastInvoiceDate(invoiceTransaction.getTransactionDate());
            activityHelper.setLastInvoiceAmount(invoiceTransaction.getDebit());
        }

        EMCQuery lastCreditNoteQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactions.class);
        lastCreditNoteQuery.addAnd("referenceType", DebtorsTransactionRefTypes.CREDIT_NOTE.toString());
        lastCreditNoteQuery.addAnd("customerId", customerId);
        lastCreditNoteQuery.addOrderBy("transactionDate", DebtorsTransactions.class.getName(), EMCQueryOrderByDirections.DESC);

        //May be more than one trans - take the top one.
        List<DebtorsTransactions> creditNoteTransactions = (List<DebtorsTransactions>) util.executeLimitedResultGeneralSelectQuery(lastCreditNoteQuery, 0, 1, userData);

        if (creditNoteTransactions.size() > 0) {
            DebtorsTransactions creditNoteTransaction = creditNoteTransactions.get(0);
            activityHelper.setLastCreditNoteNo(creditNoteTransaction.getReferenceNumber());
            activityHelper.setLastCreditNoteDate(creditNoteTransaction.getTransactionDate());
            activityHelper.setLastCreditNoteAmount(creditNoteTransaction.getDebit());
        }

        EMCQuery salesOrderMasterQuery = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderMaster.class);
        salesOrderMasterQuery.addAnd("customerNo", customerId);

        EMCQuery salesOrderMasterMaxRecIdQuery = salesOrderMasterQuery.copyQuery();
        salesOrderMasterMaxRecIdQuery.addFieldAggregateFunction("recordID", "MAX");
        salesOrderMasterMaxRecIdQuery.addAnd("customerNo", customerId);

        salesOrderMasterQuery.addAnd("recordID", salesOrderMasterMaxRecIdQuery);

        SOPSalesOrderMaster salesOrderMaster = (SOPSalesOrderMaster) util.executeSingleResultQuery(salesOrderMasterQuery, userData);

        if (salesOrderMaster != null) {
            activityHelper.setLastSalesOrderNo(salesOrderMaster.getSalesOrderNo());
            activityHelper.setLastSalesOrderDate(salesOrderMaster.getCreatedDate());

            EMCQuery salesOrderTotalQuery = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderLines.class);
            salesOrderTotalQuery.addFieldAggregateFunction("lineTotal", "SUM");
            salesOrderTotalQuery.addAnd("salesOrderNo", salesOrderMaster.getSalesOrderNo());

            BigDecimal salesOrderTotal = (BigDecimal) util.executeSingleResultQuery(salesOrderTotalQuery, userData);

            activityHelper.setLastSalesOrderAmount(salesOrderTotal == null ? BigDecimal.ZERO : salesOrderTotal);
        }

        EMCQuery lastPaymentQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactions.class);
        lastPaymentQuery.addAnd("referenceType", DebtorsTransactionRefTypes.PAYMENT.toString());
        lastPaymentQuery.addAnd("customerId", customerId);
        lastPaymentQuery.addOrderBy("transactionDate", DebtorsTransactions.class.getName(), EMCQueryOrderByDirections.DESC);

        //May be more than one trans - take the top one.
        List<DebtorsTransactions> paymentTransactions = (List<DebtorsTransactions>) util.executeLimitedResultGeneralSelectQuery(lastPaymentQuery, 0, 1, userData);

        for (DebtorsTransactions paymentTransaction : paymentTransactions) {
            activityHelper.setLastPaymentNo(paymentTransaction.getReferenceNumber());
            activityHelper.setLastPaymentDate(paymentTransaction.getTransactionDate());
            activityHelper.setLastPaymentAmount(paymentTransaction.getCredit());

            break;
        }

        EMCQuery lastReturnedPaymentQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactions.class);
        lastReturnedPaymentQuery.addAnd("customerId", customerId);
        lastReturnedPaymentQuery.addAnd("referenceType", DebtorsTransactionRefTypes.RETURNED_PAYMENT.toString());
        lastReturnedPaymentQuery.addOrderBy("transactionDate", DebtorsTransactions.class.getName(), EMCQueryOrderByDirections.DESC);

        //May be more than one trans - take the top one.
        List<DebtorsTransactions> returnedPaymentTransactions = (List<DebtorsTransactions>) util.executeLimitedResultGeneralSelectQuery(lastReturnedPaymentQuery, 0, 1, userData);

        for (DebtorsTransactions returnedPaymentTransaction : returnedPaymentTransactions) {
            activityHelper.setLastReturnedPaymentNo(returnedPaymentTransaction.getReferenceNumber());
            activityHelper.setLastReturnedPaymentDate(returnedPaymentTransaction.getTransactionDate());
            activityHelper.setLastReturnedPaymentAmount(returnedPaymentTransaction.getDebit());

            break;
        }

        return activityHelper;
    }

    /**
     * Sets closed date and closed by on the specified customer.
     */
    private void customerClosed(SOPCustomers customer, EMCUserData userData) throws EMCEntityBeanException {
        //Customer may not be closed if the customer has an outstanding balance.
        if (agingBean.getBalanceAtDate(Functions.nowDate(), customer.getCustomerId(), false, null, userData).compareTo(BigDecimal.ZERO) > 0) {
            throw new EMCEntityBeanException("A customer may only be closed when the customer has a balance of zero.");
        } else {
            customer.setClosedBy(userData.getUserName());
            customer.setClosedDate(Functions.nowDate());
            customer.setCustomerStatus(CustomerStatusEnum.CLOSED.toString());
        }
    }

    /**
     * Clears closed date and closed by on the specified customer.
     */
    private void customerUnclosed(SOPCustomers customer, EMCUserData userData) {
        customer.setClosedBy(null);
        customer.setClosedDate(null);

        if (customer.isCreditStoped()) {
            customer.setCustomerStatus(CustomerStatusEnum.STOPPED.toString());
        } else {
            customer.setCustomerStatus(CustomerStatusEnum.ACTIVE.toString());
        }
    }

    /**
     * Sets stopped by on the specified customer.
     */
    private void customerStopped(SOPCustomers customer, EMCUserData userData) {
        customer.setStoppedBy(userData.getUserName());
        customer.setDateStoped(Functions.nowDate());
        customer.setCustomerStatus(CustomerStatusEnum.STOPPED.toString());
    }

    /**
     * Clears stopped by on the specified customer.
     */
    private void customerUnstopped(SOPCustomers customer, EMCUserData userData) {
        customer.setStoppedBy(null);
        customer.setCustomerStatus(CustomerStatusEnum.ACTIVE.toString());
        customer.setDateStoped(null);

        logMessage(Level.WARNING, "Credit facilities reinstated.", userData);
    }

    /**
     * Validates the specified invoiceToCustomer.
     *
     * @param customerId Customer for which invoice to customer is being
     * changed.
     * @param invoiceToCustomer New invoice to customer.
     * @param userData User data.
     * @return A boolean indicating whether the invoice to customer may be
     * changed.
     */
    public boolean validateInvoiceToCustomer(String customerId, String invoiceToCustomer, EMCUserData userData) {
        //If a customer is set as it's own invoice customer, return true
        if (util.checkObjectsEqual(customerId, invoiceToCustomer)) {
            return true;
        }

        //Don't validate invoice to customer if it still needs to be set by the
        //insert method.  The flag in position three of user data will be set
        //in the DRM on the customer form.  This indicates the a new customer
        //is being saved and that it should be it's own invoice-to customer.
        if (userData.getUserData(3) != Boolean.TRUE) {
            SOPCustomers customer = this.findCustomer(invoiceToCustomer, userData);
            if (customer == null) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INV_TO_CUST_NOT_FOUND, userData);
                return false;
            }

            if (invoiceToCustomer != null) {
                if (!util.checkObjectsEqual(customer.getCustomerId(), customer.getInvoiceToCustomer())) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INVALID_INV_TO_CUST, userData);
                    return false;
                } else if (customerId != null && agingBean.getBalanceAtDate(Functions.nowDate(), customerId, false, null, userData).compareTo(BigDecimal.ZERO) != 0) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.MAY_NOT_CHANGE_INV_CUST, userData, customerId);
                    return false;
                }

            }
        }
        return true;
    }

    /**
     * Checks that all insurance fields has values. This method should be called
     * on insert/update validation if a credit insurer is set.
     *
     * @param customer Customer to validate.
     * @param userData User data.
     * @return A boolean indicating whether the information on the specified
     * customer is valid.
     */
    private boolean validateInsuranceFields(SOPCustomers customer, EMCUserData userData) {
        //TODO:  SOP Message enum
        if (Functions.checkBlank(customer.getCreditInsuredExpiryDate())) {
            logMessage(Level.SEVERE, customer.getDisplayLabelForField("creditInsuredExpiryDate", userData) + " is required.", userData);
            return false;
        }

        if (Functions.checkBlank(customer.getInsurerFileRef())) {
            logMessage(Level.SEVERE, customer.getDisplayLabelForField("insurerFileRef", userData) + " is required.", userData);
            return false;
        }

        if (Functions.checkBlank(customer.getInsuredDocRef())) {
            logMessage(Level.SEVERE, customer.getDisplayLabelForField("insuredDocRef", userData) + " is required.", userData);
            return false;
        }

        return true;
    }

    public List<String> getCustomersForAdminTool(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
        query.addField("customerId");
        List<String> cutomerList = util.executeGeneralSelectQuery(query, userData);
        return cutomerList;
    }

    private void updadteInvoiceCustomerInfo(SOPCustomers customer, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.UPDATE, DebtorsCustomerInvoiceMaster.class);
        query.addAnd("customerNo", customer.getCustomerId());
        query.addSet("customerTradingAs", customer.getTrandingAs());
        query.addSet("salesArea", customer.getSalesArea());
        query.addSet("salesGroup", customer.getSalesGroup());
        query.addSet("salesRegion", customer.getSalesRegion());
        query.addSet("deliveryAddress1", customer.getPhysicalAddresLine1());
        query.addSet("deliveryAddress2", customer.getPhysicalAddresLine2());
        query.addSet("deliveryAddress3", customer.getPhysicalAddresLine3());
        query.addSet("deliveryAddress4", customer.getPhysicalAddresLine4());
        query.addSet("deliveryAddress5", customer.getPhysicalAddresLine5());
        query.addSet("deliveryAddressPostalCode", customer.getPhysicalPostalCode());
        query.addSet("orderWarehouse", customer.getOrderWarehouse());
        query.addSet("deliveryTerms", customer.getDeliveryTerms());
        query.addSet("deliveryMethod", customer.getDeliveryMethod());

        util.executeUpdate(query, userData);
    }

    private void updadteInvoiceInvoiceToInfo(SOPCustomers customer, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.UPDATE, DebtorsCustomerInvoiceMaster.class);
        query.addAnd("invoiceToCustNo", customer.getCustomerId());
        query.addSet("invoiceToCustomerName", customer.getCustomerName());
        query.addSet("invoiceToCustomerExportCurrency", customer.getExportCurrency());
        query.addSet("invoiceToCustomerCountryOfDestination", customer.getCountryOfDestination());
        query.addSet("invoiceToCustomerDeliveryInstructions", customer.getDeliveryInstructions());
        query.addSet("invoiceToCustomerVatRegistration", customer.getVatRegistration());
        query.addSet("invoiceAddress1", customer.getPostalAddresLine1());
        query.addSet("invoiceAddress2", customer.getPostalAddresLine2());
        query.addSet("invoiceAddress3", customer.getPostalAddresLine3());
        query.addSet("invoiceAddress4", customer.getPostalAddresLine4());
        query.addSet("invoiceAddress5", customer.getPostalAddresLine5());
        query.addSet("invoiceAddressPostalCode", customer.getPostalPostalCode());
        query.addSet("vatNo", customer.getVatRegistration());
        query.addSet("vatCode", customer.getVatCode());
        query.addSet("termsCode", customer.getTermsOfPayment());
        query.addSet("settlementDiscCode", customer.getSettlementDiscount());
        query.addSet("pricingGroup", customer.getPriceGroup());
        query.addSet("discountGroup", customer.getDiscountGroup());

        util.executeUpdate(query, userData);

    }

    private void updadteCreditNoteCustomerInfo(SOPCustomers customer, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.UPDATE, DebtorsCreditNoteMaster.class);
        query.addAnd("customerNo", customer.getCustomerId());
        query.addSet("customerTradingAs", customer.getTrandingAs());
        query.addSet("salesArea", customer.getSalesArea());
        query.addSet("salesGroup", customer.getSalesGroup());
        query.addSet("salesRegion", customer.getSalesRegion());
        query.addSet("deliveryAddress1", customer.getPhysicalAddresLine1());
        query.addSet("deliveryAddress2", customer.getPhysicalAddresLine2());
        query.addSet("deliveryAddress3", customer.getPhysicalAddresLine3());
        query.addSet("deliveryAddress4", customer.getPhysicalAddresLine4());
        query.addSet("deliveryAddress5", customer.getPhysicalAddresLine5());
        query.addSet("deliveryAddressPostalCode", customer.getPhysicalPostalCode());
        query.addSet("orderWarehouse", customer.getOrderWarehouse());
        query.addSet("settlementDiscCode", customer.getSettlementDiscount());

        util.executeUpdate(query, userData);
    }

    private void updadteCreditNoteInvoiceToInfo(SOPCustomers customer, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.UPDATE, DebtorsCreditNoteMaster.class);
        query.addAnd("invoiceToCustNo", customer.getCustomerId());
        query.addSet("invoiceToCustomerName", customer.getCustomerName());
        query.addSet("invoiceToCustomerExportCurrency", customer.getExportCurrency());
        query.addSet("invoiceToCustomerCountryOfDestination", customer.getCountryOfDestination());
        query.addSet("invoiceToCustomerDeliveryInstructions", customer.getDeliveryInstructions());
        query.addSet("invoiceToCustomerVatRegistration", customer.getVatRegistration());
        query.addSet("invoiceAddress1", customer.getPostalAddresLine1());
        query.addSet("invoiceAddress2", customer.getPostalAddresLine2());
        query.addSet("invoiceAddress3", customer.getPostalAddresLine3());
        query.addSet("invoiceAddress4", customer.getPostalAddresLine4());
        query.addSet("invoiceAddress5", customer.getPostalAddresLine5());
        query.addSet("invoiceAddressPostalCode", customer.getPostalPostalCode());
        query.addSet("vatNo", customer.getVatRegistration());
        query.addSet("vatCode", customer.getVatCode());

        util.executeUpdate(query, userData);
    }

    @Override
    public boolean doWebRegistration(DebtorsWebRegistrationHelper helper, EMCUserData userData) throws EMCEntityBeanException {
        if (helper != null) {
            SOPCustomers customer = helper.getCustomer();
            if (!isBlank(customer)) {
                DebtorsParameters param = debtorsParametersBean.getDebtorsParameters(userData);
                if (param == null) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.PARAM_NOT_FOUND, userData);
                    return false;
                }
                //set mandatory default
                customer.setCustomerGroup(param.getCustomerGroup());
                customer.setMarketingGroup(param.getMarketingGroup());
                customer.setTermsOfPayment(param.getTermsOfPayment());
                customer.setSettlementDiscount(param.getSettlementDiscount());
                customer.setPriceGroup(param.getPriceGroup());
                customer.setOrderWarehouse(param.getOrderWarehouse());
                customer.setDeliveryMethod(param.getDeliveryMethod());
                customer.setDeliveryRules(param.getDeliveryRules());
                customer.setVatCode(param.getVatCode());
                customer.setInvoiceToCustomer(customer.getCustomerId());
                customer.setEmergencyNumber(customer.getTelephoneNumber());
                //set update flag
                userData.setUserData(3, true);

                this.insert(customer, userData);
                createWebPortalUser(customer, helper.getTempPassword(), userData);
            }
        }

        return true;
    }

    public boolean createWebPortalUser(SOPCustomers customer, String password, EMCUserData userData) throws EMCEntityBeanException {
        BaseWebPortalUsers webUser;

        if (!isBlank(customer.getEmail())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseWebPortalUsers.class);
            query.addAnd("userId", customer.getEmail());
            webUser = (BaseWebPortalUsers) util.executeSingleResultQuery(query, userData);
            if (webUser != null) {
                throw new EMCEntityBeanException("The email address " + customer.getEmail() + " has already been registered to an account.");
            }
        }

        if (isBlank(customer.getEmail())) {
            throw new EMCEntityBeanException("The email address is not captured. Can not create a web user account");
        }
        BigDecimal ordinaryPrice = priceArrangmentsBean.findItemPrice("C01071", "TRW", null, null, null, dateHandler.nowDate(), BigDecimal.ONE, userData);
        BigDecimal creditsPrice = priceArrangmentsBean.findItemPrice("C01073", "TRW", null, null, null, dateHandler.nowDate(), BigDecimal.ONE, userData);


        webUser = new BaseWebPortalUsers();
        webUser.setUserId(customer.getEmail());
        webUser.setPassword(password);
        webUser.setActive(true);
        webUser.setLinkToSourceType(WebPortalUsersReferenceType.CAPTURED.toString());
        webUser.setLinkToSourceRecId(customer.getRecordID());
        webPortalUsersBean.insert(webUser, userData);

        StringBuilder message = new StringBuilder();
        String website = isBlank(companyBean.getUserCompany(userData).getCoWebsite()) ? "the website with the credentials below:" : "at " + companyBean.getUserCompany(userData).getCoWebsite();
        message.append("Dear " + customer.getAccountContactName() + "\n");
        message.append("\n");
        //message.append(isBlank(companyBean.getUserCompany(userData).getCoTradingAs()) ? companyBean.getUserCompany(userData).getCompanyName() : companyBean.getUserCompany(userData).getCoTradingAs() + " web credentials has been created for you.\n");
        message.append("Thank you for registering on our website. You can now order Transport Emergency Cards online and print them yourself. \n");
        message.append("The cost of each card is R" + ordinaryPrice + " (Excluding VAT). \n");
        message.append("\n");
        message.append("Your login details for ordering online are: \n");
        message.append("<b>Username :</b> " + customer.getEmail() + "\n");
        message.append("<b>Password &nbsp:</b> " + password + "\n");
        message.append("\n");
        message.append("If you would like to enquire about our pre-paid option at R" + creditsPrice + " per card, please send an email to info@trec.co.za. \n");
        message.append("\n");
        message.append("Please do not hesitate to contact us should you need any assistance.\n");
        message.append("\n");
        message.append("Did you know that we sell the following products? \n");

        message.append("<ul><li>Dangerous Goods Placards for vehicles.</li> \n");

        message.append("<li>Orange warning diamonds.</li> \n");

        message.append("<li>Document holders.</li> \n");

        message.append("<li>We also have a software package available for companies who require printing Transport Emergency Cards in-house.</li></ul> \n  ");
        message.append("If you would like to enquire about any of our products, please send an email to info@trec.co.za.\n");
        message.append("\n");
        message.append("Regards,\n");
        message.append("Lizet \n");

        message.append("\n");
        message.append("<b>Registration Details</b> \n");
        message.append("\n");
        message.append("<table> <tr><td>");
        message.append("<b>Company Name  &nbsp</b></td><td>" + customer.getCustomerName() + "</td></tr>");
        message.append("<tr><td><b>Contact Person  &nbsp </b></td><td>" + customer.getAccountContactName() + "</td></tr>");
        message.append("<tr><td><b>Contact Number &nbsp&nbsp </b></td><td>" + customer.getTelephoneNumber() + "</td></tr>");
        message.append("<tr><td><b>Contact Email  &nbsp </b></td><td>" + customer.getEmail() + "</td></tr>");
        message.append("<tr><td><b>VAT Number &nbsp  </b></td><td>" + customer.getVatRegistration() + "</td></tr>");
        message.append("<tr><td><b>Where Heard  &nbsp </b></td><td>" + customer.getWhereHeard() + "</td></tr></table>");
        message.append("\n");

        message.append("<table><tr> <th><b>Physical Address</b> </th>");
        message.append("<th><b>Postal Address</b> </th></tr>");

        if (!isBlank(customer.getPhysicalAddresLine1())) {
            message.append("<tr><td>" + customer.getPhysicalAddresLine1() + "\n");
        }
        if (!isBlank(customer.getPhysicalAddresLine2())) {
            message.append(customer.getPhysicalAddresLine2() + "\n");
        }
        if (!isBlank(customer.getPhysicalAddresLine3())) {
            message.append(customer.getPhysicalAddresLine3() + "\n");
        }
        if (!isBlank(customer.getPhysicalAddresLine4())) {
            message.append(customer.getPhysicalAddresLine4() + "\n");
        }
        if (!isBlank(customer.getPhysicalAddresLine5())) {
            message.append(customer.getPhysicalAddresLine5() + "\n");
        }
        if (!isBlank(customer.getPhysicalPostalCode())) {
            message.append(customer.getPhysicalPostalCode() + "</td>");
        }
        message.append("\n");


        if (!isBlank(customer.getPostalAddresLine1())) {
            message.append("<td>" + customer.getPostalAddresLine1() + "\n");
        }
        if (!isBlank(customer.getPostalAddresLine2())) {
            message.append(customer.getPostalAddresLine2() + "\n");
        }
        if (!isBlank(customer.getPostalAddresLine3())) {
            message.append(customer.getPostalAddresLine3() + "\n");
        }
        if (!isBlank(customer.getPostalAddresLine4())) {
            message.append(customer.getPostalAddresLine4() + "\n");
        }
        if (!isBlank(customer.getPostalAddresLine5())) {
            message.append(customer.getPostalAddresLine5() + "\n");
        }
        if (!isBlank(customer.getPostalPostalCode())) {
            message.append(customer.getPostalPostalCode() + "</td></tr></table>");
        }
        message.append("\n");


        // message.append(isBlank(companyBean.getUserCompany(userData).getCoTradingAs()) ? companyBean.getUserCompany(userData).getCompanyName() : companyBean.getUserCompany(userData).getCoTradingAs());

        try {
            EMCEmail email = new EMCEmail();
            email.addRecipient(customer.getCustomerId(), customer.getEmail());
            email.setSubject(isBlank(companyBean.getUserCompany(userData).getCoTradingAs()) ? companyBean.getUserCompany(userData).getCompanyName() + " Web Credentials" : companyBean.getUserCompany(userData).getCoTradingAs() + " Web Credentials");
            email.setMessage(message.toString());
            mailManager.sendEmail(email, userData);
            logMessage(Level.INFO, "Registration processed succesfully. A confirmation email has been sent to " + customer.getEmail(), userData);
        } catch (Exception ex) {
            throw new EMCEntityBeanException(ex);
        }


        return true;
    }

    @Override
    public boolean findWebPortalUser(DebtorsWebRegistrationHelper helper, EMCUserData userData) throws EMCEntityBeanException {
        BaseWebPortalUsers webUser;

        if (helper == null) {
            logMessage(Level.SEVERE, "Failed to process request: " + helper, userData);
            return false;
        }
        if (!isBlank(helper.getMailToEmail())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseWebPortalUsers.class);
            query.addAnd("userId", helper.getMailToEmail());
            webUser = (BaseWebPortalUsers) util.executeSingleResultQuery(query, userData);
            if (webUser == null) {
                throw new EMCEntityBeanException("No web user found with the username " + helper.getMailToEmail());
            } else {
                query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
                query.addAnd("recordID", webUser.getLinkToSourceRecId());
                SOPCustomers customer = (SOPCustomers) util.executeSingleResultQuery(query, userData);


                StringBuilder message = new StringBuilder();
                String customerID = "";
                String website = companyBean.getUserCompany(userData).getCoWebsite();
                if (customer != null) {
                    customerID = customer.getCustomerId();
                    message.append("Dear " + customer.getCustomerName() + "\n");
                    message.append("\n");
                } else {
                    customerID = webUser.getUserId();
                }
                message.append("Your web credentials have been found as requested.\n");
                message.append("\n");
                message.append("You can log on to " + website + " with the following credentials below: \n");
                message.append("\n");
                message.append("<b>Username :</b> " + webUser.getUserId() + "\n");
                message.append("<b>Password : </b>" + webUser.getPassword() + "\n");
                message.append("\n");
                message.append("Regards\n");
                message.append(isBlank(companyBean.getUserCompany(userData).getCoTradingAs()) ? companyBean.getUserCompany(userData).getCompanyName() : companyBean.getUserCompany(userData).getCoTradingAs());

                try {
                    EMCEmail email = new EMCEmail();
                    email.addRecipient(customerID, customer.getEmail());
                    email.setSubject(isBlank(companyBean.getUserCompany(userData).getCoTradingAs()) ? companyBean.getUserCompany(userData).getCompanyName() + " Password Request" : companyBean.getUserCompany(userData).getCoTradingAs() + " Password Request");
                    email.setMessage(message.toString());
                    mailManager.sendEmail(email, userData);
                    logMessage(Level.INFO, "An email has been sent to " + customer.getEmail() + " with the login credentials.", userData);
                } catch (Exception ex) {
                    throw new EMCEntityBeanException(ex);
                }

                if (!webUser.isActive()) {
                    webUser.setUserId(webUser.getUserId());
                    webUser.setPassword(webUser.getPassword());
                    webUser.setActive(true);
                    webPortalUsersBean.update(webUser, userData);
                }
                return true;
            }
        }

        if (isBlank(helper.getMailToEmail())) {
            throw new EMCEntityBeanException("The email address is not captured. Please enter the email.");
        }

        return true;
    }
}

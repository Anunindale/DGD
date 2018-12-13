/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.creditheld;

import emc.bus.debtors.customerinvoice.DebtorsCustomerInvoiceLinesLocal;
import emc.bus.debtors.customerinvoice.DebtorsCustomerInvoiceMasterLocal;
import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.debtors.creditheld.DebtorsCreditHeldMaster;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.debtors.creditheld.DebtorsCreditHeldReason;
import emc.enums.debtors.creditheld.DebtorsCreditHeldRefType;
import emc.enums.debtors.creditheld.DebtorsCreditHeldStatus;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.enumQueryTypes;
import emc.enums.sop.salesorders.SalesOrderStatus;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.debtors.DebtorsCreditHeldLinesIF;
import emc.messages.ServerDebtorsMessageEnum;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @description : Entity bean for DebtorsCreditHeldMaster.
 *
 * @date        : 29 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsCreditHeldMasterBean extends EMCEntityBean implements DebtorsCreditHeldMasterLocal {

    @EJB
    private DebtorsCustomerInvoiceLinesLocal invoiceLinesBean;
    @EJB
    private DebtorsCustomerInvoiceMasterLocal invoiceMasterBean;
    @EJB
    private DebtorsParametersLocal parametersBean;

    /** Creates a new instance of DebtorsCreditHeldMasterBean */
    public DebtorsCreditHeldMasterBean() {
    }

    /**
     * Creates a Credit Held master record for the specified reference, and sets a credit held status on its lines.
     * @param reference Reference of Sales Order/Invoice on which credit should be held.
     * @param reason  Reason why credit is being held.  The credit held master will always have the reason of the last held line.
     * @param refType Reference type.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean holdCredit(String reference, DebtorsCreditHeldReason reason, DebtorsCreditHeldRefType refType, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCreditHeldMaster master = ensureCreditHeldMasterExists(reference, refType, userData);

        master.setCreditHeldReason(String.valueOf(reason));

        update(master, userData);

        if (DebtorsCreditHeldRefType.INVOICE.equals(refType)) {
            DebtorsCustomerInvoiceMaster invoiceMaster = invoiceMasterBean.getInvoiceMaster(reference, userData);
            invoiceMaster.setStatus(DebtorsInvoiceStatus.HELD.toString());

            invoiceMasterBean.update(invoiceMaster, userData);         

            List<DebtorsCustomerInvoiceLines> lines = invoiceLinesBean.getInvoiceLines(reference, userData);

            for (DebtorsCustomerInvoiceLines line : lines) {
                line.setCreditHeld(true);
                line.setCreditHeldStatus(DebtorsCreditHeldStatus.HELD.toString());

                invoiceLinesBean.update(line, userData);
            }
        }

        return true;
    }

    /**
     * Creates a Credit Held master record for the specified reference, and sets a credit held status on its lines.
     * @param line Line on which credit should be held.
     * @param reason Reason for which credit is being held.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean holdCredit(DebtorsCreditHeldLinesIF line, DebtorsCreditHeldReason reason, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCreditHeldRefType refType = null;
        String reference = null;

        line.setCreditHeld(true);
        line.setCreditHeldStatus(DebtorsCreditHeldStatus.HELD.toString());

        if (line instanceof DebtorsCustomerInvoiceLines) {
            refType = DebtorsCreditHeldRefType.INVOICE;
            reference = ((DebtorsCustomerInvoiceLines) line).getInvCNNumber();
        } else {
            if (line instanceof SOPSalesOrderLines) {
                refType = DebtorsCreditHeldRefType.SALES_ORDER;
                reference = ((SOPSalesOrderLines) line).getSalesOrderNo();
            }
        }

        //Call super.update to prevent this logic from being called recursively.
        super.update(line, userData);

        DebtorsCreditHeldMaster master = ensureCreditHeldMasterExists(reference, refType, userData);
        master.setCreditHeldReason(reason.toString());

        update(master, userData);

        return true;
    }

    /**
     * Returns the specified Credit Held master, or null, if none is found.
     * @param reference Reference to fetch.
     * @param refType Reference type.
     * @param userData User data.
     * @return A DebtorsCreditHeldMaster instance matching the specified criteria, or null, if nothing is found.
     */
    @Override
    public DebtorsCreditHeldMaster getCreditHeldMaster(String reference, DebtorsCreditHeldRefType refType, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditHeldMaster.class);
        query.addAnd("reference", reference);
        query.addAnd("referenceType", refType.toString());
        //Only include master records currently in the HELD status.
        query.addAnd("creditHeldStatus", DebtorsCreditHeldStatus.HELD.toString());

        return (DebtorsCreditHeldMaster) util.executeSingleResultQuery(query, userData);
    }

    /** Ensures that a Credit Held master record exists for the specified Sales Order/Invoice.
     *  If a record does not exists, this method will create and save a record.
     */
    private DebtorsCreditHeldMaster ensureCreditHeldMasterExists(String reference, DebtorsCreditHeldRefType refType, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCreditHeldMaster master = getCreditHeldMaster(reference, refType, userData);
        if (master == null) {
            master = new DebtorsCreditHeldMaster();
            master.setReference(reference);
            master.setReferenceType(refType.toString());
            master.setHeldDate(Functions.nowDate());
            String customerId = null;

            if (DebtorsCreditHeldRefType.INVOICE.equals(refType)) {
                DebtorsCustomerInvoiceMaster invoiceMaster = invoiceMasterBean.getInvoiceMaster(reference, userData);

                if (invoiceMaster == null) {
                    throw new EMCEntityBeanException(getMessage(ServerDebtorsMessageEnum.INV_NOT_FOUND, userData, reference));
                }
            }

            master.setCustomerId(customerId);

            this.insert(master, userData);
        }

        return master;
    }

    /**
     * Approves all Credit Held lines related to the specified master.
     * @param reference Reference number.
     * @param refType Reference type.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws emc.framework.EMCEntityBeanException
     */
    public boolean approveCreditHeldMaster(String reference, String refType, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCreditHeldRefType referenceType = DebtorsCreditHeldRefType.fromString(refType);

        DebtorsCreditHeldMaster master = getCreditHeldMaster(reference, referenceType, userData);

        List<? extends DebtorsCreditHeldLinesIF> lines = null;

        if (DebtorsCreditHeldRefType.INVOICE.equals(referenceType)) {
            lines = (List<? extends DebtorsCreditHeldLinesIF>) invoiceLinesBean.getInvoiceLines(reference, userData);

            DebtorsCustomerInvoiceMaster invoiceMaster = invoiceMasterBean.getInvoiceMaster(reference, userData);

            if (invoiceMaster != null) {
                invoiceMaster.setStatus(DebtorsInvoiceStatus.APPROVED.toString());

                invoiceMasterBean.update(invoiceMaster, userData);

              
            }
        }

        for (DebtorsCreditHeldLinesIF line : lines) {
            approveCreditHeldLine(line, userData);
        }

        master.setCreditHeldStatus(DebtorsCreditHeldStatus.APPROVED.toString());
        master.setApprovedBy(userData.getUserName());
        master.setApprovedDate(Functions.nowDate());

        this.update(master, userData);

        return true;
    }

    /**
     * Approves the specified Credit Held line.
     * @param line Line to approve.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws emc.framework.EMCEntityBeanException
     */
    public boolean approveCreditHeldLine(DebtorsCreditHeldLinesIF line, EMCUserData userData) throws EMCEntityBeanException {
        line.setCreditHeldStatus(DebtorsCreditHeldStatus.APPROVED.toString());
        line.setCreditHeld(false);

        if (line instanceof DebtorsCustomerInvoiceLines) {
            invoiceLinesBean.update(line, userData);
        }

        return true;
    }

    /**
     * Returns the total credit held for the specified customer.
     * @param customerId Customer id.
     * @param userData User data.
     * @return The total credit held for the specified customer.
     */
    public BigDecimal getTotalCreditHeldForCustomer(String customerId, EMCUserData userData) {
        DebtorsParameters parameters = parametersBean.getDebtorsParameters(userData);

        BigDecimal creditHeld = BigDecimal.ZERO;

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderLines.class);
        query.addFieldAggregateFunction("lineTotal", "SUM");
        query.addFieldAggregateFunction("vatAmount", "SUM");
        query.addTableAnd(SOPSalesOrderMaster.class.getName(), "salesOrderNo", SOPSalesOrderLines.class.getName(), "salesOrderNo");
        query.addAnd("invoiceToCustomerNo", customerId, SOPSalesOrderMaster.class.getName());
        query.addAnd("creditHeld", true);

        Object[] salesOrderHeldTotals = (Object[]) util.executeSingleResultQuery(query, userData);

        if (salesOrderHeldTotals == null) {
            return BigDecimal.ZERO;
        } else {
            creditHeld = creditHeld.add(salesOrderHeldTotals[0] == null ? BigDecimal.ZERO: (BigDecimal)salesOrderHeldTotals[0]);

            if (parameters.isCreditCheckIncludeVAT()) {
                creditHeld = creditHeld.add(salesOrderHeldTotals[1] == null ? BigDecimal.ZERO: (BigDecimal)salesOrderHeldTotals[1]);
            }
        }
        
        query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
        query.addFieldAggregateFunction("totalCost", "SUM");
        query.addFieldAggregateFunction("vatAmount", "SUM");
        query.addTableAnd(DebtorsCustomerInvoiceMaster.class.getName(), "invCNNumber", DebtorsCustomerInvoiceLines.class.getName(), "invCNNumber");
        query.addAnd("invoiceToCustNo", customerId, DebtorsCustomerInvoiceMaster.class.getName());
        query.addAnd("creditHeld", true);

        Object[] invoiceHeldTotals = (Object[]) util.executeSingleResultQuery(query, userData);

        if (invoiceHeldTotals == null) {
            return BigDecimal.ZERO;
        } else {
            creditHeld = creditHeld.add(invoiceHeldTotals[0] == null ? BigDecimal.ZERO: (BigDecimal)invoiceHeldTotals[0]);

            if (parameters.isCreditCheckIncludeVAT()) {
                creditHeld = creditHeld.add(invoiceHeldTotals[1] == null ? BigDecimal.ZERO: (BigDecimal)invoiceHeldTotals[1]);
            }
        }

        return creditHeld;
    }
}

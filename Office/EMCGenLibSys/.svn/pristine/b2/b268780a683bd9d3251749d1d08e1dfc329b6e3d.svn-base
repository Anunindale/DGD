/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.companies.VatNumber;
import emc.datatypes.base.journals.superclass.ApprovedDate;
import emc.datatypes.base.journals.superclass.PostedBy;
import emc.datatypes.base.journals.superclass.PostedDate;
import emc.datatypes.base.postalcodes.PostalCode;
import emc.datatypes.creditors.settlementdicountgroups.foreignkeys.SettlementDiscountTermIdFKNotMandatory;
import emc.datatypes.creditors.termsofpayment.foreignkeys.TermsOfPaymentFKNotMandatory;
import emc.datatypes.debtors.creditnotes.ApprovalGroup;
import emc.datatypes.debtors.creditnotes.ReasonCode;
import emc.datatypes.debtors.customerinvoice.ApprovedBy;
import emc.datatypes.debtors.customerinvoice.ApprovedTime;
import emc.datatypes.debtors.customerinvoice.Comments;
import emc.datatypes.debtors.customerinvoice.CommissionApplicable;
import emc.datatypes.debtors.customerinvoice.CustomerContact;
import emc.datatypes.debtors.customerinvoice.CustomerTradingAs;
import emc.datatypes.debtors.customerinvoice.DeliveryAddress1;
import emc.datatypes.debtors.customerinvoice.DeliveryAddress2;
import emc.datatypes.debtors.customerinvoice.DeliveryAddress3;
import emc.datatypes.debtors.customerinvoice.DeliveryAddress4;
import emc.datatypes.debtors.customerinvoice.DeliveryAddress5;
import emc.datatypes.debtors.customerinvoice.DeliveryChargeApplicable;
import emc.datatypes.debtors.customerinvoice.InvoiceAddress1;
import emc.datatypes.debtors.customerinvoice.InvoiceAddress2;
import emc.datatypes.debtors.customerinvoice.InvoiceAddress3;
import emc.datatypes.debtors.customerinvoice.InvoiceAddress4;
import emc.datatypes.debtors.customerinvoice.InvoiceAddress5;
import emc.datatypes.debtors.customerinvoice.InvoiceDate;
import emc.datatypes.debtors.invoicemastersuper.InvCNNumber;
import emc.datatypes.debtors.customerinvoice.InvoiceStock;
import emc.datatypes.debtors.customerinvoice.InvoiceToCustNo;
import emc.datatypes.debtors.customerinvoice.LastPrintedBy;
import emc.datatypes.debtors.customerinvoice.LastPrintedDate;
import emc.datatypes.debtors.customerinvoice.LastPrintedTime;
import emc.datatypes.debtors.customerinvoice.PaymentDueDate;
import emc.datatypes.debtors.customerinvoice.PostedTime;
import emc.datatypes.debtors.customerinvoice.PrintedBy;
import emc.datatypes.debtors.customerinvoice.PrintedDate;
import emc.datatypes.debtors.customerinvoice.PrintedTime;
import emc.datatypes.debtors.customerinvoice.SalesRep;
import emc.datatypes.debtors.customerinvoice.SettlementDiscDate;
import emc.datatypes.debtors.customerinvoice.Status;
import emc.datatypes.debtors.customerinvoice.WaybillNumber;
import emc.datatypes.debtors.invoicemastersuper.InvCNType;
import emc.datatypes.debtors.salesarea.foreignkey.SalesAreaFK;
import emc.datatypes.debtors.salesgroup.foreignkey.SalesGroupFK;
import emc.datatypes.debtors.salesregion.foreignkey.SalesRegionFK;
import emc.datatypes.gl.vatcodes.foreignkeys.VATCodeFK;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFK;
import emc.datatypes.pop.deliverymodes.foreignkeys.DeliveryModeIdFK;
import emc.datatypes.pop.deliveryterms.foreignkeys.DeliveryTermsIdFK;
import emc.datatypes.pop.discountgroups.foreignkeys.DiscountGroupFKNotMandatory;
import emc.datatypes.pop.extrachargegroups.foreignkeys.ExtraChargeGroupFKNotMandatory;
import emc.datatypes.pop.pricegroups.foreignkeys.PriceGroupFK;
import emc.datatypes.sop.customers.foreignkeys.CustomerIdFK;
import emc.entity.debtors.superclasses.DebtorsInvoiceMasterSuper;
import emc.enums.debtors.DebtorsInvoiceCreditNoteType;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCQuery;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
 @Entity
@Table(name = "DebtorsCustomerInvoiceMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"invCNNumber", "companyId"})})
public class DebtorsCustomerInvoiceMaster extends DebtorsInvoiceMasterSuper {

    /** Creates a new instance of DebtorsCustomerInvoiceMaster. */
    public DebtorsCustomerInvoiceMaster() {
        this.setInvCNType(DebtorsInvoiceCreditNoteType.MANUAL_INVOICE.toString());
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        //DML queries not allowed on superclasses.  If this is on the suoperclass, FK relation updates break.
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("invCNNumber", new InvCNNumber());
        toBuild.put("invCNType", new InvCNType());
        toBuild.put("invoiceStock", new InvoiceStock());
        toBuild.put("status", new Status());
        toBuild.put("customerTradingAs", new CustomerTradingAs());
        toBuild.put("customerNo", new CustomerIdFK());
        toBuild.put("invoiceToCustNo", new InvoiceToCustNo());
        toBuild.put("customerContact", new CustomerContact());
        toBuild.put("invoiceDate", new InvoiceDate());

        toBuild.put("deliveryAddress1", new DeliveryAddress1());
        toBuild.put("deliveryAddress2", new DeliveryAddress2());
        toBuild.put("deliveryAddress3", new DeliveryAddress3());
        toBuild.put("deliveryAddress4", new DeliveryAddress4());
        toBuild.put("deliveryAddress5", new DeliveryAddress5());
        toBuild.put("deliveryAddressPostalCode", new PostalCode());

        toBuild.put("invoiceAddress1", new InvoiceAddress1());
        toBuild.put("invoiceAddress2", new InvoiceAddress2());
        toBuild.put("invoiceAddress3", new InvoiceAddress3());
        toBuild.put("invoiceAddress4", new InvoiceAddress4());
        toBuild.put("invoiceAddress5", new InvoiceAddress5());
        toBuild.put("invoiceAddressPostalCode", new PostalCode());

        toBuild.put("vatNo", new VatNumber());
        toBuild.put("vatCode", new VATCodeFK());

        toBuild.put("salesGroup", new SalesGroupFK());
        toBuild.put("salesRegion", new SalesRegionFK());
        toBuild.put("salesArea", new SalesAreaFK());
        toBuild.put("salesRep", new SalesRep());
        toBuild.put("deliveryTerms", new DeliveryTermsIdFK());
        toBuild.put("pricingGroup", new PriceGroupFK());
        toBuild.put("extraChargeGroup", new ExtraChargeGroupFKNotMandatory());
        toBuild.put("discountGroup", new DiscountGroupFKNotMandatory());
        toBuild.put("termsCode", new TermsOfPaymentFKNotMandatory());
        toBuild.put("settlementDiscCode", new SettlementDiscountTermIdFKNotMandatory());
        toBuild.put("deliveryMethod", new DeliveryModeIdFK());
        toBuild.put("orderWarehouse", new WarehouseFK());

        toBuild.put("paymentDueDate", new PaymentDueDate());
        toBuild.put("settlementDiscDate", new SettlementDiscDate());

        toBuild.put("approvedDate", new ApprovedDate());
        toBuild.put("approvedTime", new ApprovedTime());
        toBuild.put("approvedBy", new ApprovedBy());

        toBuild.put("postedDate", new PostedDate());
        toBuild.put("postedTime", new PostedTime());
        toBuild.put("postedBy", new PostedBy());

        toBuild.put("printedDate", new PrintedDate());
        toBuild.put("printedTime", new PrintedTime());
        toBuild.put("printedBy", new PrintedBy());

        toBuild.put("lastPrintedDate", new LastPrintedDate());
        toBuild.put("lastPrintedTime", new LastPrintedTime());
        toBuild.put("lastPrintedBy", new LastPrintedBy());

        toBuild.put("reasonCode", new ReasonCode());
        toBuild.put("approvalGroup", new ApprovalGroup());

        toBuild.put("comments", new Comments());
        toBuild.put("waybillNumber", new WaybillNumber());
        toBuild.put("deliveryChargeApplicable", new DeliveryChargeApplicable());

        toBuild.put("commissionApplicable", new CommissionApplicable());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("invCNNumber");
        toBuild.add("invCNType");
        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();

        query.addAnd("status", DebtorsInvoiceStatus.POSTED.toString(), EMCQueryConditions.NOT);
        query.addAnd("status", DebtorsInvoiceStatus.CANCELED.toString(), EMCQueryConditions.NOT);
        query.addAnd("status", DebtorsInvoiceStatus.DISTRIBUTION.toString(), EMCQueryConditions.NOT);

        return query;
    }
}

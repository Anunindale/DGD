package emc.entity.sop;

import emc.entity.sop.superclass.SOPSalesOrderMasterSuper;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import emc.datatypes.EMCDataType;
import emc.datatypes.base.companies.VatNumber;
import emc.datatypes.creditors.settlementdicountgroups.foreignkeys.SettlementDiscountTermIdFKNotMandatory;
import emc.datatypes.creditors.termsofpayment.foreignkeys.TermsOfPaymentFKNotMandatory;
import emc.datatypes.debtors.customerinvoice.InvoiceToCustNo;
import emc.datatypes.debtors.salesarea.foreignkey.SalesAreaFK;
import emc.datatypes.debtors.salesgroup.foreignkey.SalesGroupFK;
import emc.datatypes.debtors.salesregion.foreignkey.SalesRegionFK;
import emc.datatypes.gl.vatcodes.foreignkeys.VATCodeFK;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFK;
import emc.datatypes.pop.deliveryterms.foreignkeys.DeliveryTermsIdFK;
import emc.datatypes.pop.discountgroups.foreignkeys.DiscountGroupFKNotMandatory;
import emc.datatypes.pop.extrachargegroups.foreignkeys.ExtraChargeGroupFKNotMandatory;
import emc.datatypes.pop.pricegroups.foreignkeys.PriceGroupFKNotMandatory;
import emc.datatypes.sop.customers.foreignkeys.CustomerIdFK;
import emc.datatypes.sop.salesordermaster.CustomerOrderNo;
import emc.datatypes.sop.salesordermaster.DeliveryAddress1;
import emc.datatypes.sop.salesordermaster.DeliveryAddress2;
import emc.datatypes.sop.salesordermaster.DeliveryAddress3;
import emc.datatypes.sop.salesordermaster.DeliveryAddress4;
import emc.datatypes.sop.salesordermaster.DeliveryAddress5;
import emc.datatypes.sop.salesordermaster.DeliveryAddressCode;
import emc.datatypes.sop.salesordermaster.InvoiceAddress1;
import emc.datatypes.sop.salesordermaster.InvoiceAddress2;
import emc.datatypes.sop.salesordermaster.InvoiceAddress3;
import emc.datatypes.sop.salesordermaster.InvoiceAddress4;
import emc.datatypes.sop.salesordermaster.InvoiceAddress5;
import emc.datatypes.sop.salesordermaster.InvoiceAddressCode;
import emc.datatypes.sop.salesordermaster.InvoiceStatus;
import emc.datatypes.sop.salesordermaster.RefSalesOrderNo;
import emc.datatypes.sop.salesordermaster.Reference;
import emc.datatypes.sop.salesordermaster.SalesOrderNoNoNumSeq;
import emc.datatypes.sop.salesordermaster.SalesOrderStatus;
import emc.datatypes.systemwide.Comments;
import java.util.HashMap;

/**
 * 
 * @Author wikus
 */
@Entity
@Table(name = "SOPSalesOrderCancelMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"salesOrderNo", "companyId"})})
public class SOPSalesOrderCancelMaster extends SOPSalesOrderMasterSuper {

    private String cancelReason;

    public SOPSalesOrderCancelMaster() {
        this.setDataSource(false);
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        //Foreign key update breaks if this is on superclass.  DML queries not allowed on superclasses.
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("salesOrderNo", new SalesOrderNoNoNumSeq());
        toBuild.put("refSalesOrderNo", new RefSalesOrderNo());
        toBuild.put("invoiceStatus", new InvoiceStatus());
        toBuild.put("customerNo", new CustomerIdFK());
        toBuild.put("invoiceToCustomerNo", new InvoiceToCustNo());
        toBuild.put("salsesOrderStatus", new SalesOrderStatus());
        toBuild.put("deliveryAddress1", new DeliveryAddress1());
        toBuild.put("deliveryAddress2", new DeliveryAddress2());
        toBuild.put("deliveryAddress3", new DeliveryAddress3());
        toBuild.put("deliveryAddress4", new DeliveryAddress4());
        toBuild.put("deliveryAddress5", new DeliveryAddress5());
        toBuild.put("deliveryAddressCode", new DeliveryAddressCode());
        toBuild.put("invoiceAddress1", new InvoiceAddress1());
        toBuild.put("invoiceAddress2", new InvoiceAddress2());
        toBuild.put("invoiceAddress3", new InvoiceAddress3());
        toBuild.put("invoiceAddress4", new InvoiceAddress4());
        toBuild.put("invoiceAddress5", new InvoiceAddress5());
        toBuild.put("invoiceAddressCode", new InvoiceAddressCode());
        toBuild.put("vatNo", new VatNumber());
        toBuild.put("vatCode", new VATCodeFK());
        toBuild.put("salesGroup", new SalesGroupFK());
        toBuild.put("salesRegion", new SalesRegionFK());
        toBuild.put("salesArea", new SalesAreaFK());
        toBuild.put("customerOrderNo", new CustomerOrderNo());
        toBuild.put("reference", new Reference());
        toBuild.put("orderWarehouse", new WarehouseFK());
        toBuild.put("deliveryMethod", new DeliveryTermsIdFK());
        toBuild.put("deliveryTerms", new DeliveryTermsIdFK());
        toBuild.put("pricingGroup", new PriceGroupFKNotMandatory());
        toBuild.put("discountGroup", new DiscountGroupFKNotMandatory());
        toBuild.put("extraChargeGroup", new ExtraChargeGroupFKNotMandatory());
        toBuild.put("termsCode", new TermsOfPaymentFKNotMandatory());
        toBuild.put("settlementDiscountCode", new SettlementDiscountTermIdFKNotMandatory());
        toBuild.put("comments", new Comments());
        return toBuild;
    }
}

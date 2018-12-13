package emc.entity.sop;

import emc.entity.sop.superclass.SOPSalesOrderMasterSuper;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.sop.salesorders.SalesOrderStatus;
import emc.framework.EMCQuery;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import emc.datatypes.EMCDataType;
import emc.datatypes.base.companies.VatNumber;
import emc.datatypes.creditors.settlementdicountgroups.foreignkeys.SettlementDiscountTermIdFKNotMandatory;
import emc.datatypes.creditors.termsofpayment.foreignkeys.TermsOfPaymentFKNotMandatory;
import emc.datatypes.debtors.salesarea.foreignkey.SalesAreaFK;
import emc.datatypes.debtors.salesgroup.foreignkey.SalesGroupFK;
import emc.datatypes.debtors.salesregion.foreignkey.SalesRegionFK;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFK;
import emc.datatypes.pop.deliveryterms.foreignkeys.DeliveryTermsIdFK;
import emc.datatypes.pop.discountgroups.foreignkeys.DiscountGroupFKNotMandatory;
import emc.datatypes.pop.extrachargegroups.foreignkeys.ExtraChargeGroupFKNotMandatory;
import emc.datatypes.pop.pricegroups.foreignkeys.PriceGroupFKNotMandatory;
import emc.datatypes.sop.salesordermaster.OriginalRequiredDate;
import emc.datatypes.sop.salesordermaster.BlanketOrderRef;
import emc.datatypes.sop.salesordermaster.CustomerNo;
import emc.datatypes.sop.salesordermaster.CustomerOrderNo;
import emc.datatypes.sop.salesordermaster.DateChangeReason;
import emc.datatypes.sop.salesordermaster.DeliveryAddress1;
import emc.datatypes.sop.salesordermaster.DeliveryAddress2;
import emc.datatypes.sop.salesordermaster.DeliveryAddress3;
import emc.datatypes.sop.salesordermaster.DeliveryAddress4;
import emc.datatypes.sop.salesordermaster.DeliveryAddress5;
import emc.datatypes.sop.salesordermaster.DeliveryAddressCode;
import emc.datatypes.sop.salesordermaster.DeliveryCharge;
import emc.datatypes.sop.salesordermaster.DeliveryMethod;
import emc.datatypes.sop.salesordermaster.FullyReserved;
import emc.datatypes.sop.salesordermaster.InvoiceAddress1;
import emc.datatypes.sop.salesordermaster.InvoiceAddress2;
import emc.datatypes.sop.salesordermaster.InvoiceAddress3;
import emc.datatypes.sop.salesordermaster.InvoiceAddress4;
import emc.datatypes.sop.salesordermaster.InvoiceAddress5;
import emc.datatypes.sop.salesordermaster.InvoiceAddressCode;
import emc.datatypes.sop.salesordermaster.InvoiceStatus;
import emc.datatypes.sop.salesordermaster.InvoiceToCustomer;
import emc.datatypes.sop.salesordermaster.RefSalesOrderNo;
import emc.datatypes.sop.salesordermaster.Reference;
import emc.datatypes.sop.salesordermaster.RepId;
import emc.datatypes.sop.salesordermaster.RequiredDate;
import emc.datatypes.sop.salesordermaster.SalesOrderNo;
import emc.datatypes.sop.salesordermaster.SalesOrderType;
import emc.datatypes.sop.salesordermaster.VATCode;
import emc.datatypes.systemwide.Comments;
import java.util.HashMap;

/**
 * 
 * @Author wikus
 */
@Entity
@Table(name = "SOPSalesOrderMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"salesOrderNo", "companyId"})})
public class SOPSalesOrderMaster extends SOPSalesOrderMasterSuper {

    public SOPSalesOrderMaster() {
        this.setDataSource(false);
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addAnd("salsesOrderStatus", SalesOrderStatus.CANCELLED.toString(), EMCQueryConditions.NOT);
        query.addAnd("salsesOrderStatus", SalesOrderStatus.INVOICED.toString(), EMCQueryConditions.NOT);
        query.addOrderBy("salesOrderNo");
        return query;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        //Foreign key update breaks if this is on superclass.  DML queries not allowed on superclasses.
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("salesOrderNo", new SalesOrderNo());
        toBuild.put("refSalesOrderNo", new RefSalesOrderNo());
        toBuild.put("invoiceStatus", new InvoiceStatus());
        toBuild.put("customerNo", new CustomerNo());
        toBuild.put("salesOrderType", new SalesOrderType());
        toBuild.put("invoiceToCustomerNo", new InvoiceToCustomer());
        toBuild.put("salsesOrderStatus", new emc.datatypes.sop.salesordermaster.SalesOrderStatus());
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
        toBuild.put("vatCode", new VATCode());
        toBuild.put("salesGroup", new SalesGroupFK());
        toBuild.put("salesRegion", new SalesRegionFK());
        toBuild.put("salesArea", new SalesAreaFK());
        toBuild.put("customerOrderNo", new CustomerOrderNo());
        toBuild.put("reference", new Reference());
        toBuild.put("orderWarehouse", new WarehouseFK());
        toBuild.put("deliveryMethod", new DeliveryMethod());
        toBuild.put("deliveryTerms", new DeliveryTermsIdFK());
        toBuild.put("pricingGroup", new PriceGroupFKNotMandatory());
        toBuild.put("discountGroup", new DiscountGroupFKNotMandatory());
        toBuild.put("extraChargeGroup", new ExtraChargeGroupFKNotMandatory());
        toBuild.put("termsCode", new TermsOfPaymentFKNotMandatory());
        toBuild.put("settlementDiscountCode", new SettlementDiscountTermIdFKNotMandatory());
        toBuild.put("requiredDate", new RequiredDate());
        toBuild.put("comments", new Comments());
        toBuild.put("salesRep", new RepId());
        toBuild.put("fullyReserved", new FullyReserved());
        toBuild.put("blanketOrderRef", new BlanketOrderRef());
        toBuild.put("deliveryChargs", new DeliveryCharge());
        toBuild.put("originalRequiredDate", new OriginalRequiredDate());
        toBuild.put("dateChangeReason", new DateChangeReason());
        return toBuild;
    }

    @Override
    public List<String> buildFieldListToClearOnCopy() {
        List<String> toClear = super.buildFieldListToClearOnCopy();

        toClear.add("fullyReserved");

        return toClear;
    }
}

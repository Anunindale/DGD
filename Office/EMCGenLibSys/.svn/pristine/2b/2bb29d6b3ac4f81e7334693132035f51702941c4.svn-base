package emc.entity.inventory;

/**
 * 
 * @author wikus
 */
import emc.datatypes.inventory.dimension3.Category;
import emc.datatypes.inventory.inventoryreference.AltDescription;
import emc.datatypes.inventory.inventoryreference.Dimension1;
import emc.datatypes.inventory.inventoryreference.Dimension2;
import emc.datatypes.inventory.inventoryreference.Dimension3;
import emc.datatypes.inventory.inventoryreference.Reference;
import emc.datatypes.inventory.inventoryreference.ReferenceType;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFKCascade;
import emc.datatypes.pop.supplier.foreignkeys.SupplierIdFKNotMandatory;
import emc.datatypes.sop.customers.foreignkeys.CustomerIdFKNotMandatory;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "InventoryReference", uniqueConstraints = {@UniqueConstraint(columnNames = {"uniqueDescriptor", "companyId"})})
public class InventoryReference extends EMCEntityClass {

    private String itemId;
    private String reference;
    private String refType;
    private String customerNo;
    private String supplierNo;
    private String alternativeDescription;
    private String uniqueDescriptor;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String dimension3Shade;
    private double purchasePrice;
    @Temporal(TemporalType.DATE)
    private Date purchasePriceDate;

    public String getAlternativeDescription() {
        return alternativeDescription;
    }

    public void setAlternativeDescription(String alternativeDescription) {
        this.alternativeDescription = alternativeDescription;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    public String getUniqueDescriptor() {
        return uniqueDescriptor;
    }

    public void setUniqueDescriptor(String uniqueDescriptor) {
        this.uniqueDescriptor = uniqueDescriptor;
    }

    public String getDimension1() {
        return dimension1;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public String getDimension2() {
        return dimension2;
    }

    public void setDimension2(String dimension2) {
        this.dimension2 = dimension2;
    }

    public String getDimension3() {
        return dimension3;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Date getPurchasePriceDate() {
        return purchasePriceDate;
    }

    public void setPurchasePriceDate(Date purchasePriceDate) {
        this.purchasePriceDate = purchasePriceDate;
    }

    public String getDimension3Shade() {
        return dimension3Shade;
    }

    public void setDimension3Shade(String dimension3Shade) {
        this.dimension3Shade = dimension3Shade;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("itemId", new ItemIdFKCascade());
        toBuild.put("reference", new Reference());
        toBuild.put("refType", new ReferenceType());
        toBuild.put("customerNo", new CustomerIdFKNotMandatory());
        toBuild.put("supplierNo", new SupplierIdFKNotMandatory());
        toBuild.put("alternativeDescription", new AltDescription());
        toBuild.put("dimension1", new Dimension1());
        toBuild.put("dimension2", new Dimension2());
        toBuild.put("dimension3", new Dimension3());
        toBuild.put("dimension3Shade", new Category());

        return toBuild;
    }
}

package emc.entity.pop.datasource.pricearrangements;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.brandgroup.BrandGroupDS;
import emc.datatypes.pop.datasource.pricearrangements.ItemDescription;
import emc.datatypes.pop.datasource.pricearrangements.ItemReference;
import emc.datatypes.pop.datasource.pricearrangements.SupplierDisplayField;
import emc.datatypes.pop.datasource.pricearrangements.SupplierName;
import emc.entity.pop.pricearrangements.POPPriceArrangements;
import emc.inventory.ItemReferenceInterface;
import java.lang.Override;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "POPPriceArrangementDS", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"companyId"})})
public class POPPriceArrangementDS extends POPPriceArrangements implements ItemReferenceInterface {

    private String itemReference;
    private String itemDescription;
    private String itemBrand;
    private String supplierName;
    private String supplierDisplayField;

    /**
     * Creates a new instance of POPPriceArrangementDS.
     */
    public POPPriceArrangementDS() {
        this.setDataSource(true);
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("itemReference", new ItemReference());
        toBuild.put("itemDescription", new ItemDescription());
        toBuild.put("itemBrand", new BrandGroupDS());
        toBuild.put("supplierName", new SupplierName());
        toBuild.put("supplierDisplayField", new SupplierDisplayField());
        return toBuild;
    }

    public String getItemReference() {
        return this.itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getItemDescription() {
        return this.itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getSupplierName() {
        return this.supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierDisplayField() {
        return this.supplierDisplayField;
    }

    public void setSupplierDisplayField(String supplierDisplayField) {
        this.supplierDisplayField = supplierDisplayField;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }
}
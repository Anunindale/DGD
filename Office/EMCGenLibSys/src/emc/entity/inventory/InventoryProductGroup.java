/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.productiongroup.ProductGroupId;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import emc.helpers.debtors.royalty.RoyaltyInterface;
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
@Table(name = "InventoryProductGroup", uniqueConstraints = {@UniqueConstraint(columnNames = {"productGroupId", "companyId"})})
public class InventoryProductGroup extends EMCEntityClass implements RoyaltyInterface {

    private String productGroupId;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductGroupId() {
        return productGroupId;
    }

    public void setProductGroupId(String productGroupId) {
        this.productGroupId = productGroupId;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> ret = super.buildDefaultLookupFieldList();
        ret.add("productGroupId");
        ret.add("description");
        return ret;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("productGroupId", new ProductGroupId());
        toBuild.put("description", new Description());
        return toBuild;
    }

    public String getFieldToDisplay() {
        return this.description;
    }
}

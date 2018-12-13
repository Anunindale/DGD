/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory;

import emc.datatypes.inventory.brandgroup.BrandGroupId;
import emc.datatypes.inventory.brandgroup.BrandGroupName;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import emc.helpers.debtors.royalty.RoyaltyInterface;
import java.io.Serializable;
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
@Table(name = "InventoryBrandGroup", uniqueConstraints = {@UniqueConstraint(columnNames = {"brandGroupId", "companyId"})})
public class InventoryBrandGroup extends EMCEntityClass implements Serializable, RoyaltyInterface {

    private String brandGroupId;
    private String brandGroupName;
    private String brandGroupDescription;

    /** Creates a new instance of InventoryBrandGroup */
    public InventoryBrandGroup() {
    }

    public String getBrandGroupId() {
        return brandGroupId;
    }

    public void setBrandGroupId(String brandGroupId) {
        this.brandGroupId = brandGroupId;
    }

    public String getBrandGroupName() {
        return brandGroupName;
    }

    public void setBrandGroupName(String brandGroupName) {
        this.brandGroupName = brandGroupName;
    }

    public String getBrandGroupDescription() {
        return brandGroupDescription;
    }

    public void setBrandGroupDescription(String brandGroupDescription) {
        this.brandGroupDescription = brandGroupDescription;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap map = super.buildFieldList();
        map.put("brandGroupId", new BrandGroupId());
        map.put("brandGroupDescription", new Description());
        map.put("brandGroupName", new BrandGroupName());
        return map;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("brandGroupId");
        toBuild.add("brandGroupName");
        toBuild.add("brandGroupDescription");
        return toBuild;
    }

    public String getFieldToDisplay() {
        return this.brandGroupName;
    }
}

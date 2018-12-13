/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory;

import emc.datatypes.inventory.InventoryScrap;
import emc.datatypes.inventory.itemclassification.hierachies.foreignkeys.HierarchyIdFK;
import emc.datatypes.inventory.itemgroup.AccessGroupIdFK;
import emc.datatypes.inventory.itemgroup.InventoryCostingScrap;
import emc.datatypes.inventory.itemgroup.ItemGroupId;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "InventoryItemGroup", uniqueConstraints = {@UniqueConstraint(columnNames = {"itemGroup", "companyId"})})
public class InventoryItemGroup extends EMCEntityClass {

    private String itemGroup;
    private String description;
    private String accessGroupId;
    private String hierarchy;
    private Double scrap;
    private Double costingScrap;
    private Boolean quarantineReq;
    private String mpsFlag;
    //Indicates whether agents may receive commission on items in this group.
    private boolean commissionApplicable = true;

    public InventoryItemGroup() {
    }

    public boolean getQuarantineReq() {
        return quarantineReq == null ? false : quarantineReq;
    }

    public void setQuarantineReq(Boolean quarantineReq) {
        this.quarantineReq = quarantineReq;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccessGroupId() {
        return accessGroupId;
    }

    public void setAccessGroupId(String accessGroupId) {
        this.accessGroupId = accessGroupId;
    }

    public Double getScrap() {
        return scrap == null ? 0.0d : this.scrap;
    }

    public void setScrap(Double scrap) {
        this.scrap = scrap;
    }

    public Double getCostingScrap() {
        return costingScrap == null ? 0 : costingScrap;
    }

    public void setCostingScrap(Double costingScrap) {
        this.costingScrap = costingScrap;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap dtMap = super.buildFieldList();
        dtMap.put("description", new Description());
        dtMap.put("itemGroup", new ItemGroupId());
        dtMap.put("accessGroupId", new AccessGroupIdFK());
        dtMap.put("hierarchy", new HierarchyIdFK());
        dtMap.put("scrap", new InventoryScrap());
        dtMap.put("costingScrap", new InventoryCostingScrap());

        return dtMap;
    }

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }

    @Override
    public List<String> buildDefaultLookupFieldList() {
        List<String> fields = super.buildDefaultLookupFieldList();

        fields.add("itemGroup");
        fields.add("description");

        return fields;
    }

    public boolean isCommissionApplicable() {
        return commissionApplicable;
    }

    public void setCommissionApplicable(boolean commissionApplicable) {
        this.commissionApplicable = commissionApplicable;
    }

    public String getMpsFlag() {
        return mpsFlag;
    }

    public void setMpsFlag(String mpsFlag) {
        this.mpsFlag = mpsFlag;
    }
}

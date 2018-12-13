/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.costinggroups.CostingGroup;
import emc.datatypes.inventory.costinggroups.CostingGroupId;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
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
@Table(name = "InventoryCostingGroup", uniqueConstraints = {@UniqueConstraint(columnNames = {"costingGroupId", "companyId"})})
public class InventoryCostingGroup extends EMCEntityClass implements Serializable {

    private String costingGroupId;
    private String description;
    private String costType;
    private String costLevel;
    private String costingGroup;
    private String defaultDimensionCostCalc;

    /** Creates a new instance of InventoryCostingGroup */
    public InventoryCostingGroup() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCostingGroupId() {
        return costingGroupId;
    }

    public void setCostingGroupId(String costingGroupId) {
        this.costingGroupId = costingGroupId;
    }

    /**
     * @return the costType
     */
    public String getCostType() {
        return costType;
    }

    /**
     * @param costType the costType to set
     */
    public void setCostType(String costType) {
        this.costType = costType;
    }

    /**
     * @return the costLevel
     */
    public String getCostLevel() {
        return costLevel;
    }

    /**
     * @param costLevel the costLevel to set
     */
    public void setCostLevel(String costLevel) {
        this.costLevel = costLevel;
    }

    public String getCostingGroup() {
        return costingGroup;
    }

    public void setCostingGroup(String costingGroup) {
        this.costingGroup = costingGroup;
    }

    public String getDefaultDimensionCostCalc() {
        return defaultDimensionCostCalc;
    }

    public void setDefaultDimensionCostCalc(String defaultDimensionCostCalc) {
        this.defaultDimensionCostCalc = defaultDimensionCostCalc;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("costingGroupId", new CostingGroupId());
        toBuild.put("description", new Description());
        toBuild.put("costingGroup", new CostingGroup());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("costingGroupId");
        toBuild.add("description");
        return toBuild;
    }
}

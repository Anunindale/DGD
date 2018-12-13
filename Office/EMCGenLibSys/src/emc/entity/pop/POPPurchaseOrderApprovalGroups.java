/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.itemgroup.foreignkeys.ItemGroupIdFKNotMandatory;
import emc.datatypes.pop.purchaseorderapprovalgroups.ApprovalGroupId;
import emc.datatypes.pop.purchaseorderapprovalgroups.MaxValue;
import emc.datatypes.pop.purchaseorderapprovalgroups.PriceQtyChange;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import emc.datatypes.pop.purchaseorderapprovalgroups.foreignkeys.HigherLevelApprovalGroupIdFKNM;

/**
 *
 * @author wikus
 *
 */
@Entity
@Table(name = "POPPurchaseOrderApprovalGroups", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"purchaseOrderApprovalGroupId", "companyId"})})
public class POPPurchaseOrderApprovalGroups extends EMCEntityClass {

    private String purchaseOrderApprovalGroupId;
    private String description;
    private String itemGroup;
    private double maxValue;
    private boolean allowCancel;
    private boolean priceQtyChange;
    private String higherLevelApprovalGroups;

    /**
     * Creates a new instance of CreditorsPurchaseOrderApprovalGroups
     */
    public POPPurchaseOrderApprovalGroups() {
    }

    public String getPurchaseOrderApprovalGroupId() {
        return purchaseOrderApprovalGroupId;
    }

    public void setPurchaseOrderApprovalGroupId(String purchaseOrderApprovalGroupId) {
        this.purchaseOrderApprovalGroupId = purchaseOrderApprovalGroupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public boolean getAllowCancel() {
        return allowCancel;
    }

    public void setAllowCancel(boolean allowCancel) {
        this.allowCancel = allowCancel;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public boolean isPriceQtyChange() {
        return priceQtyChange;
    }

    public void setPriceQtyChange(boolean priceQtyChange) {
        this.priceQtyChange = priceQtyChange;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("purchaseOrderApprovalGroupId", new ApprovalGroupId());
        toBuild.put("description", new Description());
        toBuild.put("maxValue", new MaxValue());
        toBuild.put("itemGroup", new ItemGroupIdFKNotMandatory());
        toBuild.put("priceQtyChange", new PriceQtyChange());
        toBuild.put("higherLevelApprovalGroups", new HigherLevelApprovalGroupIdFKNM());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> ret = super.buildDefaultLookupFieldList();
        ret.add("purchaseOrderApprovalGroupId");
        ret.add("description");
        return ret;
    }

    public String getHigherLevelApprovalGroups() {
        return higherLevelApprovalGroups;
    }

    public void setHigherLevelApprovalGroups(String higherLevelApprovalGroups) {
        this.higherLevelApprovalGroups = higherLevelApprovalGroups;
    }
}

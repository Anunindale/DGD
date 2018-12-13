/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop.costchange;

import emc.datatypes.pop.costchange.ItemPrice;
import emc.datatypes.pop.costchange.POPrice;
import emc.datatypes.pop.supplier.foreignkeys.SupplierIdFKNotMandatory;
import emc.datatypes.systemwide.Description;
import emc.datatypes.systemwide.ItemPrimaryReference;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class CostChangeSummaryDS extends POPPurchaseOrderLines {

    private String itemReference;
    private String itemDescription;
    private double itemCost;
    private boolean approveCost;
    private boolean updateCost;
    private String suppId;

    public CostChangeSummaryDS() {
        this.setDataSource(true);
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public boolean isApproveCost() {
        return approveCost;
    }

    public void setApproveCost(boolean approveCost) {
        this.approveCost = approveCost;
    }

    public double getItemCost() {
        return itemCost;
    }

    public void setItemCost(double itemCost) {
        this.itemCost = itemCost;
    }

    public boolean isUpdateCost() {
        return updateCost;
    }

    public void setUpdateCost(boolean updateCost) {
        this.updateCost = updateCost;
    }

    public String getSuppId() {
        return suppId;
    }

    public void setSuppId(String suppId) {
        this.suppId = suppId;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("itemReference", new ItemPrimaryReference());
        toBuild.put("itemDescription", new Description());
        toBuild.put("itemCost", new ItemPrice());
        toBuild.put("approveCost", new POPrice());
        toBuild.put("suppId", new SupplierIdFKNotMandatory());
        return toBuild;
    }

    @Override
    public EMCQuery getQuery() {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class.getName());
        query.addAnd("costChange", true);
        return query;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.pop.posting;

import emc.datatypes.pop.purchaseordermaster.foreignkeys.PurchaseOrderIdFKNM;
import emc.datatypes.pop.purchasepostlines.PostLineQty;
import emc.datatypes.pop.purchasepostmasterforeignkeys.PostMasterIdFK;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemReferenceFK;
import emc.datatypes.pop.purchasepostlines.NumberLabels;
import emc.datatypes.pop.purchasepostlines.StandardLocation;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "POPPurchasePostLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"postMasterId", "purchaseId", "lineNumber", "companyId"})})
public class POPPurchasePostLines extends EMCEntityClass {

    private String postMasterId;
    private String purchaseId;
    private double lineNumber;
    private String itemId;
    private double quantity;
    private long dimId;
    private String transactionNumber;
    private String itemPrimaryReference;
    private int numberLabels;

    //This is specific to received goods.
    private String standardLocation;
            
    /** This field ensures that "old" goods received reports work correctly.
     *  It keeps track of the remaining items on a purchase order line at the time of posting. 
     *  This field is updated from the report bean when data is posted. (That way we don't need to mess with transactions)
     *  -1 means that the field hasn't been set.
     */
    private Double poLineRemainingItems = -1d;
            
    /** Creates a new instance of POPPurchasePostLines */
    public POPPurchasePostLines() {
        
    }

    public String getItemPrimaryReference() {
        return itemPrimaryReference;
    }

    public void setItemPrimaryReference(String itemPrimaryReference) {
        this.itemPrimaryReference = itemPrimaryReference;
    }

    
    public String getPostMasterId() {
        return postMasterId;
    }

    public void setPostMasterId(String postMasterId) {
        this.postMasterId = postMasterId;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public double getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(double lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("postMasterId", new PostMasterIdFK());
        toBuild.put("purchaseId", new PurchaseOrderIdFKNM());
        toBuild.put("itemId", new ItemIdFK());
        toBuild.put("itemPrimaryReference", new ItemReferenceFK());
        toBuild.put("quantity", new PostLineQty());
        toBuild.put("numberLabels", new NumberLabels());
        toBuild.put("standardLocation", new StandardLocation());
        
        return toBuild;
    }
    
    @Override
    public EMCQuery buildQuery() {
        EMCQuery theQuery = super.buildQuery();
        
        theQuery.addAnd("postMasterId", new PostMasterIdFK());
        
        return theQuery;
    }

    public long getDimId() {
        return dimId;
    }

    public void setDimId(long dimId) {
        this.dimId = dimId;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public double getPoLineRemainingItems() {
        return poLineRemainingItems == null ? -1 : poLineRemainingItems;
    }

    public void setPoLineRemainingItems(double poLineRemainingItems) {
        this.poLineRemainingItems = poLineRemainingItems;
    }

    public int getNumberLabels() {
        return numberLabels;
    }

    public void setNumberLabels(int numberLabels) {
        this.numberLabels = numberLabels;
    }

    public String getStandardLocation() {
        return standardLocation;
    }

    public void setStandardLocation(String standardLocation) {
        this.standardLocation = standardLocation;
    }

}

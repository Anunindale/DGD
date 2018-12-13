/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory;

import emc.datatypes.inventory.itemclassification.itembatch.ItemBatchId;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryItemBatch", uniqueConstraints = {@UniqueConstraint(columnNames = {"itemBatchId", "companyId"})})
public class InventoryItemBatch extends EMCEntityClass {
    
    private String itemBatchId;
    private String itemId;
    @Temporal(TemporalType.DATE)
    private Date expiryDate;
    @Temporal(TemporalType.DATE)
    private Date prodDate;
    private String description;
    
    /** Creates a new instance of InventoryItemBatch */
    public InventoryItemBatch() {
        
    }

    public String getItemBatchId() {
        return itemBatchId;
    }

    public void setItemBatchId(String itemBatchId) {
        this.itemBatchId = itemBatchId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("itemId", new ItemIdFK());
        toBuild.put("itemBatchId", new ItemBatchId());
        toBuild.put("description", new Description());
        
        return toBuild;
    }
}

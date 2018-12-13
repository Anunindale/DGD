/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
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
@Table(name = "InventoryItemSerial", uniqueConstraints = {@UniqueConstraint(columnNames = {"serialId", "dimensionId", "companyId"})})
public class InventoryItemSerial extends EMCEntityClass {

    private String serialId;
    private String itemId;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date prodDate;
    private String dimensionId;
    
    /** Creates a new instance of InventoryItemSerial */
    public InventoryItemSerial() {
        
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        
        toBuild.put("itemId", new ItemIdFK());
        
        return toBuild;
    }

}

package emc.entity.inventory;

import emc.datatypes.inventory.accessgroup.AccessGroupId;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * @Author wikus
 */

@Entity
@Table(name = "InventoryItemAccessGroup", uniqueConstraints = {@UniqueConstraint(columnNames = {"itemAccessGroupId", "companyId" })})
public class InventoryItemAccessGroup extends EMCEntityClass {

    private String itemAccessGroupId;
    private String description;
    
    /** Creates a new instance of InventoryItemAccessGroup */
    public InventoryItemAccessGroup() {
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemAccessGroupId() {
        return itemAccessGroupId;
    }

    public void setItemAccessGroupId(String itemAccessGroupId) {
        this.itemAccessGroupId = itemAccessGroupId;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("itemAccessGroupId", new AccessGroupId());
        toBuild.put("description", new Description());
        
        return toBuild;
    }
    
    
    
}


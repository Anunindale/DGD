package emc.entity.inventory;

import emc.datatypes.inventory.inventoryreferencetype.Type;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "InventoryReferenceType", uniqueConstraints = {@UniqueConstraint(columnNames= {"type", "companyId"})})
public class InventoryReferenceType extends EMCEntityClass {
    
    private String type;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    @Override
public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("type", new Type());
        toBuild.put("description", new Description());
        
        return toBuild;
    }
}
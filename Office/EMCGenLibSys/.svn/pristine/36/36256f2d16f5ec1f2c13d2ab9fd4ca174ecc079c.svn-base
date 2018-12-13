/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions;

import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FK;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFKCascade;
import emc.entity.inventory.dimensions.superclasses.InventoryItemDimensionSetup;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryItemDimension2Setup", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "itemId", "dimensionId"})})
public class InventoryItemDimension2Setup extends InventoryItemDimensionSetup {
    
    private Boolean active = false;
    
    /** Creates a new instance of InventoryItemDimension2Setup */
    public InventoryItemDimension2Setup() {
        this.setEmcLabel("Item Sizes Setup");
    }

    public Boolean getActive() {
       return active == null ? false : active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("dimensionId", new Dimension2FK());
        toBuild.put("itemId", new ItemIdFKCascade());
        
        return toBuild;
    }
}

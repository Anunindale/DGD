/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions;

import emc.datatypes.inventory.Active;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FK;
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
@Table(name = "InventoryItemDimension3Setup", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "itemId", "dimensionId"})})
public class InventoryItemDimension3Setup extends InventoryItemDimensionSetup {
    boolean active = false;
    /** Creates a new instance of InventoryItemDimension3Setup */
    public InventoryItemDimension3Setup() {
        this.setEmcLabel("Item Colours Setup");
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("dimensionId", new Dimension3FK());
        toBuild.put("active", new Active());
        toBuild.put("itemId", new ItemIdFKCascade());
        return toBuild;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.dimensions;

import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FK;
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
@Table(name = "InventoryItemDimension1Setup", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "itemId", "dimensionId"})})
public class InventoryItemDimension1Setup extends InventoryItemDimensionSetup {

    private boolean active;

    /** Creates a new instance InventoryItemDimension1Setup */
    public InventoryItemDimension1Setup() {
        this.setEmcLabel("Item Configurations Setup");
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("dimensionId", new Dimension1FK());
        toBuild.put("itemId", new ItemIdFKCascade());

        return toBuild;
    }
}

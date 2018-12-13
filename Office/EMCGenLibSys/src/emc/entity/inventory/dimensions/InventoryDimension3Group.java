/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions;

import emc.entity.inventory.dimensions.superclasses.InventoryDimensionGroup;
import emc.datatypes.inventory.dimension3groups.Dimension3GroupId;
import emc.datatypes.systemwide.Description;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryColourGroup", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "dimensionGroupId"})})
public class InventoryDimension3Group extends InventoryDimensionGroup {

    /** Creates a new instance of InventoryDimension3Group */
    public InventoryDimension3Group() {
        
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("description", new Description());
        toBuild.put("dimensionGroupId", new Dimension3GroupId());
        
        return toBuild;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions;

import emc.entity.inventory.dimensions.superclasses.InventoryDimensionGroup;
import emc.datatypes.inventory.dimension1groups.Dimension1GroupId;
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
@Table(name = "InventoryDimension1Group", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "dimensionGroupId"})})
public class InventoryDimension1Group extends InventoryDimensionGroup {

    /** Creates a new instance of InventoryDimension1Group */
    public InventoryDimension1Group() {
        
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("description", new Description());
        toBuild.put("dimensionGroupId", new Dimension1GroupId());
        
        return toBuild;
    }
}

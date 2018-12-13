/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions;

import emc.entity.inventory.dimensions.superclasses.InventoryDimensionGroup;
import emc.datatypes.inventory.dimension2groups.Dimension2GroupId;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryDimension2Group", uniqueConstraints = {@UniqueConstraint(columnNames = {"dimensionGroupId", "companyId"})})
public class InventoryDimension2Group extends InventoryDimensionGroup {

    /** Creates a new instance of InventoryDimension2Group */
    public InventoryDimension2Group() {
        
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("description", new Description());
        toBuild.put("dimensionGroupId", new Dimension2GroupId());
        
        return toBuild;
    }
}

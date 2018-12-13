/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions;

import emc.datatypes.inventory.dimension1.Dimension1Description;
import emc.datatypes.inventory.dimension1.Dimension1Small;
import emc.datatypes.inventory.dimension1.SortCode;
import emc.entity.inventory.dimensions.superclasses.InventoryDimension;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name= "InventoryDimension1", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "dimensionId"})})
public class InventoryDimension1 extends InventoryDimension {
    
    //Used to ensure that duplicate configurations may not be activated within EMC.
    private boolean active;

    /** Creates a new instance of InventoryDimension1 */
    public InventoryDimension1() {
        this.setEmcLabel("Configurations");
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("dimensionId", new Dimension1Small());
        toBuild.put("description", new Dimension1Description());
        toBuild.put("sortCode", new SortCode());
        
        return toBuild;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions.superclasses;

import emc.framework.EMCEntityClass;
import javax.persistence.Entity;

/**
 *
 * @author riaan
 */
@Entity
public class InventoryDimensionGroupSetup extends EMCEntityClass {

    private String dimensionId;
    private String dimensionGroupId;
    
    /** Creates a new instance of InventoryDimensionGroupSetup */
    public InventoryDimensionGroupSetup() {
        
    }

    public String getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getDimensionGroupId() {
        return dimensionGroupId;
    }

    public void setDimensionGroupId(String dimensionGroupId) {
        this.dimensionGroupId = dimensionGroupId;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions.superclasses;

import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;

/**
 *
 * @author riaan
 */
@Entity
public class InventoryDimensionGroup extends EMCEntityClass implements Serializable {

    private String dimensionGroupId;
    private String description;
    
    /** Creates a new instance of InventoryDimensionGroup */
    public InventoryDimensionGroup() {
        
    }

    public String getDimensionGroupId() {
        return dimensionGroupId;
    }

    public void setDimensionGroupId(String dimensionGroupId) {
        this.dimensionGroupId = dimensionGroupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List ret = super.buildDefaultLookupFieldList();
        
        ret.add("dimensionGroupId");
        ret.add("description");
        
        return ret;
    }
 
}


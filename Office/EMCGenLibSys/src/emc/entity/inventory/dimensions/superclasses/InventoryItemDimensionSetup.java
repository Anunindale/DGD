/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions.superclasses;

import emc.framework.EMCEntityClass;
import java.util.List;
import javax.persistence.Entity;


/**
 *
 * @author riaan
 */
@Entity
public class InventoryItemDimensionSetup extends EMCEntityClass {

    private String itemId;
    private String dimensionId;
            
    /** Creates a new instance of InventoryItemDimensionSetup */
    public InventoryItemDimensionSetup() {
        
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> ret = super.buildDefaultLookupFieldList();
        
        ret.add("dimensionId");
        ret.add("description");
                
        return ret;
    }
    
}

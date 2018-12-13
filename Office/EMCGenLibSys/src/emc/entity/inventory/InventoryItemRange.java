/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory;

import emc.datatypes.inventory.itemrange.ItemRange;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryItemRange", uniqueConstraints = {@UniqueConstraint(columnNames = {"itemRangeId", "companyId"})})
public class InventoryItemRange extends EMCEntityClass implements Serializable {

    private String itemRangeId;
    private String description;
    
    /** Creates a new instance of InventoryItemRange */
    public InventoryItemRange() {
        
    }

    public String getItemRangeId() {
        return itemRangeId;
    }

    public void setItemRangeId(String itemRangeId) {
        this.itemRangeId = itemRangeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap map =  super.buildFieldList();
        map.put("itemRangeId", new ItemRange());
        map.put("description", new Description());
        return map;
    }
    
    @Override
    public List<String> getDefaultLookupFields() {
        List<String> ret = new ArrayList<String>();
        
        ret.add("itemRangeId");
        ret.add("description");

        return ret;
    }
}

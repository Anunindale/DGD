/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.dimensions.superclasses;

import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;

/**
 *
 * @author riaan
 */
@Entity
public class InventoryDimension extends EMCEntityClass {

    private String dimensionId;
    private String description;
    private int sortCode;

    /** Creates a new instance of InventoryDimension */
    public InventoryDimension() {

    }

    public String getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSortCode() {
        return sortCode;
    }

    public void setSortCode(int sortCode) {
        this.sortCode = sortCode;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("description", new Description());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> ret = super.buildDefaultLookupFieldList();
        ret.add("dimensionId");
        ret.add("description");
        return ret;
    }
}

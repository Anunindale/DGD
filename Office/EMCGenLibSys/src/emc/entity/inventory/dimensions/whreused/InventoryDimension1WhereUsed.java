/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.dimensions.whreused;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FK;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
import emc.datatypes.systemwide.Description;
import emc.datatypes.systemwide.ItemPrimaryReference;
import emc.framework.EMCEntityClass;
import emc.inventory.ItemReferenceInterface;
import java.util.HashMap;
import javax.persistence.Entity;

/**
 *
 * @author wikus
 */
@Entity
public class InventoryDimension1WhereUsed extends EMCEntityClass implements ItemReferenceInterface {

    private String tableName;
    private String dimension1;
    private String itemId;
    private String itemReference;
    private String itemDescription;

    public String getDimension1() {
        return dimension1;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("dimension1", new Dimension1FK());
        toBuild.put("itemId", new ItemIdFK());
        toBuild.put("itemReference", new ItemPrimaryReference());
        toBuild.put("itemDescription", new Description());
        return toBuild;
    }
}

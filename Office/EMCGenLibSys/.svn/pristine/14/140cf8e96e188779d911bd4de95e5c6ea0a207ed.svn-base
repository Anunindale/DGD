/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.dimensions.whreused;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FK;
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
public class InventoryDimension3WhereUsed extends EMCEntityClass implements ItemReferenceInterface {

    private String dimension3;
    private String itemId;
    private String itemReference;
    private String itemDescription;

    public String getDimension3() {
        return dimension3;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
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

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("dimension3", new Dimension3FK());
        toBuild.put("itemId", new ItemIdFK());
        toBuild.put("itemReference", new ItemPrimaryReference());
        toBuild.put("itemDescription", new Description());
        return toBuild;
    }
}

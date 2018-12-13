/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions.additionaldimensions;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.unitsofmeasure.foreignkeys.UnitOfMeasureFKNotMandatory;
import emc.datatypes.inventory.dimensiontable.foreignkeys.DimensionIdFK;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @Name         : InventoryAdditionalDimensions
 *
 * @Date         : 08 Jun 2009
 * 
 * @Description  : This entity class is used to keep additional dimensions for items based on a combination of an item id and a dimension id.
 *
 * @author       : riaan
 */
@Entity
@Table(name = "InventoryAdditionalDimensions", uniqueConstraints = {@UniqueConstraint(columnNames = {"itemId", "dimensionId", "companyId"})})
public class InventoryAdditionalDimensions extends EMCEntityClass {

    private String itemId;
    private long dimensionId;
    private double width;
    private String widthUOM;
    
    /** Creates a new instance of InventoryAdditionalDimensions. */
    public InventoryAdditionalDimensions() {

    }

    public void setDimensionId(long dimensionId) {
        this.dimensionId = dimensionId;
    }

    public long getDimensionId() {
        return dimensionId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public String getWidthUOM() {
        return widthUOM;
    }

    public void setWidthUOM(String widthUOM) {
        this.widthUOM = widthUOM;
    }
    
    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("itemId", new ItemIdFK());
        toBuild.put("dimensionId", new DimensionIdFK());
        toBuild.put("widthUOM", new UnitOfMeasureFKNotMandatory());
        
        return toBuild;
    }

}

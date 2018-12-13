/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions.additionaldimensions.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.datasources.inventory.BatchByDimIdDS;
import emc.datatypes.datasources.inventory.Dim1ByDimIdDS;
import emc.datatypes.datasources.inventory.Dim2ByDimIdDS;
import emc.datatypes.datasources.inventory.Dim3ByDimIdDS;
import emc.datatypes.datasources.inventory.SerialByDimIdDS;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemReferenceFK;
import emc.entity.inventory.dimensions.additionaldimensions.InventoryAdditionalDimensions;
import java.util.HashMap;

/**
 * @Name         : InventoryAdditionalDimensionsDS
 *
 * @Date         : 25 Jun 2009
 * 
 * @Description  : Datasource for Inventory Additional Dimensions table.
 *
 * @author       : riaan
 */
public class InventoryAdditionalDimensionsDS extends InventoryAdditionalDimensions {
    
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String batch;
    private String serial;
    private String itemRef;

    /** Creates a new instance of InventoryAdditionalDimensionsDS. */
    public InventoryAdditionalDimensionsDS() {
        this.setDataSource(true);
    }

    public String getDimension1() {
        return dimension1;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public String getDimension2() {
        return dimension2;
    }

    public void setDimension2(String dimension2) {
        this.dimension2 = dimension2;
    }

    public String getDimension3() {
        return dimension3;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
    
    public String getItemRef() {
        return itemRef;
    }

    public void setItemRef(String itemRef) {
        this.itemRef = itemRef;
    }
    
    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("dimension1", new Dim1ByDimIdDS());
        toBuild.put("dimension2", new Dim2ByDimIdDS());
        toBuild.put("dimension3", new Dim3ByDimIdDS());
        
        toBuild.put("batch", new BatchByDimIdDS());
        toBuild.put("serial", new SerialByDimIdDS());
        
        toBuild.put("itemRef", new ItemReferenceFK());
        
        return toBuild;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory;

import emc.datatypes.datasources.inventory.Dim1DS;
import emc.datatypes.datasources.inventory.Dim2DS;
import emc.datatypes.datasources.inventory.Dim3DS;
import emc.datatypes.datasources.inventory.itemPrimaryReferenceDS;
import emc.datatypes.inventory.barcodes.Barcode;
import emc.datatypes.inventory.barcodes.BarcodeType;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryBarcodes", uniqueConstraints = {@UniqueConstraint(columnNames = {"itemId", "barcode", "companyId"})})
public class InventoryBarcodes extends EMCEntityClass {

    private String itemId;
    private String barcode;
    private String barcodeType;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    
    
    /** Creates a new instance of InventoryBarcodes */
    public InventoryBarcodes() {
        
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getBarcodeType() {
        return barcodeType;
    }

    public void setBarcodeType(String barcodeType) {
        this.barcodeType = barcodeType;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("itemId", new ItemIdFK());
        toBuild.put("barcode", new Barcode());
        toBuild.put("barcodeType", new BarcodeType());
        toBuild.put("dimension1", new Dim1DS());
        toBuild.put("dimension2", new Dim2DS());
        toBuild.put("dimension3", new Dim3DS());
        
        return toBuild;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemReferenceFK;
import emc.datatypes.inventory.journals.stocktakecapture.Dimension1DS;
import emc.datatypes.inventory.journals.stocktakecapture.Dimension2DS;
import emc.datatypes.inventory.journals.stocktakecapture.Dimension3DS;
import emc.datatypes.systemwide.Description;
import emc.entity.sop.SOPSalesOrderPostLines;
import emc.inventory.DimensionIDInterface;
import emc.inventory.ItemReferenceInterface;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class SOPSalesOrderPostLinesDS extends SOPSalesOrderPostLines implements ItemReferenceInterface, DimensionIDInterface {

    private String itemReference;
    private String itemDescription;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String warehouse;
    private String location;
    private String serial;
    private String batch;
    private String pallet;

    public SOPSalesOrderPostLinesDS() {
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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPallet() {
        return pallet;
    }

    public void setPallet(String pallet) {
        this.pallet = pallet;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("itemReference", new ItemReferenceFK());
        toBuild.put("itemDescription", new Description());
        toBuild.put("dimension1", new Dimension1DS());
        toBuild.put("dimension2", new Dimension2DS());
        toBuild.put("dimension3", new Dimension3DS());
        return toBuild;
    }
}

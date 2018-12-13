/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.developertools;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FKNotMandatory;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemReferenceFK;
import emc.datatypes.inventory.location.foreignkeys.LocationFK;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "DevOnHandImportTable")
public class DevOnHandImportTable extends EMCEntityClass {

    private String itemId;
    private String colourOld;
    private String batch;
    private String serial;
    private String location;
    private String warehouse;
    private double qty;
    private String sizeDim;
    private String oldItem;
    private String wUOM;
    private double width;
    private double price;
    @Temporal(TemporalType.DATE)
    private Date importDate;
    private String config;
    private String colour;

    public DevOnHandImportTable() {
    }

    public DevOnHandImportTable(String itemId, String config, String colour, String size, String batch, String serial, String location, String warehouse, double qty,Date importDate, double price) {
        this.itemId = itemId;
        this.config = config;
        this.colour = colour;
        this.batch = batch;
        this.serial = serial;
        this.location = location;
        this.qty = qty;
        this.warehouse = warehouse;
        this.importDate = importDate;
        this.price = price;
        this.sizeDim = size;
    }
    
    public DevOnHandImportTable(double qty, String itemId, String sizeDim, String batch, String serial, String location, String warehouse,double price) {
        this.itemId = itemId;
        this.sizeDim = sizeDim;
        this.batch = batch;
        this.serial = serial;
        this.location = location;
        this.qty = qty;
        this.warehouse = warehouse;
        this.price = price;
    }
    
    //Used for elastic
    public DevOnHandImportTable(String itemId, String colourOld,  String batch, String serial, String location, String warehouse,double qty,double price) {
        this.itemId = itemId;
        this.colourOld = colourOld;
        this.batch = batch;
        this.serial = serial;
        this.location = location;
        this.qty = qty;
        this.warehouse = warehouse;
        this.price = price;
    }
    
    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("itemId", new ItemReferenceFK());
        toBuild.put("location", new LocationFK());
        toBuild.put("sizeDim", new Dimension2FKNotMandatory());
        
        return toBuild;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getColourOld() {
        return colourOld;
    }

    public void setColourOld(String colourOld) {
        this.colourOld = colourOld;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
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

    public String getSizeDim() {
        return sizeDim;
    }

    public void setSizeDim(String sizeDim) {
        this.sizeDim = sizeDim;
    }

    public String getOldItem() {
        return oldItem;
    }

    public void setOldItem(String oldItem) {
        this.oldItem = oldItem;
    }

    public String getWUOM() {
        return wUOM;
    }

    public void setWUOM(String wUOM) {
        this.wUOM = wUOM;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    /**
     * @return the config
     */
    public String getConfig() {
        return config;
    }

    /**
     * @param config the config to set
     */
    public void setConfig(String config) {
        this.config = config;
    }

    /**
     * @return the colour
     */
    public String getColour() {
        return colour;
    }

    /**
     * @param colour the colour to set
     */
    public void setColour(String colour) {
        this.colour = colour;
    }
}

package emc.entity.inventory.transactions.datasource;

import emc.datatypes.datasources.inventory.itemPrimaryReferenceDSNotManditory;
import emc.datatypes.inventory.transactions.datasource.search.Batch;
import emc.datatypes.inventory.transactions.datasource.search.Colour;
import emc.datatypes.inventory.transactions.datasource.search.Config;
import emc.datatypes.inventory.transactions.datasource.search.Location;
import emc.datatypes.inventory.transactions.datasource.search.Pallet;
import emc.datatypes.inventory.transactions.datasource.search.SerialNo;
import emc.datatypes.inventory.transactions.datasource.search.Size;
import emc.datatypes.inventory.transactions.datasource.search.Warehouse;
import emc.entity.inventory.journals.datasource.*;
import emc.entity.inventory.transactions.InventoryTransactions;
import java.util.HashMap;

/**
 * 
 * @author wikus
 */
public class InventoryTransactionsDS extends InventoryTransactions {

    private String colour;
    private String config;
    private String size;
    private String warehouse;
    private String batch;
    private String serialNo;
    private String location;
    private String pallet;
    private String itemRef;
    private String displayColumns;

    public String getItemRef() {
        return itemRef;
    }

    public void setItemRef(String itemRef) {
        this.itemRef = itemRef;
    }

    public InventoryTransactionsDS() {
        this.setDataSource(true);
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getDisplayColumns() {
        return displayColumns;
    }

    public void setDisplayColumns(String displayColumns) {
        this.displayColumns = displayColumns;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("colour", new Colour());
        toBuild.put("config", new Config());
        toBuild.put("size", new Size());
        toBuild.put("warehouse", new Warehouse());
        toBuild.put("itemRef", new itemPrimaryReferenceDSNotManditory());
        toBuild.put("batch", new Batch());
        toBuild.put("location", new Location());
        toBuild.put("pallet", new Pallet());
        toBuild.put("serialNo", new SerialNo());
        return toBuild;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
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
}

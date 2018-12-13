/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.journals.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.itemmaster.Description;
import emc.datatypes.inventory.journals.journallines.CountQty;
import emc.datatypes.inventory.journals.journallines.QtyOnHand;
import emc.datatypes.inventory.journals.stocktakecapture.BatchDS;
import emc.datatypes.inventory.journals.stocktakecapture.Dimension1DS;
import emc.datatypes.inventory.journals.stocktakecapture.Dimension2DS;
import emc.datatypes.inventory.journals.stocktakecapture.Dimension3DS;
import emc.datatypes.inventory.journals.stocktakecapture.ItemReferenceDS;
import emc.datatypes.inventory.journals.stocktakecapture.LocationDS;
import emc.datatypes.inventory.journals.stocktakecapture.PageNumberDS;
import emc.datatypes.inventory.journals.stocktakecapture.PalletDS;
import emc.datatypes.inventory.journals.stocktakecapture.SerialDS;
import emc.datatypes.inventory.journals.stocktakecapture.WarehouseDS;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.register.InventoryStocktakeRegister;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.inventory.DimensionIDInterface;
import emc.inventory.ItemReferenceInterface;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class InventoryStocktakeCaptureDS extends EMCEntityClass implements DimensionIDInterface, ItemReferenceInterface {

    private String journalNumber;
    private String journalDescription;
    private String transId;
    private String itemId;
    private String itemReference;
    private String itemDescription;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String warehouse;
    private String location;
    private String batch;
    private String serial;
    private String pallet;
    private double quantityOnHand;
    private double quantityCounted;
    private double variance;
    private double costVariance;
    private double itemCost;
    private boolean printOnHand;
    private String journalCreatedDate;
    private int pageNumber;
    //Sources of this datasource
    private double journalLineNo;
    private long registerLineRecordId;
    //Variance Report Field
    private double grandTotalHonHand;
    private String locationSplit;

    public InventoryStocktakeCaptureDS() {
        this.setDataSource(true);
    }

    public double getJournalLineNo() {
        return journalLineNo;
    }

    public void setJournalLineNo(double journalLineNo) {
        this.journalLineNo = journalLineNo;
    }

    public long getRegisterLineRecordId() {
        return registerLineRecordId;
    }

    public void setRegisterLineRecordId(long registerLineRecordId) {
        this.registerLineRecordId = registerLineRecordId;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
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

    public String getJournalDescription() {
        return journalDescription;
    }

    public void setJournalDescription(String journalDescription) {
        this.journalDescription = journalDescription;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getJournalNumber() {
        return journalNumber;
    }

    public void setJournalNumber(String journalNumber) {
        this.journalNumber = journalNumber;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
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

    public double getQuantityCounted() {
        return quantityCounted;
    }

    public void setQuantityCounted(double quantityCounted) {
        this.quantityCounted = quantityCounted;
    }

    public double getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(double quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
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

    public double getItemCost() {
        return itemCost;
    }

    public void setItemCost(double itemCost) {
        this.itemCost = itemCost;
    }

    public double getVariance() {
        return variance;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }

    public double getCostVariance() {
        return costVariance;
    }

    public void setCostVariance(double costVariance) {
        this.costVariance = costVariance;
    }

    public boolean isPrintOnHand() {
        return printOnHand;
    }

    public void setPrintOnHand(boolean printOnHand) {
        this.printOnHand = printOnHand;
    }

    public String getJournalCreatedDate() {
        return journalCreatedDate;
    }

    public void setJournalCreatedDate(String journalCreatedDate) {
        this.journalCreatedDate = journalCreatedDate;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public double getGrandTotalHonHand() {
        return grandTotalHonHand;
    }

    public void setGrandTotalHonHand(double grandTotalHonHand) {
        this.grandTotalHonHand = grandTotalHonHand;
    }

    public String getLocationSplit() {
        return locationSplit;
    }

    public void setLocationSplit(String locationSplit) {
        this.locationSplit = locationSplit;
    }

    @Override
    public void setDimensionId(long dimensionId) {
        return;
    }

    @Override
    public long getDimensionId() {
        return 0;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("itemReference", new ItemReferenceDS());
        toBuild.put("itemDescription", new Description());
        toBuild.put("dimension1", new Dimension1DS());
        toBuild.put("dimension2", new Dimension2DS());
        toBuild.put("dimension3", new Dimension3DS());
        toBuild.put("warehouse", new WarehouseDS());
        toBuild.put("location", new LocationDS());
        toBuild.put("batch", new BatchDS());
        toBuild.put("serial", new SerialDS());
        toBuild.put("pallet", new PalletDS());
        toBuild.put("quantityCounted", new CountQty());
        toBuild.put("quantityOnHand", new QtyOnHand());
        toBuild.put("pageNumber", new PageNumberDS());
        return toBuild;
    }

    @Override
    public EMCQuery getQuery() {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class.getName());
        query.addTableAnd(InventoryStocktakeRegister.class.getName(), "journalNumber", InventoryJournalLines.class.getName(), "masterId");
        query.addTableAnd(InventoryWarehouse.class.getName(), "warehouse", InventoryJournalLines.class.getName(), "warehouseId");
        query.addAnd("transId", InventoryJournalLines.class.getName(), EMCQueryConditions.EQUALS, "transId", InventoryStocktakeRegister.class.getName());
        query.addAnd("journalNumber", "", InventoryJournalLines.class.getName());
        query.addField("journalNumber", InventoryJournalLines.class.getName());//0
        query.addField("transId", InventoryJournalLines.class.getName());//1
        query.addField("itemId", InventoryJournalLines.class.getName());//2
        query.addField("dimension1", InventoryJournalLines.class.getName());//3
        query.addField("dimension2", InventoryJournalLines.class.getName());//4
        query.addField("dimension3", InventoryJournalLines.class.getName());//5
        query.addField("warehouse", InventoryJournalLines.class.getName());//6
        query.addField("location", InventoryStocktakeRegister.class.getName());//7
        query.addField("batch", InventoryStocktakeRegister.class.getName());//8
        query.addField("serial", InventoryStocktakeRegister.class.getName());//9
        query.addField("pallet", InventoryStocktakeRegister.class.getName());//10
        query.addField("countQOH", InventoryJournalLines.class.getName());//11
        query.addField("onHandQty", InventoryStocktakeRegister.class.getName());//12
        query.addField("quantity", InventoryStocktakeRegister.class.getName());//13
        query.addField("cost", InventoryJournalLines.class.getName());//14
        query.addField("lineNo", InventoryJournalLines.class.getName());//15
        query.addField("recordID", InventoryStocktakeRegister.class.getName());//16
        query.addField("originalCountedQty", InventoryStocktakeRegister.class.getName());//17
        query.addField("stockTakeQuantityDiff", InventoryWarehouse.class.getName());//18
        query.addField("stockTakeValueDiff", InventoryWarehouse.class.getName());//19
        query.addField("pageNumber", InventoryStocktakeRegister.class.getName());//20
        return query;
    }
}

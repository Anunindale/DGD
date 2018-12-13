/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions;

import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryPallet;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.dimensions.additionaldimensions.InventoryAdditionalDimensions;
import emc.entity.inventory.serialbatch.InventorySerialBatch;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.dimensions.DimensionsEnum;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.inventory.DimensionIDInterface;
import emc.inventory.DimensionIDInterfaceOnlyDims;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryDimensionTableBean extends EMCEntityBean implements InventoryDimensionTableLocal {

    /** Creates a new instance of InventoryDimensionTableBean */
    public InventoryDimensionTableBean() {

    }

    /** 
     * Returns the recordID of a dimension matching the specified criteria.  If one of the parameters is null, null should be passed to the method.
     * Null values added to the dimensionId field will be saved as the String "null" in SQL.
     */
    public long getDimRecordId(String dim1Id, String dim2Id, String dim3Id, String warehouseId, EMCUserData userData) {
        return getDimRecordId(null, dim1Id, dim2Id, dim3Id, warehouseId, null, null, null, userData);
    }

    /**
     * Just a wrapper for a single result select on record ID on the table
     * @param dimRecordID
     * @param userData
     * @return InventoryDimension table for record Id provided null if not found
     */
    public InventoryDimensionTable getDimensionRecord(long dimRecordID, EMCUserData userData) {
        EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, InventoryDimensionTable.class.getName());
        qu.addAnd("recordID", dimRecordID);
        return (InventoryDimensionTable) util.executeSingleResultQuery(qu, userData);
    }

    /** 
     * Returns the recordID of a dimension matching the specified criteria.  If one of the parameters is null, null should be passed to the method.
     * Null values added to the dimensionId field will be saved as the String "null" in SQL.
     */
    public long getDimRecordId(String batchId, String dim1Id, String dim2Id, String dim3Id, String warehouseId, String locationId, String palletId, String itemSerialId, EMCUserData userData) {
        if (isBlank(batchId)) {
            batchId = null;
        }
        
        if (isBlank(dim1Id)) {
            dim1Id = null;
        }
        
        if (isBlank(dim2Id)) {
            dim2Id = null;
        }
        
        if (isBlank(dim3Id)) {
            dim3Id = null;
        }
        
        if (isBlank(warehouseId)) {
            warehouseId = null;
        }
        
        if (isBlank(locationId)) {
            locationId = null;
        }
        
        if (isBlank(palletId)) {
            palletId = null;
        }
        
        if (isBlank(itemSerialId)) {
            itemSerialId = null;
        }
        
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimensionTable.class.getName());

        String dimensionId = generateDimensionId(batchId, dim1Id, dim2Id, dim3Id, warehouseId, locationId, palletId, itemSerialId);
        query.addAnd("dimensionId", dimensionId);

        query.addField("recordID");

        Long recordId = (Long) util.executeSingleResultQuery(query, userData);

        if (recordId == null) {
            try {
                InventoryDimensionTable dimTable = new InventoryDimensionTable();
                dimTable.setBatchId(batchId);
                dimTable.setDimension1Id(dim1Id);
                dimTable.setDimension2Id(dim2Id);
                dimTable.setDimension3Id(dim3Id);
                dimTable.setWarehouseId(warehouseId);
                dimTable.setLocationId(locationId);
                dimTable.setPalletId(palletId);
                dimTable.setItemSerialId(itemSerialId);

                return ((InventoryDimensionTable) (insert(dimTable, userData))).getRecordID();
            } catch (EMCEntityBeanException ex) {
                if (EMCDebug.getDebug()) Logger.getLogger("emc").log(Level.SEVERE, "Failed to get DimId", userData);
                return 0;
            }
        } else {
            return recordId;
        }
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryDimensionTable dimTable = (InventoryDimensionTable) iobject;

        dimTable.setDimensionId(generateDimensionId(dimTable.getBatchId(), dimTable.getDimension1Id(), dimTable.getDimension2Id(),
                dimTable.getDimension3Id(), dimTable.getWarehouseId(), dimTable.getLocationId(),
                dimTable.getPalletId(), dimTable.getItemSerialId()));
        return super.insert(iobject, userData);
    }

    /** 
     * Generates a dimensionId using the given values.  
     * Null values are added as the String "null".
     */
    private String generateDimensionId(String batchId, String dim1Id, String dim2Id, String dim3Id, String warehouseId, String locationId, String palletId, String itemSerialId) {
        return batchId + dim1Id + dim2Id + dim3Id + warehouseId + locationId + palletId + itemSerialId + "";
    }

    /**
     * This method is used to populate dimension fields on a record using a dimension table record id.
     * @param record The record on which dimensions should be populated.
     * @param userData User data.
     */
    public void populateDimensions(DimensionIDInterfaceOnlyDims record, EMCUserData userData) {
        InventoryDimensionTable dims = getDimensionRecord(record.getDimRecordID(), userData);

        if (dims != null) {
            record.setDimension1(dims.getDimension1Id());
            record.setDimension2(dims.getDimension2Id());
            record.setDimension3(dims.getDimension3Id());
        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to retrieve dimensions from dim id.", userData);
        }
    }

    /**
     * This method is used to populate dimension fields on a record using a dimension table record id.
     * @param record The record on which dimensions should be populated.
     * @param userData User data.
     */
    public void populateDimensions(DimensionIDInterface record, EMCUserData userData) {
        InventoryDimensionTable dims = getDimensionRecord(record.getDimensionId(), userData);

        if (dims != null) {
            record.setDimension1(dims.getDimension1Id());
            record.setDimension2(dims.getDimension2Id());
            record.setDimension3(dims.getDimension3Id());
            record.setWarehouse(dims.getWarehouseId());
            record.setLocation(dims.getLocationId());
            record.setSerial(dims.getItemSerialId());
            record.setBatch(dims.getBatchId());
            record.setPallet(dims.getPalletId());
        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to retrieve dimensions from dim id.", userData);
        }
    }

    /**
     * Returns the specified dimension on the spesified dimensionId
     * @param dimension
     * @param recordId
     * @param userData
     * @returnthe specified dimension on the spesified dimensionId or null if record does not exist or field is blank;
     */
    public String getDimension(DimensionsEnum dimension, long recordId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimensionTable.class.getName());
        query.addAnd("recordID", recordId);
        switch (dimension) {
            case DIMENSION1:
                query.addField("dimension1Id");
                break;
            case DIMENSION3:
                query.addField("dimension3Id");
                break;
            case DIMENSION2:
                query.addField("dimension2Id");
                break;
            case WAREHOUSE:
                query.addField("warehouseId");
                break;
            case LOCATION:
                query.addField("locationId");
                break;
            case SERIAL:
                query.addField("itemSerialId");
                break;
            case BATCH:
                query.addField("batchId");
                break;
            case PALLET:
                query.addField("palletId");
                break;
        }
        return (String) util.executeSingleResultQuery(query, userData);
    }

    public boolean validateDimensions(DimensionIDInterface record, boolean allActive, EMCUserData userData) {
        if (isBlank(record.getItemId())) {
            Logger.getLogger("emc").log(Level.SEVERE, "No Item ID specified", userData);
            return false;
        } else {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
            query.addTableAnd(InventoryItemMaster.class.getName(), "itemDimensionGroupId", InventoryItemDimensionGroup.class.getName(), "dimensionGroup");
            query.addAnd("itemId", record.getItemId(), InventoryItemMaster.class.getName());
            InventoryItemDimensionGroup dimRecord = (InventoryItemDimensionGroup) util.executeSingleResultQuery(query, userData);

            query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
            query.addAnd("itemId", record.getItemId(), InventoryItemMaster.class.getName());

            if (!isBlank(record.getDimension1()) && dimRecord.getDim1Active()) {
                query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
                query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
                query.addAnd("dimension1Id", record.getDimension1(), InventoryItemDimensionCombinations.class.getName());
            } else if (dimRecord.getDim1Active() && allActive) {
                Logger.getLogger("emc").log(Level.SEVERE, "Config is active, please set the field.", userData);
                return false;
            } else if (!dimRecord.getDim1Active() && !isBlank(record.getDimension1())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Config is not active.", userData);
                return false;
            }
            if (!isBlank(record.getDimension2()) && dimRecord.getDim2Active()) {
                query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
                query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
                query.addAnd("dimension2Id", record.getDimension2(), InventoryItemDimensionCombinations.class.getName());
            } else if (dimRecord.getDim2Active() && allActive) {
                Logger.getLogger("emc").log(Level.SEVERE, "Size is active, please set the field.", userData);
                return false;
            } else if (!dimRecord.getDim2Active() && !isBlank(record.getDimension2())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Size is not active.", userData);
                return false;
            }
            if (!isBlank(record.getDimension3()) && dimRecord.getDim3Active()) {
                query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
                query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
                query.addAnd("dimension3Id", record.getDimension3(), InventoryItemDimensionCombinations.class.getName());
            } else if (dimRecord.getDim3Active() && allActive) {
                Logger.getLogger("emc").log(Level.SEVERE, "Colour is active, please set the field.", userData);
                return false;
            } else if (!dimRecord.getDim3Active() && !isBlank(record.getDimension3())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Colour is not active.", userData);
                return false;
            }
            if (!isBlank(record.getWarehouse()) && dimRecord.getWarehouseActive()) {
                EMCQuery queryW = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouse.class.getName());
                queryW.addAnd("warehouseId", record.getWarehouse());
                if (!util.exists(queryW, userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The warehouse specified does not exist.", userData);
                    return false;
                }
            } else if (dimRecord.getWarehouseActive() && allActive) {
                Logger.getLogger("emc").log(Level.SEVERE, "Warehouse is active, please set the field.", userData);
                return false;
            } else if (!dimRecord.getWarehouseActive() && !isBlank(record.getWarehouse())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Warehouse is not active.", userData);
                return false;
            }
            if (!isBlank(record.getLocation()) && dimRecord.getLocationActive()) {
                if (isBlank(record.getWarehouse())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "No warehouse specified.", userData);
                    return false;
                } else {
                    EMCQuery queryL = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class.getName());
                    queryL.addAnd("warehouseId", record.getWarehouse());
                    queryL.addAnd("locationId", record.getLocation());
                    if (!util.exists(queryL, userData)) {
                        Logger.getLogger("emc").log(Level.SEVERE, "The location specified does not exist.", userData);
                        return false;
                    }
                }
            } else if (dimRecord.getLocationActive() && allActive) {
                Logger.getLogger("emc").log(Level.SEVERE, "Location is active, please set the field.", userData);
                return false;
            } else if (!dimRecord.getLocationActive() && !isBlank(record.getLocation())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Location is not active.", userData);
                return false;
            }
            if (!isBlank(record.getSerial()) && dimRecord.getSerialNumberActive()) {
                query.addTableAnd(InventoryTransactions.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
                query.addTableAnd(InventorySerialBatch.class.getName(), "transId", InventoryTransactions.class.getName(), "transId");
                query.addAnd("serial", record.getSerial(), InventorySerialBatch.class.getName());
            } else if (dimRecord.getSerialNumberActive() && allActive) {
                Logger.getLogger("emc").log(Level.SEVERE, "Serial No is active, please set the field.", userData);
                return false;
            } else if (!dimRecord.getSerialNumberActive() && !isBlank(record.getSerial())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Serial No is not active.", userData);
                return false;
            }
            if (!isBlank(record.getBatch()) && dimRecord.getBatchNumberActive()) {
                query.addTableAnd(InventoryTransactions.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
                query.addTableAnd(InventorySerialBatch.class.getName(), "transId", InventoryTransactions.class.getName(), "transId");
                query.addAnd("batch", record.getBatch(), InventorySerialBatch.class.getName());
            } else if (dimRecord.getBatchNumberActive() && allActive) {
                Logger.getLogger("emc").log(Level.SEVERE, "Batch No is active, please set the field.", userData);
                return false;
            } else if (!dimRecord.getBatchNumberActive() && !isBlank(record.getBatch())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Batch No is not active.", userData);
                return false;
            }
            if (!isBlank(record.getPallet()) && dimRecord.getPalletIdActive()) {
                EMCQuery queryP = new EMCQuery(enumQueryTypes.SELECT, InventoryPallet.class.getName());
                queryP.addAnd("palletId", record.getPallet());
                if (!util.exists(queryP, userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The pallet specified does not exist.", userData);
                    return false;
                }
            } else if (dimRecord.getPalletIdActive() && allActive) {
                Logger.getLogger("emc").log(Level.SEVERE, "Palle  is active, please set the field.", userData);
                return false;
            } else if (!dimRecord.getPalletIdActive() && !isBlank(record.getPallet())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Pallet is not active.", userData);
                return false;
            }
            return util.exists(query, userData);
        }
    }

    /**
     * Returns the dimension record ID as used in the additional dimension table
     * @param dimension1
     * @param dimension2
     * @param dimension3
     * @param batch
     * @param serial
     * @param userData
     * @return
     */
    public long getWidthDimRecordID(String dimension1, String dimension2, String dimension3, String batch, String serial, EMCUserData userData) {
        return getDimRecordId(batch, dimension1, dimension2, dimension3, null, null, null, serial, userData);
    }

    /**
     * Return the width for the specified width dim Id
     * @param widthDimId
     * @param userData
     * @return
     */
    public double findWidth(long widthDimId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryAdditionalDimensions.class.getName());
        query.addAnd("dimensionId", widthDimId);
        Object o = util.executeSingleResultQuery(query, userData);
        if (o == null) {
            return 0;
        } else {
            return ((InventoryAdditionalDimensions) o).getWidth();
        }
    }
}

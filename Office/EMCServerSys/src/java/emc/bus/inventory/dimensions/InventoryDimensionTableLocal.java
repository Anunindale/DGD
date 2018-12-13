/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor. 
 */
package emc.bus.inventory.dimensions;

import emc.enums.inventory.dimensions.DimensionsEnum;
import emc.framework.EMCUserData;
import emc.inventory.DimensionIDInterface;
import emc.inventory.DimensionIDInterfaceOnlyDims;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface InventoryDimensionTableLocal {

    /** 
     * Returns the recordID of a dimension matching the specified criteria.  If one of the parameters is null, null should be passed to the method.
     * Null values added to the dimensionId field will be saved as the String "null" in SQL.
     */
    public long getDimRecordId(java.lang.String dim1Id, java.lang.String dim2Id, java.lang.String dim3Id, java.lang.String warehouseId, emc.framework.EMCUserData userData);

    /**
     * Just a wrapper for a single result select on record ID on the table
     * @param dimRecordID
     * @param userData
     * @return InventoryDimension table for record Id provided null if not found
     */
    public emc.entity.inventory.dimensions.InventoryDimensionTable getDimensionRecord(long dimRecordID, emc.framework.EMCUserData userData);

    /**
     * This method is used to populate dimension fields on a record using a dimension table record id.
     * @param record The record on which dimensions should be populated.
     * @param userData User data.
     */
    public void populateDimensions(DimensionIDInterfaceOnlyDims record, EMCUserData userData);

    /**
     * Returns the specified dimension on the spesified dimensionId
     * @param dimension
     * @param recordId
     * @param userData
     * @returnthe specified dimension on the spesified dimensionId or null if record does not exist or field is blank;
     */
    public String getDimension(DimensionsEnum dimension, long recordId, EMCUserData userData);

    /** 
     * Returns the recordID of a dimension matching the specified criteria.  If one of the parameters is null, null should be passed to the method.
     * Null values added to the dimensionId field will be saved as the String "null" in SQL.
     */
    public long getDimRecordId(String batchId, String dim1Id, String dim2Id, String dim3Id, String warehouseId, String locationId, String palletId, String itemSerialId, EMCUserData userData);

    /**
     * This method is used to populate dimension fields on a record using a dimension table record id.
     * @param record The record on which dimensions should be populated.
     * @param userData User data.
     */
    public void populateDimensions(DimensionIDInterface record, EMCUserData userData);

    /**
     * Validate the dimension on the record;
     * @param record The record to validate
     * @param allActive Should it check all the dimensions or just the ones thats not blank
     * @param userData
     * @return The result of the validation
     */
    public boolean validateDimensions(DimensionIDInterface record, boolean allActive, EMCUserData userData);

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
    public long getWidthDimRecordID(String dimension1, String dimension2, String dimension3, String batch, String serial, EMCUserData userData);

    /**
     * Return the width for the specified width dim Id
     * @param widthDimId
     * @param userData
     * @return
     */
    public double findWidth(long widthDimId, EMCUserData userData);

}

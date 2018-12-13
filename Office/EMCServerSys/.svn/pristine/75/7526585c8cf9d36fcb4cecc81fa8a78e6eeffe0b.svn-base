package emc.bus.inventory;

import emc.entity.inventory.InventoryReference;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import emc.inventory.ItemReferenceInterface;
import emc.tables.EMCTable;
import java.util.ArrayList;

/**
 * 
 * @author wikus
 */
public interface InventoryReferenceLocal extends EMCEntityBeanLocalInterface {

    public void setNewPrimaryReference(String itemId, String Reference, EMCUserData userData) throws EMCEntityBeanException;

    public boolean checkUsed(String itemId, String reference, EMCUserData userData);

    /**
     * Does a check to see if the Item reference enterd is valid;
     * @param itemReference the reference entered
     * @param userData
     * @return String[] 0-itemId 1-Primary Reference or null if reference is invalid
     */
    public String[] checkItemReference(String itemReference, EMCUserData userData);

    public void setDefaultReference(String itemId, EMCUserData userData) throws EMCEntityBeanException;

    public boolean testUsage(EMCTable oldRecord, EMCUserData userData);

    public Object[] getSupplierReference(String itemId, String dim1Id, String dim2Id, String dim3Id, String supplierId, EMCUserData userData);

    public boolean doItemRefValidation(ItemReferenceInterface record, EMCUserData userData);

    public void populateDSFields(ItemReferenceInterface record, EMCUserData userData);

    /**
     * 0 - Reference
     * 1 - Description
     * @param itemId
     * @param refType
     * @param userData
     * @return
     */
    public ArrayList<String> findReferenceAndDesc(String itemId, InventoryReferenceTypes refType, EMCUserData userData);

    /**
     * Does a check to see if the Item reference enterd is valid;
     * @param itemReference the reference entered
     * @param logMessage should the method log messages
     * @param userData
     * @return String[] 0-itemId 1-Primary Reference or null if reference is invalid
     */
    public String[] checkItemReference(String itemReference, boolean logMessage, EMCUserData userData);

    /**
     * Validates the item reference on the specified record and fetches and sets the
     * appropriate item id, primary reference and item description.
     *
     * If the record passed to this method implements ItemDimensionInterface, the
     * appropriate dimensions will also be set.
     *
     * @param record Record to validate.
     * @param supplier Supplier number.  This may be null.
     * @param customer Customer number.  This may be null.
     * @param userData User data.
     * @return A boolean indicating whether the item reference on the specified record is valid.
     */
    public boolean processItemReference(ItemReferenceInterface record, String supplier, String customer, EMCUserData userData);

    /**
     * Gets data from the InventoryCustomerReference and InventorySupplierReference tables and
     * inserts it into the InventoryReference table.
     */
    public void importData(EMCUserData userData) throws EMCEntityBeanException;

    public InventoryReference findSupplierReferenceRecord(String itemId, String dim1Id, String dim2Id, String dim3Id, String supplierId, boolean logMsg, EMCUserData userData);

    public InventoryReference findCustomerReferenceRecord(String itemId, String dim1Id, String dim2Id, String dim3Id, String customerId, EMCUserData userData);

    public Object[] getCustomerReference(String itemId, String dim1Id, String dim2Id, String dim3Id, String customerId, EMCUserData userData);
}

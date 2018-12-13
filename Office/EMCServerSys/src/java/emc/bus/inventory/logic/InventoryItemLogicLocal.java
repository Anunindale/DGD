/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.logic;

import emc.framework.EMCUserData;
import emc.inventory.ItemUOMInterface;
import emc.inventory.ItemUnitPriceInterface;
import emc.inventory.ItemWarehouseInterface;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface InventoryItemLogicLocal {

    /** Validates dimension values on an item. */
    public boolean validateDimensionValues(emc.inventory.ItemDimensionInterface record, emc.framework.EMCUserData userData);

    /** Checks that all active dimensions have values. */
    public boolean validateActiveDimensions(emc.inventory.ItemDimensionInterface record, emc.framework.EMCUserData userData);

    /** Validates items and populates the item reference and description field on an entity.  */
    public boolean doItemRefValidation(emc.inventory.ItemReferenceInterface record, emc.framework.EMCUserData userData);

    /**
     * Sets a unit of measure on the specified record.  Existing UoM values are not overwritten.
     * @param record Record on which UoM should be set.
     * @param userData User data.
     * @return A boolean indicating success.
     */
    public boolean setItemUOM(ItemUOMInterface record, EMCUserData userData);

    /**
     * Sets a warehouse on the specified record.  Existing UoM values are not overwritten.
     * @param record Record on which UoM should be set.
     * @param userData User data.
     * @return A boolean indicating success.
     */
    public boolean setItemWarehouse(ItemWarehouseInterface record, EMCUserData userData);

    /**
     * Sets a unit price on the specified record.  Existing UoM values are not overwritten.
     * @param record Record on which unit price should be set.
     * @param userData User data.
     * @return A boolean indicating success.
     */
    public boolean setItemUnitPrice(ItemUnitPriceInterface record, EMCUserData userData);

    /**
     * Returns lead time for the the specified item and dimensions.  Inventory
     * item dimension combinations will be checked first.  If no lead time is found
     * on item dimension combinations, the item master will be checked.  If no
     * lead time is found on the item master, the default lead time passed to this
     * method will be returned.
     * @param itemId Item id of item to fetch lead time for.
     * @param dimension1 Dimension 1.
     * @param dimension2 Dimension 2.
     * @param dimension3 Dimension 3.
     * @param produce Indicates whether a production lead time should be returned.  If
     *                  this is false, a purchase lead time will be returned.
     * @param defaultLeadTime Default lead time to return if no other lead time has
     *                          been set up.
     * @param userData User data.
     * @return Lead time for the specified item and dimensions.
     */
    public BigDecimal getLeadTime(String itemId, String dimension1, String dimension2, String dimension3, boolean produce, BigDecimal defaultLeadTime, EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public BigDecimal getSafetyStock(Date requiredDate, String itemId, String dimension1, String dimension2, String dimension3, String serialNo, EMCUserData userData);

    /**
     * Returns an economic order quantity for the the specified item and dimensions.  Inventory
     * item dimension combinations will be checked first.  If no economic order quantity is found
     * on item dimension combinations, the item master will be checked.  If no
     * economic order quantity is found on the item master, zero will be returned.
     * @param itemId Item id of item to fetch economic order quantity for.
     * @param dimension1 Dimension 1.
     * @param dimension2 Dimension 2.
     * @param dimension3 Dimension 3.
     * @param userData User data.
     * @return Economic order quantity for the specified item and dimensions.
     */
    public BigDecimal getEconomicOrderQuantity(String itemId, String dimension1, String dimension2, String dimension3, EMCUserData userData);

    /**
     * Returns min/max/mult quantities for the the specified item and dimensions.  Inventory
     * item dimension combinations will be checked first.  If no values are found
     * on item dimension combinations, the item master will be checked.  If no
     * values are found on the item master, zeroes will be returned.
     * @param itemId Item id of item to fetch economic order quantity for.
     * @param dimension1 Dimension 1.
     * @param dimension2 Dimension 2.
     * @param dimension3 Dimension 3.
     * @param userData User data.
     * @return An array containing min/max/mult quantities (in order) for the specified item.
     */
    public java.math.BigDecimal[] getMinMaxMult(java.lang.String itemId, java.lang.String dimension1, java.lang.String dimension2, java.lang.String dimension3, emc.framework.EMCUserData userData);

    public java.math.BigDecimal getLeadTime(java.lang.String itemId, java.lang.String dimension1, java.lang.String dimension2, java.lang.String dimension3, boolean produce, java.math.BigDecimal defaultLeadTime, java.sql.Connection conn, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public String[] getStopPurchasingStatus(String itemId, String dimension1, String dimension2, String dimension3, EMCUserData userData);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.logic;

import emc.enums.inventory.dimensions.EnumDimensions;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.inventory.ItemDimensionInterface;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface InventoryItemDimensionCombinationLogicLocal {

    boolean calculateCombinations(java.lang.String itemId, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    void deleteCombinations(java.lang.String dimensionIdFieldName, java.lang.String dimensionId, emc.framework.EMCUserData userData);

    void updateCombinations(java.lang.String dimensionIdFieldName, java.lang.String oldDimensionId, java.lang.String newDimensionId, emc.framework.EMCUserData userData);

    void calculateCombinations(String itemId, String newDimension, List<Object> newValues, EMCUserData userData);

    /**
     * Returns active dimensions for the given item in an array.
     * @param itemId  Item to get dimensions for.
     * @param userData User data.
     * @return An array containing booleans indicating where dimensions are active.  Indexes are as follows:
     *         0: Dimension 1 
     *         1: Dimension 2 
     *         2: Dimension 3 
     *         3: Warehouse 
     */
    boolean[] getActiveDimensions(String itemId, EMCUserData userData);

    boolean validateDimensionValues(String itemId, String dimension1, String dimension2, String dimension3, EMCUserData userData);

    public boolean validateActiveDimensions(String itemId, String dimension1, String dimension2, String dimension3, String warehouse, boolean checkWarehouse, EMCUserData userData);

    public double getCostPriceForCombination(String itemId, String dim1, String dim2, String dim3, EMCUserData userData);

    public boolean validateDimensionValues(ItemDimensionInterface record, EMCUserData userData);

    public boolean validateActiveDimensions(ItemDimensionInterface record, EMCUserData userData);

    /**
     * Returns a  list of dimension id's sorted by their sort codes on the given item's applicable dimension group.
     *
     * @param dimension Dimension to search on.
     * @param itemId  Item to find dimensions for.
     * @param dimQuery  Query that could select a list of dimensions from somewhere else.  Optional.
     * @param userData User data
     * @return Sorted list of dimensions.
     */
    public List<String> getSortedDimensions(EnumDimensions dimension, String itemId, EMCQuery dimQuery, EMCUserData userData);

    /**
     * This method should check all 'open' orders and return a boolean indicating whether the specified dimension is in use on the specified item. ('Orders' refer to Sales Orders, Purchase Orders, Works Orders, etc.)
     * @param dimensionId Id of dimension to check.
     * @param itemId Id of the item to check.
     * @param dimension Indicates which dimension is to be checked.  (Dimension 1, 2 or 3)
     * @param userData User data.
     * @return A boolean indicating whether the specified dimension is in use.
     */
    public boolean isDimensionInUse(String dimensionId, String itemId, EnumDimensions dimension, EMCUserData userData);

    public boolean validateDimensionValues(String itemId, String dimension1, String dimension2, String dimension3, boolean logMessage, EMCUserData userData);

    public boolean validateActiveDimensions(String itemId, String dimension1, String dimension2, String dimension3, String warehouse, boolean checkWarehouse, boolean logMessages, EMCUserData userData);

public boolean validateActiveDimensions(String itemId, String dimension1, String dimension2, String dimension3, String warehouse, String location, String batch, String serial, String pallet, EMCUserData userData);
}

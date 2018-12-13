/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory;

import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.enums.inventory.inventorytables.InventoryItemTypes;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface InventoryItemMasterLocal extends EMCEntityBeanLocalInterface {

    boolean generateItemDimCombinations(String itemId, EMCUserData userData) throws EMCEntityBeanException;

    public String getItemDimensionGroup(String itemId, EMCUserData userData);

    public void setReference(String itemId, String reference, EMCUserData userData) throws EMCEntityBeanException;

    public void removeReference(String itemId, EMCUserData userData) throws EMCEntityBeanException;

    public String findItemDescription(String itemId, EMCUserData userData);

    public InventoryItemMaster findItem(String itemId, EMCUserData userData);

    public emc.entity.inventory.InventoryItemMaster populateDefaultItemFields(emc.framework.EMCUserData userData);

    public InventoryItemDimensionGroup getItemDimensionGroupRecord(String itemId, EMCUserData userData);

    /**
     * Finds the Min Qty for the given item
     * @param itemId the item to check
     * @param dim1 the config to check
     * @param dim2 the size to check
     * @param dim3 the colour to check
     * @param itemMaster OPTIONAL - selects it if null
     * @param userData plain old EMCUserData
     * @return Double - You take a guess
     */
    public double getMinQty(String itemId, String dim1, String dim2, String dim3, InventoryItemMaster itemMaster, EMCUserData userData);

    /**
     * Returns the type of the given item.
     * @param itemId Id of item.
     * @param userData User data.
     * @return The type of the item on the specified itemId line.
     */
    public InventoryItemTypes getItemType(String itemId, EMCUserData userData);

    public double getCostPrice(String itemId, String dim1, String dim2, String dim3, EMCUserData userData);

    public double getCostPrice(String itemId, long dimId, EMCUserData userData);

    public boolean isKimblingActive(String itemId, EMCUserData userData);

    public int getProductionLeadTime(String itemId, String dimension1, String dimension2, String dimension3, EMCUserData userData);

    public BigDecimal getTotalProcessingTime(String itemId, String dimension1, String dimension2, String dimension3, EMCUserData userData);

    public int getPurchasingLeadTime(String itemId, String dimension1, String dimension2, String dimension3, EMCUserData userData);

    public java.lang.String getItemWebStoreType(java.lang.String itemId, emc.framework.EMCUserData userdata);

    public java.util.List<emc.helpers.inventory.ItemCatalogueWebHelper> getWebItems(java.lang.String WebStoreType, emc.framework.EMCUserData userData);

    public java.util.List<emc.helpers.inventory.ItemCatalogueWebHelper> filterWebItems(java.lang.String itemId, java.lang.String WebStoreType, java.lang.String material, emc.framework.EMCUserData userData);
}

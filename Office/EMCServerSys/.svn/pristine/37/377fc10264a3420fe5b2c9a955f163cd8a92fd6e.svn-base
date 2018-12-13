/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.logic;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.bus.inventory.safetystock.InventorySafetyStockLocal;
import emc.constants.systemConstants;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.purchasing.InventoryStopPurchasingType;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.inventory.ItemDimensionInterface;
import emc.inventory.ItemReferenceInterface;
import emc.inventory.ItemUOMInterface;
import emc.inventory.ItemUnitPriceInterface;
import emc.inventory.ItemWarehouseInterface;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryItemLogicBean extends EMCBusinessBean implements InventoryItemLogicLocal {

    @EJB
    private InventoryItemDimensionCombinationLogicLocal dimLogicBean;
    @EJB
    private InventoryReferenceLocal refBean;
    @EJB
    private InventoryItemMasterLocal itemBean;
    @EJB
    private InventoryDimensionTableLocal dimTableBean;
    @EJB
    private InventorySafetyStockLocal safetyStockBean;

    /** Creates a new instance of InventoryItemLogicBean. */
    public InventoryItemLogicBean() {
    }

    /** Validates dimension values on an item. */
    public boolean validateDimensionValues(ItemDimensionInterface record, EMCUserData userData) {
        return dimLogicBean.validateDimensionValues(record.getItemId(), record.getDimension1(), record.getDimension2(), record.getDimension3(), userData);
    }

    /** Checks that all active dimensions have values. */
    public boolean validateActiveDimensions(ItemDimensionInterface record, EMCUserData userData) {
        return dimLogicBean.validateActiveDimensions(record, userData);
    }

    /** Validates items and populates the item reference and description field on an entity.  */
    public boolean doItemRefValidation(ItemReferenceInterface record, EMCUserData userData) {
        return refBean.doItemRefValidation(record, userData);
    }

    /**
     * Sets a unit of measure on the specified record.  Existing UoM values are not overwritten.
     * @param record Record on which UoM should be set.
     * @param userData User data.
     * @return A boolean indicating success.
     */
    public boolean setItemUOM(ItemUOMInterface record, EMCUserData userData) {
        InventoryItemMaster item = itemBean.findItem(record.getItemId(), userData);

        record.setUom(item.getBaseUOM());

        return true;
    }

    /**
     * Sets a warehouse on the specified record.  Existing UoM values are not overwritten.
     * @param record Record on which warehouse should be set.
     * @param userData User data.
     * @return A boolean indicating success.
     */
    public boolean setItemWarehouse(ItemWarehouseInterface record, EMCUserData userData) {
        InventoryItemMaster item = itemBean.findItem(record.getItemId(), userData);

        record.setWarehouse(item.getDefaultWarehouse());

        return true;
    }

    /**
     * Sets a unit price on the specified record.  Existing UoM values are not overwritten.
     * @param record Record on which unit price should be set.
     * @param userData User data.
     * @return A boolean indicating success.
     */
    public boolean setItemUnitPrice(ItemUnitPriceInterface record, EMCUserData userData) {
        record.setUnitPrice(new BigDecimal(itemBean.getCostPrice(record.getItemId(), record.getDimension1(), record.getDimension2(), record.getDimension3(), userData)));
        return true;
    }

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
    public BigDecimal getLeadTime(String itemId, String dimension1, String dimension2, String dimension3, boolean produce, BigDecimal defaultLeadTime, EMCUserData userData) throws EMCEntityBeanException {
        return getLeadTime(itemId, dimension1, dimension2, dimension3, produce, defaultLeadTime, null, userData);
    }

    public BigDecimal getLeadTime(String itemId, String dimension1, String dimension2, String dimension3, boolean produce, BigDecimal defaultLeadTime, Connection conn, EMCUserData userData) throws EMCEntityBeanException {


        boolean checkItem = true;
        boolean checkDimensions = false;

        BigDecimal leadTime = null;


        if (checkDimensions) {
            //First check combinations for lead time
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class);
            query.addAnd("itemId", itemId);
            if (isBlank(dimension1)) {
                query.addAnd("dimension1Id", systemConstants.emcNA());
            } else {
                query.addAnd("dimension1Id", dimension1);
            }
            if (isBlank(dimension2)) {
                query.addAnd("dimension2Id", systemConstants.emcNA());
            } else {
                query.addAnd("dimension2Id", dimension2);
            }
            if (isBlank(dimension3)) {
                query.addAnd("dimension3Id", systemConstants.emcNA());
            } else {
                query.addAnd("dimension3Id", dimension3);
            }
            if (produce) {
                query.addField("productionLeadTime");
            } else {
                query.addField("purchaseLeadTime");
            }
            if (conn == null) {
                leadTime = (BigDecimal) util.executeSingleResultQuery(query, userData);
            } else {
                InventoryItemDimensionCombinations c = (InventoryItemDimensionCombinations) util.exJDBCSingleReadQuery(conn, query, userData);
                if (c != null) {
                    if (produce) {
                        leadTime = c.getProductionLeadTime();
                    } else {
                        leadTime = c.getPurchaseLeadTime();
                    }
                }
            }
            if (leadTime != null && leadTime.compareTo(BigDecimal.ZERO) != 0) {
                checkItem = false;
            }
        }

        if (checkItem) {
            //Get lead time from item
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
            query.addAnd("itemId", itemId);
            if (produce) {
                query.addField("productionLeadTime");
            } else {
                query.addField("purchaseLeadTime");
            }
            Double dLeadTime = null;
            if (conn == null) {
                dLeadTime = (Double) util.executeSingleResultQuery(query, userData);
            } else {
                InventoryItemMaster i = (InventoryItemMaster) util.exJDBCSingleReadQuery(conn, query, userData);
                if (i != null) {
                    if (produce) {
                        dLeadTime = i.getProductionLeadTime();
                    } else {
                        dLeadTime = i.getPurchaseLeadTime();
                    }
                }
            }
            leadTime = new BigDecimal(dLeadTime == null ? 0 : dLeadTime);
        }

        if (leadTime == null || leadTime.compareTo(BigDecimal.ZERO) == 0) {
            leadTime = defaultLeadTime; //No lead time found - use default.
        }
        return leadTime;
    }

    public BigDecimal getSafetyStock(Date requiredDate, String itemId, String dimension1, String dimension2, String dimension3, String serialNo, EMCUserData userData) {
        return safetyStockBean.getSafetyStock(requiredDate, itemId, dimension1, dimension2, dimension3, serialNo, userData);
    }

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
    public BigDecimal getEconomicOrderQuantity(String itemId, String dimension1, String dimension2, String dimension3, EMCUserData userData) {

        boolean checkItem = true;
        boolean checkDimensions = false;
        BigDecimal economicOrderQuantity = null;



        if (checkDimensions) {
            //First check combinations for economic order quantity
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class);
            query.addAnd("itemId", itemId);
            if (isBlank(dimension1)) {
                query.addAnd("dimension1Id", systemConstants.emcNA());
            } else {
                query.addAnd("dimension1Id", dimension1);
            }
            if (isBlank(dimension2)) {
                query.addAnd("dimension2Id", systemConstants.emcNA());
            } else {
                query.addAnd("dimension2Id", dimension2);
            }
            if (isBlank(dimension3)) {
                query.addAnd("dimension3Id", systemConstants.emcNA());
            } else {
                query.addAnd("dimension3Id", dimension3);
            }
            query.addField("economicOrderQuantity");

            economicOrderQuantity = (BigDecimal) util.executeSingleResultQuery(query, userData);
            if (economicOrderQuantity == null || economicOrderQuantity.compareTo(BigDecimal.ZERO) == 0) {
                checkItem = true;
            }
        }

        if (checkItem) {
            //Get economic order quantity from item
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
            query.addAnd("itemId", itemId);
            query.addField("economicOrderQuantity");

            economicOrderQuantity = (BigDecimal) util.executeSingleResultQuery(query, userData);
        }

        return economicOrderQuantity == null ? BigDecimal.ZERO : economicOrderQuantity;
    }

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
    public BigDecimal[] getMinMaxMult(String itemId, String dimension1, String dimension2, String dimension3, EMCUserData userData) {

        boolean checkItem = false;
        boolean checkDimensions = false;
        Object[] minMaxMult = null;
        boolean valuesFound = false;



        if (checkDimensions) {
            //First check combinations for min/max/mult
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class);
            query.addAnd("itemId", itemId);
            if (isBlank(dimension1)) {
                query.addAnd("dimension1Id", systemConstants.emcNA());
            } else {
                query.addAnd("dimension1Id", dimension1);
            }
            if (isBlank(dimension2)) {
                query.addAnd("dimension2Id", systemConstants.emcNA());
            } else {
                query.addAnd("dimension2Id", dimension2);
            }
            if (isBlank(dimension3)) {
                query.addAnd("dimension3Id", systemConstants.emcNA());
            } else {
                query.addAnd("dimension3Id", dimension3);
            }
            query.addField("minOrderQty");
            query.addField("maxOrderQty");
            query.addField("multOrderQty");

            minMaxMult = (Object[]) util.executeSingleResultQuery(query, userData);

            valuesFound = (minMaxMult != null && ((minMaxMult[0] != null && BigDecimal.ZERO.compareTo((BigDecimal) minMaxMult[0]) < 0) ||
                    (minMaxMult[1] != null && (minMaxMult[1] != null && BigDecimal.ZERO.compareTo((BigDecimal) minMaxMult[1]) < 0) ||
                    (minMaxMult[2] != null && (minMaxMult[2] != null && BigDecimal.ZERO.compareTo((BigDecimal) minMaxMult[2]) < 0)))));

            if (!valuesFound) {
                checkItem = true;
            }
        }
        if (checkItem) {
            //Get min/max/mult
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
            query.addAnd("itemId", itemId);
            query.addField("minOrderQty");
            query.addField("maxOrderQty");
            query.addField("multOrderQty");

            minMaxMult = (Object[]) util.executeSingleResultQuery(query, userData);

            valuesFound = (minMaxMult != null && ((minMaxMult[0] != null && util.compareDouble((Double) minMaxMult[0], 0) > 0) ||
                    (minMaxMult[1] != null && (minMaxMult[1] != null && util.compareDouble((Double) minMaxMult[1], 0) > 0)) ||
                    (minMaxMult[2] != null && (minMaxMult[2] != null && util.compareDouble((Double) minMaxMult[2], 0) > 0))));

            //Convert values from double to BigDecimal
            if (valuesFound) {
                minMaxMult[0] = new BigDecimal(minMaxMult[0] == null ? 0 : (Double) minMaxMult[0]);
                minMaxMult[1] = new BigDecimal(minMaxMult[1] == null ? 0 : (Double) minMaxMult[1]);
                minMaxMult[2] = new BigDecimal(minMaxMult[2] == null ? 0 : (Double) minMaxMult[2]);
            }
        }
        return valuesFound ? new BigDecimal[]{(BigDecimal) minMaxMult[0], (BigDecimal) minMaxMult[1], (BigDecimal) minMaxMult[2]} : new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
    }

    public String[] getStopPurchasingStatus(String itemId, String dimension1, String dimension2, String dimension3, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
        query.addAnd("itemId", itemId);
        query.addField("stopPurchase");
        query.addField("overseePurchaseGroup");
        Object[] itemData = (Object[]) util.executeSingleResultQuery(query, userData);
        if (itemData != null && itemData.length == 2) {
            if (!isBlank(itemData[0]) && !InventoryStopPurchasingType.NO.toString().equals(itemData[0])) {
                return new String[]{(String) itemData[0], (String) itemData[1]};
            }
        }

        query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class);
        query.addAnd("itemId", itemId);
        if (!isBlank(dimension1)) {
            query.addAnd("dimension1Id", dimension1);
        }
        if (!isBlank(dimension2)) {
            query.addAnd("dimension2Id", dimension2);
        }
        if (!isBlank(dimension3)) {
            query.addAnd("dimension3Id", dimension3);
        }
        query.addField("stopPurchase");
        query.addField("overseePurchaseGroup");
        Object[] comData = (Object[]) util.executeSingleResultQuery(query, userData);
        if (comData != null && comData.length == 2) {
            if (!isBlank(comData[0]) && !InventoryStopPurchasingType.NO.toString().equals(comData[0])) {
                return new String[]{(String) comData[0], (String) comData[1]};
            }
        }

        return new String[]{InventoryStopPurchasingType.NO.toString(), null};
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.logic;

import emc.entity.base.BaseUOMConversionTable;
import emc.entity.base.BaseUOMDetailedConversionTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.enums.enumQueryTypes;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class BaseUOMLogicBean extends EMCBusinessBean implements BaseUOMLogicLocal {

    /**
     * Converts a value in a given UOM to another UOM.
     * Should fromUOM = toUOM method will return value
     * @param fromUOM
     * @param toUOM
     * @param value
     * @param userData
     * @return
     * @throws emc.bus.base.logic.EMCUOMConversionException
     */
    @Override
    public double convertFromUOMToUOM(String item, String fromUOM, String toUOM, double value, EMCUserData userData) throws EMCUOMConversionException {
        if (fromUOM.equalsIgnoreCase(toUOM)) {
            return value;
        }
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseUOMConversionTable.class);
        query.addAnd("unit", fromUOM);
        query.addAnd("baseUnit", toUOM);
        BaseUOMConversionTable conversionRecord = (BaseUOMConversionTable) util.executeSingleResultQuery(query, userData);

        if (conversionRecord == null) {
            throw new EMCUOMConversionException("No conversion found from " + fromUOM + " to " + toUOM);
        } else {
            BaseUOMDetailedConversionTable detailedConversionRecord = null;
            if (!isBlank(item)) {
                query = new EMCQuery(enumQueryTypes.SELECT, BaseUOMDetailedConversionTable.class);
                query.addAnd("masterRecordID", conversionRecord.getRecordID());
                query.addAnd("itemId", item);
                detailedConversionRecord = (BaseUOMDetailedConversionTable) util.executeSingleResultQuery(query, userData);
            }

            if (detailedConversionRecord == null) {
                return value * conversionRecord.getConversion();
            } else {
                return value * detailedConversionRecord.getConversion();
            }
        }
    }

    /**
     * Converts a value in a given UOM to another UOM.
     * @param fromUOM UOM to convert from.
     * @param toUOM UOM to convert to.
     * @param value Value to convert.
     * @param userData User data.
     * @return The specified value in toUOM.
     * @throws emc.bus.base.logic.EMCUOMConversionException
     */
    @Override
    public BigDecimal convertFromUOMToUOM(String item, String fromUOM, String toUOM, BigDecimal value, EMCUserData userData) throws EMCUOMConversionException {
        if (fromUOM.equalsIgnoreCase(toUOM)) {
            return value;
        }
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, "BaseUOMConversionTable");
        query.addAnd("unit", fromUOM);
        query.addAnd("baseUnit", toUOM);
        BaseUOMConversionTable conversionRecord = (BaseUOMConversionTable) util.executeSingleResultQuery(query, userData);

        if (conversionRecord == null) {
            throw new EMCUOMConversionException("No conversion found from " + fromUOM + " to " + toUOM);
        } else {
            BaseUOMDetailedConversionTable detailedConversionRecord = null;
            if (!isBlank(item)) {
                query = new EMCQuery(enumQueryTypes.SELECT, BaseUOMDetailedConversionTable.class);
                query.addAnd("masterRecordID", conversionRecord.getRecordID());
                query.addAnd("itemId", item);
                detailedConversionRecord = (BaseUOMDetailedConversionTable) util.executeSingleResultQuery(query, userData);
            }

            if (detailedConversionRecord == null) {
                return value.multiply(new BigDecimal(conversionRecord.getConversion()));
            } else {
                return value.multiply(new BigDecimal(detailedConversionRecord.getConversion()));
            }
        }
    }

    /**
     * Takes an item and converts to the base unit of measure
     * @param item
     * @param qty
     * @param unitOfMeasure
     * @param userData
     * @return qty in item base unit of measure
     * @throws emc.bus.base.logic.EMCUOMConversionException
     */
    @Override
    public double convertToItemBase(String item, double qty, String unitOfMeasure, EMCUserData userData) throws EMCUOMConversionException {
        //find item
        double ret = 0;
        EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
        qu.addAnd("itemId", item);
        InventoryItemMaster itemMast = (InventoryItemMaster) util.executeSingleResultQuery(qu, userData);
        if (itemMast != null) {
            ret = convertFromUOMToUOM(item, unitOfMeasure, itemMast.getBaseUOM(), qty, userData);
        } else {
            throw new EMCUOMConversionException("Item not found for conversion:" + item);
        }
        return ret;
    }

    /**
     * Takes an item and a quantity and converts the quantity to the item base unit of measure.
     * @param item Item id.
     * @param qty Quantity.
     * @param unitOfMeasure Unit of measure.
     * @param userData User data.
     * @return Quantity in item base unit of measure
     * @throws emc.bus.base.logic.EMCUOMConversionException
     */
    @Override
    public BigDecimal convertToItemBase(String item, BigDecimal quantity, String unitOfMeasure, EMCUserData userData) throws EMCUOMConversionException {
        //find item
        BigDecimal ret = BigDecimal.ZERO;
        EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
        qu.addAnd("itemId", item);
        InventoryItemMaster itemMast = (InventoryItemMaster) util.executeSingleResultQuery(qu, userData);
        if (itemMast != null) {
            ret = convertFromUOMToUOM(item, unitOfMeasure, itemMast.getBaseUOM(), quantity, userData);
        } else {
            throw new EMCUOMConversionException("Item not found for conversion:" + item);
        }
        return ret;
    }
}

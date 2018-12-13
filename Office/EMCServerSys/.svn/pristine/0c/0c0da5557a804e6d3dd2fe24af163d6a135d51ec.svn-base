/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.logic;

import emc.framework.EMCUserData;
import java.math.BigDecimal;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface BaseUOMLogicLocal {

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
    public double convertFromUOMToUOM(String item, String fromUOM, String toUOM, double value, EMCUserData userData) throws EMCUOMConversionException;

    /**
     * Takes an item and converts to the base unit of measure
     * @param item
     * @param qty
     * @param unitOfMeasure
     * @param userData
     * @return qty in item base unit of measure
     * @throws emc.bus.base.logic.EMCUOMConversionException
     */
    public double convertToItemBase(java.lang.String item, double qty, java.lang.String unitOfMeasure, emc.framework.EMCUserData userData) throws emc.bus.base.logic.EMCUOMConversionException;

    /**
     * Takes an item and a quantity and converts the quantity to the item base unit of measure.
     * @param item Item id.
     * @param qty Quantity.
     * @param unitOfMeasure Unit of measure.
     * @param userData User data.
     * @return Quantity in item base unit of measure
     * @throws emc.bus.base.logic.EMCUOMConversionException
     */
    public BigDecimal convertToItemBase(String item, BigDecimal quantity, String unitOfMeasure, EMCUserData userData) throws EMCUOMConversionException;

    /**
     * Converts a value in a given UOM to another UOM.
     * @param fromUOM UOM to convert from.
     * @param toUOM UOM to convert to.
     * @param value Value to convert.
     * @param userData User data.
     * @return The specified value in toUOM.
     * @throws emc.bus.base.logic.EMCUOMConversionException
     */
    public BigDecimal convertFromUOMToUOM(String item, String fromUOM, String toUOM, BigDecimal value, EMCUserData userData) throws EMCUOMConversionException;
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory.dimensions.additionaldimensions;

import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.entity.base.BaseUnitsOfMeasure;
import emc.entity.inventory.dimensions.additionaldimensions.InventoryAdditionalDimensions;
import emc.enums.base.uom.UOMTypes;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @Name         : InventoryAdditionalDimensionsBean
 *
 * @Date         : 08 Jun 2009
 * 
 * @Description  : Entity bean for the Inventory Additional Dimensions entity class.
 *
 * @author       : riaan
 */
@Stateless
public class InventoryAdditionalDimensionsBean extends EMCEntityBean implements InventoryAdditionalDimensionsLocal {

    @EJB
    private InventoryDimensionTableLocal dimTableBean;
    
    /** Creates a new instance of InventoryAdditionalDimensionsBean. */
    public InventoryAdditionalDimensionsBean() {

    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object ret = super.validateField(fieldNameToValidate, theRecord, userData);
        boolean valid = (Boolean)ret;
        
        InventoryAdditionalDimensions record = (InventoryAdditionalDimensions)theRecord;
        
        if (fieldNameToValidate.equals("widthUOM")) {
            if (record.getWidth() == 0) {
                Logger.getLogger("emc").log(Level.SEVERE, "May not enter a width UOM when width is zero.", userData);
                return false;
            } else {
                //Check that UoM is valid
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseUnitsOfMeasure.class.getName());
                query.addAnd("unit", record.getWidthUOM());
                query.addAnd("type", UOMTypes.LENGTH);
                
                if (util.executeSingleResultQuery(query, userData) == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "UOM must be a valid unit of measure of the " + UOMTypes.LENGTH + " type.", userData);
                    return false;
                }
            }
        }
        
        return valid;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);
        
        InventoryAdditionalDimensions additionalDimensions = (InventoryAdditionalDimensions)vobject;
        
        ret = ret && validateWidth(additionalDimensions, userData);
                
        return ret;
    }
 
    /**
     * Returns an Additional Dimensions record.
     * @param itemId Item id of the Additional Dimensions record to retrieve.
     * @param dimId  Dimension id containing the dimensions of the record to retrieve.
     * @param userData User data.
     * @return An InventoryAdditionalDimensions record, or null if no record is found.
     */
    public InventoryAdditionalDimensions getAdditionalDimensions(String itemId, long dimId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryAdditionalDimensions.class.getName());
        query.addAnd("dimensionId", dimId);
        query.addAnd("itemId", itemId);
        
        return (InventoryAdditionalDimensions)util.executeSingleResultQuery(query, userData);
    }
    
    /**
     * Returns an Additional Dimensions record.
     * @param itemId Item id to find dimensions for.
     * @param dimension1 Item dimension 1.
     * @param dimension2 Item dimension 2.
     * @param dimension3 Item dimension 3.
     * @param batch Item batch.
     * @param serial Item serial.
     * @param userData User data.
     * @return An InventoryAdditionalDimensions record, or null if no record is found.
     */
    public InventoryAdditionalDimensions getAdditionalDimensions(String itemId, String dimension1, String dimension2, String dimension3, String batch, String serial, EMCUserData userData) {
        long dimRecordId = dimTableBean.getDimRecordId(batch, dimension1, dimension2, dimension3, null, null, null, serial, userData);
        
        return getAdditionalDimensions(itemId, dimRecordId, userData);
    }
    
    /**
     * This method checks whether the width specified on an InventoryAdditionalDimensions record is valid.
     * @param serialBatch Record to check.
     * @param userData Userdata.
     * @return A boolean indicating whether the width specified on the given record is valid.
     */
    private boolean validateWidth(InventoryAdditionalDimensions additionalDimensions, EMCUserData userData) {
        if (additionalDimensions.getWidth() != 0 && isBlank(additionalDimensions.getWidthUOM())) {
            Logger.getLogger("emc").log(Level.SEVERE, "Please specify a width UOM", userData);
            return false;
        } else if (!isBlank(additionalDimensions.getWidthUOM()) && additionalDimensions.getWidth() == 0) {
            Logger.getLogger("emc").log(Level.SEVERE, "You may not specify a width UOM without a width", userData);
            return false;
        } else {
            return true;
        }
    }
}

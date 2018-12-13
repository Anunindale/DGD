/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory;

import emc.bus.inventory.logic.InventoryItemDimensionCombinationLogicLocal;
import emc.entity.inventory.InventoryBarcodes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryBarcodeBean extends EMCEntityBean implements InventoryBarcodeLocal {
 
    @EJB
    private InventoryItemDimensionCombinationLogicLocal dimValidateBean;

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        InventoryBarcodes record = (InventoryBarcodes) theRecord;
        if (ret) {
            if ((fieldNameToValidate.equals("dimension1") && !isBlank(record.getDimension1())) ||
                    (fieldNameToValidate.equals("dimension2") && !isBlank(record.getDimension2())) ||
                    (fieldNameToValidate.equals("dimension3") && !isBlank(record.getDimension3()))) {
                ret = dimValidateBean.validateDimensionValues(record.getItemId(), record.getDimension1(), record.getDimension2(), record.getDimension3(), userData);
            }
        }
        return ret;
    }
}

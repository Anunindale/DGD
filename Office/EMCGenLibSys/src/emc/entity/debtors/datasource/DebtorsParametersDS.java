/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.journals.stocktakecapture.ItemReferenceDS;
import emc.entity.debtors.DebtorsParameters;
import java.util.HashMap;

/**
 * @description : Data source for DebtorsParameters.
 *
 * @date        : 23 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class DebtorsParametersDS extends DebtorsParameters {

    private String handlingChargeItemRef;
    private String deliveryChargeItemRef;
    private String defaultItemRef;
    
    /** Creates a new instance of DebtorsParametersDS */
    public DebtorsParametersDS() {
        this.setDataSource(true);
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("handlingChargeItemRef", new ItemReferenceDS());
        toBuild.put("deliveryChargeItemRef", new ItemReferenceDS());
        toBuild.put("defaultItemRef", new ItemReferenceDS());

        return toBuild;
    }

    public String getHandlingChargeItemRef() {
        return handlingChargeItemRef;
    }

    public void setHandlingChargeItemRef(String handlingChargeItemRef) {
        this.handlingChargeItemRef = handlingChargeItemRef;
    }

    public String getDeliveryChargeItemRef() {
        return deliveryChargeItemRef;
    }

    public void setDeliveryChargeItemRef(String deliveryChargeItemRef) {
        this.deliveryChargeItemRef = deliveryChargeItemRef;
    }

    public String getDefaultItemRef() {
        return defaultItemRef;
    }

    public void setDefaultItemRef(String defaultItemRef) {
        this.defaultItemRef = defaultItemRef;
    }

}

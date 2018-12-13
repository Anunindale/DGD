/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.salesordermaster.CustomerName;
import emc.entity.sop.SOPSalesOrderMaster;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class SOPSalesOrderMasterDS extends SOPSalesOrderMaster {

    private String customerName;
    private String deliveryRules;

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("customerName", new CustomerName());
        return toBuild;
    }

    public SOPSalesOrderMasterDS() {
        this.setDataSource(true);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeliveryRules() {
        return deliveryRules;
    }

    public void setDeliveryRules(String deliveryRules) {
        this.deliveryRules = deliveryRules;
    }
}

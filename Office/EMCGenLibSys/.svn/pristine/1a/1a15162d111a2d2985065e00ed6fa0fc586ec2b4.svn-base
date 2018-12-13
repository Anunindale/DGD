/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.systemwide.Name;
import emc.entity.sop.SOPSalesOrderPostMaster;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class SOPSalesOrderPostMasterDS extends SOPSalesOrderPostMaster {

    private String customerName;

    public SOPSalesOrderPostMasterDS() {
        this.setDataSource(true);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("customerName", new Name());
        return toBuild;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors.allocationimport;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.customers.foreignkeys.CustomerIdFK;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "DebtorsAllocationImportSetupMaster")
public class DebtorsAllocationImportSetupMaster extends EMCEntityClass {

    private String customerId;
    private String allocationField;

    /** Creates a new instance of DebtorsAllocationImportSetupMaster. */
    public DebtorsAllocationImportSetupMaster() {
        
    }

    public String getAllocationField() {
        return allocationField;
    }

    public void setAllocationField(String allocationField) {
        this.allocationField = allocationField;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("customerId", new CustomerIdFK());

        return toBuild;
    }

}

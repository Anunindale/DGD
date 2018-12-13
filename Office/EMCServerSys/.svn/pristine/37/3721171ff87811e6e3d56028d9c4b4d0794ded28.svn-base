/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.allocationimport;

import emc.entity.debtors.allocationimport.DebtorsAllocationImportSetupLines;
import emc.entity.debtors.allocationimport.DebtorsAllocationImportSetupMaster;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class DebtorsAllocationImportSetupMasterBean extends EMCEntityBean implements DebtorsAllocationImportSetupMasterLocal {

    /** Creates a new instance of DebtorsAllocationImportSetupMasterBean. */
    public DebtorsAllocationImportSetupMasterBean() {
        
    }

    /**
     * Returns the field mappings that have set up for the specified customer.
     * @param customerId Customer id.
     * @param userData User data.
     * @return The field mappings that have been set up for the specified customer.
     */
    public Map<String, List<DebtorsAllocationImportSetupLines>> getCustomerMapping(String customerId, EMCUserData userData) {
        Map<String, List<DebtorsAllocationImportSetupLines>> mapping = new HashMap<String, List<DebtorsAllocationImportSetupLines>>();
        
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsAllocationImportSetupMaster.class);
        query.addAnd("customerId", customerId);

        List<DebtorsAllocationImportSetupMaster> allocationMasters = (List<DebtorsAllocationImportSetupMaster>)util.executeGeneralSelectQuery(query, userData);

        for (DebtorsAllocationImportSetupMaster master : allocationMasters) {
            query = new EMCQuery(enumQueryTypes.SELECT, DebtorsAllocationImportSetupLines.class);
            query.addAnd("masterID", master.getRecordID());

            mapping.put(master.getAllocationField(), (List<DebtorsAllocationImportSetupLines>)util.executeGeneralSelectQuery(query, userData));
        }

        return mapping;
    }
}

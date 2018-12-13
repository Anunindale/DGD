/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.repcommissionenquiry;

import emc.datatypes.EMCString;
import emc.datatypes.datasources.DSRelation;
import emc.entity.sop.SOPSalesRepCommission;

/**
 *
 * @author wikus
 */
public class CustomerItemField3DS extends EMCString {

    public CustomerItemField3DS() {
        DSRelation relation = new DSRelation();
        relation.setSourceTable(SOPSalesRepCommission.class.getName());
        relation.setRelatedTable(SOPSalesRepCommission.class.getName());
        relation.setRelatedField("customerItemField3");
        relation.setPKField("customerItemField3");
        relation.setFKField("customerItemField3");
        this.setDsRelation(relation);
    }
}

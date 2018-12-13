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
public class CustomerItemField6DS extends EMCString {

    public CustomerItemField6DS() {
        DSRelation relation = new DSRelation();
        relation.setSourceTable(SOPSalesRepCommission.class.getName());
        relation.setRelatedTable(SOPSalesRepCommission.class.getName());
        relation.setRelatedField("customerItemField6");
        relation.setPKField("customerItemField6");
        relation.setFKField("customerItemField6");
        this.setDsRelation(relation);
    }
}

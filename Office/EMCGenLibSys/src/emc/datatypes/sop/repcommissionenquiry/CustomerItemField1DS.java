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
public class CustomerItemField1DS extends EMCString {

    public CustomerItemField1DS() {
        DSRelation relation = new DSRelation();
        relation.setSourceTable(SOPSalesRepCommission.class.getName());
        relation.setRelatedTable(SOPSalesRepCommission.class.getName());
        relation.setRelatedField("customerItemField1");
        relation.setPKField("customerItemField1");
        relation.setFKField("customerItemField1");
        this.setDsRelation(relation);
    }
}

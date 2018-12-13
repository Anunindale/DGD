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
public class CustomerItemField4DS extends EMCString {

    public CustomerItemField4DS() {
        DSRelation relation = new DSRelation();
        relation.setSourceTable(SOPSalesRepCommission.class.getName());
        relation.setRelatedTable(SOPSalesRepCommission.class.getName());
        relation.setRelatedField("customerItemField4");
        relation.setPKField("customerItemField4");
        relation.setFKField("customerItemField4");
        this.setDsRelation(relation);
    }
}

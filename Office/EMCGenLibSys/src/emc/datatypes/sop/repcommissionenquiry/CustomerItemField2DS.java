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
public class CustomerItemField2DS extends EMCString {

    public CustomerItemField2DS() {
        DSRelation relation = new DSRelation();
        relation.setSourceTable(SOPSalesRepCommission.class.getName());
        relation.setRelatedTable(SOPSalesRepCommission.class.getName());
        relation.setRelatedField("customerItemField2");
        relation.setPKField("customerItemField2");
        relation.setFKField("customerItemField2");
        this.setDsRelation(relation);
    }
}

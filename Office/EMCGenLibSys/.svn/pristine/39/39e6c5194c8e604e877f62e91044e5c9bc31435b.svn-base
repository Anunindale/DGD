/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.repcommissionenquiry;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.sop.repgroupsetup.RepId;
import emc.entity.sop.SOPSalesRepCommission;

/**
 *
 * @author wikus
 */
public class RepIdDS extends RepId {

    public RepIdDS() {
        DSRelation relation = new DSRelation();
        relation.setSourceTable(SOPSalesRepCommission.class.getName());
        relation.setRelatedTable(SOPSalesRepCommission.class.getName());
        relation.setRelatedField("repId");
        relation.setPKField("repId");
        relation.setFKField("repId");
        this.setDsRelation(relation);
    }

}

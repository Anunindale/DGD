/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.repcommissionenquiry;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.sop.repgroups.RepGroupId;
import emc.entity.sop.SOPSalesRepCommission;
import emc.entity.sop.SOPSalesRepGroups;

/**
 *
 * @author wikus
 */
public class RepGroupDS extends RepGroupId{

    public RepGroupDS() {
        DSRelation relation = new DSRelation();
        relation.setSourceTable(SOPSalesRepGroups.class.getName());
        relation.setRelatedTable(SOPSalesRepGroups.class.getName());
        relation.setRelatedField("repGroupId");
        relation.setPKField("repGroupId");
        relation.setFKField("repGroupId");
        this.setDsRelation(relation);
    }



}

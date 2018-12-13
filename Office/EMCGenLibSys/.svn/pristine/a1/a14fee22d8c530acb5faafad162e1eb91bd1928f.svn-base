/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.repcommissionenquiry;

import emc.datatypes.datasources.DSRelation;
import emc.entity.sop.SOPSalesRepGroups;

/**
 *
 * @author wikus
 */
public class RepGroupManagerDS extends emc.datatypes.sop.repgroups.RepGroupManager {

    public RepGroupManagerDS() {
        DSRelation relation = new DSRelation();
        relation.setSourceTable(SOPSalesRepGroups.class.getName());
        relation.setRelatedTable(SOPSalesRepGroups.class.getName());
        relation.setRelatedField("groupManager");
        relation.setPKField("groupManager");
        relation.setFKField("groupManager");
        this.setDsRelation(relation);
    }
}

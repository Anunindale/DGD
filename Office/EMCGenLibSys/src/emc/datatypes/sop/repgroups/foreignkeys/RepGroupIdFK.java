/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.repgroups.foreignkeys;

import emc.datatypes.sop.repgroups.RepGroupId;
import emc.entity.sop.SOPSalesRepGroups;

/**
 *
 * @author wikus
 */
public class RepGroupIdFK extends RepGroupId {

    public RepGroupIdFK() {
        this.setRelatedTable(SOPSalesRepGroups.class.getName());
        this.setRelatedField("repGroupId");
    }
}

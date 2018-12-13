/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.repcommission;

import emc.datatypes.EMCString;
import emc.entity.sop.SOPSalesRepGroups;

/**
 *
 * @author wikus
 */
public class RepIdFK extends EMCString {

    public RepIdFK() {
        this.setEmcLabel("Sales Rep");
        this.setRelatedTable(SOPSalesRepGroups.class.getName());
        this.setRelatedField("groupManager");
        this.setMandatory(true);

    }
}

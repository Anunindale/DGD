/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.costinggroups;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class CostingGroup extends EMCString{

    public CostingGroup() {
        this.setEmcLabel("Group");
        this.setMandatory(true);
    }

}

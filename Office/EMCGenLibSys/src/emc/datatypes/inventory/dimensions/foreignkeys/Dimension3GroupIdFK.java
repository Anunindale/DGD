/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.dimensions.foreignkeys;

import emc.datatypes.inventory.dimension3groups.Dimension3GroupId;
import emc.entity.inventory.dimensions.InventoryDimension3Group;

/**
 *
 * @author riaan
 */
public class Dimension3GroupIdFK extends Dimension3GroupId {

    /** Creates a new instance of Dimension3GroupIdFK */
    public Dimension3GroupIdFK() {
        this.setMandatory(false);
        this.setRelatedField("dimensionGroupId");
        this.setRelatedTable(InventoryDimension3Group.class.getName());
    }
}

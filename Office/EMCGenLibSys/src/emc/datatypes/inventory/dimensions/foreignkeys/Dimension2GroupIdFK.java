/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.dimensions.foreignkeys;

import emc.datatypes.inventory.dimension2groups.Dimension2GroupId;
import emc.entity.inventory.dimensions.InventoryDimension2Group;

/**
 *
 * @author riaan
 */
public class Dimension2GroupIdFK extends Dimension2GroupId {

    /** Creates a new instance of Dimension2GroupIdFK */
    public Dimension2GroupIdFK() {
        this.setMandatory(false);
        this.setRelatedField("dimensionGroupId");
        this.setRelatedTable(InventoryDimension2Group.class.getName());
    }
}


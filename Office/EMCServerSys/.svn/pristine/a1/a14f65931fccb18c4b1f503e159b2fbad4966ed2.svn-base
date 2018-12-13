/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions;

import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryDimension2Bean extends EMCEntityBean implements InventoryDimension2Local {

    /** Creates a new instance of InventoryDimension2Bean */
    public InventoryDimension2Bean() {

    }

    public String findDimensionDescription(String dimension2, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2.class.getName());
        query.addAnd("dimensionId", dimension2);
        InventoryDimension2 dim = (InventoryDimension2) util.executeSingleResultQuery(query, userData);
        if (dim != null) {
            return dim.getDescription();
        } else return null;
    }

    public InventoryDimension2 findDimension2(String dimension2, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2.class);
        query.addAnd("dimensionId", dimension2);
        return (InventoryDimension2) util.executeSingleResultQuery(query, userData);
    }
}

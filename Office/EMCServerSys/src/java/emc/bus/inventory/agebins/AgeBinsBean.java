/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory.agebins;

import emc.entity.inventory.agebins.InventoryAgeBins;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author rico
 */
@Stateless
public class AgeBinsBean extends EMCEntityBean implements AgeBinsBeansLocal {

    /** Creates a new instance of AgeBinsBean. */
    public AgeBinsBean() {
        
    }

    /**
     * Returns a List containing the "binPrintDesc" values of all records in the InventoryAgeBins, sorted by their "binOrder" field.
     * @param userData User data.
     * @return A List containing the "binPrintDesc" values of all records in the InventoryAgeBins, sorted by their "binOrder" field.
     */
    public List<String> getBinLabels(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryAgeBins.class.getName());
        query.addField("ageBinPrintDesc");
        query.addOrderBy("binOrder");

        return (List<String>)util.executeGeneralSelectQuery(query, userData);
    }
}

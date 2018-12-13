/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.colourdesignmaster;

import emc.entity.inventory.colourdisignmaster.InventoryColourDesignMaster;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryColourDesignMasterBean extends EMCEntityBean implements InventoryColourDisignMasterLocal {

    public InventoryColourDesignMasterBean() {

    }

    public InventoryColourDesignMaster findDesignMaster(String designNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryColourDesignMaster.class);
        query.addAnd("designNo", designNumber);
        return  (InventoryColourDesignMaster) util.executeSingleResultQuery(query, userData);
    }
}

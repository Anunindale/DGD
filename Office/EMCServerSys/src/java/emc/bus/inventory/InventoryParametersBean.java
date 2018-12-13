/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory;

import emc.entity.inventory.InventoryParameters;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryParametersBean extends EMCEntityBean implements InventoryParametersLocal{

    public InventoryParametersBean() {
    }

    public InventoryParameters getInventoryParameters(EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryParameters.class.getName());
        InventoryParameters param = (InventoryParameters)util.executeSingleResultQuery(query, userData);
        if (param == null) {
            param = new InventoryParameters();
            this.insert(param, userData);
            logMessage(Level.SEVERE, "No Inventory Parameters found, new set created.", userData);
        }
        return param;
    }

}

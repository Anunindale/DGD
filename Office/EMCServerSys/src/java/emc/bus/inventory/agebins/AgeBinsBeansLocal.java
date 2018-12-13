/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory.agebins;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author rico
 */
@Local
public interface AgeBinsBeansLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns a List containing the "binPrintDesc" values of all records in the InventoryAgeBins, sorted by their "binOrder" field.
     * @param userData User data.
     * @return A List containing the "binPrintDesc" values of all records in the InventoryAgeBins, sorted by their "binOrder" field.
     */
    public java.util.List<java.lang.String> getBinLabels(emc.framework.EMCUserData userData);

}

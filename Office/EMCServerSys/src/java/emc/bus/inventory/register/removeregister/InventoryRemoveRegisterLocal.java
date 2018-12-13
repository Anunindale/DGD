/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.register.removeregister;

import emc.bus.inventory.register.superregister.InventoryRegisterSuperLocal;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface InventoryRemoveRegisterLocal extends InventoryRegisterSuperLocal {

    /**
     * Returns a boolean indicating whether anything has been registered against the specified reference.
     * @param masterId Master id.
     * @param transId Trans id.
     * @param userData User data.
     * @return A boolean indicating whether any registration lines exist.
     */
    public boolean checkRegistrationExists(String masterId, String transId, EMCUserData userData);

    public java.lang.Object updateWithoutTx(java.lang.Object uobject, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public java.lang.Object deleteWithoutTx(java.lang.Object dobject, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}

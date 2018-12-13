/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author riaan
 */
@Local
public interface BaseUnitsOfMeasureLocal extends EMCEntityBeanLocalInterface {

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void generateSystem(EMCUserData userData);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface POPDeliveryModesLocal extends EMCEntityBeanLocalInterface {

    public java.lang.String findDescription(java.lang.String deliveryMethod, emc.framework.EMCUserData userData);
}

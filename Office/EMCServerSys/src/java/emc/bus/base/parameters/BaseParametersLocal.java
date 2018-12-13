/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.parameters;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface BaseParametersLocal extends EMCEntityBeanLocalInterface {

    public emc.entity.base.BaseParameters getBaseParameters(emc.framework.EMCUserData userData);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.parameters;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface SOPParametersLocal extends EMCEntityBeanLocalInterface {

    public emc.entity.sop.SOPParameters getParameterRecord(emc.framework.EMCUserData userData);

    public boolean deactivateCommissionFields(EMCUserData userData);
}

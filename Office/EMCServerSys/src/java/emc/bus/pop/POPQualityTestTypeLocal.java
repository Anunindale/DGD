/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface POPQualityTestTypeLocal extends EMCEntityBeanLocalInterface {

    public String findDesc(String qualityTestId, EMCUserData userData);
}

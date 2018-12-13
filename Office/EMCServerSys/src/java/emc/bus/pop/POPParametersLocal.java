/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface POPParametersLocal extends EMCEntityBeanLocalInterface {

    public boolean getDisplaySupperRef(emc.framework.EMCUserData userData);

    public boolean serialMoreThanOne(emc.framework.EMCUserData userData);

    public emc.entity.pop.POPParameters getPOPParameters(emc.framework.EMCUserData userData);

    public java.util.List<java.lang.String> getPlannedReleaseItemFields(emc.framework.EMCUserData userData);
}

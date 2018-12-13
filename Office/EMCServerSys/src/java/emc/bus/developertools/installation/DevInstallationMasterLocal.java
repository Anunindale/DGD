/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools.installation;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface DevInstallationMasterLocal extends EMCEntityBeanLocalInterface {

    public java.util.List<java.lang.String> getInstallationNames(emc.framework.EMCUserData userData);

    public java.util.List<java.lang.Object[]> getInstallationProperties(java.lang.String installationName, emc.framework.EMCUserData userData);

    public void saveInstallationProperties(java.lang.String installationName, java.util.Map<java.lang.String, java.lang.String> propetiesToSave, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}

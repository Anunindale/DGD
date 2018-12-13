/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface BaseFilePathsLocal extends EMCEntityBeanLocalInterface {

    /**
     * Fetches and returns the system file path (which should point to a file server) for the OS specified in userData.
     * @param operatingSystemStr Java String identifier for an operating system.
     * @param userData User data.  This specifies the OS of the client application which sent the request.
     * @return A system file path.
     */
    public String getOSFilePath(String operatingSystemStr, EMCUserData userData);

    public emc.entity.base.BaseFilePaths getModuleFilePath(java.lang.String moduleId, emc.framework.EMCUserData userData);
}

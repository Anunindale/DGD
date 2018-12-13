/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.base.fileassociations;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface BaseFileAssociationsLocal extends EMCEntityBeanLocalInterface {

    /**
     * Gets file associations for the specified user.
     * @param userId User id for which associations should be retrieved.
     * @param operatingSystem Operating system for which associations should be retrieved.
     * @param userData User data.
     * @return A map containing file extensions as keys and the commands used to open these extensions as values.
     */
    public Map<String, String> getFileAssociations(String userId, String operatingSystem, EMCUserData userData);
}

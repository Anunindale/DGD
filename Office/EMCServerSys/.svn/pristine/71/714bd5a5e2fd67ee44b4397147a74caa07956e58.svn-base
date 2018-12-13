/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.permissions;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface BasePermissionsTableLocal extends EMCEntityBeanLocalInterface {

    public emc.entity.base.permissions.BasePermissionsTable getUserPermissions(java.lang.String userId, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    /**
     * Returns a List containing the class names of all menu items that the
     * specified user has access to.
     *
     * @param userId ID of user for whom permissions should be fetched.
     * @param userData User data.
     * @return A list of menu items that the specified has access to
     */
    public List<String> getUserMenuItems(String userId, EMCUserData userData);
}

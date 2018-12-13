/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.permissions;

import emc.entity.base.permissions.BaseUserPermissionsTable;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface BaseUserPermissionsTableLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns a List of Permission objects for the given user form the given form.  Returns all permissions for user if formClassName is null or blank. 
     * @param formClassName Class name of form to retrieve permissions for, or null/empty String if all permissions should be returned.
     * @param userData User data containing username of user to retrieve permissions for.
     * @return A List of BaseUserPermissionsTable object containing the requested permissions.
     */
    public java.util.List<emc.entity.base.permissions.BaseUserPermissionsTable> getPermissionsForUser(java.lang.String formClassName, emc.framework.EMCUserData userData);

    /**
     * Copies permissions from one user to another.  This means that the permissions of the second user will be identical to that of the first user.
     *
     * @param fromUser User from whom permissions should be copied.
     * @param toUser User to whom persmissions should be copied.
     * @param userData User data
     * @return A boolean indicating succcess.
     * @throws EMCEntityBeanException
     */
    public boolean copyPermissions(String fromUser, String toUser, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Returns a List of Permission objects for the given user form the given form.  Returns all permissions for user if formClassName is null or blank.
     * @param userId The id of the user to retrieve permissions for.
     * @param formClassName Class name of form to retrieve permissions for, or null/empty String if all permissions should be returned.
     * @param userData User data containing username of user to retrieve permissions for.
     * @return A List of BaseUserPermissionsTable object containing the requested permissions.
     */
    public java.util.List<emc.entity.base.permissions.BaseUserPermissionsTable> getPermissionsForUser(java.lang.String userId, java.lang.String formClassName, emc.framework.EMCUserData userData);

    /**
     * Returns the BaseUserPermissionsTable record for the specified user and
     * the specified form.  If no record exists, this will return null.
     *
     * @param formClassName Form class name.
     * @param userId User id.
     * @param userData User data.
     * @return A BaseUserPermissionsTable record for the specified user and form.
     */
    public BaseUserPermissionsTable getUserFormPermissions(String formClassName, String userId, EMCUserData userData);
}

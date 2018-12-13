/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.permissions;

import emc.entity.base.permissions.BasePermissionsTable;
import emc.entity.base.permissions.BaseUserPermissionsTable;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @Name         : BaseUserPermissionsTableBean
 *
 * @Date         : 03 Jul 2009
 * 
 * @Description  : Bean for BaseUserPermissionsTable entity. 
 *
 * @author       : riaan
 */
@Stateless
public class BaseUserPermissionsTableBean extends EMCEntityBean implements BaseUserPermissionsTableLocal {

    @EJB
    private BasePermissionsTableLocal permissionsBean;

    /** Creates a new instance of BaseUserPermissionsTableBean. */
    public BaseUserPermissionsTableBean() {
    }

    /**
     * Returns a List of Permission objects for the given user form the given form.  Returns all permissions for user if formClassName is null or blank. 
     * @param formClassName Class name of form to retrieve permissions for, or null/empty String if all permissions should be returned.
     * @param userData User data containing username of user to retrieve permissions for.
     * @return A List of BaseUserPermissionsTable object containing the requested permissions.
     */
    public List<BaseUserPermissionsTable> getPermissionsForUser(String formClassName, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseUserPermissionsTable.class.getName());
        query.addAnd("userId", userData.getUserName());
        if (!isBlank(formClassName)) {
            query.addAnd("formClassName", formClassName);
        }

        return (List<BaseUserPermissionsTable>) util.executeGeneralSelectQuery(query, userData);
    }

    /**
     * Returns a List of Permission objects for the given user form the given form.  Returns all permissions for user if formClassName is null or blank.
     * @param userId The id of the user to retrieve permissions for.
     * @param formClassName Class name of form to retrieve permissions for, or null/empty String if all permissions should be returned.
     * @param userData User data containing username of user to retrieve permissions for.
     * @return A List of BaseUserPermissionsTable object containing the requested permissions.
     */
    public List<BaseUserPermissionsTable> getPermissionsForUser(String userId, String formClassName, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseUserPermissionsTable.class.getName());
        query.addAnd("userId", userId);
        if (!isBlank(formClassName)) {
            query.addAnd("formClassName", formClassName);
        }

        return (List<BaseUserPermissionsTable>) util.executeGeneralSelectQuery(query, userData);
    }

    /**
     * Copies permissions from one user to another.  This means that the permissions of the second user will be identical to that of the first user.
     *
     * @param fromUser User from whom permissions should be copied.
     * @param toUser User to whom persmissions should be copied.
     * @param userData User data
     * @return A boolean indicating succcess.
     * @throws EMCEntityBeanException
     */
    public boolean copyPermissions(String fromUser, String toUser, EMCUserData userData) throws EMCEntityBeanException {
        if (util.checkObjectsEqual(fromUser, toUser)) {
            throw new EMCEntityBeanException("The user from which permissions are copied may not be the same as the user from which permissions are copied");
        }

        //Copy form permissions
        BasePermissionsTable fromUserPermissions = permissionsBean.getUserPermissions(fromUser, userData);
        BasePermissionsTable toUserPermissions = permissionsBean.getUserPermissions(toUser, userData);

        if (fromUserPermissions == null) {
            throw new EMCEntityBeanException("User not found - " + fromUser);
        }

        if (toUserPermissions == null) {
            toUserPermissions = new BasePermissionsTable();
            toUserPermissions.setUserId(toUser);
            toUserPermissions.setUserMenu(fromUserPermissions.getUserMenu());
            permissionsBean.insert(toUserPermissions, userData);
        } else {
            toUserPermissions.setUserMenu(fromUserPermissions.getUserMenu());
            permissionsBean.update(toUserPermissions, userData);
        }

        //Delete existing permissions
        EMCQuery query = new EMCQuery(enumQueryTypes.DELETE, BaseUserPermissionsTable.class.getName());
        query.addAnd("userId", toUser);
        query.addAnd("companyId", util.getCompanyId(BaseUserPermissionsTable.class.getName(), userData));

        util.executeUpdate(query, userData);

        //Copy detailed permissions
        List<BaseUserPermissionsTable> fromPermissions = getPermissionsForUser(fromUser, null, userData);

        for (BaseUserPermissionsTable fromPermission : fromPermissions) {
            //Clone permission
            BaseUserPermissionsTable toPermission = (BaseUserPermissionsTable) util.doClone(fromPermission, BaseUserPermissionsTable.class, userData);
            toPermission.setUserId(toUser);

            insert(toPermission, userData);
        }

        return true;
    }

    /**
     * Returns the BaseUserPermissionsTable record for the specified user and
     * the specified form.  If no record exists, this will return null.
     *
     * @param formClassName Form class name.
     * @param userId User id.
     * @param userData User data.
     * @return A BaseUserPermissionsTable record for the specified user and form.
     */
    public BaseUserPermissionsTable getUserFormPermissions(String formClassName, String userId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseUserPermissionsTable.class);
        query.addAnd("formClassName", formClassName);
        query.addAnd("userId", userId);

        return (BaseUserPermissionsTable)util.executeSingleResultQuery(query, userData);
    }
}

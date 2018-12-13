/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.permissions.info;

import emc.entity.base.permissions.BaseUserPermissionsTable;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface BasePermissionsInfoLocal {

    public java.util.List getPermissionInfoByUser(java.lang.String userId, emc.framework.EMCUserData userData);

    public java.util.List validateUserId(java.lang.String userId, emc.framework.EMCUserData userData);

    public BaseUserPermissionsTable getDetailedPermissionInfoByUser(java.lang.String userId, java.lang.String formClassName, emc.framework.EMCUserData userData);
}

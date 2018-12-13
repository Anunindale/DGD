/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.permissions;

import emc.datatypes.base.users.UserId;
import emc.entity.base.Usertable;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 * @Name         : PermissionUserIdFKCascade
 *
 * @Date         : 07 Sep 2009
 *
 * @Description  : This foreign key is used to delete permissions when a user is deleted.  Never use it anywhere else!
 *
 * @author       : riaan
 */
public class PermissionUserIdFKCascade extends UserId {

    /** Creates a new instance of PermissionUserIdFKCascade. */
    public PermissionUserIdFKCascade() {
        this.setRelatedField("userId");
        this.setRelatedTable(Usertable.class.getName());
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.permissions;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.permissions.PermissionUserIdFKCascade;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @Name         : BaseUserPermissionsTable
 *
 * @Date         : 02 Jul 2009
 * 
 * @Description  : This entity class stores permissions for various forms that users have access to.
 *
 * @author       : riaan
 */
@Entity
@Table(name = "BasePermissionsTable", uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "companyId"})})
public class BasePermissionsTable extends EMCEntityClass {

    private String userId;
    @Column(name = "userMenu", length = 10000)
    private String userMenu;
    private boolean allowClone = false;
    private boolean allowDelete = false;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserMenu() {
        return userMenu;
    }

    public void setUserMenu(String userMenu) {
        this.userMenu = userMenu;
    }

    public boolean isAllowClone() {
        return allowClone;
    }

    public void setAllowClone(boolean allowClone) {
        this.allowClone = allowClone;
    }

    public boolean isAllowDelete() {
        return allowDelete;
    }

    public void setAllowDelete(boolean allowDelete) {
        this.allowDelete = allowDelete;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("userId", new PermissionUserIdFKCascade());

        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        //Query should return no data initially.
        query.addAnd("recordID", null);
        return query;
    }
}

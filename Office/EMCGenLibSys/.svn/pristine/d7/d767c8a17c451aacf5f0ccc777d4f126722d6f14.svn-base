/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.permissions;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.permissions.PermissionUserIdFKCascade;
import emc.datatypes.base.users.foreignkeys.UserIdFK;
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
@Table(name = "BaseUserPermissionsTable", uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "formClassName", "companyId"})})
public class BaseUserPermissionsTable extends EMCEntityClass {

    private String userId;
    private String formClassName;
    @Column(length = 10000)
    private String permissionTree;
    private String formAccess;

    /** Creates a new instance of BaseUserPermissionsTable. */
    public BaseUserPermissionsTable() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFormClassName() {
        return formClassName;
    }

    public void setFormClassName(String formClassName) {
        this.formClassName = formClassName;
    }

    public String getPermissionTree() {
        return permissionTree;
    }

    public void setPermissionTree(String permissionTree) {
        this.permissionTree = permissionTree;
    }

    public String getFormAccess() {
        return formAccess;
    }

    public void setFormAccess(String formAccess) {
        this.formAccess = formAccess;
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

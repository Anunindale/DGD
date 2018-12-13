/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.users;

import emc.app.components.emcJButton;
import emc.app.components.emcMenuButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.fileassociations.BaseUserFileAssociations;
import emc.entity.base.permissions.BasePermissionsTable;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class usersformDRM extends emcDataRelationManagerUpdate {

    private Boolean doFormRelation = true;
    private emcDataRelationManagerUpdate permissionsDRM;
    private emcMenuButton permission;
    private Point formPos;

    //For Permission issues
    public usersformDRM() {
    }

    public usersformDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
        permissionsDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.BASE.getId(), new BasePermissionsTable(), userData), userData);
    }

    @Override
    public void tableRowChanged(int rowIndex) {
        boolean wasOpen = false;
        if(this.getTheForm()!=null && this.getTheForm().getDeskTop()!=null){
            BaseInternalFrame permissionI = this.getTheForm().getDeskTop().isInDesktop(emc.forms.base.display.usermenusetup.userMenuSetup.class);
                if(permissionI!= null){
                    formPos = permissionI.getLocation();
                    this.getRelatedForms().remove(permissionI);
                    this.setDoFormRelation(false);
                    permissionI.doSaveOnClose();
                    permissionI.dispose();
                    wasOpen = true;
                }
        }
        this.setDoFormRelation(true);
        super.tableRowChanged(rowIndex);
        if(permission!=null&& wasOpen){
            permission.doClick();
        }
        
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        switch (Index) {
            case 0:
                Object userId = super.getFieldValueAt(this.getLastRowAccessed(), "userId");
                Object name = super.getFieldValueAt(this.getLastRowAccessed(), "userName");
                Object userMenu = permissionsDRM.getFieldValueAt(permissionsDRM.getLastRowAccessed(), "userMenu");
                List x = new ArrayList();
                x.add(0, userId);
                x.add(1, name);
                x.add(2, userMenu);
                x.add(3, this);
                x.add(4, getDoFormRelation());
                if(formPos!=null){
                    if(this.getTheForm()!=null && this.getTheForm().getDeskTop()!=null){
                        BaseInternalFrame permissionI = this.getTheForm().getDeskTop().isInDesktop(emc.forms.base.display.usermenusetup.userMenuSetup.class);
                            if(permissionI!= null){
                               permissionI.setLocation(formPos);
                               permissionI.repaint();
                               formPos = null;
                            }
                    }
                    
                }
                formUserData.setUserData(x);
                if (getDoFormRelation()) {
                    setDoFormRelation((Boolean) false);
                }
                break;
            case 1:
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseUserFileAssociations.class);
                query.addAnd("userId", getLastFieldValueAt("userId"));

                formUserData.setUserData(0, query);
                formUserData.setUserData(2, getLastFieldValueAt("userId"));

                break;
                
            default:
                break;
        }
        return formUserData;
    }

    public Boolean getDoFormRelation() {
        return doFormRelation;
    }

    public void setDoFormRelation(Boolean doFormRelation) {
        this.doFormRelation = doFormRelation;
    }

    public emcDataRelationManagerUpdate getPermissionsDRM() {
        return permissionsDRM;
    }

    /**
     * @return the permission
     */
    public emcMenuButton getPermission() {
        return permission;
    }

    /**
     * @param permission the permission to set
     */
    public void setPermission(emcMenuButton permission) {
        this.permission = permission;
    }

    


}

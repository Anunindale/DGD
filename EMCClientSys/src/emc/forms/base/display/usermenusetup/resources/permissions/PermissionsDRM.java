/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.usermenusetup.resources.permissions;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.forms.base.display.usermenusetup.userMenuSetup;
import emc.framework.EMCUserData;

/**
 * @Name         : PermissionsDRM
 *
 * @Date         : 03 Jul 2009
 * 
 * @Description  : Data relation manager used to manage the saving and loading of form permissions.
 *
 * @author       : riaan
 */
public class PermissionsDRM extends emcDataRelationManagerUpdate {

    private userMenuSetup userMenuSetupForm;
    private PermissionsComboboxManager permissionsComboboxManager;
    
    /** Creates a new instance of PermissionsDRM. */
    public PermissionsDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData, userMenuSetup userMenuSetupForm) {
        super(tableDataSource, userData);
        this.userMenuSetupForm = userMenuSetupForm;
    }

    @Override
    public void updatePersist(int rowIndex) {
        //Handle population of records here so that it is easily accessible from selection events, toolbar buttons, etc.
        if ((Long)getFieldValueAt(rowIndex, "recordID") == 0 && userMenuSetupForm.getFormPermissionsRoot() != null) {
            setFieldValueAt(rowIndex, "formClassName", userMenuSetupForm.getLastSelectedClassName());  
            setFieldValueAt(rowIndex, "userId", userMenuSetupForm.getUserId());
        }
        
        setFieldValueAt(rowIndex, "permissionTree", userMenuSetupForm.getFormPermissionsAsXML());
        
        super.updatePersist(rowIndex);
    }
    
    public void setPermissionsComboBoxManager(PermissionsComboboxManager permissionsComboboxManager) {
        this.permissionsComboboxManager = permissionsComboboxManager;
    }

}

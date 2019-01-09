/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.usermenusetup.resources.permissions;

import emc.app.permissions.PermissionTreeObject;
import emc.forms.base.display.usermenusetup.userMenuSetup;
import emc.framework.EMCMenuItem;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * @Name         : UserPermissionsTreeSelectionListener
 *
 * @Date         : 03 Jul 2009
 * 
 * @Description  : Selection Listener used by the user permissions tree.
 *
 * @author       : riaan
 */
public class UserPermissionsTreeSelectionListener implements TreeSelectionListener {

    private userMenuSetup userMenuSetupForm;
    private PermissionsDRM permissionsDRM;
    private PermissionsComboboxManager permissionsComboboxManager;

    /** Creates a new instance of UserPermissionsTreeSelectionListener. */
    public UserPermissionsTreeSelectionListener(userMenuSetup userMenuSetupForm, PermissionsDRM permissionsDRM, PermissionsComboboxManager permissionsComboboxManager) {
        this.userMenuSetupForm = userMenuSetupForm;
        this.permissionsDRM = permissionsDRM;
        this.permissionsComboboxManager = permissionsComboboxManager;
    }

    @Override
    public void valueChanged(TreeSelectionEvent evt) {
        String lastSelectedClassName = null;
        
        DefaultMutableTreeNode lastNode = (DefaultMutableTreeNode) evt.getPath().getLastPathComponent();
        if (lastNode.getUserObject() instanceof EMCMenuItem) {
            permissionsComboboxManager.setUserPermissionComboboxEnabled(true);
            
            Object userObject = lastNode.getUserObject();
            if (userObject instanceof EMCMenuItem) {
                lastSelectedClassName = ((EMCMenuItem)userObject).getClassPath();
            }
        } else {
            permissionsComboboxManager.setUserPermissionComboboxEnabled(false);
            //Don't display anything
            userMenuSetupForm.setFormPermissionTreeModel(null);
        }

        TreePath previousSelectionPath = evt.getOldLeadSelectionPath();

        if (previousSelectionPath != null) {
            DefaultMutableTreeNode prevNode = (DefaultMutableTreeNode) previousSelectionPath.getLastPathComponent();
            Object userObject = prevNode.getUserObject();
            if (userObject instanceof EMCMenuItem) {
                //Save previous path
                permissionsDRM.updatePersist(0);
            }
        }

        userMenuSetupForm.populateFormPermissionsTree();
        userMenuSetupForm.setLastSelectedClassName(lastSelectedClassName);
    }
}

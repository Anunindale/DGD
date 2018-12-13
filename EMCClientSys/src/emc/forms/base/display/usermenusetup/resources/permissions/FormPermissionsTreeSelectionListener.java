/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.usermenusetup.resources.permissions;

import emc.app.permissions.PermissionTreeObject;
import emc.forms.base.display.usermenusetup.userMenuSetup;
import emc.app.permissions.Permission;
import emc.app.permissions.PermissionTypes;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @Name         : UserPermissionsTreeSelectionListener
 *
 * @Date         : 03 Jul 2009
 * 
 * @Description  : Selection Listener used by the form permissions tree.
 *
 * @author       : riaan
 */
public class FormPermissionsTreeSelectionListener implements TreeSelectionListener {

    private userMenuSetup userMenuSetupForm;
    private PermissionsComboboxManager permissionsComboboxManager;
    
    /** Creates a new instance of UserPermissionsTreeSelectionListener. */
    public FormPermissionsTreeSelectionListener(userMenuSetup userMenuSetupForm, PermissionsComboboxManager permissionsComboboxManager) {
        this.userMenuSetupForm = userMenuSetupForm;
        this.permissionsComboboxManager = permissionsComboboxManager;
    }

    @Override
    public void valueChanged(TreeSelectionEvent evt) {
        DefaultMutableTreeNode lastNode = (DefaultMutableTreeNode)evt.getPath().getLastPathComponent();
        if (lastNode.getUserObject() instanceof PermissionTreeObject) {
            permissionsComboboxManager.setFormPermissionComboboxEnabled(true);
            Permission permission = ((PermissionTreeObject)lastNode.getUserObject()).getPermission();
            permissionsComboboxManager.updateFormPermissionsCombobox(PermissionTypes.fromString(permission.getPermissionType()), permission.getPermission());
        } else {
            permissionsComboboxManager.setFormPermissionComboboxEnabled(false);
        }
    }
}

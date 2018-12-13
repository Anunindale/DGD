/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.usermenusetup.resources.permissions;

import emc.app.components.emcJComboBox;
import emc.app.components.emcJTree;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.forms.base.display.usermenusetup.userMenuSetup;
import emc.functions.Functions;
import emc.app.permissions.ButtonPermissions;
import emc.app.permissions.FieldPermissions;
import emc.app.permissions.FormPermissions;
import emc.app.permissions.PermissionTypes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;

/**
 * @Name         : PermissionsComboboxManager
 *
 * @Date         : 03 Jul 2009
 * 
 * @Description  : This class manages the comboxes used on the User Menu Setup form.
 *
 * @author       : riaan
 */
public class PermissionsComboboxManager implements ActionListener {

    private userMenuSetup userMenuSetupForm;
    private emcJComboBox userPermissionsComboBox;
    private emcJComboBox formPermissionsComboBox;
    private emcDataRelationManagerUpdate permissionsDRM;
    private emcJTree formPermissionsTree;
    //Combobox models for permission types.
    private DefaultComboBoxModel fieldModel = new DefaultComboBoxModel(new String[]{FieldPermissions.NO_ACCESS.toString(), FieldPermissions.VIEW.toString(), FieldPermissions.EDIT.toString()});
    private DefaultComboBoxModel buttonModel = new DefaultComboBoxModel(new String[]{ButtonPermissions.DISALLOW.toString(), ButtonPermissions.ALLOW.toString()});

    /** Creates a new instance of PermissionsComboboxManager. */
    public PermissionsComboboxManager(userMenuSetup userMenuSetupForm, emcJComboBox userPermissionsComboBox, emcJComboBox formPermissionsComboBox, emcDataRelationManagerUpdate permissionsDRM, emcJTree formPermissionsTree) {
        this.userMenuSetupForm = userMenuSetupForm;
        this.userPermissionsComboBox = userPermissionsComboBox;
        this.formPermissionsComboBox = formPermissionsComboBox;
        this.permissionsDRM = permissionsDRM;
        this.formPermissionsTree = formPermissionsTree;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == userPermissionsComboBox) {
            //Update permissions for selected form
            if (userMenuSetupForm.getFormClassName() != null) {
                //Save permissions in DRM
                permissionsDRM.setFieldValueAt(0, "formAccess", userPermissionsComboBox.getSelectedItem());
            }

            //Reset field permissions in tree and update form permissions combobox
            userMenuSetupForm.clearFieldPermissionNodes();

            if (!userPermissionsComboBox.getSelectedItem().equals(FormPermissions.CREATE.toString())) {
                formPermissionsComboBox.setSelectedItem(userPermissionsComboBox.getSelectedItem());
            } else {
                formPermissionsComboBox.setSelectedItem(FormPermissions.EDIT.toString());
            }

        //If the last selected node does not have permissions set, update last form combobox selection.  Bit of a hack.
//            DefaultMutableTreeNode formNode = (DefaultMutableTreeNode) formPermissionsTree.getLastSelectedPathComponent();
//            if (formNode != null) {
//                Object userObject = formNode.getUserObject();
//                System.out.println(userObject.getClass());
//                if (!formNode.isLeaf() || (userObject instanceof PermissionTreeObject && Functions.checkBlank(((PermissionTreeObject) userObject).getPermission().getPermission()))) {
//                    if (!userPermissionsComboBox.getSelectedItem().equals(FormPermissions.CREATE.toString())) {
//                        userMenuSetupForm.setLastFormPermission((String) userPermissionsComboBox.getSelectedItem());
//
//                        formPermissionsComboBox.setSelectedItem(userPermissionsComboBox.getSelectedItem());
//                    } else {
//                        userMenuSetupForm.setLastFormPermission(FormPermissions.EDIT.toString());
//
//                        formPermissionsComboBox.setSelectedItem(FormPermissions.EDIT.toString());
//                    }
//                }
//            }
        } else if (source == formPermissionsComboBox) {
            userMenuSetupForm.setCurrentFormPermission((String) formPermissionsComboBox.getSelectedItem());
        }
    }

    /** Sets form permissions combobox enabled state. */
    public void setFormPermissionComboboxEnabled(boolean enabled) {
        this.formPermissionsComboBox.setEnabled(enabled);
    }

    /** Sets user permissions combobox enabled state. */
    public void setUserPermissionComboboxEnabled(boolean enabled) {
        this.userPermissionsComboBox.setEnabled(enabled);
    }

    /** 
     * Updates the form permission combobox to display appropriate values for the given permission type and selects the given value.  
     */
    public void updateFormPermissionsCombobox(PermissionTypes permissionType, Object value) {
        if (PermissionTypes.FIELD.equals(permissionType)) {
            this.formPermissionsComboBox.setModel(fieldModel);
            //Default to form permission of permission is blank.
            String currentUserPermission = userMenuSetupForm.getCurrentUserPermission();
            this.formPermissionsComboBox.setSelectedItem(Functions.checkBlank(value) ? (FormPermissions.CREATE.toString().equals(currentUserPermission) ? FieldPermissions.EDIT.toString() : currentUserPermission) : value);
        } else if (permissionType.BUTTON.equals(permissionType)) {
            this.formPermissionsComboBox.setModel(buttonModel);
            this.formPermissionsComboBox.setSelectedItem(value);
        }
    }
}

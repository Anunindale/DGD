/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.permissions.resources;

import emc.app.components.emcJTextField;
import emc.app.components.emcJTree;
import emc.app.components.lookup.EMCLookup;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.base.permissions.BaseUserPermissionsTable;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCMenu;
import emc.framework.EMCMenuItem;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.xml.EMCXMLHandler;
import emc.methods.base.ServerBaseMethods;
import emc.app.permissions.ButtonPermissions;
import emc.app.permissions.FieldPermissions;
import emc.app.permissions.FormPermissions;
import emc.app.permissions.Permission;
import emc.app.permissions.PermissionTypes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author wikus
 */
public class BasePermissionsInfoDRM {

    private emcJTree permissionsTree;
    private emcJTree detailedPermissionsTree;
    private emcJTextField txtPermissions;
    private emcJTextField txtDetailedPermissions;
    private EMCLookup lkpUser;
    private emcJTextField txtUser;
    private EMCUserData userData;

    public BasePermissionsInfoDRM(EMCUserData userData) {
        this.userData = userData.copyUserDataAndDataList();
    }

    public void validateUserId() {
        Object userId = lkpUser.getValue();
        if (!Functions.checkBlank(userId)) {
            EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.VALIDATE_USER_ID.toString());
            List theList = new ArrayList();
            theList.add(userId);
            theList = EMCWSManager.executeGenericWS(cmd, theList, userData);
            if (theList.size() > 1) {
                theList = (List) theList.get(1);
                if (!theList.isEmpty() && (Boolean) theList.get(0)) {
                    txtUser.setText((String) theList.get(1));
                } else {
                    lkpUser.setValue(null);
                    txtUser.setText(null);
                }
            }
        }
    }

    public void getByUser() {
        Object userId = lkpUser.getValue();
        if (!Functions.checkBlank(userId)) {
            EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.GET_PERMISSION_TREE_INFO.toString());
            List theList = new ArrayList();
            theList.add(userId);
            theList = EMCWSManager.executeGenericWS(cmd, theList, userData);
            if (theList.size() > 1) {
                theList = (List) theList.get(1);
                if (!theList.isEmpty()) {
                    String userMenu = (String) theList.get(0);
                    DefaultMutableTreeNode userMenuTree = new EMCXMLHandler().xmltoTree(userMenu, "Permissions");
                    addFormPermissions(userMenuTree);
                    permissionsTree.setModel(new DefaultTreeModel(userMenuTree));
                }
            }
        }
    }

    private void addFormPermissions(DefaultMutableTreeNode userMenuTree) {
        Enumeration e = userMenuTree.children();
        DefaultMutableTreeNode childNode;
        while (e.hasMoreElements()) {
            try {
                childNode = (DefaultMutableTreeNode) e.nextElement();

                String path = (String) childNode.getUserObject();

                Object userObject = Class.forName(path).newInstance();

                if (userObject instanceof EMCMenuItem) {
                    EMCMenuItem menuItem = (EMCMenuItem) userObject;

                    childNode.setUserObject(menuItem);

                } else {
                    EMCMenu menu = (EMCMenu) userObject;

                    childNode.setUserObject(menu.getMenuName());

                    addFormPermissions(childNode);
                }

            } catch (Exception ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Could not find menu.", userData);
                continue;
            }
        }
    }

    public void fetchDetailedPermissions(String formClassName) {
        EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.GET_DETAILED_PERMISSION_TREE_INFO);
        List toSend = new ArrayList();
        toSend.add(lkpUser.getValue());
        toSend.add(formClassName);
        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

        DefaultMutableTreeNode detailedTree;

        if (toSend.size() > 1) {
            BaseUserPermissionsTable permission = (BaseUserPermissionsTable) toSend.get(1);
            detailedTree = new EMCXMLHandler().xmltoTree(permission.getPermissionTree(), formClassName);
            if (Functions.checkBlank(permission.getFormAccess())) {
                setPermissions(FormPermissions.getDefaultFormPermission().toString());
            } else {
                setPermissions(permission.getFormAccess());
            }
        } else {
            detailedTree = new DefaultMutableTreeNode("No Detailed Permissions found.");
            setPermissions(FormPermissions.getDefaultFormPermission().toString());
        }

        detailedPermissionsTree.setModel(new DefaultTreeModel(detailedTree));
    }

    public void setPermissions(String permission) {
        this.txtPermissions.setText(permission);
        setDetailedPermissions(null);
    }

    public void setDetailedPermissions(Permission permission) {
        if (permission == null) {
            this.txtDetailedPermissions.setText("");
        } else {
            if (Functions.checkBlank(permission.getPermission())) {
                switch (PermissionTypes.fromString(permission.getPermissionType())) {
                    case BUTTON:
                        this.txtDetailedPermissions.setText(ButtonPermissions.ALLOW.toString());
                        break;
                    case FIELD:
                        this.txtDetailedPermissions.setText(FieldPermissions.EDIT.toString());
                        break;
                }
            } else {
                this.txtDetailedPermissions.setText(permission.getPermission());
            }
        }
    }

    public void setPermissionsTree(emcJTree permissionsTree) {
        this.permissionsTree = permissionsTree;
    }

    public void setDetailedPermissionsTree(emcJTree detailedPermissionsTree) {
        this.detailedPermissionsTree = detailedPermissionsTree;
    }

    public void setTxtDetailedPermissions(emcJTextField txtDetailedPermissions) {
        this.txtDetailedPermissions = txtDetailedPermissions;
    }

    public void setTxtPermissions(emcJTextField txtPermissions) {
        this.txtPermissions = txtPermissions;
    }

    public void setUserLoookup(EMCLookup lkpUser, emcJTextField txtUser) {
        this.lkpUser = lkpUser;
        this.txtUser = txtUser;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.usermenusetup;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.forms.base.display.usermenusetup.resources.DecodeUserTree;
import emc.forms.base.display.usermenusetup.resources.EncodeUserTree;
import emc.forms.base.display.users.usersformDRM;
import emc.framework.EMCUserData;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author rico
 */
public class userMenuSetupDRM extends emcDataRelationManagerUpdate {

    userMenuSetup theForm;
    private usersformDRM userDRM;
    private boolean busy = false;

    public userMenuSetupDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData, userMenuSetup theForm) {
        super(tableDataSource, userData);
        this.theForm = theForm;
    }

    @Override
    public void setUserData(EMCUserData userData) {
        theForm.setUserId(userData.getUserData().get(0).toString());
        Object userMenu = userData.getUserData().get(2);
        setUserDRM((usersformDRM) userData.getUserData().get(3));
        Boolean res = (Boolean) userData.getUserData().get(4);
        if (userMenu != null) {
            if (res) {
                DefaultMutableTreeNode userRoot = DecodeUserTree.decodeTree(userMenu.toString());
                theForm.setUserRoot(userRoot);
            }
        } else {
            theForm.setUserRoot(null);
        }

    }

    public void setFieldOnUserForm() {
        if (userDRM != null) {
            userDRM.getPermissionsDRM().setFieldValueAtRegardRelation(userDRM.getPermissionsDRM().getLastRowAccessed(), "userMenu", EncodeUserTree.encodeUserTree(theForm.getUserRoot()), false);
        }
    }

    @Override
    public boolean persistOnClosing() {
        setFieldOnUserForm();
        if (userDRM != null) {
            userDRM.getPermissionsDRM().updatePersist(userDRM.getPermissionsDRM().getLastRowAccessed());
            //Stops "editing" when form is closed
            userDRM.setDoFormRelation(true);
        }
        theForm.persistFormPermissions();
        return true;

    }

    @Override
    public void updatePersist(int rowIndex) {
        if (!busy) {
            busy = true;
            setFieldOnUserForm();
            if (userDRM != null) {
                userDRM.getPermissionsDRM().updatePersist(userDRM.getPermissionsDRM().getLastRowAccessed());
            }

            theForm.persistFormPermissions();
            busy = false;
        }
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        return null;
    }

    public usersformDRM getUserDRM() {
        return userDRM;
    }

    public void setUserDRM(usersformDRM userDRM) {
        this.userDRM = userDRM;
    }

    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        userDRM.getPermissionsDRM().setFieldValueAt(rowIndex, columnIndex, aValue);
    }

    @Override
    public Object getFieldValueAt(int rowIndex, String columnIndex) {
        return userDRM.getPermissionsDRM().getFieldValueAt(rowIndex, columnIndex);
    }
    
    

    @Override
    public boolean isRowUpdated() {
        return true;
    }
}

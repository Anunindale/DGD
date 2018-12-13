/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.permissions.resources;

import emc.app.components.emcJTree;
import emc.framework.EMCUserData;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.tree.TreePath;

/**
 *
 * @author wikus
 */
public class PermissionInfoMouseAdapter extends MouseAdapter {

    private emcJTree permissionsTree;
    private PermissionInfoPopup popup;

    public PermissionInfoMouseAdapter(emcJTree permissionsTree, EMCUserData userData) {
        this.permissionsTree = permissionsTree;
        this.popup = new PermissionInfoPopup(permissionsTree, userData);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (e.getButton() == MouseEvent.BUTTON3) {
            TreePath path = permissionsTree.getSelectionPath();
            if (path != null) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
}

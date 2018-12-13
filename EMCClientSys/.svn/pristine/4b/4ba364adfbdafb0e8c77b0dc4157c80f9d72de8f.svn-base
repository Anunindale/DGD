/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.usermenusetup.resources;

import emc.forms.base.display.usermenusetup.userMenuSetupDRM;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

/**
 *
 * @author rico
 */
public class userMenuTreeEventHandler implements TreeModelListener {
    private boolean busy = true;
    private userMenuSetupDRM DRM;
    public userMenuTreeEventHandler(){
        
    }
    public void treeNodesChanged(TreeModelEvent arg0) {
        if(!isBusy() && DRM != null)
        getDRM().setFieldOnUserForm();
    }

    public void treeNodesInserted(TreeModelEvent arg0) {
        if(!isBusy() && DRM != null)
        getDRM().setFieldOnUserForm();
    }

    public void treeNodesRemoved(TreeModelEvent arg0) {
        if(!isBusy() && DRM != null)
        getDRM().setFieldOnUserForm();
    }

    public void treeStructureChanged(TreeModelEvent arg0) {
        if(!isBusy() && DRM != null)
        getDRM().setFieldOnUserForm();
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public userMenuSetupDRM getDRM() {
        return DRM;
    }

    public void setDRM(userMenuSetupDRM DRM) {
        this.DRM = DRM;
    }

}

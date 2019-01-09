/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.webportalusers.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJPasswordField;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @description : This button is used to change a web portanl user's password.
 *
 * @date        : 08 Apr 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class ChangePasswordButton extends emcJButton {

    private emcDataRelationManagerUpdate drm;
    
    private emcJPasswordField pwPassword;
    private emcJPasswordField pwConfirmPassword;

    /** Creates a new instance of ChangePasswordButton */
    public ChangePasswordButton(emcDataRelationManagerUpdate drm, emcJPasswordField pwPassword, emcJPasswordField pwConfirmPassword) {
        super("Change Password");
        this.drm = drm;
        this.pwPassword = pwPassword;
        this.pwConfirmPassword = pwConfirmPassword;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        String password = new String(pwPassword.getPassword());
        String confirmPassword = new String(pwConfirmPassword.getPassword());
        if (password.equals(confirmPassword)) {

            if (JOptionPane.showConfirmDialog(null, "Change User Password.  Are you sure?", "Change Password", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                drm.setFieldValueAt(drm.getLastRowAccessed(), "password", password);
                drm.updatePersist(drm.getLastRowAccessed());

                if (!drm.isRowUpdated()) {
                    //Save was successful
                    pwPassword.setText("");
                    pwConfirmPassword.setText("");
                }
            }
        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "Passwords do not match.", drm.getUserData());
        }
    }

}

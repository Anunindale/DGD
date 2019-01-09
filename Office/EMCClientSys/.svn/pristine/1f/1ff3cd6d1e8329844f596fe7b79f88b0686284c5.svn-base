/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.users.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.forms.base.display.users.usersform;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 * @Name         : CopyPermissionsButton
 *
 * @Date         : 24 Aug 2009
 *
 * @Description  : This button is used to copy permissions from one user to another user.
 *
 * @author       : riaan
 */
public class CopyPermissionsButton extends emcJButton {

    private usersform usersForm;

    /** Creates a new instance of CopyPermissionsButton. */
    public CopyPermissionsButton(usersform usersForm) {
        super("Copy Permissions");
        this.usersForm = usersForm;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        if (EMCDialogFactory.createQuestionDialog(usersForm, "Copy permissions", "Are you sure that you want to copy user permissions?") == JOptionPane.YES_OPTION) {
            new CopyPermissionsDialog(usersForm);
        }
    }
}

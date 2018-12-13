/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.users.resources;

import emc.app.components.emcJDialog;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.reporttools.selection.dialogs.resources.LoadButton;
import emc.entity.base.Usertable;
import emc.forms.base.display.users.usersform;
import emc.framework.EMCUserData;
import emc.menus.personal.menuItems.display.users;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * @Name         : CopyPermissionsDialog
 *
 * @Date         : 24 Aug 2009
 *
 * @Description  : This dialog is used to copy permissions from one user to another user.
 *
 * @author       : riaan
 */
public class CopyPermissionsDialog extends emcJDialog {

    private CopyPermissionsDialogOKButton btnOK;

    //Package private variables.
    EMCLookup lkpToUser;
    usersform usersForm;

    /** Creates a new instance of CopyPermissionsDialog. */
    public CopyPermissionsDialog(usersform usersForm) {
        super();

        this.usersForm = usersForm;
        
        this.setModal(true);
        this.setTitle("Copy Permissions");
        
        initDialog();
    }

    private void initDialog() {
        this.setSize(240, 130);
        
        ((JPanel) this.getContentPane()).setBorder(BorderFactory.createTitledBorder("Copy permissions to user"));

        this.btnOK = new CopyPermissionsDialogOKButton(this);
        this.lkpToUser = new EMCLookup(new users());
        EMCLookupPopup userPopup = new EMCLookupPopup(new Usertable(), "userId", usersForm.getUserData());
        this.lkpToUser.setPopup(userPopup);

        this.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.weighty = 0.2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;

        this.add(this.lkpToUser, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 2;
        gbc.weighty = 0.2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;

        this.add(this.btnOK, gbc);

        this.setVisible(true);
    }
    
}

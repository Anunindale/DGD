/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base.menuItems.action;

import emc.app.components.base.emailing.EMCEmailForm;
import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class EMCEmailFormMenu extends EMCMenuItem {

    public EMCEmailFormMenu() {
        this.setClassPath(EMCEmailForm.class.getName());
        this.setMenuItemType(enumMenuItems.ACTION);
        this.setMenuItemName("EMC Email");
        this.setToolTipText("Send Email from EMC.");
        this.setDoNotOpenForm(true);
    }
}

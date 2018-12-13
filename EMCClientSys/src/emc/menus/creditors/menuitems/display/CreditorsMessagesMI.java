/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.creditors.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.creditors.display.messages.CreditorsMessagesForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author Owner
 */
public class CreditorsMessagesMI extends EMCMenuItem {

    public CreditorsMessagesMI() {

        this.setClassPath(CreditorsMessagesForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Messages");
        this.setToolTipText("Edit EMC Messages");
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author rico
 */
public class SendMessageToClient extends EMCMenuItem {
    public SendMessageToClient(){
        this.setClassPath("emc.forms.base.display.sendmsg.SendMessageForm");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Message to User");
        this.setToolTipText("Send Message to a specific user works from Active Users form only");
        this.setDoNotOpenForm(true);
    }

}

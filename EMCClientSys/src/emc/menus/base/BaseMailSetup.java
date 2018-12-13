/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base;

import emc.framework.EMCMenu;
import emc.menus.base.menuItems.display.BaseMailSetupMenu;
import emc.menus.base.menuItems.display.BaseMailreturnAddressSetupMenu;

/**
 *
 * @author wikus
 */
public class BaseMailSetup extends EMCMenu {

    public BaseMailSetup() {
        this.setMenuName("Mail");
        this.setMenuList(new BaseMailSetupMenu());
        this.setMenuList(new BaseMailreturnAddressSetupMenu());
        //this.setMenuList(new BaseEMailSignaturesMenu());
    }
}

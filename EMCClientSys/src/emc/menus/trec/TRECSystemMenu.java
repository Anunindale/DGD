/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.trec;

import emc.framework.EMCMenu;
import emc.menus.trec.menuitems.display.TRECEditTRECMI;
import emc.menus.trec.menuitems.display.TRECMessagesMI;

/**
 *
 * @author Owner
 */
public class TRECSystemMenu extends EMCMenu {

    public TRECSystemMenu() {
        this.setMenuName("System");
        this.setMenuList(new TRECMessagesMI());
        this.setMenuList(new TRECEditTRECMI());
    }
}

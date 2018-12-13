/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.hr;

import emc.framework.EMCMenu;
import emc.menus.hr.menuitems.display.HRMessagesMI;

/**
 *
 * @author Owner
 */
public class HRSystemMenu extends EMCMenu {

    public HRSystemMenu() {
        this.setMenuName("System");
        this.setMenuList(new HRMessagesMI());

    }
}

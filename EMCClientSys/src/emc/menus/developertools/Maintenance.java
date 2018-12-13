/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.developertools;

import emc.framework.EMCMenu;
import emc.menus.developertools.menuitems.display.DevCloseTasksMI;
/**
 *
 * @author Pierre
 */
public class Maintenance extends EMCMenu {
   public Maintenance() {
    this.setMenuName("Maintenence");
    this.setMenuList(new DevCloseTasksMI());
   } 
}

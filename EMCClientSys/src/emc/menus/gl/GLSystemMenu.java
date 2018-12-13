/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.gl;

import emc.framework.EMCMenu;
import emc.menus.gl.menuitems.display.GLMessagesMI;

/**
 *
 * @author Owner
 */
public class GLSystemMenu extends EMCMenu {

    public GLSystemMenu() {
        this.setMenuName("System");
        this.setMenuList(new GLMessagesMI());
    }
}

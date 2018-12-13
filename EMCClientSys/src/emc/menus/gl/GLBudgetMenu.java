/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.gl;

import emc.framework.EMCMenu;
import emc.menus.gl.menuitems.display.GLBudgetModelMI;
import emc.menus.gl.menuitems.display.GLBudgetPeriodMI;

/**
 *
 * @author claudette
 */
public class GLBudgetMenu extends EMCMenu {

    public GLBudgetMenu() {
        this.setMenuName("Budgets");
        this.setMenuList(new GLBudgetModelMI());
        this.setMenuList(new GLBudgetPeriodMI());
    }
}

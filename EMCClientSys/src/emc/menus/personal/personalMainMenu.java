/*
 * personalMainMenu.java
 *
 * Created on 12 December 2007, 03:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package emc.menus.personal;

import emc.framework.EMCMenu;
import emc.menus.personal.menuItems.display.toDo;
import emc.menus.personal.menuItems.display.transactionLog;
import emc.menus.personal.menuItems.display.users;

/**
 *
 * @author rico
 */
public class personalMainMenu extends EMCMenu {
    
    /** Creates a new instance of personalMainMenu */
    toDo mtoDo = new toDo();
    transactionLog mTrans = new transactionLog(); 
    users users = new users();
    public personalMainMenu()  {
        this.setMenuName("Personal Space");
        this.setMenuList(mtoDo);
        this.setMenuList(mTrans);
        this.setMenuList(users);
    }
    
}

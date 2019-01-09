/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base;

import emc.framework.EMCMenu;
import emc.menus.base.menuItems.display.ActiveTransactions;
import emc.menus.base.menuItems.display.AdminUsers;
import emc.menus.base.menuItems.display.SendMessageToAllClients;
import emc.menus.base.menuItems.display.SendMessageToClient;

/**
 *
 * @author rico
 */
public class BaseAdmin extends EMCMenu {

    private AdminUsers users = new AdminUsers();
    private SendMessageToClient msgClient = new SendMessageToClient();
    private SendMessageToAllClients allClients = new SendMessageToAllClients();

    public BaseAdmin() {
        this.setMenuName("Admin");
        this.setMenuList(users);
        this.setMenuList(new ActiveTransactions());
        this.setMenuList(msgClient);
        this.setMenuList(allClients);
    }
}

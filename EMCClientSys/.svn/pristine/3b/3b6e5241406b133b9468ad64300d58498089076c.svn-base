/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.creditors;

import emc.framework.EMCMenu;
import emc.menus.creditors.menuitems.display.CreditorsMessagesMI;
import emc.menus.creditors.menuitems.display.CreditorsTransactionSettlementHistoryMI;

/**
 *
 * @author Owner
 */
public class CreditorsSystemMenu extends EMCMenu {

    private CreditorsTransactionSettlementHistoryMI history = new CreditorsTransactionSettlementHistoryMI();

    public CreditorsSystemMenu() {
        this.setMenuName("System");
        this.setMenuList(history);
        this.setMenuList(new CreditorsMessagesMI());
    }
}

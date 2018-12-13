/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.debtors;

import emc.framework.EMCMenu;
import emc.menus.debtors.menuitems.display.DebtorsCreditNoteRegisterMI;
import emc.menus.debtors.menuitems.display.DebtorsCustomerBalance;
import emc.menus.debtors.menuitems.display.DebtorsMessagesMI;
import emc.menus.debtors.menuitems.display.DebtorsTotalCreditHeldMI;
import emc.menus.debtors.menuitems.display.DebtorsTransactionSettlementHistory;

/**
 * @description : System menu in the Debtors module.
 *
 * @date        : 21 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class DebtorsSystemMenu extends EMCMenu {

    /** Creates a new instance of DebtorsSystemMenu */
    public DebtorsSystemMenu() {
        this.setMenuName("System");
        this.setMenuList(new DebtorsCustomerBalance());
        this.setMenuList(new DebtorsCreditNoteRegisterMI());
        this.setMenuList(new DebtorsTransactionSettlementHistory());
        this.setMenuList(new DebtorsTotalCreditHeldMI());
        this.setMenuList(new DebtorsMessagesMI());
    }
}

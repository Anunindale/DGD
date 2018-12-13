/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.creditors;

import emc.framework.EMCMenu;
import emc.menus.creditors.menuitems.display.CreditorsCreditNoteMI;
import emc.menus.creditors.menuitems.display.CreditorsInvoiceMI;
import emc.menus.pop.menuitems.display.Suppliers;

/**
 *
 * @author wikus
 */
public class creditorsMainMenu extends EMCMenu {

    public creditorsMainMenu() {
        this.setMenuName("Creditors");
        this.setMenuList(new CreditorsInvoiceMI());
        this.setMenuList(new CreditorsCreditNoteMI());
        this.setMenuList(new Suppliers());
        this.setMenuList(new creditorsEnquiryMenu());
        this.setMenuList(new creditorsFrequentlyMenu());
        this.setMenuList(new creditorsSetupMenu());
        this.setMenuList(new CreditorsSystemMenu());
    }
}

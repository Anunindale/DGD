/*
 * debtorsMainMenu.java
 *
 * Created on 29 November 2007, 11:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.menus.debtors;

import emc.framework.EMCMenu;
import emc.menus.debtors.menuitems.display.DebtorsCreditHeldMI;
import emc.menus.debtors.menuitems.display.DebtorsCreditNotes;
import emc.menus.debtors.menuitems.display.DebtorsCustomerDistributionInvoices;
import emc.menus.debtors.menuitems.display.DebtorsCustomerInvoices;
import emc.menus.debtors.menuitems.display.DebtorsCustomers;
import emc.menus.debtors.menuitems.display.DebtorsPostDatedPayments;

/**
 *
 * @author rico
 */
public class debtorsMainMenu extends EMCMenu {

    /**
     * Creates a new instance of debtorsMainMenu
     */
    public debtorsMainMenu() {
        this.setMenuName("Debtors");
        this.setMenuList(new DebtorsCreditHeldMI());
        this.setMenuList(new DebtorsCreditNotes());
        this.setMenuList(new DebtorsCustomers());
        this.setMenuList(new DebtorsCustomerInvoices());
        this.setMenuList(new DebtorsCustomerDistributionInvoices());
        this.setMenuList(new DebtorsPostDatedPayments());
        this.setMenuList(new DebtorsFrequentlyMenu());
        this.setMenuList(new DebtorsJournalsMenu());
        this.setMenuList(new DebtorsEnquiry());
        this.setMenuList(new DebtorsReportsMenu());
        this.setMenuList(new debtorsSetup());
        this.setMenuList(new DebtorsSystemMenu());
      
    }
}

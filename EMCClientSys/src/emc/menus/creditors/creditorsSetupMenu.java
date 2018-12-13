/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.creditors;

import emc.framework.EMCMenu;
import emc.menus.creditors.menuitems.display.CreditorsApprovalGroupSetupMI;
import emc.menus.creditors.menuitems.display.CreditorsNumberSequenceMenu;
import emc.menus.creditors.menuitems.display.CreditorsParametersMenu;
import emc.menus.creditors.menuitems.display.SettlementDiscountTerms;
import emc.menus.creditors.menuitems.display.TermsOfPayment;
import emc.menus.pop.menuitems.display.DiscountGroups;
import emc.menus.pop.menuitems.display.ExtraChargeGroups;
import emc.menus.pop.menuitems.display.PriceGroups;
import emc.menus.pop.menuitems.display.SupplierGroups;
import emc.menus.creditors.menuitems.display.CreditorsApprovalGroupsMI;

/**
 *
 * @author wikus
 */
public class creditorsSetupMenu extends EMCMenu {

    private SettlementDiscountTerms settelement = new SettlementDiscountTerms();
    private TermsOfPayment term = new TermsOfPayment();
    //Reverence to POP
    DiscountGroups discountGroups = new DiscountGroups();
    ExtraChargeGroups extraCharge = new ExtraChargeGroups();
    SupplierGroups supplierGroups = new SupplierGroups();
    PriceGroups priceGroups = new PriceGroups();
    CreditorsParametersMenu parameter = new CreditorsParametersMenu();

    public creditorsSetupMenu() {
        this.setMenuName("Setup");

        this.setMenuList(new CreditorsApprovalGroupsMI());
        this.setMenuList(new CreditorsApprovalGroupSetupMI());
        this.setMenuList(discountGroups);
        this.setMenuList(extraCharge);
        this.setMenuList(priceGroups);
        this.setMenuList(settelement);
        this.setMenuList(supplierGroups);
        this.setMenuList(term);
        this.setMenuList(parameter);
        this.setMenuList(new CreditorsNumberSequenceMenu());
    }
}

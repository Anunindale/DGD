/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.debtors.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.debtors.display.basket.DebtorsBasketForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author stuart
 */
public class DebtorsBasketMI extends EMCMenuItem {
    public DebtorsBasketMI(){
        this.setClassPath(DebtorsBasketForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Debtors Basket");
        this.setToolTipText("View and edit debtors baskets");
    }
}

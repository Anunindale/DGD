/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory.menuitems.display;

import emc.app.components.inventory.register.forms.stocktake.StockTakeRegisterForm;
import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class InventoryStockTakeRegisterMenu extends EMCMenuItem {

    public InventoryStockTakeRegisterMenu() {
        this.setClassPath(StockTakeRegisterForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Register: Stock Take");
        this.setToolTipText("Register Items");
    }
}

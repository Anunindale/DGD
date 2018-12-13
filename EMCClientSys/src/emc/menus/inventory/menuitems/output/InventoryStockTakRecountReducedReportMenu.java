/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.inventory.output.restocktake.InventoryStocktakeRecountReducedReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class InventoryStockTakRecountReducedReportMenu extends EMCMenuItem {

    /** Creates a new instance of  InventoryStockTakeReportMenu*/
    public InventoryStockTakRecountReducedReportMenu() {
        this.setClassPath(InventoryStocktakeRecountReducedReportForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setMenuItemName("Recount Sheet - Portrait");
        this.setToolTipText("Stock Take Recount");
    }
}

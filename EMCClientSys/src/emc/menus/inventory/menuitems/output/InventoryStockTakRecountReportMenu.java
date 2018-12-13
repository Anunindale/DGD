/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.inventory.output.restocktake.InventoryStocktakeRecountReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class InventoryStockTakRecountReportMenu extends EMCMenuItem {

    /** Creates a new instance of  InventoryStockTakeReportMenu*/
    public InventoryStockTakRecountReportMenu() {
        this.setClassPath(InventoryStocktakeRecountReportForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setMenuItemName("Recount Sheet - Landscape");
        this.setToolTipText("Stock Take Recount");
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.inventory.output.stocktakevariance.InventoryStocktakeVarianceByWarehouseReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class InventoryStockTakeVarianceByWarehouseReportMenu extends EMCMenuItem {

    /** Creates a new instance of  InventoryStockTakeVarianceReportMenu*/
    public InventoryStockTakeVarianceByWarehouseReportMenu() {
        this.setClassPath(InventoryStocktakeVarianceByWarehouseReportForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setMenuItemName("Stock Take Variance By Warehouse");
        this.setToolTipText("Variances in Stock Take");
    }
}

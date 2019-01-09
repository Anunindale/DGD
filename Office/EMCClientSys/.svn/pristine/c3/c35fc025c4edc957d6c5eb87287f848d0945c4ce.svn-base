/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory;

import emc.framework.EMCMenu;
import emc.menus.inventory.menuitems.output.InventoryStockTakRecountReducedReportMenu;
import emc.menus.inventory.menuitems.output.InventoryStockTakRecountReportMenu;
import emc.menus.inventory.menuitems.output.InventoryStockTakReportMenu;
import emc.menus.inventory.menuitems.output.InventoryStockTakeReducedReportMenu;
import emc.menus.inventory.menuitems.output.InventoryStockTakeVarianceByWarehouseReportMenu;
import emc.menus.inventory.menuitems.output.InventoryStockTakeVarianceReportMenu;
import emc.menus.inventory.menuitems.output.ItemList;

/**
 *
 * @author wikus
 */
public class InventoryStockTakeReports extends EMCMenu {

    public ItemList itemList = new ItemList();

    /** Creates a new instance of  InventoryStockTakeReports*/
    public InventoryStockTakeReports() {
        this.setMenuName("Stock Take");
        this.setMenuList(new InventoryStockTakReportMenu());
        this.setMenuList(new InventoryStockTakeReducedReportMenu());
        this.setMenuList(new InventoryStockTakRecountReportMenu());
        this.setMenuList(new InventoryStockTakRecountReducedReportMenu());
        this.setMenuList(new InventoryStockTakeVarianceReportMenu());
        this.setMenuList(new InventoryStockTakeVarianceByWarehouseReportMenu());
    }
}

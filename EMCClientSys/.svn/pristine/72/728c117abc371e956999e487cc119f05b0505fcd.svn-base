/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory;

import emc.framework.EMCMenu;
import emc.menus.inventory.menuitems.output.BatchConsolidationPickingListReportMI;
import emc.menus.inventory.menuitems.output.BelowMinReportMenu;
import emc.menus.inventory.menuitems.output.InventoryAgeing;
import emc.menus.inventory.menuitems.output.InventoryAgeingDetail;
import emc.menus.inventory.menuitems.output.InventoryAgeingFullDetail;
import emc.menus.inventory.menuitems.output.InventoryDimension1ReportMenu;
import emc.menus.inventory.menuitems.output.InventoryFactoryShopReportMenu;
import emc.menus.inventory.menuitems.output.InventoryMovementJournalDetailMenu;
import emc.menus.inventory.menuitems.output.InventoryMovementJournalSummaryMenu;
import emc.menus.inventory.menuitems.output.InventoryOpenDispatchOrderReportMenu;
import emc.menus.inventory.menuitems.output.InventoryWarehouseLocationTransferReportMI;
import emc.menus.inventory.menuitems.output.InventoryWithNoDemandReportMenu;
import emc.menus.inventory.menuitems.output.ItemDimensions;
import emc.menus.inventory.menuitems.output.ItemList;
import emc.menus.inventory.menuitems.output.OnHandReportMenu;
import emc.menus.inventory.menuitems.output.QCStockReportMenu;
import emc.menus.inventory.menuitems.output.StockByLocation;
import emc.menus.inventory.menuitems.output.StockJournalMenu;
import emc.menus.inventory.menuitems.output.StockUsageReportMenu;
import emc.menus.inventory.menuitems.output.StockValuationReportMenu;

/**
 *
 * @author riaan
 */
public class InventoryReports extends EMCMenu {

    public ItemList itemList = new ItemList();
    private OnHandReportMenu onHand = new OnHandReportMenu();
    private StockJournalMenu journals = new StockJournalMenu();
    private BelowMinReportMenu belowMin = new BelowMinReportMenu();

    /**
     * Creates a new instance of InventoryReports
     */
    public InventoryReports() {
        this.setMenuName("Reports");
        this.setMenuList(new InventoryStockTakeReports());
        this.setMenuList(new InventoryAgeing());
        this.setMenuList(new InventoryAgeingDetail());
        this.setMenuList(new InventoryAgeingFullDetail());
        this.setMenuList(new BatchConsolidationPickingListReportMI());
        this.setMenuList(new ItemDimensions());
        this.setMenuList(itemList);
        this.setMenuList(onHand);
        this.setMenuList(journals);
        this.setMenuList(belowMin);
        this.setMenuList(new StockByLocation());
        this.setMenuList(new QCStockReportMenu());
        this.setMenuList(new StockValuationReportMenu());
        this.setMenuList(new InventoryMovementJournalSummaryMenu());
        this.setMenuList(new InventoryMovementJournalDetailMenu());
        this.setMenuList(new StockUsageReportMenu());
        this.setMenuList(new InventoryDimension1ReportMenu());
        this.setMenuList(new InventoryOpenDispatchOrderReportMenu());
        this.setMenuList(new InventoryWithNoDemandReportMenu());
        this.setMenuList(new InventoryFactoryShopReportMenu());
        this.setMenuList(new InventoryWarehouseLocationTransferReportMI());
    }
}

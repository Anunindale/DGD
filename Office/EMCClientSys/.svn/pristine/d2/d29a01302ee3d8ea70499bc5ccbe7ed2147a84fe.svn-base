/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.pop;

import emc.framework.EMCMenu;
import emc.menus.pop.menuitems.display.POPGRNReprintTempMenu;
import emc.menus.pop.menuitems.output.CancelledPurchaseOrders;
import emc.menus.pop.menuitems.output.GoodsReceived;
import emc.menus.pop.menuitems.output.GoodsReturned;
import emc.menus.pop.menuitems.output.MillPurchseOrderReport;
import emc.menus.pop.menuitems.output.OutstandingPurchaseOrders;
import emc.menus.pop.menuitems.output.POPCrateLabelsReportMI;
import emc.menus.pop.menuitems.output.POPFGBoxLabelsReportMI;
import emc.menus.pop.menuitems.output.PriceVarianceMenu;
import emc.menus.pop.menuitems.output.ServicesReceivedReportMI;

/**
 *
 * @author riaan
 */
public class POPReports extends EMCMenu {

    private GoodsReceived goodsReceived = new GoodsReceived();
    private CancelledPurchaseOrders cancelledPurchaseOrders = new CancelledPurchaseOrders();
    private OutstandingPurchaseOrders outstanding = new OutstandingPurchaseOrders();
    private PriceVarianceMenu priceVariance = new PriceVarianceMenu();
    private GoodsReturned goodsReturned = new GoodsReturned();

    /** Creates a new instance of POPReports */
    public POPReports() {
        this.setMenuName("Reports");
        this.setMenuList(cancelledPurchaseOrders);
        this.setMenuList(new POPCrateLabelsReportMI());
        this.setMenuList(new POPFGBoxLabelsReportMI());
        this.setMenuList(goodsReceived);
        this.setMenuList(goodsReturned);
        this.setMenuList(outstanding);
        this.setMenuList(priceVariance);
        //this.setMenuList(new ServicesReceivedReportMI());
        this.setMenuList(new POPGRNReprintTempMenu());
        this.setMenuList(new MillPurchseOrderReport());
    }
}

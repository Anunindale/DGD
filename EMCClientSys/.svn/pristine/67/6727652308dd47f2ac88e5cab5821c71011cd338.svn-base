/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory;

import emc.framework.EMCMenu;
import emc.menus.inventory.menuitems.display.Dimension1EnquiryMI;
import emc.menus.inventory.menuitems.display.InventoryDimension1WhereUsedMenu;
import emc.menus.inventory.menuitems.display.InventoryDimension3WhereUsedMenu;
import emc.menus.inventory.menuitems.display.InventoryReqPlanAuditMI;
import emc.menus.inventory.menuitems.display.InventoryRequirementsPlanningMenu;
import emc.menus.inventory.menuitems.display.InventoryWarehouseStockEnquiryBySizeMenu;
import emc.menus.inventory.menuitems.display.InventoryWarehouseStockEnquiryMenu;
import emc.menus.inventory.menuitems.display.OnHand;
import emc.menus.inventory.menuitems.display.Transactions;

/**
 *
 * @author rico
 */
public class InventoryEnquiry extends EMCMenu {

    private OnHand onhand = new OnHand();
    private Transactions trans = new Transactions();

    public InventoryEnquiry() {
        this.setMenuName("Enquiry");
        this.setMenuList(new Dimension1EnquiryMI());
        this.setMenuList(onhand);
//        this.setMenuList(new InventoryRequirementsPlanningHistoryMenu());
        this.setMenuList(new InventoryRequirementsPlanningMenu());
        this.setMenuList(trans);
        this.setMenuList(new InventoryWarehouseStockEnquiryBySizeMenu());
        this.setMenuList(new InventoryWarehouseStockEnquiryMenu());
        this.setMenuList(new InventoryDimension1WhereUsedMenu());
        this.setMenuList(new InventoryDimension3WhereUsedMenu());
        this.setMenuList(new InventoryReqPlanAuditMI());
    }
}

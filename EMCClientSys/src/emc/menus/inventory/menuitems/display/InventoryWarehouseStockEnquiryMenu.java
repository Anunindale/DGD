/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.inventory.display.warehousestockenquiry.InventoryWarehouseStockEnquiryForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class InventoryWarehouseStockEnquiryMenu extends EMCMenuItem {

    /** Creates a new instance of  InventoryWarehouseStockEnquiryMenu*/
    public InventoryWarehouseStockEnquiryMenu() {
        this.setClassPath(InventoryWarehouseStockEnquiryForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Warehouse Stock Enquiry");
        this.setToolTipText("Enquiry on the stock in a warehouse.");
    }
}

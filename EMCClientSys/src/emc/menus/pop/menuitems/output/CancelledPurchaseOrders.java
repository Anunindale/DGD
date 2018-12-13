/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.pop.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.pop.output.cancelledpurchaseorders.CancelledPurchaseOrderReportForm;
import emc.framework.EMCMenuItem;

/**
 * @Name         : CancelledPurchaseOrders
 *
 * @Date         : 04 Jun 2009
 * 
 * @Description  : Menu item for the cancelled Purchase Orders report.
 *
 * @author       : riaan
 */
public class CancelledPurchaseOrders extends EMCMenuItem {
    
    /** Creates a new instance of CancelledPurchaseOrders */
    public CancelledPurchaseOrders() {
        this.setClassPath(CancelledPurchaseOrderReportForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setMenuItemName("Cancelled POs");
        this.setToolTipText("Cancelled Purchase Orders");
    }
}

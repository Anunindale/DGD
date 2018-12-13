/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.pop.menuitems.output;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class OutstandingPurchaseOrders extends EMCMenuItem {

    /** Creates a new instance of OutstandingPurchaseOrders */
    public OutstandingPurchaseOrders() {
        this.setClassPath(emc.forms.pop.output.outstandingpurchaseorders.OutstandingPOPrintForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setMenuItemName("Outstanding PO");
        this.setToolTipText("Outstanding Purchase Orders");
    }
}

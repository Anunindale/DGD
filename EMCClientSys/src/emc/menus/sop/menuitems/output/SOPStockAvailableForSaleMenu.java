/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.sop.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.sop.output.SOPStockAvailableForSaleForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class SOPStockAvailableForSaleMenu extends EMCMenuItem {

    /** Creates a new instance of CustomerActivityMenu */
    public SOPStockAvailableForSaleMenu() {
        this.setMenuItemName("Stock Available For Sale");
        this.setClassPath(SOPStockAvailableForSaleForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setToolTipText("Print Stock Available For Sale.");
    }
}

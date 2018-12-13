/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.pop.menuitems.output;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class PriceVarianceMenu extends EMCMenuItem {

    /** Creates a new instance of OutstandingPurchaseOrders */
    public PriceVarianceMenu() {
        this.setClassPath(emc.forms.pop.output.pricevariance.PriceVariancePrintForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setMenuItemName("Price Variance");
        this.setToolTipText("Price Variance Reports");
    }

}

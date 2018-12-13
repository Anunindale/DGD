/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.sop.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.sop.display.priceaudittrail.SOPPriceAuditTrailForm;
import emc.framework.EMCMenuItem;

/**
 * @author wikus
 */
public class SOPPriceAutitTrailMenu extends EMCMenuItem {

    /** Creates a new instance of  SOPPriceAutitTrailMenu*/
    public SOPPriceAutitTrailMenu() {
        this.setMenuItemName("Price Audit Trail - Price Change");
        this.setClassPath(SOPPriceAuditTrailForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("View changes to the price list.");
    }
}

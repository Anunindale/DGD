/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.sop.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.sop.output.priceaudittrail.SOPPriceAuditTrailPriceChangeReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class SOPPriceAuditTrailPriceChangeReportMenu extends EMCMenuItem {

    /** Creates a new instance of CustomerActivityMenu */
    public SOPPriceAuditTrailPriceChangeReportMenu() {
        this.setMenuItemName("Price Audit Trail - Price List");
        this.setClassPath(SOPPriceAuditTrailPriceChangeReportForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setToolTipText("Print out a price audit trail for the price list.");
    }
}

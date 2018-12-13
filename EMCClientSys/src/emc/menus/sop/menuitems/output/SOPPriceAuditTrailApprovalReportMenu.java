/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.sop.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.sop.output.priceaudittrail.SOPPriceAuditTrailApprovalReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class SOPPriceAuditTrailApprovalReportMenu extends EMCMenuItem {

    /** Creates a new instance of CustomerActivityMenu */
    public SOPPriceAuditTrailApprovalReportMenu() {
        this.setMenuItemName("Price Audit Trail - Approval");
        this.setClassPath(SOPPriceAuditTrailApprovalReportForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setToolTipText("Print out a price audit trail for approvals.");
    }
}

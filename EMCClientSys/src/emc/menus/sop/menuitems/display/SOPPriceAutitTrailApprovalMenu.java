/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.sop.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.sop.display.priceaudittrail.SOPPriceAuditTrailApprovalForm;
import emc.framework.EMCMenuItem;

/**
 * @author wikus
 */
public class SOPPriceAutitTrailApprovalMenu extends EMCMenuItem {

    /** Creates a new instance of  SOPPriceAutitTrailMenu*/
    public SOPPriceAutitTrailApprovalMenu() {
        this.setMenuItemName("Price Audit Trail - Approval");
        this.setClassPath(SOPPriceAuditTrailApprovalForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("View price approvals.");
    }
}

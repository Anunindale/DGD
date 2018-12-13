/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.sop.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.sop.display.pricechangeapproval.SOPPriceChangeApprovalForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class SOPPriceChangeApprovalMenu extends EMCMenuItem {

    public SOPPriceChangeApprovalMenu() {
        this.setClassPath(SOPPriceChangeApprovalForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Price Change Approval");
        this.setToolTipText("Approve price changes");
    }
}

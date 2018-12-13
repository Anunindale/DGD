/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.sop.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.sop.display.statusenquiry.SOPStatusEnquiryForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class SOPStatusEnquiryMenu extends EMCMenuItem {

    public SOPStatusEnquiryMenu() {
        this.setClassPath(SOPStatusEnquiryForm.class.getName());
        this.setMenuItemName("Sales Order Enquiry");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("View Sales Order Daily Values");
    }
}

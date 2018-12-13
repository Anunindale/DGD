/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.sop.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.sop.display.blanketorderstatusenquiry.BlanketOrderStatusEnquiryForm;
import emc.framework.EMCMenuItem;

/**
 * @author riaan
 */
public class SOPBlanketOrderStatusEnquiryMI extends EMCMenuItem {

    /** Creates a new instance of SOPBlanketOrderStatusEnquiryMI */
    public SOPBlanketOrderStatusEnquiryMI() {
        this.setMenuItemName("Blanket Order Status Enquiry");
        this.setClassPath(BlanketOrderStatusEnquiryForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("View the status of items on a Blanket Order.");
        this.setDoNotOpenForm(false);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.pop.display.purchaseorders.resources;

import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.menus.pop.menuitems.display.CancelledPO;
import emc.menus.pop.menuitems.display.SupplierReceivedJournals;

/**
 *
 * @author wikus
 */
public class EnquiryButton extends emcMenuButtonList {
    
    public EnquiryButton(BaseInternalFrame frame) {
        super("Enquiry", frame);
        this.addMenuItem("Received Notes", new SupplierReceivedJournals(), 1, false);
        this.addMenuItem("Cancelled POs", new CancelledPO(), 4, false);
    }
}


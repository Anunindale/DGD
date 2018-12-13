/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.sop;

import emc.framework.EMCMenu;
import emc.menus.sop.menuitems.display.CustomerActivityMenu;
import emc.menus.sop.menuitems.display.SOPBlanketOrderStatusEnquiryMI;
import emc.menus.sop.menuitems.display.SOPMessagesMI;

/**
 *
 * @author wikus
 */
public class SOPSystemMenu extends EMCMenu {

    /**
     * Creates a new instance of SOPClassifications
     */
    public SOPSystemMenu() {
        this.setMenuName("System");
        this.setMenuList(new SOPBlanketOrderStatusEnquiryMI());
        this.setMenuList(new CustomerActivityMenu());
        this.setMenuList(new SOPMessagesMI());
    }
}

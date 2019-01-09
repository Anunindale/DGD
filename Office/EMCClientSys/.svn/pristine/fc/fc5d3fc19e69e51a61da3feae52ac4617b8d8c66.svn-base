/*
 * SOPMainMenu.java
 *
 * Created on 14 December 2009, 12:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.menus.sop;

import emc.enums.modules.enumEMCModules;
import emc.framework.EMCMenu;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;

/**
 *
 * @author wikus
 */
public class SOPMainMenu extends EMCMenu {

    /**
     * Creates a new instance of POPMainMenu
     */
    public SOPMainMenu() {
        this.setEmcModule(enumEMCModules.SOP);
        this.setMenuName(this.getEmcModule().toString());
        this.setMenuList(new SOPCustomersMenu());

        this.setMenuList(new SOPEnquiry());
        this.setMenuList(new SOPFrequently());
        this.setMenuList(new SOPPeriodic());
        this.setMenuList(new SOPRepors());
        this.setMenuList(new SOPSetup());
        this.setMenuList(new SOPSystemMenu());
    }
}
